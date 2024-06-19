package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
public class DeviceInfoResponse implements IDomain {
	
	/** The device info list. */
	private List<IDomain> deviceInfoList;
	
	/** The user agent. */
	private String userAgent;
	private String deviceType;
	private Boolean browserOnline;
	private String browserLanguage;
	private String browserType;
	private String browserVersion;
	private String deviceId;
	private String deviceManufacturer;
	private String deviceName;
	private String deviceVersion;
	private String oSTimestamp;
	private String oSType;
	private String screenResolution;
	private String applicationId;
	private String applicationVersion;
	private String browserName;
	private String cdAppID;
	private String cdAppVersion;

	/** The event type. */
	private String eventType;
	
	/** The created on. */
	private String createdOn;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/**
	 * @return the deviceInfoList
	 */
	public List<IDomain> getDeviceInfoList() {
		return deviceInfoList;
	}

	/**
	 * @param deviceInfoList the deviceInfoList to set
	 */
	public void setDeviceInfoList(List<IDomain> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getBrowserOnline() {
		return browserOnline;
	}

	public void setBrowserOnline(Boolean browserOnline) {
		this.browserOnline = browserOnline;
	}

	public String getBrowserLanguage() {
		return browserLanguage;
	}

	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getoSTimestamp() {
		return oSTimestamp;
	}

	public void setoSTimestamp(String oSTimestamp) {
		this.oSTimestamp = oSTimestamp;
	}

	public String getoSType() {
		return oSType;
	}

	public void setoSType(String oSType) {
		this.oSType = oSType;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * @param browserName the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * @return the cdAppID
	 */
	public String getCdAppID() {
		return cdAppID;
	}

	/**
	 * @param cdAppID the cdAppID to set
	 */
	public void setCdAppID(String cdAppID) {
		this.cdAppID = cdAppID;
	}

	/**
	 * @return the cdAppVersion
	 */
	public String getCdAppVersion() {
		return cdAppVersion;
	}

	/**
	 * @param cdAppVersion the cdAppVersion to set
	 */
	public void setCdAppVersion(String cdAppVersion) {
		this.cdAppVersion = cdAppVersion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeviceInfoResponse [deviceInfoList=" + deviceInfoList + ", userAgent=" + userAgent + ", deviceType="
				+ deviceType + ", browserOnline=" + browserOnline + ", browserLanguage=" + browserLanguage
				+ ", browserType=" + browserType + ", browserVersion=" + browserVersion + ", deviceId=" + deviceId
				+ ", deviceManufacturer=" + deviceManufacturer + ", deviceName=" + deviceName + ", deviceVersion="
				+ deviceVersion + ", oSTimestamp=" + oSTimestamp + ", oSType=" + oSType + ", screenResolution="
				+ screenResolution + ", applicationId=" + applicationId + ", applicationVersion=" + applicationVersion
				+ ", browserName=" + browserName + ", cdAppID=" + cdAppID + ", cdAppVersion=" + cdAppVersion
				+ ", eventType=" + eventType + ", createdOn=" + createdOn + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + "]";
	}

	
}
