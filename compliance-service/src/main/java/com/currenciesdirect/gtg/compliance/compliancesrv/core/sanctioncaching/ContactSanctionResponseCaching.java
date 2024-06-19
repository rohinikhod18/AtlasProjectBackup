package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

@Component
public class ContactSanctionResponseCaching {
	
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ContactSanctionResponseCaching.class);
	
	@Autowired
	private ICachingService cachingServiceImpl;
	
	/**
	 * AT-3102
	 * 
	 * Execute caching.
	 *
	 * @param id the id
	 * @param providerResponse the provider response
	 * @return the string
	 */
	public String getContactSanctionCacheResponse(Integer id) {
		String providerResponse = null;
		try {
			providerResponse = cachingServiceImpl.getCacheData(id);
		}catch(Exception e) {
			LOG.error("Error in getContactSanctionCacheResponse()", e);
		}
		return providerResponse;
	}
	
	/**
	 * AT-3102
	 * Insert contact sacntion response in cache.
	 *
	 * @param id the id
	 * @param sanctionContactResponse the sanction contact response
	 */
	public void insertContactSacntionResponseInCache(Integer id,SanctionContactResponse sanctionContactResponse) {
		try {
			LOG.info("-----Inserting Contact Sanction Response into Cache----");
			sanctionContactResponse.setIsCachedResponse(Boolean.TRUE);
			String sanctionContactResponseCaching = JsonConverterUtil.convertToJsonWithoutNull(sanctionContactResponse);
			cachingServiceImpl.putIntoCache(id,sanctionContactResponseCaching);
		}catch(Exception e) {
			LOG.error("Error in insertContactSacntionResponseInCache() ", e);
		}
	}
	
	/**
	 * Clear contact sanction cache.
	 */
	public void clearContactSanctionCache() {
		cachingServiceImpl.clearCache();
	}
}