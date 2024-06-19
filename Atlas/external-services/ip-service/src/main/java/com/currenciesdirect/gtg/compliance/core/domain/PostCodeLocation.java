/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * @author manish
 *
 */
public class PostCodeLocation implements IDomain{
	
	private String postcode;
	
	private Double latitude;
	
	private Double longitude;

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	

}
