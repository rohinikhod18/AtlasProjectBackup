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
import com.currenciesdirect.gtg.compliance.core.IPaymentOutService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentOutControllerTest {

	@InjectMocks
	PaymentOutController paymentOutController;
	
	@Mock
	IPaymentOutService iPaymentOutService;
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

	public void setMockForGetPaymentOutQueue() {
		try {
			when( iPaymentOutService.getPaymentOutQueueWithCriteria(anyObject()))
					.thenReturn(getPaymentOutQueueDtoData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	public void setMockDataForGetPaymentOutQueue() {
		try {
			CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when( iPaymentOutService.getPaymentOutQueueWithCriteria(anyObject()))
					.thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
public PaymentOutUpdateRequest getPaymentOutUpdateRequest()
{
	StatusReasonUpdateRequest statusReasonUpdateRequest= new StatusReasonUpdateRequest();
	statusReasonUpdateRequest.setName("Alert from 3rd party");
	statusReasonUpdateRequest.setPreValue(false);
	statusReasonUpdateRequest.setUpdatedValue(false);
	StatusReasonUpdateRequest[] statusReasons= {statusReasonUpdateRequest};
	WatchlistUpdateRequest watchlistUpdateRequest= new WatchlistUpdateRequest();
	watchlistUpdateRequest.setName("Account Info Updated");
	watchlistUpdateRequest.setPreValue(false);
	watchlistUpdateRequest.setUpdatedValue(false);
	WatchlistUpdateRequest[] watchlist= {watchlistUpdateRequest};
		PaymentOutUpdateRequest paymentOutUpdateRequest = new PaymentOutUpdateRequest();
		paymentOutUpdateRequest.setAccountId(543775);
		paymentOutUpdateRequest.setAccountSfId("0010O00001tGABfQAO");
		paymentOutUpdateRequest.setBeneCheckStatus("PASS");
		paymentOutUpdateRequest.setBeneCheckStatus("8,877.39");
		paymentOutUpdateRequest.setBeneficiaryName("KONTRI LTD");
		paymentOutUpdateRequest.setBuyCurrency("EUR");
		paymentOutUpdateRequest.setClientNumber("0202000003119142");
		paymentOutUpdateRequest.setComment("abc");
		paymentOutUpdateRequest.setContactId(672303);
		paymentOutUpdateRequest.setContactSfId("0030O000025C6mrQAC");
		paymentOutUpdateRequest.setCountry("Germany (DEU)");
		paymentOutUpdateRequest.setCountryRiskLevel("(Low Risk Country)");
		paymentOutUpdateRequest.setCustType("CFX");
		paymentOutUpdateRequest.setEmail("przemyslaw.sienkiewicz@junk.co.uk");
		paymentOutUpdateRequest.setFragusterEventServiceLogId(23263770);
		paymentOutUpdateRequest.setIsOnQueue(true);
		paymentOutUpdateRequest.setLegalEntity("CDLGB");
		paymentOutUpdateRequest.setOrgCode("Currencies Direct");
		paymentOutUpdateRequest.setOverallWatchlistStatus(false);
		paymentOutUpdateRequest.setPaymentOutId(2849850);
		paymentOutUpdateRequest.setPrePaymentOutStatus("HOLD");
		paymentOutUpdateRequest.setTradeBeneficiayId("4046422");
		paymentOutUpdateRequest.setTradeContactId("406362");
		paymentOutUpdateRequest.setTradeContractNumber("0202000003119142-003600376");
		paymentOutUpdateRequest.setTradePaymentId("3600376");
		paymentOutUpdateRequest.setUpdatedPaymentOutStatus("HOLD");
		paymentOutUpdateRequest.setUserName(Name);
		paymentOutUpdateRequest.setUserResourceId(778933);
		paymentOutUpdateRequest.setWatchlist(watchlist);
		paymentOutUpdateRequest.setStatusReasons(statusReasons);
	return paymentOutUpdateRequest;
}
public ActivityLogs getActivityLogs()
{
	ActivityLogs activityLogs=new ActivityLogs();
	ActivityLogDataWrapper activityLogDataWrapper= new ActivityLogDataWrapper();
	activityLogDataWrapper.setActivity("Comment Added");
	activityLogDataWrapper.setActivityType("PAYMENT_OUT ADD_COMMENT");
	activityLogDataWrapper.setCreatedBy(Name);
	activityLogDataWrapper.setCreatedOn("10/09/2020 18:39:09");
	List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
	activityLogData.add(activityLogDataWrapper);
	activityLogs.setActivityLogData(activityLogData);
	return activityLogs;	
}
public void setMockDataForUpdatePaymentOut()
{
	try {
		when( iPaymentOutService.updatePaymentOut(anyObject()))
				.thenReturn(getActivityLogs());
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}
public void setMockForUpdatePaymentOut()
{
	try {
		CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
		when( iPaymentOutService.updatePaymentOut(anyObject()))
				.thenThrow(e);
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}

public PaymentOutViewMoreRequest getPaymentOutViewMoreRequest()
{
	PaymentOutViewMoreRequest paymentOutViewMoreRequest=new PaymentOutViewMoreRequest();
	paymentOutViewMoreRequest.setAccountId(519136);
	paymentOutViewMoreRequest.setClientType("CFX");
	paymentOutViewMoreRequest.setMaxViewRecord(20);
	paymentOutViewMoreRequest.setMinViewRecord(11);
	paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
	paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
	paymentOutViewMoreRequest.setPaymentOutId(2797656);
	paymentOutViewMoreRequest.setServiceType("PAYMENT_IN");
	
	return paymentOutViewMoreRequest;
	
}
public ViewMoreResponse getViewMoreResponse()
{
	ViewMoreResponse viewMoreResponse= new ViewMoreResponse();
	List<IDomain> services= new ArrayList<>();
	FurtherPaymentInDetails furtherPaymentInDetails= new FurtherPaymentInDetails();
	furtherPaymentInDetails.setAccount("23104804982601");
	furtherPaymentInDetails.setAccountName("-");
	furtherPaymentInDetails.setAmount("1,038.61");
	furtherPaymentInDetails.setDateOfPayment("29/06/2020");
	furtherPaymentInDetails.setMethod("BACS/CHAPS/TT");
	furtherPaymentInDetails.setPaymentId(1300646);
	furtherPaymentInDetails.setRiskGuardianScore("-");
	furtherPaymentInDetails.setSellCurrency("GBP");
	furtherPaymentInDetails.setTradeContractNumber("0202000003117724-003816859");
	services.add(furtherPaymentInDetails);
	viewMoreResponse.setServices(services);
	
	return viewMoreResponse;
}
public void setMockDataForGetFurtherPaymentOutViewMoreDetails()
{
	try {
		when( iPaymentOutService.getFurtherPaymentOutDetails(anyObject()))
				.thenReturn(getViewMoreResponse());
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}
public void setMockForGetFurtherPaymentOutViewMoreDetails()
{
	try {
		CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA);
		when( iPaymentOutService.getFurtherPaymentOutDetails(anyObject()))
				.thenThrow(e);
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}


public PaymentOutViewMoreRequest getPaymentOutViewMoreRequestForCustomCheck()
{
	PaymentOutViewMoreRequest paymentOutViewMoreRequest=new PaymentOutViewMoreRequest();
	paymentOutViewMoreRequest.setAccountId(49740);
	paymentOutViewMoreRequest.setClientType("PFX");
	paymentOutViewMoreRequest.setMaxViewRecord(4);
	paymentOutViewMoreRequest.setMinViewRecord(2);
	paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
	paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
	paymentOutViewMoreRequest.setPaymentOutId(182832);
	paymentOutViewMoreRequest.setServiceType("VELOCITYCHECK");
	paymentOutViewMoreRequest.setEntityType("BENEFICIARY");
	return paymentOutViewMoreRequest;
	
}
public ViewMoreResponse getViewMoreResponseForCustomCheck()
{
	ViewMoreResponse viewMoreResponse= new ViewMoreResponse();
	List<IDomain> services= new ArrayList<>();
	PaymentOutCustomCheck paymentOutCustomCheck= new PaymentOutCustomCheck();
	paymentOutCustomCheck.setCheckedOn("04/09/2020 16:34:31");
	paymentOutCustomCheck.setEnitityId("856792");
	paymentOutCustomCheck.setEntityType("BENEFICIARY");
	paymentOutCustomCheck.setFraudPredictStatus("SERVICE_FAILURE");
	paymentOutCustomCheck.setId(988938);
	paymentOutCustomCheck.setStatus("8");
	services.add(paymentOutCustomCheck);
	viewMoreResponse.setServices(services);
	
	return viewMoreResponse;
}
public void setMockDataForGetPaymentOutViewMoreDetails()
{
	try {
		when( iPaymentOutService.viewMoreDetails(anyObject()))
				.thenReturn(getViewMoreResponseForCustomCheck());
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}
public void setMockForGetPaymentOutViewMoreDetails()
{
	try {
		CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
		when( iPaymentOutService.viewMoreDetails(anyObject()))
				.thenThrow(e);
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}

public ActivityLogRequest getActivityLogRequest()
{
	ActivityLogRequest activityLogRequest = new ActivityLogRequest();
	activityLogRequest.setAccountId(49740);
	activityLogRequest.setEntityId(182832);
	activityLogRequest.setMaxRecord(10);
	activityLogRequest.setMinRecord(1);
	activityLogRequest.setRowToFetch(1);

	return activityLogRequest;
	
}

public ActivityLogs getActivityLogsForRepeatCheck()
{
	ActivityLogs activityLogs=new ActivityLogs();
	List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
	ActivityLogDataWrapper activityLogDataWrapper= new ActivityLogDataWrapper();
	activityLogDataWrapper.setActivity("After recheck Custom check status modified from fail to fail for Micheal John");
	activityLogDataWrapper.setActivityType("Paymentout custom check repeat");
	activityLogDataWrapper.setCreatedBy(Name);
	activityLogDataWrapper.setCreatedOn("11/09/2020 17:43:52");
	activityLogData.add(activityLogDataWrapper);
	activityLogs.setActivityLogData(activityLogData);
	return activityLogs;
}
public void setMockDataForGetActivites()
{
	try {
		
		when(iPaymentOutService.getActivityLogs(anyObject())).thenReturn(getActivityLogsForRepeatCheck());
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}
public void setMockForGetActivites()
{
	try {
		CompliancePortalException e= new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
		when(iPaymentOutService.getActivityLogs(anyObject())).thenThrow(e);
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}

@Test
public void testGetPaymentOutQueue()
{
	setMockForGetPaymentOutQueue();
	ModelAndView modelAndView =paymentOutController.getPaymentOutQueue(getUser());
	assertEquals("PaymentOutQueue",modelAndView.getViewName());
}
@Test
public void testForGetPaymentOutQueue()
{
	setMockDataForGetPaymentOutQueue();
	ModelAndView modelAndView =paymentOutController.getPaymentOutQueue(getUser());
	PaymentOutQueueDto paymentOutQueueDto=(PaymentOutQueueDto) modelAndView.getModel().get("paymentOutQueueDto");
	assertEquals("Error while fetching data",paymentOutQueueDto.getErrorMessage());
}


@Test
public void testGetPaymentOutQueueWithCriteria()
{
	setMockForGetPaymentOutQueue();
	 PaymentOutSearchCriteria searchCriteria= new PaymentOutSearchCriteria();
	 PaymentOutFilter filter= new PaymentOutFilter();
	 searchCriteria.setFilter(filter);
	 searchCriteria.setIsFilterApply(false);
	 searchCriteria.setIsLandingPage(false);
	 searchCriteria.setIsRequestFromReportPage(false);
	 searchCriteria.setPage(getpage());
	 PaymentOutQueueDto expectedResult=getPaymentOutQueueDtoData();
	PaymentOutQueueDto actualResult =paymentOutController.getPaymentOutQueueWithCriteria(searchCriteria,getUser());
	assertEquals(expectedResult.getPaymentOutQueue().get(0).getAccountId(),actualResult.getPaymentOutQueue().get(0).getAccountId());
}

@Test
public void testForGetPaymentOutQueueWithCriteria()
{
	setMockDataForGetPaymentOutQueue();
	 PaymentOutSearchCriteria searchCriteria= new PaymentOutSearchCriteria();
	 PaymentOutFilter filter= new PaymentOutFilter();
	 searchCriteria.setFilter(filter);
	 searchCriteria.setIsFilterApply(false);
	 searchCriteria.setIsLandingPage(false);
	 searchCriteria.setIsRequestFromReportPage(false);
	 searchCriteria.setPage(getpage());
	PaymentOutQueueDto actualResult =paymentOutController.getPaymentOutQueueWithCriteria(searchCriteria,getUser());
	assertEquals("Error while fetching data",actualResult.getErrorMessage());
}
@Test
public void testUpdatePaymentOut()
{
	setMockDataForUpdatePaymentOut();
	ActivityLogs expectedResult=getActivityLogs();
	ActivityLogs actualResult= paymentOutController.updatePaymentOut(getPaymentOutUpdateRequest(), getUser());
assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),actualResult.getActivityLogData().get(0).getActivity());
}

@Test
public void testForUpdatePaymentOut()
{
	setMockForUpdatePaymentOut();
	ActivityLogs actualResult= paymentOutController.updatePaymentOut(getPaymentOutUpdateRequest(), getUser());
	assertEquals("Error",actualResult.getErrorMessage());
}

@Test
public void testGetFurtherPaymentOutViewMoreDetails()
{
	setMockDataForGetFurtherPaymentOutViewMoreDetails();
	ViewMoreResponse expectedResult=getViewMoreResponse();
	ViewMoreResponse  actualResult=paymentOutController.getFurtherPaymentOutViewMoreDetails(getPaymentOutViewMoreRequest());
	assertSame(expectedResult.getServices().get(0).getClass(),actualResult.getServices().get(0).getClass());
}
@Test
public void testForGetFurtherPaymentOutViewMoreDetails()
{
	setMockForGetFurtherPaymentOutViewMoreDetails();
	ViewMoreResponse  actualResult=paymentOutController.getFurtherPaymentOutViewMoreDetails(getPaymentOutViewMoreRequest());
	assertEquals("Error while updating data.",actualResult.getErrorMessage());
}
@Test
public void testGetPaymentOutViewMoreDetails()
{
	setMockDataForGetPaymentOutViewMoreDetails();
	ViewMoreResponse expectedResult=getViewMoreResponseForCustomCheck();
	ViewMoreResponse  actualResult=paymentOutController.getPaymentOutViewMoreDetails(getPaymentOutViewMoreRequestForCustomCheck());
	assertSame(expectedResult.getServices().get(0).getClass(),actualResult.getServices().get(0).getClass());	
}
@Test
public void testForGetPaymentOutViewMoreDetails()
{
	setMockForGetPaymentOutViewMoreDetails();
	ViewMoreResponse  actualResult=paymentOutController.getPaymentOutViewMoreDetails(getPaymentOutViewMoreRequestForCustomCheck());
	assertEquals("Error",actualResult.getErrorMessage());
	}

@Test
public void testGetActivites()
{
	setMockDataForGetActivites();
	ActivityLogs expectedResult=getActivityLogsForRepeatCheck();
	ActivityLogs actualResult=paymentOutController.getActivites(getActivityLogRequest(), getUser());
assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),actualResult.getActivityLogData().get(0).getActivity());
}

@Test
public void testForGetActivites()
{
	setMockForGetActivites();
	ActivityLogs actualResult=paymentOutController.getActivites(getActivityLogRequest(), getUser());
	assertEquals("Error",actualResult.getErrorMessage());
}
}