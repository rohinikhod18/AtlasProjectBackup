package com.currenciesdirect.gtg.compliance.exception;

public enum BlacklistPayrefErrors {
	
	UNAUTHORIZED_EXCEPTION("FR0006", "Unauthorized by fraugster server: invalid credentials"),

	/** The server throttler exception. */
	SERVER_THROTTLER_EXCEPTION("FR0007", "Fraugster Server throttler reached to limit"),

	/** The fraugster server generic exception. */
	BLACKLISTPAYREF_SERVER_GENERIC_EXCEPTION("FR0008", "Unknow error at Fraugster server"),

	/** The fraugster server invalid request exception. */
	BLACKLISTPAYREF_SERVER_INVALID_REQUEST_EXCEPTION("FR0009", "Invalid request by Fraugster server"),

	/** SUCCESS Response. */
	SUCCESS("0000", "Success"),

	/** FAILED Response. */
	FAILED("9999", "Service Error");
	/** variable for error code. */
	private String errorCode;

	/** variable for error code. */
	private String errorDescription;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	private BlacklistPayrefErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String toString() {
		return this.errorCode + ":" + this.errorDescription;
	}
}
