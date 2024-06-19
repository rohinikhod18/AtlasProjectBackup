package com.currenciesdirect.gtg.compliance.customchecks.core.cache;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckFraudPredictRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;

/**
 * The Class CustomCheckCacheDataStructure.
 *
 * @author abhijeetg
 */
public class CustomCheckCacheDataStructure {

	/** The cache data structure. */
	private static CustomCheckCacheDataStructure cacheDataStructure = new CustomCheckCacheDataStructure();

	/** The velocity rules map map. */
	private CustomCheckCache velocityRulesMapMap = new CustomCheckCache();
	
	/** The fraud predict rules map. */
	private CustomCheckCache fraudPredictRulesMap = new CustomCheckCache();
	
	/**
	 * Instantiates a new custom check cache data structure.
	 */
	private CustomCheckCacheDataStructure() {

	}

	/**
	 * Gets the single instance of CustomCheckCacheDataStructure.
	 *
	 * @return single instance of CustomCheckCacheDataStructure
	 */
	public static CustomCheckCacheDataStructure getInstance() {
		return cacheDataStructure;
	}

	/**
	 * Sets the velocity rules map.
	 *
	 * @param properties the properties
	 */
	public void setVelocityRulesMap(Map<String, CustomCheckVelocityRules> properties) {
		this.velocityRulesMapMap.customchecksStoreAll(properties);
	}

	/**
	 * Gets the velocity rules map.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @return the velocity rules map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getVelocityRulesMap(String key) {
		return (T) this.velocityRulesMapMap.customchecksRetrieve(key);
	}
	
	/**
	 * Sets the fraud predict rules map.
	 *
	 * @param properties the properties
	 */
	public void setFraudPredictRulesMap(Map<String, CustomCheckFraudPredictRules> properties) {
		this.fraudPredictRulesMap.customchecksStoreAll(properties);
	}
	
	/**
	 * Gets the fraud predict rules map.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @return the fraud predict rules map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFraudPredictRulesMap(String key) {
		return (T) this.fraudPredictRulesMap.customchecksRetrieve(key);
	}

}
