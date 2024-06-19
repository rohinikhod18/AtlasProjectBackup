/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: FinscanPort.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.finscanport;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.sanction.core.ISanctionService;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusRequest;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusResponse;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovativesystems.ArrayOfLSTServicesLookupRequest;
import com.innovativesystems.LSTServicesGetStatusRequest;
import com.innovativesystems.LSTServicesGetStatusResponse;
import com.innovativesystems.LSTServicesLookup;
import com.innovativesystems.LSTServicesLookupSoap;
import javax.xml.namespace.QName;

/**
 * The Class FinscanPort.
 * 
 * @author abhijitg
 */
public class FinscanPort implements ISanctionService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FinscanPort.class);

	/** The finscan port. */
	private static FinscanPort finscanPort = new FinscanPort();

	/** The finscan transformer. */
	private static FinscanTransformer finscanTransformer = FinscanTransformer.getInstance();
	
	private static final String QNAME_SERVICE_URL = "http://innovativesystems.com/";
	
	private static final String QNAME_SERVICE = "LSTServicesLookup";

	/**
	 * Instantiates a new finscan port.
	 */
	private FinscanPort() {

	}

	/**
	 * Gets the single instance of FinscanPort.
	 *
	 * @return single instance of FinscanPort
	 */
	public static FinscanPort getInstance() {
		return finscanPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.es.compliance.sanction.core.ISanctionService#
	 * checkSanctionDetails(com.currenciesdirect.gtg.es.compliance.sanction.core
	 * .domain.SanctionRequest)
	 */
	@Override
	public SanctionResponse checkSanctionDetails(SanctionRequest sanctionRequest,
			ProviderProperty providerProperty) throws SanctionException {
		com.innovativesystems.LSTServicesLookupMultiResponse multiResponse = null;
		ArrayOfLSTServicesLookupRequest serviceLookUpRequest = null;
		SanctionResponse sanctionResponse = new SanctionResponse();
		try {
			LOG.debug("Calling SLLookUpMultiTask ,Request : {}", sanctionRequest);
			serviceLookUpRequest = finscanTransformer.transformRequestObject(sanctionRequest, providerProperty);			
			URL endpointUrl = new URL(providerProperty.getEndPointUrl());
			QName serviceName = new QName(QNAME_SERVICE_URL,QNAME_SERVICE);
			LSTServicesLookup lSTServicesLookup = new LSTServicesLookup(endpointUrl,serviceName);
			LSTServicesLookupSoap serviceLookUpStub = lSTServicesLookup.getLSTServicesLookupSoap();
			multiResponse = serviceLookUpStub.slLookupMulti(serviceLookUpRequest);
			sanctionResponse = finscanTransformer.transformResponseObject(sanctionRequest, multiResponse,
					providerProperty.getAlwaysPass());

			ObjectMapper mapper = new ObjectMapper();
			sanctionResponse.setProviderResponse(mapper.writeValueAsString(multiResponse));
		} catch (SanctionException sanctionException) {
			LOG.warn(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorDescription(), sanctionException);
			sanctionResponse.setErrorCode(sanctionException.getSanctionErrors().getErrorCode());
			sanctionResponse.setErrorDescription(sanctionException.getSanctionErrors().getErrorDescription());
			prepareSampleResponses(sanctionRequest, sanctionResponse);
			checkAlwaysPassForLookupMulti(sanctionResponse, providerProperty.getAlwaysPass());
		} catch (Exception e) {
			LOG.error(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorDescription(), e);
			sanctionResponse
					.setErrorCode(SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER.getErrorCode());
			sanctionResponse.setErrorDescription(
					SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER.getErrorDescription());
			prepareSampleResponses(sanctionRequest, sanctionResponse);
			checkAlwaysPassForLookupMulti(sanctionResponse, providerProperty.getAlwaysPass());
		}
		return sanctionResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.sanction.core.ISanctionService#
	 * getSanctionStatus(com.currenciesdirect.gtg.compliance.sanction.core.
	 * domain.SanctionGetStatusRequest,
	 * com.currenciesdirect.gtg.compliance.sanction.core.domain.
	 * SanctionProviderProperty)
	 */
	@Override
	public SanctionGetStatusResponse getSanctionStatus(SanctionGetStatusRequest statusRequest,
			ProviderProperty providerProperty) throws SanctionException {
		SanctionGetStatusResponse sanctionResponse = new SanctionGetStatusResponse();
		LSTServicesGetStatusResponse getStatusResponse = new LSTServicesGetStatusResponse();
		LSTServicesGetStatusRequest getStatusRequest = new LSTServicesGetStatusRequest();
		try {
			LOG.debug("Calling Get Status Request : {}" ,statusRequest);
			getStatusRequest = finscanTransformer.transformGetContactStatusRequest(statusRequest, providerProperty);
			URL endpointUrl = new URL(providerProperty.getEndPointUrl());
			QName serviceName = new QName(QNAME_SERVICE_URL,QNAME_SERVICE);
			LSTServicesLookup lSTServicesLookup = new LSTServicesLookup(endpointUrl,serviceName);
			LSTServicesLookupSoap serviceLookUpStub = lSTServicesLookup.getLSTServicesLookupSoap();
			getStatusResponse = serviceLookUpStub.slGetStatus(getStatusRequest);

			sanctionResponse = finscanTransformer.transformContactStatusResponse(getStatusResponse, statusRequest,
					providerProperty.getAlwaysPass());

		} catch (SanctionException sanctionException) {
			LOG.warn(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorDescription(), sanctionException);
			sanctionResponse.setApplicationId(statusRequest.getApplicationId());
			sanctionResponse.setId(statusRequest.getId());
			sanctionResponse.setErrorCode(sanctionException.getSanctionErrors().getErrorCode());
			sanctionResponse.setErrorDescription(sanctionException.getSanctionErrors().getErrorDescription());
			checkAlwaysPassForGetStatusResponse(sanctionResponse, providerProperty);
		} catch (Exception e) {
			LOG.error(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorDescription(), e);
			sanctionResponse.setApplicationId(statusRequest.getApplicationId());
			sanctionResponse.setId(statusRequest.getId());
			sanctionResponse
					.setErrorCode(SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER.getErrorCode());
			sanctionResponse.setErrorDescription(
					SanctionErrors.ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER.getErrorDescription());
			sanctionResponse.setStatus(Constants.SERVICE_FAILURE);
			checkAlwaysPassForGetStatusResponse(sanctionResponse, providerProperty);
		}
		return sanctionResponse;
	}

	/**
	 * Check always pass for get status response.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param providerProperty
	 *            the provider property
	 */
	// for testing purpose only.. need to remove alwayspass hook after testing
	private void checkAlwaysPassForGetStatusResponse(SanctionGetStatusResponse sanctionResponse,
			ProviderProperty providerProperty) {
		if (null != providerProperty.getAlwaysPass() && providerProperty.getAlwaysPass()) {
			sanctionResponse.setStatus(Constants.PASS);
		}
	}

	/**
	 * Prepare sample responses.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param sanctionResponse
	 *            the sanction response
	 */
	private void prepareSampleResponses(SanctionRequest sanctionRequest, SanctionResponse sanctionResponse) {
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		List<SanctionBeneficiaryResponse> beneficiaryResponses = new ArrayList<>();
		List<SanctionBankResponse> bankResponses = new ArrayList<>();

		if (null != sanctionRequest.getContactrequests() && !sanctionRequest.getContactrequests().isEmpty()) {
			for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
				SanctionContactResponse contactResponse = new SanctionContactResponse();
				contactResponse.setStatus(Constants.FAIL);
				contactResponse.setContactId(contactRequest.getContactId());
				contactResponse.setSanctionId(contactRequest.getSanctionId());
				contactResponse.setErrorCode(sanctionResponse.getErrorCode());
				contactResponse.setErrorDescription(sanctionResponse.getErrorDescription());
				contactResponses.add(contactResponse);
			}
			sanctionResponse.setContactResponses(contactResponses);
		}

		if (null != sanctionRequest.getBeneficiaryRequests() && !sanctionRequest.getBeneficiaryRequests().isEmpty()) {
			for (SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()) {
				SanctionBeneficiaryResponse beneficiaryResponse = new SanctionBeneficiaryResponse();
				beneficiaryResponse.setStatus(Constants.FAIL);
				beneficiaryResponse.setBeneficiaryId(beneficiaryRequest.getBeneficiaryId());
				beneficiaryResponse.setSanctionId(beneficiaryRequest.getSanctionId());
				beneficiaryResponse.setErrorCode(sanctionResponse.getErrorCode());
				beneficiaryResponse.setErrorDescription(sanctionResponse.getErrorDescription());
				beneficiaryResponses.add(beneficiaryResponse);
			}
			sanctionResponse.setBeneficiaryResponses(beneficiaryResponses);
		}

		if (null != sanctionRequest.getBankRequests() && !sanctionRequest.getBankRequests().isEmpty()) {
			for (SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()) {
				SanctionBankResponse bankResponse = new SanctionBankResponse();
				bankResponse.setStatus(Constants.FAIL);
				bankResponse.setBankID(bankRequest.getBankId());
				bankResponse.setSanctionId(bankRequest.getSanctionId());
				bankResponse.setErrorCode(sanctionResponse.getErrorCode());
				bankResponse.setErrorDescription(sanctionResponse.getErrorDescription());
				bankResponses.add(bankResponse);
			}
			sanctionResponse.setBankResponses(bankResponses);
		}

	}

	/**
	 * Check always pass for lookup multi.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param alwaysPass
	 *            the always pass
	 */
	// for testing purpose only.. need to remove alwayspass hook after testing
	private void checkAlwaysPassForLookupMulti(SanctionResponse sanctionResponse, Boolean alwaysPass) {
		List<SanctionContactResponse> contactResponses = sanctionResponse.getContactResponses();
		List<SanctionBeneficiaryResponse> beneficiaryResponses = sanctionResponse.getBeneficiaryResponses();
		List<SanctionBankResponse> bankResponses = sanctionResponse.getBankResponses();

		if (null != contactResponses && !contactResponses.isEmpty()) {
			checkAlwaysPassForContact(sanctionResponse, alwaysPass, contactResponses);
		}

		if (null != beneficiaryResponses && !beneficiaryResponses.isEmpty()) {
			checkAlwaysPassForBeneficiary(sanctionResponse, alwaysPass, beneficiaryResponses);
		}

		if (null != bankResponses && !bankResponses.isEmpty()) {
			checkAlwaysPassForBank(sanctionResponse, alwaysPass, bankResponses);
		}

	}

	/**
	 * Check always pass for bank.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param alwaysPass
	 *            the always pass
	 * @param bankResponses
	 *            the bank responses
	 */
	// for testing purpose only.. need to remove alwayspass hook after testing
	private void checkAlwaysPassForBank(SanctionResponse sanctionResponse, Boolean alwaysPass,
			List<SanctionBankResponse> bankResponses) {
		for (SanctionBankResponse bankResponse : bankResponses) {
			if (null != alwaysPass && alwaysPass) {
				bankResponse.setStatus(Constants.PASS);
			}
		}
		sanctionResponse.setBankResponses(bankResponses);
	}

	/**
	 * Check always pass for beneficiary.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param alwaysPass
	 *            the always pass
	 * @param beneficiaryResponses
	 *            the beneficiary responses
	 */
	// for testing purpose only.. need to remove alwayspass hook after testing
	private void checkAlwaysPassForBeneficiary(SanctionResponse sanctionResponse, Boolean alwaysPass,
			List<SanctionBeneficiaryResponse> beneficiaryResponses) {
		for (SanctionBeneficiaryResponse beneficiaryResponse : beneficiaryResponses) {
			if (null != alwaysPass && alwaysPass) {
				beneficiaryResponse.setStatus(Constants.PASS);
			}
		}
		sanctionResponse.setBeneficiaryResponses(beneficiaryResponses);
	}

	/**
	 * Check always pass for contact.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param alwaysPass
	 *            the always pass
	 * @param contactResponses
	 *            the contact responses
	 */
	// for testing purpose only.. need to remove alwayspass hook after testing
	private void checkAlwaysPassForContact(SanctionResponse sanctionResponse, Boolean alwaysPass,
			List<SanctionContactResponse> contactResponses) {
		for (SanctionContactResponse contactResponse : contactResponses) {
			if (null != alwaysPass && alwaysPass) {
				contactResponse.setStatus(Constants.PASS);
			}
		}
		sanctionResponse.setContactResponses(contactResponses);
	}

}
