package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class SocialDataRequestTest {
	
	@Test
	public void testGetEmailId() {
		SocialDataRequest socialDataRequest = new SocialDataRequest();
		socialDataRequest.setEmailId("salvatore@mailinator.com");
	    assertTrue(socialDataRequest.getEmailId().equalsIgnoreCase("salvatore@mailinator.com"));
	}
	
	@Test
	public void testSetEmailId() {
		SocialDataRequest socialDataRequest1 = new SocialDataRequest();
		socialDataRequest1.setEmailId("salvatore@mailinator.com");
	    assertTrue(socialDataRequest1.getEmailId().equalsIgnoreCase("salvatore@mailinator.com"));
	}
}
