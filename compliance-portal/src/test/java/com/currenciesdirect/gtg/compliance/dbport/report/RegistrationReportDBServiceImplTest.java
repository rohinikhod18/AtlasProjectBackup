package com.currenciesdirect.gtg.compliance.dbport.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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

import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;

import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegistrationReportDBServiceImplTest {
	
	@InjectMocks
	RegistrationReportDBServiceImpl registrationReportDBServiceImpl;
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;
	@Mock
	ResultSet countRs;
	@Mock
	AbstractDao abstractDao;
	
	
	private static final String Name = "cd.comp.system";

	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true); 
		} catch (SQLException e)
		{
			System.out.println(e);
		}
}
	
	public void setPreparedStmt()
	{
		try {
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
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
	public Page getpage()
	{
		Page page= new Page();
		page.setCurrentPage(1);
		page.setMaxRecord(40);
		page.setMinRecord(1);
		page.setPageSize(40);
		page.setTotalPages(1);
		page.setTotalRecords(1);
		return page;
		
	}
	public RegistrationReportSearchCriteria getRegistrationReportSearchCriteria()
	{
		RegistrationReportSearchCriteria searchCriteria=new RegistrationReportSearchCriteria();
		RegistrationReportFilter filter= new RegistrationReportFilter();
		filter.setKeyword("0201000010026273");
		 filter.setUserProfile(getUser());
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(true);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
	    searchCriteria.setPage(getpage());
		return searchCriteria;
	}
	

	public void setMock()
	{
		
			try {
				when(rs.getTimestamp(RegQueDBColumns.REGISTEREDON.getName())).thenReturn(Timestamp.valueOf("2020-08-21 16:26:33"));
				when(rs.getInt(RegQueDBColumns.CONTACTID.getName())).thenReturn(50680);
				when(rs.getInt(RegQueDBColumns.ACCOUNTID.getName())).thenReturn(49806);
				when(rs.getString(RegQueDBColumns.CONTACTNAME.getName())).thenReturn("Robert Gill");
				when(rs.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("0201000010026273");
				when(rs.getString(RegQueDBColumns.ORGANIZATION.getName())).thenReturn("Currencies Direct");
				when(rs.getString(RegQueDBColumns.LEGALENTITY.getName())).thenReturn("TORGB");
				when(rs.getInt(RegQueDBColumns.KYCSTATUS.getName())).thenReturn(2);
				when(rs.getInt(RegQueDBColumns.FRAUGSTERSTATUS.getName())).thenReturn(7);
				when(rs.getInt(RegQueDBColumns.SANCTIONSTATUS.getName())).thenReturn(1);
				when(rs.getInt(RegQueDBColumns.BLACKLISTSTATUS.getName())).thenReturn(1);
				when(rs.getInt(RegQueDBColumns.CUSOMCHECKSTATUS.getName())).thenReturn(1);
				when(rs.getString(RegQueDBColumns.BUYCURRENCY.getName())).thenReturn("EUR");
				when(rs.getString(RegQueDBColumns.SELLCURRENCY.getName())).thenReturn("GBP");
				when(rs.getString(RegQueDBColumns.TRANSACTIONVALUE.getName())).thenReturn("10,000 - 25,000");
				when(rs.getString(RegQueDBColumns.SOURCE.getName())).thenReturn("Web");
				when(rs.getInt(RegQueDBColumns.TYPE.getName())).thenReturn(2);
				when(rs.getString(RegQueDBColumns.COUNTRY_OF_RESIDENCE.getName())).thenReturn("United Kingdom");
				when(rs.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName())).thenReturn(1);
				when(rs.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName())).thenReturn(1);
				when(rs.getString(RegQueDBColumns.REASON.getName())).thenReturn("abc");
				when(rs.getInt(RegQueDBColumns.WATCHLISTID.getName())).thenReturn(1);
				when(rs.getString(RegQueDBColumns.OWNER.getName())).thenReturn(Name);
				when(rs.getString(RegQueDBColumns.CODE.getName())).thenReturn(" CDINC");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
	}
	public RegistrationQueueDto setRegistrationQueueData() {

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
		registrationQueue.setTradeAccountNum("0201000010026273");
		registrationQueue.setRegisteredOn("21/08/2020 16:26:33");
		registrationQueue.setContactName("Robert Gill");
		registrationQueue.setOrganisation("Currencies Direct");
		registrationQueue.setType("PFX");
		registrationQueue.setBuyCurrency("EUR");
		registrationQueue.setSellCurrency("GBP");
		registrationQueue.setSource("Web");
		registrationQueue.setTransactionValue("10,000 - 25,000");
		registrationQueue.setEidCheck("FAIL");
		registrationQueue.setFraugster("WATCH_LIST");
		registrationQueue.setBlacklist("PASS");
		registrationQueue.setCountryOfResidence("United Kingdom");
		registrationQueue.setComplianceStatus("INACTIVE");
		registrationQueue.setAccountVersion(1);
		registrationQueue.setSanction("PASS");
		registrationQueue.setCustomCheck("PASS");
		registrationQueue.setAccountId(49806);
		registrationQueue.setContactId(50680);
		data.add(registrationQueue);
		return data;
	}

	public  RegistrationQueueDto getRegistrationQueueDto()
	{
		RegistrationQueueDto registrationQueueDto=new RegistrationQueueDto();
		registrationQueueDto.setRegistrationQueue(getRegistrationQueue());
		return registrationQueueDto;
		}

	@Test
	public void testGetRegistrationQueueWithCriteria()
	{
		setMock();
		setPreparedStmt();
		RegistrationQueueDto expectedResult=setRegistrationQueueData();
		RegistrationReportSearchCriteria searchCriteria=getRegistrationReportSearchCriteria();
		try {
			RegistrationQueueDto actualresult=registrationReportDBServiceImpl.getRegistrationQueueWithCriteria(searchCriteria);
 		assertEquals(expectedResult.getRegistrationQueue().get(0).getAccountId(),actualresult.getRegistrationQueue().get(0).getAccountId());   
		
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}  
	}
	
	@Test
	public void testGetRegistrationQueueWholeData()
	{
	setMock();
	setPreparedStmt();
	RegistrationQueueDto expectedResult=getRegistrationQueueDto();
	RegistrationReportSearchCriteria searchCriteria=getRegistrationReportSearchCriteria();
	try {
		RegistrationQueueDto actualresult=registrationReportDBServiceImpl.getRegistrationQueueWholeData(searchCriteria);
		assertEquals(expectedResult.getRegistrationQueue().get(0).getAccountId(),actualresult.getRegistrationQueue().get(0).getAccountId());   
		
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}  
}
	
	
	@Test
	public void testForGetRegistrationQueueWithCriteria()
	{
		setMock();
		RegistrationReportSearchCriteria searchCriteria=getRegistrationReportSearchCriteria();
		try {
		registrationReportDBServiceImpl.getRegistrationQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}  
	}
	
	@Test
	public void testForGetRegistrationQueueWholeData()
	{
	setMock();
	RegistrationReportSearchCriteria searchCriteria=getRegistrationReportSearchCriteria();
	try {
		registrationReportDBServiceImpl.getRegistrationQueueWholeData(searchCriteria);
	} catch (CompliancePortalException e) {
		 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
	}  
}
}