/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterProviderBaseRequest.
 *
 * @author manish
 */
public class FraugsterProviderBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trans id. */
	@JsonProperty("trans_id")
	private String transId;
	
	/** The event type. */
	@JsonProperty("event_type")
	private String eventType;
	
	/** The cust id. */
	@JsonProperty("cust_id")
	private String custId;
	
	/** The organisation code. */
	@JsonProperty("organisation_code")
	private String organisationCode;
	
	/** The cust signup ts. */
	@JsonProperty("cust_signup_ts")
	private String custSignupTs;
	
	/** The browser user agent. */
	@JsonProperty("browser_user_agent")
	private String browserUserAgent;
	
	/** The screen resolution. */
	@JsonProperty("screen_resolution")
	private String screenResolution;
	
	/** The brwsr type. */
	@JsonProperty("brwsr_type")
	private String brwsrType;
	
	/** The brwsr version. */
	@JsonProperty("brwsr_version")
	private String brwsrVersion;
	
	/** The device type. */
	@JsonProperty("device_type")
	private String deviceType;
	
	/** The device name. */
	@JsonProperty("device_name")
	private String deviceName;
	
	/** The device version. */
	@JsonProperty("device_version")
	private String deviceVersion;
	
	/** The device id. */
	@JsonProperty("device_id")
	private String deviceId;
	
	/** The device manufacturer. */
	@JsonProperty("device_manufacturer")
	private String deviceManufacturer;
	
	/** The os type. */
	@JsonProperty("os_type")
	private String osType;
	
	/** The brwsr lang. */
	@JsonProperty("brwsr_lang")
	private String brwsrLang;
	
	/** The browser online. */
	@JsonProperty("browser_online")
	private String browserOnline;
	
	/** The os ts. */
	@JsonProperty("os_ts")
	private String osTs;

	/**
	 * Gets the browser user agent.
	 *
	 * @return the browser user agent
	 */
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}

	/**
	 * Sets the browser user agent.
	 *
	 * @param browserUserAgent the new browser user agent
	 */
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}

	/**
	 * Gets the screen resolution.
	 *
	 * @return the screen resolution
	 */
	public String getScreenResolution() {
		return screenResolution;
	}

	/**
	 * Sets the screen resolution.
	 *
	 * @param screenResolution the new screen resolution
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * Gets the brwsr type.
	 *
	 * @return the brwsr type
	 */
	public String getBrwsrType() {
		return brwsrType;
	}

	/**
	 * Sets the brwsr type.
	 *
	 * @param brwsrType the new brwsr type
	 */
	public void setBrwsrType(String brwsrType) {
		this.brwsrType = brwsrType;
	}

	/**
	 * Gets the brwsr version.
	 *
	 * @return the brwsr version
	 */
	public String getBrwsrVersion() {
		return brwsrVersion;
	}

	/**
	 * Sets the brwsr version.
	 *
	 * @param brwsrVersion the new brwsr version
	 */
	public void setBrwsrVersion(String brwsrVersion) {
		this.brwsrVersion = brwsrVersion;
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
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	 * Gets the os type.
	 *
	 * @return the os type
	 */
	public String getOsType() {
		return osType;
	}

	/**
	 * Sets the os type.
	 *
	 * @param osType the new os type
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}

	/**
	 * Gets the brwsr lang.
	 *
	 * @return the brwsr lang
	 */
	public String getBrwsrLang() {
		return brwsrLang;
	}

	/**
	 * Sets the brwsr lang.
	 *
	 * @param brwsrLang the new brwsr lang
	 */
	public void setBrwsrLang(String brwsrLang) {
		this.brwsrLang = brwsrLang;
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
	 * Gets the os ts.
	 *
	 * @return the os ts
	 */
	public String getOsTs() {
		return osTs;
	}

	/**
	 * Sets the os ts.
	 *
	 * @param osTs the new os ts
	 */
	public void setOsTs(String osTs) {
		this.osTs = osTs;
	}

	/**
	 * Gets the cust id.
	 *
	 * @return the cust id
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * Sets the cust id.
	 *
	 * @param custId the new cust id
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * Gets the trans id.
	 *
	 * @return the trans id
	 */
	public String getTransId() {
		return transId;
	}

	/**
	 * Sets the trans id.
	 *
	 * @param transId the new trans id
	 */
	public void setTransId(String transId) {
		this.transId = transId;
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
	 * Gets the organisation code.
	 *
	 * @return the organisation code
	 */
	public String getOrganisationCode() {
		return organisationCode;
	}

	/**
	 * Sets the organisation code.
	 *
	 * @param organisationCode the new organisation code
	 */
	public void setOrganisationCode(String organisationCode) {
		this.organisationCode = organisationCode;
	}

	/**
	 * Gets the cust signup ts.
	 *
	 * @return the cust signup ts
	 */
	public String getCustSignupTs() {
		return custSignupTs;
	}

	/**
	 * Sets the cust signup ts.
	 *
	 * @param custSignupTs the new cust signup ts
	 */
	public void setCustSignupTs(String custSignupTs) {
		this.custSignupTs = custSignupTs;
	}
}
