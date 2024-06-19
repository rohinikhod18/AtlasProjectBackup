/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.mqbroadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastQueueRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;


/**
 * @author manish
 *
 */
public class MQTransformer {

	private static final Logger LOG = LoggerFactory.getLogger(MQTransformer.class);

	public Message<MQMessageContext> transformRequest(Message<MQMessageContext> message) {
		BroadCastQueueRequest request = (BroadCastQueueRequest) message.getPayload().getRequest();
		MQProviderRequest providerRequest = new MQProviderRequest();
		try {
			providerRequest.setAccountCID(request.getAccountID());
			
			providerRequest.setEntityType(request.getEntityType());
			providerRequest.setId(request.getId());
			providerRequest.setOrganizationID(request.getOrganizationID());
			providerRequest.setPaymentInCID(request.getPaymentInID());
			providerRequest.setPaymentOutCID(request.getPaymentOutID());
			providerRequest.setStatusJson(request.getStatusJson());
			providerRequest.setTimestamp(String.valueOf(request.getTimestamp()));
			message.getPayload().setMqProviderRequest(providerRequest);
			MQProviderResponse mqProviderResponse = new MQProviderResponse();
			mqProviderResponse.setStatus(BroadCastStatusEnum.FAILED.toString());
			message.getPayload().setMqProviderResponse(mqProviderResponse);
		} catch (Exception e) {
			LOG.error("Error in MQTransformer  class : transformRequest -", e);
		}
		return message;
	}

	public Message<MQMessageContext> transformResponse(Message<MQMessageContext> message) {
		try {
			// to-do
		} catch (Exception e) {
			LOG.error("Error in MQTransformer  class : transformRequest -", e);
		}
		return message;
	}
}
