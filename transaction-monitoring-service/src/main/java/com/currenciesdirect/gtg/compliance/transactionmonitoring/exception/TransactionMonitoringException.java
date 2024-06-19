/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.exception;

/**
 * The Class TransactionMonitoringException.
 */
public class TransactionMonitoringException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SUCCESSMESSAGE. */
	public static final String SUCCESSMESSAGE = "successMsg";

	/** The transaction monitoring errors. */
	private final TransactionMonitoringErrors transactionMonitoringErrors;

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
	 * Instantiates a new transaction monitoring exception.
	 *
	 * @param transactionMonitoringErrors the transaction monitoring errors
	 */
	public TransactionMonitoringException(TransactionMonitoringErrors transactionMonitoringErrors) {
		super(transactionMonitoringErrors.getErrorCode(),
				new Throwable(transactionMonitoringErrors.getErrorDescription()));
		this.transactionMonitoringErrors = transactionMonitoringErrors;
		this.description = transactionMonitoringErrors.getErrorDescription();
		this.exception = this;
	}

	/**
	 * Instantiates a new transaction monitoring exception.
	 *
	 * @param transactionMonitoringErrors the transaction monitoring errors
	 * @param description the description
	 */
	public TransactionMonitoringException(TransactionMonitoringErrors transactionMonitoringErrors, String description) {
		super(transactionMonitoringErrors.getErrorCode(), new Throwable(description));
		this.transactionMonitoringErrors = transactionMonitoringErrors;
		this.description = description;
		this.exception = this;
	}

	/**
	 * Instantiates a new transaction monitoring exception.
	 *
	 * @param transactionMonitoringErrors the transaction monitoring errors
	 * @param e the e
	 */
	public TransactionMonitoringException(TransactionMonitoringErrors transactionMonitoringErrors, Throwable e) {
		super(transactionMonitoringErrors.getErrorCode(), e);
		this.transactionMonitoringErrors = transactionMonitoringErrors;
		this.description = transactionMonitoringErrors.getErrorDescription();
		this.exception = e;
	}

	/**
	 * Gets the fraugster errors.
	 *
	 * @return the fraugster errors
	 */
	public TransactionMonitoringErrors getFraugsterErrors() {
		return transactionMonitoringErrors;
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
