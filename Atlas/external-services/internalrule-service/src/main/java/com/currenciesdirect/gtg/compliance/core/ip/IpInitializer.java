/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;


/**
 * The Class IpInitializer.
 *
 * @author manish
 */
@javax.ejb.Singleton
@Startup
public class IpInitializer {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpInitializer.class);
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		LOG.debug("======================INIT STARTED=================================");
		IpConcreteDataBuilder.getInstance().loadCache();
		LOG.debug("======================INIT END=================================");
	}

}
