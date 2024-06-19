package com.currenciesdirect.gtg.compliance.compliancesrv.core;

public enum UserLockResourceTypeEnum {

	PAY_IN(1,"PAY_OUT"),
	PAY_OUT(2,"PAY_IN"),
	CONTACT(3,"CONTACT"),
	ACCOUNT(4, "ACCOUNT");
	
	private Integer databaseStatus;
	private String uiStatus;
	
	UserLockResourceTypeEnum (Integer databaseStatus,String uiStatus){
		this.databaseStatus = databaseStatus;
		this.uiStatus = uiStatus;
	}
	
	public static String getUiStatusFromDatabaseStatus(Integer status){
		for(UserLockResourceTypeEnum serviceStatus :UserLockResourceTypeEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return null;
	}
	
	public static Integer getDbStatusFromUiStatus(String status){
		for(UserLockResourceTypeEnum serviceStatus :UserLockResourceTypeEnum.values()){
			if(serviceStatus.getUiStatus().equals(status)){
				return serviceStatus.getDatabaseStatus();
			}
		}
		return null;
	}
	
	public String getUiStatus() {
		return this.uiStatus;
	}			
	
	public Integer getDatabaseStatus() {
		return this.databaseStatus;
	}
}
