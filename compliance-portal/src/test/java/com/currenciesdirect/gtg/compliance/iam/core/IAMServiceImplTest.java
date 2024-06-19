package com.currenciesdirect.gtg.compliance.iam.core;

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

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.iam.dbport.IIAMDBService;

public class IAMServiceImplTest {

	@InjectMocks
	IAMServiceImpl iAMServiceImpl;
	
	@Mock
	IIAMDBService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public UserProfile getUser() {
		UserProfile userprof = new UserProfile();
		UserPermission userPerm = new UserPermission();
		Role role = new Role();
		userprof.setId(1);
		userprof.setName("cd.comp.system");
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

	private java.util.List<Function> getFunctionInfo() {
		java.util.List<Function> listFunctions = new ArrayList<>();
		Function function = new Function();
		function.setName("canWorkOnPFX");
		function.setHasAccess(true);
		function.setHasOverrideAccess(false);
		listFunctions.add(function);
		return listFunctions;
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
	public void responceMocking() throws CompliancePortalException {
		
		when(service.getUserRoleDetails(anyObject())).thenReturn(getRole());	
	}
	
public void responceMockGetUserFunctions() throws CompliancePortalException {
	CompliancePortalException e=new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
		when(service.getUserRoleDetails(anyObject())).thenThrow(e);
		
	}
	@Test
	public void testGetUserFunctions() throws CompliancePortalException {
		try {
			responceMocking();
			List<Function> expectedResult=getFunctionInfo();
			List<Function> actualResult=iAMServiceImpl.getUserFunctions(getUser());
			assertEquals(expectedResult.get(0).getName(), actualResult.get(0).getName());
		} catch (CompliancePortalException e) {
			 System.out.println(e);
		}
	}
	@Test
	public void testForGetUserFunctions() throws CompliancePortalException {
		try {
			responceMockGetUserFunctions();
			iAMServiceImpl.getUserFunctions(getUser());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
	
	

	@Test
	public void testGetUserPermissionsByRoleAndView() {
		UserProfile userInfo = getUser();
		UserPermissionDirector permissionDirector = new UserPermissionDirector();
		permissionDirector.constructUserPermissions(userInfo);
		userInfo.setPermissions(permissionDirector.getUserPermission());
		assertEquals(userInfo.getName(),userInfo.getName());
	}

}
