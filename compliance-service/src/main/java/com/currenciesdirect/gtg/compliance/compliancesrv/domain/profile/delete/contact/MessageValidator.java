package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class MessageValidator extends BaseMessageValidator{
	
	private static final String TRADE_ACC_ID = "trade_acc_id";
	private static final String ACC_SF_ID = "acc_sf_id";
	private static final String TRADE_CONTACT_ID = "trade_contact_id";
	private static final String CONTACT_SF_ID = "contact_sf_id";
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
		RegistrationResponse response = new RegistrationResponse();
		DeleteContactRequest deleteContactRequest = null;
		FieldValidator validator;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			deleteContactRequest = (DeleteContactRequest) messageExchange.getRequest();
			deleteContactRequest.setCorrelationID(correlationID);
			
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.DELETE_CONTACT) {
				validator = validateDeleteContactReuqest(deleteContactRequest);
				createBaseResponse(response, validator, FundsInReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, ServiceInterfaceType.PROFILE.name());
				messageExchange.setResponse(response);
			} 
		} catch (Exception e) {
			LOG.error(INVALID_REQUEST, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setOsrID(messageExchange.getRequest().getOsrId());
		response.setCorrelationID(correlationID);
		messageExchange.setResponse(response);
		return message;
	}
	
	/**
	 * Validate delete contact reuqest.
	 *
	 * @param deleteContactRequest the delete contact request
	 * @return the field validator
	 */
	private FieldValidator validateDeleteContactReuqest(DeleteContactRequest deleteContactRequest) {
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { deleteContactRequest.getOrgCode(), deleteContactRequest.getContactSfId(),
						deleteContactRequest.getTradeContactId(), deleteContactRequest.getAccountSfId(), 
						deleteContactRequest.getTradeAccountId() },
				new String[] { ORG_CODE, CONTACT_SF_ID, TRADE_CONTACT_ID, ACC_SF_ID, TRADE_ACC_ID });
		return fv;
	}
	
}
