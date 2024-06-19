/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionErrors.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.exception;

/**
 * The Enum SanctionErrors.
 */
public enum SanctionErrors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),

	/** DATABASE_GENERIC_ERROR. */
	DATABASE_GENERIC_ERROR("DB0001", "Error while saving the data"),


	/** The error while fetching data from db to cache. */
	ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE("DB0003", "Error while fetching data from db to cache"),

	/** The error while inserting cache. */
	ERROR_WHILE_INSERTING_CACHE("CM0001", "Error while adding in cache memory"),

	/** The error while deleting from memory cache. */
	ERROR_WHILE_DELETING_FROM_MEMORY_CACHE("CM0002", "Error while deleting from cache memory"),

	/** The error while fetching from cache. */
	ERROR_WHILE_FETCHING_FROM_CACHE("CM0003", "Error while searching from cache"),

	/** The error while initialising cache. */
	ERROR_WHILE_INITIALISING_CACHE("CM0004", "Error while initialising cache memory"),

	/** The error while updating cache. */
	ERROR_WHILE_UPDATING_CACHE("CM0005", "Error while updating cache memory"),

	/** The error while loading provider init data. */
	ERROR_WHILE_LOADING_PROVIDER_INIT_DATA("DB0006", "Error while loading provider initialization data from database"),

	/** The error while sending request to sanction provider. */
	ERROR_WHILE_SENDING_REQUEST_TO_SANCTION_PROVIDER("SN0001", "Sanction provider error out"),

	/** The sanction generic exception. */
	SANCTION_GENERIC_EXCEPTION("SN0002", "Sanction generic exception"),

	/** The error while transformation request. */
	ERROR_WHILE_TRANSFORMATION_REQUEST("SN0003", "Error while request transformation"),

	/** The error while transformation response. */
	ERROR_WHILE_TRANSFORMATION_RESPONSE("SN0005", "Error while response transformation"),
	
	/** The validation exception. */
	VALIDATION_EXCEPTION("SN0004","Some mandatory parameters are missing: "),
	
	/** The errror in finscan port. */
	ERRROR_IN_FINSCAN_PORT("SN0005","Exception in finscan Port: ");
	
	/** variable for error code. */
	private String errorCode;
	
	/** variable for error code. */
	private String errorDescription;

	/**
	 * Instantiates a new sanction errors.
	 *
	 * @param errorCode the error code
	 * @param errorDescription the error description
	 */
	private SanctionErrors(String errorCode, String errorDescription) {
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
