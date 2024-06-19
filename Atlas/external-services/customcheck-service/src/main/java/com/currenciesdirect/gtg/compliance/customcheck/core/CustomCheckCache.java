
package com.currenciesdirect.gtg.compliance.customcheck.core;

import java.util.concurrent.ConcurrentHashMap;
 
/**
 * The Class CustomCheckCache.
 *
 * @param <T> the generic type
 */
public class CustomCheckCache<T extends Object> {

	
	
	private final ConcurrentHashMap<String , T>  cache = new ConcurrentHashMap<>();
	
	public  void store(String key,  T dataField) {
		cache.put(key, dataField);
	}

	public  void storeAll(ConcurrentHashMap<String,T> initData) {
		cache.putAll(initData);
	}
	
	public boolean contains(String key) {
		return cache.get(key) != null;
	}

	public T retrieve(String key) {
		return (T) cache.get(key);
	}

	public  ConcurrentHashMap<String, T> getAll() {
		return cache;
	}

	public void remove(String key) {
		cache.remove(key);
	}
	
}
