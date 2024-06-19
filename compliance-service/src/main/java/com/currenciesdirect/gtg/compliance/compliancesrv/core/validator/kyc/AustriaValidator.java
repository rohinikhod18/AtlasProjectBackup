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
public class AustriaValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AustriaValidator.class);

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
			Boolean fnameLnameDOBCountry = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getCountry().isEmpty();
			isValid = validateRequestFields(request, fnameLnameDOBCountry);

		} catch (Exception exception) {
			LOGGER.warn("Failed Austria Validation", exception);
			return Boolean.FALSE;
		}

		return isValid;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param isValid
	 *            the is valid
	 * @param fnameLnameDOBCountry
	 *            the fname lname DOB country
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean fnameLnameDOBCountry) {
		Boolean address = !request.getStreet().isEmpty() && !request.getCity().isEmpty()
				&& !request.getPostCode().isEmpty();
		return (fnameLnameDOBCountry && address);
	}
}
