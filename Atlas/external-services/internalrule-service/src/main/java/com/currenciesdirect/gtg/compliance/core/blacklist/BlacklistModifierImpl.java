package com.currenciesdirect.gtg.compliance.core.blacklist;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.IValidator;
import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.blacklist.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;
import com.currenciesdirect.gtg.compliance.validator.blacklist.BlacklistValidatorImpl;

/**
 * The Class BlacklistModifierImpl.
 *
 * @author Rajesh
 */
public class BlacklistModifierImpl implements IBlacklistModifier {

	/** The i blacklist. */
	@SuppressWarnings("squid:S3077")
	private static volatile IBlacklistModifier iBlacklist = null;
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BlacklistModifierImpl.class);
	
	/** The i DB service. */
	private IDBService iDBService = DBServiceImpl.getInstance();
	
	/** The i validator. */
	private IValidator<IRequest> iValidator = BlacklistValidatorImpl.getInstance();
	
	/**
	 * Instantiates a new blacklist modifier impl.
	 */
	private BlacklistModifierImpl() {
	}

	/**
	 * Gets the single instance of BlacklistModifierImpl.
	 *
	 * @return single instance of BlacklistModifierImpl
	 */
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

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistModifier#saveIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.InternalServiceRequestWrapper)
	 */
	@Override
	public InternalServiceResponse saveIntoBlacklist(InternalServiceRequest request) throws BlacklistException {
		LOG.debug("BlacklistModifierImpl.saveIntoBlacklist : start");
		try {
			if (Boolean.TRUE.equals(iValidator.validate(request))) {
				return iDBService.saveIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			String logData = "saveIntoBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw blacklistException;
		} catch (Exception e) {
			String logData = "saveIntoBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_INSERTING_DATA);
		}
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistModifier#updateIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest)
	 */
	@Override
	public BlacklistModifierResponse updateIntoBlacklist(BlacklistUpdateRequest request) throws BlacklistException {
		LOG.debug("BlacklistModifierImpl.updateIntoBlacklist : start");
		try {
			if (Boolean.TRUE.equals(iValidator.validate(request))) {
				return iDBService.updateIntoBlacklist(request);
			} else {
				throw new BlacklistException(BlacklistErrors.INVALID_REQUEST);
			}
		} catch (BlacklistException blacklistException) {
			String logData = "updateIntoBlacklist CorrelationId: " + request.getCorrelationId().toString();
			LOG.error(logData);
			throw blacklistException;
		} catch (Exception e) {
			String logData = "updateIntoBlacklist CorrelationId: " + request.getCorrelationId().toString();
			LOG.error(logData);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_UPDATING_DATA);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistModifier#deleteFromBlacklist(com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest)
	 */
	public InternalServiceResponse deleteFromBlacklist(InternalServiceRequest request) throws BlacklistException {
		LOG.debug("BlacklistModifierImpl.deleteFromBlacklist : start");
		try {
				return iDBService.deleteFromBacklist(request);			
		} catch (BlacklistException blacklistException) {
			String logData = "deleteFromBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw blacklistException;
		} catch (Exception e) {
			String logData = "deleteFromBlacklist CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_DELETING_DATA);
		}
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IBlacklistModifier#getBlacklistDataByType(com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest)
	 */
	public InternalServiceResponse getBlacklistDataByType(InternalServiceRequest request) throws BlacklistException {
		LOG.debug("BlacklistModifierImpl.getBlacklistDataByType : start");
		try {
				return iDBService.getBlacklistDataByType(request);
		} catch (BlacklistException blacklistException) {
			String logData = "getBlacklistDataByType CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw blacklistException;
		} catch (Exception e) {
			String logData = "getBlacklistDataByType CorrelationId: " + request.getCorrelationID().toString();
			LOG.debug(logData);
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE);
		}
	}

}
