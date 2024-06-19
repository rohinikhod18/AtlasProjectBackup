package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DataAnonContactTest {

	@Test
	public void testGetTradeContactId() {
		DataAnonContact dataAnonContact= new DataAnonContact();
		dataAnonContact.setTradeContactId(585717);
	    assertTrue(dataAnonContact.getTradeContactId().equals(585717));
	}

	@Test
	public void testSetTradeContactId() {
		DataAnonContact dataAnonContact1= new DataAnonContact();
		dataAnonContact1.setTradeContactId(585717);
	    assertTrue(dataAnonContact1.getTradeContactId().equals(585717));
	}

	@Test
	public void testGetContactSfId() {
		DataAnonContact dataAnonContact= new DataAnonContact();
		dataAnonContact.setContactSfId("0032000001mftMXAAY");
	    assertTrue(dataAnonContact.getContactSfId().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testSetContactSfId() {
		DataAnonContact dataAnonContact1= new DataAnonContact();
		dataAnonContact1.setContactSfId("0032000001mftMXAAY");
	    assertTrue(dataAnonContact1.getContactSfId().equalsIgnoreCase("0032000001mftMXAAY"));
	}

}
