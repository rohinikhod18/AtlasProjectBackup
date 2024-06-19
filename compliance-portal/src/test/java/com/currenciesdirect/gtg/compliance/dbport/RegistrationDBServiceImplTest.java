package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueFilter;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegistrationDBServiceImplTest {

	@InjectMocks
	RegistrationDBServiceImpl registrationDBServiceImpl;
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	
	@Mock
	ResultSet resultSet;
	@Mock
	ResultSet countRs;
	@Mock
	AbstractDao abstractDao;
     @Mock
	private ITransform<ActivityLogs, RegUpdateDBDto> regActivityLogTransformer;
	private static final String Name = "cd.comp.system";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			int[] count = {1};
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(preparedStatement.executeQuery()).thenReturn(resultSet);
			when(preparedStatement.getResultSet()).thenReturn(resultSet);
			when(preparedStatement.executeUpdate()).thenReturn(1);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(connection.prepareStatement(anyString(),anyInt())).thenReturn(preparedStatement);
			when(preparedStatement.executeBatch()).thenReturn(count);
			when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

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

	public RegistrationViewMoreRequest getRegistrationViewMoreRequest() {
		RegistrationViewMoreRequest registrationViewMoreRequest = new RegistrationViewMoreRequest();
		registrationViewMoreRequest.setClientType("PFX");
		registrationViewMoreRequest.setEntityId(53964);
		registrationViewMoreRequest.setEntityType("CONTACT");
		registrationViewMoreRequest.setMaxViewRecord(11);
		registrationViewMoreRequest.setMinViewRecord(2);
		registrationViewMoreRequest.setNoOfDisplayRecords(1);
		registrationViewMoreRequest.setOrganisation("Currencies Direct");
		registrationViewMoreRequest.setServiceType("SANCTION");
		return registrationViewMoreRequest;
	}

	public ViewMoreResponse getViewMoreResponse() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		Sanction sanction = new Sanction();
		sanction.setEntityType("CONTACT");
		sanction.setEventServiceLogId(1056512);
		sanction.setIsRequired(true);
		sanction.setOfacList("Safe");
		sanction.setSanctionId("002-C-0000053964");
		sanction.setStatus(true);
		sanction.setStatusValue("PASS");
		sanction.setUpdatedBy(Name);
		sanction.setUpdatedOn("06/04/2021 11:28:18");
		sanction.setWorldCheck("Safe");
		domainList.add(sanction);
		viewMoreResponse.setServices(domainList);
		return viewMoreResponse;

	}

	public void setMockForSanctionDetails() {
		try {
			when(resultSet.getString(Constants.SUMMARY)).thenReturn(
					"{\"status\":\"PASS\",\"sanctionId\":\"002-C-0000053964\",\"ofacList\":\"Safe\",\"worldCheck\":\"Safe\",\"updatedOn\":\"2021-04-06 11:28:18.982\",\"providerName\":\"FINSCAN\",\"providerMethod\":\"SLGetStatus\"}");
			when(resultSet.getString(Constants.UPDATED_BY)).thenReturn("Name");
			when(resultSet.getInt("id")).thenReturn(1056512);
			when(resultSet.getString(Constants.UPDATED_ON)).thenReturn("2021-04-06 11:28:18.983");
			when(resultSet.getInt(Constants.ENTITY_TYPE)).thenReturn(3);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetMoreSanctionDetails() {
		try {
			setMockForSanctionDetails();
			ViewMoreResponse expectedResult = getViewMoreResponse();
			ViewMoreResponse actualResult = registrationDBServiceImpl
					.getMoreSanctionDetails(getRegistrationViewMoreRequest());
			assertSame(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegistrationViewMoreRequest getRegistrationViewMoreRequestKyc() {
		RegistrationViewMoreRequest registrationViewMoreRequest = new RegistrationViewMoreRequest();
		registrationViewMoreRequest.setClientType("PFX");
		registrationViewMoreRequest.setEntityId(53964);
		registrationViewMoreRequest.setEntityType("CONTACT");
		registrationViewMoreRequest.setMaxViewRecord(11);
		registrationViewMoreRequest.setMinViewRecord(2);
		registrationViewMoreRequest.setNoOfDisplayRecords(1);
		registrationViewMoreRequest.setOrganisation("Currencies Direct");
		registrationViewMoreRequest.setServiceType("KYC");

		return registrationViewMoreRequest;
	}

	public ViewMoreResponse getViewMoreResponseKYC() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		Kyc kyc = new Kyc();
		kyc.setCheckedOn("10/02/2021 10:50:16");
		kyc.setDob("17/05/1992");
		kyc.setEidCheck(false);
		kyc.setId(1023188);
		kyc.setEntityType("CONTACt");
		kyc.setIsRequired(true);
		kyc.setReferenceId("002-C-0000053964");
		kyc.setStatus(false);
		kyc.setStatusValue("SERVICE_FAILURE");
		kyc.setVerifiactionResult("Not Available");
		domainList.add(kyc);
		viewMoreResponse.setServices(domainList);
		return viewMoreResponse;
	}

	public void setMockForKycDetails() {
		try {
			when(resultSet.getInt("id")).thenReturn(1023188);
			when(resultSet.getString(Constants.SUMMARY)).thenReturn(
					"{\"status\":\"SERVICE_FAILURE\",\"eidCheck\":false,\"verifiactionResult\":\"Not Available\",\"referenceId\":\"002-C-0000053964\",\"dob\":\"17/05/1992\",\"checkedOn\":\"2021-02-10 10:50:16.262\",\"providerName\":\"GBGROUP\",\"providerMethod\":\"authenticateSP\"}");
			when(resultSet.getInt(Constants.ENTITY_TYPE)).thenReturn(3);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testgetMoreKycDetails() {
		setMockForKycDetails();
		ViewMoreResponse expectedResult = getViewMoreResponseKYC();
		try {
			ViewMoreResponse actualResult = registrationDBServiceImpl
					.getMoreKycDetails(getRegistrationViewMoreRequestKyc());
			assertSame(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegistrationSearchCriteria getRegistrationSearchCriteria() {
		String[] type = { "CFX" };
		RegistrationSearchCriteria searchCriteria = new RegistrationSearchCriteria();
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		searchCriteria.setPage(getpage());
		RegistrationQueueFilter filter = new RegistrationQueueFilter();
		filter.setUserProfile(getUser());
		filter.setExcludeCustType(type);
		searchCriteria.setFilter(filter);
		return searchCriteria;
	}

	public RegistrationQueueDto getRegistrationQueueDto() {
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		List<RegistrationQueueData> registrationQueue = new ArrayList<>();
		RegistrationQueueData registrationQueueData = new RegistrationQueueData();
		registrationQueueData.setTradeAccountNum("0201000003403405");
		registrationQueueData.setRegisteredOn("24/03/2021 15:41:37");
		registrationQueueData.setContactName("GRIME MARTYN");
		registrationQueueData.setOrganisation("Currencies Direct");
		registrationQueueData.setType("PFX");
		registrationQueueData.setBuyCurrency("EUR");
		registrationQueueData.setSellCurrency("GBP");
		registrationQueueData.setSource("Web");
		registrationQueueData.setTransactionValue("over 250,000");
		registrationQueueData.setEidCheck("NOT_REQUIRED");
		registrationQueueData.setFraugster("SERVICEFAILURE");
		registrationQueueData.setSanction("PENDING");
		registrationQueueData.setBlacklist("PASS");
		registrationQueueData.setContactId(33948);
		registrationQueueData.setAccountId(33189);
		registrationQueueData.setCustomCheck("PASS");
		registrationQueueData.setCountryOfResidence("France");
		registrationQueueData.setNewOrUpdated("N");
		registrationQueueData.setRegisteredOn("28/03/2019 16:29:10");
		registrationQueueData.setAccountVersion(1);
		registrationQueue.add(registrationQueueData);
		registrationQueueDto.setRegistrationQueue(registrationQueue);
		List<String> currency = new ArrayList<>();
		currency.add("AED");
		List<String> legalEntity = new ArrayList<>();
		legalEntity.add("CDINC");
		List<String> onfidoStatus = new ArrayList<>();
		onfidoStatus.add("REJECT");
		List<String> organization = new ArrayList<>();
		organization.add("CDINC");
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
		registrationQueueDto.setTotalRecords(1);
		registrationQueueDto.setCurrency(currency);
		registrationQueueDto.setLegalEntity(legalEntity);
		registrationQueueDto.setOrganization(organization);
		registrationQueueDto.setOnfidoStatus(onfidoStatus);
		registrationQueueDto.setOwner(owner);
		registrationQueueDto.setSource(source);
		registrationQueueDto.setTransValue(transValue);
		registrationQueueDto.setWatchList(watchlist);
		registrationQueueDto.setSearchCriteria(
				"{\"page\":{\"minRecord\":1,\"maxRecord\":20,\"currentRecord\":null,\"totalRecords\":1542,\"currentPage\":1,\"totalPages\":78,\"pageSize\":20},\"custType\":null,\"isFilterApply\":false,\"isRequestFromReportPage\":false,\"isLandingPage\":false,\"filter\":{\"sort\":null,\"status\":null,\"keyword\":null,\"userProfile\":{\"name\":\"cd.comp.system\",\"givenName\":null,\"familyName\":null,\"middleName\":null,\"nickName\":null,\"preferredUserName\":\"Default User\",\"website\":null,\"email\":null,\"gender\":null,\"birthdate\":null,\"zoneinfo\":null,\"locale\":null,\"phoneNumber\":null,\"permissions\":{\"canWorkOnCFX\":false,\"canWorkOnPFX\":true,\"canViewRegistrationQueue\":true,\"canViewRegistrationDetails\":true,\"canViewPaymentInQueue\":true,\"canViewPaymentInDetails\":true,\"canViewPaymentOutQueue\":true,\"canViewPaymentOutDetails\":true,\"canViewRegistrationReport\":true,\"canViewPaymentInReport\":true,\"canViewPaymentOutReport\":true,\"canViewWorkEfficiacyReport\":true,\"canManageWatchListCategory1\":true,\"canManageWatchListCategory2\":true,\"canUnlockRecords\":true,\"canViewDashboard\":true,\"canManageFraud\":true,\"canManageCustom\":true,\"canManageEID\":true,\"canManageSanction\":true,\"canManageBlackList\":true,\"canDoAdministration\":true,\"isReadOnlyUser\":false,\"canManageBeneficiary\":true,\"canInitiateDataAnon\":true,\"canApproveDataAnon\":true,\"canNotLockAccount\":false},\"role\":{\"names\":[\"ATLAS_DATA_ANON_APPROVER\",\"ATLAS_DATA_ANON_INITIATOR\",\"ATLAS_DEPT_HEAD\",\"ATLAS_BENE_MGMT\"],\"functions\":[{\"name\":\"canWorkOnPFX\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewRegistrationQueue\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewPaymentInQueue\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewPaymentOutQueue\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewRegistrationReport\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewPaymentInReport\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewPaymentOutReport\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canUnlockRecords\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewDashboard\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageFraud\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageCustom\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageEID\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageSanction\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageBlackList\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canDoAdministration\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewWorkEfficiancyReport\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageWatchListCategory1\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageWatchListCategory2\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canManageBeneficiary\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canViewDataAnonQueue\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canInitiateDataAnon\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canApproveDataAnon\",\"hasAccess\":true,\"hasOverrideAccess\":false},{\"name\":\"canWorkOnCFX\",\"hasAccess\":false,\"hasOverrideAccess\":false},{\"name\":\"isReadOnlyUser\",\"hasAccess\":false,\"hasOverrideAccess\":false},{\"name\":\"canNotLockAccount\",\"hasAccess\":false,\"hasOverrideAccess\":false}]},\"id\":1},\"excludeCustType\":[\"CFX\"],\"custType\":null,\"kycStatus\":null,\"blacklistStatus\":null,\"sanctionStatus\":null,\"fraugsterStatus\":null,\"customCheckStatus\":null,\"watchListStatus\":null,\"organization\":null,\"legalEntity\":null,\"buyCurrency\":null,\"sellCurrency\":null,\"source\":null,\"transValue\":null,\"dateFrom\":null,\"dateTo\":null,\"newOrUpdatedRecord\":null,\"owner\":null,\"watchListReason\":null,\"dateFilterType\":null,\"deviceId\":null,\"onfidoStatus\":null,\"riskStatus\":null}}");
		registrationQueueDto.setPage(getpage());
		SavedSearch savedSearch = new SavedSearch();
		List<SavedSearchData> savedSearchDataList = new ArrayList<>();
		SavedSearchData savedSearchData = new SavedSearchData();
		savedSearchData.setSaveSearchFilter(
				"{\"dateFrom\":\"01/08/2019\",\"dateTo\":\"01/08/2019\",\"owner\":[\"caroline.jones\"],\"dateFilterType\":[\"Yesterday\"]}");
		savedSearchData.setSaveSearchName("sp");
		savedSearchDataList.add(savedSearchData);
		savedSearch.setSavedSearchData(savedSearchDataList);
		registrationQueueDto.setSavedSearch(savedSearch);
		return registrationQueueDto;

	}

	public void setMockForRegistrationQueueWithCriteria() {
		try {
			when(resultSet.getInt(RegQueDBColumns.CONTACTID.getName())).thenReturn(33948);
			when(resultSet.getInt(RegQueDBColumns.ACCOUNTID.getName())).thenReturn(33189);
			when(resultSet.getString(RegQueDBColumns.CONTACTNAME.getName())).thenReturn("GRIME MARTYN");
			when(resultSet.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("0201000003403405");
			when(resultSet.getTimestamp(RegQueDBColumns.REGISTEREDON.getName()))
					.thenReturn(Timestamp.valueOf("2021-03-24 15:41:37"));
			when(resultSet.getString(RegQueDBColumns.ORGANIZATION.getName())).thenReturn("Currencies Direct");
			when(resultSet.getInt(RegQueDBColumns.KYCSTATUS.getName())).thenReturn(4);
			when(resultSet.getInt(RegQueDBColumns.FRAUGSTERSTATUS.getName())).thenReturn(3);
			when(resultSet.getInt(RegQueDBColumns.SANCTIONSTATUS.getName())).thenReturn(2);
			when(resultSet.getInt(RegQueDBColumns.BLACKLISTSTATUS.getName())).thenReturn(1);
			when(resultSet.getString(RegQueDBColumns.BUYCURRENCY.getName())).thenReturn("EUR");
			when(resultSet.getString(RegQueDBColumns.SELLCURRENCY.getName())).thenReturn("GBP");
			when(resultSet.getString(RegQueDBColumns.TRANSACTIONVALUE.getName())).thenReturn("over 250,000");
			when(resultSet.getString(RegQueDBColumns.SOURCE.getName())).thenReturn("Web");
			when(resultSet.getInt(RegQueDBColumns.TYPE.getName())).thenReturn(2);
			when(resultSet.getString(RegQueDBColumns.COUNTRY_OF_RESIDENCE.getName())).thenReturn("France");
			when(resultSet.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName())).thenReturn(1);
			when(resultSet.getInt(RegQueDBColumns.CUSOMCHECKSTATUS.getName())).thenReturn(1);
			when(resultSet.getTimestamp(RegQueDBColumns.REGISTERED_DATE.getName()))
					.thenReturn(Timestamp.valueOf("2019-03-28 16:29:10"));
			when(resultSet.getInt(RegQueDBColumns.TOTALROWS.getName())).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	@Test
	public void testgetRegistrationQueueWithCriteria() {
		setMockForRegistrationQueueWithCriteria();
		RegistrationQueueDto expectedResult = getRegistrationQueueDto();
		try {
			RegistrationQueueDto actualresult = registrationDBServiceImpl
					.getRegistrationQueueWithCriteria(getRegistrationSearchCriteria());
			assertEquals(expectedResult.getRegistrationQueue().get(0).getAccountId(),
					actualresult.getRegistrationQueue().get(0).getAccountId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegistrationViewMoreRequest getRegistrationViewMoreRequestFruagster() {
		RegistrationViewMoreRequest registrationViewMoreRequest = new RegistrationViewMoreRequest();
		registrationViewMoreRequest.setClientType("PFX");
		registrationViewMoreRequest.setEntityId(53964);
		registrationViewMoreRequest.setEntityType("CONTACT");
		registrationViewMoreRequest.setMaxViewRecord(11);
		registrationViewMoreRequest.setMinViewRecord(2);
		registrationViewMoreRequest.setNoOfDisplayRecords(1);
		registrationViewMoreRequest.setOrganisation("Currencies Direct");
		registrationViewMoreRequest.setServiceType("FRAUGSTER");
		return registrationViewMoreRequest;
	}

	public void setMockForFraugsterDetails() {
		try {
			when(resultSet.getString(Constants.SUMMARY)).thenReturn(
					"{\"status\":\"PASS\",\"frgTransId\":\"fac40e7f-89ed-4e7f-88ca-bea2d78a01c6\",\"score\":\"-97.41\",\"fraugsterApproved\":\"1.0\",\"cdTrasId\":\"00000539641\"}");
			when(resultSet.getString(Constants.UPDATED_ON)).thenReturn("2021-02-10 10:50:17.36");
			when(resultSet.getString(Constants.UPDATED_BY)).thenReturn("cd_api_user");
			when(resultSet.getInt("id")).thenReturn(1023187);
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public ViewMoreResponse getViewMoreResponseFruagster() {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		List<IDomain> domainList = new ArrayList<>();
		Fraugster fraugster = new Fraugster();
		fraugster.setCreatedOn("10/02/2021 10:50:17");
		fraugster.setFraugsterId("fac40e7f-89ed-4e7f-88ca-bea2d78a01c6");
		fraugster.setScore("-97.41");
		fraugster.setStatus(true);
		fraugster.setUpdatedBy("cd_api_user");
		domainList.add(fraugster);
		viewMoreResponse.setServices(domainList);
		return viewMoreResponse;
	}

	@Test
	public void testgetMoreFraugsterDetails() {
		setMockForFraugsterDetails();
		ViewMoreResponse expectedResult = getViewMoreResponseFruagster();
		try {
			ViewMoreResponse actualResult = registrationDBServiceImpl
					.getMoreFraugsterDetails(getRegistrationViewMoreRequestFruagster());
			assertSame(expectedResult.getClass(), actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public ActivityLogRequest getActivityLogRequest() {
		ActivityLogRequest activityLogRequest = new ActivityLogRequest();
		activityLogRequest.setAccountId(56116);
		activityLogRequest.setCustType("PFX");
		activityLogRequest.setEntityId(57022);
		activityLogRequest.setMaxRecord(20);
		activityLogRequest.setMinRecord(11);
		activityLogRequest.setUser(getUser());
		return activityLogRequest;
	}

	public ActivityLogs getActivityLogs() {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> actvtyLogDataList = new ArrayList<>();
		ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
		activityLogData.setActivity(
				"After recheck FraudPredict status modified from PASS to SERVICE_FAILURE for Micheal jone Norse");
		activityLogData.setActivityType("Profile fraudpredict repeat");
		activityLogData.setCreatedOn("12/04/2021 10:08:21");
		activityLogData.setCreatedBy("cd.comp.system");
		actvtyLogDataList.add(activityLogData);
		activityLogs.setActivityLogData(actvtyLogDataList);
		return activityLogs;
	}

	public void setMockForActivityLog() {
		try {
			when(resultSet.getString("Activity")).thenReturn(
					"After recheck FraudPredict status modified from PASS to SERVICE_FAILURE for Micheal jone Norse");
			when(resultSet.getString("ActivityType")).thenReturn("Profile fraudpredict repeat");
			when(resultSet.getString("User")).thenReturn("cd.comp.system");
			when(resultSet.getTimestamp("CreatedOn")).thenReturn(Timestamp.valueOf("2021-04-12 10:08:21"));
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testgetActivityLogs() {
		setMockForActivityLog();
		ActivityLogs expectedResult = getActivityLogs();
		try {
			ActivityLogs actualResult = registrationDBServiceImpl.getActivityLogs(getActivityLogRequest());
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public RegUpdateDBDto getRegUpdateDBDto() {
		RegUpdateDBDto regUpdateDBDto = new RegUpdateDBDto();
		regUpdateDBDto.setAccountId(56116);
		regUpdateDBDto.setAccountSfId("0035E000102efr9DKN");
		regUpdateDBDto.setComment("test");
		regUpdateDBDto.setComplianceDoneOn("2021-04-12 10:09:40.0");
		regUpdateDBDto.setComplianceExpiry("2024-04-12 10:09:40.0");
		regUpdateDBDto.setContactId(57022);
		regUpdateDBDto.setContactSfId("0050E000110yC9IDRM");
		regUpdateDBDto.setContactStatus("INACTIVE");
		regUpdateDBDto.setCreatedBy("cd.comp.system");
		regUpdateDBDto.setCustType("PFX");
		regUpdateDBDto.setFragusterEventServiceLogId(1056579);
		regUpdateDBDto.setIsAccountOnQueue(false);
		regUpdateDBDto.setIsContactOnQueue(false);
		regUpdateDBDto.setIsRequestFromQueue(true);
		regUpdateDBDto.setOrgCode("Currencies Direct");
		regUpdateDBDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.FAIL);
		regUpdateDBDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.FAIL);
		regUpdateDBDto.setRegistrationInDate("2021-04-08 15:03:35.51");
		regUpdateDBDto.setUserResourceId(696831);
		List<ProfileActivityLogDto> activityLog = new ArrayList<>();
		ProfileActivityLogDto profileActivityLogDto = new ProfileActivityLogDto();
		profileActivityLogDto.setAccountId(56116);
		profileActivityLogDto.setActivityBy(Name);
		ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
		activityLogDetailDto.setActivityType(ActivityType.PROFILE_CONTACT_STATUS_UPDATE);
		activityLogDetailDto.setCreatedBy(Name);
		activityLogDetailDto.setCreatedOn(Timestamp.valueOf("2021-04-12 10:42:26.58"));
		activityLogDetailDto.setLog("Contact status modified from ACTIVE to INACTIVE");
		activityLogDetailDto.setUpdatedBy(Name);
		activityLogDetailDto.setUpdatedOn(Timestamp.valueOf("2021-04-12 10:42:26.58"));
		profileActivityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		profileActivityLogDto.setComment("test");
		profileActivityLogDto.setTimeStatmp(Timestamp.valueOf("2021-04-12 10:42:26.58"));
		profileActivityLogDto.setOrgCode("Currencies Direct");
		profileActivityLogDto.setUpdatedBy(Name);
		profileActivityLogDto.setUpdatedOn(Timestamp.valueOf("2021-04-12 10:42:26.58"));
		activityLog.add(profileActivityLogDto);
		regUpdateDBDto.setActivityLog(activityLog);
		return regUpdateDBDto;
	}

	public ActivityLogs getActivityLogsforUpdateContact() {
		ActivityLogs activityLogs = new ActivityLogs();
		activityLogs.setComplianceDoneOn("2021-04-12 10:09:40.0");
		activityLogs.setComplianceExpiry("2021-04-12 10:09:40.0");
		activityLogs.setRegistrationInDate("2021-04-08 15:03:35.51");
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivity("Contact status modified from ACTIVE to INACTIVE");
		activityLogDataWrapper.setActivityType("PROFILE CONTACT_STATUS_UPDATE");
		activityLogDataWrapper.setCreatedBy(Name);
		activityLogDataWrapper.setCreatedOn("12/04/2021 10:42:26");
		activityLogData.add(activityLogDataWrapper);
		activityLogs.setActivityLogData(activityLogData);

		return activityLogs;

	}

	public void setMockForupdateContact() {
		try {
			Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
			when(resultSet.getInt("TradeAccountID")).thenReturn(411364);
			when(resultSet.getInt("TradeContactId")).thenReturn(7896013);
			when(resultSet.getInt(1)).thenReturn(834576);
			when(resultSet.getString(Constants.ATTRIBUTES)).thenReturn("{\"onQueue\":false,\"acc_sf_id\":\"0035E000102efr9DKN\",\"trade_acc_id\":411364,\"trade_acc_number\":\"0201040010026216\",\"branch\":\"Moorgate HO\",\"channel\":\"Mobile\",\"cust_type\":\"PFX\",\"act_name\":\"Micheal jone Norse\",\"country_interest\":\"\",\"trade_name\":\"\",\"trasaction_purpose\":\"\",\"op_country\":\"\",\"turnover\":\"\",\"service_req\":\"Transfer\",\"buying_currency\":\"EUR\",\"selling_currency\":\"GBP\",\"txn_value\":\"2,000 - 5,000\",\"source_of_fund\":\"\",\"source\":\"Web\",\"sub_source\":\"Natural\",\"referral\":\"\",\"referral_text\":\"Natural\",\"extended_referral\":\"\",\"ad_campaign\":\"\",\"keywords\":\"\",\"search_engine\":\"\",\"website\":\"\",\"affiliate_name\":\"STP - temporary affiliate code\",\"reg_mode\":\"Mobile\",\"organization_legal_entity\":\"CDLGB\",\"version\":1,\"reg_date_time\":\"2019-05-23T11:38:01Z\",\"company\":{\"billing_address\":\"A, Dalemoor,Keighley,BD22 8EZ,UK\",\"company_phone\":\"\",\"shipping_address\":\"\",\"vat_no\":\"\",\"country_region\":\"\",\"country_of_establishment\":\"\",\"corporate_type\":\"\",\"registration_no\":\"\",\"incorporation_date\":\"\",\"industry\":\"\",\"etailer\":\"false\",\"option\":\"false\",\"type_of_financial_account\":\"\",\"ccj\":\"false\",\"ongoing_due_diligence_date\":\"\",\"est_no_transactions_pcm\":\"\"},\"corporate_compliance\":{\"sic\":\"\",\"former_name\":\"\",\"legal_form\":\"\",\"foreign_owned_company\":\"\",\"net_worth\":\"\",\"fixed_assets\":\"\",\"total_liabilities_and_equities\":\"\",\"total_share_holders\":\"\",\"global_ultimate_DUNS\":\"\",\"global_ultimate_name\":\"\",\"global_ultimate_country\":\"\",\"registration_date\":\"\",\"match_name\":\"\",\"iso_country_code_2_digit\":\"\",\"iso_country_code_3_digit\":\"\",\"statement_date\":\"\",\"gross_income\":\"\",\"net_income\":\"\",\"total_current_assets\":\"\",\"total_assets\":\"\",\"total_long_term_liabilities\":\"\",\"total_current_liabilities\":\"\",\"total_matched_shareholders\":\"\",\"financial_strength\":\"\"},\"risk_profile\":{\"country_risk_indicator\":\"\",\"risk_trend\":\"\",\"risk_direction\":\"\",\"updated_risk\":\"\",\"failure_score\":\"\",\"delinquency_failure_score\":\"\",\"continent\":\"\",\"country\":\"\",\"state_country\":\"\",\"duns_number\":\"\",\"trading_styles\":\"\",\"us1987_primary_SIC_4_digit\":\"\",\"financial_figures_month\":\"\",\"financial_figures_year\":\"\",\"financial_year_end_date\":\"\",\"annual_sales\":\"\",\"modelled_annual_sales\":\"\",\"net_worth_amount\":\"\",\"modelled_net_worth\":\"\",\"location_type\":\"\",\"import_export_indicator\":\"\",\"domestic_ultimate_record\":\"\",\"global_ultimate_record\":\"\",\"group_structure_number_of_levels\":\"\",\"headquarter_details\":\"\",\"immediate_parent_details\":\"\",\"domestic_ultimate_parent_details\":\"\",\"global_ultimate_parent_details\":\"\",\"credit_limit\":\"\",\"risk_rating\":\"\",\"profit_loss\":\"\"},\"conversionPrediction\":{\"AccountId\":\"1120O014274TF7CQAW\",\"ETVBand\":\"2k – 10k\",\"conversionFlag\":\"High\",\"conversionProbability\":0.82625836},\"affiliate_number\":\"A000000\"}");
		when(regActivityLogTransformer.transform(anyObject())).thenReturn(getActivityLogsforUpdateContact());
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testupdateContact() {
		setMockForupdateContact();
		ActivityLogs expectedResult = getActivityLogsforUpdateContact();
		try {
			ActivityLogs actualResult = registrationDBServiceImpl.updateContact(getRegUpdateDBDto());
			assertEquals(expectedResult.getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

}
