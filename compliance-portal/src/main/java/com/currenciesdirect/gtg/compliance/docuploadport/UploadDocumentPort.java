package com.currenciesdirect.gtg.compliance.docuploadport;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.docupload.Document;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentDetails;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentDto;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class UploadDocumentPort.
 */
public class UploadDocumentPort {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(UploadDocumentPort.class);

	/**
	 * Instantiates a new upload document port.
	 */
	private UploadDocumentPort() {
	}

	/**
	 * Gets the attached document.
	 *
	 * @param crmAccountId
	 *            the crm account id
	 * @param crmContactId
	 *            the crm contact id
	 * @param orgCode
	 *            the org code
	 * @param source
	 *            the source
	 * @return the attached document
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public static List<Document> getAttachedDocument(String crmAccountId, String crmContactId, String orgCode,
			String source) throws CompliancePortalException {
		DocumentRequest documentRequest = getDocumentRequest(crmAccountId, crmContactId, orgCode, source);
		HttpClientPool clientPool = HttpClientPool.getInstance();
		try {
			String baseUrl = System.getProperty("baseEnterpriseUrl");
			List<Document> documents = clientPool
					.sendRequest(baseUrl + "/es-restfulinterface/rest/documentService/acquireFiles", "POST",
							JsonConverterUtil.convertToJsonWithoutNull(documentRequest), DocumentDetails.class, null,
							MediaType.TEXT_PLAIN_TYPE)
					.getDocumentList();
			if (documents == null) {
				return new ArrayList<>();
			}
			return documents;
		} catch (Exception e) {
			log.error("Exception: {1} ", e);
		}
		return new ArrayList<>();
	}

	/**
	 * Gets the document request.
	 *
	 * @param crmAccountId
	 *            the crm account id
	 * @param crmContactId
	 *            the crm contact id
	 * @param orgCode
	 *            the org code
	 * @param source
	 *            the source
	 * @return the document request
	 */
	private static DocumentRequest getDocumentRequest(String crmAccountId, String crmContactId, String orgCode,
			String source) {
		if (crmAccountId == null || crmContactId == null || orgCode == null || source == null) {
			return null;
		}
		DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setCrmAccountID(crmAccountId);
		documentRequest.setCrmContactID(crmContactId);
		documentRequest.setOrgCode(orgCode);
		documentRequest.setWithDetails(Boolean.FALSE);
		documentRequest.setSource(source);
		return documentRequest;
	}

	/**
	 * Update document date format.
	 *
	 * @param documents
	 *            the documents
	 * @return the list
	 */
	public static List<DocumentDto> updateDocumentDateFormat(List<Document> documents) {

		List<DocumentDto> documentList = new ArrayList<>();
		for (Document document : documents) {
			DocumentDto documentDto = new DocumentDto();
			documentDto.setCreatedBy(document.getCreatedBy());
			documentDto.setDocumentName(document.getDocumentName());
			if (isNullOrEmpty(document.getDocumentType())) {
				documentDto.setDocumentType("-");
			} else {
				documentDto.setDocumentType(document.getDocumentType());
			}
			if (isNullOrEmpty(document.getNote())) {
				documentDto.setNote("-");
			} else {
				documentDto.setNote(document.getNote());
			}
			documentDto.setUrl(document.getUrl());
			documentDto.setCreatedOn(DateTimeFormatter.dateTimeFormatter(document.getCreatedOn()));
			documentDto.setAuthorized(document.getAuthorized());
			documentDto.setCategory(document.getCategory());
			documentDto.setDocumentId(document.getDocumentId());
			documentList.add(documentDto);
		}

		return documentList;
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	private static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}
}
