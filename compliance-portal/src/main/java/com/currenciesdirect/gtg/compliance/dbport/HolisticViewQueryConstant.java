package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum HolisticViewQueryConstant.
 *
 * @author laxmib
 */
@SuppressWarnings("squid:S1192")
public enum HolisticViewQueryConstant {
	
	HOLISTIC_VIEW("select accAttri.Attributes AS Accountattribute, conAttri.Attributes AS Contactattribute , "
			+ " acc.CreatedOn As  RegistrationInDate, "
		+ " acc.ComplianceDoneOn AS compliancedoneon, "
		+ " org.Code AS OrgCode, "
		+ " con.AccountID as AccountID, "
		+ " con.ID AS ContactID, "
	    + " acc.AccountStatus AS AccountStatus from Account acc "
		+ " Join Accountattribute accAttri "
		+ " On acc.id = accAttri.id "
		+ " Join Organization org"
		+ " ON org.id = acc.OrganizationID "
		+ " Join Contact con "
		+ " ON acc.id = con.accountid "
		+ " Join ContactAttribute conAttri "
		+ " On con.id = conAttri.id "
		+ " where acc.id = ? and con.id = ?"),
	
	HOLISTIC_VIEW_PAYMENT_DETAILS("select "
		+ " count( distinct(ContractNumber) ) As NumberOfTrades, "
		+ " max( TransactionDate ) AS LastTradeDate, "
		+ " min( TransactionDate ) AS FirstTradeDate "
		+ " "
		+ " from "
		+ " paymentout "
		+ " where "
		+ " AccountID = ? "),
	
	HOLISTIC_VIEW_TOTAL_SALE_AMOUNT("SELECT Sum(vBaseCurrencyAmount) AS TotalSaleAmount"
			+ " FROM PaymentInAttribute pa "
			+ " JOIN PaymentIn pin ON pa.id=pin.id"
			+ " WHERE accountid = ? "),
	
	HOLISTIC_VIEW_PAYMENTOUT_DETAILS("SELECT "
		+ " * "
		+ "FROM "
		+ " Comp.PaymentOut payout "
		+ "JOIN Comp.PaymentOutAttribute payoutattri On "
		+ " payout.id = payoutattri.id "
		+ "where "
		+ " payout.id = ?"),
	
	HOLISTIC_VIEW_PAYMENTIN_DETAILS("SELECT "
		+ " * "
		+ "FROM "
		+ " Comp.PaymentIn payin "
		+ "JOIN Comp.PaymentInAttribute payinattri On "
		+ " payin.id = payinattri.id "
		+ "where "
		+ " payin.id = ?"),
	
	GET_OTHER_CONTACT_DETAILS("SELECT "
		+ " c.ID AS contactID, "
		+ " c.Name AS Name, "
		+ " conAttri.Attributes AS Contactattribute, "
		+ " CASE "
		+ " A.[Type] "
		+ " WHEN 1 THEN 'CFX' "
		+ " ELSE 'PFX' "
		+ " END AS CustomerType, "
		+ " cste.Status AS complianceStatus "
		+ "FROM "
		+ " Contact C "
		+ "JOIN ContactAttribute conAttri ON "
		+ " c.id = conAttri.id "
		+ "JOIN Account A ON "
		+ " C.Accountid = A.id "
		+ "JOIN ContactComplianceStatusEnum cste ON "
		+ " c.complianceStatus = cste.id "
		+ "WHERE "
		+ " AccountID = ? "
		+ " AND C.Id <> ? AND (C.Deleted = 0 OR C.Deleted IS NULL)"),
	
	GET_DEVICE_INFO("select "
		+ " deviceinfo.DeviceType AS DeviceType, "
		+ " deviceinfo.DeviceName AS DeviceName, "
		+ " deviceinfo.DeviceVersion AS DeviceVersion, "
		+ " deviceinfo.DeviceID AS DeviceID, "
		+ " deviceinfo.DeviceManufacturer AS DeviceManufacturer, "
		+ " deviceinfo.OSType AS OSType, "
		+ " deviceinfo.BrowserName AS BrowserName, "
		+ " deviceinfo.BrowserVersion AS BrowserVersion, "
		+ " deviceinfo.BrowserLanguage AS BrowserLanguage, "
		+ " deviceinfo.BrowserOnline AS BrowserOnline, "
		+ " deviceinfo.OSTimestamp AS OSTimestamp, "
		+ " deviceinfo.CDAppID AS CDAppID, "
		+ " deviceinfo.CDAppVersion AS CDAppVersion "
		+ " FROM "
		+ " DeviceInfo AS deviceinfo "
		+ " JOIN Event AS eve ON "
		+ " deviceinfo.EventID = eve.ID "
		+ " JOIN EventTypeEnum ete ON "
		+ " eve.EventType = ete.ID "
		+ " WHERE "
		+ " eve.accountid = ? "
		+ " ORDER BY "
		+ " deviceinfo.CreatedOn"),
	
	GET_CONTACT_DETAILS_FOR_CFX("SELECT "
			+ " c.ID AS contactID, "
			+ " c.Name AS Name, "
			+ " conAttri.Attributes AS Contactattribute, "
			+ " CASE "
			+ " A.[Type] "
			+ " WHEN 1 THEN 'CFX' "
			+ " ELSE 'PFX' "
			+ " END AS CustomerType, "
			+ " cste.Status AS complianceStatus "
			+ "FROM "
			+ " Contact C "
			+ "JOIN ContactAttribute conAttri ON "
			+ " c.id = conAttri.id "
			+ "JOIN Account A ON "
			+ " C.Accountid = A.id "
			+ "JOIN ContactComplianceStatusEnum cste ON "
			+ " c.complianceStatus = cste.id "
			+ "WHERE "
			+ " AccountID = ? AND (c.Deleted = 0 OR c.Deleted IS NULL)");
	
	
	
	private String query;

	HolisticViewQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}
	
	
}