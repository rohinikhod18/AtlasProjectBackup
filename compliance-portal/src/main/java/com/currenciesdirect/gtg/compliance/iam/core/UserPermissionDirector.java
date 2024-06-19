package com.currenciesdirect.gtg.compliance.iam.core;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class UserPermissionDirector.
 */
public class UserPermissionDirector {
	
	private UserPermission userPermission;

	/**
	 * Construct user permissions.
	 *
	 * @param user the user
	 */
	public void constructUserPermissions(UserProfile user){
		
		 this.userPermission =  new UserPermissionBuilder(user.getRole()).buildServicePermissions()
						 					.buildStatusUpdatePermissions()
						 					.buildCustTypePermissions()
						 					.buildAdministrationPermissions()
						 					.buildLockingPermissions()
						 					.buildStatusReasonPermissions()
						 					.buildPagePermissions()
						 					.getUserPermissions();
		
	}
	
	/**
	 * Gets the user permission.
	 *
	 * @return the user permission
	 */
	public UserPermission getUserPermission(){
		return userPermission;
	}
	
	
	
}
