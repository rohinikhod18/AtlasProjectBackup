package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IRegistrationQueueDBService.
 */
public interface IRegistrationDBService extends IDBService {

	/**
	 * Gets the registration queue.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the registration queue
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the registration details.
	 *
	 * @param contactId
	 *            the contact id
	 * @param searchCriteria 
	 * @return the registration details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public IRegistrationDetails getRegistrationDetails(Integer contactId, RegistrationSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Update contact.
	 *
	 * @param requestHandlerDto
	 *            the request handler dto
	 * @return 
	 * @throws CompliancePortalException
	 */
	public ActivityLogs updateContact(RegUpdateDBDto requestHandlerDto) throws CompliancePortalException;
	
	/**
     * @param viewMoreRequest
     * @return
     * @throws CompliancePortalException
     */
    public ViewMoreResponse getMoreKycDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;
    
    /**
     * @param viewMoreRequest
     * @return
     * @throws CompliancePortalException
     */
    public ViewMoreResponse getMoreSanctionDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;
    
    /**
     * @param viewMoreRequest
     * @return
     * @throws CompliancePortalException
     */
    public ViewMoreResponse getMoreCustomCheckDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;
    
    /**
     * @param viewMoreRequest
     * @return
     * @throws CompliancePortalException
     */
    public ViewMoreResponse getMoreFraugsterDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;

    /**
     * @param viewMoreRequest
     * @return
     * @throws CompliancePortalException
     */
    public ViewMoreResponse getMoreOnfidoDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;


	public boolean setPoiExistsFlag(UserProfile user, Contact contact)throws CompliancePortalException;
 
	//AT-4114
	 public ViewMoreResponse getMoreIntuitionDetails(RegistrationViewMoreRequest viewMoreRequest) throws CompliancePortalException;
}