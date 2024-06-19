package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

public class EventServiceLogSummary {
	
	/** The entity service hash. */
	private Long entityServiceHash;
	
	/** The status. */
	private Integer status;
	
	/** The event service log id. */
	private Integer eventServiceLogId;
	
	/** The event id. */
	private Integer eventId;
	
	/** The entity type. */
	private Integer entityType;
	
	/** The entity id. */
	private Integer entityId;
	
	/** The entity version. */
	private Integer entityVersion;
	
	/** The service type. */
	private Integer serviceType;
	
	private String serviceName; 
	
	private String entityName;

	/**
	 * @return the entityServiceHash
	 */
	public Long getEntityServiceHash() {
		return entityServiceHash;
	}

	/**
	 * @param entityServiceHash the entityServiceHash to set
	 */
	public void setEntityServiceHash(Long entityServiceHash) {
		this.entityServiceHash = entityServiceHash;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
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
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the entityType
	 */
	public Integer getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

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
	 * @return the entityVersion
	 */
	public Integer getEntityVersion() {
		return entityVersion;
	}

	/**
	 * @param entityVersion the entityVersion to set
	 */
	public void setEntityVersion(Integer entityVersion) {
		this.entityVersion = entityVersion;
	}

	/**
	 * @return the serviceType
	 */
	public Integer getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}