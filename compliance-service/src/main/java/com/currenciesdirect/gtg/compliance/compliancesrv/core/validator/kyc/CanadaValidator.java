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
public class CanadaValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CanadaValidator.class);

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
			Boolean fnameLnameDOBCivic = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getCivicNumber().isEmpty();
			isValid = validateRequestFields(request, fnameLnameDOBCivic);

		} catch (Exception exception) {
			LOGGER.warn("Failed Canada Validation", exception);
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
	 * @param fnameLnameDOBCivic
	 *            the fname lname DOB civic
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean fnameLnameDOBCivic) {
		Boolean address = !request.getCountry().isEmpty() && !request.getStreet().isEmpty()
				&& !request.getCity().isEmpty() && !request.getState().isEmpty();
		return (fnameLnameDOBCivic && address && !request.getPostCode().isEmpty());
	}
}
