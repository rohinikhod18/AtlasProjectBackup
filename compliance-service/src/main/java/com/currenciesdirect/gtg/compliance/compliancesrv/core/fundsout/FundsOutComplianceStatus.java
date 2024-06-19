package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

/**
 * The Enum FundsOutComplianceStatus.
 */
public enum FundsOutComplianceStatus {
	
	/** The clear. */
	CLEAR(1,"CLEAR"),
	
	/** The reject. */
	REJECT(2,"REJECT"), 
	
	/** The seize. */
	SEIZE(3,"SEIZE"), 
	
	/** The hold. */
	HOLD(4,"HOLD"),
	
	/** The watched. */
	WATCHED(5,"WATCHED"),
	
	/** The reverse. */
	REVERSED(6,"REVERSED"),
	
	/** The clean. */
	CLEAN(7,"CLEAN"); //For Intuition only not updated in Database;
	
	/** The funds out status as integer. */
	private Integer fundsOutStatusAsInteger;
	
	/** The funds out status as string. */
	private String fundsOutStatusAsString;
	

	/**
	 * Instantiates a new funds out compliance status.
	 *
	 * @param fundsOutStatusAsInteger the funds out status as integer
	 * @param fundsOutStatusAsString the funds out status as string
	 */
	FundsOutComplianceStatus(Integer fundsOutStatusAsInteger,String fundsOutStatusAsString){
		this.fundsOutStatusAsInteger=fundsOutStatusAsInteger;
		this.fundsOutStatusAsString=fundsOutStatusAsString;
	}

	/**
	 * Gets the funds out status as integer.
	 *
	 * @return the funds out status as integer
	 */
	public Integer getFundsOutStatusAsInteger() {
		return fundsOutStatusAsInteger;
	}


	/**
	 * Gets the funds out status as string.
	 *
	 * @return the funds out status as string
	 */
	public String getFundsOutStatusAsString() {
		return fundsOutStatusAsString;
	}

	/**
	 * Gets the funds in compliance status as string.
	 *
	 * @param status the status
	 * @return the funds in compliance status as string
	 */
	public static String getFundsInComplianceStatusAsString(Integer status) {
		for (FundsOutComplianceStatus customerType : FundsOutComplianceStatus.values()) {
			if (customerType.getFundsOutStatusAsInteger().equals(status)) {
				return customerType.getFundsOutStatusAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the funds in compliance status as integer.
	 *
	 * @param status the status
	 * @return the funds in compliance status as integer
	 */
	public static Integer getFundsInComplianceStatusAsInteger(String status) {
		for (FundsOutComplianceStatus customerType : FundsOutComplianceStatus.values()) {
			if (customerType.getFundsOutStatusAsString().equals(status)) {
				return customerType.getFundsOutStatusAsInteger();
			}
		}
		return null;
	}
	
}
