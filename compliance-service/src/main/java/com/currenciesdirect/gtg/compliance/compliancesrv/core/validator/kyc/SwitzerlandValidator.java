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
public class SwitzerlandValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SwitzerlandValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.
	 * KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isValid = Boolean.FALSE;
		try {
			Boolean fnameLnameCountryBuilding = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getCountry().isEmpty() && !request.getBuildingName().isEmpty();
			isValid = validaetRequestFields(request, fnameLnameCountryBuilding);
		} catch (Exception exception) {
			LOGGER.warn("Failed Switzerland validations", exception);
			isValid = Boolean.FALSE;
			return isValid;
		}

		return isValid;
	}

	/**
	 * Validaet request fields.
	 *
	 * @param request
	 *            the request
	 * @param isValid
	 *            the is valid
	 * @param fnameLnameCountryBuilding
	 *            the fname lname country building
	 * @return the boolean
	 */
	private Boolean validaetRequestFields(Contact request, Boolean fnameLnameCountryBuilding) {
		Boolean address = !request.getStreet().isEmpty() && !request.getCity().isEmpty()
				&& !request.getPostCode().isEmpty();
		return (fnameLnameCountryBuilding && address);
	}

}
