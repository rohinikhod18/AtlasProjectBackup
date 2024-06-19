package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterEntity.
 */
public class FraugsterEntity implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fraugster id. */
	protected String fraugsterId;

	/** The gender. */
	protected String gender;

	/** The full name. */
	protected String fullName;

	/** The country. */
	protected String country;

	/** The dob. */
	protected String dob;

	/**
	 * Gets the fraugster id.
	 *
	 * @return the fraugsterId
	 */
	public String getfraugsterId() {
		return fraugsterId;
	}

	/**
	 * Sets the fraugster id.
	 *
	 * @param fraugsterId
	 *            the fraugsterId to set
	 */
	public void setFraugsterId(String fraugsterId) {
		this.fraugsterId = fraugsterId;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

}
