package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class DataAnonReplyHandler {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataAnonReplyHandler.class);
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		String jsonResponseEntity;
		String baseResponseEntity;
		@SuppressWarnings("unchecked")
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		DataAnonResponseForES response = messageExchange.getResponse(DataAnonResponseForES.class);
		BaseResponse baseResponse = (BaseResponse) messageExchange.getResponse().getAdditionalAttribute(Constants.BASE_RESPONSE);
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
		baseResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(baseResponse);
		LOGGER.warn("\nResponse for Enterprise : \n{}",jsonResponseEntity);
		LOGGER.warn("\nResponse for Atlas : \n{}",baseResponseEntity);
		return message;
	}
}