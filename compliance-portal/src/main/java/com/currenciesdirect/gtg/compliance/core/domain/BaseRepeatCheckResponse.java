package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class BaseRepeatCheckResponse.
 */
public class BaseRepeatCheckResponse {
	
	/** The status. */
	private String status;
	
	/** The success count. */
	private Integer successCount;
	
	/** The fail count. */
	private Integer failCount;
	
	/** The fraugster fail count. */
	private Integer fraugsterFailCount;

	/** The sanction fail count. */
	private Integer sanctionFailCount;

	/** The blacklist fail count. */
	private Integer blacklistFailCount;

	/** The custom check fail count. */
	private Integer customCheckFailCount;

	/** The eid fail count. */
	private Integer eidFailCount;
	
	/** The batch id. */
	private Integer batchId;
	
	/** The total count. */
	private Integer totalCount;
	
	
	/**
	 * Gets the fraugster fail count.
	 *
	 * @return the fraugster fail count
	 */
	public Integer getFraugsterFailCount() {
		return fraugsterFailCount;
	}

	/**
	 * Sets the fraugster fail count.
	 *
	 * @param fraugsterFailCount
	 *            the new fraugster fail count
	 */
	public void setFraugsterFailCount(Integer fraugsterFailCount) {
		this.fraugsterFailCount = fraugsterFailCount;
	}

	/**
	 * Gets the sanction fail count.
	 *
	 * @return the sanction fail count
	 */
	public Integer getSanctionFailCount() {
		return sanctionFailCount;
	}

	/**
	 * Sets the sanction fail count.
	 *
	 * @param sanctionFailCount
	 *            the new sanction fail count
	 */
	public void setSanctionFailCount(Integer sanctionFailCount) {
		this.sanctionFailCount = sanctionFailCount;
	}

	/**
	 * Gets the blacklist fail count.
	 *
	 * @return the blacklist fail count
	 */
	public Integer getBlacklistFailCount() {
		return blacklistFailCount;
	}

	/**
	 * Sets the blacklist fail count.
	 *
	 * @param blacklistFailCount
	 *            the new blacklist fail count
	 */
	public void setBlacklistFailCount(Integer blacklistFailCount) {
		this.blacklistFailCount = blacklistFailCount;
	}

	/**
	 * Gets the custom check fail count.
	 *
	 * @return the custom check fail count
	 */
	public Integer getCustomCheckFailCount() {
		return customCheckFailCount;
	}

	/**
	 * Sets the custom check fail count.
	 *
	 * @param customCheckFailCount
	 *            the new custom check fail count
	 */
	public void setCustomCheckFailCount(Integer customCheckFailCount) {
		this.customCheckFailCount = customCheckFailCount;
	}

	/**
	 * Gets the eid fail count.
	 *
	 * @return the eid fail count
	 */
	public Integer getEidFailCount() {
		return eidFailCount;
	}

	/**
	 * Sets the eid fail count.
	 *
	 * @param eidFailCount
	 *            the new eid fail count
	 */
	public void setEidFailCount(Integer eidFailCount) {
		this.eidFailCount = eidFailCount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the successCount
	 */
	public Integer getSuccessCount() {
		return successCount;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}	

	/**
	 * @return the batchId
	 */
	public Integer getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}