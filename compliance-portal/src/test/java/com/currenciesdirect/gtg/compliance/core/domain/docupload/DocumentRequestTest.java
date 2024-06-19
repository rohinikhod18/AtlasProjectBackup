package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DocumentRequestTest {

	@Test
	public void testGetWithDetails() {
		DocumentRequest documentRequest =new DocumentRequest();
		documentRequest.setWithDetails(true);
		assertTrue(documentRequest.getWithDetails().equals(true));
	}

	@Test
	public void testSetWithDetails() {
		DocumentRequest documentRequest1 =new DocumentRequest();
		documentRequest1.setWithDetails(true);
		assertTrue(documentRequest1.getWithDetails().equals(true));
	}

	@Test
	public void testGetCrmAccountID() {
		DocumentRequest documentRequest =new DocumentRequest();
		documentRequest.setCrmAccountID("0032000001mftMXAAY");
		assertTrue(documentRequest.getCrmAccountID().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testSetCrmAccountID() {
		DocumentRequest documentRequest1 =new DocumentRequest();
		documentRequest1.setCrmAccountID("0032000001mftMXAAY");
		assertTrue(documentRequest1.getCrmAccountID().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testGetCrmContactID() {
		DocumentRequest documentRequest =new DocumentRequest();
		documentRequest.setCrmContactID("0032000001mftMXAAY");
		assertTrue(documentRequest.getCrmContactID().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testSetCrmContactID() {
		DocumentRequest documentRequest1 =new DocumentRequest();
		documentRequest1.setCrmContactID("0032000001mftMXAAY");
		assertTrue(documentRequest1.getCrmContactID().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testGetOrgCode() {
		DocumentRequest documentRequest =new DocumentRequest();
		documentRequest.setOrgCode("torFX");
		assertTrue(documentRequest.getOrgCode().equalsIgnoreCase("torFX"));
	}

	@Test
	public void testSetOrgCode() {
		DocumentRequest documentRequest1 =new DocumentRequest();
		documentRequest1.setOrgCode("torFX");
		assertTrue(documentRequest1.getOrgCode().equalsIgnoreCase("torFX"));
	}

	@Test
	public void testGetSource() {
		DocumentRequest documentRequest =new DocumentRequest();
		documentRequest.setSource("atlas");
		assertTrue(documentRequest.getSource().equalsIgnoreCase("atlas"));
	}

	@Test
	public void testSetSource() {
		DocumentRequest documentRequest1 =new DocumentRequest();
		documentRequest1.setSource("atlas");
		assertTrue(documentRequest1.getSource().equalsIgnoreCase("atlas"));
	}

}
