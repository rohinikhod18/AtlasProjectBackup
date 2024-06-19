/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author manish
 * 
 */
public class IpCache {

	/** The ip property cache. */
	private final Map<String , Object>  ipPropertyCache = new ConcurrentHashMap<>();
	
	
	/**
	 * Ip store.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param data the data
	 */
	public <T extends Object> void ipStore(String key,  T data) {
		ipPropertyCache.put(key, data);
	}

	
	/**
	 * Ip store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public  <T extends Object> void ipStoreAll(Map<String, T> providerCountryMap) {
		ipPropertyCache.putAll(providerCountryMap);
	}
	
	
	/**
	 * Ip contains.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean ipContains(String key) {
		return ipPropertyCache.get(key) != null;
	}

	
	/**
	 * Ip retrieve.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object ipRetrieve(String key) {
		return ipPropertyCache.get(key);
	}

	
	
	/**
	 * Ip get all.
	 *
	 * @return the map
	 */
	public  Map<String,Object> ipGetAll() {
		return ipPropertyCache;
	}

	
	/**
	 * Ip remove.
	 *
	 * @param key the key
	 */
	public void ipRemove(String key) {
		ipPropertyCache.remove(key);
	}
	
}
