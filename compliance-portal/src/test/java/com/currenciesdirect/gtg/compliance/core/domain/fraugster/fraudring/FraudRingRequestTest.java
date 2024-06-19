package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import static org.junit.Assert.*;

import org.junit.Test;

import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonAccount;

@SuppressWarnings("squid:S1192")
public class FraudRingRequestTest {

	@Test
	public void testGetCrmContactId() {
		FraudRingRequest fraudRingRequest =new FraudRingRequest();
		fraudRingRequest.setCrmContactId("00120000013ao4HAAQ");
	    assertTrue(fraudRingRequest.getCrmContactId().equals("00120000013ao4HAAQ"));
	}

	@Test
	public void testSetCrmContactId() {
		FraudRingRequest fraudRingRequest1 =new FraudRingRequest();
		fraudRingRequest1.setCrmContactId("00120000013ao4HAAQ");
	    assertTrue(fraudRingRequest1.getCrmContactId().equals("00120000013ao4HAAQ"));
	}

}
