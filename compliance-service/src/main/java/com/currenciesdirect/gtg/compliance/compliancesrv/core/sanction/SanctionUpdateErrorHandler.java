package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class SanctionUpdateErrorHandler.
 */
public class SanctionUpdateErrorHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SanctionUpdateErrorHandler.class);

	/**
	 * Log error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {

		MessageContext context = message.getPayload();
		SanctionUpdateRequest request = context.getGatewayMessageExchange().getRequest(SanctionUpdateRequest.class);
		LOG.error("Error in SanctionUpdateErrorHandler logError() method for AccountId : {}", request.getAccountId());
		SanctionUpdateResponse response = createResponse(message);
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
	 * @param message
	 *            the message
	 * @return the sanction update response
	 */
	private SanctionUpdateResponse createResponse(Message<MessageContext> message) {
		MessageContext context = message.getPayload();
		SanctionUpdateResponse response = new SanctionUpdateResponse();
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(FundsInReasonCode.SYSTEM_FAILURE.getReasonCode());
		response.setResponseDescription(FundsInReasonCode.SYSTEM_FAILURE.getReasonDescription());
		context.getGatewayMessageExchange().setResponse(response);
		return response;
	}

}
