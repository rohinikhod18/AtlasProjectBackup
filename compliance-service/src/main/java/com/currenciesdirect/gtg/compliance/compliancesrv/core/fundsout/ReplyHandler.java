package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.BulkRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
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
		ResponseEntity<ServiceMessageResponse> responseEntity ;
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
				&& operation == OperationEnum.CUSTOMCHECK_RESEND) {
			CustomCheckResendResponse customCheckResponse = new CustomCheckResendResponse();
			MessageExchange customCheckExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			CustomCheckResponse serviceResponse = (CustomCheckResponse)customCheckExchange.getResponse();
			customCheckResponse.setResponse(serviceResponse);
			responseEntity = new ResponseEntity<>(customCheckResponse, HttpStatus.OK);
		} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
				&& operation == OperationEnum.WHITELIST_UPDATE) {
			MessageExchange customCheckExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			CustomCheckResponse serviceResponse = (CustomCheckResponse)customCheckExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
			
		} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT 
				&& operation == OperationEnum.RECHECK_FAILURES) {
			MessageExchange fundsOutBulkExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FUNDSOUT_BULK_RECHECK_SERVICE);
			BulkRecheckResponse serviceResponse = (BulkRecheckResponse)fundsOutBulkExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		}
		else{
			FundsOutBaseRequest serviceRequest = (FundsOutBaseRequest) messageExchange.getRequest();
			FundsOutResponse serviceResponse = (FundsOutResponse) messageExchange.getResponse();
			if (serviceResponse == null) {
				serviceResponse = new FundsOutResponse();
				serviceResponse.setStatus(FundsOutComplianceStatus.HOLD.name());
				serviceResponse.setResponseReason(FundsOutReasonCode.SYSTEM_FAILURE);
			}
			serviceResponse.setOsrID(message.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
			serviceResponse.setTradePaymentID(serviceRequest.getPaymentFundsoutId());
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
		LOGGER.warn(" ??????????????????????? FUNDS_OUT processSkippedMessages {}", message.getPayload().getGatewayMessageExchange().getResponse());
		
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

		BaseResponse registrationResponse = new BaseResponse();
		
		registrationResponse.setDecision(BaseResponse.DECISION.FAIL);
		message.getPayload().getGatewayMessageExchange().setResponse(registrationResponse);
		ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(registrationResponse, HttpStatus.OK);
		
		deferredResult.setResult(responseEntity);
		}catch(Exception e){
			LOGGER.error("Exception in processSkippedMessages()", e);
			message.getPayload().setFailed(true);
		}
	}
	
}