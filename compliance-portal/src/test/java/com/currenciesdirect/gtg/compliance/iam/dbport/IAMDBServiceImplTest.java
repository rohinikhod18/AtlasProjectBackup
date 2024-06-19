package com.currenciesdirect.gtg.compliance.iam.dbport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class IAMDBServiceImplTest {

	@InjectMocks
	IAMDBServiceImpl iAMDBServiceImpl;

	@Mock
	private UserProfile user;

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		try {
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}

	private static final String Name = "cd.comp.system";

	public UserProfile getUser() {
		UserProfile userprof = new UserProfile();
		UserPermission userPerm = new UserPermission();

		
		userprof.setId(1);
		userprof.setName(Name);
		userprof.setPreferredUserName("null null");

		userPerm.setCanWorkOnCFX(false);
		userPerm.setCanWorkOnPFX(true);
		userPerm.setCanViewRegistrationQueue(true);
		userPerm.setCanViewRegistrationDetails(true);
		userPerm.setCanViewPaymentInQueue(true);
		userPerm.setCanViewPaymentInDetails(true);
		userPerm.setCanViewPaymentOutQueue(true);
		userPerm.setCanViewPaymentOutDetails(true);
		userPerm.setCanViewRegistrationReport(true);
		userPerm.setCanViewPaymentInReport(true);
		userPerm.setCanViewPaymentOutReport(true);
		userPerm.setCanViewWorkEfficiacyReport(true);
		userPerm.setCanManageWatchListCategory1(true);
		userPerm.setCanApproveDataAnon(false);
		userPerm.setCanManageWatchListCategory2(true);
		userPerm.setCanUnlockRecords(true);
		userPerm.setCanViewDashboard(true);
		userPerm.setCanManageFraud(true);
		userPerm.setCanManageCustom(true);
		userPerm.setCanManageEID(true);
		userPerm.setCanManageSanction(true);
		userPerm.setCanManageSanction(true);
		userPerm.setCanManageBlackList(true);
		userPerm.setCanDoAdministration(true);
		userPerm.setIsReadOnlyUser(true);
		userPerm.setCanManageBeneficiary(true);
		userPerm.setCanInitiateDataAnon(true);
		userPerm.setCanApproveDataAnon(true);
		userPerm.setCanNotLockAccount(true);
		userprof.setPermissions(userPerm);
		Role role = new Role();
		Set<String> name = new HashSet<>();
		name.add("ATLAS_DATA_ANON_APPROVER");
		name.add("ATLAS_DATA_ANON_INITIATOR");
		name.add("ATLAS_DEPT_HEAD");
		role.setNames(name);
		userprof.setRole(role);
		return userprof;
	}

	
	public Role getRole()
	{
		List<Function> listFunctions = new ArrayList<>();
		Function function = new Function();

		function.setName("canWorkOnPFX");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		Role role = new Role();
		Set<String> name = new HashSet<>();
		name.add("ATLAS_DATA_ANON_APPROVER");
		name.add("ATLAS_DATA_ANON_INITIATOR");
		name.add("ATLAS_DEPT_HEAD");
		role.setNames(name);
		role.setFunctions(listFunctions);
		return role;
		
	}
	public void setMockForGetUserRoleDetails()
	{
		try {
			when(preparedStatement.executeQuery()).thenReturn(rs);
			  when(rs.getInt("ID")).thenReturn(1);
			  when(rs.getString("name")).thenReturn("canWorkOnPFX");
			  when(rs.getBoolean("Include")).thenReturn(true);
			  when(rs.getBoolean("override")).thenReturn(false);
			 
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testGetUserRoleDetails() {
		try {
			setMockForGetUserRoleDetails();
			Role expectedResult=getRole();
			Role actualResult=iAMDBServiceImpl.getUserRoleDetails(getUser());
			assertEquals(expectedResult.getFunctions().get(0).getName(),actualResult.getFunctions().get(0).getName());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForGetUserRoleDetails() {
		try {
			iAMDBServiceImpl.getUserRoleDetails(getUser());
			} catch (CompliancePortalException e) {
		    assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}

}
