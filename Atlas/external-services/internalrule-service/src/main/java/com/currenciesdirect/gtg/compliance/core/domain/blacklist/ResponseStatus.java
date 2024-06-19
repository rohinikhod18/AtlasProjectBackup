package com.currenciesdirect.gtg.compliance.core.domain.blacklist;

/**
 * The Enum ResponseStatus.
 */
public enum ResponseStatus {

	/** The success. */
	SUCCESS("Success"),

	/** The failed. */
	FAILED("Failed"),

	/** The pass. */
	PASS("PASS"),

	/** The fail. */
	FAIL("FAIL"),

	/** The not found. */
	NOT_FOUND("Not Found");

	/** The status. */
	private String status;

	/**
	 * Instantiates a new response status.
	 *
	 * @param status
	 *            the status
	 */
	ResponseStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
}
