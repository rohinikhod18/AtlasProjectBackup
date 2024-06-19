package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import io.swagger.annotations.ApiModelProperty;

public class OnfidoSummary {
	
	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;
	
	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;
	
	/** The onfido id. */
	@ApiModelProperty(value = "The onfido id", required = true)
	private String onfidoId;
	
	/** The reviewed. */
	private String reviewed;
	
	/** The updated on. */
	private String updatedOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The onfido report. */
	private OnfidoReport onfidoReport;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the eventServiceLogId
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * @param eventServiceLogId the eventServiceLogId to set
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	/**
	 * @return the onfidoId
	 */
	public String getOnfidoId() {
		return onfidoId;
	}

	/**
	 * @param onfidoId the onfidoId to set
	 */
	public void setOnfidoId(String onfidoId) {
		this.onfidoId = onfidoId;
	}

	/**
	 * @return the reviewed
	 */
	public String getReviewed() {
		return reviewed;
	}

	/**
	 * @param reviewed the reviewed to set
	 */
	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	/**
	 * @return the updatedOn
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the onfidoReport
	 */
	public OnfidoReport getOnfidoReport() {
		return onfidoReport;
	}

	/**
	 * @param onfidoReport the onfidoReport to set
	 */
	public void setOnfidoReport(OnfidoReport onfidoReport) {
		this.onfidoReport = onfidoReport;
	}

}
