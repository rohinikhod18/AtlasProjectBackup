/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.IpException;






/**
 * @author manish
 * 
 */
public abstract class IpAbstractCacheDataBuilder {
	
	protected IpCacheDataStructure ipCacheDataStructure = IpCacheDataStructure
			.getInstance();

	/**
	 *
	 * @throws B2BException
	 */
	public abstract void loadCache() throws IpException;
	
	public abstract IpProviderProperty getProviderInitConfigProperty(String providerName) throws IpException;
	
	

	
}
