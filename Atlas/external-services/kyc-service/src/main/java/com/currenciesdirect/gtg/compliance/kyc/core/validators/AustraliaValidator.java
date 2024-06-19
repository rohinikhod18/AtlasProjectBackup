/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;


/**
 * The Class AustraliaValidator.
 *
 * @author manish
 */
public class AustraliaValidator extends AbstractValidator implements Ivalidator {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AustraliaValidator.class);

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.validators.Ivalidator#validateKYCRequest(KYCContactRequest)
	 */
	@Override
	public FieldValidator validateKYCRequest(KYCContactRequest request) throws KYCException {

		FieldValidator fieldValidator = new FieldValidator();
		try {

			fieldValidator.isValidObject(
					new Object[] { request.getPersonalDetails().getForeName(),
							request.getPersonalDetails().getSurName(), request.getAddress().getPostCode() },
					new String[] { "first_name", "last_name", "post_code" });

		} catch (Exception exception) {
			LOG.debug("error in validator ", exception);
			fieldValidator.addError(Constants.VALIDATION_ERROR, "");
		}

		return fieldValidator;
	}
	
	
}
