/*package com.currenciesdirect.gtg.compliance.kyc.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

public class KYCServiceImplTest {

	@InjectMocks
	KYCServiceImpl kYCServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public KYCProviderRequest kYCProviderRequestMocking() {
		KYCProviderRequest kYCProviderRequest = new KYCProviderRequest();
		kYCProviderRequest.setSourceApplication("Atlas");
		kYCProviderRequest.setRequestType("individual");
		kYCProviderRequest.setOrgCode("TorFXOz");
		KYCContactRequest kYCContactRequest = new KYCContactRequest();
		List<KYCContactRequest> contact = new ArrayList<>();
		kYCContactRequest.setContactSFId("0030O000029F9O1QAK");
		Address address = new Address();
		address.setAreaNumber("British");
		address.setPostCode("V2Z 2T7");
		address.setAza("Langley");
		address.setBuildingName("48 Ave");
		address.setBuildingNumber("de8764");
		address.setAddressLine1("2204 Glencair");
		address.setCountry("NOR");
		address.setCity("city");
		address.setState("State/Province");
		address.setStreet("gtd24");
		kYCContactRequest.setAddress(address);
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setForeName("Chadsley");
		personalDetails.setSurName("Atkins");
		personalDetails.setDob("1996-09-19");
		kYCContactRequest.setPersonalDetails(personalDetails);
		contact.add(kYCContactRequest);
		kYCProviderRequest.setAccountSFId("0010O00001v2ntLQAQ");
		kYCProviderRequest.setContact(contact);
		kYCProviderRequest.setCorrelationID(UUID.randomUUID());
	
		return kYCProviderRequest;
		
	}
	public KYCProviderResponse getResponseForCDSA()
	{
		KYCProviderResponse response= new KYCProviderResponse();
		response.setContactResponse(getContactResponse());
		response.setOrgCode("CD SA");
		response.setAccountId("0010O00001v2ntLQAQ");
		response.setSourceApplication("Atlas");
		return response;
	}
	public KYCProviderResponse getResponseForCDLEU()
	{
		KYCProviderResponse response= new KYCProviderResponse();
		response.setContactResponse(getContactResponseCDLEU());
		response.setOrgCode("CD SA");
		response.setAccountId("0010O00001v2ntLQAQ");
		response.setSourceApplication("Atlas");
		return response;
	}
	public KYCProviderResponse getResponseWithError()
	{
		KYCProviderResponse response= new KYCProviderResponse();
		response.setContactResponse(getContactResponseWithError());
		response.setOrgCode("TorFXOz");
		response.setAccountId("0010O00001v2ntLQAQ");
		response.setSourceApplication("Atlas");
		return response;
	}
	public List<KYCContactResponse> getContactResponse()
	{
		KYCContactResponse contactResponse = new KYCContactResponse();
		List<KYCContactResponse> contactResponses= new ArrayList<>();
		contactResponse.setContactSFId("0030O000029F9O1QAK");
		contactResponse.setStatus(Constants.NOT_REQUIRED);
		contactResponse.setOverallScore(Constants.NOT_AVAILABLE);
		contactResponses.add(contactResponse);
		return contactResponses;
	}
	public List<KYCContactResponse> getContactResponseCDLEU()
	{
		KYCContactResponse contactResponse = new KYCContactResponse();
		List<KYCContactResponse> contactResponses= new ArrayList<>();
		contactResponse.setContactSFId("0030O000029F9O1QAK");
		contactResponse.setStatus(Constants.NOT_REQUIRED);
		contactResponse.setOverallScore(Constants.NOT_AVAILABLE);
		contactResponse.setBandText(Constants.EU_LE_POI_NEEDED);
		contactResponses.add(contactResponse);
		return contactResponses;
	}
	public List<KYCContactResponse> getContactResponseWithError()
	{
		KYCContactResponse contactResponse = new KYCContactResponse();
		List<KYCContactResponse> contactResponses= new ArrayList<>();
		contactResponse.setContactSFId("0030O000029F9O1QAK");
		contactResponse.setStatus(Constants.NOT_REQUIRED);
		contactResponse.setOverallScore(Constants.NOT_AVAILABLE);
		contactResponse.setErrorCode("KYC0005");
		contactResponse.setErrorDescription("Country is not supported");
		contactResponses.add(contactResponse);
		return contactResponses;
	}
	
	@Test
	public void testCheckKYC() {
		KYCProviderRequest request = kYCProviderRequestMocking();
		KYCProviderResponse expectedResult=getResponseWithError();
		try {
			KYCProviderResponse response=kYCServiceImpl.checkKYC(request);
			assertEquals(expectedResult.getContactResponse().get(0).getErrorCode(),response.getContactResponse().get(0).getErrorCode());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testCheckKYCCDSA() {
		KYCProviderRequest request = kYCProviderRequestMocking();
		request.setOrgCode("CD SA");
		KYCProviderResponse expectedResponse=getResponseForCDSA();
		try {
			KYCProviderResponse response=kYCServiceImpl.checkKYC(request);
			assertEquals(expectedResponse.getAccountSFId(),response.getAccountSFId());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testCheckKYCCDLEU() {
		KYCProviderRequest request = kYCProviderRequestMocking();
		request.setLegalEntity("CDLEU");
		KYCProviderResponse expectedResponse=getResponseForCDLEU();
		try {
			KYCProviderResponse response=kYCServiceImpl.checkKYC(request);
			assertEquals(expectedResponse.getContactResponse().get(0).getBandText(),response.getContactResponse().get(0).getBandText());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	
}*/
