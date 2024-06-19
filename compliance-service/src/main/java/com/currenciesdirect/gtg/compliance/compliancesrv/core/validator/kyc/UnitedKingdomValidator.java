/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.kyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * @author manish
 *
 */
public class UnitedKingdomValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitedKingdomValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.
	 * KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isValidated = Boolean.FALSE;
		try {
			Boolean fnameLnameCountryBuiding = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getCountry().isEmpty() && !request.getBuildingName().isEmpty();
			isValidated = validateRequestFields(request, fnameLnameCountryBuiding);
		} catch (Exception exception) {
			LOGGER.warn("Failed UK validations", exception);
			return Boolean.FALSE;
		}

		return isValidated;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param isValidated
	 *            the is validated
	 * @param fnameLnameCountryBuiding
	 *            the fname lname country buiding
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean fnameLnameCountryBuiding) {
		Boolean address = !request.getStreet().isEmpty() && !request.getCity().isEmpty();
		return (fnameLnameCountryBuiding && address);
	}
}
