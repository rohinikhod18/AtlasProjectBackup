package com.currenciesdirect.gtg.compliance.core;

import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;



/**
 * @author manish
 * 
 */
public class IpCacheDataStructure {
	

	private static IpCacheDataStructure ipCacheDataStructure = new IpCacheDataStructure();

	private IpCacheDataStructure() {

	}

	public static IpCacheDataStructure getInstance() {
		return ipCacheDataStructure;
	}

	
	
	
	private IpCache providerInitConfigMap = new IpCache();

	
	public void setIpProviderinitConfigMap(ConcurrentHashMap<String, IpProviderProperty> properties) {
		this.providerInitConfigMap.storeAll(properties);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T GetProviderinitConfigMap(String providerName) {
	 return	(T)this.providerInitConfigMap.retrieve(providerName);
	}
	
    
    

}
