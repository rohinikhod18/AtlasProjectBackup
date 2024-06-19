package com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionPostCardIPDeviceMQRequest implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The continent. */
	@JsonProperty(value = "continent")
	public String continent;
	
	/** The longitude. */
	@JsonProperty(value = "longitude")
	public String longitude;
	
	/** The latitiude. */
	@JsonProperty(value = "latitiude")
	public String latitiude;
	
	/** The region. */
	@JsonProperty(value = "region")
	public String region;
	
	/** The city. */
	@JsonProperty(value = "city")
	public String city;
	
	/** The timezone. */
	@JsonProperty(value = "timezone")
	public String timezone;
	
	/** The organization. */
	@JsonProperty(value = "organization")
	public String organization;
	
	/** The carrier. */
	@JsonProperty(value = "carrier")
	public String carrier;
	
	/** The connection type. */
	@JsonProperty(value = "connection_type")
	public String connectionType;
	
	/** The line speed. */
	@JsonProperty(value = "line_speed")
	public String lineSpeed;
	
	/** The ip routing type. */
	@JsonProperty(value = "ip_routing_type")
	public String ipRoutingType;
	
	/** The country name. */
	@JsonProperty(value = "country_name")
	public String countryName;
	
	/** The country code. */
	@JsonProperty(value = "country_code")
	public String countryCode;
	
	/** The state name. */
	@JsonProperty(value = "state_name")
	public String stateName;
	
	/** The state code. */
	@JsonProperty(value = "state_code")
	public String stateCode;
	
	/** The postal code. */
	@JsonProperty(value = "postal_code")
	public String postalCode;
	
	/** The area code. */
	@JsonProperty(value = "area_code")
	public String areaCode;
	
	/** The anonymizer status. */
	@JsonProperty(value = "anonymizer_status")
	public String anonymizerStatus;
	
	/** The ip address. */
	@JsonProperty(value = "ip_address")
	public String ipAddress;
	
	/** The device ID. */
	@JsonProperty(value = "device_ID")
	public String deviceID;
}
