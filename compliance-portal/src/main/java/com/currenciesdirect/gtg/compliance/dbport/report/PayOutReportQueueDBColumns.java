package com.currenciesdirect.gtg.compliance.dbport.report;

/**
 * The Enum PayOutQueDBColumns.
 */
public enum PayOutReportQueueDBColumns {

	 COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), CLIENTNAME("ClientName"), // contact fields
																								 
	TRADEACCOUNTNUM("Client"), REGISTEREDON("RegisteredOn"), ORGANIZATION("Organization"), LEGALENTITY("LegalEntity"),ISO_COUNTRY("IsoCountry"),VALUE_DATE("valueDate"), MATURITY_DATE("maturityDate"), ACSFID("ACSFID"), // account fields
	
	TYPE("Type"), SELLCURRENCY("SellCurrency"),  SOURCE("Source"), TRANSACTIONVALUE("TransactionValue"), // account attribute fields
	
	KYCSTATUS("KYCStatus"), BLACKLISTSTATUS("BlacklistStatus"), FRAUGSTERSTATUS("FraugsterStatus"), CUSTOMCHECKSTATUS("CustomCheckStatus"),
	
	SANCTIONSTATUS("SanctionStatus"),SANCTIONCONSTATUS("SanctionConStatus"), INTUITIONSTATUS("IntuitionCheckStatus"), //AT-4607
	
	SANCTIONBANKSTATUS("SanctionBankStatus"),SANCTIONBENESTATUS("SanctionBeneStatus"), // eventServiceLog fields
	
	USER_RESOURCE_LOCK_ID("ResourceId"), LOCKED_BY("LockedBy"),// userresourcelock fields
	
	PAYMENTOUTID("PaymentOutID"),ACCOUNTID("AccountId"),CONTACTID("ContactId"),TRANSACTIONID("Transactions"),OVERALLSTATUS("OverAllStatus"), //PaymentOut fields
	
	COUNTRY("Country"), //Country
	
	DATE("Date"),  BUYCURRENCY("Buy"),AMOUNT("Amount"),ATTRIBUTE("Attributes"),BENEFICIARYAMMOUNT("BeneficiaryAmount"),TRANSACTIONDATE("poa.transactiondate"),	ALIAS_PAYMENTOUTID("payout.id"),
	BENEFICIARY("Beneficiary"), //PaymentOutAttribute fields
	
	WATCHLISTSTATUS("WatchListStatus"),TOTAL_ROWS("TotalRows"),WATCHLISTREASON("WatchListReason"),REASONOFTRANSFER("ReasonOfTransfer"),
	
	FILTER_BY_WATCHLISTREASON("WatchListReason"), FILTER_BY_LOCKED_BY("LockedBy"), FILTER_BY_OVERALLSTATUS("OverAllStatus"),
	
	SEARCH_BY_ACSFID("ACSFID"), SEARCH_BY_ATTRIBUTES("Attributes"), SEARCH_BY_REASONOFTRANSFER("ReasonOfTransfer"),
	
	 SEARCH_BY_BENEFICIARY("Beneficiary"), SEARCH_BY_CLIENT("Client"), 
	 
	 SEARCH_BY_TRANSACTIONS("Transactions"), SEARCH_BY_CLIENT_NAME("ClientName"); //ContactWatchList
	
	private String name;

	private PayOutReportQueueDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
