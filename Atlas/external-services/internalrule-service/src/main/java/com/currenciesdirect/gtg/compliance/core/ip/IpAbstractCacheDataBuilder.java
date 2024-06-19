/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;

/**
 * The Class IpAbstractCacheDataBuilder.
 *
 * @author manish
 */
public abstract class IpAbstractCacheDataBuilder {
	
	/** The ip cache data structure. */
	protected IpCacheDataStructure ipCacheDataStructure = IpCacheDataStructure
			.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws IpException the ip exception
	 */
	public abstract void loadCache() throws IpException;
	
	/**
	 * Gets the provider init config property.
	 *
	 * @param providerName the provider name
	 * @return the provider init config property
	 * @throws IpException the ip exception
	 */
	public abstract IpProviderProperty getProviderInitConfigProperty(String providerName) throws IpException;
	
	

	
}
