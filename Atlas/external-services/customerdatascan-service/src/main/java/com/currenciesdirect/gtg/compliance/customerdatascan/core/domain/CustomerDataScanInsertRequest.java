package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

import java.util.List;

/**
 * The Class CustomerDataScanInsertRequest.
 * 
 * 	@author Rajesh
 */
public class CustomerDataScanInsertRequest implements IRequest {

	/** The save. */
	private List<CustomerInsertRequestData> save;
	
	/** The source application. */
	private String sourceApplication;
	
	/** The request type. */
	private String requestType;
	
	/** The correlation id. */
	private String correlationId;
	
	/** The user name. */
	private String userName;
	
	

	/**
	 * Gets the save.
	 *
	 * @return the save
	 */
	public List<CustomerInsertRequestData> getSave() {
		return save;
	}

	/**
	 * Sets the save.
	 *
	 * @param save the new save
	 */
	public void setSave(List<CustomerInsertRequestData> save) {
		this.save = save;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((save == null) ? 0 : save.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDataScanInsertRequest other = (CustomerDataScanInsertRequest) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (save == null) {
			if (other.save != null)
				return false;
		} else if (!save.equals(other.save))
			return false;
		if (sourceApplication == null) {
			if (other.sourceApplication != null)
				return false;
		} else if (!sourceApplication.equals(other.sourceApplication))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerDataScanInsertRequest [save=" + save + ", sourceApplication=" + sourceApplication
				+ ", requestType=" + requestType + ", correlationId=" + correlationId + "]";
	}
	
}
