package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IActivityLogService.
 */
public interface IActivityLogService {

	/**
	 * Gets the payment in activity logs.
	 *
	 * @param request
	 *            the request
	 * @return the payment in activity logs
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public ActivityLogs getPaymentInActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * Gets the payment out activity logs.
	 *
	 * @param request
	 *            the request
	 * @return the payment out activity logs
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public ActivityLogs getPaymentOutActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * Gets the registration activity logs.
	 *
	 * @param request
	 *            the request
	 * @return the registration activity logs
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public ActivityLogs getRegistrationActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * Gets the consolidated registration, payment in, payment out activity logs.
	 *
	 * @param request
	 *            the request
	 * @return the consolidated registration, payment in, payment out activity logs
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public ActivityLogs getAllActivityLogs(ActivityLogRequest request) throws CompliancePortalException;
}
