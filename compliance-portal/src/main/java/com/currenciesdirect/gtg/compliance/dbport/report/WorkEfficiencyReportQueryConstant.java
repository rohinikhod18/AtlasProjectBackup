package com.currenciesdirect.gtg.compliance.dbport.report;

/**
 * The Enum ReportQueryConstant.
 * 
 * @author laxmib
 *
 */
public enum WorkEfficiencyReportQueryConstant {
	
	GET_WORK_EFFICIENCY_REPORT_RESOURCETYPE_FILTER(""),
	
	GET_WORK_EFFICIENCY_REPORT_ON_LOAD_INNER_QUERY_WITHOUT_PAGINATION("SELECT * FROM  (SELECT "
				+ "                                               ur.SSOUserID , "
				+ " acc.type                                             acctype, "
				+ " Count(url.id)                                        AS "
				+ " lockedrecords, "
				+ " Count(lockreleasedon)                                AS "
				+ " releasedrecords, "
				+ " AVG(Datediff(second, url.createdon, lockreleasedon)) AS seconds "
				+ " FROM   userresourcelock url "
				+ " JOIN contact con "
				+ " ON url.resourceid = con.id "
				+ " JOIN account acc "
				+ " ON acc.id = con.accountid "
				+ "                                               LEFT JOIN     [User] ur "
				+ "                                                 ON       ur.id = url.userid "
				+ " AND url.id IN (SELECT id "
				+ " FROM   userresourcelock "
				+ " WHERE  Cast(createdon AS DATE) BETWEEN "
				+ " ? AND ? "
				+ " AND userid IN (SELECT DISTINCT( userid "
				+ " ) "
				+ " FROM   userresourcelock) "
				+ " ) "
				+ "                                        where ur.SSOUserID IS NOT NULL AND userid > 0 "
				+ " GROUP  BY userid, ur.SSOUserID , "
				+ " acc.type) AS Result"),
	
	
	
	/*GET_WORK_EFFICIENCY_REPORT_ON_LOAD_INNER_QUERY("DECLARE @Offset int = ?, "
			+ "@PageRows int = ?;"
			+ "WITH SelectedID AS( "
			+ "	SELECT "
			+ "		ur.SSOUserID, "
			+ "		CASE WHEN pfx.[type] IS NOT NULL THEN pfx.[type] "
			+ "			 WHEN cfx.[type] IS NOT NULL THEN cfx.[type] "
			+ "			 WHEN pin.[type] IS NOT NULL THEN pin.[type] "
			+ "			 WHEN pout.[type] IS NOT NULL THEN pout.[type] "
			+ "		END AS acctype, "
			+ "		url.id  AS urlid, "
			+ "		lockreleasedon , "
			+ "		url.createdon "
			+ "	FROM "
			+ "		userresourcelock url "
			+ "	LEFT JOIN contact con ON url.resourceid = con.id AND url.ResourceType = 3 "
			+ "	LEFT JOIN account pfx ON pfx.id = con.accountid "
			+ "	LEFT JOIN account cfx ON cfx.id = url.resourceid AND url.ResourceType = 4 "
			+ "	LEFT JOIN paymentin payin ON payin.id = url.resourceid AND url.ResourceType = 2 "
			+ "	LEFT JOIN Account pin ON payin.accountid=pin.id "
			+ "	LEFT JOIN paymentout payout ON payout.id = url.resourceid AND url.ResourceType = 1 "
			+ "	LEFT JOIN Account pout ON payout.accountid=pout.id "
			+ "	LEFT JOIN [User] ur ON ur.id = url.userid "
			+ "	where url.createdon BETWEEN ? AND ? "
			+ "		AND userid > 0 "
			+ "		 "
			+ ") "
			+ "SELECT "
			+ "	SSOUserID,acctype, "
			+ "    Count( urlid ) AS lockedrecords, "
			+ "	Count( lockreleasedon ) AS releasedrecords, "
			+ "	Sum( Datediff( second, createdon, lockreleasedon )) AS seconds "
			+ "FROM SelectedID "
			+ "GROUP BY "
			+ "	SSOUserID, "
			+ "	acctype "
			+ "ORDER BY "
			+ "	SSOUserID ASC OFFSET @Offset ROWS "
			+ " FETCH NEXT @PageRows ROWS ONLY"),*/
	
	GET_WORK_EFFICIENCY_REPORT_ON_LOAD_INNER_QUERY("DECLARE @Offset int = ?, "
			+ "@PageRows int = ?;"
			+ "WITH SelectedID AS( "
			+ " SELECT "
			+ " ur.SSOUserID, "
			+ " CASE WHEN pfx.[type] IS NOT NULL THEN pfx.[type] "
			+ " WHEN cfx.[type] IS NOT NULL THEN cfx.[type] "
			+ " WHEN pin.[type] IS NOT NULL THEN pin.[type] "
			+ " WHEN pout.[type] IS NOT NULL THEN pout.[type] "
			+ " END AS acctype, "
			+ " url.id  AS urlid, "
			+ "     url.ResourceType,"
			+ " lockreleasedon , "
			+ " url.createdon "
			+ " FROM "
			+ " userresourcelock url "
			+ " LEFT JOIN contact con ON url.resourceid = con.id AND url.ResourceType = 3 "
			+ " LEFT JOIN account pfx ON pfx.id = con.accountid "
			+ " LEFT JOIN account cfx ON cfx.id = url.resourceid AND url.ResourceType = 4 "
			+ " LEFT JOIN paymentin payin ON payin.id = url.resourceid AND url.ResourceType = 2 "
			+ " LEFT JOIN Account pin ON payin.accountid=pin.id "
			+ " LEFT JOIN paymentout payout ON payout.id = url.resourceid AND url.ResourceType = 1 "
			+ " LEFT JOIN Account pout ON payout.accountid=pout.id "
			+ " LEFT JOIN [User] ur ON ur.id = url.userid "
			+ " where url.createdon BETWEEN ? AND ? "
			+ " AND userid > 0  "),
	
	GET_WORK_EFFICIENCY_REPORT_ON_LOAD("  {INNER_QUERY} ) "
			+ "SELECT "
			+ " SSOUserID,acctype,ResourceType, "
			+ " Count( urlid ) AS lockedrecords, "
			+ " Count( lockreleasedon ) AS releasedrecords, "
			+ " AVG( Datediff( SECOND, createdon, lockreleasedon )) AS seconds, "
			+ " count(*) OVER() AS totalrows"
			+ " FROM SelectedID "
			+ " GROUP BY "
			+ " SSOUserID, "
			+ " ResourceType,"
			+ " acctype "
			+ " ORDER BY "
			+ " SSOUserID ASC OFFSET @Offset ROWS "
			+ " FETCH NEXT @PageRows ROWS ONLY"),
	
	GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD_INNER_QUERY("WITH SelectedID AS( "
			+ " SELECT "
			+ " ur.SSOUserID, "
			+ " CASE WHEN pfx.[type] IS NOT NULL THEN pfx.[type] "
			+ " WHEN cfx.[type] IS NOT NULL THEN cfx.[type] "
			+ " WHEN pin.[type] IS NOT NULL THEN pin.[type] "
			+ " WHEN pout.[type] IS NOT NULL THEN pout.[type] "
			+ " END AS acctype, "
			+ " url.id  AS urlid, "
			+ "     url.ResourceType,"
			+ " lockreleasedon , "
			+ " url.createdon "
			+ " FROM "
			+ " userresourcelock url "
			+ " LEFT JOIN contact con ON url.resourceid = con.id AND url.ResourceType = 3 "
			+ " LEFT JOIN account pfx ON pfx.id = con.accountid "
			+ " LEFT JOIN account cfx ON cfx.id = url.resourceid AND url.ResourceType = 4 "
			+ " LEFT JOIN paymentin payin ON payin.id = url.resourceid AND url.ResourceType = 2 "
			+ " LEFT JOIN Account pin ON payin.accountid=pin.id "
			+ " LEFT JOIN paymentout payout ON payout.id = url.resourceid AND url.ResourceType = 1 "
			+ " LEFT JOIN Account pout ON payout.accountid=pout.id "
			+ " LEFT JOIN [User] ur ON ur.id = url.userid "
			+ " where url.createdon BETWEEN ? AND ? "
			+ " AND userid > 0  "),
	
	GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD("  {INNER_QUERY} ) "
			+ "SELECT "
			+ " SSOUserID,acctype,ResourceType, "
			+ " Count( urlid ) AS lockedrecords, "
			+ " Count( lockreleasedon ) AS releasedrecords, "
			+ " AVG( Datediff( SECOND, createdon, lockreleasedon )) AS seconds, "
			+ " count(*) OVER() AS totalrows"
			+ " FROM SelectedID "
			+ " GROUP BY "
			+ " SSOUserID, "
			+ " ResourceType,"
			+ " acctype "),
	
	GET_WORK_EFFICIENCY_REPORT_COUNT("SELECT COUNT(*) AS count from ( {INNER_QUERY} ) AS Result2 "),
	
	GET_USERNAME_FOR_WORK_EFFICIENCY_REPORT("select SSOUserID from [user]"),
	
	GET_WATCHLIST("select id,reason from watchlist"),
	
	GET_WATCHLIST_CATEGORY("select id,reason from watchlist where category in (?)"),
	
	GET_OWNER("select distinct(SSOUserID) from [user] ur Join UserResourceLock url ON ur.id = url.UserID and ur.id <> 0"),
	
	GET_PAYMENT_STATUS("select status from PaymentComplianceStatusEnum ");
	
								
	private String query;

	WorkEfficiencyReportQueryConstant(String query) {
		this.query = query;

	}

	public String getQuery() {
		return this.query;
	}

}