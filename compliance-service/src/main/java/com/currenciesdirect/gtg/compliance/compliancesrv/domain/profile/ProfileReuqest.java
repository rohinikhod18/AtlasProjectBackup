package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ProfileReuqest.
 */
public abstract class ProfileReuqest extends ServiceMessage implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty(value="org_code")
	private String orgCode;
	
	/** The source application. */
	@ApiModelProperty(value = "source application", dataType = "java.lang.String", required = true)
	@JsonProperty(value="source_application")
	private String sourceApplication;
	
	/** The cust type. */
	@ApiModelProperty(value = "The customer type (PFX/CFX)", required = true, example = "PFX")
	@JsonProperty(value="cust_type")
	private String custType;
	
	/** The device info. */
	@ApiModelProperty(value = "The device info", required = true)
	@JsonProperty(value="device_info")
	private DeviceInfo deviceInfo;
	
	@ApiModelProperty(value = "Whether this data should be broadcast to other systems such as those in SA Cape Town", example = "true", required = true)
	@JsonProperty(value="isBroadCastRequired")
	private Boolean isBroadCastRequired = Boolean.TRUE;
	
	/** The event. */
	@ApiModelProperty(value = "Event")
	@JsonProperty(value="event")
	private String event;

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
	 * Gets the device info.
	 *
	 * @return the device info
	 */
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * Sets the device info.
	 *
	 * @param deviceInfo the new device info
	 */
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProfileReuqest [orgCode=" + orgCode + ", sourceApplication=" + sourceApplication + ", deviceInfo="
				+ deviceInfo + ", getEvent()="+ getEvent()+"]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceInfo == null) ? 0 : deviceInfo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfileReuqest other = (ProfileReuqest) obj;
		if (deviceInfo == null) {
			if (other.deviceInfo != null)
				return false;
		} else if (!deviceInfo.equals(other.deviceInfo)) {
			return false;
		  }
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode)) {
			return false;
		  }
		if (sourceApplication == null) {
			if (other.sourceApplication != null)
				return false;
		} else if (!sourceApplication.equals(other.sourceApplication)) {
			return false;
		  }	
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event)) {
			return false;
		  }
		return true;
	}

	/**
	 * @return the isBroadcastRequired
	 */
	public Boolean getIsBroadCastRequired() {
		return isBroadCastRequired;
	}

	/**
	 * @param isBroadcastRequired the isBroadcastRequired to set
	 */
	public void setIsBroadCastRequired(Boolean isBroadcastRequired) {
		this.isBroadCastRequired = isBroadcastRequired;
	}
	
	

}
