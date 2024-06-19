/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Identification;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

/**
 * The Class SouthAfricaValidator.
 *
 * @author manish
 */
public class SouthAfricaValidator extends AbstractValidator implements Ivalidator {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SouthAfricaValidator.class);

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
			if (null != request.getIdentification()) {
				validateNationalIdNumber(request, validator);
			}

			super.validateKYCRequestFnameLnameDobPostCode(request);

			// AT -260.region removed.-Tejas
			validator.isValidObject(new Object[] { request.getAddress().getCity(), request.getAddress().getAddress1() },
					new String[] { "city", "street" });

		} catch (Exception exception) {
			LOG.debug("error in validator ", exception);
			validator.addError(Constants.VALIDATION_ERROR, "");
		}

		return validator;
	}

	/**
	 * Validate national id number.
	 *
	 * @param request
	 *            the request
	 * @param validator
	 *            the validator
	 */
	private void validateNationalIdNumber(KYCContactRequest request, FieldValidator validator) {
		for (Identification identification : request.getIdentification()) {
			if (Constants.KYC_NATIONAL_ID_NUMBER.equalsIgnoreCase(identification.getType().replaceAll("\\s+", ""))
					&& isNullOrEmpty(identification.getNumber().replaceAll("\\s+", ""))) {

				validator.addError(Constants.KYC_NATIONAL_ID_NUMBER, "national_id_number");

			}
		}
	}

}
