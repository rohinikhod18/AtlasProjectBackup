/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * @author basits
 *
 */
public enum OnfidoStatusEnum {
	
	CLEAR(1,"CLEAR"),

	REJECT(2,"REJECTED"),
	
	CAUTION(3,"CAUTION"),
	
	SUSPECT(4,"SUSPECTED"),
	
	FRAUD(5,"FRAUD"),
	
	ACCEPT(6,"ACCEPT"),
	
	CONSIDER(7,"CONSIDER"),
	
	/** The not required. */
	NOT_REQUIRED(9,"NOT_REQUIRED");
	
	/** The service status as integer. */
	private Integer onfidoStatusAsInteger;
	
	/** The service status as string. */
	private String onfidoStatusAsString;
	
	/**
	 * Instantiates a new service status.
	 *
	 * @param serviceStatusAsInteger the service status as integer
	 * @param serviceStatusAsString the service status as string
	 */
	OnfidoStatusEnum (Integer onfidoStatusAsInteger,String onfidoStatusAsString){
		this.onfidoStatusAsInteger = onfidoStatusAsInteger;
		this.onfidoStatusAsString = onfidoStatusAsString;
	}
	
	/**
	 * @return the onfidoStatusAsInteger
	 */
	public Integer getOnfidoStatusAsInteger() {
		return onfidoStatusAsInteger;
	}

	/**
	 * @return the onfidoStatusAsString
	 */
	public String getOnfidoStatusAsString() {
		return onfidoStatusAsString;
	}

	/**
	 * Gets the status as string.
	 *
	 * @param status the status
	 * @return the status as string
	 */
	public static String getStatusAsString(Integer status){
		for(OnfidoStatusEnum serviceStatus :OnfidoStatusEnum.values()){
			if(serviceStatus.getOnfidoStatusAsInteger().equals(status)){
				return serviceStatus.getOnfidoStatusAsString();
			}
		}
		return null;
	}
	
	/**
	 * Gets the status as integer.
	 *
	 * @param status the status
	 * @return the status as integer
	 */
	public static Integer getStatusAsInteger(String status){
		for(OnfidoStatusEnum serviceStatus :OnfidoStatusEnum.values()){
			if(serviceStatus.getOnfidoStatusAsString().equalsIgnoreCase(status)){
				return serviceStatus.getOnfidoStatusAsInteger();
			}
		}
		return null;
	}
}
