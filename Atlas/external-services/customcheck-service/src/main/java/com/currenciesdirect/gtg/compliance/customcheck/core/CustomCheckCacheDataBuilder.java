package com.currenciesdirect.gtg.compliance.customcheck.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSubScriptionDataStructure;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckErrors;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;

/**
 * The Class CustomCheckCacheDataBuilder.
 * 
 * @author abhijitg
 */
public class CustomCheckCacheDataBuilder {

	static final Logger LOG = LoggerFactory.getLogger(CustomCheckCacheDataBuilder.class);
	static CustomCheckSubScriptionDataStructure customCheckSubScriptionDataStructure = CustomCheckSubScriptionDataStructure
			.getInstance();

	private static volatile  CustomCheckCacheDataBuilder customCheckCacheDataBuilder = null;

	private static IDBService iCustomCheckServiceDao = DBServiceImpl.getInstance();

	private CustomCheckCacheDataBuilder() {
	}

	public static CustomCheckCacheDataBuilder getInstance() {
		if (customCheckCacheDataBuilder == null) {
			synchronized (CustomCheckCacheDataBuilder.class) {
				customCheckCacheDataBuilder = new CustomCheckCacheDataBuilder();
			}
		}
		return customCheckCacheDataBuilder;
	}

	static {

		try {
			loadCache();
		} catch (CustomCheckException customCheckException) {
			try {
				throw customCheckException;
			} catch (Exception e) {
				LOG.error("Error while initialising cache : static block", e);
			}
		}
	}

	/**
	 * Load cache.
	 *
	 * @throws CustomCheckException the custom check exception
	 */
	public static void loadCache() throws CustomCheckException {

		try {
			customCheckSubScriptionDataStructure.setCacheOccupation(iCustomCheckServiceDao.getAllFromOccupation());

			customCheckSubScriptionDataStructure
					.setCacheCountriesOfTrade(iCustomCheckServiceDao.getAllFromCountriesOfTrade());

			customCheckSubScriptionDataStructure
					.setCachePurposeOfTransactionMap(iCustomCheckServiceDao.getAllFromPurposeOfTransaction());

			customCheckSubScriptionDataStructure.setCacheSource(iCustomCheckServiceDao.getAllFromSourceOfLead());

			customCheckSubScriptionDataStructure.setCacheSourceOfFund(iCustomCheckServiceDao.getAllFromSourceOfFund());

			customCheckSubScriptionDataStructure
					.setCacheValueOfTransactionMap(iCustomCheckServiceDao.getAllFromValueOfTransaction());

		} catch (CustomCheckException customCheckException) {
			throw customCheckException;
		} catch (Exception e) {
			LOG.error("Error in class CustomCheckConcreteDataBuilder loadCache() :", e);
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INITIALISING_CACHE,e);
		}
	}

	
	/**
	 * Adds records to cache.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean addToCache(CustomCheckInsertRequest request) throws CustomCheckException {
		try {
		
				if (request.getOccupation() != null
						&& request.getOccupation().getDataField() != null
						&& request.getOccupation().getScore() != null) {

					customCheckSubScriptionDataStructure.addToOccupationMap(
							request.getOccupation().getDataField(),
							request.getOccupation().getScore());
				}
				if (request.getCountriesOfTrade() != null
						&& request.getCountriesOfTrade().getDataField() != null
						&& request.getCountriesOfTrade().getScore() != null) {
					customCheckSubScriptionDataStructure.addToCountriesOfTradeMap(
							request.getCountriesOfTrade().getDataField(),
							request.getCountriesOfTrade().getScore());
				}
				if (request.getPurposeOfTransaction() != null
						&& request.getPurposeOfTransaction().getDataField() != null
						&& request.getPurposeOfTransaction().getScore() != null) {

					customCheckSubScriptionDataStructure.addToPurposeOfTransactionMap(
							request.getPurposeOfTransaction().getDataField(),
							request.getPurposeOfTransaction().getScore());
				}
				if (request.getSourceOfLead() != null
						&& request.getSourceOfLead().getDataField() != null
						&& request.getSourceOfLead().getScore() != null) {
					customCheckSubScriptionDataStructure.addToSourceMap(
							request.getSourceOfLead().getDataField(),
							request.getSourceOfLead().getScore());
				}
				if (request.getSourceOfFund() != null
						&& request.getSourceOfFund().getDataField() != null
						&& request.getSourceOfFund().getScore() != null) {
					customCheckSubScriptionDataStructure.addToSourceOfFundMap(
							request.getSourceOfFund().getDataField(),
							request.getSourceOfFund().getScore());
				}
				if (request.getValueOfTransaction() != null
						&& request.getValueOfTransaction().getDataField() != null
						&& request.getValueOfTransaction().getScore() != null) {
					customCheckSubScriptionDataStructure.addToValueOfTransactionMap(
							request.getValueOfTransaction().getDataField(),
							request.getValueOfTransaction().getScore());
				}
			
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INSERTING_CACHE,e);
		}
		return true;
	}
	
	/**
	 * Update records to cache.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean updateToCache(CustomCheckUpdateRequest request) throws CustomCheckException {
		try {
		
				if (request.getOccupation() != null
						&& request.getOccupation().getDataField() != null
						&& request.getOccupation().getScore() != null) {

					customCheckSubScriptionDataStructure.addToOccupationMap(
							request.getOccupation().getDataField(),
							request.getOccupation().getScore());
				}
				if (request.getCountriesOfTrade() != null
						&& request.getCountriesOfTrade().getDataField() != null
						&& request.getCountriesOfTrade().getScore() != null) {
					customCheckSubScriptionDataStructure.addToCountriesOfTradeMap(
							request.getCountriesOfTrade().getDataField(),
							request.getCountriesOfTrade().getScore());
				}
				if (request.getPurposeOfTransaction() != null
						&& request.getPurposeOfTransaction().getDataField() != null
						&& request.getPurposeOfTransaction().getScore() != null) {

					customCheckSubScriptionDataStructure.addToPurposeOfTransactionMap(
							request.getPurposeOfTransaction().getDataField(),
							request.getPurposeOfTransaction().getScore());
				}
				if (request.getSourceOfLead() != null
						&& request.getSourceOfLead().getDataField() != null
						&& request.getSourceOfLead().getScore() != null) {
					customCheckSubScriptionDataStructure.addToSourceMap(
							request.getSourceOfLead().getDataField(),
							request.getSourceOfLead().getScore());
				}
				if (request.getSourceOfFund() != null
						&& request.getSourceOfFund().getDataField() != null
						&& request.getSourceOfFund().getScore() != null) {
					customCheckSubScriptionDataStructure.addToSourceOfFundMap(
							request.getSourceOfFund().getDataField(),
							request.getSourceOfFund().getScore());
				}
				if (request.getValueOfTransaction() != null
						&& request.getValueOfTransaction().getDataField() != null
						&& request.getValueOfTransaction().getScore() != null) {
					customCheckSubScriptionDataStructure.addToValueOfTransactionMap(
							request.getValueOfTransaction().getDataField(),
							request.getValueOfTransaction().getScore());
				}
			
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INSERTING_CACHE,e);
		}
		return true;
	}


	/**
	 * Delete key from cache.
	 *
	 * @param request the request
	 * @return the boolean
	 * @throws CustomCheckException the custom check exception
	 */
	public Boolean deleteKeyFromCache(CustomCheckDeleteRequest request) throws CustomCheckException {
		try {
				if (request.getOccupation() != null && !request.getOccupation().isEmpty()) {
					customCheckSubScriptionDataStructure
							.deleteKeyFromOccupationCache(request.getOccupation());
				}
				if (request.getCountriesOfTrade() != null && !request.getCountriesOfTrade().isEmpty()) {
					customCheckSubScriptionDataStructure.deleteKeyFromCountriesOfTradeCache(
							request.getCountriesOfTrade());
				}
				if (request.getPurposeOfTransaction() != null && !request.getPurposeOfTransaction().isEmpty()) {
					customCheckSubScriptionDataStructure.deleteKeyFromPurposeOfTransactionCache(
							request.getPurposeOfTransaction());
				}
				if (request.getSourceOfLead() != null && !request.getSourceOfLead().isEmpty()) {
					customCheckSubScriptionDataStructure
							.deleteKeyFromSourceCache(request.getSourceOfLead());
				}
				if (request.getSourceOfFund() != null && !request.getSourceOfFund().isEmpty()) {
					customCheckSubScriptionDataStructure
							.deleteKeyFromSourceOfFundCache(request.getSourceOfFund());

				}
				if (request.getValueOfTransaction() != null && !request.getValueOfTransaction().isEmpty()) {
					customCheckSubScriptionDataStructure.deleteKeyFromValueOfTransactionCache(
							request.getValueOfTransaction());
				}
			
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_DELETING_FROM_MEMORY_CACHE,e);
		}
		return true;
	}

	
	/**
	 * Search from cache.
	 *
	 * @param request the request
	 * @return the custom check search response
	 * @throws CustomCheckException the custom check exception
	 */
	public CustomCheckSearchResponse searchFromCache(CustomCheckSearchRequest request) throws CustomCheckException {
		LOG.info("CustomCheck Searching started : searchFromCache() ");
		Integer occupationScore = 0;
		Integer countriesOfTradeScore = 0;
		Integer purposeOfTransactionScore = 0;
		Integer valuesOfTRansactionScore = 0;
		Integer sourceOfFundScore = 0;
		Integer sourceOfLeadScore = 0;
		Integer totalScore = 0;

		CustomCheckSearchResponse searchResponse = new CustomCheckSearchResponse();
		searchResponse.setOccupation(0);
		searchResponse.setCountriesOfTrade(0);
		searchResponse.setPurposeOfTransaction(0);
		searchResponse.setSourceOfLead(0);
		searchResponse.setValueOfTransaction(0);
		searchResponse.setSourceOfFund(0);
		try {
			if (request != null) {
				if (request.getOccupation() != null && !request.getOccupation().isEmpty()) {
					occupationScore = customCheckSubScriptionDataStructure.searchByKeyFromOccupation(request.getOccupation());
					searchResponse.setOccupation(occupationScore);
				}
				if (request.getCountriesOfTrade() != null && !request.getCountriesOfTrade().isEmpty()) {
					countriesOfTradeScore = customCheckSubScriptionDataStructure.searchByKeyFromCountriesOfTrade(request.getCountriesOfTrade());
					searchResponse.setCountriesOfTrade(countriesOfTradeScore);
				}
				if (request.getPurposeOfTransaction() != null && !request.getPurposeOfTransaction().isEmpty()) {
					purposeOfTransactionScore = customCheckSubScriptionDataStructure.searchByKeyFromPurposeOfTransaction(
							request.getPurposeOfTransaction());
					searchResponse.setPurposeOfTransaction(purposeOfTransactionScore);
				}
				if (request.getSourceOfLead() != null && !request.getSourceOfLead().isEmpty()) {
					sourceOfLeadScore = customCheckSubScriptionDataStructure
							.searchByKeyFromSource(request.getSourceOfLead());
					searchResponse.setSourceOfLead(sourceOfLeadScore);
				}
				if (request.getSourceOfFund() != null && !request.getSourceOfFund().isEmpty()) {
					sourceOfFundScore = customCheckSubScriptionDataStructure
							.searchByKeyFromSourceOfFund(request.getSourceOfFund());
					searchResponse.setSourceOfFund(sourceOfFundScore);
				}
				if (request.getValueOfTransaction() != null && !request.getValueOfTransaction().isEmpty()) {
					valuesOfTRansactionScore = customCheckSubScriptionDataStructure.searchByKeyFromValueOfTransaction(
							request.getValueOfTransaction());

					searchResponse.setValueOfTransaction(valuesOfTRansactionScore);
				}
			}

			totalScore = occupationScore + countriesOfTradeScore + purposeOfTransactionScore + valuesOfTRansactionScore
					+ sourceOfFundScore + sourceOfLeadScore + totalScore;
			searchResponse.setTotal(totalScore);
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_FROM_CACHE,e);
		}
		return searchResponse;

	}
}
