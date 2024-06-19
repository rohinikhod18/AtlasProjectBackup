package com.currenciesdirect.gtg.compliance.customerdatascan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ValidatorHelper.
 */
public class ValidatorHelper {

	static final Logger LOG = LoggerFactory.getLogger(ValidatorHelper.class);
	private static volatile ValidatorHelper validatDocument = null;

	private ValidatorHelper() {
	}

	public static ValidatorHelper getInstance() {
		if (validatDocument == null) {
			synchronized (ValidatorHelper.class) {
				if (validatDocument == null) {
					validatDocument = new ValidatorHelper();
				}
			}
		}
		return validatDocument;
	}

	/**
	 * Validate.
	 *
	 * @param key the key
	 * @param field the field
	 * @param fieldName the field name
	 */
	public void validate(Regex key, String field, String fieldName) {

		boolean matches;
		String dataType;

		if (field == null) {
			throw new IllegalArgumentException(fieldName + " is not valid ");
		}

			matches = field.matches(key.getRegularEx());
			dataType = key.getRegexFor();

		if (!matches) {
			throw new IllegalArgumentException(fieldName + " should contain "
					+ dataType);
		}
	}
}

enum Regex {

	ALPHA("[a-zA-Z]*", "Aplhabet");

	private String regularEx;
	private String regexFor;

	Regex(String regularEx, String regexFor) {
		this.regularEx = regularEx;
		this.regexFor = regexFor;
	}

	public String getRegularEx() {
		return regularEx;
	}

	public String getRegexFor() {
		return regexFor;
	}

}
