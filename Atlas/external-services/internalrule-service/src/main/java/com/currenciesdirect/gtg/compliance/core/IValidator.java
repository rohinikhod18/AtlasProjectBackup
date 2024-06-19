package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

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
	public Boolean validate(T object) throws InternalRuleException;
}
