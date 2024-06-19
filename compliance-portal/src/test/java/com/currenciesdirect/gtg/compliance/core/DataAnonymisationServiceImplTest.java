package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class DataAnonymisationServiceImplTest {

	@InjectMocks
	private DataAnonymisationServiceImpl dataAnonymisationServiceImpl;

	@Mock
	IAnonymisationDBService iAnonymisationDBService;

	@Mock
	DataAnonymisationDto dataAnonymisationDto;

	@Mock
	UserPermission perm;

	@Mock
	private DataAnonymisationDataRequest dataAnonymizeRequest;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public DataAnonymisationDataRequest getRequest() {
		DataAnonymisationDataRequest dataAnonRequest = new DataAnonymisationDataRequest();
		dataAnonRequest.setAccountId(47600);
		dataAnonRequest.setContactId(48438);
		dataAnonRequest.setEnterComment(" anonymise this acc");
		return dataAnonRequest;
	}

	public DataAnonymisationDataRequest getCancelDataAnonRequest() {
		DataAnonymisationDataRequest dataAnonRequest = new DataAnonymisationDataRequest();
		dataAnonRequest.setAccountId(47600);
		dataAnonRequest.setEnterComment(" anonymise this acc");
		return dataAnonRequest;
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

	public void responceMockingForGetDataAnonymize() {
		try {
			when(iAnonymisationDBService.getDataAnonymize(anyObject(), anyObject())).thenReturn(true);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void responceMockForGetDataAnonymize() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA);
			when(iAnonymisationDBService.getDataAnonymize(anyObject(), anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void responceMockingForCancelDataAnonymisation() {
		try {
			when(iAnonymisationDBService.cancelDataAnonymisation(anyObject(), anyObject())).thenReturn(true);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void responceMockForCancelDataAnonymisation() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA);
			when(iAnonymisationDBService.cancelDataAnonymisation(anyObject(), anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public DataAnonymisationDataRequest getDataAnonHistoryRequest() {
		DataAnonymisationDataRequest dataAnonRequest = new DataAnonymisationDataRequest();
		dataAnonRequest.setId(325);
		dataAnonRequest.setAccountId(47600);
		dataAnonRequest.setContactId(48438);
		dataAnonRequest.setEnterComment("cancel anon req");
		dataAnonRequest.setInitialApprovalDate("2019-11-28 12:10:52");
		return dataAnonRequest;
	}

	private void responceMockingForGetDataAnonHistory() {
		try {
			when(iAnonymisationDBService.getDataAnonHistory(anyObject())).thenReturn(setResponceForDataAnonHistory());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	private void responceMockForGetDataAnonHistory() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iAnonymisationDBService.getDataAnonHistory(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	private DataAnonymisationDto setResponceForDataAnonHistory() {
		DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		java.util.List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
		DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();
		dataAnonymisationData.setId(325);
		dataAnonymisationData.setActivity("REJECTITION");
		dataAnonymisationData.setActivityBy("1");
		dataAnonymisationData.setComment("cancel anon req");
		dataAnonymisationData.setInitialApprovalDate("2019-11-28 12:10:52");
		dataAnonymisationDataList.add(dataAnonymisationData);
		dataAnonDto.setDataAnonymisation(dataAnonymisationDataList);
		return dataAnonDto;
	}

	@Test
	public void testGetDataAnonymize() {
		try {
			DataAnonymisationDataRequest request = getRequest();
			UserProfile userinfo = getUser();
			responceMockingForGetDataAnonymize();
			boolean anonymize = dataAnonymisationServiceImpl.getDataAnonymize(userinfo, request);
			assertEquals(Boolean.TRUE, anonymize);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testCancelDataAnonymisation() {
		DataAnonymisationDataRequest request = getRequest();
		UserProfile userinfo = getUser();
		try {
			responceMockingForCancelDataAnonymisation();
			boolean anonymize = dataAnonymisationServiceImpl.cancelDataAnonymisation(userinfo, request);
			assertEquals(Boolean.TRUE, anonymize);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetDataAnonHistory() {
		responceMockingForGetDataAnonHistory();
		try {
			DataAnonymisationDto expectedResult = setResponceForDataAnonHistory();
			DataAnonymisationDto actualResult = dataAnonymisationServiceImpl
					.getDataAnonHistory(getDataAnonHistoryRequest());
			assertSame(expectedResult.getDataAnonymisation().get(0).getActivity(),
					actualResult.getDataAnonymisation().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetDataAnonHistory() {
		responceMockForGetDataAnonHistory();
		try {
			dataAnonymisationServiceImpl.getDataAnonHistory(getDataAnonHistoryRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}