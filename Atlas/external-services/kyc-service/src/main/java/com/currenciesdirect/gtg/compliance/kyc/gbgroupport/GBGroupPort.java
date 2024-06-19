/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.gbgroupport;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.types.UnsignedInt;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.kyc.core.IKYCProviderService;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.ProfileVersionID;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.id3global.www.ID3gWS._2013._04.BasicHttpBinding_GlobalAuthenticateStub;
import com.id3global.www.ID3gWS._2013._04.GlobalInputData;
import com.id3global.www.ID3gWS._2013._04.GlobalProfileIDVersion;
import com.id3global.www.ID3gWS._2013._04.GlobalResultData;

/**
 * The Class GBGroupPort.
 *
 * @author manish
 */
public class GBGroupPort implements IKYCProviderService, Callable<KYCContactResponse> {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(GBGroupPort.class);

	/** The request. */
	private KYCContactRequest request;

	/** The property. */
	private KYCProviderProperty property;

	/**
	 * Instantiates a new GB group port.
	 *
	 * @param request the request
	 * @param property the property
	 */
	public GBGroupPort(KYCContactRequest request, KYCProviderProperty property) {
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
		GlobalInputData globalInputData = new GlobalInputData();
		KYCContactResponse response = new KYCContactResponse();
		String id3GlobalEndpointUrl = null;
		String profileIDVersionID = null;
		List<ProfileVersionID> profileVersionIDs = null;
		GlobalResultData result = null;
		try {

			id3GlobalEndpointUrl = property.getEndPointUrl();

			profileVersionIDs = property.getProfileVersionIDList();
			for (ProfileVersionID profileVersionID : profileVersionIDs) {
				if (profileVersionID.getCountry().equalsIgnoreCase(request.getAddress().getCountry().trim())) {
					profileIDVersionID = profileVersionID.getId();
					break;
				}
			}

			globalInputData = GBGroupTransformer.getInstance().transformRequestObject(request);
			URL endpointUrl = new URL(id3GlobalEndpointUrl);
			Service service = new Service();
			BasicHttpBinding_GlobalAuthenticateStub authenticateStub = new BasicHttpBinding_GlobalAuthenticateStub(
					endpointUrl, service);
			authenticateStub.setHeader(getSecurityHeader(property));
			authenticateStub.setTimeout(property.getSocketTimeoutMillis());
			GlobalProfileIDVersion profileIDVersion = new GlobalProfileIDVersion();
			profileIDVersion.setID(profileIDVersionID);
			UnsignedInt version = new UnsignedInt(0);
			profileIDVersion.setVersion(version);
			result = authenticateStub.authenticateSP(profileIDVersion, request.getContactSFId(), globalInputData);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(result);
			response.setProviderResponse(json);
			response.setProviderUniqueRefNo(result.getCustomerRef());
			response.setProviderMethod(Constants.GBGROUP_PROVIDER_METHOD);
			response.setProviderName(Constants.GBGROUP_PROVIDER);
			response.setOverallScore(String.valueOf(result.getScore()));
			
			response.setBandText(String.valueOf(result.getBandText()));
			
			if(!Constants.PASS.equalsIgnoreCase(response.getBandText())){
				response.setStatus(Constants.FAIL);
			}else{
				response.setStatus(Constants.PASS);
			}
			
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
		} catch (KYCException exception) {
			logDebug(exception);
			response.setBandText(Constants.NOT_PERFORMED);
			response.setStatus(Constants.NOT_PERFORMED);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.GBGROUP_PROVIDER);
			response.setProviderMethod(Constants.GBGROUP_PROVIDER_METHOD);
			response.setErrorCode(exception.getkycErrors().getErrorCode());
			response.setErrorDescription(exception.getDescription());
		} catch (Exception e) {
			LOG.error(" Error in GBGroupPort : checkKYCdetails()" , e);
			response.setBandText(Constants.SERVICE_FAILURE);
			response.setStatus(Constants.SERVICE_FAILURE);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.GBGROUP_PROVIDER);
			response.setProviderMethod(Constants.GBGROUP_PROVIDER_METHOD);
			response.setErrorCode(KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_GBGROUP_KYC_PROVIDER.getErrorCode());
			response.setErrorDescription(KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_GBGROUP_KYC_PROVIDER.getErrorDescription()+e.getMessage());
		}
		if(property.getAlwaysPass()!=null&&property.getAlwaysPass()){
			response.setBandText(Constants.PASS);
			response.setStatus(Constants.PASS);
		}
		/**
		 * Code added for AT-1504 by Vishal J
		 * 
		 * Description: Provider is going to send 2 new bandtext separately for address verification and identity verification 
		 * (POA Needed or POI Needed) so depending upon that we will set new response description from Compliance towards SF.
		 * */
		if(property.getPoaRequired()!=null&&property.getPoaRequired()){
			response.setBandText("POA NEEDED");
			response.setStatus(Constants.FAIL);
		}
		if(property.getPoiRequired()!=null&&property.getPoiRequired()){
			response.setBandText("POI NEEDED");
			response.setStatus(Constants.FAIL);
		}
		
		return response;
	}

	/**
	 * Gets the security header.
	 *
	 * @param property the property
	 * @return the security header
	 * @throws KYCException the KYC exception
	 */
	private SOAPHeaderElement getSecurityHeader(KYCProviderProperty property) throws KYCException {
		SOAPHeaderElement headerGbGroup = null;
		String id3GlobalQname = null;
		String userName = null;
		String passWord = null;
		try {
			id3GlobalQname = property.getQname();
			userName = property.getUserName();
			passWord = property.getPassWord();
			QName headerName = new QName(id3GlobalQname, "Security");
			headerGbGroup = new SOAPHeaderElement(headerName);
			headerGbGroup.setActor(null);
			headerGbGroup.setPrefix("wsse");
			headerGbGroup.setMustUnderstand(true);
			SOAPElement utElemGbGroup;
			utElemGbGroup = headerGbGroup.addChildElement("UsernameToken");
			SOAPElement userNameElemGbGroup = utElemGbGroup.addChildElement("Username");
			userNameElemGbGroup.setValue(userName);
			SOAPElement passwordElemGbGroup = utElemGbGroup.addChildElement("Password");
			passwordElemGbGroup.setValue(passWord);
		} catch (Exception exception) {
			throw new KYCException(KYCErrors.ERROR_GENERATING_SECURITY_HEADER, exception);
		}
		return headerGbGroup;
	}


	
	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class GBGroup Port :", exception);
	}
	

}
