package com.currenciesdirect.gtg.compliance.compliancesrv.core.kyc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class SanctionRepeatErrorHandler.
 */
public class KycRepeatErrorHandler {

	public Message<MessageContext> logError(Message<MessageContext> message) {
		KYCResendResponse response = createResponse();
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		return message;
	}

	private KYCResendResponse createResponse() {
		KYCResendResponse response = new KYCResendResponse();
		response.setDecision(DECISION.FAIL);
		response.setResponseCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
		response.setResponseDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
		KYCSummary summary = new KYCSummary();
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setResponseCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
		summary.setResponseDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
		response.setSummary(summary);
		return response;
	}

}
