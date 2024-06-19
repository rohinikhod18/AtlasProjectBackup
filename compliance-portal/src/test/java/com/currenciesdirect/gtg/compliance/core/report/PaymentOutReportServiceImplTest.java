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
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentOutReportServiceImplTest {

	@InjectMocks
	PaymentOutReportServiceImpl paymentOutReportServiceImpl;

	@Mock
	IPaymentOutReportDBService iPaymentOutReportDBService;
	
	private static final String NAME = "cd.comp.system";
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	public PaymentOutQueueDto getPaymentOutQueueDtoData() {
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
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		paymentOutQueueDto.setCountry(country);
		paymentOutQueueDto.setCurrency(currency);
		paymentOutQueueDto.setOwner(owner);
		paymentOutQueueDto.setSource(source);
		paymentOutQueueDto.setTransValue(transValue);
		paymentOutQueueDto.setTotalRecords(1);
		paymentOutQueueDto.setWatchList(watchlist);
		paymentOutQueueDto.setLegalEntity(legalEntity);
		paymentOutQueueDto.setOrganization(organization);
		paymentOutQueueDto.setOnfidoStatus(onfidoStatus);
		paymentOutQueueDto.setPage(getpage());
		paymentOutQueueDto.setPaymentOutQueue(getpaymentOutQueue());
		return paymentOutQueueDto;
	}

	public List<PaymentOutQueueData> getpaymentOutQueue() {
		List<PaymentOutQueueData> data = new ArrayList<>();
		PaymentOutQueueData paymentOutQueueData = new PaymentOutQueueData();
		paymentOutQueueData.setPaymentOutId("182899");
		paymentOutQueueData.setTransactionId("0202088009936016-000000066");
		paymentOutQueueData.setClientId("0202088009936016");
		paymentOutQueueData.setDate("29/07/2020 11:11:06");
		paymentOutQueueData.setContactName("John2 2-add Cena");
		paymentOutQueueData.setType("PFX");
		paymentOutQueueData.setBuyCurrency("GBP");
		paymentOutQueueData.setAmount("337.53");
		paymentOutQueueData.setBeneficiary("Convena Distribution A/S");
		data.add(paymentOutQueueData);
		return data;
	}

	public void setMockData() {
		try {
			when(iPaymentOutReportDBService.getPaymentOutReportWithCriteria(anyObject()))
					.thenReturn(getPaymentOutQueueDtoData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMock() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentOutReportDBService.getPaymentOutReportWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
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

	public PaymentOutQueueDto getpaymentOutQueueDto() {
		PaymentOutQueueDto data = new PaymentOutQueueDto();
		data.setPaymentOutQueue(getpaymentOutQueue());
		return data;
	}

	public void setMockForPaymentOutReportInExcelFormat() {
		try {
			when(iPaymentOutReportDBService.getPaymentOutQueueWholeData(anyObject()))
					.thenReturn(getpaymentOutQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockPaymentOutReportInExcelFormat() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentOutReportDBService.getPaymentOutQueueWholeData(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutReportSearchCriteria getPaymentOutReportSearchCriteria() {
		PaymentOutReportSearchCriteria searchCriteria = new PaymentOutReportSearchCriteria();
		PaymentOutReportFilter filter = new PaymentOutReportFilter();
		filter.setKeyword("0202088009936016-000000066");
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(true);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		searchCriteria.setPage(getpage());
		return searchCriteria;
	}

	@Test
	public void testGetPaymentOutReportWithCriteria() {
		setMockData();
		PaymentOutReportSearchCriteria searchCriteria = getPaymentOutReportSearchCriteria();
		PaymentOutQueueDto expectedResult = getPaymentOutQueueDtoData();
		try {
			PaymentOutQueueDto paymentOutQueueDto = paymentOutReportServiceImpl
					.getPaymentOutReportWithCriteria(searchCriteria);
			assertEquals(expectedResult.getPaymentOutQueue().get(0).getClientId(),
					paymentOutQueueDto.getPaymentOutQueue().get(0).getClientId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetPaymentOutReportWithCriteria() {
		setMock();
		try {
			paymentOutReportServiceImpl.getPaymentOutReportWithCriteria(getPaymentOutReportSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testGetPaymentOutQueueWholeData() {
		setMockForPaymentOutReportInExcelFormat();
		PaymentOutQueueDto expectedResult = getpaymentOutQueueDto();
		PaymentOutReportSearchCriteria searchCriteria = getPaymentOutReportSearchCriteria();
		try {
			PaymentOutQueueDto paymentOutQueueDto = paymentOutReportServiceImpl
					.getPaymentOutQueueWholeData(searchCriteria);
			assertEquals(expectedResult.getPaymentOutQueue().get(0).getClientId(),
					paymentOutQueueDto.getPaymentOutQueue().get(0).getClientId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetPaymentOutQueueWholeData() {
		setMockPaymentOutReportInExcelFormat();
		try {
			paymentOutReportServiceImpl.getPaymentOutQueueWholeData(getPaymentOutReportSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}