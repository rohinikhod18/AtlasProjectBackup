package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface INewRegistrationDBService {

	Message<MessageContext> saveNewRegistration(Message<MessageContext> message) throws ComplianceException;

	Message<MessageContext> updateComplianceStatus(Message<MessageContext> message) throws ComplianceException;

}
