package com.currenciesdirect.gtg.compliance.util;

import java.text.DecimalFormat;

/**
 * The Class DecimalFormatter.
 */
public class DecimalFormatter {

	/** The Constant FOUR_DIGIT_FORMAT. */
	private static final String FOUR_DIGIT_FORMAT = ".####";
	
	/** The Constant ONE_DIGIT_FORMAT. */
	private static final String ONE_DIGIT_FORMAT = ".#";

	/**
	 * Convert to four digit.
	 *
	 * @return the string
	 */
	
	protected DecimalFormatter()
	{
		
	}
	
	/**
	 * Convert to four digit.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String convertToFourDigit(Double value) {
		if (value == null) {
			return null;
		}
		DecimalFormat format = new DecimalFormat(FOUR_DIGIT_FORMAT);
		return format.format(value);
	}

	/**
	 * Convert to four digit.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String convertToFourDigit(String value) {
		if (value == null) {
			return null;
		}
		if(value.indexOf('.') <= 0){
			return value;
		}
		DecimalFormat format = new DecimalFormat(FOUR_DIGIT_FORMAT);
		return format.format(Double.valueOf(value));
	}

	/**
	 * Convert to four digit.
	 *
	 * @param value
	 *            the value
	 * @return the double
	 */
	public static Double convertToFourDigitAsDouble(Double value) {
		if (value == null) {
			return null;
		}
		DecimalFormat format = new DecimalFormat(FOUR_DIGIT_FORMAT);
		return Double.valueOf(format.format(value));
	}

	/**
	 * Convert to four digit.
	 *
	 * @param value
	 *            the value
	 * @return the double
	 */
	public static Double convertToFourDigitAsDouble(String value) {
		if (value == null) {
			return null;
		}
		if(value.indexOf('.') <= 0){
			return Double.valueOf(value);
		}
		DecimalFormat format = new DecimalFormat(FOUR_DIGIT_FORMAT);
		return Double.valueOf(format.format(value));
	}
	
	/**
	 * Convert to four digit.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String convertToOneDigit(String value) {
		if (value == null) {
			return null;
		}
		if(value.indexOf('.') <= 0){
			return value;
		}
		DecimalFormat format = new DecimalFormat(ONE_DIGIT_FORMAT);
		return format.format(Double.valueOf(value));
	}

}
