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
public class DenmarkValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DenmarkValidator.class);

	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isvalidate = Boolean.FALSE;

		try {
			Boolean lnameCountryBuildingStreet = !request.getLastName().isEmpty() && !request.getCountry().isEmpty()
					&& !request.getBuildingName().isEmpty() && !request.getStreet().isEmpty();
			isvalidate = validateRequestFields(request, lnameCountryBuildingStreet);

		} catch (Exception exception) {
			LOGGER.warn("Failed Denamrk validations", exception);
			isvalidate=Boolean.FALSE;
			return isvalidate;
		}

		return isvalidate;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param isvalidate
	 *            the isvalidate
	 * @param lnameCountryBuildingStreet
	 *            the lname country building street
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean lnameCountryBuildingStreet) {
		Boolean address = !request.getCity().isEmpty() && !request.getPostCode().isEmpty()
				&& request.getPhoneHome().isEmpty();
		Boolean identityDocCheck = request.getPassportNumber().isEmpty() && request.getNationalIdType().isEmpty();
		return (lnameCountryBuildingStreet && address && identityDocCheck);
	}
}
