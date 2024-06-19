package com.currenciesdirect.gtg.compliance.core.report;

import static org.junit.Assert.assertEquals;
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

import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegistrationReportServiceImplTest {

	@InjectMocks
	RegistrationReportServiceImpl registrationReportServiceImpl;

	@Mock
	IRegistrationReportDBService iRegistrationReportDBService;

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

	public List<Function> getFunctionInfo() {
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

	public Page getpage() {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setMaxRecord(40);
		page.setMinRecord(1);
		page.setPageSize(40);
		page.setTotalPages(1);
		page.setTotalRecords(1);
		return page;
	}

	public RegistrationQueueDto setRegistrationQueueData() {

		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add("CDINC");
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add("REJECT");
		List<String> organization = new ArrayList<>();
		organization.add("Currencies Direct");
		List<String> owner = new ArrayList<>();
		owner.add(NAME);
		List<String> source = new ArrayList<>();
		source.add("web");
		List<String> transValue = new ArrayList<>();
		transValue.add("Under 2,000");
		WatchListData watchlistData = new WatchListData();
		watchlistData.setId(1);
		watchlistData.setName("abc");
		List<WatchListData> watchlistDatas = new ArrayList<>();
		watchlistDatas.add(watchlistData);
		Watchlist watchlist = new Watchlist();
		watchlist.setWatchlistData(watchlistDatas);
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		registrationQueueDto.setCurrency(currency);
		registrationQueueDto.setIsFromDetails(false);
		registrationQueueDto.setLegalEntity(legalEntity);
		registrationQueueDto.setOnfidoStatus(onfidoStatus);
		registrationQueueDto.setOrganization(organization);
		registrationQueueDto.setOwner(owner);
		registrationQueueDto.setPage(getpage());
		registrationQueueDto.setRegistrationQueue(getRegistrationQueue());
		registrationQueueDto.setSavedSearch(getsavedSearch());
		registrationQueueDto.setSource(source);
		registrationQueueDto.setTransValue(transValue);
		registrationQueueDto.setUser(getUser());
		registrationQueueDto.setWatchList(watchlist);
		return registrationQueueDto;

	}

	public SavedSearch getsavedSearch() {
		SavedSearchData savedSearchData = new SavedSearchData();
		savedSearchData.setSaveSearchFilter("\"blacklistStatus\":[\"FAIL\"]");
		savedSearchData.setSaveSearchName("abc");
		return null;
	}

	public List<RegistrationQueueData> getRegistrationQueue() {
		List<RegistrationQueueData> data = new ArrayList<>();
		RegistrationQueueData registrationQueue = new RegistrationQueueData();
		registrationQueue.setTradeAccountNum("0401263960076444");
		registrationQueue.setRegisteredOn("18/08/2020 09:54:05");
		registrationQueue.setContactName("Robert Gill");
		registrationQueue.setOrganisation("Currencies Direct");
		registrationQueue.setType("PFX");
		registrationQueue.setBuyCurrency("EUR");
		registrationQueue.setSellCurrency("GBP");
		registrationQueue.setSource("Web");
		registrationQueue.setTransactionValue("Under 2,000");
		registrationQueue.setEidCheck("FAIL");
		data.add(registrationQueue);
		return data;
	}

	public RegistrationQueueDto getRegistrationQueueDto() {
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		registrationQueueDto.setRegistrationQueue(getRegistrationQueue());
		return registrationQueueDto;
	}

	public RegistrationReportSearchCriteria getRegistrationReportSearchCriteria() {
		RegistrationReportSearchCriteria searchCriteria = new RegistrationReportSearchCriteria();
		RegistrationReportFilter filter = new RegistrationReportFilter();
		filter.setKeyword("0401263960076444");
		filter.setUserProfile(getUser());
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(true);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		searchCriteria.setPage(getpage());
		return searchCriteria;
	}

	public void setMockForgetRegistrationQueueWithCriteria() {
		try {
			when(iRegistrationReportDBService.getRegistrationQueueWithCriteria(anyObject()))
					.thenReturn(setRegistrationQueueData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockgetRegistrationQueueWithCriteria() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRegistrationReportDBService.getRegistrationQueueWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForRegistrationQueueWholeData() {
		try {
			when(iRegistrationReportDBService.getRegistrationQueueWholeData(anyObject()))
					.thenReturn(getRegistrationQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockRegistrationQueueWholeData() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRegistrationReportDBService.getRegistrationQueueWholeData(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetRegistrationQueueWithCriteria() {
		RegistrationQueueDto expectedResult = setRegistrationQueueData();
		setMockForgetRegistrationQueueWithCriteria();
		RegistrationReportSearchCriteria searchCriteria = getRegistrationReportSearchCriteria();
		try {
			RegistrationQueueDto actualResult = registrationReportServiceImpl
					.getRegistrationQueueWithCriteria(searchCriteria);
			assertEquals(expectedResult.getRegistrationQueue().get(0).getContactName(),
					actualResult.getRegistrationQueue().get(0).getContactName());

		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetRegistrationQueueWithCriteria() {
		setMockgetRegistrationQueueWithCriteria();
		try {
			registrationReportServiceImpl.getRegistrationQueueWithCriteria(getRegistrationReportSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetRegistrationQueueWholeData() {
		setMockForRegistrationQueueWholeData();
		RegistrationQueueDto expectedResult = getRegistrationQueueDto();
		RegistrationReportSearchCriteria searchCriteria = getRegistrationReportSearchCriteria();
		try {
			RegistrationQueueDto actualResult = registrationReportServiceImpl
					.getRegistrationQueueWholeData(searchCriteria);
			assertEquals(expectedResult.getRegistrationQueue().get(0).getContactName(),
					actualResult.getRegistrationQueue().get(0).getContactName());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetRegistrationQueueWholeData() {
		setMockRegistrationQueueWholeData();
		try {
			registrationReportServiceImpl.getRegistrationQueueWholeData(getRegistrationReportSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}
