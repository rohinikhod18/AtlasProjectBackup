package com.currenciesdirect.gtg.compliance.compliancesrv.core.intuitionpaymentcsv;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

@Component
public class IntuitionHistoricPaymentCsvFlowService {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(IntuitionHistoricPaymentCsvFlowService.class);
	
	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;
	
	/** The intuition payment scheduler cron job. */
	@Autowired
	private IntuitionHistoricPaymentSchedulerCronJob intuitionPaymentSchedulerCronJob;
	
	/**
	 * Gets the intuition payment cron job.
	 *
	 * @return the intuition payment cron job
	 */
	@Bean
	public String getIntuitionPaymentCronJob() {
		return intuitionPaymentSchedulerCronJob.geIntuitionCronJob();
	}
	
	
	/**
	 * Initiate flow.
	 */
	public void initiateFlow(){
		boolean isFlowEnable = System.getProperty("intuition.historic.payment.flow.enable") != null
				? Boolean.parseBoolean(System.getProperty("intuition.historic.payment.flow.enable"))
				: Boolean.FALSE;
		
		if (isFlowEnable) {
			LOG.info("---------------------Intuition Historic Payment Scheduler flow is enabled---------------------");
			MessageContext messageContext = new MessageContext();
			executeWorkflow(messageContext);
		} else {
			LOG.info("---------------------Intuition Historic Payment Scheduler flow is disabled---------------------");
		}
	}


	/**
	 * Execute workflow.
	 *
	 * @param messageContext the message context
	 * @return the deferred result
	 */
	private DeferredResult<ResponseEntity> executeWorkflow(MessageContext messageContext) {
		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>(-1L);

		Map<String, Object> messageHeaders = new HashMap<>();
		messageHeaders.put(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT, deferredResult);
		
		MessageExchange gateWayMessageExchange = new MessageExchange();
		IntuitionHistoricPaymentsRequest intuitionPaymentRequest = new IntuitionHistoricPaymentsRequest();
		gateWayMessageExchange.setServiceTypeEnum(ServiceTypeEnum.GATEWAY_SERVICE);
		gateWayMessageExchange.setRequest(intuitionPaymentRequest);
		messageContext.appendMessageExchange(gateWayMessageExchange);
		
		Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders).build();
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
			messageChannel = applicationContext.getBean("INTUITION_PAYMENT.CSV.IN.CHANNEL", MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			LOG.error("Error in IntuitionPaymentCsvFlowService ", nbde);
		}
		return messageChannel;
	}

}
