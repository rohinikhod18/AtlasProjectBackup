package com.currenciesdirect.gtg.compliance.compliancesrv.core.fraugster;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class SanctionRepeatErrorHandler.
 */
public class FraugsterRepeatErrorHandler {

	/**
	 * Log error.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {
		FraugsterResendResponse response = createResponse();
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
	 * @return the fraugster resend response
	 */
	private FraugsterResendResponse createResponse() {
		FraugsterResendResponse response = new FraugsterResendResponse();
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonCode());
		response.setResponseDescription(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonDescription());
		FraugsterSummary summary = new FraugsterSummary();
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		
		response.setSummary(summary);
		return response;
	}

}
