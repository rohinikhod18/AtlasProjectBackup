package com.currenciesdirect.gtg.compliance.fraugster.fraugsterport;

import java.util.regex.Pattern;

import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsin.response.FraudPredictFundsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsout.response.FraudPredictFundsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderOnUpdateSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderPaymentInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderPaymentOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request.ProviderSignUpRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response.FraudPredictSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterProfileBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.util.ObjectUtils;

public class FraudPredictTransformer {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FraudPredictTransformer.class);

	/** The fraud Predict transformer. */
	private static FraudPredictTransformer fraudPredictTransformer = null;
	
	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	private static final String BADREQUEST = "BAD_REQUEST";
	
	private static final String NOTPERFORMED = "NOT_PERFORMED";

	/**
	 * Instantiates a new fraud predict transformer.
	 */
	private FraudPredictTransformer() {

	}

	public static FraudPredictTransformer getInstance() {
		if (fraudPredictTransformer == null) {
			fraudPredictTransformer = new FraudPredictTransformer();
		}
		return fraudPredictTransformer;
	}

	/**
	 * @param request
	 * @return signupRequest
	 * @throws FraugsterException
	 */
	public ProviderSignUpRequest transformFraudPredictSignupRequest(FraugsterSignupContactRequest request)
			throws FraugsterException {

		LOG.debug("Fraud predict transform Signup Request : STARTED ");
		ProviderSignUpRequest signupRequest = new ProviderSignUpRequest();

		try {

			signupRequest.setEmailAddress(request.getEmail());
			transformDeviceInfoAndProviderBaseRequest(request, signupRequest);

			signupRequest.setJobTitle(request.getJobTitle());
			signupRequest.setNoOfContacts(request.getAccountContactNum());
			signupRequest.setRegModeY(request.getRegMode());
			signupRequest.setOrgCode(request.getOrganizationCode());
			signupRequest.setCustType(request.getCustomerType());

			signupRequest = ObjectUtils.replaceEmptyWithNull(signupRequest, ProviderSignUpRequest.class);
			if(null != signupRequest.getEmailAddress() && signupRequest.getEmailAddress().trim().isEmpty()) {
					signupRequest.setEmailAddress(null);
			}

			String jsonSignupProviderRequest = JsonConverterUtil.convertToJsonWithoutNull(signupRequest);
			LOG.warn("\n -------Signup fraudpredict Request Start -------- \n  {}", jsonSignupProviderRequest);
			LOG.warn(" \n -----------Signup fraudpredict Request End ---------");

		} catch (Exception e) {
			LOG.error("Error in  transformSignupRequest() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict : transform Signup Request : ENDED {}", signupRequest);

		return signupRequest;
	}

	/**
	 * @param request
	 * @param providerRequest
	 */
	private void transformDeviceInfoAndProviderBaseRequest(FraugsterProfileBaseRequest request,
			ProviderSignUpRequest providerRequest) {

		providerRequest.setEventType(request.getEventType());
		providerRequest.setCustomerNo(request.getCustID());
		providerRequest.setScreenResolution(request.getBrowserScreenResolution());
		providerRequest.setBrowserType(request.getBrowserName());
		providerRequest.setBrowserVersion(request.getBrowserMajorVersion());
	    providerRequest.setBillAdLine1(request.getBillingAddress());
		providerRequest.setDeviceType(request.getDeviceType());
		providerRequest.setDeviceName(request.getDeviceName());
		providerRequest.setDeviceManufacturer(request.getDeviceManufacturer());
		providerRequest.setDeviceOsType(request.getOsName());
		providerRequest.setBrowserLang(request.getBrowserLanguage());
		providerRequest.setBrowserOnline(request.getBrowserOnline());
		providerRequest.setAdCampaign(request.getAdCampaign());
		providerRequest.setAddressType(request.getAddressType());
		providerRequest.setAffiliateName(request.getAffiliateName());
		providerRequest.setAza(request.getAza());
		providerRequest.setBranch(request.getBranch());
		providerRequest.setChannel(request.getChannel());
		providerRequest.setCountryOfResidence(request.getCountryOfResidenceCode());
		providerRequest.setOpCountry(request.getCountriesOfOperation());
		providerRequest.setDeviceName(request.getDeviceName());
		providerRequest.setReferralText(request.getReferralText());
		providerRequest.setSearchEngine(request.getSearchEngine());
		providerRequest.setTitle(request.getTitle());
		providerRequest.setTurnover(request.getTurnover());
		providerRequest.setTxnValue(request.getTxnValueRange());
		providerRequest.setKeywords(request.getKeywords());
		providerRequest.setOnQueue(request.getOnQueue());
		providerRequest.setPhoneHome(request.getPhoneHome());
		providerRequest.setPhoneWork(request.getPhoneWork());
		providerRequest.setRegMode(request.getRegMode());
		providerRequest.setRegionSuburb(request.getRegionSuburb());
		providerRequest.setResidentialStatus(request.getResidentialStatus());
		providerRequest.setSource(request.getSource());
		providerRequest.setSubSource(request.getSubSource());
		providerRequest.setEidStatus(request.getEidStatus());
		providerRequest.setSanctionStatus(request.getSanctionStatus());
		ObjectUtils.setNullFieldsToDefault(providerRequest);
		
		if(null !=(request.getiPAddress()) && !(request.getiPAddress()).isEmpty()) {
			  if(validate(request.getiPAddress())) {
			     providerRequest.setIpAddress(request.getiPAddress());
			  }
			 else {
			       providerRequest.setIpAddress(null);
		        }
		  }
		else {
			providerRequest.setIpAddress(null);
		}
   }

	/**
	 * @param response
	 * @return fraudDetectionResponse
	 * @throws FraugsterException
	 */
	public FraugsterSignupContactResponse transformFraudPredictSignupResponse(FraudPredictSignupResponse response)
			throws FraugsterException {

		LOG.debug("Fraud Predict transformSignupResponse : STARTED ");
		FraugsterSignupContactResponse fraudDetectionResponse = new FraugsterSignupContactResponse();
		try {
			transformBaseResponse(response, fraudDetectionResponse);
		} catch (Exception e) {
			LOG.warn("Error in  transformSignupResponse() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict transformSignupResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * @param response
	 * @param fraudDetectionResponse
	 */
	private void transformBaseResponse(FraudPredictSignupResponse response,
			FraugsterSignupContactResponse fraudDetectionResponse) {
		
		if(null!=response.getStatus() && response.getStatus().equals(BADREQUEST)) {
			fraudDetectionResponse.setFrgTransId(NOTPERFORMED);
		}
		else {
			if(null!=response.getScore()) {
				fraudDetectionResponse.setScore((response.getScore()).toString());
				}
			fraudDetectionResponse.setPercentageFromThreshold((response.getPercentageFromThreshold()).toString());
			fraudDetectionResponse.setFrgTransId(response.getFrgTransId());
		}
		fraudDetectionResponse.setFraugsterApproved(Float.toString(response.getFraudpredictApproved()));
		fraudDetectionResponse.setMessage(response.getMessage());
		fraudDetectionResponse.setDecisionDrivers(response.getDecisionDrivers());
		fraudDetectionResponse.setError(response.getErrors());
		fraudDetectionResponse.setStatus(response.getStatus());
	}

	/**
	 * @param request
	 * @return updateRequest
	 * @throws FraugsterException
	 */
	public ProviderOnUpdateSignupRequest transformOnUpdateRequest(FraugsterOnUpdateContactRequest request)
			throws FraugsterException {

		LOG.debug("Fraud predict transform Update Request : STARTED ");
		ProviderOnUpdateSignupRequest updateRequest = new ProviderOnUpdateSignupRequest();

		try {
			updateRequest.setEmailAddress(request.getEmail());
			transformDeviceInfoAndProviderBaseRequest(request, updateRequest);
			updateRequest = ObjectUtils.replaceEmptyWithNull(updateRequest, ProviderOnUpdateSignupRequest.class);
			if(null != updateRequest.getEmailAddress() && updateRequest.getEmailAddress().trim().isEmpty()) {
					updateRequest.setEmailAddress(null);
			}

		} catch (Exception e) {
			LOG.error("Error in  transformUpdateRequest() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict : transform Update Request : ENDED {}",updateRequest);

		return updateRequest;
	}

	/**
	 * @param response
	 * @return fraudDetectionResponse
	 * @throws FraugsterException
	 */
	public FraugsterOnUpdateContactResponse transformUpdateResponse(FraudPredictSignupResponse response)
			throws FraugsterException {

		LOG.debug("Fraud Predict transform Update Response : STARTED ");
		FraugsterOnUpdateContactResponse fraudDetectionResponse = new FraugsterOnUpdateContactResponse();
		try {
			
			if(null!=response.getStatus() && response.getStatus().equals(BADREQUEST)) {
				fraudDetectionResponse.setFrgTransId(NOTPERFORMED);
			}
			else {
				
				if(null!=response.getScore()) {
					fraudDetectionResponse.setScore((response.getScore()).toString());
					}
				fraudDetectionResponse.setPercentageFromThreshold((response.getPercentageFromThreshold()).toString());
				fraudDetectionResponse.setFrgTransId(response.getFrgTransId());
			}
			fraudDetectionResponse.setFraugsterApproved(Float.toString(response.getFraudpredictApproved()));
			fraudDetectionResponse.setMessage(response.getMessage());
			fraudDetectionResponse.setDecisionDrivers(response.getDecisionDrivers());
			fraudDetectionResponse.setError(response.getErrors());
			fraudDetectionResponse.setStatus(response.getStatus());

		} catch (Exception e) {
			LOG.warn("Error in  transformUpdateResponse() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict transformUpdateResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * @param request
	 * @param contact
	 * @return paymentsInProviderRequest
	 * @throws FraugsterException
	 */
	public ProviderPaymentInRequest transformPaymentsInRequest(FraugsterPaymentsInRequest request,
			FraugsterPaymentsInContactRequest contact) throws FraugsterException {
		LOG.debug("Fraud Predict transform PaymentsIn Request : STARTED ");
		ProviderPaymentInRequest paymentsInProviderRequest = new ProviderPaymentInRequest();
		try {

			paymentsInProviderRequest.setBillAdLine1(contact.getAddressOnCard());
			paymentsInProviderRequest.setBillAdZip(contact.getCustomerAddressOrPostcode());
			paymentsInProviderRequest.setContractNumber(contact.getContractNumber());
			paymentsInProviderRequest.setCountryOfFund(contact.getCountryOfFund());
			paymentsInProviderRequest.setCustomerNo(contact.getCustomerAccountNumber());
			paymentsInProviderRequest.setCustType(request.getCustType());
			paymentsInProviderRequest.setOrgCode(request.getOrgCode());
			paymentsInProviderRequest.setRiskScore(contact.getRiskScore());
			paymentsInProviderRequest.setSellingAmountGbpValue(contact.getSellingAmountGbpValue());
			paymentsInProviderRequest.setSourceApplication(request.getSourceApplication());
			paymentsInProviderRequest.setThirdPartyPayment(contact.getThirdPartyPayment());
			paymentsInProviderRequest.setTransactionCurrency(contact.getCurrency());
			paymentsInProviderRequest.setTurnover(contact.getTurnover());
			paymentsInProviderRequest.setBrowserLang(contact.getBrowserLanguage());
			paymentsInProviderRequest.setBrowserOnline(contact.getBrowserOnline());
			paymentsInProviderRequest.setPaymentMethod(contact.getPaymentType());
			paymentsInProviderRequest.setScreenResolution(contact.getBrowserScreenResolution());
			paymentsInProviderRequest.setBrowserType(contact.getBrowserType());
			paymentsInProviderRequest.setBrowserVersion(contact.getBrowserMajorVersion());
			paymentsInProviderRequest.setDeviceManufacturer(contact.getDeviceManufacturer());
			paymentsInProviderRequest.setDeviceName(contact.getDeviceName());
			paymentsInProviderRequest.setDeviceOsType(contact.getOsName());
			paymentsInProviderRequest.setDeviceType(contact.getDeviceType());
			paymentsInProviderRequest = ObjectUtils.replaceEmptyWithNull(paymentsInProviderRequest, ProviderPaymentInRequest.class);
			String jsonPayInProviderRequest = JsonConverterUtil.convertToJsonWithoutNull(paymentsInProviderRequest);
			LOG.warn("\n ------- Payments in fraudpredict Request Start -------- \n  {}", jsonPayInProviderRequest);
			LOG.warn(" \n -----------Payments  fraudpredict Request End ---------");

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraugster transformPaymentsInRequest : ENDED ");
		return paymentsInProviderRequest;

	}

	/**
	 * @param response
	 * @return fraudDetectionResponse
	 * @throws FraugsterException
	 */
	public FraugsterPaymentsInContactResponse transformPaymentsInResponse(FraudPredictFundsInResponse response)
			throws FraugsterException {

		LOG.debug("Fraud Predict transform PaymentsIn Response : STARTED ");
		FraugsterPaymentsInContactResponse fraudDetectionResponse = new FraugsterPaymentsInContactResponse();
		try {

			if(null!=response.getStatus() && response.getStatus().equals(BADREQUEST)) {
				fraudDetectionResponse.setFrgTransId(NOTPERFORMED);
			}
			else {
				if(null!=response.getScore()) {
					fraudDetectionResponse.setScore((response.getScore()).toString());
					}
				fraudDetectionResponse.setPercentageFromThreshold((response.getPercentageFromThreshold()).toString());
				fraudDetectionResponse.setFrgTransId(response.getFrgTransId());
			}
			fraudDetectionResponse.setFraugsterApproved(Float.toString(response.getFraudPredictApproved()));
			fraudDetectionResponse.setMessage(response.getMessage());
			fraudDetectionResponse.setDecisionDrivers(response.getDecisionDrivers());
			fraudDetectionResponse.setError(response.getError());
			fraudDetectionResponse.setStatus(response.getStatus());

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict transformPaymentsInResponse : ENDED ");

		return fraudDetectionResponse;
	}

	/**
	 * @param request
	 * @param contact
	 * @return paymentsOutProviderRequest
	 * @throws FraugsterException
	 */
	public ProviderPaymentOutRequest transformPaymentsOutRequest(FraugsterPaymentsOutRequest request,
			FraugsterPaymentsOutContactRequest contact) throws FraugsterException {

		LOG.debug("Fraud Predict transformPaymentsOutRequest : STARTED ");
		ProviderPaymentOutRequest paymentsOutProviderRequest = new ProviderPaymentOutRequest();

		try {
			paymentsOutProviderRequest.setBeneficiaryBankAddress(contact.getBeneficiaryBankAddress());
			paymentsOutProviderRequest.setBeneficiaryBankName(contact.getBeneficiaryBankName());
			paymentsOutProviderRequest.setBeneficiaryCountry(contact.getBeneficiaryCountryCode());
			paymentsOutProviderRequest.setBeneficiaryPhone(contact.getBeneficiaryPhone());
			paymentsOutProviderRequest.setBeneficiaryType(contact.getBeneficiaryType());
			paymentsOutProviderRequest.setBrowserLang(contact.getBrowserLanguage());
			paymentsOutProviderRequest.setBrowserOnline(contact.getBrowserOnline());
			paymentsOutProviderRequest.setBeneficiaryCurrencyCode(contact.getBeneficiaryCurrencyCode());
			paymentsOutProviderRequest.setBrowserType(contact.getBrowserType());
			paymentsOutProviderRequest.setBrowserVersion(contact.getBrowserMajorVersion());
			paymentsOutProviderRequest.setBuyCurrency(contact.getBeneficiaryCurrency());
			paymentsOutProviderRequest.setBuyingAmount(contact.getBuyingAmount());
			paymentsOutProviderRequest.setContractNumber(contact.getContracrNumber());
			paymentsOutProviderRequest.setCustomerNo(contact.getCustomerNo());
			paymentsOutProviderRequest.setCustType(request.getCustType());
			paymentsOutProviderRequest.setDeviceManufacturer(contact.getDeviceManufacturer());
			paymentsOutProviderRequest.setDeviceName(contact.getDeviceName());
			paymentsOutProviderRequest.setDeviceOsType(contact.getOsName());
			paymentsOutProviderRequest.setDeviceType(contact.getDeviceType());
			paymentsOutProviderRequest.setOrgCode(request.getOrgCode());
			paymentsOutProviderRequest.setPurposeOfTrade(contact.getPurposeOfTrade());
			paymentsOutProviderRequest.setScreenResolution(contact.getBrowserScreenResolution());
			paymentsOutProviderRequest.setSellingCurrency(contact.getSellingCurrency());
			paymentsOutProviderRequest.setSourceApplication(request.getSourceApplication());
			
			if(null !=contact.getBeneficiaryEmail() && !(contact.getBeneficiaryEmail()).isEmpty())
			    paymentsOutProviderRequest.setBeneficiaryEmail(contact.getBeneficiaryEmail());
			else
				paymentsOutProviderRequest.setBeneficiaryEmail(null);
			paymentsOutProviderRequest = ObjectUtils.replaceEmptyWithNull(paymentsOutProviderRequest, ProviderPaymentOutRequest.class);
			String jsonPayOutProviderRequest = JsonConverterUtil.convertToJsonWithoutNull(paymentsOutProviderRequest);
			LOG.warn("\n ------- Payments out fraudpredict Request Start -------- \n  {}", jsonPayOutProviderRequest);
			LOG.warn(" \n -----------Payments out fraudpredict Request End ---------");

		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict transformPaymentsOutRequest : ENDED ");

		return paymentsOutProviderRequest;
	}

	/**
	 * @param response
	 * @return fraudDetectionResponse
	 * @throws FraugsterException
	 */
	public FraugsterPaymentsOutContactResponse transformPaymentsOutResponse(FraudPredictFundsOutResponse response)
			throws FraugsterException {

		LOG.debug("Fraud Predict transformPaymentsOutResponse : STARTED ");
		FraugsterPaymentsOutContactResponse fraudDetectionResponse = new FraugsterPaymentsOutContactResponse();
		try {
            
			if(null!=response.getStatus() && response.getStatus().equals(BADREQUEST)) {
				fraudDetectionResponse.setFrgTransId(NOTPERFORMED);
			}
			else {
				if(null!=response.getScore()) {
					fraudDetectionResponse.setScore((response.getScore()).toString());
					}
				fraudDetectionResponse.setPercentageFromThreshold((response.getPercentageFromThreshold()).toString());
				fraudDetectionResponse.setFrgTransId(response.getFrgTransId());
			}
			fraudDetectionResponse.setFraugsterApproved(Float.toString(response.getFraudPredictApproved()));
			fraudDetectionResponse.setMessage(response.getMessage());
			fraudDetectionResponse.setDecisionDrivers(response.getDecisionDrivers());
			fraudDetectionResponse.setError(response.getError());
			fraudDetectionResponse.setStatus(response.getStatus());
		} catch (Exception e) {
			LOG.error("Error in  transformPaymentsOutResponse() ", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_RESPONSE_TRANSFORMATION);
		}
		LOG.debug("Fraud Predict transformPaymentsOutResponse : ENDED ");

		return fraudDetectionResponse;
	}
	
	//method is added to apply validation on IP Address
	private boolean validate(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}

}
