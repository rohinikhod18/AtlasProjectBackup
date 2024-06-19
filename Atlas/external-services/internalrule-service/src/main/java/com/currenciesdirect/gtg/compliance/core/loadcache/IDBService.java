package com.currenciesdirect.gtg.compliance.core.loadcache;

import java.util.concurrent.ConcurrentMap;

/**
 * The Interface IDBService.
 */
public interface IDBService {

	/**
	 * Gets the fraud sight provider init config property.
	 *
	 * @return the fraud sight provider init config property
	 */
	public ConcurrentMap<String, FraudSightProviderProperty> getFraudSightProviderInitConfigProperty();
}
