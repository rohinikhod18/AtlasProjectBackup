package com.currenciesdirect.gtg.compliance.customchecks.util;

/**
 * The Class StringUtils.
 */
public class StringUtils {
	
	/**
	 * Instantiates a new string utils.
	 */
	private StringUtils(){}
	
	/**
	 * Checks if is null or empty.
	 *
	 * @param str the str
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;
		
		return result;
	}
	
	/**
	 * Left pad with zero.
	 *
	 * @param size the size
	 * @param id the id
	 * @return the string
	 */
	public static String leftPadWithZero(Integer size, Integer id){
		String format = "%0"+size+"d";
		return String.format(format, id);
	}
	
	/**
	 * Left pad with zero.
	 *
	 * @param size the size
	 * @param chars the chars
	 * @return the string
	 */
	public static String leftPadWithZero(Integer size, String chars){
		String format = "%0$"+size+"s";
		return String.format(format, chars).replace(' ', '0');
	}
	
}
