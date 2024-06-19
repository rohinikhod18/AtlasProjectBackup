package com.currenciesdirect.gtg.compliance.blacklist.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUISearchResponse;
import com.currenciesdirect.gtg.compliance.blacklist.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * @author Rajesh
 *
 */
public class BlacklistSearchImpl implements IBlacklistSearch {

	@SuppressWarnings("squid:S3077")
	private static volatile IBlacklistSearch iBlacklistSearch = null;
	static final Logger LOG = LoggerFactory.getLogger(BlacklistSearchImpl.class);
	private IDBService iDBService = DBServiceImpl.getInstance();
	private IValidator<IRequest> iValidator = ValidatorImpl.getInstance();

	private BlacklistSearchImpl() {
	}

	public static IBlacklistSearch getInstance() {
		if (iBlacklistSearch == null) {
			synchronized (BlacklistSearchImpl.class) {
				if (iBlacklistSearch == null) {
					iBlacklistSearch = new BlacklistSearchImpl();
				}
			}
		}
		return iBlacklistSearch;
	}

	@Override
	public BlacklistSTPResponse stpSearchFromBlacklist(BlacklistSTPRequest request) throws BlacklistException {
		LOG.info("BlacklistSearchImpl.scoreFromBlacklist : start");
		BlacklistSTPResponse response = null;
		try {
			if (iValidator.validate(request)) {
				response = iDBService.stpSearchIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			logError(request.getCorrelationId(), null, null, blacklistException.getOrgException());
			throw blacklistException;
		} catch (Exception e) {
			logError(request.getCorrelationId(), null, null, e);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return response;
	}

	@Override
	public BlacklistUISearchResponse uiSearchFromBlacklist(BlacklistUISearchRequest request) throws BlacklistException {
		LOG.info("BlacklistSearchImpl.searchFromBlacklist : start");
		BlacklistUISearchResponse response = null;
		try {
			if (iValidator.validate(request)) {
				response = iDBService.uiSearchIntoBlacklist(request);
				LOG.info(response.toString());
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			logError(request.getCorrelationId(), null, null, blacklistException.getOrgException());
			throw blacklistException;
		} catch (Exception e) {
			logError(request.getCorrelationId(), null, null, e);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return response;
	}

	private void logError(String correlationId, String tradeAccountId, String tradeContachId, Throwable exception) {
		LOG.error("CorrelationId:" + correlationId + " " + "tradeAccountId:" + tradeAccountId + " " + "tradeContactId:"
				+ tradeContachId + " " + "Exception: ", exception);
	}

}
