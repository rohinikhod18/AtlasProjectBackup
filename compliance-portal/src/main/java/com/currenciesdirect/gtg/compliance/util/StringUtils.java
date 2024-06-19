package com.currenciesdirect.gtg.compliance.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;

public class StringUtils {
	
	private static Logger log = LoggerFactory.getLogger(StringUtils.class);
	
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
	
	/**
	 * Checks if is null or trim empty set NA.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String isNullOrTrimEmptySetNA(String str) {
		String dash;
		if (str == null || str.trim().isEmpty()){
			dash = Constants.NOT_AVAILABLE_UI;
			return dash;
		}else {
		     return str;
		}
	}
	
	/**
	 * Checks if is null or trim empty set NA.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String isNullOrTrimEmptySetSpace(String str) {
		String dash;
		if (str == null || str.trim().isEmpty()){
			dash = Constants.BLANK;
			return dash;
		}else {
		     return str;
		}
	}
	
	/***
	 * This function returns number format as per Locale define.(i.e ENGLISH)
	 * given number is formated in comma separated form after every 3 digit
	 * @param value
	 * @return
	 * @author abhijeetg
	 */
	public static String getNumberFormat(String value) {
		try {
			if (!isNullOrEmpty(value)) {
				Double number = Double.valueOf(value);
				NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
				DecimalFormat decimalFormat = (DecimalFormat) format;
				decimalFormat.setGroupingUsed(true);
				decimalFormat.setGroupingSize(3);
				return decimalFormat.format(number);
			}
		} catch (Exception e) {
			log.debug("Error while formating number" , e);
			return value;
		}
		return value;
	}

	/**
	 * Removes the comma at end.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String removeCommaAtEnd(String input){
		String result="";
		result = input.trim();
		result = result.substring(0, result.length()-1);
		
		return result;
	}
	
}
