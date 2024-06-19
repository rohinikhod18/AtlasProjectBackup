package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

public interface IActivityDBServiceImpl {

	public  Message<MessageContext> createProfileActivity(Message<MessageContext> message);
}
