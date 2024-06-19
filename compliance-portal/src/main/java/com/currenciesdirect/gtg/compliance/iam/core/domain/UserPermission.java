package com.currenciesdirect.gtg.compliance.iam.core.domain;


/**
 * The Class UserPerminssion.
 */
public class UserPermission {

	
	/** The can work on CFX. */
	private boolean canWorkOnCFX;
	
	/** The can work on PFX. */
	private boolean canWorkOnPFX;
	
	/** The can view registration queue. */
	private boolean canViewRegistrationQueue;
	
	/** The can view registration details. */
	private boolean canViewRegistrationDetails;
	
	/** The can view payment in queue. */
	private boolean canViewPaymentInQueue;
	
	/** The can view payment in details. */
	private boolean canViewPaymentInDetails;
	
	/** The can view payment out queue. */
	private boolean canViewPaymentOutQueue;
	
	/** The can view payment out details. */
	private boolean canViewPaymentOutDetails;
	
	/** The can view registrations. */
	private boolean canViewRegistrationReport;
	
	/** The can view payment in. */
	private boolean canViewPaymentInReport;
	
	/** The can view payment out. */
	private boolean canViewPaymentOutReport;
	
	/** The can view work efficiacy report. */
	private boolean canViewWorkEfficiacyReport;
	
	/** The can manage watch list. */
	private boolean canManageWatchListCategory1;
	
	/** The can manage watch list category 2. */
	private boolean canManageWatchListCategory2;
	
	/** The can unlock records. */
	private boolean canUnlockRecords;
	
	/** The can view dashboard. */
	private boolean canViewDashboard;
	
	
	/** The can manage fraud. */
	private boolean canManageFraud;
	
	/** The can manage custom. */
	private boolean canManageCustom;
	
	/** The can manage EID. */
	private boolean canManageEID;
	
	/** The can manage sanction. */
	private boolean canManageSanction;
	
	/** The can manage black list. */
	private boolean canManageBlackList;
	
	/** The can do administration. */
	private boolean canDoAdministration;
	
	private boolean isReadOnlyUser;
	
	/** The can manage beneficiary. */
	private boolean canManageBeneficiary;


	/** The can Initiate Data Anon . */
	private boolean canInitiateDataAnon;
	
	/** The can Approve Data Anon . */
	private boolean canApproveDataAnon;
	
	/** The can Not Lock Account . */
	private boolean canNotLockAccount;
	
	/**
	 * @return the canWorkOnCFX
	 */
	public boolean getCanWorkOnCFX() {
		return canWorkOnCFX;
	}
	
	
	public boolean getIsReadOnlyUser()
	{
		return isReadOnlyUser;
	}
	
	public void setIsReadOnlyUser(boolean isReadOnlyUser)
	{
		this.isReadOnlyUser=isReadOnlyUser;
	}

	/**
	 * @param canWorkOnCFX the canWorkOnCFX to set
	 */
	public void setCanWorkOnCFX(boolean canWorkOnCFX) {
		this.canWorkOnCFX = canWorkOnCFX;
	}

	/**
	 * @return the canWorkOnPFX
	 */
	public boolean getCanWorkOnPFX() {
		return canWorkOnPFX;
	}

	/**
	 * @param canWorkOnPFX the canWorkOnPFX to set
	 */
	public void setCanWorkOnPFX(boolean canWorkOnPFX) {
		this.canWorkOnPFX = canWorkOnPFX;
	}

	/**
	 * @return the canViewRegistrationQueue
	 */
	public boolean getCanViewRegistrationQueue() {
		return canViewRegistrationQueue;
	}

	/**
	 * @param canViewRegistrationQueue the canViewRegistrationQueue to set
	 */
	public void setCanViewRegistrationQueue(boolean canViewRegistrationQueue) {
		this.canViewRegistrationQueue = canViewRegistrationQueue;
	}

	/**
	 * @return the canViewPaymentInQueue
	 */
	public boolean getCanViewPaymentInQueue() {
		return canViewPaymentInQueue;
	}

	/**
	 * @param canViewPaymentInQueue the canViewPaymentInQueue to set
	 */
	public void setCanViewPaymentInQueue(boolean canViewPaymentInQueue) {
		this.canViewPaymentInQueue = canViewPaymentInQueue;
	}

	/**
	 * @return the canViewPaymentOutQueue
	 */
	public boolean getCanViewPaymentOutQueue() {
		return canViewPaymentOutQueue;
	}

	/**
	 * @param canViewPaymentOutQueue the canViewPaymentOutQueue to set
	 */
	public void setCanViewPaymentOutQueue(boolean canViewPaymentOutQueue) {
		this.canViewPaymentOutQueue = canViewPaymentOutQueue;
	}

	/**
	 * @return the canViewRegistrations
	 */
	public boolean getCanViewRegistrationReport() {
		return canViewRegistrationReport;
	}

	/**
	 * @param canViewRegistrations the canViewRegistrationReport to set
	 */
	public void setCanViewRegistrationReport(boolean canViewRegistrationReport) {
		this.canViewRegistrationReport = canViewRegistrationReport;
	}

	/**
	 * @return the canViewPaymentIn
	 */
	public boolean getCanViewPaymentInReport() {
		return canViewPaymentInReport;
	}

	/**
	 * @param canViewPaymentIn the canViewPaymentInReport to set
	 */
	public void setCanViewPaymentInReport(boolean canViewPaymentInReport) {
		this.canViewPaymentInReport = canViewPaymentInReport;
	}

	/**
	 * @return the canViewPaymentOut
	 */
	public boolean getCanViewPaymentOutReport() {
		return canViewPaymentOutReport;
	}

	/**
	 * @param canViewPaymentOut the canViewPaymentOutReport to set
	 */
	public void setCanViewPaymentOutReport(boolean canViewPaymentOutReport) {
		this.canViewPaymentOutReport = canViewPaymentOutReport;
	}

	/**
	 * @return the canManageWatchListCategory1
	 */
	public boolean getCanManageWatchListCategory1() {
		return canManageWatchListCategory1;
	}

	/**
	 * @param canManageWatchListCategory1 the canManageWatchListCategory1 to set
	 */
	public void setCanManageWatchListCategory1(boolean canManageWatchListCategory1) {
		this.canManageWatchListCategory1 = canManageWatchListCategory1;
	}
	/**
	 * @return the canManageWatchListCategory2
	 */
	public boolean getCanManageWatchListCategory2() {
		return canManageWatchListCategory2;
	}

	/**
	 * @param canManageWatchListCategory2 the canManageWatchListCategory2 to set
	 */
	public void setCanManageWatchListCategory2(boolean canManageWatchListCategory2) {
		this.canManageWatchListCategory2 = canManageWatchListCategory2;
	}

	/**
	 * @return the canUnlockRecords
	 */
	public boolean getCanUnlockRecords() {
		return canUnlockRecords;
	}

	/**
	 * @param canUnlockRecords the canUnlockRecords to set
	 */
	public void setCanUnlockRecords(boolean canUnlockRecords) {
		this.canUnlockRecords = canUnlockRecords;
	}

	/**
	 * @return the canViewDashboard
	 */
	public boolean getCanViewDashboard() {
		return canViewDashboard;
	}

	/**
	 * @param canViewDashboard the canViewDashboard to set
	 */
	public void setCanViewDashboard(boolean canViewDashboard) {
		this.canViewDashboard = canViewDashboard;
	}

	/**
	 * @return the canManageFraud
	 */
	public boolean getCanManageFraud() {
		return canManageFraud;
	}

	/**
	 * @param canManageFraud the canManageFraud to set
	 */
	public void setCanManageFraud(boolean canManageFraud) {
		this.canManageFraud = canManageFraud;
	}

	/**
	 * @return the canManageCustom
	 */
	public boolean getCanManageCustom() {
		return canManageCustom;
	}

	/**
	 * @param canManageCustom the canManageCustom to set
	 */
	public void setCanManageCustom(boolean canManageCustom) {
		this.canManageCustom = canManageCustom;
	}

	/**
	 * @return the canManageEID
	 */
	public boolean getCanManageEID() {
		return canManageEID;
	}

	/**
	 * @param canManageEID the canManageEID to set
	 */
	public void setCanManageEID(boolean canManageEID) {
		this.canManageEID = canManageEID;
	}

	/**
	 * @return the canManageSanction
	 */
	public boolean getCanManageSanction() {
		return canManageSanction;
	}

	/**
	 * @param canManageSanction the canManageSanction to set
	 */
	public void setCanManageSanction(boolean canManageSanction) {
		this.canManageSanction = canManageSanction;
	}

	/**
	 * @return the canManageBlackList
	 */
	public boolean getCanManageBlackList() {
		return canManageBlackList;
	}

	/**
	 * @param canManageBlackList the canManageBlackList to set
	 */
	public void setCanManageBlackList(boolean canManageBlackList) {
		this.canManageBlackList = canManageBlackList;
	}

	/**
	 * @return the canDoAdministration
	 */
	public boolean getCanDoAdministration() {
		return canDoAdministration;
	}

	/**
	 * @param canDoAdministration the canDoAdministration to set
	 */
	public void setCanDoAdministration(boolean canDoAdministration) {
		this.canDoAdministration = canDoAdministration;
	}

	/**
	 * @return the canViewWorkEfficiacyReport
	 */
	public boolean isCanViewWorkEfficiacyReport() {
		return canViewWorkEfficiacyReport;
	}

	/**
	 * @param canViewWorkEfficiacyReport the canViewWorkEfficiacyReport to set
	 */
	public void setCanViewWorkEfficiacyReport(boolean canViewWorkEfficiacyReport) {
		this.canViewWorkEfficiacyReport = canViewWorkEfficiacyReport;
	}

	/**
	 * @return the canViewRegistrationDetails
	 */
	public boolean isCanViewRegistrationDetails() {
		return canViewRegistrationDetails;
	}

	/**
	 * @param canViewRegistrationDetails the canViewRegistrationDetails to set
	 */
	public void setCanViewRegistrationDetails(boolean canViewRegistrationDetails) {
		this.canViewRegistrationDetails = canViewRegistrationDetails;
	}

	/**
	 * @return the canViewPaymentInDetails
	 */
	public boolean isCanViewPaymentInDetails() {
		return canViewPaymentInDetails;
	}

	/**
	 * @param canViewPaymentInDetails the canViewPaymentInDetails to set
	 */
	public void setCanViewPaymentInDetails(boolean canViewPaymentInDetails) {
		this.canViewPaymentInDetails = canViewPaymentInDetails;
	}

	/**
	 * @return the canViewPaymentOutDetails
	 */
	public boolean isCanViewPaymentOutDetails() {
		return canViewPaymentOutDetails;
	}

	/**
	 * @param canViewPaymentOutDetails the canViewPaymentOutDetails to set
	 */
	public void setCanViewPaymentOutDetails(boolean canViewPaymentOutDetails) {
		this.canViewPaymentOutDetails = canViewPaymentOutDetails;
	}
	
	public boolean isCanManageBeneficiary() {
		return canManageBeneficiary;
	}


	public void setCanManageBeneficiary(boolean canManageBeneficiary) {
		this.canManageBeneficiary = canManageBeneficiary;
	}


	/**
	 * @return the canInitiateDataAnon
	 */
	public boolean isCanInitiateDataAnon() {
		return canInitiateDataAnon;
	}


	/**
	 * @param canInitiateDataAnon the canInitiateDataAnon to set
	 */
	public void setCanInitiateDataAnon(boolean canInitiateDataAnon) {
		this.canInitiateDataAnon = canInitiateDataAnon;
	}

	/**
	 * @return the canApproveDataAnon
	 */
	public boolean isCanApproveDataAnon() {
		return canApproveDataAnon;
	}


	/**
	 * @param canApproveDataAnon the canApproveDataAnon to set
	 */
	public void setCanApproveDataAnon(boolean canApproveDataAnon) {
		this.canApproveDataAnon = canApproveDataAnon;
	}

	/**
	 * @return the canNotLockAccount
	 */
	public boolean isCanNotLockAccount() {
		return canNotLockAccount;
	}


	/**
	 * @param canNotLockAccount the canNotLockAccount to set
	 */
	public void setCanNotLockAccount(boolean canNotLockAccount) {
		this.canNotLockAccount = canNotLockAccount;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserPermission [canWorkOnCFX=" + canWorkOnCFX + ", canWorkOnPFX=" + canWorkOnPFX
				+ ", canViewRegistrationQueue=" + canViewRegistrationQueue + ", canViewRegistrationDetails="
				+ canViewRegistrationDetails + ", canViewPaymentInQueue=" + canViewPaymentInQueue
				+ ", canViewPaymentInDetails=" + canViewPaymentInDetails + ", canViewPaymentOutQueue="
				+ canViewPaymentOutQueue + ", canViewPaymentOutDetails=" + canViewPaymentOutDetails
				+ ", canViewRegistrationReport=" + canViewRegistrationReport + ", canViewPaymentInReport="
				+ canViewPaymentInReport + ", canViewPaymentOutReport=" + canViewPaymentOutReport
				+ ", canViewWorkEfficiacyReport=" + canViewWorkEfficiacyReport + ", canManageWatchListCategory1="
				+ canManageWatchListCategory1 + ", canManageWatchListCategory2=" + canManageWatchListCategory2
				+ ", canUnlockRecords=" + canUnlockRecords + ", canViewDashboard=" + canViewDashboard
				+ ", canManageFraud=" + canManageFraud + ", canManageCustom=" + canManageCustom + ", canManageEID="
				+ canManageEID + ", canManageSanction=" + canManageSanction + ", canManageBlackList="
				+ canManageBlackList + ", canDoAdministration=" + canDoAdministration + ", isReadOnlyUser="
				+ isReadOnlyUser + ", canManageBeneficiary=" + canManageBeneficiary + ", canInitiateDataAnon=" + canInitiateDataAnon+", canApproveDataAnon=" + canApproveDataAnon+", canNotLockAccount=" + canNotLockAccount+"]";
	}

}
