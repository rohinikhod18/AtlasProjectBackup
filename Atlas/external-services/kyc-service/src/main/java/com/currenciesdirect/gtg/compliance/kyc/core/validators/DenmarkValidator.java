/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * The Class DenmarkValidator.
 *
 * @author manish
 */
public class DenmarkValidator extends AbstractValidator implements Ivalidator {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DenmarkValidator.class);

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

			if (null != request.getPhone()) {
				validateHomePhone(request, validator);
			}

			validator.isValidObject(
					new Object[] { request.getPersonalDetails().getSurName(), request.getAddress().getStreet(),
							request.getAddress().getCity(), request.getAddress().getPostCode() },
					new String[] { "last_name", "street", "city", "post_code" });

		} catch (Exception exception) {
			LOG.debug("error in validator ", exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}

	/**
	 * Validate home phone.
	 *
	 * @param request
	 *            the request
	 * @param validator
	 *            the validator
	 */
	private void validateHomePhone(KYCContactRequest request, FieldValidator validator) {
		for (Phone phone : request.getPhone()) {
			if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))
					&& isNullOrEmpty(phone.getNumber())) {

				validator.addError(Constants.KYC_PHONE_TYPE_HOME, "home_phone");

			}

		}
	}

}
