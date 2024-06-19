package com.currenciesdirect.gtg.compliance.dbport;

public enum PayInQueDBColumns {

	COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), CLIENTNAME("ClientName"), // contact fields
	 
	TRADEACCOUNTNUM("Client"), REGISTEREDON("RegisteredOn"), ORGANIZATION("Organization"), LEGALENTITY("LegalEntity"), // account fields
	
	TYPE("Type"),   SOURCE("Source"), TRANSACTIONVALUE("TransactionValue"), // account attribute fields
	
	KYCSTATUS("KYCStatus"), BLACKLISTSTATUS("BlacklistStatus"), FRAUGSTERSTATUS("FraugsterStatus"), CUSTOMCHECKSTATUS("CustomCheckStatus"), 
	SANCTIONSTATUS("SanctionStatus"), // eventServiceLog fields
	
	BLACKLISTSTATUSFILTER("payin.BlacklistStatus"), FRAUGSTERSTATUSFILTER("payin.FraugsterStatus"),	SANCTIONSTATUSFILTER("payin.SanctionStatus"),
	
	INTUITIONSTATUS("IntuitionCheckStatus"), //AT-4607
	
	USER_RESOURCE_LOCK_ID("ResourceId"), LOCKED_BY("LockedBy"),// userresourcelock fields
	
	PAYMENTINID("PaymentInID"),ACCOUNTID("AccountId"),CONTACTID("ContactId"),TRANSACTIONID("Transactions"),
	
	OVERALLSTATUS("OverAllStatus"), //PaymentOut fields
	
	COUNTRY("Country"), //Country
	
	COUNTRY_DISPLAY_NAME("CountryDisplayName"),RISKSTATUS("riskStatus"),INITIALSTATUS("initialStatus"),
	
	DATE("Date"),  SELLCURRENCY("transactioncurrency"),AMOUNT("Amount"), TRANSACTIONDATE("payinattri.transactiondate"),ALIAS_PAYMENTINID("payin.id"),
	METHOD("paymentmethod"), //PaymentInAttribute fields
	
	WATCHLISTSTATUS("WatchListStatus"), WATCHLISTREASON("WatchListReason"), OWNER("username"), PAYMENTSTATUS("compliancestatusenum"), //ContactWatchList
	
	TOTALROWS("TotalRows"), FLITER_BY_ORGANIZATION("org.NAME"),  FLITER_BY_SELLCURRENCY("payinattri.transactioncurrency"), FLITER_BY_COUNTRY(" ctr.DisplayName"),
	
	FILTER_BY_BLACKLISTSTATUS("payin.BlacklistStatus"),FILTER_BY_FRAUGSTERSTATUS("payin.FraugsterStatus"),FILTER_BY_SANCTIONSTATUS("payin.SanctionStatus"),
	
	FILTER_BY_WATCHLISTSTATUS("WatchListStatus"),FILTER_BY_INTUITIONCHECK("payin.IntuitionCheckStatus"),FILTER_BY_TRAANSACTIONMONITORING_REQUEST("acc.AccountTMFlag")/*Added for AT-4655*/,FILTER_BY_CUSTOMCHECKSTATUS("CustomCheckStatus");//for TM filter
	private String name;

	private PayInQueDBColumns(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
