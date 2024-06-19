/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * The Class ContactRegSanctionRequest.
 *
 * @author manish
 */
public class ContactRegSanctionRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The customer type. */
	private String customerType;

	/** The contactrequests. */
	private List<SanctionContactRequest> contactrequests;

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
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Gets the contactrequests.
	 *
	 * @return the contactrequests
	 */
	public List<SanctionContactRequest> getContactrequests() {
		return contactrequests;
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
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType
	 *            the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Sets the contactrequests.
	 *
	 * @param contactrequests
	 *            the new contactrequests
	 */
	public void setContactrequests(List<SanctionContactRequest> contactrequests) {
		this.contactrequests = contactrequests;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContactRegSanctionRequest [orgCode=" + orgCode + ", customerType=" + customerType + ", contactrequests="
				+ contactrequests + ", sourceApplication=" + sourceApplication + "]";
	}

}
