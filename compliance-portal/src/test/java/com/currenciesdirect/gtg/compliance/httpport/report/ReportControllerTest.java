package com.currenciesdirect.gtg.compliance.httpport.report;

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
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportData;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentInReportService;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentOutReportService;
import com.currenciesdirect.gtg.compliance.core.report.IRegistrationReportService;
import com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class ReportControllerTest {

	@InjectMocks
	ReportController reportController;

	@Mock
	IRegistrationReportService iRegistrationReportService;
	
	@Mock
	IWorkEfficiencyReportService iWorkEfficiencyReportService;
	
	@Mock
	IPaymentOutReportService iPaymentOutReportService;
	
	@Mock
	IPaymentInReportService iPaymentInReportService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private static final String NAME = "cd.comp.system";
	private static final String TRANSVALUE ="Under 2,000";
	private static final String LEGALENTITY="CDINC";
	private static final String ONFIDOSTATUS="REJECT";
	
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

	public RegistrationQueueDto setRegistrationQueueData() {

		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add(LEGALENTITY);
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add(ONFIDOSTATUS);
		List<String> organization = new ArrayList<>();
		organization.add(NAME);
		List<String> owner = new ArrayList<>();
		owner.add(NAME);
		List<String> source = new ArrayList<>();
		source.add("web");
		List<String> transValue = new ArrayList<>();
		transValue.add(TRANSVALUE);
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

	public List<RegistrationQueueData> getRegistrationQueue() {
		List<RegistrationQueueData> data = new ArrayList<>();
		RegistrationQueueData registrationQueue = new RegistrationQueueData();
		registrationQueue.setTradeAccountNum("0201000010026273");
		registrationQueue.setRegisteredOn("21/08/2020 16:26:33");
		registrationQueue.setContactName("Robert Gill");
		registrationQueue.setOrganisation(NAME);
		registrationQueue.setType("PFX");
		registrationQueue.setBuyCurrency("EUR");
		registrationQueue.setSellCurrency("GBP");
		registrationQueue.setSource("Web");
		registrationQueue.setTransactionValue(TRANSVALUE);
		registrationQueue.setEidCheck("FAIL");
		registrationQueue.setAccountId(49806);
		data.add(registrationQueue);
		return data;
	}

	public SavedSearch getsavedSearch() {
		SavedSearchData savedSearchData = new SavedSearchData();
		savedSearchData.setSaveSearchFilter("\"blacklistStatus\":[\"FAIL\"]");
		savedSearchData.setSaveSearchName("abc");
		return null;

	}

	public void setMockdata() {
		try {
			when(iRegistrationReportService.getRegistrationQueueWithCriteria(anyObject()))
					.thenReturn(setRegistrationQueueData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockGetRegistrationQueueWithCriteria() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRegistrationReportService.getRegistrationQueueWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMock() {
		try {
			when(iRegistrationReportService.getRegistrationQueueWithCriteria(anyObject()))
					.thenReturn(setRegistrationQueueData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public List<WorkEfficiencyReportData> getWorkEfficiencyReportData() {
		List<WorkEfficiencyReportData> data = new ArrayList<>();
		WorkEfficiencyReportData workEfficiencyReportData = new WorkEfficiencyReportData();
		workEfficiencyReportData.setAccountType("PFX");
		workEfficiencyReportData.setLockedRecords(1);
		workEfficiencyReportData.setPercentEfficiency(0.0);
		workEfficiencyReportData.setQueueType("Payment out");
		workEfficiencyReportData.setReleasedRecords(0);
		workEfficiencyReportData.setSeconds(0.0);
		workEfficiencyReportData.setSlaValue(3.0);
		workEfficiencyReportData.setTotalRows(1);
		workEfficiencyReportData.setTimeEfficiency(0.0);
		workEfficiencyReportData.setUserName(NAME);
		data.add(workEfficiencyReportData);
		return data;
	}

	public WorkEfficiencyReportDto setWorkEfficiencyReportDto() {
		List<String> queueList = new ArrayList<>();
		queueList.add("Contact");
		List<String> userNameList = new ArrayList<>();
		userNameList.add("abhay");
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		workEfficiencyReportDto.setDateDifference(7);
		workEfficiencyReportDto.setPage(getpage());
		workEfficiencyReportDto.setQueueList(queueList);
		workEfficiencyReportDto.setUserNameList(userNameList);
		workEfficiencyReportDto.setWorkEfficiencyReportData(getWorkEfficiencyReportData());
		return workEfficiencyReportDto;
	}

	public void setMockForWorkEfficiencyReport() {
		try {
			when(iWorkEfficiencyReportService.getWorkEfficiencyReport()).thenReturn(setWorkEfficiencyReportDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForWorkEfficiencyReport() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iWorkEfficiencyReportService.getWorkEfficiencyReport()).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutQueueDto getPaymentOutQueueDtoData() {
		List<String> country = new ArrayList<>();
		country.add("AbKhazia");
		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add(LEGALENTITY);
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add(ONFIDOSTATUS);
		List<String> organization = new ArrayList<>();
		organization.add("Currencies Direct");
		List<String> owner = new ArrayList<>();
		owner.add(NAME);
		List<String> source = new ArrayList<>();
		source.add("web");
		List<String> transValue = new ArrayList<>();
		transValue.add(TRANSVALUE);
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
		paymentOutQueueDto.setPaymentOutQueue(getpaymentOutQueue());
		return paymentOutQueueDto;
	}

	public List<PaymentOutQueueData> getpaymentOutQueue() {
		List<PaymentOutQueueData> data = new ArrayList<>();
		PaymentOutQueueData paymentOutQueueData = new PaymentOutQueueData();
		paymentOutQueueData.setPaymentOutId("182899");
		paymentOutQueueData.setTransactionId("0202088009936016-000000064");
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

	public void setMockForgetPaymentOutReport() {
		try {
			when(iPaymentOutReportService.getPaymentOutReportWithCriteria(anyObject()))
					.thenReturn(getPaymentOutQueueDtoData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForgetPaymentOutReport() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentOutReportService.getPaymentOutReportWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentInQueueDto getPaymentInQueueDto() {

		List<String> country = new ArrayList<>();
		country.add("AbKhazia");
		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add(LEGALENTITY);
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add(ONFIDOSTATUS);
		List<String> organization = new ArrayList<>();
		organization.add(NAME);
		List<String> owner = new ArrayList<>();
		owner.add(NAME);
		List<String> source = new ArrayList<>();
		source.add("web");
		List<String> transValue = new ArrayList<>();
		transValue.add(TRANSVALUE);
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
		paymentInQueueDto.setPaymentInQueue(paymentInQueue());
		return paymentInQueueDto;
	}

	public List<PaymentInQueueData> paymentInQueue() {
		List<PaymentInQueueData> data = new ArrayList<>();
		PaymentInQueueData paymentInQueueData = new PaymentInQueueData();
		paymentInQueueData.setTransactionId("0201000010009491-003796441");
		paymentInQueueData.setDate("05/11/2020 13:05:06");
		paymentInQueueData.setClientId("0201000010009497");
		paymentInQueueData.setContactName("John2 main-deb Cena");
		paymentInQueueData.setType("PFX");
		paymentInQueueData.setSellCurrency("GBP");
		paymentInQueueData.setAmount("700");
		data.add(paymentInQueueData);
		return data;
	}

	public void setMockForPaymentInReport() {
		try {
			when(iPaymentInReportService.getPaymentInReportWithCriteria(anyObject()))
					.thenReturn(getPaymentInQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForPaymentInReport() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentInReportService.getPaymentInReportWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockWorkEfficiencyReportWithCriteria() {
		try {
			when(iWorkEfficiencyReportService.getWorkEfficiencyReportWithCriteria(anyObject()))
					.thenReturn(setWorkEfficiencyReportDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataWorkEfficiencyReportWithCriteria() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iWorkEfficiencyReportService.getWorkEfficiencyReportWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutQueueDto getpaymentOutQueueDto() {
		PaymentOutQueueDto data = new PaymentOutQueueDto();
		data.setPaymentOutQueue(getpaymentOutQueue());
		return data;
	}

	public void setMockForPaymentOutReportInExcelFormat() {
		try {
			when(iPaymentOutReportService.getPaymentOutQueueWholeData(anyObject())).thenReturn(getpaymentOutQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForPaymentOutReportInExcelFormat() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentOutReportService.getPaymentOutQueueWholeData(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegistrationQueueDto getRegistrationQueueDto() {
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		registrationQueueDto.setRegistrationQueue(getRegistrationQueue());
		return registrationQueueDto;
	}

	public void setMockForRegistrationReportInExcelFormat() {
		try {
			when(iRegistrationReportService.getRegistrationQueueWholeData(anyObject()))
					.thenReturn(getRegistrationQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentInQueueDto setPaymentInQueueDto() {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		paymentInQueueDto.setPaymentInQueue(paymentInQueue());
		return paymentInQueueDto;
	}

	public void setgetPaymentInReportInExcelFormat() {
		try {
			when(iPaymentInReportService.getPaymentInQueueWholeData(anyObject())).thenReturn(setPaymentInQueueDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForgetworkEfficiencyReportInExcelFormat() {
		try {
			when(iWorkEfficiencyReportService.getWorkEfficiencyReportWithCriteriaInExcelFormat(anyObject()))
					.thenReturn(setWorkEfficiencyReportDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

//------------Test Cases------------//

	@Test
	public void testGetHolisticViewReport() {
		setMockdata();
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getHolisticViewReport(user);
		assertEquals("HolisticReport", modelAndView.getViewName());
	}

	@Test
	public void testGetWorkEfficiencyReport() {
		setMockForWorkEfficiencyReport();
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getWorkEfficiencyReport(user);
		assertEquals("WorkEfficiencyReport", modelAndView.getViewName());
	}

	@Test
	public void testForGetWorkEfficiencyReport() {
		setMockDataForWorkEfficiencyReport();
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getWorkEfficiencyReport(user);
		WorkEfficiencyReportDto workEfficiencyReportDto = (WorkEfficiencyReportDto) modelAndView.getModel()
				.get("workEfficiencyReportDto");
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				workEfficiencyReportDto.getErrorMessage());
	}

	@Test
	public void testGetPaymentOutQueueWithCriteria() {
		setMockForgetPaymentOutReport();
		ModelAndView modelAndView = reportController.getPaymentOutReport(getUser());
		assertEquals("PaymentOutReport", modelAndView.getViewName());
	}

	@Test
	public void testForGetPaymentOutQueueWithCriteria() {
		setMockDataForgetPaymentOutReport();
		ModelAndView modelAndView = reportController.getPaymentOutReport(getUser());
		PaymentOutQueueDto paymentOutQueueDto = (PaymentOutQueueDto) modelAndView.getModel().get("paymentOutQueueDto");
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				paymentOutQueueDto.getErrorMessage());
	}

	@Test
	public void testGetPaymentInReport() {
		setMockForPaymentInReport();
		ModelAndView modelAndView = reportController.getPaymentInReport(getUser());
		assertEquals("PaymentInReport", modelAndView.getViewName());
	}

	@Test
	public void testForGetPaymentInReport() {
		setMockDataForPaymentInReport();
		ModelAndView modelAndView = reportController.getPaymentInReport(getUser());
		PaymentInQueueDto paymentInQueueDto = (PaymentInQueueDto) modelAndView.getModel().get("paymentInQueueDto");
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				paymentInQueueDto.getErrorMessage());
	}

	@Test
	public void testGetRegistrationReport() {
		setMockdata();
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getRegistrationReport(user);
		assertEquals("RegReport", modelAndView.getViewName());
	}

	@Test
	public void testGetRegistrationQueueWithCriteria() {
		setMockdata();
		RegistrationQueueDto expectedResult = setRegistrationQueueData();
		RegistrationReportSearchCriteria request = new RegistrationReportSearchCriteria();
		RegistrationReportFilter filter = new RegistrationReportFilter();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setPage(getpage());
		request.setIsRequestFromReportPage(false);
		UserProfile user = getUser();
		RegistrationQueueDto actualResult = reportController.getRegistrationQueueWithCriteria(request, user);
		assertEquals(expectedResult.getRegistrationQueue().get(0).getContactName(),
				actualResult.getRegistrationQueue().get(0).getContactName());
	}

	@Test
	public void testForGetRegistrationQueueWithCriteria() {
		setMockGetRegistrationQueueWithCriteria();
		RegistrationReportSearchCriteria request = new RegistrationReportSearchCriteria();
		RegistrationReportFilter filter = new RegistrationReportFilter();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setPage(getpage());
		request.setIsRequestFromReportPage(false);
		RegistrationQueueDto actualResult = reportController.getRegistrationQueueWithCriteria(request, getUser());
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				actualResult.getErrorMessage());
	}

	@Test
	public void testGetPaymentInReportWithCriteria() {
		setMockForPaymentInReport();
		PaymentInQueueDto expectedResult = getPaymentInQueueDto();
		PaymentInReportFilter filter = new PaymentInReportFilter();
		PaymentInReportSearchCriteria request = new PaymentInReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		UserProfile user = getUser();
		PaymentInQueueDto actualResult = reportController.getPaymentInReportWithCriteria(request, user);
		assertEquals(expectedResult.getPaymentInQueue().get(0).getContactName(),
				actualResult.getPaymentInQueue().get(0).getContactName());
	}

	@Test
	public void testForGetPaymentInReportWithCriteria() {
		setMockDataForPaymentInReport();
		PaymentInReportFilter filter = new PaymentInReportFilter();
		PaymentInReportSearchCriteria request = new PaymentInReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		PaymentInQueueDto actualResult = reportController.getPaymentInReportWithCriteria(request, getUser());
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				actualResult.getErrorMessage());
	}

	@Test
	public void testGetPaymentOutReportWithCriteria() {
		setMockForgetPaymentOutReport();
		PaymentOutQueueDto expectedResult = getPaymentOutQueueDtoData();
		PaymentOutReportFilter filter = new PaymentOutReportFilter();
		PaymentOutReportSearchCriteria request = new PaymentOutReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		UserProfile user = getUser();
		PaymentOutQueueDto paymentOutQueueDto = reportController.getPaymentOutReportWithCriteria(request, user);
		assertEquals(expectedResult.getPaymentOutQueue().get(0).getContactName(),
				paymentOutQueueDto.getPaymentOutQueue().get(0).getContactName());
	}

	@Test
	public void testForGetPaymentOutReportWithCriteria() {
		setMockDataForgetPaymentOutReport();
		PaymentOutReportFilter filter = new PaymentOutReportFilter();
		PaymentOutReportSearchCriteria request = new PaymentOutReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		PaymentOutQueueDto paymentOutQueueDto = reportController.getPaymentOutReportWithCriteria(request, getUser());
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				paymentOutQueueDto.getErrorMessage());
	}

	@Test
	public void testGetWorkEfficiencyReportWithCriteria() {
		setMockWorkEfficiencyReportWithCriteria();
		WorkEfficiencyReportDto expectedResult = setWorkEfficiencyReportDto();
		WorkEfficiencyReportFilter filter = new WorkEfficiencyReportFilter();
		WorkEfficiencyReportSearchCriteria request = new WorkEfficiencyReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		UserProfile user = getUser();
		WorkEfficiencyReportDto actualResult = reportController.getWorkEfficiencyReportWithCriteria(request, user);
		assertEquals(expectedResult.getWorkEfficiencyReportData().get(0).getSeconds(),
				actualResult.getWorkEfficiencyReportData().get(0).getSeconds());
	}

	@Test
	public void testForGetWorkEfficiencyReportWithCriteria() {
		setMockDataWorkEfficiencyReportWithCriteria();
		WorkEfficiencyReportFilter filter = new WorkEfficiencyReportFilter();
		WorkEfficiencyReportSearchCriteria request = new WorkEfficiencyReportSearchCriteria();
		request.setFilter(filter);
		request.setIsFilterApply(true);
		request.setIsRequestFromReportPage(false);
		request.setPage(getpage());
		UserProfile user = getUser();
		WorkEfficiencyReportDto actualResult = reportController.getWorkEfficiencyReportWithCriteria(request, user);
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(),
				actualResult.getErrorMessage());
	}

	@Test
	public void testGetPaymentOutReportInExcelFormatTest() {
		setMockForPaymentOutReportInExcelFormat();
		String searchCriteria = "{\"filter\":{\"keyword\":\"0202088009936016-000000066\"},\"page\":"
				+ "{\"minRecord\":1,\"maxRecord\":1,\"totalRecords\":1,\"currentPage\":1,\"totalPages\":1},"
				+ "\"isFilterApply\":true,\"isRequestFromReportPage\":false,\"isFromClearFilter\":false}";
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getPaymentOutReportInExcelFormat(searchCriteria, user);
		assertEquals("excelView", modelAndView.getViewName());
	}

	@Test
	public void testGetRegistrationReportInExcelFormat() {
		setMockForRegistrationReportInExcelFormat();
		String requestDownloadCriteria = "{\"filter\":{\"keyword\":\"0201000010026273\\t\",\"sort\":null},\"page\":{\"minRecord\":1,\"maxRecord\":1,\"totalRecords\":1,\"currentPage\":1,\"totalPages\":1},\"isFilterApply\":true,\"isRequestFromReportPage\":true,\"isFromClearFilter\":false}";
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getRegistrationReportInExcelFormat(requestDownloadCriteria, user);
		assertEquals("excelView", modelAndView.getViewName());
	}

	@Test
	public void testGetPaymentInReportInExcelFormat() {
		setgetPaymentInReportInExcelFormat();
		String searchCriteria = "{\"filter\":{\"keyword\":\"0201000010009491-003796441\"},\"page\":{\"minRecord\":1,\"maxRecord\":1,\"totalRecords\":1,\"currentPage\":1,\"totalPages\":1},\"isFilterApply\":true,\"isRequestFromReportPage\":false,\"isFromClearFilter\":false}";
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getPaymentInReportInExcelFormat(searchCriteria, user);
		assertEquals("excelView", modelAndView.getViewName());
	}

	@Test
	public void testGetworkEfficiencyReportInExcelFormat() {
		setMockForgetworkEfficiencyReportInExcelFormat();
		String searchCriteria = "{\"filter\":{\"custType\":[\"PFX\"]},\"page\":{\"minRecord\":1,\"maxRecord\":2,\"totalRecords\":2,\"currentPage\":null,\"totalPages\":1},\"isFilterApply\":false,\"isRequestFromReportPage\":false,\"isFromClearFilter\":false}";
		UserProfile user = getUser();
		ModelAndView modelAndView = reportController.getworkEfficiencyReportInExcelFormat(searchCriteria, user);
		assertEquals("excelView", modelAndView.getViewName());
	}
}
