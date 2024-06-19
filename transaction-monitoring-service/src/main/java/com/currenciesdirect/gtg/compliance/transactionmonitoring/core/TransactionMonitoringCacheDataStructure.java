package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;

/**
 * The Class TransactionMonitoringCacheDataStructure.
 */
public class TransactionMonitoringCacheDataStructure {

	/** The cache data structure. */
	private static TransactionMonitoringCacheDataStructure cacheDataStructure = new TransactionMonitoringCacheDataStructure();

	/**
	 * Instantiates a new transaction monitoring cache data structure.
	 */
	private TransactionMonitoringCacheDataStructure() {

	}

	/**
	 * Gets the single instance of TransactionMonitoringCacheDataStructure.
	 *
	 * @return single instance of TransactionMonitoringCacheDataStructure
	 */
	public static TransactionMonitoringCacheDataStructure getInstance() {
		return cacheDataStructure;
	}

	/** The provider init config map. */
	private TransactionMonitoringCache providerInitConfigMap = new TransactionMonitoringCache();

	/**
	 * Sets the transaction monitoring providerinit config map.
	 *
	 * @param properties the properties
	 */
	public void setTransactionMonitoringProviderinitConfigMap(
			ConcurrentMap<String, TransactionMonitoringProviderProperty> properties) {
		this.providerInitConfigMap.transactionMonitoringStoreAll(properties);
	}

	/**
	 * Gets the providerinit config map.
	 *
	 * @param <T>        the generic type
	 * @param methodName the method name
	 * @return the providerinit config map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProviderinitConfigMap(String methodName) {
		return (T) this.providerInitConfigMap.transactionMonitoringRetrieve(methodName);
	}

}
