/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The Class TransactionMonitoringCache.
 */
public class TransactionMonitoringCache {

	/** The transaction monitoring proprty cache. */
	private final ConcurrentMap<String, Object> transactionMonitoringProprtyCache = new ConcurrentHashMap<>();

	/**
	 * Transaction monitoring store.
	 *
	 * @param <T>                       the generic type
	 * @param transactionMonitoringKey  the transaction monitoring key
	 * @param transactionMonitoringData the transaction monitoring data
	 */
	public <T extends Object> void transactionMonitoringStore(String transactionMonitoringKey,
			T transactionMonitoringData) {
		transactionMonitoringProprtyCache.put(transactionMonitoringKey, transactionMonitoringData);
	}

	/**
	 * Transaction monitoring store all.
	 *
	 * @param <T>                the generic type
	 * @param providerCountryMap the provider country map
	 */
	public <T extends Object> void transactionMonitoringStoreAll(ConcurrentMap<String, T> providerCountryMap) {
		transactionMonitoringProprtyCache.putAll(providerCountryMap);
	}

	/**
	 * Transaction monitoring contains.
	 *
	 * @param transactionMonitoringKey the transaction monitoring key
	 * @return true, if successful
	 */
	public boolean transactionMonitoringContains(String transactionMonitoringKey) {
		return transactionMonitoringProprtyCache.get(transactionMonitoringKey) != null;
	}

	/**
	 * Transaction monitoring retrieve.
	 *
	 * @param transactionMonitoringKey the transaction monitoring key
	 * @return the object
	 */
	public Object transactionMonitoringRetrieve(String transactionMonitoringKey) {
		return transactionMonitoringProprtyCache.get(transactionMonitoringKey);
	}

	/**
	 * Transaction monitoring get all.
	 *
	 * @return the concurrent map
	 */
	public ConcurrentMap<String, Object> transactionMonitoringGetAll() {
		return transactionMonitoringProprtyCache;
	}

	/**
	 * Transaction monitoring remove.
	 *
	 * @param transactionMonitoringKey the transaction monitoring key
	 */
	public void transactionMonitoringRemove(String transactionMonitoringKey) {
		transactionMonitoringProprtyCache.remove(transactionMonitoringKey);
	}

}
