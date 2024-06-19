/**
 * 
 */
package com.currenciesdirect.gtg.compliance.customchecks.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author manish
 * 
 */
public class CustomCheckCache {

	/** The customchecks property cache. */
	private final Map<String, Object> customchecksPropertyCache = new ConcurrentHashMap<>();

	
	/**
	 * Customchecks store.
	 *
	 * @param <T> the generic type
	 * @param customchecksKey the customchecks key
	 * @param customchecksData the customchecks data
	 */
	public <T extends Object> void customchecksStore(String customchecksKey, T customchecksData) {
		customchecksPropertyCache.put(customchecksKey, customchecksData);
	}

	
	/**
	 * Customchecks store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public <T extends Object> void customchecksStoreAll(Map<String, T> providerCountryMap) {
		customchecksPropertyCache.putAll(providerCountryMap);
	}

	
	/**
	 * Customchecks contains.
	 *
	 * @param customchecksKey the customchecks key
	 * @return true, if successful
	 */
	public boolean customchecksContains(String customchecksKey) {
		return customchecksPropertyCache.get(customchecksKey) != null;
	}

	
	/**
	 * Customchecks retrieve.
	 *
	 * @param customchecksKey the customchecks key
	 * @return the object
	 */
	public Object customchecksRetrieve(String customchecksKey) {
		return customchecksPropertyCache.get(customchecksKey);
	}

	
	/**
	 * Customchecks get all.
	 *
	 * @return the map
	 */
	public Map<String, Object> customchecksGetAll() {
		return customchecksPropertyCache;
	}

	
	/**
	 * Customchecks remove.
	 *
	 * @param customchecksKey the customchecks key
	 */
	public void customchecksRemove(String customchecksKey) {
		customchecksPropertyCache.remove(customchecksKey);
	}

}
