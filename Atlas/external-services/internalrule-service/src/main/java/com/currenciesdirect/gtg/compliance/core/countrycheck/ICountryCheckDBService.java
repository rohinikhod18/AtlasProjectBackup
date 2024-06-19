package com.currenciesdirect.gtg.compliance.core.countrycheck;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckException;

/**
 * The Interface ICountryCheckDBService.
 */
@FunctionalInterface
public interface ICountryCheckDBService {

	/**
	 * Gets the country check response.
	 *
	 * @param country
	 *            the country
	 * @return the country check response
	 * @throws CountryCheckException
	 *             the country check exception
	 */
	public CountryCheckContactResponse getCountryCheckResponse(String country) throws CountryCheckException;
}
