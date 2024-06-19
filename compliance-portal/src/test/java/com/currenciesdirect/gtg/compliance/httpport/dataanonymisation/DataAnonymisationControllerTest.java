package com.currenciesdirect.gtg.compliance.httpport.dataanonymisation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.IAnonymisationService;
import com.currenciesdirect.gtg.compliance.core.IRegistrationService;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationFilter;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class DataAnonymisationControllerTest {

	@Mock
	IAnonymisationService iAnonymisationService;

	@Mock
	IRegistrationService iRegistrationService;

	@Mock
	IRegistrationDetails registrationDetailsDto;

	@InjectMocks
	private DataAnonymisationController dataAnonymisationController;

	private static final String NAME = "cd.comp.system";

	@Before
	public void setUp() {
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

	private DataAnonymisationDto getdataAnonQueue() {
		DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
		DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();
		String[] comment = { "test dev 33333" };
		dataAnonymisationData.setAccountId(49756);
		dataAnonymisationData.setContactId(50607);
		dataAnonymisationData.setInitialApprovalBy(NAME);
		dataAnonymisationData.setInitialApprovalComment(comment);
		dataAnonymisationData.setInitialApprovalDate("28/11/2019 12:10:52");
		dataAnonymisationData.setFinalApprovalBy("test_atlas_senior_executive");
		dataAnonymisationData.setFinalApprovalComment("final approval");
		dataAnonymisationData.setFinalApprovalDate("17/07/2020 17:07:22");
		dataAnonymisationData.setComplianceStatus("INACTIVE");
		dataAnonymisationData.setType("PFX");
		dataAnonymisationData.setSource("Web");
		dataAnonymisationData.setId(235);
		dataAnonymisationData.setDataAnonymizationStatus(1);
		dataAnonymisationData.setContactName("John2 N22 Cena");
		dataAnonymisationData.setTradeAccountNum("0201000010009484");
		dataAnonymisationDataList.add(dataAnonymisationData);
		dataAnonDto.setDataAnonymisation(dataAnonymisationDataList);
		return dataAnonDto;
	}

	private DataAnonymisationDataRequest getRequestInfo() {
		DataAnonymisationDataRequest dataAnonymisationDataRequest = new DataAnonymisationDataRequest();
		dataAnonymisationDataRequest.setAccountId(49756);
		dataAnonymisationDataRequest.setContactId(50607);
		dataAnonymisationDataRequest.setComment("initialization request");
		dataAnonymisationDataRequest.setId(235);
		dataAnonymisationDataRequest.setInitialApprovalDate("2020-7-30 12:10:52");
		return dataAnonymisationDataRequest;
	}

	public DataAnonymisationDto setHistory() {
		DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
		DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();
		dataAnonymisationData.setActivity("INITIALIZATION");
		dataAnonymisationData.setActivityBy(NAME);
		dataAnonymisationData.setActivityDate("2019-11-28 12:10:52");
		dataAnonymisationData.setComment("initialization request");
		dataAnonymisationDataList.add(dataAnonymisationData);
		dataAnonDto.setDataAnonymisation(dataAnonymisationDataList);
		return dataAnonDto;
	}

	public DataAnonymisationFilter getDataAnonymisationFilter() {
		DataAnonymisationFilter dataAnonymisationFilter = new DataAnonymisationFilter();
		UserProfile user = getUser();
		String[] excludeCustType = { "CFX" };
		dataAnonymisationFilter.setExcludeCustType(excludeCustType);
		dataAnonymisationFilter.setUserProfile(user);
		return dataAnonymisationFilter;
	}

	public DataAnonymisationSearchCriteria getDataAnonymisationSearchCriteria() {
		DataAnonymisationFilter dataAnonymisationFilter = getDataAnonymisationFilter();
		DataAnonymisationSearchCriteria searchCriteria = new DataAnonymisationSearchCriteria();
		searchCriteria.setFilter(dataAnonymisationFilter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		return searchCriteria;
	}

	public IRegistrationDetails setregistrationDetailsDto() {
		registrationDetailsDto.setCurrentRecord(1);
		registrationDetailsDto.setTotalRecords(1);
		return registrationDetailsDto;
	}

	@Test
	public void testGetDataAnonymize() {
		boolean result1 = true;
		DataAnonymisationDataRequest dataAnonymisationDataRequest = getRequestInfo();
		UserProfile user = getUser();
		try {
			when(iAnonymisationService.getDataAnonymize(user, dataAnonymisationDataRequest)).thenReturn(true);
			boolean result = dataAnonymisationController.getDataAnonymize(user, dataAnonymisationDataRequest);
			assertEquals(result1, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockData() {
		CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA);
		CompliancePortalException e1 = new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
		try {
			when(iAnonymisationService.getDataAnonymize(anyObject(), anyObject())).thenThrow(e);
			when(iAnonymisationService.getDataAnonHistory(anyObject())).thenThrow(e1);
			when(iAnonymisationService.cancelDataAnonymisation(anyObject(), anyObject())).thenThrow(e);
			when(iAnonymisationService.getDataAnonymisationWithCriteria(anyObject())).thenThrow(e1);
			when(iRegistrationService.getRegistrationDetails(anyInt(), anyString(), anyObject())).thenThrow(e1);
		} catch (CompliancePortalException cp) {
			System.out.println(cp);
		}
	}

	@Test
	public void testGetDataAnonHistory() {
		DataAnonymisationDto dataAnonDto = setHistory();
		DataAnonymisationDataRequest dataAnonymisationDataRequest = getRequestInfo();
		try {
			when(iAnonymisationService.getDataAnonHistory(dataAnonymisationDataRequest)).thenReturn(setHistory());
			DataAnonymisationDto dataAnonDto1 = dataAnonymisationController
					.getDataAnonHistory(dataAnonymisationDataRequest);
			List<DataAnonymisationDataRequest> dataAnonymisationDataList1 = dataAnonDto.getDataAnonymisation();
			List<DataAnonymisationDataRequest> dataAnonymisationDataList = dataAnonDto1.getDataAnonymisation();
			assertEquals(dataAnonymisationDataList.get(0).getActivity(),
					dataAnonymisationDataList1.get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetDataAnonHistory() {
		setMockData();
		DataAnonymisationDto dataAnonymisationDto = dataAnonymisationController.getDataAnonHistory(getRequestInfo());
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				dataAnonymisationDto.getErrorMessage());
	}

	@Test
	public void testCancelDataAnonymisation() {
		DataAnonymisationDataRequest dataAnonymisationDataRequest = getRequestInfo();
		UserProfile user = getUser();
		try {
			when(iAnonymisationService.cancelDataAnonymisation(anyObject(), anyObject())).thenReturn(true);
			boolean result = dataAnonymisationController.cancelDataAnonymisation(user, dataAnonymisationDataRequest);
			assertEquals(true, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetDataAnonymisationWithCriteria() {
		DataAnonymisationSearchCriteria dataAnonymisationDataRequest = getDataAnonymisationSearchCriteria();
		UserProfile user = getUser();
		try {
			when(iAnonymisationService.getDataAnonymisationWithCriteria(dataAnonymisationDataRequest))
					.thenReturn(getdataAnonQueue());
			DataAnonymisationDto dataAnonymisationDto = dataAnonymisationController
					.getDataAnonymisationWithCriteria(dataAnonymisationDataRequest, user);
			DataAnonymisationDto dataAnonymisationDto1 = getdataAnonQueue();
			List<DataAnonymisationDataRequest> actualResult = dataAnonymisationDto.getDataAnonymisation();
			List<DataAnonymisationDataRequest> expectedResult = dataAnonymisationDto1.getDataAnonymisation();
			assertEquals(expectedResult.get(0).getAccountId(), actualResult.get(0).getAccountId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetDataAnonymisationWithCriteria() {
		setMockData();
		DataAnonymisationDto dataAnonymisationDto = dataAnonymisationController
				.getDataAnonymisationWithCriteria(getDataAnonymisationSearchCriteria(), getUser());
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				dataAnonymisationDto.getErrorMessage());
	}

	@Test
	public void testGetDataAnonymisationQueue() {
		try {
			when(iAnonymisationService.getDataAnonymisationWithCriteria(anyObject())).thenReturn(getdataAnonQueue());
			UserProfile user = getUser();
			ModelAndView modelAndView = dataAnonymisationController.getDataAnonymisationQueue(user);
			assertEquals("DataAnonymisationQueue", modelAndView.getViewName());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetRegistrationDetails() {
		registrationDetailsDto = setregistrationDetailsDto();
		String search = "{\"filter\":{},\"page\":{\"minRecord\":1,\"maxRecord\":40,\"totalRecords\":156,\"currentPage\":1,\"totalPages\":4,\"currentRecord\":1},\"isFilterApply\":false,\"isRequestFromReportPage\":false,\"isFromClearFilter\":false}";
		try {
			when(iRegistrationService.getRegistrationDetails(anyInt(), anyString(), anyObject()))
					.thenReturn(registrationDetailsDto);
			UserProfile user = getUser();
			ModelAndView modelAndView = dataAnonymisationController.getRegistrationDetails(50607, "PFX", search,
					"queue", user);
			assertEquals("RegistrationItem", modelAndView.getViewName());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
}