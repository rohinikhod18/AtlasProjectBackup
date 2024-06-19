package com.currenciesdirect.gtg.compliance.sanction.core.domain;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class SanctionGetStatusResponseTest {

	@Test
	public void testGetApplicationId() {
		SanctionGetStatusResponse sanctionGetStatusResponse = new SanctionGetStatusResponse();
		sanctionGetStatusResponse.setApplicationId("CLNTS");
		assertTrue(sanctionGetStatusResponse.getApplicationId().equalsIgnoreCase("CLNTS"));
	}

	@Test
	public void testSetApplicationId() {
		SanctionGetStatusResponse sanctionGetStatusResponse1 = new SanctionGetStatusResponse();
		sanctionGetStatusResponse1.setApplicationId("CLNTS");
		assertTrue(sanctionGetStatusResponse1.getApplicationId().equalsIgnoreCase("CLNTS"));
	}

	@Test
	public void testGetId() {
		SanctionGetStatusResponse sanctionGetStatusResponse = new SanctionGetStatusResponse();
		sanctionGetStatusResponse.setId(31659556);
		assertTrue(sanctionGetStatusResponse.getId().equals(31659556));
	}

	@Test
	public void testSetId() {
		SanctionGetStatusResponse sanctionGetStatusResponse1 = new SanctionGetStatusResponse();
		sanctionGetStatusResponse1.setId(31659556);
		assertTrue(sanctionGetStatusResponse1.getId().equals(31659556));
	}
	
	@Test
	public void testGetErrorCode() {
		SanctionGetStatusResponse sanctionGetStatusResponse = new SanctionGetStatusResponse();
		sanctionGetStatusResponse.setErrorCode("9999");
		assertTrue(sanctionGetStatusResponse.getErrorCode().equals("9999"));
	}

	@Test
	public void testSetErrorCode() {
		SanctionGetStatusResponse sanctionGetStatusResponse1 = new SanctionGetStatusResponse();
		sanctionGetStatusResponse1.setErrorCode("9999");
		assertTrue(sanctionGetStatusResponse1.getErrorCode().equals("9999"));

	}

	@Test
	public void testGetErrorDescription() {
		SanctionGetStatusResponse sanctionGetStatusResponse = new SanctionGetStatusResponse();
		sanctionGetStatusResponse.setErrorDescription("Service Error");
		assertTrue(sanctionGetStatusResponse.getErrorDescription().equals("Service Error"));
	}

	@Test
	public void testSetErrorDescription() {
		SanctionGetStatusResponse sanctionGetStatusResponse1 = new SanctionGetStatusResponse();
		sanctionGetStatusResponse1.setErrorDescription("Service Error");
		assertTrue(sanctionGetStatusResponse1.getErrorDescription().equals("Service Error"));
	}

}
