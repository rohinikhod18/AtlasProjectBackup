package com.currenciesdirect.gtg.compliance.commons.enums;

public enum CustomerTypeEnum {

	/** The cfx. */
	CFX(1, "CFX"),

	/** The pfx. */
	PFX(2, "PFX"),

	/** The cfx etailer. */
	CFX_ETAILER(3, "CFX (etailer)");

	/** The cust type as integer. */
	private Integer custTypeAsInteger;

	/** The cust type as string. */
	private String custTypeAsString;

	/**
	 * Instantiates a new customer type enum.
	 *
	 * @param databaseStatus
	 *            the database status
	 * @param uiStatus
	 *            the ui status
	 */
	CustomerTypeEnum(Integer custTypeAsInteger, String custTypeAsString) {
		this.custTypeAsInteger = custTypeAsInteger;
		this.custTypeAsString = custTypeAsString;
	}

	/**
	 * Gets the cust type as integer.
	 *
	 * @return the cust type as integer
	 */
	public Integer getCustTypeAsInteger() {
		return custTypeAsInteger;
	}

	/**
	 * Gets the cust type as string.
	 *
	 * @return the cust type as string
	 */
	public String getCustTypeAsString() {
		return custTypeAsString;
	}

	/**
	 * Gets the custumer type as string.
	 *
	 * @param type
	 *            the type
	 * @return the custumer type as string
	 */
	public static String getCustumerTypeAsString(Integer type) {
		for (CustomerTypeEnum customerType : CustomerTypeEnum.values()) {
			if (customerType.getCustTypeAsInteger().equals(type)) {
				return customerType.getCustTypeAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the custumer type as integer.
	 *
	 * @param type
	 *            the type
	 * @return the custumer type as integer
	 */
	public static Integer getCustumerTypeAsInteger(String type) {
		for (CustomerTypeEnum customerType : CustomerTypeEnum.values()) {
			if (customerType.getCustTypeAsString().equals(type)) {
				return customerType.getCustTypeAsInteger();
			}
		}
		return null;
	}

}
