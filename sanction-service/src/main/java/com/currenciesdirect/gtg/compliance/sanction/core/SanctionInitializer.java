 /*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionCache.java
 ******************************************************************************/

package com.currenciesdirect.gtg.compliance.sanction.core;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author manish
 *
 */
@Component
public class SanctionInitializer {
	
	private static final Logger LOG = LoggerFactory.getLogger(SanctionInitializer.class);
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		LOG.debug("======================INIT STARTED=================================");
		SanctionConcreteDataBuilder.getInstance().loadCache();
		LOG.debug("======================INIT END=================================");
	}

}
