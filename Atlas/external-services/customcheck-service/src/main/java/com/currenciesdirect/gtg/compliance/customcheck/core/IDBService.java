package com.currenciesdirect.gtg.compliance.customcheck.core;

import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateResponse;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;

/**
 * The Interface IDBService.
 */
public interface IDBService {
	
	/**
	 * Save custom check details.
	 *
	 * @param CustomCheckInsertRequest the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean saveCustomCheckDetails(CustomCheckInsertRequest request) throws CustomCheckException ;
	
	/**
	 * Update custom check details.
	 *
	 * @param CustomCheckUpdateRequest the custom check request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public CustomCheckUpdateResponse updateCustomCheckDetails(CustomCheckUpdateRequest request)throws CustomCheckException;
	
	/**
	 * Delete custom check details.
	 *
	 * @param CustomCheckDeleteRequest the custom check request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean deleteCustomCheckDetails(CustomCheckDeleteRequest request)throws CustomCheckException;
	
	/**
	 * Search custom check details.
	 *
	 * @param CustomCheckSearchRequest the custom check request
	 * @return the custom check response
	 * @throws CustomCheckException the custom check exception
	 */
//	public CustomCheckSearchResponse searchCustomCheckDetails(CustomCheckSearchRequest request)throws CustomCheckException;

	/**
	 * Gets the all from occupation.
	 *
	 * @return the all from occupation
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String,Integer> getAllFromOccupation() throws CustomCheckException;
	
	/**
	 * Gets the all from source.
	 *
	 * @return the all from source
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String, Integer> getAllFromSourceOfLead()throws CustomCheckException;
	
	/**
	 * Gets the all from source of fund.
	 *
	 * @return the all from source of fund
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String, Integer> getAllFromSourceOfFund()throws CustomCheckException;
	
	/**
	 * Gets the all from value of transaction.
	 *
	 * @return the all from value of transaction
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String, Integer> getAllFromValueOfTransaction()throws CustomCheckException;
	
	/**
	 * Gets the all from purpose of transaction.
	 *
	 * @return the all from purpose of transaction
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String, Integer> getAllFromPurposeOfTransaction()throws CustomCheckException;
	
	/**
	 * Gets the all from countries of trade.
	 *
	 * @return the all from countries of trade
	 * @throws CustomCheckException the custom check exception
	 */
	public ConcurrentHashMap<String, Integer> getAllFromCountriesOfTrade()throws CustomCheckException;

}
