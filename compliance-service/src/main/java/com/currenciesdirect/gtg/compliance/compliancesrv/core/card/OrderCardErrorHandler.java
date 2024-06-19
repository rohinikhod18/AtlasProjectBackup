package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

public class OrderCardErrorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCardErrorHandler.class);

	public Message<MessageContext> logError(Message<MessageContext> message) {

		MessageContext context = message.getPayload();
		RegistrationServiceRequest request = context.getGatewayMessageExchange().getRequest(
				RegistrationServiceRequest.class);
		LOG.error("Error in OrderCardErrorHandler logError() method for TradeAccountNumber : {}",
				request.getAccount().getTradeAccountNumber());
		OrderCardResponse response = createResponse(message);

		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		ResponseEntity<ServiceMessageResponse> responseEntity;
		responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		deferredResult.setResult(responseEntity);
		return message;
	}

	private OrderCardResponse createResponse(Message<MessageContext> message) {	
		//AT-4812
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();	
		Boolean isContactIdWrong=messageExchange.getRequest().getAdditionalAttributes().containsKey("CONTACT_ID_WRONG");
		if(isContactIdWrong) {
			MessageContext context = message.getPayload();
			OrderCardResponse response = new OrderCardResponse();
			response.setStatus(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setDecision(DECISION.FAIL);
			response.setResponseCode(FundsInReasonCode.CARD_CONTACT_ID_AND_TRADE_ACCOUNT_NUMBER_MISMATCHED.getReasonCode());
			response.setResponseDescription(FundsInReasonCode.CARD_CONTACT_ID_AND_TRADE_ACCOUNT_NUMBER_MISMATCHED.getReasonDescription());
			context.getGatewayMessageExchange().setResponse(response);
			return response;
		}else {
			MessageContext context = message.getPayload();
			OrderCardResponse response = new OrderCardResponse();
			response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			response.setDecision(DECISION.FAIL);
			response.setResponseCode(FundsInReasonCode.SYSTEM_FAILURE.getReasonCode());
			response.setResponseDescription(FundsInReasonCode.SYSTEM_FAILURE.getReasonDescription());
			context.getGatewayMessageExchange().setResponse(response);
			return response;
		}
		
	}

}
