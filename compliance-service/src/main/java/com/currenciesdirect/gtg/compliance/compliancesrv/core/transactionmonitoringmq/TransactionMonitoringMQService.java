package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.PostCardTransactionMQDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.TransactionMonitoringMQDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.PostCardTransactionMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class TransactionMonitoringMQService.
 */
@Component
public class TransactionMonitoringMQService {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(TransactionMonitoringMQService.class);

	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;

	/** The transaction monitoring MQDB service impl. */
	@Autowired
	private TransactionMonitoringMQDBServiceImpl transactionMonitoringMQDBServiceImpl;
	
	/** The post card transaction MQDB service impl. */
	@Autowired
	private PostCardTransactionMQDBServiceImpl postCardTransactionMQDBServiceImpl;

	/** The Constant ORG_IN_CHANNEL. */
	private static final String ORG_IN_CHANNEL = "%s.%s.in.channel";

	/** The Constant DEFAULT_IN_CHANNEL. */
	private static final String DEFAULT_IN_CHANNEL = "%s.%s.in.channel";

	/**
	 * Do transaction monitoring MQ.
	 */
	public void doTransactionMonitoringMQ() {
		TransactionMonitoringMQRequest broadCastQueueRequest = new TransactionMonitoringMQRequest();

		try {
			if (Boolean.TRUE.equals(transactionMonitoringMQDBServiceImpl.isMessageForBroadcast())) {

				MessageContext messageContextTM = buildMessageContext(ServiceTypeEnum.GATEWAY_SERVICE,
						ServiceInterfaceType.PROFILE, OperationEnum.TRANSACTION_MONITORING_MQ_RESEND, null,
						broadCastQueueRequest);

				executeWorkflowTM(messageContextTM);
			}
		} catch (ComplianceException e) {
			LOG.error("Error in executing broadcast service", e);
		}
	}

	/**
	 * Builds the message context.
	 *
	 * @param serviceType the service type
	 * @param serviceInterface the service interface
	 * @param operation the operation
	 * @param orgCode the org code
	 * @param payload the payload
	 * @return the message context
	 */
	public MessageContext buildMessageContext(final ServiceTypeEnum serviceType,
			final ServiceInterfaceType serviceInterface, final OperationEnum operation, final String orgCode,
			final ServiceMessage payload) {

		MessageExchange gateWayMessageExchange = new MessageExchange();
		gateWayMessageExchange.setServiceTypeEnum(serviceType);
		gateWayMessageExchange.setServiceInterface(serviceInterface);
		gateWayMessageExchange.setOperation(operation);
		gateWayMessageExchange.setRequest(payload);

		// addHTTPHeaders(httpRequest, payload);

		MessageContext messageContext = new MessageContext();

		messageContext.appendMessageExchange(gateWayMessageExchange);

		return messageContext;
	}

	/**
	 * Execute workflow TM.
	 *
	 * @param messageContext the message context
	 * @return the deferred result
	 */
	public DeferredResult<ResponseEntity> executeWorkflowTM(final MessageContext messageContext) {
		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>(-1L);// 8000L,
																					// "timeout
																					// occured"

		MessageExchange gateWayMessageExchange = messageContext.getMessageCollection().get(0).getMessageExchange();

		if (null == gateWayMessageExchange.getRequest().getOsrId()
				|| gateWayMessageExchange.getRequest().getOsrId().isEmpty()) {
			gateWayMessageExchange.getRequest().setOsrId(UUID.randomUUID().toString());
		}

		Map<String, Object> messageHeaders = new HashMap<>();
		messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_RECEIVE_TIME,
				messageContext.getAuditInfo().getCreatedOn());
		messageHeaders.put(MessageContextHeaders.GATEWAY_SERVICE_ID, gateWayMessageExchange.getServiceInterface());
		messageHeaders.put(MessageContextHeaders.GATEWAY_OPERATION, gateWayMessageExchange.getOperation().name());
		messageHeaders.put(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT, deferredResult);
		messageHeaders.put("correlationID", UUID.randomUUID().toString());
		Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
				.build();

		MessageChannel messageChannel = getInMessageChannel(gateWayMessageExchange.getOperation().name(),
				gateWayMessageExchange.getServiceInterface().name());

		messageChannel.send(message);

		return deferredResult;
	}

	/**
	 * Gets the in message channel.
	 *
	 * @param operation the operation
	 * @param gatewayService the gateway service
	 * @return the in message channel
	 */
	private MessageChannel getInMessageChannel(final String operation, final String gatewayService) {
		MessageChannel messageChannel;
		String inChannel = String.format(ORG_IN_CHANNEL, gatewayService, operation);
		try {
			messageChannel = applicationContext.getBean(inChannel, MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			inChannel = String.format(DEFAULT_IN_CHANNEL, gatewayService, operation);
			String logData = "message forwarding to default channel [";
			logData = logData.concat(inChannel).concat("], exception was= ").concat(nbde.toString());
			LOG.debug(logData);
			messageChannel = applicationContext.getBean(inChannel, MessageChannel.class);
		}
		return messageChannel;
	}

	/**
	 * Adds the HTTP headers.
	 *
	 * @param httpRequest the http request
	 * @param payload the payload
	 */
	private void addHTTPHeaders(final HttpServletRequest httpRequest, final ServiceMessage payload) {
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		StringBuilder values;
		String headerName;
		Enumeration<String> headers;
		while (headerNames.hasMoreElements()) {
			headerName = headerNames.nextElement();
			headers = httpRequest.getHeaders(headerName);
			values = new StringBuilder();
			while (headers.hasMoreElements()) {
				if (values.length() > 0) {
					values.append(',');
				}
				values.append(headers.nextElement());
			}
			payload.addAttribute("http.header.".concat(headerName), values.toString());
		}
		payload.addAttribute("http.header.requestURI", httpRequest.getRequestURI());
		payload.addAttribute("http.header.remoteAddr", httpRequest.getRemoteAddr());
	}

	/**
	 * Handle response.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> handleResponse(Message<MessageContext> message) {
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

	/**
	 * Do post card transaction MQ.
	 */
	//AT-5023
	public void doPostCardTransactionMQ() {
		PostCardTransactionMQRequest postCardMQRequest = new PostCardTransactionMQRequest();

		try {
			if (Boolean.TRUE.equals(postCardTransactionMQDBServiceImpl.isMessageForBroadcast())) {

				MessageContext messageContextTM = buildMessageContext(ServiceTypeEnum.GATEWAY_SERVICE,
						ServiceInterfaceType.CARD, OperationEnum.TRANSACTION_MONITORING_MQ_RESEND, null,
						postCardMQRequest);

				executeWorkflowTM(messageContextTM);
			}
		} catch (ComplianceException e) {
			LOG.error("Error in executing post card transaction broadcast service", e);
		}
		
	}
	
}
