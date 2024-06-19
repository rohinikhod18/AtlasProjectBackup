package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryRequest;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IRegistrationQueueService.
 */
public interface IRegistrationService {

	/**
	 * Gets the registration queue.
	 *
	 * @param request
	 *            the search criteria
	 * @return the registration queue
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationSearchCriteria request) throws CompliancePortalException;

	/**
	 * Gets the registration details.
	 *
	 * @param contactId            the contact id
	 * @param custType the cust type
	 * @param searchCriteria the search criteria
	 * @return the registration details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	IRegistrationDetails getRegistrationDetails(Integer contactId, String custType, RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException;

	/**
	 * Update contact.
	 *
	 * @param registrationUpdateRequest
	 *            the registration update request
	 * @return the activity logs
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	ActivityLogs updateContact(RegistrationUpdateRequest registrationUpdateRequest) throws CompliancePortalException;

	
	/**
	 * lockResource.
	 *
	 * @param lockResourceRequest the lock resource request
	 * @return the lock resource response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public LockResourceResponse lockResource(LockResourceRequest lockResourceRequest) throws CompliancePortalException;

	/**
	 * Gets the activity logs.
	 *
	 * @param request the request
	 * @return the activity logs
	 * @throws CompliancePortalException the compliance portal exception
	 */
	ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException;

	/**
	 * viewMoredetails.
	 *
	 * @param viewMoreRequest the view more request
	 * @return the view more response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ViewMoreResponse viewMoreDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;
	
	
	/**
	 * viewMoredetails.
	 *
	 * @param logResponse the log response
	 * @return the provider response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public ProviderResponseLogResponse getProviderResponse(ProviderResponseLogRequest logResponse) throws CompliancePortalException;
	
	
	/**
	 * Lock resource multi contacts.
	 *
	 * @param lockResourceRequest the lock resource request
	 * @return the lock resource response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public LockResourceResponse lockResourceMultiContacts(LockResourceRequest lockResourceRequest) throws CompliancePortalException;

	/**
	 * getDeviceInfo.
	 *
	 * @param deviceInfoRequest the device info request
	 * @return the device info response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DeviceInfoResponse getDeviceInfo(DeviceInfoRequest deviceInfoRequest)throws CompliancePortalException;

	/**
	 * getAccountHistory.
	 *
	 * @param accountHistoryRequest the account history request
	 * @return the account history response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public AccountHistoryResponse getAccountHistory(AccountHistoryRequest accountHistoryRequest)throws CompliancePortalException;

	public boolean setPoiExistsFlag(UserProfile user, Contact contact) throws CompliancePortalException;
	
}