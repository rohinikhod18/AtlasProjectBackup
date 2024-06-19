package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class SanctionSummary.
 */
public class SanctionSummary extends BaseResponse{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;
	
	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;
	
	/** The sanction id. */
	@ApiModelProperty(value = "The sanction id", required = true)
	private String sanctionId;
	
	/** The ofac list. */
	@ApiModelProperty(value = "The ofac list", required = true)
	private String ofacList;
	
	/** The world check. */
	@ApiModelProperty(value = "The world check", required = true)
	private String worldCheck;
	
	/** The updated on. */
	@ApiModelProperty(value = "The updated on", required = true)
	private String updatedOn;
	
	/** The updated by. */
	@ApiModelProperty(value = "The updated by", required = true)
	private String updatedBy;
	
	/** The provider name. */
	@ApiModelProperty(value = "The provider name", required = true)
	private String providerName;
	
	/** The provider method. */
	@ApiModelProperty(value = "The provider method", required = true)
	private String providerMethod;
	
	
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
	
	/**
	 * Gets the event service log id.
	 *
	 * @return the event service log id
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}
	
	/**
	 * Sets the event service log id.
	 *
	 * @param eventServiceLogId the new event service log id
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * Gets the provider method.
	 *
	 * @return the provider method
	 */
	public String getProviderMethod() {
		return providerMethod;
	}

	/**
	 * Sets the provider method.
	 *
	 * @param providerMethod the new provider method
	 */
	public void setProviderMethod(String providerMethod) {
		this.providerMethod = providerMethod;
	}
}
