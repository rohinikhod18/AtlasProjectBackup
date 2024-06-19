/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum CustomerTypeEnum.
 *
 * @author bnt
 */
public enum CustomerTypeEnum {
	
	/** The cfx. */
	CFX(1,"CFX"),
	
	/** The pfx. */
	PFX(2,"PFX"),
	
	/** The cfx etailer. */
	CFX_ETAILER(3, "CFX (etailer)");
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/**
	 * Instantiates a new customer type enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 */
	CustomerTypeEnum (Integer databaseStatus,String uiStatus){
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
		for(CustomerTypeEnum custType :CustomerTypeEnum.values()){
			if(custType.getDatabaseStatus().equals(status)){
				return custType.getUiStatus();
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
		for(CustomerTypeEnum custType :CustomerTypeEnum.values()){
			if(custType.getUiStatus().equals(status)){
				return custType.getDatabaseStatus();
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
