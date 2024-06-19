package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutBlacklist;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentOutDBServiceImplTest {

	@InjectMocks
	PaymentOutDBServiceImpl paymentOutDBServiceImpl;

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Mock
	AbstractDao abstractDao;
	
	@Mock
	ITransform<PaymentOutDetailsDto, PaymentOutDBDto> itransform;
	private static final String NAME = "cd.comp.system";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
					.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true)
					.thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
					.thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		} catch (SQLException e) {
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

	public ActivityLogRequest getActivityLogRequest() {
		ActivityLogRequest activityLogRequest = new ActivityLogRequest();
		activityLogRequest.setAccountId(655383);
		activityLogRequest.setEntityId(2801562);
		activityLogRequest.setMaxRecord(10);
		activityLogRequest.setMinRecord(1);
		activityLogRequest.setRowToFetch(10);
		activityLogRequest.setUser(getUser());
		return activityLogRequest;
	}

	public ActivityLogRequest getActivityLogRequests() {
		ActivityLogRequest activityLogRequest = new ActivityLogRequest();
		activityLogRequest.setAccountId(655383);
		activityLogRequest.setMaxRecord(10);
		activityLogRequest.setMinRecord(1);
		activityLogRequest.setRowToFetch(10);
		activityLogRequest.setUser(getUser());
		return activityLogRequest;
	}

	public void setMockForGetActivityLogs() {
		try {
			when(rs.getString("Activity"))
					.thenReturn("After recheck Custom check status modified from fail to fail for Eur Individual Type");
			when(rs.getString("ActivityType")).thenReturn("PAYMENT_OUT CUSTOM_CHECK_REPEAT");
			when(rs.getString("User")).thenReturn(NAME);
			when(rs.getTimestamp("CreatedOn")).thenReturn(Timestamp.valueOf("2020-12-02 13:50:32.453"));

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ActivityLogs setActivityLog() {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataWrapperList = new ArrayList<>();
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper
				.setActivity("After recheck Custom check status modified from fail to fail for Eur Individual Type");
		activityLogDataWrapper.setActivityType("PAYMENT_OUT CUSTOM_CHECK_REPEAT");
		activityLogDataWrapper.setCreatedBy(NAME);
		activityLogDataWrapper.setCreatedOn("2020-12-02 13:50:32.453");
		activityLogDataWrapperList.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogDataWrapperList);
		return activityLogs;
	}

	@Test
	public void testGetActivityLogs() {
		setMockForGetActivityLogs();

		try {
			ActivityLogs expectedResult = setActivityLog();
			ActivityLogs actualResult = paymentOutDBServiceImpl.getActivityLogs(getActivityLogRequest());
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetActivityLogs() {
		try {
			paymentOutDBServiceImpl.getActivityLogs(getActivityLogRequests());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequestSanction() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setEntityType("BENEFICIARY");
		paymentOutViewMoreRequest.setMaxViewRecord(11);
		paymentOutViewMoreRequest.setMinViewRecord(2);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("SANCTION");
		return paymentOutViewMoreRequest;
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreSanction() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setMaxViewRecord(11);
		paymentOutViewMoreRequest.setMinViewRecord(2);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("SANCTION");
		return paymentOutViewMoreRequest;
	}

	public void setMockForSanctionDetails() {
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn(
					"{\"status\":\"PASS\",\"sanctionId\":\"002-P-0001127750\",\"ofacList\":\"Safe\",\"worldCheck\":\"Safe\",\"updatedOn\":\"2020-12-02 04:28:35.718\",\"providerName\":\"FINSCAN\",\"providerMethod\":\"SLLookupMulti\"}");
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-12-02 04:28:35.72");
			when(rs.getString("updatedBy")).thenReturn(NAME);
			when(rs.getInt("id")).thenReturn(22583273);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ViewMoreResponse getViewMoreResponseSanction() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		Sanction sanction = new Sanction();
		sanction.setEntityType("BENEFICIARY");
		sanction.setEventServiceLogId(22583273);
		sanction.setIsRequired(true);
		sanction.setOfacList("Safe");
		sanction.setSanctionId("002-P-0001127750");
		sanction.setStatus(true);
		sanction.setStatusValue("PASS");
		sanction.setUpdatedBy(NAME);
		sanction.setUpdatedOn("2020-12-02 04:28:35.72");
		sanction.setWorldCheck("Safe");
		domainList.add(sanction);
		return viewMoreResponse;
	}

	@Test
	public void testGetMoreSanctionDetails() {
		setMockForSanctionDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponseSanction();
			ViewMoreResponse actualResult = paymentOutDBServiceImpl
					.getMoreSanctionDetails(getPaymentOutViewMoreRequestSanction());
			assertEquals(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetMoreSanctionDetails() {

		try {
			paymentOutDBServiceImpl.getMoreSanctionDetails(getPaymentOutViewMoreSanction());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequestFraugster() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setEntityType("CONTACT");
		paymentOutViewMoreRequest.setMaxViewRecord(11);
		paymentOutViewMoreRequest.setMinViewRecord(2);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("FRAUGSTER");
		return paymentOutViewMoreRequest;
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreFraugster() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setMaxViewRecord(11);
		paymentOutViewMoreRequest.setMinViewRecord(2);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(1);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("FRAUGSTER");
		return paymentOutViewMoreRequest;
	}

	public void setMockForFraugsterDetails() {
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn(
					"{\"status\":\"PASS\",\"frgTransId\":\"160ad2b1-5564-45ce-9d71-8b769ba5231c\",\"score\":\"-99.07\",\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"28015360201000010030093\"}");
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-12-02 04:28:33.743");
			when(rs.getString("updatedBy")).thenReturn(NAME);
			when(rs.getInt("id")).thenReturn(22583269);
			when(rs.getInt("poid")).thenReturn(182847);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ViewMoreResponse getViewMoreResponseFraugster() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		Fraugster fraugster = new Fraugster();
		fraugster.setCreatedOn("02/12/2020 04:28:33");
		fraugster.setFraugsterId("160ad2b1-5564-45ce-9d71-8b769ba5231c");
		fraugster.setId(22583269);
		fraugster.setScore("-99.07");
		fraugster.setStatus(true);
		fraugster.setUpdatedBy(NAME);
		domainList.add(fraugster);
		return viewMoreResponse;
	}

	@Test
	public void testGetMoreFraugsterDetails() {
		setMockForFraugsterDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponseFraugster();
			ViewMoreResponse actualResult = paymentOutDBServiceImpl
					.getMoreFraugsterDetails(getPaymentOutViewMoreRequestFraugster());
			assertEquals(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetMoreFraugsterDetails() {

		try {
			paymentOutDBServiceImpl.getMoreFraugsterDetails(getPaymentOutViewMoreFraugster());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequestCustomCheck() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setEntityType("CONTACT");
		paymentOutViewMoreRequest.setMaxViewRecord(4);
		paymentOutViewMoreRequest.setMinViewRecord(4);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(6);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("VELOCITYCHECK");
		return paymentOutViewMoreRequest;
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreCustomCheck() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setMaxViewRecord(4);
		paymentOutViewMoreRequest.setMinViewRecord(4);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(6);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setServiceType("VELOCITYCHECK");
		return paymentOutViewMoreRequest;
	}

	public void setMockForCustomCheckDetails() {
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn(
					"{\"countryWhitelisted\":false,\"countryWhitelistedForFundsIn\":false,\"orgCode\":\"Currencies Direct\",\"accId\":654144,\"paymentTransId\":2801536,\"overallStatus\":\"FAIL\",\"velocityCheck\":{\"status\":\"FAIL\",\"noOffundsoutTxn\":\"FAIL\",\"permittedAmoutcheck\":\"PASS\",\"beneCheck\":\"FAIL\",\"matchedAccNumber\":\"0201000009926582, 0401000009926671, 0101000009926609, 0301000009926599, 0401000009930798, 0401000009935607, 0201000005936163, 0401000010005168\",\"noOfFundsOutTxnDetails\":\"(Current Count : 3, Max allowed count:2)\"},\"whiteListCheck\":{\"currency\":\"NOT_REQUIRED\",\"amoutRange\":\"NOT_REQUIRED\",\"thirdParty\":\"NOT_REQUIRED\",\"reasonOfTransfer\":\"NOT_REQUIRED\",\"status\":\"NOT_REQUIRED\"},\"accountWhiteList\":{\"orgCode\":\"Currencies Direct\",\"accountId\":654144,\"createdOn\":\"2020-11-13 10:41:56.985\",\"updatedOn\":\"2020-12-02 09:47:09.981\",\"approvedReasonOfTransList\":[\"\"],\"approvedBuyCurrencyAmountRangeList\":[{\"code\":\"GBP\",\"txnAmountUpperLimit\":50000.0},{\"code\":\"CAD\",\"txnAmountUpperLimit\":50000.0}],\"approvedSellCurrencyAmountRangeList\":[{\"code\":\"CAD\",\"txnAmountUpperLimit\":50000.0},{\"code\":\"NZD\",\"txnAmountUpperLimit\":50000.0}],\"approvedThirdpartyAccountList\":[],\"approvedHighRiskCountryList\":[],\"documentationRequiredWatchlistSellCurrency\":[],\"usClientListBBeneAccNumber\":[],\"approvedOPIAccountNumber\":[],\"approvedHighRiskCountryListForFundsIn\":[]},\"fraudPredictStatus\":\"PASS\"}");
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-12-02 15:17:10.197");
			when(rs.getInt("id")).thenReturn(22584074);
			when(rs.getInt(Constants.ENTITY_TYPE)).thenReturn(2);
			when(rs.getString("entityid")).thenReturn("1127750");
			when(rs.getString("status")).thenReturn("2");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ViewMoreResponse getViewMoreResponseCustomCheck() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		PaymentOutCustomCheck paymentOutCustomCheck = new PaymentOutCustomCheck();
		paymentOutCustomCheck.setCheckedOn("02/12/2020 15:17:10");
		paymentOutCustomCheck.setEnitityId("1127750");
		paymentOutCustomCheck.setEntityType("BENEFICIARY");
		paymentOutCustomCheck.setFraudPredictStatus("PASS");
		paymentOutCustomCheck.setId(22584074);
		paymentOutCustomCheck.setStatus("2");
		VelocityCheck velocityCheck = new VelocityCheck();
		velocityCheck.setMatchedAccNumber(
				"0201000009926582, 0401000009926671, 0101000009926609, 0301000009926599, 0401000009930798, 0401000009935607, 0201000005936163, 0401000010005168");
		velocityCheck.setBeneCheck("FAIL");
		velocityCheck.setNoOffundsoutTxn("FAIL (Current Count : 3, Max allowed count:2)");
		velocityCheck.setPermittedAmoutcheck("PASS");
		WhitelistCheck whitelistCheck = new WhitelistCheck();
		whitelistCheck.setAmoutRange("NOT_REQUIRED");
		whitelistCheck.setCurrency("NOT_REQUIRED");
		whitelistCheck.setReasonOfTransfer("NOT_REQUIRED");
		whitelistCheck.setThirdParty("NOT_REQUIRED");
		paymentOutCustomCheck.setVelocityCheck(velocityCheck);
		paymentOutCustomCheck.setWhiteListCheck(whitelistCheck);
		domainList.add(paymentOutCustomCheck);
		return viewMoreResponse;
	}

	@Test
	public void testGetMoreCustomCheckDetails() {
		setMockForCustomCheckDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponseCustomCheck();
			ViewMoreResponse actualResult = paymentOutDBServiceImpl
					.getMoreCustomCheckDetails(getPaymentOutViewMoreRequestCustomCheck());
			assertEquals(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {

		}
	}

	@Test
	public void testForGetMoreCustomCheckDetails() {

		try {
			paymentOutDBServiceImpl.getMoreCustomCheckDetails(getPaymentOutViewMoreCustomCheck());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public void setMockFurtherPaymentOutDetails() {
		try {
			when(rs.getInt("poid")).thenReturn(182847);
			when(rs.getString(Constants.BENEFICIARY_FIRST_NAME)).thenReturn("Micheal");
			when(rs.getString(Constants.BENEFICIARY_LAST_NAME)).thenReturn("John");
			when(rs.getString("AccountNumber")).thenReturn("BARCUGKXXXX951753857");
			when(rs.getTimestamp(Constants.DATE_OF_PAYMENT)).thenReturn(Timestamp.valueOf("2020-03-09 10:31:21.0"));
			when(rs.getString(Constants.AMOUNT)).thenReturn("15000.00000000");
			when(rs.getString("BuyCurrency")).thenReturn("USD");
			when(rs.getString("MaturityDate")).thenReturn("2020-03-09");
			when(rs.getString(Constants.TRADE_CONTRACT_NUMBER)).thenReturn("0201000006484316-000000009");
			when(rs.getString("BankName")).thenReturn("HDFC BANK LIMITED");
			when(rs.getString("BuyCurrency")).thenReturn("USD");
			when(rs.getString("BuyCurrency")).thenReturn("USD");
		} catch (SQLException e) {
			System.out.println(e);

		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequest() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(49740);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setEntityId(50581);
		paymentOutViewMoreRequest.setEntityType("CONTACT");
		paymentOutViewMoreRequest.setMaxViewRecord(20);
		paymentOutViewMoreRequest.setMinViewRecord(11);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(10);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setServiceType("PAYMENT_OUT");
		return paymentOutViewMoreRequest;
	}

	public PaymentOutViewMoreRequest getPaymentOutViewMoreRequests() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setEntityId(50581);
		paymentOutViewMoreRequest.setEntityType("CONTACT");
		paymentOutViewMoreRequest.setMaxViewRecord(20);
		paymentOutViewMoreRequest.setMinViewRecord(11);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(10);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setServiceType("PAYMENT_OUT");
		return paymentOutViewMoreRequest;
	}

	public ViewMoreResponse getViewMoreResponsegetFurtherPaymentOutDetails() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> services = new ArrayList<>();
		FurtherPaymentOutDetails furtherPaymentOutDetails = new FurtherPaymentOutDetails();
		furtherPaymentOutDetails.setAccount("BARCUGKXXXX951753857");
		furtherPaymentOutDetails.setAccountName("Micheal John");
		furtherPaymentOutDetails.setAmount("15,000");
		furtherPaymentOutDetails.setBankName("HDFC BANK LIMITED");
		furtherPaymentOutDetails.setBuyCurrency("USD");
		furtherPaymentOutDetails.setDateOfPayment("09/03/2020");
		furtherPaymentOutDetails.setPaymentId(182847);
		furtherPaymentOutDetails.setTradeContractNumber("0201000006484316-000000009");
		furtherPaymentOutDetails.setValuedate("09/03/2020");
		services.add(furtherPaymentOutDetails);
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
	}

	@Test
	public void testGetFurtherPaymentOutDetails() {
		setMockFurtherPaymentOutDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponsegetFurtherPaymentOutDetails();
			ViewMoreResponse actualResult = paymentOutDBServiceImpl
					.getFurtherPaymentOutDetails(getPaymentOutViewMoreRequest());
			assertEquals(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetFurtherPaymentOutDetails() {
		try {
			paymentOutDBServiceImpl.getFurtherPaymentOutDetails(getPaymentOutViewMoreRequests());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public PaymentOutViewMoreRequest getPaymentOutViewFurtherPaymentInDetails() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setAccountId(654144);
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setMaxViewRecord(20);
		paymentOutViewMoreRequest.setMinViewRecord(11);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(10);
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setServiceType("PAYMENT_IN");
		return paymentOutViewMoreRequest;
	}

	public PaymentOutViewMoreRequest getPaymentOutViewPaymentInDetails() {
		PaymentOutViewMoreRequest paymentOutViewMoreRequest = new PaymentOutViewMoreRequest();
		paymentOutViewMoreRequest.setClientType("PFX");
		paymentOutViewMoreRequest.setMaxViewRecord(20);
		paymentOutViewMoreRequest.setMinViewRecord(11);
		paymentOutViewMoreRequest.setNoOfDisplayRecords(10);
		paymentOutViewMoreRequest.setPaymentOutId(2801536);
		paymentOutViewMoreRequest.setOrganisation("Currencies Direct");
		paymentOutViewMoreRequest.setServiceType("PAYMENT_IN");
		return paymentOutViewMoreRequest;
	}

	public void setMockFurtherPaymentInDetails() {
		try {
			when(rs.getInt("pyiid")).thenReturn(1314701);
			when(rs.getString(Constants.PAYMENT_METHOD_IN)).thenReturn("SWITCH/DEBIT");
			when(rs.getString("Ccfirstname")).thenReturn("John");
			when(rs.getString("Ccfirstname")).thenReturn("vishal tiwari");
			when(rs.getString("DebtorName")).thenReturn("vishal tiwari");
			when(rs.getString("DebtorAccountNumber")).thenReturn("5555********4444");
			when(rs.getTimestamp(Constants.DATE_OF_PAYMENT)).thenReturn(Timestamp.valueOf("2020-09-09 12:57:12.0"));
			when(rs.getString(Constants.AMOUNT)).thenReturn("323.00000000");
			when(rs.getString("SellCurrency")).thenReturn("GBP");
			when(rs.getString(Constants.TRADE_CONTRACT_NUMBER)).thenReturn("0201000010030093-003830912");
			when(rs.getString("TScore")).thenReturn("100.0");
			when(rs.getString("BuyCurrency")).thenReturn("USD");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ViewMoreResponse getViewMoreResponsegetFurtherPaymentInDetails() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> services = new ArrayList<>();
		FurtherPaymentInDetails furtherPaymentInDetails = new FurtherPaymentInDetails();
		furtherPaymentInDetails.setAccount("5555********4444");
		furtherPaymentInDetails.setAccountName("vishal tiwari");
		furtherPaymentInDetails.setAmount("323");
		furtherPaymentInDetails.setDateOfPayment("09/09/2020");
		furtherPaymentInDetails.setMethod("SWITCH/DEBIT");
		furtherPaymentInDetails.setPaymentId(1314701);
		furtherPaymentInDetails.setRiskGuardianScore("-");
		furtherPaymentInDetails.setSellCurrency("GBP");
		furtherPaymentInDetails.setTradeContractNumber("0201000010030093-003830912");
		services.add(furtherPaymentInDetails);
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
	}

	@Test
	public void testGetViewMoreFurtherPaymentInDetails() {
		setMockFurtherPaymentInDetails();
		try {
			ViewMoreResponse expectedResult = getViewMoreResponsegetFurtherPaymentInDetails();
			ViewMoreResponse actualResult = paymentOutDBServiceImpl
					.getViewMoreFurtherPaymentInDetails(getPaymentOutViewFurtherPaymentInDetails());
			assertEquals(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetViewMoreFurtherPaymentInDetails() {

		try {
			paymentOutDBServiceImpl.getViewMoreFurtherPaymentInDetails(getPaymentOutViewPaymentInDetails());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

	public PaymentOutSearchCriteria getPaymentOutSearchCriteria() {
		String[] type = { "CFX" };
		PaymentOutSearchCriteria paymentOutSearchCriteria = new PaymentOutSearchCriteria();
		paymentOutSearchCriteria.setIsFilterApply(false);
		paymentOutSearchCriteria.setIsLandingPage(false);
		paymentOutSearchCriteria.setIsRequestFromReportPage(false);
		paymentOutSearchCriteria.setPage(getpage());
		PaymentOutFilter filter = new PaymentOutFilter();
		filter.setUserProfile(getUser());
		filter.setExcludeCustType(type);
		paymentOutSearchCriteria.setFilter(filter);
		return paymentOutSearchCriteria;
	}

	public PaymentOutDetailsDto getPaymentOutDetailsDto() {
		PaymentOutDetailsDto paymentOutDetailsDto = new PaymentOutDetailsDto();
		AccountWrapper account = new AccountWrapper();
		account.setTradeAccountNumber("0201040010026208");
		account.setPurposeOfTran("----");
		account.setServiceRequired("Transfer");
		account.setSourceOfFund("----");
		account.setSource("Web");
		account.setAffiliateName("STP - temporary affiliate code");
		paymentOutDetailsDto.setAccount(account);
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper
				.setActivity("After recheck Custom check status modified from fail to fail for Mark Pattison");
		activityLogDataWrapper.setActivityType("Paymentout custom check repeat");
		activityLogDataWrapper.setCreatedBy(NAME);
		activityLogDataWrapper.setCreatedOn("12/04/2021 16:24:54");
		ActivityLogDataWrapper activityLogDataWrapper1 = new ActivityLogDataWrapper();
		activityLogDataWrapper1.setActivity("Amount Velocity Check Fail");
		activityLogDataWrapper1.setActivityType("Payment Out-STP");
		activityLogDataWrapper1.setCreatedBy(NAME);
		activityLogDataWrapper1.setCreatedOn("02/04/2021 11:52:23");
		activityLogData.add(activityLogDataWrapper1);
		activityLogData.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogData);
		paymentOutDetailsDto.setActivityLogs(activityLogs);
		paymentOutDetailsDto.setAlertComplianceLog("----");
		PaymentOutBlacklist blacklist = new PaymentOutBlacklist();
		Blacklist benficiaryBlacklist = new Blacklist();
		benficiaryBlacklist.setAccNumberMatchedData("(No Match Found)");
		benficiaryBlacklist.setAccountNumber("false");
		benficiaryBlacklist.setBankName("false");
		benficiaryBlacklist.setBankNameMatchedData("(No Match Found)");
		benficiaryBlacklist.setEmail("NOT_REQUIRED");
		benficiaryBlacklist.setEntityType("BENEFICIARY");
		benficiaryBlacklist.setFailCount(0);
		benficiaryBlacklist.setId(1056455);
		benficiaryBlacklist.setIp("");
		benficiaryBlacklist.setIsRequired(true);
		benficiaryBlacklist.setPassCount(3);
		benficiaryBlacklist.setName("false");
		benficiaryBlacklist.setNameMatchedData("(No Match Found)");
		benficiaryBlacklist.setPhone(false);
		benficiaryBlacklist.setStatus(true);

		blacklist.setBenficiaryBlacklist(benficiaryBlacklist);
		paymentOutDetailsDto.setBlacklist(blacklist);
		Watchlist watachList = new Watchlist();
		WatchListData watchlistData = new WatchListData();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlistData.setContactId(-1);
		watchlistData.setName("Account Info Updated");
		watchlistData.setValue(false);
		WatchListData watchlistData1 = new WatchListData();
		watchlistData1.setContactId(-1);
		watchlistData1.setName("Cash based industry");
		watchlistData1.setValue(false);
		watchlistDataList.add(watchlistData);
		watchlistDataList.add(watchlistData1);
		watachList.setWatchlistData(watchlistDataList);
		paymentOutDetailsDto.setWatchlist(watachList);
		paymentOutDetailsDto.setContactWatchlist(watachList);
		ContactWrapper currentContact = new ContactWrapper();
		currentContact.setTradeContactID(7896005);
		currentContact.setDob("17/05/1992");
		currentContact.setPhoneWork("----");
		currentContact.setEmail("abc2133@gmail");
		currentContact.setOccupation("Other");
		currentContact.setAccountId(56107);
		currentContact.setAddress("A, Dalemoor, Keighley, BD22 8EZ, GBR");
		currentContact.setComplianceStatus("ACTIVE");
		currentContact.setCountryOfResidence("GBR");
		currentContact.setCrmAccountId("0030E000099efr9DKN");
		currentContact.setCrmContactId("0048E000104yC9IDRM");
		currentContact.setIsUsClient(false);
		currentContact.setMobile("+44-7311123456");
		currentContact.setName("Micheal Norse");
		currentContact.setRegComplete("25/03/2021 12:21:25");
		currentContact.setRegIn("25/03/2021 12:21:25");
		paymentOutDetailsDto.setCurrentContact(currentContact);
		paymentOutDetailsDto.setCurrentRecord(3);
		CustomCheck customCheck = new CustomCheck();
		CountryCheck countryCheck = new CountryCheck();
		countryCheck.setCheckedOn("02/04/2021 11:52:15");
		countryCheck.setCountry("France");
		countryCheck.setCountryCheckTotalRecords(1);
		countryCheck.setEntityType("BENEFICIARY");
		countryCheck.setFailCount(0);
		countryCheck.setId(1056454);
		countryCheck.setIsRequired(false);
		countryCheck.setPassCount(1);
		countryCheck.setRiskLevel("(Low Risk Country)");
		countryCheck.setStatus("Pass");
		customCheck.setCountryCheck(countryCheck);
		PaymentOutCustomCheck paymentOutCustomCheck = new PaymentOutCustomCheck();
		paymentOutCustomCheck.setCheckedOn("12/04/2021 16:24:53");
		paymentOutCustomCheck.setEntityType("BENEFICIARY");
		EuPoiCheck euPoiCheck = new EuPoiCheck();
		euPoiCheck.setStatus("Not Required");
		paymentOutCustomCheck.setEuPoiCheck(euPoiCheck);
		paymentOutCustomCheck.setFailCount(2);
		paymentOutCustomCheck.setFraudPredictStatus("PASS");
		paymentOutCustomCheck.setId(1056654);
		paymentOutCustomCheck.setIsRequired(false);
		paymentOutCustomCheck.setPassCount(2);
		paymentOutCustomCheck.setTotalRecords(2);
		VelocityCheck velocityCheck = new VelocityCheck();
		velocityCheck.setBeneCheck("PASS");
		velocityCheck.setNoOffundsoutTxn("PASS");
		velocityCheck.setPermittedAmoutcheck("FAIL(Max:1,001)");
		WhitelistCheck whiteListCheck = new WhitelistCheck();
		whiteListCheck.setAmoutRange("Not Required");
		whiteListCheck.setCurrency("Not Required");
		whiteListCheck.setReasonOfTransfer("Not Required");
		whiteListCheck.setThirdParty("Not Required");
		paymentOutCustomCheck.setWhiteListCheck(whiteListCheck);
		paymentOutCustomCheck.setVelocityCheck(velocityCheck);
		customCheck.setPaymentOutCustomCheck(paymentOutCustomCheck);
		paymentOutDetailsDto.setCustomCheck(customCheck);
		paymentOutDetailsDto.setCustomRuleFPFlag(true);
		List<UploadDocumentTypeDBDto> documentList = new ArrayList<>();
		UploadDocumentTypeDBDto doc = new UploadDocumentTypeDBDto();
		doc.setDocumentName("National ID Card");
		doc.setId(1);
		UploadDocumentTypeDBDto doc1 = new UploadDocumentTypeDBDto();
		doc1.setDocumentName("Drivers License");
		doc1.setId(2);
		UploadDocumentTypeDBDto doc2 = new UploadDocumentTypeDBDto();
		doc2.setDocumentName("Passport");
		doc2.setId(3);
		documentList.add(doc);
		documentList.add(doc1);
		documentList.add(doc2);
		paymentOutDetailsDto.setDocumentList(documentList);
		Fraugster fraugster = new Fraugster();
		fraugster.setCreatedOn("02/04/2021 11:52:12");
		fraugster.setFailCount(0);
		fraugster.setFraugsterId("2f91fc63-abed-4d59-8eb1-369f885812dc");
		fraugster.setFraugsterTotalRecords(1);
		fraugster.setId(1056453);
		fraugster.setIsRequired(false);
		fraugster.setPassCount(1);
		fraugster.setScore("-99.97");
		fraugster.setStatus(true);
		fraugster.setStatusValue("Pass");
		paymentOutDetailsDto.setFraugster(fraugster);
		paymentOutDetailsDto.setName("");
		paymentOutDetailsDto.setIsOnQueue(true);
		paymentOutDetailsDto.setLocked(true);
		paymentOutDetailsDto.setLockedBy(NAME);
		paymentOutDetailsDto.setNoOfContactsForAccount(1);
		return paymentOutDetailsDto;
	}

	public void setMockForPaymentOutDetails() {
		try {
			when(rs.getString("AccountAttrib")).thenReturn("{\"onQueue\":false,\"acc_sf_id\":\"0030E000099efr9DKN\","
					+ "\"trade_acc_id\":411356,\"trade_acc_number\":\"0201040010026208\",\"branch\":\"Moorgate HO\","
					+ "\"channel\":\"Mobile\",\"cust_type\":\"PFX\",\"act_name\":\"Micheal Norse\",\"country_interest\":"
					+ "\"\",\"trade_name\":\"\",\"trasaction_purpose\":\"\",\"op_country\":\"\",\"turnover\":\"\","
					+ "\"service_req\":\"Transfer\",\"buying_currency\":\"EUR\",\"selling_currency\":\"GBP\",\"txn_value\":"
					+ "\"2,000 - 5,000\",\"source_of_fund\":\"\",\"source\":\"Web\",\"sub_source\":\"Natural\",\"referral\":"
					+ "\"\",\"referral_text\":\"Natural\",\"extended_referral\":\"\",\"ad_campaign\":\"\",\"keywords\":"
					+ "\"\",\"search_engine\":\"\",\"website\":\"\",\"affiliate_name\":\"STP - temporary affiliate code\","
					+ "\"reg_mode\":\"Mobile\",\"organization_legal_entity\":\"CDLGB\",\"version\":1,\"reg_date_time\":"
					+ "\"2019-05-23T11:38:01Z\",\"company\":{\"billing_address\":\"A, Dalemoor,Keighley,BD22 8EZ,UK\","
					+ "\"company_phone\":\"\",\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\","
					+ "\"country_of_establishment\":\"\",\"corporate_type\":\"\",\"registration_no\":\"\",\"incorporation_date\":"
					+ "\"\",\"industry\":\"\",\"etailer\":\"false\",\"option\":\"false\",\"type_of_financial_account\":\"\",\"ccj\":"
					+ "\"false\",\"ongoing_due_diligence_date\":\"\",\"est_no_transactions_pcm\":\"\"},\"corporate_compliance\":{"
					+ "\"sic\":\"\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\",\"net_worth\":\"\","
					+ "\"fixed_assets\":\"\",\"total_liabilities_and_equities\":\"\",\"total_share_holders\":\"\",\"global_ultimate_DUNS\":"
					+ "\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":\"\",\"registration_date\":\"\",\"match_name\":\"\","
					+ "\"iso_country_code_2_digit\":\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"\","
					+ "\"gross_income\":\"\",\"net_income\":\"\",\"total_current_assets\":\"\",\"total_assets\":\"\","
					+ "\"total_long_term_liabilities\":\"\",\"total_current_liabilities\":\"\",\"total_matched_shareholders\":\"\","
					+ "\"financial_strength\":\"\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\","
					+ "\"risk_direction\":\"\",\"updated_risk\":\"\",\"failure_score\":\"\",\"delinquency_failure_score\":"
					+ "\"\",\"continent\":\"\",\"country\":\"\",\"state_country\":\"\",\"duns_number\":\"\",\"trading_styles\":"
					+ "\"\",\"us1987_primary_SIC_4_digit\":\"\",\"financial_figures_month\":\"\",\"financial_figures_year\":"
					+ "\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\",\"net_worth_amount\":"
					+ "\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":\"\",\"domestic_ultimate_record\":"
					+ "\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":\"\",\"headquarter_details\":\"\","
					+ "\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":\"\",\"global_ultimate_parent_details\":"
					+ "\"\",\"credit_limit\":\"\",\"risk_rating\":\"\",\"profit_loss\":\"\"},\"conversionPrediction\":{"
					+ "\"AccountId\":\"1120O014274TF7CQAW\",\"ETVBand\":\"2k – 10k\",\"conversionFlag\":\"High\",\"conversionProbability\":0.82625836},"
					+ "\"affiliate_number\":\"A000000\"}");
			when(rs.getInt(Constants.ACCOUNT_ID)).thenReturn(56107);
			when(rs.getString("vBeneficiaryFirstName")).thenReturn("Mark");
			when(rs.getString("vBeneficiaryLastName")).thenReturn("Pattison");
			when(rs.getString("vBuyingAmount")).thenReturn("385.59000000");
			when(rs.getString("ContactAttrib"))
					.thenReturn("{\"onQueue\":false,\"contact_sf_id\":\"0048E000104yC9IDRM\","
							+ "\"trade_contact_id\":7896005,\"title\":\"\",\"pref_name\":\"\",\"first_name\":\"Micheal\","
							+ "\"middle_name\":\"\",\"last_name\":\"Norse\",\"second_surname\":\"\",\"mothers_surname\":\"\","
							+ "\"dob\":\"1992-05-17\",\"position_of_significance\":\"\",\"authorised_signatory\":\"true\","
							+ "\"job_title\":\"Other\",\"address_type\":\"Current Address\",\"address1\":\"A, Dalemoor\","
							+ "\"town_city_muncipalty\":\"Keighley\",\"building_name\":\"\",\"street\":\"A, Dalemoor\","
							+ "\"street_number\":\"\",\"street_type\":\"\",\"post_code\":\"BD22 8EZ\",\"post_code_lat\":0.0,"
							+ "\"post_code_long\":0.0,\"country_of_residence\":\"GBR\",\"home_phone\":\"\",\"work_phone\":\"\","
							+ "\"work_phone_ext\":\"\",\"mobile_phone\":\"+44-7311123456\",\"primary_phone\":\"Mobile\",\"email\":"
							+ "\"abc2133@gmail\",\"second_email\":\"\",\"designation\":\"Other\",\"ip_address\":\"172.31.4.211\","
							+ "\"ip_address_latitude\":\"0.00\",\"ip_address_longitude\":\"0.00\",\"is_primary_contact\":false,"
							+ "\"country_of_nationality\":\"\",\"gender\":\"\",\"occupation\":\"Other\",\"passport_number\":\"\","
							+ "\"passport_country_code\":\"\",\"passport_exipry_date\":\"\",\"passport_full_name\":\"\","
							+ "\"passport_mrz_line_1\":\"\",\"passport_mrz_line_2\":\"\",\"passport_birth_family_name\":\"\","
							+ "\"passport_name_at_citizen\":\"\",\"passport_birth_place\":\"\",\"driving_license\":\"\","
							+ "\"driving_version_number\":\"\",\"driving_license_card_number\":\"\",\"driving_license_country\":"
							+ "\"\",\"driving_state_code\":\"\",\"driving_expiry\":\"\",\"medicare_card_number\":\"\","
							+ "\"medicare_ref_number\":\"\",\"australia_rta_card_number\":\"\",\"state_province_county\":\"\","
							+ "\"municipality_of_birth\":\"\",\"country_of_birth\":\"\",\"state_of_birth\":\"\",\"civic_number\":"
							+ "\"\",\"sub_building\":\"\",\"unit_number\":\"\",\"sub_city\":\"Keighley\",\"national_id_type\":"
							+ "\"\",\"national_id_number\":\"\",\"years_in_address\":\"3.00\",\"residential_status\":\"\","
							+ "\"version\":1,\"district\":\"\",\"area_number\":\"\",\"aza\":\"Keighley\",\"prefecture\":\"\",\"floor_number\":\"\"}");
			when(rs.getInt("ContactId")).thenReturn(57013);
			when(rs.getString("ContactName")).thenReturn("Micheal Norse");
			when(rs.getString("ContractNumber")).thenReturn("3111I119700-048");
			when(rs.getString("Country")).thenReturn("FRA");
			when(rs.getString("CRMAccountId")).thenReturn("0030E000099efr9DKN");
			when(rs.getString("Organisation")).thenReturn("Currencies Direct");
			when(rs.getString("PaymentOutAttributes")).thenReturn(
					"{\"org_code\":\"Currencies Direct\",\"source_application\":\"Titan\",\"trade\":{\"cust_type\":\"PFX\",\"trade_contact_id\":7896005,\"contract_number\":\"3111I119700-048\",\"deal_date\":\"2019-02-11\",\"selling_amount\":350.0,\"buying_amount\":385.59,\"purpose_of_trade\":\"LIVING_FUNDS\",\"maturity_date\":\"2019-02-11\",\"trade_account_number\":\"0201040010026208\",\"value_date\":\"2019-02-11\",\"buying_amount_base_value\":1477.75,\"buy_currency\":\"EUR\",\"sell_currency\":\"GBP\",\"third_party_payment\":true,\"customer_legal_entity\":\"CDLGB\"},\"beneficiary\":{\"phone\":\"\",\"first_name\":\"Mark\",\"last_name\":\"Pattison\",\"email\":\"\",\"country\":\"FRA\",\"account_number\":\"0201040010026208\",\"currency_code\":\"EUR\",\"beneficiary_id\":8788368,\"beneficiary_bankid\":8788368,\"beneficary_bank_name\":\"CREDIT AGRICOLE SA\",\"beneficary_bank_address\":\"PO BOX 516 5 PLACE JEAN JAURES 33001 BORDEAUX FRANCE\",\"trans_ts\":\"2019-02-05T19:38:22Z\",\"payment_reference\":\"CD 73408\",\"opi_created_date\":\"2019-02-11\",\"beneficiary_type\":\"Individual\",\"payment_fundsout_id\":183078,\"amount\":385.59},\"version\":1,\"osr_id\":\"13492d2f-121a-4a0f-a5bc-6dc3b2c1f627\"}");
			when(rs.getInt("Id")).thenReturn(183077);
			when(rs.getString("vReasonoftransfer")).thenReturn("LIVING_FUNDS");
			when(rs.getTimestamp("RegIn")).thenReturn(Timestamp.valueOf("2021-03-25 10:52:53.013"));
			when(rs.getString("vSellingAmount")).thenReturn("350.00000000");
			when(rs.getString("TradeAccountNumber")).thenReturn("0201040010026208");
			when(rs.getString("TradeContactId")).thenReturn("7896005");
			when(rs.getInt("UserResourceLockId")).thenReturn(696838);
			when(rs.getString("AccComplianceStatus")).thenReturn("ACTIVE");
			when(rs.getString("ContactComplianceStatus")).thenReturn("ACTIVE");
			when(rs.getString("BeneficiaryCounry")).thenReturn("FRA");
			when(rs.getString("CountryOfBeneficiaryFullName")).thenReturn("France");
			when(rs.getString("BuyCurrency")).thenReturn("EUR");
			when(rs.getString("PaymentOutStaus")).thenReturn("HOLD");
			when(rs.getString("CRMContactID")).thenReturn("0048E000104yC9IDRM");
			when(rs.getTimestamp("RegComplete")).thenReturn(Timestamp.valueOf("2021-03-25 12:21:25.087"));
			when(rs.getString("TradePaymentId")).thenReturn("183078");
			when(rs.getString("vtradeBeneficiaryId")).thenReturn("8788368");
			when(rs.getBoolean("Deleted")).thenReturn(false);
			when(rs.getBoolean("isOnQueue")).thenReturn(true);
			when(rs.getString("InitialStatus")).thenReturn("NSTP;CUVAC");
			when(rs.getTimestamp("UpdatedOn")).thenReturn(Timestamp.valueOf("2021-04-12 16:24:54.96"));
			when(rs.getString("poiExists")).thenReturn("0");
			when(rs.getString("vTradeBankID")).thenReturn("8788368");
			when(rs.getString("vBuyingAmount")).thenReturn("385.59000000");
			when(rs.getString("LegalEntity")).thenReturn("CDLGB");
			when(rs.getString("LockedBy")).thenReturn(NAME);
			when(rs.getString(Constants.SERVICE_TYPE)).thenReturn("BLACKLIST").thenReturn("FRAUGSTER");
			when(rs.getInt("EntityType")).thenReturn(2).thenReturn(3);
			when(rs.getString("EntityId")).thenReturn("8788368").thenReturn("57013");
			when(rs.getInt("EventServiceLogId")).thenReturn(1056455).thenReturn(1056453);
			when(rs.getInt("EventServiceStatus")).thenReturn(1);
			when(rs.getString("Summary")).thenReturn("{\"status\":\"PASS\",\"ip\":\"NOT_REQUIRED\",\"email\":"
					+ "\"NOT_REQUIRED\",\"accountNumber\":\"false\",\"accNumberMatchedData\":\"No Match Found\","
					+ "\"name\":\"false\",\"nameMatchedData\":\"No Match Found\",\"domain\":\"NOT_REQUIRED\","
					+ "\"webSite\":\"NOT_REQUIRED\",\"bankName\":\"false\",\"bankNameMatchedData\":\"No Match Found\"}")
					.thenReturn("{\"status\":\"PASS\",\"frgTransId\":\"2f91fc63-abed-4d59-8eb1-369f885812dc\","
							+ "\"score\":\"-99.97\",\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"1830770201040010026208\"}");
			when(rs.getString("EventServiceUpdatedBy")).thenReturn("cd_api_user");
			when(rs.getTimestamp("EventServiceUpdatedOn")).thenReturn(Timestamp.valueOf("2021-04-02 11:52:15.877"))
					.thenReturn(Timestamp.valueOf("2021-04-02 11:52:12.883"));
			when(rs.getString(Constants.WATCHLISTNAME)).thenReturn("Account Info Updated")
					.thenReturn("Cash based industry");
			when(rs.getInt(Constants.CONTACTID)).thenReturn(-1);
			when(rs.getString("Status")).thenReturn("CLEAR").thenReturn("REJECT").thenReturn("SEIZE").thenReturn("HOLD")
					.thenReturn("REVERSED");
			when(rs.getInt("Id")).thenReturn(0);
			when(rs.getString("Reason")).thenReturn("Alert from 3rd party").thenReturn("Alert from law enforcement");
			when(rs.getInt(Constants.PAYMENT_OUT_ID)).thenReturn(-1);
			when(rs.getString(Constants.BENEFICIARY_LAST_NAME)).thenReturn("Pattison");
			when(rs.getString(Constants.BENEFICIARY_FIRST_NAME)).thenReturn("Mark");
			when(rs.getString("AccountNumber")).thenReturn("0201040010026208");
			when(rs.getString(Constants.AMOUNT)).thenReturn("385.59000000");
			when(rs.getString("BuyCurrency")).thenReturn("EUR");
			when(rs.getTimestamp(Constants.DATE_OF_PAYMENT)).thenReturn(Timestamp.valueOf("2019-02-05 19:38:22.0"));
			when(rs.getString("Activity"))
					.thenReturn("After recheck Custom check status modified from fail to fail for Mark Pattison")
					.thenReturn("Amount Velocity Check Fail");
			when(rs.getString("ActivityType")).thenReturn("PAYMENT_OUT CUSTOM_CHECK_REPEAT")
					.thenReturn("PAYMENT_OUT PAYMENT_OUT_STP");
			when(rs.getString("User")).thenReturn(NAME);
			when(rs.getTimestamp("CreatedOn")).thenReturn(Timestamp.valueOf("2021-04-12 16:24:54.837"))
					.thenReturn(Timestamp.valueOf("2021-04-02 11:52:23.75"));
			when(rs.getInt(1)).thenReturn(1);
			when(rs.getBoolean("alwaysPass")).thenReturn(true);
           when(itransform.transform(anyObject())).thenReturn(getPaymentOutDetailsDto());
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	@Test
	public void testgetPaymentOutDetails() {
		try {
			setMockForPaymentOutDetails();
			PaymentOutDetailsDto expectedResult = getPaymentOutDetailsDto();
			PaymentOutDetailsDto actualResult = paymentOutDBServiceImpl.getPaymentOutDetails(183077, "PFX",
					getPaymentOutSearchCriteria());
			assertEquals(expectedResult.getAccount().getAccSFID(), actualResult.getAccount().getAccSFID());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public PaymentOutSearchCriteria setPaymentOutSearchCriteria() {
		String[] type = { "CFX" };
		PaymentOutSearchCriteria paymentOutSearchCriteria = new PaymentOutSearchCriteria();
		PaymentOutFilter filter = new PaymentOutFilter();
		filter.setKeyword("3111I119700-048");
		filter.setExcludeCustType(type);
		filter.setUserProfile(getUser());
		paymentOutSearchCriteria.setFilter(filter);
		paymentOutSearchCriteria.setIsFilterApply(false);
		paymentOutSearchCriteria.setIsLandingPage(false);
		paymentOutSearchCriteria.setIsRequestFromReportPage(false);
		paymentOutSearchCriteria.setPage(getpage());

		return paymentOutSearchCriteria;

	}
}
