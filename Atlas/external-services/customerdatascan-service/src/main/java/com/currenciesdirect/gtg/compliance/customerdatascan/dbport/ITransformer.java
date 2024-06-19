package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;


/**
 * The Interface ITransformer.
 *
 * @param <T> the generic type
 * @param <F> the generic type
 */
@FunctionalInterface
public interface ITransformer<T extends Object, F extends Object> {

	/**
	 * Transform.
	 *
	 * @param from            the object form to do transformation
	 * @return to the object to which transform
	 * @throws CustomerDataScanException the customer data scan exception
	 */
	public T transform(F from) throws CustomerDataScanException;

}
