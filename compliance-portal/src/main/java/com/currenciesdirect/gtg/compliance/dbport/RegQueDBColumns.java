package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum RegistrationQueueDBColumns.
 */
public enum RegQueDBColumns {

	CONTACTID("ContactId"), COMPLIANCESTATUS("ComplianceStatus"), CONTACTNAME("ContactName"), // contact
																								// fields
	ACCOUNTID("AccountId"), TRADEACCOUNTNUM("TradeAccountNumber"), REGISTEREDON("UpdatedOn"), ORGANIZATION(
			"Organization"), ORG_NAME("org.NAME"), LEGALENTITY("LegalEntity"), LEGAL_ENTITY("le.code"), DEVICE_ID("DeviceId"),
	DATA_ANON_STATUS("A.DataAnonStatus"),// account fields
	TYPE("Type"), TYPE_FILTER("A.Type"), SELLCURRENCY("vSellCurrency"), BUYCURRENCY("vBuyCurrency"), SOURCE("vSource"), TRANSACTIONVALUE(
			"vTransactionValue"), // account attribute fields
	KYCSTATUS("eidstatus"), BLACKLISTSTATUS("BlacklistStatus"), FRAUGSTERSTATUS("FraugsterStatus"), SANCTIONSTATUS(
			"SanctionStatus"),EVENTID("eventId"), // eventServiceLog fields
	USER_RESOURCE_LOCK_ID("userResourceiLockId"), LOCKED_BY("LockedBy"),// userresourcelock fields
	
	SERVICETYPE("ServiceType"),STATUS("status"), // VIEW MORE DETAILS
	
	SOURCEQUEUE("source"), CODE("Code"), CURRENCY("Code"),REASON("reason"),OWNER("SSOUserID"),WATCHLIST("watchlist"),
	REGOWNER("ssouserid") ,REGSTATUS("ComplianceStatus") ,TOTALROWS("TotalRows"),WATCHLISTID("id"),
	ISLOCKRELEASEDON("LockReleasedOn"),ONFIDOSTATUS("DocumentVerifiedStatus"),
	
	
	USER_RESOURCE_CREATEDON("UserResourceCreatedOn"),USER_RESOURCE_WORKFLOWTIME("UserResourceWorkflowTime"),USER_RESOURCE_ENTITYTYPE("UserResourceEntityType"),
	ACCOUNT_CREATEDON("b.createdon"),CONTACT_CREATEDON("b.createdon"),
	ACCOUNT_UPDATEDON("B.UpdatedOn"),CONTACT_UPDATEDON("B.UpdatedOn"),
	COUNTRY_OF_RESIDENCE("CountryFullName"), ACCOUNT_VERSION("Version"), REGISTERED_DATE("registeredDate"),CUSOMCHECKSTATUS("customCheckStatus");
	
	private String name;

	private RegQueDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
