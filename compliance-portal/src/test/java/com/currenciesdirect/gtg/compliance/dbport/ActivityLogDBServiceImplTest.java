package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class ActivityLogDBServiceImplTest {

	private static final String NAME = "cd.comp.system";

	@InjectMocks
	ActivityLogDBServiceImpl activityLogDBServiceImpl;

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

	int offset = 0;
	int rowsToFetch = 40;
	String query = "";
	String countQuery = "";

	@Before
	public void setup() throws SQLException {
		MockitoAnnotations.initMocks(this);
		connection = org.mockito.Mockito.mock(Connection.class);
		ResultSet rs = mock(ResultSet.class);
		 when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
		Mockito.when(preparedStatement.execute()).thenReturn(true);
		Mockito.when(rs.getString(Constants.ACTIVITY)).thenReturn("KYC - identity verification failed");
		Mockito.when(rs.getString(Constants.USER)).thenReturn("cd-comp-system");
		Mockito.when(rs.getString(Constants.ACTIVITY_TYPE)).thenReturn("Signup-STP");
		Mockito.when(rs.getTimestamp(Constants.CREATED_ON)).thenReturn(Timestamp.valueOf("2019-11-28 12:10:52"));
		Mockito.when(rs.getString(Constants.CREATED_ON)).thenReturn("2019-11-28 12:10:52");
		Mockito.when(rs.getString(Constants.COMMENT)).thenReturn("-");
		Mockito.when(rs.getInt("TotalCount")).thenReturn(0);
	}

	public void mockConnection()
	{
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public UserProfile getUser() {
		UserProfile userprof = new UserProfile();
		UserPermission userPerm = new UserPermission();

		Role role = new Role();
		userprof.setId(1);
		userprof.setName(NAME);
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

		Set<String> name = new HashSet<>();
		name.add("ATLAS_DATA_ANON_APPROVER");
		name.add("ATLAS_DATA_ANON_INITIATOR");
		name.add("ATLAS_DEPT_HEAD");
		role.setNames(name);
		role.setFunctions(getFunctionInfo());
		userprof.setRole(role);
		return userprof;
	}

	private List<Function> getFunctionInfo() {
		List<Function> listFunctions = new ArrayList<>();
		Function function = new Function();
		function.setName("canWorkOnPFX");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewRegistrationQueue");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewPaymentInQueue");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewPaymentInReport");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewPaymentOutReport");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canUnlockRecords");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewDashboard");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageFraud");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageCustom");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageEID");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageSanction");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageBlackList");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canDoAdministration");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewWorkEfficiancyReport");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageWatchListCategory1");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageWatchListCategory2");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canManageBeneficiary");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canViewDataAnonQueue");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canInitiateDataAnon");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canApproveDataAnon");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canWorkOnCFX");
		function.setHasAccess(false);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("isReadOnlyUser");
		function.setHasAccess(false);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		function.setName("canNotLockAccount");
		function.setHasAccess(false);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);

		return listFunctions;
	}

	public ActivityLogRequest getRequest() {
		UserProfile userinfo = getUser();
		ActivityLogRequest activityLogRequest = new ActivityLogRequest();
		activityLogRequest.setAccountId(49774);
		activityLogRequest.setMaxRecord(10);
		activityLogRequest.setMinRecord(1);
		activityLogRequest.setUser(userinfo);
		return activityLogRequest;
	}

	public ActivityLogs activityLogDataWrapperMocking() {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataWrapper = new ArrayList<>();
		ActivityLogDataWrapper activityLog = new ActivityLogDataWrapper();
		activityLog.setActivity("KYC - identity verification failed");
		activityLog.setActivityType("Signup-STP");
		activityLog.setContactName("--");
		activityLog.setCreatedBy("cd-comp-system");
		activityLog.setCreatedOn("2019-11-28 12:10:52");
		activityLogDataWrapper.add(activityLog);
		activityLogs.setActivityLogData(activityLogDataWrapper);
		return activityLogs;
	}

	// test cases
	@Test
	public void testGetPaymentInActivityLogs() {
		mockConnection();
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs expectedResult = activityLogDataWrapperMocking();
			ActivityLogs actualResult = activityLogDBServiceImpl.getPaymentInActivityLogs(request);
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(), actualResult.getActivityLogData().get(0).getActivity());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetPaymentInActivityLogs() {
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs actualResult = activityLogDBServiceImpl.getPaymentInActivityLogs(request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}

	@Test
	public void testGetPaymentOutActivityLogs() {
		mockConnection();
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs expectedResult = activityLogDataWrapperMocking();
			ActivityLogs actualResult = activityLogDBServiceImpl.getPaymentOutActivityLogs(request);
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(), actualResult.getActivityLogData().get(0).getActivity());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetPaymentOutActivityLogs() {
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs actualResult = activityLogDBServiceImpl.getPaymentOutActivityLogs(request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}

	@Test
	public void testGetRegistrationActivityLogs() {
		mockConnection();
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs expectedResult = activityLogDataWrapperMocking();
			ActivityLogs actualResult = activityLogDBServiceImpl.getRegistrationActivityLogs(request);
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(), actualResult.getActivityLogData().get(0).getActivity());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetRegistrationActivityLogs() {
		
		try {
			ActivityLogRequest request = getRequest();			
			ActivityLogs actualResult= activityLogDBServiceImpl.getRegistrationActivityLogs(request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
	@Test
	public void testGetAllActivityLogs() {
		mockConnection();
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs expectedResult= activityLogDataWrapperMocking();
			ActivityLogs actualResult = activityLogDBServiceImpl.getAllActivityLogs(request);
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(), actualResult.getActivityLogData().get(0).getActivity());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetAllActivityLogs() {
		try {
			ActivityLogRequest request = getRequest();
			ActivityLogs actualResult= activityLogDBServiceImpl.getAllActivityLogs(request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
}
