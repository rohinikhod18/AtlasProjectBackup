package com.currenciesdirect.gtg.compliance.customchecks.domain;

/**
 * The Enum ResponseStatus.
 */
public enum  ResponseStatus{
	
	/** The success. */
	PASS("PASS"),
	
	/** The failed. */
	FAIL("FAIL"),
	
	NOT_REQUIRED("NOT_REQUIRED"),
	
	NOT_PERFORMED("NOT_PERFORMED"), 
	
	SERVICE_FAILURE("SERVICE_FAILURE"),
	;
	/** The status. */
	private String status;
	
	/**
	 * Instantiates a new response status.
	 *
	 * @param status the status
	 */
	private ResponseStatus(String status){
		this.status = status;
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
