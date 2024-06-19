/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterSignupContactRequest.
 *
 * @author manish
 */
public class FraugsterSignupContactRequest extends FraugsterProfileBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The second email. */
	private String secondEmail;

	/** The code latitude. */
	private Float postCodeLatitude;

	/** The post code longitude. */
	private Float postCodeLongitude;

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
	 * Gets the second email.
	 *
	 * @return the second email
	 */
	public String getSecondEmail() {
		return secondEmail;
	}

	/**
	 * Sets the second email.
	 *
	 * @param secondEmail
	 *            the new second email
	 */
	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

}
