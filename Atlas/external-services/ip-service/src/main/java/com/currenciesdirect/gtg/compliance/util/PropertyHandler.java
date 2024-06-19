/*
 * Copyright Currencies Direct Ltd 2013-2015. All rights reserved worldwide.
 * Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.
 */

package com.currenciesdirect.gtg.compliance.util;

/**
 * The Class PropertyHandler.
 * 
 * @author Manish
 */
public class PropertyHandler {

	/**
	 * Gets the property.
	 * 
	 * @param propertyName
	 *            the property name
	 * @return the property
	 */
	public static String getProperty(String propertyName) {
		try {
			return System.getProperty(propertyName).trim();
		} catch (Exception e) {
			System.out.println("	Error For [key:" + propertyName + "]	Message:"
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * Gets the property.
	 * 
	 * @param propertyName
	 *            the property name
	 * @return the property
	 */
	public static String getProperty(String propertyName, String orgCode) {
		try {
			if (orgCode != null && orgCode.trim().length() > 0) {
				String val = System.getProperty(
						normalizeForProperty(orgCode) + "." + propertyName)
						.trim();
				if (val != null) {
					return val;
				}
			}
		} catch (Exception e) {
		}
		return getProperty(propertyName);
	}

	private static String normalizeForProperty(String str) {
		return (str == null ? str : str.trim().replaceAll("\\s", "")
				.toLowerCase());
	}
}