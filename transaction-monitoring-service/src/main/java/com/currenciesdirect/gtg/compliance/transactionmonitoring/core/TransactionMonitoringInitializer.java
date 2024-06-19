/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;



/**
 * The Class TransactionMonitoringInitializer.
 */
@Component
public class TransactionMonitoringInitializer {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(TransactionMonitoringInitializer.class);


	/**
	 * Inits the.
	 */
	public void init() {
		LOG.debug("======================INIT STARTED=================================");
		try {
			TransactionMonitoringConcreteDataBuilder.getInstance().loadCache();
			
		} catch (Exception e) {
			LOG.error(" Error in TransactionMonitoringInitializer init():===============\n"
					+ "|------------------------------------------------------|\n"
					+ "| INITIALIZATION \n"
					+ "| ERROR \n"
					+ "| PLEASE RESTART \n"
					+ "| SERVER \n"
					+ "|------------------------------------------------------|\n", e);

		}
		LOG.debug("======================INIT END=================================");
	}

}
