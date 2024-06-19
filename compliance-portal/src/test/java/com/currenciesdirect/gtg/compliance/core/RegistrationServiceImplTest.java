package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueFilter;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegistrationServiceImplTest {

	@InjectMocks
	RegistrationServiceImpl registrationServiceImpl;

	@Mock
	IRegistrationDBService iRegistrationQueueDBService;

	@Mock
	RegistrationDetailsFactory registrationDetailsFactory;

	@Mock
	ITransform<LockResourceDBDto, LockResourceRequest> lockResourceTransformer;

	@Mock
	ITransform<RegUpdateDBDto, RegistrationUpdateRequest> regUpdateTransformer;

	@Mock
	ICfxDBService iCfxDBService;

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

	public RegistrationQueueDto setRegistrationQueueData() {

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
		registrationQueue.setTradeAccountNum("6195248065345382");
		registrationQueue.setRegisteredOn("17/04/2020 11:08:14");
		registrationQueue.setContactName("Saad Rashed Mohammad AL-FAQIH");
		registrationQueue.setOrganisation("Currencies Direct");
		registrationQueue.setType("PFX");
		registrationQueue.setBuyCurrency("EUR");
		registrationQueue.setSellCurrency("GBP");
		registrationQueue.setSource("Web");
		registrationQueue.setTransactionValue("2,000 - 5,000");
		registrationQueue.setEidCheck("FAIL");
		registrationQueue.setAccountId(49715);
		data.add(registrationQueue);
		return data;
	}

	public SavedSearch getsavedSearch() {
		SavedSearchData savedSearchData = new SavedSearchData();
		savedSearchData.setSaveSearchFilter("\"blacklistStatus\":[\"FAIL\"]");
		savedSearchData.setSaveSearchName("abc");
		return null;
	}

	public void setMockDataForGetRegistrationQueue() {
		try {
			when(iRegistrationQueueDBService.getRegistrationQueueWithCriteria(anyObject()))
					.thenReturn(setRegistrationQueueData());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetRegistrationQueue() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iRegistrationQueueDBService.getRegistrationQueueWithCriteria(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegistrationSearchCriteria setSearchCriteria() {
		RegistrationSearchCriteria registrationSearchCriteria = new RegistrationSearchCriteria();
		RegistrationQueueFilter filter = new RegistrationQueueFilter();
		filter.setKeyword("6195248065345382");
		registrationSearchCriteria.setFilter(filter);
		registrationSearchCriteria.setIsFilterApply(true);
		registrationSearchCriteria.setIsLandingPage(false);
		registrationSearchCriteria.setIsRequestFromReportPage(false);
		registrationSearchCriteria.setPage(getpage());
		return registrationSearchCriteria;
	}

	public LockResourceRequest setLockResourceRequest() {
		LockResourceRequest lockResourceRequest = new LockResourceRequest();
		lockResourceRequest.setLock(true);
		lockResourceRequest.setResourceId(50705);
		lockResourceRequest.setResourceType("CONTACT");
		lockResourceRequest.setUser(getUser());
		return lockResourceRequest;
	}

	public LockResourceResponse setLockResourceResponse() {
		LockResourceResponse lockResourceResponse = new LockResourceResponse();
		lockResourceResponse.setName(NAME);
		lockResourceResponse.setResourceId(50705);
		lockResourceResponse.setUserResourceId(666537);
		lockResourceResponse.setStatus("Pass");
		return lockResourceResponse;

	}

	public LockResourceDBDto getLockResourceDBDto() {
		LockResourceDBDto lockResourceDBDto = new LockResourceDBDto();
		lockResourceDBDto.setId(666537);
		lockResourceDBDto.setUserId(getUser().getName());
		lockResourceDBDto.setResourceId(50705);
		lockResourceDBDto.setResourceType("CONTACT");
		lockResourceDBDto.setCreatedBy(getUser().getName());
		lockResourceDBDto.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		return lockResourceDBDto;
	}

	public void setMockForLockResource() {
		try {
			when(iRegistrationQueueDBService.insertLockResource(anyObject())).thenReturn(setLockResourceResponse());
			when(lockResourceTransformer.transform(anyObject())).thenReturn(getLockResourceDBDto());
			when(iCfxDBService.insertLockResourceForMultiContact(anyObject())).thenReturn(setLockResourceResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForLockResource() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA);
			when(iRegistrationQueueDBService.insertLockResource(anyObject())).thenThrow(e);
			when(lockResourceTransformer.transform(anyObject())).thenReturn(getLockResourceDBDto());
			when(iCfxDBService.insertLockResourceForMultiContact(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public DeviceInfoResponse setGetDeviceInfo() {
		DeviceInfoResponse deviceInfoResponse = new DeviceInfoResponse();
		deviceInfoResponse.setCreatedOn("2020-04-30 15:15:38.843");
		deviceInfoResponse.setEventType("PROFILE_NEW_REGISTRATION");
		return deviceInfoResponse;
	}

	public void setMockDataForGetDeviceInfo() {
		try {
			when(iRegistrationQueueDBService.getDeviceInfo(anyObject())).thenReturn(setGetDeviceInfo());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetDeviceInfo() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA);
			when(iRegistrationQueueDBService.getDeviceInfo(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public ProviderResponseLogResponse setGetProviderResponse() {
		String responseJson = "{\"frgTransId\":\"242498ec-5b0c-4279-a958-58e82a85f52c\",\"score\":"
				+ "\"0.00086633547\",\"fraugsterApproved\":\"1.0\",\"id\":50567,\"status\":\"PASS\","
				+ "\"percentageFromThreshold\":\"-88.6\",\"decisionDrivers\":{\"txn_value\":"
				+ "{\"value\":\"2,000 - 5,000\",\"featureImportance\":-0.066787176},\"source\""
				+ ":{\"value\":\"WEB\",\"featureImportance\":0.6384221},\"reg_mode\":{\"value\":"
				+ "\"MOBILE\",\"featureImportance\":-0.029656295},\"affiliate_name\":{\"value\":"
				+ "\"MARKETING USA\",\"featureImportance\":-0.6497799},\"ad_campaign\":{\"value\":"
				+ "\"\",\"featureImportance\":-0.044710137},\"channel\":{\"value\":\"ANDROIDAPP\","
				+ "\"featureImportance\":-0.22755389},\"ip_line_speed\":{\"value\":\"\","
				+ "\"featureImportance\":0.09858213},\"search_engine\":{\"value\":\"\","
				+ "\"featureImportance\":-0.06874219},\"gender_fullcontact\":{\"value\":\"MALE\","
				+ "\"featureImportance\":-0.08977834},\"browser_lang\":{\"value\":\"\","
				+ "\"featureImportance\":-0.073005},\"screen_resolution\":{\"value\":\"750X1334\","
				+ "\"featureImportance\":-0.02764587},\"country_of_residence\":{\"value\":\"NOR\","
				+ "\"featureImportance\":-0.32634246},\"fullcontact_matched\":{\"value\":\"YES\","
				+ "\"featureImportance\":-0.099806294},\"ip_longitude\":{\"value\":\"\",\"featureImportance\":-1.3007079},"
				+ "\"keywords\":{\"value\":\"\",\"featureImportance\":-0.019817943},\"ip_connection_type\""
				+ ":{\"value\":\"\",\"featureImportance\":-0.20402662},\"device_type\":{\"value\":"
				+ "\"IPHONE\",\"featureImportance\":-0.003853333},\"op_country\":{\"value\":\"\","
				+ "\"featureImportance\":0.0},\"residential_status\":{\"value\":\"\",\"featureImportance"
				+ "\":0.00016981395},\"address_type\":{\"value\":\"CURRENT ADDRESS\",\"featureImportance"
				+ "\":0.0},\"device_manufacturer\":{\"value\":\"APPLE\",\"featureImportance\":-0.011105637},"
				+ "\"ip_carrier\":{\"value\":\"\",\"featureImportance\":-0.13480407},\"device_name\":{"
				+ "\"value\":\"BNT SOFT IPHONE\",\"featureImportance\":-0.021149453},\"ip_anonymizer_status\":{"
				+ "\"value\":\"\",\"featureImportance\":-0.039138474},\"ip_routing_type\":{"
				+ "\"value\":\"\",\"featureImportance\":-0.040089235},"
				+ "\"eid_status\":{\"value\":\"1\",\"featureImportance\":-0.10561975},"
				+ "\"age_range_fullcontact\":{\"value\":\"NONE\",\"featureImportance\":-0.10939652},"
				+ "\"location_country_fullcontact\":{\"value\":\"NONE\",\"featureImportance\":-0.060013425},"
				+ "\"ip_latitude\":{\"value\":\"\",\"featureImportance\":-0.7728313},"
				+ "\"browser_type\":{\"value\":\"\",\"featureImportance\":-0.030349376},"
				+ "\"title\":{\"value\":\"\",\"featureImportance\":-0.032831136},\"referral_text\":{"
				+ "\"value\":\"NATURAL\",\"featureImportance\":-0.01893612},\"browser_online\":{"
				+ "\"value\":\"\",\"featureImportance\":0.004631058},\"device_os_type\":{\"value\":"
				+ "\"APPLE IOS\",\"featureImportance\":-0.07307137},\"sub_source\":{"
				+ "\"value\":\"NATURAL\",\"featureImportance\":-0.23653843},\"browser_version\":{"
				+ "\"value\":\"\",\"featureImportance\":-0.016109085},\"email_domain\":{\"value\":"
				+ "\"GMAIL.CA\",\"featureImportance\":-0.65024966},\"branch\":{\"value\":\"USA\","
				+ "\"featureImportance\":-0.034485456},\"sanction_status\":{\"value\":\"1\","
				+ "\"featureImportance\":-0.073463775},\"turnover\":{\"featureImportance\":-0.18324597},"
				+ "\"region_suburb\":{\"value\":\"\",\"featureImportance\":-0.38938737},"
				+ "\"social_profiles_count\":{\"value\":\"1.0\",\"featureImportance\":-0.2013586},"
				+ "\"state\":{\"value\":\"TORONTO\",\"featureImportance\":-0.8684819}}}";
		ProviderResponseLogResponse providerResponseLogResponse = new ProviderResponseLogResponse();
		providerResponseLogResponse.setResponseJson(responseJson);
		return providerResponseLogResponse;
	}

	public void setMockDatForGetProviderResponse() {
		try {
			when(iRegistrationQueueDBService.getProviderResponse(anyObject())).thenReturn(setGetProviderResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetProviderResponse() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_GETTING_PROVIDER_RESPONSE);
			when(iRegistrationQueueDBService.getProviderResponse(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetRegistrationQueueWithCriteria() {
		try {
			setMockDataForGetRegistrationQueue();
			RegistrationQueueDto expectedResult = setRegistrationQueueData();
			RegistrationQueueDto actualResult = registrationServiceImpl
					.getRegistrationQueueWithCriteria(setSearchCriteria());
			assertEquals(expectedResult.getCountry(), actualResult.getCountry());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetRegistrationQueueWithCriteria() {
		try {
			setMockForGetRegistrationQueue();
			registrationServiceImpl.getRegistrationQueueWithCriteria(setSearchCriteria());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testLockResource() {
		try {
			setMockForLockResource();
			LockResourceResponse expectedResult = setLockResourceResponse();
			LockResourceResponse actualResult = registrationServiceImpl.lockResource(setLockResourceRequest());
			assertEquals(expectedResult.getLock(), actualResult.getLock());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForLockResource() {
		try {
			setMockDataForLockResource();
			registrationServiceImpl.lockResource(setLockResourceRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetDeviceInfo() {
		DeviceInfoRequest deviceInfoRequest = new DeviceInfoRequest();
		deviceInfoRequest.setAccountId(49727);
		setMockDataForGetDeviceInfo();
		DeviceInfoResponse expectedResult = setGetDeviceInfo();
		DeviceInfoResponse actualResult;
		try {
			actualResult = registrationServiceImpl.getDeviceInfo(deviceInfoRequest);
			assertEquals(expectedResult.getCreatedOn(), actualResult.getCreatedOn());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetDeviceInfo() {
		DeviceInfoRequest deviceInfoRequest = new DeviceInfoRequest();
		deviceInfoRequest.setAccountId(49727);
		setMockForGetDeviceInfo();

		try {
			registrationServiceImpl.getDeviceInfo(deviceInfoRequest);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA.getErrorDescription(),
					e.getDescription());
		}
	}

	@Test
	public void testGetProviderResponse() {
		ProviderResponseLogRequest providerResponseLogRequest = new ProviderResponseLogRequest();
		providerResponseLogRequest.setEventServiceLogId(966610);
		providerResponseLogRequest.setServiceType("FRAUGSTER");
		setMockDatForGetProviderResponse();
		ProviderResponseLogResponse expectedResult = setGetProviderResponse();
		try {
			ProviderResponseLogResponse actualResult = registrationServiceImpl
					.getProviderResponse(providerResponseLogRequest);
			assertEquals(expectedResult.getResponseJson(), actualResult.getResponseJson());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetProviderResponse() {
		ProviderResponseLogRequest providerResponseLogRequest = new ProviderResponseLogRequest();
		providerResponseLogRequest.setEventServiceLogId(966610);
		providerResponseLogRequest.setServiceType("FRAUGSTER");
		setMockForGetProviderResponse();
		try {
			registrationServiceImpl.getProviderResponse(providerResponseLogRequest);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_GETTING_PROVIDER_RESPONSE.getErrorDescription(),
					e.getDescription());
		}
	}

	@Test
	public void testLockResourceMultiContacts() {
		try {
			setMockForLockResource();
			LockResourceResponse expectedResult = setLockResourceResponse();
			LockResourceResponse actualResult = registrationServiceImpl
					.lockResourceMultiContacts(setLockResourceRequest());
			assertEquals(expectedResult.getLock(), actualResult.getLock());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForLockResourceMultiContacts() {
		try {
			setMockDataForLockResource();
			registrationServiceImpl.lockResourceMultiContacts(setLockResourceRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorDescription(), e.getDescription());
		}
	}
}
