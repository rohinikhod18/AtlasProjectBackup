package com.currenciesdirect.gtg.compliance.core.domain;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class Sanction.
 */
public class Sanction implements IDomain {

	private Integer eventServiceLogId;
	
	private String entityId;
	
	/** The entity type. */
	private String entityType;

	/** The updated on. */
	private String updatedOn;

	/** The updated by. */
	private String updatedBy;

	/** The sanction id. */
	private String sanctionId;

	/** The ofac list. */
	private String ofacList;

	/** The world check. */
	private String worldCheck;

	/** The status. */
	private Boolean status;

	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;

	/** The sanction total records. */
	private Integer sanctionTotalRecords;
	
	//added by Vishal J to check whether Sanction needs to check
	/** Requirement of Sanction check.*/
	private Boolean isRequired;
	
	//added by Vishal J to store what is status of response
	/** Value of status (PASS, FAIL, NOT_REQUIRED etc.) */
	private String statusValue;
	
	private String prevStatusValue;
	
	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
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
	 * Gets the sanction id.
	 *
	 * @return the sanction id
	 */
	public String getSanctionId() {
		return sanctionId;
	}

	/**
	 * Sets the sanction id.
	 *
	 * @param sanctionId
	 *            the new sanction id
	 */
	public void setSanctionId(String sanctionId) {
		this.sanctionId = sanctionId;
	}

	/**
	 * Gets the ofac list.
	 *
	 * @return the ofac list
	 */
	public String getOfacList() {
		return ofacList;
	}

	/**
	 * Sets the ofac list.
	 *
	 * @param ofacList
	 *            the new ofac list
	 */
	public void setOfacList(String ofacList) {
		this.ofacList = ofacList;
	}

	/**
	 * Gets the world check.
	 *
	 * @return the world check
	 */
	public String getWorldCheck() {
		return worldCheck;
	}

	/**
	 * Sets the world check.
	 *
	 * @param worldCheck
	 *            the new world check
	 */
	public void setWorldCheck(String worldCheck) {
		this.worldCheck = worldCheck;
	}

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

	/**
	 * Gets the pass count.
	 *
	 * @return the pass count
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 *
	 * @param passCount
	 *            the new pass count
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 *
	 * @return the fail count
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 *
	 * @param failCount
	 *            the new fail count
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}


	public String getEntityType() {
		return entityType;
	}


	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}


	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}


	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	public Integer getSanctionTotalRecords() {
		return sanctionTotalRecords;
	}

	public void setSanctionTotalRecords(Integer sanctionTotalRecords) {
		this.sanctionTotalRecords = sanctionTotalRecords;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	//added by Vishal J to check whether Sanction needs to check
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
