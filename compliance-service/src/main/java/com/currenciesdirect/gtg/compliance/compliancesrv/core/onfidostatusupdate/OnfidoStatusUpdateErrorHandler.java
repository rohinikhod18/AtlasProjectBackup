package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public class OnfidoStatusUpdateErrorHandler {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(OnfidoStatusUpdateErrorHandler.class);

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> logError(Message<MessageContext> message) {

		MessageContext context = message.getPayload();
		OnfidoUpdateRequest request = context.getGatewayMessageExchange().getRequest(OnfidoUpdateRequest.class);
		LOG.error("Error in OnfidoStatusUpdateErrorHandler logError() method for AccountId : {}", request.getAccountId());
		OnfidoUpdateResponse response = createResponse(message);
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		return message;
	}

	/**
	 * Creates the response.
	 *
	 * @param message the message
	 * @return the onfido update response
	 */
	private OnfidoUpdateResponse createResponse(Message<MessageContext> message) {
		MessageContext context = message.getPayload();
		OnfidoUpdateResponse response = new OnfidoUpdateResponse();
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(FundsInReasonCode.SYSTEM_FAILURE.getReasonCode());
		response.setResponseDescription(FundsInReasonCode.SYSTEM_FAILURE.getReasonDescription());
		context.getGatewayMessageExchange().setResponse(response);
		return response;
	}

}
