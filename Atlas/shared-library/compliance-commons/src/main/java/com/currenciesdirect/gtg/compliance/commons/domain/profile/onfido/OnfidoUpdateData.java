package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class OnfidoUpdateData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The entity id. */
	@ApiModelProperty(value = "The entity id", required = true)
	private Integer entityId;
	
	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;
	
	/** The entity type. */
	@ApiModelProperty(value = "The entity type", required = true)
	private String entityType;
	
	/** The field. */
	@ApiModelProperty(value = "The field", required = true)
	private String field;
	
	/** The value. */
	@ApiModelProperty(value = "The value", required = true)
	private String value;
	
	/** The contact status. */
	@ApiModelProperty(value = "The contact status", required = true)
	private String contactStatus;

	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the contactStatus
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * @param contactStatus the contactStatus to set
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

}
