package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.BaseMessageValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class DataValidator extends BaseMessageValidator{
	
	private static final Logger LOG = LoggerFactory.getLogger(DataValidator.class);

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
		MessageExchange messageExchange =  message.getPayload().getGatewayMessageExchange();
		response.setOsrID(message.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
		try {
			DeleteContactRequest fRequest = (DeleteContactRequest) messageExchange.getRequest();
			RegistrationServiceRequest oldReuqest = (RegistrationServiceRequest)fRequest.getAdditionalAttribute("regRequest");
			Account acc = oldReuqest.getAccount();
			validateDeleteContact(response, oldReuqest,acc);
			messageExchange.setResponse(response);
			}
		catch (Exception e) {
			LOG.error(REQUEST_IS_NOT_VALID, e);
			response.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
			response.setResponseDescription(ComplianceReasonCode.MISSINGINFO.getReasonDescription());
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			messageExchange.setResponse(response);
		}
		response.setCorrelationID(correlationID);
		return message;
	}

	/**
	 * Validate delete contact.
	 *
	 * @param response the response
	 * @param fRequest the f request
	 * @param oldReuqest the old reuqest
	 * @param acc the acc
	 */
	private void validateDeleteContact(RegistrationResponse response, RegistrationServiceRequest oldReuqest, Account acc) {
		FieldValidator fv = new FieldValidator();
		List<Contact> contacts = acc.getContacts();
		if(null == acc.getAccSFID() || null == acc.getTradeAccountID()){
			fv.addError("Provided Account or Contact does not exist", "acc_sf_id / contact_sf_id");
		} else if(contacts.isEmpty() || null == contacts.get(0).getContactSFID() || null == contacts.get(0).getTradeContactID()){
			fv.addError("Contact does not exist", "contact_sf_id or trade_contact_id");
		} else if(!contacts.isEmpty() && Boolean.TRUE.equals(contacts.get(0).getPrimaryContact())) {
			fv.addError("Cannot delete primary contact", "");
		} else if((boolean) oldReuqest.getAdditionalAttribute("PaymentInitiated")){
			fv.addError("Cannot delete contact having payment history", "");
		}
		
		createBaseResponse(response, fv, ComplianceReasonCode.MISSINGINFO.getReasonCode(), 
				ComplianceReasonCode.MISSINGINFO.getReasonDescription(),"PROFILE");
	}
}
