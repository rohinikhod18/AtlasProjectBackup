package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DataAnonymisationResponseTest {

	@Test
	public void testGetCode() {
		DataAnonymisationResponse dataAnonymisationResponse =new DataAnonymisationResponse();
		dataAnonymisationResponse.setCode("CDA001");
		assertTrue(dataAnonymisationResponse.getCode().equalsIgnoreCase("CDA001"));
	}

	@Test
	public void testSetCode() {
		DataAnonymisationResponse dataAnonymisationResponse1 =new DataAnonymisationResponse();
		dataAnonymisationResponse1.setCode("CDA001");
		assertTrue(dataAnonymisationResponse1.getCode().equalsIgnoreCase("CDA001"));
	}

	@Test
	public void testGetStatus() {
		DataAnonymisationResponse dataAnonymisationResponse =new DataAnonymisationResponse();
		dataAnonymisationResponse.setStatus("RECEIVED");
		assertTrue(dataAnonymisationResponse.getStatus().equalsIgnoreCase("RECEIVED"));
	}
	
	@Test
	public void testSetStatus() {
		DataAnonymisationResponse dataAnonymisationResponse1 =new DataAnonymisationResponse();
		dataAnonymisationResponse1.setStatus("RECEIVED");
		assertTrue(dataAnonymisationResponse1.getStatus().equalsIgnoreCase("RECEIVED"));
	}

	@Test
	public void testGetSystem() {
		DataAnonymisationResponse dataAnonymisationResponse =new DataAnonymisationResponse();
		dataAnonymisationResponse.setSystem("ATLAS");
		assertTrue(dataAnonymisationResponse.getSystem().equalsIgnoreCase("ATLAS"));
	}

	@Test
	public void testSetSystem() {
		DataAnonymisationResponse dataAnonymisationResponse1 =new DataAnonymisationResponse();
		dataAnonymisationResponse1.setSystem("ATLAS");
		assertTrue(dataAnonymisationResponse1.getSystem().equalsIgnoreCase("ATLAS"));
	}

	@Test
	public void testGetDescription() {
		DataAnonymisationResponse dataAnonymisationResponse =new DataAnonymisationResponse();
		dataAnonymisationResponse.setDescription("RECEIVED and PROCESS");
		assertTrue(dataAnonymisationResponse.getDescription().equalsIgnoreCase("RECEIVED and PROCESS"));
	}

	@Test
	public void testSetDescription() {
		DataAnonymisationResponse dataAnonymisationResponse1 =new DataAnonymisationResponse();
		dataAnonymisationResponse1.setDescription("RECEIVED and PROCESS");
		assertTrue(dataAnonymisationResponse1.getDescription().equalsIgnoreCase("RECEIVED and PROCESS"));
	}

}
