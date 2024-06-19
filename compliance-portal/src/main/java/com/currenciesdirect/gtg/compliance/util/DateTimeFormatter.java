package com.currenciesdirect.gtg.compliance.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;

/**
 * The Class DateTimeFormatter.
 * 
 * @author abhijeetg
 */
public class DateTimeFormatter {

	private static final Logger LOG = LoggerFactory.getLogger(DateTimeFormatter.class);

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public static final String DATE_FORMAT_FOR_FILTER = "MM/dd/yyyy";

	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String TIME_FORMAT_FOR_HOURS = "hh a";

	public static final String ERROR = "Formating Error :";

	public static final String RFC_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
	private static final String[] FORMATS = { "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
			"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ",
			"MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss",
			"yyyy-MM-dd", "yyyy-M-dd", "yyyy-d-MM", "yyyy-dd-MM", DATE_FORMAT };

	private static final String[] ONLY_DATE_FORMATS = {

			"yyyy-MM-dd", "yyyy-M-dd", "yyyy-d-MM", "yyyy-dd-MM", DATE_FORMAT, "dd-MM-yyyy", "dd-MM-yy", "dd/MM/yy",
			"MM-dd-yyyy", DATE_FORMAT_FOR_FILTER, "MM-dd-yy", "MM/dd/yy" };

	private DateTimeFormatter() {

	}

	/**
	 * Gets the date time in RFC 3359.
	 *
	 * @param date
	 *            the date
	 * @return the date time in RFC 3359
	 */
	public static String getDateTimeInRFC3339(String date) {
		String rfcdate = Constants.DASH_UI;
		if (date == null || date.isEmpty()) {
			return rfcdate;
		}
		for (String parse : FORMATS) {
			SimpleDateFormat sdf = new SimpleDateFormat(parse);
			try {
				java.util.Date parseDate = sdf.parse(date);
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
	 * Gets the date in RFC 3339.
	 *
	 * @param date the date
	 * @return the date in RFC 3339
	 */
	public static String getDateInRFC3339(String date) {
		String rfcdate = Constants.DASH_UI;
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

	/**
	 * Date format.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateFormat(String date) {
		if (null == date || date.isEmpty()) {
			return "";
		}
		date = date.trim();
		try {
			return formatDate(date);
		} catch (Exception e) {
			LOG.warn(ERROR, e);
			return date;
		}

	}

	private static String formatDate(String date) {
		java.sql.Date dateFormat = null;
		try {
			dateFormat = java.sql.Date.valueOf(date);
		} catch (Exception ex) {
			LOG.trace(ERROR, ex);
			try {
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				dateFormat = new java.sql.Date(formatter.parse(date).getTime());
			} catch (Exception e) {
				LOG.trace(ERROR, e);
				return "";
			}
		}
		SimpleDateFormat formattedDate = new SimpleDateFormat(DATE_FORMAT);
		return formattedDate.format(dateFormat);
	}

	/**
	 * Date time formatter.
	 *
	 * @param dateTime
	 *            the date time
	 * @return the string
	 */
	public static String dateTimeFormatter(Timestamp dateTime) {
		if (null == dateTime) {
			return "";
		}
		try {
			SimpleDateFormat formattedDateTime = new SimpleDateFormat(DATE_TIME_FORMAT);
			return formattedDateTime.format(dateTime);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return dateTime.toString();
		}
	}

	/**
	 * Removes the time from date.
	 *
	 * @param dateTime
	 *            the date timedateFormat
	 * @return the string
	 */
	public static String removeTimeFromDate(Timestamp dateTime) {
		if (null == dateTime) {
			return null;
		}
		try {
			SimpleDateFormat formattedDateTime = new SimpleDateFormat(DATE_FORMAT);
			return formattedDateTime.format(dateTime);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return dateTime.toString();
		}
	}

	/**
	 * Date time formatter.
	 *
	 * @param dateTime
	 *            the date time
	 * @return the string
	 */
	public static String dateTimeFormatter(String dateTime) {
		if (null == dateTime) {
			return null;
		}
		Timestamp dateTimeFormat = null;
		try {
			dateTimeFormat = Timestamp.valueOf(dateTime);
			
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			try {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.fffffffff");
				dateTimeFormat = new java.sql.Timestamp(formatter.parse(dateTime).getTime()); //NOSONAR
			} catch (Exception ex) {
				LOG.trace(ERROR, ex);
				return "";
			}
			return "";
		}
		SimpleDateFormat formattedDateTime = new SimpleDateFormat(DATE_TIME_FORMAT);
		return formattedDateTime.format(dateTimeFormat);
	}

	/**
	 * Removes the time from date.
	 *
	 * @param dateTime
	 *            the date time
	 * @return the string
	 */
	public static String removeTimeFromDate(String dateTime) {
		if (null == dateTime) {
			return null;
		}
		try {
			Timestamp dateTimeFormat = Timestamp.valueOf(dateTime);
			SimpleDateFormat formattedDateTime = new SimpleDateFormat(DATE_FORMAT);
			return formattedDateTime.format(dateTimeFormat);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return dateTime;
		}
	}

	/**
	 * Its format date i.e. 08:15:02
	 *
	 * @param time
	 *            the time
	 * @return the time
	 */
	public static String getTime(String time) {
		if (time == null || time.isEmpty()) {
			return "";
		}
		try {
			Timestamp dateTimeFormat = Timestamp.valueOf(time);
			SimpleDateFormat formattedDateTime = new SimpleDateFormat(TIME_FORMAT);
			return formattedDateTime.format(dateTimeFormat);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return time;
		}
	}

	/**
	 * Gets the hours with Time unit (i.e AM/PM).
	 *
	 * @param timeStamp
	 *            the time stamp
	 * @return the hours
	 */
	public static String getHours(String timeStamp) {
		if (timeStamp == null || timeStamp.isEmpty()) {
			return "";
		}
		try {
			Timestamp dateTimeFormat = Timestamp.valueOf(timeStamp);
			DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_FOR_HOURS);
			return dateFormat.format(dateTimeFormat);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return timeStamp;
		}
	}

	/**
	 * Gets the date after substracting days.
	 *
	 * @return the date after substracting days
	 */
	public static String getDateAfterSubstractingDays() {
		try {
			LocalDate date = LocalDate.now().minusDays(Long.parseLong(SearchCriteriaUtil.getDefaultQueueRecordSize()));
			return dateFormatForFilter(date.toString());
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return "";
		}
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public static String getCurrentDate() {
		try {
			LocalDate date = LocalDate.now();
			return dateFormatForFilter(date.toString());
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return "";
		}
	}

	/**
	 * Date format for filter.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateFormatForFilter(String date) {
		if (null == date || date.isEmpty()) {
			return "";
		}
		try {
			java.sql.Date dateFormat = java.sql.Date.valueOf(date);
			SimpleDateFormat formattedDate = new SimpleDateFormat(DATE_FORMAT_FOR_FILTER);
			return formattedDate.format(dateFormat);
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return date;
		}

	}

	/**
	 * Gets Date time stamp in SQL Format
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getTimestamp(String date) throws ParseException {
		java.sql.Timestamp sqlDate = null;
		if (null == date || date.isEmpty()) {
			return sqlDate;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date parsed = format.parse(date);
			return new java.sql.Timestamp(parsed.getTime());
		} catch (Exception e) {
			LOG.trace(ERROR, e);
			return sqlDate;
		}

	}

	/**
	 * Removes the millis from time stamp.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String removeMillisFromTimeStamp(String date) {
		if(null != date && date.contains(".")) {
			return date.substring(0, date.indexOf('.'));
		}
		return date;
	}
	
	/**
	 * Parses the date.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String parseDate(String date) {
		if (date.contains("/")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			Date dateO;
			try {
				dateO = sdf.parse(date);
			} catch (ParseException e) {
				return null;
			}
			sdf = new SimpleDateFormat("yyyy-mm-dd");
			return sdf.format(dateO);
		} else {
			return date;
		}
	}
}
