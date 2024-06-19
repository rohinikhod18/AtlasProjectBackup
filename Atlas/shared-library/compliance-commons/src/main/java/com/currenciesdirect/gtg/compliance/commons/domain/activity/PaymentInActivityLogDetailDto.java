package com.currenciesdirect.gtg.compliance.commons.domain.activity;

import java.sql.Timestamp;

/**
 * The Class PaymentInActivityLogDetailDto.
 */
public class PaymentInActivityLogDetailDto {
	
	/** The id. */
	private Integer id;
	
	/** The activity id. */
	private Integer activityId;
	
	/** The activity type. */
	private ActivityType activityType;
	
	/** The log. */
	private String log;
	
	/** The created by. */
	private String createdBy;
	
	/** The created on. */
	private Timestamp createdOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated on. */
	private Timestamp updatedOn;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the activity id.
	 *
	 * @return the activity id
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * Gets the activity type.
	 *
	 * @return the activity type
	 */
	public ActivityType getActivityType() {
		return activityType;
	}

	/**
	 * Gets the log.
	 *
	 * @return the log
	 */
	public String getLog() {
		return log;
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
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
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
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the activity id.
	 *
	 * @param activityId the new activity id
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * Sets the activity type.
	 *
	 * @param activityType the new activity type
	 */
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	/**
	 * Sets the log.
	 *
	 * @param log the new log
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn the new updated on
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	

}
