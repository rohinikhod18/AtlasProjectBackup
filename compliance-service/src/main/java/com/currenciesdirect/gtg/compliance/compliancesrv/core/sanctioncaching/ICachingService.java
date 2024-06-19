package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching;

import org.springframework.cache.Cache;

public interface ICachingService {
	
	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	Cache getCache();
	
	/**
	 * Put into cache.
	 *
	 * @param contactId the contact id
	 * @param providerResponse the provider response
	 */
	void putIntoCache(Integer contactId,String providerResponse);

	/**
	 * Gets the cache data.
	 *
	 * @param contactId the contact id
	 * @param providerResponse the provider response
	 * @return the cache data
	 */
	String getCacheData(Integer contactId);
	
	/**
	 * Clear cache.
	 */
	public void clearCache();

}
