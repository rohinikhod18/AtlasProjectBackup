package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface ICardDBServiceImpl {
	Message<MessageContext> saveNewCard(Message<MessageContext> message) throws ComplianceException;
}
