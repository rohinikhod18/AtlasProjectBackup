package com.currenciesdirect.gtg.compliance.fraugster.core.validator;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Interface IValidator.
 * @author abhijitg
 */
public interface IValidator {
	
	
	/**
	 * Validate fraugster request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean validateFraugsterSignupRequest(FraugsterSignupContactRequest request) throws FraugsterException;
	
	/**
	 * Validate fraugster update request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean validateFraugsterOnUpdateRequest(FraugsterOnUpdateContactRequest request)throws FraugsterException;
	
	
	/**
	 * Validate fraugster payment sout request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean validateFraugsterPaymentsOutRequest(FraugsterPaymentsOutContactRequest request) throws FraugsterException;
	
	/**
	 * Validate fraugster paymentsin request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean validateFraugsterPaymentsInRequest(FraugsterPaymentsInContactRequest request) throws FraugsterException;

}
