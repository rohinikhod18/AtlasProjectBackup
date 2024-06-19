package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Enum ResponseStatus.
 */
public enum  ResponseStatus{
	
	/** The success. */
	SUCCESS("Success"),
	
	/** The failed. */
	FAILED("Failed");
	
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
