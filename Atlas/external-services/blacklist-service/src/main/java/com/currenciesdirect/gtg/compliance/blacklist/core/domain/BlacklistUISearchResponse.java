package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.blacklist.core.IResponse;


/**
 * The Class BlacklistUISearchResponse.
 *  @author Rajesh
 */
public class BlacklistUISearchResponse implements IResponse {
	
	/** The data. */
	private List<BlacklistData> data ; 
	
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


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<BlacklistData> getData() {
		return data;
	}


	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<BlacklistData> data) {
		this.data = data;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime
				* result
				+ ((errorDescription == null) ? 0 : errorDescription.hashCode());
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
		BlacklistUISearchResponse other = (BlacklistUISearchResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
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


	@Override
	public String toString() {
		return "BlacklistSearchResponse [data=" + data + ", errorCode="
				+ errorCode + ", errorDescription=" + errorDescription + "]";
	}



}
