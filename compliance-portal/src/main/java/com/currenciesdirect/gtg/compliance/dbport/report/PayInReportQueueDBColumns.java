package com.currenciesdirect.gtg.compliance.dbport.report;

public enum PayInReportQueueDBColumns {

	COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), CLIENTNAME("clientname"), // contact fields
	 
	TRADEACCOUNTNUM("client"), REGISTEREDON("RegisteredOn"), ORGANIZATION("organization"), LEGALENTITY("LegalEntity"), // account fields
	
	TYPE("Type"),   SOURCE("Source"), TRANSACTIONVALUE("TransactionValue"), // account attribute fields
	
	KYCSTATUS("KYCStatus"), BLACKLISTSTATUS("BlacklistStatus"), FRAUGSTERSTATUS("FraugsterStatus"), CUSTOMCHECKSTATUS("CustomCheckStatus"), 
	SANCTIONSTATUS("SanctionStatus"), // eventServiceLog fields
	
	USER_RESOURCE_LOCK_ID("ResourceId"), LOCKED_BY("LockedBy"),// userresourcelock fields
	
	PAYMENTINID("paymentinid"),ACCOUNTID("AccountId"),CONTACTID("ContactId"),TRANSACTIONID("transactions"),
	
	OVERALLSTATUS("OverAllStatus"), //PaymentOut fields
	
	COUNTRY("Country"), //Country
	
	COUNTRY_DISPLAY_NAME("CountryDisplayName"),RISKSTATUS("riskStatus"),
	
	DATE("Date"),  SELLCURRENCY("transactioncurrency"),AMOUNT("amount"), TRANSACTIONDATE("payinattri.transactiondate"),
	METHOD("paymentmethod"), //PaymentOutAttribute fields
	
	WATCHLISTSTATUS("watchliststatus"), WATCHLISTREASON("WatchListReason"), OWNER("username"), PAYMENTSTATUS("compliancestatusenum"), //ContactWatchList
	
	TOTALROWS("TotalRows"), ALIAS_PAYMENTINID("payin.id"),  ACSFID_SEARCH_KEYWORD("ACSFID"),
	
	METHOD_SEARCH_KEYWORD("method"), AMOUNT_SEARCH_KEYWORD("amount"), 
	
	CONTACT_ATTRIBUTE_SEARCH_KEYWORD("ContactAttributes"), CLIENT_SEARCH_KEYWORD("client"), TRANSACTIONS_SEARCH_KEYWORD("Transactions"),
	
	FILTER_BY_WATCHLISTREASON("WatchListReason"), FILTER_BY_OWNER("LockedBy"), FILTER_BY_PAYMENTSTATUS("OverAllStatus"),  SEARCH_BY_CLIENT_NAME("ClientName");

	private String name;

	private PayInReportQueueDBColumns(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
