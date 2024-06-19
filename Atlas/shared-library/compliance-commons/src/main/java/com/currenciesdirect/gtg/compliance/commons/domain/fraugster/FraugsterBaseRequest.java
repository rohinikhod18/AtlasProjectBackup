/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterBaseRequest.
 *
 * @author manish
 */
public class FraugsterBaseRequest implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The registration date time. */
	private String registrationDateTime;
	
	/** The organization code. */
	private String organizationCode;
	
	/** The event type. */
	private String eventType;

	/**  Contact table ID leftpadded. */
	private String transactionID;

	/**  account table ID leftpadded. */
	private String custID;

	/** The id. */
	private Integer id;
	
	/** The user agent. */
	private String userAgent;
	
	/** The browser screen resolution. */
	private String browserScreenResolution;
	
	/** The browser name. */
	private String browserName;
	
	/** The browser major version. */
	private String browserMajorVersion;
	
	/** The device type. */
	private String deviceType;
	
	/** The device name. */
	private String deviceName;
	
	/** The device version. */
	private String deviceVersion;
	
	/** The device ID. */
	private String deviceID;
	
	/** The device manufacturer. */
	private String deviceManufacturer;
	
	/** The os name. */
	private String osName;
	
	/** The browser language. */
	private String browserLanguage;
	
	/** The browser online. */
	private String browserOnline;
	
	/** The os date and time. */
	protected String osDateAndTime;
	
	/** The browser Type*/
	private String browserType;

	
	/**
	 * @return browserType
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * @param browserType
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * Gets the registration date time.
	 *
	 * @return the registration date time
	 */
	public String getRegistrationDateTime() {
		return registrationDateTime;
	}

	/**
	 * Sets the registration date time.
	 *
	 * @param registrationDateTime the new registration date time
	 */
	public void setRegistrationDateTime(String registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	/**
	 * Gets the organization code.
	 *
	 * @return the organization code
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * Sets the organization code.
	 *
	 * @param organizationCode the new organization code
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType the new event type
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Gets the transaction ID.
	 *
	 * @return the transaction ID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * Sets the transaction ID.
	 *
	 * @param transactionID the new transaction ID
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * Gets the cust ID.
	 *
	 * @return the cust ID
	 */
	public String getCustID() {
		return custID;
	}

	/**
	 * Sets the cust ID.
	 *
	 * @param custID the new cust ID
	 */
	public void setCustID(String custID) {
		this.custID = custID;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}




	
	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent the new user agent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * Gets the browser screen resolution.
	 *
	 * @return the browser screen resolution
	 */
	public String getBrowserScreenResolution() {
		return browserScreenResolution;
	}

	/**
	 * Sets the browser screen resolution.
	 *
	 * @param browserScreenResolution the new browser screen resolution
	 */
	public void setBrowserScreenResolution(String browserScreenResolution) {
		this.browserScreenResolution = browserScreenResolution;
	}

	/**
	 * Gets the browser name.
	 *
	 * @return the browser name
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * Sets the browser name.
	 *
	 * @param browserName the new browser name
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * Gets the browser major version.
	 *
	 * @return the browser major version
	 */
	public String getBrowserMajorVersion() {
		return browserMajorVersion;
	}

	/**
	 * Sets the browser major version.
	 *
	 * @param browserMajorVersion the new browser major version
	 */
	public void setBrowserMajorVersion(String browserMajorVersion) {
		this.browserMajorVersion = browserMajorVersion;
	}

	/**
	 * Gets the device type.
	 *
	 * @return the device type
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType the new device type
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName the new device name
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Gets the device version.
	 *
	 * @return the device version
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * Sets the device version.
	 *
	 * @param deviceVersion the new device version
	 */
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	/**
	 * Gets the device ID.
	 *
	 * @return the device ID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * Sets the device ID.
	 *
	 * @param deviceID the new device ID
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * Gets the device manufacturer.
	 *
	 * @return the device manufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * Sets the device manufacturer.
	 *
	 * @param deviceManufacturer the new device manufacturer
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * Gets the os name.
	 *
	 * @return the os name
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * Sets the os name.
	 *
	 * @param osName the new os name
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * Gets the browser language.
	 *
	 * @return the browser language
	 */
	public String getBrowserLanguage() {
		return browserLanguage;
	}

	/**
	 * Sets the browser language.
	 *
	 * @param browserLanguage the new browser language
	 */
	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}

	/**
	 * Gets the browser online.
	 *
	 * @return the browser online
	 */
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * Sets the browser online.
	 *
	 * @param browserOnline the new browser online
	 */
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * Gets the os date and time.
	 *
	 * @return the os date and time
	 */
	public String getOsDateAndTime() {
		return osDateAndTime;
	}

	/**
	 * Sets the os date and time.
	 *
	 * @param osDateAndTime the new os date and time
	 */
	public void setOsDateAndTime(String osDateAndTime) {
		this.osDateAndTime = osDateAndTime;
	}
	
}
