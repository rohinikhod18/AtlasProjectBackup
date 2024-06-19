/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class InternalServiceResponse.
 *
 * @author manish
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalServiceResponse extends ServiceMessageResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The account SF id. */
	private String accountSFId;

	/** The org code. */
	private String orgCode;

	/** The source application. */
	private String sourceApplication;

	/** The contacts. */
	private List<ContactResponse> contacts;

	/** The blacklist data by type. */
	private List<BlacklistDataResponse> blacklistDataResponse;

	/** The data. */
	private List<BlacklistData> data;
	
	/** The only blacklist check perform. */
	private Boolean onlyBlacklistCheckPerform = Boolean.FALSE;


	/**
	 * Gets the blacklist data response.
	 *
	 * @return the blacklist data response
	 */
	public List<BlacklistDataResponse> getBlacklistDataResponse() {
		return blacklistDataResponse;
	}

	/**
	 * Sets the blacklist data response.
	 *
	 * @param blacklistDataResponse
	 *            the new blacklist data response
	 */
	public void setBlacklistDataResponse(List<BlacklistDataResponse> blacklistDataResponse) {
		this.blacklistDataResponse = blacklistDataResponse;
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
	 * @param data
	 *            the new data
	 */
	public void setData(List<BlacklistData> data) {
		this.data = data;
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
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the account SF id.
	 *
	 * @return the account SF id
	 */
	public String getAccountSFId() {
		return accountSFId;
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
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the account SF id.
	 *
	 * @param accountSFId
	 *            the new account SF id
	 */
	public void setAccountSFId(String accountSFId) {
		this.accountSFId = accountSFId;
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
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<ContactResponse> getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts.
	 *
	 * @param contacts
	 *            the new contacts
	 */
	public void setContacts(List<ContactResponse> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Adds the contact response.
	 *
	 * @param cResponse
	 *            the c response
	 */
	public void addContactResponse(ContactResponse cResponse) {
		if (null == contacts)
			contacts = new ArrayList<>();
		contacts.add(cResponse);
	}

	/**
	 * @return the onlyBlacklistCheckPerform
	 */
	public Boolean getOnlyBlacklistCheckPerform() {
		return onlyBlacklistCheckPerform;
	}

	/**
	 * @param onlyBlacklistCheckPerform the onlyBlacklistCheckPerform to set
	 */
	public void setOnlyBlacklistCheckPerform(Boolean onlyBlacklistCheckPerform) {
		this.onlyBlacklistCheckPerform = onlyBlacklistCheckPerform;
	}
}
