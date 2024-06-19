package com.currenciesdirect.gtg.compliance.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RegistrationRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IRecheckDBService.
 */
public interface IRecheckDBService {

	/**
	 * Gets the funds out failures.
	 *
	 * @param request
	 *            the request
	 * @return the funds out failures
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public List<PaymentOutRecheckFailureDetails> getFundsOutFailures(BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the funds out service failure count.
	 *
	 * @param request
	 *            the request
	 * @return the funds out service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getFundsOutServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the registration service failure count.
	 *
	 * @param request
	 *            the request
	 * @return the registration service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getRegistrationServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	/**
	 * Gets the funds in service failure count.
	 *
	 * @param request
	 *            the request
	 * @return the funds in service failure count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public BaseRepeatCheckResponse getFundsInServiceFailureCount(BaseRepeatCheckRequest request)
			throws CompliancePortalException;
	
	/**
	 * Gets the funds in failures.
	 *
	 * @param request
	 *            the request
	 * @return the funds in failures
	 */
	public List<PaymentInRecheckFailureDetails> getFundsInFailures(BaseRepeatCheckRequest request) throws CompliancePortalException;



	/**
	 * Gets the registration failures.
	 *
	 * @param request
	 *            the request
	 * @return the registration failures
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public List<RegistrationRecheckFailureDetails> getRegistrationFailures(BaseRepeatCheckRequest request)
			throws CompliancePortalException;

	public Integer getRepeatCheckProgressBar(BaseRepeatCheckRequest request) throws CompliancePortalException;

	
	/**
	 * Save failed details to DB.
	 *
	 * @param paymentOutFailedList the payment out failed list
	 * @param baseRepeatCheckResponse 
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public Integer saveFailedDetailsToDB(List<PaymentOutRecheckFailureDetails> paymentOutFailedList, BaseRepeatCheckResponse baseRepeatCheckResponse) throws CompliancePortalException;

	/**
	 * Gets the first failed payment out to reprocess.
	 * @param baseRepeatCheckResponse 
	 *
	 * @return the first failed payment out to reprocess
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ReprocessFailedDetails getFirstFailedRecordToReprocess(BaseRepeatCheckResponse baseRepeatCheckResponse, BaseRepeatCheckRequest request) throws CompliancePortalException;
	
	/**
	 * Gets the reprocess status for failed.
	 *
	 * @param failedFundsOutDetails the failed funds out details
	 * @return the reprocess status for failed
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean getReprocessStatusForFailed(ReprocessFailedDetails reprocessFailedDetails) throws CompliancePortalException;

	/**
	 * Delete reprocess failed.
	 * @param baseRepeatCheckResponse2 
	 *
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public Integer deleteReprocessFailed(BaseRepeatCheckRequest request) throws CompliancePortalException;

	/**
	 * Gets the reprocess payments list.
	 *
	 * @param request the request
	 * @param baseRepeatCheckResponse 
	 * @return the reprocess payments list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	List<ReprocessFailedDetails> getReprocessList(BaseRepeatCheckRequest request, BaseRepeatCheckResponse baseRepeatCheckResponse)
			throws CompliancePortalException;
	
	/**
	 * Save failed details to DB.
	 * @param baseRepeatCheckResponse 
	 *
	 * @param paymentOutFailedList the payment out failed list
	 * @return the integer
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public Integer saveFailedFundsInDetailsToDB(List<PaymentInRecheckFailureDetails> paymentInFailedList, BaseRepeatCheckResponse baseRepeatCheckResponse) throws CompliancePortalException;

	public int saveFailedRegDetailsToDB(List<RegistrationRecheckFailureDetails> registrationFailedList,
			BaseRepeatCheckResponse repeatCheckResponse) throws CompliancePortalException;
	
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
	 * @param branchId the branch id
	 * @return the string
	 * @throws CompliancePortalException the compliance portal exception
	 */ //AT-4355
	public String showCountReprocessFailed(Integer branchId) throws CompliancePortalException;

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
	//AT-5023
	public boolean updatePostCardTMMQRetryCount() throws CompliancePortalException;
}
