/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

/**
 * @author manish
 *
 */
public enum FundsInComplianceStatus {
	CLEAR(1,"CLEAR"),
	REJECT(2,"REJECT"), 
	SEIZE(3,"SEIZE"), 
	HOLD(4,"HOLD"),
	WATCHED(5,"WATCHED"),
	REVERSED(6,"REVERSED"),
	CLEAN(7,"CLEAN"); //For Intuition only not updated in Database
	
	private Integer fundsInStatusAsInteger;
	private String fundsInStatusAsString;
	

	FundsInComplianceStatus(Integer fundsInStatusAsInteger,String fundsInStatusAsString){
		this.fundsInStatusAsInteger=fundsInStatusAsInteger;
		this.fundsInStatusAsString=fundsInStatusAsString;
	}

	public Integer getFundsInStatusAsInteger() {
		return fundsInStatusAsInteger;
	}

	public String getFundsInStatusAsString() {
		return fundsInStatusAsString;
	}

	public static String getFundsInComplianceStatusAsString(Integer status) {
		for (FundsInComplianceStatus customerType : FundsInComplianceStatus.values()) {
			if (customerType.getFundsInStatusAsInteger().equals(status)) {
				return customerType.getFundsInStatusAsString();
			}
		}
		return null;
	}

	public static Integer getFundsInComplianceStatusAsInteger(String status) {
		for (FundsInComplianceStatus customerType : FundsInComplianceStatus.values()) {
			if (customerType.getFundsInStatusAsString().equals(status)) {
				return customerType.getFundsInStatusAsInteger();
			}
		}
		return null;
	}
	
}
