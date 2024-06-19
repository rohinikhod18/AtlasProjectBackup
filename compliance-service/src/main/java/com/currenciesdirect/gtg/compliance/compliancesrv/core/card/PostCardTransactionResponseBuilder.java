package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.PostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class PostCardTransactionResponseBuilder {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PostCardTransactionResponseBuilder.class);
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		String operation = String.valueOf(message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION));
		LOG.debug("Build response for operation : {}", operation);
		
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		PostCardTransactionResponse response = (PostCardTransactionResponse) exchange.getResponse();
		
		if (BaseResponse.DECISION.FAIL == response.getDecision()
				&& InternalProcessingCode.INV_REQUEST.name().equals(response.getErrorCode())) {

			DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
					.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			ResponseEntity<ServiceMessageResponse> responseEntity =
					new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

			deferredResult.setResult(responseEntity);
		} else {
			DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
					.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			ResponseEntity<ServiceMessageResponse> responseEntity =
					new ResponseEntity<>(response, HttpStatus.OK);

			deferredResult.setResult(responseEntity);
		}
		
		return message;
	}
}
