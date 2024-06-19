package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class PrepareSanctionUpdateResponse {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PrepareSanctionUpdateResponse.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION).equals( OperationEnum.SANCTION_UPDATE.name())) {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			SanctionUpdateResponse response=exchange.getResponse(SanctionUpdateResponse.class);
			if(BaseResponse.DECISION.FAIL==response.getDecision()
					&& InternalProcessingCode.INV_REQUEST.name().equals(response.getErrorCode())){
			DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
					.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

			deferredResult.setResult(responseEntity);
			}else{
				DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
						.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

				ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

				deferredResult.setResult(responseEntity);
			}
			return message;
		} else {
			LOGGER.warn("SanctionResendResponse Response : NO RESPONSE ");
			message.getPayload().setFailed(true);
		}
		return message;
	}
}
