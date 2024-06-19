/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author manish
 * 
 */
public class IpCache {

	
	private final ConcurrentHashMap<String , Object>  cache = new ConcurrentHashMap<>();
	
	public <T extends Object> void store(String key,  T data) {
		cache.put(key, data);
	}
/*
	public <T extends Object> void storeAll(Hashtable<T, List<? extends Object>> initData) {
		cache.putAll(initData);
	}*/
	public  <T extends Object> void storeAll(ConcurrentHashMap<String, T> providerCountryMap) {
		cache.putAll(providerCountryMap);
	}
	
	public boolean contains(String key) {
		return cache.get(key) != null;
	}

	public Object retrieve(String key) {
		return (Object) cache.get(key);
	}

	public  ConcurrentHashMap<String,Object> getAll() {
		return cache;
	}

	public void remove(String key) {
		cache.remove(key);
	}
	
}
