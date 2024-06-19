package com.currenciesdirect.gtg.compliance.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IRecheckService.
 */
public interface IRecheckService {

	/**
	 * Repeat check funds out failures.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public BaseRepeatCheckResponse repeatCheckFundsOutFailures(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the registration service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the registration service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getRegistrationServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the funds in service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the funds in service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getFundsInServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the funds out service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the funds out service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getFundsOutServiceFailureCount(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException;
	
	/**
	 * Repeat check funds in failures.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public BaseRepeatCheckResponse repeatCheckFundsInFailures(UserProfile user, BaseRepeatCheckRequest request) throws CompliancePortalException;


	/**
	 * Repeat check registration failures.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse repeatCheckRegistrationFailures(UserProfile user, BaseRepeatCheckRequest request)
			throws CompliancePortalException;
	
	/**
	 * Gets the repeat check progress bar.
	 *
	 * @param request the request
	 * @return the repeat check progress bar
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public Integer getRepeatCheckProgressBar(BaseRepeatCheckRequest request) throws CompliancePortalException;

	
	/**
	 * Force clearfunds outs.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public BaseRepeatCheckResponse forceClearFundsOuts(UserProfile user, BaseRepeatCheckRequest request) throws CompliancePortalException;
	
	
	/**
	 * Force clearfunds In.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public BaseRepeatCheckResponse forceClearFundsIn(UserProfile user, BaseRepeatCheckRequest request) throws CompliancePortalException;

	/**
	 * Delete reprocess failed.
	 *
	 * @param request the request
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public Integer deleteReprocessFailed(BaseRepeatCheckRequest request) throws CompliancePortalException;
	
	/**
	 * Update TMMQ retry count.
	 *
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean updateTMMQRetryCount() throws CompliancePortalException;
	
	/**
	 * Show count reprocess failed.
	 *
	 * @param batchId the batch id
	 * @return the string
	 * @throws CompliancePortalException the compliance portal exception
	 */ //AT-4355
	public String showCountReprocessFailed(Integer batchId) throws CompliancePortalException;

	/**
	 * Clear reprocess failed.
	 *
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */ //AT-4355
	public Integer clearReprocessFailed() throws CompliancePortalException;
	
	/**
	 * Update post card TMMQ retry count.
	 *
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	// AT-5023
	public boolean updatePostCardTMMQRetryCount() throws CompliancePortalException;
}
