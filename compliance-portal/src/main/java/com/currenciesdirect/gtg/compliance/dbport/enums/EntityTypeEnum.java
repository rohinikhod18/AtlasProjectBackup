/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum EntityTypeEnum.
 *
 * @author bnt
 */
public enum EntityTypeEnum {
	
	/** The unknown. */
	UNKNOWN(0,"UNKNOWN"),
	
	/** The account. */
	ACCOUNT(1,"ACCOUNT"),
	
	/** The beneficiary. */
	BENEFICIARY(2,"BENEFICIARY"),
	
	/** The contact. */
	CONTACT(3,"CONTACT"),
	
	/** The bank. */
	BANK(4, "BANK"),
	
	/** The debtor. */
	DEBTOR(5, "DEBTOR");
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/**
	 * Instantiates a new entity type enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 */
	EntityTypeEnum (Integer databaseStatus,String uiStatus){
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
		for(EntityTypeEnum serviceStatus :EntityTypeEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return null;
	}
	
	/**
	 * Gets the database status from ui status.
	 *
	 * @param status the status
	 * @return the database status from ui status
	 */
	public static Integer getDatabaseStatusFromUiStatus(String status){
		for(EntityTypeEnum serviceStatus :EntityTypeEnum.values()){
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
