package com.currenciesdirect.gtg.compliance.dbport.report;

@SuppressWarnings("squid:S1192")
public enum RegistrationReportQueryConstant {
	
	
	REGISTRATION_REPORT_CONTACT_FILTER( "SELECT B.AccountId , A.Id ContactId, ROW_NUMBER() OVER (PARTITION BY B.AccountId ORDER BY B.UpdatedOn) RNum "
			+ " FROM ContactAttribute A JOIN Contact B ON A.Id = B.ID {REGISTRATION_REPORT_COUNTRY_OF_RESIDENCE_FILTER} {WATCHLIST_CATEGORY_FILTER} "),
	
	REGISTRATION_REPORT_ACCOUNT_FILTER( "SELECT A.Id AccountId "
			+ " FROM AccountAttribute A JOIN ACCOUNT B ON A.id = B.id "),
	
	REGISTRATION_REPORT_FILTER( " SELECT A.Type,A.ResourceType,A.ResourceId, A.ContactId, A.AccountId, A.UpdatedOn,  "
			+ "ROW_NUMBER() over (PARTITION BY CASE WHEN a.Type = 2 THEN a.contactId ELSE a.accountid END ORDER BY a.updatedon DESC) RowNum " 
			+ " FROM vCustomer A "
			+ " {CONTACT_FILTER_JOIN} {ACCOUNT_FILTER_JOIN} {ORG_TABLE_JOIN} {USER_FILTER_JOIN} {LEGAL_ENTITY_JOIN} {DEVICE_ID_JOIN} {ONFIDO_JOIN}"),
			
	CONTACT_FILTER_JOIN(" JOIN ContactAttributeFilter CF ON A.ContactId = CF.ContactId"),//AND CF.Rnum=1 
	
	ACCOUNT_FILTER_JOIN(" JOIN AccountAttributeFilter AF ON A.AccountId = AF.AccountId "),
	
	CONTACT_FILTER_LEFT_JOIN(" LEFT JOIN ContactAttributeFilter CF ON A.ContactId = CF.ContactId"),//AND CF.Rnum=1
	
	ACCOUNT_FILTER_LEFT_JOIN(" LEFT JOIN AccountAttributeFilter AF ON A.AccountId = AF.AccountId "),
	
	REPORT_FILTER_NULL_CHECK(" ((CF.ContactId IS NOT NULL AND A.Type=2) OR (AF.AccountId IS NOT NULL AND A.Type IN (1,3))) "),
	
	ORG_TABLE_JOIN("JOIN ACCOUNT acc ON A.AccountId = acc.id "	
			+ " JOIN organization org ON acc.organizationid = org.id"),
	
	LEGEL_ENTITY_TABLE_JOIN("JOIN ACCOUNT ac ON A.AccountId = ac.id JOIN LegalEntity le ON ac.LegalEntityID = le.ID "),
	
	USER_FILTER_JOIN("JOIN Userresourcelock ul ON (ul.Resourceid = A.ContactId AND ul.resourcetype=3) OR (ul.Resourceid = A.Accountid AND ul.resourcetype=4)"
			+ " JOIN [USER] usr ON usr.id = ul.createdby"),
	
	WATCHLIST_CATEGORY_FILTER(" LEFT JOIN ContactWatchList CL ON CL.contact=B.id LEFT JOIN watchlist W ON CL.reason=w.id AND w.category IN(?) "),
	
	WATCHLIST_FILTER(" EXISTS( SELECT 1 FROM ContactWatchList CL WHERE Reason IN(?) AND A.contactid = CL.contact ) "),
	REGISTRATION_REPORT_COUNTRY_OF_RESIDENCE_FILTER("JOIN Country coun ON coun.id = b.country"),
	
	GET_REGISTRATION_REPORT("DECLARE @Offset int = ?, @Rows int = ?; DECLARE @tmpRows int = @Rows*10;"
			+ " WITH "
			+ "ContactAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_CONTACT_FILTER} ),"
			+ "AccountAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_ACCOUNT_FILTER} ),"
			+ "SelectedIds AS "
			+ "( {REGISTRATION_QUEUE_FILTER} "
			+ "   ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE} OFFSET @Offset ROWS FETCH NEXT @tmpRows ROWS ONLY), "//
			+ " SelectedId AS ( "
			+ "select TOP (@Rows) * "
			+ "from SelectedIds a "
			+ " WHERE RowNum = 1  ORDER BY  {SORT_FIELD_NAME} {ORDER_TYPE} "//here
			+ ") "
			+ " SELECT "
			+ "    c.Id                     AS contactid,"
			+ " CASE acc.[type] WHEN 2 THEN c.compliancestatus ELSE acc.compliancestatus END AS compliancestatus, "
			//+ "				c.compliancestatus, "
			
			+ " c.accountid, "
			+ "     CASE acc.[type] WHEN 2 THEN  c.NAME ELSE acc.NAME END   AS contactname, "
			+ " acc.tradeaccountnumber AS TradeAccountNumber, "
			+ "    c.updatedon              AS registeredon, "
			+ "    acc.crmaccountid         AS ACSFID, "
			+ "    accattrib.vsellcurrency, "
			+ "    accattrib.vbuycurrency, "
			+ "    accattrib.vsource, "
			+ "    accattrib.vtransactionvalue, "
			+ " org.NAME AS organization, "
			+ "    acc.LegalEntity, "
			+ "    acc.[type]  AS Type, "
			+ "    CASE acc.[type] WHEN 2 THEN c.eidstatus  ELSE acc.eidstatus END AS eidstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.blackliststatus ELSE acc.blackliststatus END AS blackliststatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.fraugsterstatus ELSE acc.fraugsterstatus END AS fraugsterstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.sanctionstatus ELSE acc.sanctionstatus END AS sanctionstatus, "
			+ " CASE acc.[type] WHEN 2 THEN c.customCheckStatus ELSE 9 END AS customCheckStatus, "
			+ "    CASE acc.[type] WHEN 2 THEN contactstatusEnum.Status ELSE accountstatusEnum.Status END AS status, "
			+ "    contactstatusEnum.status AS contactStatus, "
			+ " ur.id AS userresourceilockid, "
			+ " usr.ssouserid AS lockedby, "
			+ "    ur.createdon             AS UserResourceCreatedOn, "
			+ "    ur.workflowtime          AS UserResourceWorkflowTime, "
			+ "    ur.resourcetype          AS UserResourceEntityType, "
			+ "    SI.UpdatedOn AS UpdatedOn, "
			+ "    coun.DisplayName         AS CountryFullName, "
			+ "    coun.Code                AS CountryCode, "
			+ "    acc.ComplianceDoneOn     AS registeredDate, "
			+ "    CASE acc.[type] WHEN 2 THEN c.version "
			+ "    ELSE acc.version END AS version "
			+ "FROM   contact c "
			+ "JOIN SelectedId Si ON c.Id = Si.ContactId "
			+ "JOIN contactattribute conattrib ON c.id = conattrib.id "
			+ "JOIN account acc ON acc.id = c.accountid "
			+ " {JOIN_LEGAL_ENTITY} " //Add for AT-3269
			+ "JOIN accountattribute accattrib ON c.accountid = accattrib.id "
			+ "JOIN organization org ON acc.organizationid = org.id "
			+ "JOIN contactcompliancestatusenum contactstatusEnum ON c.compliancestatus = contactstatusEnum.id "
			+ "JOIN accountcompliancestatusenum accountstatusEnum ON acc.compliancestatus = accountstatusEnum.id "
			+ "  JOIN      country coun ON c.country = coun.id "
			+ "LEFT JOIN userresourcelock ur ON  ur.resourceId = Si.ResourceId AND ur.ResourceType = si.ResourceType "
			+ " AND ur.lockreleasedon IS NULL "
			+ "LEFT JOIN [user] usr ON usr.id = ur.createdby WHERE c.Deleted = 0 OR c.Deleted IS NULL"),
			//+ " ORDER BY  Si.UpdatedOn DESC "),
	
	GET_REGISTRATION_EXCEL_REPORT(" WITH "
			+ "ContactAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_CONTACT_FILTER} ),"
			+ "AccountAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_ACCOUNT_FILTER} ),"
			+ "SelectedIds AS "
			+ "( {REGISTRATION_QUEUE_FILTER} ), "//
			+ " SelectedId AS ( "
			+ "select TOP 100 PERCENT * "
			+ "from SelectedIds a WHERE RowNum = 1 "
			+ "ORDER BY ROW_NUMBER() over (PARTITION BY CASE WHEN a.Type = 2 THEN a.contactId ELSE a.accountid END "
			+ " ORDER BY a.updatedon DESC),a.updatedon DESC "
			+ ") "
			+ " SELECT "
			+ "    c.Id                     AS contactid, "
			+ " c.compliancestatus, "
			+ " c.accountid, "
			+ "     CASE acc.[type] WHEN 2 THEN c.NAME ELSE acc.NAME END   AS contactname, "
			+ " acc.tradeaccountnumber AS TradeAccountNumber, "
			+ "    c.updatedon              AS registeredon, "
			+ "    acc.crmaccountid         AS ACSFID, "
			+ "    accattrib.vsellcurrency, "
			+ "    accattrib.vbuycurrency, "
			+ "    accattrib.vsource, "
			+ "    accattrib.vtransactionvalue, "
			+ " org.NAME AS organization, "
			+ " acc.LegalEntity, "
			+ "    CASE acc.[type] WHEN 2 THEN 'PFX' WHEN 1 THEN 'CFX' ELSE 'CFX(Etailer)' END AS Type, "
			+ "    CASE acc.[type] WHEN 2 THEN c.eidstatus  ELSE acc.eidstatus END AS eidstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.blackliststatus ELSE acc.blackliststatus END AS blackliststatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.fraugsterstatus ELSE acc.fraugsterstatus END AS fraugsterstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.sanctionstatus ELSE acc.sanctionstatus END AS sanctionstatus,"
			+ "    CASE acc.[type] WHEN 2 THEN c.customCheckStatus ELSE 9 END AS customCheckStatus, "// for AT-3011
			+ "    CASE acc.[type] WHEN 2 THEN contactstatusEnum.Status ELSE accountstatusEnum.Status END AS status, "
			+ "    contactstatusEnum.status AS contactStatus, "
			+ "             ur.id AS userresourceilockid, "
			+ "             usr.ssouserid AS lockedby, "
			+ "    ur.createdon             AS UserResourceCreatedOn, "
			+ "    ur.workflowtime          AS UserResourceWorkflowTime, "
			+ "    ur.resourcetype          AS UserResourceEntityType, "
			+ "    SI.UpdatedOn AS UpdatedOn, "
			+ "    coun.DisplayName         AS CountryFullName, "
			+ "    coun.Code                AS CountryCode, "
			+ "    acc.ComplianceDoneOn     AS registeredDate, "
			+ "    CASE acc.[type] WHEN 2 THEN c.version "
			+ "    ELSE acc.version END AS version "
			+ "FROM   contact c "
			+ "JOIN SelectedId Si ON c.Id = Si.ContactId "
			+ "JOIN contactattribute conattrib ON c.id = conattrib.id "
			+ "JOIN account acc ON acc.id = c.accountid "
			+ " {JOIN_LEGAL_ENTITY} " //Add for AT-3269 
			+ "JOIN accountattribute accattrib ON c.accountid = accattrib.id "
			+ "JOIN organization org ON acc.organizationid = org.id "
			+ "JOIN contactcompliancestatusenum contactstatusEnum ON c.compliancestatus = contactstatusEnum.id "
			+ "JOIN accountcompliancestatusenum accountstatusEnum ON acc.compliancestatus = accountstatusEnum.id "
			+ "  JOIN      country coun ON c.country = coun.id "
			+ "LEFT JOIN userresourcelock ur ON c.id = ur.resourceid "
			+ "   AND ur.resourcetype = Si.ResourceType "
			+ "             AND ur.lockreleasedon IS NULL "
			+ "LEFT JOIN [user] usr ON usr.id = ur.createdby WHERE c.Deleted = 0 OR c.Deleted IS NULL"
			+ " ORDER BY  Si.UpdatedOn DESC"),
	
	GET_REGISTRATION_REPORT_COUNT(" WITH "
			+ "ContactAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_CONTACT_FILTER} ),"
			+ "AccountAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_ACCOUNT_FILTER} ),"
			+ "SelectedIds AS "
			+ "( {REGISTRATION_QUEUE_FILTER} "
			+ " ), "//ORDER BY A.UpdatedOn DESC OFFSET @Offset ROWS FETCH NEXT @rows ROWS ONLY
			+ " SelectedId AS ( "
			+ "select TOP 100 PERCENT *, "
			+ "ROW_NUMBER() over (PARTITION BY CASE WHEN a.Type = 2 THEN a.contactId ELSE a.accountid END ORDER BY a.updatedon DESC) Rnum " 
			+ "from SelectedIds a "
			+ "ORDER BY ROW_NUMBER() over (PARTITION BY CASE WHEN a.Type = 2 THEN a.contactId ELSE a.accountid END ORDER BY a.updatedon DESC),a.updatedon DESC "
			+ " ) "
			+ " SELECT "
			+ "    Count(1) AS TotalRows "
			+ "FROM   contact c "
			+ "LEFT JOIN SelectedId Si ON c.Id = Si.ContactId "
			+ "LEFT JOIN contactattribute conattrib ON c.id = conattrib.id "
			+ "LEFT JOIN account acc ON acc.id = c.accountid "
			+ " {JOIN_LEGAL_ENTITY} " //Add for AT-3269
			+ "LEFT JOIN accountattribute accattrib ON c.accountid = accattrib.id "
			+ "LEFT JOIN organization org ON acc.organizationid = org.id "
			+ "LEFT JOIN contactcompliancestatusenum contactstatusEnum ON c.compliancestatus = contactstatusEnum.id "
			+ "LEFT JOIN userresourcelock ur ON ur.resourceId = Si.ResourceId AND ur.ResourceType = si.ResourceType "
			+ "   AND ur.lockreleasedon IS NULL "
			+ "LEFT JOIN [user] usr ON usr.id = ur.createdby"
			+ " WHERE Si.Rnum = 1 AND (c.Deleted = 0 OR c.Deleted IS NULL)"),

	GET_REGISTRATION_REPORT_WITHOUT_PAGINATION( "SELECT Count(*) AS count "
			+ "FROM   ( "
			+ "                 SELECT    ur.id AS userresourceilockid, "
			+ "                           ( "
			+ "                                  SELECT ssouserid "
			+ "                                  FROM   [User] "
			+ "                                  WHERE  id=ur.createdby) AS lockedby, "
			+ "                           c.id                           AS contactid, "
			+ "                           ( "
			+ "                                  SELECT status "
			+ "                                  FROM   contactcompliancestatusenum "
			+ "                                  WHERE  id = c.compliancestatus) AS contactstatus, "
			+ "                           c.accountid, "
			+ "                           c.NAME                 AS contactname, "
			+ "                             acc.CRMAccountID AS ACSFID, "
			+ "                             accattrib.Attributes AS AccountAttributes, "
			+ "                             conattrib.Attributes    AS ContactAttributes, "
			+ "                           acc.tradeaccountnumber AS tradeaccountnumber, "
			+ "                           acc.createdon          AS registeredon, "
			+ "                           ur.userid              AS username, "
			+ "                           accattrib.type, "
			+ "                           accattrib.sellcurrency, "
			+ "                           accattrib.buycurrency, "
			+ "                           accattrib.source, "
			+ "                           accattrib.transactionvalue, "
			+ "                           ( "
			+ "                                  SELECT ',' "
			+ "                                        + ( "
			+ "                                                SELECT reason "
			+ "                                                FROM   watchlist "
			+ "                                                WHERE  id = st1.reason) AS [text()] "
			+ "                                  FROM   contactwatchlist st1 "
			+ "                                  WHERE  st1.contact = c.id FOR xml path ('')) AS watchlist, "
			+ "                           ( "
			+ "                                  SELECT NAME "
			+ "                                  FROM   organization "
			+ "                                  WHERE  id = acc.organizationid) AS organization, "
			+ "                           ( "
			+ "                                    SELECT TOP 1 "
			+ "                                             status "
			+ "                                    FROM     eventservicelog e "
			+ "                                    WHERE    e.servicetype = "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   compliance_servicetypeenum "
			+ "                                                    WHERE  code = 'KYC') "
			+ "                                    AND      e.entityid = c.id "
			+ "                                    AND      e.entitytype = 'CONTACT' "
			+ "                                    AND      e.eventid IN "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   event "
			+ "                                                    WHERE  paymentinid IS NULL "
			+ "                                                    AND    paymentoutid IS NULL "
			+ "                                                    AND    accountid = acc.id) "
			+ "                                    ORDER BY e.updatedon DESC) AS kycstatus, "
			+ "                           ( "
			+ "                                    SELECT TOP 1 "
			+ "                                             status "
			+ "                                    FROM     eventservicelog e "
			+ "                                    WHERE    e.servicetype = "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   compliance_servicetypeenum "
			+ "                                                    WHERE  code = 'BLACKLIST') "
			+ "                                    AND      e.entityid = c.id "
			+ "                                    AND      e.entitytype = 'CONTACT' "
			+ "                                    AND      e.eventid IN "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   event "
			+ "                                                    WHERE  paymentinid IS NULL "
			+ "                                                    AND    paymentoutid IS NULL "
			+ "                                                    AND    accountid = acc.id) "
			+ "                                    ORDER BY e.updatedon DESC) AS blackliststatus, "
			+ "                           ( "
			+ "                                    SELECT TOP 1 "
			+ "                                             status "
			+ "                                    FROM     eventservicelog e "
			+ "                                    WHERE    e.servicetype = "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   compliance_servicetypeenum "
			+ "                                                    WHERE  code = 'FRAUGSTER') "
			+ "                                    AND      e.entityid = c.id "
			+ "                                    AND      e.entitytype = 'CONTACT' "
			+ "                                    AND      e.eventid IN "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   event "
			+ "                                                    WHERE  paymentinid IS NULL "
			+ "                                                    AND    paymentoutid IS NULL "
			+ "                                                    AND    accountid = acc.id) "
			+ "                                    ORDER BY e.updatedon DESC) AS fraugsterstatus, "
			+ "                           ( "
			+ "                                    SELECT TOP 1 "
			+ "                                             status "
			+ "                                    FROM     eventservicelog e "
			+ "                                    WHERE    e.servicetype = "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   compliance_servicetypeenum "
			+ "                                                    WHERE  code = 'SANCTION') "
			+ "                                    AND      e.entityid = c.id "
			+ "                                    AND      e.entitytype = 'CONTACT' "
			+ "                                    AND      e.eventid IN "
			+ "                                             ( "
			+ "                                                    SELECT id "
			+ "                                                    FROM   event "
			+ "                                                    WHERE  paymentinid IS NULL "
			+ "                                                    AND    paymentoutid IS NULL "
			+ "                                                    AND    accountid = acc.id) "
			+ "                                    ORDER BY e.updatedon DESC) AS sanctionstatus "
			+ "                 FROM   contact c "
			+ "                 LEFT JOIN ContactAttribute conattrib "
			+ "                 ON        c.id = conattrib.id "
			+ "                 LEFT JOIN account acc "
			+ "                 ON        acc.id = c.accountid "
			+ "                 LEFT JOIN accountattribute accattrib "
			+ "                 ON        c.accountid = accattrib.id "
			+ "                 LEFT JOIN userresourcelock ur "
			+ "                 ON        c.id = ur.resourceid "
			+ "                 AND       ur.lockreleasedon IS NULL "
			+ "                 AND       ur.resourcetype = 'CONTACT' "
			+ "                 WHERE     c.compliancestatus IN ( 1, "
			+ "                                                  4, "
			+ "                                                  5 )) AS rc1"),	
	
	
	GET_REG_REPORT_INNER_QUERY("SELECT row_number()  OVER ( ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE}) AS rownum, "
			+ "        rqtemp.* "
			+ "    FROM "
			+ "( "
			+ " select "
			+ " ur.id AS userresourceilockid,  usr.ssouserid AS lockedby, "
			+ " ur.CreatedOn AS UserResourceCreatedOn,    ur.WorkflowTime AS UserResourceWorkflowTime, "
			+ " ur.ResourceType AS UserResourceEntityType,  c.id AS contactid, "
			+ " c.compliancestatus, "
			+ " c.accountid, "
			+ " c.NAME AS contactname, "
			+ " acc.tradeaccountnumber AS TradeAccountNumber ,  "
			+ " c.createdon AS registeredon, "
			+ " acc.CRMAccountID AS ACSFID, "
			+ " conattrib.attributes       AS ContactAttributes, "
			+ " accattrib.sellcurrency,  "
			+ " accattrib.buycurrency, "
			+ " accattrib.source,   accattrib.transactionvalue, "
			+ " (   SELECT ','  +    (  SELECT reason       FROM   watchlist "
			+ " WHERE  id = st1.reason) AS [text()] "
			+ " FROM   contactwatchlist st1 "
			+ " WHERE  st1.contact = c.id      FOR xml path ('')) AS watchlist , "
			+ " org.NAME AS organization,    acc.[type] AS Type, "
			+ " c.EIDStatus AS kycstatus, "
			+ " c.BlacklistStatus AS blackliststatus, "
			+ " c.FraugsterStatus AS fraugsterstatus, "
			+ " c.SanctionStatus AS sanctionstatus,  contactstatusEnum.status AS contactStatus "
			+ " FROM    contact c "
			+ "                                         LEFT JOIN contactattribute conattrib "
			+ "                              ON c.id = conattrib.id "
			+ " JOIN account acc ON "
			+ " acc.id = c.accountid "
			+ " JOIN accountattribute accattrib ON       c.accountid = accattrib.id "
			+ " JOIN organization org ON     acc.organizationid = org.id " 
			+ " JOIN ContactComplianceStatusEnum  contactstatusEnum  ON c.compliancestatus = contactstatusEnum.id "
			+ " LEFT JOIN userresourcelock ur ON   c.id = ur.resourceid "
			+ " AND ur.resourcetype = 'CONTACT'  AND ur.lockreleasedon IS NULL LEFT JOIN [user] usr ON "
			+ " usr.id = ur.createdby        WHERE "
			+ " {PFX_DATETIME_CRITERIA} "
			+ " acc.[type] = 'PFX' "
			+ " UNION ALL SELECT "
			+ " ur.id AS userresourceilockid,   usr.ssouserid AS lockedby, "
			+ " ur.CreatedOn AS UserResourceCreatedOn,    ur.WorkflowTime AS UserResourceWorkflowTime,   ur.ResourceType AS UserResourceEntityType, "
			+ " ( "
			+ " SELECT "
			+ " top 1 c.id "
			+ " FROM "
			+ " Contact c "
			+ " where "
			+ " c.AccountID = acc.id "
			+ " order by "
			+ " c.id "
			+ " ) AS contactid, "
			+ " acc.compliancestatus, "
			+ " acc.id as accountid, "
			+ " acc.NAME AS contactname,  "
			+ " acc.tradeaccountnumber AS TradeAccountNumber , "
			+ " acc.createdon AS registeredon, "
			+ "                          acc.CRMAccountID AS ACSFID, "
			//+ "                          conattrib.attributes     AS ContactAttributes, "
			+ "                          ''    AS ContactAttributes, "
			+ "                          accattrib.sellcurrency, "
			+ "                          accattrib.buycurrency,   accattrib.source, "
			+ "                          accattrib.transactionvalue,  "
			+ "                         (   SELECT ','   +   (  SELECT reason       FROM   watchlist "
			+ "                          WHERE  id = st1.reason) AS [text()] "
			+ "                          FROM   contactwatchlist st1 "
			+ "                         WHERE  st1.contact = acc.id      FOR xml path ('')) AS watchlist ,   org.NAME AS organization, "
			+ "                          acc.[type] AS Type,     acc.EIDStatus AS kycstatus, "
			+ "                          acc.BlacklistStatus AS blackliststatus, "
			+ "                          acc.FraugsterStatus AS fraugsterstatus, "
			+ "                          acc.SanctionStatus AS sanctionstatus ,  contactstatusEnum.status AS contactStatus "
			+ "                      FROM  account acc "
			+ "                      JOIN accountattribute accattrib ON    acc.id = accattrib.id "
			/*+ " 					 JOIN contact c  ON acc.id = c.accountid "
			+ "						 JOIN contactattribute conattrib  ON c.id = conattrib.id "*/
			+ "                      JOIN organization org ON    acc.organizationid = org.id "
			+ "                      JOIN ContactComplianceStatusEnum  contactstatusEnum  ON acc.compliancestatus = contactstatusEnum.id "
			+ "                      LEFT JOIN userresourcelock ur ON      acc.id = ur.resourceid "
			+ "                           AND ur.resourcetype = 'ACCOUNT' AND ur.lockreleasedon IS NULL"
			+ "                      LEFT JOIN [user] usr ON    usr.id = ur.createdby "
			+ "                      WHERE  {CFX_DATETIME_CRITERIA} "
			+ "                           acc.[type] = 'CFX'      "
			+ " "
			+ "                      ) AS rqtemp"),
	
	GET_REG_REPORT_FOR_FILTER("WITH regque "
			+ " AS "
			+ " ( "
			+ " {INNER_QUERY} "
			+ " ) "
			+ " SELECT "
			+ " regque.*, "
			+ " ( SELECT max (rownum) FROM regque) AS 'TotalRows' "
			+ " FROM regque "
			+ " WHERE rownum >= ? "
			+ " AND rownum <= ?"),
	
	GET_REGISTRATION_REPORT_WHOLE_DATA( "select rc.* "
			+ "from( {INNER_QUERY}"
			+ " )as rc"
			),
	
	GET_REGISTRATION_DASHBOARD_BY_LEGALENTITY("SELECT "
			+ " CASE "
			+ " WHEN c.[Type] IN(1,3) THEN COUNT( DISTINCT c.AccountID ) "
			+ " ELSE COUNT( c.contactid ) "
			+ " END AS BUWiseTotal, "
			+ " le.code as legalEntity, "
			+ " CASE "
			+ " WHEN c.[type] IN(1, 3) THEN 'CFX' "
			+ " ELSE 'PFX' "
			+ " END AS [customerType] "
			+ " FROM vInactiveCustomer c "
			+ " JOIN Account acc ON c.accountid = acc.id "
			+ " JOIN LegalEntity le ON acc.LegalEntityID = le.id "
			+ " GROUP BY "
			+ " le.code, "
			+ " C.type"),
	
	GET_REGISTRATION_DASHBOARD_BY_COUNTRY("SELECT "
			+ " CASE "
			+ " WHEN c.[type] IN(1, 3) THEN COUNT(DISTINCT c.AccountID ) "
			+ " ELSE COUNT( c.contactid ) "
			+ " END AS CountryWiseTotal, "
			+ " ctr.displayname, ctr.ShortCode, "
			+ " CASE "
			+ "     WHEN c.[type] IN(1, 3) THEN 'CFX' "
			+ "     ELSE 'PFX' "
			+ " END AS [customerType] "
			+ "FROM "
			+ " vInactiveCustomer c "
			+ "JOIN Contact cn ON cn.id = c.contactid "
			+ "JOIN Country ctr ON cn.country = ctr.id "
			+ "GROUP BY "
			+ " ctr.displayname, ctr.ShortCode, c.[type] "
			+ "order by "
			+ " CountryWiseTotal desc"),
	
	GET_REGISTRATION_DASHBOARD_TIMELINE_SNAP_SHOT("select "
			+ " min( vc.contactid ) AS firstContactId, "
			+ " max( vc.contactid ) AS lastContactId, "
			+ " CASE "
			+ "     WHEN vc.[type] IN(1,3) THEN 'CFX' "
			+ "     ELSE 'PFX' "
			+ " END AS [customerType], "
			+ " CASE "
			+ "     WHEN vc.[type] IN(1,3) THEN DATEDIFF(dd, min( acc.createdon ), CURRENT_TIMESTAMP) "
			+ "     ELSE DATEDIFF(dd, min( c.createdon), CURRENT_TIMESTAMP) "
			+ " END As oldContactAge, "
			+ " CASE "
			+ "     WHEN vc.[type] IN(1,3) THEN DATEDIFF(dd, max( acc.createdon ),CURRENT_TIMESTAMP) "
			+ "     ELSE DATEDIFF(dd,max( c.createdon ),CURRENT_TIMESTAMP) "
			+ " END As newContactAge, "
			+ " CASE "
			+ "     WHEN vc.[type] IN(1,3) THEN Avg( CAST( Datediff( dd, acc.createdon, CURRENT_TIMESTAMP ) AS BIGINT )) "
			+ "     ELSE Avg( CAST( Datediff( dd, c.createdon, CURRENT_TIMESTAMP ) AS BIGINT )) "
			+ " END AS Age "
			+ "FROM "
			+ " vInactiveCustomer vc "
			+ "JOIN account acc ON acc.id = vc.accountid "
			+ "JOIN Contact c ON c.id = vc.contactid "
			+ "group by "
			+ " vc.[type]"),
	
	GET_PAYMENTIN_DASHBOARD_TIMELINE_SNAP_SHOT("SELECT  min(payin.id) AS firstPaymentInId, max(payin.id) AS lastPaymentInId, "
			+ " DATEDIFF(mi, min(payin.transactiondate) , CURRENT_TIMESTAMP) As oldPaymentInAge, "
			+ " DATEDIFF(mi, max(payin.transactiondate) , CURRENT_TIMESTAMP) As newPaymentInAge, "
			+ " Avg (CAST ( (Datediff(mi, payin.transactiondate, CURRENT_TIMESTAMP))  AS BIGINT ) )  AS Age  FROM paymentin payIn "
			+ " JOIN paymentinattribute payInAttri ON payin.id = payinattri.id "
			+ " JOIN paymentcompliancestatusenum pcse ON payin.compliancestatus = pcse.id "
			+ " LEFT JOIN userresourcelock ur ON ur.resourceid = payin.id AND resourcetype = 2 AND ur.lockreleasedon IS NULL"
			+ " WHERE  pcse.status='HOLD' AND payIn.deleted <> 1 AND payIn.isOnQueue=1 "),
	
	GET_PAYMENTOUT_DASHBOARD_TIMELINE_SNAP_SHOT("SELECT min(payOut.id) AS firstPaymentOutId, max(payOut.id) AS lastPaymentOutid, "
			+ " DATEDIFF(mi, min(payOut.transactiondate) , CURRENT_TIMESTAMP) As oldPaymentOutAge, "
			+ " DATEDIFF(mi, max(payOut.transactiondate) , CURRENT_TIMESTAMP) As newPaymentOutAge, "
			+ " Avg (CAST ( (Datediff(mi, payOut.transactiondate, CURRENT_TIMESTAMP)) AS BIGINT ) ) AS Age FROM paymentout payOut "
			+ " JOIN paymentoutattribute payOutAttri ON payOut.id = payOutAttri.id "
			+ " JOIN paymentcompliancestatusenum pcse ON payOut.compliancestatus = pcse.id "
			+ " LEFT JOIN UserResourceLock usr ON usr.ResourceID = payOut.id AND usr.ResourceType=1 and lockreleasedon IS NULL "
			+ " WHERE pcse.status='HOLD' AND payOut.deleted <> 1  AND payOut.isOnQueue=1"),
	
	GET_PAYMENTIN_DASHBOARD_BY_LEGALENTITY("SELECT "
			+ " Count(payin.id) AS records, "
			+ " le.code AS legalEntity "
			+ " FROM "
			+ " account acc JOIN LegalEntity le ON acc.LegalEntityID = le.id "
			+ " JOIN paymentIn payin ON payin.accountid = acc.id "
			+ " LEFT JOIN userresourcelock ur ON ur.resourceid = payin.id AND resourcetype = 2 "
			+ " AND ur.lockreleasedon IS NULL "
			+ " WHERE "
			+ " payin.compliancestatus = 4 AND payin.deleted = 0 AND payin.isOnQueue=1 "
			+ " GROUP BY le.code"),

	GET_PAYMENTOUT_DASHBOARD_BY_LEGALENTITY("SELECT "
			+ " Count(po.id) AS records, "
			+ " le.code AS legalEntity "
			+ " FROM "
			+ " account acc "
			+ " JOIN LegalEntity le ON acc.LegalEntityID = le.id "
			+ " JOIN paymentout po ON po.accountid = acc.id "
			+ " LEFT JOIN UserResourceLock ur ON po.id = ur.resourceid AND Resourcetype=1 AND lockreleasedon IS NULL "
			+ " WHERE "
			+ " po.compliancestatus = 4 AND po.deleted = 0 AND po.isOnQueue=1 "
			+ " GROUP BY le.code "),
	
	GET_CLEARED_RECORD_DETAILS(" select "
			+ " CASE ResourceType "
			+ " WHEN 1 THEN 'PAYMENT_OUT' "
			+ " WHEN 2 THEN 'PAYMENT_IN' "
			+ " WHEN 3 THEN 'CONTACT' "
			+ " WHEN 4 THEN 'ACCOUNT' "
			+ " END AS ResourceType, "
			+ " AVG(datediff(mi,createdOn , WorkflowTime)) As AvgClearingTime, "
			+ "        datediff(hh, ? , getDate() )As TotalHours,  count(WorkflowTime) As ToatalRecords "
			+ " from "
			+ " UserResourceLock ur "
			+ " where "
			+ " WorkflowTime >= ? "
			+ " GROUP BY ResourceType"),
	

	DEVICE_ID_JOIN("JOIN Event e ON A.AccountId = e.AccountID AND e.EventType = 1"  
			+  " JOIN DeviceInfo di ON di.EventID = e.ID"),
	
	ONFIDO_JOIN("JOIN ACCOUNT ac ON A.AccountId = ac.id ");
	
	//***********************************************************************************************/
	private String query;

	RegistrationReportQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}


}
