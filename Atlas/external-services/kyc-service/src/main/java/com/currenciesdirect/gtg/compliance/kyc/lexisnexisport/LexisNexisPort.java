/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.lexisnexisport;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.kyc.core.IKYCProviderService;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seisint.webservices.WsIdentity.InstantIDIntl2Request;
import com.seisint.webservices.WsIdentity.InstantIDIntl2ResponseEx;
import com.seisint.webservices.WsIdentity.WsIdentityServiceSoapStub;


/**
 * The Class LexisNexisPort.
 *
 * @author manish
 */
public class LexisNexisPort implements IKYCProviderService, Callable<KYCContactResponse> {

	/** The Constant LOG. */
	private static final Logger LOG =  LoggerFactory.getLogger(LexisNexisPort.class);

	/** The request. */
	private KYCContactRequest request;

	/** The property. */
	private KYCProviderProperty property;
	
	/**
	 * Instantiates a new lexis nexis port.
	 *
	 * @param request the request
	 * @param property the property
	 */
	public LexisNexisPort(KYCContactRequest request, KYCProviderProperty property) {
		this.request = request;
		this.property = property;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public KYCContactResponse call() throws Exception {
		return checkKYCdetails(request, property);
	}

	

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.IKYCProviderService#checkKYCdetails(com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCContactRequest, com.currenciesdirect.gtg.compliance.kyc.core.domain.ProviderProperty)
	 */
	@Override
	public KYCContactResponse checkKYCdetails(KYCContactRequest request, KYCProviderProperty property)
			throws KYCException {
		KYCContactResponse response = new KYCContactResponse();
		LexisNexisTransformer lexisNexisTransformer = LexisNexisTransformer.getInstance();
		String lexisNexisEndpointUrl = null;

		try {
			keepOnlyAlphaNumbericsInPhone(request.getPhone());
			if (request.getAddress() != null && request.getAddress().getPostCode() != null) {
				request.getAddress().setPostCode(keepOnlyAlphaNumberics(request.getAddress().getPostCode()));
			}
			lexisNexisEndpointUrl = property.getEndPointUrl();

			InstantIDIntl2Request idIntl2Request = lexisNexisTransformer.transformRequestObject(request);
			URL endpointUrl = new URL(lexisNexisEndpointUrl);
			Service service = new Service();
			service.getEngine().setOption(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");

			WsIdentityServiceSoapStub wsIdentityServiceSoapStub = new WsIdentityServiceSoapStub(endpointUrl, service);
			wsIdentityServiceSoapStub.setHeader(getSecurityHeader(property));
			wsIdentityServiceSoapStub.setTimeout(property.getSocketTimeoutMillis());
			InstantIDIntl2ResponseEx result = wsIdentityServiceSoapStub.instantIDIntl2(idIntl2Request);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(result);
			response.setProviderResponse(json);
			response.setProviderUniqueRefNo(result.getResponse().getHeader().getQueryId());
			response.setProviderName(Constants.LEXISNEXIS_PROVIDER);
			response.setProviderMethod(Constants.LEXISNEXIS_PROVIDER_METHOD);
			response.setOverallScore(result.getResponse().getResult().getInternationalVerificationIndex());
			handleLexisNexisResponse(response, result);
			
			
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
		} catch (KYCException exception) {
			response.setBandText(Constants.NOT_PERFORMED);
			response.setStatus(Constants.NOT_AVAILABLE);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			logDebug(exception);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.LEXISNEXIS_PROVIDER);
			response.setProviderMethod(Constants.LEXISNEXIS_PROVIDER_METHOD);
			response.setErrorCode(exception.getkycErrors().getErrorCode());
			response.setErrorDescription(exception.getDescription());
		} catch (Exception e) {
			LOG.error(" Error in LexisNexisPort : checkKYCdetails()" , e);
			response.setBandText(Constants.SERVICE_FAILURE);
			response.setStatus(Constants.SERVICE_FAILURE);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.LEXISNEXIS_PROVIDER);
			response.setProviderMethod(Constants.LEXISNEXIS_PROVIDER_METHOD);
			response.setErrorCode(KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_LEXISNEXIS_KYC_PROVIDER.getErrorCode());
			response.setErrorDescription(KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_LEXISNEXIS_KYC_PROVIDER.getErrorDescription());
		}
		if(property.getAlwaysPass()!=null&&property.getAlwaysPass()){
			response.setBandText(Constants.PASS);
			response.setStatus(Constants.PASS);
		}
		return response;
	}

	/**
	 * Handle lexis nexis response.
	 *
	 * @param response the response
	 * @param result the result
	 */
	private void handleLexisNexisResponse(KYCContactResponse response, InstantIDIntl2ResponseEx result) {
		Integer overAllScore;
		if (result.getResponse().getResult().getInternationalVerificationIndex() != null) {
			overAllScore = Integer.parseInt(result.getResponse().getResult().getInternationalVerificationIndex());
			if (overAllScore <= 50 && overAllScore >= 40) {
				response.setBandText(Constants.PASS);
			} else if (overAllScore <= 40 && overAllScore >= 30) {
				response.setBandText(Constants.REFER);
			} else if (overAllScore < 30) {
				response.setBandText(Constants.NO_MATCH);
			}
			
			if(!Constants.PASS.equalsIgnoreCase(response.getBandText())){
				response.setStatus(Constants.FAIL);
			}else{
				response.setStatus(Constants.PASS);
			}
		}
	}

	/**
	 * Gets the security header.
	 *
	 * @param property the property
	 * @return the security header
	 * @throws KYCException the KYC exception
	 */
	private SOAPHeaderElement getSecurityHeader(KYCProviderProperty property) throws KYCException {
		String lexisNexisQname = null;
		String userNameLexisNexis = null;
		String passWordLexisNexis = null;
		SOAPHeaderElement headerLexisNexis = null;
		try {
			lexisNexisQname = property.getQname();
			userNameLexisNexis = property.getUserName();
			passWordLexisNexis = property.getPassWord();
			QName headerName = new QName(lexisNexisQname, "Security");
			headerLexisNexis = new SOAPHeaderElement(headerName);
			headerLexisNexis.setActor(null);
			headerLexisNexis.setPrefix("wsse");
			headerLexisNexis.setMustUnderstand(true);

			SOAPElement utElem;
			utElem = headerLexisNexis.addChildElement("UsernameToken");
			SOAPElement userNameElem = utElem.addChildElement("Username");
			userNameElem.setValue(userNameLexisNexis);
			SOAPElement passwordElem = utElem.addChildElement("Password");
			passwordElem.setValue(passWordLexisNexis);
		} catch (Exception exception) {
			throw new KYCException(KYCErrors.ERROR_GENERATING_SECURITY_HEADER, exception);
		}
		return headerLexisNexis;
	}




	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class GBGroup Port :", exception);
	}
	
	/**
	 * Keep only alpha numberics in phone.
	 *
	 * @param phones the phones
	 */
	private  void keepOnlyAlphaNumbericsInPhone(List<Phone> phones){
		if(phones != null){
			for(Phone phone : phones){
				if(phone != null){
					phone.setNumber(keepOnlyNumberics(phone.getNumber()));
				}
			}
		}
	}
	
	/**
	 * Keep only alpha numberics.
	 *
	 * @param orgString the org string
	 * @return the string
	 */
	private String keepOnlyAlphaNumberics(String orgString){
		if(orgString == null || orgString.trim().isEmpty()){
			return orgString;
		}
		String newString =orgString;
		
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match= pt.matcher(newString);
        while(match.find())
        {
            String s= match.group();
            newString=newString.replaceAll("\\"+s, "");
        }
		return newString;
		
	}
	
	/**
	 * Keep only numberics.
	 *
	 * @param orgString the org string
	 * @return the string
	 */
	private String keepOnlyNumberics(String orgString){
		if(orgString == null || orgString.trim().isEmpty()){
			return orgString;
		}
		String newString =orgString.replaceAll("[a-zA-Z]", "");
		
        Pattern pt = Pattern.compile("[^0-9]");
        Matcher match= pt.matcher(newString);
        while(match.find())
        {
            String s= match.group();
            newString=newString.replaceAll("\\"+s, "");
        }
		return newString;
		
	}
}
