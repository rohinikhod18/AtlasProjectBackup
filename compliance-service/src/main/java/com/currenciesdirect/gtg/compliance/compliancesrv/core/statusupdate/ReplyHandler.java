package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;


public class ReplyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyHandler.class);
	
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		String jsonResponseEntity;
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity ;
		BaseResponse serviceResponse = (BaseResponse) messageExchange.getResponse();
		if (serviceResponse == null) {
			serviceResponse = new BaseResponse();
		}
		
		if(!serviceResponse.getResponseCode().equalsIgnoreCase(InternalProcessingCode.INV_REQUEST.getCode())) {
			serviceResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
			serviceResponse.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());
		}
		
		responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);

		deferredResult.setResult(responseEntity);

		message.getPayload().clearAll();
		jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
		LOGGER.warn("Response :{}",jsonResponseEntity);
		return message;
	}

}