package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum PayOutQueDBColumns.
 */
public enum PayOutQueDBColumns {

	 COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), CLIENTNAME("ClientName"), // contact fields
																								 
	TRADEACCOUNTNUM("Client"), REGISTEREDON("RegisteredOn"), ORGANIZATION("Organization"),ACSFID("ACSFID"), // account fields
	
	TYPE("Type"), SELLCURRENCY("SellCurrency"),  SOURCE("Source"), TRANSACTIONVALUE("TransactionValue"), // account attribute fields
	
	KYCSTATUS("KYCStatus"), BLACKLISTSTATUS("BlacklistStatus"), FRAUGSTERSTATUS("FraugsterStatus"), CUSTOMCHECKSTATUS("CustomCheckStatus") ,BLACKLISTPAYREFSTATUS("PaymentReferenceStatus"), //AT-3854, //AT-3855 
	
	SANCTIONSTATUS("SanctionStatus"),SANCTIONCONSTATUS("SanctionConStatus"),SANCTIONBANKSTATUS("SanctionBankStatus"),
	
	INTUITIONSTATUS("IntuitionCheckStatus"), //AT-4607
	
	SANCTIONBENESTATUS("SanctionBeneStatus"), // eventServiceLog fields
	
	USER_RESOURCE_LOCK_ID("ResourceId"), LOCKED_BY("LockedBy"),// userresourcelock fields
	
	PAYMENTOUTID("PaymentOutID"),ACCOUNTID("AccountId"),CONTACTID("ContactId"),TRANSACTIONID("Transactions"),OVERALLSTATUS("OverAllStatus"), //PaymentOut fields
	
	COUNTRY("Country"), //Country 
	
	COUNTRY_DISPLAY_NAME("CountryDisplayName"),INITIALSTATUS("initialStatus"),
	
	DATE("Date"),  BUYCURRENCY("Buy"),AMOUNT("Amount"),ATTRIBUTE("Attributes"),BENEFICIARYAMMOUNT("BeneficiaryAmount"),TRANSACTIONDATE("poa.transactiondate"),	ALIAS_PAYMENTOUTID("payout.id"),
	BENEFICIARY("Beneficiary"), BENEFICIARYFIRSTNAME("BeneficiaryFirstName"), BENEFICIARYLASTNAME("BeneficiaryLastName"), //PaymentOutAttribute fields
	REGOWNER("ssouserid"),ISLOCKRELEASEDON("LockReleasedOn"),
	
	WATCHLISTSTATUS("WatchListStatus"),TOTAL_ROWS("TotalRows"),WATCHLISTREASON("WatchListReason"),REASONOFTRANSFER("ReasonOfTransfer"),
	
	FILTER_BY_BUYCURRENCY("poa.buyingcurrency"), FILTER_BY_ORGANIZATION("org.NAME")  , FILTER_BY_COUNTRY("ctr.DisplayName"),
	
	FILTER_BY_BLACKLISTSTATUS("payOut.BlacklistStatus"), FILTER_BY_FRAUGSTERSTATUS("payOut.FraugsterStatus"), 
	
	FILTER_BY_SANCTIONSTATUS("payOut.SanctionStatus"), FILTER_BY_WATCHLISTSTATUS("WatchListStatus"), FILTER_BY_BLACKLISTPAYREFSTATUS("payOut.PaymentReferenceStatus"),
	
	FILTER_BY_CUSTOMCHECKSTATUS("payOut.CustomCheckStatus"),FILTER_BY_INTUITIONCHECK("payOut.IntuitionCheckStatus"),FILTER_BY_TRAANSACTIONMONITORING_REQUEST("acc.AccountTMFlag")/*Added for AT-4656*/, LEGAL_ENTITY("le.code"); //ContactWatchList
	
	private String name;

	private PayOutQueDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
