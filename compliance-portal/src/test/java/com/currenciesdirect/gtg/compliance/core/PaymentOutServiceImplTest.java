package com.currenciesdirect.gtg.compliance.core;

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

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
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
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentOutServiceImplTest {

	@InjectMocks
	PaymentOutServiceImpl paymentOutServiceImpl;

	@Mock
	IPaymentOutDBService iPaymentOutDBService;

	@Mock
	ITransform<PaymentOutUpdateDBDto, PaymentOutUpdateRequest> paymentOutUpdateTransformer;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private static final String NAME = "cd.comp.system";

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
		organization.add(NAME);
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

	public void setMockDataForGetPaymentOutQueueWithCriteria() {
		try {
			when(iPaymentOutDBService.getPaymentOutQueueWithCriteria(anyObject()))
					.thenReturn(getPaymentOutQueueDtoData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetPaymentOutQueueWithCriteria() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iPaymentOutDBService.getPaymentOutQueueWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequest() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(519136);
		paymentOutViewMoreRequest.setClientType("CFX");
		paymentOutViewMoreRequest.setMaxViewRecord(20);
		paymentOutViewMoreRequest.setMinViewRecord(11);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
		paymentOutViewMoreRequest.setOrganisation(NAME);
		paymentOutViewMoreRequest.setPaymentOutId(2797656);
		paymentOutViewMoreRequest.setServiceType("PAYMENT_IN");
		return paymentOutViewMoreRequest;
	}

	public ViewMoreResponse getViewMoreResponse() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> services = new ArrayList<>();
		FurtherPaymentInDetails furtherPaymentInDetails = new FurtherPaymentInDetails();
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

	public void setMockDataForGetFurtherPaymentOutViewMoreDetails() {
		try {
			when(iPaymentOutDBService.getViewMoreFurtherPaymentInDetails(anyObject()))
					.thenReturn(getViewMoreResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetFurtherPaymentOutViewMoreDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA);
			when(iPaymentOutDBService.getViewMoreFurtherPaymentInDetails(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutUpdateRequest getPaymentOutUpdateRequest() {
		StatusReasonUpdateRequest statusReasonUpdateRequest = new StatusReasonUpdateRequest();
		statusReasonUpdateRequest.setName("Alert from 3rd party");
		statusReasonUpdateRequest.setPreValue(false);
		statusReasonUpdateRequest.setUpdatedValue(false);
		StatusReasonUpdateRequest[] statusReasons = { statusReasonUpdateRequest };
		WatchlistUpdateRequest watchlistUpdateRequest = new WatchlistUpdateRequest();
		watchlistUpdateRequest.setName("Account Info Updated");
		watchlistUpdateRequest.setPreValue(false);
		watchlistUpdateRequest.setUpdatedValue(false);
		WatchlistUpdateRequest[] watchlist = { watchlistUpdateRequest };
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
		paymentOutUpdateRequest.setUserName(NAME);
		paymentOutUpdateRequest.setUserResourceId(778933);
		paymentOutUpdateRequest.setWatchlist(watchlist);
		paymentOutUpdateRequest.setStatusReasons(statusReasons);
		return paymentOutUpdateRequest;
	}

	public ActivityLogs getActivityLogs() {
		ActivityLogs activityLogs = new ActivityLogs();
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivity("Comment Added");
		activityLogDataWrapper.setActivityType("PAYMENT_OUT ADD_COMMENT");
		activityLogDataWrapper.setCreatedBy(NAME);
		activityLogDataWrapper.setCreatedOn("10/09/2020 18:39:09");
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		activityLogData.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogData);
		return activityLogs;
	}

	public void setMockDataForUpdatePaymentOut() {
		try {
			when(iPaymentOutDBService.updatePaymentOut(anyObject())).thenReturn(getActivityLogs());
			when(paymentOutUpdateTransformer.transform(anyObject())).thenReturn(getPaymentOutUpdateDBDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutUpdateDBDto getPaymentOutUpdateDBDto() {
		PaymentOutUpdateRequest request = getPaymentOutUpdateRequest();
		PaymentOutUpdateDBDto requestHandler = new PaymentOutUpdateDBDto();
		requestHandler.setAccountId(request.getAccountId());
		requestHandler.setContactId(request.getContactId());
		requestHandler.setAccountSfId(request.getAccountSfId());
		requestHandler.setContactSfId(request.getContactSfId());
		requestHandler.setPaymentOutId(request.getPaymentOutId());
		requestHandler.setOrgCode(request.getOrgCode());
		requestHandler.setComment(request.getComment());
		requestHandler.setCreatedBy(NAME);
		requestHandler.setOverallPaymentOutWatchlistStatus(
				Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS
						: ServiceStatusEnum.FAIL);
		requestHandler.setAddWatchlist(new ArrayList<>());
		requestHandler.setDeletedWatchlist(new ArrayList<>());
		requestHandler.setPreviousReason(new ArrayList<>());
		requestHandler.setAddReasons(new ArrayList<>());
		requestHandler.setDeletedReasons(new ArrayList<>());
		requestHandler.setActivityLogs(new ArrayList<>());
		requestHandler.setTradeBeneficiayId(request.getTradeBeneficiayId());
		requestHandler.setTradeContactid(request.getTradeContactId());
		requestHandler.setTradeContractnumber(request.getTradeContractNumber());
		requestHandler.setTradePaymentid(request.getTradePaymentId());
		requestHandler.setWatchlist(request.getWatchlist());
		requestHandler.setCustType(request.getCustType());
		requestHandler.setIsPaymentOnQueue(request.getIsOnQueue());
		requestHandler.setIsRequestFromQueue(request.getIsOnQueue());
		requestHandler.setFragusterEventServiceLogId(request.getFragusterEventServiceLogId());
		requestHandler.setUserResourceId(request.getUserResourceId());
		return requestHandler;
	}

	public ActivityLogRequest getActivityLogRequest() {
		ActivityLogRequest activityLogRequest = new ActivityLogRequest();
		activityLogRequest.setAccountId(49740);
		activityLogRequest.setEntityId(182832);
		activityLogRequest.setMaxRecord(10);
		activityLogRequest.setMinRecord(1);
		activityLogRequest.setRowToFetch(1);
		return activityLogRequest;

	}

	public ActivityLogs getActivityLogsForRepeatCheck() {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper
				.setActivity("After recheck Custom check status modified from fail to fail for Micheal John");
		activityLogDataWrapper.setActivityType("Paymentout custom check repeat");
		activityLogDataWrapper.setCreatedBy(NAME);
		activityLogDataWrapper.setCreatedOn("11/09/2020 17:43:52");
		activityLogData.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogData);
		return activityLogs;
	}

	public void setMockDataForGetActivites() {
		try {
			when(iPaymentOutDBService.getActivityLogs(anyObject())).thenReturn(getActivityLogsForRepeatCheck());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetActivites() {
		try {
			CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
			when(iPaymentOutDBService.getActivityLogs(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequestForCustomCheck() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
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

	public ViewMoreResponse getViewMoreResponseForCustomCheck() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> services = new ArrayList<>();
		PaymentOutCustomCheck paymentOutCustomCheck = new PaymentOutCustomCheck();
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

	public void setMockDataForGetPaymentOutViewMoreDetails() {
		try {
			when(iPaymentOutDBService.getMoreCustomCheckDetails(anyObject()))
					.thenReturn(getViewMoreResponseForCustomCheck());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetPaymentOutViewMoreDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
			when(iPaymentOutDBService.getMoreCustomCheckDetails(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetPaymentOutQueueWithCriteria() {
		setMockDataForGetPaymentOutQueueWithCriteria();
		PaymentOutSearchCriteria searchCriteria = new PaymentOutSearchCriteria();
		PaymentOutFilter filter = new PaymentOutFilter();
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		searchCriteria.setPage(getpage());
		try {
			PaymentOutQueueDto expectedResult = getPaymentOutQueueDtoData();
			PaymentOutQueueDto actualResult = paymentOutServiceImpl.getPaymentOutQueueWithCriteria(searchCriteria);
			assertEquals(expectedResult.getPaymentOutQueue().get(0).getAccountId(),
					actualResult.getPaymentOutQueue().get(0).getAccountId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetPaymentOutQueueWithCriteria() {
		setMockForGetPaymentOutQueueWithCriteria();
		PaymentOutSearchCriteria searchCriteria = new PaymentOutSearchCriteria();
		PaymentOutFilter filter = new PaymentOutFilter();
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		searchCriteria.setPage(getpage());
		try {
			paymentOutServiceImpl.getPaymentOutQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetFurtherPaymentOutDetails() {
		setMockDataForGetFurtherPaymentOutViewMoreDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponse();
			ViewMoreResponse actualResult = paymentOutServiceImpl
					.getFurtherPaymentOutDetails(getPaymentOutViewMoreRequest());
			assertEquals(expectedResult.getServices().get(0).getClass(), actualResult.getServices().get(0).getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetFurtherPaymentOutDetails() {
		setMockForGetFurtherPaymentOutViewMoreDetails();
		try {
			paymentOutServiceImpl.getFurtherPaymentOutDetails(getPaymentOutViewMoreRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetActivites() {
		setMockDataForGetActivites();
		ActivityLogs expectedResult = getActivityLogsForRepeatCheck();
		ActivityLogs actualResult;
		try {
			actualResult = paymentOutServiceImpl.getActivityLogs(getActivityLogRequest());
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetActivites() {
		setMockForGetActivites();
		try {
			paymentOutServiceImpl.getActivityLogs(getActivityLogRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.DATABASE_GENERIC_ERROR.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetPaymentOutViewMoreDetails() {
		setMockDataForGetPaymentOutViewMoreDetails();
		ViewMoreResponse expectedResult = getViewMoreResponseForCustomCheck();
		ViewMoreResponse actualResult;
		try {
			actualResult = paymentOutServiceImpl.viewMoreDetails(getPaymentOutViewMoreRequestForCustomCheck());
			assertSame(expectedResult.getServices().get(0).getClass(), actualResult.getServices().get(0).getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetPaymentOutViewMoreDetails() {
		setMockForGetPaymentOutViewMoreDetails();
		try {
			paymentOutServiceImpl.viewMoreDetails(getPaymentOutViewMoreRequestForCustomCheck());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.DATABASE_GENERIC_ERROR.getErrorDescription(), e.getDescription());
		}
	}
}