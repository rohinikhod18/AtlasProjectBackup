package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;



public class BlacklistPayrefCacheDataStructure 
{
   private static BlacklistPayrefCacheDataStructure cacheDataStructure= new BlacklistPayrefCacheDataStructure();
   
   /**
	 * Instantiates a new  BlacklistPayref cache data structure.
	 */
   public BlacklistPayrefCacheDataStructure() {
		
   }  
   
   /**
	 * Gets the single instance of BlacklistPayrefCacheDataStructure.
	 *
	 * @return single instance of BlacklistPayrefCacheDataStructure
	 */
   
   static BlacklistPayrefCacheDataStructure getInstance()
   {
	   return cacheDataStructure;
   }
   
   /** The provider init config map. */
  private BlacklistPayrefCache providerInitConfigMap= new BlacklistPayrefCache();


  public void setBlacklistPayrefCache(ConcurrentMap<String,BlacklistPayrefProviderProperty> properties) {
		this.providerInitConfigMap.BlacklistPayrefStoreAll(properties);
	}
  
  
  
  /**
	 * Gets the providerinit config map.
	 *
	 * @param <T>
	 *            the generic type
	 * @param methodName
	 *            the method name
	 * @return the providerinit config map
	 */
  
  @SuppressWarnings("unchecked")
	public <T> T getProviderinitConfigMap(String methodName) {
		return (T) this.providerInitConfigMap.BlacklistPayrefRetrieve(methodName);
	}
   
}
