package com.currenciesdirect.gtg.compliance.customchecks.exception;


/**
 * The Class CustomerDataScanException.
 */
public class CustomChecksException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The customer data scan errors. */
	private final CustomChecksErrors customerDataScanErrors;
	
	/** The description. */
	private final String description;
	
	/** The org exception. */
	private final Throwable orgException;
	
	/**
	 * Instantiates a new customer data scan exception.
	 *
	 * @param customerDataScanErrors the customer data scan errors
	 * @param orgException the org exception
	 */
	public CustomChecksException(CustomChecksErrors customerDataScanErrors, Throwable orgException){
		super(customerDataScanErrors.getErrorCode(),orgException);
		this.customerDataScanErrors = customerDataScanErrors;
		this.description = customerDataScanErrors.getErrorDescription();
		this.orgException = orgException;
	}
	
	/**
	 * Instantiates a new customer data scan exception.
	 *
	 * @param customerDataScanErrors the customer data scan errors
	 */
	public CustomChecksException(CustomChecksErrors customerDataScanErrors){
		super(customerDataScanErrors.getErrorCode(),new Throwable(customerDataScanErrors.getErrorDescription()));
		this.customerDataScanErrors=customerDataScanErrors;
		this.description=customerDataScanErrors.getErrorDescription();
		this.orgException = this;
	}
	
	/**
	 * Instantiates a new customer data scan exception.
	 *
	 * @param customerDataScanErrors the customer data scan errors
	 * @param description the description
	 */
	public CustomChecksException(CustomChecksErrors customerDataScanErrors,String description){
		super(customerDataScanErrors.getErrorCode(),new Throwable(description));
		this.customerDataScanErrors=customerDataScanErrors;
		this.description=description;
		this.orgException = this;
	}

	/**
	 * Gets the customer data scan errors.
	 *
	 * @return the customer data scan errors
	 */
	public CustomChecksErrors getCustomerDataScanErrors() {
		return customerDataScanErrors;
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
