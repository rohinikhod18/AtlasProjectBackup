package com.currenciesdirect.gtg.compliance.compliancesrv.core.kyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

/**
 * The Class PrepareKycResendResponse.
 */
public class PrepareKycResendResponse {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PrepareKycResendResponse.class);

	/**
	 * Process.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION).equals(OperationEnum.KYC_RESEND.name())) {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			KYCResendResponse kycResendRes = exchange.getResponse(KYCResendResponse.class);

			if (BaseResponse.DECISION.FAIL==kycResendRes.getDecision()
					&& InternalProcessingCode.INV_REQUEST.name().equals(kycResendRes.getErrorCode())) {

				DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
						.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

				ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(kycResendRes,
						HttpStatus.BAD_REQUEST);

				deferredResult.setResult(responseEntity);
			} else {
				DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
						.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

				ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(kycResendRes,
						HttpStatus.OK);

				deferredResult.setResult(responseEntity);
			}

			return message;
		} else {
			LOGGER.warn("KycResendResponse Response : NO RESPONSE ");
			message.getPayload().setFailed(true);
		}
		return message;
	}
}
