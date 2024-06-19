package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.blacklist.core.IResponse;

/**
 * The Class BlacklistSTPResponse.
 *
 * @author Rajesh
 */
public class BlacklistSTPResponse implements IResponse {

	/** The data. */
	private List<BlacklistSTPIdAndData> search;
	
	/** The status. */
	private String status;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

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
	 * @param errorCode
	 *            the new error code
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
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public List<BlacklistSTPIdAndData> getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search
	 *            the new search
	 */
	public void setSearch(List<BlacklistSTPIdAndData> search) {
		this.search = search;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((search == null) ? 0 : search.hashCode());
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
		BlacklistSTPResponse other = (BlacklistSTPResponse) obj;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlacklistSearchResponse [data=" + search + ", errorCode=" + errorCode + ", errorDescription="
				+ errorDescription + "]";
	}

}
