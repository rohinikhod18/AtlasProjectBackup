package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterContactRequest.
 */
public class FraugsterContactRequest extends FraugsterEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The contact id. */
	private Integer contactId;

	/** The is existing. */
	private Boolean isExisting;

	/** Added new Fields. */
	private String previousOfac;

	/** The previous world check. */
	private String previousWorldCheck;

	/** The previous status. */
	private String previousStatus;

	/** The category. */
	private String category;

	/** The id. */
	private Integer id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the checks if is existing.
	 *
	 * @return the checks if is existing
	 */
	public Boolean getIsExisting() {
		return isExisting;
	}

	/**
	 * Sets the checks if is existing.
	 *
	 * @param isExisting
	 *            the new checks if is existing
	 */
	public void setIsExisting(Boolean isExisting) {
		this.isExisting = isExisting;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the previous ofac.
	 *
	 * @return the previous ofac
	 */
	public String getPreviousOfac() {
		return previousOfac;
	}

	/**
	 * Sets the previous ofac.
	 *
	 * @param previousOfac
	 *            the new previous ofac
	 */
	public void setPreviousOfac(String previousOfac) {
		this.previousOfac = previousOfac;
	}

	/**
	 * Gets the previous world check.
	 *
	 * @return the previous world check
	 */
	public String getPreviousWorldCheck() {
		return previousWorldCheck;
	}

	/**
	 * Sets the previous world check.
	 *
	 * @param previousWorldCheck
	 *            the new previous world check
	 */
	public void setPreviousWorldCheck(String previousWorldCheck) {
		this.previousWorldCheck = previousWorldCheck;
	}

	/**
	 * Gets the previous status.
	 *
	 * @return the previous status
	 */
	public String getPreviousStatus() {
		return previousStatus;
	}

	/**
	 * Sets the previous status.
	 *
	 * @param previousStatus
	 *            the new previous status
	 */
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category
	 *            the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FraugsterContactRequest [fraugsterId=");
		builder.append(fraugsterId);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", country=");
		builder.append(country);
		builder.append(", dob=");
		builder.append(dob);
		builder.append(", contactId=");
		builder.append(contactId);
		builder.append(']');
		return builder.toString();
	}

}
