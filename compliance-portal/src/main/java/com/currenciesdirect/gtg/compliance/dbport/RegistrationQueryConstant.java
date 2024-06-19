package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum RegistrationQueryConstant.
 *
 * @author Rajesh
 */
@SuppressWarnings("squid:S1192")
public enum RegistrationQueryConstant {

	//Query changes since for CFX customer shown on basis of accountid 
	/** Optimized registration queue query */
	
	REGISTRATION_QUEUE_CONTACT_FILTER( "SELECT B.AccountId , A.Id ContactId, ROW_NUMBER() OVER (PARTITION BY B.AccountId ORDER BY B.UpdatedOn) RNum "
			+ " FROM ContactAttribute A JOIN Contact B ON A.Id = B.ID {REGISTRATION_QUEUE_COUNTRY_OF_RESIDENCE_FILTER} {WATCHLIST_CATEGORY_FILTER}"),
	
	REGISTRATION_QUEUE_ACCOUNT_FILTER( "SELECT A.Id AccountId "
			+ " FROM AccountAttribute A JOIN ACCOUNT B ON A.id = B.id " ),
	
	REGISTRATION_QUEUE_FILTER(" SELECT A.Type, A.ResourceType, A.ResourceID,  A.ContactId, A.AccountId, A.UpdatedOn, A.RowNum "
			+ " FROM vInActiveCustomer A "
			+ " {CONTACT_FILTER_JOIN} {ACCOUNT_FILTER_JOIN} {ORG_TABLE_JOIN} {USER_FILTER_JOIN} {LEGAL_ENTITY_JOIN} {DEVICE_ID_JOIN} {ONFIDO_JOIN}"),
			
	CONTACT_FILTER_JOIN(" JOIN ContactAttributeFilter CF ON A.ContactId = CF.ContactId AND CF.Rnum=1 "),
	
	ACCOUNT_FILTER_JOIN(" JOIN AccountAttributeFilter AF ON A.AccountId = AF.AccountId "),
	
    CONTACT_FILTER_LEFT_JOIN(" LEFT JOIN ContactAttributeFilter CF ON A.ContactId = CF.ContactId AND CF.Rnum=1 "),
	
	ACCOUNT_FILTER_LEFT_JOIN(" LEFT JOIN AccountAttributeFilter AF ON A.AccountId = AF.AccountId "),
	
	REPORT_FILTER_NULL_CHECK(" ((CF.ContactId IS NOT NULL AND A.Type=2) OR (AF.AccountId IS NOT NULL AND A.Type IN (1,3))) "),
	
	ORG_TABLE_JOIN("JOIN ACCOUNT acc ON A.AccountId = acc.id "	
			+ " JOIN organization org ON acc.organizationid = org.id"),
	
	LEGEL_ENTITY_TABLE_JOIN("JOIN ACCOUNT ac ON A.AccountId = ac.id JOIN LegalEntity le ON ac.LegalEntityID = le.ID "),
	
	USER_FILTER_JOIN("JOIN Userresourcelock ul ON (ul.Resourceid = A.ContactId AND ul.resourcetype=3) OR (ul.Resourceid = A.Accountid AND ul.resourcetype=4)"
			+ " JOIN [USER] usr ON usr.id = ul.createdby"),
	
	REGISTRATION_QUEUE_COUNTRY_OF_RESIDENCE_FILTER("JOIN Country coun ON coun.id = b.country"),
	WATCHLIST_CATEGORY_FILTER(" LEFT JOIN ContactWatchList CL ON CL.contact=B.id LEFT JOIN watchlist W ON CL.reason=w.id AND w.category IN(?) "),
	
	GET_REGISTRATION_QUEUE("DECLARE @Offset int = ?, @Rows int = ?;DECLARE @tmpRows int = @Rows*10; "
			+ " WITH "
			+ "ContactAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_CONTACT_FILTER} ),"
			+ "AccountAttributeFilter AS "
			+ "( {REGISTRATION_QUEUE_ACCOUNT_FILTER} ),"
			+ "SelectedIds AS "
			+ "( {REGISTRATION_QUEUE_FILTER} "
			+ " ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE} OFFSET @Offset ROWS FETCH NEXT @tmpRows ROWS ONLY ), "
			+ " SelectedId AS ( "
			+ "select TOP (@Rows) * "
			+ "from SelectedIds a "
			+ " WHERE RowNum = 1 "
			+ "ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE} ) "
			+ "SELECT "
			+ "    c.Id                     AS contactid, "
			//+ "                c.compliancestatus, "
			+ " CASE acc.[type] WHEN 2 THEN c.compliancestatus ELSE acc.compliancestatus END AS compliancestatus, "
			+ "                c.accountid, "
			+ "     CASE acc.[type] WHEN 2 THEN  c.NAME ELSE acc.NAME END   AS contactname, "
			+ "    acc.tradeaccountnumber   AS TradeAccountNumber, "
			+ "    c.updatedon              AS registeredon, "
			+ "    acc.crmaccountid         AS ACSFID, "
			+ "    accattrib.vsellcurrency, "
			+ "    accattrib.vbuycurrency, "
			+ "    accattrib.vsource, "
			+ "    accattrib.vtransactionvalue, "
			+ "    org.NAME                 AS organization, "
			+ "    acc.LegalEntity, "
			+ "     acc.[type] AS Type, "
			+ "    CASE acc.[type] WHEN 2 THEN c.eidstatus  ELSE acc.eidstatus END AS eidstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.blackliststatus ELSE acc.blackliststatus END AS blackliststatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.fraugsterstatus ELSE acc.fraugsterstatus END AS fraugsterstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.sanctionstatus ELSE acc.sanctionstatus END AS sanctionstatus, "
			+ "    CASE acc.[type] WHEN 2 THEN c.customCheckStatus ELSE 9 END AS customCheckStatus, "
			+ "    contactstatusEnum.status AS contactStatus, "
			+ "    ur.id                    AS userresourceilockid, "
			+ "    usr.ssouserid            AS lockedby, "
			+ "    ur.createdon             AS UserResourceCreatedOn, "
			+ "    ur.workflowtime          AS UserResourceWorkflowTime, "
			+ "    ur.resourcetype          AS UserResourceEntityType, "
			+ "    SI.UpdatedOn             AS UpdatedOn, "
			+ "    coun.DisplayName         AS CountryFullName, "
			+ "    coun.Code                AS CountryCode, "
			+ "    acc.ComplianceDoneOn     AS registeredDate, "
			+ "    CASE acc.[type] WHEN 2 THEN c.version "
			+ "    ELSE acc.version END AS version "					
			+ "            FROM    contact c "
			+ "JOIN SelectedId Si ON c.Id = Si.ContactId "
			+ "JOIN contactattribute conattrib ON c.id = conattrib.id "
			+ "JOIN account acc ON acc.id = c.accountid "
			+ " {JOIN_LEGAL_ENTITY} " //Add for AT-3269
			+ "            JOIN accountattribute accattrib ON       c.accountid = accattrib.id "
			+ "            JOIN organization org ON     acc.organizationid = org.id "
			+ " JOIN contactcompliancestatusenum contactstatusEnum ON c.compliancestatus = contactstatusEnum.id "
			+ "  JOIN      country coun ON c.country = coun.id"
			+ " LEFT JOIN userresourcelock ur ON ur.resourceId = Si.ResourceId AND ur.ResourceType = si.ResourceType "
			+ "  AND ur.lockreleasedon IS NULL "
			+ "  LEFT JOIN [user] usr ON    usr.id = ur.createdby WHERE c.Deleted = 0 OR c.Deleted IS NULL"),
	
	
	GET_REGISTRATION_QUEUE_COUNT(" WITH "
		+ "ContactAttributeFilter AS "
		+ "( {REGISTRATION_QUEUE_CONTACT_FILTER} ), "
		+ "AccountAttributeFilter AS "
		+ "( {REGISTRATION_QUEUE_ACCOUNT_FILTER}  ), "
		+ " SelectedIds AS "
		+ "( {REGISTRATION_QUEUE_FILTER}  ) ,"
		+ " SelectedId AS ( "
		+ "select TOP 100 PERCENT * "
		+ "from SelectedIds a "
		+ " WHERE RowNum = 1 "
		+ "ORDER BY a.updatedon DESC ) "
		+ " SELECT "
		+ "    Count(1)              AS TotalRows "
		+ "FROM contact c JOIN SelectedId Si ON c.Id = Si.ContactId "
		+ "LEFT JOIN contactattribute conattrib ON c.id = conattrib.id "
		+ "LEFT JOIN account acc ON acc.id = c.accountid "
		+ " {JOIN_LEGAL_ENTITY} " //Add for AT-3269
		+ "LEFT JOIN accountattribute accattrib ON c.accountid = accattrib.id "
		+ "LEFT JOIN organization org ON acc.organizationid = org.id "
		+ "LEFT JOIN contactcompliancestatusenum contactstatusEnum ON c.compliancestatus = contactstatusEnum.id "
		+ " LEFT JOIN userresourcelock ur ON ur.resourceId = Si.ResourceId AND ur.ResourceType = si.ResourceType "
		+ "   AND ur.lockreleasedon IS NULL "
		+ "LEFT JOIN [user] usr ON usr.id = ur.createdby WHERE c.Deleted = 0 OR c.Deleted IS NULL" ),

	GET_CONTACTID_FOR_FILTER("SELECT rc.ContactId "
			+ "FROM   ( {INNER_QUERY} "
			+ "        ) AS rc "
			+ "WHERE  rownum >= ? "
			+ "       AND rownum <= ?"),

	GET_OTHER_CONTACT_DETAILS("SELECT "
					 + " c.ID, "
					 + " c.Name, c.SanctionStatus,c.BlacklistStatus,"
					 + " CASE A.[Type] WHEN 1 THEN 'CFX' ELSE 'PFX' END AS CustomerType, "
					 + " cste.Status AS complianceStatus "
					 + "FROM "
					 + " Contact C JOIN Account A ON C.Accountid=A.id "
					 + " JOIN ContactComplianceStatusEnum cste ON c.complianceStatus = cste.id "
					 + "WHERE "
					 + " AccountID =? "
					 + " AND C.Id <>? AND (C.Deleted = 0 OR C.Deleted IS NULL)"),

	GET_CONTACT_DETAILS_WITH_EVENT("SELECT "
			+ "    cr.ID, "
			+ "    conStatus.Status as ContactComplianceStatus, "
			+ "    accStatus.Status as AccountComplianceStatus, "
			+ "    cr.name, cr.isOnQueue, "
			+ "    cr.AccountID AS AccountId, "
			+ "    cr.CRMContactID, "
			+ "    cr.TradeContactID, "
			+ "    cr.complianceDoneOn As RegComplete, "
			+ "    cr.createdOn AS RegIn,cr.CustomCheckStatus, "
			+ "    cr.POIExists AS poiExists, " //AT-3391
			+ "    coun.DisplayName as countryFullName, "
			+ "    acc.ComplianceDoneOn AS RegCompleteAcc, "
			+ "    acc.CreatedOn AS RegistrationInDate, "
			+ "    acc.ComplianceExpiry AS ComplianceExpiry, "
			+ "    acc.CRMAccountID, "
			+ "    acc.TradeAccountNumber, "
			+ "    acc.LegalEntity, "
			+ "    org.code AS Organization, "
			+ "    conAttrib.Attributes AS contactAttrib, "
			+ "    accAttrib.Attributes AS AccountAttrib, "
			+ "    ur.id AS userResourceLockId, "
			+ " Lock.ssouserid AS LockedBy, "
			+ " cste.code AS ServiceName, "
			+ "    esl.Id as EventServiceLogId, "
			+ "    esl.EventId as EventId, "
			+ "    esl.summary as EventSummary, "
			+ "    esl.Status as EventStatus, "
			+ " event.ssouserid AS EventUpdatedBy, "
			+ "    esl.updatedOn as EventUpdatedOn, "
			+ "    acc.AccountTMFlag AS accountTMFlag, "
			+ "    acc.DataAnonStatus     AS dataAnonStatus, "
			+ "    acc.IntuitionRiskLevel     AS riskLevel, "
			+ "    acc.Version     AS accountVersion "
			+ "FROM Contact AS cr JOIN Account acc ON acc.id = cr.accountId "
			+ "    JOIN Country coun ON coun.id = cr.country "
			+ "    JOIN Organization org ON org.id=cr.OrganizationID "
			+ "    JOIN ContactAttribute conAttrib ON conAttrib.ID = cr.ID "
			+ "    JOIN AccountAttribute accAttrib ON accAttrib.ID=cr.AccountID "
			+ "    JOIN ContactComplianceStatusEnum conStatus ON conStatus.ID=cr.ComplianceStatus "
			+ "    LEFT JOIN Event eve ON eve.accountid=acc.id "
			+ "    AND PaymentInID is null "
			+ "    and PaymentOutID is null "
			+ " LEFT JOIN EventServiceLog esl ON esl.eventid = eve.id "
			+ "    and (esl.EntityID = cr.id  AND esl.EntityType = 3 OR esl.EntityType = 1)" //Contact 
			//+ "		 or (esl.EntityID = cr.accountId AND esl.EntityType = 1 and esl.eventid = eve.id)"
			+ " LEFT JOIN compliance_servicetypeenum cste ON cste.id = esl.servicetype "
			+ "    LEFT JOIN AccountComplianceStatusEnum accStatus ON accStatus.ID=acc.ComplianceStatus "
			+ "    LEFT JOIN UserResourceLock ur on cr.Id = ur.ResourceID "
			+ " AND ur.LockReleasedOn IS NULL AND ur.ResourceType = 3 "
			+ " LEFT JOIN [user] Lock ON lock.id = ur.createdby "
			+ " LEFT JOIN [user] event ON event.id = esl.updatedby "
			+ "where "
			+ " cr.id = ? "
			+ "order by esl.updatedOn desc"),
	
	GET_CONTACT_DETAILS_BY_RECORD_NUM("SELECT cr.ID, " + " conStatus.Status as ContactComplianceStatus, "
			+ " accStatus.Status as AccountComplianceStatus, " + " cr.name, " + " cr.AccountID AS AccountId, "
			+ " cr.CRMContactID, " + " cr.TradeContactID, " + " cr.complianceDoneOn As RegComplete, "
			+ " cr.createdOn AS RegIn, " + " acc.CRMAccountID,  " + " acc.TradeAccountNumber,"
			+ " org.code AS Organization, " + "       conAttrib.Attributes AS contactAttrib, "
			+ "       accAttrib.Attributes AS AccountAttrib, "
			+ "  ur.id AS userResourceLockId,ur.createdBy AS LockedBy,"
			+ " (select code from Compliance_ServiceTypeEnum where id =esl.serviceType) as ServiceName, "
			+ " esl.Id as EventServiceLogId , esl.EventId as EventId, " + " esl.summary as EventSummary, " + " esl.Status as EventStatus, "
			+ " esl.updatedBy as EventUpdatedBy, " + " esl.updatedOn as EventUpdatedOn " + " FROM " + "   (SELECT * "
			+ "    FROM " + "       (SELECT *  " + "       FROM contact  " + "       WHERE ID=(SELECT ContactId  FROM "
			+ " (SELECT ROW_NUMBER() OVER ( "
			+ " ORDER BY ID DESC) AS RowNum, "
			+ " id AS ContactId " 
			+ " FROM Contact "
			+ " WHERE ComplianceStatus<>1 ) AS RowConstrainedResult "
			+ " WHERE RowNum=?) ) AS ContactResult) AS cr "
			+ " LEFT JOIN Account acc ON acc.id=cr.accountId " + " JOIN Organization org ON org.id=cr.OrganizationID "
			+ " LEFT JOIN ContactAttribute conAttrib ON conAttrib.ID = cr.ID "
			+ " LEFT JOIN AccountAttribute accAttrib ON accAttrib.ID=cr.AccountID "
			+ " LEFT JOIN ContactComplianceStatusEnum conStatus ON conStatus.ID=cr.ComplianceStatus "
			+ " LEFT JOIN EventServiceLog esl ON esl.EntityID=cr.id AND esl.EntityType='CONTACT' and esl.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null ) "
			+ " LEFT JOIN AccountComplianceStatusEnum accStatus ON accStatus.ID=acc.ComplianceStatus "
			+ " LEFT JOIN UserResourceLock ur on cr.Id = ur.ResourceID AND ur.LockReleasedOn IS NULL AND  ur.ResourceType='CONTACT'"
			+ " order by esl.updatedOn desc"),
	
	GET_CONTACT_DETAILS_BY_CONTACTID("SELECT cr.ID, " + " conStatus.Status as ContactComplianceStatus, "
			+ " accStatus.Status as AccountComplianceStatus, " + " cr.name, " + " cr.AccountID AS AccountId, "
			+ " cr.CRMContactID, " + " cr.TradeContactID, " + " cr.complianceDoneOn As RegComplete, "
			+ " cr.createdOn AS RegIn, " + " acc.CRMAccountID,  " + " acc.TradeAccountNumber,"
			+ " org.code AS Organization, " + "       conAttrib.Attributes AS contactAttrib, "
			+ "       accAttrib.Attributes AS AccountAttrib, "
			+ "  ur.id AS userResourceLockId,(select ssouserid from  [user] where id = ur.createdby) AS LockedBy,"
			+ " (select code from Compliance_ServiceTypeEnum where id =esl.serviceType) as ServiceName, "
			+ " esl.Id as EventServiceLogId , esl.EventId as EventId, " + " esl.summary as EventSummary, " + " esl.Status as EventStatus, "
			+ " (select ssouserid from  [user] where id = esl.updatedby) as EventUpdatedBy, " + " esl.updatedOn as EventUpdatedOn " + " FROM " + "   (SELECT * "
			+ "    FROM " + "       (SELECT *  " + "       FROM contact  " + "       WHERE ID=("+GET_CONTACTID_FOR_FILTER.getQuery()+") ) AS ContactResult) AS cr "
			+ " LEFT JOIN Account acc ON acc.id=cr.accountId " + " JOIN Organization org ON org.id=cr.OrganizationID "
			+ " LEFT JOIN ContactAttribute conAttrib ON conAttrib.ID = cr.ID "
			+ " LEFT JOIN AccountAttribute accAttrib ON accAttrib.ID=cr.AccountID "
			+ " LEFT JOIN ContactComplianceStatusEnum conStatus ON conStatus.ID=cr.ComplianceStatus "
			+ " LEFT JOIN EventServiceLog esl ON esl.EntityID=cr.id AND esl.EntityType='CONTACT' and esl.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null ) "
			+ " LEFT JOIN AccountComplianceStatusEnum accStatus ON accStatus.ID=acc.ComplianceStatus "
			+ " LEFT JOIN UserResourceLock ur on cr.Id = ur.ResourceID AND ur.LockReleasedOn IS NULL AND  ur.ResourceType='CONTACT'"
			+ " order by esl.updatedOn desc"),

	GET_WATCHLIST_OF_CONTACT(
			"SELECT w.Reason AS WatchlistName ,isNull(cw.Contact,-1) AS ContactId FROM Watchlist w LEFT JOIN ContactWatchList cw ON ( w.id=cw.reason AND cw.contact=?) order by WatchlistName"),

	GET_WATCHLIST_ENUM("SELECT Reason FROM Watchlist"),

	GET_ACTIVITY_LOGS_OF_CONTACT(
			" SELECT (select SSOUserID from [user] where ID=pal.ActivityBy) As [User], pal.CreatedOn, pald.Log As [Activity], ate.module+' '+ate.type As [ActivityType], pal.Comments AS [Comment] FROM ProfileActivityLog  pal "
			+ " JOIN ProfileActivityLogDetail pald ON pal.id=pald.ActivityId "
			+ " JOIN ActivityTypeEnum ate ON pald.ActivityType=ate.id "
			+ " where  pal.contactId= ? OR (pal.Accountid= ?  AND pal.contactId IS NULL ) "
			+ " ORDER BY pal.CreatedOn desc"),

	GET_CONTACT_STATUS_REASON(
			"SELECT sur.Reason , ISNULL(psr.ContactID,-1) AS ContactID FROM StatusUpdateReason sur  LEFT JOIN ProfileStatusReason psr ON psr.StatusUpdateReasonID=sur.id AND  psr.ContactID=?   WHERE  sur.Module IN ('CONTACT','ALL') order by sur.Reason"),

	DELETE_CONTACT_WATCHLIST(
			"DELETE ContactWatchList WHERE Contact=? AND Reason IN (SELECT  [Id] FROM [Watchlist] WHERE Reason IN (?))"),

	GET_WATCHLIST_REASON("SELECT  [Id] FROM [Watchlist]"),
	
    //Query use for AT-2986
	INSERT_CONTACT_WATCHLIST(
			"INSERT INTO [ContactWatchList] VALUES ((SELECT  [Id] FROM [Watchlist] WHERE Reason = ?), ? , (select Id from [user] where SSOUserID=?) , ?)"),
	
	INSERT_WATCHLIST_BY_ACCOUNT_ID("INSERT INTO [ContactWatchList] ( [Reason], [Contact], [CreatedBy], [CreatedOn] ) "
			+ "select (SELECT [Id] FROM [Watchlist] WHERE Reason = ?), ID,(select Id from [user] where SSOUserID=?) , ? FROM CONTACT where AccountId=?"),

	DELETE_CONTACT_REASON(
			"DELETE [ProfileStatusReason] WHERE ContactID=? AND StatusUpdateReasonID IN (SELECT Id FROM [StatusUpdateReason] WHERE Module IN ('CONTACT','ALL') AND Reason IN (?))"),

	GET_STATUS_REASON_ID("SELECT Id FROM [StatusUpdateReason]"),

	INSERT_CONTACT_REASON(
			"INSERT INTO [ProfileStatusReason] VALUES((SELECT Id FROM Organization WHERE Code=?), ?, ?, (SELECT Id FROM StatusUpdateReason WHERE Module IN ('CONTACT','ALL') AND Reason=?), (select Id from [user] where SSOUserID=?), ?, (select Id from [user] where SSOUserID=?), ?)"),
	
	INSERT_PROFILE_ACTIVITY_LOG(
			"INSERT INTO [ProfileActivityLog] ([Timestamp], ActivityBy, OrganizationID, AccountID, ContactID, Comments, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn, UserLockID) "
			+ "VALUES (?, (select ID from [user] where SSOUserID=?), (SELECT Id FROM Organization WHERE Code= ? ), ?, ?, ?, "
			+ "(select Id from [user] where SSOUserID=?), ?, (select Id from [user] where SSOUserID=?), ?, "
			+ "(SELECT ID FROM UserResourceLock WHERE ResourceType = ? AND ResourceID = ? AND LockReleasedOn IS NULL))"),

	INSERT_PROFILE_ACTIVITY_LOG_DETAIL(
			"INSERT INTO [ProfileActivityLogDetail] VALUES(?, (SELECT Id FROM ActivityTypeEnum WHERE Module=? and Type=?), ?, (select Id from [user] where SSOUserID=?), ?, (select Id from [user] where SSOUserID=?), ?)"),

	UPDATE_CONTACT_STATUS(
			"UPDATE [Contact] SET ComplianceStatus=(SELECT Id FROM ContactComplianceStatusEnum WHERE Status=?), UpdatedBy=(select id  from [user] where SSOUserID=?), UpdatedOn = ? , isOnQueue = ? WHERE Id=?"),
	
	INSERT_USER_RESOURCE_LOCK(
			"INSERT INTO [UserResourceLock] (UserID, ResourceType, ResourceID, LockReleasedOn, CreatedBy, CreatedOn, WorkflowTime ) "
			+ " VALUES ((select Id from [user] where SSOUserID=?),?,?,?,(select Id from [user] where SSOUserID=?),?,?)"),
	
	INSERT_USER_RESOURCE_LOCK_PFX(
			"INSERT INTO [UserResourceLock] (UserID, ResourceType, ResourceID, LockReleasedOn, CreatedBy, CreatedOn, WorkflowTime ) VALUES "
			+ " ((select Id from [user] where SSOUserID=?),?,?,?,(select Id from [user] where SSOUserID=?),?,?)"),

	UPDATE_USER_RESOURCE_TO_LOCK(
			"UPDATE [UserResourceLock] SET [UserID] = (select Id from [user] where SSOUserID=?) , [CreatedBy]= (select Id from [user] where SSOUserID=?) , [CreatedOn] = ? , [LockReleasedOn] = ?  "
			+ " WHERE (id=?)"),
	
	UPDATE_USER_RESOURCE_LOCK(
			"UPDATE [UserResourceLock] SET LockReleasedOn= ? WHERE ID = ? and LockReleasedOn is null"),
	
	UPDATE_USER_RESOURCE_LOCK_PFX(
			"UPDATE [UserResourceLock] SET LockReleasedOn= ? WHERE ID = ? and LockReleasedOn is null"),

	GET_ACCOUNT_WATCHLIST("select cw.Contact as ContactId,w.Reason as WatchlistName from contactWatchlist cw "
			+ "join WatchList w on cw.Reason= w.id "
			+ " where contact in "
			+ "(select id from contact where accountId= ? ) order by WatchlistName"),
	
	//query added for AT-2986
	GET_CONTACT_WATCHLIST_WITH_SELECTED("SELECT w.Reason AS WatchlistName ,isNull(cw.Contact,-1) AS ContactId FROM Watchlist w " 
			+ "LEFT JOIN ContactWatchList cw ON ( w.id=cw.reason AND cw.Contact = ?) order by WatchlistName"),
	
	//query added for AT-2986
	GET_CONTACT_WATCHLIST_CATEGORY_WITH_SELECTED("SELECT w.Reason AS WatchlistName ,isNull(cw.Contact,-1) AS ContactId FROM Watchlist w " 
			+ "LEFT JOIN ContactWatchList cw ON ( w.id=cw.reason AND cw.contact = ?) WHERE w.category IN (?) order by WatchlistName"),
	
	GET_ACCOUNT_WATCHLIST_WITH_SELECTED("SELECT w.Reason AS WatchlistName ,isNull(cw.Contact,-1) AS ContactId FROM Watchlist w " 
	+ "LEFT JOIN ContactWatchList cw ON ( w.id=cw.reason AND cw.contact IN (select ID from contact where AccountId=?)) order by WatchlistName"),
	
	GET_ACCOUNT_WATCHLIST_CATEGORY_WITH_SELECTED("SELECT w.Reason AS WatchlistName ,isNull(cw.Contact,-1) AS ContactId FROM Watchlist w " 
			+ "LEFT JOIN ContactWatchList cw ON ( w.id=cw.reason AND cw.contact IN(select ID from contact where AccountId=?)) WHERE w.category IN (?) order by WatchlistName"),
	//Query changes since for CFX customer shown on basis of accountid 
	
/*	*//**Optimized registration queue query with criteria, used for registration pagination and filter criteria*//*
	GET_REG_QUEUE_INNER_QUERY("SELECT row_number()  OVER ( ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE}) AS rownum, "
			+ "        rqtemp.* "
			+ "    FROM "
			+ "        ( "
			+ "            select "
			+ "                ur.id AS userresourceilockid, "
			+ "                usr.ssouserid AS lockedby, "
			+ "                ur.CreatedOn AS UserResourceCreatedOn, "
			+ "                ur.WorkflowTime AS UserResourceWorkflowTime, "
			+ "                ur.ResourceType AS UserResourceEntityType, "
			+ "                c.id AS contactid, "
			+ "                c.compliancestatus, "
			+ "                c.accountid, "
			+ "                c.NAME AS contactname, "
			+ "                acc.tradeaccountnumber, "
			+ "                " + RegQueDBColumns.CONTACT_CREATEDON.getName()+  " AS registeredon, "
			+ "                accattrib.sellcurrency, "
			+ "                accattrib.buycurrency, "
			+ "                accattrib.source, "
			+ "                accattrib.transactionvalue, "
			+ "                org.NAME AS organization, "
			+ "                acc.[type] AS Type, "
			+ "                c.EIDStatus AS kycstatus, "
			+ "                c.BlacklistStatus AS blackliststatus, "
			+ "                c.FraugsterStatus AS fraugsterstatus, "
			+ "                c.SanctionStatus AS sanctionstatus "
			+ "            FROM "
			+ "                contact c "
			+ "            JOIN account acc ON "
			+ "                acc.id = c.accountid "
			+ "            JOIN accountattribute accattrib ON "
			+ "                c.accountid = accattrib.id "
			+ "            JOIN organization org ON "
			+ "                acc.organizationid = org.id "
			+ "            LEFT JOIN userresourcelock ur ON "
			+ "                c.id = ur.resourceid "
			+ "                AND ur.resourcetype = 'CONTACT' "
			+ "            LEFT JOIN [user] usr ON "
			+ "                usr.id = ur.createdby "
			+ "            WHERE "
			+ "                 {PFX_DATETIME_CRITERIA} "
			+ "                 c.compliancestatus = 4 "
			+ "                AND acc.[type] = 'PFX' "
			+ "                AND ur.lockreleasedon IS NULL "
			+ "                "
			+ "        UNION SELECT "
			+ "                ur.id AS userresourceilockid, "
			+ "                usr.ssouserid AS lockedby, "
			+ "                ur.CreatedOn AS UserResourceCreatedOn, "
			+ "                ur.WorkflowTime AS UserResourceWorkflowTime, "
			+ "                ur.ResourceType AS UserResourceEntityType, "
			+ "                ( "
			+ "                    SELECT "
			+ "                        top 1 c.id "
			+ "                    FROM "
			+ "                        Contact c "
			+ "                    where "
			+ "                        c.AccountID = acc.id "
			+ "                    order by "
			+ "                        c.id "
			+ "                ) AS contactid, "
			+ "                acc.compliancestatus, "
			+ "                acc.id as accountid, "
			+ "                acc.NAME AS contactname, "
			+ "                acc.tradeaccountnumber, "
			+ "               " + RegQueDBColumns.ACCOUNT_CREATEDON.getName()+  " AS registeredon,  "
			+ "                accattrib.sellcurrency, "
			+ "                accattrib.buycurrency, "
			+ "                accattrib.source, "
			+ "                accattrib.transactionvalue, "
			+ "                org.NAME AS organization, "
			+ "                acc.[type] AS Type, "
			+ "                acc.EIDStatus AS kycstatus, "
			+ "                acc.BlacklistStatus AS blackliststatus, "
			+ "                acc.FraugsterStatus AS fraugsterstatus, "
			+ "                acc.SanctionStatus AS sanctionstatus "
			+ "            FROM "
			+ "                account acc "
			+ "            JOIN accountattribute accattrib ON "
			+ "                acc.id = accattrib.id "
			+ "            JOIN organization org ON "
			+ "                acc.organizationid = org.id "
			+ "            LEFT JOIN userresourcelock ur ON "
			+ "                acc.id = ur.resourceid "
			+ "                AND ur.resourcetype = 'ACCOUNT' "
			+ "            LEFT JOIN [user] usr ON "
			+ "                usr.id = ur.createdby "
			+ "            WHERE "
			+ "                 {CFX_DATETIME_CRITERIA}  "
			+ "                   acc.compliancestatus = 4 "
			+ "                AND acc.[type] = 'CFX' "
			+ "                AND ur.lockreleasedon IS NULL "
			+ "              "
			+ "        ) AS rqtemp"),*/
	
    GET_PROFILE_ACTIVITY_LOGS_BY_ROWS("SELECT  ac.* FROM "
			+ " (SELECT  Row_number() over (ORDER BY pal.createdon desc) AS rownum,(select SSOUserID from [user] where ID=pal.activityby)              AS [User], "
			+ "       pal.createdon, pal.comments , "
			+ "       pald.log                    AS [Activity], "
			+ "       ate.module + ' ' + ate.type AS [ActivityType] "
			+ "       FROM   profileactivitylog pal "
			+ "       JOIN profileactivitylogdetail pald "
			+ "         ON pal.id = pald.activityid "
			+ "       JOIN activitytypeenum ate "
			+ "         ON pald.activitytype = ate.id "
			+ "        WHERE   pal.contactid = ? OR (pal.Accountid=? AND pal.contactId IS NULL AND ate.module = 'PROFILE' )  ) as ac "
			+ "         where rownum >= ? and rownum <= ? "),
    
    SAVE_INTO_BROADCAST_QUEUE("INSERT INTO [StatusBroadCastQueue]([OrganizationID], [EntityType], [AccountID], [ContactID], [PaymentInID], [PaymentOutID], [StatusJson], [DeliveryStatus], [DeliverOn], [CreatedBy], [CreatedOn]) "
    		+ "    VALUES((SELECT ID FROM Organization WHERE code=?), ?, ?, ?, ?, ?, ?, ?, ?, (select Id from [user] where SSOUserID=?), ?)"),
    
    GET_VIEWMORE_REGISTRATION_DETAILS("Select * from (SELECT Row_number() "
			+ " OVER (ORDER BY id DESC) AS rownum, PQueue.* "
			+ " From "
			+ " (SELECT esl.id, "
			+ " esl.eventId, "
			+ " esl.entitytype, "
			+ " esl.entityid, "
			+ " esl.createdon, "
			+ " esl.updatedon, "
			+ " (select SSOUserId from [user] where ID=esl.updatedBy) AS updatedBy , "
			+ " esl.Summary "
			+ "                           "
			+ " FROM   eventservicelog esl "
			+ "                   Left Join Event eve ON eve.id = esl.eventId "
			+ " WHERE  servicetype = (SELECT id "
			+ " FROM   compliance_servicetypeenum "
			+ " WHERE code = ?) "
			+ " AND entitytype = ? "
			+ " and EntityID = ? "
			+ "                         and eve.PaymentInID is null "
			+ "                         and eve.PaymentOutID is null "
			+ " ) AS PQueue ) AS PQueue2 "
			+ " WHERE  rownum >= ? "
			+ " AND rownum <= ? ORDER  BY updatedon DESC"),

	GET_BACKLISTED_CONTACTS_OF_ACCOUNT("SELECT EntityId, max (CreatedOn),status FROM  EventServiceLog "
			+ "where serviceType IN (3,5,8) and Status NOT IN (1,7,9) and EntityType=3 and EntityID IN "
			+ "(select id from contact where accountId = ? AND Id <> ?) group by EntityId,Status"),
	
	UPDATE_ACCOUNT_STATUS("UPDATE Account SET AccountStatus=?, ComplianceStatus= (SELECT Id FROM AccountComplianceStatusEnum WHERE status=?), ComplianceDoneOn = ? , ComplianceExpiry = ?, isOnQueue =? WHERE Id=? "),
	
	GET_PROVIDER_RESPONSE("select ProviderResponse from EventServiceLog where id = ?"),
	
	GET_VIEWMORE_REGISTRATION_DETAILS_FOR_CUSTOM_CHECK("Select * from  (SELECT esl.id, "
			+ "                            esl.status, "
			+ "                            esl.eventId, "
			+ " esl.entitytype, "
			+ " esl.entityid, "
			+ " esl.createdon, "
			+ " esl.updatedon, "
			+ " esl.Summary, "
			+ "                          (select SSOUserId from [user] where ID=esl.updatedBy) AS updatedBy, "
			+ "                          (select code from compliance_servicetypeenum where id = esl.servicetype ) AS ServiceType "
			+ " FROM   eventservicelog esl "
			+ "                   Left Join Event eve ON eve.id = esl.eventId "
			+ " WHERE  servicetype IN   (SELECT id "
			+ " FROM   compliance_servicetypeenum "
			+ " WHERE  code IN (?,?,?)) "
			+ "                                             "
			+ " AND entitytype = ?"
			+ " and EntityID = ?"
			+ "                         and eve.PaymentInID is null "
			+ "                         and eve.PaymentOutID is null "
			+ " ) AS PQueue ORDER  BY eventId DESC, servicetype ASC"),
				
	GET_ORGANIZATION("select Code from Organization"),
	
	GET_SOURCE("select distinct(source) from AccountAttribute"),
	
	GET_TRANSACTIONVALUE("select distinct(TransactionValue) from AccountAttribute"),
	
	GET_CURRENCY("select Code from Currency"),
	
	GET_PAYMENTOUTINFO_FOR_ACCOUNT("select * from(SELECT Row_number() "
			+ "OVER (ORDER BY poid DESC)  AS rownum, PQueue.* From (SELECT poa.id                 AS poid, "
			+ " poa.transactiondate    AS [DateofPayment], "
			+ " poa.buyingamount       AS Amount, "
			+ " poa.buyingcurrency     AS BuyCurrency, "
			+ " acc.NAME               AS Accountname, "
			+ " acc.tradeaccountnumber AS Account "
			+ " FROM   paymentoutattribute poa join PaymentOut po on poa.id = po.id JOIN account acc ON acc.id = po.accountid "
			+ " WHERE  acc.id = ? "
			+ " ) AS PQueue ) AS PQueue2 WHERE  rownum >= ? "
			+ " AND rownum <= ? ORDER  BY DateofPayment DESC"),
	
	//change in query to fetch beneficiary first name ,beneficiary last name and attributes column data  - Vishal J
	/*GET_PAYMENTOUTINFO_FOR_ACCOUNT_SELECT("With PQueue(rownum,poid,DateofPayment, Amount,BuyCurrency,TradeContractNumber,BeneficiaryFirstName,BeneficiaryLastName,AccountNumber,PaymentReference,Attributes,Accountname,Account,Valuedate)  AS "
			+ "            ( "
			+ "                SELECT "
			+ "                    Row_number() OVER (ORDER BY poa.id  DESC) AS rownum, "
			+ "                    poa.id AS poid, "
			+ "                    po.transactiondate AS DateofPayment, "
			+ "                    poa.vBeneficiaryAmount AS Amount, "
			+ "                    poa.vbuyingcurrency AS BuyCurrency, "
			+ "                    po.ContractNumber AS TradeContractNumber,"
			+ "                    poa.vbeneficiaryfirstname AS BeneficiaryFirstName, "
			+ "                    poa.vbeneficiarylastname AS BeneficiaryLastName, "
			+ " 				   JSON_VALUE(ATTRIBUTES, '$.beneficiary.account_number') AS AccountNumber,"	
			+ "                    JSON_VALUE(ATTRIBUTES, '$.beneficiary.payment_reference') AS PaymentReference,"
			+ "                    poa.attributes AS Attributes, "
			+ "                    acc.NAME AS Accountname, "
			+ "                    acc.tradeaccountnumber AS Account ,"
			+"                     poa.vvaluedate AS Valuedate   "
			+ "                FROM paymentoutattribute poa "
			+ "                    join PaymentOut po on poa.id = po.id "
			+ "                    JOIN account acc ON acc.id = po.accountid "
			+ "                WHERE acc.id = ? "
			+ "            ) "
			+ "SELECT PQueue.*, ( SELECT max (rownum) FROM PQueue) AS 'TotalRows' FROM    PQueue "
			+ "WHERE rownum >= ? "
			+ "    AND rownum <= ? "
			+ "ORDER BY DateofPayment DESC"),*/
	
	GET_PAYMENTOUTINFO_FOR_ACCOUNT_SELECT("SELECT "
			+ " COUNT(poa.id) over ( partition by po.accountid) AS TotalRows, "
			+ " poa.id AS poid, "
			+ " po.transactiondate AS DateofPayment, "
			+ " poa.vBankName AS BankName, "
			+ " poa.vBeneficiaryAmount AS Amount, "
			+ " poa.vbuyingcurrency AS BuyCurrency, "
			+ " po.ContractNumber AS TradeContractNumber, "
			+ " poa.vbeneficiaryfirstname AS BeneficiaryFirstName, "
			+ " poa.vbeneficiarylastname AS BeneficiaryLastName, "
			+ " JSON_VALUE(ATTRIBUTES, '$.beneficiary.payment_reference') AS PaymentReference, "
			+ " JSON_VALUE(ATTRIBUTES, '$.beneficiary.account_number') AS AccountNumber, "
			+ " acc.NAME               AS Accountname, "
			+ " acc.tradeaccountnumber AS Account , "
			+ " poa.vMaturityDate AS MaturityDate "
			+ " FROM "
			+ " paymentoutattribute poa join PaymentOut po on "
			+ " poa.id = po.id "
			+ " JOIN account acc ON "
			+ " acc.id = po.accountid "
			+ " WHERE "
			+ " acc.id = ? "
			+ " ORDER BY "
			+ " poa.id DESC "
			+ " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"),
	
	GET_PAYMENTINFO_FOR_ACCOUNT("select * from (select Row_number() over (order by pyiid desc) AS rownum , PQueue.* From "
			+ "(SELECT pia.id                 AS pyiid, "
			+ " pyi.transactiondate AS [DateofPayment], "
			+ " pia.vTransactionAmount AS Amount, "
			+ " pia.vTransactionCurrency     AS SellCurrency, "
			+ " pia.vpaymentMethod      AS PaymentMethodin, "
			+ " acc.NAME               AS Accountname, "
			+ " acc.tradeaccountnumber AS Account "
			+ " FROM   paymentInattribute pia join PaymentIn pyi on pia.id = pyi.id JOIN account acc ON acc.id = pyi.accountid "
			+ " WHERE  acc.id = ? "
			+ " ) AS PQueue ) AS PQueue2 where rownum >= ? "
			+ "and rownum<= ? ORDER  BY DateofPayment DESC"),
	
	//'attributes' column is fetched from paymentInAttribute table 
	/*GET_PAYMENTINFO_FOR_ACCOUNT_SELECT("With PQueue(rownum,poid,DateofPayment,PaymentMethodin,Attributes,Amount,SellCurrency,Accountname,Account)  AS "
			+ "            ( "
			+ "                SELECT "
			+ "                    Row_number() OVER (ORDER BY poa.id  DESC) AS rownum, "
			+ "                    poa.id AS poid, "
			+ "                    po.transactiondate AS DateofPayment, "
			+ "			           poa.vpaymentMethod      AS PaymentMethodin, "
			+ "                    poa.attributes AS Attributes, "
			+ "                    poa.vTransactionAmount AS Amount, "
			+ "                    poa.vTransactionCurrency AS SellCurrency, "
			+ "                    acc.NAME AS Accountname, "
			+ "                    acc.tradeaccountnumber AS Account "
			+ "                FROM paymentInAttribute poa "
			+ "                    join PaymentIn po on poa.id = po.id "
			+ "                    JOIN account acc ON acc.id = po.accountid "
			+ "                WHERE acc.id = ? "
			+ "            ) "
			+ "SELECT PQueue.*, ( SELECT max (rownum) FROM PQueue) AS 'TotalRows' FROM    PQueue "
			+ "WHERE rownum >= ? "
			+ "    AND rownum <= ? "
			+ "ORDER BY DateofPayment DESC"),*/
	
	GET_PAYMENTINFO_FOR_ACCOUNT_SELECT("SELECT "
			+ " COUNT(poa.id) over ( partition by po.accountid) AS TotalRows, "
			+ " poa.id AS poid, "
			+ " po.transactiondate AS DateofPayment, "
			+ " poa.vpaymentMethod AS PaymentMethodin, "
			+ " poa.vTransactionAmount AS Amount, "
			+ " poa.vTransactionCurrency AS SellCurrency, "
			+ " acc.NAME AS Accountname, "
			+ " acc.tradeaccountnumber AS Account, "
			+ " po.TradeContractNumber AS TradeContractNumber,"
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.cc_first_name') AS Ccfirstname, "
			+ " JSON_VALUE(ATTRIBUTES, '$.debtorAccountNumber') AS DebtorAccountNumber, "
			+ " JSON_VALUE(ATTRIBUTES, '$.risk_score') AS RiskScore, "
			+ " JSON_VALUE(ATTRIBUTES, '$.risk_score.tScore') AS TScore, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.debtor_name') AS DebtorName, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.debtor_bank_name') AS BankName "
			+ "FROM "
			+ " paymentInAttribute poa join PaymentIn po on "
			+ " poa.id = po.id "
			+ "JOIN account acc ON "
			+ " acc.id = po.accountid "
			+ "WHERE "
			+ " acc.id = ? "
			+ "ORDER BY "
			+ " poa.id DESC "
			+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"),
	
	GET_RECORDLOCK_STATUS("select * from UserResourceLock url Join [user] ur ON url.UserID = ur.id where ResourceID =? and ResourceType=? and LockReleasedOn is null"),
	
	GET_COUNTRY_LIST("select DisplayName AS Country from Country"),
	
	
	
	//***********Same Query to be used for all three flows*******************************************/
	/*BeneficiaryFirstName, BeneficiaryLastName and Attributes columns from PaymentOutAttribute
	are fetched to show beneficiary details on UI - Vishal J*/
	GET_VIEWMORE_FURTHER_PAYMENT_OUT_DETAILS("select * from(SELECT Row_number() "
			+ "OVER (ORDER BY poid DESC)  AS rownum, PQueue.* From (SELECT poa.id                 AS poid, "
			+ " po.transactiondate    AS [DateofPayment], "
			+ " poa.vBankName AS BankName, "
			+ " poa.BeneAmount       AS Amount, "
			+ "                       poa.vbeneficiaryfirstname AS BeneficiaryFirstName, "
			+ "                       poa.vbeneficiarylastname AS BeneficiaryLastName, "
			+ "                       poa.attributes AS Attributes, "
			+ "                       JSON_VALUE(ATTRIBUTES, '$.beneficiary.account_number') AS AccountNumber,"
			+ "                       po.ContractNumber AS TradeContractNumber,"
			+ "                       JSON_VALUE(ATTRIBUTES, '$.beneficiary.payment_reference') AS PaymentReference,"
			+ " poa.vbuyingcurrency     AS BuyCurrency, "
			+ " acc.NAME               AS Accountname, "
			+ "                        poa.vMaturityDate   AS MaturityDate     ,    "
			+ " acc.tradeaccountnumber AS Account "
			+ " FROM   paymentoutattribute poa join PaymentOut po on poa.id = po.id JOIN account acc ON acc.id = po.accountid "
			+ " WHERE  acc.id = ? "
			+ " ) AS PQueue ) AS PQueue2 WHERE  rownum >= ? "
			+ " AND rownum <= ? ORDER  BY DateofPayment DESC"),
	
	/*GET_VIEWMORE_FURTHER_PAYMENT_IN_DETAILS("select * from (select Row_number() over (order by pyiid desc) AS rownum , PQueue.* From "
			+ "(SELECT pia.id                 AS pyiid, "
			+ "			              pyi.transactiondate    AS [DateofPayment], "
			+ "			              pia.vTransactionAmount       AS Amount, "
			+ "			              pia.vTransactionCurrency     AS SellCurrency, "
			+ "                       pyi.TradeContractNumber AS TradeContractNumber,"
			+ "                       JSON_VALUE(ATTRIBUTES, '$.trade.bill_ad_zip') AS PaymentReference,"
			+ "			              pia.vpaymentMethod      AS PaymentMethodin, "
			+ "                       pia.attributes AS Attributes, "
			+ "			              acc.NAME               AS Accountname, "
			+ "			              acc.tradeaccountnumber AS Account "
			+ "			 FROM   paymentInattribute pia join PaymentIn pyi on pia.id = pyi.id JOIN account acc ON acc.id = pyi.accountid "
			+ "			 WHERE  acc.id = ? "
			+ "			 ) AS PQueue ) AS PQueue2 where rownum >= ? "
			+ "and rownum<= ? ORDER  BY DateofPayment DESC"),*/
	
	GET_VIEWMORE_FURTHER_PAYMENT_IN_DETAILS("select * from (select Row_number() over (order by pyiid desc) AS rownum , PQueue.* From "
			+ "(SELECT pia.id                 AS pyiid, "
			+ " pyi.transactiondate    AS [DateofPayment], "
			+ " pia.attributes AS Attributes, "
			+ " pia.vTransactionAmount       AS Amount, "
			+ " pia.vTransactionCurrency     AS SellCurrency, "
			+ " pyi.TradeContractNumber AS TradeContractNumber, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.bill_ad_zip') AS PaymentReference, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.cc_first_name') AS Ccfirstname, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.debtor_name') AS DebtorName, "
			+ " JSON_VALUE(ATTRIBUTES, '$.debtorAccountNumber') AS DebtorAccountNumber, "
			+ " JSON_VALUE(ATTRIBUTES, '$.risk_score') AS RiskScore, "
			+ " JSON_VALUE(ATTRIBUTES, '$.risk_score.tScore') AS TScore, "
			+ " JSON_VALUE(ATTRIBUTES, '$.trade.debtor_bank_name') AS BankName, "
			+ " pia.vpaymentMethod      AS PaymentMethodin, "
			+ " acc.NAME               AS Accountname, "
			+ " acc.tradeaccountnumber AS Account "
			+ " FROM   paymentInattribute pia join PaymentIn pyi on pia.id = pyi.id JOIN account acc ON acc.id = pyi.accountid "
			+ " WHERE  acc.id = ? "
			+ " ) AS PQueue ) AS PQueue2 where rownum >= ? "
			+ " and rownum<= ? ORDER  BY DateofPayment DESC"),
	
	GET_FURTHER_PAYMENT_IN_DETAILS_COUNT("SELECT COUNT(*) as Count from PaymentIn where ID in (SELECT pia.id   AS pyiid "
			+ " FROM   paymentInattribute pia join PaymentIn pyi on pia.id = pyi.id JOIN account acc ON acc.id = pyi.accountid "
			+ " WHERE  acc.id = ?)"),
	
	GET_FURTHER_PAYMENT_OUT_DETAILS_COUNT("SELECT COUNT(*) as Count from PaymentOut where ID in (SELECT poa.id                 AS poid "
			+ " FROM   paymentoutattribute poa join PaymentOut po on poa.id = po.id JOIN account acc ON acc.id = po.accountid "
			+ " WHERE  acc.id = ?)"),
	
	GET_DOCUMENT_LIST("select * from Document"),
	
	GET_REG_QUEUE_FOR_PAGINATION_FILTER("WITH regque "
			+ " AS "
			+ " ( "
			+ " {INNER_QUERY} "
			+ " ) "
			+ " SELECT "
			+ " regque.*, "
			+ " ( SELECT max (rownum) FROM regque) AS 'TotalRows' "
			+ " FROM regque "
			+ " WHERE  rownum in (?,?,?,?)"),
	
	GET_PAGINATION_DETAILS("SELECT rc.* "
			+ " FROM   (SELECT Row_number() "
			+ " OVER ( "
			+ " ORDER BY contactid DESC) AS rownum, "
			+ " rc1.* "
			+ " FROM   (SELECT c.id                             AS contactid, "
			+ " c.accountid, "
			+ " acc.createdon                    AS registeredon, "
			+ " accattrib.type, "
			+ " accattrib.sellcurrency, "
			+ " accattrib.buycurrency, "
			+ " accattrib.source, "
			+ " accattrib.transactionvalue, "
			+ " (SELECT NAME "
			+ " FROM   organization "
			+ " WHERE  id = acc.organizationid) AS organization, "
			+ " (SELECT TOP 1 status "
			+ " FROM   eventservicelog e "
			+ " WHERE  e.servicetype = (SELECT id "
			+ " FROM "
			+ " compliance_servicetypeenum "
			+ " WHERE  code = 'KYC') "
			+ " AND e.entityid = c.id "
			+ " AND e.entitytype = 'CONTACT' and e.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null and accountid=acc.id ) "
			+ " ORDER  BY e.updatedon DESC)     AS kycstatus, "
			+ " (SELECT TOP 1 status "
			+ " FROM   eventservicelog e "
			+ " WHERE  e.servicetype = (SELECT id "
			+ " FROM "
			+ " compliance_servicetypeenum "
			+ " WHERE  code = 'BLACKLIST') "
			+ " AND e.entityid = c.id "
			+ " AND e.entitytype = 'CONTACT' and e.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null and accountid=acc.id ) "
			+ " ORDER  BY e.updatedon DESC)     AS blackliststatus, "
			+ " (SELECT TOP 1 status "
			+ " FROM   eventservicelog e "
			+ " WHERE  e.servicetype = (SELECT id "
			+ " FROM "
			+ " compliance_servicetypeenum "
			+ " WHERE  code = 'FRAUGSTER') "
			+ " AND e.entityid = c.id "
			+ " AND e.entitytype = 'CONTACT' and e.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null and accountid=acc.id ) "
			+ " ORDER  BY e.updatedon DESC)     AS fraugsterstatus, "
			+ " (SELECT TOP 1 status "
			+ " FROM   eventservicelog e "
			+ " WHERE  e.servicetype = (SELECT id "
			+ " FROM "
			+ " compliance_servicetypeenum "
			+ " WHERE  code = 'SANCTION') "
			+ " AND e.entityid = c.id "
			+ " AND e.entitytype = 'CONTACT' and e.eventId IN (select id from Event where PaymentInID is null and PaymentOutID is null  and accountid=acc.id) "
			+ " ORDER  BY e.updatedon DESC)     AS sanctionstatus "
			+ " FROM   contact c "
			+ " LEFT JOIN account acc "
			+ " ON acc.id = c.accountid "
			+ " LEFT JOIN accountattribute accAttrib "
			+ " ON c.accountid = accattrib.id "
			+ " WHERE  c.compliancestatus IN (4,5) ) AS rc1 "
			+ " ) AS rc "
			+ " WHERE  rownum in (?,?,?,?)"),
	
	UPDATE_ACCOUNT_FROM_ATTRIBUTE("update AccountAttribute set Attributes = ? where id=?"),
	
	GET_ACC_ATTRIBUTE("Select Attributes From AccountAttribute Where Id=?"),
	
	GET_KYC_SUPPORTED_COUNTRY_LIST("SELECT * FROM Country WHERE DisplayName IN (SELECT Country FROM KYC_CountryProviderMapping)"),
	
	GET_ACCOUNT_CONTACT_IDS_BY_CONTACT_ID("select c.TradeContactId,c.CRMContactID,a.CRMAccountID,a.TradeAccountID,a.TradeAccountNumber FROM Contact c left join Account a on c.AccountID=a.id where c.id=?"),
	
	UPDATE_WORKFLOW_TIME("UPDATE [UserResourceLock] SET WorkflowTime=? WHERE ID=?"),
	
	UPDATE_WATCHLISTSTATUS_ACCOUNT("UPDATE [Account] SET UpdatedBy=(select id  from [user] where SSOUserID=?),UpdatedOn=?,PayInWatchlistStatus=?,PayOutWatchlistStatus=? WHERE Id=?"),
	
	//New AT-2986
	UPDATE_WATCHLISTSTATUS_CONTACT("UPDATE [Contact] SET UpdatedBy=(select id  from [user] where SSOUserID=?),UpdatedOn=?,PayInWatchlistStatus=?,PayOutWatchlistStatus=? WHERE Id=?"),
		
	GET_DEVICE_INFO("SELECT deviceinfo.UserAgent,deviceinfo.CreatedOn, eve.EventType ,ete.Type "
			+ "         FROM DeviceInfo AS deviceinfo "
			+ "         JOIN Event AS eve "
			+ "         ON deviceinfo.EventID = eve.ID "
			+ "         JOIN EventTypeEnum ete "
			+ "         ON eve.EventType = ete.ID "
			+ "         WHERE eve.accountid = ? "
			+ "         ORDER BY deviceinfo.CreatedOn"),
	
	GET_OLD_ACCOUNT_STATUS("select compliancestatus from Account where ID = ?"),
	
	
	GET_ACCOUNT_HISTORY("select  "
			+ " case   when aa.version = 1 then aa.attributes "
			+ " else aah.attributes end as attributes,   aa.createdOn AS createdon , a.ComplianceDoneOn as compliancedoneon"
			+ " from AccountAttribute aa LEFT JOIN AccountAttributeHistory aah ON AA.id=aah.accountid   "
			+ " and Aah.version=1 JOIN account a  on a.id=AA.id   WHERE aa.id=?  "),
	
	GET_CONTACT_HISTORY("select case when ca.version =1 then ca.attributes else cah.attributes end as attributes  "
			+ " from ContactAttribute ca left join ContactAttributeHistory cah on ca.id = cah.contactid  "
			+ " and cah.version = 1  "
			+ " join contact c on c.id = ca.id  "
			+ " join account a on c.accountid=a.id  "
			+ " where datediff(minute,a.createdOn,c.CreatedOn) < ? And a.id=? "),
	
	GET_DEVICE_FOR_SIGNUP("SELECT *    FROM DeviceInfo AS deviceinfo    JOIN Event AS eve   "
			+ "  ON deviceinfo.EventID = eve.ID  AND eve.EventType=1 "
			+ "  JOIN EventTypeEnum ete    ON eve.EventType = ete.ID   "
			+ "   WHERE eve.accountid = ?  ORDER BY deviceinfo.CreatedOn"),
	
	GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN("SELECT Reason,StopPaymentIn,StopPaymentOut,Category FROM watchlist WHERE (StopPaymentIn = 1 OR StopPaymentOut = 1)"),
	
	GET_CONTACT_WATCHLIST("select DISTINCT watch.Reason from contactwatchlist cw   Join WatchList watch   "
			+ "    ON watch.id = cw.reason where contact IN (select id from Contact where Accountid= ? )"),
	
	//Added for AT-2986
	GET_CONTACT_WATCHLIST_FOR_PFX("select DISTINCT watch.Reason from contactwatchlist cw   Join WatchList watch   "
				+ "    ON watch.id = cw.reason where contact = ?"),
		
	GET_ALERT_COMPLIANCE_LOG("SELECT TOP 1 * FROM ProfileActivityLogDetail PALD  "
			+ " JOIN ProfileActivityLog PAL ON PALD.ActivityID = PAL.ID "
			+ " JOIN ActivityTypeEnum ATE ON ATE.id = PALD.ActivityType AND ATE.Module='PROFILE' AND ATE.[Type]='COMPLIANCE_LOG'"
			+ " where PAL.AccountID= ? order by PALD.id desc"),
	
	GET_FRAUGSTER_PROVIDER_RESPONSE_BY_ACCOUNTID("WITH SelectedID AS( "
			+ "SELECT "
			+ " Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum , "
			+ " esl.entityType,esl.EntityID, JSON_VALUE(esl.ProviderResponse, "
			+ " '$.frgTransId') AS frgTransId, "
			+ " JSON_VALUE(esl.ProviderResponse, "
			+ " '$.fraugsterApproved') AS fraugsterApproved, "
			+ " esl.id AS eventServiceLogId, u.SSOUserID AS UpdatedBy  "
			+ " FROM "
			+ " EventServiceLog esl left JOIN Event e ON E.id=esl.eventid JOIN [User] u ON u.id=esl.updatedby "
			+ "WHERE "
			+ " e.Accountid=? "
			+ " AND esl.ServiceType=6 And esl.status NOT IN(5,8,9) and e.eventType IN (1,2,3)"
			+ "and JSON_VALUE(esl.ProviderResponse, '$.frgTransId') is not null) "
			+ "SELECT * FROM SelectedID WHERE rnum=1"),
	
	GET_FRAUGSTER_PROVIDER_RESPONSE_BY_CONTACTID("WITH SelectedID AS( "
			+ "SELECT "
			+ " Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum , "
			+ " esl.entityType,esl.EntityID, JSON_VALUE(esl.ProviderResponse, '$.frgTransId') AS frgTransId, "
			+ " JSON_VALUE(esl.ProviderResponse, '$.fraugsterApproved') AS fraugsterApproved,"
			+ " JSON_VALUE(esl.ProviderResponse, '$.score') AS score,"
			+ " esl.id AS eventServiceLogId, u.SSOUserID AS UpdatedBy "
			+ "FROM "
			+ " EventServiceLog esl left JOIN Event e ON E.id=esl.eventid "
			+ " JOIN CONTACT c on c.id=esl.entityid JOIN [User] u ON u.id=esl.updatedby "
			+ "WHERE "
			+ " c.id= ? "
			+ " AND esl.ServiceType=6 And esl.status NOT IN(5,8,9) and e.eventType IN (1,2,3)"
			+ "and JSON_VALUE(esl.ProviderResponse, '$.frgTransId') is not null) "
			+ "SELECT * FROM SelectedID WHERE rnum=1"),
	
	GET_FRAUGSTER_PROVIDER_RESPONSE_FOR_PAYMENT("WITH SelectedID AS( "
			+ "SELECT "
			+ " Row_number() OVER (PARTITION BY esl.EntityID ORDER BY esl.updatedon DESC) rnum , "
			+ " esl.entityType,esl.EntityID, JSON_VALUE(esl.ProviderResponse, '$.frgTransId') AS frgTransId, "
			+ " JSON_VALUE(esl.ProviderResponse, '$.fraugsterApproved') AS fraugsterApproved, "
			+ " esl.id AS eventServiceLogId, u.SSOUserID AS UpdatedBy  "
			+ "FROM "
			+ " EventServiceLog esl left JOIN Event e ON E.id=esl.eventid "
			+ " JOIN CONTACT c on c.id=esl.entityid JOIN [User] u ON u.id=esl.updatedby "
			+ "WHERE "
			+ " c.id= ? "
			+ " AND esl.ServiceType=6 And esl.status NOT IN(5,8,9) and e.eventType = ? AND esl.EntityType=3"
			+ "and JSON_VALUE(esl.ProviderResponse, '$.frgTransId') is not null) "
			+ "SELECT * FROM SelectedID WHERE rnum=1"),
	
	INSERT_FRAUGSTER_SCHEDULAR_DATA("INSERT INTO FraugsterSchedularData (AtlasID, FraugsterTransactionID, Status, AsyncStatus, "
			+ "AsyncStatusDate, CreatedBy) VALUES(?,?,?,?,?,(select Id from [user] where SSOUserID=?))"),
	
	UPDATE_CONTACT_IS_ON_QUEUE_STATUS(
			"UPDATE [Contact] SET UpdatedBy=(select id  from [user] where SSOUserID=?), UpdatedOn = ? , isOnQueue = ? WHERE Id=?"),
	
	GET_FRAUGSTER_PROVIDER_RESPONSE("select JSON_VALUE(esl.ProviderResponse, '$.frgTransId') AS frgTransId,esl.EntityID, "
			+ " JSON_VALUE(esl.ProviderResponse, '$.fraugsterApproved') AS fraugsterApproved from EventServiceLog esl "
			+ " where ServiceType = 6 and esl.status NOT IN(5,8,9) and id =?"),
	
	GET_CONTACTID_BY_ACCOUNTID("select id from Contact Where Accountid = ?"),
	
	UPDATE_FRAUGSTER_SCHEDULAR_DATA_FOR_WL("UPDATE  FraugsterSchedularData "
			+ "  SET   AsyncStatus= ? , AsyncStatusDate= ? , "
			+ " UpdatedBy = (select Id from [user] where SSOUserID=?) , UpdatedOn = ?, Delivered = ? WHERE FraugsterTransactionID = ? "),
	
	UPDATE_FRAUGSTER_SCHEDULAR_DATA_FOR_REASON("UPDATE  FraugsterSchedularData "
			+ "  SET   AsyncStatus= ? , AsyncStatusDate= ? , "
			+ " UpdatedBy = (select Id from [user] where SSOUserID=?) , UpdatedOn = ? , Delivered = ? WHERE FraugsterTransactionID = ? "),
	
	GET_SERVICE_FAILURE_REG_COUNT("SELECT SUM(Sanction_Fail)as Sanction_Fail, SUM(Fraugster_Fail) as Fraugster_Fail, "
			+ "SUM(Eid_Fail) as Eid_Fail, SUM(Blacklist_Fail) as Blacklist_Fail "
			+ "FROM ( "
			+ " SELECT "
			+ " count(CASE WHEN a.SanctionStatus IN (8) THEN 1 ELSE NULL END ) AS Sanction_Fail, "
			+ " count(CASE WHEN a.BlacklistStatus IN (8) THEN 1 ELSE NULL END ) AS Blacklist_Fail, "
			+ " NULL AS Fraugster_Fail , "
			+ " NULL AS Eid_Fail "
			+ " FROM Account A "
			+ " where a.createdon BETWEEN ? AND ? "
			+ " AND a.ComplianceStatus = 4 "
			+ " AND a.type <> 2 "
			+ " UNION "
			+ " SELECT "
			+ " count(CASE WHEN c.SanctionStatus IN (8) THEN 1 ELSE NULL END ) AS Sanction_Fail, "
			+ " count(CASE WHEN c.BlacklistStatus IN (8) THEN 1 ELSE NULL END ) AS Blacklist_Fail, "
			+ " count(CASE WHEN c.FraugsterStatus IN (8) THEN 1 ELSE NULL END )AS Fraugster_Fail , "
			+ " count(CASE WHEN c.EIDStatus IN (8) THEN 1 ELSE NULL END )AS Eid_Fail "
			+ " FROM Contact C JOIN Account acc on acc.id = c.AccountID "
			+ " where c.createdon BETWEEN ? AND ? "
			+ " AND c.ComplianceStatus = 4 "
			+ " ) countTable"), 
	
	GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_PFX (" SELECT NULL AS AccountID, c.id As ContactId , A.type "
			+ " FROM Contact C JOIN Account A ON c.accountid=A.id "
			+ " WHERE "
			+ " c.ComplianceStatus = 4 "
			+ " AND (c.fraugsterstatus IN(8) "
			+ " OR c.SanctionStatus IN(8) "
			+ " OR c.BlacklistStatus IN(8) "
			+ " OR c.EIDStatus IN(8)) "
			+ " AND a.type = 2 "
			+ " and c.createdon BETWEEN ? AND ? "),
	
	GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_CFX ("SELECT acc.id AS AccountID, "
			+ "       NULL   AS ContactId, "
			+ "       acc.type "
			+ "FROM   account acc "
			+ "       JOIN contact con "
			+ "         ON con.accountid = acc.id "
			+ "WHERE  acc.compliancestatus = 4 "
			+ "       AND ( acc.blackliststatus IN( 8 ) "
			+ "              OR acc.sanctionstatus IN( 8 ) ) "
			+ "       AND acc.type <> 2 "
			+ "       AND acc.createdon BETWEEN ? AND ? "
			+ "UNION "
			+ "SELECT NULL AS AccountID, "
			+ "       c.id AS ContactId, "
			+ "       A.type "
			+ "FROM   contact C "
			+ "       JOIN account A "
			+ "         ON c.accountid = A.id "
			+ "WHERE  c.compliancestatus = 4 "
			+ "       AND ( c.blackliststatus IN( 8 ) "
			+ "              OR c.fraugsterstatus IN( 8 ) "
			+ "               ) "
			+ "       AND a.type <> 2 "
			+ "       AND c.createdon BETWEEN ? AND ?"),
	
	INSERT_INTO_REPROCESS_FAILED_REGISTRATION ("INSERT INTO ReproccessFailed " 
            + " (TransType, BatchId, TransId, Status, RetryCount, CreatedBy, CreatedOn)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?)" 
            ),
	
	GET_MAX_BATCH_ID_FROM_REPROCESS_FAILED("SELECT Max(BatchId) AS MaxBatchId FROM ReproccessFailed"),
	
	INSERT_INTO_REPROCESS_FAILED ("INSERT INTO ReproccessFailed " 
            + " (TransType, BatchId, TransId, Status, RetryCount, CreatedBy, CreatedOn)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?)" 
            ),
	
	GET_REPROCESS_ELIGIBLE_REGISTRATIONS("SELECT * FROM ReproccessFailed where BatchId = ? and TransType IN (1, 4)"),
	
	GET_CONTACTS_FOR_CURRENT_ACCOUNT("SELECT CRMContactID, TradeContactID FROM Contact where AccountID = ?"),
	
	GET_PAYEE_CLIENT_DETAILS("SELECT name FROM Account where TradeAccountNumber = ?"),
	
	GET_BENE_CLIENT_TRADE_ACCOUNT_NUMBER("SELECT tradeaccountnumber from account where id = ?"),
	
	GET_PAYMENT_DETAILS(" select "
						+ " po.TransactionDate, "
						+ " poa.vBuyingCurrency, "
						+ " poa.vBeneficiaryAmount, "
						+ " JSON_VALUE(poa.Attributes,'$.beneficiary.payment_reference') as reference, "
						+ " po.ComplianceStatus, "
						+ " po.UpdatedOn "
						+ " from Comp.PaymentOut po "
						+ " join Comp.Account acc on acc.ID = po.AccountID "
						+ " join Comp.PaymentOutAttribute poa on po.ID =poa.ID "
						+ " where acc.TradeAccountNumber=? and "
						+ " poa.vTradeBeneficiaryID=? "
						+ " and po.OrganizationID= "
						+ " (Select id from Comp.Organization where code = ?)"
						+ "  and po.ComplianceStatus <> 6"),
	
	CHECK_IF_BENE_IS_WHITELISTED("Select * from WhiteListBeneficiary where AccountNumber = ? and Deleted = 0"),
	
	GET_LEGAL_ENTITY("select Code from LegalEntity"),
	
	DEVICE_ID_JOIN("JOIN Event e ON A.AccountId = e.AccountID AND e.EventType = 1"  
			+  " JOIN DeviceInfo di ON di.EventID = e.ID"),
	
	ONFIDO_JOIN("JOIN ACCOUNT ac ON A.AccountId = ac.id "),
	
	//AT-5465
	GET_SERVICE_FAILURE_REG_COUNT_FOR_ACTIVE_CUSTOMER("SELECT SUM(Sanction_Fail)as Sanction_Fail, SUM(Fraugster_Fail) as Fraugster_Fail, "
			+ "SUM(Eid_Fail) as Eid_Fail, SUM(Blacklist_Fail) as Blacklist_Fail "
			+ "FROM ( "
			+ " SELECT "
			+ " count(CASE WHEN a.SanctionStatus IN (8) THEN 1 ELSE NULL END ) AS Sanction_Fail, "
			+ " count(CASE WHEN a.BlacklistStatus IN (8) THEN 1 ELSE NULL END ) AS Blacklist_Fail, "
			+ " NULL AS Fraugster_Fail , "
			+ " NULL AS Eid_Fail "
			+ " FROM Account A "
			+ " where a.createdon BETWEEN ? AND ? "
			+ " AND a.ComplianceStatus IN (1, 4) "
			+ " AND a.type <> 2 "
			+ " UNION "
			+ " SELECT "
			+ " count(CASE WHEN c.SanctionStatus IN (8) THEN 1 ELSE NULL END ) AS Sanction_Fail, "
			+ " count(CASE WHEN c.BlacklistStatus IN (8) THEN 1 ELSE NULL END ) AS Blacklist_Fail, "
			+ " count(CASE WHEN c.FraugsterStatus IN (8) THEN 1 ELSE NULL END )AS Fraugster_Fail , "
			+ " count(CASE WHEN c.EIDStatus IN (8) THEN 1 ELSE NULL END )AS Eid_Fail "
			+ " FROM Contact C JOIN Account acc on acc.id = c.AccountID "
			+ " where c.createdon BETWEEN ? AND ? "
			+ " AND c.ComplianceStatus IN (1, 4) "
			+ " ) countTable"),
	
	//AT-5465
	GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_PFX_ACTIVE_CUSTOMER (" SELECT NULL AS AccountID, c.id As ContactId , A.type "
			+ " FROM Contact C JOIN Account A ON c.accountid=A.id "
			+ " WHERE "
			+ " c.ComplianceStatus IN(1,4) "
			+ " AND (c.fraugsterstatus IN(8) "
			+ " OR c.SanctionStatus IN(8) "
			+ " OR c.BlacklistStatus IN(8) "
			+ " OR c.EIDStatus IN(8)) "
			+ " AND a.type = 2 "
			+ " and c.createdon BETWEEN ? AND ? "),
	
	UPDATE_CONTACT_POI_EXISTS_FLAG("update Contact set POIExists = 1 where CRMContactID = ?"),//AT-3391
	
	UPDATE_TMMQ_RETRY_COUNT("UPDATE TransactionMonitoringMQ SET RetryCount = 0"),//Add for AT-4185
	
	//AT-5023
	UPDATE_POST_CARD_TMMQ_RETRY_COUNT("UPDATE PostCardTransactionMonitoringMQ SET RetryCount = 0"),
	
	//AT-5465
	GET_SERVICE_FAILURE_REGISTRATION_DETAILS_FOR_CFX_ACTIVE_CUSTOMER("SELECT acc.id AS AccountID, "
			+ "       NULL   AS ContactId, "
			+ "       acc.type "
			+ "FROM   account acc "
			+ "       JOIN contact con "
			+ "         ON con.accountid = acc.id "
			+ "WHERE  acc.compliancestatus IN(1, 4) "
			+ "       AND ( acc.blackliststatus IN( 8 ) "
			+ "              OR acc.sanctionstatus IN( 8 ) ) "
			+ "       AND acc.type <> 2 "
			+ "       AND acc.createdon BETWEEN ? AND ? "
			+ "UNION "
			+ "SELECT NULL AS AccountID, "
			+ "       c.id AS ContactId, "
			+ "       A.type "
			+ "FROM   contact C "
			+ "       JOIN account A "
			+ "         ON c.accountid = A.id "
			+ "WHERE  c.compliancestatus IN(1, 4) "
			+ "       AND ( c.blackliststatus IN( 8 ) "
			+ "              OR c.fraugsterstatus IN( 8 ) "
			+ "               ) "
			+ "       AND a.type <> 2 "
			+ "       AND c.createdon BETWEEN ? AND ?");
	
	private String query;

	RegistrationQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}

}