package com.currenciesdirect.gtg.compliance.compliancesrv.flowcontrol;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;


public class ResponseMessageAggregator {
	private static final Logger LOG = LoggerFactory.getLogger(ResponseMessageAggregator.class);
	
	/**
	 * Complie response.
	 *
	 * @param responses the responses
	 * @return the message
	 */
	@Aggregator
	public Message<MessageContext> complieResponse(List<Message<MessageContext>> responses) {

		Message<MessageContext> responseMsg = null; 
		try{
			// The received list will have 3 cloned Messages. And each will contain and Exchange object for
			// KYC_SERVICE, FRAUGSTER_SERVICE, SANCTION_SERVICE, & INTERNAL_RULE_SERVICE
			// INTERNAL_RULE_SERVICE can be taken from any of these Exhange object
			// however, we need to extract other exchange objects based on ServiceTobeInvoked value
			
			// So for first msgs from list, keep only INTERNAL_RULE_SERVICE & exchange for ServiceTobeInvoked
			// remove rest.  then get the rest from  remaining msgs.
			
			responseMsg =  responses.get(0);
			if (ServiceTypeEnum.KYC_SERVICE!=responseMsg.getPayload().getServiceTobeInvoked()) 
				responseMsg.getPayload().removeMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			if (ServiceTypeEnum.FRAUGSTER_SERVICE!=responseMsg.getPayload().getServiceTobeInvoked())
				responseMsg.getPayload().removeMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			if (ServiceTypeEnum.SANCTION_SERVICE!=responseMsg.getPayload().getServiceTobeInvoked())
				responseMsg.getPayload().removeMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			
			MessageExchange messageExchange = responseMsg.getPayload().getGatewayMessageExchange();
			RegistrationResponse registrationResponse = new RegistrationResponse();
				for (int i = 1; i < responses.size(); i++) {
					Message<MessageContext> tmp = responses.get(i);
					extractMsgExchange(responseMsg, tmp);
				}
			messageExchange.setResponse(registrationResponse);
		}catch(Exception ex){
			LOG.error("error in complieResponse", ex);
			if(responseMsg != null){
				responseMsg.getPayload().setFailed(true);
			}
		}
		return responseMsg;
	}

	private void extractMsgExchange(Message<MessageContext> responseMsg, Message<MessageContext> tmp) {
		if(tmp != null ){
			MessageExchange tempExchange = null;
			switch(tmp.getPayload().getServiceTobeInvoked()){
			case FRAUGSTER_SERVICE:
				tempExchange = tmp.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
				LOG.debug("ResponseMessageAggregator.complieResponse(): added FRAUGSTER_SERVICE");
				break;
			case KYC_SERVICE:
				tempExchange = tmp.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
				LOG.debug("ResponseMessageAggregator.complieResponse(): added KYC_SERVICE");
				break;
			case SANCTION_SERVICE:
				tempExchange = tmp.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
				LOG.debug("ResponseMessageAggregator.complieResponse(): added SANCTION_SERVICE");
				break;
			default:
				break;
			}
			if(tmp.getPayload() != null && tmp.getPayload().isFailed())
				responseMsg.getPayload().setFailed(true);
			if(null != tempExchange)
				responseMsg.getPayload().appendMessageExchange(tempExchange);
		}
	}
	
	/**
	 * Correlating response msgs.
	 *
	 * @param message the message
	 * @return the object
	 */
	@CorrelationStrategy
	public Object correlatingResponseMsgs(Message<MessageContext> message) {
		return message.getHeaders().get("correlationID");
	}

}
