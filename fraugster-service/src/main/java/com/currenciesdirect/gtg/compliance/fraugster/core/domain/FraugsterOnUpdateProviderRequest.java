/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterOnUpdateProviderRequest.
 *
 * @author manish
 */
public class FraugsterOnUpdateProviderRequest extends FraugsterProfileProviderBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The update method. */
	@JsonProperty("update_method")
	private String updateMethod;
	
	/** The post code latitude. */
	@JsonProperty("post_code_latitude")
	private String postCodeLatitude;
	
	/** The post code longitude. */
	@JsonProperty("post_code_longitude")
	private String postCodeLongitude;
	
	/** The record updated on. */
	@JsonProperty("record_updated_on")
	private String recordUpdatedOn;
	
	/** The account status. */
	@JsonProperty("account_status")
	private String accountStatus;
	
	/** The contact status. */
	@JsonProperty("contact_status")
	private String contactStatus;
	
	/** The online login status. */
	@JsonProperty("online_login_status")
	private String onlineLoginStatus;
	
	/** The session id. */
	@JsonProperty("session_id")
	private String sessionId;
	
	/** The last pin issued date time. */
	@JsonProperty("last_pin_issued_date_time")
	private String lastPinIssuedDateTime;
	
	/** The last login fail date time. */
	@JsonProperty("last_login_fail_date_time")
	private String lastLoginFailDateTime;
	
	/** The last password reset on. */
	@JsonProperty("last_password_reset_on")
	private String lastPasswordResetOn;
	
	/** The list of devices used by customer. */
	@JsonProperty("list_of_devices_used_by_customer")
	private String listOfDevicesUsedByCustomer;
	
	/** The second contact added later online. */
	@JsonProperty("second_contact_added_later_online")
	private String secondContactAddedLaterOnline;
	
	/** The last ip addresses. */
	@JsonProperty("last_ip_addresses")
	private String lastIpAddresses;
	
	/** The last 5 login date time. */
	@JsonProperty("last_5_login_date_time")
	private String last5LoginDateTime;

	/** The australia RTA card number. */
	// added below field as per 89 document.
	@JsonProperty("australia_rta_card_number")
	private String australiaRTACardNumber;
	
	/** The average transaction value. */
	@JsonProperty("average_transaction_value")
	private String averageTransactionValue;
	
	/** The bill trfr crny. */
	@JsonProperty("bill_trfr_crny")
	private String billTrfrCrny;
	
	/** The civic number. */
	@JsonProperty("civic_number")
	private String civicNumber;
	
	/** The hongkong id number. */
	@JsonProperty("hongkong_id_number")
	private String hongkongIdNumber;
	
	/** The mexico curpid. */
	@JsonProperty("mexico_curpid")
	private String mexicoCurpid;
	
	/** The phone work extension. */
	@JsonProperty("phone_work_extension")
	private String phoneWorkExtension;
	
	/** The previous city. */
	@JsonProperty("previous_city")
	private String previousCity;
	
	/** The previous country. */
	@JsonProperty("previous_country")
	private String previousCountry;
	
	/** The previous post code. */
	@JsonProperty("previous_post_code")
	private String previousPostCode;
	
	/** The previous state. */
	@JsonProperty("previous_state")
	private String previousState;
	
	/** The previous street. */
	@JsonProperty("previous_street")
	private String previousStreet;

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
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Gets the last pin issued date time.
	 *
	 * @return the last pin issued date time
	 */
	public String getLastPinIssuedDateTime() {
		return lastPinIssuedDateTime;
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
	public String getSecondContactAddedLaterOnline() {
		return secondContactAddedLaterOnline;
	}

	/**
	 * Gets the last ip addresses.
	 *
	 * @return the last ip addresses
	 */
	public String getLastIpAddresses() {
		return lastIpAddresses;
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
	 * @param updateMethod the new update method
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	/**
	 * Sets the record updated on.
	 *
	 * @param recordUpdatedOn the new record updated on
	 */
	public void setRecordUpdatedOn(String recordUpdatedOn) {
		this.recordUpdatedOn = recordUpdatedOn;
	}

	/**
	 * Sets the account status.
	 *
	 * @param accountStatus the new account status
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Sets the online login status.
	 *
	 * @param onlineLoginStatus the new online login status
	 */
	public void setOnlineLoginStatus(String onlineLoginStatus) {
		this.onlineLoginStatus = onlineLoginStatus;
	}

	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Sets the last pin issued date time.
	 *
	 * @param lastPinIssuedDateTime the new last pin issued date time
	 */
	public void setLastPinIssuedDateTime(String lastPinIssuedDateTime) {
		this.lastPinIssuedDateTime = lastPinIssuedDateTime;
	}

	/**
	 * Sets the last login fail date time.
	 *
	 * @param lastLoginFailDateTime the new last login fail date time
	 */
	public void setLastLoginFailDateTime(String lastLoginFailDateTime) {
		this.lastLoginFailDateTime = lastLoginFailDateTime;
	}

	/**
	 * Sets the last password reset on.
	 *
	 * @param lastPasswordResetOn the new last password reset on
	 */
	public void setLastPasswordResetOn(String lastPasswordResetOn) {
		this.lastPasswordResetOn = lastPasswordResetOn;
	}

	/**
	 * Sets the list of devices used by customer.
	 *
	 * @param listOfDevicesUsedByCustomer the new list of devices used by customer
	 */
	public void setListOfDevicesUsedByCustomer(String listOfDevicesUsedByCustomer) {
		this.listOfDevicesUsedByCustomer = listOfDevicesUsedByCustomer;
	}

	/**
	 * Sets the second contact added later online.
	 *
	 * @param secondContactAddedLaterOnline the new second contact added later online
	 */
	public void setSecondContactAddedLaterOnline(String secondContactAddedLaterOnline) {
		this.secondContactAddedLaterOnline = secondContactAddedLaterOnline;
	}

	/**
	 * Sets the last ip addresses.
	 *
	 * @param lastIpAddresses the new last ip addresses
	 */
	public void setLastIpAddresses(String lastIpAddresses) {
		this.lastIpAddresses = lastIpAddresses;
	}

	/**
	 * Sets the last 5 login date time.
	 *
	 * @param last5LoginDateTime the new last 5 login date time
	 */
	public void setLast5LoginDateTime(String last5LoginDateTime) {
		this.last5LoginDateTime = last5LoginDateTime;
	}

	/**
	 * Gets the post code latitude.
	 *
	 * @return the post code latitude
	 */
	public String getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude the new post code latitude
	 */
	public void setPostCodeLatitude(String postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the post code longitude
	 */
	public String getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude the new post code longitude
	 */
	public void setPostCodeLongitude(String postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
	}

	/**
	 * Gets the australia RTA card number.
	 *
	 * @return the australia_rta_card_number
	 */
	public String getAustraliaRTACardNumber() {
		return australiaRTACardNumber;
	}

	/**
	 * Sets the australia RTA card number.
	 *
	 * @param australiaRTACardNumber the new australia RTA card number
	 */
	public void setAustraliaRTACardNumber(String australiaRTACardNumber) {
		this.australiaRTACardNumber = australiaRTACardNumber;
	}

	/**
	 * Gets the average transaction value.
	 *
	 * @return the average_transaction_value
	 */
	public String getAverageTransactionValue() {
		return averageTransactionValue;
	}

	/**
	 * Sets the average transaction value.
	 *
	 * @param averageTransactionValue the new average transaction value
	 */
	public void setAverageTransactionValue(String averageTransactionValue) {
		this.averageTransactionValue = averageTransactionValue;
	}

	/**
	 * Gets the bill trfr crny.
	 *
	 * @return the bill_trfr_crny
	 */
	public String getBillTrfrCrny() {
		return billTrfrCrny;
	}

	/**
	 * Sets the bill trfr crny.
	 *
	 * @param billTrfrCrny the new bill trfr crny
	 */
	public void setBillTrfrCrny(String billTrfrCrny) {
		this.billTrfrCrny = billTrfrCrny;
	}

	/**
	 * Gets the civic number.
	 *
	 * @return the civic_number
	 */
	public String getCivicNumber() {
		return civicNumber;
	}

	/**
	 * Sets the civic number.
	 *
	 * @param civicNumber the new civic number
	 */
	public void setCivicNumber(String civicNumber) {
		this.civicNumber = civicNumber;
	}

	/**
	 * Gets the hongkong id number.
	 *
	 * @return the hongkong_id_number
	 */
	public String getHongkongIdNumber() {
		return hongkongIdNumber;
	}

	/**
	 * Sets the hongkong id number.
	 *
	 * @param hongkongIdNumber the new hongkong id number
	 */
	public void setHongkongIdNumber(String hongkongIdNumber) {
		this.hongkongIdNumber = hongkongIdNumber;
	}

	/**
	 * Gets the mexico curpid.
	 *
	 * @return the mexico_curpid
	 */
	public String getMexicoCurpid() {
		return mexicoCurpid;
	}

	/**
	 * Sets the mexico curpid.
	 *
	 * @param mexicoCurpid the new mexico curpid
	 */
	public void setMexicoCurpid(String mexicoCurpid) {
		this.mexicoCurpid = mexicoCurpid;
	}

	/**
	 * Gets the phone work extension.
	 *
	 * @return the phone_work_extension
	 */
	public String getPhoneWorkExtension() {
		return phoneWorkExtension;
	}

	/**
	 * Sets the phone work extension.
	 *
	 * @param phoneWorkExtension the new phone work extension
	 */
	public void setPhoneWorkExtension(String phoneWorkExtension) {
		this.phoneWorkExtension = phoneWorkExtension;
	}

	/**
	 * Gets the previous city.
	 *
	 * @return the previous_city
	 */
	public String getPreviousCity() {
		return previousCity;
	}

	/**
	 * Sets the previous city.
	 *
	 * @param previousCity the new previous city
	 */
	public void setPreviousCity(String previousCity) {
		this.previousCity = previousCity;
	}

	/**
	 * Gets the previous country.
	 *
	 * @return the previous_country
	 */
	public String getPreviousCountry() {
		return previousCountry;
	}

	/**
	 * Sets the previous country.
	 *
	 * @param previousCountry the new previous country
	 */
	public void setPreviousCountry(String previousCountry) {
		this.previousCountry = previousCountry;
	}

	/**
	 * Gets the previous post code.
	 *
	 * @return the previous_post_code
	 */
	public String getPreviousPostCode() {
		return previousPostCode;
	}

	/**
	 * Sets the previous post code.
	 *
	 * @param previousPostCode the new previous post code
	 */
	public void setPreviousPostCode(String previousPostCode) {
		this.previousPostCode = previousPostCode;
	}

	/**
	 * Gets the previous state.
	 *
	 * @return the previous_state
	 */
	public String getPreviousState() {
		return previousState;
	}

	/**
	 * Sets the previous state.
	 *
	 * @param previousState the new previous state
	 */
	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	/**
	 * Gets the previous street.
	 *
	 * @return the previous_street
	 */
	public String getPreviousStreet() {
		return previousStreet;
	}

	/**
	 * Sets the previous street.
	 *
	 * @param previousStreet the new previous street
	 */
	public void setPreviousStreet(String previousStreet) {
		this.previousStreet = previousStreet;
	}

}
