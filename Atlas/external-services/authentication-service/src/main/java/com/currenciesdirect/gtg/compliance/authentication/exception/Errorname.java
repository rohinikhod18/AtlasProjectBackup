/**
 * 
 */
package com.currenciesdirect.gtg.compliance.authentication.exception;

/**
 * @author manish
 *
 */
public enum Errorname {
	
	/**
	 * SUCCESS Response
	 */
	SUCCESS("0000", "Success"),

	/**
	 * FAILED Response
	 */
	FAILED("9999", "Service Error"),

	/**
	 * DATABASE_GENERIC_ERROR
	 */
	DATABASE_GENERIC_ERROR("DB0001","Error while saving the data"),
	
	INVALID_REQUEST("IR0001","Invalid Request"),
	
	AUTHENTICATION_GENERIC_EXCEPTION("AU0001","authentication generic exception"),
	
	AUTHENTICATION_FAILURE_EXCEPTION("AU0002","User name or password is incorrect");
	
	
	
	
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
	private Errorname(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * @return error description
	 */
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * @return error code
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return this.errorCode + ":" + this.errorDescription;
	}
}
