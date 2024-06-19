package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RecentPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IPaymentOutServiceImpl.
 */
public interface IPaymentOutService {
	
	/**
	 * Gets the payment out queue.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out oueue
	 * @throws CompliancePortalException the compliance portal exception
	 */
	PaymentOutQueueDto getPaymentOutQueueWithCriteria(PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException;
	
	/**
	 * Gets the payment out detail.
	 *
	 * @param paymentOutId the payment out id
	 * @param searchCriteria the search criteria
	 * @return the payment out detail
	 * @throws CompliancePortalException the compliance portal exception
	 */
	PaymentOutDetailsDto getPaymentOutDetails(Integer paymentOutId, String custType, PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException;
	
	/**
	 * Gets the payment out detail.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out detail
	 * @throws CompliancePortalException the compliance portal exception
	 */
	PaymentOutDetailsDto getPaymentOutDetailsWithCriteria(PaymentOutSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the activity logs.
	 *
	 * @param request the request
	 * @return the activity logs
	 * @throws CompliancePortalException the compliance portal exception
	 */
	ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException;
	
	
	/**
	 * Update payment out.
	 *
	 * @param request the request
	 * @return the activity logs
	 * @throws CompliancePortalException the compliance portal exception
	 */
	ActivityLogs updatePaymentOut(PaymentOutUpdateRequest request) throws CompliancePortalException;

	/**
	 * View More Details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the view more response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse viewMoreDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException;
	
	/**
	 * Further PaymentOut ViewMore Details.
	 *
	 * @param request the request
	 * @return the view more response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	ViewMoreResponse getFurtherPaymentOutDetails(PaymentOutViewMoreRequest request) throws CompliancePortalException;

	/**
	 * Get most recent payment out id.
	 *
	 * @param tradeId the trade id
	 * @param accountNumber the account number
	 * @return the recent paymentOut details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	RecentPaymentOutDetails getMostRecentPaymentOutId(String tradeId,String accountNumber)throws CompliancePortalException;

	public boolean setPoiExistsFlagForPaymentOut(UserProfile user, Contact contact) throws CompliancePortalException;

}
