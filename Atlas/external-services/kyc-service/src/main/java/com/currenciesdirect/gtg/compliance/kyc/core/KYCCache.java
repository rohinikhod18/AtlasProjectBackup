/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author manish
 * 
 */
public class KYCCache {

	/** The kyc property cache. */
	private final ConcurrentMap<String , Object>  kycPropertyCache = new ConcurrentHashMap<>();
	
	
	/**
	 * Kyc store.
	 *
	 * @param <T> the generic type
	 * @param kycKey the kyc key
	 * @param kycData the kyc data
	 */
	public <T extends Object> void kycStore(String kycKey,  T kycData) {
		kycPropertyCache.put(kycKey, kycData);
	}
	
	/**
	 * Kyc store all.
	 *
	 * @param <T> the generic type
	 * @param providerCountryMap the provider country map
	 */
	public  <T extends Object> void kycStoreAll(ConcurrentMap<String, T> providerCountryMap) {
		kycPropertyCache.putAll(providerCountryMap);
	}
	
	
	/**
	 * Kyc contains.
	 *
	 * @param kycKey the kyc key
	 * @return true, if successful
	 */
	public boolean kycContains(String kycKey) {
		return kycPropertyCache.get(kycKey) != null;
	}

	
	/**
	 * Kyc retrieve.
	 *
	 * @param kycKey the kyc key
	 * @return the object
	 */
	public Object kycRetrieve(String kycKey) {
		return  kycPropertyCache.get(kycKey);
	}

	
	
	/**
	 * Kyc get all.
	 *
	 * @return the concurrent map
	 */
	public  ConcurrentMap<String,Object> kycGetAll() {
		return kycPropertyCache;
	}

	
	/**
	 * Kyc remove.
	 *
	 * @param kycKey the kyc key
	 */
	public void kycRemove(String kycKey) {
		kycPropertyCache.remove(kycKey);
	}
	
}
