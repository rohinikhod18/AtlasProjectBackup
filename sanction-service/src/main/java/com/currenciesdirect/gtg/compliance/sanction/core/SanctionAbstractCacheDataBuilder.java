 /*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionAbstractCacheDataBuilder.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Class SanctionAbstractCacheDataBuilder.
 *
 * @author manish
 */
public abstract class SanctionAbstractCacheDataBuilder {

	/** The cache data structure. */
	protected SanctionCacheDataStructure cacheDataStructure = SanctionCacheDataStructure.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws SanctionException the sanction exception
	 */
	public abstract void loadCache() throws SanctionException;

	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 * @throws SanctionException the sanction exception
	 */
	public abstract ProviderProperty getProviderInitConfigProperty(String methodName) throws SanctionException;

}
