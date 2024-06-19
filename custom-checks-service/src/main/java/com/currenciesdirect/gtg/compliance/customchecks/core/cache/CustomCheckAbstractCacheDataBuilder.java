/**
 * 
 */
package com.currenciesdirect.gtg.compliance.customchecks.core.cache;

import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomCheckAbstractCacheDataBuilder.
 *
 * @author abhijeetg
 */
public abstract class CustomCheckAbstractCacheDataBuilder {

	/** The cache data structure. */
	protected CustomCheckCacheDataStructure cacheDataStructure = CustomCheckCacheDataStructure.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws CustomChecksException the custom checks exception
	 */
	public abstract void loadCache() throws CustomChecksException;


	/**
	 * Gets the velocity rules map.
	 *
	 * @param methodName the method name
	 * @return the velocity rules map
	 * @throws CustomChecksException the custom checks exception
	 */
	public abstract CustomCheckVelocityRules getVelocityRulesMap(String methodName) throws CustomChecksException;

}
