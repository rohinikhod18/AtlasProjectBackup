package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface IDataAnonService {
	
	/**
	 * Process data anonymization.
	 *
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> processDataAnonymization(Message<MessageContext> message) throws ComplianceException;

}
