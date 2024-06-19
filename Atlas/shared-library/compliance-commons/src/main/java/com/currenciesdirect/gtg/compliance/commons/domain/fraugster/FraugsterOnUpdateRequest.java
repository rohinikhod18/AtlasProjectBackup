/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FraugsterOnUpdateRequest.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraugsterOnUpdateRequest extends ServiceMessage implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;
	
	/** The contact requests. */
	private List<FraugsterOnUpdateContactRequest>  contactRequests;
	
    /** The request type. */
    private String requestType;
    
    
    /** The source application. */
    private String sourceApplication;

	/**
	 * Gets the org code.
	 *
	 * @return the org code
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
	 * Gets the request type.
	 *
	 * @return the request type
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
	 * Gets the source application.
	 *
	 * @return the source application
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

	/**
	 * Gets the contact requests.
	 *
	 * @return the contact requests
	 */
	public List<FraugsterOnUpdateContactRequest> getContactRequests() {
		return contactRequests;
	}

	/**
	 * Sets the contact requests.
	 *
	 * @param contactRequests the new contact requests
	 */
	public void setContactRequests(List<FraugsterOnUpdateContactRequest> contactRequests) {
		this.contactRequests = contactRequests;
	}

	
}



