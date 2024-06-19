
package com.currenciesdirect.gtg.compliance.core.blacklist;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class BlacklistCache.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class BlacklistCache<K extends Object, V extends Object> {

	/** The cache. */
	private final Map<K, V> cache = new ConcurrentHashMap<>();

	/**
	 * Store.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void store(K key, V value) {
		cache.put(key, value);
	}

	/**
	 * Store all.
	 *
	 * @param initData
	 *            the initializing data
	 */
	public void storeAll(Map<K, V> initData) {
		cache.putAll(initData);
	}

	/**
	 * Contains value for key.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	public boolean contains(K key) {
		return cache.get(key) != null;
	}

	/**
	 * Retrieve value for key.
	 *
	 * @param key
	 *            the key
	 * @return the v
	 */
	public V retrieve(K key) {
		return cache.get(key);
	}

	/**
	 * Gets the all map.
	 *
	 * @return the all
	 */
	public Map<K, V> getAll() {
		return cache;
	}

	/**
	 * Removes the value for key.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	public boolean remove(K key) {
		return (cache.remove(key) != null);
	}

	/**
	 * Gets the all keys.
	 *
	 * @return the all keys
	 */
	public Set<K> getAllKeys() {
		return cache.keySet();
	}

	/**
	 * Contains key.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	public boolean containsKey(K key) {
		return cache.containsKey(key);
	}

}
