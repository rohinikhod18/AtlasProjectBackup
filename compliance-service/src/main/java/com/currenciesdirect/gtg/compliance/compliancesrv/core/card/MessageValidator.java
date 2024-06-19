package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.PostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.PostCardTransactionResponse;
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

	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		OrderCardResponse orderCardResponse = new OrderCardResponse();
		OrderCardRequest orderCardRequest;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			orderCardRequest = (OrderCardRequest) messageExchange.getRequest();
			orderCardRequest.setCorrelationID(correlationID);

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.CARD && operation == OperationEnum.CHECK_CARD_ELIGIBILITY) {
				boolean isValid = validateOrderCardRequest(orderCardRequest);
				if (!isValid) {
					orderCardResponse.setDecision(DECISION.SUCCESS);
				} else {
					LOG.debug("{} : {}", ComplianceReasonCode.MISSINGINFO.getReasonCode(), orderCardRequest);

					orderCardResponse.setDecision(DECISION.FAIL);
					orderCardResponse.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
					orderCardResponse.setResponseDescription(Constants.MSG_MISSING_INFO_DATE_FORMAT);
					orderCardResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
				}
			}
		} catch (Exception e) {
			LOG.error(INVALID_REQUEST, e);
			orderCardResponse.setDecision(BaseResponse.DECISION.FAIL);
			orderCardResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		orderCardResponse.setOsrID(messageExchange.getRequest().getOsrId());
		orderCardResponse.setCorrelationID(correlationID);
		messageExchange.setResponse(orderCardResponse);
		return message;
	}

	//AT-5050
	private boolean validateOrderCardRequest(OrderCardRequest orderCardRequest) {
		return ((orderCardRequest.getTradeAccountNumber().equals("") || orderCardRequest.getTradeAccountNumber().trim().isEmpty())
					|| (orderCardRequest.getTradeContactID().equals("") || orderCardRequest.getTradeContactID().trim().isEmpty()));
	}
	
	/**
	 * Process for post card transaction.
	 *
	 * @param message the message
	 * @return the message
	 */
	//AT-4896
	public Message<MessageContext> processForPostCardTransaction(Message<MessageContext> message, @Header UUID correlationID){
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		PostCardTransactionResponse response = new PostCardTransactionResponse();
		PostCardTransactionRequest request;
		ServiceInterfaceType serviceInterfaceType;
		OperationEnum operation;
		
		try {
			request = (PostCardTransactionRequest) messageExchange.getRequest();
			
			serviceInterfaceType = messageExchange.getServiceInterface();
			operation = messageExchange.getOperation();
			
			if(serviceInterfaceType == ServiceInterfaceType.CARD && operation == OperationEnum.POST_TRANSACTION) {
				boolean isValid = validatePostCardTransaction(request);
				if (isValid) {
					response.setDecision(DECISION.SUCCESS);
				} else {
					LOG.error("Error in Post Card Transaction due to  {}", Constants.MSG_MISSING_INFO);
					
					LOG.debug("{} : {}", ComplianceReasonCode.MISSINGINFO.getReasonCode(), request);

					response.setDecision(DECISION.FAIL);
					response.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
					response.setResponseDescription(Constants.MSG_MISSING_INFO);
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
				}
			}
			
		} catch(Exception e) {
			LOG.error(INVALID_REQUEST, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setCorrelationID(correlationID);
		messageExchange.setResponse(response);
		return message;
		
	}

	/**
	 * Validate post card transaction.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	private boolean validatePostCardTransaction(PostCardTransactionRequest request) {
		return request.getPaymentLifeId() != null && !request.getPaymentLifeId().isEmpty();
	}

}