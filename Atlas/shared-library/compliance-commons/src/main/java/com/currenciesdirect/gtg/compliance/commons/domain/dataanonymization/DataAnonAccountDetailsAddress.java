package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonAccountDetailsAddress implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The address line. */
	@ApiModelProperty(value = "The address line", required = true)
	@JsonProperty("address_line")
	private String addressLine;
	
	@ApiModelProperty(value = "The city", required = true)
	@JsonProperty("city")
	private String city;
	
	@ApiModelProperty(value = "The postal_code", required = true)
	@JsonProperty("postal_code")
	private String postalCode;
	
	@ApiModelProperty(value = "The state", required = true)
	@JsonProperty("state")
	private String state;
	
	@ApiModelProperty(value = "The street", required = true)
	@JsonProperty("street")
	private String street;
	
	@ApiModelProperty(value = "The country", required = true)
	@JsonProperty("country")
	private String country;

	/**
	 * @return the addressLine
	 */
	public String getAddressLine() {
		return addressLine;
	}

	/**
	 * @param addressLine the addressLine to set
	 */
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
