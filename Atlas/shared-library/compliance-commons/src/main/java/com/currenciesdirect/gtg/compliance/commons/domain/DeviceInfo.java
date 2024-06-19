package com.currenciesdirect.gtg.compliance.commons.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DeviceInfo.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"browser_user_agent",
	//added screen_resolution instead of device_resolution and browser_screen_resolution
	"screen_resolution",
	"brwsr_type",
	"brwsr_version",
	"device_type",
	"device_name",
	"device_version",
	"device_id",
	"device_manufacturer",
	"os_type",
	"brwsr_lang",
	"browser_online",
	"os_ts",
	//added new 
	"cd_app_id",
	"cd_app_version"
})
public class DeviceInfo implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** The screen resolution. */
	@ApiModelProperty(value = "Device Screen Resolution", example = "1920x1080 24", required = true)
	@JsonProperty("screen_resolution")
	private String screenResolution;
	
	/** The device type. */
	@ApiModelProperty(value = "Type of Device", example = "Desktop, Tablet, Mobile", required = true)
	@JsonProperty("device_type")
	private String deviceType;
	
	/** The device id. */
	@ApiModelProperty(value = "MAC Address of the Device", example = "C6D67B8A-1178-4E91-8DB7-46139250B804", required = false)
	@JsonProperty("device_id")
	private String deviceId;
	
	/** The device version. */
	@ApiModelProperty(value = "Device Version", example = "72.2.7", required = true)
	@JsonProperty("device_version")
	private String deviceVersion;
	
	/** The device name. */
	@ApiModelProperty(value = "Device Model Name", example = "Kindle Fire 72.2.7", required = true)
	@JsonProperty("device_name")
	private String deviceName;
	
	/** The device manufacturer. */
	@ApiModelProperty(value = "Manufacturer of Device", example = "Amazon", required = true)
	@JsonProperty("device_manufacturer")
	private String deviceManufacturer;
	
	/** The browser name. */
	@ApiModelProperty(value = "Web Browser Name", example = "Chrome, Safari, Chrome Mobile", required = true)
	@JsonProperty("brwsr_type")
	private String browserName;
	
	/** The browser major version. */
	@ApiModelProperty(value = "Web Browser Version", example = "72", required = true)
	@JsonProperty("brwsr_version")
	private String browserMajorVersion;
	
	/** The browser language. */
	@ApiModelProperty(value = "Web Browser Language", example = "en-GB", required = true)
	@JsonProperty("brwsr_lang")
	private String browserLanguage;
	
	/** The browser online. */
	@ApiModelProperty(value = "web browser status", example = "true", required = true)
	@JsonProperty("browser_online")
	private String browserOnline;
	
	/** The user agent. */
	@ApiModelProperty(value = "Web browser user agent. Composed of a BASE64 encoded object of client's system information", example = "eyJ0aW1lIjoiMjAxOS0wMi0yN1QwODo1ODoyNiswMDowMCIsInRpbWV6b25lIjowLCJsYW5ndWFnZXMiOlsiZW4tR0IiLCJlbi1VUyIsImVuIl0sInVzZXJBZ2VudCI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDYuMTsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzcyLjAuMzYyNi4xMTkgU2FmYXJpLzUzNy4zNiIsInBsdWdpbnMiOnsiMCI6eyJuYW1lIjoiQ2hyb21lIFBERiBQbHVnaW4ifSwiMSI6eyJuYW1lIjoiQ2hyb21lIFBERiBWaWV3ZXIifSwiMiI6eyJuYW1lIjoiTmF0aXZlIENsaWVudCJ9fSwiaHRtbDUiOnsiYXBwbGljYXRpb25jYWNoZSI6dHJ1ZSwiY2FudmFzIjp0cnVlLCJjYW52YXN0ZXh0Ijp0cnVlLCJoYXNoY2hhbmdlIjp0cnVlLCJoaXN0b3J5Ijp0cnVlLCJhdWRpbyI6dHJ1ZSwidmlkZW8iOnRydWUsImluZGV4ZWRkYiI6dHJ1ZSwiaW5wdXQiOnsiYXV0b2NvbXBsZXRlIjp0cnVlLCJhdXRvZm9jdXMiOnRydWUsImxpc3QiOnRydWUsInBsYWNlaG9sZGVyIjp0cnVlLCJtYXgiOnRydWUsIm1pbiI6dHJ1ZSwibXVsdGlwbGUiOnRydWUsInBhdHRlcm4iOnRydWUsInJlcXVpcmVkIjp0cnVlLCJzdGVwIjp0cnVlfSwiaW5wdXR0eXBlcyI6eyJzZWFyY2giOnRydWUsInRlbCI6dHJ1ZSwidXJsIjp0cnVlLCJlbWFpbCI6dHJ1ZSwiZGF0ZXRpbWUiOmZhbHNlLCJkYXRlIjp0cnVlLCJtb250aCI6dHJ1ZSwid2VlayI6dHJ1ZSwidGltZSI6dHJ1ZSwiZGF0ZXRpbWUtbG9jYWwiOnRydWUsIm51bWJlciI6ZmFsc2UsInJhbmdlIjp0cnVlLCJjb2xvciI6dHJ1ZX0sImxvY2Fsc3RvcmFnZSI6dHJ1ZSwic2Vzc2lvbnN0b3JhZ2UiOnRydWUsInBvc3RtZXNzYWdlIjp0cnVlLCJ3ZWJzb2NrZXRzIjp0cnVlLCJ3ZWJ3b3JrZXJzIjp0cnVlfSwiY3NzIjp7ImJhY2tncm91bmRzaXplIjp0cnVlLCJib3JkZXJpbWFnZSI6dHJ1ZSwiYm9yZGVycmFkaXVzIjp0cnVlLCJib3hzaGFkb3ciOnRydWUsImZsZXhib3giOnRydWUsImZsZXhib3hsZWdhY3kiOnRydWUsImhzbGEiOnRydWUsInJnYmEiOnRydWUsIm11bHRpcGxlYmdzIjp0cnVlLCJvcGFjaXR5Ijp0cnVlLCJ0ZXh0c2hhZG93Ijp0cnVlLCJjc3NhbmltYXRpb25zIjp0cnVlLCJjc3Njb2x1bW5zIjp0cnVlLCJnZW5lcmF0ZWRjb250ZW50Ijp0cnVlLCJjc3NncmFkaWVudHMiOnRydWUsImNzc3JlZmxlY3Rpb25zIjp0cnVlLCJjc3N0cmFuc2Zvcm1zIjp0cnVlLCJjc3N0cmFuc2Zvcm1zM2QiOnRydWUsImNzc3RyYW5zaXRpb25zIjp0cnVlfSwiZmxhc2giOmZhbHNlLCJzdmciOnRydWUsImNvb2tpZXMiOnRydWUsInNjcmVlblJlc29sdXRpb24iOiIxMzY2IHggNzY4IiwicGl4ZWxSYXRpbyI6MSwic2NyZWVuUmVzb2x1dGlvbk5hdGl2ZSI6IjEzNjYgeCA3NjgiLCJicm93c2VyIjoiQ2hyb21lIiwiYnJvd3NlclZlcnNpb24iOiI3Mi4wLjM2MjYuMTE5IiwiYnJvd3Nlck1ham9yVmVyc2lvbiI6NzIsIm9zIjoiV2luZG93cyIsIm9zVmVyc2lvbiI6IjcifQ==", required = true)
	@JsonProperty("browser_user_agent")
	private String userAgent;

	/** The os name. */
	@ApiModelProperty(value = "Operating System of Device", example = "Android 5.1.1", required = true)
	@JsonProperty("os_type")
	private String osName;
	
	/** The os date and time. */
	@ApiModelProperty(value = "Operating System DateTime and Timezone (default UTC)", example = "2019-02-27T12:28:30Z", required = true)
	@JsonProperty("os_ts")
	private String osDateAndTime;

	/** The cd app id. */
	@ApiModelProperty(value = "CD Mobile Application Id", required = true)
	@JsonProperty("cd_app_id")
	private String cdAppId;

	/** The cd app version. */
	@ApiModelProperty(value = "CD Mobile Application version", required = true)
	@JsonProperty("cd_app_version")
	private String cdAppVersion;
	
	/**
	 * Gets the device type.
	 *
	 * @return The deviceType
	 */
	@JsonProperty("device_type")
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType            The device_type
	 */
	@JsonProperty("device_type")
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Gets the device id.
	 *
	 * @return The deviceId
	 */
	@JsonProperty("device_id")
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId            The device_id
	 */
	@JsonProperty("device_id")
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the device version.
	 *
	 * @return The deviceVersion
	 */
	@JsonProperty("device_version")
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * Sets the device version.
	 *
	 * @param deviceVersion            The device_version
	 */
	@JsonProperty("device_version")
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	/**
	 * Gets the device name.
	 *
	 * @return The deviceName
	 */
	@JsonProperty("device_name")
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName            The device_name
	 */
	@JsonProperty("device_name")
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Gets the device manufacturer.
	 *
	 * @return The deviceManufacturer
	 */
	@JsonProperty("device_manufacturer")
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * Sets the device manufacturer.
	 *
	 * @param deviceManufacturer            The device_manufacturer
	 */
	@JsonProperty("device_manufacturer")
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * Gets the browser name.
	 *
	 * @return The browserName
	 */
	@JsonProperty("brwsr_type")
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * Sets the browser name.
	 *
	 * @param browserName            The browser_name
	 */
	@JsonProperty("brwsr_type")
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * Gets the browser major version.
	 *
	 * @return The browserMajorVersion
	 */
	@JsonProperty("brwsr_version")
	public String getBrowserMajorVersion() {
		return browserMajorVersion;
	}

	/**
	 * Sets the browser major version.
	 *
	 * @param browserMajorVersion            The browser_major_version
	 */
	@JsonProperty("brwsr_version")
	public void setBrowserMajorVersion(String browserMajorVersion) {
		this.browserMajorVersion = browserMajorVersion;
	}

	/**
	 * Gets the browser language.
	 *
	 * @return The browserLanguage
	 */
	@JsonProperty("brwsr_lang")
	public String getBrowserLanguage() {
		return browserLanguage;
	}

	/**
	 * Sets the browser language.
	 *
	 * @param browserLanguage            The browser_language
	 */
	@JsonProperty("brwsr_lang")
	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}

	/**
	 * Gets the browser online.
	 *
	 * @return The browserOnline
	 */
	@JsonProperty("browser_online")
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * Sets the browser online.
	 *
	 * @param browserOnline            The browser_online
	 */
	@JsonProperty("browser_online")
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * Gets the user agent.
	 *
	 * @return The userAgent
	 */
	@JsonProperty("browser_user_agent")
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent            The user_agent
	 */
	@JsonProperty("browser_user_agent")
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * Gets the os name.
	 *
	 * @return The osName
	 */
	@JsonProperty("os_type")
	public String getOsName() {
		return osName;
	}

	/**
	 * Sets the os name.
	 *
	 * @param osName            The os_name
	 */
	@JsonProperty("os_type")
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	/**
	 * Gets the os date and time.
	 *
	 * @return The osDateAndTime
	 */
	@JsonProperty("os_ts")
	public String getOsDateAndTime() {
		return osDateAndTime;
	}
	
	/**
	 * Sets the os date and time.
	 *
	 * @param osDateAndTime            The osDateAndTime
	 */	
	@JsonProperty("os_ts")
	public void setOsDateAndTime(String osDateAndTime) {
		this.osDateAndTime = osDateAndTime;
	}

	/**
	 * Gets the screen resolution.
	 *
	 * @return The screenResolution
	 */
	@JsonProperty("screen_resolution")
	public String getScreenResolution() {
		return screenResolution;
	}
	
	/**
	 * Sets the screen resolution.
	 *
	 * @param screenResolution            The screenResolution
	 */	
	@JsonProperty("screen_resolution")
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}
	
	/**
	 * Gets the cd app id.
	 *
	 * @return The cdAppId
	 */
	@JsonProperty("cd_app_id")
	public String getCdAppId() {
		return cdAppId;
	}
	
	/**
	 * Sets the cd app id.
	 *
	 * @param cdAppId            The cdAppId
	 */
	@JsonProperty("cd_app_id")
	public void setCdAppId(String cdAppId) {
		this.cdAppId = cdAppId;
	}
	
	/**
	 * Gets the cd app version.
	 *
	 * @return The cdAppVersion
	 */
	@JsonProperty("cd_app_version")
	public String getCdAppVersion() {
		return cdAppVersion;
	}
	
	/**
	 * Sets the cd app version.
	 *
	 * @param cdAppVersion            The cdAppVersion
	 */
	@JsonProperty("cd_app_version")
	public void setCdAppVersion(String cdAppVersion) {
		this.cdAppVersion = cdAppVersion;
	}
	
}