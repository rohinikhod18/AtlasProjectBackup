package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Document.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

	/** The created on. */
	private Timestamp createdOn;

	/** The created by. */
	private String createdBy;

	/** The document name. */
	private String documentName;

	/** The document type. */
	private String documentType;

	/** The note. */
	private String note;

	/** The url. */
	private String url;
	
	/** The crm contact ID. */
	private String crmContactID;
	
	private String category;
	
	private Boolean authorized;
	
	/** The document id. */
	@JsonProperty("document_id")
	private String documentId;

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the document name.
	 *
	 * @return the document name
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * Sets the document name.
	 *
	 * @param documentName
	 *            the new document name
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * Gets the document type.
	 *
	 * @return the document type
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * Sets the document type.
	 *
	 * @param documentType
	 *            the new document type
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the new note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the crm contact ID.
	 *
	 * @return the crm contact ID
	 */
	public String getCrmContactID() {
		return crmContactID;
	}

	/**
	 * Sets the crm contact ID.
	 *
	 * @param crmContactID the new crm contact ID
	 */
	public void setCrmContactID(String crmContactID) {
		this.crmContactID = crmContactID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}
	/**
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	
	public String getDocumentId() {
		return documentId;
	}
	/**
	 * Sets the document id.
	 *
	 * @param document id
	 *          
	 */
	
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}


	@Override
	public String toString() {
		return "Document [createdOn=" + createdOn + ", createdBy=" + createdBy + ", documentName=" + documentName
				+ ", documentType=" + documentType + ", note=" + note + ", url=" + url + ", crmContactID="
				+ crmContactID + ", category=" + category + ", authorized=" + authorized + "]";
	}

	
}
