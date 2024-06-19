package com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * @author prashant.verma
 */
public interface KafkaTxExecutorService {

	public Message<MessageContext> executeKafkaTx(Message<MessageContext> message);
	
	public void executeKafkaTx(OperationEnum operation, ServiceInterfaceType serviceType,
			String transactionId, ServiceMessage messageRequest);
	
}
