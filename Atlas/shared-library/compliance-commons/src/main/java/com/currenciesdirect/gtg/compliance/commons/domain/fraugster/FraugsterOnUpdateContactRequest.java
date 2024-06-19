/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterOnUpdateContactRequest.
 *
 * @author manish
 */
public class FraugsterOnUpdateContactRequest extends FraugsterProfileBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The update method. */
	private String updateMethod;

	/** The record updated on. */
	private String recordUpdatedOn;

	/** The account status. */
	private String accountStatus;

	/** The contact status. */
	private String contactStatus;

	/** The online login status. */
	private String onlineLoginStatus;

	/** The session ID. */
	private String sessionID;

	/** The last PIN issued date time. */
	private String lastPINIssuedDateTime;

	/** The last login fail date time. */
	private String lastLoginFailDateTime;

	/** The last password reset on. */
	private String lastPasswordResetOn;

	/** The list of devices used by customer. */
	private String listOfDevicesUsedByCustomer;

	/** The second contact added later online. */
	private Boolean secondContactAddedLaterOnline;

	/** The last IP addresses. */
	private String lastIPAddresses;

	/** The last 5 login date time. */
	private String last5LoginDateTime;

	/** The code latitude. */
	private Float postCodeLatitude;

	/** The post code longitude. */
	private Float postCodeLongitude;

	/**
	 * Gets the post code latitude.
	 *
	 * @return the post code latitude
	 */
	public Float getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude
	 *            the new post code latitude
	 */
	public void setPostCodeLatitude(Float postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the post code longitude
	 */
	public Float getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude
	 *            the new post code longitude
	 */
	public void setPostCodeLongitude(Float postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
	}

	/**
	 * Gets the update method.
	 *
	 * @return the update method
	 */
	public String getUpdateMethod() {
		return updateMethod;
	}

	/**
	 * Gets the record updated on.
	 *
	 * @return the record updated on
	 */
	public String getRecordUpdatedOn() {
		return recordUpdatedOn;
	}

	/**
	 * Gets the account status.
	 *
	 * @return the account status
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Gets the online login status.
	 *
	 * @return the online login status
	 */
	public String getOnlineLoginStatus() {
		return onlineLoginStatus;
	}

	/**
	 * Gets the session ID.
	 *
	 * @return the session ID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Gets the last PIN issued date time.
	 *
	 * @return the last PIN issued date time
	 */
	public String getLastPINIssuedDateTime() {
		return lastPINIssuedDateTime;
	}

	/**
	 * Gets the last password reset on.
	 *
	 * @return the last password reset on
	 */
	public String getLastPasswordResetOn() {
		return lastPasswordResetOn;
	}

	/**
	 * Gets the list of devices used by customer.
	 *
	 * @return the list of devices used by customer
	 */
	public String getListOfDevicesUsedByCustomer() {
		return listOfDevicesUsedByCustomer;
	}

	/**
	 * Gets the second contact added later online.
	 *
	 * @return the second contact added later online
	 */
	public Boolean getSecondContactAddedLaterOnline() {
		return secondContactAddedLaterOnline;
	}

	/**
	 * Gets the last IP addresses.
	 *
	 * @return the last IP addresses
	 */
	public String getLastIPAddresses() {
		return lastIPAddresses;
	}

	/**
	 * Gets the last 5 login date time.
	 *
	 * @return the last 5 login date time
	 */
	public String getLast5LoginDateTime() {
		return last5LoginDateTime;
	}

	/**
	 * Sets the update method.
	 *
	 * @param updateMethod
	 *            the new update method
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	/**
	 * Sets the account status.
	 *
	 * @param accountStatus
	 *            the new account status
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Sets the online login status.
	 *
	 * @param onlineLoginStatus
	 *            the new online login status
	 */
	public void setOnlineLoginStatus(String onlineLoginStatus) {
		this.onlineLoginStatus = onlineLoginStatus;
	}

	/**
	 * Sets the session ID.
	 *
	 * @param sessionID
	 *            the new session ID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Sets the last PIN issued date time.
	 *
	 * @param lastPINIssuedDateTime
	 *            the new last PIN issued date time
	 */
	public void setLastPINIssuedDateTime(String lastPINIssuedDateTime) {
		this.lastPINIssuedDateTime = lastPINIssuedDateTime;
	}

	/**
	 * Sets the last password reset on.
	 *
	 * @param lastPasswordResetOn
	 *            the new last password reset on
	 */
	public void setLastPasswordResetOn(String lastPasswordResetOn) {
		this.lastPasswordResetOn = lastPasswordResetOn;
	}

	/**
	 * Sets the list of devices used by customer.
	 *
	 * @param listOfDevicesUsedByCustomer
	 *            the new list of devices used by customer
	 */
	public void setListOfDevicesUsedByCustomer(String listOfDevicesUsedByCustomer) {
		this.listOfDevicesUsedByCustomer = listOfDevicesUsedByCustomer;
	}

	/**
	 * Sets the second contact added later online.
	 *
	 * @param secondContactAddedLaterOnline
	 *            the new second contact added later online
	 */
	public void setSecondContactAddedLaterOnline(Boolean secondContactAddedLaterOnline) {
		this.secondContactAddedLaterOnline = secondContactAddedLaterOnline;
	}

	/**
	 * Sets the last IP addresses.
	 *
	 * @param lastIPAddresses
	 *            the new last IP addresses
	 */
	public void setLastIPAddresses(String lastIPAddresses) {
		this.lastIPAddresses = lastIPAddresses;
	}

	/**
	 * Sets the last 5 login date time.
	 *
	 * @param last5LoginDateTime
	 *            the new last 5 login date time
	 */
	public void setLast5LoginDateTime(String last5LoginDateTime) {
		this.last5LoginDateTime = last5LoginDateTime;
	}

	/**
	 * Gets the last login fail date time.
	 *
	 * @return the last login fail date time
	 */
	public String getLastLoginFailDateTime() {
		return lastLoginFailDateTime;
	}

	/**
	 * Sets the last login fail date time.
	 *
	 * @param lastLoginFailDateTime
	 *            the new last login fail date time
	 */
	public void setLastLoginFailDateTime(String lastLoginFailDateTime) {
		this.lastLoginFailDateTime = lastLoginFailDateTime;
	}

	/**
	 * Sets the record updated on.
	 *
	 * @param recordUpdatedOn
	 *            the new record updated on
	 */
	public void setRecordUpdatedOn(String recordUpdatedOn) {
		this.recordUpdatedOn = recordUpdatedOn;
	}

}
