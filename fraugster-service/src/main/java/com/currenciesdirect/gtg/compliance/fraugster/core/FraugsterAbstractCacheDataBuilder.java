/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Class FraugsterAbstractCacheDataBuilder.
 *
 * @author manish
 */
public abstract class FraugsterAbstractCacheDataBuilder {

	/** The cache data structure. */
	protected FraugsterCacheDataStructure cacheDataStructure = FraugsterCacheDataStructure.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws FraugsterException
	 *             the fraugster exception
	 */
	public abstract void loadCache() throws FraugsterException;

	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName
	 *            the method name
	 * @return the provider init config property
	 * @throws FraugsterException
	 *             the fraugster exception
	 */
	public abstract FraugsterProviderProperty getProviderInitConfigProperty(String methodName)
			throws FraugsterException;

}
