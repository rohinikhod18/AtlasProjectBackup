package com.currenciesdirect.gtg.compliance.exception.blacklist;

import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class BlacklistException.
 */
public class BlacklistException extends InternalRuleException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param blacklistErrors
	 *            the blacklist errors
	 * @param orgException
	 *            the org exception
	 */
	public BlacklistException(BlacklistErrors blacklistErrors, Throwable orgException) {
		super(blacklistErrors, orgException);
	}

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param blacklistErrors
	 *            the blacklist errors
	 */
	public BlacklistException(BlacklistErrors blacklistErrors) {
		super(blacklistErrors, new Throwable(blacklistErrors.getErrorDescription()));
	}

	/**
	 * Instantiates a new blacklist exception.
	 *
	 * @param blacklistErrors
	 *            the blacklist errors
	 * @param description
	 *            the description
	 */
	public BlacklistException(BlacklistErrors blacklistErrors, String description) {
		super(blacklistErrors, new Throwable(description));
	}

	

}
