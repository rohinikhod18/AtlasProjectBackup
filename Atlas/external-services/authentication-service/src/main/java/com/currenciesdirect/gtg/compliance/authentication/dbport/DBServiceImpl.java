package com.currenciesdirect.gtg.compliance.authentication.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.authentication.core.IDBService;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.AuthRequest;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.RoleFunction;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.User;
import com.currenciesdirect.gtg.compliance.authentication.exception.AuthenticationException;
import com.currenciesdirect.gtg.compliance.authentication.exception.Errorname;
import com.currenciesdirect.gtg.compliance.authentication.util.PasswordUtil;


public class DBServiceImpl extends AbstractDao implements IDBService {

	private static IDBService dbServiceImpl = null;
	
	public static final String LOGIN_SERVICE_QUERY = "SELECT a.FirstName,a.LastName,a.Password, a.PasswordSalt,a.ID as UserID,b.RoleID FROM [dbo].[User] a LEFT JOIN UserRole b on a.ID = b.UserID WHERE a.LoginName=?";
	public static final String GET_ORGANISATION_USER = "select OrganizationID from OrganizationUser where UserID=?";
	public static final String ROLE_ID_QUERY = "SELECT * FROM [dbo].[RoleFunction] WHERE RoleID=?";
	public static final String ROLE_QUERY="SELECT Name FROM Role WHERE ID=?";
	
	public static final String USER_DETAILS_QUERY = "SELECT a.FirstName,a.LastName,a.Password, a.PasswordSalt,a.ID as UserID,b.RoleID,c.OrganizationID,e.Name, d.ID,d.FunctionID,d.CanView,d.CanCreate,d.CanModify,d.CanDelete FROM [dbo].[User] a LEFT JOIN UserRole b on a.ID = b.UserID LEFT JOIN OrganizationUser c on a.ID = c.UserID LEFT JOIN Role e on b.RoleID = e.ID LEFT JOIN RoleFunction d on b.RoleID = d.RoleID WHERE a.LoginName=?";
	/**
	 * The Constant logger
	 */
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(DBServiceImpl.class);
	
	private DBServiceImpl() {
		
	}
	
	public static IDBService getInsance() {
		if(dbServiceImpl == null) {
			dbServiceImpl = new DBServiceImpl();
		}
		return dbServiceImpl;
	}
	
	
	/**
	 * @param userName
	 * @param password
	 * @return
	 * @throws ExceptionHandler
	 * 
	 */
	public User getUserDetails(AuthRequest authRequest) throws AuthenticationException {
		LOG.info("DBServiceImpl start: getUserDetails method start");
		List<RoleFunction> roleFunctionList = new ArrayList<RoleFunction>();
		Boolean status = false;
		User user = new User();
		Connection conn = null;
		//String userName = "TorFxOz_admin_aml", password = "9S1oakXtWPhoQMQDBr96Y//d8dYKyM3m9q7jOAYfrFM=";
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3=null;
		PreparedStatement stmt4=null;
		ResultSet resultSet = null;
		PasswordUtil passwordUtil = new PasswordUtil();
		String passwordSalt = null;
		String resultPassword = null;
		try {
			conn = getConnection();

			/*		 For Role ID and User ID 
			stmt = conn
					.prepareStatement(LOGIN_SERVICE_QUERY);
			stmt.setString(1, authRequest.getUserName());
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				user.setRoleId(resultSet.getInt("RoleID"));
				user.setUserId(resultSet.getInt("UserID"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				String passwordSalt = resultSet.getString("PasswordSalt");
				String resultPassword = resultSet.getString("Password");
				status = passwordUtil.verifyPassword(passwordSalt,
						resultPassword, authRequest.getPassWord());
			}
			if(status) {
			 For Organization Code 
			stmt1 = conn
					.prepareStatement(GET_ORGANISATION_USER);

			stmt1.setInt(1, user.getUserId());
			resultSet = stmt1.executeQuery();
			while (resultSet.next()) {
				user.setOrgCode(resultSet.getInt("OrganizationID"));
			}

			 For RoleFunction matrix and Function ID 
			stmt2 = conn.prepareStatement(ROLE_ID_QUERY);
			stmt2.setInt(1, user.getRoleId());
			resultSet = stmt2.executeQuery();
			while (resultSet.next()) {
				RoleFunction roleFunction = new RoleFunction();
				roleFunction.setID(resultSet.getInt("ID"));
				roleFunction.setFunctionId(resultSet.getInt("FunctionID"));
				roleFunction.setCanDelete(resultSet.getBoolean("CanDelete"));
				roleFunction.setCanModify(resultSet.getBoolean("CanModify"));
				roleFunction.setCanView(resultSet.getBoolean("CanView"));
				roleFunction.setCanCreate(resultSet.getBoolean("CanCreate"));
				roleFunctionList.add(roleFunction);
			}
			user.setRoleFuctionList(roleFunctionList);
			
			*//** For Getting user role name*//*
			stmt3=conn.prepareStatement(ROLE_QUERY);
			stmt3.setInt(1, user.getRoleId());
			resultSet = stmt3.executeQuery();
			while(resultSet.next())
			{
				user.setRole(resultSet.getString("Name"));
			}
			
			
   } else {
	   throw new AuthenticationException(Errorname.AUTHENTICATION_FAILURE_EXCEPTION);
   }*/
			/* For Role ID and User ID */
			stmt4 = conn
					.prepareStatement(USER_DETAILS_QUERY);
			stmt4.setString(1, authRequest.getUserName());
			resultSet = stmt4.executeQuery();
			while (resultSet.next()) {
				user.setRoleId(resultSet.getInt("RoleID"));
				user.setUserId(resultSet.getInt("UserID"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				 passwordSalt = resultSet.getString("PasswordSalt");
				 resultPassword = resultSet.getString("Password");
			
				user.setOrgCode(resultSet.getInt("OrganizationID"));
				user.setRole(resultSet.getString("Name"));
				RoleFunction roleFunction = new RoleFunction();
				roleFunction.setID(resultSet.getInt("ID"));
				roleFunction.setFunctionId(resultSet.getInt("FunctionID"));
				roleFunction.setCanDelete(resultSet.getBoolean("CanDelete"));
				roleFunction.setCanModify(resultSet.getBoolean("CanModify"));
				roleFunction.setCanView(resultSet.getBoolean("CanView"));
				roleFunction.setCanCreate(resultSet.getBoolean("CanCreate"));
				roleFunctionList.add(roleFunction);
			}
			user.setRoleFuctionList(roleFunctionList);
			status = passwordUtil.verifyPassword(passwordSalt,
					resultPassword, authRequest.getPassWord());
			if(!status) {
				 throw new AuthenticationException(Errorname.AUTHENTICATION_FAILURE_EXCEPTION);
			}
		} catch (AuthenticationException e) {
		  throw e;	
		} catch (Exception e) {
			
			throw new AuthenticationException(Errorname.AUTHENTICATION_GENERIC_EXCEPTION,e); //ExceptionHandler(BankingServiceErrors.INVALID_USER);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(stmt);
			closeConnection(conn);
		}
		LOG.info("DBServiceImpl: getUserDetails method end");
		return user;
	}
	
	

	
	
}
