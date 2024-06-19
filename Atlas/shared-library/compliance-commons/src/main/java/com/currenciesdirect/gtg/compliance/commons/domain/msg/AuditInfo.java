package com.currenciesdirect.gtg.compliance.commons.domain.msg;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class AuditInfo.
 */
public class AuditInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created on. */
	private Date createdOn;

	/** The updated on. */
	private Date updatedOn;

	/**
	 * Instantiates a new audit info.
	 */
	public AuditInfo() {
		this.createdOn = new java.util.Date();
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn the new updated on
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}