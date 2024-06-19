package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.BulkRegRecheckResponse;
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
		ResponseEntity<ServiceMessageResponse> responseEntity;
		ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
		OperationEnum operation = messageExchange.getOperation();
		if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.RECHECK_FAILURES) {
			MessageExchange regBulkExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.REGISTRATION_BULK_RECHECK_SERVICE);
			BulkRegRecheckResponse serviceResponse = (BulkRegRecheckResponse) regBulkExchange.getResponse();
			responseEntity = new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		} else {

			BaseResponse regResponse = (BaseResponse) messageExchange.getResponse();
			responseEntity = new ResponseEntity<>(regResponse, HttpStatus.OK);
		}

		deferredResult.setResult(responseEntity);
		jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
		LOGGER.warn("Response: {}",jsonResponseEntity);
		return message;
	}

	public void onFail(ErrorMessage errorMessage) {

		try {
			String jsonResponseEntity;
			MessageHandlingException messageException = (MessageHandlingException) errorMessage.getPayload();
			ComplianceException complianceException = (ComplianceException) messageException.getCause();
			Message<MessageContext> message = (Message<MessageContext>) messageException.getFailedMessage();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			BaseResponse registrationResponse = new BaseResponse();
			registrationResponse.setResponseCode(complianceException.getIpc().getCode());
			registrationResponse.setResponseDescription(complianceException.getException().getMessage());
			registrationResponse.setDecision(BaseResponse.DECISION.FAIL);
			messageExchange.setResponse(registrationResponse);

			LOGGER.warn("Reply handler returning error response {}", registrationResponse);

			DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
					.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(registrationResponse, HttpStatus.OK);
			jsonResponseEntity = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
			LOGGER.warn("Response : {}",jsonResponseEntity);
			deferredResult.setResult(responseEntity);
		} catch (Exception e) {
			LOGGER.error("Exception in onFail()", e);
		}
	}

	public void processSkippedMessages(Message<MessageContext> message) {
		ResponseEntity<ServiceMessageResponse> responseEntity = null;
		DeferredResult<ResponseEntity<?>> deferredResult = null;
		try {

			LOGGER.warn(" ??????????????????????? processSkippedMessages {}",
					message.getPayload().getGatewayMessageExchange().getResponse());

			deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
					.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			BaseResponse registrationResponse = new BaseResponse();

			registrationResponse.setDecision(BaseResponse.DECISION.FAIL);
			message.getPayload().getGatewayMessageExchange().setResponse(registrationResponse);
			responseEntity = new ResponseEntity<>(registrationResponse, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Exception in processSkippedMessages()", e);
		}
		if (deferredResult != null)
			deferredResult.setResult(responseEntity);
		else {
			BaseResponse registrationResponse = new BaseResponse();
			registrationResponse.setResponseCode("Unknown error");
			registrationResponse.setResponseDescription("Unknown error");
			registrationResponse.setDecision(BaseResponse.DECISION.FAIL);
			message.getPayload().getGatewayMessageExchange().setResponse(registrationResponse);

			LOGGER.warn("Reply handler returning error response {}", registrationResponse);

			deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
					.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);
			responseEntity = new ResponseEntity<>(registrationResponse, HttpStatus.OK);

			deferredResult.setResult(responseEntity);
		}

	}
}