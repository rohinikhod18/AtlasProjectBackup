package com.currenciesdirect.gtg.compliance.commons.domain.activity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ActivityLogData.
 */
public class ActivityLogData implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The created on. */
	@ApiModelProperty(value = "The created on date", example = "", required = true)
	private String createdOn;

	/** The created by. */
	@ApiModelProperty(value = "The created by name", example = "", required = true)
	private String createdBy;

	/** The activity. */
	@ApiModelProperty(value = "The activity", example = "", required = true)
	private String activity;

	/** The activity type. */
	@ApiModelProperty(value = "The activity type", example = "", required = true)
	private String activityType;
	
	/** The contract number. */
	@ApiModelProperty(value = "The contract number for Payment Logs", example = "", required = false)
	private String contractNumber;

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
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
	 * Gets the activity.
	 *
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * Gets the activity type.
	 *
	 * @return the activity type
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
	 * Sets the activity.
	 *
	 * @param activity
	 *            the new activity
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * Sets the activity type.
	 *
	 * @param activityType
	 *            the new activity type
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

}
