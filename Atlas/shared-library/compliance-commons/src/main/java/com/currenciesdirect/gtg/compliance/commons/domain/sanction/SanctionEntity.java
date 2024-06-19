package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

public abstract class SanctionEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150347178760315615L;

	/** The sanction id. */
	protected String sanctionId;

	/** The gender. */
	protected String gender;

	/** The full name. */
	protected String fullName;

	/** The country. */
	protected String country;

	/** The dob. */
	protected String dob;

	/**
	 * @return the sanctionId
	 */
	public String getSanctionId() {
		return sanctionId;
	}

	/**
	 * @param sanctionId the sanctionId to set
	 */
	public void setSanctionId(String sanctionId) {
		this.sanctionId = sanctionId;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	
}
