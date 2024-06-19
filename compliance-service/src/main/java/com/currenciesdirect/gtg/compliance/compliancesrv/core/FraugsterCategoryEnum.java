package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Enum FraugsterCategoryEnum.
 */
public enum FraugsterCategoryEnum {
	
	/** The individual. */
	INDIVIDUAL("Individual"),
	
	/** The organization. */
	ORGANIZATION("Organization");
	
	
	/** The value. */
	private String value;

	/**
	 * Instantiates a new sanction category enum.
	 *
	 * @param value the value
	 */
	FraugsterCategoryEnum(String value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
