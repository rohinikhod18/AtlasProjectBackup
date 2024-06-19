package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class ViewMoreResponse.
 */
public class ViewMoreResponse {
	
	/** The services. */
	private List<IDomain> services;
	
	/** The left records. */
	private Integer leftRecords;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	
	/**
	 * @return the services
	 */
	public List<IDomain> getServices() {
		return services;
	}


	/**
	 * @param services the services to set
	 */
	public void setServices(List<IDomain> services) {
		this.services = services;
	}


	/**
	 * @return the leftRecords
	 */
	public Integer getLeftRecords() {
		return leftRecords;
	}


	/**
	 * @param leftRecords the leftRecords to set
	 */
	public void setLeftRecords(Integer leftRecords) {
		this.leftRecords = leftRecords;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
