package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

@SuppressWarnings({"squid:S2479","squid:S1192"})
public enum SanctionCsvQueryConstant {

	GET_PFX_DETAILS("SELECT a.TradeAccountNumber AS TradeAccountNumber, \n" + 
			"	a.ID AS AccountID, \n" + 
			"	a.CreatedOn AS AccountCreatedOn,  \n" + 
			"	MAX(c.CreatedOn) AS LatestContactCreatedOn, \n" + 
			"	MAX(pin.CreatedOn) AS LatestPaymentInCreatedOn,  \n" + 
			"	MAX(pout.CreatedOn) AS LatestPaymentOutCreatedOn\n" + 
			"INTO #PFXFinalFilterList\n" + 
			"FROM Comp.Account a WITH (NOLOCK)\n" + 
			"LEFT JOIN Comp.Contact c WITH (NOLOCK) ON a.ID = c.AccountID\n" + 
			"LEFT JOIN Comp.PaymentIn pin WITH (NOLOCK) ON a.ID = pin.AccountID\n" + 
			"LEFT JOIN Comp.PaymentOut pout WITH (NOLOCK) ON a.ID = pout.AccountID\n" + 
			"WHERE a.[Type] IN (2) -- (2) PFX \n" + 
			"AND a.CreatedOn < ?\n" + 
			"GROUP BY a.TradeAccountNumber, a.ID, a.CreatedOn\n" + 
			"SELECT DISTINCT(fl.AccountID), c.ID as ContactID, fl.TradeAccountNumber,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.first_name') + ' ' + JSON_VALUE(ca.[ATTRIBUTES], '$.last_name') AS FullName,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.dob') AS dob,\n" + 
			"	JSON_VALUE(esl.Summary, '$.sanctionId') AS Contact_SanctionID,\n" + 
			"	ct.DisplayName AS Citizenship\n" + 
			"FROM #PFXFinalFilterList fl\n" + 
			"JOIN Contact c WITH (NOLOCK) ON fl.AccountID = c.AccountID\n" + 
			"JOIN ContactAttribute ca WITH (NOLOCK) ON c.ID = ca.ID\n" + 
			"JOIN Country ct WITH (NOLOCK) ON c.Country = ct.ID\n" + 
			"JOIN Event e WITH (NOLOCK) ON fl.AccountID = e.AccountID AND e.EventType = 1\n" + 
			"JOIN EventServiceLog esl WITH (NOLOCK) ON e.id = esl.EventID AND esl.ServiceType = 7 AND ca.ID = esl.EntityID\n" + 
			"WHERE LatestContactCreatedOn IS NULL  OR LatestContactCreatedOn < ?\n" + 
			"	AND LatestPaymentInCreatedOn IS NULL OR LatestPaymentInCreatedOn < ?\n" + 
			"	AND LatestPaymentOutCreatedOn IS NULL OR LatestPaymentOutCreatedOn < ?"),

	GET_CFX_ACCOUNT_DETAILS("SELECT a.TradeAccountNumber AS TradeAccountNumber, \n" + 
			"	a.ID AS AccountID, \n" + 
			"	a.CreatedOn AS AccountCreatedOn,  \n" + 
			"	MAX(c.CreatedOn) AS LatestContactCreatedOn, \n" + 
			"	MAX(pin.CreatedOn) AS LatestPaymentInCreatedOn,  \n" + 
			"	MAX(pout.CreatedOn) AS LatestPaymentOutCreatedOn\n" + 
			"INTO #CFXAccountFinalFilterList\n" + 
			"FROM Comp.Account a WITH (NOLOCK)\n" + 
			"LEFT JOIN Comp.Contact c WITH (NOLOCK) ON a.ID = c.AccountID\n" + 
			"LEFT JOIN Comp.PaymentIn pin WITH (NOLOCK) ON a.ID = pin.AccountID\n" + 
			"LEFT JOIN Comp.PaymentOut pout WITH (NOLOCK) ON a.ID = pout.AccountID\n" + 
			"WHERE a.[Type] IN (1,3) -- (1,3) CFX \n" + 
			"AND a.CreatedOn < ?\n" + 
			"GROUP BY a.TradeAccountNumber, a.ID, a.CreatedOn\n" + 
			"SELECT DISTINCT(fl.AccountID), c.ID as ContactID, fl.TradeAccountNumber,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.first_name') + ' ' + JSON_VALUE(ca.[ATTRIBUTES], '$.last_name') AS FullName,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.dob') AS dob,\n" + 
			"	JSON_VALUE(esl.Summary, '$.sanctionId') AS Contact_SanctionID,\n" + 
			"	ct.DisplayName AS Citizenship\n" + 
			"FROM #CFXAccountFinalFilterList fl\n" + 
			"JOIN Contact c WITH (NOLOCK) ON fl.AccountID = c.AccountID\n" + 
			"JOIN ContactAttribute ca WITH (NOLOCK) ON c.ID = ca.ID\n" + 
			"JOIN Country ct WITH (NOLOCK) ON c.Country = ct.ID\n" + 
			"JOIN Event e WITH (NOLOCK) ON fl.AccountID = e.AccountID AND e.EventType = 1\n" + 
			"JOIN EventServiceLog esl WITH (NOLOCK) ON e.id = esl.EventID AND esl.ServiceType = 7 AND esl.EntityID = fl.AccountID\n" + 
			"WHERE LatestContactCreatedOn IS NULL  OR LatestContactCreatedOn < ?\n" + 
			"	AND LatestPaymentInCreatedOn IS NULL OR LatestPaymentInCreatedOn < ?\n" + 
			"	AND LatestPaymentOutCreatedOn IS NULL OR LatestPaymentOutCreatedOn < ?"),
			
	GET_CFX_CONTACT_DETAILS("SELECT a.TradeAccountNumber AS TradeAccountNumber, \n" + 
			"	a.ID AS AccountID, \n" + 
			"	a.CreatedOn AS AccountCreatedOn,  \n" + 
			"	MAX(c.CreatedOn) AS LatestContactCreatedOn, \n" + 
			"	MAX(pin.CreatedOn) AS LatestPaymentInCreatedOn,  \n" + 
			"	MAX(pout.CreatedOn) AS LatestPaymentOutCreatedOn\n" + 
			"INTO #CFXContactFinalFilterList\n" + 
			"FROM Comp.Account a WITH (NOLOCK)\n" + 
			"LEFT JOIN Comp.Contact c WITH (NOLOCK) ON a.ID = c.AccountID\n" + 
			"LEFT JOIN Comp.PaymentIn pin WITH (NOLOCK) ON a.ID = pin.AccountID\n" + 
			"LEFT JOIN Comp.PaymentOut pout WITH (NOLOCK) ON a.ID = pout.AccountID\n" + 
			"WHERE a.[Type] IN (1,3) -- (1,3) CFX \n" + 
			"AND a.CreatedOn < ?\n" + 
			"GROUP BY a.TradeAccountNumber, a.ID, a.CreatedOn\n" + 
			"SELECT DISTINCT(fl.AccountID), c.ID as ContactID, fl.TradeAccountNumber,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.first_name') + ' ' + JSON_VALUE(ca.[ATTRIBUTES], '$.last_name') AS FullName,\n" + 
			"	JSON_VALUE(ca.[ATTRIBUTES], '$.dob') AS dob,\n" + 
			"	JSON_VALUE(esl.Summary, '$.sanctionId') AS Contact_SanctionID,\n" + 
			"	ct.DisplayName AS Citizenship\n" + 
			"FROM #CFXContactFinalFilterList fl\n" + 
			"JOIN Contact c WITH (NOLOCK) ON fl.AccountID = c.AccountID\n" + 
			"JOIN ContactAttribute ca WITH (NOLOCK) ON c.ID = ca.ID\n" + 
			"JOIN Country ct WITH (NOLOCK) ON c.Country = ct.ID\n" + 
			"JOIN Event e WITH (NOLOCK) ON fl.AccountID = e.AccountID AND e.EventType = 1\n" + 
			"JOIN EventServiceLog esl WITH (NOLOCK) ON e.id = esl.EventID AND esl.ServiceType = 7 AND ca.ID = esl.EntityID\n" + 
			"WHERE LatestContactCreatedOn IS NULL  OR LatestContactCreatedOn < ?\n" + 
			"	AND LatestPaymentInCreatedOn IS NULL OR LatestPaymentInCreatedOn < ?\n" + 
			"	AND LatestPaymentOutCreatedOn IS NULL OR LatestPaymentOutCreatedOn < ?"),
	
	GET_DEBTOR_DETAILS("WITH FundsInEvents(FundsInEventsID) AS (\n" + 
			"		SELECT ID\n" + 
			"		From Comp.Event WITH (NOLOCK)\n" + 
			"		WHERE EventType = 6 --FundsIn\n" + 
			"),\n" + 
			"DebtorSanctionESL(PaymentID, SanctionID) AS (\n" + 
			"		SELECT esl.EntityID,\n" + 
			"		JSON_VALUE(esl.ProviderResponse, '$.sanctionId') sanctionId\n" + 
			"		FROM Comp.EventServiceLog AS esl WITH (NOLOCK) \n" + 
			"		JOIN FundsInEvents ON FundsInEventsID = esl.EventID\n" + 
			"		WHERE esl.ServiceType = 7 --Sanction\n" + 
			"		AND esl.EntityType = 5  --Debtor\n" + 
			"),\n" + 
			"ThirdPartyPayInDetails(TPPaymentInAttributeID, TradeAccountNumber, DebtorName, CountryOfFund) AS \n" + 
			"(\n" + 
			"	    SELECT ID, \n" + 
			"		JSON_VALUE(pia.[Attributes], '$.trade.trade_account_number'),\n" + 
			"		JSON_VALUE(pia.[Attributes], '$.trade.debtor_name'),\n" + 
			"		JSON_VALUE(pia.[Attributes], '$.trade.country_of_fund')\n" + 
			"	    FROM Comp.PaymentInAttribute pia\n" + 
			"	    WHERE pia.vThirdPartyPayment = 1 \n" + 
			")\n" + 
			"SELECT SanctionID, TradeAccountNumber, DebtorName AS 'FullName', c.DisplayName AS Citizenship,\n" + 
			"	'' AS dob\n" + 
			"FROM ThirdPartyPayInDetails tppid\n" + 
			"JOIN DebtorSanctionESL dsesl ON tppid.TPPaymentInAttributeID = dsesl.PaymentID\n" + 
			"JOIN Comp.Country c ON CountryOfFund = c.Code \n" + 
			"WHERE DebtorName IS NOT NULL \n" + 
			"	AND DebtorName != ''\n" + 
			"	AND SanctionID != 'Not Available'"),
	
	GET_WATCHLIST_ID(" SELECT [Id] as Reason FROM [Watchlist] WHERE Reason = ?"),
	
	CHECK_CONTACT_EXISTS_IN_CONTACTWATCHLIST("SELECT contact as ContactID FROM ContactWatchList cwl WHERE REASON=?"),
	
	INSERT_INACTIVE_FINSCAN_WATCHLIST("INSERT INTO [ContactWatchList](Reason,Contact,CreatedBy,CreatedOn) VALUES(?, ?, ?, ?)");

	private String query;

	SanctionCsvQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query; 
	}
}
