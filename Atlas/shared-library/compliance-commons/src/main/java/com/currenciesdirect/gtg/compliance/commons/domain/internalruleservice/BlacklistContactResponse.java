/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class BlacklistContactResponse.
 *
 * @author manish
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlacklistContactResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The data. */
	private List<BlacklistSTPData> data;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<BlacklistSTPData> getData() {
		return data;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(List<BlacklistSTPData> data) {
		this.data = data;
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
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
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
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
