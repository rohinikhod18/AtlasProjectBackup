/**
 * 
 */
package com.currenciesdirect.gtg.compliance.exception;

/**
 * @author manish
 *
 */
public enum InternalRuleErrors implements Errors {
	
	/**
	 * SUCCESS Response
	 */
	SUCCESS("0000", "Success"),

	/**
	 * FAILED Response
	 */
	FAILED("9999", "Service Error"),
	
	INVALID_REQUEST_TYPE("0001","Request type must be valid"),

	/**
	 * DATABASE_GENERIC_ERROR
	 */
	DATABASE_GENERIC_ERROR("DB0001","Error while saving the data"),
	
	/** The invalid request. */
	INVALID_REQUEST("WLIR0001", "Invalid Request"),
	
	
	DUPLICATE_DATA_ADDITION("WLDB0052", "Data Already Exists");
	
	
	/**
	 * variable for error code
	 */
	private String errorCode;
	/**
	 * variable for error code
	 */
	private String errorDescription;

	/**
	 * @param errorCode
	 * @param errorDescription
	 */
	private InternalRuleErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * @return error description
	 */
	@Override
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * @return error code
	 */
	@Override
	public String getErrorCode() {
		return this.errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.errorCode + ":" + this.errorDescription;
	}
}
