package com.currenciesdirect.gtg.compliance.iam.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IIAMService.
 * @author rajeshs
 * 
 * Responsibility
 * 
 * 1. Get user functions 
 * 2. Get user permissions for user by view
 * 
 */
public interface IIAMService {

	/**
	 * Call DBService to get All accessible functions of user
	 * @param user information
	 * @return functions which user has access
	 * @throws CompliancePortalException
	 * 
	 */
	List<Function> getUserFunctions(UserProfile user) throws CompliancePortalException;
	
	
	/**
	 * Populates user permissions from the user functions
	 * @param user information
	 * @return permissions of user
	 * @throws CompliancePortalException
	 */
	UserPermission getUserPermissionsByRoleAndView(UserProfile user) throws CompliancePortalException;
}
