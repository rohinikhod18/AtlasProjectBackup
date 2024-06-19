package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

public class OrderCardResponseBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCardResponseBuilder.class);

	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		String operation = String.valueOf(message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION));
		LOG.debug("Build response for operation : {}", operation);

		if (OperationEnum.CHECK_CARD_ELIGIBILITY.name().equals(operation) ||
				OperationEnum.CARD_STATUS_UPDATE.name().equals(operation)) {
			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			OrderCardResponse orderCardResponse = exchange.getResponse(OrderCardResponse.class);

			if (BaseResponse.DECISION.FAIL == orderCardResponse.getDecision()
					&& InternalProcessingCode.INV_REQUEST.name().equals(orderCardResponse.getErrorCode())) {

				DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
						.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

				ResponseEntity<ServiceMessageResponse> responseEntity =
						new ResponseEntity<>(orderCardResponse, HttpStatus.BAD_REQUEST);

				deferredResult.setResult(responseEntity);
			} else {
				DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message
						.getHeaders().get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

				ResponseEntity<ServiceMessageResponse> responseEntity =
						new ResponseEntity<>(orderCardResponse, HttpStatus.OK);

				deferredResult.setResult(responseEntity);
			}

			return message;
		}

		LOG.warn("Discard message : Not a CARD operation");
		message.getPayload().setFailed(true);
		return message;
	}
}
