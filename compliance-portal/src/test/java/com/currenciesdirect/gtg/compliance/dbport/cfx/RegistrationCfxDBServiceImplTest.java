package com.currenciesdirect.gtg.compliance.dbport.cfx;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
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

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegistrationCfxDBServiceImplTest {

	@InjectMocks
	RegistrationCfxDBServiceImpl registrationCfxDBServiceImpl;
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet resultSet;
	@Mock
	ResultSet countRs;
	@Mock
	AbstractDao abstractDao;

	private static final String Name = "cd.comp.system";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(preparedStatement.executeQuery()).thenReturn(resultSet);
			when(preparedStatement.executeUpdate()).thenReturn(1);
			when(preparedStatement.getResultSet()).thenReturn(resultSet);
			Mockito.when(resultSet.next()).thenReturn(false).thenReturn(true);
			Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(connection.prepareStatement(anyString(),anyInt())).thenReturn(preparedStatement);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public UserProfile getUser() {
		UserProfile userprof = new UserProfile();
		UserPermission userPerm = new UserPermission();
		Role role = new Role();
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
	public Page getpage()
	{
		Page page= new Page();
		page.setCurrentPage(1);
		page.setMaxRecord(40);
		page.setMinRecord(1);
		page.setPageSize(40);
		page.setTotalPages(1);
		page.setTotalRecords(1);
		return page;
		
	}

	public LockResourceDBDto getLockResourceDBDto()
	{
		LockResourceDBDto lockResourceDBDto= new LockResourceDBDto();
		lockResourceDBDto.setCreatedBy(Name);
		lockResourceDBDto.setCreatedOn(Timestamp.valueOf("2021-04-12 13:45:08.367"));
		lockResourceDBDto.setResourceId(54087);
		lockResourceDBDto.setResourceType("ACCOUNT");
		lockResourceDBDto.setUserId(Name);	
		return lockResourceDBDto;	
	}
	
	public LockResourceResponse getLockResourceResponse()
	{
		LockResourceResponse lockResourceResponse= new LockResourceResponse();
		lockResourceResponse.setUserResourceId(696832);
		lockResourceResponse.setStatus("Pass");
		lockResourceResponse.setResourceId(54087);
		lockResourceResponse.setName(Name);
		return lockResourceResponse;
	}
	public void setMockForLockResourceForMultiContact()
	{
		try {
			when(resultSet.getInt(1)).thenReturn(696832);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	
	@Test
	public void testinsertLockResourceForMultiContact()
	{
		setMockForLockResourceForMultiContact();
		LockResourceResponse expectedResult= getLockResourceResponse();
		try {
			LockResourceResponse actualresult= registrationCfxDBServiceImpl.insertLockResourceForMultiContact(getLockResourceDBDto());
        	assertEquals(expectedResult.getResourceId(), actualresult.getResourceId());
		
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	
	
	public LockResourceDBDto getLockResourceDBDtoForUpdateLock()
	{
		LockResourceDBDto lockResourceDBDto= new LockResourceDBDto();
		lockResourceDBDto.setCreatedBy(Name);
		lockResourceDBDto.setCreatedOn(Timestamp.valueOf("2021-04-12 13:45:08.367"));
		lockResourceDBDto.setResourceId(54087);
		lockResourceDBDto.setResourceType("ACCOUNT");
		lockResourceDBDto.setLockReleasedOn(Timestamp.valueOf("2021-04-12 14:12:23.371"));
		lockResourceDBDto.setId(696834);
		lockResourceDBDto.setUserId(Name);	
		return lockResourceDBDto;	
	}
	
	
	public LockResourceResponse getLockResourceResponseForUpdateLock()
	{
		LockResourceResponse lockResourceResponse= new LockResourceResponse();
		lockResourceResponse.setUserResourceId(696834);
		lockResourceResponse.setStatus("Pass");
		lockResourceResponse.setResourceId(54087);
		lockResourceResponse.setName(Name);
		return lockResourceResponse;
	}
	
	
	@Test
	public void testupdateLockResourceForMultiContact()
	{
		LockResourceResponse expectedResult=getLockResourceResponseForUpdateLock();
		try {
			LockResourceResponse actualResult= registrationCfxDBServiceImpl.updateLockResourceForMultiContact(getLockResourceDBDtoForUpdateLock());
		assertEquals(expectedResult.getResourceId(), actualResult.getResourceId());
		
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	
	
	public ActivityLogRequest getActivityLogRequest()
	{
		ActivityLogRequest activityLogRequest= new ActivityLogRequest();
		activityLogRequest.setCustType("CFX");
		activityLogRequest.setEntityId(54087);
		activityLogRequest.setMaxRecord(20);
		activityLogRequest.setMinRecord(11);
		activityLogRequest.setUser(getUser());
		return activityLogRequest;		
	}
	
	public ActivityLogs getActivityLogs()
	{
		ActivityLogs activityLogs= new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
		activityLogData.setActivity("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
		activityLogData.setActivityType("Signup-STP");
		activityLogData.setCreatedOn("10/03/2021 10:02:15");
		activityLogData.setCreatedBy(Name);
		activityLogDataList.add(activityLogData);
		activityLogs.setActivityLogData(activityLogDataList);
		return activityLogs;			
	}
	
	public void setMockForGetActivityLogs()
	{
		try {
			Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
			when(resultSet.getString("Activity")).thenReturn("For CONTACT, After repeat FRAUGSTER check status changed from SERVICE_FAILURE to PASS");
			when(resultSet.getString("ActivityType")).thenReturn("Signup-STP");
			when(resultSet.getString("User")).thenReturn(Name);
			when(resultSet.getTimestamp("CreatedOn")).thenReturn(Timestamp.valueOf("2021-03-10 10:02:15.263"));
		
		} catch (SQLException e) {
			System.out.println(e);
		}	
	}

	@Test
	public void testGetActivityLogs() {
		setMockForGetActivityLogs();
		ActivityLogs expectedResult = getActivityLogs();
		try {
			ActivityLogs actualResult = registrationCfxDBServiceImpl.getActivityLogs(getActivityLogRequest());
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

}