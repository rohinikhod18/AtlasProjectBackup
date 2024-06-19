package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

@SuppressWarnings({ "unchecked" })
public class IntuitionResponseHandler {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(IntuitionResponseHandler.class);
	
	/**
	 * Log error.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> logError(Message<MessageContext> message) {
		
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
		
		ResponseEntity<ServiceMessageResponse> responseEntity = null;

		responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		deferredResult.setResult(responseEntity);

		return message;
	}
	
	/**
	 * Response handler for intuition payment status update.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> responseHandlerForIntuitionPaymentStatusUpdate(Message<MessageContext> message) {
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

		ResponseEntity<ServiceMessageResponse> responseEntity = null;

		if (message.getPayload().getValidationResult().equalsIgnoreCase("FAIL")) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
		}

		deferredResult.setResult(responseEntity);

		return message;
	}
	
	public Message<MessageContext> responseHandlerForDormantCustomerUpdateToIntuition(Message<MessageContext> message) {
		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

		ResponseEntity<RegistrationResponse> responseEntity = null;
		RegistrationResponse response = (RegistrationResponse) message.getPayload().getGatewayMessageExchange().getResponse();		
		
		if(response.getAccount().getIntuitionRiskLevel() != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);	
		} else {
			responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		deferredResult.setResult(responseEntity);

		return message;
	}
	
	/**
	 * Response handler for intuition historic payment upload.
	 *
	 * @param message the message
	 * @return the message
	 */
	//AT-5264
	public Message<MessageContext> responseHandlerForIntuitionHistoricPaymentUpload(Message<MessageContext> message) {
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
		
		ResponseEntity<BaseResponse> responseEntity = null;
		
		if(message.getPayload().isFailed()) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
		}
		
		deferredResult.setResult(responseEntity);
		
		return message;
	}
	
}
