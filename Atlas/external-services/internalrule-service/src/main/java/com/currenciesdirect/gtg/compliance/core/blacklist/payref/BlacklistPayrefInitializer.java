package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class BlacklistPayrefInitializer 
{
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG =LoggerFactory.getLogger(BlacklistPayrefInitializer.class);

	public void init() {
		LOG.debug("======================INIT STARTED=================================");
		try {
			BlacklistPayrefConcreteDataBuilder.getInstance().loadCache();

		} catch (Exception e) {
			LOG.warn("Error in BlacklistPayrefInitializer");
		}
	}
	
	
}
