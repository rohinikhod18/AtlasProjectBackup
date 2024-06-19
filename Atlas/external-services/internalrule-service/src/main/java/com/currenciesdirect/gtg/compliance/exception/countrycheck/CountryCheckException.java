package com.currenciesdirect.gtg.compliance.exception.countrycheck;

import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class CountryCheckException.
 */
public class CountryCheckException extends InternalRuleException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new country check exception.
	 *
	 * @param countryCheckErrors the country check errors
	 * @param orgException the org exception
	 */
	public CountryCheckException(CountryCheckErrors countryCheckErrors, Throwable orgException) {
		super(countryCheckErrors, orgException);
	}

	/**
	 * Instantiates a new country check exception.
	 *
	 * @param countryCheckErrors the country check errors
	 */
	public CountryCheckException(CountryCheckErrors countryCheckErrors) {
		super(countryCheckErrors, new Throwable(countryCheckErrors.getErrorDescription()));
	}

	/**
	 * Instantiates a new country check exception.
	 *
	 * @param countryCheckErrors the country check errors
	 * @param description the description
	 */
	public CountryCheckException(CountryCheckErrors countryCheckErrors, String description) {
		super(countryCheckErrors, new Throwable(description));
	}

}
