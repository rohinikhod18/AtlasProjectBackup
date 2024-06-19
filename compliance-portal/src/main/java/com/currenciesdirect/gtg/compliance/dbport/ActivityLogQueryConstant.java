package com.currenciesdirect.gtg.compliance.dbport;

/**
 * The Enum ActivityLogQueryConstant.
 */
@SuppressWarnings("squid:S1192")
public enum ActivityLogQueryConstant {

	/** The get payment in activity logs. */
	GET_PAYMENT_IN_ACTIVITY_LOGS_BY_ROWS("WITH SelectID AS( "
			+ " SELECT Row_number() "
			+ "      OVER ( "
			+ "       ORDER BY pal.createdon DESC) AS rownum, "
			+ " (SELECT ssouserid "
			+ " FROM   [user] "
			+ " WHERE  id = pal.activityby) AS [User], "
			+ " pal.createdon, "
			+ " pald.[log] As [Activity], "
			+ " ate.module + ' ' + ate.type  AS [ActivityType], "
			+ " pin.id, "
			+ " pin.tradecontractnumber AS contractNumber, "
			+ " pal.comments AS [Comment] "
			+ " FROM paymentin pin "
			+ " JOIN paymentinactivitylog pal "
			+ " ON pin.id = pal.paymentinid "
			+ " JOIN paymentinactivitylogdetail pald "
			+ " ON pal.id = pald.activityid "
			+ " JOIN activitytypeenum ate "
			+ " ON pald.activitytype = ate.id "
			+ " WHERE  pin.accountid = ? ) "
			+ " SELECT * FROM SelectID "
			+ " WHERE  rownum >= ? "
			+ "       AND rownum <= ?"),
	
	/** The get payment in activity logs count. */
	GET_PAYMENT_IN_ACTIVITY_LOGS_COUNT("SELECT COUNT(USER) as TotalCount "
			+ "FROM ( "
			+ "SELECT (SELECT ssouserid "
			+ "FROM [User] "
			+ "WHERE id = pal.activityby) AS [USER], "
			+ "pal.createdon, "
			+ "pald.[log] As [Activity], "
			+ "ate.module + ' ' + ate.type AS [ActivityType], "
			+ "pin.id, "
			+ "pin.tradecontractnumber AS contractNumber, "
			+ "pal.comments AS [Comment] "
			+ " FROM paymentin pin "
			+ "JOIN paymentinactivitylog pal ON "
			+ "pin.id = pal.paymentinid "
			+ "JOIN paymentinactivitylogdetail pald ON "
			+ "pal.id = pald.activityid "
			+ "JOIN activitytypeenum ate ON "
			+ "pald.activitytype = ate.id "
			+ "WHERE pin.accountid = ? ) PIN"),
	
	/** The get payment out activity logs by rows. */
	GET_PAYMENT_OUT_ACTIVITY_LOGS_BY_ROWS("WITH selectid "
			+ "     AS (SELECT Row_number() "
			+ "                  OVER ( "
			+ "                    ORDER BY pal.createdon DESC) AS rownum, "
			+ "                (SELECT ssouserid "
			+ "                 FROM   [user] "
			+ "                 WHERE  id = pal.activityby)     AS [User], "
			+ "                pal.createdon, "
			+ "                pald.[log]                       AS [Activity], "
			+ "                ate.module + ' ' + ate.type      AS [ActivityType], "
			+ "                pout.id, "
			+ "                pout.contractnumber              AS contractNumber, "
			+ "                pal.comments                     AS [Comment] "
			+ "         FROM   paymentout pout "
			+ "                JOIN paymentoutactivitylog pal "
			+ "                  ON pout.id = pal.paymentoutid "
			+ "                JOIN paymentoutactivitylogdetail pald "
			+ "                  ON pal.id = pald.activityid "
			+ "                JOIN activitytypeenum ate "
			+ "                  ON pald.activitytype = ate.id "
			+ "         WHERE  pout.accountid = ?) "
			+ "SELECT * "
			+ "FROM   selectid "
			+ "WHERE  rownum >= ? "
			+ "       AND rownum <= ?"),
	
	/** The get payment out activity logs count. */
	GET_PAYMENT_OUT_ACTIVITY_LOGS_COUNT("SELECT COUNT(USER) as TotalCount "
			+ "FROM ( "
			+ "SELECT (SELECT ssouserid "
			+ "FROM [User] "
			+ "WHERE id = pal.activityby) AS [USER], "
			+ "pal.createdon, "
			+ "pald.[log] As [Activity], "
			+ "ate.module + ' ' + ate.type AS [ActivityType], "
			+ "pout.id, "
			+ "pout.contractnumber AS contractNumber, "
			+ "pal.comments AS [Comment] "
			+ " FROM paymentout pout "
			+ "JOIN paymentoutactivitylog pal ON "
			+ "pout.id = pal.paymentoutid "
			+ "JOIN paymentoutactivitylogdetail pald ON "
			+ "pal.id = pald.activityid "
			+ "JOIN activitytypeenum ate ON "
			+ "pald.activitytype = ate.id "
			+ " WHERE pout.accountid = ? ) PO"),
	
	/** The get registration activity logs. */
	GET_REGISTRATION_ACTIVITY_LOGS("WITH SelectID AS( "
			+ " SELECT Row_number() "
			+ "      OVER ( "
			+ "       ORDER BY pal.createdon DESC) AS rownum, "
			+ " (SELECT ssouserid "
			+ " FROM   [user] "
			+ " WHERE  id = pal.activityby) AS [User], "
			+ " pal.createdon As CreatedOn, "
			+ " pald.[log] As [Activity], "
			+ " ate.module + ' ' + ate.type  AS [ActivityType], "
			+ " pal.comments AS [Comment] "
			+ " FROM ProfileActivityLog pal "
			+ " JOIN ProfileActivityLogDetail pald "
			+ " ON pal.id = pald.activityid "
			+ " JOIN activitytypeenum ate "
			+ " ON pald.activitytype = ate.id "
			+ " WHERE  pal.accountid = ? ) "
			+ " SELECT * FROM SelectID "
			+ " WHERE  rownum >= ? "
			+ "       AND rownum <= ?"),
	
	
	/** The get registration activity logs count. */
	GET_REGISTRATION_ACTIVITY_LOGS_COUNT("SELECT COUNT(USER) as TotalCount "
			+ "FROM ( "
			+ "SELECT (SELECT ssouserid "
			+ "FROM [user] "
			+ "WHERE id = pal.activityby) AS [User], "
			+ "pal.createdon As CreatedOn, "
			+ "pald.[log] As [Activity], "
			+ "ate.module + ' ' + ate.type AS [ActivityType], "
			+ "pal.comments AS [Comment] "
			+ "FROM ProfileActivityLog pal "
			+ "JOIN ProfileActivityLogDetail pald "
			+ "ON pal.id = pald.activityid "
			+ "JOIN activitytypeenum ate "
			+ "ON pald.activitytype = ate.id "
			+ "WHERE pal.accountid = ? ) REG"),
	
	/** The get all (consolidated registration, payment in, payment out) activity logs. */
	GET_ALL_ACTIVITY_LOGS("WITH SelectedAcc AS(" + 
			" SELECT id AS AccountID FROM Account WHERE id= ? " + 
			")," + 
			"PayIn AS(" + 
			"SELECT id AS PayInID, p.tradecontractnumber AS ContractNumber FROM PaymentIn p JOIN SelectedAcc s ON p.accountid=s.AccountID" + 
			")," + 
			"PayOut AS(" + 
			"SELECT id AS PayOutID, p.contractnumber AS ContractNumber FROM PaymentOut p JOIN SelectedAcc s ON p.accountid=s.AccountID" + 
			")," + 
			"combine AS (" + 
			" SELECT ROW_NUMBER() OVER ( ORDER BY [CreatedOn] DESC)  AS 'row_num', *" + 
			" FROM (" + 
			" SELECT " + 
			" ssouserid  AS [USER], " + 
			" pal.createdon AS [CreatedOn], " + 
			" pald.[log] As [Activity], " + 
			" ate.module + ' ' + ate.type  AS [ActivityType], " + 
			" '---' AS [ContractNumber], " + 
			" pal.comments AS [Comment] " + 
			" FROM ProfileActivityLog pal JOIN SelectedAcc s ON pal.accountId=s.accountid " + 
			" JOIN ProfileActivityLogDetail pald ON pal.id = pald.activityid " + 
			" JOIN activitytypeenum ate ON pald.activitytype = ate.id" + 
			" JOIN [User] u ON u.id = pal.activityby" + 
			" UNION" + 
			" SELECT" + 
			" ssouserid  AS [USER], " + 
			" pal.createdon AS [CreatedOn], " + 
			" pald.[log] As [Activity], " + 
			" ate.module + ' ' + ate.type  AS [ActivityType], " + 
			" pin.[ContractNumber], " + 
			" pal.comments AS [Comment] " + 
			" FROM PayIn pin JOIN paymentinactivitylog pal ON pin.PayInID = pal.paymentinid " + 
			" JOIN paymentinactivitylogdetail pald ON pal.id = pald.activityid " + 
			" JOIN activitytypeenum ate ON pald.activitytype = ate.id" + 
			" JOIN [User] u ON u.id = pal.activityby" + 
			" UNION" + 
			" SELECT" + 
			" ssouserid  AS [USER]," + 
			" pal.createdon AS [CreatedOn], " + 
			" pald.[log] As [Activity], " + 
			" ate.module + ' ' + ate.type  AS [ActivityType], " + 
			" pout.[ContractNumber], " + 
			" pal.comments AS [Comment] " + 
			" FROM PayOut pout JOIN paymentoutactivitylog pal ON pout.PayOutId = pal.paymentoutid " + 
			" JOIN paymentoutactivitylogdetail pald ON pal.id = pald.activityid " + 
			" JOIN activitytypeenum ate ON pald.activitytype = ate.id" + 
			" JOIN [User] u ON u.id = pal.activityby" + 
			" )as A" + 
			")" + 
			"SELECT (SELECT COUNT(1) FROM combine) AS totalrecords, *" + 
			"FROM combine " + 
			"WHERE row_num BETWEEN ? AND ? ");

	/** The query. */
	private String query;

	/**
	 * Instantiates a new activity log query constant.
	 *
	 * @param query the query
	 */
	ActivityLogQueryConstant(String query) {
		this.query = query;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return this.query;
	}
}
