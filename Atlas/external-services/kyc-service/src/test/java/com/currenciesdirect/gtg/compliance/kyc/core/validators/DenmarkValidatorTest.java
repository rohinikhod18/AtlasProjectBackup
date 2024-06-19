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
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;

public class DenmarkValidatorTest {

	@InjectMocks
	DenmarkValidator denmarkValidator;
	
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
		address.setState("State/Province");
		address.setStreet("gtd24");
		kYCContactRequest.setAddress(address);
		
		return kYCContactRequest;
		}
	@Test
	public void testValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMocking();
		FieldValidator validator1= new FieldValidator();
		try {
			FieldValidator validator=denmarkValidator.validateKYCRequest(request);
			assertEquals(validator1.getErrors(),validator.getErrors());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMock();
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setForeName("Chadsley");
		personalDetails.setSurName("Atkins");
		personalDetails.setDob("1996-09-19");
		request.setPersonalDetails(personalDetails);
		FieldValidator validator1= new FieldValidator();
		validator1.addError("can not be blank","city");
		try {
			FieldValidator validator=denmarkValidator.validateKYCRequest(request);
			assertEquals(validator1.getErrors().get(0).getErrorMessage(),validator.getErrors().get(0).getErrorMessage());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForErrorValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMock();
		FieldValidator validator1= new FieldValidator();
		validator1.addError(Constants.VALIDATION_ERROR, "");
		try {
			FieldValidator validator=denmarkValidator.validateKYCRequest(request);
			assertEquals(validator1.getErrors().get(0).getErrorMessage(),validator.getErrors().get(0).getErrorMessage());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
}
