package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public interface IPaymentInService {

	/**
	 * Gets the payment out oueue.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out oueue
	 */
	PaymentInQueueDto getPaymentInQueueWithCriteria(PaymentInSearchCriteria searchCriteria) throws CompliancePortalException;
	
	/**
	 * Gets the payment in detail.
	 *
	 * @param paymentInId the payment out id
	 * @param custType 
	 * @param searchCriteria the search criteria
	 * @return the payment in detail
	 * @throws CompliancePortalException 
	 */
	PaymentInDetailsDto getPaymentInDetails(Integer paymentInId, String custType, PaymentInSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Update payment in.
	 * 
	 * @param paymentInUpdateRequest
	 * @return the activity logs
	 */
	ActivityLogs updatePaymentIn(PaymentInUpdateRequest paymentInUpdateRequest) throws CompliancePortalException;

	/**
	 * @param request
	 * @return ActivityLogs
	 * @throws CompliancePortalException
	 */
	ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * @param viewMoreRequest
	 * @return ViewMoreResponse
	 * @throws CompliancePortalException 
	 */
	public ViewMoreResponse viewMoreDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	public boolean setPoiExistsFlagForPaymentIn(UserProfile user, Contact contact) throws CompliancePortalException;
	
}
