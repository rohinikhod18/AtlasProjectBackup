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
public class SwedenValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SwedenValidator.class);

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
			Boolean fnameLnameTitleDOB = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getTitle().isEmpty() && !request.getDob().isEmpty();
			isValid = validateRequestFields(request, fnameLnameTitleDOB);
		} catch (Exception exception) {
			LOGGER.warn("Failed Sweden Validations", exception);
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
	 * @param fnameLnameTitleDOB
	 *            the fname lname title DOB
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean fnameLnameTitleDOB) {
		Boolean genderAddress = !request.getGender().isEmpty() && !request.getCountry().isEmpty()
				&& !request.getBuildingName().isEmpty() && !request.getStreet().isEmpty();
		return (fnameLnameTitleDOB && genderAddress && !request.getPostCode().isEmpty());
	}

}
