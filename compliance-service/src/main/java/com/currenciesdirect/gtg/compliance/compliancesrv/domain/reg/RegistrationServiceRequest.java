package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RegistrationServiceRequest extends ServiceMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty(value="org_code")
	private String orgCode;
	
	@JsonIgnore
	private Integer orgId;
	
	@JsonProperty(value="account")
	private Account account;
	
	@ApiModelProperty(value = "source_application", dataType = "java.lang.String", required = true)
	@JsonProperty(value="source_application")
	private String sourceApplication;
	
	@JsonProperty(value="cookie_info")
	private String cookieInfo;

	@JsonProperty(value="device_info")
	private DeviceInfo deviceInfo;
	
	@ApiModelProperty(value = "Whether this data should be broadcast to other systems such as those in SA Cape Town", example = "true", required = true)
	@JsonProperty(value="isBroadCastRequired")
	private Boolean isBroadCastRequired = Boolean.TRUE;
	
	@ApiModelProperty(value = "event", example = "migrate_customer")
	@JsonProperty(value="event")
	private String event;
	
	@JsonIgnore
	private Integer oldOrgId;

	@JsonProperty(value="isCardApply")
	private String isCardApply;

	@JsonProperty(value="ipAddress")
	private String ipAddress;

	@JsonProperty(value="tradeContractId")
	private String tradeContractId;

	@JsonProperty(value = "lastPasswordChangeDate")
	private String lastPasswordChangeDate;

	@JsonProperty(value = "appInstallDate")
	private String appInstallDate;

	@JsonProperty(value = "deviceId")
	private String deviceId;

	@JsonProperty(value = "lastEmailChangeDate")
	private String lastEmailChangeDate;

	@JsonProperty(value = "lastPhoneChangeDate")
	private String lastPhoneChangeDate;

	@JsonProperty(value = "lastAddressChangeDate")
	private String lastAddressChangeDate;

	@JsonProperty(value = "lastPinChangeDate")
	private String lastPinChangeDate;

	@JsonProperty(value="card")
	private Card card;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	public String getCookieInfo() {
		return cookieInfo;
	}

	public void setCookieInfo(String cookieInfo) {
		this.cookieInfo = cookieInfo;
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

	/**
	 * @return the oldOrgId
	 */
	public Integer getOldOrgId() {
		return oldOrgId;
	}

	/**
	 * @param oldOrgId the oldOrgId to set
	 */
	public void setOldOrgId(Integer oldOrgId) {
		this.oldOrgId = oldOrgId;
	}

	public String getIsCardApply() {
		return isCardApply;
	}

	public void setIsCardApply(String isCardApply) {
		this.isCardApply = isCardApply;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTradeContractId() {
		return tradeContractId;
	}

	public void setTradeContractId(String tradeContractId) {
		this.tradeContractId = tradeContractId;
	}

	public String getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	public void setLastPasswordChangeDate(String lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	public String getAppInstallDate() {
		return appInstallDate;
	}

	public void setAppInstallDate(String appInstallDate) {
		this.appInstallDate = appInstallDate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLastEmailChangeDate() {
		return lastEmailChangeDate;
	}

	public void setLastEmailChangeDate(String lastEmailChangeDate) {
		this.lastEmailChangeDate = lastEmailChangeDate;
	}

	public String getLastPhoneChangeDate() {
		return lastPhoneChangeDate;
	}

	public void setLastPhoneChangeDate(String lastPhoneChangeDate) {
		this.lastPhoneChangeDate = lastPhoneChangeDate;
	}

	public String getLastAddressChangeDate() {
		return lastAddressChangeDate;
	}

	public void setLastAddressChangeDate(String lastAddressChangeDate) {
		this.lastAddressChangeDate = lastAddressChangeDate;
	}

	public String getLastPinChangeDate() {
		return lastPinChangeDate;
	}

	public void setLastPinChangeDate(String lastPinChangeDate) {
		this.lastPinChangeDate = lastPinChangeDate;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "RegistrationServiceRequest{" +
				"orgCode='" + orgCode + '\'' +
				", orgId=" + orgId +
				", account=" + account +
				", sourceApplication='" + sourceApplication + '\'' +
				", cookieInfo='" + cookieInfo + '\'' +
				", deviceInfo=" + deviceInfo +
				", isBroadCastRequired=" + isBroadCastRequired +
				", event='" + event + '\'' +
				", oldOrgId=" + oldOrgId +
				", isCardApply='" + isCardApply + '\'' +
				", ipAddress='" + ipAddress + '\'' +
				", tradeContractId='" + tradeContractId + '\'' +
				", lastPasswordChangeDate=" + lastPasswordChangeDate +
				", appInstallDate=" + appInstallDate +
				", deviceId='" + deviceId + '\'' +
				", lastEmailChangeDate=" + lastEmailChangeDate +
				", lastPhoneChangeDate=" + lastPhoneChangeDate +
				", lastAddressChangeDate=" + lastAddressChangeDate +
				", lastPinChangeDate=" + lastPinChangeDate +
				", card=" + card +
				'}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationServiceRequest other = (RegistrationServiceRequest) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account)) {
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
	 * Validate signup.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateSignup() {
		FieldValidator validator = new FieldValidator();
		validator.mergeErrors(this.account.validateSignUp());
		if (this.deviceInfo != null) {
			// if date fields are empty or null set it to default date to pass validation
			if(null != deviceInfo.getOsDateAndTime() && !deviceInfo.getOsDateAndTime().isEmpty()){
				validator.isDateInFormat(new String[]{deviceInfo.getOsDateAndTime()}, 
						new String[]{"os_date_time"}, Constants.RFC_TIMESTAMP_FORMAT);
			}
			
			return validator;
		}
		return validator;
	}
	
	/**
	 * Validate signup.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateUpdateAccount() {
		FieldValidator validator = new FieldValidator();
		validator.isValidObject(new Object[]{orgCode},
				new String[]{"org_code"});
		validator.mergeErrors(this.account.validateUpdateAccount());
		if (this.deviceInfo != null) {
			// if date fields are empty or null set it to default date to pass validation
			if(null != deviceInfo.getOsDateAndTime() && !deviceInfo.getOsDateAndTime().isEmpty()){
				validator.isDateInFormat(new String[]{deviceInfo.getOsDateAndTime()}, 
						new String[]{"os_date_time"}, Constants.RFC_TIMESTAMP_FORMAT);
			}
			return validator;
		}
		return validator;
	}

}