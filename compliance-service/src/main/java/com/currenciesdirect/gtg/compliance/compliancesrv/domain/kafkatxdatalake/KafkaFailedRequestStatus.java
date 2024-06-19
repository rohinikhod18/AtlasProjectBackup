package com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake;

/**
 * @author prashant.verma
 */
public enum KafkaFailedRequestStatus {
	PENDING(0,"PENDING"),
	ERROR(3,"REJECT"), 
	COMPLETE(2,"COMPLETE");
	
	private Integer fundsInStatusAsInteger;
	private String fundsInStatusAsString;
	

	KafkaFailedRequestStatus(Integer fundsInStatusAsInteger,String fundsInStatusAsString){
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
		for (KafkaFailedRequestStatus customerType : KafkaFailedRequestStatus.values()) {
			if (customerType.getFundsInStatusAsInteger().equals(status)) {
				return customerType.getFundsInStatusAsString();
			}
		}
		return null;
	}

	public static Integer getFundsInComplianceStatusAsInteger(String status) {
		for (KafkaFailedRequestStatus customerType : KafkaFailedRequestStatus.values()) {
			if (customerType.getFundsInStatusAsString().equals(status)) {
				return customerType.getFundsInStatusAsInteger();
			}
		}
		return null;
	}
	
}
