package com.currenciesdirect.gtg.compliance.core.domain.sanction;

public class SanctionUpdateData {
	
	private Integer entityId;
	
	private Integer eventServiceLogId;
	
	private String entityType;
	
	private String field;
	
	private String value;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

}
