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
public class AustraliaValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AustraliaValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.
	 * KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isvalidate = Boolean.FALSE;
		try {
			Boolean fnameLnameDOBCountry = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
					&& !request.getDob().isEmpty() && !request.getCountry().isEmpty();
			isvalidate = validateRequestFields(request, fnameLnameDOBCountry);
		} catch (Exception exception) {
			LOGGER.warn("Failed Australia Validation", exception);
			return Boolean.FALSE;
		}
		return isvalidate;
	}

	/**
	 * Validate request fields.
	 *
	 * @param request
	 *            the request
	 * @param fnameLnameDOBCountry
	 *            the fname lname DOB country
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean fnameLnameDOBCountry) {
		Boolean cityPostcode = !request.getCity().isEmpty() && !request.getState().isEmpty()
				&& !request.getPostCode().isEmpty() && !request.getNationalIdType().isEmpty();
		return (fnameLnameDOBCountry && cityPostcode && !request.getMedicareReferenceNumber().isEmpty());
	}

}
