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
public class HongKongValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(HongKongValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.
	 * KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean valid;
		try {
			Boolean nameDOBGendercheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getGender().isEmpty();
			valid = validateRequestFields(request, nameDOBGendercheck);
		} catch (Exception exception) {
			LOGGER.warn("Failed HongKong validations", exception);
		valid=Boolean.FALSE;
			return valid;
		}

		return valid;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param nameDOBGendercheck
	 *            the name DOB gendercheck
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameDOBGendercheck) {
		Boolean addressCheck = !request.getCountry().isEmpty() && !request.getBuildingName().isEmpty()
				&& !request.getStreet().isEmpty() && !request.getCity().isEmpty();
		Boolean statePostcode = !request.getState().isEmpty() && !request.getPostCode().isEmpty();

		return (nameDOBGendercheck && addressCheck && statePostcode);
	}

}
