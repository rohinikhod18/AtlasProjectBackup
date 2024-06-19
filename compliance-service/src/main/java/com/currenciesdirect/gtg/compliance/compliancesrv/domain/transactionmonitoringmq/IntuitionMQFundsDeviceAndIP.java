package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionMQFundsDeviceAndIP {

	@JsonProperty(value = "brwsr_type")
	private String brwsrType;
	
	@JsonProperty(value = "brwsr_version")
	private String brwsrVersion;
	
	@JsonProperty(value = "brwsr_lang")
	private String brwsrLang;
	
	@JsonProperty(value = "browser_online")
	private String browserOnline;
	
	@JsonProperty(value = "version")
	private String version;
	
	@JsonProperty(value = "status_update_reason")
	private String statusUpdateReason;
	
	@JsonProperty(value = "device_name")
	private String deviceName;
	
	@JsonProperty(value = "device_version")
	private String deviceVersion;
	
	@JsonProperty(value = "device_id")
	private String deviceId;
	
	@JsonProperty(value = "device_manufacturer")
	private String deviceManufacturer;
	
	@JsonProperty(value = "device_type")
	private String deviceType;
	
	@JsonProperty(value = "os_type")
	private String osType;
	
	@JsonProperty(value = "screen_resolution")
	private String screenResolution;
}
