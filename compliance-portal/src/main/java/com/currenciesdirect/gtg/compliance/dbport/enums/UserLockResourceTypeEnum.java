package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum UserLockResourceTypeEnum.
 */
public enum UserLockResourceTypeEnum {

	/** The pay out. */
	PAY_OUT(1,"PAY_OUT","Payment Out"),
	
	/** The pay in. */
	PAY_IN(2,"PAY_IN", "Payment In"),
	
	/** The contact. */
	CONTACT(3,"CONTACT", "Contact"),
	
	/** The account. */
	ACCOUNT(4, "ACCOUNT", "Account");
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/** The status. */
	private String status;
	
	/**
	 * Instantiates a new user lock resource type enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 * @param status the status
	 */
	UserLockResourceTypeEnum (Integer databaseStatus,String uiStatus, String status){
		this.databaseStatus = databaseStatus;
		this.uiStatus = uiStatus;
		this.status = status;
	}
	
	/**
	 * Gets the ui status from database status.
	 *
	 * @param status the status
	 * @return the ui status from database status
	 */
	public static String getUiStatusFromDatabaseStatus(Integer status){
		for(UserLockResourceTypeEnum serviceStatus :UserLockResourceTypeEnum.values()){
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
		for(UserLockResourceTypeEnum serviceStatus :UserLockResourceTypeEnum.values()){
			if(serviceStatus.getUiStatus().equals(status)){
				return serviceStatus.getDatabaseStatus();
			}
		}
		return null;
	}
	
	/**
	 * Gets the ui status from database status with UI format.
	 *
	 * @param status the status
	 * @return the ui status from database status with UI format
	 */
	public static String getUiStatusFromDatabaseStatusWithUIFormat(Integer status){
		for(UserLockResourceTypeEnum serviceStatus :UserLockResourceTypeEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getStatus();
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

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

}
