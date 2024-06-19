package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FraugsterSummary.
 */

public class FraugsterSummary implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;

	/** The frg trans id. */
	@ApiModelProperty(value = "The Fraugster transaction id", required = true)
	private String frgTransId;

	/** The score. */
	@ApiModelProperty(value = "The score", required = true)
	private String score;

	/** The fraugster approved. */
	@ApiModelProperty(value = "The fraugster approved", required = true)
	private String fraugsterApproved;

	/** The error code. */
	@ApiModelProperty(value = "The error code", required = true)
	private String errorCode;

	/** The error description. */
	@ApiModelProperty(value = "The error description", required = true)
	private String errorDescription;
	
	/** The cd tras id. */
	@ApiModelProperty(value = "The CD transaction ID", required = true)
	private String cdTrasId;
	
	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;
	
	/** The updated by. */
	@ApiModelProperty(value = "The updated by", required = true)
	private String updatedBy;
	
	/** The created on. */
	@ApiModelProperty(value = "The created on date", required = true)
	private String createdOn;
	
	/** The fraugster id. */
	@ApiModelProperty(value = "The fraugster id", required = true)
	private String fraugsterID;
	
	/** The id. */
	@ApiModelProperty(value = "The id", required = true)
	private String id;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * Gets the fraugster ID.
	 *
	 * @return the fraugster ID
	 */
	public String getFraugsterId() {
		return fraugsterID;
	}

	/**
	 * Sets the fraugster ID.
	 *
	 * @param fraugsterID
	 *            the new fraugster ID
	 */
	public void setFraugsterId(String fraugsterID) {
		this.fraugsterID = fraugsterID;
	}
	
	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	
	
	/**
	 * @return eventServiceLogId
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * @param eventServiceLogId
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	/**
	 * @return updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the frg trans id.
	 *
	 * @return the frg trans id
	 */
	public String getFrgTransId() {
		return frgTransId;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public String getScore() {
		return score;
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
	 * Sets the frg trans id.
	 *
	 * @param frgTransId
	 *            the new frg trans id
	 */
	public void setFrgTransId(String frgTransId) {
		this.frgTransId = frgTransId;
	}

	/**
	 * Sets the score.
	 *
	 * @param score
	 *            the new score
	 */
	public void setScore(String score) {
		this.score = score;
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
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the fraugster approved.
	 *
	 * @return the fraugster approved
	 */
	public String getFraugsterApproved() {
		return fraugsterApproved;
	}

	/**
	 * Sets the fraugster approved.
	 *
	 * @param fraugsterApproved
	 *            the new fraugster approved
	 */
	public void setFraugsterApproved(String fraugsterApproved) {
		this.fraugsterApproved = fraugsterApproved;
	}
	
	public String getCdTrasId() {
		return cdTrasId;
	}
	
	public void setCdTrasId(String cdTrasId) {
		this.cdTrasId = cdTrasId;
	}

}
