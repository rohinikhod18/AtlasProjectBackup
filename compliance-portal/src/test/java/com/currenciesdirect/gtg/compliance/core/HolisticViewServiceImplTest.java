package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticAccount;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticContact;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentSummary;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class HolisticViewServiceImplTest {

	@InjectMocks
	HolisticViewServiceImpl holisticViewServiceImpl;

	@Mock
	IHolisticViewDBService iHolisticViewDBService;

	@Mock
	IActivityLogDBService iActivityLogDBService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public HolisticViewRequest getHolisticViewRequest() {
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		holisticViewRequest.setAccountId(654316);
		holisticViewRequest.setContactId(802249);
		holisticViewRequest.setCustType("PFX");
		return holisticViewRequest;
	}

	public HolisticViewResponse getHolisticViewResponse() {
		HolisticAccount holisticAccount = new HolisticAccount();
		holisticAccount.setAccSFID("0016E00001BybGoQAJ");
		holisticAccount.setAffiliateName("Unallocated Marketing");
		holisticAccount.setAffiliateNumber("A010069");
		holisticAccount.setBuyingCurrency("GBP");
		HolisticContact holisticContact = new HolisticContact();
		holisticContact.setAccountStatus("INACTIVE");
		holisticContact.setAddress("Po Box 784, York, YO1 0NA, GBR");
		holisticContact.setAddressType("Current Address");
		holisticContact.setAuthorisedSignatory("true");
		holisticContact.setId(802248);
		holisticContact.setJobTitle("Other");
		holisticContact.setName("Kkkkk Kkkk");
		List<HolisticContact> holisticContacts = new ArrayList<>();
		holisticContacts.add(holisticContact);
		HolisticViewResponse holisticViewResponse = new HolisticViewResponse();
		holisticViewResponse.setHolisticAccount(holisticAccount);
		holisticViewResponse.setHolisticContact(holisticContact);
		holisticViewResponse.setHolisticContacts(holisticContacts);
		return holisticViewResponse;
	}

	public PaymentSummary setPaymentSummary() {
		PaymentSummary paymentSummary = new PaymentSummary();
		paymentSummary.setNoOfTrades(0);
		paymentSummary.setTotalSaleAmount(0.0);
		return paymentSummary;
	}

	public ActivityLogs getActivityLog() {
		ActivityLogDataWrapper activityLogDataWrapper = new ActivityLogDataWrapper();
		activityLogDataWrapper.setActivity("KYC - address verification failed");
		activityLogDataWrapper.setActivityType("Signup-STP");
		activityLogDataWrapper.setContractNumber("----");
		activityLogDataWrapper.setCreatedBy("cd.comp.system");
		activityLogDataWrapper.setCreatedOn("17/09/2020 05:46:30");
		List<ActivityLogDataWrapper> activityLogData = new ArrayList<>();
		activityLogData.add(activityLogDataWrapper);
		ActivityLogs activityLogs = new ActivityLogs();
		activityLogs.setActivityLogData(activityLogData);

		return activityLogs;
	}

	public void setMockForGetHolisticViewDetails() {
		HolisticViewResponse holisticViewResponse = new HolisticViewResponse();
		holisticViewResponse.setPaymentSummary(setPaymentSummary());
		try {
			when(iHolisticViewDBService.getHolisticAccountDetails(anyObject())).thenReturn(getHolisticViewResponse());
			when(iHolisticViewDBService.getHolisticPaymentDetails(anyObject(), anyObject()))
					.thenReturn(holisticViewResponse);
			when(iActivityLogDBService.getAllActivityLogs(anyObject())).thenReturn(getActivityLog());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockDataForGetHolisticViewDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iHolisticViewDBService.getHolisticAccountDetails(anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public HolisticViewResponse setHolisticViewResponsePaymentOutDetail() {
		PaymentOutSummary paymentOutSummary = new PaymentOutSummary();
		paymentOutSummary.setBankName("NATIONAL AUSTRALIA BANK LIMITED");
		paymentOutSummary.setBeneficiaryAccountNumber("NATAAU3303M6666666666");
		paymentOutSummary.setBeneficiaryName("ddd dd");
		paymentOutSummary.setBeneficiaryType("Individual");
		paymentOutSummary.setBuyCurrency("AUD");
		paymentOutSummary.setCountryOfBeneficiary("AUS");
		paymentOutSummary.setIsDeleted(false);
		HolisticViewResponse holisticViewResponse = new HolisticViewResponse();
		holisticViewResponse.setPaymentOutSummary(paymentOutSummary);
		return holisticViewResponse;

	}

	public HolisticViewResponse setHolisticViewResponsePaymentInDetail() {
		PaymentInSummary paymentInSummary = new PaymentInSummary();
		paymentInSummary.setIsDeleted(false);
		paymentInSummary.setSellCurrency("EUR");
		paymentInSummary.setThirdPartyFlag(false);
		HolisticViewResponse holisticViewResponse = new HolisticViewResponse();
		holisticViewResponse.setPaymentInSummary(paymentInSummary);
		return holisticViewResponse;
	}

	public void setMockForGetHolisticViewPaymentOutDetails() {
		try {
			when(iHolisticViewDBService.getHolisticPaymentOutDetails(anyObject(), anyObject()))
					.thenReturn(setHolisticViewResponsePaymentOutDetail());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockGetHolisticViewPaymentOutDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iHolisticViewDBService.getHolisticPaymentOutDetails(anyObject(), anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetHolisticViewPaymentInDetails() {
		try {
			when(iHolisticViewDBService.getHolisticPaymentInDetails(anyObject(), anyObject()))
					.thenReturn(setHolisticViewResponsePaymentInDetail());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockGetHolisticViewPaymentInDetails() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iHolisticViewDBService.getHolisticPaymentInDetails(anyObject(), anyObject())).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetHolisticViewPaymentInDetails() {
		setMockForGetHolisticViewPaymentInDetails();
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		holisticViewRequest.setPaymentInId(1314713);
		try {
			HolisticViewResponse expectedResult = setHolisticViewResponsePaymentInDetail();
			HolisticViewResponse actualResult = holisticViewServiceImpl
					.getHolisticViewPaymentInDetails(holisticViewRequest);
			assertEquals(expectedResult.getPaymentInSummary().getIsDeleted(),
					actualResult.getPaymentInSummary().getIsDeleted());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetHolisticViewPaymentInDetails() {
		setMockGetHolisticViewPaymentInDetails();
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		holisticViewRequest.setPaymentInId(1314713);
		try {
			holisticViewServiceImpl.getHolisticViewPaymentInDetails(holisticViewRequest);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetHolisticViewPaymentOutDetails() {
		setMockForGetHolisticViewPaymentOutDetails();
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		holisticViewRequest.setPaymentOutId(2797872);
		try {
			HolisticViewResponse expectedResult = setHolisticViewResponsePaymentOutDetail();
			HolisticViewResponse actualResult = holisticViewServiceImpl
					.getHolisticViewPaymentOutDetails(holisticViewRequest);
			assertEquals(expectedResult.getPaymentOutSummary().getBankName(),
					actualResult.getPaymentOutSummary().getBankName());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetHolisticViewPaymentOutDetails() {
		setMockGetHolisticViewPaymentOutDetails();
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		holisticViewRequest.setPaymentOutId(2797872);
		try {
			holisticViewServiceImpl.getHolisticViewPaymentOutDetails(holisticViewRequest);
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}

	@Test
	public void testGetHolisticViewDetails() {
		setMockForGetHolisticViewDetails();
		try {
			HolisticViewResponse expectesResult = getHolisticViewResponse();
			expectesResult.setActivityLogs(getActivityLog());
			expectesResult.setPaymentSummary(setPaymentSummary());
			HolisticViewResponse actualResult = holisticViewServiceImpl
					.getHolisticViewDetails(getHolisticViewRequest());
			assertEquals(expectesResult.getActivityLogs().getActivityLogData().get(0).getActivity(),
					actualResult.getActivityLogs().getActivityLogData().get(0).getActivity());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetHolisticViewDetails() {
		setMockDataForGetHolisticViewDetails();
		try {
			holisticViewServiceImpl.getHolisticViewDetails(getHolisticViewRequest());
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorDescription(), e.getDescription());
		}
	}
}