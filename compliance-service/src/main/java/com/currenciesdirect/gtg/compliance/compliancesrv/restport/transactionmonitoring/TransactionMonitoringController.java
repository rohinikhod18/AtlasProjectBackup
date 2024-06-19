package com.currenciesdirect.gtg.compliance.compliancesrv.restport.transactionmonitoring;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountMQRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringUpdatePaymentStatusRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringUpdateRegStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.MQBulkReprocessResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/intuition")
public class TransactionMonitoringController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMonitoringController.class);
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Update intuition status from registration detail page - When registration status is updated in Atlas same should be synchronized with Intuition", 
			value = "Call this api to update registration status on click", nickname = "updateStatus")
	@ApiResponses(value = {
			@ApiResponse(response = TransactionMonitoringSignupResponse.class, code = 200, message = "A successful response is an instance of the TransactionMonitoringSignupResponse class.") })
	@PostMapping(value = "/updateRegStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateRegStatus(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringUpdateRegStatusRequest updateRegStatusRequest) {
		
		String jsonUpdateRegStatusRequest = JsonConverterUtil.convertToJsonWithNull(updateRegStatusRequest);// Added for AT-5230
		LOGGER.warn("\n---- Intuition update status request received {} ----\n", jsonUpdateRegStatusRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.INTUITION, OperationEnum.UPDATE_ACCOUNT, updateRegStatusRequest.getOrgCode(),updateRegStatusRequest);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
	/**
	 * Update repeat check status.
	 *
	 * @param httpRequest the http request
	 * @param updateRegStatusRequest the update reg status request
	 * @return the deferred result
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Update intuition status from registration detail page - When registration status is updated in Atlas same should be synchronized with Intuition", 
			value = "Call this api to update registration status on click", nickname = "updateRepeatCheckStatus")
	@ApiResponses(value = {
			@ApiResponse(response = TransactionMonitoringSignupResponse.class, code = 200, message = "A successful response is an instance of the TransactionMonitoringSignupResponse class.") })
	@PostMapping(value = "/updateRepeatCheckStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateRepeatCheckStatus(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringUpdateRegStatusRequest updateRegStatusRequest) {
		LOGGER.debug("Intuition update repeat check status request received {}", updateRegStatusRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.REPEAT_CHECK_STATUS_UPDATE, updateRegStatusRequest.getOrgCode(),updateRegStatusRequest);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
	//AT-4415
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Update intuition status from payment in detail page - When payment in status is updated in Atlas same should be synchronized with Intuition", 
			value = "Call this api to update payment in status on click", nickname = "updateFundsInRepeatCheckStatus")
	@ApiResponses(value = {
			@ApiResponse(response = TransactionMonitoringPaymentInResponse.class, code = 200, message = "A successful response is an instance of the TransactionMonitoringSignupResponse class.") })
	@PostMapping(value = "/updateFundsInRepeatCheckStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateFundsInRepeatCheckStatus(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringUpdatePaymentStatusRequest updatePaymentInStatusRequest) {
		LOGGER.debug("Intuition payment in update repeat check status request received {}", updatePaymentInStatusRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.REPEAT_CHECK_STATUS_UPDATE, updatePaymentInStatusRequest.getOrgCode(),updatePaymentInStatusRequest);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
	//AT-4451
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Update intuition status from payment out detail page - When payment out status is updated in Atlas same should be synchronized with Intuition", 
			value = "Call this api to update payment out status on click", nickname = "updateFundsOutRepeatCheckStatus")
	@ApiResponses(value = {
			@ApiResponse(response = TransactionMonitoringPaymentOutResponse.class, code = 200, message = "A successful response is an instance of the TransactionMonitoringSignupResponse class.") })
	@PostMapping(value = "/updateFundsOutRepeatCheckStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateFundsOutRepeatCheckStatus(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringUpdatePaymentStatusRequest updatePaymentOutStatusRequest) {
		LOGGER.debug("Intuition payment out update repeat check status request received {}", updatePaymentOutStatusRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.REPEAT_CHECK_STATUS_UPDATE, updatePaymentOutStatusRequest.getOrgCode(),updatePaymentOutStatusRequest);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
	/**
	 * Tmmq bulk reprocess.
	 *
	 * @param httpRequest the http request
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @return the deferred result
	 */
	//AT-4699
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Bulk reporcess Transaction Monitoring MQ Request", 
			value = "Call this api to bulk reprocess tm mq", nickname = "tmmqBulkReprocess")
	@ApiResponses(value = {
			@ApiResponse(response = MQBulkReprocessResponse.class, code = 200, message = "A successful response is an instance of the MQBulkReprocessResponse class.") })
	@PostMapping(value = "/tmmqBulkReprocess", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> tmmqBulkReprocess(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringMQRequest transactionMonitoringMQRequest) {
		LOGGER.debug("Bulk reporcess Transaction Monitoring MQ Request received {}", transactionMonitoringMQRequest);

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.INTUITION, OperationEnum.TRANSACTION_MONITORING_MQ_RESEND,
				transactionMonitoringMQRequest.getOrgCode(), transactionMonitoringMQRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername("cd.comp.system");

		return super.executeWorkflow(messageContext, userToken);
	}
	
	/**
	 * Tm payment status update.
	 *
	 * @param httpRequest the http request
	 * @param transactionMonitoringPaymentResponse the transaction monitoring payment response
	 * @return the deferred result
	 */
	// AT-4748
	@ApiOperation(notes = "Called when Intuition sends a Request to update payment status", value = "Called when Intuition sends a Request to update payment status", nickname = "Intuition-Payment-Update")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add basic token", defaultValue = "Basic <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(response = HttpStatus.class, code = 200, message = "A successful response is an instance of the Http class.") })
	@PostMapping(value = "/atlasPaymentStatusUpdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> tmPaymentStatusUpdate(final HttpServletRequest httpRequest,
			@ApiParam(value = "Intuition Payment Response", required = true) final @RequestBody IntuitionPaymentStatusUpdateRequest intuitionPaymentStatusUpdate,
			@RequestHeader("Authorization") String authenticate, @RequestHeader("correlation-id") String correlationId) {

		if (!intuitionPaymentStatusUpdate.isAunthenticated(authenticate)) {
			DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();
			ResponseEntity<Default> responseEntity = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

			deferredResult.setResult(responseEntity);

			return deferredResult;
		}
		
		intuitionPaymentStatusUpdate.setCorrelationID(UUID.fromString(correlationId));
		
		String jsonPaymentRequest = JsonConverterUtil.convertToJsonWithoutNull(intuitionPaymentStatusUpdate);
		LOGGER.warn("\n -------Intuition Update Payment Request Start -------- \n  {}", jsonPaymentRequest);
		LOGGER.warn(" \n -----------Intuition Update Payment Request End ---------");

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.INTUITION, OperationEnum.STATUS_UPDATE, null, intuitionPaymentStatusUpdate);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername("cd.comp.system");

		return super.executeWorkflow(messageContext, userToken);
	}
	
	/**
	 * Save registartion into MQ.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @return the deferred result
	 */
	//AT-4773
	@SuppressWarnings("rawtypes")
	@ApiOperation(notes = "Sync Registration record with Intuition", 
			value = "Call this api to sync registration record with intuition", nickname = "syncRegWithIntuition")
	@ApiResponses(value = {
			@ApiResponse(response = BaseResponse.class, code = 200, message = "A successful response is an instance of the BaseResponse class.") })
	@PostMapping(value = "/syncRegWithIntuition", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> saveRegistartionIntoMQ(final HttpServletRequest httpRequest,
			final @RequestBody TransactionMonitoringAccountMQRequest request) {
		LOGGER.info("Intuition Registration Request save to MQ {}", request);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.INTUITION, OperationEnum.UPDATE_OPI, null,request);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
}
