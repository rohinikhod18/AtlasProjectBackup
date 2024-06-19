package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

public class IpServiceResponse {

	private List<IpResponseData> searchResult;
	private String errorCode;
	private String errorDescription;
	private String correlationID;
	private String responseStatus;
	
	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	
	public List<IpResponseData> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<IpResponseData> searchResult) {
		this.searchResult = searchResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationID == null) ? 0 : correlationID.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		IpServiceResponse other = (IpServiceResponse) obj;
		if (correlationID == null) {
			if (other.correlationID != null)
				return false;
		} else if (!correlationID.equals(other.correlationID))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorDescription == null) {
			if (other.errorDescription != null)
				return false;
		} else if (!errorDescription.equals(other.errorDescription))
			return false;
		return true;
	}

}
