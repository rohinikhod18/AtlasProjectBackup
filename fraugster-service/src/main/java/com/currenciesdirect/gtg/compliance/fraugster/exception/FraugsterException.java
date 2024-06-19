/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.exception;

/**
 * The Class FraugsterException.
 *
 * @author manish
 */
public class FraugsterException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SUCCESSMESSAGE. */
	public static final String SUCCESSMESSAGE = "successMsg";

	/** The fraugster errors. */
	private final FraugsterErrors fraugsterErrors;

	/** The exception. */
	private final Throwable exception;

	/** The error description. */
	private final String description;

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Instantiates a new fraugster exception.
	 *
	 * @param fraugsterErrors
	 *            the fraugster errors
	 */
	public FraugsterException(FraugsterErrors fraugsterErrors) {
		super(fraugsterErrors.getErrorCode(), new Throwable(fraugsterErrors.getErrorDescription()));
		this.fraugsterErrors = fraugsterErrors;
		this.description = fraugsterErrors.getErrorDescription();
		this.exception = this;
	}

	/**
	 * Instantiates a new fraugster exception.
	 *
	 * @param fraugsterErrors
	 *            the fraugster errors
	 * @param description
	 *            the description
	 */
	public FraugsterException(FraugsterErrors fraugsterErrors, String description) {
		super(fraugsterErrors.getErrorCode(), new Throwable(description));
		this.fraugsterErrors = fraugsterErrors;
		this.description = description;
		this.exception = this;
	}

	/**
	 * Instantiates a new fraugster exception.
	 *
	 * @param fraugsterErrors
	 *            the fraugster errors
	 * @param e
	 *            the e
	 */
	public FraugsterException(FraugsterErrors fraugsterErrors, Throwable e) {
		super(fraugsterErrors.getErrorCode(), e);
		this.fraugsterErrors = fraugsterErrors;
		this.description = fraugsterErrors.getErrorDescription();
		this.exception = e;
	}

	/**
	 * Gets the fraugster errors.
	 *
	 * @return the fraugster errors
	 */
	public FraugsterErrors getFraugsterErrors() {
		return fraugsterErrors;
	}

	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}

}
