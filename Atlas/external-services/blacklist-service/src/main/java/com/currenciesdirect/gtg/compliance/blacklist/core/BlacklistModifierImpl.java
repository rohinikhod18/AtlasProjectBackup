package com.currenciesdirect.gtg.compliance.blacklist.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.blacklist.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * @author Rajesh
 *
 */
public class BlacklistModifierImpl implements IBlacklistModifier {

	@SuppressWarnings("squid:S3077")
	private static volatile IBlacklistModifier iBlacklist = null;
	static final Logger LOG = LoggerFactory.getLogger(BlacklistModifierImpl.class);
	private IDBService iDBService = DBServiceImpl.getInstance();
	private IValidator<IRequest> iValidator = ValidatorImpl.getInstance();

	private BlacklistModifierImpl() {
	}

	public static IBlacklistModifier getInstance() {
		if (iBlacklist == null) {
			synchronized (BlacklistModifierImpl.class) {
				if (iBlacklist == null) {
					iBlacklist = new BlacklistModifierImpl();
				}
			}
		}
		return iBlacklist;
	}

	@Override
	public BlacklistModifierResponse saveIntoBlacklist(BlacklistRequest request) throws BlacklistException {
		LOG.info("BlacklistModifierImpl.saveIntoBlacklist : start");
		try {
			if (iValidator.validate(request)) {
				return iDBService.saveIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			logError(request.getCorrelationId(), null, null, blacklistException.getOrgException());
			throw blacklistException;
		} catch (Exception e) {
			logError(request.getCorrelationId(), null, null, e);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA);
		}
	}

	@Override
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest request) throws BlacklistException {
		LOG.info("BlacklistModifierImpl.updateIntoBlacklist : start");
		try {
			if (iValidator.validate(request)) {
				return iDBService.updateIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			logError(request.getCorrelationId(), null, null, blacklistException.getOrgException());
			throw blacklistException;
		} catch (Exception e) {
			logError(request.getCorrelationId(), null, null, e);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_UPDATING_DATA);
		}
	}

	@Override
	public BlacklistModifierResponse deleteFromBlacklist(BlacklistRequest request) throws BlacklistException {
		LOG.info("BlacklistModifierImpl.deleteFromBlacklist : start");
		try {
			if (iValidator.validate(request)) {
				return iDBService.deleteFromBacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}

		} catch (BlacklistException blacklistException) {
			logError(request.getCorrelationId(), null, null, blacklistException.getOrgException());
			throw blacklistException;
		} catch (Exception e) {
			logError(request.getCorrelationId(), null, null, e);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA);
		}
	}

	private void logError(String correlationId, String tradeAccountId, String tradeContachId, Throwable exception) {
		LOG.error("CorrelationId:" + correlationId + " " + "tradeAccountId:" + tradeAccountId + " " + "tradeContactId:"
				+ tradeContachId + " " + "Exception: ", exception);
	}

}
