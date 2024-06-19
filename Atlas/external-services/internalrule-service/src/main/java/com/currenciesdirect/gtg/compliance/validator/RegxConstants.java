package com.currenciesdirect.gtg.compliance.validator;

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
	NAME("^[a-zA-Z0-9-ÀàáẦầĀ̀ā̀ẰằÆ̀æ̀ÈèỀềḔḕÈ̩è̩ə̀ɚ̀H̀h̀ÌìĪ̀ī̀i̇̀K̀k̀M̀m̀ǸǹÒòỜờỒồṐṑÒ̩ò̩ɔ̀R̀r̀S̀s̀T̀t̀ÙùŪ̀ū̀ǛǜỪừV̀v̀ʌ̀ẀẁX̀x̀ỲỳȲ̀ȳ̀Z̀z̀ᾺὰῈὲῊὴῚὶῒῸὸῪὺῢῺὼЀѐЍѝéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛÕäëïöüÿÄËÏÖÜŸçÇßØøÅåÆæœ._\\& ]{0,100}$"),
	
	/**The phone number*/
	PHONE_NUMBER("^[\\d() +-]+$");
	

	/** The pattern. */
	private String pattern;

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
