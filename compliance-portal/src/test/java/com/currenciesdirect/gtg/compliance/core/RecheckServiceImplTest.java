package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
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

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RegistrationRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.dbport.PaymentInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.dbport.PaymentOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.recheckmonitor.RecheckMonitorThread;

public class RecheckServiceImplTest {

	@InjectMocks
	RecheckServiceImpl recheckServiceImpl;

	@Mock
	IRecheckDBService iRecheckDBService;

	@Mock
	RecheckMonitorThread recheckMonitorThread;

	@Mock
	BaseRepeatCheckResponse baseRepeatCheckResponse;
	
	@Mock
	PaymentInDBServiceImpl paymentInDBServiceImpl;
	@Mock 
	PaymentOutDBServiceImpl paymentOutDBServiceImpl;

	private static final String DATEFROM = "2020-08-04 00:00:00";
	private static final String DATETO = "2020-08-05 00:00:00";

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
		baseRepeatCheckRequest.setDateFrom(DATEFROM);
		baseRepeatCheckRequest.setDateTo(DATETO);
		baseRepeatCheckRequest.setModuleName("Registration");
		return baseRepeatCheckRequest;
	}

	public BaseRepeatCheckRequest setBaseRepeatCheckRequestForFundsOut() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom(DATEFROM);
		baseRepeatCheckRequest.setDateTo(DATETO);
		baseRepeatCheckRequest.setModuleName("Payment Out");
		return baseRepeatCheckRequest;
	}

	public BaseRepeatCheckRequest setBaseRepeatCheckRequestForFundsIn() {
		BaseRepeatCheckRequest baseRepeatCheckRequest = new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom(DATEFROM);
		baseRepeatCheckRequest.setDateTo(DATETO);
		baseRepeatCheckRequest.setModuleName("Payment In");
		return baseRepeatCheckRequest;
	}

	public List<ReprocessFailedDetails> setReprocessListRegistrationOutput() {
		List<ReprocessFailedDetails> reprocessPaymentList = new ArrayList<>();
		ReprocessFailedDetails reprocessFailedPaymentDetail = new ReprocessFailedDetails();
		reprocessFailedPaymentDetail.setId(19308);
		reprocessFailedPaymentDetail.setBatchId(7);
		reprocessFailedPaymentDetail.setTransType("CONTACT");
		reprocessFailedPaymentDetail.setTransId(50642);
		reprocessFailedPaymentDetail.setStatus("FAILED");
		reprocessPaymentList.add(reprocessFailedPaymentDetail);
		return reprocessPaymentList;
	}

	public void setOutput() {
		try {

			when(iRecheckDBService.getRegistrationFailures(anyObject()))
					.thenReturn(setRegistrationRecheckFailureDetailsPFX());
			when(iRecheckDBService.saveFailedRegDetailsToDB(anyList(), anyObject())).thenReturn(1);
			when(iRecheckDBService.getReprocessList(anyObject(), anyObject()))
					.thenReturn(setReprocessListRegistrationOutput());
		} catch (CompliancePortalException e) {

			System.out.println(e);
		}
	}

	public List<RegistrationRecheckFailureDetails> setRegistrationRecheckFailureDetailsPFX() {
		List<RegistrationRecheckFailureDetails> failedRegistrationList = new ArrayList<>();
		RegistrationRecheckFailureDetails recheckFailureDetail = new RegistrationRecheckFailureDetails();
		recheckFailureDetail.setAccountId(0);
		recheckFailureDetail.setContactId(50665);
		recheckFailureDetail.setCustomerType("PFX");
		failedRegistrationList.add(recheckFailureDetail);
		return failedRegistrationList;
	}

	public BaseRepeatCheckResponse setDataCount() {
		baseRepeatCheckResponse.setBlacklistFailCount(1);
		baseRepeatCheckResponse.setFraugsterFailCount(1);
		baseRepeatCheckResponse.setSanctionFailCount(1);
		baseRepeatCheckResponse.setEidFailCount(1);
		return baseRepeatCheckResponse;
	}

	public List<PaymentInRecheckFailureDetails> setDataPaymentIn() {
		List<PaymentInRecheckFailureDetails> failedPaymentInList = new ArrayList<>();
		PaymentInRecheckFailureDetails recheckFailureDetail = new PaymentInRecheckFailureDetails();
		recheckFailureDetail.setAccountId(45518);
		recheckFailureDetail.setContactId(50617);
		recheckFailureDetail.setComplianceStatus("HOLD");
		recheckFailureDetail.setOrgId(2);
		recheckFailureDetail.setOrgCode("Currencies Direct");
		recheckFailureDetail.setPaymentInId(291342);
		recheckFailureDetail.setTradeContractNumber("0202088009936016-003796509");
		recheckFailureDetail.setTradePaymentId(1457057430);
		failedPaymentInList.add(recheckFailureDetail);
		return failedPaymentInList;
	}

	public void setOutputFundsOIn() {
		try {
			recheckMonitorThread.setName("RECHECK MONITER-7");
			when(iRecheckDBService.getFundsInFailures(anyObject())).thenReturn(setDataPaymentIn());
			when(iRecheckDBService.saveFailedFundsInDetailsToDB(anyList(), anyObject())).thenReturn(1);
			when(iRecheckDBService.getReprocessList(anyObject(), anyObject()))
					.thenReturn(setReprocessListRegistrationOutput());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public List<PaymentOutRecheckFailureDetails> setDataPaymentOut() {
		List<PaymentOutRecheckFailureDetails> failedPaymentInList = new ArrayList<>();
		PaymentOutRecheckFailureDetails recheckFailureDetail = new PaymentOutRecheckFailureDetails();
		recheckFailureDetail.setAccountId(45518);
		recheckFailureDetail.setContactId(50616);
		recheckFailureDetail.setComplianceStatus("HOLD");
		recheckFailureDetail.setOrgId(2);
		recheckFailureDetail.setOrgCode("Currencies Direct");
		recheckFailureDetail.setPaymentOutId(182850);
		recheckFailureDetail.setTradeContractNumber("0202088009936016-003505372");
		recheckFailureDetail.setTradePaymentId(2001502000);
		failedPaymentInList.add(recheckFailureDetail);
		return failedPaymentInList;
	}

	public void setOutputFundsOut() {
		try {
			recheckMonitorThread.setName("RECHECK MONITER-7");
			when(iRecheckDBService.getFundsOutFailures(anyObject())).thenReturn(setDataPaymentOut());
			when(iRecheckDBService.saveFailedDetailsToDB(anyList(), anyObject())).thenReturn(1);
			when(iRecheckDBService.getReprocessList(anyObject(), anyObject()))
					.thenReturn(setReprocessListRegistrationOutput());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
//-------Test cases--------//

	@Test
	public void testGetFundsOutServiceFailureCount() {
		baseRepeatCheckResponse = setDataCount();
		BaseRepeatCheckRequest request = setBaseRepeatCheckRequest();
		UserProfile user = getUser();
		try {
			when(iRecheckDBService.getFundsOutServiceFailureCount(anyObject())).thenReturn(baseRepeatCheckResponse);
			BaseRepeatCheckResponse repeatCheckResponse = recheckServiceImpl.getFundsOutServiceFailureCount(user,
					request);
			assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), repeatCheckResponse.getBlacklistFailCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetFundsOutServiceFailureCount() {
		CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
		try {
			when(iRecheckDBService.getFundsOutServiceFailureCount(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e1) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetFundsOutServiceFailureCount() {
		setMockForGetFundsOutServiceFailureCount();
		try {
			recheckServiceImpl.getFundsOutServiceFailureCount(getUser(), setBaseRepeatCheckRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetFundsInServiceFailureCount() {
		baseRepeatCheckResponse = setDataCount();
		BaseRepeatCheckRequest request = setBaseRepeatCheckRequest();
		UserProfile user = getUser();
		try {
			when(iRecheckDBService.getFundsInServiceFailureCount(anyObject())).thenReturn(baseRepeatCheckResponse);
			BaseRepeatCheckResponse repeatCheckResponse = recheckServiceImpl.getFundsInServiceFailureCount(user,
					request);
			assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), repeatCheckResponse.getBlacklistFailCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetFundsInServiceFailureCount() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRecheckDBService.getFundsInServiceFailureCount(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetFundsInServiceFailureCount() {
		try {
			setMockForGetFundsInServiceFailureCount();
			recheckServiceImpl.getFundsInServiceFailureCount(getUser(), setBaseRepeatCheckRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetRegistrationServiceFailureCount() {
		baseRepeatCheckResponse = setDataCount();
		BaseRepeatCheckRequest request = setBaseRepeatCheckRequest();
		UserProfile user = getUser();
		try {
			when(iRecheckDBService.getRegistrationServiceFailureCount(anyObject())).thenReturn(baseRepeatCheckResponse);
			BaseRepeatCheckResponse repeatCheckResponse = recheckServiceImpl.getRegistrationServiceFailureCount(user,
					request);
			assertEquals(baseRepeatCheckResponse.getBlacklistFailCount(), repeatCheckResponse.getBlacklistFailCount());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetRegistrationServiceFailureCount() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRecheckDBService.getRegistrationServiceFailureCount(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetRegistrationServiceFailureCount() {
		try {
			recheckServiceImpl.getRegistrationServiceFailureCount(getUser(), setBaseRepeatCheckRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}
	public void setMockForgetRepeatCheckProgressBar()
	{
		try {
			when(iRecheckDBService.getRepeatCheckProgressBar(anyObject())).thenReturn(0);
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
	try {
		int actualResult=recheckServiceImpl.getRepeatCheckProgressBar(req);
		assertEquals(expectedResult,actualResult);
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}	
	}
	
	public List<PaymentInRecheckFailureDetails> getPaymentInRecheckFailureDetails()
	{
		List<PaymentInRecheckFailureDetails> paymentInFailedList= new ArrayList<>();
		PaymentInRecheckFailureDetails paymentInRecheckFailureDetails= new PaymentInRecheckFailureDetails();
		paymentInRecheckFailureDetails.setAccountId(56106);
		paymentInRecheckFailureDetails.setComplianceStatus("HOLD");
		paymentInRecheckFailureDetails.setContactId(57012);
		paymentInRecheckFailureDetails.setOrgCode("Currencies Direct");
		paymentInRecheckFailureDetails.setOrgId(2);
		paymentInRecheckFailureDetails.setPaymentInId(341550);
		paymentInRecheckFailureDetails.setTradeContractNumber("7112I355457-013");
		paymentInRecheckFailureDetails.setTradePaymentId(331539);
		paymentInFailedList.add(paymentInRecheckFailureDetails);
		return paymentInFailedList;
		
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
			when(iRecheckDBService.getFundsInFailures(anyObject())).thenReturn(getPaymentInRecheckFailureDetails());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testforceClearFundsIn() 
	{
		setMockforceClearFundsIn();
		BaseRepeatCheckResponse expectedResult=new BaseRepeatCheckResponse();
		expectedResult.setStatus("PASS");
		try {
			BaseRepeatCheckResponse actualResult=recheckServiceImpl.forceClearFundsIn(getUser(), getBaseRepeatCheckRequest());
		assertEquals(expectedResult.getStatus(),actualResult.getStatus());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	
	
	public BaseRepeatCheckRequest getBaseRepeatCheckRequestFundsOut()
	{
		BaseRepeatCheckRequest baseRepeatCheckRequest= new BaseRepeatCheckRequest();
		baseRepeatCheckRequest.setDateFrom("2021-04-23 00:00:00");
		baseRepeatCheckRequest.setDateTo("2021-04-27 00:00:00");
		baseRepeatCheckRequest.setModuleName("Payment Out");
		return baseRepeatCheckRequest;
	}
	
	public List<PaymentOutRecheckFailureDetails> getPaymentOutRecheckFailureDetails()
	{
		List<PaymentOutRecheckFailureDetails> paymentOutFailedList= new ArrayList<>();
		PaymentOutRecheckFailureDetails paymentOutRecheckFailureDetails= new PaymentOutRecheckFailureDetails();
		paymentOutRecheckFailureDetails.setAccountId(56129);
		paymentOutRecheckFailureDetails.setComplianceStatus("HOLD");
		paymentOutRecheckFailureDetails.setContactId(57035);
		paymentOutRecheckFailureDetails.setPaymentOutId(193085);
		paymentOutRecheckFailureDetails.setOrgCode("Currencies Direct");
		paymentOutRecheckFailureDetails.setOrgId(2);
		paymentOutRecheckFailureDetails.setTradeContractNumber("0201040010026224-3517137");
		paymentOutRecheckFailureDetails.setTradePaymentId(3517137);
		paymentOutFailedList.add(paymentOutRecheckFailureDetails);
		return paymentOutFailedList;
	}
	public void setmockforceClearFundsOuts()
	{
		try {
			when(iRecheckDBService.getFundsOutFailures(anyObject())).thenReturn(getPaymentOutRecheckFailureDetails());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testforceClearFundsOuts()
	{
		setmockforceClearFundsOuts();
		BaseRepeatCheckResponse expectedResult= new BaseRepeatCheckResponse();
		expectedResult.setStatus("PASS");
		try {
			BaseRepeatCheckResponse actualResult=recheckServiceImpl.forceClearFundsOuts(getUser(), getBaseRepeatCheckRequestFundsOut());
		assertEquals(expectedResult.getStatus(),actualResult.getStatus());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
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
			when(iRecheckDBService.deleteReprocessFailed(anyObject())).thenReturn(1);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testdeleteReprocessFailed()
	{
		setMockdeleteReprocessFailed();
		Integer expectedResult=1;
		try {
			Integer actualResult=recheckServiceImpl.deleteReprocessFailed(getBaseRepeatCheckRequestForDelete());
		assertEquals(expectedResult,actualResult);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}	
	}	
}