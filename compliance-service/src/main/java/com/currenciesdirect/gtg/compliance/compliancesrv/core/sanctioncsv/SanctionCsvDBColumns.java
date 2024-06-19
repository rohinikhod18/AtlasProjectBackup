package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

public enum SanctionCsvDBColumns {
	CLIENT_ID("Contact_SanctionID"), FULLNAME("FullName"),COMMENT_ID("TradeAccountNumber"),COUNTRY("Citizenship"),DOB("dob"),
	DEBTOR_CLIENT_ID("SanctionID"),CONTACT_ID("ContactID");
	
	private String name;

	private SanctionCsvDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
