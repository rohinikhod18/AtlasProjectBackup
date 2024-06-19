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

public class AustriaValidatorTest {

	@InjectMocks
	AustriaValidator austriaValidator;
	
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
		personalDetails.setForeName("Chadsley");
		personalDetails.setDob("1996-09-19");
		kYCContactRequest.setPersonalDetails(personalDetails);
		return kYCContactRequest;
		}
	
	@Test
	public void testValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMocking();
		FieldValidator validate=new FieldValidator();
		try {
			FieldValidator	validator=austriaValidator.validateKYCRequest(request);
			assertEquals(validate.getErrors(),validator.getErrors());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForValidateKYCRequest() {
		KYCContactRequest request = kYCContactRequestMock();
		FieldValidator validate=new FieldValidator();
		validate.addError("can not be blank","last_name");
		try {
			FieldValidator	validator=austriaValidator.validateKYCRequest(request);
			assertEquals(validate.getErrors().get(0).getErrorMessage(),validator.getErrors().get(0).getErrorMessage());
		} catch (KYCException e) {
			System.out.println(e);
		}
	}

}
