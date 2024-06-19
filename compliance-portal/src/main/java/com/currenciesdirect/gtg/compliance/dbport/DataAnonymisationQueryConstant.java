package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Class DataAnonymisationQueryConstant.
 * 
 * @author deepakk
 */
public enum DataAnonymisationQueryConstant {
	
	GET_CONTACT_DETAILS_WITH_EVENT("SELECT"
			+ " org.Name AS Organization,"
			+ " a.TradeAccountID,"
			+ " a.CRMAccountID,"
			+ " a.TradeAccountNumber,"
			+ " c.TradeContactID,"
			+ " c.CRMContactID"
			+ " FROM Comp.Account a"
			+ " JOIN Comp.Contact c on c.AccountID = a.ID"
			+ " JOIN Comp.Organization org on a.OrganizationID = org.ID"
			+ " WHERE a.ID = ?"),
	
	GET_ACTIVITY_LOGS_OF_CONTACT(
			" SELECT (select SSOUserID from [user] where ID=pal.ActivityBy) As [User], pal.CreatedOn, pald.Log As [Activity], ate.module+' '+ate.type As [ActivityType], pal.Comments AS [Comment] FROM ProfileActivityLog  pal "
			+ " JOIN ProfileActivityLogDetail pald ON pal.id=pald.ActivityId "
			+ " JOIN ActivityTypeEnum ate ON pald.ActivityType=ate.id "
			+ " where  pal.contactId= ? OR (pal.Accountid= ?  AND pal.contactId IS NULL ) "
			+ " ORDER BY pal.CreatedOn desc"),
	
	GET_CONTACT_STATUS_REASON(
			"SELECT sur.Reason , ISNULL(psr.ContactID,-1) AS ContactID FROM StatusUpdateReason sur  LEFT JOIN ProfileStatusReason psr ON psr.StatusUpdateReasonID=sur.id AND  psr.ContactID=?   WHERE  sur.Module IN ('CONTACT','ALL') order by sur.Reason"),
	
	
	INSERT_INTO_DATA_ANONYMIZE("INSERT into DataAnonActivityLog (AccountID, ContactID, InitialApprovalComment, InitialApprovalBy, InitialApprovalDate, CreatedBy, CreatedOn, UpdatedOn) VALUES (?,?,?,?,?,?,?,?)"),	


	UPDATE_ACCOUNT_DATA_ANON("update Account set DataAnonStatus = 1 where ID = ?"),
	
	UPDATE_FINAL_ACCOUNT_DATA_ANON("update Account set DataAnonStatus = 2 where ID = ?"),
	
	UPDATE_CANCEL_ACCOUNT_DATA_ANON("update Account set DataAnonStatus = 0 where ID = ?"),
	
	UPDATE_ON_DATA_ANONYMIZE_REJECT("update DataAnonActivityLog set RejectedComment = ?, RejectedBy = ?, RejectedDate = ? where AccountID = ?"),
	
	UPDATE_ON_DATA_ANON_ACTIVITY_LOG("update DataAnonActivityLog set FinalApprovalComment = ?, FinalApprovalBy = ?, FinalApprovalDate = ? where AccountID = ?"),
	
	DATA_ANON_QUERY(" select "
			+ " acc.TradeAccountNumber  AS TradeAccountNumber,"
			+ " CASE acc.[type] WHEN 2 THEN  con.NAME ELSE acc.NAME END   AS ContactName,"
			+ " acc.[Type] AS Type,"
			+ " CASE acc.[type] WHEN 2 THEN contactstatusEnum.Status ELSE accountstatusEnum.Status END AS status,"
			+ " con.ID AS ContactId,"
			+ " acc.ID as AccountId,"
			+ " dataanon.Id,"
			+ " accattr.vsource,"
			+ " dataanon.InitialApprovalBy,"
			+ " u.ssouserid AS InitialBy,"
			+ " dataanon.InitialApprovalDate,"
			+ " dataanon.FinalApprovalBy,"
			+ " u1.SSOUserID AS FinalBy,"
			+ " dataanon.FinalApprovalDate,"
			+ " acc.DataAnonStatus"
			+ " from comp.DataAnonActivityLog dataanon"
			+ " JOIN comp.Account acc ON acc.ID = dataanon.AccountID"
			+ " AND acc.DataAnonStatus <> 0"
			+ " JOIN comp.Contact con ON con.ID = dataanon.ContactID "
			+ " JOIN comp.AccountAttribute accattr ON dataanon.AccountID = accattr.ID"
			+ " JOIN contactcompliancestatusenum contactstatusEnum ON con.compliancestatus = contactstatusEnum.id"
			+ " JOIN accountcompliancestatusenum accountstatusEnum ON acc.compliancestatus = accountstatusEnum.id"
			+ " LEFT JOIN comp.[User] u ON"
			+ " u.ID = dataanon.InitialApprovalBy"
			+ " LEFT JOIN Comp.[User] u1 ON"
			+ " u1.ID = dataanon.FinalApprovalBy  "),
	
	DATA_ANON_MAIN_QUERY("DECLARE @Offset int = ?,@Rows int = ?; DECLARE @tmpRows int = @Rows*10;"
			+ " WITH TotalRecord AS({DATA_ANON_QUERY} "
			+ " ORDER BY {SORT_FIELD_NAME} {ORDER_TYPE} OFFSET @Offset ROWS FETCH NEXT @tmpRows ROWS ONLY)"
			+ " SELECT "
			+ " TOP (@Rows) *"
			+ " FROM TotalRecord "),
	
	DATA_ANON_COUNT("select "
			+ " Count(1)  AS TotalRows"
			+ " from comp.DataAnonActivityLog dataanon"
			+ " JOIN comp.Account acc ON acc.ID = dataanon.AccountID"
			+ " AND acc.DataAnonStatus<>0 "
			+ " JOIN comp.Contact con ON con.ID = dataanon.ContactID "
			+ " JOIN comp.AccountAttribute accattr ON dataanon.AccountID = accattr.ID"
			+ " JOIN contactcompliancestatusenum contactstatusEnum ON con.compliancestatus = contactstatusEnum.id"
			+ " JOIN accountcompliancestatusenum accountstatusEnum ON acc.compliancestatus = accountstatusEnum.id"
			+ " LEFT JOIN comp.[User] u ON"
			+ " u.ID = dataanon.InitialApprovalBy"
			+ " LEFT JOIN Comp.[User] u1 ON"
			+ " u1.ID = dataanon.FinalApprovalBy "),
    //Added for AT-2527
	GET_COUNT_FROM_ACCOUNTID("SELECT Count(id) as AnonCount from Comp.DataAnonActivityLog WHERE AccountID =?"),
	
	UPDATE_ON_REINITIATE_DATA_ANONYMISATION("update DataAnonActivityLog set  InitialApprovalComment= ?, InitialApprovalBy = ?, InitialApprovalDate = ? where AccountID = ?"),
	
	INSERT_INTO_DATA_ANON_ACTIVITY_LOG_HISTORY("INSERT into DataAnonActivityLogHistory ([ID], [Activity], [Comment], [ActivityBy], [ActivityDate]) VALUES ((SELECT [ID] FROM DataAnonActivityLog where AccountID =?), ?,?,?,?)"),

	SHOW_DATAANON_HISTORY("select ID ,Activity, (select SSOUserID from Comp.[User] where ID=ActivityBy) as ActivityBy, ActivityDate ,Comment FROM DataAnonActivityLogHistory where ID=? ORDER BY ActivityDate desc");   
	
	private String query;

	DataAnonymisationQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query; 
	}
}
