package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class FxTicketDetailRequestListTest {

	@Test
	public void testGetFxTicketPayload() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setTitanAccountNumber("");
		fxTicketRequestPayload.setOrganizationCode("");
		FxTicketDetailRequestList fxTicketDetailRequestList= new FxTicketDetailRequestList();
		fxTicketDetailRequestList.setFxTicketPayload(fxTicketRequestPayload);
		assertEquals(fxTicketRequestPayload.getTitanAccountNumber(), fxTicketDetailRequestList.getFxTicketPayload().getTitanAccountNumber());
	}

	@Test
	public void testSetFxTicketPayload() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setTitanAccountNumber("");
		fxTicketRequestPayload1.setOrganizationCode("");
		FxTicketDetailRequestList fxTicketDetailRequestList= new FxTicketDetailRequestList();
		fxTicketDetailRequestList.setFxTicketPayload(fxTicketRequestPayload1);
		assertEquals(fxTicketRequestPayload1.getTitanAccountNumber(), fxTicketDetailRequestList.getFxTicketPayload().getTitanAccountNumber());

	}

}