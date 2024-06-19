package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class Fraugster.
 */
public class Fraugster implements IDomain {

	/** The id. */
	private Integer id;

	/** The created on. */
	private String createdOn;

	/** The updated by. */
	private String updatedBy;

	/** The fraugster id. */
	private String fraugsterId;

	/** The score. */
	private String score;
	
	/** The fraugster total records. */
	private Integer fraugsterTotalRecords;

	/**added by Vishal J to check whether we need to do fraugster check*/
	private Boolean isRequired;

	/**added by Vishal J to get the status of fraugster check*/
	private String statusValue;
	
	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;
	
	/** The status. */
	private Boolean status;
	
	private String prevStatusValue;
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
		
	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
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
	 * Gets the fraugster id.
	 *
	 * @return the fraugster id
	 */
	public String getFraugsterId() {
		return fraugsterId;
	}

	/**
	 * Sets the fraugster id.
	 *
	 * @param fraugsterId
	 *            the new fraugster id
	 */
	public void setFraugsterId(String fraugsterId) {
		this.fraugsterId = fraugsterId;
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
	 * Sets the score.
	 *
	 * @param score
	 *            the new score
	 */
	public void setScore(String score) {
		this.score = score;
	}

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
	 * @return the fraugsterTotalRecords
	 */
	public Integer getFraugsterTotalRecords() {
		return fraugsterTotalRecords;
	}

	/**
	 * @param fraugsterTotalRecords the fraugsterTotalRecords to set
	 */
	public void setFraugsterTotalRecords(Integer fraugsterTotalRecords) {
		this.fraugsterTotalRecords = fraugsterTotalRecords;
	}
	
	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Gets the status value.
	 *
	 * @return the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the status value.
	 *
	 * @param statusValue the new status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * Gets the prev status value.
	 *
	 * @return the prev status value
	 */
	public String getPrevStatusValue() {
		return prevStatusValue;
	}

	/**
	 * Sets the prev status value.
	 *
	 * @param prevStatusValue the new prev status value
	 */
	public void setPrevStatusValue(String prevStatusValue) {
		this.prevStatusValue = prevStatusValue;
	}

	
}
