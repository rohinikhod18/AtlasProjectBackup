package com.currenciesdirect.gtg.compliance.dbport.cfx;

/**
 * The Enum DBQueryConstant.
 *
 * @author Rajesh
 */
public enum RegistrationCfxDBQueryConstant {

	GET_CFX_REGISTRATION_DETAILS_WITH_EVENT("SELECT "
			+ " cr.ID, "
			+ " conStatus.Status as ContactComplianceStatus, "
			+ " accStatus.Status as AccountComplianceStatus, " 
			+ " cr.name, "
			+ " cr.AccountID AS AccountId, "
			+ " cr.CRMContactID, "
			+ " cr.TradeContactID, "
			+ " cr.complianceDoneOn As RegComplete, "
			+ " cr.createdOn AS RegIn, "
			+ " coun.DisplayName as countryFullName, "
			+ " acc.ComplianceDoneOn AS RegCompleteAcc, "
			+ " acc.CreatedOn AS RegistrationInDate, "
			+ " acc.ComplianceExpiry AS ComplianceExpiry, "
			+ " acc.CRMAccountID, acc.isOnQueue,"
			+ " acc.TradeAccountId, "
			+ " acc.TradeAccountNumber, "
			+ " acc.LegalEntity, "
			+ " org.code AS Organization, "
			+ " conAttrib.Attributes AS contactAttrib, "
			+ " accAttrib.Attributes AS AccountAttrib, "
			+ " ur.id AS userResourceLockId, "
			+ " Lock.ssouserid AS LockedBy, "
			+ " cste.code AS ServiceName, "
			+ " eslc.Id as EventServiceLogId, "
			+ " eslc.EventId as EventId, "
			+ " eslc.entityid, "
			+ " eslc.EntityType as EntityType, "
			+ " eslc.summary as EventSummary, "
			+ " eslc.Status as EventStatus, "
			+ " event.ssouserid AS EventUpdatedBy, "
					+ " eslc.updatedOn as EventUpdatedOn, "
					+ "    acc.DataAnonStatus     AS dataAnonStatus "
			+ "FROM Contact AS cr "
					+ " JOIN Account acc ON acc.id=cr.accountId "
					+ " JOIN Organization org ON org.id=cr.OrganizationID "
					+ " JOIN Country coun ON coun.id = cr.country "
					+ " JOIN ContactAttribute conAttrib ON conAttrib.ID = cr.ID "
					+ " JOIN AccountAttribute accAttrib ON accAttrib.ID=cr.AccountID "
					+ " JOIN ContactComplianceStatusEnum conStatus ON conStatus.ID=cr.ComplianceStatus "
					+ " LEFT JOIN Event eve ON eve.accountid=acc.id "
			+ " AND PaymentInID is null "
			+ " and PaymentOutID is null "
					+ " LEFT JOIN EventServiceLog eslc ON eslc.eventid=eve.id  "
			+ " LEFT JOIN compliance_servicetypeenum cste ON cste.id = eslc.servicetype "
					+ " LEFT JOIN AccountComplianceStatusEnum accStatus ON accStatus.ID=acc.ComplianceStatus "
			+ " LEFT JOIN UserResourceLock ur on acc.Id = ur.ResourceID  AND ur.LockReleasedOn IS NULL "
			+ " AND ur.ResourceType = 4 "
			+ " LEFT JOIN [user] Lock ON lock.id = ur.createdby "
			+ " LEFT JOIN [user] event ON event.id = eslc.updatedBy "
			+ "where acc.id = (SELECT accountid FROM Contact WHERE id = ?) AND (cr.Deleted = 0 OR cr.Deleted IS NULL)"
					+ " order by eslc.updatedOn desc"),
	
	/*INSERT_USER_RESOURCE_LOCK_BY_ACCOUNT_ID("INSERT INTO UserResourceLock ([UserID], [ResourceType]," 
			+ " [ResourceID], [LockReleasedOn], [CreatedBy], [CreatedOn],[WorkflowTime]) "
			+ " SELECT (select Id from [user] where SSOUserID=?),?,?,?,(select Id from [user] where SSOUserID=?),?,? FROM Contact WHERE AccountId=?"),*/
	
	INSERT_USER_RESOURCE_LOCK_BY_ACCOUNT_ID("INSERT INTO UserResourceLock ([UserID], [ResourceType], "
			+ " [ResourceID], [LockReleasedOn], [CreatedBy], [CreatedOn],[WorkflowTime]) VALUES "
			+ "((select Id from [user] where SSOUserID= ?), "
			+ "?,?,?,(select Id from [user] where SSOUserID= ?) "
			+ ",?,?)"),
				
	UPDATE_USER_RESOURCE_LOCK_BY_ACCOUNT_ID(
			"UPDATE [UserResourceLock] SET LockReleasedOn= ? " 
			+ "WHERE ResourceID IN (select ID from Account where ID=?) and LockReleasedOn is null"),
	
	GET_RECORDLOCK_STATUS_BY_ACCOUNT_ID("select * from UserResourceLock  where ResourceID IN(select ID FROM Account WHERE ID=?) and ResourceType=? and LockReleasedOn is null"),
	
	GET_ACTIVITY_LOGS_OF_ACCOUNT_ID("SELECT (SELECT name FROM contact WHERE id=pal.contactid) As ContactName,"
			+ "(SELECT name FROM account WHERE id=pal.AccountID) As AccountName,pal.contactId,(select SSOUserId from [user] where ID=pal.ActivityBy) As [User],"
			+ "pal.CreatedOn, pald.Log As [Activity], ate.module+' '+ate.type As [ActivityType], pal.Comments AS [Comment] "
			+ " FROM ProfileActivityLog  pal JOIN ProfileActivityLogDetail pald ON pal.id=pald.ActivityId AND pal.AccountID =?"
			+ " JOIN ActivityTypeEnum ate ON pald.ActivityType=ate.id ORDER BY pal.CreatedOn DESC"),
	
	GET_PROFILE_STATUS_REASON_BY_ACCOUNT_ID("SELECT sur.Reason , ISNULL(psr.ContactID,-1) AS ContactID FROM StatusUpdateReason sur "
			+" LEFT JOIN ProfileStatusReason psr ON psr.StatusUpdateReasonID=sur.id AND  psr.ContactID IN (SELECT ID FROM CONTACT WHERE AccountID=?) "
			+"WHERE  sur.Module IN ('CONTACT','ALL') order by sur.Reason"),
	
	INSERT_WATCHLIST_BY_ACCOUNT_ID("INSERT INTO [ContactWatchList] ( [Reason], [Contact], [CreatedBy], [CreatedOn] ) " 
			+ "select (SELECT  [Id] FROM [Watchlist] WHERE Reason = ?), ID,(select Id from [user] where SSOUserID=?) , ? FROM CONTACT where AccountId=?"),
	
	INSERT_REASON_ACCOUNT_ID(" INSERT INTO [ProfileStatusReason] ([OrganizationID], [AccountID], [ContactID], [StatusUpdateReasonID], [CreatedBy], [CreatedOn], [UpdatedBy], [UpdatedOn])"
			+ "select (SELECT Id FROM Organization WHERE Code=?),?,ID,(SELECT Id FROM StatusUpdateReason WHERE Module IN ('CONTACT','ALL') AND Reason=?),(select Id from [user] where SSOUserID=?),?,(select Id from [user] where SSOUserID=?),? FROM CONTACT where AccountId=?"),
	
	UPDATE_ACCOUNT_STATUS(
			"UPDATE [Account] SET ComplianceStatus=(SELECT Id FROM ContactComplianceStatusEnum WHERE Status=?), AccountStatus= ? , ComplianceDoneOn = ? , ComplianceExpiry = ? , isOnQueue= ? WHERE Id=?"),
	
	GET_CFX_PROFILE_ACTIVITY_LOGS_BY_ROWS("SELECT  ac.* FROM (SELECT  Row_number() over (ORDER BY pal.createdon desc) AS rownum,(select SSOUserId from [user] where ID=pal.ActivityBy) AS [User], "
			+ "pal.createdon, pald.log  AS [Activity], ate.module + ' ' + ate.type AS [ActivityType], pal.Comments AS [Comment] FROM   profileactivitylog pal "
			+ "JOIN profileactivitylogdetail pald  ON pal.id = pald.activityid AND pal.AccountId=? " 
			+ "JOIN activitytypeenum ate  ON pald.activitytype = ate.id ) as ac where rownum >= ? and rownum <= ?"),
	
	GET_ACCOUNT_CONTACT_IDS_BY_ACCCOUNID("select top 1 c.TradeContactId, c.CRMContactID,a.CRMAccountID,a.TradeAccountID,a.TradeAccountNumber FROM Account a left join Contact c  on a.id=c.AccountID where a.id=? "),
	
	UPDATE_ACCOUNT_IS_ON_QUEUE_STATUS("UPDATE [Account] SET UpdatedBy=(select id  from [user] where SSOUserID=?),"
			+ "  UpdatedOn = ? , isOnQueue= ? WHERE Id=?"),
	
	UPDATE_CONTACT_STATUS("UPDATE [Contact] SET ComplianceStatus=(SELECT Id FROM ContactComplianceStatusEnum WHERE Status=?) "
			+"WHERE AccountId=?"),
	
	GET_CFX_DASHBOARD_ACCOUNT_CONTACT_DETAILS("SELECT c.ID, " + 
			" c.name, " + 
			" c.AccountID AS AccountId, " + 
			" c.CRMContactID, " + 
			" c.TradeContactID, " + 
			" c.complianceDoneOn As RegComplete, " + 
			" c.createdOn AS RegIn," + 
		    " c.POIExists AS poiExists, " +//AT-3391
			" ca.[Attributes] AS contactAttrib," + 
			" conStatus.Status as ContactComplianceStatus,  " + 
			" a.ComplianceDoneOn AS RegCompleteAcc, " + 
			" a.CreatedOn AS RegistrationInDate, " + 
			" a.ComplianceExpiry AS ComplianceExpiry, " + 
			" a.CRMAccountID, " + 
			" a.isOnQueue, " + 
			" a.TradeAccountId, " + 
			" a.TradeAccountNumber, " + 
			" a.LegalEntity, " + 
			" a.DataAnonStatus AS dataAnonStatus, " + 
			" a.AccountTMFlag AS accountTMFlag, " +
			" a.IntuitionRiskLevel AS riskLevel, " + //Added for AT-4190
			" a.Version AS AccountVersion, " +
			" aa.[Attributes] AS AccountAttrib, " + 
			" accStatus.Status as AccountComplianceStatus, " + 
			" coun.DisplayName as countryFullName, " + 
			" org.code AS Organization, " + 
			" ur.id AS userResourceLockId, " + 
			" usr.ssouserid AS LockedBy " + 
			"FROM Contact c " + 
			"JOIN ContactAttribute ca on ca.ID = c.ID " + 
			"JOIN ContactComplianceStatusEnum conStatus ON conStatus.ID = c.ComplianceStatus " + 
			"JOIN Account a on a.ID = c.AccountID " + 
			"JOIN AccountAttribute aa on aa.ID = a.ID " + 
			"JOIN AccountComplianceStatusEnum accStatus ON accStatus.ID = a.ComplianceStatus " + 
			"JOIN Organization org on org.ID = c.OrganizationID " + 
			"JOIN Country coun on coun.ID = c.Country " + 
			"LEFT JOIN UserResourceLock ur on a.Id = ur.ResourceID  AND ur.LockReleasedOn IS NULL AND ur.ResourceType = 4 " + 
			"LEFT JOIN [user] usr ON usr.id = ur.createdby " + 
			"Where a.ID = (SELECT accountid FROM Contact WHERE id = ?) AND (c.Deleted = 0 OR c.Deleted IS NULL)"),
	
	GET_CFX_DASHBOARD_EVENTS_DETAILS("SELECT cste.code AS ServiceName, " + 
			" esl.Id as EventServiceLogId, " + 
			" esl.EventId as EventId, " + 
			" esl.Entityid, " + 
			" esl.EntityType as EntityType, " + 
			" esl.summary as EventSummary, " + 
			" esl.Status as EventStatus, " + 
			" esl.updatedOn as EventUpdatedOn," + 
			" usr.ssouserid AS EventUpdatedBy" + 
			" FROM Event e " + 
			" Join EventServiceLog esl on esl.EventID = e.ID AND e.PaymentInID is null And e.PaymentOutID is null" + 
			" JOIN Compliance_ServiceTypeEnum cste on cste.ID = esl.ServiceType " + 
			" JOIN [User] usr on usr.ID = esl.UpdatedBy " + 
			" WHERE e.AccountID = ?" + 
			" ORDER BY esl.UpdatedOn desc");
	
	private String query;

	RegistrationCfxDBQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}

}