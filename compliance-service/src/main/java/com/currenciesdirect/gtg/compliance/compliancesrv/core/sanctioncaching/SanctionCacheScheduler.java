package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SanctionCacheScheduler {
	
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SanctionCacheScheduler.class);
	
	@Autowired
	private ContactSanctionResponseCaching contactSanctionStatusCaching;
	
	@Scheduled(initialDelay=60000, fixedRate=1800000)
	public void initiatScheduler() {
		try {
			LOG.info("----------- Initiating Sanction Cache Cleaning-----------");
			contactSanctionStatusCaching.clearContactSanctionCache();
			LOG.info("----------- Sanction Cache Cleared-----------");
		}catch(Exception e) {
			LOG.error("Error in SanctionCacheScheduler ",e);
		}
	}
}
