package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class SanctionRepeatErrorHandler.
 */
public class SanctionRepeatErrorHandler {

	public Message<MessageContext> logError(Message<MessageContext> message) {
		SanctionResendResponse response = createResponse();
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		return message;
	}

	private SanctionResendResponse createResponse() {
		SanctionResendResponse response = new SanctionResendResponse();
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonCode());
		response.setResponseDescription(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonDescription());
		SanctionSummary summary = new SanctionSummary();
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setResponseCode(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonCode());
		summary.setResponseDescription(FundsOutReasonCode.SYSTEM_FAILURE.getFundsOutReasonDescription());
		response.setSummary(summary);
		return response;
	}

}
