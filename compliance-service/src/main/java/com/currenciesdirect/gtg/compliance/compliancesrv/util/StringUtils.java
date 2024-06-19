package com.currenciesdirect.gtg.compliance.compliancesrv.util;

public class StringUtils {
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
	 * Checks if is null or trim empty.
	 *
	 * @param str the str
	 * @return true, if is null or trim empty
	 */
	public static boolean isNullOrTrimEmpty(String str) {
		boolean result = true;
		if (str != null && !str.trim().isEmpty())
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
