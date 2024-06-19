package com.currenciesdirect.gtg.compliance.core.blacklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.IValidator;
import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.dbport.blacklist.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.currenciesdirect.gtg.compliance.validator.blacklist.BlacklistValidatorImpl;

/**
 * @author Rajesh
 *
 */
public class BlacklistSearchImpl implements IBlacklistSearch {

	@SuppressWarnings("squid:S3077")
	private static volatile IBlacklistSearch iBlacklistSearch = null;
	private static final Logger LOG = LoggerFactory.getLogger(BlacklistSearchImpl.class);
	private IDBService iDBService = DBServiceImpl.getInstance();
	private IValidator<IRequest> iValidator = BlacklistValidatorImpl.getInstance();

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
	public BlacklistContactResponse stpSearchFromBlacklist(BlacklistSTPRequest request) throws BlacklistException {
		LOG.debug("BlacklistSearchImpl.scoreFromBlacklist : start");
		BlacklistContactResponse response = new BlacklistContactResponse();
		try {
			if (Boolean.TRUE.equals(iValidator.validate(request))) {
				response = iDBService.stpSearchIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			throw blacklistException;
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return response;
	}

	public InternalServiceResponse uiSearchFromBlacklist(InternalServiceRequest request) throws BlacklistException {
		LOG.debug("BlacklistSearchImpl.searchFromBlacklist : start");
		InternalServiceResponse response = null;
		try {
				response = iDBService.uiSearchIntoBlacklist(request);
		} catch (BlacklistException blacklistException) {
			String logData = "uiSearchFromBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw blacklistException;
		} catch (Exception e) {
			String logData = "uiSearchFromBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA, e);
		}
		return response;
	}

}
