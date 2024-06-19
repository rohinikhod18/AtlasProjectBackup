/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionException.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.exception;

/**
 * The Class SanctionException.
 */
public class SanctionException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sanction errors. */
	private final SanctionErrors sanctionErrors;

	/** The description. */
	private final String description;

	/** The org exception. */
	private final Throwable orgException;

	/** The Constant SUCCESSMESSAGE. */
	public static final String SUCCESSMESSAGE = "success";

	/** The Constant ERRORMESSAGE. */
	public static final String ERRORMESSAGE = "failed";

	/**
	 * Instantiates a new sanction exception.
	 *
	 * @param sanctionErrors the sanction errors
	 * @param orgException the org exception
	 */
	public SanctionException(SanctionErrors sanctionErrors, Throwable orgException) {
		super(sanctionErrors.getErrorCode(), orgException);
		this.sanctionErrors = sanctionErrors;
		this.description = sanctionErrors.getErrorDescription();
		this.orgException = orgException;
	}

	/**
	 * Instantiates a new sanction exception.
	 *
	 * @param sanctionErrors the sanction errors
	 */
	public SanctionException(SanctionErrors sanctionErrors) {
		this(sanctionErrors, new Throwable(sanctionErrors.getErrorDescription()));
	}

	/**
	 * Instantiates a new sanction exception.
	 *
	 * @param sanctionErrors the sanction errors
	 * @param description the description
	 */
	public SanctionException(SanctionErrors sanctionErrors, String description) {
		this(sanctionErrors, new Throwable(description));
	}

	/**
	 * Gets the sanction errors.
	 *
	 * @return the sanction errors
	 */
	public SanctionErrors getSanctionErrors() {
		return sanctionErrors;
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
}
