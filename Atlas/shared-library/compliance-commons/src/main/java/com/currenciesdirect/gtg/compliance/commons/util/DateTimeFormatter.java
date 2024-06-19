package com.currenciesdirect.gtg.compliance.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DateTimeFormatter.
 * @author abhijeetg
 */
public class DateTimeFormatter {

	private static final Logger LOG = LoggerFactory.getLogger(DateTimeFormatter.class);
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String DATE_FORMAT_FOR_FILTER = "MM/dd/yyyy";
	
	public static final String DATE_TIME_FORMAT = "dd/MM/YYYY HH:mm:ss";
	
	public static final String TIME_FORMAT = "HH:mm:ss";
	
	public static final String TIME_FORMAT_FOR_HOURS = "hh a";
	
	public static final String ERROR = "Formating Error :";
	
	public static final String JSON_DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	public static final String JSON_TIMEZONE = "UTC";
	
	public static final String RFC_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

	public static final String RFC_DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String[] FORMATS = { 
			JSON_DATEFORMAT,   "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss", 
            "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", 
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", 
            "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss", 
            "yyyy:MM:dd HH:mm:ss",
            RFC_DATE_FORMAT,
            "yyyy-M-dd",
            "yyyy-d-MM",
            "yyyy-dd-MM",
            DATE_FORMAT};
	private static final String[] ONLY_DATE_FORMATS = { 
			
			RFC_DATE_FORMAT,
            "yyyy/MM/dd",
            "yyyy-M-dd",
            "yyyy/M/dd",
            "yyyy-MM-d",
            "yyyy/MM/d",
            "dd-MM-yyyy",
            DATE_FORMAT,
            };
	
	private DateTimeFormatter(){
		
	}
	
	
	/**
	 * Gets the date time in RFC 3359.
	 *
	 * @param date the date
	 * @return the date time in RFC 3359
	 */
	public static String getDateTimeInRFC3339(String date) {
		String rfcdate = "";
		if (date == null || date.isEmpty()) {
			return rfcdate;
		}
		for (String parse : FORMATS) {
			SimpleDateFormat sdf = new SimpleDateFormat(parse);
			try {
				Date parseDate = sdf.parse(date);
				SimpleDateFormat ss = new SimpleDateFormat(RFC_TIMESTAMP_FORMAT);
				rfcdate = ss.format(parseDate);
				break;
			} catch (ParseException e) {
				LOG.trace(ERROR, e);
			}
		}
		return rfcdate;
	}
	
	/**
	 * Gets the date time in RFC 3359.
	 *
	 * @param date the date
	 * @return the date time in RFC 3359
	 */
	public static String getDateInRFC3339(String date) {

			String rfcdate = "";
			if (date == null || date.isEmpty()) {
				return rfcdate;
			}
			for (String parse : ONLY_DATE_FORMATS) {
				SimpleDateFormat sdf = new SimpleDateFormat(parse);
				try {
					java.util.Date parseDate = sdf.parse(date);
					SimpleDateFormat ss = new SimpleDateFormat(RFC_DATE_FORMAT);
					rfcdate = ss.format(parseDate);
					break;
				} catch (ParseException e) {
					LOG.trace(ERROR, e);
				}
			}
			return rfcdate;
	}
	/**
	 * Date format.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String getUKDateFormat(String date) {
		String rfcdate = "";
		if (date == null || date.isEmpty()) {
			return rfcdate;
		}
		for (String parse : ONLY_DATE_FORMATS) {
			SimpleDateFormat sdf = new SimpleDateFormat(parse);
			try {
				java.util.Date parseDate = sdf.parse(date);
				SimpleDateFormat ss = new SimpleDateFormat(DATE_FORMAT);
				rfcdate = ss.format(parseDate);
				break;
			} catch (ParseException e) {
				LOG.trace(ERROR, e);
			}
		}
		return rfcdate;

	}

	public static Timestamp convertStringToTimestamp(String strDate) {

		for (String parse : FORMATS) {
			SimpleDateFormat sdf = new SimpleDateFormat(parse);
			try {
				Date date = sdf.parse(strDate);
				return new Timestamp(date.getTime());
			}  catch (ParseException e) {
				LOG.trace(ERROR, e);
			}
		}
		return new Timestamp(0);
	}
	
}
