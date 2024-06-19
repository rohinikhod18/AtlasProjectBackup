package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Enum PaymentOutReportQueryConstant
 * @author sayleeb
 *
 */
@SuppressWarnings("squid:S1192")
public enum PaymentOutReportQueryConstant {
	
	
	PAYMENT_OUT_QUEUE_CONTACT_FILTER( "SELECT B.AccountId , A.Id ContactId, ROW_NUMBER() OVER (PARTITION BY B.AccountId ORDER BY B.UpdatedOn) RNum "
			+ " FROM ContactAttribute A JOIN Contact B ON A.Id = B.ID {WATCHLIST_CATEGORY_FILTER} {WATCHLIST_FILTER} {PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER}"),
	
	WATCHLIST_FILTER(" EXISTS( SELECT 1 FROM ContactWatchList CL WHERE Reason IN(?) AND a.id = CL.contact ) "),
	
	WATCHLIST_CATEGORY_FILTER(" LEFT JOIN ContactWatchList CL ON CL.contact=B.id JOIN watchlist W ON CL.reason=w.id AND w.category IN(?) "),
	
	PAYMENT_OUT_QUEUE_ACCOUNT_FILTER( "SELECT A.Id AccountId"
			+ " FROM AccountAttribute A JOIN ACCOUNT B ON A.id = B.id"),
	
	PAYMENT_OUT_QUEUE_PAYMENT_FILTER( "SELECT P.Id AS PaymentId"
			+ " FROM Comp.PaymentOutAttribute P {COUNTRY_JOIN}  "),
	
	CONTACT_FILTER_JOIN(" LEFT JOIN ContactAttributeFilter CF ON Payout.ContactId = CF.ContactId"),
	ACCOUNT_FILTER_JOIN(" LEFT JOIN AccountAttributeFilter AF ON Payout.AccountId = AF.AccountId "),
	PAYMENT_FILTER_JOIN(" JOIN PaymentAttributeFilter PF ON PF.PaymentId = payOut.id "),
	ORG_FILTER_JOIN("JOIN Organization org  ON org.Id = payout.organizationID"),
	LEGEL_ENTITY_TABLE_JOIN("JOIN LegalEntity le ON payout.LegalEntityID = le.ID "),
	COUNTRY_FILTER_JOIN("LEFT JOIN  country c ON c.code=P.vBeneficiaryCountry"),
	USER_FILTER_JOIN("LEFT JOIN [USER] U ON U.id=ur.UserId"),
	REPORT_FILTER_NULL_CHECK(" (CF.ContactId IS NOT NULL OR AF.AccountId IS NOT NULL) "),
	PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER("JOIN country cor ON cor.id = B.country"),
	
	PAYMENT_QUEUE_FILTER("SELECT payOut.id AS resourceID, resourceType, ur.createdby  "
			+ "FROM paymentout payOut LEFT JOIN  userresourcelock ur ON ur.resourceID = payOut.id and ur.lockreleasedon is NULL AND resourceType = 1 "
			+ " {CONTACT_FILTER_JOIN} {ACCOUNT_FILTER_JOIN} {PAYMENT_TABLE_JOIN} {ORG_JOIN} {USER_JOIN} {LEGAL_ENTITY_JOIN} JOIN PaymentOutAttribute poa ON poa.id = payOut.id JOIN Account acc ON payout.accountid = acc.id "
			),
	
	GET_PAYMENTOUT_REPORT("DECLARE @Offset int = ?, @Rows int = ?;"
			+ "WITH "
			+ "ContactAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_CONTACT_FILTER} "
			+ "), "
			+ "AccountAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_ACCOUNT_FILTER} "
			+ "), "
			+ "PaymentAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_PAYMENT_FILTER} "
			+ "), "
			+ "SelectedId AS "
			+ "( "
			+ "    {PAYMENT_QUEUE_FILTER} "
			+ "    Order BY {SORT_FIELD_NAME} {ORDER_TYPE} OFFSET @Offset ROWS FETCH NEXT @rows ROWS ONLY "
			+ ") "
			+ "SELECT "
			+ "poa.attributes, "
			+ "    payout.id AS PaymentOutID, "
			+ "    payout.accountid AS AccountId, "
			+ "    payout.contactid AS ContactId, "
			+ "    payout.compliancestatus AS ComplianceStatus, "
			+ "    payOut.contractnumber AS Transactions, "
			+ "    acc.tradeaccountnumber AS Client, "
			+ "     acc.crmaccountid        AS ACSFID, "
			+ "    payOut.transactiondate AS [Date], "
			+ "         acc.[type] "
			+ "     AS Type, "
			+ "    CASE "
			+ "        WHEN acc.[type] = 2 THEN con.NAME "
			+ "        ELSE acc.NAME "
			+ "    END AS clientname, "
			+ "    poa.vbuyingcurrency AS Buy, "
			+ "    poa.vbuyingamount AS Amount, "
			+ "    poa.vBeneficiaryAmount AS BeneficiaryAmount, "
			+ "    concat( poa.vbeneficiaryfirstname,' ', poa.vBeneficiarylastname)AS Beneficiary, "
			+ "    poa.vReasonOfTransfer  AS ReasonOfTransfer, "
			+ "    ResourceId, "
			+ "    us.ssouserid AS LockedBy, "
			+ "    org.NAME AS Organization, "
			+ "    acc.LegalEntity, "
			+ "    poa.vvalueDate AS valueDate, "
			+ "    poa.vMaturityDate AS maturityDate,"
			+ "    c.DisplayName AS Country, "
			+ "    c.Code AS IsoCountry, "
			+ "    pcse.status AS OverAllStatus, "
			+ "    CASE "
			+ "    WHEN acc.[type] = 2 THEN con.PayOutWatchListStatus  "
			+ "   ELSE acc.PayOutWatchListStatus  END AS WatchListStatus, "//Changes for 2986
			+ "    payOut.blackliststatus, "
			+ "    payOut.fraugsterstatus, "
			+ "    payOut.sanctionstatus, "
			+ "    payOut.customcheckstatus, "
			+ "    payOut.intuitionCheckStatus, " // AT-4607
			+"     payOut.PaymentReferenceStatus"//changes for 3854
			+ "    FROM "
			+ "    paymentout payOut "
			+ "JOIN SelectedId pq ON pq.resourceID = payOut.id "
			+ "JOIN paymentoutattribute poa ON "
			+ "    payout.id = poa.id "
			+ "JOIN paymentcompliancestatusenum pcse ON "
			+ "    payout.compliancestatus = pcse.id "
			+ "JOIN account acc ON "
			+ "    payOut.accountid = acc.id "
			+ "JOIN contact con ON "
			+ "    payOut.contactid = con.id "
			+ "JOIN organization org ON "
			+ "    org.id = acc.organizationid "
			+ " LEFT JOIN  country c ON c.code=poa.vBeneficiaryCountry  "
			+ "LEFT JOIN [user] us ON "
			+ "    us.id = pq.createdby order by {SORT_FIELD_NAME} {ORDER_TYPE}"),
	
	GET_PAYMENTOUT_REPORT_DATA("WITH "
			+ "ContactAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_CONTACT_FILTER} "
			+ "), "
			+ "AccountAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_ACCOUNT_FILTER} "
			+ "), "
			+ "PaymentAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_PAYMENT_FILTER} "
			+ "), "
			+ "SelectedId AS "
			+ " ( "
			+ "    {PAYMENT_QUEUE_FILTER} "
			+ ") "
			+ " SELECT "
			+ "poa.attributes, "
			+ " payout.id AS PaymentOutID, "
			+ " payout.accountid AS AccountId, "
			+ " payout.contactid AS ContactId, "
			+ " payout.compliancestatus AS ComplianceStatus, "
			+ " payOut.contractnumber AS Transactions, "
			+"                                    payOut.initialStatus AS initialStatus, "//AT-3472
			+ " acc.tradeaccountnumber AS Client, "
			+ "     acc.crmaccountid        AS ACSFID, "
			+ "    payOut.transactiondate AS [Date], "
			+ "    CASE "
			+ "        WHEN acc.[type] = 2 THEN 'PFX' "
			+ "        WHEN acc.[type] = 1 THEN 'CFX' "
			+ "        ELSE 'CFX(Etailer)' "
			+ "    END AS Type, "
			+ "    CASE "
			+ "        WHEN acc.[type] = 2 THEN con.NAME "
			+ "        ELSE acc.NAME "
			+ "    END AS clientname, "
			+ "    poa.vbuyingcurrency AS Buy, "
			+ "    poa.vbuyingamount AS Amount, "
			+ "    poa.vBeneficiaryAmount AS BeneficiaryAmount, "
			+ "    concat(poa.vBeneficiaryFirstName,' ', poa.vBeneficiaryLastName) AS Beneficiary,  "
			+ "    poa.vReasonOfTransfer  AS ReasonOfTransfer, "
			+ "    ResourceId, "
			+ "    us.ssouserid AS LockedBy, "
			+ "    org.NAME AS Organization, "
			+ "    acc.LegalEntity,"
			+ "    poa.vvalueDate AS valueDate, "
			+ "    poa.vMaturityDate AS maturityDate,"
			+ "    c.DisplayName AS Country, "
			+ "    c.Code AS IsoCountry, "			
			+ "    pcse.status AS OverAllStatus, "
			+ "    acc.PayOutWatchListStatus AS WatchListStatus, "
			+ "    payOut.blackliststatus, "
			+ "    payOut.fraugsterstatus, "
			+ "    payOut.sanctionstatus, "
			+ "    payOut.customcheckstatus, "
			+ "    payOut.PaymentReferenceStatus ," //AT-3855
			+ "    payout.IntuitionCheckStatus " //AT-4717
			+ "FROM "
			+ "    paymentout payOut "
			+ "JOIN SelectedId pq ON pq.resourceID = payOut.id "
			+ "JOIN paymentoutattribute poa ON "
			+ "    payout.id = poa.id "
			+ "JOIN paymentcompliancestatusenum pcse ON "
			+ "    payout.compliancestatus = pcse.id "
			+ "JOIN account acc ON "
			+ "    payOut.accountid = acc.id "
			+ "JOIN contact con ON "
			+ "    payOut.contactid = con.id "
			+ "JOIN organization org ON "
			+ "    org.id = acc.organizationid "
			+ " LEFT JOIN  country c ON c.code=poa.vBeneficiaryCountry  "
			+ "LEFT JOIN [user] us ON "
			+ "    us.id = pq.createdby ORDER BY  payout.updatedon DESC"),
	
	GET_PAYMENTOUT_REPORT_COUNT(
			"WITH "
			+ "ContactAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_CONTACT_FILTER} "
			+ "), "
			+ "AccountAttributeFilter AS "
			+ "( "
			+ "    {PAYMENT_OUT_QUEUE_ACCOUNT_FILTER} "
			+ "), "
			+ "PaymentAttributeFilter AS "
			+ " ( "
			+ "    {PAYMENT_OUT_QUEUE_PAYMENT_FILTER} "
			+ "), "
			+ "SelectedId AS "
			+ " ( "
			+ "    {PAYMENT_QUEUE_FILTER} "
			+ " ) "
			+ " SELECT "
			+ "count(payout.id) as TotalRows  "
			+ "FROM "
			+ "    paymentout payOut "
			+ "JOIN SelectedId pq ON pq.resourceID = payOut.id "
			+ "JOIN paymentoutattribute poa ON "
			+ "    payout.id = poa.id "
			+ "JOIN paymentcompliancestatusenum pcse ON "
			+ "    payout.compliancestatus = pcse.id "
			+ "JOIN account acc ON "
			+ "    payOut.accountid = acc.id "
			+ "JOIN contact con ON "
			+ "    payOut.contactid = con.id "
			+ "JOIN organization org ON "
			+ "    org.id = acc.organizationid "
			+ "LEFT JOIN [user] us ON "
			+ "    us.id = pq.createdby"),
	
	GET_PAYMENTOUT_REPORT_WITHOUT_PAGINATION("SELECT Count(*) AS count from( SELECT * FROM "
			+ " ( "
			+ " SELECT "
			+ " payout.id AS PaymentOutID, "
			+ " payout.accountid AS AccountId, "
			+ " payout.contactid AS ContactId, "
			+ " payout.compliancestatus AS ComplianceStatus, "
			+ " payOut.deleted AS Deleted, "
			+ " payOut.contractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS Client, "
			+ " payOutAttri.transactiondate AS [Date], "
			+ " con.[NAME] AS ClientName, "
			+ " acc.[type] AS Type, "
			+ " payOutAttri.buyingcurrency AS Buy, "
			+ " payOutAttri.buyingamount AS Amount, "
			+ " payOutAttri.Attributes AS Attributes, "
			+ " payOutAttri.beneficiaryfirstname + ' '  + payOutAttri.beneficiarylastname AS Beneficiary, "
			+ " ur.id AS ResourceId, "
			+ " (SELECT SSOUserId FROM [User] WHERE id=ur.createdby) AS LockedBy, "
			+ " (SELECT NAME FROM organization WHERE id = acc.organizationid) AS Organization, "
			+ "                                               "
			+ "                                               ( Select ','+(select wl.reason AS WatchListReason from WatchList wl where id=cw.Reason)  AS [text()] "
			+ "                                                From contactwatchlist cw JOIN contact con ON cw.contact=con.id AND con.accountid = payout.accountid "
			+ "                                                For XML PATH ('') ) AS WatchListReason, "
			+ "                       "
			+ " (SELECT code AS Country FROM country WHERE id = (SELECT beneficiarycountry FROM paymentoutattribute pa WHERE pa.id = payOut.id)) AS Country, "
			+ " (SELECT status FROM paymentcompliancestatusenum WHERE id = (SELECT compliancestatus FROM paymentout po WHERE payOut.id = po.id)) AS OverAllStatus, "
			+ " (SELECT Count(*) FROM contactwatchlist cw WHERE cw.contact IN (SELECT id FROM contact WHERE accountid = payOut.accountid)) AS WatchListStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'BLACKLIST') AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC)) AS BlackListStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'FRAUGSTER') AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC)) AS FraugsterStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'SANCTION') AND esl.entitytype = 'CONTACT' AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC) ORDER BY esl.id DESC) AS SanctionConStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'SANCTION') AND esl.entitytype = 'BENEFICIARY' AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC) ORDER BY esl.id DESC) AS SanctionBeneStatus, "
			+ " ( "
			+ " SELECT "
			+ " TOP 1 status "
			+ " FROM eventservicelog esl "
			+ " WHERE esl.servicetype = "
			+ " ( "
			+ " SELECT "
			+ " id "
			+ " FROM compliance_servicetypeenum "
			+ " WHERE code = 'SANCTION' "
			+ " ) "
			+ " AND esl.entitytype = 'BANK' "
			+ " AND payOut.id = "
			+ " ( "
			+ " SELECT "
			+ " TOP 1 paymentoutid "
			+ " FROM event "
			+ " WHERE id IN ( esl.eventid ) "
			+ " ORDER BY id DESC "
			+ " ) "
			+ " ORDER BY esl.id DESC "
			+ " ) "
			+ " AS SanctionBankStatus "
			+ " FROM paymentout payOut "
			+ " JOIN paymentoutattribute payOutAttri ON payout.id = payOutAttri.id "
			+ " JOIN account acc ON payOut.accountid = acc.id "
			+ " JOIN contact con ON payOut.contactid = con.id "
			+ " LEFT JOIN userresourcelock ur ON payout.id = ur.resourceid "
			+ " AND ur.lockreleasedon IS NULL "
			+ " AND ur.resourcetype = 'PAYMENT_OUT'   ) "
			+ " AS potemp "
			+ " WHERE deleted <> 1 ) "
			+ " AS potemp2 "),
	
	GET_PAYMENTOUT_REPORT_INNER_QUERY("SELECT Top 100 PERCENT Row_number() OVER ( ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE}) AS rownum, potemp2.* from( SELECT potemp.* FROM "
			+ " ( "
			+ " SELECT "
			+ " payout.id AS PaymentOutID, "
			+ " payout.accountid AS AccountId, "
			+ " payout.contactid AS ContactId, "
			+ " payout.compliancestatus AS ComplianceStatus, "
			+ " payOut.deleted AS Deleted, "
			+ " payOut.contractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS Client, "
			+ " payOutAttri.transactiondate AS [Date], "
			+ " con.[NAME] AS ClientName, "
			+ " acc.[type] AS Type, "
			+ " payOutAttri.buyingcurrency AS Buy, "
			+ " payOutAttri.buyingamount AS Amount, "
			+ " payOutAttri.Attributes AS Attributes, "
			+ " payOutAttri.beneficiaryfirstname + ' ' + payOutAttri.beneficiarylastname AS Beneficiary, "
			+ " ur.id AS ResourceId, "
			+ " (SELECT SSOUserId FROM [User] WHERE id=ur.createdby) AS LockedBy, "
			+ " (SELECT NAME FROM organization WHERE id = acc.organizationid) AS Organization, "
			+ " "
			+ " ( Select ','+(select wl.reason AS WatchListReason from WatchList wl where id=cw.Reason)  AS [text()] "
			+ " From contactwatchlist cw JOIN contact con ON cw.contact=con.id AND con.accountid = payout.accountid "
			+ " For XML PATH ('') ) AS WatchListReason, "
			+ " "
			+ " (SELECT code AS Country FROM country WHERE id = (SELECT beneficiarycountry FROM paymentoutattribute pa WHERE pa.id = payOut.id)) AS Country, "
			+ " (SELECT status FROM paymentcompliancestatusenum WHERE id = (SELECT compliancestatus FROM paymentout po WHERE payOut.id = po.id)) AS OverAllStatus, "
			+ " (SELECT Count(*) FROM contactwatchlist cw WHERE cw.contact IN (SELECT id FROM contact WHERE accountid = payOut.accountid)) AS WatchListStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'BLACKLIST') AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC)) AS BlackListStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'FRAUGSTER') AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC)) AS FraugsterStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'SANCTION') AND esl.entitytype = 'CONTACT' AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC) ORDER BY esl.id DESC) AS SanctionConStatus, "
			+ " (SELECT TOP 1 status FROM eventservicelog esl WHERE esl.servicetype = (SELECT id FROM compliance_servicetypeenum WHERE code = 'SANCTION') AND esl.entitytype = 'BENEFICIARY' AND payOut.id = (SELECT TOP 1 paymentoutid FROM event WHERE id IN ( esl.eventid ) ORDER BY id DESC) ORDER BY esl.id DESC) AS SanctionBeneStatus, "
			+ " ( "
			+ " SELECT "
			+ " TOP 1 status "
			+ " FROM eventservicelog esl "
			+ " WHERE esl.servicetype = "
			+ " ( "
			+ " SELECT "
			+ " id "
			+ " FROM compliance_servicetypeenum "
			+ " WHERE code = 'SANCTION' "
			+ " ) "
			+ " AND esl.entitytype = 'BANK' "
			+ " AND payOut.id = "
			+ " ( "
			+ " SELECT "
			+ " TOP 1 paymentoutid "
			+ " FROM event "
			+ " WHERE id IN ( esl.eventid ) "
			+ " ORDER BY id DESC "
			+ " ) "
			+ " ORDER BY esl.id DESC "
			+ " ) "
			+ " AS SanctionBankStatus "
			+ " FROM paymentout payOut "
			+ " JOIN paymentoutattribute payOutAttri ON payout.id = payOutAttri.id "
			+ " JOIN account acc ON payOut.accountid = acc.id "
			+ " JOIN contact con ON payOut.contactid = con.id "
			+ " LEFT JOIN userresourcelock ur ON payout.id = ur.resourceid "
			+ " AND ur.lockreleasedon IS NULL "
			+ " AND ur.resourcetype = 'PAYMENT_OUT'   ) "
			+ " AS potemp "
			+ " WHERE deleted <> 1 ) "
			+ " AS potemp2"),
	
	
	GET_PAYMENTOUT_REPORT_PAGINATION_WITH_CRITERIA("Select * from ({INNER_QUERY}) AS pqueue3 "
			+ "WHERE  rownum >= ? "
			+ "AND    rownum <= ?"), 
	
	//GET_PAYMENTOUT_REPORT_COUNT("SELECT COUNT(*) as count FROM PaymentOut"),
	
	GET_PAYMENTOUT_REPORT_PAGINATION_WITH_SELECT("WITH poque AS( "
			+ "SELECT "
			+ " Row_number() OVER( ORDER BY payout.id DESC ) AS rownum, "
			+ " payout.id AS PaymentOutID, "
			+ " payout.accountid AS AccountId, "
			+ " payout.contactid AS ContactId,   "
			+ " payout.compliancestatus AS ComplianceStatus, "
			+ " payOut.contractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS Client, "
			+ " poa.transactiondate AS [Date], "
			+ " acc.[type] AS Type, "
			+ " case when  acc.[type]='PFX' then con.NAME when acc.[type]='CFX' then acc.NAME "
			+ " END as clientname,"
			+ " acc.CRMAccountID AS ACSFID,  poa.ReasonOfTransfer AS ReasonOfTransfer,  "
			+ " poa.buyingcurrency AS Buy, "
			+ " poa.buyingamount AS Amount, "
			+ "     poa.Attributes AS Attributes,  poa.BeneAmount AS BeneficiaryAmount, "
			+ " concat(poa.vBeneficiaryFirstName,' ', poa.vBeneficiaryLastName) AS Beneficiary, "
			+ " ur.id AS ResourceId, "
			+ " us.SSOUserId AS LockedBy, "
			+ " org.NAME AS Organization, "
			+ " ( Select ','+(select wl.reason AS WatchListReason from WatchList wl where id=cw.Reason)  AS [text()] "
			+ " From contactwatchlist cw JOIN contact con ON cw.contact=con.id AND con.accountid = payout.accountid "
			+ " For XML PATH ('') ) AS WatchListReason, "
			+ " ctr.code AS Country, "
			+ " pcse.status AS OverAllStatus, "
			+ " acc.WatchlistStatus AS WatchListStatus, "
			+ " payOut.BlacklistStatus, "
			+ " payOut.FraugsterStatus, "
			+ " payOut.SanctionStatus, "
			+ " payOut.CustomCheckStatus "
			+ " FROM paymentout payOut "
			+ " JOIN paymentoutattribute poa ON payout.id = poa.id "
			+ " JOIN paymentcompliancestatusenum pcse ON  payout.compliancestatus = pcse.id "
			+ " JOIN account acc ON payOut.accountid = acc.id "
			+ " JOIN contact con ON payOut.contactid = con.id "
			+ " JOIN Country ctr ON poa.beneficiarycountry = ctr.id "
			+ " JOIN organization org ON org.id = acc.organizationid "
			+ " LEFT JOIN userresourcelock ur ON  payout.id = ur.resourceid "
			+ " AND ur.resourcetype = 'PAYMENT_OUT' "
			+ " LEFT JOIN [user] us ON us.id = ur.createdby "
			+ " WHERE "
			+ " poa.transactiondate > DATEADD(day,-" + SearchCriteriaUtil.getDefaultQueueRecordSize() + ",GETDATE()) "
			+ " AND payOut.deleted = 0 "
			+ ") SELECT *, (SELECT max( rownum ) FROM poque ) AS 'TotalRows' "
			+ "FROM "
			+ " poque "
			+ "WHERE "
			+ " rownum >= ? "
			+ " AND rownum <= ? "),
	
	
	GET_PAYMENTOUT_REPORT_INNER_QUERY_SELECT(" SELECT Row_number() OVER ( ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE}) AS rownum, pqueue2.* from "
			+ "       ( SELECT pqueue.* FROM ( select"
			+ " payout.id AS PaymentOutID, "
			+ " payout.accountid AS AccountId, "
			+ " payout.contactid AS ContactId, "
			+ " payout.compliancestatus AS ComplianceStatus, "
			+ " payOut.contractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS Client, "
			+ " poa.transactiondate AS [Date], "
			+ " acc.[type] AS Type, "
			+ " case when  acc.[type]='PFX' then con.NAME when acc.[type]='CFX' then acc.NAME "
			+ " END as clientname,"
			+ " poa.buyingcurrency AS Buy, "
			+ " poa.buyingamount AS Amount, "
			+ " poa.Attributes AS Attributes,  poa.BeneAmount AS BeneficiaryAmount, acc.CRMAccountID AS ACSFID,  poa.ReasonOfTransfer AS ReasonOfTransfer,  "
			+ " concat(poa.vBeneficiaryFirstName,' ', poa.vBeneficiaryLastName) AS Beneficiary, "
			+ " ur.id AS ResourceId, "
			+ " us.SSOUserId AS LockedBy, "
			+ " org.NAME AS Organization, "
			+ " ( Select ',' + (select wl.reason AS WatchListReason from WatchList wl where id=cw.Reason)  AS [text()] "
			+ " From contactwatchlist cw JOIN contact con ON cw.contact=con.id AND con.accountid = payout.accountid "
			+ " For XML PATH ('') ) AS WatchListReason, "
			+ " ctr.code AS Country, ctr.DisplayName AS CountryDisplayName , "
			+ " pcse.status AS OverAllStatus, "
			+ " acc.WatchlistStatus AS WatchListStatus, "
			+ " payOut.BlacklistStatus, "
			+ " payOut.FraugsterStatus, "
			+ " payOut.SanctionStatus, "
			+ " payOut.CustomCheckStatus "
			+ " FROM paymentout payOut "
			+ " JOIN paymentoutattribute poa ON payout.id = poa.id "
			+ " JOIN paymentcompliancestatusenum pcse ON  payout.compliancestatus = pcse.id "
			+ " JOIN account acc ON payOut.accountid = acc.id "
			+ " JOIN contact con ON payOut.contactid = con.id "
			+ " LEFT JOIN Country ctr ON poa.beneficiarycountry = ctr.id "
			+ " JOIN organization org ON org.id = acc.organizationid "
			+ " LEFT JOIN userresourcelock ur ON  payout.id = ur.resourceid "
			+ " AND ur.resourcetype = 'PAYMENT_OUT' AND ur.LockReleasedOn IS NULL "
			+ " LEFT JOIN [user] us ON us.id = ur.createdby "
			+ " WHERE "
			+ " {PAYMENTOUT_DATETIME_CRITERIA} "
			+ " payOut.deleted = 0 )  As pqueue "),
	
	GET_PAYMENTOUT_REPORT_PAGINATION_WITH_CRITERIA_SELECT("WITH poque AS " 
			+ " ( "
			+" {INNER_QUERY}"
			+ "  ) "
			+ " As pqueue2 ) SELECT "
			+ "    *, ( SELECT max (rownum) FROM poque) AS 'TotalRows' "
			+ " FROM poque "
			+ " WHERE rownum >= ? "
			+ "  AND rownum <= ? "),
	
	
	GET_PAYMNETOUT_REPORT_WHOLE_DATA( "select rc.* "
			+ "from( {INNER_QUERY}"
			+ " )AS  pqueue2 )as rc"
			);
	
	
	
       private String query;

		PaymentOutReportQueryConstant(String query) {
			this.query = query;

		}

		public String getQuery() {
			return this.query;
		}
}
