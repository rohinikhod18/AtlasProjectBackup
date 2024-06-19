/*
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.transactionmonitoringport;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.PostCardTransactionRequestTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.IDBService;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.ITransactionMonitoringProviderService;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.dbport.TransactionMonitoringDBServiceImpl;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.util.Constants;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class IntuitionPort.
 */
public class IntuitionPort implements ITransactionMonitoringProviderService {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(IntuitionPort.class);

	/** The i transaction monitoring provider service. */
	private static ITransactionMonitoringProviderService iTransactionMonitoringProviderService = null;

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/** The intuition transformer. */
	private IntuitionTransformer intuitionTransformer = IntuitionTransformer.getInstance();
	
	/** The Constant UPDATE. */
	private static final String UPDATE = "update";

	/** The Constant BASIC. */
	private static final String BASIC = "Basic";

	/** The Constant CORRELATIONID. */
	private static final String CORRELATIONID = "correlation-id";

	/** The db service impl. */
	private IDBService dbServiceImpl = TransactionMonitoringDBServiceImpl.getInstance();

	/**
	 * Instantiates a new intuition port.
	 */
	private IntuitionPort() {
	}

	/**
	 * Gets the single instance of IntuitionPort.
	 *
	 * @return single instance of IntuitionPort
	 */
	public static ITransactionMonitoringProviderService getInstance() {
		if (iTransactionMonitoringProviderService == null) {
			iTransactionMonitoringProviderService = new IntuitionPort();
		}
		return iTransactionMonitoringProviderService;
	}

	/**
	 * Perfrom intuition account check.
	 *
	 * @param request                 the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringAccountSignupResponse perfromIntuitionAccountCheck(TransactionMonitoringSignupRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException {

		TransactionMonitoringAccountSignupResponse tmSignupResponse = new TransactionMonitoringAccountSignupResponse();
		IntuitionSignupProviderRequest intuitionSignupProviderRequest = null;
		
		Response response = null;
		ResteasyClient client = null;

		try {
			/*
			LOG.debug("IntuitionPort STARTED : perfromIntuitionAccountCheck() ");
			client = resteasyClientHandler.getRetEasyClient();

			intuitionSignupProviderRequest = intuitionTransformer.transformNewSignupAccountRequest(request);
			
			String jsonAccountSignupRequest = JsonConverterUtil
					.convertToJsonWithNull(intuitionSignupProviderRequest);
			
			
			LOG.warn("\n -------Intuition Account Request Start -------- \n  {}", jsonAccountSignupRequest);
			LOG.warn(" \n -----------Intuition Account Request End ---------");

			ResteasyWebTarget target1 = client.target(accountProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					BASIC + " " + accountProviderProperty.getAuthorization());
			LOG.warn("\n Intuition AUTHORIZATION Key:  {}", accountProviderProperty.getAuthorization());
			LOG.warn("\n Intuition EndPoint URL:  {}", accountProviderProperty.getEndPointUrl());
			
			response = sendDataToIntuition(request, tmSignupResponse, response, jsonAccountSignupRequest, clientrequest);
			
			LOG.warn("\n Intuition ALL Response:  {}", response);
			tmSignupResponse = handleTransactionMonitoringServerTransactionResponse(response, request, jsonAccountSignupRequest);
			
			String jsonAccountSignupResponse = JsonConverterUtil.convertToJsonWithNull(tmSignupResponse);
			*/
			String json = "{\"errorCode\":null,\"errorDescription\":null,\"accountId\":null,\"httpStatus\":200,"
	        		+ "\"status\":\"PASS\",\"errorDesc\":\"{\\\"id\\\":nu\\\"\\\",\\\"RuleScore\\\":20,"
	        		+ "\\\"profileScore\\\":64,\\\"RiskLevel\\\":\\\"High\\\",\\\"cardDecision\\\":null,\\\"Status\\\":"
	        		+ "\\\"Hold\\\",\\\"RulesTriggered\\\":[\\\"P-HRCTestRule\\\",\\\"P-HRCurrOccVowel\\\"]}\","
	        		+ "\"transactionMonitoringAccountProviderResponse\":{\"correlationId\":\"f789d5e872915d378ff098e67241970b\","
	        		+ "\"id\":\"\",\"Status\":\"Hold\",\"RuleScore\":\"20\",\"profileScore\":\"64\","
	        		+ "\"RulesTriggered\":[\"P-HRCTestRule\",\"P-HRCurrOccVowel\"],\"RiskLevel\":\"High\"},\"osr_id\":null}";

	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        tmSignupResponse = objectMapper.readValue(json, TransactionMonitoringAccountSignupResponse.class);
	       
	        String jsonAccountSignupRequest = JsonConverterUtil.convertToJsonWithNull(tmSignupResponse);
	        LOG.warn("\n -------Intuition Account Request Start -------- \n  {}", jsonAccountSignupRequest);
	        LOG.warn(" \n -----------Intuition Account Request End ---------");

	        String jsonAccountSignupResponse = JsonConverterUtil.convertToJsonWithNull(tmSignupResponse);
	        LOG.warn("\n -------Intuition Account Response Start -------- \n  {}", jsonAccountSignupResponse);
	        LOG.warn(" \n -----------Intuition Account Response End ---------");

		} /*
			 * catch (TransactionMonitoringException e) {
			 * LOG.error("Error in  IntuitionPort : perfromIntuitionAccountCheck() ", e); }
			 */ catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_SIGNUP, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return tmSignupResponse;
	}

	private Response sendDataToIntuition(TransactionMonitoringSignupRequest request,
			TransactionMonitoringAccountSignupResponse tmSignupResponse, Response response,
			String jsonAccountSignupRequest, Builder clientrequest) throws TransactionMonitoringException {
		try {
			response = clientrequest.post(Entity.entity(jsonAccountSignupRequest, MediaType.APPLICATION_JSON));
		} catch (Exception e) {
			LOG.error("Error in  IntuitionPort response internal catch: ", e);
			saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
			LOG.warn("\n ------------ Intuition Entry add in MQ internal catch -------------");
			tmSignupResponse.setStatus(Constants.SERVICE_FAILURE);
		}
		return response;
	}

	/**
	 * Handle transaction monitoring server tansaction response.
	 *
	 * @param response the response
	 * @param request the request
	 * @param jsonAccountSignupRequest the json account signup request
	 * @return the transaction monitoring signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringAccountSignupResponse handleTransactionMonitoringServerTransactionResponse(Response response,
			TransactionMonitoringSignupRequest request, String jsonAccountSignupRequest) throws TransactionMonitoringException {
		TransactionMonitoringAccountSignupResponse tmSignupResponse = new TransactionMonitoringAccountSignupResponse();
		TransactionMonitoringAccountProviderResponse tmAccountProviderResponse = new TransactionMonitoringAccountProviderResponse();
		
		if (null != response) {
			response.bufferEntity();
			tmSignupResponse.setHttpStatus(response.getStatus());
			tmSignupResponse.setErrorDesc(response.readEntity(String.class));
			if (response.getStatus() == 200) {
				tmAccountProviderResponse = response.readEntity(TransactionMonitoringAccountProviderResponse.class);
				tmSignupResponse.setAccountId(request.getTransactionMonitoringAccountRequest().getId());
				tmSignupResponse.setTransactionMonitoringAccountProviderResponse(tmAccountProviderResponse);
				tmAccountProviderResponse.setCorrelationId(response.getHeaderString(CORRELATIONID));
				tmSignupResponse.setStatus(Constants.PASS); 
				tmSignupResponse.setOsrID(request.getOsrId());
			}else if (response.getStatus() == 400) {
				tmAccountProviderResponse.setCorrelationId(response.getHeaderString(CORRELATIONID));
				tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
				tmSignupResponse.setErrorDescription("TransactionMonitoring Invalid Request For Sending to Intuition.");
				tmSignupResponse.setTransactionMonitoringAccountProviderResponse(tmAccountProviderResponse);
				LOG.error("TransactionMonitoring Invalid Request For Sending to Intuition.");
			} else if (response.getStatus() == 401) {
				LOG.warn("\n Intuition 401 Error :  {}", response.getStatus());
				tmSignupResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 403) {
				tmSignupResponse.setErrorDescription(TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
			} else if (response.getStatus() == 404) {
				tmSignupResponse.setErrorDescription(TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
			} else if (response.getStatus() == 500) {
				tmSignupResponse.setErrorDescription(TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 503) {
				tmSignupResponse.setErrorDescription(TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
			} else {
				tmSignupResponse.setErrorDescription(Constants.SERVICE_FAILURE);
				tmSignupResponse.setStatus(Constants.SERVICE_FAILURE);
				tmAccountProviderResponse.setStatus(Constants.SERVICE_FAILURE);
				saveIntoTransactionMonitoringMQAccount(request, jsonAccountSignupRequest, request.getRequestType());
				LOG.error("{}", Constants.SERVICE_FAILURE);
			}
		} else {
			LOG.warn("\n Intuition Else Part Error :  {}", response);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.FAILED);
		}
		return tmSignupResponse;
	}

	/**
	 * Save into transaction monitoring MQ account.
	 *
	 * @param request the request
	 * @param jsonAccountSignupRequest the json account signup request
	 * @param requestType the request type
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private void saveIntoTransactionMonitoringMQAccount(TransactionMonitoringSignupRequest request,
			String jsonAccountSignupRequest, String requestType) throws TransactionMonitoringException {
		if(request.getIsPresent() == 0) {
			int conId = request.getTransactionMonitoringContactRequests().isEmpty() ? 0:request.getTransactionMonitoringContactRequests().get(0).getId();
			dbServiceImpl.saveIntoTransactionMonitoringMQ(request.getTransactionMonitoringAccountRequest().getId(),
					null, jsonAccountSignupRequest, requestType, request.getOrgCode(), request.getCreatedBy(), conId);
		}
	}

	/**
	 * Perfrom intuition for funds in check.
	 *
	 * @param request the request
	 * @param fundsInProviderProperty the funds in provider property
	 * @return the transaction monitoring payment in response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringPaymentInResponse perfromIntuitionForFundsInCheck(
			TransactionMonitoringPaymentsInRequest request, TransactionMonitoringProviderProperty fundsInProviderProperty)
			throws TransactionMonitoringException {
		TransactionMonitoringPaymentInResponse transactionMonitoringResponse = new TransactionMonitoringPaymentInResponse();
		IntuitionFundsProviderRequest intutFundsInProviderRequest = null;

		Response response = null;
		ResteasyClient client = null;

		try {

			LOG.debug("IntuitionPort STARTED : perfromIntuitionForFundsInCheck() ");
			/*client = resteasyClientHandler.getRetEasyClient();

			intutFundsInProviderRequest = intuitionTransformer.transformPaymentInRequest(request);

			String jsonFundsInRequest = JsonConverterUtil
					.convertToJsonWithNull(intutFundsInProviderRequest);
			LOG.warn("\n -------Intuition Funds In Request Start -------- \n  {}", jsonFundsInRequest);
			LOG.warn(" \n -----------Intuition Funds In Request End ---------");

			ResteasyWebTarget target1 = client.target(fundsInProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					BASIC + " " + fundsInProviderProperty.getAuthorization());
			response = clientrequest.post(Entity.entity(jsonFundsInRequest, MediaType.APPLICATION_JSON));
			LOG.warn("\n -------Intuition Funds In Response Start -------- \n  {}", response);
			transactionMonitoringResponse.setHttpStatus(response.getStatus());
			transactionMonitoringResponse = handleTransactionMonitoringServerTansactionPaymentInResponse(response, jsonFundsInRequest, request);
			
			String jsonFundsInResponse = JsonConverterUtil.convertToJsonWithNull(transactionMonitoringResponse);*/
			
			String json ="{\"status\":\"PASS\",\"correlationId\":\"dbb0fd51f8bc8c1823704772da1635dc\","
					+ "\"transactionMonitoringFundsProviderResponse\":{\"correlationId\":null,\"id\":"
					+ "\"080801015832043-003601512_622756\",\"RulesTriggered\":[\"AF7X-AL2A-HRC003\","
					+ "\"FirstPymtTP\",\"PF6X-MM1F-SDC011\"],\"Status\":\"Hold\",\"RuleScore\":70,"
					+ "\"RuleRiskLevel\":\"High\",\"ClientRiskScore\":23,\"ClientRiskLevel\":\"Low\","
					+ "\"UserId\":null,\"Action\":null},\"httpStatus\":200,\"errorDesc\":"
					+ "\"{\\\"ClientRiskLevel\\\":\\\"Low\\\",\\\"id\\\":\\\"080801015832043-003601512_622756\\\","
					+ "\\\"Status\\\":\\\"Hold\\\",\\\"Action\\\":null,\\\"UserId\\\":null,\\\"RuleRiskLevel\\\":"
					+ "\\\"High\\\",\\\"ClientRiskScore\\\":23,\\\"RulesTriggered\\\":[\\\"AF7X-AL2A-HRC003\\\","
					+ "\\\"FirstPymtTP\\\",\\\"PF6X-MM1F-SDC011\\\"],\\\"RuleScore\\\":70}\"}";
			ObjectMapper objectMapper = new ObjectMapper();
	        
			transactionMonitoringResponse = objectMapper.readValue(json, TransactionMonitoringPaymentInResponse.class);
	       
	        String jsonAccounRequest = JsonConverterUtil.convertToJsonWithNull(transactionMonitoringResponse);
	        LOG.warn("\n -------Intuition Account Request Start -------- \n  {}", jsonAccounRequest);
	        LOG.warn(" \n -----------Intuition Account Request End ---------");

			/*
			 * } catch (TransactionMonitoringException e) {
			 * LOG.error("Error in  IntuitionPort : perfromIntuitionForFundsInCheck() ", e);
			 */
		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_PAYMENTIN, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return transactionMonitoringResponse;
	}
	
	/**
	 * Perfrom intuition for funds out check.
	 *
	 * @param request the request
	 * @param fundsOutProviderProperty the funds out provider property
	 * @return the transaction monitoring payment out response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public TransactionMonitoringPaymentOutResponse perfromIntuitionForFundsOutCheck(
			TransactionMonitoringPaymentsOutRequest request, TransactionMonitoringProviderProperty fundsOutProviderProperty)
			throws TransactionMonitoringException {
		TransactionMonitoringPaymentOutResponse transactionMonitoringResponse = new TransactionMonitoringPaymentOutResponse();
		IntuitionFundsProviderRequest intuitionFundsOutProviderRequest = null;

		Response response = null;
		ResteasyClient client = null;

		try {

			/*client = resteasyClientHandler.getRetEasyClient();

			intuitionFundsOutProviderRequest = intuitionTransformer.transformPaymentOutRequest(request);

			String jsonFundsOutRequest = JsonConverterUtil
					.convertToJsonWithNull(intuitionFundsOutProviderRequest);
			LOG.warn("\n -------Intuition Funds Out Request Start -------- \n  {}", jsonFundsOutRequest);
			LOG.warn(" \n -----------Intuition Funds Out Request End ---------");

			ResteasyWebTarget target1 = client.target(fundsOutProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					BASIC + " " + fundsOutProviderProperty.getAuthorization());
			response = clientrequest.post(Entity.entity(jsonFundsOutRequest, MediaType.APPLICATION_JSON));
			LOG.warn("\n -------Intuition Funds Out Response Start -------- \n  {}", response);
			transactionMonitoringResponse.setHttpStatus(response.getStatus());
			transactionMonitoringResponse = handleTransactionMonitoringServerTansactionPaymentOutResponse(response, request, jsonFundsOutRequest);
			
			String jsonFundsOutResponse = JsonConverterUtil.convertToJsonWithNull(transactionMonitoringResponse);
			LOG.warn("\n -------Intuition Funds Out Response Start -------- \n  {}", jsonFundsOutResponse);
			LOG.warn(" \n -----------Intuition Funds Out Response End ---------");
		} catch (TransactionMonitoringException e) {
			LOG.error("Error in  IntuitionPort : perfromIntuitionForFundsInCheck() ", e); */
			LOG.debug("IntuitionPort STARTED : perfromIntuitionForFundsOutCheck() ");
			String json ="{\"status\":\"PASS\",\"correlationId\":\"dbb0fd51f8bc8c1823704772da1635dc\","
					+ "\"transactionMonitoringFundsProviderResponse\":{\"correlationId\":null,\"id\":"
					+ "\"080801015832043-003601512_622756\",\"RulesTriggered\":[\"AF7X-AL2A-HRC003\","
					+ "\"FirstPymtTP\",\"PF6X-MM1F-SDC011\"],\"Status\":\"Hold\",\"RuleScore\":70,"
					+ "\"RuleRiskLevel\":\"High\",\"ClientRiskScore\":23,\"ClientRiskLevel\":\"Low\","
					+ "\"UserId\":null,\"Action\":null},\"httpStatus\":200,\"errorDesc\":"
					+ "\"{\\\"ClientRiskLevel\\\":\\\"Low\\\",\\\"id\\\":\\\"080801015832043-003601512_622756\\\","
					+ "\\\"Status\\\":\\\"Hold\\\",\\\"Action\\\":null,\\\"UserId\\\":null,\\\"RuleRiskLevel\\\":"
					+ "\\\"High\\\",\\\"ClientRiskScore\\\":23,\\\"RulesTriggered\\\":[\\\"AF7X-AL2A-HRC003\\\","
					+ "\\\"FirstPymtTP\\\",\\\"PF6X-MM1F-SDC011\\\"],\\\"RuleScore\\\":70}\"}";
			ObjectMapper objectMapper = new ObjectMapper();
	        
			transactionMonitoringResponse = objectMapper.readValue(json, TransactionMonitoringPaymentOutResponse.class);
	       
	        String jsonAccounRequest = JsonConverterUtil.convertToJsonWithNull(transactionMonitoringResponse);
	        LOG.warn("\n -------Intuition Account Request Start -------- \n  {}", jsonAccounRequest);
	        LOG.warn(" \n -----------Intuition Account Request End ---------");
	        
		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_PAYMENTOUT, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return transactionMonitoringResponse;
	}
	
	/**
	 * Handle transaction monitoring server tansaction payment in response.
	 *
	 * @param response the response
	 * @return the transaction monitoring payment in response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringPaymentInResponse handleTransactionMonitoringServerTansactionPaymentInResponse(
			Response response, String jsonFundsInRequest, TransactionMonitoringPaymentsInRequest request) throws TransactionMonitoringException {
		TransactionMonitoringPaymentInResponse tmPaymentInResponse = new TransactionMonitoringPaymentInResponse();
		TransactionMonitoringFundsProviderResponse tmFundsProviderResponse = new TransactionMonitoringFundsProviderResponse();
		String tmResponse = null;
		
		if (null != response) {
			tmPaymentInResponse.setHttpStatus(response.getStatus());
			response.bufferEntity();
			tmPaymentInResponse.setErrorDesc(response.readEntity(String.class));
			if (response.getStatus() == 200) {
				tmFundsProviderResponse = response.readEntity(TransactionMonitoringFundsProviderResponse.class);
				tmResponse = response.getHeaderString(CORRELATIONID);
				tmPaymentInResponse.setCorrelationId(tmResponse);
				String tmStatus = tmFundsProviderResponse.getStatus();
				tmPaymentInResponse.setHttpStatus(response.getStatus());
				tmPaymentInResponse.setTransactionMonitoringFundsProviderResponse(tmFundsProviderResponse);
				setTransactionMonitoringPayINStatus(tmPaymentInResponse, tmStatus);
			} else if (response.getStatus() == 400) {
				tmPaymentInResponse.setCorrelationId(response.getHeaderString(CORRELATIONID));
				tmPaymentInResponse.setStatus(Constants.SERVICE_FAILURE);
				tmPaymentInResponse.setHttpStatus(response.getStatus());
				tmPaymentInResponse.setErrorDescription("TransactionMonitoring Invalid Payment Request For Sending to Intuition");
				LOG.error("TransactionMonitoring Invalid Payment Request For Sending to Intuition.");
			} else if (response.getStatus() == 401) {
				tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 403) {
				tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				LOG.error("{}", TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
			} else if (response.getStatus() == 404) {
				tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				LOG.error("{}",TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
			} else if (response.getStatus() == 500) {
				tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				LOG.error("{}", TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 503) {
				tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				LOG.error("{}", TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
			}else {
				tmPaymentInResponse.setErrorDescription("service failure");
				saveIntoTransactionMonitoringMQPaymentIn(jsonFundsInRequest, request);
				tmPaymentInResponse.setStatus(Constants.SERVICE_FAILURE);
				LOG.error("{}", Constants.SERVICE_FAILURE);
			}
		} else {
			tmPaymentInResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
		}
		return tmPaymentInResponse;
	}

	private void setTransactionMonitoringPayINStatus(TransactionMonitoringPaymentInResponse tmPaymentInResponse,
			String tmStatus) {
		//Update for AT-4923
		if (tmPaymentInResponse.getTransactionMonitoringFundsProviderResponse() != null
				&& tmPaymentInResponse.getTransactionMonitoringFundsProviderResponse().getAction() != null) {
			if (tmPaymentInResponse.getTransactionMonitoringFundsProviderResponse().getAction().equalsIgnoreCase(Constants.CLEAN) 
					|| tmPaymentInResponse.getTransactionMonitoringFundsProviderResponse().getAction().equalsIgnoreCase(Constants.CLEAR))
				tmPaymentInResponse.setStatus(Constants.PASS);
			else
				tmPaymentInResponse.setStatus(Constants.FAIL);
		} else {
			if (tmStatus.equalsIgnoreCase(Constants.CLEAN) || tmStatus.equalsIgnoreCase(Constants.CLEAR))
				tmPaymentInResponse.setStatus(Constants.PASS);
			else
				tmPaymentInResponse.setStatus(Constants.FAIL);
		}
	}
	
	/**
	 * @param jsonFundsInRequest
	 * @param request
	 * @param requestType
	 * @throws TransactionMonitoringException
	 */
	private void saveIntoTransactionMonitoringMQPaymentIn(String jsonFundsInRequest,
			TransactionMonitoringPaymentsInRequest request) throws TransactionMonitoringException {
		if(request.getIsPresent() == 0) {
			dbServiceImpl.saveIntoTransactionMonitoringMQ(request.getAccountId(),
					request.getFundsInId(),  jsonFundsInRequest, request.getRequestType(), request.getOrgCode(), request.getCreatedBy(), request.getContactId());
			}
	}
	
	/**
	 * Handle transaction monitoring server tansaction payment out response.
	 *
	 * @param response the response
	 * @return the transaction monitoring payment out response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private TransactionMonitoringPaymentOutResponse handleTransactionMonitoringServerTansactionPaymentOutResponse(
			Response response, TransactionMonitoringPaymentsOutRequest request, String jsonFundsOutRequest) throws TransactionMonitoringException {
		TransactionMonitoringPaymentOutResponse tmPaymentOutResponse = new TransactionMonitoringPaymentOutResponse();
		String tmResponse = null;

		if (null != response) {
			tmPaymentOutResponse.setHttpStatus(response.getStatus());
			response.bufferEntity();
			tmPaymentOutResponse.setErrorDesc(response.readEntity(String.class));
			if (response.getStatus() == 200) {
				TransactionMonitoringFundsProviderResponse tmFundsProviderResponse = response.readEntity(TransactionMonitoringFundsProviderResponse.class);
				tmResponse = response.getHeaderString(CORRELATIONID);
				tmPaymentOutResponse.setCorrelationId(tmResponse);
				String tmStatus = tmFundsProviderResponse.getStatus();
				tmPaymentOutResponse.setHttpStatus(response.getStatus());
				tmPaymentOutResponse.setTransactionMonitoringFundsProviderResponse(tmFundsProviderResponse);
				setTransactionMonitoringPayOutStatus(tmPaymentOutResponse, tmStatus);
			} else if (response.getStatus() == 400) {
				tmPaymentOutResponse.setCorrelationId(response.getHeaderString(CORRELATIONID));
				tmPaymentOutResponse.setStatus(Constants.SERVICE_FAILURE);
				tmPaymentOutResponse.setHttpStatus(response.getStatus());
				tmPaymentOutResponse.setErrorDescription("TransactionMonitoring Invalid Payment-out Request For Sending to Intuition");
				LOG.error("TransactionMonitoring Invalid Payment-out Request For Sending to Intuition.");
			} else if (response.getStatus() == 401) {
				tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 403) {
				tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				LOG.error("{}", TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
			} else if (response.getStatus() == 404) {
				tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				LOG.error("{}", TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
			} else if (response.getStatus() == 500) {
				tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				LOG.error("{}", TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 503) {
				tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				LOG.error("{}", TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
			}else {
				tmPaymentOutResponse.setErrorDescription("service failure");
				saveIntoTransactionMonitoringMQPaymentOut(request, jsonFundsOutRequest);
				tmPaymentOutResponse.setStatus(Constants.SERVICE_FAILURE);
				LOG.error("{}", Constants.SERVICE_FAILURE);
			}
		} else {
			tmPaymentOutResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
		}
		return tmPaymentOutResponse;
	}
	
	private void setTransactionMonitoringPayOutStatus(TransactionMonitoringPaymentOutResponse tmPaymentOutResponse,
			String tmStatus) {
		//Updated for AT-4923
		if (tmPaymentOutResponse.getTransactionMonitoringFundsProviderResponse() != null
				&& tmPaymentOutResponse.getTransactionMonitoringFundsProviderResponse().getAction() != null) {
			if (tmPaymentOutResponse.getTransactionMonitoringFundsProviderResponse().getAction().equalsIgnoreCase(Constants.CLEAN)
					|| tmPaymentOutResponse.getTransactionMonitoringFundsProviderResponse().getAction().equalsIgnoreCase(Constants.CLEAR))
				tmPaymentOutResponse.setStatus(Constants.PASS);
			else
				tmPaymentOutResponse.setStatus(Constants.FAIL);
		} else {
			if (tmStatus.equalsIgnoreCase(Constants.CLEAN) || tmStatus.equalsIgnoreCase(Constants.CLEAR))
				tmPaymentOutResponse.setStatus(Constants.PASS);
			else
				tmPaymentOutResponse.setStatus(Constants.FAIL);
		}
	}

	/**
	 * @param request
	 * @param jsonFundsOutRequest
	 * @param requestType
	 * @throws TransactionMonitoringException
	 */
	private void saveIntoTransactionMonitoringMQPaymentOut(TransactionMonitoringPaymentsOutRequest request,
			String jsonFundsOutRequest) throws TransactionMonitoringException {
		if(request.getIsPresent() == 0) {
			dbServiceImpl.saveIntoTransactionMonitoringMQ(request.getAccountId(),
					request.getFundsOutId(),  jsonFundsOutRequest, request.getRequestType(), request.getOrgCode(), request.getCreatedBy(), request.getContactId());
			}
	}
	
	/**
	 * Perfrom intuition update account check.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring account signup response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringAccountSignupResponse perfromIntuitionUpdateAndAddContactCheck(
			TransactionMonitoringSignupRequest request, TransactionMonitoringProviderProperty accountProviderProperty)
			throws TransactionMonitoringException {

		TransactionMonitoringAccountSignupResponse tmUpdateResponse = new TransactionMonitoringAccountSignupResponse();
		IntuitionSignupProviderRequest intuitionUpdateProviderRequest = null;
		
		Response response = null;
		ResteasyClient client = null;
		String operation = null;

		try {
			if(request.getRequestType().equalsIgnoreCase(UPDATE)) {
				operation = "Update";
			} else {
				operation = "Add Contact";
			}

			LOG.debug("IntuitionPort STARTED : perfromIntuitionUpdateAndAddContactCheck() ");
			client = resteasyClientHandler.getRetEasyClient();

			intuitionUpdateProviderRequest = intuitionTransformer.transformNewSignupAccountRequest(request);

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String jsonUpdateRequest = mapper.writeValueAsString(intuitionUpdateProviderRequest);

			LOG.warn("\n -------Intuition {} Request Start -------- \n {}", operation, jsonUpdateRequest);
			LOG.warn(" \n -----------Intuition {} Request End ---------", operation);

			ResteasyWebTarget target1 = client.target(accountProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					BASIC + " " + accountProviderProperty.getAuthorization());
			response = clientrequest.method("PATCH", Entity.entity(jsonUpdateRequest, MediaType.APPLICATION_JSON));
			tmUpdateResponse = handleTransactionMonitoringServerTransactionResponse(response, request, jsonUpdateRequest);
			
			String jsonAccountSignupResponse = JsonConverterUtil.convertToJsonWithNull(tmUpdateResponse);
			LOG.warn("\n -------Intuition {} Response Start -------- \n  {}", operation, jsonAccountSignupResponse);
			LOG.warn(" \n -----------Intuition {} Response End ---------",operation);

		} catch (TransactionMonitoringException e) {
			LOG.error("Error in  IntuitionPort : perfromIntuitionUpdateAndAddContactCheck() ", e);
		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_SIGNUP, e);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return tmUpdateResponse;
	}
	
	//AT-4896
	/**
	 * Perform intuition post card transaction check.
	 *
	 * @param request the request
	 * @param accountProviderProperty the account provider property
	 * @return the transaction monitoring post card transaction response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringPostCardTransactionResponse performIntuitionPostCardTransactionCheck(
			TransactionMonitoringPostCardTransactionRequest request,
			TransactionMonitoringProviderProperty accountProviderProperty) throws TransactionMonitoringException {
		TransactionMonitoringPostCardTransactionResponse tmResponse = new TransactionMonitoringPostCardTransactionResponse();
		IntuitionPostCardRequest intuitionRequest = null;
		Response response = null;
		ResteasyClient client = null;
		String jsonCardRequest = null;

		try {
			
			if (PostCardTransactionRequestTypeEnum.CX_REVERSAL.getCardRequestTypeAsString()
					.equalsIgnoreCase(request.getRequestType())) {
				intuitionRequest = intuitionTransformer.transformPostCardTransactionPatchRequest(request);
				jsonCardRequest = JsonConverterUtil.convertToJsonWithoutNull(intuitionRequest);
			}
			else {
				intuitionRequest = intuitionTransformer.transformPostCardTransactionPostRequest(request);
				jsonCardRequest = JsonConverterUtil.convertToJsonWithNull(intuitionRequest);
			}

			LOG.warn("\n -------Intuition Post Card Transaction Request Start -------- \n {}", jsonCardRequest);
			LOG.warn(" \n -----------Intuition Post Card Transaction Request End ---------");

			client = resteasyClientHandler.getRetEasyClient();
			ResteasyWebTarget target1 = client.target(accountProviderProperty.getEndPointUrl());
			Builder clientrequest = target1.request().header(HttpHeaders.AUTHORIZATION,
					BASIC + " " + accountProviderProperty.getAuthorization());
			
			if (PostCardTransactionRequestTypeEnum.CX_REVERSAL.getCardRequestTypeAsString()
					.equalsIgnoreCase(request.getRequestType()))
				response = clientrequest.method("PATCH", Entity.entity(jsonCardRequest, MediaType.APPLICATION_JSON));
			else
				response = clientrequest.post(Entity.entity(jsonCardRequest, MediaType.APPLICATION_JSON));

			handlePostCardTransactionMonitoringServerResponse(response, request, jsonCardRequest, tmResponse);

		} catch (Exception e) {
			LOG.error("Error in  IntuitionPort : performIntuitionPostCardTransactionCheck() ", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return tmResponse;
	}

	/**
	 * Handle post card transaction monitoring server response.
	 *
	 * @param response        the response
	 * @param request         the request
	 * @param jsonCardRequest the json card request
	 * @return the transaction monitoring post card transaction response
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private void handlePostCardTransactionMonitoringServerResponse(Response response,
			TransactionMonitoringPostCardTransactionRequest request, String jsonCardRequest,
			TransactionMonitoringPostCardTransactionResponse tmCardResponse) throws TransactionMonitoringException {

		if (null != response) {
			tmCardResponse.setHttpStatus(response.getStatus());
			if (response.getStatus() == 200) {
				tmCardResponse.setStatus(Constants.PASS);
				LOG.info(
						"Request sent successfully to Intuition for Post Card Transaction for InstructionNumber : {} and TransactionID : {}",
						request.getTitanAccountNumber(), request.getTrxID());
			} else if (response.getStatus() == 400) {
				tmCardResponse.setErrorDescription("PostCardTransactionMonitoring Invalid Request For Sending to Intuition.");
				saveIntoPostCardTransactionMonitoringFailedRequest(request, jsonCardRequest);
				LOG.error("PostCardTransactionMonitoring Invalid Request For Sending to Intuition.");
			} else if (response.getStatus() == 401) {
				LOG.warn("\n Intuition 401 Error :  {}", response.getStatus());
				tmCardResponse.setErrorDescription(TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", TransactionMonitoringErrors.UNAUTHORIZED_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 403) {
				tmCardResponse.setErrorDescription(TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", TransactionMonitoringErrors.ACCESS_DENIED.getErrorDescription());
			} else if (response.getStatus() == 404) {
				tmCardResponse.setErrorDescription(TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", TransactionMonitoringErrors.ENTITY_NOT_FOUND.getErrorDescription());
			} else if (response.getStatus() == 500) {
				tmCardResponse.setErrorDescription(TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", TransactionMonitoringErrors.TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION.getErrorDescription());
			} else if (response.getStatus() == 503) {
				tmCardResponse.setErrorDescription(TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", TransactionMonitoringErrors.SERVICE_UNAVAILABLE.getErrorDescription());
			} else {
				tmCardResponse.setErrorDescription(Constants.SERVICE_FAILURE);
				tmCardResponse.setStatus(Constants.SERVICE_FAILURE);
				saveIntoPostCardTransactionMonitoringMQ(request, jsonCardRequest);
				LOG.error("{}", Constants.SERVICE_FAILURE);
			}
		} else {
			LOG.warn("\n Intuition Post Card Transaction Else Part Error :  {}", response);
			LOG.error("{}", Constants.SERVICE_FAILURE);
		}
	}

	/**
	 * Save into post card transaction monitoring MQ.
	 *
	 * @param request         the request
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException
	 */
	private void saveIntoPostCardTransactionMonitoringMQ(TransactionMonitoringPostCardTransactionRequest request,
			String jsonCardRequest) throws TransactionMonitoringException {
		if (request.getIsPresent() == 0) {
			dbServiceImpl.saveIntoPostCardTransactionMonitorigMQ(request.getTrxID(), jsonCardRequest, request.getRequestType());
		}

	}

	/**
	 * Save into post card transaction monitoring failed request.
	 *
	 * @param request         the request
	 * @param jsonCardRequest the json card request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	private void saveIntoPostCardTransactionMonitoringFailedRequest(
			TransactionMonitoringPostCardTransactionRequest request, String jsonCardRequest)
			throws TransactionMonitoringException {
		dbServiceImpl.saveIntoPostCardTransactionMonitoringFailedRequest(request.getTrxID(), jsonCardRequest);

	}
}
