package com.currenciesdirect.gtg.compliance.httpport;

import static org.junit.Assert.*;
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
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.IHolisticViewService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticAccount;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticContact;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentSummary;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public class HolisticViewControllerTest {
	@InjectMocks
	HolisticViewController holisticViewController;
	
	@Mock
	IHolisticViewService iHolisticViewService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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

	public HolisticViewResponse getHolisticViewResponse()
	{
		HolisticAccount holisticAccount= new HolisticAccount();
		holisticAccount.setAccSFID("0016E00001BybGoQAJ");
		holisticAccount.setAffiliateName("Unallocated Marketing");
		holisticAccount.setAffiliateNumber("A010069");
		holisticAccount.setBuyingCurrency("GBP");
		HolisticContact holisticContact= new HolisticContact();
		holisticContact.setAccountStatus("INACTIVE");
		holisticContact.setAddress("Po Box 784, York, YO1 0NA, GBR");
		holisticContact.setAddressType("Current Address");
		holisticContact.setAuthorisedSignatory("true");
		holisticContact.setId(802248);
		holisticContact.setJobTitle("Other");
		holisticContact.setName("Kkkkk Kkkk");
		List<HolisticContact> holisticContacts=new ArrayList<>();
		holisticContacts.add(holisticContact);
		PaymentSummary paymentSummary= new PaymentSummary();
		paymentSummary.setNoOfTrades(0);
		paymentSummary.setTotalSaleAmount(0.0);
		ActivityLogDataWrapper activityLogDataWrapper= new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivity("KYC - address verification failed");
		activityLogDataWrapper.setActivityType("Signup-STP");
		activityLogDataWrapper.setContractNumber("----");
		activityLogDataWrapper.setCreatedBy("cd.comp.system");
		activityLogDataWrapper.setCreatedOn("17/09/2020 05:46:30");
		List<ActivityLogDataWrapper> activityLogData= new ArrayList<>();
	activityLogData.add(activityLogDataWrapper);
		ActivityLogs activityLogs= new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		holisticViewResponse.setHolisticAccount(holisticAccount);
		holisticViewResponse.setHolisticContact(holisticContact);
		holisticViewResponse.setHolisticContacts(holisticContacts);
		holisticViewResponse.setPaymentSummary(paymentSummary);
		holisticViewResponse.setActivityLogs(activityLogs);
		return holisticViewResponse;
	}

	public void setMockForGetHolisticViewDetails()
	{
		try {
			when(iHolisticViewService.getHolisticViewDetails(anyObject())).thenReturn(getHolisticViewResponse());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	
	public HolisticViewResponse  setHolisticViewResponsePaymentInDetail()
	{
		PaymentInSummary paymentInSummary= new PaymentInSummary();
		paymentInSummary.setIsDeleted(false);
		paymentInSummary.setSellCurrency("EUR");
		paymentInSummary.setThirdPartyFlag(false);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		holisticViewResponse.setPaymentInSummary(paymentInSummary);
		return holisticViewResponse;
	}
	public void setMockForGetHolisticViewPaymentInDetails()
	{
		try {
			when(iHolisticViewService.getHolisticViewPaymentInDetails(anyObject())).thenReturn(setHolisticViewResponsePaymentInDetail());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	public HolisticViewResponse setHolisticViewResponsePaymentOutDetail()
	{
		PaymentOutSummary paymentOutSummary = new PaymentOutSummary();
		paymentOutSummary.setBankName("NATIONAL AUSTRALIA BANK LIMITED");
		paymentOutSummary.setBeneficiaryAccountNumber("NATAAU3303M6666666666");
		paymentOutSummary.setBeneficiaryName("ddd dd");
		paymentOutSummary.setBeneficiaryType("Individual");
		paymentOutSummary.setBuyCurrency("AUD");
		paymentOutSummary.setCountryOfBeneficiary("AUS");
		paymentOutSummary.setIsDeleted(false);
		HolisticViewResponse holisticViewResponse= new HolisticViewResponse();
		holisticViewResponse.setPaymentOutSummary(paymentOutSummary);
		return holisticViewResponse;
		
	}
	
	public void setMockForGetHolisticViewPaymentOutDetails()
	{
		try {
			when(iHolisticViewService.getHolisticViewPaymentOutDetails(anyObject())).thenReturn(setHolisticViewResponsePaymentOutDetail());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	public void testGetHolisticViewDetails() {
		setMockForGetHolisticViewDetails();
		Integer contactId=802249;
		 String custType="PFX";
		 String searchCriteria="{\"filter\":{\"sort\":null},\"page\":{\"minRecord\":1,\"maxRecord\":25,\"totalRecords\":718186,\"currentPage\":1,\"totalPages\":28728,\"currentRecord\":4},\"isFilterApply\":true,\"isRequestFromReportPage\":true,\"isFromClearFilter\":false}";
		 String source="report";
		 Integer accountId=654316;
		 ModelAndView modelAndView=	 holisticViewController.getHolisticViewDetails(contactId, custType, searchCriteria, source, accountId, getUser());
		assertEquals("PFXHolisticView",modelAndView.getViewName());
	}

	@Test
	public void testGetHolisticViewPaymentInDetails() {
		setMockForGetHolisticViewPaymentInDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentInId(1314713);
		ModelAndView modelAndView=holisticViewController.getHolisticViewPaymentInDetails(holisticViewRequest);
		assertEquals("HolisticFundsInView",modelAndView.getViewName());
	}

	@Test
	public void testGetHolisticViewPaymentOutDetails() {
		setMockForGetHolisticViewPaymentOutDetails();
		HolisticViewRequest holisticViewRequest= new HolisticViewRequest();
		holisticViewRequest.setPaymentOutId(2797872);
		ModelAndView modelAndView=holisticViewController.getHolisticViewPaymentOutDetails(holisticViewRequest);
		assertEquals("HolisticFundsOutView",modelAndView.getViewName());
	}

}