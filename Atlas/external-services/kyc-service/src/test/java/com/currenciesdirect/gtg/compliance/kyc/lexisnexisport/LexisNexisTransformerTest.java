package com.currenciesdirect.gtg.compliance.kyc.lexisnexisport;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.seisint.webservices.WsIdentity.InstantIDIntl2Request;


public class LexisNexisTransformerTest {
	
	@InjectMocks
	LexisNexisTransformer lexisNexisTransformer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	public KYCContactRequest kYCContactRequestMocking() {
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
	address.setCountry("Canada");
	address.setCity("city");
	address.setState("State/Province");
	address.setStreet("gtd24");
	kYCContactRequest.setAddress(address);
	
	PersonalDetails personalDetails = new PersonalDetails();
	personalDetails.setForeName("Chadsley");
	personalDetails.setSurName("Atkins");
	personalDetails.setDob("1996-09-19");
	kYCContactRequest.setPersonalDetails(personalDetails);
	return kYCContactRequest;
	}
	
	@Test
	public void testTransformRequestObject() {
		InstantIDIntl2Request idIntl2Request;
		KYCContactRequest kYCContactRequest = kYCContactRequestMocking();
		try {
			idIntl2Request =	lexisNexisTransformer.transformRequestObject(kYCContactRequest);
			assertEquals(idIntl2Request.getUser(),idIntl2Request.getUser());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}

}
