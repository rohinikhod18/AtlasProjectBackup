package com.currenciesdirect.gtg.compliance.sanction.core.validator;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;

public class SanctionValidatorTest {

	@InjectMocks
	SanctionValidator sanctionValidator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public SanctionRequest requestMocking() {
		SanctionRequest sanctionRequest = new SanctionRequest();
		sanctionRequest.setCustomerType("PFX");
		SanctionContactRequest sanctionContactRequest = new SanctionContactRequest();
		List<SanctionContactRequest> contactrequests = new ArrayList<>();
		sanctionContactRequest.setContactId(6638905);
		sanctionContactRequest.setPreviousOfac("Not Required");
		sanctionContactRequest.setFullName("Damon Salvatore");
		sanctionContactRequest.setPreviousWorldCheck("Not Required");
		sanctionContactRequest.setSanctionId("002-C-0000053920");
		contactrequests.add(sanctionContactRequest);
		sanctionRequest.setContactrequests(contactrequests);
		return sanctionRequest;
	}
	
	public SanctionRequest getSanctionRequest()
	{
		SanctionRequest sanctionRequest =requestMocking();
		List<SanctionBankRequest> bankRequests= new ArrayList<>();
		SanctionBankRequest sanctionBankRequest= new SanctionBankRequest();
		bankRequests.add(sanctionBankRequest);
		sanctionRequest.setBankRequests(bankRequests);
		return sanctionRequest;
	}
	@Test
	public void testForValidateRequest() {
		try {
		SanctionRequest sanctionRequest = requestMocking();
		FieldValidator fieldValidator1=new FieldValidator();
		FieldValidator fieldValidator =sanctionValidator.validateRequest(sanctionRequest);
		assertEquals(fieldValidator1.getErrors(),fieldValidator.getErrors());
		}
		catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	@Test
	public void testValidateRequest() {
		try {
		SanctionRequest sanctionRequest = getSanctionRequest();
		FieldValidator fieldValidator1=new FieldValidator();
		fieldValidator1.addError("can not be blank","bank country, bank sanctionID, bank name");
		FieldValidator fieldValidator =sanctionValidator.validateRequest(sanctionRequest);
		assertEquals(fieldValidator1.getErrors().get(0).getErrorMessage(),fieldValidator.getErrors().get(0).getErrorMessage());
		}
		catch(Exception e) {
			System.out.println(e);
		}	
	}

}
