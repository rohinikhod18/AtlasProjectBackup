package com.currenciesdirect.gtg.compliance.blacklist.exception;


/**
 * The Class BlacklistException.
 */
public class BlacklistException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The blacklist errors. */
	private final BlacklistErrors blacklistErrors;

	/** The description. */
	private final String description;

	/** The org exception. */
	private final Throwable orgException;

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param blacklistErrors
	 *            the blacklist errors
	 * @param orgException
	 *            the org exception
	 */
	public BlacklistException(BlacklistErrors blacklistErrors, Throwable orgException) {
		super(blacklistErrors.getErrorCode(), orgException);
		this.blacklistErrors = blacklistErrors;
		this.description = blacklistErrors.getErrorDescription();
		this.orgException = orgException;
	}

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param customerDataScanErrors
	 *            the customer data scan errors
	 */
	public BlacklistException(BlacklistErrors customerDataScanErrors) {
		super(customerDataScanErrors.getErrorCode(), new Throwable(customerDataScanErrors.getErrorDescription()));
		this.blacklistErrors = customerDataScanErrors;
		this.description = customerDataScanErrors.getErrorDescription();
		this.orgException = this;
	}

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param blacklistErrors
	 *            the blacklist errors
	 * @param description
	 *            the description
	 */
	public BlacklistException(BlacklistErrors blacklistErrors, String description) {
		super(blacklistErrors.getErrorCode(), new Throwable(description));
		this.blacklistErrors = blacklistErrors;
		this.description = description;
		this.orgException = this;
	}

	/**
	 * Gets the blacklist errors.
	 *
	 * @return the blacklist errors
	 */
	public BlacklistErrors getBlacklistErrors() {
		return blacklistErrors;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the org exception.
	 *
	 * @return the org exception
	 */
	public Throwable getOrgException() {
		return orgException;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return description;
	}

}
