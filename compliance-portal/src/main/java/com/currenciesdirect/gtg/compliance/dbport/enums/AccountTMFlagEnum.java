package com.currenciesdirect.gtg.compliance.dbport.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum AccountTMFlagEnum.
 */
public enum AccountTMFlagEnum {
     
	
	/** The fail. */
	ACCOUNTTMFLAG0(0,"Fail","FAIL"),
	
	/** The pass. */
	ACCOUNTTMFLAG1(1,"Pass","PASS"),
	
	/** The passfor2. */
	ACCOUNTTMFLAG2(2,"Pass","PASS"),
	
	/** The fail. */
	ACCOUNTTMFLAG3(3,"Fail","FAIL"),
	
	/** The passfor4. */
	ACCOUNTTMFLAG4(4,"Pass","PASS");
	
	
	
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/** The status. */
	private String status;

	/**
	 * Instantiates a new account TM flag enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 * @param status the status
	 */
	private AccountTMFlagEnum(Integer databaseStatus, String uiStatus, String status) {
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
		for(AccountTMFlagEnum tmStatus :AccountTMFlagEnum.values()){
			if(tmStatus.getDatabaseStatus().equals(status)){
				return tmStatus.getUiStatus();
			}
		}
		return ACCOUNTTMFLAG0.getUiStatus();
	}
	
	/**
	 * Gets the ui status from database status.
	 *
	 * @param status the status
	 * @return the ui status from database status
	 */
	public static String getUiStatusFromDatabaseStatus(String status){
		for(AccountTMFlagEnum tmStatus :AccountTMFlagEnum.values()){
			if(tmStatus.getStatus().equals(status)){
				return tmStatus.getUiStatus();
			}
		}
		return ACCOUNTTMFLAG0.getUiStatus();
	}
	
	/**
	 * Gets the status from database status.
	 *
	 * @param status the status
	 * @return the status from database status
	 */
	public static String getStatusFromDatabaseStatus(Integer status){
		for(AccountTMFlagEnum tmStatus :AccountTMFlagEnum.values()){
			if(tmStatus.getDatabaseStatus().equals(status)){
				return tmStatus.getStatus();
			}
		}
		return ACCOUNTTMFLAG0.getStatus();
	}
	

	/**
	 * Gets the database status.
	 *
	 * @return the database status
	 */
	public Integer getDatabaseStatus() {
		return databaseStatus;
	}

	/**
	 * Gets the ui status.
	 *
	 * @return the ui status
	 */
	public String getUiStatus() {
		return uiStatus;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	
	
}
