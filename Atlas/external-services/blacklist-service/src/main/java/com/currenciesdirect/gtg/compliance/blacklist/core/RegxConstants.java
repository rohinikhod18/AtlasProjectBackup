package com.currenciesdirect.gtg.compliance.blacklist.core;

import java.util.regex.Pattern;

/**
 * The Enum RegxConstants.
 * 
 * @author Rajesh
 */
public enum RegxConstants {

	/** The ip address. */
	IP_ADDRESS("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"),

	/** The email. */
	EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),

	/** The name. */
	NAME("^[a-zA-Z ]{0,100}$");

	/** The pattern. */
	String pattern;

	/**
	 * Instantiates a new regx constants.
	 *
	 * @param pattern
	 *            the pattern
	 */
	RegxConstants(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	String getPattern() {
		return this.pattern;
	}

	/**
	 * Validate.
	 *
	 * @param pattern
	 *            the pattern
	 * @param value
	 *            the value
	 * @return the boolean
	 */
	public static Boolean validate(RegxConstants pattern, String value) {
		return Pattern.matches(pattern.getPattern(), value);
	}
}