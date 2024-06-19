/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;


/**
 * The Interface Ivalidator.
 *
 * @author manish
 */
public interface Ivalidator {
	
	/**
	 * Validate KYC request.
	 *
	 * @param request the request
	 * @return the field validator
	 * @throws KYCException the KYC exception
	 */
	public FieldValidator validateKYCRequest(KYCContactRequest request) throws KYCException;

}
