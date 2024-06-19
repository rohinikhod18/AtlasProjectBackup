package com.currenciesdirect.gtg.compliance.blacklist.core;

import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * The Interface IValidator.
 *
 * @param <T>
 *            the generic type
 */
@FunctionalInterface
public interface IValidator<T extends Object> {

	/**
	 * Validate object.
	 *
	 * @param object
	 *            the object
	 * @return true, if successful
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public boolean validate(T object) throws BlacklistException;
}
