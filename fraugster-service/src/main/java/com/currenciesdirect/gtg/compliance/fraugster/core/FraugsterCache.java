/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The Class FraugsterCache.
 *
 * @author manish
 */
public class FraugsterCache {
	
	
	/** The fraugster proprty cache. */
	private final ConcurrentMap<String , Object>  fraugsterProprtyCache = new ConcurrentHashMap<>();
	
	
	/**
	 * Fraugster store.
	 *
	 * @param <T> the generic type
	 * @param fraugsterKey the fraugster key
	 * @param fraugsterData the fraugster data
	 */
	public <T extends Object> void fraugsterStore(String fraugsterKey,  T fraugsterData) {
		fraugsterProprtyCache.put(fraugsterKey, fraugsterData);
	}
	
	
	/**
	 * Fraugster store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public  <T extends Object> void fraugsterStoreAll(ConcurrentMap<String, T> providerCountryMap) {
		fraugsterProprtyCache.putAll(providerCountryMap);
	}
	
	
	/**
	 * Fraugster contains.
	 *
	 * @param fraugsterKey the fraugster key
	 * @return true, if successful
	 */
	public boolean fraugsterContains(String fraugsterKey) {
		return fraugsterProprtyCache.get(fraugsterKey) != null;
	}

	
	/**
	 * Fraugster retrieve.
	 *
	 * @param fraugsterKey the fraugster key
	 * @return the object
	 */
	public Object fraugsterRetrieve(String fraugsterKey) {
		return  fraugsterProprtyCache.get(fraugsterKey);
	}

	
	/**
	 * Fraugster get all.
	 *
	 * @return the concurrent map
	 */
	public  ConcurrentMap<String,Object> fraugsterGetAll() {
		return fraugsterProprtyCache;
	}

	
	/**
	 * Fraugster remove.
	 *
	 * @param fraugsterKey the fraugster key
	 */
	public void fraugsterRemove(String fraugsterKey) {
		fraugsterProprtyCache.remove(fraugsterKey);
	}
	
}
