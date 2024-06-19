/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: CategoryEnum.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.util;

/**
 * The Enum CategoryEnum.
 */
public enum CategoryEnum {

	WC_LOW_RISK_INDIVIDUAL("WC_LowRisk_Individuals"),

	WC_PEP_INDIVIDUAL("WC_PEP_Individuals"),

	WC_SANCTIONED_INDIVIDUAL("WC_Sanctioned_Individuals"),

	WC_LOW_RISK_ENTITIES("WC_LowRisk_Entities"),

	WC_PEP_ENTITIES("WC_PEP_Entities"),

	WC_SANCTIONED_ENTITIES("WC_Sanctioned_Entities");

	private String category;

	CategoryEnum(String category) {
		this.category = category;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

}
