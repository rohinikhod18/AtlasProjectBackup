package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

public class DataEnricher{
	
	@Autowired
	protected NewRegistrationDBServiceImpl newRegDBServiceImpl;
	
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		DeleteContactRequest request = messageExchange.getRequest(DeleteContactRequest.class);
		try {
			getUserTableId(message);
			RegistrationServiceRequest registrationServiceRequest = newRegDBServiceImpl.getContatDetailsForDelete(request);
			messageExchange.getRequest().addAttribute("regRequest", registrationServiceRequest);
			
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}
		return message;
	}
	
	/**
	 * Gets the user table id.
	 *
	 * @param message the message
	 * @return the user table id
	 * @throws ComplianceException the compliance exception
	 */
	public void getUserTableId(Message<MessageContext> message) throws ComplianceException{
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Integer userID = newRegDBServiceImpl.getUserIDFromSSOUserID(token.getPreferredUserName());
		//setting appropriate user id into UserProfile token
		token.setUserID(userID);
	}
}
