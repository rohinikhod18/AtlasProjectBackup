package com.currenciesdirect.gtg.compliance.exception.globalcheck;

import com.currenciesdirect.gtg.compliance.exception.Errors;

/**
 * The Enum GlobalCheckErrors.
 */
public enum GlobalCheckErrors implements Errors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),
	
	APPLY_RULE_FAILED("GC001","Error while applying rule"),
	
	STATE_CAN_NOT_BE_BLANK("GC001","state can not be blank");


	/** variable for error code. */
	private String errorCode;

	/** variable for error code. */
	private String errorDescription;

	/**
	 * Instantiates a new GlobalCheck errors.
	 *
	 * @param errorCode
	 *            the error code
	 * @param errorDescription
	 *            the error description
	 */
	private GlobalCheckErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the error description.
	 *
	 * @return error description
	 */
	@Override
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * Gets the error code.
	 *
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
