package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterSignupProviderRequest.
 * 
 * @author abhijitg
 */
public class FraugsterSignupProviderRequest extends FraugsterProfileProviderBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The post code latitude. */
	@JsonProperty("post_code_latitude")
	private Float postCodeLatitude;

	/** The post code longitude. */
	@JsonProperty("post_code_longitude")
	private Float postCodeLongitude;

	/** The phone. */
	@JsonProperty("phone")
	private String phone;

	/** The cust scndry email. */
	@JsonProperty("cust_scndry_email")
	private String custScndryEmail;

	/**
	 * Gets the post code latitude.
	 *
	 * @return the post code latitude
	 */
	public Float getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude
	 *            the new post code latitude
	 */
	public void setPostCodeLatitude(Float postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the post code longitude
	 */
	public Float getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude
	 *            the new post code longitude
	 */
	public void setPostCodeLongitude(Float postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the cust scndry email.
	 *
	 * @return the cust scndry email
	 */
	public String getCustScndryEmail() {
		return custScndryEmail;
	}

	/**
	 * Sets the cust scndry email.
	 *
	 * @param custScndryEmail
	 *            the new cust scndry email
	 */
	public void setCustScndryEmail(String custScndryEmail) {
		this.custScndryEmail = custScndryEmail;
	}

}
