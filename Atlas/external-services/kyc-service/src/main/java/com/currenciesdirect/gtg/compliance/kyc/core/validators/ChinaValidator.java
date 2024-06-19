/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

/**
 * The Class ChinaValidator.
 *
 * @author manish
 */
public class ChinaValidator extends AbstractValidator implements Ivalidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.kyc.core.validators.Ivalidator#
	 * validateKYCRequest(com.currenciesdirect.gtg.compliance.commons.domain.kyc
	 * .KYCContactRequest)
	 */
	@Override
	public FieldValidator validateKYCRequest(KYCContactRequest request) throws KYCException {

		return super.validateKYCRequestLastNamePostCode(request);

	}

}
