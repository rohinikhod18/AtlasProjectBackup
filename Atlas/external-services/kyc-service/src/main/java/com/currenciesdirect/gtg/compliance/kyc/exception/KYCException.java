/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.exception;

/**
 * @author manish
 *
 */
public class KYCException extends Exception {

	private static final long serialVersionUID = 1L;

	private final KYCErrors kycErrors;

	/** The error description. */
	private final String description;

	private final Throwable exception;

	/**
	 * Instantiates a new KYC exception.
	 *
	 * @param kycErrors
	 *            the kyc errors
	 * @param e
	 *            the e
	 */
	public KYCException(KYCErrors kycErrors, Throwable e) {
		super(kycErrors.getErrorCode(), e);
		this.kycErrors = kycErrors;
		this.description = kycErrors.getErrorDescription();
		this.exception = e;
	}

	/**
	 * Instantiates a new KYC exception.
	 *
	 * @param kycErrors the kyc errors
	 */
	public KYCException(KYCErrors kycErrors) {
		super(kycErrors.getErrorCode(), new Throwable(kycErrors.getErrorDescription()));
		this.kycErrors = kycErrors;
		this.description = kycErrors.getErrorDescription();
		this.exception = this;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public KYCErrors getkycErrors() {
		return kycErrors;
	}

	/**
	 * @param b2bErrors
	 */

	public Throwable getException() {
		return exception;
	}

}
