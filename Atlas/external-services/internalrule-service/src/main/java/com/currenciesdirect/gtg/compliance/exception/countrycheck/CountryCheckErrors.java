package com.currenciesdirect.gtg.compliance.exception.countrycheck;

import com.currenciesdirect.gtg.compliance.exception.Errors;

/**
 * The Enum BlacklistErrors.
 */
public enum CountryCheckErrors implements Errors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),

	/** DATABASE_GENERIC_ERROR. */
	DATABASE_GENERIC_ERROR("CCDB0001", "Error"),

	/** The error while searching data. */
	ERROR_WHILE_SEARCHING_DATA("CCDB0040", "Error while searching data."),
	
	
	ERROR_COUNTRY_NOT_FOUND("CCDB0040", "Country Not Found"),

	/** The invalid request. */
	INVALID_REQUEST("CCIR0001", "Invalid Request");

	/** variable for error code. */
	private String errorCode;

	/** variable for error code. */
	private String errorDescription;

	/**
	 * Instantiates a new blacklist errors.
	 *
	 * @param errorCode
	 *            the error code
	 * @param errorDescription
	 *            the error description
	 */
	private CountryCheckErrors(String errorCode, String errorDescription) {
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
