package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataAnonymizationServiceRequestTest {

	@Test
	public void testGetOrgCode() {
		DataAnonymizationServiceRequest dataAnonymizationServiceRequest = new DataAnonymizationServiceRequest();
		dataAnonymizationServiceRequest.setOrgCode("Currencies Direct");
		assertTrue(dataAnonymizationServiceRequest.getOrgCode().equalsIgnoreCase("Currencies Direct"));
	}

	@Test
	public void testSetOrgCode() {
		DataAnonymizationServiceRequest dataAnonymizationServiceRequest1 = new DataAnonymizationServiceRequest();
		dataAnonymizationServiceRequest1.setOrgCode("Currencies Direct");
		assertTrue(dataAnonymizationServiceRequest1.getOrgCode().equalsIgnoreCase("Currencies Direct"));
	}

	@Test
	public void testGetAccount() {
		DataAnonymizationServiceRequest dataAnonymizationServiceRequest = new DataAnonymizationServiceRequest();
		DataAnonAccount dataAnonAccount = new DataAnonAccount();
		dataAnonAccount.setTradeAccountID(301000006);
		dataAnonymizationServiceRequest.setAccount(dataAnonAccount);
		assertTrue(dataAnonAccount.getTradeAccountID().equals(301000006));
	}

	@Test
	public void testSetAccount() {
		DataAnonymizationServiceRequest dataAnonymizationServiceRequest1 = new DataAnonymizationServiceRequest();
		DataAnonAccount dataAnonAccount = new DataAnonAccount();
		dataAnonAccount.setTradeAccountID(301000006);
		dataAnonymizationServiceRequest1.setAccount(dataAnonAccount);
		assertTrue(dataAnonAccount.getTradeAccountID().equals(301000006));
	}

}
