package com.currenciesdirect.gtg.compliance.core;

import org.opensaml.saml1.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.core.blacklist.BlacklistModifierImpl;
import com.currenciesdirect.gtg.compliance.core.blacklist.BlacklistSearchImpl;
import com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistModifier;
import com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistSearch;
import com.currenciesdirect.gtg.compliance.core.blacklist.payref.BlacklistPayrefConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.core.blacklist.payref.IBlacklistPayref;
import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayRefModifierImpl;
import com.currenciesdirect.gtg.compliance.core.countrycheck.CountryCheckSearchImpl;
import com.currenciesdirect.gtg.compliance.core.countrycheck.ICountryCheckSearch;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FraudSightScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck.IRuleService;
import com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck.StateRuleService;
import com.currenciesdirect.gtg.compliance.core.ip.IpService;
import com.currenciesdirect.gtg.compliance.core.ip.IpServiceImpl;
import com.currenciesdirect.gtg.compliance.core.loadcache.FraudSightConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.core.loadcache.FraudSightProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.util.Constants;

/**
 * The Class ServiceExecutor.
 */
public class ServiceExecutor {

	/** The blacklist search. */
	private IBlacklistSearch blacklistSearch = BlacklistSearchImpl.getInstance();

	/** The service executor. */
	@SuppressWarnings("squid:S3077")
	private static volatile ServiceExecutor serviceExecutor = null;

	/** The ip service. */
	private IpService ipService = IpServiceImpl.getInstance();

	/** The rule service. */
	private IRuleService ruleService = StateRuleService.getInstance();

	/** The country check search. */
	private ICountryCheckSearch countryCheckSearch = CountryCheckSearchImpl.getInstance();

	/** The service transformer. */
	private IServiceTransformer serviceTransformer = ServiceTransformerImpl.getInstance();
	
	private IBlacklistModifier blacklistModifier = BlacklistModifierImpl.getInstance();

	/** The concrete data builder. */
	private FraudSightConcreteDataBuilder concreteDataBuilder = FraudSightConcreteDataBuilder.getInstance();
	
	/** The blacklist payref. */
	private IBlacklistPayref  blacklistPayref= BlacklistPayRefModifierImpl.getInstance();
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(ServiceExecutor.class);
	
	/** The concrete data builder. */
	private BlacklistPayrefConcreteDataBuilder concretePayRefDataBuilder=BlacklistPayrefConcreteDataBuilder.getInstance();

	/**
	 * Instantiates a new service executor.
	 */
	private ServiceExecutor() {

	}

	/**
	 * Gets the single instance of ServiceExecutor.
	 *
	 * @return single instance of ServiceExecutor
	 */
	public static ServiceExecutor getInstance() {
		if (serviceExecutor == null) {
			synchronized (ServiceExecutor.class) {
				if (serviceExecutor == null) {
					serviceExecutor = new ServiceExecutor();
				}
			}
		}
		return serviceExecutor;
	}
	
	
	/**
	 * Show blacklist data by type.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	public InternalServiceResponse showBlacklistDataByType(InternalServiceRequest request){
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			response = blacklistModifier.getBlacklistDataByType(request);
		} catch (BlacklistException e) {
			response.setStatus("Fail");
			response.setErrorCode(e.getErrors().getErrorCode());
			response.setErrorDescription(e.getErrors().getErrorDescription());
			logger.error("Exception in ServiceExecutor showBlacklistDataByType:", e);
		}
		return response;
	}

	/**
	 * Save into blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	public InternalServiceResponse saveIntoBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			response = blacklistModifier.saveIntoBlacklist(request);
		} catch (BlacklistException e) {
			response.setStatus("Fail");
			if (e.getErrors().getErrorCode().equalsIgnoreCase(BlacklistErrors.INVALID_INPUT.getErrorCode())) {
				response.setErrorCode(BlacklistErrors.INVALID_INPUT.getErrorCode());
				response.setErrorDescription(BlacklistErrors.INVALID_INPUT.getErrorDescription());
			} else if (e.getErrors().getErrorCode().equalsIgnoreCase(BlacklistErrors.INVALID_REQUEST.getErrorCode())) {
				response.setErrorCode(BlacklistErrors.INVALID_REQUEST.getErrorCode());
				response.setErrorDescription(BlacklistErrors.INVALID_REQUEST.getErrorDescription());
			} else if (e.getErrors().getErrorCode().equalsIgnoreCase(BlacklistErrors.DUPLICATE_DATA_ADDITION.getErrorCode())) {
				response.setErrorCode(BlacklistErrors.DUPLICATE_DATA_ADDITION.getErrorCode());
				response.setErrorDescription(BlacklistErrors.DUPLICATE_DATA_ADDITION.getErrorDescription());
			} else {
				logger.error("Exception in ServiceExecutor saveIntoBlacklistData:", e);
			}
		}
		return response;
	}
	
	/**
	 * Delete from blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	public InternalServiceResponse deleteFromBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			
			response = blacklistModifier.deleteFromBlacklist(request);
		} catch (BlacklistException e) {
			response.setStatus("Fail");
			response.setErrorCode(e.getErrors().getErrorCode());
			response.setErrorDescription(e.getErrors().getErrorDescription());
			logger.error("Exception in ServiceExecutor deleteFromBlacklistData:", e);
		}
		return response;
	}
	
	
	/**
	 * Search blacklist data.
	 *
	 * @param request the request
	 * @return the internal service response
	 */
	public InternalServiceResponse searchBlacklistData(InternalServiceRequest request) {
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			
			response = blacklistSearch.uiSearchFromBlacklist(request);
		} catch (BlacklistException e) {
			response.setStatus("Fail");
			response.setErrorCode(e.getErrors().getErrorCode());
			response.setErrorDescription(e.getErrors().getErrorDescription());
			logger.error("Exception in ServiceExecutor searchBlacklistData:", e);
		}
		return response;
	}
	
	/**
	 * Execute blacklist service.
	 *
	 * @param request
	 *            the request
	 * @param auroraId
	 *            the aurora id
	 * @return the blacklist contact response
	 */
	public BlacklistContactResponse executeBlacklistService(InternalServiceRequestData request) {
		BlacklistSTPRequest blacklistSTPRequest = new BlacklistSTPRequest();
		BlacklistContactResponse blacklistSTPResponse = new BlacklistContactResponse();
		try {
			blacklistSTPRequest = serviceTransformer.transformToBlacklistSTPRequest(request);
			blacklistSTPResponse = blacklistSearch.stpSearchFromBlacklist(blacklistSTPRequest);
			blacklistSTPResponse = serviceTransformer.transformBlacklistSTPResponse(request, blacklistSTPResponse);
			
		} catch (InternalRuleException e) {
			logger.error("Exception in InternalServicesFacade executeBlacklistService:", e);
			if (e.getErrors().getErrorCode().equals(BlacklistErrors.INVALID_REQUEST.getErrorCode()) 
					|| e.getErrors().getErrorCode().equals(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA.getErrorCode()) ) {
				blacklistSTPResponse.setStatus(Constants.NOT_PERFORMED);
			}else{
				blacklistSTPResponse.setStatus(Constants.SERVICE_FAILURE);
			}
			blacklistSTPResponse.setErrorCode(e.getErrors().getErrorCode());
			blacklistSTPResponse.setErrorCode(e.getErrors().getErrorDescription());
		} catch (Exception e) {
			blacklistSTPResponse.setStatus(Constants.SERVICE_FAILURE);
			blacklistSTPResponse.setErrorCode("0001");
			blacklistSTPResponse.setErrorCode("Generic Exception");
			logger.error("Exception in InternalServicesFacade executeBlacklistService:", e);
		}
		return blacklistSTPResponse;

	}

	/**
	 * Execute global check service.
	 *
	 * @param request
	 *            the request
	 * @param orgCode
	 *            the org code
	 * @return the global check contact response
	 */
	public GlobalCheckContactResponse executeGlobalCheckService(InternalServiceRequestData request, String orgCode) {
		GlobalCheckContactResponse checkResponse = new GlobalCheckContactResponse();
		try {
			
			if(Constants.USA.equalsIgnoreCase(request.getCountry())){
				StateRuleRequest stateRuleRequest = serviceTransformer.transformToRuleRequest(request, orgCode);
				checkResponse = ruleService.applyRule(stateRuleRequest);
			}else{
				checkResponse.setStatus(Constants.NOT_REQUIRED);
				checkResponse.setCountry(request.getCountry());
				checkResponse.setState(request.getState());
			}
		
		} catch (InternalRuleException e) {
			logger.error("Exception in InternalServicesFacade doIpCheck:", e);
			if (e.getErrors().getErrorCode().equals(GlobalCheckErrors.APPLY_RULE_FAILED.getErrorCode())
					|| e.getErrors().getErrorCode().equals(GlobalCheckErrors.STATE_CAN_NOT_BE_BLANK.getErrorCode())) {
				checkResponse.setStatus(Constants.NOT_PERFORMED);
			} else {
				checkResponse.setStatus(Constants.SERVICE_FAILURE);
			}
			checkResponse.setErrorCode(e.getErrors().getErrorCode());
			checkResponse.setErrorDescription(e.getErrors().getErrorDescription());
		}
		return checkResponse;

	}

	/**
	 * Execute ip service.
	 *
	 * @param request
	 *            the request
	 * @return the ip contact response
	 */
	public IpContactResponse executeIpService(InternalServiceRequestData request) {
		IpServiceRequest ipServiceRequest = new IpServiceRequest();
		IpContactResponse ipServiceResponse = new IpContactResponse();
		try {
			ipServiceRequest = serviceTransformer.transformTpIpRequest(request);
			ipServiceResponse = ipService.checkIpPostCodeDistance(ipServiceRequest);			
		
		} catch (Exception exception) {
			logger.error(Constants.EXCEPTION_IN_SERVICE_EXECUTOR_IP_SERVICE, exception);
			ipServiceResponse.setStatus(Constants.SERVICE_FAILURE);
			ipServiceResponse.setErrorCode("0001");
			ipServiceResponse.setErrorDescription("Generic Exception");
		}
		return ipServiceResponse;

	}

	/**
	 * Execute country check service.
	 *
	 * @param request
	 *            the request
	 * @param correlationId
	 *            the correlation id
	 * @return the country check contact response
	 */
	public CountryCheckContactResponse executeCountryCheckService(InternalServiceRequestData request) {

		CountryCheckContactResponse response = null;

		try {
			response = countryCheckSearch.getCountryCheckResponse(request.getCountry());
		} catch (InternalRuleException e) {
			response = new CountryCheckContactResponse();
			if(e.getErrors().getErrorCode().equals(CountryCheckErrors.INVALID_REQUEST.getErrorCode()) 
					|| e.getErrors().getErrorCode().equals(CountryCheckErrors.ERROR_COUNTRY_NOT_FOUND.getErrorCode()) 
					|| e.getErrors().getErrorCode().equals(CountryCheckErrors.ERROR_WHILE_SEARCHING_DATA.getErrorCode())){
				response.setStatus(Constants.NOT_PERFORMED);
			logger.debug("Exception in ServiceExecutor executeCountryCheckService:", e);
			}else{
				response.setStatus(Constants.SERVICE_FAILURE);
				logger.error("Exception in ServiceExecutor executeCountryCheckService:", e);
			}
			response.setErrorcode(e.getErrors().getErrorCode());
			response.setErrorDescription(e.getErrors().getErrorDescription());
		}

		return response;
	}
	
	public CardFraudScoreResponse executeCardFraudScoreService(InternalServiceRequestData request) {
		CardFraudScoreResponse response =new CardFraudScoreResponse();
		try {
		response.setStatus(Constants.NOT_REQUIRED);
		if(Constants.PAYMENT_METHOD_DEBIT.equalsIgnoreCase(request.getPaymentMethod())
				&& null != request.getCardFraudScore()){
			if(request.getCardFraudScoreThreshold()==0 && request.getCardFraudScore()==0){
				response.setStatus(Constants.FAIL);
				response.settScore(request.getCardFraudScore());
				return response;
			}
			if(request.getCardFraudScoreThreshold()>=request.getCardFraudScore()){
				response.setStatus(Constants.PASS);
			}else {
				response.setStatus(Constants.FAIL);
				response.settScore(request.getCardFraudScore());//Add for AT-3470
			}
		}
		
		}catch (Exception e) {
			logger.error("Exception in ServiceExecutor executeCardFraudScoreService:", e);
		}
		return response;
	}

	public FraudSightScoreResponse executeFraudSightScoreService(InternalServiceRequestData request) {
		FraudSightScoreResponse response =new FraudSightScoreResponse();
		try {
			response.setStatus(Constants.NOT_REQUIRED);
			if(Constants.PAYMENT_METHOD_DEBIT.equalsIgnoreCase(request.getPaymentMethod())
					&& null != request.getFraudSightScore()){
				executeFraudSightService(request,response);
			}
		}catch (Exception e) {
			logger.error("Exception in ServiceExecutor executeCardFraudScoreService:", e);
		}
		return response;
	}
	
	//AT-3714
	public FraudSightScoreResponse executeFraudSightService(InternalServiceRequestData requestData,FraudSightScoreResponse response) {
		FraudSightProviderProperty fraudSightProviderProperty = null;
		try {
			fraudSightProviderProperty = concreteDataBuilder.getProviderInitConfigProperty("FRAUDSIGHTSCORE");
			double highRiskThreshold = fraudSightProviderProperty.getHighRiskThreshold();
			double lowRiskThreshold = fraudSightProviderProperty.getLowRiskThreshold();
			double reviewThreshold = fraudSightProviderProperty.getReviewThreshold();
			double fsScore=Double.parseDouble(requestData.getFraudSightScore());
			if (requestData.getFraudSightMessage().equalsIgnoreCase("low-risk")) {
				if (fsScore < lowRiskThreshold) {
					response.setStatus(Constants.PASS);
				} else {
					response.setStatus(Constants.FAIL);
					response.setFsScore(requestData.getFraudSightScore());
				}
			}

			if (requestData.getFraudSightMessage().equalsIgnoreCase("high-risk")) {
				if (fsScore < highRiskThreshold) {
					response.setStatus(Constants.PASS);
				} else {
					response.setStatus(Constants.FAIL);
					response.setFsScore(requestData.getFraudSightScore());
				}
			}

			if (requestData.getFraudSightMessage().equalsIgnoreCase("review")) {
				if (fsScore < reviewThreshold) {
					response.setStatus(Constants.PASS);
				} else {
					response.setStatus(Constants.FAIL);
					response.setFsScore(requestData.getFraudSightScore());
				}
			}
		} catch (Exception e) {
			logger.error("Exception in ServiceExecutor executeFraudSightService:", e);
		}
		return response;
	}
	
	/**
	 * Execute pay ref check service.
	 *
	 * @param request the request
	 * @return the blacklist payref contact response
	 * @throws Exception the exception
	 */
	public BlacklistPayrefContactResponse executePayRefCheckService(InternalServiceRequestData request) throws Exception
	{
		BlacklistPayrefProviderProperty providerProperty;
		BlacklistPayrefContactResponse  blacklistPayrefContactResponse = new BlacklistPayrefContactResponse();
		int payRefLength = request.getPayementRefernce().length();
		
		try {
			providerProperty=concretePayRefDataBuilder.getProviderInitConfigProperty("BLACKLIST_PAY_REF");
			if(payRefLength >= 3) {
				blacklistPayrefContactResponse=blacklistPayref.doPayRefPaymentsOutCheck(request,providerProperty);
				if(blacklistPayrefContactResponse.getStatus() == null) {
					blacklistPayrefContactResponse.setPaymentReference(request.getPayementRefernce());
					blacklistPayrefContactResponse.setStatus(Constants.SERVICE_FAILURE);
				}
			} else {
				blacklistPayrefContactResponse.setPaymentReference(request.getPayementRefernce());
				blacklistPayrefContactResponse.setStatus(Constants.PASS);
			}
			
		} catch (BlacklistException e) {
			logger.error("Exception in ServiceExecutor executePayRefCheckService:", e);
		}
		
		return blacklistPayrefContactResponse;
		
	}

}
