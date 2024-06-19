package com.currenciesdirect.gtg.compliance.compliancesrv.core;


/**
 * The Enum ServiceStatus.
 */
public enum ServiceStatus {

	/** The pass. */
	PASS(1,"PASS"),
	
	/** The fail. */
	FAIL(2,"FAIL"),
	
	/** The refer. */
	//REFER(3,"REFER"),
	
	/** The pending. */
	PENDING(4, "PENDING"),
	
	/** The not performed. */
	NOT_PERFORMED(5, "NOT_PERFORMED"),
	
	/** The no match. */
	//NO_MATCH(6, "NO_MATCH"),
	
	/** The watch list. */
	WATCH_LIST(7, "WATCH_LIST"),
	
	/** The service failure. */
	SERVICE_FAILURE(8,"SERVICE_FAILURE"),
	
	/** The not required. */
	NOT_REQUIRED(9,"NOT_REQUIRED");
	
	/** The service status as integer. */
	private Integer serviceStatusAsInteger;
	
	/** The service status as string. */
	private String serviceStatusAsString;
	
	/**
	 * Instantiates a new service status.
	 *
	 * @param serviceStatusAsInteger the service status as integer
	 * @param serviceStatusAsString the service status as string
	 */
	ServiceStatus (Integer serviceStatusAsInteger,String serviceStatusAsString){
		this.serviceStatusAsInteger = serviceStatusAsInteger;
		this.serviceStatusAsString = serviceStatusAsString;
	}
	
	/**
	 * Gets the service status as integer.
	 *
	 * @return the service status as integer
	 */
	public Integer getServiceStatusAsInteger() {
		return serviceStatusAsInteger;
	}
	
	/**
	 * Gets the service status as string.
	 *
	 * @return the service status as string
	 */
	public String getServiceStatusAsString() {
		return serviceStatusAsString;
	}
	
	/**
	 * Gets the status as string.
	 *
	 * @param status the status
	 * @return the status as string
	 */
	public static String getStatusAsString(Integer status){
		for(ServiceStatus serviceStatus :ServiceStatus.values()){
			if(serviceStatus.getServiceStatusAsInteger().equals(status)){
				return serviceStatus.getServiceStatusAsString();
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
		for(ServiceStatus serviceStatus :ServiceStatus.values()){
			if(serviceStatus.getServiceStatusAsString().equals(status)){
				return serviceStatus.getServiceStatusAsInteger();
			}
		}
		return null;
	}

}
