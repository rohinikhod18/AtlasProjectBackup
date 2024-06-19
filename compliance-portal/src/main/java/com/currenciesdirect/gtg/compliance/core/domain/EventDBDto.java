package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

/**
 * The Class EventDBDto.
 */
public class EventDBDto {

	private Integer id;
	
	private String entityId;
	
	private String eitityType;
	
	private String serviceType;

	/** The summary. */
	private String summary;

	/** The updated on. */
	private Timestamp updatedOn;

	/** The updated by. */
	private String updatedBy;

	/** The status. */
	private String status;
	
	//AT-4962
	/** The intuition current action. */ 
	private String intuitionCurrentAction;

	
	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary
	 *            the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEitityType() {
		return eitityType;
	}

	public void setEitityType(String eitityType) {
		this.eitityType = eitityType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getIntuitionCurrentAction() {
		return intuitionCurrentAction;
	}

	public void setIntuitionCurrentAction(String intuitionCurrentAction) {
		this.intuitionCurrentAction = intuitionCurrentAction;
	}
}
