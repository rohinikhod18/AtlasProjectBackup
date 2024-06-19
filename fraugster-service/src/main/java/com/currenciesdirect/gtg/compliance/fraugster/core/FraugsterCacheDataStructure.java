package com.currenciesdirect.gtg.compliance.fraugster.core;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;

/**
 * The Class FraugsterCacheDataStructure.
 *
 * @author manish
 */
public class FraugsterCacheDataStructure {

	/** The cache data structure. */
	private static FraugsterCacheDataStructure cacheDataStructure = new FraugsterCacheDataStructure();

	/**
	 * Instantiates a new fraugster cache data structure.
	 */
	private FraugsterCacheDataStructure() {

	}

	/**
	 * Gets the single instance of FraugsterCacheDataStructure.
	 *
	 * @return single instance of FraugsterCacheDataStructure
	 */
	public static FraugsterCacheDataStructure getInstance() {
		return cacheDataStructure;
	}

	/** The provider init config map. */
	private FraugsterCache providerInitConfigMap = new FraugsterCache();

	/**
	 * Sets the fraugster providerinit config map.
	 *
	 * @param properties
	 *            the properties
	 */
	public void setFraugsterProviderinitConfigMap(ConcurrentMap<String, FraugsterProviderProperty> properties) {
		this.providerInitConfigMap.fraugsterStoreAll(properties);
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
		return (T) this.providerInitConfigMap.fraugsterRetrieve(methodName);
	}

}
