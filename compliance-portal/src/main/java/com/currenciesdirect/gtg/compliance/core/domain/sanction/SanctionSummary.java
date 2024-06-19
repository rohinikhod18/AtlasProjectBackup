package com.currenciesdirect.gtg.compliance.core.domain.sanction;

/**
 * The Class SanctionSummary.
 */
public class SanctionSummary {

	
	private Integer eventServiceLogId;
	
	/** The sanction id. */
	private String sanctionId;
	
	/** The ofac list. */
	private String ofacList;
	
	/** The world check. */
	private String worldCheck;
	
	/** The updated on. */
	private String updatedOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The status. */
	private String status;

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
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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
	 * @param updatedOn the new updated on
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
	 * @param updatedBy the new updated by
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
	 * @param sanctionId the new sanction id
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
	 * @param ofacList the new ofac list
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
	 * @param worldCheck the new world check
	 */
	public void setWorldCheck(String worldCheck) {
		this.worldCheck = worldCheck;
	}


	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}
}
