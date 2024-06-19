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
public class BrazilValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BrazilValidator.class);

	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isvalidate = Boolean.FALSE;
		try {
			Boolean nameDOBGendercheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getGender().isEmpty();
			isvalidate = validateRequestFields(request, nameDOBGendercheck);
		} catch (Exception exception) {
			LOGGER.warn("Failed Brazil Validation", exception);
			return Boolean.FALSE;
		}

		return isvalidate;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param nameDOBGendercheck
	 *            the name DOB gendercheck
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameDOBGendercheck) {
		Boolean address = !request.getCountry().isEmpty() && !request.getCity().isEmpty()
				&& !request.getState().isEmpty() && !request.getPostCode().isEmpty();
		return (nameDOBGendercheck && address && !request.getNationalIdNumber().isEmpty());
	}
}
