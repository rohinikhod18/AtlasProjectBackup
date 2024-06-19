package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationResponse;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IAnonymisationService.
 */
public interface IAnonymisationService {
	
	/**
	 * Gets the data anonymisation with criteria.
	 *
	 * @param request the request
	 * @return the data anonymisation with criteria
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonymisationWithCriteria(DataAnonymisationSearchCriteria request) throws CompliancePortalException;
	
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
	public DataAnonymisationResponse updateDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;

	/**
	 * Cancel data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public boolean  cancelDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException;
	
	/**
	 * Gets the data anon history.
	 *
	 * @param request the request
	 * @return the data anon history
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonHistory(DataAnonymisationDataRequest request) throws CompliancePortalException;
}
