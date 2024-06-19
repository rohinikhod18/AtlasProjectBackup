package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IntuitionSignupIPDeviceProviderRequest  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "device_id")
	private String deviceId;
	
	@JsonProperty(value = "continent")
	private String continent;

	@JsonProperty(value = "longitude")
	private Double longitude;
	
	@JsonProperty(value = "latitiude")
	private Double latitiude;
	
	@JsonProperty(value = "region")
	private String region;
	
	@JsonProperty(value = "city")
	private String city;
	
	@JsonProperty(value = "timezone")
	private String timezone;
	
	@JsonProperty(value = "organization")
	private String organization;
	
	@JsonProperty(value = "carrier")
	private String carrier;
	
	@JsonProperty(value = "connection_type")
	private String connectionType;
	
	@JsonProperty(value = "line_speed")
	private String lineSpeed;
	
	@JsonProperty(value = "ip_routing_type")
	private String ipRoutingType;
	
	@JsonProperty(value = "country_name")
	private String countryName;
	
	@JsonProperty(value = "country_code")
	private String countryCode;
	
	@JsonProperty(value = "state_name")
	private String stateName;
	
	@JsonProperty(value = "state_code")
	private String stateCode;
	
	@JsonProperty(value = "postal_code")
	private String postalCode;
	
	@JsonProperty(value = "area_code")
	private String areaCode;
	
	@JsonProperty(value = "anonymizer_status")
	private String anonymizerStatus;
	
	@JsonProperty(value = "ip_address")
	private String ipAddress;

    @JsonProperty(value = "contactIP")
    private String contactIp;

}
