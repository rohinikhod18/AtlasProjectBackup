package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.BulkRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

@SuppressWarnings({ "unchecked" })
public class ReplyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyHandler.class);

	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		String jsonResponseEntity;
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		ResponseEntity<ServiceMessageResponse> responseEntity ;
		if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.WHITELIST_UPDATE) {
			MessageExchange customCheckExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			CustomCheckResponse serviceResponse = (CustomCheckResponse) customCheckExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);

		}else if(serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.DELETE_OPI){
			
			FundsInDeleteResponse serviceResponse = (FundsInDeleteResponse)messageExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN 
				&& operation == OperationEnum.RECHECK_FAILURES) {
			MessageExchange fundsInBulkExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FUNDSIN_BULK_RECHECK_SERVICE);
			BulkRecheckResponse serviceResponse = (BulkRecheckResponse)fundsInBulkExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
			
		else {
			BaseResponse serviceResponse = (BaseResponse) messageExchange.getResponse();
			if (serviceResponse == null) {
				serviceResponse = new BaseResponse();
			}
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}

		deferredResult.setResult(responseEntity);

		message.getPayload().clearAll();
		jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
		LOGGER.warn("Response :{}",jsonResponseEntity);
		return message;
	}

	public void processSkippedMessages(Message<MessageContext> message) {
		try{
		LOGGER.warn(" ??????????????????????? FUNDS_IN processSkippedMessages {}", message.getPayload().getGatewayMessageExchange().getResponse());
		
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

		BaseResponse registrationResponse = new BaseResponse();
		
		registrationResponse.setDecision(BaseResponse.DECISION.FAIL);
		message.getPayload().getGatewayMessageExchange().setResponse(registrationResponse);
		ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(registrationResponse, HttpStatus.OK);
		
		deferredResult.setResult(responseEntity);
		}catch(Exception e){
			LOGGER.error("Exception in processSkippedMessages()",e);
		}
		
	}
}