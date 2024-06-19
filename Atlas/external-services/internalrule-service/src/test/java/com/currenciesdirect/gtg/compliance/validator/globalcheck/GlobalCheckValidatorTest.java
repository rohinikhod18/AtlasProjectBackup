package com.currenciesdirect.gtg.compliance.validator.globalcheck;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;

public class GlobalCheckValidatorTest {

	@InjectMocks
	GlobalCheckValidator globalCheckValidator;
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
	}
	public InternalServiceRequestData getInternalServiceRequestData()
	{
		InternalServiceRequestData internalServiceRequestData= new InternalServiceRequestData();
		internalServiceRequestData.setState("Western Australia");
		
		return internalServiceRequestData;
		
	}
	@Test
	public void testGlobalCheckValidator() {
		boolean actualResult=globalCheckValidator.globalCheckValidator(getInternalServiceRequestData());
		assertEquals(Boolean.TRUE,actualResult);
	}

}
