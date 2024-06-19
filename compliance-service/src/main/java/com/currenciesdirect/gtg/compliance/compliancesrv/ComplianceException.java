package com.currenciesdirect.gtg.compliance.compliancesrv;

/**
 * The Class ComplianceException.
 */
public class ComplianceException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The ipc. */
	private final InternalProcessingCode ipc;

	/** The exception. */
	private final Throwable exception;

	/**
	 * Instantiates a new compliance exception.
	 *
	 * @param ipc the ipc
	 * @param exception the exception
	 */
	public ComplianceException(InternalProcessingCode ipc, Throwable exception) {
		super(exception);
		this.ipc = ipc;
		this.exception = exception;
	}
	
	public ComplianceException(InternalProcessingCode ipc) {
		this.ipc = ipc;
		this.exception = this;
	}

	/**
	 * Gets the ipc.
	 *
	 * @return the ipc
	 */
	public InternalProcessingCode getIpc() {
		return ipc;
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
