/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.exception;

/**
 * The Enum FraugsterErrors.
 *
 * @author manish
 */
public enum FraugsterErrors {

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

	/** The error while loading fraugster session. */
	ERROR_WHILE_LOADING_FRAUGSTER_SESSION("CH0002", "Error while initializing fraugster Login/logout session"),

	/** The error while populating object. */
	ERROR_WHILE_POPULATING_OBJECT("OB0001", "Error while populating object"),

	/** The invalid request. */
	INVALID_REQUEST("IR0001", "Invalid Request"),

	/** The error while login. */
	ERROR_WHILE_LOGIN("FR0001", "Error while login to fraugster api"),

	/** The error while logout. */
	ERROR_WHILE_LOGOUT("FR0002", "Error while logout from fraugster api"),

	/** The error while signup. */
	ERROR_WHILE_SIGNUP("FR0003", "Error while sign up reqest to fraugster api"),

	/** The error while request transformation. */
	ERROR_WHILE_REQUEST_TRANSFORMATION("FR0004", "Error while transforming fraugster request"),

	/** The error while response transformation. */
	ERROR_WHILE_RESPONSE_TRANSFORMATION("FR0005", "Error while transforming fraugster response"),

	/** The unauthorized exception. */
	UNAUTHORIZED_EXCEPTION("FR0006", "Unauthorized by fraugster server: invalid credentials"),

	/** The server throttler exception. */
	SERVER_THROTTLER_EXCEPTION("FR0007", "Fraugster Server throttler reached to limit"),

	/** The fraugster server generic exception. */
	FRAUGSTER_SERVER_GENERIC_EXCEPTION("FR0008", "Unknow error at Fraugster server"),

	/** The fraugster server invalid request exception. */
	FRAUGSTER_SERVER_INVALID_REQUEST_EXCEPTION("FR0009", "Invalid request by Fraugster server"),

	/** The error while onupdate. */
	ERROR_WHILE_ONUPDATE("FR0010", "Error while On update reqest to fraugster api"),

	/** The error while paymentout. */
	ERROR_WHILE_PAYMENTOUT("FR0011", "Error while payment out reqest to fraugster api"),

	/** The error while paymentin. */
	ERROR_WHILE_PAYMENTIN("FR0012", "Error while payment in reqest to fraugster api"),

	/** The error while fetching cache data. */
	ERROR_WHILE_FETCHING_CACHE_DATA("CH0002", "Error while fetching data from cache"),

	/** The internal server error. */
	INTERNAL_SERVER_ERROR("500", "Internal Server Error");

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
	private FraugsterErrors(String errorCode, String errorDescription) {
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
