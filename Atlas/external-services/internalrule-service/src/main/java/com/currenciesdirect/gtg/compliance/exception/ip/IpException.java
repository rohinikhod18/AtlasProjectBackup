/**
 * 
 */
package com.currenciesdirect.gtg.compliance.exception.ip;

import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class IpException.
 *
 * @author manish
 */
public class IpException extends InternalRuleException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SUCCESSMESSAGE. */
	public static final String SUCCESSMESSAGE = "successMsg";

	/**
	 * Instantiates a new ip exception.
	 *
	 * @param ipErrors
	 *            the ip errors
	 */

	public IpException(IpErrors ipErrors) {
		super(ipErrors, new Throwable(ipErrors.getErrorDescription()));
	}

	/**
	 * Instantiates a new ip exception.
	 *
	 * @param ipErrors
	 *            the ip errors
	 * @param description
	 *            the description
	 */
	public IpException(IpErrors ipErrors, String description) {
		super(ipErrors, new Throwable(description));
	}

	/**
	 * Instantiates a new ip exception.
	 *
	 * @param ipErrors
	 *            the ip errors
	 * @param e
	 *            the e
	 */
	public IpException(IpErrors ipErrors, Throwable e) {
		super(ipErrors, e);
	}
}
