package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;


public abstract class BlacklistPayrefAbstractcacheDatabuilder 
{
	protected BlacklistPayrefCacheDataStructure cacheDataStructure= BlacklistPayrefCacheDataStructure.getInstance();

	
	public abstract void loadCache() throws BlacklistException;
	
	
	public abstract BlacklistPayrefProviderProperty getProviderInitConfigProperty(String methodName)
			throws BlacklistException;
			
}
