package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface IUpdateRegistrationDBService {
	Message<MessageContext> updateAccountDetails(Message<MessageContext> message) throws ComplianceException;

	Message<MessageContext> updateComplianceStatusForRegUpdate(Message<MessageContext> updMessage);

	Message<MessageContext> updateContactAttributes(Message<MessageContext> message) throws ComplianceException;
}