package com.currenciesdirect.gtg.compliance.exception;

/**
 * The Enum CompliancePortalErrors.
 */
public enum CompliancePortalErrors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),

	/** DATABASE_GENERIC_ERROR. */
	DATABASE_GENERIC_ERROR("CPDB0001", "Error"),

	/** The error while inserting data. */
	ERROR_WHILE_INSERTING_DATA("CPDB0010", "Error while inserting data."),

	/** The error while updating data. */
	ERROR_WHILE_UPDATING_DATA("CPDB00020", "Error while updating data."),

	/** The error while deleting data. */
	ERROR_WHILE_DELETING_DATA("CPDB0030", "Error while deleting data."),

	/** The error while searching data. */
	ERROR_WHILE_SEARCHING_DATA("CPDB0040", "Error while searching data."),

	/** The error while searching data from cache. */
	ERROR_WHILE_SEARCHING_DATA_FROM_CACHE("CPDB0050", "Error while searching data from cache."),

	/** The error while loading data into cache. */
	ERROR_WHILE_LOADING_DATA_INTO_CACHE("CPDB0060", "Error while loading data into cache."),

	/** The error while fetching data blacklist type. */
	ERROR_WHILE_FETCHING_DATA("CPDB0070", "Error while fetching data"),

	/** The error while validating data. */
	ERROR_WHILE_VALIDATING_DATA("CPDB0051", "Error while validating data."),

	/** The invalid request. */
	INVALID_REQUEST("CPIR0001", "Invalid Request"),
	
	UNAUTHORISED_USER("CPUU00001","Unauthorised user"), 
	ERROR_WHILE_SAVING_DATA_INTO_BROADCAST_QUEUE("CPBQ00001","Error While Inserting Data in Broadcase queue"),
	
	ERROR_WHILE_GETTING_PROVIDER_RESPONSE("CPDB0080","Error while fetching provder response from database"),
	
	/** The error current date is less than past date". */
	ERROR_FROMTO_LESS_THAN_DATEFROM("CPDB0090", "FromTo is less than DateFrom"),
	
	ERROR_WHILE_GETTING_DEVICE_INFO_DATA("CPDB0100","Error while fetching device info from database");

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
	private CompliancePortalErrors(String errorCode, String errorDescription) {
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
