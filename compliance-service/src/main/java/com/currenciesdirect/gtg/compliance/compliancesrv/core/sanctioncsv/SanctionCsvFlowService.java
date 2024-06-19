package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * The Class SanctionCsvFlowService.
 */
@Component
public class SanctionCsvFlowService {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SanctionCsvFlowService.class);
	
	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * Initiate flow.
	 */
	public void initiateFlow() {
		SanctionCsvRequest sanctionCsvRequest = new SanctionCsvRequest();
		SanctionCsvMessageContext messageContext = new SanctionCsvMessageContext();
		messageContext.setRequest(sanctionCsvRequest);
		
		LOG.info("-------Sanction CSV File Upload Flow Started ------");
		executeWorkflow(messageContext);
		LOG.info("-------Sanction CSV File Upload Flow Ended ------");
	}
	
	/**
	 * Execute workflow.
	 *
	 * @param messageContext the message context
	 * @return the deferred result
	 */
	private DeferredResult<ResponseEntity> executeWorkflow(final SanctionCsvMessageContext messageContext) {
		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>(-1L);
		
		Map<String, Object> messageHeaders = new HashMap<>();
		messageHeaders.put(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT, deferredResult);
		Message<SanctionCsvMessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders).build();

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
			messageChannel = applicationContext.getBean("SANCTION.CSV.IN.CHANNEL", MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			LOG.error("Error in SanctionCsvFlowService ", nbde);
		}
		return messageChannel;
	}

}
