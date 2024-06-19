package com.currenciesdirect.gtg.compliance.httpport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.core.IRecheckService;

public class RecheckControllerTest {

	@InjectMocks
	RecheckController recheckController;
	@Mock
	IRecheckService iRecheckService;
	@Mock
	HttpServletRequest httpRequest;

	@Before
	public void setUp() {
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

	public BaseRepeatCheckRequest setBaseRepeatCheckRequest() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2020-08-04 00:00:00");
		baseRepeatCheckRequest.setDateTo("2020-08-05 00:00:00");
		baseRepeatCheckRequest.setModuleName("Registration");
		return baseRepeatCheckRequest;
	}

	public BaseRepeatCheckRequest setBaseRepeatCheckRequestForFundsOut() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2020-08-04 00:00:00");
		baseRepeatCheckRequest.setDateTo("2020-08-05 00:00:00");
		baseRepeatCheckRequest.setModuleName("Payment Out");
		return baseRepeatCheckRequest;
	}

	public BaseRepeatCheckRequest setBaseRepeatCheckRequestForFundsIn() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2020-08-04 00:00:00");
		baseRepeatCheckRequest.setDateTo("2020-08-05 00:00:00");
		baseRepeatCheckRequest.setModuleName("Payment In");
		return baseRepeatCheckRequest;
	}

	public void setOutputMock() {
		BaseRepeatCheckResponse response = new BaseRepeatCheckResponse();
		response.setTotalCount(1);
		try {
			when(iRecheckService.repeatCheckRegistrationFailures(anyObject(), anyObject())).thenReturn(response);
			when(iRecheckService.repeatCheckFundsInFailures(anyObject(), anyObject())).thenReturn(response);
			when(iRecheckService.repeatCheckFundsOutFailures(anyObject(), anyObject())).thenReturn(response);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockHttpRequest() {
		String user = "{\"name\":\"cd.comp.system\",\"givenName\":null,\"familyName\":null,"
				+ "\"middleName\":null,\"nickName\":null,\"preferredUserName\":"
				+ "\"Default User\",\"website\":null,\"email\":null,\"gender\":null,"
				+ "\"birthdate\":null,\"zoneinfo\":null,\"locale\":null,\"phoneNumber\":null,"
				+ "\"permissions\":{\"canWorkOnCFX\":false,\"canWorkOnPFX\":true,"
				+ "\"canViewRegistrationQueue\":true,\"canViewRegistrationDetails\":true,"
				+ "\"canViewPaymentInQueue\":true,\"canViewPaymentInDetails\":true,"
				+ "\"canViewPaymentOutQueue\":true,\"canViewPaymentOutDetails\":true,"
				+ "\"canViewRegistrationReport\":true,\"canViewPaymentInReport\":true,"
				+ "\"canViewPaymentOutReport\":true,\"canViewWorkEfficiacyReport\":true,"
				+ "\"canManageWatchListCategory1\":true,\"canManageWatchListCategory2\""
				+ ":true,\"canUnlockRecords\":true,\"canViewDashboard\""
				+ ":true,\"canManageFraud\":true,\"canManageCustom\":true,"
				+ "\"canManageEID\":true,\"canManageSanction\":true,\"canManageBlackList\""
				+ ":true,\"canDoAdministration\":true,\"isReadOnlyUser\""
				+ ":false,\"canManageBeneficiary\":true,\"canInitiateDataAnon\":true,"
				+ "\"canApproveDataAnon\":true,\"canNotLockAccount\":false},\"role\":{\"names\":"
				+ "[\"ATLAS_DATA_ANON_APPROVER\",\"ATLAS_DATA_ANON_INITIATOR\","
				+ "\"ATLAS_DEPT_HEAD\",\"ATLAS_BENE_MGMT\"],\"functions\":[{\"name\":"
				+ "\"canWorkOnPFX\",\"hasAccess\":true,\"hasOverrideAccess\":false}"
				+ ",{\"name\":\"canViewRegistrationQueue\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canViewPaymentInQueue\","
				+ "\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canViewPaymentOutQueue\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canViewRegistrationReport\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":"
				+ "\"canViewPaymentInReport\",\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canViewPaymentOutReport\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canUnlockRecords\",\"hasAccess\":"
				+ "true,\"hasOverrideAccess\":false},{\"name\":\"canViewDashboard\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":"
				+ "\"canManageFraud\",\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canManageCustom\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canManageEID\",\"hasAccess\""
				+ ":true,\"hasOverrideAccess\":false},{\"name\":\"canManageSanction\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":"
				+ "\"canManageBlackList\",\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canDoAdministration\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canViewWorkEfficiancyReport\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canManageWatchListCategory1\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canManageWatchListCategory2\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":"
				+ "\"canManageBeneficiary\",\"hasAccess\":true,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canViewDataAnonQueue\",\"hasAccess\""
				+ ":true,\"hasOverrideAccess\":false},{\"name\":\"canInitiateDataAnon\""
				+ ",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":"
				+ "\"canApproveDataAnon\",\"hasAccess\":true,\"hasOverrideAccess\":false},"
				+ "{\"name\":\"canWorkOnCFX\",\"hasAccess\":false,\"hasOverrideAccess\""
				+ ":false},{\"name\":\"isReadOnlyUser\",\"hasAccess\":false,"
				+ "\"hasOverrideAccess\":false},{\"name\":\"canNotLockAccount\",\"hasAccess\":false,\"hasOverrideAccess\":false}]},\"id\":1}";
		when(httpRequest.getHeader("user")).thenReturn(user);
	}

	public BaseRepeatCheckResponse setDataCount() {
		BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();
		baseRepeatCheckResponse.setBlacklistFailCount(1);
		baseRepeatCheckResponse.setFraugsterFailCount(1);
		baseRepeatCheckResponse.setSanctionFailCount(1);
		baseRepeatCheckResponse.setEidFailCount(1);
		return baseRepeatCheckResponse;
	}

	public void setMockDataForFailureCount() {
		try {
			when(iRecheckService.getRegistrationServiceFailureCount(anyObject(), anyObject())).thenReturn(setDataCount());
			when(iRecheckService.getFundsInServiceFailureCount(anyObject(), anyObject())).thenReturn(setDataCount());
			when(iRecheckService.getFundsOutServiceFailureCount(anyObject(), anyObject())).thenReturn(setDataCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	// ------Test Cases------//

	@Test
	public void testRepeatCheckRegistrationFailures() {
		setOutputMock();
		Integer i = 1;
		UserProfile user = getUser();
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		BaseRepeatCheckResponse response = recheckController.repeatCheckRegistrationFailures(user,
				baseRepeatCheckRequest);
		assertEquals(i, response.getTotalCount());
	}

	@Test
	public void testRepeatCheckFundsInFailures() {
		setMockHttpRequest();
		setOutputMock();
		Integer i = 1;
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequestForFundsIn();
		BaseRepeatCheckResponse response = recheckController.repeatCheckFundsInFailures(httpRequest,
				baseRepeatCheckRequest);
		assertEquals(i, response.getTotalCount());
	}

	@Test
	public void testRepeatCheckFundsOutFailures() {
		setMockHttpRequest();
		setOutputMock();
		Integer i = 1;
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequestForFundsOut();
		BaseRepeatCheckResponse response = recheckController.repeatCheckFundsOutFailures(httpRequest,
				baseRepeatCheckRequest);
		assertEquals(i, response.getTotalCount());
	}

	@Test
	public void testGetRegistrationServiceFailureCount() {
		BaseRepeatCheckResponse baseRepeatCheckResponse = setDataCount();
		setMockDataForFailureCount();
		UserProfile user = getUser();
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequest();
		BaseRepeatCheckResponse response = recheckController.getRegistrationServiceFailureCount(user,
				baseRepeatCheckRequest);
		assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), response.getBlacklistFailCount());
	}

	@Test
	public void testGetFundsInServiceFailureCount() {
		BaseRepeatCheckResponse baseRepeatCheckResponse = setDataCount();
		setMockDataForFailureCount();
		UserProfile user = getUser();
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequestForFundsIn();
		BaseRepeatCheckResponse response = recheckController.getFundsInServiceFailureCount(user,
				baseRepeatCheckRequest);
		assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), response.getBlacklistFailCount());
	}

	@Test
	public void testGetFundsOutServiceFailureCount() {
		BaseRepeatCheckResponse baseRepeatCheckResponse = setDataCount();
		setMockDataForFailureCount();
		UserProfile user = getUser();
		BaseRepeatCheckRequest baseRepeatCheckRequest = setBaseRepeatCheckRequestForFundsOut();
		BaseRepeatCheckResponse response = recheckController.getFundsOutServiceFailureCount(user,
				baseRepeatCheckRequest);
		assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), response.getBlacklistFailCount());
	}
	
	
	public BaseRepeatCheckRequest getBaseRepeatCheckRequestFundsOut()
	{
		BaseRepeatCheckRequest baseRepeatCheckRequest= new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2021-04-23 00:00:00");
		baseRepeatCheckRequest.setDateTo("2021-04-27 00:00:00");
		baseRepeatCheckRequest.setModuleName("Payment Out");
		return baseRepeatCheckRequest;
	}
	
	public void setmockforceClearFundsOuts()
	{
		try {
			when(iRecheckService.forceClearFundsOuts(anyObject(), anyObject())).thenReturn(setBaseRepeatCheckResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	
	public BaseRepeatCheckResponse setBaseRepeatCheckResponse()
	{
		BaseRepeatCheckResponse baseRepeatCheckResponse= new BaseRepeatCheckResponse();
		baseRepeatCheckResponse.setStatus("PASS");
		return baseRepeatCheckResponse;
	}
	@Test
	public void testforceClearFundsOuts()
	{
		setmockforceClearFundsOuts();
		BaseRepeatCheckResponse expectedResult=setBaseRepeatCheckResponse();
			BaseRepeatCheckResponse actualResult=recheckController.forceClearFundsOuts(getUser(), getBaseRepeatCheckRequestFundsOut());
		assertEquals(expectedResult.getStatus(),actualResult.getStatus());	
	}
	
	
	
	public BaseRepeatCheckRequest getBaseRepeatCheckRequest()
	{
		BaseRepeatCheckRequest baseRepeatCheckRequest= new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2021-04-15 00:00:00");
		baseRepeatCheckRequest.setDateTo("2021-04-22 00:00:00");
		baseRepeatCheckRequest.setModuleName("Payment In");
		return baseRepeatCheckRequest;
	}
	
	
	public void setMockforceClearFundsIn()
	{
		try {
			when(iRecheckService.forceClearFundsIn(anyObject(), anyObject())).thenReturn(setBaseRepeatCheckResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testforceClearFundsIn() 
	{
		setMockforceClearFundsIn();
		BaseRepeatCheckResponse expectedResult=setBaseRepeatCheckResponse();
		BaseRepeatCheckResponse actualResult=recheckController.forceClearFundsIn(getUser(), getBaseRepeatCheckRequest());
		assertEquals(expectedResult.getStatus(),actualResult.getStatus());
	}
	
	public BaseRepeatCheckRequest getBaseRepeatCheckRequestForDelete()
	{
		BaseRepeatCheckRequest baseRepeatCheckRequest= new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setBatchId(1);
		baseRepeatCheckRequest.setTransTypeInteger(1);
		return baseRepeatCheckRequest;
	}
	public void setMockdeleteReprocessFailed()
	{
		try {
			when(iRecheckService.deleteReprocessFailed(anyObject())).thenReturn(1);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testdeleteReprocessFailed()
	{
		setMockdeleteReprocessFailed();
		Integer expectedResult=1;
			Integer actualResult=recheckController.deleteReprocessFailed(getBaseRepeatCheckRequestForDelete());
		assertEquals(expectedResult,actualResult);
	}	
	
	public void setMockForgetRepeatCheckProgressBar()
	{
		try {
			when(iRecheckService.getRepeatCheckProgressBar(anyObject())).thenReturn(0);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testgetRepeatCheckProgressBar()
	{
		setMockForgetRepeatCheckProgressBar();
		BaseRepeatCheckRequest req= new BaseRepeatCheckRequest();
		req.setBatchId(1);
		int expectedResult=0;
		int actualResult=recheckController.getRepeatCheckProgressBar(req);
		assertEquals(expectedResult,actualResult);	
	}
	
	
	
	
	
	
}