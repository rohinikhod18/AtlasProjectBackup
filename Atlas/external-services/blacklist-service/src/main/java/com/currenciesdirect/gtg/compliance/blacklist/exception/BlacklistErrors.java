package com.currenciesdirect.gtg.compliance.blacklist.exception;

/**
 * The Enum BlacklistErrors.
 */
public enum BlacklistErrors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),

	/** DATABASE_GENERIC_ERROR. */
	DATABASE_GENERIC_ERROR("BLDB0001", "Error"),

	/** The error while inserting data. */
	ERROR_WHILE_INSERTING_DATA("BLDB0010", "Error while inserting data."),

	/** The error while updating data. */
	ERROR_WHILE_UPDATING_DATA("BLDB00020", "Error while updating data."),

	/** The error while deleting data. */
	ERROR_WHILE_DELETING_DATA("BLDB0030", "Error while deleting data."),

	/** The error while searching data. */
	ERROR_WHILE_SEARCHING_DATA("BLDB0040", "Error while searching data."),

	/** The error while searching data from cache. */
	ERROR_WHILE_SEARCHING_DATA_FROM_CACHE("BLDB0050", "Error while searching data from cache."),

	/** The error while loading data into cache. */
	ERROR_WHILE_LOADING_DATA_INTO_CACHE("BLDB0060", "Error while loading data into cache."),

	/** The error while fetching data blacklist type. */
	ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE("BLDB0070", "Error while fetching data from balcklistType"),

	/** The error while validating data. */
	ERROR_WHILE_VALIDATING_DATA("BLDB0051", "Error while validating data."),

	/** The invalid request. */
	INVALID_REQUEST("BLIR0001", "Invalid Request");

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
	private BlacklistErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the error description.
	 *
	 * @return error description
	 */
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * Gets the error code.
	 *
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
	@Override
	public String toString() {
		return this.errorCode + ":" + this.errorDescription;
	}
}
