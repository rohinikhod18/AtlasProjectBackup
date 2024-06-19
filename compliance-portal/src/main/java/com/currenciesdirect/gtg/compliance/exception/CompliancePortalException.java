package com.currenciesdirect.gtg.compliance.exception;

/**
 * The Class CompliancePortalException.
 */
public class CompliancePortalException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The compliance portal errors. */
	private final CompliancePortalErrors compliancePortalErrors;

	/** The description. */
	private final String description;

	/** The org exception. */
	private final Throwable orgException;

	
	/**
	 * Instantiates a new compliance portal exception.
	 *
	 * @param compliancePortalErrors the compliance portal errors
	 * @param orgException the org exception
	 */
	public CompliancePortalException(CompliancePortalErrors compliancePortalErrors, Throwable orgException) {
		super(compliancePortalErrors.getErrorCode(), orgException);
		this.compliancePortalErrors = compliancePortalErrors;
		this.description = compliancePortalErrors.getErrorDescription();
		this.orgException = orgException;
	}

	
	/**
	 * Instantiates a new compliance portal exception.
	 *
	 * @param compliancePortalErrors the compliance portal errors
	 */
	public CompliancePortalException(CompliancePortalErrors compliancePortalErrors) {
		super(compliancePortalErrors.getErrorCode(), new Throwable(compliancePortalErrors.getErrorDescription()));
		this.compliancePortalErrors = compliancePortalErrors;
		this.description = compliancePortalErrors.getErrorDescription();
		this.orgException = this;
	}

	
	/**
	 * Instantiates a new compliance portal exception.
	 *
	 * @param compliancePortalErrors the compliance portal errors
	 * @param description the description
	 */
	public CompliancePortalException(CompliancePortalErrors compliancePortalErrors, String description) {
		super(compliancePortalErrors.getErrorCode(), new Throwable(description));
		this.compliancePortalErrors = compliancePortalErrors;
		this.description = description;
		this.orgException = this;
	}

	
	/**
	 * Gets the compliance portal errors.
	 *
	 * @return the compliance portal errors
	 */
	public CompliancePortalErrors getCompliancePortalErrors() {
		return compliancePortalErrors;
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
