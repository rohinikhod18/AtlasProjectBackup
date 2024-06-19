package com.currenciesdirect.gtg.compliance.httpport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.IPaymentInService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentInControllerTest {

	@InjectMocks
	PaymentInController paymentInController;
	@Mock
	IPaymentInService ipaymentInService;
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
		List<String> paymentMethod = new ArrayList<>();
		paymentMethod.add("SWITCH/DEBIT");
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		paymentInQueueDto.setSavedSearch(getsavedSearch());
		paymentInQueueDto.setCountry(country);
		paymentInQueueDto.setCurrency(currency);
		paymentInQueueDto.setLegalEntity(legalEntity);
		paymentInQueueDto.setOrganization(organization);
		paymentInQueueDto.setOnfidoStatus(onfidoStatus);
		paymentInQueueDto.setOwner(owner);
		paymentInQueueDto.setPage(getpage());
		paymentInQueueDto.setWatchList(watchlist);
		paymentInQueueDto.setUser(getUser());
		paymentInQueueDto.setTotalPages(1);
		paymentInQueueDto.setTotalRecords(1);
		paymentInQueueDto.setTransValue(transValue);
		paymentInQueueDto.setPaymentMethod(paymentMethod);
		paymentInQueueDto.setPaymentInQueue(PaymentInQueue());
		
		return paymentInQueueDto;
	}

	public List<PaymentInQueueData> PaymentInQueue() {
		List<PaymentInQueueData> data = new ArrayList<>();
		PaymentInQueueData paymentInQueueData = new PaymentInQueueData();

		paymentInQueueData.setTransactionId("0201000010009491-003796436");
		paymentInQueueData.setDate("05/11/2020 13:05:06");
		paymentInQueueData.setClientId("0201000010009491");
		paymentInQueueData.setContactName("Poi main Clark");
		paymentInQueueData.setType("PFX");
		paymentInQueueData.setSellCurrency("GBP");
		paymentInQueueData.setAmount("700");
		paymentInQueueData.setMethod("BACS/CHAPS/TT");
		paymentInQueueData.setOverallStatus("HOLD");
		paymentInQueueData.setFraugster("PASS");
		paymentInQueueData.setSanction("PASS");
		paymentInQueueData.setBlacklist("PASS");
		paymentInQueueData.setWatchlist("FAIL");
		paymentInQueueData.setContactId(50620);
		paymentInQueueData.setAccountId(49762);
		paymentInQueueData.setPaymentInId("281295");
		paymentInQueueData.setLegalEntity("CDLGB");
		data.add(paymentInQueueData);
		return data;
	}
	public SavedSearch getsavedSearch() {
		SavedSearchData savedSearchData = new SavedSearchData();
		savedSearchData.setSaveSearchFilter("{\"custType\":[\"PFX\"],\"organization\":[\"TorFX\"]}");
		savedSearchData.setSaveSearchName("TorFx");
		return null;

	}
public void setMockDataForGetPaymentInQueue() 
{
	try {
		when(ipaymentInService.getPaymentInQueueWithCriteria(anyObject()))
				.thenReturn(getPaymentInQueueDto());
		
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}
public void setMockDataForGetPaymentIn() 
{
	try {
		CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
		when(ipaymentInService.getPaymentInQueueWithCriteria(anyObject())).thenThrow(e);} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}

public void setMockDatForGetPaymentInQueueWithCriteria()
{
	try {
	when(ipaymentInService.getPaymentInQueueWithCriteria(anyObject()))
	.thenReturn(getPaymentInQueueDto());
} catch (CompliancePortalException e) {
System.out.println(e);
}
	}
	
	
	public PaymentInUpdateRequest getPaymentInUpdateRequest()
	{
		StatusReasonUpdateRequest reasons= new StatusReasonUpdateRequest();
		reasons.setName("Alert from 3rd party");
		reasons.setPreValue(false);
		reasons.setUpdatedValue(false);
		
		StatusReasonUpdateRequest[] statusReasons= {reasons};
		
		WatchlistUpdateRequest watchlistData= new WatchlistUpdateRequest();
		watchlistData.setName("Account Info Updated");
		watchlistData.setPreValue(false);
		watchlistData.setUpdatedValue(false);
		WatchlistUpdateRequest[] watchlistDataSet= {watchlistData};
		
		PaymentInUpdateRequest paymentInUpdateRequest= new PaymentInUpdateRequest();
		paymentInUpdateRequest.setAccountId(49767);
		paymentInUpdateRequest.setAccountSfId("0016E000020plr0TAB");
		paymentInUpdateRequest.setClientNumber("0016E000020plr0TAB");
		paymentInUpdateRequest.setComment("abc");
		paymentInUpdateRequest.setContactId(50630);
		paymentInUpdateRequest.setContactName("John2 main-deb Cena");
		paymentInUpdateRequest.setContactSfId("0037G23747c7pFlDTJ");
		paymentInUpdateRequest.setCustType("PFX");
		paymentInUpdateRequest.setDebtorAmount("700");
		paymentInUpdateRequest.setEmail("john2cena@gmail.ca");
		paymentInUpdateRequest.setFragusterEventServiceLogId(977257);
		paymentInUpdateRequest.setIsOnQueue(true);
		paymentInUpdateRequest.setLegalEntity("CDLCA");
		paymentInUpdateRequest.setPaymentMethod("BACS/CHAPS/TT");
		paymentInUpdateRequest.setOrgCode("Currencies Direct");
		paymentInUpdateRequest.setOverallWatchlistStatus(false);
		paymentInUpdateRequest.setPaymentinId(281299);
		paymentInUpdateRequest.setPrePaymentInStatus("HOLD");
		paymentInUpdateRequest.setSellCurrency("GBP");
		paymentInUpdateRequest.setTradeContactId("66365382");
		paymentInUpdateRequest.setTradeContractNumber("0201000010009491-003796441");
		paymentInUpdateRequest.setTradePaymentId("281301");
		paymentInUpdateRequest.setUpdatedPaymentInStatus("HOLD");
		paymentInUpdateRequest.setUserName(Name);
		paymentInUpdateRequest.setUserResourceId(666554);
		paymentInUpdateRequest.setWatchlist(watchlistDataSet);
		paymentInUpdateRequest.setStatusReasons(statusReasons);
		return paymentInUpdateRequest;
	}
	public ActivityLogs getActivityLogs()
	{
		
		ActivityLogDataWrapper activityLogDataWrapper= new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivity("Comment Added");
		activityLogDataWrapper.setActivityType("PAYMENT_IN ADD_COMMENT");
		activityLogDataWrapper.setCreatedBy(Name);
		activityLogDataWrapper.setCreatedOn("07/09/2020 17:50:41");
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		activityLogData.add(activityLogDataWrapper);
		ActivityLogs activityLogs = new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);
		return activityLogs;
	} 
	public void setMockDatForUpdatePaymentIn()
	{
		try {
			when(ipaymentInService.updatePaymentIn(anyObject()))
			.thenReturn(getActivityLogs());
		} catch (CompliancePortalException e) {
		System.out.println(e);
		}
}
	public void setMockForUpdatePaymentIn()
	{
		try {
			CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA); 
			when(ipaymentInService.updatePaymentIn(anyObject()))
			.thenThrow(e);
		} catch (CompliancePortalException e) {
		System.out.println(e);
		}
}
	
	
	public ViewMoreResponse getViewMoreResponse()
	{
		ViewMoreResponse viewMoreResponse= new ViewMoreResponse();
		List<IDomain> services= new ArrayList<>();
		
		Sanction sanction= new Sanction();
		sanction.setEntityType("sanction");
		sanction.setEventServiceLogId(6207);
		sanction.setIsRequired(true);
		sanction.setOfacList("Safe");
		sanction.setSanctionId("001-D-0000000093");
		sanction.setStatus(false);
		sanction.setStatusValue("PENDING");
		sanction.setUpdatedBy("cd_api_user");
		sanction.setUpdatedOn("18/04/2017 18:35:34");
		sanction.setWorldCheck("Confirmed hit");
		services.add(sanction );
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
		
	}
	public PaymentInViewMoreRequest getPaymentInViewMoreRequest()
	{
		PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
		paymentInViewMoreRequest.setAccountId(331);
		paymentInViewMoreRequest.setClientType("CFX");
		paymentInViewMoreRequest.setEntityType("DEBTOR");
		paymentInViewMoreRequest.setMaxViewRecord(11);
		paymentInViewMoreRequest.setMinViewRecord(2);
		paymentInViewMoreRequest.setNoOfDisplayRecords(1);
		paymentInViewMoreRequest.setOrganisation("TorFX");
		paymentInViewMoreRequest.setPaymentInId(93);
		paymentInViewMoreRequest.setServiceType("SANCTION");
		
		return paymentInViewMoreRequest;
	}
	public void setMockDataForGetPaymentInViewMoreDetails()
	{
		try {
			when(ipaymentInService.viewMoreDetails(anyObject()))
			.thenReturn(getViewMoreResponse());
		} catch (CompliancePortalException e) {
		System.out.println(e);
		}
	}
	public void setMockForGetPaymentInViewMoreDetails()
	{
		try {
			CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR); 
			when(ipaymentInService.viewMoreDetails(anyObject()))
			.thenThrow(e);
		} catch (CompliancePortalException e) {
		System.out.println(e);
		}
	}
	//------Test Cases-----//
	@Test
	public void testGetPaymentInQueue()
	{
	setMockDataForGetPaymentInQueue() ;
	PaymentInQueueDto expectedlResult=getPaymentInQueueDto();
		ModelAndView modelAndView =paymentInController.getPaymentInQueue(getUser());
		PaymentInQueueDto actualResult=(PaymentInQueueDto) modelAndView.getModel().get("paymentInQueueDto");
		assertEquals(expectedlResult.getPaymentInQueue().get(0).getTransactionId(),actualResult.getPaymentInQueue().get(0).getTransactionId());
	}
	@Test
	public void testForGetPaymentInQueue()
	{
		setMockDataForGetPaymentIn() ;
		ModelAndView modelAndView =paymentInController.getPaymentInQueue(getUser());
		PaymentInQueueDto paymentInQueueDto=(PaymentInQueueDto) modelAndView.getModel().get("paymentInQueueDto");
	assertEquals("Error while fetching data",paymentInQueueDto.getErrorMessage());
	}
	
	
	@Test
	public void testGetPaymentInQueueWithCriteria()
	{
		setMockDataForGetPaymentInQueue();
		PaymentInSearchCriteria searchCriteria= new PaymentInSearchCriteria();
		PaymentInFilter filter=new PaymentInFilter();
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		PaymentInQueueDto expectedResult=getPaymentInQueueDto();
		PaymentInQueueDto actualResult =paymentInController.getPaymentInQueueWithCriteria(searchCriteria, getUser());
        assertEquals(expectedResult.getCountry(),actualResult.getCountry());
	}
	@Test
	public void testForGetPaymentInQueueWithCriteria()
	{
		setMockDataForGetPaymentIn();
		PaymentInSearchCriteria searchCriteria= new PaymentInSearchCriteria();
		PaymentInFilter filter=new PaymentInFilter();
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		PaymentInQueueDto actualResult =paymentInController.getPaymentInQueueWithCriteria(searchCriteria, getUser());
		assertEquals("Error while fetching data",actualResult.getErrorMessage());
	}
	
	
	@Test
	public void testUpdatePaymentIn()
	{
		setMockDatForUpdatePaymentIn();
		ActivityLogs expectedResult=getActivityLogs();
		ActivityLogs actualResult=paymentInController.updatePaymentIn(getPaymentInUpdateRequest(), getUser());
	   assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),actualResult.getActivityLogData().get(0).getActivity());
	
	}
	
	@Test
	public void testForUpdatePaymentIn()
	{
		setMockForUpdatePaymentIn();
		ActivityLogs actualResult=paymentInController.updatePaymentIn(getPaymentInUpdateRequest(), getUser());
	   assertEquals("Error while updating data.",actualResult.getErrorMessage());
	
	}
	@Test
	public void testGetPaymentInViewMoreDetails()
	{
		setMockDataForGetPaymentInViewMoreDetails();
		ViewMoreResponse expectedResult=getViewMoreResponse();
		ViewMoreResponse actualResult= paymentInController.getPaymentInViewMoreDetails(getPaymentInViewMoreRequest());
	assertSame(expectedResult.getServices().get(0).getClass(),actualResult.getServices().get(0).getClass());
	}
	
	@Test
	public void testForGetPaymentInViewMoreDetails()
	{
		setMockForGetPaymentInViewMoreDetails();
		ViewMoreResponse actualResult= paymentInController.getPaymentInViewMoreDetails(getPaymentInViewMoreRequest());
		 assertEquals("Error",actualResult.getErrorMessage());
	}
}
