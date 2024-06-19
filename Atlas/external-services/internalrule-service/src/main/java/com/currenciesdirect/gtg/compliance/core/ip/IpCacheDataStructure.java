package com.currenciesdirect.gtg.compliance.core.ip;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;




/**
 * @author manish
 * 
 */
public class IpCacheDataStructure {
	

	private static IpCacheDataStructure ipCacheDataStructure = new IpCacheDataStructure();
	private IpCache providerInitConfigMap = new IpCache();

	private IpCacheDataStructure() {

	}

	public static IpCacheDataStructure getInstance() {
		return ipCacheDataStructure;
	}
	
	public void setIpProviderinitConfigMap(Map<String, IpProviderProperty> properties) {
		this.providerInitConfigMap.ipStoreAll(properties);
	}
	
	/**
	 * Gets the provider init config map.
	 *
	 * @param <T> the generic type
	 * @param providerName the provider name
	 * @return the provider init config map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProviderInitConfigMap(String providerName) {
	 return	(T)this.providerInitConfigMap.ipRetrieve(providerName);
	}
	
    
    

}
