package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface ITransactionMonitoringMQDBService {
	
	public Boolean isMessageForBroadcast() throws ComplianceException;

	Message<MessageContext> loadMessageFromDB(Message<MessageContext> message) throws ComplianceException;

}
