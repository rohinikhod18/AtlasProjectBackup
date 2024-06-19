package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

/**
 * The Class DocumentInfo.
 */
public class DocumentInfo {

	/** The document name. */
	private String documentName;

	/** The type. */
	private String type;

	/** The note. */
	private String note;

	/** The created by. */
	private String createdBy;

	/** The crated on. */
	private Timestamp cratedOn;

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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * Gets the crated on.
	 *
	 * @return the crated on
	 */
	public Timestamp getCratedOn() {
		return cratedOn;
	}

	/**
	 * Sets the crated on.
	 *
	 * @param cratedOn
	 *            the new crated on
	 */
	public void setCratedOn(Timestamp cratedOn) {
		this.cratedOn = cratedOn;
	}
}
