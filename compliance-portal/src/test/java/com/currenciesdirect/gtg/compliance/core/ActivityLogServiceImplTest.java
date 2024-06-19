package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class ActivityLogServiceImplTest {

	private static final String NAME = "cd.comp.system";

	@InjectMocks
	ActivityLogServiceImpl activityLogServiceImpl;

	@Mock
	IActivityLogDBService iActivityLogDBService;

	@Mock
	ActivityLogs activityLogs;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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

	public void getActivityLogsResponceMocking() {
		try {
			when(iActivityLogDBService.getPaymentInActivityLogs(anyObject())).thenReturn(responceMocking());
			when(iActivityLogDBService.getPaymentOutActivityLogs(anyObject())).thenReturn(responceMocking());
			when(iActivityLogDBService.getRegistrationActivityLogs(anyObject())).thenReturn(responceMocking());
			when(iActivityLogDBService.getAllActivityLogs(anyObject())).thenReturn(responceMocking());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void getActivityLogsResponceMock() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iActivityLogDBService.getPaymentInActivityLogs(anyObject())).thenThrow(e);
			when(iActivityLogDBService.getPaymentOutActivityLogs(anyObject())).thenThrow(e);
			when(iActivityLogDBService.getRegistrationActivityLogs(anyObject())).thenThrow(e);
			when(iActivityLogDBService.getAllActivityLogs(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public ActivityLogs responceMocking() {
		ActivityLogs activityLog = new ActivityLogs();
		activityLog.setActivityLogData(activityLogDataWrapperMocking());
		return activityLog;
	}

	public List<ActivityLogDataWrapper> activityLogDataWrapperMocking() {
		List<ActivityLogDataWrapper> activityLogDataWrapper = new ArrayList<>();
		ActivityLogDataWrapper activityLog = new ActivityLogDataWrapper();
		activityLog.setActivity("KYC - identity verification failed");
		activityLog.setActivityType("Signup-STP");
		activityLog.setContactName("--");
		activityLog.setCreatedBy("cd-comp-system");
		activityLog.setCreatedOn("10/07/2020 16:11:06");
		activityLogDataWrapper.add(activityLog);
		return activityLogDataWrapper;
	}

	// test cases
	
	@Test
	public void testGetPaymentInActivityLogs() throws CompliancePortalException {
		ActivityLogs expectedResult = responceMocking();
		getActivityLogsResponceMocking();
		ActivityLogs actualResult = activityLogServiceImpl.getPaymentInActivityLogs(getRequest());
		assertSame(expectedResult.getActivityLogData().get(0).getActivity(),
				actualResult.getActivityLogData().get(0).getActivity());
	}

	@Test
	public void testForGetPaymentInActivityLogs() {
		getActivityLogsResponceMock();
		try {
			activityLogServiceImpl.getPaymentInActivityLogs(getRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetPaymentOutActivityLogs() throws CompliancePortalException {
		ActivityLogs expectedResult = responceMocking();
		getActivityLogsResponceMocking();
		ActivityLogs actualResult = activityLogServiceImpl.getPaymentOutActivityLogs(getRequest());
		assertSame(expectedResult.getActivityLogData().get(0).getActivity(),
				actualResult.getActivityLogData().get(0).getActivity());
	}

	@Test
	public void testForGetPaymentOutActivityLogs() {
		getActivityLogsResponceMock();
		try {
			activityLogServiceImpl.getPaymentOutActivityLogs(getRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetRegistrationActivityLogs() throws CompliancePortalException {
		ActivityLogs expectedResult = responceMocking();
		getActivityLogsResponceMocking();
		ActivityLogs actualResult = activityLogServiceImpl.getRegistrationActivityLogs(getRequest());
		assertSame(expectedResult.getActivityLogData().get(0).getActivity(),
				actualResult.getActivityLogData().get(0).getActivity());
	}

	@Test
	public void testForGetRegistrationActivityLogs() {
		getActivityLogsResponceMock();
		try {
			activityLogServiceImpl.getRegistrationActivityLogs(getRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetAllActivityLogs() throws CompliancePortalException {
		ActivityLogs expectedResult = responceMocking();
		getActivityLogsResponceMocking();
		ActivityLogs actualResult = activityLogServiceImpl.getAllActivityLogs(getRequest());
		assertSame(expectedResult.getActivityLogData().get(0).getActivity(),
				actualResult.getActivityLogData().get(0).getActivity());
	}

	@Test
	public void testForGetAllActivityLogs() {
		getActivityLogsResponceMock();
		try {
			activityLogServiceImpl.getAllActivityLogs(getRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}