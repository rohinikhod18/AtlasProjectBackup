package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import static org.junit.Assert.*;
import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DataAnonAccountTest {	
	
	@Test
	public void testGetTradeAccountNumber() {
		DataAnonAccount dataAnonAccount =new DataAnonAccount();
		dataAnonAccount.setTradeAccountNumber("0201000006579483");
	    assertTrue(dataAnonAccount.getTradeAccountNumber().equalsIgnoreCase("0201000006579483"));
	}

	@Test
	public void testSetTradeAccountNumber() {
		DataAnonAccount dataAnonAccount1 =new DataAnonAccount();
		dataAnonAccount1.setTradeAccountNumber("0201000006579483");
	    assertTrue(dataAnonAccount1.getTradeAccountNumber().equalsIgnoreCase("0201000006579483"));
	}

	@Test
	public void testGetTradeAccountID() {
		DataAnonAccount dataAnonAccount =new DataAnonAccount();
		dataAnonAccount.setTradeAccountID(30502);
	    assertTrue(dataAnonAccount.getTradeAccountID().equals(30502));
	}

	@Test
	public void testSetTradeAccountID() {
		DataAnonAccount dataAnonAccount1 =new DataAnonAccount();
		dataAnonAccount1.setTradeAccountID(30502);
	    assertTrue(dataAnonAccount1.getTradeAccountID().equals(30502));
	}

	@Test
	public void testGetAccountSFID() {
		DataAnonAccount dataAnonAccount =new DataAnonAccount();
		dataAnonAccount.setAccountSFID("00120000013ao4HAAQ");
	    assertTrue(dataAnonAccount.getAccountSFID().equalsIgnoreCase("00120000013ao4HAAQ"));
	}

	@Test
	public void testSetAccountSFID() {
		DataAnonAccount dataAnonAccount1 =new DataAnonAccount();
		dataAnonAccount1.setAccountSFID("00120000013ao4HAAQ");
	    assertTrue(dataAnonAccount1.getAccountSFID().equalsIgnoreCase("00120000013ao4HAAQ"));
	}

	@Test
	public void testGetContact() {
		DataAnonContact dataAnonContact = new DataAnonContact();
		dataAnonContact.setContactSfId("0032000001U0o1ZAAR");
		assertTrue(dataAnonContact.getContactSfId().equalsIgnoreCase("0032000001U0o1ZAAR"));
	}

	@Test
	public void testSetContact() {
		DataAnonContact dataAnonContact1 = new DataAnonContact();
		dataAnonContact1.setContactSfId("0032000001U0o1ZAAR");
		assertTrue(dataAnonContact1.getContactSfId().equalsIgnoreCase("0032000001U0o1ZAAR"));
	}
}
