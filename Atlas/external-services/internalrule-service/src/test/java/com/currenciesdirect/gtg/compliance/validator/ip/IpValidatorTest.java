package com.currenciesdirect.gtg.compliance.validator.ip;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;



public class IpValidatorTest {
	

	@InjectMocks
	IpValidator ipValidator;
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
	}
	public IpServiceRequest getIpServiceRequest()
	{
		IpServiceRequest ipServiceRequest= new IpServiceRequest();
		ipServiceRequest.setCountry("United KingDom");
		ipServiceRequest.setIpAddress("172.31.22.43");
		return ipServiceRequest;
		
	}
	@Test
	public void testValidate() {
		try {
			Boolean	actualResult = ipValidator.validate(getIpServiceRequest());
			assertEquals(Boolean.TRUE,actualResult);
		} catch (IpException e) {
			System.out.println(e);
		}		
	}

	@Test
	public void testForValidate() {
		IpServiceRequest ipServiceRequest=null;
		try {
			ipValidator.validate(ipServiceRequest);	
		} catch (Exception e) {
			assertEquals("com.currenciesdirect.gtg.compliance.exception.ip.IpException: IP1002",e.getMessage());
		}		
	}
}
