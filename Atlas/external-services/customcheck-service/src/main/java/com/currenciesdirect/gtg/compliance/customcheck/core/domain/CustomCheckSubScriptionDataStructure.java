package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.customcheck.core.CustomCheckCache;

/**
 * The Class CustomCheckSubScriptionDataStructure.
 */
public class CustomCheckSubScriptionDataStructure {

	private volatile static CustomCheckSubScriptionDataStructure customCheckSubScriptionDataStructure = null;

	private CustomCheckSubScriptionDataStructure() {

	}

	public static CustomCheckSubScriptionDataStructure getInstance() {
		if (customCheckSubScriptionDataStructure == null) {

			synchronized (CustomCheckSubScriptionDataStructure.class) {

				if (customCheckSubScriptionDataStructure == null) {

					customCheckSubScriptionDataStructure = new CustomCheckSubScriptionDataStructure();
				}
			}
		}
		return customCheckSubScriptionDataStructure;
	}

	private CustomCheckCache<Integer> occupationMap = new CustomCheckCache<Integer>();

	private CustomCheckCache<Integer> sourceMap = new CustomCheckCache<Integer>();

	private CustomCheckCache<Integer> sourceOfFundMap = new CustomCheckCache<Integer>();

	private CustomCheckCache<Integer> valueOfTransactionMap = new CustomCheckCache<Integer>();

	private CustomCheckCache<Integer> coutriesOfTradeMap = new CustomCheckCache<Integer>();

	private CustomCheckCache<Integer> purposeOfTransactionMap = new CustomCheckCache<Integer>();

	public void setCacheOccupation(ConcurrentHashMap<String, Integer> cacheOccupation) {
		this.occupationMap.storeAll(cacheOccupation);
	}

	public void setCacheCountriesOfTrade(ConcurrentHashMap<String, Integer> cacheCoutriesOfTrade) {
		this.coutriesOfTradeMap.storeAll(cacheCoutriesOfTrade);
	}

	public void setCacheSource(ConcurrentHashMap<String, Integer> cacheSource) {
		this.sourceMap.storeAll(cacheSource);
	}

	public void setCacheSourceOfFund(ConcurrentHashMap<String, Integer> cacheSourceOfFund) {
		this.sourceOfFundMap.storeAll(cacheSourceOfFund);
	}

	public void setCacheValueOfTransactionMap(ConcurrentHashMap<String, Integer> cacheValueOfTransaction) {
		this.valueOfTransactionMap.storeAll(cacheValueOfTransaction);
	}

	public void setCachePurposeOfTransactionMap(ConcurrentHashMap<String, Integer> cachePurposeOfTransaction) {
		this.purposeOfTransactionMap.storeAll(cachePurposeOfTransaction);
	}

	/**
	 * Adds the to occupation map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToOccupationMap(String key, Integer score) {
		this.occupationMap.store(key, score);
	}

	/**
	 * Update to occupation map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updateToOccupationMap(String key, Integer score) {
		this.occupationMap.store(key, score);
	}

	/**
	 * Search by key from occupation.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public Integer searchByKeyFromOccupation(String key) {
		return  this.occupationMap.retrieve(key);
	}

	/**
	 * Delete key from occupation cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromOccupationCache(String key) {
		this.occupationMap.remove(key);
	}

	/**
	 * Gets the occupation map.
	 *
	 * @return the occupation map
	 */
	public ConcurrentHashMap<String, Integer> getOccupationMap() {
		return this.occupationMap.getAll();
	}

	/**
	 * Gets the occupation score by key.
	 *
	 * @param key the key
	 * @return the occupation score by key
	 */
	public Integer getOccupationScoreByKey(String key) {
		return this.occupationMap.retrieve(key);
	}

	/**
	 * Contains key in occupation map.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public Boolean containsKeyInOccupationMap(String key) {
		Boolean contains = this.occupationMap.contains(key);
		return contains;
	}

	/**
	 * Adds the to countries of trade map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToCountriesOfTradeMap(String key, Integer score) {
		this.coutriesOfTradeMap.store(key, score);
	}

	/**
	 * Update countries of trade map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updateCountriesOfTradeMap(String key, Integer score) {
		this.coutriesOfTradeMap.store(key, score);
	}

	/**
	 * Search by key from countries of trade.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public Integer searchByKeyFromCountriesOfTrade(String key) {
		return  this.coutriesOfTradeMap.retrieve(key);
	}

	/**
	 * Delete key from countries of trade cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromCountriesOfTradeCache(String key) {
		this.coutriesOfTradeMap.remove(key);
	}

	/**
	 * Gets the countries of trade map.
	 *
	 * @return the countries of trade map
	 */
	public ConcurrentHashMap<String, Integer> getCountriesOfTradeMap() {
		return this.coutriesOfTradeMap.getAll();
	}

	/**
	 * Gets the countries of trade score by key.
	 *
	 * @param key the key
	 * @return the countries of trade score by key
	 */
	public Integer getCountriesOfTradeScoreByKey(String key) {
		return this.coutriesOfTradeMap.retrieve(key);
	}

	/**
	 * Contains key in countries of trade map.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKeyInCountriesOfTradeMap(String key) {
		boolean contains = this.coutriesOfTradeMap.contains(key);
		return contains;
	}

	/**
	 * Adds the to value of transaction map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToValueOfTransactionMap(String key, Integer score) {
		this.valueOfTransactionMap.store(key, score);
	}

	/**
	 * Update value of transaction map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updateValueOfTransactionMap(String key, Integer score) {
		this.valueOfTransactionMap.store(key, score);
	}

	/**
	 * Search by key from value of transaction.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public  Integer searchByKeyFromValueOfTransaction(String key) {
		return  this.valueOfTransactionMap.retrieve(key);
	}

	/**
	 * Delete key from value of transaction cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromValueOfTransactionCache(String key) {
		this.valueOfTransactionMap.remove(key);
	}

	/**
	 * Gets the value of transaction.
	 *
	 * @return the value of transaction
	 */
	public ConcurrentHashMap<String, Integer> getValueOfTransaction() {
		return this.valueOfTransactionMap.getAll();
	}

	/**
	 * Gets the value of transaction score by key.
	 *
	 * @param key the key
	 * @return the value of transaction score by key
	 */
	public Integer getValueOfTransactionScoreByKey(String key) {
		return this.valueOfTransactionMap.retrieve(key);
	}

	/**
	 * Contains key in value of transaction map.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	public Boolean containsKeyInValueOfTransactionMap(String key) {
		Boolean contains = this.valueOfTransactionMap.contains(key);
		return contains;
	}


	/**
	 * Adds the to purpose of transaction map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToPurposeOfTransactionMap(String key, Integer score) {
		this.purposeOfTransactionMap.store(key, score);
	}

	/**
	 * Update purpose of transaction map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updatePurposeOfTransactionMap(String key, Integer score) {
		this.purposeOfTransactionMap.store(key, score);
	}

	/**
	 * Search by key from purpose of transaction.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public Integer searchByKeyFromPurposeOfTransaction(String key) {
		return  this.purposeOfTransactionMap.retrieve(key);
	}

	/**
	 * Delete key from purpose of transaction cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromPurposeOfTransactionCache(String key) {
		this.purposeOfTransactionMap.remove(key);
	}

	/**
	 * Gets the purpose of transaction.
	 *
	 * @return the purpose of transaction
	 */
	public ConcurrentHashMap<String, Integer> getPurposeOfTransaction() {
		return this.purposeOfTransactionMap.getAll();
	}

	/**
	 * Gets the purpose of transaction score by key.
	 *
	 * @param key the key
	 * @return the purpose of transaction score by key
	 */
	public Integer getPurposeOfTransactionScoreByKey(String key) {
		return this.purposeOfTransactionMap.retrieve(key);
	}

	/**
	 * Contains key in purpose of transaction map.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKeyInPurposeOfTransactionMap(String key) {
		boolean contains = this.purposeOfTransactionMap.contains(key);
		return contains;
	}

	/**
	 * Adds the to source of fund map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToSourceOfFundMap(String key, Integer score) {
		this.sourceOfFundMap.store(key, score);
	}

	/**
	 * Update p source of fund map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updatePSourceOfFundMap(String key, Integer score) {
		this.sourceOfFundMap.store(key, score);
	}

	/**
	 * Search by key from source of fund.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public Integer searchByKeyFromSourceOfFund(String key) {
		return this.sourceOfFundMap.retrieve(key);
	}

	/**
	 * Delete key from source of fund cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromSourceOfFundCache(String key) {
		this.sourceOfFundMap.remove(key);
	}

	/**
	 * Gets the source of fund.
	 *
	 * @return the source of fund
	 */
	public ConcurrentHashMap<String, Integer> getSourceOfFund() {
		return this.sourceOfFundMap.getAll();
	}

	/**
	 * Gets the source of fund score by key.
	 *
	 * @param key the key
	 * @return the source of fund score by key
	 */
	public Integer getSourceOfFundScoreByKey(String key) {
		return this.sourceOfFundMap.retrieve(key);
	}

	/**
	 * Contains key in source of fund map.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKeyInSourceOfFundMap(String key) {
		boolean contains = this.sourceOfFundMap.contains(key);
		return contains;
	}

	// Source Cache

	/**
	 * Adds the to source map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void addToSourceMap(String key, Integer score) {
		this.sourceMap.store(key, score);
	}

	/**
	 * Update p source map.
	 *
	 * @param key the key
	 * @param score the score
	 */
	public void updatePSourceMap(String key, Integer score) {
		this.sourceMap.store(key, score);
	}

	/**
	 * Search by key from source.
	 *
	 * @param key the key
	 * @return the integer
	 */
	public Integer searchByKeyFromSource(String key) {
		return  this.sourceMap.retrieve(key);
	}

	/**
	 * Delete key from source cache.
	 *
	 * @param key the key
	 */
	public void deleteKeyFromSourceCache(String key) {
		this.sourceMap.remove(key);
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public ConcurrentHashMap<String, Integer> getSource() {
		return this.sourceMap.getAll();
	}

	/**
	 * Gets the source score by key.
	 *
	 * @param key the key
	 * @return the source score by key
	 */
	public Integer getSourceScoreByKey(String key) {
		return this.sourceMap.retrieve(key);
	}

	/**
	 * Contains key in source map.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	public Boolean containsKeyInSourceMap(String key) {
		Boolean contains = this.sourceMap.contains(key);
		return contains;
	}
}
