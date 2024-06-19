package com.currenciesdirect.gtg.compliance.core.countrycheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.dbport.countrycheck.CountryCheckDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckException;

/**
 * The Class CountryCheckSearchImpl.
 */
public class CountryCheckSearchImpl implements ICountryCheckSearch {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CountryCheckSearchImpl.class);
	
	/** The check DB service. */
	private ICountryCheckDBService checkDBService = CountryCheckDBServiceImpl.getInstance();
	
	/** The country check search. */
	@SuppressWarnings("squid:S3077")
	private static volatile ICountryCheckSearch countryCheckSearch = null;

	/**
	 * Instantiates a new country check search impl.
	 */
	private CountryCheckSearchImpl() {

	}

	public static ICountryCheckSearch getInstance() {
		if (countryCheckSearch == null) {
			synchronized (CountryCheckSearchImpl.class) {
				if (countryCheckSearch == null) {
					countryCheckSearch = new CountryCheckSearchImpl();
				}
			}
		}
		return countryCheckSearch;
	}

	@Override
	public CountryCheckContactResponse getCountryCheckResponse(String country) throws CountryCheckException {

		if (country == null || country.isEmpty()) {
			throw new CountryCheckException(CountryCheckErrors.INVALID_REQUEST);
		}
		try {
			return checkDBService.getCountryCheckResponse(country);
		} catch (CountryCheckException e) {
			logError(e);
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CountryCheckException(CountryCheckErrors.ERROR_WHILE_SEARCHING_DATA);
		}
	}

	private void logError(Throwable exception) {
		LOG.debug("Exception: ", exception);
	}

}
