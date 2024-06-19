/*
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.restport;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BaseController.
 */
public class BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	/** The Constant ORG_IN_CHANNEL. */
	private static final String ORG_IN_CHANNEL = "%s.%s.%s.in.channel";

	/** The Constant DEFAULT_IN_CHANNEL. */
	private static final String DEFAULT_IN_CHANNEL = "%s.%s.in.channel";

	/** The application context. */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		LOGGER.debug("Controller [{}] initialized", this.getClass());
	}

	/**
	 * Destroy.
	 */
	@PreDestroy
	public void destroy() {
		LOGGER.debug("Controller [{}] destroyed", this.getClass());
	}

	/**
	 * Execute workflow.
	 *
	 * @param messageContext
	 *            the message context
	 * @param userToken
	 *            the user token
	 * @return the deferred result
	 */

	@ApiModelProperty(value = "Returns the result object and the setOrExpired boolean (true if this DeferredResult is no longer usable)", required = true)
	public DeferredResult<ResponseEntity> executeWorkflow(final MessageContext messageContext, AccessToken userToken) {
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
		messageHeaders.put(MessageContextHeaders.GATEWAY_REQUEST_ORG, messageContext.getOrgCode());
		messageHeaders.put(MessageContextHeaders.GATEWAY_USER, mapUserProfile(userToken));
		messageHeaders.put(MessageContextHeaders.OSR_ID, gateWayMessageExchange.getRequest().getOsrId());
		messageHeaders.put("correlationID", UUID.randomUUID().toString());
		Message<MessageContext> message = MessageBuilder.withPayload(messageContext).copyHeaders(messageHeaders)
				.build();

		MessageChannel messageChannel = getInMessageChannel(gateWayMessageExchange.getOperation().name(),
				gateWayMessageExchange.getServiceInterface().name(), messageContext.getOrgCode());

		messageChannel.send(message);

		return deferredResult;
	}

	/**
	 * Gets the in message channel.
	 *
	 * @param operation
	 *            the operation
	 * @param gatewayService
	 *            the gateway service
	 * @param orgCode
	 *            the org code
	 * @return the in message channel
	 */
	private MessageChannel getInMessageChannel(final String operation, final String gatewayService,
			final String orgCode) {
		MessageChannel messageChannel;
		String inChannel = String.format(ORG_IN_CHANNEL, orgCode, gatewayService, operation);
		try {
			messageChannel = applicationContext.getBean(inChannel, MessageChannel.class);
		} catch (NoSuchBeanDefinitionException nbde) {
			inChannel = String.format(DEFAULT_IN_CHANNEL, gatewayService, operation);
			String logData = "message forwarding to default channel [";
			logData = logData.concat(inChannel).concat("], exception was= ").concat(nbde.toString());
			LOGGER.debug(logData);
			messageChannel = applicationContext.getBean(inChannel, MessageChannel.class);
		}
		return messageChannel;
	}

	/**
	 * Builds the message context.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param serviceType
	 *            the service type
	 * @param serviceInterface
	 *            the service interface
	 * @param operation
	 *            the operation
	 * @param orgCode
	 *            the org code
	 * @param payload
	 *            the payload
	 * @return the message context
	 */
	public MessageContext buildMessageContext(final HttpServletRequest httpRequest, final ServiceTypeEnum serviceType,
			final ServiceInterfaceType serviceInterface, final OperationEnum operation, final String orgCode,
			final ServiceMessage payload) {

		MessageExchange gateWayMessageExchange = new MessageExchange();
		gateWayMessageExchange.setServiceTypeEnum(serviceType);
		gateWayMessageExchange.setServiceInterface(serviceInterface);
		gateWayMessageExchange.setOperation(operation);
		gateWayMessageExchange.setRequest(payload);

		addHTTPHeaders(httpRequest, payload);

		MessageContext messageContext = new MessageContext();
		if (null != orgCode)
			messageContext.setOrgCode(orgCode.toUpperCase());
		messageContext.appendMessageExchange(gateWayMessageExchange);

		return messageContext;
	}

	/**
	 * Map user profile.
	 *
	 * @param userToken
	 *            the user token
	 * @return the user profile
	 */
	private UserProfile mapUserProfile(AccessToken userToken) {
		UserProfile user = new UserProfile();
		user.setBirthdate(userToken.getBirthdate());
		user.setEmail(userToken.getEmail());
		user.setFamilyName(userToken.getFamilyName());
		user.setGender(userToken.getGender());
		user.setGivenName(userToken.getGivenName());
		user.setMiddleName(userToken.getMiddleName());
		user.setName(userToken.getName());
		user.setNickName(userToken.getNickName());
		user.setPhoneNumber(userToken.getPhoneNumber());
		user.setPreferredUserName(userToken.getPreferredUsername());
		user.setWebsite(userToken.getWebsite());
		user.setZoneinfo(userToken.getZoneinfo());
		return user;
	}

	/**
	 * Adds the HTTP headers.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param payload
	 *            the payload
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

}