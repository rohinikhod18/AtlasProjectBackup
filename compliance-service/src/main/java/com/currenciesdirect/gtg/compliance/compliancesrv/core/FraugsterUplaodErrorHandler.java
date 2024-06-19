package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice.MessageHandlingExpressionEvaluatingAdviceException;
import org.springframework.messaging.Message;

/**
 * The Class FraugsterUplaodErrorHandler.
 */
public class FraugsterUplaodErrorHandler {
	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FraugsterUplaodErrorHandler.class);

	/**
	 * On error.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageHandlingExpressionEvaluatingAdviceException> onError(
			Message<MessageHandlingExpressionEvaluatingAdviceException> message) {

		Message<String> message1 = (Message<String>) message.getPayload().getFailedMessage();
		LOGGER.warn("Error in FraugsterUplaodErrorHandler class while uploading fraguster data {}", message1);
		return message;
	}
}
