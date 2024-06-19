package com.currenciesdirect.gtg.compliance.core;

/**
 * The Interface ITransform.
 *
 * @param <T>
 *            the generic type
 * @param <F>
 *            the generic type
 */
@FunctionalInterface
public interface ITransform<T, F> {

	/**
	 * Transform.
	 *
	 * @param from
	 *            the from
	 * @return the t
	 */
	T transform(F from);
}
