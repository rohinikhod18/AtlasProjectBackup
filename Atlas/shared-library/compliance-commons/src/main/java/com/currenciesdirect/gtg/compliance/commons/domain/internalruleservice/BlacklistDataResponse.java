package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

/**
 * The Class BlacklistDataResponse.
 */
public class BlacklistDataResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The blacklist type. */
	private String blacklistType;
	
	/** The created on date. */
	private String createdOnDate;
	
	/** The value. */
	private String value;
	
	/** The notes. */
	private String notes;

	/**
	 * @return the blacklistType
	 */
	public String getBlacklistType() {
		return blacklistType;
	}

	/**
	 * @param blacklistType the blacklistType to set
	 */
	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}

	/**
	 * @return the createdOnDate
	 */
	public String getCreatedOnDate() {
		return createdOnDate;
	}

	/**
	 * @param createdOnDate the createdOnDate to set
	 */
	public void setCreatedOnDate(String createdOnDate) {
		this.createdOnDate = createdOnDate;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
