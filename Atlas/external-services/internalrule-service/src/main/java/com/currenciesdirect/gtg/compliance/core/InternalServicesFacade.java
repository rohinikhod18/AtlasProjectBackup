package com.currenciesdirect.gtg.compliance.core;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FraudSightScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.util.Constants;
import com.currenciesdirect.gtg.compliance.util.RequestTypes;

/**
 * @author manish
 *
 */
public class InternalServicesFacade implements InternalService {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(InternalServicesFacade.class);
	
	private static InternalService internalService = null;

	private ServiceExecutor serviceExecutor = ServiceExecutor.getInstance();

	private InternalServicesFacade() {

	}

	public static InternalService getInstance() {
		if (internalService == null) {
			internalService = new InternalServicesFacade();
		}
		return internalService;
	}
	
	/**
	 * Business--
	 * Perform checks.
	 * Depending on requestType method is called.
	 * @param request the request
	 * @return the internal service response
	 * @throws InternalRuleException the internal rule exception
	 */
	@Override
	public InternalServiceResponse performCheck(InternalServiceRequest request) throws InternalRuleException {
		InternalServiceResponse internalServiceResponse = null;
		try {			
			RequestTypes requestType = RequestTypes.valueOf(request.getRequestType().toUpperCase());
			checkRequestType(requestType);
			internalServiceResponse = checkForRegistration(requestType, request);
			if (null == internalServiceResponse) {
				switch (requestType) {				
				case PAYMENT_IN:
					return performPaymentIn(request);
				case PAYMENT_OUT:
					return performPaymentOut(request);
				case SHOW_BLACKLIST_DATA:
					return showBlacklistDataByType(request);
				case ADD_BLACKLIST_DATA:
					return saveIntoBlacklistData(request);
				case DELETE_BLACKLIST_DATA:
					return deleteFromBlacklistData(request);
				case SEARCH_BLACKLIST_DATA:
					return searchBlacklistData(request);
				case PAYMENT_OUT_PAY_REF:
					return performPaymentOutPayRef(request); //AT-3658
				default:
					throw new InternalRuleException(InternalRuleErrors.INVALID_REQUEST_TYPE);
				}
			}
		} catch (InternalRuleException exception) {
			throw exception;

		} catch (Exception exception) {
			throw new InternalRuleException(InternalRuleErrors.FAILED, exception);
		}
		return internalServiceResponse;

	}

	private InternalServiceResponse checkForRegistration(RequestTypes requestType, InternalServiceRequest request)
			throws InternalRuleException {
		InternalServiceResponse internalServiceResponse = null;
		try {
			switch (requestType) {
			case SIGNUP_PFX:
			case ADD_CONTACT_PFX:
			case UPDATE_ACCOUNT_PFX:
			case BLACKLIST_RECHECK:
				return performPfxRegistration(request);
			case SIGNUP_CFX:
			case ADD_CONTACT_CFX:
			case UPDATE_ACCOUNT_CFX:
				return performCfxRegistration(request);
			default:
				break;
			}
		} catch (Exception exception) {
			throw new InternalRuleException(InternalRuleErrors.FAILED, exception);
		}
		return internalServiceResponse;
	}

	private void checkRequestType(RequestTypes requestType) throws InternalRuleException {
		if (requestType == null) {
			throw new InternalRuleException(InternalRuleErrors.INVALID_REQUEST_TYPE);
		}
	}
	
	/**
	 * Save into blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	private InternalServiceResponse saveIntoBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response;
		response = serviceExecutor.saveIntoBlacklistData(request);
		return response;
	}

	/**
	 * Show blacklist data by type.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	private InternalServiceResponse showBlacklistDataByType(InternalServiceRequest request) {
		InternalServiceResponse response;
		response = serviceExecutor.showBlacklistDataByType(request);
		return response;
	}

	/**
	 * Delete from blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	private InternalServiceResponse deleteFromBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response;
		response = serviceExecutor.deleteFromBlacklistData(request);
		return response;
	}
	
	/**
	 * Search blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	private InternalServiceResponse searchBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response;
		response = serviceExecutor.searchBlacklistData(request);
		return response;
	}
	
	/**
	 * status is returned with contactResponse as parameter by calling different method for each status -
	 * */
	private String getContactStatus(ContactResponse response) {
		if (isContactFailed(response)) {
			return Constants.FAIL;
		} else if (isContactInWatchlist(response)) {
			return Constants.WATCHLIST;
		} else if (isContactNotRequired(response)) {
			return Constants.NOT_REQUIRED;
		} else {
			return Constants.PASS;
		}

	}

	private boolean isContactFailed(ContactResponse response) {

		return Constants.FAIL.equals(response.getBlacklist().getStatus())
				|| Constants.FAIL.equals(response.getCountryCheck().getStatus())
				|| Constants.FAIL.equals(response.getGlobalCheck().getStatus())
				|| Constants.FAIL.equals(response.getIpCheck().getStatus())
				|| Constants.FAIL.equals(response.getBlacklistPayref().getStatus());
	}

	private boolean isContactInWatchlist(ContactResponse response) {

		return Constants.WATCHLIST.equals(response.getCountryCheck().getStatus())
				|| Constants.WATCHLIST.equals(response.getGlobalCheck().getStatus());
	}

	private boolean isContactNotRequired(ContactResponse response) {

		return Constants.NOT_REQUIRED.equals(response.getBlacklist().getStatus())
				&& Constants.NOT_REQUIRED.equals(response.getCountryCheck().getStatus())
				&& Constants.NOT_REQUIRED.equals(response.getGlobalCheck().getStatus())
				&& Constants.NOT_REQUIRED.equals(response.getIpCheck().getStatus());
	}
	/**
	 * Default response is set which is NOT_REQUIRED
	 */
	private ContactResponse createDefaultContactResponse() {
		IpContactResponse ipServiceResponse = new IpContactResponse();
		GlobalCheckContactResponse checkResponse = new GlobalCheckContactResponse();
		CountryCheckContactResponse countryCheckResponse = new CountryCheckContactResponse();
		BlacklistContactResponse blacklistSTPResponse = new BlacklistContactResponse();
		BlacklistPayrefContactResponse blacklistPayrefContactResponse = new BlacklistPayrefContactResponse();
		ContactResponse contactResponse = new ContactResponse();
		contactResponse.setBlacklist(blacklistSTPResponse);
		contactResponse.setCountryCheck(countryCheckResponse);
		contactResponse.setGlobalCheck(checkResponse);
		contactResponse.setIpCheck(ipServiceResponse);
		contactResponse.setBlacklistPayref(blacklistPayrefContactResponse);//Add for AT-3649
		return contactResponse;
	}
	
	/**
	 * Business -- 
	 * For CFX Registration only blacklistService is performed and response is set accordingly.
	 * Implementation---
	 * 1) Get the InternalServiceRequestData from the request.Initialize isBlacklisted to false.
	 * 2) InternalServiceRequestData is iterated and by default all services are initialized to Not Required status.
	 * 3) Then blacklist method is called and the response is set in contactResponse.If status is false then isBlacklisted is set to true
	 * 4) The contactResponse is  passed to getContactStatus method and status returned from method is set.
	 */
	private InternalServiceResponse performCfxRegistration(InternalServiceRequest request) {
		boolean isBlacklisted = false;
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponses = new ArrayList<>();
		response.setContacts(contactResponses);
		List<InternalServiceRequestData> internalServiceRequestData = request.getSearchdata();
		for (InternalServiceRequestData requestData : internalServiceRequestData) {
			ContactResponse contactResponse = createDefaultContactResponse();
			requestData.setIsEmailDomainCheckRequired(Boolean.FALSE);
			if (!isBlacklisted) {
				BlacklistContactResponse blacklistSTPResponse = serviceExecutor.executeBlacklistService(requestData);
				contactResponse.setBlacklist(blacklistSTPResponse);
				if (Constants.FAIL.equalsIgnoreCase(blacklistSTPResponse.getStatus())) {
					isBlacklisted = true;
				}
			}
			contactResponse.setId(requestData.getId());
			contactResponse.setEntityType(requestData.getEntityType());
			contactResponse.setContactStatus(getContactStatus(contactResponse));
			contactResponses.add(contactResponse);
		}
		return response;
	}

	/**
	 * Business -- 
	 * For PFX Registration,blacklistService,CountryCheck service,GlobalCheck Service and Ip Service is performed in 
	 * order mentioned.
	 * If any of the above service is failed then further services are not performed and also other micro-services 
	 * are not performed.
	 *Implementation---
	 * 1) Get the InternalServiceRequestData from the request.Initialize isBlacklisted to false.
	 * 2) InternalServiceRequestData is iterated and by default all services are initialized to Not Required status.
	 * 3) Then each Service is called and there status is checked.If any of service returns fail status then isBlacklisted value is true.
	 *    3.1) isBlacklisted value is checked and since it is not false rest of services method are not called and getContactStatus method is 
	 *    called.
	 * 4) If all services are performed then response from all services are set in contactResponse and passed to getContactStatus method and 
	 *   status returned from method is set.
	 */
	private InternalServiceResponse performPfxRegistration(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponses = new ArrayList<>();
		response.setContacts(contactResponses);
		List<InternalServiceRequestData> internalServiceRequestData = request.getSearchdata();
		for (InternalServiceRequestData requestData : internalServiceRequestData) {
			ContactResponse contactResponse = createDefaultContactResponse();
			requestData.setIsEmailDomainCheckRequired(Boolean.TRUE);
			
			BlacklistContactResponse blacklistSTPResponse = serviceExecutor.executeBlacklistService(requestData);
			contactResponse.setBlacklist(blacklistSTPResponse);	
			
			if(Boolean.FALSE.equals(request.getOnlyBlacklistCheckPerform())) {
				CountryCheckContactResponse countryCheckResponse = serviceExecutor.executeCountryCheckService(requestData);
				contactResponse.setCountryCheck(countryCheckResponse);	
				
				GlobalCheckContactResponse checkResponse = serviceExecutor.executeGlobalCheckService(requestData,
						request.getOrgCode());
				contactResponse.setGlobalCheck(checkResponse);	
				
				IpContactResponse ipServiceResponse = serviceExecutor.executeIpService(requestData);
				contactResponse.setIpCheck(ipServiceResponse);
			}
			
			contactResponse.setId(requestData.getId());
			contactResponse.setEntityType(requestData.getEntityType());
			contactResponse.setContactStatus(getContactStatus(contactResponse));
			contactResponses.add(contactResponse);
		}
		if(Boolean.TRUE.equals(request.getOnlyBlacklistCheckPerform())) {
			response.setOnlyBlacklistCheckPerform(Boolean.TRUE);
		}
		return response;
	}

	/**
	 * Business -- 
	 * For FundsIn(PaymentIN),blacklistService,CountryCheck service,and CardFraudScore service is performed.
	 * each service is called and there response is set and return
	 * 
	 *Implementation---
	 * 1) Get the InternalServiceRequestData from the request.Initialize isBlacklisted to false.
	 * 2) InternalServiceRequestData is iterated and by default all services are initialized to Not Required status.
	 * 3) Then each Service's method is called and the response is set to contactResponse.
	 * 4) After all the methods are called and the respective responses are set in contactResponse and it is passed to
	 *    getContactStatus method from which status is returned which is set.
	 */
	private InternalServiceResponse performPaymentIn(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponses = new ArrayList<>();
		response.setContacts(contactResponses);
		List<InternalServiceRequestData> internalServiceRequestData = request.getSearchdata();
		for (InternalServiceRequestData requestData : internalServiceRequestData) {
			ContactResponse contactResponse = createDefaultContactResponse();
			/*
			 * For funds IN request with switch/debit method blacklist will be performed on ccname only
			 */
			if(!isNullOrEmpty(requestData.getCcName())){
				BlacklistContactResponse blacklistSTPResponse = serviceExecutor.executeBlacklistService(requestData);
				if(blacklistSTPResponse.getStatus().equals(Constants.NOT_PERFORMED))
					blacklistSTPResponse.setStatus(Constants.NOT_REQUIRED);
				contactResponse.setBlacklist(blacklistSTPResponse);
			}else{
				BlacklistContactResponse blacklistSTPResponse = new BlacklistContactResponse();
				blacklistSTPResponse.setStatus(Constants.NOT_REQUIRED);
				contactResponse.setBlacklist(blacklistSTPResponse);
			}
			CountryCheckContactResponse countryCheckResponse = serviceExecutor.executeCountryCheckService(requestData);
			if(countryCheckResponse.getStatus().equalsIgnoreCase(Constants.WATCHLIST)){
				countryCheckResponse.setStatus(Constants.FAIL);
			}
			if((CountryCheckErrors.INVALID_REQUEST.getErrorCode()).equals(countryCheckResponse.getErrorcode())){
				countryCheckResponse.setStatus(Constants.NOT_REQUIRED);
				countryCheckResponse.setErrorcode("");
				countryCheckResponse.setErrorDescription("");
			}
			contactResponse.setCountryCheck(countryCheckResponse);
			//AT-3830
			if(Boolean.TRUE.equals(request.getIsFraudSightEligible())) {
			FraudSightScoreResponse fraudSightScoreResponse = serviceExecutor.executeFraudSightScoreService(requestData);
			contactResponse.setFraudSightCheck(fraudSightScoreResponse);
			}else {
				FraudSightScoreResponse fraudSightScoreResponse =new FraudSightScoreResponse();
				fraudSightScoreResponse.setStatus(Constants.NOT_REQUIRED);
				contactResponse.setFraudSightCheck(fraudSightScoreResponse);
			}
			
            if(Boolean.FALSE.equals(request.getIsFraudSightEligible()) && Boolean.TRUE.equals(request.getIsRgDataExists())) {
			CardFraudScoreResponse cardFraudScoreResponse = serviceExecutor.executeCardFraudScoreService(requestData);
			contactResponse.setCardFraudCheck(cardFraudScoreResponse);
			}else {
				CardFraudScoreResponse cardFraudScoreResponse =new CardFraudScoreResponse();
				cardFraudScoreResponse.setStatus(Constants.NOT_REQUIRED);
				contactResponse.setCardFraudCheck(cardFraudScoreResponse);
			}
            
			contactResponse.setId(requestData.getId());
			contactResponse.setEntityType(requestData.getEntityType());
			contactResponse.setContactStatus(getContactStatus(contactResponse));
			contactResponses.add(contactResponse);
		}
		return response;
	}
	
	/**
	 * Business -- 
	 * For FundsOut(PaymentOut),blacklistService,CountryCheck service is performed.
	 * each service is called and there response is set and return
	 *Implementation---
	 * 1) Get the InternalServiceRequestData from the request.Initialize isBlacklisted to false.
	 * 2) InternalServiceRequestData is iterated and by default all services are initialized to Not Required status.
	 * 3) Then each Service's method is called and the response is set to contactResponse.
	 * 4) After all the methods are called and the respective responses are set in contactResponse and it is passed to
	 *    getContactStatus method from which status is returned which is set.
	 */
	private InternalServiceResponse performPaymentOut(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponses = new ArrayList<>();
		response.setContacts(contactResponses);
		List<InternalServiceRequestData> internalServiceRequestData = request.getSearchdata();
		
		for (InternalServiceRequestData requestData : internalServiceRequestData) {
			ContactResponse contactResponse = createDefaultContactResponse();
			BlacklistContactResponse blacklistSTPResponse = serviceExecutor.executeBlacklistService(requestData);
			contactResponse.setBlacklist(blacklistSTPResponse);
			CountryCheckContactResponse countryCheckResponse = serviceExecutor.executeCountryCheckService(requestData);
		    
			if(countryCheckResponse.getStatus().equalsIgnoreCase(Constants.WATCHLIST)){
				countryCheckResponse.setStatus(Constants.FAIL);
			}
			
			try {
				BlacklistPayrefContactResponse blacklistPayrefCheckResponse= serviceExecutor.executePayRefCheckService(requestData);
				contactResponse.setBlacklistPayref(blacklistPayrefCheckResponse);
			} catch (Exception e) {
				LOG.error("Error in InternalServicesFacade method performPaymentOut:", e);
			}
			contactResponse.setCountryCheck(countryCheckResponse);
			contactResponse.setId(requestData.getId());
			contactResponse.setEntityType(requestData.getEntityType());
			contactResponse.setContactStatus(getContactStatus(contactResponse));
			contactResponses.add(contactResponse);
		}
		return response;
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str the str
	 * @return true, if is null or empty
	 */
	//checks whether string is null or empty
	public boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;
		
		return result;
	}
	
	//AT-3658
	/**
	 * Perform payment out pay ref.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	private InternalServiceResponse performPaymentOutPayRef(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponses = new ArrayList<>();
		response.setContacts(contactResponses);
		List<InternalServiceRequestData> internalServiceRequestData = request.getSearchdata();
		
		
		for (InternalServiceRequestData requestData : internalServiceRequestData) {
			BlacklistPayrefContactResponse blacklistPayrefContactResponse = new BlacklistPayrefContactResponse();
			ContactResponse contactResponse = new ContactResponse();
			contactResponse.setBlacklistPayref(blacklistPayrefContactResponse);
			try {
				BlacklistPayrefContactResponse blacklistPayrefCheckResponse= serviceExecutor.executePayRefCheckService(requestData);
				contactResponse.setBlacklistPayref(blacklistPayrefCheckResponse);
			} catch (Exception e) {
				LOG.error("Error in InternalServicesFacade method performPaymentOutPayRef:", e);
			}
			contactResponse.setId(requestData.getId());
			contactResponse.setEntityType(requestData.getEntityType());
			contactResponse.setContactStatus(contactResponse.getBlacklistPayref().getStatus());
			contactResponses.add(contactResponse);
		}
		return response;
	}


	
}