package com.currenciesdirect.gtg.compliance.compliancesrv.core;

public enum NationalIdType {

	HK_ID_NUMBER("HK ID Number"),
	CURP_ID_NUMBER("CURP ID Number"),
	SOCIAL_INSURANCE_NUMBER("Social Insurance Number"),
    SSN("Social Security Number"),
    SA_NATIONAL_ID_NUMBER("National ID Number"),
    NIF("NIF");
	
	private String name;
	
	private NationalIdType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	
}
