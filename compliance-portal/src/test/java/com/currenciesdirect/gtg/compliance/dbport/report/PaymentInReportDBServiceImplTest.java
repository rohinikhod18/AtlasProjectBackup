package com.currenciesdirect.gtg.compliance.dbport.report;

import static org.junit.Assert.assertEquals;
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

import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.dbport.PayInQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentInReportDBServiceImplTest {
	@InjectMocks
	PaymentInReportDBServiceImpl paymentInReportDBServiceImpl;
	
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;
	@Mock
	ResultSet resultSet;
	@Mock
	AbstractDao abstractDao;
	
	
	String keyword="0202088009936016-000000066";
	private static final String Name ="cd.comp.system";
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false); 
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
	
	
	public PaymentInReportSearchCriteria setFilter()
	 {
		 PaymentInReportSearchCriteria searchCriteria = new PaymentInReportSearchCriteria();
			PaymentInReportFilter filter = new PaymentInReportFilter();
			filter.setUserProfile(getUser());
			searchCriteria.setFilter(filter);
			searchCriteria.setIsLandingPage(true);
			return searchCriteria;
	 }

	public List<PaymentInQueueData> PaymentInQueue()
	 {
		 List<PaymentInQueueData> data=  new ArrayList<>();
		 PaymentInQueueData paymentInQueueData= new PaymentInQueueData();
		 
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
	 
	 public PaymentInQueueDto setPaymentInQueueDto()
	 {
		 PaymentInQueueDto paymentInQueueDto= new PaymentInQueueDto();
		 
		 paymentInQueueDto.setPaymentInQueue(PaymentInQueue());
		return paymentInQueueDto;
		 
	 }
	 public PaymentInQueueDto getPaymentInQueueDto()
		{
			
			List<String> country= new ArrayList<>();
			 country.add("Canada");
			 List<String> currency= new ArrayList<>();
				currency.add("AED");
				List<String> legalEntity= new ArrayList<>();
				legalEntity.add("CDINC");
				List<String> onfidoStatus= new ArrayList<>();
				onfidoStatus.add("REJECT");
				List<String> organization= new ArrayList<>();
				organization.add("CDINC");
				List<String> owner= new ArrayList<>();
				owner.add(Name);
				List<String> source= new ArrayList<>();
				source.add("web");
				List<String> transValue= new ArrayList<>();
				transValue.add("Under 2,000");
				WatchListData watchlistData= new WatchListData();
				watchlistData.setId(1);
				watchlistData.setName("abc");
				List<WatchListData> watchlistDatas= new ArrayList<>();
				watchlistDatas.add(watchlistData);
					Watchlist watchlist= new Watchlist();
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

	 public void setMock()
		{
			
			try {
				when(rs.getString(PayInQueDBColumns.TRANSACTIONID.getName())).thenReturn("0201000010009491-003796441");
				when(rs.getString(PayInQueDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("0202088009936016");
				when(rs.getInt(PayInQueDBColumns.ACCOUNTID.getName())).thenReturn(49767);
				when(rs.getInt(PayInQueDBColumns.CONTACTID.getName())).thenReturn(50630);
				when(rs.getTimestamp(PayInQueDBColumns.DATE.getName())).thenReturn(Timestamp.valueOf("2020-11-01 13:05:06"));
				when(rs.getString(PayInQueDBColumns.CLIENTNAME.getName())).thenReturn("John2 2-add Cena");
				when(rs.getString(PayInQueDBColumns.TYPE.getName())).thenReturn("PFX");
				when(rs.getString(PayInQueDBColumns.SELLCURRENCY.getName())).thenReturn("EUR");
				when(rs.getInt(PayInQueDBColumns.AMOUNT.getName())).thenReturn(700);
				when(rs.getString(PayInQueDBColumns.METHOD.getName())).thenReturn("France");
				when(rs.getString(PayInQueDBColumns.COUNTRY.getName())).thenReturn("CAN");
				when(rs.getString(PayInQueDBColumns.COUNTRY_DISPLAY_NAME.getName())).thenReturn("Canada");
				when(rs.getString(PayInQueDBColumns.OVERALLSTATUS.getName())).thenReturn("HOLD");
				when(rs.getInt(PayInQueDBColumns.WATCHLISTSTATUS.getName())).thenReturn(2);
				when(rs.getInt(PayInQueDBColumns.FRAUGSTERSTATUS.getName())).thenReturn(1);
				when(rs.getInt(PayInQueDBColumns.SANCTIONSTATUS.getName())).thenReturn(1);
				when(rs.getInt(PayInQueDBColumns.BLACKLISTSTATUS.getName())).thenReturn(1);
				when(rs.getInt(PayInQueDBColumns.CUSTOMCHECKSTATUS.getName())).thenReturn(1);
				when(rs.getString(PayInQueDBColumns.ORGANIZATION.getName())).thenReturn("Currencies Direct");
				when(rs.getString(PayInQueDBColumns.LEGALENTITY.getName())).thenReturn("CDLGB");
				when(rs.getString(PayInQueDBColumns.LOCKED_BY.getName())).thenReturn("");
				when(rs.getInt(PayInQueDBColumns.USER_RESOURCE_LOCK_ID.getName())).thenReturn(1);
				when(rs.getString(RegQueDBColumns.CODE.getName())).thenReturn("CDINC");
				
				when(rs.getString(RegQueDBColumns.OWNER.getName())).thenReturn(Name);
				when(rs.getString(RegQueDBColumns.CURRENCY.getName())).thenReturn("AED");
				
			} catch (Exception e) {
				System.out.println(e);
			}	
		}
	 
	 @Test
	 public void testGetPaymentInReportWithCriteria()
	 {
		 PaymentInQueueDto expectedResult=getPaymentInQueueDto();
		 setPreparedStmt();
		 setMock();
		 try {
			PaymentInQueueDto paymentInQueueDto=paymentInReportDBServiceImpl.getPaymentInReportWithCriteria(setFilter());
			assertEquals(expectedResult.getPaymentInQueue().get(0).getAccountId(),paymentInQueueDto.getPaymentInQueue().get(0).getAccountId());
		 } catch (CompliancePortalException e) {
			System.out.println(e);
		}
	 }
	 
	 @Test
	 public void testForGetPaymentInReportWithCriteria()
	 {
		 setMock();
		 try {
			paymentInReportDBServiceImpl.getPaymentInReportWithCriteria(setFilter());
			 } catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	 }
	 @Test
	 public void testGetPaymentInQueueWholeData()
	 {
		 setPreparedStmt();
		 PaymentInQueueDto expectedResult=setPaymentInQueueDto();
		 setMock();
		 try {
			PaymentInQueueDto paymentInQueueDto=paymentInReportDBServiceImpl.getPaymentInQueueWholeData(setFilter());
			assertEquals(expectedResult.getPaymentInQueue().get(0).getAccountId(),paymentInQueueDto.getPaymentInQueue().get(0).getAccountId());
		 } catch (CompliancePortalException e) {
			System.out.println(e);
		}
	 }
	 @Test
	 public void testForGetPaymentInQueueWholeData()
	 {
		 setMock();
		 try {
			paymentInReportDBServiceImpl.getPaymentInQueueWholeData(setFilter());
			 } catch (CompliancePortalException e) {
				 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			}
	 }
}