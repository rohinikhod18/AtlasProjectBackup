package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum ProfileComplianceStatus.
 */
public enum ProfileComplianceStatus {
	
	/** The active. */
	ACTIVE(1,"Active"),
	
	/** The not performed. */
	NOT_PERFORMED(2,"Not Performed"),
	
	/** The in queue. */
	IN_QUEUE(3,"In Queue"),
	
	/** The inactive. */
	INACTIVE(4, "Inactive"),
	
	/** The rejected. */
	REJECTED(5, "Rejected"),
	
	/** The watch list. */
	WATCH_LIST(6, "Watch List");
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/**
	 * Instantiates a new profile compliance status.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 */
	ProfileComplianceStatus (Integer databaseStatus,String uiStatus){
		this.databaseStatus = databaseStatus;
		this.uiStatus = uiStatus;
	}
	
	/**
	 * Gets the ui status from database status.
	 *
	 * @param status the status
	 * @return the ui status from database status
	 */
	public static String getUiStatusFromDatabaseStatus(Integer status){
		for(ProfileComplianceStatus serviceStatus :ProfileComplianceStatus.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return null;
	}
	
	/**
	 * Gets the db status from ui status.
	 *
	 * @param status the status
	 * @return the db status from ui status
	 */
	public static Integer getDbStatusFromUiStatus(String status){
		for(ProfileComplianceStatus serviceStatus :ProfileComplianceStatus.values()){
			if(serviceStatus.getUiStatus().equals(status)){
				return serviceStatus.getDatabaseStatus();
			}
		}
		return null;
	}
	
	/**
	 * Gets the ui status.
	 *
	 * @return the ui status
	 */
	public String getUiStatus() {
		return this.uiStatus;
	}			
	
	/**
	 * Gets the database status.
	 *
	 * @return the database status
	 */
	public Integer getDatabaseStatus() {
		return this.databaseStatus;
	}
}
