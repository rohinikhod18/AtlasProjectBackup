package com.currenciesdirect.gtg.compliance.transactionmonitoring.core;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.TransactionMonitoringProviderProperty;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.dbport.TransactionMonitoringDBServiceImpl;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Class FraugsterConcreteDataBuilder.
 *
 * @author manish
 */
public class TransactionMonitoringConcreteDataBuilder extends TransactionMonitoringAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringConcreteDataBuilder.class);

	/** The transaction monitoring concrete data builder. */
	private static TransactionMonitoringConcreteDataBuilder transactionMonitoringConcreteDataBuilder = null;

	/** The db service impl. */
	private IDBService dbServiceImpl = TransactionMonitoringDBServiceImpl.getInstance();

	/**
	 * Instantiates a new transaction monitoring concrete data builder.
	 */
	private TransactionMonitoringConcreteDataBuilder() {
		LOG.debug("TransactionMonitoringConcreteDataBuilder Contructor created");
	}

	/**
	 * Gets the single instance of TransactionMonitoringConcreteDataBuilder.
	 *
	 * @return single instance of TransactionMonitoringConcreteDataBuilder
	 */
	public static TransactionMonitoringConcreteDataBuilder getInstance() {
		if (transactionMonitoringConcreteDataBuilder == null) {
			transactionMonitoringConcreteDataBuilder = new TransactionMonitoringConcreteDataBuilder();
		}
		return transactionMonitoringConcreteDataBuilder;
	}

	/**
	 * Load cache.
	 */
	@Override
	public void loadCache() {
		try {
			ConcurrentMap<String, TransactionMonitoringProviderProperty> hm;
			hm = dbServiceImpl.getTransactionMonitoringProviderInitConfigProperty();
			cacheDataStructure.setTransactionMonitoringProviderinitConfigMap(hm);
		} catch (TransactionMonitoringException ipException) {
			LOG.error("Error in class TransactionMonitoringConcreteDataBuilder loadCache() :", ipException);
		} catch (Exception e) {
			LOG.error("Error in class TransactionMonitoringConcreteDataBuilder loadCache() :", e);
		}

	}

	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	@Override
	public TransactionMonitoringProviderProperty getProviderInitConfigProperty(String methodName)
			throws TransactionMonitoringException {
		TransactionMonitoringProviderProperty property = new TransactionMonitoringProviderProperty();
		try {
			property = cacheDataStructure.getProviderinitConfigMap(methodName);
		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return property;
	}

}
