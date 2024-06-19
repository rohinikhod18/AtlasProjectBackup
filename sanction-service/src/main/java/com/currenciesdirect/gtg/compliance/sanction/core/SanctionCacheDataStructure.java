/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionCacheDataStructure.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

/**
 * The Class SanctionCacheDataStructure.
 *
 * @author manish
 */
public class SanctionCacheDataStructure {

	/** The cache data structure. */
	private static SanctionCacheDataStructure cacheDataStructure = new SanctionCacheDataStructure();

	/** The provider init config map. */
	private SanctionCache providerInitConfigMap = new SanctionCache();

	/**
	 * Instantiates a new sanction cache data structure.
	 */
	private SanctionCacheDataStructure() {

	}

	/**
	 * Gets the single instance of SanctionCacheDataStructure.
	 *
	 * @return single instance of SanctionCacheDataStructure
	 */
	public static SanctionCacheDataStructure getInstance() {
		return cacheDataStructure;
	}

	/**
	 * Sets the sanction providerinit config map.
	 *
	 * @param properties
	 *            the properties
	 */
	public void setSanctionProviderinitConfigMap(Map<String, ProviderProperty> properties) {
		this.providerInitConfigMap.sanctionStoreAll(properties);
	}

	/**
	 * Gets the provider init config map.
	 *
	 * @param <T>
	 *            the generic type
	 * @param methodName
	 *            the method name
	 * @return the provider init config map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProviderInitConfigMap(String methodName) {
		return (T) this.providerInitConfigMap.sanctionRetrieve(methodName);
	}

}
