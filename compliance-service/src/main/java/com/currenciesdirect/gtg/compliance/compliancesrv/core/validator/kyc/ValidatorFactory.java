/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.kyc;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * @author manish
 *
 */
public class ValidatorFactory {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(ValidatorFactory.class);
	
	private static ValidatorFactory factory = null;
	
	private ValidatorFactory() {
		
	}
	
	public static ValidatorFactory getInstance() {
		if(factory == null) {
			factory = new ValidatorFactory();
		}
		return factory;
	}
	
	
	public IValidator getValidator(String countryName){ //TO_DO throws KYCException 
		IValidator ivalidator;
		
		ivalidator = getValidatorForLexisNexisOtherCountries(countryName);
		if(null == ivalidator)
			ivalidator = getValidatorForLexisNexisWesternCountries(countryName);
		if(null == ivalidator)
			getValidatorForGBGroup(countryName);
		
		return ivalidator;
	}

	private IValidator getValidatorForGBGroup(String countryName) {
		IValidator gbGroupvalidator = null;
		try {
			switch(countryName.toUpperCase()) {
			case Constants.DENMARK:
				gbGroupvalidator = new DenmarkValidator();
				break;
			case Constants.GERMANY:
				gbGroupvalidator = new GermanyValidator();
				break;	
			case Constants.NETHERLANDS:
				gbGroupvalidator = new NetherlandsValidator();
				break;
			case Constants.NORWAY:
				gbGroupvalidator = new NorwayValidator();
				break;
			case Constants.SWEDEN:
				gbGroupvalidator = new SwedenValidator();
				break;
			case Constants.SWITZERLAND:
				gbGroupvalidator = new SwitzerlandValidator();
				break;
			case Constants.UNITEDKINGDOM:
				gbGroupvalidator = new UnitedKingdomValidator();
				break;
			case Constants.SPAIN:
				gbGroupvalidator = new SpainValidator();
				break;
							
			default :
				break;
			}
		} catch (Exception e) {
			LOG.error("Error in ValidatorFactory : getValidatorForGBGroup()",e);
		}
		return gbGroupvalidator;
	}

	private IValidator getValidatorForLexisNexisOtherCountries(String countryName) {
		IValidator lexisNexisOtherCountriesValidator = null;
		try {			
			switch(countryName.toUpperCase()) {
			
			case Constants.SOUTHAFRICA:
				lexisNexisOtherCountriesValidator = new SouthAfricaValidator();
				break;
			case Constants.MEXICO:
				lexisNexisOtherCountriesValidator = new MexicoValidator();
				break;
			case Constants.BRAZIL:
				lexisNexisOtherCountriesValidator = new BrazilValidator();
				break;
			case Constants.HONGKONG:
				lexisNexisOtherCountriesValidator = new HongKongValidator();
				break;
			case Constants.JAPAN:
				lexisNexisOtherCountriesValidator = new JapanValidator();
				break;
			default :
				break;
			}
			} catch (Exception e) {
				LOG.error("Error in ValidatorFactory : getValidator()",e);
			}
			return lexisNexisOtherCountriesValidator;
		
	}
	
	private IValidator getValidatorForLexisNexisWesternCountries(String countryName) {
		IValidator lexisNexisWesternCountriesValidator = null;

		try {
			switch (countryName.toUpperCase()) {
			case Constants.LUXEMBOURG:
				lexisNexisWesternCountriesValidator = new LuxembourgValidator();
				break;
			case Constants.AUSTRIA:
				lexisNexisWesternCountriesValidator = new AustriaValidator();
				break;
			case Constants.AUSTRALIA:
				lexisNexisWesternCountriesValidator = new AustraliaValidator();
				break;
			case Constants.NEWZEALAND:
				lexisNexisWesternCountriesValidator = new NewZealandValidator();
				break;
			case Constants.CANADA:
				lexisNexisWesternCountriesValidator = new CanadaValidator();
				break;
			
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error("Error in ValidatorFactory : getValidator()", e);
		}
		return lexisNexisWesternCountriesValidator;
	}
}
