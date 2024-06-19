package com.currenciesdirect.gtg.compliance.compliancesrv.core.card.update;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

public class MessageValidator extends BaseMessageValidator {
	
	private static final String INVALID_REQUEST = "Request is not valid";
	private static final Logger LOG = LoggerFactory.getLogger(MessageValidator.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		OrderCardResponse orderCardResponse = new OrderCardResponse();
		OrderCardStatusUpdateRequest orderCardStatusUpdateRequest;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			orderCardStatusUpdateRequest = (OrderCardStatusUpdateRequest) messageExchange.getRequest();
			orderCardStatusUpdateRequest.setCorrelationID(correlationID);

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.CARD && operation == OperationEnum.CARD_STATUS_UPDATE) {
				Boolean isNotValid = validateOrderCardStatusUpdateRequest(orderCardStatusUpdateRequest);
				if (!isNotValid) {
					orderCardResponse.setDecision(DECISION.SUCCESS);
				} else {
					LOG.debug("{} : {}", ComplianceReasonCode.MISSINGINFO.getReasonCode(), orderCardStatusUpdateRequest);

					orderCardResponse.setDecision(DECISION.FAIL);
					orderCardResponse.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
					orderCardResponse.setResponseDescription(Constants.MSG_MISSING_INFO);
					orderCardResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
				}
			}
		} catch (Exception e) {
			LOG.error(INVALID_REQUEST, e);
			orderCardResponse.setDecision(DECISION.FAIL);
			orderCardResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		orderCardResponse.setOsrID(messageExchange.getRequest().getOsrId());
		orderCardResponse.setCorrelationID(correlationID);
		messageExchange.setResponse(orderCardResponse);
		return message;
	}

	/**
	 * Validate order card status update request.
	 *
	 * @param orderCardStatusUpdateRequest the order card status update request
	 * @return true, if successful
	 */
	private Boolean validateOrderCardStatusUpdateRequest(OrderCardStatusUpdateRequest orderCardStatusUpdateRequest) {
		
		return ((orderCardStatusUpdateRequest.getTradeAccountNumber().equals("")
				        || orderCardStatusUpdateRequest.getTradeAccountNumber().trim().isEmpty())
				|| (orderCardStatusUpdateRequest.getCardId().equals("")
				|| orderCardStatusUpdateRequest.getCardId().trim().isEmpty())

				|| (orderCardStatusUpdateRequest.getContactId().equals("")						
				|| orderCardStatusUpdateRequest.getContactId().trim().isEmpty()||orderCardStatusUpdateRequest.getContactId().matches(".*[A-Za-z].*"))

		);
	}

}