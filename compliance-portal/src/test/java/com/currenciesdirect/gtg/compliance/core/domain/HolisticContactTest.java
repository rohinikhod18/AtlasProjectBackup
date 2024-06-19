package com.currenciesdirect.gtg.compliance.core.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class HolisticContactTest {

	@Test
	public void testGetAccountStatus() {
		HolisticContact holisticContact= new HolisticContact();
		holisticContact.setAccountStatus("INACTIVE");
		assertEquals("INACTIVE",holisticContact.getAccountStatus());
	}

	@Test
	public void testSetAccountStatus() {
		HolisticContact holisticContacts= new HolisticContact();
		holisticContacts.setAccountStatus("INACTIVE");
		assertEquals("INACTIVE",holisticContacts.getAccountStatus());
	}

}