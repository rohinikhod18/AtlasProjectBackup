package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public class PrepareResponseHandler {

	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {

		return prepareCreateUpdateResponse(message);

	}

	public Message<MessageContext> prepareCreateUpdateResponse(Message<MessageContext> message) {
		ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
		switch (eventType) {
		case PROFILE:
			setRegistrationRespone(message.getPayload());
			break;
		case FUNDSIN:
		case FUNDSOUT:
			setFundsoutRespone(message.getPayload());
			break;
		default:
			break;
		}
		return message;
	}

	private void setRegistrationRespone(MessageContext context) {
		RegistrationResponse response = (RegistrationResponse) context.getGatewayMessageExchange().getResponse();
	
		if (response == null) {
			response = new RegistrationResponse();
		}
		//updating response for save to broadcast
		if (InternalProcessingCode.INV_REQUEST.toString().equals(response.getErrorCode())) {
			RegistrationServiceRequest registrationServiceRequest = (RegistrationServiceRequest) context
					.getGatewayMessageExchange().getRequest();
			Account account = registrationServiceRequest.getAccount();

			ComplianceAccount complianceAccount = new ComplianceAccount();
			complianceAccount.setArc(ComplianceReasonCode.MISSINGINFO);
			complianceAccount.setErrorCode(response.getErrorCode());
			complianceAccount.setErrorDescription(response.getErrorDescription());
			if (null != account ){
				setComplianceAccountReasonDescription(response, account, complianceAccount);
			}
			response.setAccount(complianceAccount);
			response.setResponseCode(null);
			response.setResponseDescription(null);

		}
		response.setCorrelationID(context.getGatewayMessageExchange().getRequest().getCorrelationID());
		context.getGatewayMessageExchange().setResponse(response);
	}

	/**
	 * @param response
	 * @param account
	 * @param complianceAccount
	 */
	private void setComplianceAccountReasonDescription(RegistrationResponse response, Account account,ComplianceAccount complianceAccount) {
		
		if (null != account.getAccSFID()) {
			complianceAccount.setAccountSFID(account.getAccSFID());
		}
		if (null != account.getTradeAccountID()) {
			complianceAccount.setTradeAccountID(account.getTradeAccountID());
		}
		for (Contact contact : account.getContacts()) {
			if (null != contact.getContactSFID()) {
			ComplianceContact complianceContact = new ComplianceContact();
			complianceContact.setContactSFID(contact.getContactSFID());
			complianceContact.setResponseCode(response.getResponseCode());
			complianceContact.setResponseDescription(response.getResponseDescription());
			complianceAccount.addContact(complianceContact);
			}
		}
	}
	
	
	private void setFundsoutRespone(MessageContext context) {
		FundsOutResponse response = (FundsOutResponse) context.getGatewayMessageExchange().getResponse();
		if (response == null) {
			response = new FundsOutResponse();
		}
		response.setCorrelationID(context.getGatewayMessageExchange().getRequest().getCorrelationID());
		context.getGatewayMessageExchange().setResponse(response);
	}

}
