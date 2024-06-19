package com.currenciesdirect.gtg.compliance.dbport.report;


import static org.junit.Assert.assertEquals;
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
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.dbport.PayOutQueDBColumns;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentOutReportDBServiceImplTest {
	
	@InjectMocks
	PaymentOutReportDBServiceImpl paymentOutReportDBServiceImpl;
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
	String keyword="0202088009936016-000000066";
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false); 
			
			Mockito.when(preparedStatement.execute()).thenReturn(true); 
		
		} catch (SQLException e)
		{
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
		userprof.setName("cd.comp.system");
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

	public PaymentOutReportSearchCriteria getPaymentOutReportSearchCriteria()
	{
		String[] excludeCustType = {"CFX"};
		PaymentOutReportSearchCriteria searchCriteria=new PaymentOutReportSearchCriteria();
		PaymentOutReportFilter filter= new PaymentOutReportFilter();
	    filter.setKeyword(keyword);
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(true);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		filter.setExcludeCustType(excludeCustType);
		filter.setUserProfile(getUser());
         searchCriteria.setPage(getpage());
		return searchCriteria;
	}
	public PaymentOutReportSearchCriteria getSearchCriteria()
	{
		String[] excludeCustType = {"CFX"};
		PaymentOutReportSearchCriteria searchCriteria=new PaymentOutReportSearchCriteria();
		PaymentOutReportFilter filter= new PaymentOutReportFilter();
		searchCriteria.setIsFilterApply(true);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		filter.setExcludeCustType(excludeCustType);
		filter.setUserProfile(getUser());
         searchCriteria.setPage(getpage());
		return searchCriteria;
	}
	public PaymentOutQueueDto getpaymentOutQueueDto()
	{
		PaymentOutQueueDto data= new PaymentOutQueueDto();
		data.setPaymentOutQueue(getpaymentOutQueue());
		return data;
	}
	public List<PaymentOutQueueData> getpaymentOutQueue()
	{
		List<PaymentOutQueueData> data= new  ArrayList<>();
		PaymentOutQueueData paymentOutQueueData=new PaymentOutQueueData();
		paymentOutQueueData.setPaymentOutId("182899");
		paymentOutQueueData.setTransactionId(keyword);
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
	
	public void setMock()
	{
		String attribute="{\"org_code\":\"Currencies Direct\",\"source_application\":\"Titan\",\"trade\":{\"cust_type\":\"PFX\",\"trade_contact_id\":\"66365359\",\n" + 
				"\"contract_number\":\"0202088009936016-000000066\",\"deal_date\":\"2019-02-11\",\"selling_amount\":350.0,\n" + 
				"\"buying_amount\":385.59,\"purpose_of_trade\":\"LIVING_FUNDS\",\n" + 
				"\"maturity_date\":\"2019-02-11\",\"trade_account_number\":\"0202088009936016\",\n" + 
				"\"value_date\":\"2019-02-11\",\"buying_amount_base_value\":337.72,\n" + 
				"\"buy_currency\":\"EUR\",\"sell_currency\":\"GBP\",\"third_party_payment\":true,\n" + 
				"\"customer_legal_entity\":\"CDLGB\"},\"beneficiary\":{\n" + 
				"\"phone\":\"\",\"first_name\":\"Mark\",\"last_name\":\"Pattison\",\"email\":\"\",\"country\":\"FRA\",\n" + 
				"\"account_number\":\"CCBPFRPPBDXFR7610907003912103622595174\",\n" + 
				"\"currency_code\":\"EUR\",\"beneficiary_id\":8788349,\"beneficiary_bankid\"\n" + 
				":8788349,\"beneficary_bank_name\":\"CREDIT AGRICOLE SA\",\n" + 
				"\"beneficary_bank_address\":\"PO BOX 516 5 PLACE JEAN JAURES 33001 BORDEAUX FRANCE\",\"trans_ts\":\"2019-02-05T19:38:22Z\",\n" + 
				"\"payment_reference\":\"CD 73408\",\"opi_created_date\":\"2019-02-11\",\n" + 
				"\"beneficiary_type\":\"Individual\",\"payment_fundsout_id\":182856,\"amount\":385.59},\n" + 
				"\"version\":1,\"osr_id\":\"13492d2f-121a-4a0f-a5bc-6dc3b2c1f627\"}";
		try {
			when(rs.getString(PayOutQueDBColumns.TRANSACTIONID.getName())).thenReturn(keyword);
			when(rs.getString(PayOutQueDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("0202088009936016");
			when(rs.getInt(PayOutQueDBColumns.ACCOUNTID.getName())).thenReturn(45518);
			when(rs.getString(PayOutQueDBColumns.CONTACTID.getName())).thenReturn("50616");
			when(rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())).thenReturn(Timestamp.valueOf("2019-02-05 19:38:22.0"));
			when(rs.getString(PayOutQueDBColumns.CLIENTNAME.getName())).thenReturn("John2 2-add Cena");
			when(rs.getString(PayOutQueDBColumns.TYPE.getName())).thenReturn("PFX");
			when(rs.getString(PayOutQueDBColumns.BUYCURRENCY.getName())).thenReturn("EUR");
			when(rs.getString(PayOutQueDBColumns.ATTRIBUTE.getName())).thenReturn(attribute);
			when(rs.getString(PayOutQueDBColumns.BENEFICIARY.getName())).thenReturn("Mark Pattison");
			when(rs.getString(PayOutQueDBColumns.COUNTRY.getName())).thenReturn("France");
			when(rs.getString(PayOutQueDBColumns.OVERALLSTATUS.getName())).thenReturn("HOLD");
			when(rs.getInt(PayOutQueDBColumns.WATCHLISTSTATUS.getName())).thenReturn(2);
			when(rs.getInt(PayOutQueDBColumns.FRAUGSTERSTATUS.getName())).thenReturn(8);
			when(rs.getInt(PayOutQueDBColumns.SANCTIONSTATUS.getName())).thenReturn(2);
			when(rs.getInt(PayOutQueDBColumns.BLACKLISTSTATUS.getName())).thenReturn(1);
			when(rs.getInt(PayOutReportQueueDBColumns.CUSTOMCHECKSTATUS.getName())).thenReturn(8);
			when(rs.getInt(PayOutQueDBColumns.PAYMENTOUTID.getName())).thenReturn(182901);
			when(rs.getString(PayOutQueDBColumns.ORGANIZATION.getName())).thenReturn("Currencies Direct");
			when(rs.getString(PayOutReportQueueDBColumns.LEGALENTITY.getName())).thenReturn("CDLGB");
			when(rs.getString(PayOutReportQueueDBColumns.VALUE_DATE.getName())).thenReturn("0202088009936016-000000064");
			when( rs.getString(PayOutReportQueueDBColumns.MATURITY_DATE.getName())).thenReturn("0202088009936016-000000064");
		} catch (Exception e) {
			System.out.println(e);
		}	
	}
	
	@Test
	public void testGetPaymentOutQueueWholeData() {
		setMock();
		PaymentOutReportSearchCriteria searchCriteria = getPaymentOutReportSearchCriteria();
		PaymentOutQueueDto expectedResult = getpaymentOutQueueDto();
		try {
			PaymentOutQueueDto paymentOutQueueDto = paymentOutReportDBServiceImpl
					.getPaymentOutQueueWholeData(searchCriteria);
			assertEquals(expectedResult.getPaymentOutQueue().get(0).getTransactionId(),
					paymentOutQueueDto.getPaymentOutQueue().get(0).getTransactionId());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetPaymentOutQueueWholeData() {
		setMock();
		PaymentOutReportSearchCriteria searchCriteria = getSearchCriteria();
		try {
			 paymentOutReportDBServiceImpl.getPaymentOutQueueWholeData(searchCriteria);
			} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
	@Test
	public void testGetPaymentOutReportWithCriteria() {
		setMock();
		PaymentOutReportSearchCriteria searchCriteria = getPaymentOutReportSearchCriteria();
		PaymentOutQueueDto expectedResult = getpaymentOutQueueDto();
		try {
			PaymentOutQueueDto paymentOutQueueDto = paymentOutReportDBServiceImpl
					.getPaymentOutReportWithCriteria(searchCriteria);
			assertEquals(expectedResult.getPaymentOutQueue().get(0).getTransactionId(),
					paymentOutQueueDto.getPaymentOutQueue().get(0).getTransactionId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetPaymentOutReportWithCriteria() {
		setMock();
		PaymentOutReportSearchCriteria searchCriteria = getSearchCriteria();
		try {
			 paymentOutReportDBServiceImpl.getPaymentOutReportWithCriteria(searchCriteria);
			} catch (Exception e) {
				assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			}
	}
}
