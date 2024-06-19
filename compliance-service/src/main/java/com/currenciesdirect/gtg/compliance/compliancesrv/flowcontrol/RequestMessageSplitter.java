
package com.currenciesdirect.gtg.compliance.compliancesrv.flowcontrol;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.ObjectCloner;


public class RequestMessageSplitter {
	private static final Logger LOG = LoggerFactory.getLogger(RequestMessageSplitter.class);
	@SuppressWarnings("unchecked")
	@Splitter
	public List<Message<MessageContext>> splitRequestMessage(Message<MessageContext> message, 
			@Header("numberOfCopiesToMake") int numberOfCopiesToMake){
		
		List<Message<MessageContext>> serviceMsgs = new ArrayList<>();
		for(int i=0;i<numberOfCopiesToMake;i++){
			Message<MessageContext> tmp=null;
			try {
				tmp = (Message<MessageContext>) ObjectCloner.deepCopy(message);
			} catch (Exception e) {
				LOG.error("error in splitRequestMessage", e);
				message.getPayload().setFailed(true);
			}
			serviceMsgs.add(tmp);
		}
		LOG.debug("######## RequestMessageSplitter size: {}",serviceMsgs.size());
		return serviceMsgs;
	}

}
