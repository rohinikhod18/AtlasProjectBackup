package com.currenciesdirect.gtg.compliance.iam.dbport;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IIAMDBService.
 */
@FunctionalInterface
public interface IIAMDBService {
	
	/**
	 * Gets the user role details.
	 *
	 * @param user the user
	 * @return the user role details
	 * @throws CompliancePortalException 
	 */
	public Role getUserRoleDetails(UserProfile user) throws CompliancePortalException;

}
