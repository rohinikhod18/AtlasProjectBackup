package com.currenciesdirect.gtg.compliance.iam.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class IAMDBServiceImpl.
 */
@Component("IAMDBServiceImpl")
public class IAMDBServiceImpl extends AbstractDao implements IIAMDBService {

	@Override
	public Role getUserRoleDetails(UserProfile user) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			getUser(connection,user);
			if(null == user.getId()){	
				return user.getRole();
			}
			StringBuilder s1 = new StringBuilder();
			for (int i = 0; i < user.getRole().getNames().size(); i++) {
				s1.append("?,");
			}
			s1.setLength(s1.length() - 1);
			
			statement = connection
					.prepareStatement(IAMDBQueryConstant.GET_USER_ROLE_PERMISSION.getQuery().replace("{ROLES}", s1));
			int i = 1;
			for (String role : user.getRole().getNames()) {
				statement.setString(i++, role);
			}

			statement.setString(i, user.getName());

			rs = statement.executeQuery();

			List<Function> functions = new ArrayList<>();
			while (rs.next()) {
				String functionName = rs.getString("name");
				boolean include = rs.getBoolean("Include");
				boolean override = rs.getBoolean("override");
				if (functions.contains(new Function(functionName)) && override && !include) {
					functions.remove(new Function(functionName));
				} else if (!functions.contains(new Function(functionName))) {
					Function function = new Function();
					function.setName(functionName);
					function.setHasAccess(Boolean.TRUE);
					functions.add(function);
				}
			}
			user.getRole().getFunctions().clear();
			user.getRole().getFunctions().addAll(functions);
			
		} catch (Exception exception) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, exception);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return user.getRole();
	}

	private void getUser(Connection connection, UserProfile user) throws CompliancePortalException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(IAMDBQueryConstant.GET_USER.getQuery());
			statement.setString(1, user.getName());
			rs = statement.executeQuery();
			if(rs.next()){
				user.setId(rs.getInt("ID"));
			}
		} catch (Exception exception) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, exception);
		}finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}		
	}
	
}
