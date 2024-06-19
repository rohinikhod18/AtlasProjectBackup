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
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentInReportServiceImplTest {

	@InjectMocks
	PaymentInReportServiceImpl paymentInReportServiceImpl;

	@Mock
	IPaymentInReportDBService iPaymentInReportDBService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private static final String Name = "cd.comp.system";

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

	public PaymentInQueueDto getPaymentInQueueDto() {

		List<String> country = new ArrayList<>();
		country.add("AbKhazia");
		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add("CDINC");
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add("REJECT");
		List<String> organization = new ArrayList<>();
		organization.add("Currencies Direct");
		List<String> owner = new ArrayList<>();
		owner.add(Name);
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
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		paymentInQueueDto.setCountry(country);
		paymentInQueueDto.setCurrency(currency);
		paymentInQueueDto.setLegalEntity(legalEntity);
		paymentInQueueDto.setOrganization(organization);
		paymentInQueueDto.setOnfidoStatus(onfidoStatus);
		paymentInQueueDto.setOwner(owner);
		paymentInQueueDto.setPage(getpage());
		paymentInQueueDto.setWatchList(watchlist);
		paymentInQueueDto.setUser(getUser());
		paymentInQueueDto.setPaymentInQueue(PaymentInQueue());
		return paymentInQueueDto;
	}

	public List<PaymentInQueueData> PaymentInQueue() {
		List<PaymentInQueueData> data = new ArrayList<>();
		PaymentInQueueData paymentInQueueData = new PaymentInQueueData();
		paymentInQueueData.setTransactionId("0201000010009491-003796441");
		paymentInQueueData.setDate("05/11/2020 13:05:06");
		paymentInQueueData.setClientId("0201000010009497");
		paymentInQueueData.setContactName("John2 main-deb Cena");
		paymentInQueueData.setType("PFX");
		paymentInQueueData.setSellCurrency("GBP");
		paymentInQueueData.setAmount("700");
		paymentInQueueData.setLegalEntity("CDLGB");
		paymentInQueueData.setAccountId(49767);
		paymentInQueueData.setContactId(50630);
		data.add(paymentInQueueData);
		return data;
	}

	public PaymentInQueueDto setPaymentInQueueDto() {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		paymentInQueueDto.setPaymentInQueue(PaymentInQueue());
		return paymentInQueueDto;
	}

	public void setMock() {
		try {
			when(iPaymentInReportDBService.getPaymentInReportWithCriteria(anyObject()))
					.thenReturn(getPaymentInQueueDto());
			when(iPaymentInReportDBService.getPaymentInQueueWholeData(anyObject())).thenReturn(setPaymentInQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockData() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentInReportDBService.getPaymentInReportWithCriteria(anyObject())).thenThrow(e);
			when(iPaymentInReportDBService.getPaymentInQueueWholeData(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentInReportSearchCriteria setSearchCriteria() {
		PaymentInReportSearchCriteria searchCriteria = new PaymentInReportSearchCriteria();
		PaymentInReportFilter filter = new PaymentInReportFilter();
		filter.setUserProfile(getUser());
		filter.setKeyword("0201000010009491-003796441");
		searchCriteria.setFilter(filter);
		searchCriteria.setIsLandingPage(true);
		return searchCriteria;
	}

	@Test
	public void testGetPaymentInReportWithCriteria() {
		setMock();
		PaymentInQueueDto expectedResult = getPaymentInQueueDto();
		try {
			PaymentInQueueDto actualResult = paymentInReportServiceImpl
					.getPaymentInReportWithCriteria(setSearchCriteria());
			assertEquals(expectedResult.getPaymentInQueue().get(0).getAccountId(),
					actualResult.getPaymentInQueue().get(0).getAccountId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}

	}

	@Test
	public void testForGetPaymentInReportWithCriteria() {
		setMockData();
		try {
			paymentInReportServiceImpl.getPaymentInReportWithCriteria(setSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetPaymentInQueueWholeData() {
		setMock();
		try {
			PaymentInQueueDto expectedResult = setPaymentInQueueDto();
			PaymentInQueueDto actualResult = paymentInReportServiceImpl.getPaymentInQueueWholeData(setSearchCriteria());
			assertEquals(expectedResult.getPaymentInQueue().get(0).getAccountId(),
					actualResult.getPaymentInQueue().get(0).getAccountId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetPaymentInQueueWholeData() {
		setMockData();
		try {
			paymentInReportServiceImpl.getPaymentInQueueWholeData(setSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}
