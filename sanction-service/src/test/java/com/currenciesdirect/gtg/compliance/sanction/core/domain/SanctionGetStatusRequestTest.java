package com.currenciesdirect.gtg.compliance.sanction.core.domain;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class SanctionGetStatusRequestTest {

	@Test
	public void testGetApplicationId() {
		SanctionGetStatusRequest sanctionGetStatusRequest = new SanctionGetStatusRequest();
		sanctionGetStatusRequest.setApplicationId("CLNTS");
		assertTrue(sanctionGetStatusRequest.getApplicationId().equalsIgnoreCase("CLNTS"));
	}

	@Test
	public void testSetApplicationId() {
		SanctionGetStatusRequest sanctionGetStatusRequest1 = new SanctionGetStatusRequest();
		sanctionGetStatusRequest1.setApplicationId("CLNTS");
		assertTrue(sanctionGetStatusRequest1.getApplicationId().equalsIgnoreCase("CLNTS"));
	}

	@Test
	public void testGetId() {
		SanctionGetStatusRequest sanctionGetStatusRequest = new SanctionGetStatusRequest();
		sanctionGetStatusRequest.setId(31659556);
		assertTrue(sanctionGetStatusRequest.getId().equals(31659556));
	}

	@Test
	public void testSetId() {
		SanctionGetStatusRequest sanctionGetStatusRequest1 = new SanctionGetStatusRequest();
		sanctionGetStatusRequest1.setId(31659556);
		assertTrue(sanctionGetStatusRequest1.getId().equals(31659556));
	}

}
