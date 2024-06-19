package com.currenciesdirect.gtg.compliance.dbport;

import static org.junit.Assert.*;
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
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationFilter;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymizationServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

@RunWith(PowerMockRunner.class)
public class DataAnonymisationDBServiceImplTest {

	@InjectMocks
	DataAnonymisationDBServiceImpl dataAnonymisationDBServiceImpl;

	@Mock
	private UserProfile user;

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
	private DataAnonymisationDataRequest dataAnonymisationDataRequest;

	int offset = 0;
	int rowsToFetch = 40;
	String query = "";
	String countQuery = "";

	@Before
	public void setUp() {
		try {
			connection = org.mockito.Mockito.mock(Connection.class);
			ResultSet rs = mock(ResultSet.class);
			when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(rs.getString(DataAnonDBColumns.ACTIVITY.getName())).thenReturn("INITIALIZATION");
			Mockito.when(rs.getString(DataAnonDBColumns.ACTIVITYBY.getName())).thenReturn("cd.comp.system");
			Mockito.when(rs.getString(DataAnonDBColumns.COMMENT.getName())).thenReturn("initialization request");
			Mockito.when(rs.getTimestamp(DataAnonDBColumns.ACTIVITYDATE.getName()))
					.thenReturn(Timestamp.valueOf("2019-11-28 12:10:52"));
			Mockito.when(rs.getString(DataAnonDBColumns.ACTIVITYDATE.getName())).thenReturn("2019-11-28 12:10:52");
			Mockito.when(rs.getInt("AnonCount")).thenReturn(1);
			when(rs.getInt(DataAnonDBColumns.CONTACTID.getName())).thenReturn(50513);
			when(rs.getInt(DataAnonDBColumns.ACCOUNTID.getName())).thenReturn(49674);
			when(rs.getString(DataAnonDBColumns.CONTACTNAME.getName())).thenReturn("Saad Rashed Mohammad AL-FAQIH");
			when(rs.getString(DataAnonDBColumns.TRADEACCOUNTNUM.getName())).thenReturn("6881588000345442");
			when(rs.getTimestamp(DataAnonDBColumns.INITIAL_APPROVALDATE.getName())).thenReturn(Timestamp.valueOf("2020-07-28 10:19:38"));
			when(rs.getTimestamp(DataAnonDBColumns.FINAL_APPROVALDATE.getName())).thenReturn(Timestamp.valueOf("2020-07-28 10:19:40"));
			when(rs.getString(DataAnonDBColumns.REGOWNER.getName())).thenReturn("cd.comp.system");
			when(rs.getInt(DataAnonDBColumns.DATAANONSTATUS.getName())).thenReturn(1);
			when(rs.getString(DataAnonDBColumns.SOURCE.getName())).thenReturn("Web");
			when(rs.getInt(DataAnonDBColumns.TYPE.getName())).thenReturn(2);
			when(rs.getString(DataAnonDBColumns.STATUS.getName())).thenReturn("INACTIVE");
			when(rs.getInt(DataAnonDBColumns.ID.getName())).thenReturn(249);
			when(rs.getInt(DataAnonDBColumns.TOTALROWS.getName())).thenReturn(1);
			when(rs.getString("CRMContactID")).thenReturn("9886O65210WIHiPVSQ");
			when(rs.getInt("TradeContactID")).thenReturn(578664288);
			when(rs.getString("CRMAccountID")).thenReturn("9884Z50980FDWo3ESQ");
			when(rs.getInt("TradeAccountID")).thenReturn(28874587);
			when(rs.getString("TradeAccountNumber")).thenReturn("6881588000345442");
			when(rs.getString("Organization")).thenReturn("Currencies Direct");
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
public void mockGetConnection()
{
	try {
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
	} catch (SQLException e) {
		System.out.println(e);
	}
}
	public DataAnonymisationDataRequest getRequestInfo() {
		dataAnonymisationDataRequest = new DataAnonymisationDataRequest();
		dataAnonymisationDataRequest.setAccountId(49756);
		dataAnonymisationDataRequest.setContactId(50607);
		dataAnonymisationDataRequest.setComment("initialization request");
		dataAnonymisationDataRequest.setId(235);
		dataAnonymisationDataRequest.setInitialApprovalDate("2020-7-30 12:10:52");

		return dataAnonymisationDataRequest;
	}

	public UserProfile getUserInfo() {
		UserProfile userProfile = new UserProfile();
		userProfile.setId(1);
		userProfile.setName("cd.comp.system");
		userProfile.setPermissions(getUserPermission());
		userProfile.setPreferredUserName("Default User");
		userProfile.setRole(getRoleInfo());

		return userProfile;
	}

	public Role getRoleInfo() {
		Set<String> names = new HashSet<>();
		names.add("ATLAS_DATA_ANON_APPROVER");
		names.add("ATLAS_DATA_ANON_INITIATOR");
		names.add("ATLAS_DEPT_HEAD");
		names.add("ATLAS_BENE_MGMT");
		Role role = new Role();
		role.setNames(names);
		role.setFunctions(getFunctionInfo());
		return role;
	}

	public UserPermission getUserPermission() {
		UserPermission permission = new UserPermission();
		permission.setCanWorkOnCFX(false);
		permission.setCanWorkOnCFX(true);
		permission.setCanViewRegistrationQueue(true);
		permission.setCanViewRegistrationDetails(true);
		permission.setCanViewPaymentInQueue(true);
		permission.setCanViewPaymentInDetails(true);
		permission.setCanViewPaymentOutQueue(true);
		permission.setCanViewPaymentOutDetails(true);
		permission.setCanViewRegistrationReport(true);
		permission.setCanViewPaymentInReport(true);
		permission.setCanViewPaymentOutReport(true);
		permission.setCanViewWorkEfficiacyReport(true);
		permission.setCanManageWatchListCategory1(true);
		permission.setCanManageWatchListCategory2(true);
		permission.setCanUnlockRecords(true);
		permission.setCanViewDashboard(true);
		permission.setCanManageFraud(true);
		permission.setCanManageCustom(true);
		permission.setCanManageEID(true);
		permission.setCanManageSanction(true);
		permission.setCanManageBlackList(true);
		permission.setCanDoAdministration(true);
		permission.setIsReadOnlyUser(false);
		permission.setCanManageBeneficiary(true);
		permission.setCanInitiateDataAnon(true);
		permission.setCanApproveDataAnon(true);
		permission.setCanNotLockAccount(false);
		return permission;
	}

	public List<Function> getFunctionInfo() {
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

	List<DataAnonymisationDataRequest> setHistory() {
		DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
		DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();
		dataAnonymisationData.setActivity("INITIALIZATION");
		dataAnonymisationData.setActivityBy("cd.comp.system");
		dataAnonymisationData.setActivityDate("2019-11-28 12:10:52");
		dataAnonymisationData.setComment("initialization request");
		dataAnonymisationDataList.add(dataAnonymisationData);

		return dataAnonymisationDataList;
	}
public DataAnonymisationDto getDataAnonymisationDto()
{
	DataAnonymisationDto dataAnonymisationDto= new DataAnonymisationDto();
	List<DataAnonymisationDataRequest> dataAnonymisation= new ArrayList<>();
	DataAnonymisationDataRequest dataAnonymisationDataRequest= new DataAnonymisationDataRequest();
	dataAnonymisationDataRequest.setAccountId(49674);
	dataAnonymisationDataRequest.setComplianceStatus("INACTIVE");
	dataAnonymisationDataRequest.setContactId(50513);
	dataAnonymisationDataRequest.setContactName("Saad Rashed Mohammad AL-FAQIH");
	dataAnonymisationDataRequest.setDataAnonymizationStatus(1);
	dataAnonymisationDataRequest.setApprovedDate("28/07/2020 10:19:40");
	dataAnonymisationDataRequest.setId(249);
	dataAnonymisationDataRequest.setInitialApprovalDate("28/07/2020 10:19:38");
	dataAnonymisationDataRequest.setInitialApprovalBy("cd.comp.system");
	dataAnonymisationDataRequest.setSource("Web");
	dataAnonymisationDataRequest.setTradeAccountNum("6881588000345442");
	dataAnonymisationDataRequest.setType("PFX");
	dataAnonymisationDto.setDataAnonymisation(dataAnonymisation);
	dataAnonymisationDto.setTotalRecords(1);
	return dataAnonymisationDto;
	
	}

	@Test 
	public void testGetDataAnonymisationWithCriteria()
	{
		mockGetConnection();
		String[] custType = {"CFX"};
		DataAnonymisationSearchCriteria searchCriteria= new DataAnonymisationSearchCriteria();
		DataAnonymisationFilter filter= new DataAnonymisationFilter();
		filter.setUserProfile(getUserInfo());
		filter.setExcludeCustType(custType);
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		try {
			DataAnonymisationDto expectedResult=getDataAnonymisationDto();
			DataAnonymisationDto actualResult=dataAnonymisationDBServiceImpl.getDataAnonymisationWithCriteria(searchCriteria);
		assertEquals(expectedResult.getTotalRecords(),actualResult.getTotalRecords());
		} catch (CompliancePortalException e) {
		System.out.println(e);
		}
		
	}
	@Test 
	public void testForGetDataAnonymisationWithCriteria()
	{
		String[] custType = {"CFX"};
		DataAnonymisationSearchCriteria searchCriteria= new DataAnonymisationSearchCriteria();
		DataAnonymisationFilter filter= new DataAnonymisationFilter();
		filter.setUserProfile(getUserInfo());
		filter.setExcludeCustType(custType);
		searchCriteria.setFilter(filter);
		searchCriteria.setIsFilterApply(false);
		searchCriteria.setIsLandingPage(false);
		searchCriteria.setIsRequestFromReportPage(false);
		try {
			dataAnonymisationDBServiceImpl.getDataAnonymisationWithCriteria(searchCriteria);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			
		}
		
	}
	@Test
	public void testGetAnonymisationDetails()
	{
		mockGetConnection();
		DataAnonymisationDataRequest dataAnonymisationDataRequest= new DataAnonymisationDataRequest();
		dataAnonymisationDataRequest.setAccountId(49674);
		dataAnonymisationDataRequest.setContactId(50513);
		dataAnonymisationDataRequest.setComment("Testing");
		try {
			DataAnonymizationServiceRequest actualResult=dataAnonymisationDBServiceImpl.getAnonymisationDetails(dataAnonymisationDataRequest);
		assertEquals("Currencies Direct",actualResult.getOrgCode());
		} catch (CompliancePortalException e) {
			System.out.println(e);		
		}		
	}
	
	@Test
	public void testForGetAnonymisationDetails()
	{
		DataAnonymisationDataRequest dataAnonymisationDataRequest= new DataAnonymisationDataRequest();
		dataAnonymisationDataRequest.setAccountId(49674);
		dataAnonymisationDataRequest.setContactId(50513);
		dataAnonymisationDataRequest.setComment("Testing");
		try {
			dataAnonymisationDBServiceImpl.getAnonymisationDetails(dataAnonymisationDataRequest);		
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
				
		}		
	}
	@Test
	public void testGetDataAnonHistory()  {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = setHistory();
		DataAnonymisationDto dataAnonDto1;
		try {
			dataAnonDto1 = dataAnonymisationDBServiceImpl.getDataAnonHistory(request);
			List<DataAnonymisationDataRequest> dataAnonymisationDataList1 = dataAnonDto1.getDataAnonymisation();
			assertEquals(dataAnonymisationDataList.get(0).getActivity(), dataAnonymisationDataList1.get(0).getActivity());		
		} catch (CompliancePortalException e) {
			System.out.println(e);	
		}
	}
	@Test
	public void testForGetDataAnonHistory()  {
		DataAnonymisationDataRequest request = getRequestInfo();
		try {
			dataAnonymisationDBServiceImpl.getDataAnonHistory(request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
			
		}
	}

	@Test
	public void testGetDataAnonymize() {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		boolean expectedResult = true;
		boolean result;
		try {
			result = dataAnonymisationDBServiceImpl.getDataAnonymize(userProf, request);
			assertEquals(expectedResult, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);	
		}	
	}
	@Test
	public void testForGetDataAnonymize() {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		try {
			 dataAnonymisationDBServiceImpl.getDataAnonymize(userProf, request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(),e.getMessage());
				
		}	
	}
	@Test
	public void testGetDataAnonymisation()  {
		mockGetConnection();
		try {
			Mockito.when(rs.getInt("AnonCount")).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);	
		}
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		boolean expectedResult = true;
		boolean result;
		try {
			result = dataAnonymisationDBServiceImpl.getDataAnonymize(userProf, request);
			assertEquals(expectedResult, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);	
		}
		
	}
	@Test
	public void testForGetDataAnonymisation()  {
	
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		try {
			 dataAnonymisationDBServiceImpl.getDataAnonymize(userProf, request);	
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(),e.getMessage());	
		}
		
	}
	@Test
	public void testCancelDataAnonymisation()  {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		boolean expectedResult = true;
		boolean result;
		try {
			result = dataAnonymisationDBServiceImpl.cancelDataAnonymisation(userProf, request);
			assertEquals(expectedResult, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);	
		}
		
	}
	
	@Test
	public void testForCancelDataAnonymisation()  {
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		
		try {
			dataAnonymisationDBServiceImpl.cancelDataAnonymisation(userProf, request);
			
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(),e.getMessage());
			
		}
		
	}

	@Test
	public void testUpdateDataAnonymisation() {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		boolean expectedResult = true;
		boolean result;
		try {
			result = dataAnonymisationDBServiceImpl.updateDataAnonymisation(userProf, request);
			assertEquals(expectedResult, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	public void testForUpdateDataAnonymisation() {
		
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		try {
			dataAnonymisationDBServiceImpl.updateDataAnonymisation(userProf, request);	
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(),e.getMessage());
			
		}
		
	}

	@Test
	public void testSaveIntoDataAnonHistory()  {
		mockGetConnection();
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		boolean expectedResult = true;
		boolean result;
		try {
			result = dataAnonymisationDBServiceImpl.saveIntoDataAnonHistory(userProf, request);
			assertEquals(expectedResult, result);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	public void testForSaveIntoDataAnonHistory()  {
		DataAnonymisationDataRequest request = getRequestInfo();
		UserProfile userProf = getUserInfo();
		try {
			 dataAnonymisationDBServiceImpl.saveIntoDataAnonHistory(userProf, request);
		} catch (Exception e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(),e.getMessage());
			
		}
		
	}
}