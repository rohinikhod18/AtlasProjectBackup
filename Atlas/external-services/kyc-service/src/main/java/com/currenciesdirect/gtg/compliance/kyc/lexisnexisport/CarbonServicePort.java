package com.currenciesdirect.gtg.compliance.kyc.lexisnexisport;

import java.util.concurrent.Callable;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.kyc.core.IKYCProviderService;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verid.carbon.integration.Carbon;
import com.verid.carbon.integration.CarbonWebService;
import com.verid.carbon.integration.TransactionIdentityVerification;
import com.verid.carbon.integration.TransactionResponse;

/**
 * The Class CarbonServicePort.
 */
public class CarbonServicePort implements IKYCProviderService, Callable<KYCContactResponse> {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CarbonServicePort.class);

	/** The request. */
	private KYCContactRequest request;

	/** The property. */
	private KYCProviderProperty property;

	/**
	 * Instantiates a new carbon service port.
	 *
	 * @param request
	 *            the request
	 * @param property
	 *            the property
	 */
	public CarbonServicePort(KYCContactRequest request, KYCProviderProperty property) {
		this.request = request;
		this.property = property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public KYCContactResponse call() throws Exception {
		return checkKYCdetails(request, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.IKYCProviderService#
	 * checkKYCdetails(com.currenciesdirect.gtg.compliance.commons.domain.kyc.
	 * KYCContactRequest,
	 * com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty)
	 */
	@Override
	public KYCContactResponse checkKYCdetails(KYCContactRequest request, KYCProviderProperty property)
			throws KYCException {
		KYCContactResponse response = new KYCContactResponse();
		CarbonServiceTransformer carbonServiceTransformer = CarbonServiceTransformer.getInstance();
		try {
			QName serviceName = new QName(property.getQname(), "CarbonWebService");
			CarbonWebService ss = new CarbonWebService(
					CarbonWebService.class.getClassLoader().getResource("resources/ws_1.wsdl"), serviceName);
			Carbon port = ss.getCarbonSoapPort();
			BindingProvider prov = (BindingProvider) port;
			prov.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, property.getEndPointUrl());
			prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, property.getUserName());
			prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, property.getPassWord());

			TransactionIdentityVerification transactionIdentityVerification = carbonServiceTransformer
					.transformRequestObject(request, property);
			TransactionResponse transactionResponse = port.identityVerification(transactionIdentityVerification);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(transactionResponse);
			response.setProviderResponse(json);
			response.setProviderName(Constants.CARBONSERVICE_PROVIDER);
			response.setProviderMethod(Constants.CARBONSERVICE_PROVIDER_METHOD);
			response.setOverallScore("--");
			handleCarbonServiceResponse(response, transactionResponse);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			
		} catch (KYCException exception) {
			response.setBandText(Constants.NOT_PERFORMED);
			response.setStatus(Constants.NOT_PERFORMED);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			logDebug(exception);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.CARBONSERVICE_PROVIDER);
			response.setProviderMethod(Constants.CARBONSERVICE_PROVIDER_METHOD);
			response.setErrorCode(exception.getkycErrors().getErrorCode());
			response.setErrorDescription(exception.getDescription());
		} catch (Exception e) {
			LOG.error(" Error in CarbonServicePort : checkKYCdetails()", e);
			response.setBandText(Constants.SERVICE_FAILURE);
			response.setStatus(Constants.SERVICE_FAILURE);
			response.setOverallScore(Constants.NOT_AVAILABLE);
			response.setContactSFId(request.getContactSFId());
			response.setId(request.getId());
			response.setProviderName(Constants.CARBONSERVICE_PROVIDER);
			response.setProviderMethod(Constants.CARBONSERVICE_PROVIDER_METHOD);
			response.setErrorCode(KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_CARBONSERVICE_KYC_PROVIDER.getErrorCode());
			response.setErrorDescription(
					KYCErrors.ERROR_WHILE_SENDING_REQUEST_TO_CARBONSERVICE_KYC_PROVIDER.getErrorDescription());
		}
		if(property.getAlwaysPass()!=null && property.getAlwaysPass()){
			response.setBandText(Constants.PASS);
			response.setStatus(Constants.PASS);
		}
		return response;
	}

	/**
	 * Log debug.
	 *
	 * @param exception
	 *            the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class CarbonService Port :", exception);
	}

	/**
	 * Handle carbon service response.
	 *
	 * @param response
	 *            the response
	 * @param transactionResponse
	 *            the transaction response
	 */
	private void handleCarbonServiceResponse(KYCContactResponse response, TransactionResponse transactionResponse) {
		if (!Constants.PASSED
				.equalsIgnoreCase(transactionResponse.getTransactionStatus().getTransactionResult().toString())) {
			setFailureResponse(response, transactionResponse);
		} else {
			response.setStatus(Constants.PASS);
			response.setBandText(Constants.PASS);
		}
	}

	/**
	 * Sets the failure response.
	 *
	 * @param response
	 *            the response
	 * @param transactionResponse
	 *            the transaction response
	 */
	private void setFailureResponse(KYCContactResponse response, TransactionResponse transactionResponse) {
		if (Constants.FAILED
				.equalsIgnoreCase(transactionResponse.getTransactionStatus().getTransactionResult().toString())
				|| Constants.INVALID_TXN_INITIATED
						.equalsIgnoreCase(transactionResponse.getInformation().get(0).getDetailCode())
				|| Constants.ERROR
						.equalsIgnoreCase(transactionResponse.getTransactionStatus().getTransactionResult().toString())) {
			response.setStatus(Constants.FAIL);
			response.setBandText(Constants.FAIL);
		} else{
			response.setStatus(Constants.SERVICE_FAILURE);
			response.setBandText(Constants.SERVICE_FAILURE);
		}
	}

}
