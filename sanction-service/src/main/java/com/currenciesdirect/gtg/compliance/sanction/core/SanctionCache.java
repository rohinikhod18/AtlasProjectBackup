 /*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionCache.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author manish
 * 
 */
public class SanctionCache {
	

	/** The sanction property cache. */
	private final Map<String, Object> sanctionPropertyCache = new ConcurrentHashMap<>();

	
	/**
	 * Sanction store.
	 *
	 * @param <T> the generic type
	 * @param sanctionKey the sanction key
	 * @param sanctionData the sanction data
	 */
	public <T extends Object> void sanctionStore(String sanctionKey, T sanctionData) {
		sanctionPropertyCache.put(sanctionKey, sanctionData);
	}

	
	/**
	 * Sanction store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public <T extends Object> void sanctionStoreAll(Map<String, T> providerCountryMap) {
		sanctionPropertyCache.putAll(providerCountryMap);
	}

	
	/**
	 * Sanction contains.
	 *
	 * @param sanctionKey the sanction key
	 * @return true, if successful
	 */
	public boolean sanctionContains(String sanctionKey) {
		return sanctionPropertyCache.get(sanctionKey) != null;
	}

	
	/**
	 * Sanction retrieve.
	 *
	 * @param sanctionKey the sanction key
	 * @return the object
	 */
	public Object sanctionRetrieve(String sanctionKey) {
		return sanctionPropertyCache.get(sanctionKey);
	}

	
	/**
	 * Sanction get all.
	 *
	 * @return the map
	 */
	public Map<String, Object> sanctionGetAll() {
		return sanctionPropertyCache;
	}

	
	/**
	 * Sanction remove.
	 *
	 * @param sanctionKey the sanction key
	 */
	public void sanctionRemove(String sanctionKey) {
		sanctionPropertyCache.remove(sanctionKey);
	}

}
