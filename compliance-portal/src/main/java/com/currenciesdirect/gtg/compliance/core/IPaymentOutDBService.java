package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RecentPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IPaymentOutDBService.
 */
public interface IPaymentOutDBService extends IDBService{
	
	/**
	 * Gets the payment out queue.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out queue
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentOutQueueDto getPaymentOutQueueWithCriteria(PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException;
	
	/**
	 * Gets the payment out details.
	 *
	 * @param paymentOutId the payment out id
	 * @param paymentOutSearchCriteria the payment out search criteria
	 * @return the payment out details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentOutDetailsDto getPaymentOutDetails(Integer paymentOutId,  String custType, PaymentOutSearchCriteria paymentOutSearchCriteria) throws CompliancePortalException;
	
	/**
	 * Gets the payment out details with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out details with criteria
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentOutDetailsDto getPaymentOutDetailsWithCriteria(PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException;
	
	
	/**
	 * Update payment out.
	 *
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 * @return the activity logs
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ActivityLogs updatePaymentOut(PaymentOutUpdateDBDto paymentOutUpdateDBDto) throws CompliancePortalException;

	/**
	 * Gets the View More Kyc Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more kyc details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreKycDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 * Gets the View More Sanction Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more sanction details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreSanctionDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 * Gets the View More Fraugster Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more fraugster details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreFraugsterDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 * Gets the View More CustomCheck Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more custom check details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreCustomCheckDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 *  Gets the Further PaymentOut Details.
	 *
	 * @param request the request
	 * @return the further payment out details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getFurtherPaymentOutDetails(PaymentOutViewMoreRequest request) throws CompliancePortalException;

	/**
	 * Gets the Further PaymentIn Details.
	 *
	 * @param request the request
	 * @return the view more further payment in details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getViewMoreFurtherPaymentInDetails(PaymentOutViewMoreRequest request) throws CompliancePortalException;

	/**
	 * Gets the Country Check Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more country check details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreCountryCheckDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;
	
	/**
	 * Gets the most recent payment out id.
	 *
	 * @param tradeId the trade id
	 * @param accountNumber the account number
	 * @return the most recent payment out id
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public RecentPaymentOutDetails getMostRecentPaymentOutId(String tradeId, String accountNumber)throws CompliancePortalException;
	
	/**
	 * Force clear payment out.
	 *
	 * @param paymentOutFailedList the payment out failed list
	 * @param activityLogs the activity logs
	 * @param user the user
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public void forceClearPaymentOut(PaymentOutRecheckFailureDetails paymentOutFailed, PaymentOutActivityLogDto activityLogs, UserProfile user )
	      throws CompliancePortalException;

	public boolean setPoiExistsFlagForPaymentOut(UserProfile user, Contact contact) throws CompliancePortalException; 
	
	//AT-3658
	/**
	 * Gets the Payment Reference Check Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more payment reference check details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMorePaymentReferenceCheckDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;
	
	//AT-4306
	/**
	 * Gets the more intuition check details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more intuition check details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreIntuitionCheckDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;
}
