package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Class CustomerDataScanDeleteRequest.
 * 
 * 	@author Rajesh
 */
public class CustomerDataScanDeleteRequest implements IRequest {

	/** The org code. */
	private String orgCode;
	
	/** The sf account id. */
	private String sfAccountID;
	
	/** The source application. */
	private String sourceApplication;
	
	/** The request type. */
	private String requestType;
	
	/** The correlation id. */
	private String correlationId;
	
	/** The user name. */
	private String userName;
	
	/* 
	 * @see com.currenciesdirect.gtg.es.compliance.customerdatascan.core.domain.IRequest#getOrgCode()
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the sf account id.
	 *
	 * @return the sf account id
	 */
	public String getSfAccountID() {
		return sfAccountID;
	}

	/**
	 * Sets the sf account id.
	 *
	 * @param sfAccountID the new sf account id
	 */
	public void setSfAccountID(String sfAccountID) {
		this.sfAccountID = sfAccountID;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.es.compliance.customerdatascan.core.domain.IRequest#getSourceApplication()
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

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.es.compliance.customerdatascan.core.domain.IRequest#getRequestType()
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

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
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
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result
				+ ((sfAccountID == null) ? 0 : sfAccountID.hashCode());
		result = prime
				* result
				+ ((sourceApplication == null) ? 0 : sourceApplication
						.hashCode());
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
		CustomerDataScanDeleteRequest other = (CustomerDataScanDeleteRequest) obj;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (sfAccountID == null) {
			if (other.sfAccountID != null)
				return false;
		} else if (!sfAccountID.equals(other.sfAccountID))
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
		return "CustomerDataScanDeleteRequest [orgCode=" + orgCode
				+ ", sfAccountID=" + sfAccountID + ", sourceApplication="
				+ sourceApplication + ", requestType=" + requestType + "]";
	}
	
}
