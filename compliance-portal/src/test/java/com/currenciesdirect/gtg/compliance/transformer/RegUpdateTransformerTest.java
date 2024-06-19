package com.currenciesdirect.gtg.compliance.transformer;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class RegUpdateTransformerTest {

	@InjectMocks
	RegUpdateTransformer regUpdateTransformer;
	
	
	@Before
	public void setup() {
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

	public RegistrationUpdateRequest getRegistrationUpdateRequest()
	{
		
		RegistrationUpdateRequest registrationUpdateRequest= new RegistrationUpdateRequest();
		registrationUpdateRequest.setAccountId(58144);
		registrationUpdateRequest.setAccountSfId("0035E000122efr9DKN");
		registrationUpdateRequest.setComment("test");
		registrationUpdateRequest.setComplianceDoneOn("----");
		registrationUpdateRequest.setContactId(59046);
		registrationUpdateRequest.setContactSfId("0050E000139yC9IDRM");
		
		StatusReasonUpdateRequest reason= new StatusReasonUpdateRequest();
		reason.setName("Alert from 3rd party");
		reason.setPreValue(false);
		reason.setUpdatedValue(false);
		StatusReasonUpdateRequest reason1= new StatusReasonUpdateRequest();
		reason1.setName("Alert from law enforcement");
		reason1.setPreValue(false);
		reason1.setUpdatedValue(false);
		StatusReasonUpdateRequest[] contactStatusReasons= {reason,reason1};
		registrationUpdateRequest.setContactStatusReasons(contactStatusReasons);
		registrationUpdateRequest.setCustType("PFX");
		registrationUpdateRequest.setFraugsterEventServiceLogId(1077227);
		registrationUpdateRequest.setIsOnQueue(false);
		registrationUpdateRequest.setOrgCode("Currencies Direct");
		registrationUpdateRequest.setOverallWatchlistStatus(false);
		registrationUpdateRequest.setPreAccountStatus("INACTIVE");
		registrationUpdateRequest.setPreContactStatus("INACTIVE");
		registrationUpdateRequest.setRegistrationInDate("2021-04-28 13:24:19.237");
		registrationUpdateRequest.setUpdatedAccountStatus("ACTIVE");
		registrationUpdateRequest.setUser(getUser());
		registrationUpdateRequest.setUserResourceId(716883);
		WatchlistUpdateRequest watchlistUpdateRequest= new WatchlistUpdateRequest();
		watchlistUpdateRequest.setName("Account Info Updated");
		watchlistUpdateRequest.setPreValue(false);
		watchlistUpdateRequest.setUpdatedValue(false);
		WatchlistUpdateRequest watchlistUpdateRequest1= new WatchlistUpdateRequest();
		watchlistUpdateRequest1.setName("CD Inc Client");
		watchlistUpdateRequest1.setPreValue(false);
		watchlistUpdateRequest1.setUpdatedValue(false);
		WatchlistUpdateRequest[] watchlist= {watchlistUpdateRequest,watchlistUpdateRequest1};
		registrationUpdateRequest.setWatchlist(watchlist);
		return registrationUpdateRequest;
		
	}
	
	
	public RegUpdateDBDto getRegUpdateDBDto()
	{
		RegUpdateDBDto regUpdateDBDto= new RegUpdateDBDto();
		regUpdateDBDto.setAccountId(58144);
		regUpdateDBDto.setAccountSfId("0035E000122efr9DKN");
		regUpdateDBDto.setComment("test");
		regUpdateDBDto.setComplianceDoneOn("----");
		regUpdateDBDto.setContactId(59046);
		regUpdateDBDto.setContactSfId("0050E000139yC9IDRM");
		List<ProfileActivityLogDto> activityLog=new ArrayList<>();
		ProfileActivityLogDto profileActivityLogDto= new ProfileActivityLogDto();
		profileActivityLogDto.setAccountId(58144);
		profileActivityLogDto.setActivityBy(NAME);
		profileActivityLogDto.setComment("test");
		profileActivityLogDto.setContactId(59046);
		profileActivityLogDto.setCreatedBy(NAME);
		profileActivityLogDto.setCreatedOn(Timestamp.valueOf("2021-04-29 11:08:03.848"));
		profileActivityLogDto.setOrgCode("Currencies Direct");
		profileActivityLogDto.setTimeStatmp(Timestamp.valueOf("2021-04-29 11:08:03.848"));
		profileActivityLogDto.setUpdatedBy(NAME);
		profileActivityLogDto.setUpdatedOn(Timestamp.valueOf("2021-04-29 11:08:03.848"));
		ActivityLogDetailDto activityLogDetailDto= new ActivityLogDetailDto();
		activityLogDetailDto.setActivityType(ActivityType.PROFILE_CONTACT_STATUS_UPDATE);
		activityLogDetailDto.setCreatedBy(NAME);
		activityLogDetailDto.setCreatedOn(Timestamp.valueOf("2021-04-29 11:08:03.848"));
		activityLogDetailDto.setUpdatedBy(NAME);
		activityLogDetailDto.setUpdatedOn(Timestamp.valueOf("2021-04-29 11:08:03.848"));
		activityLogDetailDto.setLog("Contact status modified from INACTIVE to ACTIVE");
		profileActivityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		regUpdateDBDto.setActivityLog(activityLog);
		regUpdateDBDto.setComplianceDoneOn("2021-04-29 10:59:25.0");
		regUpdateDBDto.setComplianceExpiry("2024-04-29 10:59:25.0");
		regUpdateDBDto.setContactStatus("ACTIVE");
		regUpdateDBDto.setCreatedBy(NAME);        
		regUpdateDBDto.setCustType("PFX");
		regUpdateDBDto.setFragusterEventServiceLogId(1077227);
		regUpdateDBDto.setIsAccountOnQueue(false);
		regUpdateDBDto.setIsContactOnQueue(false);
		regUpdateDBDto.setIsRequestFromQueue(false);
		regUpdateDBDto.setOrgCode("Currancies Direct");
		regUpdateDBDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.FAIL);
		regUpdateDBDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.FAIL);
		regUpdateDBDto.setRegistrationInDate("2021-04-28 13:24:19.237");
		regUpdateDBDto.setUserResourceId(716883);
		return regUpdateDBDto;
	}
	
	@Test
	public void testtransform()
	{
		RegUpdateDBDto expectedResult=getRegUpdateDBDto();
		RegUpdateDBDto actualResult=regUpdateTransformer.transform(getRegistrationUpdateRequest());
	    assertEquals(expectedResult.getAccountId(),actualResult.getAccountId());
	}
	
	
	@Test
	public void testgetActivityLogsForDeletedWatchlist()
	{
		List<String> deletedWatchlistList=new ArrayList<>();
		deletedWatchlistList.add("CD Inc Client");
		String expectedResult="CD Inc Client watchlist removed";
	  String actualResult=RegUpdateTransformer.getActivityLogsForDeletedWatchlist("CD Inc Client", deletedWatchlistList);
	assertEquals(expectedResult,actualResult);
	}
	
}
