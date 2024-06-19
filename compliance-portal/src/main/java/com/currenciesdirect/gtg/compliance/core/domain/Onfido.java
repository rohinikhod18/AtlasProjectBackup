package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

public class Onfido implements IDomain{
	
	/** The event service log id. */
	private Integer eventServiceLogId;
	
	private String entityId;
	
	/** The entity type. */
	private String entityType;
	
	/** The id. */
	private String onfidoId;
	
	/** The updated on. */
	private String updatedOn;

	/** The updated by. */
	private String updatedBy;
	
	/** The reviewed. */
	private String reviewed;
	
	/** The status. */
	private Boolean status;
	
	/** The sanction total records. */
	private Integer onfidoTotalRecords;
	
	/** The is required. */
	private Boolean isRequired;
	
	/** The pass count. */
	private Integer passCount;
	
	/** The fail count. */
	private Integer failCount;
	
	/** The onfido report. */
	private String onfidoReport;
	
	/** The status value. */
	private String statusValue;

	/**
	 * @return the onfdioId
	 */
	public String getOnfidoId() {
		return onfidoId;
	}

	/**
	 * @param onfdioId the onfdioId to set
	 */
	public void setOnfidoId(String onfdioId) {
		this.onfidoId = onfdioId;
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
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the onfidoTotalRecords
	 */
	public Integer getOnfidoTotalRecords() {
		return onfidoTotalRecords;
	}

	/**
	 * @param onfidoTotalRecords the onfidoTotalRecords to set
	 */
	public void setOnfidoTotalRecords(Integer onfidoTotalRecords) {
		this.onfidoTotalRecords = onfidoTotalRecords;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the isRequired
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * @param isRequired the isRequired to set
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * @return the passCount
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * @param passCount the passCount to set
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the statusValue
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * @param statusValue the statusValue to set
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * @return the onfidoReport
	 */
	public String getOnfidoReport() {
		return onfidoReport;
	}

	/**
	 * @param onfidoReport the onfidoReport to set
	 */
	public void setOnfidoReport(String onfidoReport) {
		this.onfidoReport = onfidoReport;
	}
}
