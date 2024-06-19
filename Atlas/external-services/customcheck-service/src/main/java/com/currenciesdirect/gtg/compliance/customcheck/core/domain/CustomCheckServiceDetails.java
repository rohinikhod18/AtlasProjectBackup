package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

import java.sql.Timestamp;
 

/**
 * The Class CustomCheckServiceDetails.
 */
public class CustomCheckServiceDetails implements IDomain {
	
	private String organizationCode;
	
	private String sourceApplication;
	
	private String correlationID;
	
	private String request;
	
	private String response;
	
	private String requestType;
	
	private Timestamp createdOn;
	
	private Integer createdBy;
	
	private Timestamp updateOn;
	
	private Integer updatedBy;
	
	private String operation;

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSource(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Timestamp updateOn) {
		this.updateOn = updateOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationID == null) ? 0 : correlationID.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((organizationCode == null) ? 0 : organizationCode.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		result = prime * result + ((updateOn == null) ? 0 : updateOn.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomCheckServiceDetails other = (CustomCheckServiceDetails) obj;
		if (correlationID == null) {
			if (other.correlationID != null)
				return false;
		} else if (!correlationID.equals(other.correlationID))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (organizationCode == null) {
			if (other.organizationCode != null)
				return false;
		} else if (!organizationCode.equals(other.organizationCode))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (sourceApplication == null) {
			if (other.sourceApplication != null)
				return false;
		} else if (!sourceApplication.equals(other.sourceApplication))
			return false;
		if (updateOn == null) {
			if (other.updateOn != null)
				return false;
		} else if (!updateOn.equals(other.updateOn))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomCheckServiceDetails [organizationCode=" + organizationCode + ", sourceApplication=" + sourceApplication
				+ ", correlationID=" + correlationID + ", request=" + request + ", response=" + response
				+ ", requestType=" + requestType + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", updateOn=" + updateOn + ", updatedBy=" + updatedBy + ", operation=" + operation + "]";
	}

}
