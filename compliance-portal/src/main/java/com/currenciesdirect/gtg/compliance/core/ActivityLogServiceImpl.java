package com.currenciesdirect.gtg.compliance.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Class ActivityLogServiceImpl.
 */
@Component("activityLogServiceImpl")
public class ActivityLogServiceImpl implements IActivityLogService {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(ActivityLogServiceImpl.class);

	/** The i activity log DB service. */
	@Autowired
	@Qualifier("activityLogDBServiceImpl")
	private IActivityLogDBService iActivityLogDBService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogService#
	 * getPaymentInActivityLogs(java.lang.Integer)
	 */
	@Override
	public ActivityLogs getPaymentInActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			return iActivityLogDBService.getPaymentInActivityLogs(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogService#
	 * getPaymentOutActivityLogs(com.currenciesdirect.gtg.compliance.core.domain
	 * .ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getPaymentOutActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			return iActivityLogDBService.getPaymentOutActivityLogs(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogService#
	 * getRegistrationActivityLogs(com.currenciesdirect.gtg.compliance.core.
	 * domain.ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getRegistrationActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			return iActivityLogDBService.getRegistrationActivityLogs(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogService#
	 * getAllActivityLogs(com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getAllActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		try {
			return iActivityLogDBService.getAllActivityLogs(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

	/**
	 * Log error.
	 *
	 * @param t
	 *            the t
	 */
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

}
