package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

import java.util.List;

/**
 * The Class CustomerDataScanSearchResponse.
 * 
 * 	@author Rajesh
 */
public class CustomerDataScanSearchResponse implements IResponse {

	
	
	/** The search. */
	private List<CustomerSearchResponseData> search;
	
	/** The status. */
	private String status;
	
	/** The error code. */
	private  String errorCode;
	
	/** The error description. */
	private String errorDescription;

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public List<CustomerSearchResponseData> getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the new search
	 */
	public void setSearch(List<CustomerSearchResponseData> search) {
		this.search = search;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
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
		CustomerDataScanSearchResponse other = (CustomerDataScanSearchResponse) obj;
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
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerDataScanSearchResponse [search=" + search + ", errorCode=" + errorCode + ", errorDescription="
				+ errorDescription + "]";
	}


	
}
