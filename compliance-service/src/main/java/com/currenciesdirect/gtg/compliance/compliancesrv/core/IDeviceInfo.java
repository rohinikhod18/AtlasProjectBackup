package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface IDeviceInfo {
	
	Message<MessageContext> saveDeviceInfo(Message<MessageContext> message) throws ComplianceException;
}
