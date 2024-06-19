package com.currenciesdirect.gtg.compliance.customcheck.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateResponse;
import com.currenciesdirect.gtg.compliance.customcheck.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckErrors;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;

/**
 * The Class CustomCheckImpl.
 * 
 * @author abhijitg
 */
public class CustomCheckServiceImpl implements ICustomCheckService {
	
	static final Logger LOG = LoggerFactory.getLogger(CustomCheckServiceImpl.class);

	private static volatile  CustomCheckServiceImpl customCheckServiceImpl = null;

	private IDBService iDBService = DBServiceImpl.getInstance();

	private static CustomCheckCacheDataBuilder customCheckConcreteDataBuilder = CustomCheckCacheDataBuilder
			.getInstance();

	private CustomCheckServiceImpl() {
	}

	public static CustomCheckServiceImpl getInstance() {
		if (customCheckServiceImpl == null) {
			synchronized (CustomCheckServiceImpl.class) {
				if (customCheckServiceImpl == null) {
					customCheckServiceImpl = new CustomCheckServiceImpl();
				}
			}
		}
		return customCheckServiceImpl;
	}

	@Override
	public Boolean saveCustomCheckDetails(CustomCheckInsertRequest request) throws CustomCheckException {
		try {
			iDBService.saveCustomCheckDetails(request);
			customCheckConcreteDataBuilder.addToCache(request);
		} catch (CustomCheckException customCheckException) {
			LOG.error(" CorrelationID: " + request.getCorrelationId() + ", TradeAccountId:"
					+ request.getTradeAccountId() + ", TradeContactId : " + request.getTradeContactId()
					+ customCheckException.getDescription(), customCheckException.getOrgException());
			throw customCheckException;
		} catch (Exception e) {
			LOG.error("Error while saving data : saveCustomCheckDetails() --> CorrelationID: "
					+ request.getCorrelationId() + ", TradeAccountId:" + request.getTradeAccountId()
					+ ", TradeContactId : " + request.getTradeContactId(),e);
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INSERTING_DATA);
		}
		return true;
	}

	@Override
	public CustomCheckUpdateResponse updateCustomCheckDetails(CustomCheckUpdateRequest request) throws CustomCheckException {
		CustomCheckUpdateResponse response=null;
		try {
			response=iDBService.updateCustomCheckDetails(request);
			customCheckConcreteDataBuilder.updateToCache(request);
		} catch (CustomCheckException customCheckException) {
			LOG.error(" CorrelationID: " + request.getCorrelationId() + ", TradeAccountId:"
					+ request.getTradeAccountId() + ", TradeContactId : " + request.getTradeContactId()
					+ customCheckException.getDescription(), customCheckException.getOrgException());
			throw customCheckException;
		} catch (Exception e) {
			LOG.error("Error while updating data : updateCustomCheckDetails() --> CorrelationID: "
					+ request.getCorrelationId() + ", TradeAccountId:" + request.getTradeAccountId()
					+ ", TradeContactId : " + request.getTradeContactId(),e);
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_UPDATING_DATA);
		}
		return response;
	}

	@Override
	public Boolean deleteCustomCheckDetails(CustomCheckDeleteRequest request) throws CustomCheckException {
		try {
			iDBService.deleteCustomCheckDetails(request);
			customCheckConcreteDataBuilder.deleteKeyFromCache(request);
		} catch (CustomCheckException customCheckException) {
			LOG.error(" CorrelationID: " + request.getCorrelationId() + ", TradeAccountId:"
					+ request.getTradeAccountId() + ", TradeContactId : " + request.getTradeContactId()
					+ customCheckException.getDescription(), customCheckException.getOrgException());
			throw customCheckException;
		} catch (Exception e) {
			LOG.error("Error while deleting data : deleteCustomCheckDetails() --> CorrelationID: "
					+ request.getCorrelationId() + ", TradeAccountId:" + request.getTradeAccountId()
					+ ", TradeContactId : " + request.getTradeContactId(),e);
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_DELETING_DATA);
		}
		return true;
	}

	@Override
	public CustomCheckSearchResponse searchCustomCheckDetails(CustomCheckSearchRequest request)
			throws CustomCheckException {
		CustomCheckSearchResponse searchResponse;
		try {
			// CustomCheckSearchResponse customChecksearchResponse =
			// iCustomCheckServiceDao.searchCustomCheckDetails(request);
			searchResponse = customCheckConcreteDataBuilder.searchFromCache(request);
		} catch (CustomCheckException customCheckException) {
			LOG.error(" CorrelationID: " + request.getCorrelationId() + ", TradeAccountId:"
					+ request.getTradeAccountId() + ", TradeContactId : " + request.getTradeContactId()
					+ customCheckException.getDescription(), customCheckException.getOrgException());
			throw customCheckException;
		} catch (Exception e) {
			LOG.error("Error while searching data : searchCustomCheckDetails() --> CorrelationID: "
					+ request.getCorrelationId() + ", TradeAccountId:" + request.getTradeAccountId()
					+ ", TradeContactId : " + request.getTradeContactId(),e);
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_FROM_CACHE);
		}
		return searchResponse;
	}
}
