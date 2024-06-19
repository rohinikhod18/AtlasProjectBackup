package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Enum PaymentInQueryConstant.
 *
 * @author Rajesh
 */
@SuppressWarnings("squid:S1192")
public enum PaymentInQueryConstant {	
	
	PAYMENTIN_QUEUE_CONTACT_ATTRIBUTE_FILTER( "SELECT   b.accountid , a.id contactid, Row_number() OVER (partition BY b.accountid ORDER BY b.updatedon) rnum "
			+ "   FROM contactattribute A JOIN contact B ON a.id = b.id {PAYMENTIN_QUEUE_COUNTRY_OF_RESIDENCE_FILTER} {WATCHLIST_CATEGORY_FILTER}"),
	
	PAYMENTIN_QUEUE_ACCOUNT_ATTRIBUTE_FILTER( "  SELECT a.id accountid, b.organizationId as organization  "
			+ " FROM   accountattribute A  JOIN account B  ON  a.id = b.id"),
	
	PAYMENTIN_QUEUE_ATTRIBUTE_FILTER(" SELECT p.id AS paymentid  FROM   comp.paymentinattribute P " 
			+ "{COUNTRY_JOIN} "),
	
	PAYMENTIN_QUEUE_FILTER(" SELECT  payin.id AS resourceid,  resourcetype, ur.createdby "
			/*
			 * +
			 * " (SELECT	se.status FROM ServiceStatusEnum se WHERE se.ID = esl.Status) AS ESLRiskStatus "
			 */
			+ " FROM   paymentin payin " //,  U.SsoUserId
			+ " LEFT JOIN userresourcelock ur  ON  ur.resourceid = payin.id  AND resourcetype = 2  AND ur.LockReleasedOn IS NULL "
			/*
			 * +
			 * " LEFT JOIN eventservicelog esl ON esl.EntityID = payin.ID AND esl.ServiceType = 11 "
			 */
			+ " {CONTACT_FILTER_JOIN} {ACCOUNT_FILTER_JOIN} {ATTRIBUTE_FILTER_JOIN} {USER_JOIN} {ORG_TABLE_JOIN} {LEGAL_ENTITY_TABLE_JOIN} JOIN Account acc ON payin.accountid = acc.id  "
			+ " WHERE payin.compliancestatus=4 AND payin.Deleted <> 1 AND payin.isOnQueue=1 "), 
	
	PAYMENTIN_QUEUE_CONTACT_FILTER_JOIN(" LEFT JOIN  contactattributefilter CF ON payin.contactid = cf.contactid"),
	
	PAYMENTIN_QUEUE_ACCOUNT_FILTER_JOIN(" LEFT JOIN  accountattributefilter AF ON payin.accountid = af.accountid"),
	
	PAYMENTIN_QUEUE_ATTRIBUTE_FILTER_JOIN(" JOIN paymentattributefilter PF ON payin.id = PF.paymentid"),
	
	WATCHLIST_CATEGORY_FILTER(" LEFT JOIN ContactWatchList CL ON CL.contact=B.id JOIN watchlist W ON CL.reason=w.id AND w.category IN(?)  "),
	
	ORG_TABLE_JOIN("JOIN Organization org ON org.Id = payin.organizationID  "),
	
	LEGAL_ENTITY_TABLE_JOIN("JOIN LegalEntity le ON le.ID = payin.LegalEntityID"),
	
	USER_FILTER_JOIN("LEFT JOIN [USER] U ON U.id=ur.UserId "),
	
	COUNTRY_FILTER_JOIN("LEFT JOIN  country c ON c.code=P.vCountryOfPayment"),
	
	RG_FILTER_JOIN("LEFT JOIN eventservicelog esl ON esl.EntityID = payin.ID AND esl.ServiceType = 11"),
	
	QUEUE_FILTER_NULL_CHECK(" (CF.ContactId IS NOT NULL OR AF.AccountId IS NOT NULL) "),
	
	PAYMENTIN_QUEUE_COUNTRY_OF_RESIDENCE_FILTER("JOIN country cor ON cor.id = b.country"),
	
	GET_PAYMENTIN_QUEUE(" DECLARE @Offset INT = ?,   @Rows   INT = ?;"
			+ " WITH "
			+ " contactattributefilter AS ( {PAYMENTIN_REPORT_CONTACT_FILTER} ), "
			+ " accountattributefilter AS ( {PAYMENTIN_REPORT_ACCOUNT_FILTER} ), "
			+ " paymentattributefilter AS ( {PAYMENTIN_ATTRIBUTE_FILTER} ), "
			+ " selectedid AS (  {PAYMENTIN_REPORT_FILTER} order by {SORT_FIELD_NAME} {ORDER_TYPE}  offset @Offset rows  FETCH next @rows rows only ) "
			+ " SELECT    pina.attributes, "
			+ "          payin.id               AS paymentinid, "
			+ "          payin.accountid        AS accountid, "
			+ "          payin.contactid        AS contactid, "
			+ "          payin.compliancestatus AS compliancestatus, "
			+ "          payin.tradeContractNumber   AS transactions, "
			+ "          acc.tradeaccountnumber  AS client, "
			+ "          con.Country             AS countryOfResidence,"
			+ "          acc.crmaccountid        AS acsfid, "
			+ "          payin.transactiondate  AS [Date], "
			+ "          acc.[type] AS type, "
			+ "          CASE "
			+ "                    WHEN acc.[type] = 2 THEN con.NAME "
			+ "                    ELSE acc.NAME  END AS clientname, "
			+ "          pina.vTransactionCurrency                         AS transactioncurrency, "
			+ "          pina.vTransactionAmount                           AS amount, "
			+ "          pina.vBaseCurrencyAmount                          AS basecurrencyamount, "
			+ " pina.vPaymentMethod AS paymentmethod, "
			+ "          pina.vThirdPartyPayment                           AS isthirdpartypayment, "
			+ "          resourceid, "
			+ " us.ssouserid AS lockedby, "
			+ " org.NAME AS organization,"
			+ " acc.LegalEntity, "
			+ " pina.vCountryOfPayment AS country, "
			+ "          coun.DisplayName AS countrydisplayname,"
			+ " pcse.status AS overallstatus, "
			+ "          CASE "
			+ "          WHEN acc.[type] = 2 THEN con.PayInwatchliststatus "
			+ " ELSE acc.PayInwatchliststatus END AS WatchListStatus, "//changes for AT-2986
			+ "          payin.blackliststatus, "
			+ "          payin.fraugsterstatus, "
			+ "          payin.sanctionstatus, "
			+ "          payin.customcheckstatus, "
			+ " payin.intuitionCheckStatus, " // AT-4607
			+ "          payin.debitCardFraudCheckStatus AS riskStatus "
			+ " FROM      paymentin payin "
			+ " JOIN      selectedid pq "
			+ " ON        pq.resourceid = payin.id "
			+ " JOIN      paymentinattribute pina "
			+ " ON        payin.id = pina.id "
			+ " JOIN      paymentcompliancestatusenum pcse "
			+ " ON        payin.compliancestatus = pcse.id "
			+ " JOIN      account acc "
			+ " ON        payin.accountid = acc.id "
			+ " JOIN      contact con "
			+ " ON        payin.contactid = con.id "
			+ " JOIN      organization org "
			+ " ON        org.id = acc.organizationid "
			+ " LEFT JOIN country cor "
			+ " ON        cor.id = con.country "
			+ " LEFT JOIN country coun "
			+ " ON        coun.code = pina.vcountryofpayment "
			+ " LEFT JOIN [user] us "
			+ " ON        us.id = pq.createdby order by {SORT_FIELD_NAME} {ORDER_TYPE}"),
	
	PAYMENTIN_QUEUE_COUNT(" WITH "
			+ " contactattributefilter AS ( {PAYMENTIN_REPORT_CONTACT_FILTER} ), "
			+ " accountattributefilter AS ( {PAYMENTIN_REPORT_ACCOUNT_FILTER} ), "
			+ " paymentattributefilter AS ( {PAYMENTIN_ATTRIBUTE_FILTER} ), "
			+ " selectedid AS (  {PAYMENTIN_REPORT_FILTER}  ) " //ORDER BY  payin.updatedon DESC offset @Offset rows  FETCH next @rows rows only
			+ " SELECT   Count(payin.Id)              AS TotalRows "
			+ " FROM      paymentin payin "
			+ " JOIN      selectedid pq "
			+ " ON        pq.resourceid = payin.id "
			+ " JOIN      paymentinattribute pina "
			+ " ON        payin.id = pina.id "
			+ " JOIN      paymentcompliancestatusenum pcse "
			+ " ON        payin.compliancestatus = pcse.id "
			+ " JOIN      account acc "
			+ " ON        payin.accountid = acc.id "
			+ " JOIN      contact con "
			+ " ON        payin.contactid = con.id "
			+ " JOIN      organization org "
			+ " ON        org.id = acc.organizationid "
			+ " LEFT JOIN eventservicelog esl ON"
			+ " esl.EntityID=payin.ID"
			+ " AND esl.ServiceType = 11"
			+ " LEFT JOIN [user] us "
			+ " ON        us.id = pq.createdby"),
	
	GET_PAYMENTIN_QUEUE_PAGINATION("WITH pique AS( "
			+ " SELECT "
			+ " Row_number() OVER(ORDER BY payin.id DESC) AS rownum, "
			+ " payin.id AS paymentinid, "
			+ " payin.accountid AS accountid, "
			+ " payin.contactid AS contactid, "
			+ " payin.compliancestatus AS compliancestatus, "
			+ " payin.deleted AS deleted, "
			+ " payin.tradecontractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS client, "
			+ " payinattri.transactiondate AS [Date], "
			+ " acc.[type] AS [type], "
			+ "                case when  acc.[type]='PFX' then con.NAME "
			+ "                when acc.[type]='CFX' then acc.NAME "
			+ "                END as clientname, "
			+ " payinattri.transactioncurrency AS sell, "
			+ " payinattri.transactionamount AS amount, "
			+ " payinattri.paymentmethod AS [method], "
			+ " ur.id AS resourceid, "
			+ " us.ssouserid AS lockedby, "
			+ " org.NAME AS organization, "
			+ " ctr.code AS country, "
			+ " pcse.status AS overallstatus, "
			+ " acc.WatchlistStatus AS watchliststatus, "
			+ " payin.BlacklistStatus AS blackliststatus, "
			+ " payin.FraugsterStatus AS fraugsterstatus, "
			+ " payin.SanctionStatus AS sanctionstatus, "
			+ " payin.CustomCheckStatus AS CustomCheckStatus "
			+ " FROM "
			+ " paymentin payIn "
			+ " JOIN paymentinattribute payInAttri ON payin.id = payinattri.id "
			+ " JOIN account acc ON payin.accountid = acc.id "
			+ " JOIN contact con ON payin.contactid = con.id "
			+ " JOIN paymentcompliancestatusenum pcse ON payin.compliancestatus = pcse.id "
			+ " LEFT JOIN Country ctr ON payInAttri.countryofpayment = ctr.id "
			+ " JOIN organization org ON org.id = acc.organizationid "
			+ " LEFT JOIN userresourcelock ur ON payin.id = ur.resourceid "
			+ " AND ur.resourcetype = 'PAYMENT_IN' "
			+ " LEFT JOIN [user] us ON us.id = ur.createdby "
			+ " WHERE "
			+ " payinattri.transactiondate > DATEADD(day,-"+SearchCriteriaUtil.getDefaultQueueRecordSize()+",GETDATE()) "
			+ " and pcse.status='HOLD' "
			+ " AND payIn.deleted = 0 "
			+ " AND ur.lockreleasedon IS NULL "
			+ " "
			+ " "
			+ " ) "
			+ " SELECT *, (SELECT max(rownum) FROM pique) AS 'TotalRows' "
			+ " FROM "
			+ " pique "
			+ " WHERE "
			+ " rownum >= ? "
			+ " AND rownum <= ?"),
	
	GET_PAYMENTIN_QUEUE_COUNT("SELECT COUNT(*) as count FROM PaymentIn WHERE complianceStatus<>1"),
	
	GET_PAYMENTIN_QUEUE_WITHOUT_PAGINATION("SELECT Count(*) AS count from( SELECT * FROM "
			+ " ( SELECT payin.id AS paymentinid,payin.accountid  AS accountid,payin.contactid AS contactid, "
			+ " payin.compliancestatus AS compliancestatus,payin.deleted AS deleted,payin.TradeContractNumber AS Transactions, "
			+ " acc.tradeaccountnumber AS client,payinattri.transactiondate  AS [Date], "
			+ " con.NAME  AS clientname,acc.type AS type,payinattri.TransactionCurrency AS sell, "
			+ " payinattri.TransactionAmount AS amount,payinattri.paymentMethod AS method, "
			+ " ur.id AS resourceid,(SELECT SSOUserId FROM [User] where id=ur.createdby) AS lockedby, "
			+ " (SELECT NAME FROM organization WHERE  id = acc.organizationid) AS organization, "
			+ " ( SELECT code AS country FROM   country WHERE  id = "
			+ " ( SELECT CountryOfPayment FROM   paymentinattribute pa WHERE  pa.id = payin.id)) AS country, "
			+ " ( SELECT status FROM   paymentcompliancestatusenum WHERE  id = "
			+ " ( SELECT compliancestatus FROM   paymentin pyi WHERE  payin.id = pyi.id)) AS overallstatus , "
			+ " ( SELECT Count(*) FROM   contactwatchlist cw WHERE  cw.contact IN (select id from Contact where AccountId=payin.Accountid)) AS watchliststatus, "
			+ " ( SELECT TOP 1 status FROM eventservicelog esl  WHERE esl.servicetype = "
			+ " ( SELECT id FROM compliance_servicetypeenum  WHERE  code = 'BLACKLIST') AND payin.id = "
			+ "                (select top 1 paymentinid from event where id in (esl.eventid) order by id desc))AS blackliststatus, "
			+ " ( SELECT TOP 1 status  FROM     eventservicelog esl WHERE esl.servicetype = "
			+ " ( SELECT id FROM   compliance_servicetypeenum WHERE  code = 'FRAUGSTER') AND payin.id = (select top 1 paymentinid from event where id in (esl.eventid) order by id desc)) "
			+ "                 AS fraugsterstatus, "
			+ " ( SELECT TOP 1 status  FROM     eventservicelog esl  WHERE    esl.servicetype = "
			+ " ( SELECT id FROM   compliance_servicetypeenum WHERE  code = 'SANCTION') AND payin.id = (select top 1 paymentinid from event where id in (esl.eventid) order by id desc)) "
			+ "                 AS sanctionstatus, "
			+ " ( SELECT TOP 1 status  FROM     eventservicelog esl  WHERE    esl.servicetype = "
			+ " ( SELECT id FROM   compliance_servicetypeenum WHERE  code = 'VELOCITYCHECK') AND payin.id = "
			+ "                         (select top 1 paymentinid from event where id in (esl.eventid) order by id desc)) AS CustomCheckStatus "
			+ " FROM paymentIn payIn "
			+ " JOIN paymentinattribute payInAttri ON payin.id = payinattri.id "
			+ " JOIN account acc ON payin.accountid = acc.id "
			+ " JOIN contact con ON payin.contactid = con.id "
			+ " LEFT JOIN userresourcelock ur ON payin.id = ur.resourceid AND ur.ResourceType = 'PAYMENT_IN' "
			+ " AND ur.lockreleasedon IS NULL) AS pqueue "
			+ " where compliancestatus <> 1 AND deleted <> 1) AS pqueue2"),
	
	
	GET_PAYMENTIN_QUEUE_INNER_QUERY("SELECT "
			+ " Row_number() OVER( "
			+ " ORDER BY "
			+ " paymentinid desc "
			+ " ) AS rownum, "
			+ " pqueue2.* "
			+ "from "
			+ " ( "
			+ " SELECT "
			+ " pqueue.* "
			+ " FROM "
			+ " ( "
			+ " select "
			+ " payin.id AS paymentinid, "
			+ " payin.accountid AS accountid, "
			+ " payin.contactid AS contactid, "
			+ " payin.compliancestatus AS compliancestatus, "
			+ " payin.deleted AS deleted, "
			+ " payin.tradecontractnumber AS Transactions, "
			+ " acc.tradeaccountnumber AS client, "
			+ " payin.transactiondate AS [Date], "
			+ " case "
			+ " when acc.[type] = 2 then 'CFX' "
			+ " when acc.[type] = 1 then 'PFX' "
			+ " END as [type], "
			+ " case "
			+ " when acc.[type] = 2 then con.NAME "
			+ " when acc.[type] = 1 then acc.NAME "
			+ " END as clientname, "
			+ " payinattri.vtransactioncurrency AS sell, "
			+ " payinattri.vtransactionamount AS amount, "
			+ " payinattri.vpaymentmethod AS [method], "
			+ " ur.id AS resourceid, "
			+ " us.ssouserid AS lockedby, "
			+ " org.NAME AS organization, "
			+ " payInAttri.vcountryofpayment AS country, "
			+ " ctr.DisplayName AS CountryDisplayName, "
			+ " pcse.status AS overallstatus, "
			+ " acc.WatchlistStatus AS watchliststatus, "
			+ " payin.BlacklistStatus AS blackliststatus, "
			+ " payin.FraugsterStatus AS fraugsterstatus, "
			+ " payin.SanctionStatus AS sanctionstatus, "
			+ " payin.CustomCheckStatus AS CustomCheckStatus "
			+ " FROM "
			+ " paymentin payIn "
			+ " JOIN paymentinattribute payInAttri ON "
			+ " payin.id = payinattri.id "
			+ " JOIN account acc ON "
			+ " payin.accountid = acc.id "
			+ " JOIN contact con ON "
			+ " payin.contactid = con.id "
			+ " JOIN paymentcompliancestatusenum pcse ON "
			+ " payin.compliancestatus = pcse.id "
			+ " LEFT JOIN Country ctr ON "
			+ " payInAttri.vcountryofpayment = ctr.code "
			+ " JOIN organization org ON "
			+ " org.id = acc.organizationid "
			+ " LEFT JOIN userresourcelock ur ON "
			+ " payin.id = ur.resourceid "
			+ " AND ur.resourcetype = 2  AND ur.lockreleasedon IS NULL "
			+ " LEFT JOIN [user] us ON "
			+ " us.id = ur.createdby "
			+ " WHERE "
			+ " pcse.status='HOLD' "
			+ " AND payIn.deleted = 0 "
			+ " "
			+ " ) As pqueue) As pqueue2"),
	
	GET_PAYMENTIN_QUEUE_PAGINATION_WITH_CRITERIA(" WITH pique  "
			+ "AS "
			+ "( {INNER_QUERY} ) "
			+ "   SELECT *, (SELECT max(rownum) FROM pique) AS 'TotalRows' "
			+ " FROM pique WHERE rownum >= ? AND rownum <= ? "),
	
	GET_PAYMENT_IN_DETAILS("SELECT pyi.id, "
			+ " pyi.accountid, "
			+ " pyi.contactid, "
			+ " org.NAME AS Organisation, pyi.isOnQueue,"
			+ " pyi.TradeContractNumber, "
			+ " pyi.deleted, "
			+ " pyi.Updatedon, "
			+"                  pyi.initialStatus, "
			+ " (SELECT status "
			+ " FROM   paymentcompliancestatusenum "
			+ " WHERE  id = pyi.compliancestatus)   AS PaymentInStaus, "
			+ " pia.vPaymentMethod, "
			+ " pia.vThirdPartyPayment, "
			+ " pia.vDebitorName, "
			+ " pia.attributes AS PaymentInAttributes, "
			+ " pia.vCountryOfPayment AS Country, "
			+ " coun.displayName AS CountryOfFundFullName, "
			+ " pia.vTransactionAmount AS Amount, "
			+ " pia.vTransactionCurrency AS SellCurrency, "
			+ " pyi.TransactionDate AS DateOfPayment, "
			+ " co.NAME AS ContactName, "
			+ " acc.compliancedoneon AS RegComplete, "
			+" pyi.IntuitionClientRiskLevel AS IntuitionRiskLevel, " //AT-4187
			+" acc.AccountTMFlag, "
			+" acc.Version as AccountVersion, " 
			+ " co.createdon AS RegIn, "
			+ " (SELECT status "
			+ " FROM contactcompliancestatusenum "
			+ " WHERE  id = co.compliancestatus) AS ContactComplianceStatus, "
			+ " co.tradecontactid AS TradeContactId, "
			+ " pyi.tradepaymentid, "
			+ " coa.attributes AS contactAttrib, "
			+ " (SELECT status "
			+ " FROM accountcompliancestatusenum "
			+ " WHERE id = acc.compliancestatus)   AS AccComplianceStatus, "
			+ " acc.crmaccountid, "
			+ " acc.tradeaccountnumber, "
			+ " accAttrib.attributes AS AccountAttrib, "
			+ " co.crmcontactid, "
			+ " ur.id AS userResourceLockId, "
			+ " (SELECT SSOUserId FROM [User] where id=ur.createdby) AS LockedBy, "
			+ " esl.id AS EventServiceLogId, "
			+ " esl.entityid, "
			+ " (SELECT TOP 1 code "
			+ " FROM compliance_servicetypeenum "
			+ " WHERE  id = esl.servicetype) AS ServiceType, "
			+ " esl.entitytype, "
			+ " esl.eventid, "
			+ " esl.summary, "
			+ " JSON_VALUE(esl.ProviderResponse, '$.Action') AS intuitionCurrentAction, " //AT-4962
			+ " esl.status AS EventServiceStatus, "
			+ " (SELECT SSOUserId FROM [User] where id=esl.updatedby) AS EventServiceUpdatedBy, "
			+ " esl.updatedon AS EventServiceUpdatedOn, "
			+ "     JSON_VALUE(pia.ATTRIBUTES, '$.trade.third_party_payment') AS IsThirdParty,  "
			+ "     JSON_VALUE(pia.ATTRIBUTES, '$.trade.customer_legal_entity') AS LegalEntity, "
			+ "              co.POIExists AS poiExists " //AT-3450
			+ " FROM (SELECT pyi.* "
			+ " FROM paymentin pyi "
			+ " WHERE  id = ?) AS pyi "
			+ " LEFT JOIN organization org "
			+ " ON pyi.organizationid = org.id "
			+ " LEFT JOIN paymentinattribute pia "
			+ " ON pia.id = pyi.id "
			+ " LEFT JOIN contact co "
			+ " ON co.id = pyi.contactid "
			+ " LEFT JOIN contactattribute coa "
			+ " ON coa.id = pyi.contactid "
			+ " LEFT JOIN account acc "
			+ " ON acc.id = pyi.accountid "
			+ " LEFT JOIN accountattribute accAttrib "
			+ " ON accAttrib.id = pyi.accountid "
			+ "                 LEFT JOIN country coun"
			+ "                        ON coun.code = pia.vCountryOfPayment"
			+ " LEFT JOIN eventservicelog esl "
			+ " ON esl.id IN (SELECT ESL.id AS logid "
			+ " FROM [eventservicelog] ESL "
			+ " LEFT JOIN event EV "
			+ " ON ESL.eventid = EV.id "
			+ " AND EV.paymentoutid IS NULL "
			+ " WHERE EV.paymentinid = pyi.id) "
			+ " LEFT JOIN userresourcelock ur "
			+ " ON pyi.id = ur.resourceid "
			+ " AND ur.lockreleasedon IS NULL "
			+ " AND ur.resourcetype = 2 "
			+ " ORDER BY esl.servicetype, "
			+ " ESL.updatedon DESC"),
	
	GET_PAYMENTIN_STATUS_WITH_OHTER_STATUS("SELECT PCSE.Status, PYI.Id FROM PaymentIn PYI RIGHT JOIN PaymentComplianceStatusEnum PCSE ON PYI.ComplianceStatus = PCSE.Id and PYI.Id = ?"), 
	
	GET_PAYMENTIN_STATUS_REASON("SELECT    sur.Reason , "
			+ "          Isnull(psr.paymentinid,-1) AS PaymentinId "
			+ "FROM      statusupdatereason sur "
			+ "LEFT JOIN paymentinstatusreason psr "
			+ "ON        psr.statusupdatereasonid=sur.id "
			+ "AND       psr.paymentinid=? "
			+ "WHERE     sur.module IN ('PAYMENT_IN','ALL') order by sur.Reason"), 
	

	GET_ACTIVITY_LOGS_OF_PAYMENT_IN("WITH SELECTedID AS ( "
			+ "SELECT "
			+ "( "
			+ " select "
			+ " SSOUserId "
			+ " from "
			+ " [user] "
			+ " where "
			+ " ID = pal.ActivityBy "
			+ " ) As [User], "
			+ " pal.CreatedOn, "
			+ " pald.Log As [Activity], "
			+ " ate.module + ' ' + ate.type As [ActivityType], "
			+ " pal.Comments AS [Comment] "
			+ " "
			+ "FROM "
			+ " PaymentInActivityLog pal "
			+ "JOIN PaymentInActivityLogDetail pald ON "
			+ " pal.id = pald.ActivityId "
			+ " AND pal.paymentInId = ? "
			+ "JOIN ActivityTypeEnum ate ON "
			+ " pald.ActivityType = ate.id ) "
			+ "SELECT * FROM SELECTedID "
			+ "ORDER BY "
			+ " SELECTedID.CreatedOn desc "),
	
	GET_PAYMENTIN_QUEUE_FOR_PAGINATION_FILTER("SELECT rc.* "
			+ "FROM   ( {INNER_QUERY} "
			+ "   ) as pqueue2     ) AS rc "
			+ "WHERE  rownum in (?,?,?,?) "),
	
	GET_PAGINATION_DETAILS("SELECT * from( SELECT   Row_number() OVER ( ORDER BY paymentinid DESC) AS rownum, pqueue.* FROM "
			+ " ( SELECT payin.id AS paymentinid,payin.accountid  AS accountid,payin.contactid AS contactid, "
			+ " payin.compliancestatus AS compliancestatus,payin.deleted AS deleted,payin.TradeContractNumber AS Transactions, "
			+ " acc.tradeaccountnumber AS client,payinattri.transactiondate  AS [Date], "
			+ " con.NAME  AS clientname,acc.type AS type,payinattri.TransactionCurrency AS sell, "
			+ " payinattri.TransactionAmount AS amount,payinattri.paymentMethod AS method, "
			+ " (SELECT NAME FROM   organization WHERE  id = acc.organizationid) AS organization, "
			+ " ( SELECT code AS country FROM   country WHERE  id = "
			+ " ( SELECT CountryOfPayment FROM   paymentinattribute pa WHERE  pa.id = payin.id)) AS country, "
			+ " ( SELECT status FROM   paymentcompliancestatusenum WHERE  id = "
			+ " ( SELECT compliancestatus FROM   paymentin pyi WHERE  payin.id = pyi.id)) AS overallstatus , "
			+ " ( SELECT Count(*) FROM   contactwatchlist cw WHERE  cw.contact IN (select id from Contact where AccountId=payin.Accountid)) AS watchliststatus, "
			+ " ( SELECT TOP 1 status FROM eventservicelog esl  WHERE esl.servicetype = "
			+ " ( SELECT id FROM compliance_servicetypeenum  WHERE  code = 'BLACKLIST') AND payin.id = "
			+ "                         (select top 1 paymentinid from event where id in (esl.eventid) order by id desc))AS blackliststatus, "
			+ " ( SELECT TOP 1 status  FROM     eventservicelog esl WHERE esl.servicetype = "
			+ " ( SELECT id FROM   compliance_servicetypeenum WHERE  code = 'FRAUGSTER') AND payin.id = "
			+ "                         (select top 1 paymentinid from event where id in (esl.eventid) order by id desc)) AS fraugsterstatus, "
			+ " ( SELECT TOP 1 status  FROM     eventservicelog esl  WHERE    esl.servicetype = "
			+ " ( SELECT id FROM   compliance_servicetypeenum WHERE  code = 'SANCTION') AND payin.id = "
			+ "                         (select top 1 paymentinid from event where id in (esl.eventid) order by id desc)) AS sanctionstatus "
			+ " "
			+ " FROM paymentIn payIn "
			+ " JOIN paymentinattribute payInAttri ON payin.id = payinattri.id "
			+ " JOIN account acc ON payin.accountid = acc.id "
			+ " JOIN contact con ON payin.contactid = con.id "
			+ " ) AS pqueue "
			+ " where compliancestatus <> 1 AND deleted <> 1) AS pqueue2 "
			+ " WHERE  rownum in (?,?,?,?)"), 
	
	INSERT_PAYMENT_IN_REASON("INSERT INTO [PaymentInStatusReason]([PaymentInID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn]) VALUES(?, (SELECT Id FROM StatusUpdateReason WHERE Module IN ('PAYMENT_IN','ALL') AND Reason=?), (select Id from [user] where SSOUserID=?), ?, (select Id from [user] where SSOUserID=?), ?)"), 
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG("INSERT INTO [PaymentInActivityLog] (PaymentInID, ActivityBy, [Timestamp], Comments, CreatedBy, CreatedOn, UserLockID) "
			+ "VALUES(?, (select Id from [user] where SSOUserID=?), "
			+ " ?, ?, (select Id from [user] where SSOUserID=?), ?, "
			+ "(SELECT ID FROM UserResourceLock WHERE ResourceType = 2 AND ResourceID = ? AND LockReleasedOn IS NULL) )"),
	
	INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL("INSERT INTO [PaymentInActivityLogDetail]([ActivityID], [ActivityType], [Log], [CreatedBy], [CreatedOn]) VALUES(?, (SELECT Id FROM ActivityTypeEnum WHERE Module=? and Type=?), ?, (select Id from [user] where SSOUserID=?), ?)"), 
	
	UPDATE_PAYMENT_IN_STATUS("UPDATE [PaymentIn] SET ComplianceStatus=(SELECT Id FROM PaymentComplianceStatusEnum WHERE Status=?),UpdatedBy=(select id  from [user] where SSOUserID=?),UpdatedOn=?, isOnQueue=? WHERE Id=?"), 
	
	GET_PAYMENTIN_ACTIVITY_LOGS_BY_ROWS("WIth SelectID AS( "
			+ " SELECT "
			+ " ( "
			+ " select "
			+ " SSOUserId "
			+ " from "
			+ " [user] "
			+ " where "
			+ " ID = pal.ActivityBy "
			+ " ) AS [User], "
			+ " pal.createdon, "
			+ " pald.log AS [Activity], "
			+ " ate.module + ' ' + ate.type AS [ActivityType], "
			+ " pal.Comments AS [Comment] "
			+ " FROM "
			+ " paymentinactivitylog pal "
			+ " JOIN paymentinactivitylogdetail pald ON "
			+ " pal.id = pald.activityid "
			+ " AND pal.paymentinid = ? "
			+ " JOIN activitytypeenum ate ON "
			+ " pald.activitytype = ate.id ) SELECT "
			+ " * "
			+ "FROM "
			+ " SelectID "
			+ "ORDER BY "
			+ " CreatedOn desc OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"),
	
	GET_VIEWMORE_PAYMENTIN_DETAILS("Select * from "
			+ "(SELECT Row_number() OVER (ORDER BY id DESC) AS rownum, PQueue.* from "
			+ "(select esl.id, "
			+ " esl.eventId, "
			+ " eve.PaymentInID, "
			+ " esl.entitytype, "
			+ " esl.entityid, "
			+ " esl.createdon, "
			+ " esl.updatedon, "
			+ " esl.Summary, "
			+ " (select SSOUserId from [user] where ID=esl.UpdatedBy) As UpdatedBy,esl.status "
			+ " from Event eve Join EventServiceLog esl ON eve.id = esl.eventId "
			+ " where servicetype = (SELECT id "
			+ " FROM compliance_servicetypeenum "
			+ " WHERE code = ? ) "
			+ " and EntityType = ? "
			+ " and eve.PaymentInID = ? "
			+ " and eve.PaymentOutID is null )AS PQueue ) AS PQueue2   WHERE  rownum >= ? "
			+ " AND rownum <= ? ORDER  BY updatedon DESC"),
	
	GET_PRIMARY_CONTACT_NAME("select name from contact  where accountid=? and [primary]=1"),
	
	//added for AT-898 for Sprint 1
	GET_NO_OF_CONTACTS_FOR_ACCOUNT("select count(*) from Contact where AccountID = ?"),
	
	UPDATE_PAYMENT_IN_IS_ON_QUEUE_STATUS("UPDATE [PaymentIn] SET UpdatedBy=(select id  from [user] where SSOUserID=?),"
			+ " UpdatedOn=?, isOnQueue=? WHERE Id=?"),
	
	GET_SERVICE_FAILURE_PAYMENT_IN_COUNT("SELECT "
			+ " Count(CASE WHEN pin.FraugsterStatus IN (8) THEN 1 ELSE NULL END) AS Fraugster_Fail," 
			+ " Count(CASE WHEN pin.SanctionStatus IN (8) THEN 1 ELSE NULL END) AS Sanction_Fail, "
			+ " Count(CASE WHEN pin.BlacklistStatus IN (8) THEN 1 ELSE NULL END) AS Blacklist_Fail, "
			+ " Count(CASE WHEN pin.CustomCheckStatus IN (8) THEN 1 ELSE NULL END) AS CustomCheck_Fail "
			+ "FROM Paymentin pin "
			+ "WHERE"
			+ " pin.ComplianceStatus = 4 "
			+ " AND pin.createdon BETWEEN ? AND ? "
			),
	
	GET_SERVICE_FAILURE_PAYMENT_IN_DETAILS("SELECT "
			+ " pin.id AS paymentID, "
			+ " pin.[TradeContractNumber], "
			+ " pin.TradePaymentID, "
			+ " o.Code AS orgCode, "
			+ " o.id AS orgID, "
			+ " pin.AccountID, "
			+ " pin.ContactID, "
			+ " pin.compliancestatus "
			+ "FROM PaymentIn pin "
			+ "JOIN Organization o ON pin.OrganizationID = o.id "
			+ "JOIN Account ac ON pin.accountid = ac.id "
			+ "WHERE "
			+ " pin.ComplianceStatus = 4 "
			+ " AND (pin.fraugsterstatus IN(8) "
			+ " OR pin.SanctionStatus IN(8) "
			+ " OR pin.BlacklistStatus IN(8) "
			+ " OR pin.CustomCheckStatus IN(8)) "
			+ " AND pin.createdon BETWEEN ? AND ? "),
	
	UPDATE_CONTACT_POI_EXISTS_FLAG("update Contact set POIExists = 1 where CRMContactID = ?");//AT-3450
	
	private String query;

	PaymentInQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}

}