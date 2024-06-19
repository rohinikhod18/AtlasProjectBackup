/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

/**
 * @author basits
 *
 */
public class EuPoiCheckResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	private String status;

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
}
