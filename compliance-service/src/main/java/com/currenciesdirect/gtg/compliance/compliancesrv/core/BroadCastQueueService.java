/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.BroadCastQueueDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastQueueRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;

/**
 * The Class BroadCastQueueService.
 *
 * @author manish
 */
public class BroadCastQueueService {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BroadCastQueueService.class);

	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Do message broadcasting.
	 */
	public void doMessageBroadcasting() {
		BroadCastQueueRequest broadCastQueueRequest = new BroadCastQueueRequest();
		MQMessageContext messageContext = new MQMessageContext();
		messageContext.setRequest(broadCastQueueRequest);
		BroadCastQueueDBServiceImpl broadCastQueueDBServiceImpl = applicationContext
				.getBean("BroadCastQueueDBServiceImpl", BroadCastQueueDBServiceImpl.class);
		try {
			while (Boolean.TRUE.equals(broadCastQueueDBServiceImpl.isMessageForBroadcast())) {
				executeWorkflow(messageContext);
			}
		} catch (ComplianceException e) {
			LOG.error("Error in executing broadcast service", e);
		}
	}

	/**
	 * Execute workflow.
	 *
	 * @param messageContext
	 *            the message context
	 * @return the deferred result
	 */
	public DeferredResult<ResponseEntity> executeWorkflow(final MQMessageContext messageContext) {

		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>(-1L);// 8000L,
																					// "timeout
																					// occured"
		Map<String, Object> messageHeaders = new HashMap<>();
		messageHeaders.put(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT, deferredResult);
		Message<MQMessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
				.build();

		MessageChannel messageChannel = getInMessageChannel();
		if (messageChannel != null)
			messageChannel.send(message);
		return deferredResult;
	}

	/**
	 * Gets the in message channel.
	 *
	 * @return the in message channel
	 */
	private MessageChannel getInMessageChannel() {
		MessageChannel messageChannel = null;
		try {
			messageChannel = applicationContext.getBean("PUSH.MQ.IN.CHANNEL", MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			LOG.error("Error in BroadCastQueueService", nbde);
		}
		return messageChannel;
	}

	/**
	 * Handle response.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MQMessageContext> handleResponse(Message<MQMessageContext> message) {
		if (Boolean.TRUE.equals(message.getPayload().getNoMessageLeft())) {
			LOG.debug("NO MESSAGE LEFT FOR BROADCAST");
		} else {
			LOG.debug("PUSH MQ EXECUTED SUCCESSFULLY");
		}

		DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
				.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

		BaseResponse regResponse = new BaseResponse();
		ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(regResponse, HttpStatus.OK);

		deferredResult.setResult(responseEntity);

		return message;
	}
}
