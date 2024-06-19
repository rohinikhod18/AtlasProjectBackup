package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class FxTicketRequestPayloadTest {

	@Test
	public void testGetTitanAccountNumber() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setTitanAccountNumber("0202000002600209");
		assertEquals("0202000002600209",fxTicketRequestPayload.getTitanAccountNumber());
	}

	@Test
	public void testSetTitanAccountNumber() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setTitanAccountNumber("0202000002600209");
		assertEquals("0202000002600209",fxTicketRequestPayload1.getTitanAccountNumber());

	}

	@Test
	public void testGetOrganizationCode() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setOrganizationCode("TorFx");
		assertEquals("TorFx",fxTicketRequestPayload.getOrganizationCode());
	}

	@Test
	public void testSetOrganizationCode() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setOrganizationCode("TorFx");
		assertEquals("TorFx",fxTicketRequestPayload1.getOrganizationCode());

	}

	@Test
	public void testGetFxStatus() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setFxStatus("CLOSED");
		assertEquals("CLOSED",fxTicketRequestPayload.getFxStatus());
	}

	@Test
	public void testSetFxStatus() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setFxStatus("CLOSED");
		assertEquals("CLOSED",fxTicketRequestPayload1.getFxStatus());
	}

	@Test
	public void testGetFxTicketId() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setFxTicketId("0201000001164125");
		assertEquals("0201000001164125",fxTicketRequestPayload.getFxTicketId());
	}

	@Test
	public void testSetFxTicketId() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setFxTicketId("0201000001164125");
		assertEquals("0201000001164125",fxTicketRequestPayload1.getFxTicketId());
	}

	@Test
	public void testGetRequestSource() {
		FxTicketRequestPayload fxTicketRequestPayload= new FxTicketRequestPayload();
		fxTicketRequestPayload.setRequestSource("atlas");
		assertEquals("atlas",fxTicketRequestPayload.getRequestSource());
	}

	@Test
	public void testSetRequestSource() {
		FxTicketRequestPayload fxTicketRequestPayload1= new FxTicketRequestPayload();
		fxTicketRequestPayload1.setRequestSource("atlas");
		assertEquals("atlas",fxTicketRequestPayload1.getRequestSource());
	}

}