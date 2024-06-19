/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import org.springframework.stereotype.Component;

/**
 * The Class KYCInitializer.
 *
 * @author manish
 */
@Component
public class KYCInitializer {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(KYCInitializer.class);

	/**
	 * Inits the.
	 */
	public void init() {
		LOG.debug("======================INIT STARTED=================================");
		KYCConcreteDataBuilder.getInstance().loadCache();
		LOG.debug("======================INIT END=================================");
	}

}
