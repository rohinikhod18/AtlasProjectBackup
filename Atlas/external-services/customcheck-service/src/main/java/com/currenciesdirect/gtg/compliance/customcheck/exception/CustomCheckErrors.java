package com.currenciesdirect.gtg.compliance.customcheck.exception;

public enum CustomCheckErrors {

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
	DATABASE_GENERIC_ERROR("DB0001", "Error while saving the data"),
	
	ERROR_WHILE_SEARCHING_DATA("DB0002", "Error while searching data."),
	
	ERROR_WHILE_DELETING_DATA("DB0003", "Error while deleteing data."),
	
	ERROR_WHILE_INSERTING_DATA("DB0004", "Error while inserting data."),
	
	ERROR_WHILE_UPDATING_DATA("DB0005", "Error while updating data."),
	
	INVALID_DATA("DB0006", "Invalid Data"),
	
	ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE("DB0003", "Error while fetching data from db to cache"),

	INVALID_REQUEST("IR0001", "Invalid Request"),

	ERROR_WHILE_INSERTING_CACHE("CM0001", "Error while adding in cache memory"),

	ERROR_WHILE_DELETING_FROM_MEMORY_CACHE("CM0002", "Error while deleting from cache memory"),

	ERROR_WHILE_FETCHING_FROM_CACHE("CM0003", "Error while searching from cache"),
	
	ERROR_WHILE_INITIALISING_CACHE("CM0004", "Error while initialising cache memory"),

	ERROR_WHILE_UPDATING_CACHE("CM0005", "Error while updating cache memory"),
	
	ERROR_WHILE_LOGGING_DATA("CM0005", "Error while logging data"); 

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
	private CustomCheckErrors(String errorCode, String errorDescription) {
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
