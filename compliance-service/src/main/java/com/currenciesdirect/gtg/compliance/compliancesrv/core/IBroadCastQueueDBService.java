/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core;


import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * @author manish
 *
 */
public interface IBroadCastQueueDBService {

	public Message<MQMessageContext> loadMessageFromDB(Message<MQMessageContext> message) throws ComplianceException;
	
	public Message<MQMessageContext> updateBroadCastStatusToDB(Message<MQMessageContext> message) throws ComplianceException;
	
	public Message<MessageContext> saveIntoBroadcastQueue(Message<MessageContext> message)
			throws ComplianceException;
	
	public Boolean isMessageForBroadcast() throws ComplianceException;
	
	public ComplianceAccount getContactsDetails(String accountSfId) throws ComplianceException;

}
