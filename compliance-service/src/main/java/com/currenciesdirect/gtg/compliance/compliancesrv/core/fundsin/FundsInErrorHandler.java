/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class FundsInErrorHandler.
 *
 * @author laxmib
 */
public class FundsInErrorHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsInErrorHandler.class);

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {
		MessageContext context = message.getPayload();
		FundsInCreateRequest request = context.getGatewayMessageExchange().getRequest(FundsInCreateRequest.class);
		LOG.error("Error in FundsInErrorHandler logError() method for AccountId : {}, ContactId : {} ", request.getAccId(), request.getContactId());
		FundsInCreateResponse response = createResponse(message);
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		LOG.debug("logError-->Response : {}", responseEntity);
		return message;
	}

	/**
	 * Creates the response.
	 *
	 * @param message
	 *            the message
	 * @return the funds in create response
	 */
	private FundsInCreateResponse createResponse(Message<MessageContext> message) {
		MessageContext context = message.getPayload();
		FundsInCreateRequest request = context.getGatewayMessageExchange().getRequest(FundsInCreateRequest.class);
		FundsInCreateResponse response = new FundsInCreateResponse();
		response.setOrgCode(request.getOrgCode());
		response.setOsrID(request.getOsrId());
		response.setStatus(FundsInComplianceStatus.HOLD.name());
		response.setDecision(DECISION.FAIL);
		response.setResponseReason(FundsInReasonCode.SYSTEM_FAILURE);
		response.setTradeContractNumber(request.getTrade().getContractNumber());
		response.setTradeAccountNumber(request.getTradeAccountNumber());
		response.setTradePaymentID(request.getPaymentFundsInId());
		context.getGatewayMessageExchange().setResponse(response);
		return response;
	}

}