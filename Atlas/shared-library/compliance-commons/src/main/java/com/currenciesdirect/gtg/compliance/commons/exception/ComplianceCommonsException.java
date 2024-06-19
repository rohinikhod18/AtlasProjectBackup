package com.currenciesdirect.gtg.compliance.commons.exception;

public class ComplianceCommonsException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The compliance commons error. */
	private final ComplianceCommonsError complianceCommonsError;
	
	/** The org exception. */
	private final Throwable orgException;
	
	/** The description. */
	private final String description;
	
	/**
	 * Instantiates a new compliance commons exception.
	 *
	 * @param complianceCommonsError the compliance commons error
	 * @param orgException the org exception
	 */
	public ComplianceCommonsException(ComplianceCommonsError complianceCommonsError,Throwable orgException) {
		super(complianceCommonsError.getErrorCode(), orgException);
		this.complianceCommonsError = complianceCommonsError;
		this.description = complianceCommonsError.getErrorDescription();
		this.orgException = orgException;
	}
	
	/**
	 * Instantiates a new compliance commons exception.
	 *
	 * @param complianceCommonsError the compliance commons error
	 */
	public ComplianceCommonsException(ComplianceCommonsError complianceCommonsError) {
		super(complianceCommonsError.getErrorCode(), new Throwable(complianceCommonsError.getErrorDescription()));
		this.complianceCommonsError = complianceCommonsError;
		this.description = complianceCommonsError.getErrorDescription();
		this.orgException = this;
	}
	
	/**
	 * Instantiates a new compliance commons exception.
	 *
	 * @param complianceCommonsError the compliance commons error
	 * @param description the description
	 */
	public ComplianceCommonsException(ComplianceCommonsError complianceCommonsError,String description) {
		super(complianceCommonsError.getErrorCode(), new Throwable(description));
		this.complianceCommonsError = complianceCommonsError;
		this.description = complianceCommonsError.getErrorDescription();
		this.orgException = this;
	}

	/**
	 * @return the complianceCommonsError
	 */
	public ComplianceCommonsError getComplianceCommonsError() {
		return complianceCommonsError;
	}

	/**
	 * @return the orgException
	 */
	public Throwable getOrgException() {
		return orgException;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
