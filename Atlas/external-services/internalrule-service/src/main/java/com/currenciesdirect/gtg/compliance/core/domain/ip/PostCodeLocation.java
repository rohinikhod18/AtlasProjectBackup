/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.ip;

import com.currenciesdirect.gtg.compliance.core.domain.IDomain;

/**
 * The Class PostCodeLocation.
 *
 * @author manish
 */
public class PostCodeLocation implements IDomain {

	/** The postcode. */
	private String postcode;

	/** The latitude. */
	private Double latitude;

	/** The longitude. */
	private Double longitude;

	/**
	 * Gets the postcode.
	 *
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Sets the postcode.
	 *
	 * @param postcode
	 *            the new postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude
	 *            the new latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude
	 *            the new longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
