package com.currenciesdirect.gtg.compliance.compliancesrv.core.titan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class ResponseHandler {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		FundsInCreateResponse response = (FundsInCreateResponse) messageExchange.getResponse();
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		String jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
		LOGGER.warn("Response: {}", jsonResponseEntity);
		return message;
	}

}
