package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IPAddressDetails extends ServiceMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
    
    @JsonProperty(value = "connectionType")
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

	/**
	 * @return the continent
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * @param continent the continent to set
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitiude
	 */
	public Double getLatitiude() {
		return latitiude;
	}

	/**
	 * @param latitiude the latitiude to set
	 */
	public void setLatitiude(Double latitiude) {
		this.latitiude = latitiude;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the connectionType
	 */
	public String getConnectionType() {
		return connectionType;
	}

	/**
	 * @param connectionType the connectionType to set
	 */
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * @return the lineSpeed
	 */
	public String getLineSpeed() {
		return lineSpeed;
	}

	/**
	 * @param lineSpeed the lineSpeed to set
	 */
	public void setLineSpeed(String lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	/**
	 * @return the ipRoutingType
	 */
	public String getIpRoutingType() {
		return ipRoutingType;
	}

	/**
	 * @param ipRoutingType the ipRoutingType to set
	 */
	public void setIpRoutingType(String ipRoutingType) {
		this.ipRoutingType = ipRoutingType;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the anonymizerStatus
	 */
	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	/**
	 * @param anonymizerStatus the anonymizerStatus to set
	 */
	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	

	/**
	 * @return the contactIp
	 */
	public String getContactIp() {
		return contactIp;
	}

	/**
	 * @param contactIp the contactIp to set
	 */
	public void setContactIp(String contactIp) {
		this.contactIp = contactIp;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
