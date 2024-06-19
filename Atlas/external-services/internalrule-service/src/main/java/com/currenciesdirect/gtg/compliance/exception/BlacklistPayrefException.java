package com.currenciesdirect.gtg.compliance.exception;

public class BlacklistPayrefException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SUCCESSMESSAGE. */
	public static final String SUCCESSMESSAGE = "successMsg";

	/** The fraugster errors. */
	private final BlacklistPayrefErrors blacklistPayrefErrors;

	/** The exception. */
	private final Throwable exception;

	/** The error description. */
	private final String description;

	

	/**
	 * @param blacklistPayrefErrors
	 */
	public BlacklistPayrefException(BlacklistPayrefErrors blacklistPayrefErrors) {
		super(blacklistPayrefErrors.getErrorCode(), new Throwable(blacklistPayrefErrors.getErrorDescription()));
		this.blacklistPayrefErrors = blacklistPayrefErrors;
		this.description = blacklistPayrefErrors.getErrorDescription();
		this.exception = this;
	}

	/**
	 * @param blacklistPayrefErrors
	 * @param description
	 */
	public BlacklistPayrefException(BlacklistPayrefErrors blacklistPayrefErrors, String description) {
		super(blacklistPayrefErrors.getErrorCode(), new Throwable(description));
		this.blacklistPayrefErrors = blacklistPayrefErrors;
		this.description = description;
		this.exception = this;
	}
	
	/**
	 * @param blacklistPayrefErrors
	 * @param e
	 */
	public BlacklistPayrefException(BlacklistPayrefErrors blacklistPayrefErrors, Throwable e) {
		super(blacklistPayrefErrors.getErrorCode(), e);
		this.blacklistPayrefErrors = blacklistPayrefErrors;
		this.description = blacklistPayrefErrors.getErrorDescription();
		this.exception = e;
	}


	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	
	

}
