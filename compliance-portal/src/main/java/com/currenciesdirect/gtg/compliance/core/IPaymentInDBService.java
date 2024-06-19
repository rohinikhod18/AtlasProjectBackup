package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;


/**
 * The Interface IPaymentInDBService.
 */
public interface IPaymentInDBService extends IDBService {
	
	/**
	 * Gets the payment in queue with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return PaymentInQueueDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentInQueueDto getPaymentInQueueWithCriteria(PaymentInSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the payment in details.
	 *
	 * @param paymentInId the payment in id
	 * @param custType 
	 * @param searchCriteria 
	 * @return PaymentInDetailsDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentInDetailsDto getPaymentInDetails(Integer paymentInId, String custType, PaymentInSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * @param paymentInUpdateDBDto
	 * @return ActivityLogs
	 * @throws CompliancePortalException
	 */
	public ActivityLogs updatePaymentIn(PaymentInUpdateDBDto paymentInUpdateDBDto)throws CompliancePortalException;

	
	/**
	 * @param viewMoreRequest
	 * @return ViewMoreResponse
	 * @throws CompliancePortalException
	 */
	public ViewMoreResponse getMoreFraugsterDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 * @param viewMoreRequest
	 * @return ViewMoreResponse
	 * @throws CompliancePortalException
	 */
	public ViewMoreResponse getMoreSanctionDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException;

	/**
	 * @param viewMoreRequest
	 * @return ViewMoreResponse
	 * @throws CompliancePortalException
	 */
	public ViewMoreResponse getMoreCustomCheckDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException;
	
	/**
	 * @param paymentOutFailed
	 * @param activityLogs
	 * @param user
	 * @throws CompliancePortalException
	 */
	public void forceClearPaymentIn(PaymentInRecheckFailureDetails paymentOutFailed, PaymentInActivityLogDto activityLogs, UserProfile user )
		      throws CompliancePortalException;

	public boolean setPoiExistsFlagForPaymentIn(UserProfile user, Contact contact) throws CompliancePortalException; 
	
	//AT-4306
	/**
	 * Gets the more intuition details.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the more intuition details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse getMoreIntuitionDetails(PaymentInViewMoreRequest viewMoreRequest) throws CompliancePortalException;
}
