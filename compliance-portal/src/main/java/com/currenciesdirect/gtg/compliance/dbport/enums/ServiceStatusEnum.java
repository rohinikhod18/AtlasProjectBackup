package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum ServiceStatusEnum.
 */
public enum ServiceStatusEnum {
	
	/** The pass. */
	PASS(1,"Pass","PASS"),
	
	/** The fail. */
	FAIL(2,"Fail","FAIL"),
	
	/** The refer. */
	REFER(3,"Refer","REFER"),
	
	/** The pending. */
	PENDING(4, "PENDING","PENDING"),
	
	/** The not performed. */
	NOT_PERFORMED(5, "Not Performed","NOT_PERFORMED"),
	
	/** The no match. */
	NO_MATCH(6, "No Match","NO_MATCH"),
	
	/** The watch list. */
	WATCH_LIST(7, "Watch List","WATCH_LIST"),
	
	/** The service failure. */
	SERVICE_FAILURE(8,"Service Failure","SERVICEFAILURE"),
	
	/** The not required. */
	NOT_REQUIRED(9,"Not Required","NOT_REQUIRED");
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/** The status. */
	private String status;
	
	/**
	 * Instantiates a new service status enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 * @param status the status
	 */
	ServiceStatusEnum (Integer databaseStatus,String uiStatus,String status){
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
		for(ServiceStatusEnum serviceStatus :ServiceStatusEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return NOT_REQUIRED.getUiStatus();
	}
	
	public static String getUiStatusFromDatabaseStatus(String status){
		for(ServiceStatusEnum serviceStatus :ServiceStatusEnum.values()){
			if(serviceStatus.getStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return NOT_REQUIRED.getUiStatus();
	}
	
	/**
	 * Gets the status from database status.
	 *
	 * @param status the status
	 * @return the status from database status
	 */
	public static String getStatusFromDatabaseStatus(Integer status){
		for(ServiceStatusEnum serviceStatus :ServiceStatusEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getStatus();
			}
		}
		return NOT_REQUIRED.getStatus();
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
	public String getStatus(){
		return this.status;
	}
	
}
