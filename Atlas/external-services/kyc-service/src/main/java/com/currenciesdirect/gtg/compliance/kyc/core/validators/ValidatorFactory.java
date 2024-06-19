/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * A factory for creating Validator objects.
 *
 * @author manish
 */
public class ValidatorFactory {

	/** The factory. */
	private static ValidatorFactory factory = null;

	/**
	 * Instantiates a new validator factory.
	 */
	private ValidatorFactory() {

	}

	/**
	 * Gets the single instance of ValidatorFactory.
	 *
	 * @return single instance of ValidatorFactory
	 */
	public static ValidatorFactory getInstance() {
		if (factory == null) {
			factory = new ValidatorFactory();
		}
		return factory;
	}

	/**
	 * Gets the validator.
	 *
	 * @param countryName
	 *            the country name
	 * @return the validator
	 * @throws KYCException
	 *             the KYC exception
	 */
	public Ivalidator getValidator(String countryName) throws KYCException {
		Ivalidator kycValidator = null;

		try {
			kycValidator = getNorthCountryWiseValidator(countryName);
			if(null == kycValidator)
				kycValidator = getSouthCountryWiseValidator(countryName);
		} catch (KYCException kycException) {
			throw kycException;
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

		return kycValidator;
	}

	private Ivalidator getNorthCountryWiseValidator(String countryName)  {
		Ivalidator kycValidator = null;
		switch (countryName.toUpperCase()) {
		case Constants.COUNTRY_GERMANY:
			kycValidator = new GermanyValidator();
			break;
		case Constants.COUNTRY_NORWAY:
			kycValidator = new NorwayValidator();
			break;
		case Constants.COUNTRY_SWEDEN:
			kycValidator = new SwedenValidator();
			break;
		case Constants.COUNTRY_UNITEDKINGDOM:
			kycValidator = new UnitedKingdomValidator();
			break;
		case Constants.COUNTRY_AUSTRIA:
			kycValidator = new AustriaValidator();
			break;
		case Constants.COUNTRY_SPAIN:
			kycValidator = new SpainValidator();
			break;
		default:
			break;
		}
		return kycValidator;
	}
	
	private Ivalidator getSouthCountryWiseValidator(String countryName) throws KYCException {
		Ivalidator kycValidator = null;
		switch (countryName.toUpperCase()) {
		case Constants.COUNTRY_AUSTRALIA:
			kycValidator = new AustraliaValidator();
			break;
		case Constants.COUNTRY_NEWZEALAND:
			kycValidator = new NewZealandValidator();
			break;
		case Constants.COUNTRY_SOUTHAFRICA:
			kycValidator = new SouthAfricaValidator();
			break;
		//Add for AT-3662 - Automate US EIDV
		case Constants.COUNTRY_USA:
			kycValidator = new USAValidator();
			break;
		default:
			throw new KYCException(KYCErrors.COUNTRY_NOT_SUPPORTED);
		}
		return kycValidator;
	}
}
