package com.currenciesdirect.gtg.compliance.core.domain.ip;

import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;


/**
 * The Class IpServiceRequest.
 */
public class IpServiceRequest implements IRequest{


	
	/** The ip address. */
	private String ipAddress;
	
	/** The country. */
	private String country;
	
	/** The post code. */
	private String postalCode;

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}


	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
