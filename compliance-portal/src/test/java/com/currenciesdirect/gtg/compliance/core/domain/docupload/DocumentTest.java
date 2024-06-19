package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DocumentTest {

	@Test
	public void testGetCreatedBy() {
		Document document =new Document();
		document.setCreatedBy("2");
		assertTrue(document.getCreatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testSetCreatedBy() {
		Document document1 =new Document();
		document1.setCreatedBy("2");
		assertTrue(document1.getCreatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testGetDocumentName() {
		Document document =new Document();
		document.setDocumentName("National ID Card");
		assertTrue(document.getDocumentName().equalsIgnoreCase("National ID Card"));
	}

	@Test
	public void testSetDocumentName() {
		Document document1 =new Document();
		document1.setDocumentName("National ID Card");
		assertTrue(document1.getDocumentName().equalsIgnoreCase("National ID Card"));
	}

	@Test
	public void testGetDocumentType() {
		Document document =new Document();
		document.setDocumentType("PID");
		assertTrue(document.getDocumentType().equalsIgnoreCase("PID"));
	}

	@Test
	public void testSetDocumentType() {
		Document document1 =new Document();
		document1.setDocumentType("PID");
		assertTrue(document1.getDocumentType().equalsIgnoreCase("PID"));
	}

	@Test
	public void testGetNote() {
		Document document =new Document();
		document.setNote("national id card");
		assertTrue(document.getNote().equalsIgnoreCase("national id card"));
	}

	@Test
	public void testSetNote() {
		Document document1 =new Document();
		document1.setNote("national id card");
		assertTrue(document1.getNote().equalsIgnoreCase("national id card"));
	}

	@Test
	public void testGetUrl() {
		Document document =new Document();
		document.setUrl("Base Enterprise URL");
		assertTrue(document.getUrl().equalsIgnoreCase("Base Enterprise URL"));
	}

	@Test
	public void testSetUrl() {
		Document document1 =new Document();
		document1.setUrl("Base Enterprise URL");
		assertTrue(document1.getUrl().equalsIgnoreCase("Base Enterprise URL"));
	}

	@Test
	public void testGetCrmContactID() {
		Document document =new Document();
		document.setUrl("0032000001mftMXAAY");
		assertTrue(document.getUrl().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testSetCrmContactID() {
		Document document1 =new Document();
		document1.setCrmContactID("0032000001mftMXAAY");
		assertTrue(document1.getCrmContactID().equalsIgnoreCase("0032000001mftMXAAY"));
	}

	@Test
	public void testGetCategory() {
		Document document =new Document();
		document.setCategory("PID");
		assertTrue(document.getCategory().equalsIgnoreCase("PID"));
	}

	@Test
	public void testSetCategory() {
		Document document1 =new Document();
		document1.setCategory("PID");
		assertTrue(document1.getCategory().equalsIgnoreCase("PID"));
	}

	@Test
	public void testGetAuthorized() {
		Document document =new Document();
		document.setAuthorized(true);
		assertTrue(document.getAuthorized().equals(true));
	}

	@Test
	public void testSetAuthorized() {
		Document document1 =new Document();
		document1.setAuthorized(true);
		assertTrue(document1.getAuthorized().equals(true));
	}

	@Test
	public void testGetDocumentId() {
		Document document =new Document();
		document.setDocumentId("541785");
		assertTrue(document.getDocumentId().equalsIgnoreCase("541785"));
	}

	@Test
	public void testSetDocumentId() {
		Document document1 =new Document();
		document1.setDocumentId("541785");
		assertTrue(document1.getDocumentId().equalsIgnoreCase("541785"));
	}

}
