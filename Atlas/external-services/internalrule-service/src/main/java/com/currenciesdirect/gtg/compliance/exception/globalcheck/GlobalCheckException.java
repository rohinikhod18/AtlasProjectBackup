package com.currenciesdirect.gtg.compliance.exception.globalcheck;

import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class GlobalCheckException.
 */
public class GlobalCheckException extends InternalRuleException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	

	/**
	 * Instantiates a new global check exception.
	 *
	 * @param globalCheckErrors the global check errors
	 * @param orgException the org exception
	 */
	public GlobalCheckException(GlobalCheckErrors globalCheckErrors, Throwable orgException) {
		super(globalCheckErrors, orgException);
	}

	/**
	 * Instantiates a new globalCheckErrors exception.
	 *
	 * @param globalCheckErrors
	 *            the globalCheckErrors 
	 */
	public GlobalCheckException(GlobalCheckErrors globalCheckErrors) {
		super(globalCheckErrors, new Throwable(globalCheckErrors.getErrorDescription()));
	}


	/**
	 * Instantiates a new global check exception.
	 *
	 * @param globalCheckErrors the global check errors
	 * @param description the description
	 */
	public GlobalCheckException(GlobalCheckErrors globalCheckErrors, String description) {
		super(globalCheckErrors, new Throwable(description));
	}

}
