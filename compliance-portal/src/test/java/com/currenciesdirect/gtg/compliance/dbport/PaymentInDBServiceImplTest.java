package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.*;
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
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class PaymentInDBServiceImplTest {
	@InjectMocks
	PaymentInDBServiceImpl paymentInDBServiceImpl;
	
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
	private static final String NAME = "cd.comp.system";
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.getResultSet()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false)
			.thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true); 
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		} catch (SQLException e)
		{
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
	public PaymentInSearchCriteria getPaymentInSearchCriteria()
	{
		String[] custType = {"CFX"};
		PaymentInSearchCriteria paymentInSearchCriteria= new PaymentInSearchCriteria();
		PaymentInFilter filter= new PaymentInFilter();
		filter.setUserProfile(getUser());
		filter.setExcludeCustType(custType);
		paymentInSearchCriteria.setFilter(filter);
		paymentInSearchCriteria.setIsFilterApply(false);
		paymentInSearchCriteria.setIsLandingPage(false);
		paymentInSearchCriteria.setIsRequestFromReportPage(false);
		paymentInSearchCriteria.setFilter(filter);
		return paymentInSearchCriteria;
	}
	public void setMockPaymentInQueueWithCriteria()
	{
		try {
			when(rs.getString(PayInQueDBColumns.RISKSTATUS.getName())).thenReturn("NOT_REQUIRED");
			when(rs.getString(PayInQueDBColumns.TRANSACTIONID.getName())).thenReturn("8868546104247690-38342041");
			when(rs.getString(PayInQueDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("8868546104247690");
			when(rs.getInt(PayInQueDBColumns.ACCOUNTID.getName())).thenReturn(17986);
			when(rs.getInt(PayInQueDBColumns.CONTACTID.getName())).thenReturn(18656);
			when(rs.getTimestamp(PayInQueDBColumns.DATE.getName())).thenReturn(Timestamp.valueOf("2020-05-05 13:05:06.0"));
			when(rs.getString(PayInQueDBColumns.CLIENTNAME.getName())).thenReturn("Aniket mane");
			when(rs.getInt(PayInQueDBColumns.TYPE.getName())).thenReturn(2);
			when(rs.getString(PayInQueDBColumns.SELLCURRENCY.getName())).thenReturn("GBP");
			when(rs.getString(PayInQueDBColumns.AMOUNT.getName())).thenReturn("7000.00000000");
			when(rs.getString(PayInQueDBColumns.METHOD.getName())).thenReturn("BACS/CHAPS/TT");
			when(rs.getString(PayInQueDBColumns.OVERALLSTATUS.getName())).thenReturn("HOLD");
			when(rs.getInt(PayInQueDBColumns.WATCHLISTSTATUS.getName())).thenReturn(2);
			when(rs.getInt(PayInQueDBColumns.FRAUGSTERSTATUS.getName())).thenReturn(1);
			when(rs.getInt(PayInQueDBColumns.SANCTIONSTATUS.getName())).thenReturn(9);
			when(rs.getInt(PayInQueDBColumns.BLACKLISTSTATUS.getName())).thenReturn(9);
			when(rs.getInt(PayInQueDBColumns.CUSTOMCHECKSTATUS.getName())).thenReturn(1);
			when(rs.getInt(PayInQueDBColumns.PAYMENTINID.getName())).thenReturn(301395);
			when(rs.getString(PayInQueDBColumns.ORGANIZATION.getName())).thenReturn("FCG");
			when(rs.getString(PayInQueDBColumns.LEGALENTITY.getName())).thenReturn("FCGEU");
			when(rs.getInt(RegQueDBColumns.TOTALROWS.getName())).thenReturn(1);
			when(rs.getString(RegQueDBColumns.OWNER.getName())).thenReturn(NAME);
			when(rs.getString(RegQueDBColumns.CODE.getName())).thenReturn("FCG").thenReturn("FCGEU");
			when(rs.getString(RegQueDBColumns.CURRENCY.getName())).thenReturn("AED");
			when(rs.getString("Country")).thenReturn("Canada");
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	
	public PaymentInQueueDto getPaymentInQueueDto() { 
		PaymentInQueueDto paymentInQueueDto= new PaymentInQueueDto(); 
	List<PaymentInQueueData> paymentInQueue= new ArrayList<>();
	PaymentInQueueData paymentInQueueData= new PaymentInQueueData();
	paymentInQueueData.setClientId("8868546104247690");
	paymentInQueueData.setAccountId(17986);
	paymentInQueueData.setAmount("7,000");
	paymentInQueueData.setBlacklist("NOT REQUIRED");
	paymentInQueueData.setContactId(18656);
	paymentInQueueData.setContactName("Aniket mane");
	paymentInQueueData.setDate("05/05/2020 13:05:06");
	paymentInQueueData.setCustomCheck("PASS");
	paymentInQueueData.setCountryFullName("----");
	paymentInQueueData.setFraugster("PASS");
	paymentInQueueData.setLegalEntity("FCGEU");
	paymentInQueueData.setMethod("BACS/CHAPS/TT");
	paymentInQueueData.setOrganization("FCG");
	paymentInQueueData.setPaymentInId("301395");
	paymentInQueueData.setOverallStatus("HOLD");
	paymentInQueueData.setRiskStatus("NOT REQUIRED");
	paymentInQueueData.setSanction("NOT REQUIRED");
	paymentInQueueData.setSellCurrency("GBP");
    paymentInQueueData.setTransactionId("8868546104247690-38342041");
    paymentInQueueData.setType("PFX");
    paymentInQueueData.setWatchlist("FAIL"); 
    paymentInQueue.add(paymentInQueueData);
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
		owner.add(NAME);
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
		paymentInQueueDto.setCountry(country);
		paymentInQueueDto.setCurrency(currency);
		paymentInQueueDto.setLegalEntity(legalEntity);
		paymentInQueueDto.setOrganization(organization);
		paymentInQueueDto.setOnfidoStatus(onfidoStatus);
		paymentInQueueDto.setOwner(owner);
		paymentInQueueDto.setPage(getpage());
		paymentInQueueDto.setWatchList(watchlist);
		paymentInQueueDto.setUser(getUser());
		paymentInQueueDto.setPaymentInQueue(paymentInQueue);
	  return paymentInQueueDto; 
	  }
	 
	
	
	public void setMockMoreFraugsterDetails()
	{
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn("{\"status\":\"PASS\",\"frgTransId\":"
					+ "\"c82b7ea0-0c96-4ad0-9440-0248eacfd022\",\"score\":\"-91.71\",\"fraugsterApproved\":"
					+ "\"1.0\",\"cdTrasId\":\"13200040201000009969608\"}");
			when(rs.getInt(Constants.ENTITY_TYPE)).thenReturn(1);
			when(rs.getString("entityid")).thenReturn("651605");
			when(rs.getInt("id")).thenReturn(22576723);
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-11-25 06:23:07.497");
			when(rs.getString("updatedBy")).thenReturn(NAME);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	public ViewMoreResponse getViewMoreResponseFraugsterDetails()
	{
		ViewMoreResponse viewMoreResponse= new ViewMoreResponse();	
		List<IDomain> services= new ArrayList<>();
		Fraugster fraugster= new Fraugster();
		fraugster.setCreatedOn("25/11/2020 06:23:07");
		fraugster.setFraugsterId("c82b7ea0-0c96-4ad0-9440-0248eacfd022");
		fraugster.setId(22576723);
		fraugster.setScore("-91.71");
		fraugster.setStatus(true);
		fraugster.setUpdatedBy(NAME);
		services.add(fraugster);
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
	}
public PaymentInViewMoreRequest getPaymentInViewMoreRequestFraugsterDetails()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
	paymentInViewMoreRequest.setAccountId(651605);
	paymentInViewMoreRequest.setClientType("PFX");
	paymentInViewMoreRequest.setEntityType("CONTACT");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setPaymentInId(1320004);
	paymentInViewMoreRequest.setServiceType("FRAUGSTER");
	return paymentInViewMoreRequest;	
}
public PaymentInViewMoreRequest getPaymentInViewMoreFraugsterDetails()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
	paymentInViewMoreRequest.setAccountId(651605);
	paymentInViewMoreRequest.setClientType("PFX");
	paymentInViewMoreRequest.setEntityType("CONTACT");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setServiceType("FRAUGSTER");
	return paymentInViewMoreRequest;
}

	public void setMockMoreSanctionDetails()
	{
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn("{\"status\":\"PASS\",\"sanctionId\":"
					+ "\"002-D-0001319674\",\"ofacList\":\"Safe\",\"worldCheck\":\"Safe\",\"updatedOn\":"
					+ "\"2020-11-13 10:19:27.149\",\"providerName\":\"FINSCAN\",\"providerMethod\":\"SLLookupMulti\"}");
			when(rs.getInt(Constants.ENTITY_TYPE)).thenReturn(5);
			when(rs.getString("entityid")).thenReturn("651605");
			when(rs.getInt("id")).thenReturn(22569350);
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-11-25 07:39:09.993");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	public ViewMoreResponse getViewMoreResponseSanctionDetails()
	{
		ViewMoreResponse viewMoreResponse= new ViewMoreResponse();	
		List<IDomain> services= new ArrayList<>();
		Sanction sanction = new Sanction();
		sanction.setEntityType("DEBTOR");
		sanction.setEventServiceLogId(22569350);
		sanction.setIsRequired(true);
		sanction.setOfacList("Safe");
		sanction.setWorldCheck("Safe");
		sanction.setSanctionId("002-D-0001319674");
		sanction.setStatus(true);
		sanction.setStatusValue("PASS");
		sanction.setUpdatedBy("test_atlas_senior_executive");
		sanction.setUpdatedBy("25/11/2020 07:39:09");
		services.add(sanction);
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
	}
public PaymentInViewMoreRequest getPaymentInViewMoreRequestSanctionDetails()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
    paymentInViewMoreRequest.setAccountId(649478);
	paymentInViewMoreRequest.setClientType("CFX");
	paymentInViewMoreRequest.setEntityType("DEBTOR");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setPaymentInId(1319674);
	paymentInViewMoreRequest.setServiceType("SANCTION");
	return paymentInViewMoreRequest;
	
}
public PaymentInViewMoreRequest getPaymentInViewMoreSanctionDetails()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
    paymentInViewMoreRequest.setAccountId(649478);
	paymentInViewMoreRequest.setClientType("CFX");
	paymentInViewMoreRequest.setEntityType("DEBTOR");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setServiceType("SANCTION");
	return paymentInViewMoreRequest;
	
}public void setMockMoreCustomCheckDetails()
	{
		try {
			when(rs.getString(Constants.SUMMARY)).thenReturn("{\"countryWhitelisted\":false,"
					+ "\"countryWhitelistedForFundsIn\":false,\"overallStatus\":\"PASS\",\"velocityCheck\":{"
					+ "\"status\":\"NOT_REQUIRED\",\"noOffundsoutTxn\":\"NOT_REQUIRED\","
					+ "\"permittedAmoutcheck\":\"NOT_REQUIRED\",\"beneCheck\":\"NOT_REQUIRED\"},"
					+ "\"whiteListCheck\":{\"currency\":\"PASS\",\"amoutRange\":\"NOT_REQUIRED\","
					+ "\"thirdParty\":\"NOT_REQUIRED\",\"reasonOfTransfer\":\"NOT_REQUIRED\",\"status\":"
					+ "\"PASS\"},\"accountWhiteList\":{\"orgCode\":\"Currencies Direct\",\"accountId\":651605,"
					+ "\"createdOn\":\"2020-11-17 07:42:00.499\",\"approvedReasonOfTransList\":[\"STUDY_FEES\","
					+ "\"\"],\"approvedBuyCurrencyAmountRangeList\":[{\"code\":\"USD\",\"txnAmountUpperLimit\":100000.0}],"
					+ "\"approvedSellCurrencyAmountRangeList\":[{\"code\":\"GBP\",\"txnAmountUpperLimit\":"
					+ "100000.0}],\"approvedThirdpartyAccountList\":[],\"approvedHighRiskCountryList\":"
					+ "[],\"documentationRequiredWatchlistSellCurrency\":[],\"usClientListBBeneAccNumber\":[],"
					+ "\"approvedOPIAccountNumber\":[],\"approvedHighRiskCountryListForFundsIn\":[]}}");
			when(rs.getInt(Constants.ENTITY_TYPE)).thenReturn(1);
			when(rs.getString("entityid")).thenReturn("651605");
			when(rs.getInt("id")).thenReturn(22576764);
			when(rs.getString("status")).thenReturn("1");
			when(rs.getString(Constants.UPDATED_ON)).thenReturn("2020-11-25 07:32:32.81");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	public ViewMoreResponse getViewMoreResponseCustomCheck()
	{
		ViewMoreResponse viewMoreResponse= new ViewMoreResponse();	
		List<IDomain> services= new ArrayList<>();
		PaymentOutCustomCheck paymentOutCustomCheck= new PaymentOutCustomCheck();
		paymentOutCustomCheck.setCheckedOn("25/11/2020 07:32:32");
		paymentOutCustomCheck.setEnitityId("651605");
		paymentOutCustomCheck.setEntityType("ACCOUNT");
		paymentOutCustomCheck.setId(22576764);
		paymentOutCustomCheck.setStatus("1");
		VelocityCheck velocityCheck= new VelocityCheck();
		velocityCheck.setBeneCheck("NOT_REQUIRED");
		velocityCheck.setNoOffundsoutTxn("NOT_REQUIRED");
		velocityCheck.setPermittedAmoutcheck("NOT_REQUIRED");
		paymentOutCustomCheck.setVelocityCheck(velocityCheck);
		WhitelistCheck whiteListCheck= new WhitelistCheck();
		whiteListCheck.setAmoutRange("Not Required");
		whiteListCheck.setCurrency("PASS");
		whiteListCheck.setReasonOfTransfer("Not Required");
		whiteListCheck.setThirdParty("Not Required");
		paymentOutCustomCheck.setWhiteListCheck(whiteListCheck);
		services.add(paymentOutCustomCheck);
		viewMoreResponse.setServices(services);
		return viewMoreResponse;
	}
public PaymentInViewMoreRequest getPaymentInViewMoreRequestCustomCheck()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
	paymentInViewMoreRequest.setAccountId(651605);
	paymentInViewMoreRequest.setClientType("PFX");
	paymentInViewMoreRequest.setEntityType("ACCOUNT");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setPaymentInId(1320004);
	paymentInViewMoreRequest.setServiceType("VELOCITYCHECK");
	return paymentInViewMoreRequest;
}
public PaymentInViewMoreRequest getPaymentInViewMoreCustomCheck()
{
	PaymentInViewMoreRequest paymentInViewMoreRequest= new PaymentInViewMoreRequest();
	paymentInViewMoreRequest.setAccountId(651605);
	paymentInViewMoreRequest.setClientType("PFX");
	paymentInViewMoreRequest.setEntityType("ACCOUNT");
	paymentInViewMoreRequest.setMaxViewRecord(11);
	paymentInViewMoreRequest.setMinViewRecord(2);
	paymentInViewMoreRequest.setNoOfDisplayRecords(1);
	paymentInViewMoreRequest.setOrganisation("Currencies Direct");
	paymentInViewMoreRequest.setServiceType("VELOCITYCHECK");
	return paymentInViewMoreRequest;
	
}

@Test
public void testGetPaymentInQueueWithCriteria() {
	setMockPaymentInQueueWithCriteria();
	try {
		
		PaymentInQueueDto actualResult=paymentInDBServiceImpl.getPaymentInQueueWithCriteria(getPaymentInSearchCriteria());
		PaymentInQueueDto expectedResult=getPaymentInQueueDto();
	assertEquals(expectedResult.getPaymentInQueue().get(0).getAccountId(),actualResult.getPaymentInQueue().get(0).getAccountId());
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
}


	@Test
	public void testGetMoreFraugsterDetails() {
		setMockMoreFraugsterDetails();
		ViewMoreResponse expectedResult=getViewMoreResponseFraugsterDetails();
		try {
			ViewMoreResponse actualResult=paymentInDBServiceImpl.getMoreFraugsterDetails(getPaymentInViewMoreRequestFraugsterDetails());
			assertEquals(expectedResult.getClass(),actualResult.getClass());
			} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetMoreFraugsterDetails() {
		
		try {
	        paymentInDBServiceImpl.getMoreFraugsterDetails(getPaymentInViewMoreRequestFraugsterDetails());			
			} catch (CompliancePortalException e) {
				assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			}
	}
	@Test
	public void testGetMoreSanctionDetails() {
		setMockMoreSanctionDetails();
		ViewMoreResponse expectedResult=getViewMoreResponseSanctionDetails();
		try {
			ViewMoreResponse actualResult=paymentInDBServiceImpl.getMoreSanctionDetails(getPaymentInViewMoreRequestSanctionDetails());
			assertEquals(expectedResult.getClass(),actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	@Test
	public void testForGetMoreSanctionDetails() {
		
		try {
			paymentInDBServiceImpl.getMoreSanctionDetails(getPaymentInViewMoreSanctionDetails());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
		
	}
	
	@Test
	public void testGetMoreCustomCheckDetails() {
		setMockMoreCustomCheckDetails();
		try {
			ViewMoreResponse expectedResult=getViewMoreResponseCustomCheck();
			ViewMoreResponse actualResult=paymentInDBServiceImpl.getMoreCustomCheckDetails(getPaymentInViewMoreRequestCustomCheck());
		assertEquals(expectedResult.getClass(),actualResult.getClass());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}	
	}
	@Test
	public void testForGetMoreCustomCheckDetails() {
		try {
			paymentInDBServiceImpl.getMoreCustomCheckDetails(getPaymentInViewMoreRequestCustomCheck());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
	}
	
}
