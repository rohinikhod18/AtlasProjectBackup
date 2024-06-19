/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.reg;

import java.io.Serializable;

/**
 * @author manish
 *
 */
public class EventServiceResponseSummary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String status;
	
	private String summary;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


}
