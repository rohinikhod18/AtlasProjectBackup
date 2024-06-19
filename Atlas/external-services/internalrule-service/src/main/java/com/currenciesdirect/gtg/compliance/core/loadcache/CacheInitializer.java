package com.currenciesdirect.gtg.compliance.core.loadcache;

import org.springframework.stereotype.Component;

/**
 * The Class CacheInitializer.
 */
@Component
public class CacheInitializer {
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CacheInitializer.class);

	/**
	 * Inits the.
	 */
	public void init() {
		LOG.warn("======================FRAUD SIGHT INIT STARTED=================================");
		FraudSightConcreteDataBuilder.getInstance().loadCache();
		LOG.warn("======================FRAUD SIGHT INIT END=================================");
	}
}
