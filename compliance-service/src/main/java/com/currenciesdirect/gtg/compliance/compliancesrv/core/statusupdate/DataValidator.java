package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class DataValidator extends BaseMessageValidator{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataValidator.class);

	/**
	 * @param message
	 * @param correlationID
	 * @return
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		StatusUpdateResponse response = new StatusUpdateResponse(); 
		StatusUpdateRequest statusUpdateRequest = null;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		
		try {
			LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),message.getPayload());
			statusUpdateRequest = (StatusUpdateRequest) messageExchange.getRequest();
			statusUpdateRequest.setCorrelationID(correlationID);
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			FieldValidator validator = new FieldValidator();
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.STATUS_UPDATE) {
				validateStatusUpdate(statusUpdateRequest,validator);
				if(!validator.isFailed()) {
					createBaseResponse(response, validator, ComplianceReasonCode.MISSINGINFO.getReasonCode(),
						Constants.MSG_MISSING_INFO, "PROFILE STATUS UPDATE");
				}else {
					response.setDecision(BaseResponse.DECISION.SUCCESS);
				}
			}
		}
		catch(Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			response.setErrorDescription(InternalProcessingCode.INV_REQUEST.toString());
		}
		response.setCorrelationID(correlationID);
		response.setOsrID(messageExchange.getRequest().getOsrId());
		messageExchange.setResponse(response);
		return message;
	} 
	
	/**
	 * @param statusUpdateRequest
	 * @param validator
	 */
	private void validateStatusUpdate(StatusUpdateRequest statusUpdateRequest, FieldValidator validator) {
		Account oldAccount = (Account)statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		
		if(null == oldAccount || null == oldAccount.getAccSFID() || !oldAccount.getAccSFID().equals(statusUpdateRequest.getCrmAccountId())) {
			validator.addError(Constants.ERR_INVALID_ACCOUNT_NOT_PRESENT, Constants.FIELD_ACC_ACC_SF_ID);
		}
		else {
			Contact oldContact = null;
			for (Contact contact : oldAccount.getContacts()) {
				if (contact.getContactSFID().equals(statusUpdateRequest.getCrmContactId())) {
					oldContact = contact;
					break;
				}
			}
			
			if(null == oldContact || oldAccount.getContacts().isEmpty()) {
				validator.addError(Constants.ERR_INVALID_COTACT,Constants.FIELD_CONTACT_CONTACT_SF_ID);
			}
			if(null != oldContact && ContactComplianceStatus.ACTIVE.name().equalsIgnoreCase(oldContact.getContactStatus())) {
				validator.addError(Constants.ERR_ACCOUNT_ALREADY_ACTIVE,Constants.FIELD_CONTACT_CONTACT_SF_ID);
			}
		}
	}
}
