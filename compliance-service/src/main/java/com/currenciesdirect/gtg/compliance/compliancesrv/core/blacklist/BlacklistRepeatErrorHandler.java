package com.currenciesdirect.gtg.compliance.compliancesrv.core.blacklist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class BlacklistRepeatErrorHandler.
 */
public class BlacklistRepeatErrorHandler {

	/**
	 * Log error.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {
		BlacklistResendResponse response = createResponse();
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
	 * @return the blacklist resend response
	 */
	private BlacklistResendResponse createResponse() {
		BlacklistResendResponse response = new BlacklistResendResponse();
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonCode());
		response.setResponseDescription(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonDescription());
		BlacklistSummary summary = new BlacklistSummary();
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		
		response.setSummary(summary);
		return response;
	}

}


