package com.currenciesdirect.gtg.compliance.kyc.core.validators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

public class CanadaValidatorTest {

	@InjectMocks
	CanadaValidator canadaValidator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public KYCContactRequest kYCContactRequestMocking() {
	KYCContactRequest kYCContactRequest = new KYCContactRequest();
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
	
	public KYCContactRequest kYCContactRequestMock() {
		KYCContactRequest kYCContactRequest = new KYCContactRequest();
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
		personalDetails.setDob("1996-09-19");
		kYCContactRequest.setPersonalDetails(personalDetails);
		return kYCContactRequest;
		}
	
	@Test
	public void testValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMocking();
		FieldValidator validator=new FieldValidator();
		try {
			FieldValidator	validator1=canadaValidator.validateKYCRequest(request);
			assertEquals(validator.getErrors(),validator1.getErrors());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMock();
		FieldValidator validator=new FieldValidator();
	validator.addError("can not be blank", "first_name, last_name");
		try {
			FieldValidator	validator1=canadaValidator.validateKYCRequest(request);
			assertEquals(validator.getErrors().get(0).getErrorMessage(),validator1.getErrors().get(0).getErrorMessage());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}

}
