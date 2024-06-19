package com.currenciesdirect.gtg.compliance.fraugster.core.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;


public class FraugsterValidatorTest {
	
	@InjectMocks
	FraugsterValidator fraugsterValidator;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public FraugsterSignupContactRequest fraugsterSignupContactRequestMocking() {
		FraugsterSignupContactRequest fraugsterSignupContactRequest = new FraugsterSignupContactRequest();
		fraugsterSignupContactRequest.setEventType("Profile new Registration");
		fraugsterSignupContactRequest.setCustID("432918");
		fraugsterSignupContactRequest.setTransactionID("0201000000304748");
		return fraugsterSignupContactRequest;	
	}
	
	public FraugsterSignupContactRequest fraugsterSignupContactRequestMock() {
		FraugsterSignupContactRequest fraugsterSignupContactRequest = new FraugsterSignupContactRequest();
		fraugsterSignupContactRequest.setCustID("432918");
		fraugsterSignupContactRequest.setTransactionID("0201000000304748");
		return fraugsterSignupContactRequest;
	}
	public FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequestMocking() {
		FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequest = new FraugsterOnUpdateContactRequest();
		fraugsterOnUpdateContactRequest.setEventType("update");
		fraugsterOnUpdateContactRequest.setCustID("432918");
		fraugsterOnUpdateContactRequest.setTransactionID("0201000000304748");
		return fraugsterOnUpdateContactRequest;
	}
	
	public FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequestMock() {
		FraugsterOnUpdateContactRequest fraugsterOnUpdateContactRequest = new FraugsterOnUpdateContactRequest();
		fraugsterOnUpdateContactRequest.setCustID("432918");
		fraugsterOnUpdateContactRequest.setTransactionID("0201000000304748");
		return fraugsterOnUpdateContactRequest;
	}
	
	public FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequestMocking() {
		FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequest = new FraugsterPaymentsOutContactRequest();
		fraugsterPaymentsOutContactRequest.setEventType("Payment Out");
		fraugsterPaymentsOutContactRequest.setCustID("432918");
		fraugsterPaymentsOutContactRequest.setTransactionID("0201000000304748");
		return fraugsterPaymentsOutContactRequest;
	}
	
	public FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequestMock() {
		FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequest = new FraugsterPaymentsOutContactRequest();
		fraugsterPaymentsOutContactRequest.setCustID("432918");
		fraugsterPaymentsOutContactRequest.setTransactionID("0201000000304748");
		return fraugsterPaymentsOutContactRequest;
	}
	
	public FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequestMocking() {
		FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequest = new FraugsterPaymentsInContactRequest();
		fraugsterPaymentsInContactRequest.setEventType("Payment Out");
		fraugsterPaymentsInContactRequest.setCustID("432918");
		fraugsterPaymentsInContactRequest.setTransactionID("0201000000304748");
		return fraugsterPaymentsInContactRequest;
	}
	public FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequestMock() {
		FraugsterPaymentsInContactRequest fraugsterPaymentsInContactRequest = new FraugsterPaymentsInContactRequest();
		fraugsterPaymentsInContactRequest.setCustID("432918");
		fraugsterPaymentsInContactRequest.setTransactionID("0201000000304748");
		return fraugsterPaymentsInContactRequest;
	}
	@Test
	public void testValidateFraugsterSignupRequest() {
		Boolean expectedIsvalidate = true;
		Boolean	isvalidate= fraugsterValidator.validateFraugsterSignupRequest(fraugsterSignupContactRequestMocking());
		assertEquals(expectedIsvalidate,isvalidate);
	}
	@Test
	public void testForValidateFraugsterSignupRequest() {
		Boolean expectedIsvalidate = false;
		Boolean	isvalidate= fraugsterValidator.validateFraugsterSignupRequest(fraugsterSignupContactRequestMock());
		assertEquals(expectedIsvalidate,isvalidate);
	}
	@Test
	public void testValidateFraugsterOnUpdateRequest() {
		Boolean expectedIsvalidate = true;
		Boolean	isvalidate= fraugsterValidator.validateFraugsterOnUpdateRequest(fraugsterOnUpdateContactRequestMocking());
		assertEquals(expectedIsvalidate,isvalidate);
	}

	@Test
	public void testForValidateFraugsterOnUpdateRequest() {
		Boolean expectedIsvalidate = false;
		Boolean	isvalidate= fraugsterValidator.validateFraugsterOnUpdateRequest(fraugsterOnUpdateContactRequestMock());
		assertEquals(expectedIsvalidate,isvalidate);
	}
	@Test
	public void testValidateFraugsterPaymentsOutRequest() {
		Boolean expectedIsvalidate = true;
		FraugsterPaymentsOutContactRequest fraugsterPaymentsOutContactRequest= fraugsterPaymentsOutContactRequestMocking();
		Boolean	isvalidate= fraugsterValidator.validateFraugsterPaymentsOutRequest(fraugsterPaymentsOutContactRequest);
		assertEquals(expectedIsvalidate,isvalidate);
	}

	@Test
	public void testForValidateFraugsterPaymentsOutRequest() {
		Boolean expectedIsvalidate = false;
		Boolean	isvalidate= fraugsterValidator.validateFraugsterPaymentsOutRequest(fraugsterPaymentsOutContactRequestMock());
		assertEquals(expectedIsvalidate,isvalidate);
	}
	@Test
	public void testValidateFraugsterPaymentsInRequest() throws FraugsterException {
		Boolean expectedIsvalidate = true;
		Boolean	isvalidate = fraugsterValidator.validateFraugsterPaymentsInRequest(fraugsterPaymentsInContactRequestMocking());
		assertEquals(expectedIsvalidate,isvalidate);
	}
	@Test
	public void testForValidateFraugsterPaymentsInRequest() throws FraugsterException {
		Boolean expectedIsvalidate = false;
		Boolean	isvalidate = fraugsterValidator.validateFraugsterPaymentsInRequest(fraugsterPaymentsInContactRequestMock());
		assertEquals(expectedIsvalidate,isvalidate);
	}
}
