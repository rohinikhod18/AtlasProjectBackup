package com.currenciesdirect.gtg.compliance.exception;

/**
 * The Class InternalRuleException.
 */
public class InternalRuleException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The errors. */
	private final transient Errors errors;
	
	 /** The description. */
	private  final String description;

	/** The org exception. */
	private  final Throwable orgException;
	
	
	/**
	 * Instantiates a new internal rule exception.
	 *
	 * @param errors the errors
	 * @param orgException the org exception
	 */
	public InternalRuleException(Errors errors, Throwable orgException) {
		super(errors.getErrorCode(), orgException);
		this.errors = errors;
		this.description = errors.getErrorDescription();
		this.orgException = orgException;
	}

	
	/**
	 * Instantiates a new internal rule exception.
	 *
	 * @param errors the errors
	 */
	public InternalRuleException(Errors errors) {
		super(errors.getErrorCode(), new Throwable(errors.getErrorDescription()));
		this.errors = errors;
		this.description = errors.getErrorDescription();
		this.orgException = this;
	}

	
	/**
	 * Instantiates a new internal rule exception.
	 *
	 * @param errors the errors
	 * @param description the description
	 */
	public InternalRuleException(Errors errors, String description) {
		super(errors.getErrorCode(), new Throwable(description));
		this.errors = errors;
		this.description = description;
		this.orgException = this;
	}

	public Errors getErrors() {
		return errors;
	}

	public String getDescription() {
		return description;
	}

	public Throwable getOrgException() {
		return orgException;
	}


}
