package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class FraugsterSummaryTest {

	@Test
	public void testGetId() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setId("7485598");
		assertTrue(fraugsterSummary.getId().equalsIgnoreCase("7485598"));
	}

	@Test
	public void testSetId() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setId("7485598");
		assertTrue(fraugsterSummary1.getId().equalsIgnoreCase("7485598"));
	}

	@Test
	public void testGetFraugsterId() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setFraugsterId("95632b95-b9a1-4617-8e1c-37f97b9a10a9");
		assertTrue(fraugsterSummary.getFraugsterId().equalsIgnoreCase("95632b95-b9a1-4617-8e1c-37f97b9a10a9"));
	}

	@Test
	public void testSetFraugsterId() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setFraugsterId("95632b95-b9a1-4617-8e1c-37f97b9a10a9");
		assertTrue(fraugsterSummary1.getFraugsterId().equalsIgnoreCase("95632b95-b9a1-4617-8e1c-37f97b9a10a9"));
	}

	@Test
	public void testGetCreatedOn() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setCreatedOn("16/09/2020 11:21:35");
		assertTrue(fraugsterSummary.getCreatedOn().equalsIgnoreCase("16/09/2020 11:21:35"));
	}

	@Test
	public void testSetCreatedOn() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setCreatedOn("16/09/2020 11:21:35");
		assertTrue(fraugsterSummary1.getCreatedOn().equalsIgnoreCase("16/09/2020 11:21:35"));
	}

	@Test
	public void testGetEventServiceLogId() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setEventServiceLogId(3659451);
		assertTrue(fraugsterSummary.getEventServiceLogId().equals(3659451));
	}

	@Test
	public void testSetEventServiceLogId() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setEventServiceLogId(3659451);
		assertTrue(fraugsterSummary1.getEventServiceLogId().equals(3659451));
	}

	@Test
	public void testGetUpdatedBy() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setUpdatedBy("2");
		assertTrue(fraugsterSummary.getUpdatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testSetUpdatedBy() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setUpdatedBy("2");
		assertTrue(fraugsterSummary.getUpdatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testGetFrgTransId() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setFrgTransId( "95632b95-b9a1-4617-8e1c-37f97b9a10a9");
		assertTrue(fraugsterSummary.getFrgTransId().equalsIgnoreCase( "95632b95-b9a1-4617-8e1c-37f97b9a10a9"));
	}

	@Test
	public void testSetFrgTransId() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setFrgTransId( "95632b95-b9a1-4617-8e1c-37f97b9a10a9");
		assertTrue(fraugsterSummary1.getFrgTransId().equalsIgnoreCase( "95632b95-b9a1-4617-8e1c-37f97b9a10a9"));
	}
	
	@Test
	public void testGetScore() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setScore("0.0015080875");
		assertTrue(fraugsterSummary.getScore().equalsIgnoreCase("0.0015080875"));
	}
	
	@Test
	public void testSetScore() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setScore("0.0015080875");
		assertTrue(fraugsterSummary1.getScore().equalsIgnoreCase("0.0015080875"));
	}

	@Test
	public void testGetStatus() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setStatus("PASS");
		assertTrue(fraugsterSummary.getStatus().equalsIgnoreCase("PASS"));
	}

	@Test
	public void testSetStatus() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setStatus("PASS");
		assertTrue(fraugsterSummary1.getStatus().equalsIgnoreCase("PASS"));
	}

	@Test
	public void testGetErrorCode() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setErrorCode("555");
		assertTrue(fraugsterSummary.getErrorCode().equalsIgnoreCase("555"));
	}

	@Test
	public void testSetErrorCode() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setErrorCode("--");
		assertTrue(fraugsterSummary1.getErrorCode().equalsIgnoreCase("--"));
	}

	@Test
	public void testGetErrorDescription() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setErrorDescription("--");
		assertTrue(fraugsterSummary.getErrorDescription().equalsIgnoreCase("--"));
	}

	@Test
	public void testSetErrorDescription() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setErrorDescription("--");
		assertTrue(fraugsterSummary1.getErrorDescription().equalsIgnoreCase("--"));
	}

	@Test
	public void testGetFraugsterApproved() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setFraugsterApproved("YES");
		assertTrue(fraugsterSummary.getFraugsterApproved().equalsIgnoreCase("YES"));
	}

	@Test
	public void testSetFraugsterApproved() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setFraugsterApproved("YES");
		assertTrue(fraugsterSummary1.getFraugsterApproved().equalsIgnoreCase("YES"));
	}

	@Test
	public void testGetCdTrasId() {
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		fraugsterSummary.setCdTrasId("805784826");
		assertTrue(fraugsterSummary.getCdTrasId().equalsIgnoreCase("805784826"));
	}

	@Test
	public void testSetCdTrasId() {
		FraugsterSummary fraugsterSummary1 = new FraugsterSummary();
		fraugsterSummary1.setCdTrasId("805784826");
		assertTrue(fraugsterSummary1.getCdTrasId().equalsIgnoreCase("805784826"));
	}

}
