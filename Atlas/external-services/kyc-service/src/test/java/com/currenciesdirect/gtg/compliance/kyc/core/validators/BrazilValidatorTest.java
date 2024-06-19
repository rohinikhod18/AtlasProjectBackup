package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

public class BrazilValidatorTest {
	
	@InjectMocks
	BrazilValidator brazilValidator;
	
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
	address.setCountry("Nor");
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
	public void testValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMocking();
		FieldValidator validator;
		try {
			validator=brazilValidator.validateKYCRequest(request);
			assertEquals(validator.getErrors(),validator.getErrors());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}

}
