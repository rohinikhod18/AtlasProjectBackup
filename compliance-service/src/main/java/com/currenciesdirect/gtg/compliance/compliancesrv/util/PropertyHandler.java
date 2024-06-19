/*
 * Copyright Currencies Direct Ltd 2013-2015. All rights reserved worldwide.
 * Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.
 */

package com.currenciesdirect.gtg.compliance.compliancesrv.util;

/**
 * The Class PropertyHandler.
 * 
 * @author laxmib
 *
 */
public class PropertyHandler {
	
	private PropertyHandler() {

	}
	
	/**
	 * Gets the compliance expiry years
	 * 
	 * @return complianceExpiryYears
	 */
	public static Integer getComplianceExpiryYears() {
		String size = System.getProperty("complianceExpiryYears");
		if (size == null) {
			return Constants.DEFAULT_COMPLIANCE_EXPIRY_YEARS;
		} else {
			try {
				return Integer.parseInt(size);
			} catch (NumberFormatException e) {
				return Constants.DEFAULT_COMPLIANCE_EXPIRY_YEARS;
			}

		}
	}

}
