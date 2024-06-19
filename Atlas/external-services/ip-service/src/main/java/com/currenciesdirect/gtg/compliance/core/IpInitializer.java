/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;


/**
 * @author manish
 *
 */
@javax.ejb.Singleton
@Startup
public class IpInitializer {
	
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(IpInitializer.class);
	
	@PostConstruct
	public void init(){
		LOG.info("======================INIT STARTED=================================");
		IpConcreteDataBuilder.getInstance().loadCache();
		LOG.info("======================INIT END=================================");
	}

}
