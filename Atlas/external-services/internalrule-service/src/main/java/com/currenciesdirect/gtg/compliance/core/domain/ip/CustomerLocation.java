package com.currenciesdirect.gtg.compliance.core.domain.ip;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class CustomerLocation.
 */
@SuppressWarnings({"squid:S100","squid:S116", "squid:S117"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CustomerLocation")
public class CustomerLocation implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ip_address. */
	private String ip_address;

	/** The postal_code. */
	private String postal_code;

	/** The country_id. */
	private String ip_country_code;

	/** The latitude. */
	private String latitude;

	/** The longitude. */
	private String longitude;
	
	/** The ip country. */
	private String ip_country;
	
	/** The ip anonymizer status. */
	private String ip_anonymizer_status;
	
	/** The ip city. */
	private String ip_city;
	
	/** The ip city cf. */
	private String ip_city_cf;
	
	/** The ip routing type. */
	private String ip_routing_type;
	
	

	/**
	 * Gets the ip_address.
	 * 
	 * @return the ip_address
	 */
	public String getIp_address() {
		return ip_address;
	}

	/**
	 * Sets the ip_address.
	 * 
	 * @param ip_address
	 *            the ip_address to set
	 */
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	/**
	 * Gets the postal_code.
	 * 
	 * @return the postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}

	/**
	 * Sets the postal_code.
	 * 
	 * @param postal_code
	 *            the postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the ip country code.
	 *
	 * @return the ip_country_code
	 */
	public String getIp_country_code() {
		return ip_country_code;
	}

	/**
	 * Sets the ip country code.
	 *
	 * @param ip_country_code the ip_country_code to set
	 */
	public void setIp_country_code(String ip_country_code) {
		this.ip_country_code = ip_country_code;
	}

	/**
	 * Gets the ip country.
	 *
	 * @return the ip_country
	 */
	public String getIp_country() {
		return ip_country;
	}

	/**
	 * Sets the ip country.
	 *
	 * @param ip_country the ip_country to set
	 */
	public void setIp_country(String ip_country) {
		this.ip_country = ip_country;
	}

	/**
	 * Gets the ip anonymizer status.
	 *
	 * @return the ip_anonymizer_status
	 */
	public String getIp_anonymizer_status() {
		return ip_anonymizer_status;
	}

	/**
	 * Sets the ip anonymizer status.
	 *
	 * @param ip_anonymizer_status the ip_anonymizer_status to set
	 */
	public void setIp_anonymizer_status(String ip_anonymizer_status) {
		this.ip_anonymizer_status = ip_anonymizer_status;
	}

	/**
	 * Gets the ip city.
	 *
	 * @return the ip_city
	 */
	public String getIp_city() {
		return ip_city;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ip_city the ip_city to set
	 */
	public void setIp_city(String ip_city) {
		this.ip_city = ip_city;
	}

	/**
	 * Gets the ip city cf.
	 *
	 * @return the ip_city_cf
	 */
	public String getIp_city_cf() {
		return ip_city_cf;
	}

	/**
	 * Sets the ip city cf.
	 *
	 * @param ip_city_cf the ip_city_cf to set
	 */
	public void setIp_city_cf(String ip_city_cf) {
		this.ip_city_cf = ip_city_cf;
	}

	/**
	 * Gets the ip routing type.
	 *
	 * @return the ip_routing_type
	 */
	public String getIp_routing_type() {
		return ip_routing_type;
	}

	/**
	 * Sets the ip routing type.
	 *
	 * @param ip_routing_type the ip_routing_type to set
	 */
	public void setIp_routing_type(String ip_routing_type) {
		this.ip_routing_type = ip_routing_type;
	}

}
