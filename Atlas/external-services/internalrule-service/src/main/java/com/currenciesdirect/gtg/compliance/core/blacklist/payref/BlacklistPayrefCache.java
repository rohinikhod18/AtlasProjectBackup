package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BlacklistPayrefCache 
{
	/** The fraugster proprty cache. */
	private final ConcurrentMap<String , Object>  BlacklistPayrefProprtyCache = new ConcurrentHashMap<>();
	
	
	/**
	 * Fraugster store.
	 *
	 * @param <T> the generic type
	 * @param fraugsterKey the fraugster key
	 * @param fraugsterData the fraugster data
	 */
	public <T extends Object> void BlacklistPayrefStore(String BlacklistPayrefKey,  T BlacklistPayrefData) {
		BlacklistPayrefProprtyCache.put(BlacklistPayrefKey, BlacklistPayrefData);
	}
	
	
	/**
	 * Fraugster store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public  <T extends Object> void BlacklistPayrefStoreAll(ConcurrentMap<String, T> providerCountryMap) {
		BlacklistPayrefProprtyCache.putAll(providerCountryMap);
	}
	
	
	/**
	 * Fraugster contains.
	 *
	 * @param fraugsterKey the fraugster key
	 * @return true, if successful
	 */
	public boolean BlacklistPayrefContains(String BlacklistPayrefKey) {
		return BlacklistPayrefProprtyCache.get(BlacklistPayrefKey) != null;
	}

	
	/**
	 * Fraugster retrieve.
	 *
	 * @param fraugsterKey the fraugster key
	 * @return the object
	 */
	public Object BlacklistPayrefRetrieve(String BlacklistPayrefKey) {
		return  BlacklistPayrefProprtyCache.get(BlacklistPayrefKey);
	}

	
	/**
	 * Fraugster get all.
	 *
	 * @return the concurrent map
	 */
	public  ConcurrentMap<String,Object> BlacklistPayrefGetAll() {
		return BlacklistPayrefProprtyCache;
	}

	
	/**
	 * Fraugster remove.
	 *
	 * @param fraugsterKey the fraugster key
	 */
	public void BlacklistPayrefRemove(String BlacklistPayrefKey) {
		BlacklistPayrefProprtyCache.remove(BlacklistPayrefKey);
	}
	
	
	
}
