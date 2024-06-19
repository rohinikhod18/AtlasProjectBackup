package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice.MessageHandlingExpressionEvaluatingAdviceException;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class SanctionUplaodErrorHandler.
 */
public class SanctionUplaodErrorHandler {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SanctionUplaodErrorHandler.class);
	
	@Autowired
	private SanctionCsvRetryHandler sanctionCsvRetryHandler;
	
	/**
	 * On error.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageHandlingExpressionEvaluatingAdviceException> onError(
			Message<MessageHandlingExpressionEvaluatingAdviceException> message) throws IOException{
		
		Message<String> message1 = (Message<String>) message.getPayload().getFailedMessage();
		LOG.error("Error while uploading sanction CSV file {}",message1);
		
		sanctionCsvRetryHandler.retryUploadingCsv();
		
		return message;
	}

}
