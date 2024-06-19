package com.currenciesdirect.gtg.compliance.core.domain.blacklist;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class BaseBlackListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The created by. */
	protected String createdBy;

	/** The created on. */
	protected Timestamp createdOn;

	/** The updated by. */
	protected String updatedBy;

	/** The updated on. */
	protected Timestamp updatedOn;

	/** The correlation id. */
	protected UUID correlationId;
	
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
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy
	 *            the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public UUID getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId
	 *            the new correlation id
	 */
	public void setCorrelationId(UUID correlationId) {
		this.correlationId = correlationId;
	}
}
