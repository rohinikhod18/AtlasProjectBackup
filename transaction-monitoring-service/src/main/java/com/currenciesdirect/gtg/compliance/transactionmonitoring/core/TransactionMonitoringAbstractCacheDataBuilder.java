/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Class TransactionMonitoringAbstractCacheDataBuilder.
 */
public abstract class TransactionMonitoringAbstractCacheDataBuilder {

	/** The cache data structure. */
	protected TransactionMonitoringCacheDataStructure cacheDataStructure = TransactionMonitoringCacheDataStructure
			.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public abstract void loadCache() throws TransactionMonitoringException;

	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public abstract TransactionMonitoringProviderProperty getProviderInitConfigProperty(String methodName)
			throws TransactionMonitoringException;

}
