/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.fraugster.core.sessionhandler.FraugsterSessionHandler;

/**
 * The Class FraugsterInitializer.
 *
 * @author manish
 */
@Component
public class FraugsterInitializer {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(FraugsterInitializer.class);


    /** The Constant FRAUGSTER . */
    private static final String FRAUGSTER = "Fraugster";
    
    /** The Constant FRAUGSTER_SERVICE_PROVIDER . */
    private static final String FRAUGSTER_SERVICE_PROVIDER = "fraugster.service.provider";
	/**
	 * Inits the.
	 */

	public void init() {
		LOG.debug("======================INIT STARTED=================================");
		try {
			FraugsterConcreteDataBuilder.getInstance().loadCache();
			if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
			  FraugsterSessionHandler.getInstance().startFraugsterSession();
			}
		} catch (Exception e) {
			LOG.error(" Error in FraugsterInitializer init():=============== \n"
					+ "|------------------------------------------------------|\n"
					+ "| INITIALIZATION \n"
					+ "| ERROR \n"
					+ "| PLEASE RESTART \n"
					+ "| SERVER \n"
					+ "|------------------------------------------------------|\n", e);

		}
		LOG.debug("======================INIT END=================================");
	}

	/**
	 * Shutdown.
	 */

	public void shutdown() {
		LOG.debug("======================SHUTDOWN STARTED=================================");
		if (FRAUGSTER.equals(System.getProperty(FRAUGSTER_SERVICE_PROVIDER))) {
		  FraugsterSessionHandler.getInstance().stopscheduler();
		}
		LOG.debug("======================SHUTDOWN END=================================");

	}

}
