package com.currenciesdirect.gtg.compliance.core.loadcache;

/**
 * The Class FraudSightAbstractCacheDataBuilder.
 */
public abstract class FraudSightAbstractCacheDataBuilder {
	/** The cache data structure. */
	protected FraudSightCacheDataStructure cacheDataStructure = FraudSightCacheDataStructure.getInstance();

	
	/**
	 * Load cache.
	 */
	public abstract void loadCache() ;

	
	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 */
	public abstract FraudSightProviderProperty getProviderInitConfigProperty(String methodName);
		
}
