package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventServiceLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String entityType;
	private Integer entityId;
	private Integer entityVersion;
	private String serviceName;
	private String serviceProviderName;
	private String providerResponse;
	private String status;
	private String summary;
	private Integer cratedBy;
	private Timestamp createdOn;
	private Integer updatedBy;
	private Timestamp updatedOn;
	private Integer eventServiceLogId;
	private Integer serviceType;
	
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Integer  getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer  entityId) {
		this.entityId = entityId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getProviderResponse() {
		return providerResponse;
	}
	public void setProviderResponse(String providerResponse) {
		this.providerResponse = providerResponse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getCratedBy() {
		return cratedBy;
	}
	public void setCratedBy(Integer cratedBy) {
		this.cratedBy = cratedBy;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Integer getEntityVersion() {
		return entityVersion;
	}
	public void setEntityVersion(Integer version) {
		this.entityVersion = version;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result + ((cratedBy == null) ? 0 : cratedBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((providerResponse == null) ? 0 : providerResponse.hashCode());
		result = prime * result + ((serviceName == null) ? 0 : serviceName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
		return result;
	}
	
	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventServiceLog other = (EventServiceLog) obj;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId)) {
			return false;
		  }
		if (cratedBy == null) {
			if (other.cratedBy != null)
				return false;
		} else if (!cratedBy.equals(other.cratedBy)) {
			return false;
		  }
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		  }
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId)) {
			return false;
		  }
		if (providerResponse == null) {
			if (other.providerResponse != null)
				return false;
		} else if (!providerResponse.equals(other.providerResponse)) {
			return false;
		  }
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName)) {
			return false;
		  }
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status)) {
			return false;
		  }
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary)) {
			return false;
		  }
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy)) {
			return false;
		  }
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn)) {
			return false;
		  }
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventServiceLog [eventId=" + eventId + ", entityType=" + entityType + ", entityId=" + entityId
				+ ", entityVersion=" + entityVersion + ", serviceName=" + serviceName + ", serviceProviderName="
				+ serviceProviderName + ", providerResponse=" + providerResponse + ", status=" + status + ", summary="
				+ summary + ", cratedBy=" + cratedBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + ", eventServiceLogId=" + eventServiceLogId + "]";
	}
	
}
