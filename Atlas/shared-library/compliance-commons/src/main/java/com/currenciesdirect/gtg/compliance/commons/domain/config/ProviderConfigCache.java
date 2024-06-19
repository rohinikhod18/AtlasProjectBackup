/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The Class ProviderConfigCache.
 */
public class ProviderConfigCache {

	/** The cache. */
	private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

	/**
	 * Store.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param data the data
	 */
	public <T extends Object> void store(String key, T data) {
		cache.put(key, data);
	}

	/**
	 * Store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public <T extends Object> void storeAll(ConcurrentMap<String, T> providerCountryMap) {
		cache.putAll(providerCountryMap);
	}

	/**
	 * Contains.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean contains(String key) {
		return cache.get(key) != null;
	}

	/**
	 * Retrieve.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object retrieve(String key) {
		return cache.get(key);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public ConcurrentMap<String, Object> getAll() {
		return cache;
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 */
	public void remove(String key) {
		cache.remove(key);
	}

}
