package com.currenciesdirect.gtg.compliance.core;


import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymizationServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IAnonymisationDBService.
 */
public interface IAnonymisationDBService extends IDBService {
	
	/**
	 * Gets the data anonymisation with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return the data anonymisation with criteria
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonymisationWithCriteria(DataAnonymisationSearchCriteria searchCriteria) throws CompliancePortalException;

	
	/**
	 * Gets the anonymisation details.
	 *
	 * @param contactId the contact id
	 * @param custType the cust type
	 * @param searchCriteria the search criteria
	 * @return the anonymisation details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymizationServiceRequest getAnonymisationDetails(DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;

	
	/**
	 * Gets the data anonymize.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anonymize
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean getDataAnonymize(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;
	
	/**
	 * Update data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean updateDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;

	/**
	 * Cancel data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean cancelDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;
	
	/**
	 * Gets the data anon history.
	 *
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anon history
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonHistory( DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException ;
	
}
