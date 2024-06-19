package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Enum ContactComplianceStatus.
 */
public enum ContactComplianceStatus {
	
	/** The active. */
	ACTIVE(1,"ACTIVE"),
	
	/** The not performed. */
	NOT_PERFORMED(2,"NOT_PERFORMED"), 
	
	/** The in queue. */
	IN_QUEUE(3,"IN_QUEUE"), 
	
	/** The inactive. */
	INACTIVE(4,"INACTIVE"), 
	
	/** The rejected. */
	REJECTED(5,"REJECTED"),
	
	/** The watch list. */
	WATCH_LIST(6,"WATCH_LIST");
	
	/** The compliance status as integer. */
	private Integer complianceStatusAsInteger;
	
	/** The cust type as string. */
	private String complianceStatusAsString;

	/**
	 * Instantiates a new customer type enum.
	 *
	 * @param complianceStatusAsInteger the compliance status as integer
	 * @param complianceStatusAsString the compliance status as string
	 */
	ContactComplianceStatus(Integer complianceStatusAsInteger, String complianceStatusAsString) {
		this.complianceStatusAsInteger = complianceStatusAsInteger;
		this.complianceStatusAsString = complianceStatusAsString;
	}

	/**
	 * Gets the compliance status as integer.
	 *
	 * @return the compliance status as integer
	 */
	public Integer getComplianceStatusAsInteger() {
		return complianceStatusAsInteger;
	}

	/**
	 * Gets the compliance status as string.
	 *
	 * @return the compliance status as string
	 */
	public String getComplianceStatusAsString() {
		return complianceStatusAsString;
	}
	
	/**
	 * Gets the compliance as string.
	 *
	 * @param status the status
	 * @return the compliance as string
	 */
	public static String getComplianceAsString(Integer status) {
		for (ContactComplianceStatus customerType : ContactComplianceStatus.values()) {
			if (customerType.getComplianceStatusAsInteger().equals(status)) {
				return customerType.getComplianceStatusAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the custumer type as integer.
	 *
	 * @param status the status
	 * @return the custumer type as integer
	 */
	public static Integer getComplianceAsInteger(String status) {
		for (ContactComplianceStatus customerType : ContactComplianceStatus.values()) {
			if (customerType.getComplianceStatusAsString().equals(status)) {
				return customerType.getComplianceStatusAsInteger();
			}
		}
		return null;
	}

}
