package com.currenciesdirect.gtg.compliance.commons.keycloaktokengeneration;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The Class KeycloakInitializer.
 */
@Component
public class KeycloakInitializer {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(KeycloakInitializer.class);
	
	/** The external systems. */
	private String[] externalSystems = new String[] {"titan","commhub"};
	
	/**
	 * Inits the.
	 */

	public void init() {
		LOG.debug("======================INIT STARTED=================================");
		try {
				if(null != KeycloakConfig.getInstance(externalSystems[0]).getTokenUrl())
					Tokenizer.getInstance().runscheduler();
		} catch (Exception e) {
			LOG.error("Error in KeycloakInitializer init():===============	\n"
					+ "|------------------------------------------------------|\n"
					+ "| 				INITIALIZATION 						  \n"
					+ "| 					ERROR							  \n"
					+ "| 				PLEASE RESTART						  \n"
					+ "| 					SERVER							  \n"
					+ "|------------------------------------------------------|\n", e);

		}
		LOG.debug("======================INIT END=================================");
	}

}
