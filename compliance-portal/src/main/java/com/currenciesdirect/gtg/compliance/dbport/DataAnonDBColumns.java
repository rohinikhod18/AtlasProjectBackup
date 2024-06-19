package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum DataAnonymisationDBColumns.
 */
public enum DataAnonDBColumns {

	CONTACTID("ContactId"), COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), // contact
																								// fields
	ACCOUNTID("AccountId"), TRADEACCOUNTNUM("TradeAccountNumber"), REGISTEREDON("UpdatedOn"), ORGANIZATION(
			"Organization"), ORG_NAME("org.NAME"), LEGALENTITY("LegalEntity"), LEGAL_ENTITY("le.code"),
	DATAANONSTATUS("DataAnonStatus"), REQUESTDATE("UpdatedOn"),
	// account fields
	TYPE("Type"), TYPE_FILTER("A.Type"), SOURCE("vSource"), // account attribute fields
	
	SERVICETYPE("ServiceType"),STATUS("status"), // VIEW MORE DETAILS
	
	SOURCEQUEUE("source"), CODE("Code"), CURRENCY("Code"),REASON("reason"),OWNER("FinalBy"),
	REGOWNER("InitialBy") ,REGSTATUS("ComplianceStatus") ,TOTALROWS("TotalRows"),
	ISLOCKRELEASEDON("LockReleasedOn"),
	
	//dataanonactivitylog fields
	INITIAL_APPROVALBY("InitialApprovalBy"), INITIAL_APPROVALDATE("InitialApprovalDate"),INITIAL_APPROVALCOMMENT("InitialApprovalComment"),
	
	FINAL_APPROVALBY("FinalApprovalBy"), FINAL_APPROVALDATE("FinalApprovalDate"),FINAL_APPROVALCOMMENT("FinalApprovalComment"),
	REJECTEDBY("rejectedby"), REJECTEDDATE("rejectedDate"),REJECTEDCOMMENT("rejectedComment"),
	
	//dataanonactivitylogHistory fields
	ID("Id"),ACTIVITY("Activity"),COMMENT("Comment"),ACTIVITYBY("ActivityBy"),ACTIVITYDATE("ActivityDate");
	
	private String name;

	private DataAnonDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}	
	

}
