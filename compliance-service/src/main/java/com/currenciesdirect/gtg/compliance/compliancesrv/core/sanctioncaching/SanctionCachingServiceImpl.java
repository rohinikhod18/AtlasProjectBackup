package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
public class SanctionCachingServiceImpl implements ICachingService{

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SanctionCachingServiceImpl.class);
	
	/** The Constant CONTACT_SANCTION_CACHE. */
	private static final String CONTACT_SANCTION_CACHE = "sanctionResponseCache";
	
	/** The cache manager. */
	@Autowired
	private CacheManager cacheManager;
	
	/**
	 * AT-3102
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	@Override
	public Cache getCache() {
		return cacheManager.getCache(CONTACT_SANCTION_CACHE);
	}
	
	/**
	 * AT-3102
	 * Gets the cache data.
	 *
	 * @param contactId the contact id
	 * @param providerResponse the provider response
	 * @return the cache data
	 */
	public String getCacheData(Integer contactId) {
		ValueWrapper valueWrapper = null;
		String providerResponse = null;
		try {
			Cache cache = getCache();
			valueWrapper = cache.get(contactId);
			if(null !=  valueWrapper)
				providerResponse = (String) valueWrapper.get();
		}catch(Exception e) {
			LOG.error("Error in getCacheData of SanctionCachingServiceImpl ",e);
		}
		return providerResponse;
	}

	/**
	 * AT-3102
	 * Adds the status.
	 *
	 * @param contactId the contact id
	 * @param providerResponse the provider response
	 */
	@Override
	@CachePut(value=CONTACT_SANCTION_CACHE)
	public void putIntoCache(Integer contactId,String providerResponse) {
		try {
			getCache().put(contactId,providerResponse);
		}catch(Exception e) {
			LOG.error("Error in putData() of SanctionCachingServiceImpl ",e);
		}
	}

	/**
	 * Clear cache.
	 */
	@CacheEvict(value=CONTACT_SANCTION_CACHE)
	public void clearCache() {
		try {
			Cache cache = getCache();
			cache.clear();
		}catch(Exception e) {
			LOG.error("Error in clearCache() of SanctionCachingServiceImpl ",e);
		}
	}
}