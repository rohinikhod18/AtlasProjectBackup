/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FraugsterPaymentsInRequest.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraugsterPaymentsInRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The contact requests. */
	private List<FraugsterPaymentsInContactRequest> contactRequests;

	/** The request type. */
	private String requestType;

	/** The source application. */
	private String sourceApplication;
	
	/** The cust Type. */
	private String custType;
	
	/**
	 * @return custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

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
	 * @param orgCode
	 *            the new org code
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
	 * @param requestType
	 *            the new request type
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
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the contact requests.
	 *
	 * @return the contact requests
	 */
	public List<FraugsterPaymentsInContactRequest> getContactRequests() {
		return contactRequests;
	}

	/**
	 * Sets the contact requests.
	 *
	 * @param contactRequests
	 *            the new contact requests
	 */
	public void setContactRequests(List<FraugsterPaymentsInContactRequest> contactRequests) {
		this.contactRequests = contactRequests;
	}

}
