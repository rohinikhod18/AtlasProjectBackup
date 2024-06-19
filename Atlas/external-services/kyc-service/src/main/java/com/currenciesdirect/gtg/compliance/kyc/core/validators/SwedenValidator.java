/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * The Class SwedenValidator.
 *
 * @author manish
 */
public class SwedenValidator extends AbstractValidator implements Ivalidator {

	/** The Constant LOG. */
	private	static final Logger LOG = LoggerFactory.getLogger(SwedenValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.commons.domain.kyc
	 * .KYCContactRequest)
	 */
	@Override
	public FieldValidator validateKYCRequest(KYCContactRequest request) throws KYCException {
		FieldValidator validator = new FieldValidator();
		try {

			validator.isValidObject(
					new Object[] { request.getPersonalDetails().getForeName(),
							request.getPersonalDetails().getSurName(), request.getPersonalDetails().getTitle(),
							request.getPersonalDetails().getDob(), request.getAddress().getBuildingNumber(),
							request.getAddress().getStreet(), request.getAddress().getCity(),
							request.getAddress().getPostCode() },
					new String[] { "first_name", "last_name", "title", "dob", "building_number", "street", "city",
							"post_code" });
		} catch (Exception exception) {
			LOG.debug("error in validator ", exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}

}
