package com.currenciesdirect.gtg.compliance.core.loadcache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The Class FraudSightCache.
 */
public class FraudSightCache {

	/** The fraudSight proprty cache. */
	private final ConcurrentMap<String , Object>  fraudSightProprtyCache = new ConcurrentHashMap<>();
	
	
	/**
	 * fraudSight store.
	 *
	 * @param <T> the generic type
	 * @param fraudSightKey the fraudSight key
	 * @param fraudSightData the fraudSight data
	 */
	public <T extends Object> void fraudSightStore(String fraudSightKey,  T fraudSightData) {
		fraudSightProprtyCache.put(fraudSightKey, fraudSightData);
	}
	
	
	/**
	 * fraudSight store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public  <T extends Object> void fraudSightStoreAll(ConcurrentMap<String, T> providerCountryMap) {
		fraudSightProprtyCache.putAll(providerCountryMap);
	}
	
	
	/**
	 * fraudSight contains.
	 *
	 * @param fraudSight the fraudSight key
	 * @return true, if successful
	 */
	public boolean fraudSightContains(String fraudSightKey) {
		return fraudSightProprtyCache.get(fraudSightKey) != null;
	}

	
	/**
	 * fraudSight retrieve.
	 *
	 * @param fraudSight the fraudSight key
	 * @return the object
	 */
	public Object fraudSightRetrieve(String fraudSightKey) {
		return  fraudSightProprtyCache.get(fraudSightKey);
	}

	
	/**
	 * fraudSight get all.
	 *
	 * @return the concurrent map
	 */
	public  ConcurrentMap<String,Object> fraudSightGetAll() {
		return fraudSightProprtyCache;
	}

	
	/**
	 * fraudSight remove.
	 *
	 * @param fraudSight the fraudSight key
	 */
	public void fraudSightRemove(String fraudSightKey) {
		fraudSightProprtyCache.remove(fraudSightKey);
	}
	
}
