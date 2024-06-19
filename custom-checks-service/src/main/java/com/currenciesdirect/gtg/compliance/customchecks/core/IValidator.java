package com.currenciesdirect.gtg.compliance.customchecks.core;

import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Interface IValidator.
 * 
 * @param <T>
 *            the generic type
 * 
 * @author Rajesh
 */
@FunctionalInterface
public interface IValidator<T> {

	/**
	 * Validate.
	 * 
	 * @param request
	 *            the request
	 * @throws CustomChecksException
	 *             the customer data scan exception
	 */
	public void validate(T request) throws CustomChecksException;

}
