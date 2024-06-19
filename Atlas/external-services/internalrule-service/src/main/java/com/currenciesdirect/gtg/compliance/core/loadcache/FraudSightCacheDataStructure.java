package com.currenciesdirect.gtg.compliance.core.loadcache;

import java.util.concurrent.ConcurrentMap;


/**
 * The Class FraudSightCacheDataStructure.
 */
public class FraudSightCacheDataStructure {
	/** The cache data structure. */
	private static FraudSightCacheDataStructure cacheDataStructure = new FraudSightCacheDataStructure();

	/**
	 * Instantiates a new fraudSight cache data structure.
	 */
	private FraudSightCacheDataStructure() {

	}

	/**
	 * Gets the single instance of FraudSightCacheDataStructure.
	 *
	 * @return single instance of FraudSightCacheDataStructure
	 */
	public static FraudSightCacheDataStructure getInstance() {
		return cacheDataStructure;
	}

	/** The provider init config map. */
	private FraudSightCache providerInitConfigMap = new FraudSightCache();

	/**
	 * Sets the fraudSight providerinit config map.
	 *
	 * @param properties
	 *            the properties
	 */
	public void setFraudSightProviderinitConfigMap(ConcurrentMap<String, FraudSightProviderProperty> properties) {
		this.providerInitConfigMap.fraudSightStoreAll(properties);
	}

	/**
	 * Gets the providerinit config map.
	 *
	 * @param <T>
	 *            the generic type
	 * @param methodName
	 *            the method name
	 * @return the providerinit config map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProviderinitConfigMap(String methodName) {
		return (T) this.providerInitConfigMap.fraudSightRetrieve(methodName);
	}
}
