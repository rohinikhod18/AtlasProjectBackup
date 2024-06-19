package com.currenciesdirect.gtg.compliance.iam.dbport;

/**
 * The Enum IAMDBQueryConstant.
 */
public enum IAMDBQueryConstant {
	
	GET_USER_ROLE_PERMISSION("SELECT F.[Name],1 AS Include ,0 AS override FROM [Function] F WHERE id IN (SELECT DISTINCT(Functionid) FROM [FunctionGroupMapping] WHERE FunctionGroupID IN (SELECT FunctionGroupId FROM RoleFunctionGroupMapping WHERE RoleId IN (SELECT id FROM Role WHERE SSORoleId in ({ROLES})))) "
			+ "UNION "
			+ "SELECT F.[Name],OFN.Include as Include, 1 as override FROM [FUNCTION] F "
			+ "                 JOIN OverideFunction OFN ON  F.ID =  (SELECT FunctionId FROM OverideFunction WHERE USERID = (SELECT ID FROM [USER] WHERE SSOUserId = ?))"),
	
	GET_USER("select ID from [user] where SSOUserID =? and Deleted =0 and Active =1");
	
	private String query;
	
	IAMDBQueryConstant(String query){
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
	
	

}
