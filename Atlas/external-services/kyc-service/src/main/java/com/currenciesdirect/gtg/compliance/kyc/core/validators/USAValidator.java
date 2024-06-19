package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * The class USAValidator
 * 
 * @author deepak.k
 *
 */
public class USAValidator extends AbstractValidator implements Ivalidator {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(USAValidator.class);

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
							request.getPersonalDetails().getSurName(), 
							request.getAddress().getBuildingName(),
							request.getAddress().getStreet(), request.getAddress().getCity(),
							request.getAddress().getPostCode() },
					new String[] { "first_name", "last_name", "building_name", "street", "city", "post_code" });

		} catch (Exception exception) {
			LOG.debug("error in validator ", exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}
}
