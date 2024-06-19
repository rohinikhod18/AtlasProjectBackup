
package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("squid:S1192")
public class DocumentDtoTest {

	@Test
	public void testGetDocumentId() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setDocumentId("001mftMXA");
		assertTrue(documentDto.getDocumentId().equalsIgnoreCase("001mftMXA"));
	}

	@Test
	public void testSetDocumentId() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setDocumentId("001mftMXA");
		assertTrue(documentDto1.getDocumentId().equalsIgnoreCase("001mftMXA"));
	}

	@Test
	public void testGetCreatedOn() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setCreatedOn("2017-04-04 04:42:26");
		assertTrue(documentDto.getCreatedOn().equalsIgnoreCase("2017-04-04 04:42:26"));
	}

	@Test
	public void testSetCreatedOn() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setCreatedOn("2017-04-04 04:42:26");
		assertTrue(documentDto1.getCreatedOn().equalsIgnoreCase("2017-04-04 04:42:26"));
	}

	@Test
	public void testGetCreatedBy() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setCreatedBy("2");
		assertTrue(documentDto.getCreatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testSetCreatedBy() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setCreatedBy("2");
		assertTrue(documentDto1.getCreatedBy().equalsIgnoreCase("2"));
	}

	@Test
	public void testGetDocumentName() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setDocumentName("National ID Card");
		assertTrue(documentDto.getDocumentName().equalsIgnoreCase("National ID Card"));
	}

	@Test
	public void testSetDocumentName() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setDocumentName("National ID Card");
		assertTrue(documentDto1.getDocumentName().equalsIgnoreCase("National ID Card"));
	}

	@Test
	public void testGetDocumentType() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setDocumentType("PID");
		assertTrue(documentDto.getDocumentType().equalsIgnoreCase("PID"));
	}

	@Test
	public void testSetDocumentType() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setDocumentType("PID");
		assertTrue(documentDto1.getDocumentType().equalsIgnoreCase("PID"));
	}

	@Test
	public void testGetNote() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setDocumentName("National ID Card");
		assertTrue(documentDto.getDocumentName().equalsIgnoreCase("National ID Card"));
	}

	@Test
	public void testSetNote() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setNote("national id card");
		assertTrue(documentDto1.getNote().equalsIgnoreCase("national id card"));
	}

	@Test
	public void testGetUrl() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setUrl("Base Enterprise URL");
		assertTrue(documentDto.getUrl().equalsIgnoreCase("Base Enterprise URL"));
	}

	@Test
	public void testSetUrl() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setUrl("Base Enterprise URL");
		assertTrue(documentDto1.getUrl().equalsIgnoreCase("Base Enterprise URL"));
	}

	@Test
	public void testGetAuthorized() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setAuthorized(true);
		assertTrue(documentDto.getAuthorized().equals(true));
	}

	@Test
	public void testSetAuthorized() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setAuthorized(true);
		assertTrue(documentDto1.getAuthorized().equals(true));
	}

	@Test
	public void testGetCategory() {
		DocumentDto documentDto =new DocumentDto();
		documentDto.setCategory("PID");
		assertTrue(documentDto.getCategory().equals("PID"));
	}

	@Test
	public void testSetCategory() {
		DocumentDto documentDto1 =new DocumentDto();
		documentDto1.setCategory("PID");
		assertTrue(documentDto1.getCategory().equals("PID"));
	}

}

