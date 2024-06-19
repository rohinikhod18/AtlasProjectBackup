/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;


import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterProfileBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterOnUpdateProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsInProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsOutProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterPaymentsProviderBaseRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProfileProviderBaseRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderBaseRequest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSearchDataResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSignupProviderRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.util.ObjectUtils;

/**
 * The Class FraugsterTransformer.
 *
 * @author manish
 */
public class FraugsterTransformer {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FraugsterTransformer.class);

	/** The fraugster transformer. */
	private static FraugsterTransformer fraugsterTransformer = null;

	/**
	 * Instantiates a new fraugster transformer.
	 */
	private FraugsterTransformer() {

	}

	/**
	 * Gets the single instance of FraugsterTransformer.
	 *
	 * @return single instance of FraugsterTransformer
	 */
	public static FraugsterTransformer getInstance() {
		if (fraugsterTransformer == null) {
			fraugsterTransformer = new FraugsterTransformer();
		}
		return fraugsterTransformer;
	}

	/**
	 * Transform signup request.
	 *
	 * @param request            the request
	 * @return the fraugster signup provider request
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterSignupProviderRequest transformNewSignupRequest(FraugsterSignupContactRequest request)
			throws FraugsterException {

		LOG.debug("Fraugster transformSignupRequest : STARTED ");
		FraugsterSignupProviderRequest signupRequest = new FraugsterSignupProviderRequest();

		try {

			signupRequest.setOrganisationCode(request.getOrganizationCode());
			signupRequest.setCustScndryEmail(request.getSecondEmail());

			signupRequest.setPostCodeLatitude(request.getPostCodeLatitude());
			signupRequest.setPostCodeLongitude(request.getPostCodeLongitude());
			signupRequest.setPhone(request.getPhoneHome());

			transformDeviceInfoAndProviderBaseRequest(request, signupRequest);
			transformProfileBaseRequest(signupRequest, request);
			ObjectUtils.setNullFieldsToDefault(signupRequest);
			

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraugster : transform Signup Request : ENDED {}",signupRequest);

		return signupRequest;
	}

	/**
	 * Transform device info and provider base request.
	 *
	 * @param request the request
	 * @param providerRequest the provider request
	 */
	private void transformDeviceInfoAndProviderBaseRequest(FraugsterBaseRequest request,
			FraugsterProviderBaseRequest providerRequest) {
	
			providerRequest.setCustSignupTs(request.getRegistrationDateTime());
		
		providerRequest.setEventType(request.getEventType());
		providerRequest.setCustId(request.getCustID());
		providerRequest.setTransId(request.getTransactionID());
		providerRequest.setBrowserUserAgent(request.getUserAgent());
		providerRequest.setScreenResolution(request.getBrowserScreenResolution());
		providerRequest.setBrwsrType(request.getBrowserName());
		providerRequest.setBrwsrVersion(request.getBrowserMajorVersion());
		providerRequest.setDeviceType(request.getDeviceType());
		providerRequest.setDeviceName(request.getDeviceName());
		providerRequest.setDeviceVersion(request.getDeviceVersion());
		providerRequest.setDeviceId(request.getDeviceID());
		providerRequest.setDeviceManufacturer(request.getDeviceManufacturer());
		providerRequest.setOsType(request.getOsName());
		providerRequest.setBrwsrLang(request.getBrowserLanguage());
		providerRequest.setBrowserOnline(request.getBrowserOnline());
		providerRequest.setOsTs(request.getOsDateAndTime());
	}

	/**
	 * Transform base response.
	 *
	 * @param response the response
	 * @param fraudDetectionResponse the fraud detection response
	 */
	private void transformBaseResponse(FraugsterSearchDataResponse response,
			FraugsterBaseResponse fraudDetectionResponse) {
		fraudDetectionResponse.setFrgTransId(response.getFrgTransId());
		fraudDetectionResponse.setFraugsterApproved(response.getFraugsterApproved());
		fraudDetectionResponse.setScore(response.getScore());
		fraudDetectionResponse.setErrorMsg(response.getErrorMsg());
		fraudDetectionResponse.setInvestigationPoints(response.getInvestigationPoints());
	}

	/**
	 * Transform new signup response.
	 *
	 * @param response the response
	 * @return the fraugster signup contact response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterSignupContactResponse transformNewSignupResponse(FraugsterSearchDataResponse response)
			throws FraugsterException {

		LOG.debug("Fraugster transformSignupResponse : STARTED ");
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		try {
			transformBaseResponse(response, fraudDetectionResponse);
		} catch (Exception e) {
			LOG.warn("Error in  transformSignupRequest() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformSignupResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * Transform profile base request.
	 *
	 * @param providerBaseRequest the provider base request
	 * @param profileBaseRequest the profile base request
	 */
	private void transformProfileBaseRequest(FraugsterProfileProviderBaseRequest providerBaseRequest,
			FraugsterProfileBaseRequest profileBaseRequest) {
	
		providerBaseRequest.setAcctContactNum(profileBaseRequest.getAccountContactNum());
		providerBaseRequest.setCustTitle(profileBaseRequest.getTitle());
		providerBaseRequest.setPreferredName(profileBaseRequest.getPreferredName());
		providerBaseRequest.setCustFirstName(profileBaseRequest.getFirstName());
		providerBaseRequest.setCustMiddleName(profileBaseRequest.getMiddleName());
		providerBaseRequest.setCustLastName(profileBaseRequest.getLastName());
		providerBaseRequest.setCustDOB(profileBaseRequest.getDob());
		providerBaseRequest.setBillAddName(profileBaseRequest.getAddressType());
		providerBaseRequest.setBillAddLine1(profileBaseRequest.getStreet());
		providerBaseRequest.setBillAddCity(profileBaseRequest.getCity());
		providerBaseRequest.setBillAddState(profileBaseRequest.getState());
		providerBaseRequest.setBillAddCtry(profileBaseRequest.getCountry());
		providerBaseRequest.setBillAddZip(profileBaseRequest.getPostCode());

		providerBaseRequest.setPhoneType(profileBaseRequest.getPhoneType());
		providerBaseRequest.setPhoneWork(profileBaseRequest.getPhoneWork());
		providerBaseRequest.setScndryPhone(profileBaseRequest.getPhoneMobile());
		providerBaseRequest.setCustEmail(profileBaseRequest.getEmail());

		providerBaseRequest.setPrimaryContact(String.valueOf(profileBaseRequest.getPrimaryContact()));
		providerBaseRequest.setCustNationality(profileBaseRequest.getNationality());
		/**If gender is not present check  what is title**/
		if(null!=profileBaseRequest.getGender()&&!profileBaseRequest.getGender().isEmpty()){
			providerBaseRequest.setCustMF(profileBaseRequest.getGender());
		}else {
			String title = profileBaseRequest.getTitle().replaceAll("[-+.^:,]","").replaceAll("\\s+","");
			
			 List<String> maleTitles = new ArrayList<>();
			
			 List<String> femaleTitles = new ArrayList<>();
			
			maleTitles.add("Father");
			maleTitles.add("Senor");
			maleTitles.add("Dr (Male)");
			maleTitles.add("Professor (Male)");
			maleTitles.add("Mr");
			maleTitles.add("Lord");
			maleTitles.add("Sir");
			maleTitles.add("Sr");
			maleTitles.add("Signor");
			maleTitles.add("Signore");
			
			femaleTitles.add("Lady");
			femaleTitles.add("Ms");
			femaleTitles.add("Mrs");
			femaleTitles.add("Madame");
			femaleTitles.add("Miss");
			femaleTitles.add("Senora");
			femaleTitles.add("Senorita");
			femaleTitles.add("Signorina");
			femaleTitles.add("Mother");
			
			for (String female : femaleTitles) {
				if(female.equalsIgnoreCase(title)){
					providerBaseRequest.setCustMF("Female");
					break;
				}
			}
			
			for (String male : maleTitles) {
				if(male.equalsIgnoreCase(title)){
					providerBaseRequest.setCustMF("Male");
					break;
				}
			}
		}
		
		providerBaseRequest.setCustOccptn(profileBaseRequest.getOccupation());
		providerBaseRequest.setPassportIdNum(profileBaseRequest.getPassportNumber());
		providerBaseRequest.setPassportCountryCode(profileBaseRequest.getPassportCountryCode());
		providerBaseRequest.setPassportFamilyNameAtBirth(profileBaseRequest.getPassportFamilyNameAtBirth());
		providerBaseRequest.setPassportNameAtCitizenship(profileBaseRequest.getPassportNameAtCitizenship());
		providerBaseRequest.setPassportPlaceOfBirth(profileBaseRequest.getPassportPlaceOfBirth());
		providerBaseRequest.setDlLicenseNumber(profileBaseRequest.getDlLicenseNumber());
		providerBaseRequest.setDlCardNumber(profileBaseRequest.getdLCardNumber());
		providerBaseRequest.setDlCountryCode(profileBaseRequest.getDlCountryCode());
		providerBaseRequest.setDlStateCode(profileBaseRequest.getDlStateCode());
		providerBaseRequest.setDlExpiryDate(profileBaseRequest.getDlExpiryDate());
		providerBaseRequest.setMedicareCardNumber(profileBaseRequest.getMedicareCardNumber());
		providerBaseRequest.setMedicareReferenceNumber(profileBaseRequest.getMedicareReferenceNumber());
		providerBaseRequest.setMunicipalityOfBirth(profileBaseRequest.getMunicipalityOfBirth());
		providerBaseRequest.setSubBuildingOrFlat(profileBaseRequest.getSubBuildingorFlat());
		providerBaseRequest.setBuildingNumber(profileBaseRequest.getBuildingNumber());
		providerBaseRequest.setUnitNumber(profileBaseRequest.getUnitNumber());
		providerBaseRequest.setSubCity(profileBaseRequest.getSubCity());
		providerBaseRequest.setBillAddCtryRgn(profileBaseRequest.getRegion());
		providerBaseRequest.setTransChannel(profileBaseRequest.getChannel());
		providerBaseRequest.setIp(profileBaseRequest.getiPAddress());
		providerBaseRequest.setIpLat(profileBaseRequest.getiPLatitude());
		providerBaseRequest.setIpLong(profileBaseRequest.getiPLongitude());
		providerBaseRequest.setCustomerType(profileBaseRequest.getCustomerType());

		providerBaseRequest.setCountriesOfOperation(profileBaseRequest.getCountriesOfOperation());
		providerBaseRequest.setCountryOfBirth(profileBaseRequest.getCountryOfBirth());
		providerBaseRequest.setPymtSource(profileBaseRequest.getSourceOfFund());
		providerBaseRequest.setSource(profileBaseRequest.getSource());
		providerBaseRequest.setSubSource(profileBaseRequest.getSubSource());
		providerBaseRequest.setReferral(profileBaseRequest.getReferral());
		providerBaseRequest.setReferralText(profileBaseRequest.getReferralText());
		providerBaseRequest.setExtendedReferral(profileBaseRequest.getExtendedReferral());
		providerBaseRequest.setCampaign(profileBaseRequest.getAdCampaign());
		providerBaseRequest.setKeywords(profileBaseRequest.getKeywords());
		providerBaseRequest.setSearchEngine(profileBaseRequest.getSearchEngine());
		providerBaseRequest.setWebsite(profileBaseRequest.getWebsite());
	}

	/**
	 * Transform OnUpdate request.
	 *
	 * @param request            the request
	 * @return the fraugster OnUpdate provider request
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterOnUpdateProviderRequest transformOnUpdateRequest(FraugsterOnUpdateContactRequest request)
			throws FraugsterException {

		LOG.debug("Fraugster transformOnUpdateRequest : STARTED ");
		FraugsterOnUpdateProviderRequest onUpdateRequest = new FraugsterOnUpdateProviderRequest();

		try {

			transformProfileBaseRequest(onUpdateRequest, request);
			transformDeviceInfoAndProviderBaseRequest(request, onUpdateRequest);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformOnUpdateRequest : ENDED ");

		return onUpdateRequest;
	}

	/**
	 * Transform on update response.
	 *
	 * @param response the response
	 * @return the fraugster on update contact response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterOnUpdateContactResponse transformOnUpdateResponse(FraugsterSearchDataResponse response)
			throws FraugsterException {

		LOG.debug("Fraugster transformOnUpdateResponse : STARTED ");
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();
		try {
			transformBaseResponse(response, fraudDetectionResponse);

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformOnUpdateResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * Transform payments out request.
	 *
	 * @param request the request
	 * @return the fraugster payments out provider request
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsOutProviderRequest transformPaymentsOutRequest(FraugsterPaymentsOutContactRequest request)
			throws FraugsterException {

		LOG.debug("Fraugster transformPaymentsOutRequest : STARTED ");
		FraugsterPaymentsOutProviderRequest paymentsOutProviderRequest = new FraugsterPaymentsOutProviderRequest();

		try {

			paymentsOutProviderRequest.setAccountWithInstitution(request.getAccountWithInstitution());
			paymentsOutProviderRequest.setBaBic(request.getCreditorAgentBICCodeOrSortCode());
			paymentsOutProviderRequest.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
			paymentsOutProviderRequest.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
			paymentsOutProviderRequest.setBeneficiaryBankAddress(request.getBeneficiaryBankAddress());
			paymentsOutProviderRequest.setBeneficiaryBankName(request.getBeneficiaryBankName());
			paymentsOutProviderRequest.setBeneficiaryCountry(request.getBeneficiaryCountry());
			paymentsOutProviderRequest.setBeneficiaryCurrency(request.getBeneficiaryCurrency());
			paymentsOutProviderRequest.setBeneficiaryEmail(request.getBeneficiaryEmail());
			paymentsOutProviderRequest.setBeneficiaryFirstName(request.getBeneficiaryFirstName());
			paymentsOutProviderRequest.setBeneficiaryLastName(request.getBeneficiaryLastName());
			paymentsOutProviderRequest.setBeneficiaryPhone(request.getBeneficiaryPhone());
			paymentsOutProviderRequest.setBeneficiarySwift(request.getBeneficiarySwift());

			paymentsOutProviderRequest.setCustomerType(request.getCustomerType());
			paymentsOutProviderRequest.setIntermediaryInstitution(request.getIntermediaryInstitution());
			paymentsOutProviderRequest.setOpiCountry(request.getoPICountry());
				paymentsOutProviderRequest.setOpiCreatedDate(request.getoPICreatedDate());
			
			paymentsOutProviderRequest.setOrderingInstitution(request.getOrderingInstitution());

			paymentsOutProviderRequest.setPaymentCountry(request.getPaymentCountry());
			paymentsOutProviderRequest.setRemittanceInfo(request.getRemittanceInformation());
			paymentsOutProviderRequest.setSenderCorrespondent(request.getSenderCorrespondent());

				paymentsOutProviderRequest.setTransTs(request.getTransactionDateTime());
			

			paymentsOutProviderRequest.setValueDate(String.valueOf(request.getValueDate()));
			paymentsOutProviderRequest.setDealId(request.getDealId());
			transformDeviceInfoAndProviderBaseRequest(request, paymentsOutProviderRequest);
			transformPaymentsBaseRequest(paymentsOutProviderRequest, request);

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformPaymentsOutRequest : ENDED ");

		return paymentsOutProviderRequest;
	}

	/**
	 * Transform payments base request.
	 *
	 * @param baseRequest the base request
	 * @param paymentsBaseRequest the payments base request
	 */
	private void transformPaymentsBaseRequest(FraugsterPaymentsProviderBaseRequest baseRequest,
			FraugsterPaymentsBaseRequest paymentsBaseRequest) {
		

		baseRequest.setCust_first_name(paymentsBaseRequest.getCustomerFirstName());
		baseRequest.setCust_last_name(paymentsBaseRequest.getCustomerLastName());

		baseRequest.setOrganisationCode(paymentsBaseRequest.getOrganizationCode());


		baseRequest.setTrans_amt(paymentsBaseRequest.getTransactionAmount());

		baseRequest.setTrans_currency(paymentsBaseRequest.getCurrency());

		baseRequest.setCustomer_account_number(paymentsBaseRequest.getCustomerAccountNumber());

		baseRequest.setTrfr_reason(paymentsBaseRequest.getReason());
		
		baseRequest.setCust_signup_score(paymentsBaseRequest.getCustSignupScore());

	}

	/**
	 * Transform payments out response.
	 *
	 * @param response the response
	 * @return the fraugster payments out contact response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsOutContactResponse transformPaymentsOutResponse(FraugsterSearchDataResponse response)
			throws FraugsterException {

		LOG.debug("Fraugster transformPaymentsOutResponse : STARTED ");
		FraugsterPaymentsOutContactResponse fraudDetectionResponse = new FraugsterPaymentsOutContactResponse();
		try {
			transformBaseResponse(response, fraudDetectionResponse);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformPaymentsOutResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * Transform payments in request.
	 *
	 * @param request the request
	 * @return the fraugster payments in provider request
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsInProviderRequest transformPaymentsInRequest(FraugsterPaymentsInContactRequest request)
			throws FraugsterException {
		LOG.debug("Fraugster transformPaymentsOutRequest : STARTED ");
		FraugsterPaymentsInProviderRequest paymentsInProviderRequest = new FraugsterPaymentsInProviderRequest();
		try {

			paymentsInProviderRequest.setAccountIdentification(request.getAccountIdentification());
			paymentsInProviderRequest.setAvTradeFrequency(request.getaVTradeFrequency());
			paymentsInProviderRequest.setAvTradeValue(request.getaVTradeValue());
			paymentsInProviderRequest.setAvsResult(request.getaVSResult());
			paymentsInProviderRequest.setBillAdLine1(request.getAddressOnCard());
			paymentsInProviderRequest.setBillAdZip(request.getCustomerAddressOrPostcode());
			paymentsInProviderRequest.setCcFirstName(request.getNameOnCard());
				paymentsInProviderRequest.setChequeClearanceDate(request.getChequeClearanceDate());
				paymentsInProviderRequest.setDebitCardAddedDate(request.getDebitCardAddedDate());
			paymentsInProviderRequest.setIsThreeds(request.getThreeDStatus());
			paymentsInProviderRequest.setPmtMethod(request.getPaymentType());
			paymentsInProviderRequest.setThirdPartyPayment(request.getThirdPartyPayment());
				paymentsInProviderRequest.setTransTs(request.getDateAndTime());
				paymentsInProviderRequest.setTransactionReference(request.getTransactionReference());
			transformPaymentsBaseRequest(paymentsInProviderRequest, request);
			transformDeviceInfoAndProviderBaseRequest(request, paymentsInProviderRequest);

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformPaymentsInRequest : ENDED ");
		return paymentsInProviderRequest;

	}

	/**
	 * Transform payments in response.
	 *
	 * @param response the response
	 * @return the fraugster payments in contact response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsInContactResponse transformPaymentsInResponse(FraugsterSearchDataResponse response)
			throws FraugsterException {

		LOG.debug("Fraugster transformPaymentsInResponse : STARTED ");
		FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
		try {
			transformBaseResponse(response, fraudDetectionResponse);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformPaymentsInResponse : ENDED ");

		return fraudDetectionResponse;
	}

}
