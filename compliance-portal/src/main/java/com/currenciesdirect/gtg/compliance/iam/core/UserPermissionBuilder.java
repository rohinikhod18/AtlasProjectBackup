package com.currenciesdirect.gtg.compliance.iam.core;

import com.currenciesdirect.gtg.compliance.httpport.ViewControllerConstant;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;

/**
 * The Class RegistationDetailsPermissionBuilder.
 */
public class UserPermissionBuilder {

	private UserPermission permission;

	private Role role;

	/**
	 * Instantiates a new registation details permission builder.
	 *
	 * @param role
	 *            the role
	 */
	public UserPermissionBuilder(Role role) {
		this.role = role;
		permission = new UserPermission();
	}

	/**
	 * Builds the.
	 *
	 * @return the reg details user permission
	 */
	public UserPermission build() {
		return permission;
	}

	/**
	 * Builds the service permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildServicePermissions() {
		permission.setCanManageSanction(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_SANCTION));
		permission.setCanManageEID(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_EID));
		permission.setCanManageCustom(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_CUSTOM));
		permission.setCanManageBlackList(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_BLACKLIST));
		permission.setCanManageFraud(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_FRAUD));
		permission.setCanManageWatchListCategory1(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1));
		permission.setCanManageWatchListCategory2(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2));
		return this;
	}

	/**
	 * Builds the status update permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildStatusUpdatePermissions() {
		return this;
	}

	/**
	 * Builds the status reason permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildStatusReasonPermissions() {
		return this;
	}

	/**
	 * Builds the locking permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildLockingPermissions() {
		permission.setCanUnlockRecords(isFunctionAllowed(ViewControllerConstant.CAN_UNLOCK));
		return this;
	}

	/**
	 * Builds the cust type permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildCustTypePermissions() {
		permission.setCanWorkOnCFX(isFunctionAllowed(ViewControllerConstant.CAN_WORK_ON_CFX));
		permission.setCanWorkOnPFX(isFunctionAllowed(ViewControllerConstant.CAN_WORK_ON_PFX));
		return this;
	}

	/**
	 * Builds the page permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildPagePermissions() {
		
		permission.setCanViewPaymentInQueue(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_PAYMENTIN_QUQUE));
		permission.setCanViewPaymentOutQueue(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_PAYMENTOUT_QUQUE));
		permission.setCanViewRegistrationQueue(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE));
		permission.setCanViewRegistrationReport(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_REGISTRTION_REPORT));
		permission.setCanViewPaymentOutReport(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_PAYMENTOUT_REPORT));
		permission.setCanViewPaymentInReport(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_PAYMENTIN_REPORT));
		permission.setCanViewDashboard(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_DASHBOARD));
		permission.setCanViewWorkEfficiacyReport(isFunctionAllowed(ViewControllerConstant.CAN_VIEW_WORK_EFFICIANCY_REPORT));
		permission.setCanInitiateDataAnon(isFunctionAllowed(ViewControllerConstant.CAN_INITIATE_DATA_ANON));
		permission.setCanApproveDataAnon(isFunctionAllowed(ViewControllerConstant.CAN_APPROVE_DATA_ANON));
		permission.setIsReadOnlyUser(isFunctionAllowed(ViewControllerConstant.IS_READ_ONLY_USER));
		permission.setCanViewRegistrationDetails(canViewRegistrationDetailsPage());
		permission.setCanViewPaymentInDetails(canViewPaymentInDetailsPage());
		permission.setCanViewPaymentOutDetails(canViewPaymentOutDetailsPage());
		permission.setCanManageBeneficiary(isFunctionAllowed(ViewControllerConstant.CAN_MANAGE_BENEFICIARY));
		permission.setCanNotLockAccount(isFunctionAllowed(ViewControllerConstant.CAN_NOT_LOCK_ACCOUNT));
		return this;
	}

	/**
	 * Builds the administration permissions.
	 *
	 * @return the user permission builder
	 */
	public UserPermissionBuilder buildAdministrationPermissions() {
		permission.setCanDoAdministration(isFunctionAllowed(ViewControllerConstant.CAN_DO_ADMINISTRATION));
		return this;
	}

	public UserPermission getUserPermissions() {
		return this.permission;
	}

	private Boolean isFunctionAllowed(String funcationName) {
		return role.getFunctionByName(funcationName).getHasAccess();
	}

	private boolean canViewRegistrationDetailsPage() {
		if (!permission.getCanViewRegistrationQueue()) {
			return false;
		}
		return permission.getCanManageEID() || permission.getCanManageFraud() || permission.getCanManageBlackList()
				|| permission.getCanManageSanction();

	}
	
	private boolean canViewPaymentInDetailsPage() {
		if (!permission.getCanViewPaymentInQueue()) {
			return false;
		}
		boolean permissionofManageFraudBlacklist=permission.getCanManageFraud() || permission.getCanManageBlackList();
		boolean permissionofManageSanctionCustomWatchlist= permission.getCanManageSanction() || permission.getCanManageCustom() || permission.getCanManageWatchListCategory1();
        
		return permissionofManageFraudBlacklist||permissionofManageSanctionCustomWatchlist;
	}
	
	private boolean canViewPaymentOutDetailsPage() {
		if (!permission.getCanViewPaymentOutQueue()) {
			return false;
		}
		boolean permissionOfManageFraudBlacklist=permission.getCanManageFraud() || permission.getCanManageBlackList();
		boolean permissionOfManageSanctionCustomWatchlist= permission.getCanManageSanction() || permission.getCanManageCustom() || permission.getCanManageWatchListCategory1();
        
		return permissionOfManageFraudBlacklist||permissionOfManageSanctionCustomWatchlist;
	}

}
