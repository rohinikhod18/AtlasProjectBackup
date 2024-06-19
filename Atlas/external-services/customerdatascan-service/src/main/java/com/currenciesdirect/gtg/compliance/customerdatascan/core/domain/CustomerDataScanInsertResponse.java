package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

import java.util.List;

/**
 * The Class CustomerDataScanResponse.
 * 
 * 	@author Rajesh
 */
public class CustomerDataScanInsertResponse implements IResponse {
	
	private List<CustomerInsertResponseData> save;
	
	/** The status. */
	private String status;
	
	/** The error code. */
	private String errorCode;
	
	/** The error description. */
	private String errorDescription;
	
	public List<CustomerInsertResponseData> getSave() {
		return save;
	}

	public void setSave(List<CustomerInsertResponseData> save) {
		this.save = save;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @param errorDescrption the new error description
	 */
	public void setErrorDescription(String errorDescrption) {
		this.errorDescription = errorDescrption;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		result = prime * result + ((save == null) ? 0 : save.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CustomerDataScanInsertResponse other = (CustomerDataScanInsertResponse) obj;
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
		if (save == null) {
			if (other.save != null)
				return false;
		} else if (!save.equals(other.save))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "CustomerDataScanInsertResponse [save=" + save + ", status=" + status + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription + "]";
	}
}
