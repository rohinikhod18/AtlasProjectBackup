package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;

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
	 * @throws CustomerDataScanException
	 *             the customer data scan exception
	 */
	public void validate(T request) throws CustomerDataScanException;

}
