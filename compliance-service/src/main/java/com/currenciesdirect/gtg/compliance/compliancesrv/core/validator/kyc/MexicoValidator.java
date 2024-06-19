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
public class MexicoValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(MexicoValidator.class);

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
			Boolean nameDOBGendercheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getGender().isEmpty();
			isValid = validateRequestFields(request, nameDOBGendercheck);
		} catch (Exception exception) {
			LOGGER.warn("Failed Mexico validations", exception);
			isValid=Boolean.FALSE;
			return isValid;
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
	 * @param nameDOBGendercheck
	 *            the name DOB gendercheck
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameDOBGendercheck) {
		Boolean address = !request.getCountry().isEmpty() && !request.getCity().isEmpty()
				&& !request.getState().isEmpty() && !request.getPostCode().isEmpty();
		return (nameDOBGendercheck && address && !request.getStateOfBirth().isEmpty());
	}
}
