package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class DeleteContactRuleService {
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> msg) {
		
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		ServiceInterfaceType serviceInterfaceType = exchange.getServiceInterface();
		OperationEnum operation = exchange.getOperation();
		
		if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.DELETE_CONTACT) {
			deleteContactResponse(exchange);
			return msg;
		}
		return msg;
	}

	/**
	 * Delete contact response.
	 *
	 * @param exchange the exchange
	 */
	private void deleteContactResponse(MessageExchange exchange) {
		DeleteContactRequest deleteContactRequest = exchange.getRequest(DeleteContactRequest.class);
		RegistrationResponse response = (RegistrationResponse) exchange.getResponse();
		ComplianceAccount account = new ComplianceAccount();
		ComplianceContact contact = new ComplianceContact();
		contact.setContactSFID(deleteContactRequest.getContactSfId());
		account.setAccountSFID(deleteContactRequest.getAccountSfId());
		account.addContact(contact);
		response.setOrgCode(deleteContactRequest.getOrgCode());
		response.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
		response.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());
		response.setAccount(account);
		exchange.setResponse(response);
	}
	
}
