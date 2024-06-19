/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.exception;

/**
 * The Enum FraugsterErrors.
 *
 * @author manish
 */
public enum TransactionMonitoringErrors {

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error"),

	/** DATABASE_GENERIC_ERROR. */
	DATABASE_GENERIC_ERROR("DB0001", "Error while saving the data"),

	/** The error while loading provider init data. */
	ERROR_WHILE_LOADING_PROVIDER_INIT_DATA("DB0002", "Error while loading provider initialization data from database"),

	/** The error while loading cache. */
	ERROR_WHILE_LOADING_CACHE("CH0001", "Error loading cache init data"),

	/** The error while populating object. */
	ERROR_WHILE_POPULATING_OBJECT("OB0001", "Error while populating object"),

	/** The invalid request. */
	INVALID_REQUEST("IR0001", "Invalid Request"),

	/** The error while signup. */
	ERROR_WHILE_SIGNUP("TM0003", "Error while sign up reqest to TransactionMonitoring api"),

	/** The error while request transformation. */
	ERROR_WHILE_REQUEST_TRANSFORMATION("TM0004", "Error while transforming TransactionMonitoring request"),

	/** The error while response transformation. */
	ERROR_WHILE_RESPONSE_TRANSFORMATION("FR0005", "Error while transforming TransactionMonitoring response"),

	/** The unauthorized exception. */
	UNAUTHORIZED_EXCEPTION("TM0006", "Unauthorized by TransactionMonitoring server: invalid credentials"),

	/** The server throttler exception. */
	SERVER_THROTTLER_EXCEPTION("TM0007", "Fraugster Server throttler reached to limit"),

	/** The TransactionMonitoring server generic exception. */
	TRANSACTIONMONITORING_SERVER_GENERIC_EXCEPTION("TM0008", "Unknow error at TransactionMonitoring server"),

	/** The TransactionMonitoring server invalid request exception. */
	TRANSACTIONMONITORING_SERVER_INVALID_REQUEST_EXCEPTION("TM0009", "Invalid request by TransactionMonitoring server"),

	/** The error while onupdate. */
	ERROR_WHILE_ONUPDATE("TM0010", "Error while On update reqest to TransactionMonitoring api"),

	/** The error while paymentout. */
	ERROR_WHILE_PAYMENTOUT("TM0011", "Error while payment out reqest to TransactionMonitoring api"),

	/** The error while paymentin. */
	ERROR_WHILE_PAYMENTIN("TM0012", "Error while payment in reqest to TransactionMonitoring api"),

	/** The error while fetching cache data. */
	ERROR_WHILE_FETCHING_CACHE_DATA("CH0002", "Error while fetching data from cache"),

	/** The internal server error. */
	INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
	
	/** The access denied. */
	ACCESS_DENIED("403", "Access Denied Error"),
	
	/** The entity not found. */
	ENTITY_NOT_FOUND("404", "Entity not found"),
	
	/** The service unavailable. */
	SERVICE_UNAVAILABLE("503", "Service Unavailable");

	/** variable for error code. */
	private String errorCode;

	/** variable for error code. */
	private String errorDescription;

	/**
	 * Instantiates a new fraugster errors.
	 *
	 * @param errorCode
	 *            the error code
	 * @param errorDescription
	 *            the error description
	 */
	private TransactionMonitoringErrors(String errorCode, String errorDescription) {
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
