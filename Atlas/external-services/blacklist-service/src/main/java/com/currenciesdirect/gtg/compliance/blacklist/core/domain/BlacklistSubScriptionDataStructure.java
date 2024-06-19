package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

import java.util.Map;
import java.util.Set;

import com.currenciesdirect.gtg.compliance.blacklist.core.BlacklistCache;


/**
 * @author Rajesh
 *
 */
public class BlacklistSubScriptionDataStructure {

	private static volatile BlacklistSubScriptionDataStructure blacklistSubScriptionDataStructure = null;
	
	private BlacklistCache<String,Integer> blacklistTypeMap = new BlacklistCache<>();

	private BlacklistSubScriptionDataStructure() {

	}

	public static BlacklistSubScriptionDataStructure getInstance() {
		if (blacklistSubScriptionDataStructure == null) {

			synchronized (BlacklistSubScriptionDataStructure.class) {

				if (blacklistSubScriptionDataStructure == null) {

					blacklistSubScriptionDataStructure = new BlacklistSubScriptionDataStructure();
				}
			}
		}
		return blacklistSubScriptionDataStructure;
	}

	/**
	 * Sets the blaklist types.
	 *
	 * @param blacklistTypes the blacklist types
	 */
	public void setBlaklistTypes(Map<String, Integer> blacklistTypes) {
		this.blacklistTypeMap.storeAll(blacklistTypes);
	}

	

	/**
	 * Adds the blacklist type to map.
	 *
	 * @param type the type
	 * @param relevence the relevence
	 */
	public void addBlacklistTypeToMap(String type, Integer relevence) {
		this.blacklistTypeMap.store(type, relevence);
	}

	/**
	 * Update blacklist type to map.
	 *
	 * @param type the type
	 * @param relevence the relevence
	 */
	public void updateBlacklistTypeToMap(String type, Integer relevence) {
		this.blacklistTypeMap.store(type, relevence);
	}

	/**
	 * Search relevence.
	 *
	 * @param type the type
	 * @return the integer
	 */
	public Integer searchRelevence(String type) {
		return  this.blacklistTypeMap.retrieve(type);
	}

	/**
	 * Delete type from blacklist type cache.
	 *
	 * @param type the type
	 * @return true, if successful
	 */
	public boolean deleteTypeFromBlacklistTypeCache(String type) {
		return this.blacklistTypeMap.remove(type);
	}

	/**
	 * Gets the all blacklist types.
	 *
	 * @return the all blacklist types
	 */
	public Map<String, Integer> getAllBlacklistTypes() {
		return this.blacklistTypeMap.getAll();
	}
	
	/**
	 * Gets the all blacklist types keys.
	 *
	 * @return the all blacklist types keys
	 */
	public Set<String> getAllBlacklistTypesKeys() {
		return this.blacklistTypeMap.getAllKeys();
	}
	
	/**
	 * Checks if is type present.
	 *
	 * @param type the type
	 * @return true, if is type present
	 */
	public boolean isTypePresent(String type){
		return this.blacklistTypeMap.containsKey(type);
	}

	
}
