package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

/**
 * The Class SanctionUploadSuccessHandler.
 */
public class SanctionUploadSuccessHandler {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SanctionUploadSuccessHandler.class);
	
	/**
	 * On success.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<String> onSuccess(AdviceMessage<String> message) throws ComplianceException {
		LOG.info("--------------------------------- Sanction CSV file uploaded successfully --------------------------");
		return message;	
	}

}
