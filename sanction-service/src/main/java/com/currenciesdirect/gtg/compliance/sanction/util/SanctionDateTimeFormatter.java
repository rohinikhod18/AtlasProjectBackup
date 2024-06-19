/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: DateTimeFormatter.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DateTimeFormatter.
 * @author abhijeetg
 */
public class SanctionDateTimeFormatter {
	private static final Logger LOGEER = LoggerFactory.getLogger(SanctionDateTimeFormatter.class);
	
	/** The Constant DOB_FORMAT_FOR_PROVIDER. */
	private static final String DOB_FORMAT_FOR_PROVIDER = "yyyyMMdd";
	
	private static final String ERROR = "Formating Error :";
	
	
	/** The Constant ONLY_DATE_FORMATS. */
	private static final String[] ONLY_DATE_FORMATS = { 
			
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "yyyy-M-dd",
            "yyyy/M/dd",
            "yyyy-MM-d",
            "yyyy/MM/d",
            "dd-MM-yyyy",
			"dd/MM/yyyy",
            };
	
	private SanctionDateTimeFormatter(){
		
	}
	
	/**
	 * Gets the DOB for provider.
	 *
	 * @param date the date
	 * @return the DOB for provider
	 */
	public static String getDOBForProvider(String date) {
		String dob = "";
		if (date == null || date.trim().isEmpty()) {
			return dob;
		}
		for (String parse : ONLY_DATE_FORMATS) {
			SimpleDateFormat sdf = new SimpleDateFormat(parse);
			try {
				java.util.Date parseDate = sdf.parse(date);
				SimpleDateFormat ss = new SimpleDateFormat(DOB_FORMAT_FOR_PROVIDER);
				dob = ss.format(parseDate);
				break;
			} catch (ParseException e) {
				LOGEER.trace(ERROR, e);
			}
		}
		if(dob == null || dob.isEmpty()){
			dob= date;
		}
		return dob;

	}

}
