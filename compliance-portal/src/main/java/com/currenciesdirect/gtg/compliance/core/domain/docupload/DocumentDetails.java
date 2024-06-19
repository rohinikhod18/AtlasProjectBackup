package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class DocumentDetails.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDetails {

	/** List of URLs. */
	private List<String> urlList;

	/** The document list. */
	private List<Document> documentList;

	/**
	 * The Error Code.
	 */
	private String errorCode;

	/** The Error Description. */
	private String errorDescription;

	/**
	 * Gets the document list.
	 *
	 * @return the document list
	 */
	public List<Document> getDocumentList() {
		return documentList;
	}

	/**
	 * Sets the document list.
	 *
	 * @param documentList
	 *            the new document list
	 */
	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}

	/**
	 * Gets the url list.
	 *
	 * @return the urlList
	 */
	public List<String> getUrlList() {
		return urlList;
	}

	/**
	 * Sets the url list.
	 *
	 * @param urlList
	 *            the urlList to set
	 */
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}