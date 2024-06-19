/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: FinscanTransformer.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.finscanport;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusRequest;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusResponse;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;
import com.currenciesdirect.gtg.compliance.sanction.util.SanctionDateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovativesystems.ArrayOfLSTServicesLookupRequest;
import com.innovativesystems.ArrayOfLSTServicesLookupResponse;
import com.innovativesystems.ArrayOfSLComplianceList;
import com.innovativesystems.ArrayOfSLComplianceRecord;
import com.innovativesystems.ArrayOfSLCustomStatus;
import com.innovativesystems.LSTServicesGetStatusRequest;
import com.innovativesystems.LSTServicesGetStatusResponse;
import com.innovativesystems.LSTServicesLookupMultiResponse;
import com.innovativesystems.LSTServicesLookupRequest;
import com.innovativesystems.LSTServicesLookupResponse;
import com.innovativesystems.SLClientSearchCodeEnum;
import com.innovativesystems.SLClientStatusEnum;
import com.innovativesystems.SLComplianceRecord;
import com.innovativesystems.SLCustomStatus;
import com.innovativesystems.SLGenderEnum;
import com.innovativesystems.SLPairStatusEnum;
import com.innovativesystems.SLResultTypeEnum;
import com.innovativesystems.SLSearchTypeEnum;
import com.innovativesystems.SLYesNoEnum;

/**
 * The Class FinscanTransformer.
 * 
 * @author Manish m
 */
public class FinscanTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FinscanTransformer.class);

	/** The finscan transformer. */
	private static FinscanTransformer finscanTransformer;

	/**
	 * Instantiates a new finscan transformer.
	 */
	private FinscanTransformer() {

	}

	/**
	 * Gets the single instance of FinscanTransformer.
	 *
	 * @return single instance of FinscanTransformer
	 */
	public static FinscanTransformer getInstance() {
		if (finscanTransformer == null) {
			finscanTransformer = new FinscanTransformer();
		}
		return finscanTransformer;
	}

	/**
	 * Transform request object.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param providerProperty
	 *            the provider property
	 * @return the LST services lookup request[]
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public ArrayOfLSTServicesLookupRequest transformRequestObject(SanctionRequest sanctionRequest,
			ProviderProperty providerProperty) throws SanctionException {

		ArrayOfLSTServicesLookupRequest lookupRequests = new ArrayOfLSTServicesLookupRequest();

		try {
			
			if (sanctionRequest.getContactrequests() != null) {
				tranformRequestForContact(sanctionRequest, providerProperty, lookupRequests.getLSTServicesLookupRequest());
			}
			if (sanctionRequest.getBeneficiaryRequests() != null) {
				tranformRequestForBeneficiary(sanctionRequest, providerProperty, lookupRequests.getLSTServicesLookupRequest());
			}
			if (sanctionRequest.getBankRequests() != null) {
				tranformRequestForBank(sanctionRequest, providerProperty, lookupRequests.getLSTServicesLookupRequest());
			}

		} catch (SanctionException e) {

			throw e;
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_REQUEST, e);
		}
		return lookupRequests;
	}

	/**
	 * Tranform request for contact.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param providerProperty
	 *            the provider property
	 * @param lookupRequests
	 *            the lookup requests
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public void tranformRequestForContact(SanctionRequest sanctionRequest, ProviderProperty providerProperty,
			List<LSTServicesLookupRequest> lookupRequests) throws SanctionException {
		try {
			for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
				LSTServicesLookupRequest lookupRequest = new LSTServicesLookupRequest();
				lookupRequest.setOrganizationName(Constants.CD);
				lookupRequest.setUserName(providerProperty.getUserName());
				lookupRequest.setPassword(providerProperty.getPassWord());
				lookupRequest.setApplicationId(Constants.CLNTS);

				// Modified wrt AT-1951
				lookupRequest.setLists(new ArrayOfSLComplianceList());

				if (contactRequest.getCategory() == null || contactRequest.getCategory().trim().isEmpty()
						|| Constants.INDIVIDUAL.equalsIgnoreCase(contactRequest.getCategory())) {
					lookupRequest.setSearchType(SLSearchTypeEnum.INDIVIDUAL);
				} else {
					lookupRequest.setSearchType(SLSearchTypeEnum.ORGANIZATION);
				}
				lookupRequest.setClientStatus(SLClientStatusEnum.ACTIVE);
				lookupRequest.setClientId(contactRequest.getSanctionId());
				setGenderForContact(contactRequest, lookupRequest);
				lookupRequest.setNameLine(contactRequest.getFullName());
				lookupRequest.setClientSearchCode(SLClientSearchCodeEnum.FULL_NAME);
				lookupRequest.setReturnComplianceRecords(SLYesNoEnum.YES);
				lookupRequest.setAddClient(SLYesNoEnum.YES);
				lookupRequest.setSendToReview(SLYesNoEnum.YES);
				lookupRequest.setUpdateUserFields(SLYesNoEnum.YES);
				lookupRequest.setUserField1Label(Constants.CITIZENSHIP);
				lookupRequest.setUserField1Value(contactRequest.getCountry());
				lookupRequest.setUserField2Label("Date of Birth");
				String dob = SanctionDateTimeFormatter.getDOBForProvider(contactRequest.getDob());
				lookupRequest.setUserField2Value(dob);
				lookupRequest.setReturnCategory(SLYesNoEnum.YES);
				lookupRequest.setReturnSourceLists(SLYesNoEnum.YES);
				lookupRequest.setGenerateclientId(SLYesNoEnum.NO);
				if(null != sanctionRequest.getCustomerNumber())
					lookupRequest.setComment(sanctionRequest.getCustomerNumber());
				lookupRequests.add(lookupRequest);
				String json = JsonConverterUtil.convertToJsonWithNull(lookupRequest);
				LOG.warn("\nSanction Soap LookUp Contact Request : \n{}",json);
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_REQUEST, e);
		}
	}

	/**
	 * Sets the gender for contact.
	 *
	 * @param contactRequest
	 *            the contact request
	 * @param lookupRequest
	 *            the lookup request
	 */
	private void setGenderForContact(SanctionContactRequest contactRequest, LSTServicesLookupRequest lookupRequest) {
		if (contactRequest.getGender() != null && !contactRequest.getGender().isEmpty()) {
			if ("Male".equalsIgnoreCase(contactRequest.getGender().trim())) {
				lookupRequest.setGender(SLGenderEnum.MALE);
			} else if ("Female".equalsIgnoreCase(contactRequest.getGender().trim())) {
				lookupRequest.setGender(SLGenderEnum.FEMALE);
			} else {
				lookupRequest.setGender(SLGenderEnum.UNKNOWN);
			}
		} else {
			lookupRequest.setGender(SLGenderEnum.UNKNOWN);
		}
	}

	/**
	 * Tranform request for beneficiary.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param providerProperty
	 *            the provider property
	 * @param lookupRequests
	 *            the lookup requests
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public void tranformRequestForBeneficiary(SanctionRequest sanctionRequest, ProviderProperty providerProperty,
			List<LSTServicesLookupRequest> lookupRequests) throws SanctionException {
		try {
			for (SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()) {
				LSTServicesLookupRequest lookupRequest = new LSTServicesLookupRequest();
				lookupRequest.setOrganizationName(Constants.CD);
				lookupRequest.setUserName(providerProperty.getUserName());
				lookupRequest.setPassword(providerProperty.getPassWord());
				lookupRequest.setApplicationId(Constants.BNFC);

				// Modified wrt AT-1951
				lookupRequest.setLists(new ArrayOfSLComplianceList());

				if (beneficiaryRequest.getCategory() == null || beneficiaryRequest.getCategory().trim().isEmpty()
						|| Constants.INDIVIDUAL.equalsIgnoreCase(beneficiaryRequest.getCategory())) {
					lookupRequest.setSearchType(SLSearchTypeEnum.INDIVIDUAL);
				} else {
					lookupRequest.setSearchType(SLSearchTypeEnum.ORGANIZATION);
				}

				lookupRequest.setClientStatus(SLClientStatusEnum.ACTIVE);
				lookupRequest.setClientId(beneficiaryRequest.getSanctionId());
				setGenderForBene(beneficiaryRequest, lookupRequest);

				lookupRequest.setNameLine(beneficiaryRequest.getFullName());
				lookupRequest.setClientSearchCode(SLClientSearchCodeEnum.FULL_NAME);
				lookupRequest.setReturnComplianceRecords(SLYesNoEnum.YES);
				lookupRequest.setAddClient(SLYesNoEnum.YES);
				lookupRequest.setSendToReview(SLYesNoEnum.YES);
				lookupRequest.setUpdateUserFields(SLYesNoEnum.YES);
				lookupRequest.setUserField1Label(Constants.CITIZENSHIP);
				lookupRequest.setUserField1Value(beneficiaryRequest.getCountry());
				lookupRequest.setUserField2Label("Date of Birth");
				String dob = SanctionDateTimeFormatter.getDOBForProvider(beneficiaryRequest.getDob());
				lookupRequest.setUserField2Value(dob);
				lookupRequest.setReturnCategory(SLYesNoEnum.YES);
				lookupRequest.setReturnSourceLists(SLYesNoEnum.YES);
				lookupRequest.setGenerateclientId(SLYesNoEnum.NO);
				if(null != sanctionRequest.getCustomerNumber())
					lookupRequest.setComment(sanctionRequest.getCustomerNumber());
				lookupRequests.add(lookupRequest);
				String json = JsonConverterUtil.convertToJsonWithNull(lookupRequest);
				LOG.warn("\nSanction Soap LookUp Benficiary Request : \n{}",json);
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_REQUEST, e);
		}
	}

	/**
	 * Sets the gender for bene.
	 *
	 * @param beneficiaryRequest
	 *            the beneficiary request
	 * @param lookupRequest
	 *            the lookup request
	 */
	private void setGenderForBene(SanctionBeneficiaryRequest beneficiaryRequest,
			LSTServicesLookupRequest lookupRequest) {
		if (beneficiaryRequest.getGender() != null || !beneficiaryRequest.getGender().isEmpty()) {
			if ("Male".equalsIgnoreCase(beneficiaryRequest.getGender().trim())) {
				lookupRequest.setGender(SLGenderEnum.MALE);
			} else if ("Female".equalsIgnoreCase(beneficiaryRequest.getGender().trim())) {
				lookupRequest.setGender(SLGenderEnum.FEMALE);
			} else {
				lookupRequest.setGender(SLGenderEnum.UNKNOWN);
			}
		} else {
			lookupRequest.setGender(SLGenderEnum.UNKNOWN);
		}
	}

	/**
	 * Tranform request for bank.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param providerProperty
	 *            the provider property
	 * @param lookupRequests
	 *            the lookup requests
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public void tranformRequestForBank(SanctionRequest sanctionRequest, ProviderProperty providerProperty,
			List<LSTServicesLookupRequest> lookupRequests) throws SanctionException {
		try {
			for (SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()) {
				LSTServicesLookupRequest lookupRequest = new LSTServicesLookupRequest();
				lookupRequest.setOrganizationName(Constants.CD);
				lookupRequest.setUserName(providerProperty.getUserName());
				lookupRequest.setPassword(providerProperty.getPassWord());
				lookupRequest.setApplicationId(Constants.BANK);
				lookupRequest.setGender(SLGenderEnum.MALE);

				// Modified wrt AT-1951
				lookupRequest.setLists(new ArrayOfSLComplianceList());
				lookupRequest.setCustomStatus(getCustomStatuses());

				lookupRequest.setSearchType(SLSearchTypeEnum.ORGANIZATION);
				lookupRequest.setClientStatus(SLClientStatusEnum.ACTIVE);
				lookupRequest.setClientId(bankRequest.getSanctionId());

				lookupRequest.setNameLine(bankRequest.getBankName());
				lookupRequest.setClientSearchCode(SLClientSearchCodeEnum.FULL_NAME);
				lookupRequest.setReturnComplianceRecords(SLYesNoEnum.YES);
				lookupRequest.setAddClient(SLYesNoEnum.YES);
				lookupRequest.setSendToReview(SLYesNoEnum.YES);
				lookupRequest.setUpdateUserFields(SLYesNoEnum.YES);
				lookupRequest.setUserField1Label(Constants.CITIZENSHIP);
				lookupRequest.setUserField1Value(bankRequest.getCountry());

				lookupRequest.setReturnCategory(SLYesNoEnum.YES);
				lookupRequest.setReturnSourceLists(SLYesNoEnum.YES);
				lookupRequest.setGenerateclientId(SLYesNoEnum.NO);
				if(null != sanctionRequest.getCustomerNumber())
					lookupRequest.setComment(sanctionRequest.getCustomerNumber());
				lookupRequests.add(lookupRequest);
				String json = JsonConverterUtil.convertToJsonWithNull(lookupRequest);
				LOG.warn("\nSanction Soap LookUp Bank Request : \n{}",json);
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_REQUEST, e);
		}
	}
	
	/**
	 * // AT-4384
	 * Adding custom status for BANK as 
	 * <customStatus>
	 *  <SLCustomStatus>
	 *		<statusString>Permanent Safe</statusString>
	 *		<status>SAFE</status>
	 *	</SLCustomStatus>
	 * </customStatus>
	 *
	 * @return ArrayOfSLCustomStatus
	 */
	private static ArrayOfSLCustomStatus getCustomStatuses() {
		
		ArrayOfSLCustomStatus arrayOfSLCustomStatus = new ArrayOfSLCustomStatus();
		
		List<SLCustomStatus> slCustomStatus = arrayOfSLCustomStatus.getSLCustomStatus();
		
		SLCustomStatus permanentSafeToSafeCustomStatus = new SLCustomStatus();
		permanentSafeToSafeCustomStatus.setStatusString(Constants.PERMANENT_SAFE);
		permanentSafeToSafeCustomStatus.setStatus(SLPairStatusEnum.SAFE);
		
		slCustomStatus.add(permanentSafeToSafeCustomStatus);
		
		return arrayOfSLCustomStatus;
	}

	/**
	 * Transform response object.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param multiResponse
	 *            the multi response
	 * @param alwaysPass
	 *            the always pass
	 * @return the sanction response
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public SanctionResponse transformResponseObject(SanctionRequest sanctionRequest,
			LSTServicesLookupMultiResponse multiResponse, Boolean alwaysPass) throws SanctionException {
		SanctionResponse response = new SanctionResponse();
		List<SanctionContactResponse> contactReponses = new ArrayList<>();
		List<SanctionBeneficiaryResponse> beneficiaryResponses = new ArrayList<>();
		List<SanctionBankResponse> bankResponses = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		LSTServicesLookupMultiResponse tmp = new LSTServicesLookupMultiResponse();
		ArrayOfLSTServicesLookupResponse tmpResponses = new ArrayOfLSTServicesLookupResponse();
		try {
			ObjectCloner.copyNullFields(tmp, multiResponse, LSTServicesLookupMultiResponse.class);
		} catch (Exception e1) {
			LOG.error("Error : transformResponseObject() -->", e1);
		}

		try {
			ArrayOfLSTServicesLookupResponse lookupResponse = multiResponse.getResponses();
			for (LSTServicesLookupResponse lstServicesLookupResponse : lookupResponse.getLSTServicesLookupResponse()) {
				tmpResponses.getLSTServicesLookupResponse().add(lstServicesLookupResponse);
				if (tmp != null) {
					tmp.setResponses(tmpResponses);
				}
				tmp.setMessage(lstServicesLookupResponse.getMessage());
				tmp.setStatus(lstServicesLookupResponse.getStatus());
				String applicationId = getApplicationId(sanctionRequest, lstServicesLookupResponse);
				if (Constants.CLNTS.equalsIgnoreCase(applicationId)) {
					SanctionContactResponse contactReponse = tranformResponseForContact(lstServicesLookupResponse,
							alwaysPass);
					contactReponse.setProviderResponse(mapper.writeValueAsString(tmp));
					contactReponse.setContactId(getRequestContactId(sanctionRequest, contactReponse.getSanctionId()));
					contactReponse.setProviderName(Constants.FINSCAN_PROVIDER);
					contactReponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_LOOKUP_MULTI);
					contactReponses.add(contactReponse);
				}
				if (Constants.BNFC.equalsIgnoreCase(applicationId)) {

					SanctionBeneficiaryResponse beneficiaryResponse = tranformResponseForBeneficiary(
							lstServicesLookupResponse, alwaysPass);
					beneficiaryResponse.setProviderResponse(mapper.writeValueAsString(tmp));
					beneficiaryResponse
							.setBeneficiaryId(getRequestBeneId(sanctionRequest, beneficiaryResponse.getSanctionId()));
					beneficiaryResponse.setProviderName(Constants.FINSCAN_PROVIDER);
					beneficiaryResponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_LOOKUP_MULTI);
					beneficiaryResponses.add(beneficiaryResponse);
				}
				if (Constants.BANK.equalsIgnoreCase(applicationId)) {
					SanctionBankResponse bankResponse = tranformResponseForBank(lstServicesLookupResponse, alwaysPass);
					bankResponse.setProviderResponse(mapper.writeValueAsString(tmp));
					bankResponse.setBankID(getRequestBankId(sanctionRequest, bankResponse.getSanctionId()));
					bankResponse.setProviderName(Constants.FINSCAN_PROVIDER);
					bankResponse.setProviderMethod(Constants.PROVIDER_METHOD_SL_LOOKUP_MULTI);
					bankResponses.add(bankResponse);
				}

			}
			response.setContactResponses(contactReponses);
			response.setBeneficiaryResponses(beneficiaryResponses);
			response.setBankResponses(bankResponses);
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_RESPONSE, e);
		}
		return response;
	}

	/**
	 * Gets the request contact id.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param sanctionID
	 *            the sanction ID
	 * @return the request contact id
	 */
	private Integer getRequestContactId(SanctionRequest sanctionRequest, String sanctionID) {
		for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
			if (sanctionID.equals(contactRequest.getSanctionId())) {
				return contactRequest.getContactId();
			}
		}
		return 0;
	}

	/**
	 * Gets the request bene id.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param sanctionID
	 *            the sanction ID
	 * @return the request bene id
	 */
	private Integer getRequestBeneId(SanctionRequest sanctionRequest, String sanctionID) {
		for (SanctionBeneficiaryRequest beneRequest : sanctionRequest.getBeneficiaryRequests()) {
			if (sanctionID.equals(beneRequest.getSanctionId())) {
				return beneRequest.getBeneficiaryId();
			}
		}
		return 0;
	}

	/**
	 * Gets the request bank id.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param sanctionID
	 *            the sanction ID
	 * @return the request bank id
	 */
	private Integer getRequestBankId(SanctionRequest sanctionRequest, String sanctionID) {
		for (SanctionBankRequest bankRequest : sanctionRequest.getBankRequests()) {
			if (sanctionID.equals(bankRequest.getSanctionId())) {
				return bankRequest.getBankId();
			}
		}
		return 0;
	}

	/**
	 * Gets the application id.
	 *
	 * @param sanctionRequest
	 *            the sanction request
	 * @param contactResponse
	 *            the contact response
	 * @return the application id
	 */
	private String getApplicationId(SanctionRequest sanctionRequest, LSTServicesLookupResponse contactResponse) {

		if (sanctionRequest.getContactrequests() != null && !sanctionRequest.getContactrequests().isEmpty()) {
			for (SanctionContactRequest contactRequest : sanctionRequest.getContactrequests()) {
				if (contactResponse.getClientId().equals(contactRequest.getSanctionId())) {
					return Constants.CLNTS;
				}
			}
		}
		if (sanctionRequest.getBeneficiaryRequests() != null && !sanctionRequest.getBeneficiaryRequests().isEmpty()) {
			for (SanctionBeneficiaryRequest beneficiaryRequest : sanctionRequest.getBeneficiaryRequests()) {
				if (contactResponse.getClientId().equals(beneficiaryRequest.getSanctionId())) {
					return Constants.BNFC;
				}
			}
		}

		return Constants.BANK;
	}

	/**
	 * Tranform response for contact.
	 *
	 * @param lstServicesLookupResponse
	 *            the lst services lookup response
	 * @param alwaysPass
	 *            the always pass
	 * @return the sanction contact response
	 * @throws SanctionException
	 *             the sanction exception
	 */
	private SanctionContactResponse tranformResponseForContact(LSTServicesLookupResponse lstServicesLookupResponse,
			Boolean alwaysPass) throws SanctionException {
		SanctionContactResponse contactReponse = new SanctionContactResponse();
		try {
			transformBaseResponse(contactReponse, lstServicesLookupResponse, alwaysPass);
		} catch (Exception e) {
			contactReponse.setStatus(Constants.FAIL);
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_RESPONSE, e);
		}
		return contactReponse;
	}

	/**
	 * Tranform response for beneficiary.
	 *
	 * @param lstServicesLookupResponse
	 *            the lst services lookup response
	 * @param alwaysPass
	 *            the always pass
	 * @return the sanction beneficiary response
	 * @throws SanctionException
	 *             the sanction exception
	 */
	private SanctionBeneficiaryResponse tranformResponseForBeneficiary(
			LSTServicesLookupResponse lstServicesLookupResponse, Boolean alwaysPass) throws SanctionException {
		SanctionBeneficiaryResponse beneficiaryResponse = new SanctionBeneficiaryResponse();
		try {
			transformBaseResponse(beneficiaryResponse, lstServicesLookupResponse, alwaysPass);
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_RESPONSE, e);
		}
		return beneficiaryResponse;
	}

	/**
	 * Tranform response for bank.
	 *
	 * @param lstServicesLookupResponse
	 *            the lst services lookup response
	 * @param alwaysPass
	 *            the always pass
	 * @return the sanction bank response
	 * @throws SanctionException
	 *             the sanction exception
	 */
	private SanctionBankResponse tranformResponseForBank(LSTServicesLookupResponse lstServicesLookupResponse,
			Boolean alwaysPass) throws SanctionException {
		SanctionBankResponse bankResponse = new SanctionBankResponse();
		try {
			transformBaseResponse(bankResponse, lstServicesLookupResponse, alwaysPass);
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_RESPONSE, e);
		}
		return bankResponse;
	}

	private boolean isWorldCheck(String catagory) {

		if (Constants.WC_LRI.equalsIgnoreCase(catagory) || Constants.WC_PEE_IND.equalsIgnoreCase(catagory)) {
			return true;
		}

		if (Constants.WC_LRE.equalsIgnoreCase(catagory) || Constants.WC_SNC.equalsIgnoreCase(catagory)) {
			return true;
		}

		return (Constants.WC_PEP.equalsIgnoreCase(catagory) || Constants.WC_SNC_IND.equalsIgnoreCase(catagory));
	}

	/**
	 * Checks if is ofac found.
	 *
	 * @param catagory
	 *            the catagory
	 * @return true, if is ofac found
	 */
	private boolean isOfacFound(String catagory) {
		return Constants.HRI.equalsIgnoreCase(catagory) || Constants.HRE.equalsIgnoreCase(catagory);
	}

	/**
	 * Transform base response.
	 *
	 * @param baseResponse
	 *            the base response
	 * @param lstServicesLookupResponse
	 *            the lst services lookup response
	 * @param alwaysPass
	 *            the always pass
	 */
	private void transformBaseResponse(SanctionBaseResponse baseResponse,
			LSTServicesLookupResponse lstServicesLookupResponse, Boolean alwaysPass) {
		baseResponse.setOfacList(Constants.NO_MATCH_FOUND);
		baseResponse.setWorldCheck(Constants.NO_MATCH_FOUND);
		baseResponse.setSanctionId(lstServicesLookupResponse.getClientId());
		baseResponse.setPendingCount(lstServicesLookupResponse.getPendingCount());
		baseResponse.setResultsCount(lstServicesLookupResponse.getResultsCount());

		setWcAndOfac(baseResponse, lstServicesLookupResponse.getComplianceRecords());
		// for testing purpose only.. need to remove alwayspass hook after
		// testing
		if (null != alwaysPass && alwaysPass) {
			baseResponse.setStatus(Constants.PASS);
		} // testing code end
		else if ((SLResultTypeEnum.ERROR)==lstServicesLookupResponse.getStatus()) {
			baseResponse.setOfacList(Constants.NOT_AVAILABLE);
			baseResponse.setWorldCheck(Constants.NOT_AVAILABLE);
			baseResponse.setStatus(Constants.SERVICE_FAILURE);
		} else {
			baseResponse.setStatus(lstServicesLookupResponse.getStatus().toString());
		}
		baseResponse.setStatusDescription(lstServicesLookupResponse.getMessage());
	}

	/**
	 * Sets the wc and ofac.
	 *
	 * @param baseResponse
	 *            the base response
	 * @param complianceRecords
	 *            the compliance records
	 */
	private void setWcAndOfac(SanctionBaseResponse baseResponse, ArrayOfSLComplianceRecord complianceRecords) {
		for (SLComplianceRecord complianceRecord : complianceRecords.getSLComplianceRecord()) {
			boolean worldCheckFound = false;
			boolean ofacFound = false;
			for (String finscancategory : complianceRecord.getFinscanCategory().getString()) {
				if (isWorldCheck(finscancategory)) {
					baseResponse.setWorldCheck(Constants.MATCH_FOUND);
					worldCheckFound = true;
				}
				if (isOfacFound(finscancategory)) {
					baseResponse.setOfacList(Constants.MATCH_FOUND);
					ofacFound = true;
				}
			}
			if (ofacFound && worldCheckFound) {
				break;
			}
		}
	}

	/**
	 * Transform get contact status request.
	 *
	 * @param request
	 *            the request
	 * @param property
	 *            the property
	 * @return the LST services get status request
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public LSTServicesGetStatusRequest transformGetContactStatusRequest(SanctionGetStatusRequest request,
			ProviderProperty property) throws SanctionException {
		LSTServicesGetStatusRequest getStatusRequest = new LSTServicesGetStatusRequest();
		try {
			getStatusRequest.setApplicationId(request.getApplicationId());
			getStatusRequest.setClientId(request.getSanctionId());
			getStatusRequest.setUserName(property.getUserName());
			getStatusRequest.setPassword(property.getPassWord());
			getStatusRequest.setOrganizationName(Constants.CD);
			
			if (Constants.BANK.equalsIgnoreCase(request.getApplicationId())) {
				getStatusRequest.setCustomStatus(getCustomStatuses());
			}

		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_REQUEST, e);
		}
		return getStatusRequest;

	}

	/**
	 * Made changes to set OfactList and WorldCheck ServiceStatus -Saylee.
	 *
	 * @param response
	 *            the response
	 * @param fundsOutRequest
	 *            the funds out request
	 * @param alwaysPass
	 *            the always pass
	 * @return the sanction get status response
	 * @throws SanctionException
	 *             the sanction exception
	 */
	public SanctionGetStatusResponse transformContactStatusResponse(LSTServicesGetStatusResponse response,
			SanctionGetStatusRequest fundsOutRequest, Boolean alwaysPass) throws SanctionException {
		SanctionGetStatusResponse fundsOutResponse = new SanctionGetStatusResponse();
		try {

			fundsOutResponse.setSanctionId(response.getClientId());
			fundsOutResponse.setResultsCount(response.getResultsCount());
			fundsOutResponse.setPendingCount(response.getPendingCount());
			fundsOutResponse.setStatusDescription(response.getMessage());
			if (null != alwaysPass && alwaysPass) {
				fundsOutResponse.setStatus(Constants.PASS);
			} else if ((SLResultTypeEnum.ERROR)==response.getStatus()) {
				fundsOutResponse.setStatus(Constants.SERVICE_FAILURE);
			} else {
				fundsOutResponse.setStatus(response.getStatus().toString());
			}
			ObjectMapper mapper = new ObjectMapper();
			fundsOutResponse.setProviderResponse(mapper.writeValueAsString(response));
			fundsOutResponse.setId(fundsOutRequest.getId());
			fundsOutResponse.setApplicationId(fundsOutRequest.getApplicationId());
			setOfacListServiceStatus(fundsOutResponse);
			setWorldCheckServiceStatus(fundsOutResponse);
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_TRANSFORMATION_RESPONSE, e);
		}
		return fundsOutResponse;
	}

	/**
	 * Set WorldCheck status as per ProviderStatus.
	 *
	 * @param fundsOutResponse
	 *            the new world check service status
	 */
	private void setWorldCheckServiceStatus(SanctionGetStatusResponse fundsOutResponse) {
		if (!Constants.NO_MATCH_FOUND.equalsIgnoreCase(fundsOutResponse.getWorldCheck())
				|| !Constants.MATCH_FOUND.equalsIgnoreCase(fundsOutResponse.getWorldCheck())) {
			fundsOutResponse.setWorldCheck(getProviderStatus(fundsOutResponse));
		}
	}

	/**
	 * Set OfacList status as per ProviderStatus.
	 *
	 * @param fundsOutResponse
	 *            the new ofac list service status
	 */
	private void setOfacListServiceStatus(SanctionGetStatusResponse fundsOutResponse) {
		if (!Constants.NO_MATCH_FOUND.equalsIgnoreCase(fundsOutResponse.getOfacList())
				|| !Constants.MATCH_FOUND.equalsIgnoreCase(fundsOutResponse.getOfacList())) {
			fundsOutResponse.setOfacList(getProviderStatus(fundsOutResponse));
		}
	}

	/**
	 * Check status.
	 *
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return true, if successful
	 */
	private boolean checkStatus(SanctionGetStatusResponse fundsOutResponse) {
		return (Constants.NOT_PERFORMED.equalsIgnoreCase(fundsOutResponse.getStatus())
				|| Constants.SERVICE_FAILURE.equalsIgnoreCase(fundsOutResponse.getStatus())
				|| Constants.NOT_REQUIRED.equalsIgnoreCase(fundsOutResponse.getStatus()));
	}

	/**
	 * Set status for OfacList and Worldcheck 1.If SanctionStatus is Fail or
	 * Pending then set Status for OfacList and WorldCheck to MATCH_FOUND 2.If
	 * SanctionStatus is NOT_PERFORMED or SERVICE_FAILURE or NOT_REQUIRED then
	 * set Status for OfacList and WorldCheck to NOT_AVAILABLE 3.If If
	 * SanctionStatus is PASS then set Status for OfacList and WorldCheck to
	 * NO_MATCH_FOUND - Saylee
	 *
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the provider status
	 */
	private String getProviderStatus(SanctionGetStatusResponse fundsOutResponse) {

		if (!Constants.PASS.equalsIgnoreCase(fundsOutResponse.getStatus()) && !checkStatus(fundsOutResponse)) {
			return Constants.MATCH_FOUND;
		} else if (checkStatus(fundsOutResponse)) {
			return Constants.NOT_AVAILABLE;
		} else if (Constants.PASS.equalsIgnoreCase(fundsOutResponse.getStatus())) {
			return Constants.NO_MATCH_FOUND;
		}
		return Constants.NOT_AVAILABLE;
	}

}
