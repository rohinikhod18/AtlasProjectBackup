/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.io.Serializable;

/**
 * The Class Identification.
 *
 * @author manish
 */
public class Identification implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private String type;

	/** The number. */
	private String number;

	/** The medicare ref number. */
	private String medicareRefNumber;

	/** The city of issue. */
	private String cityOfIssue;

	/** The country of issue. */
	private String countryOfIssue;

	/** The district of issue. */
	private String districtOfIssue;

	/** The state of issue. */
	private String stateOfIssue;

	/** The dl version number. */
	private String dlVersionNumber;

	/** The exiprydate. */
	private String exiprydate;

	/** The passport full name. */
	private String passportFullName;

	/** The passport MRZ line 1. */
	private String passportMRZLine1;

	/** The passport MRZ line 2. */
	private String passportMRZLine2;

	/** The family name at birth. */
	private String familyNameAtBirth;

	/** The family name at citizenship. */
	private String familyNameAtCitizenship;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number
	 *            the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the city of issue.
	 *
	 * @return the city of issue
	 */
	public String getCityOfIssue() {
		return cityOfIssue;
	}

	/**
	 * Sets the city of issue.
	 *
	 * @param cityOfIssue
	 *            the new city of issue
	 */
	public void setCityOfIssue(String cityOfIssue) {
		this.cityOfIssue = cityOfIssue;
	}

	/**
	 * Gets the country of issue.
	 *
	 * @return the country of issue
	 */
	public String getCountryOfIssue() {
		return countryOfIssue;
	}

	/**
	 * Sets the country of issue.
	 *
	 * @param countryOfIssue
	 *            the new country of issue
	 */
	public void setCountryOfIssue(String countryOfIssue) {
		this.countryOfIssue = countryOfIssue;
	}

	/**
	 * Gets the district of issue.
	 *
	 * @return the district of issue
	 */
	public String getDistrictOfIssue() {
		return districtOfIssue;
	}

	/**
	 * Sets the district of issue.
	 *
	 * @param districtOfIssue
	 *            the new district of issue
	 */
	public void setDistrictOfIssue(String districtOfIssue) {
		this.districtOfIssue = districtOfIssue;
	}

	/**
	 * Gets the state of issue.
	 *
	 * @return the state of issue
	 */
	public String getStateOfIssue() {
		return stateOfIssue;
	}

	/**
	 * Sets the state of issue.
	 *
	 * @param stateOfIssue
	 *            the new state of issue
	 */
	public void setStateOfIssue(String stateOfIssue) {
		this.stateOfIssue = stateOfIssue;
	}

	/**
	 * Gets the dl version number.
	 *
	 * @return the dl version number
	 */
	public String getDlVersionNumber() {
		return dlVersionNumber;
	}

	/**
	 * Sets the dl version number.
	 *
	 * @param dlVersionNumber
	 *            the new dl version number
	 */
	public void setDlVersionNumber(String dlVersionNumber) {
		this.dlVersionNumber = dlVersionNumber;
	}

	/**
	 * Gets the exiprydate.
	 *
	 * @return the exiprydate
	 */
	public String getExiprydate() {
		return exiprydate;
	}

	/**
	 * Sets the exiprydate.
	 *
	 * @param exiprydate
	 *            the new exiprydate
	 */
	public void setExiprydate(String exiprydate) {
		this.exiprydate = exiprydate;
	}

	/**
	 * Gets the passport full name.
	 *
	 * @return the passport full name
	 */
	public String getPassportFullName() {
		return passportFullName;
	}

	/**
	 * Sets the passport full name.
	 *
	 * @param passportFullName
	 *            the new passport full name
	 */
	public void setPassportFullName(String passportFullName) {
		this.passportFullName = passportFullName;
	}

	/**
	 * Gets the passport MRZ line 1.
	 *
	 * @return the passport MRZ line 1
	 */
	public String getPassportMRZLine1() {
		return passportMRZLine1;
	}

	/**
	 * Sets the passport MRZ line 1.
	 *
	 * @param passportMRZLine1
	 *            the new passport MRZ line 1
	 */
	public void setPassportMRZLine1(String passportMRZLine1) {
		this.passportMRZLine1 = passportMRZLine1;
	}

	/**
	 * Gets the passport MRZ line 2.
	 *
	 * @return the passport MRZ line 2
	 */
	public String getPassportMRZLine2() {
		return passportMRZLine2;
	}

	/**
	 * Sets the passport MRZ line 2.
	 *
	 * @param passportMRZLine2
	 *            the new passport MRZ line 2
	 */
	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
	}

	/**
	 * Gets the family name at birth.
	 *
	 * @return the family name at birth
	 */
	public String getFamilyNameAtBirth() {
		return familyNameAtBirth;
	}

	/**
	 * Sets the family name at birth.
	 *
	 * @param familyNameAtBirth
	 *            the new family name at birth
	 */
	public void setFamilyNameAtBirth(String familyNameAtBirth) {
		this.familyNameAtBirth = familyNameAtBirth;
	}

	/**
	 * Gets the family name at citizenship.
	 *
	 * @return the family name at citizenship
	 */
	public String getFamilyNameAtCitizenship() {
		return familyNameAtCitizenship;
	}

	/**
	 * Sets the family name at citizenship.
	 *
	 * @param familyNameAtCitizenship
	 *            the new family name at citizenship
	 */
	public void setFamilyNameAtCitizenship(String familyNameAtCitizenship) {
		this.familyNameAtCitizenship = familyNameAtCitizenship;
	}

	/**
	 * Gets the medicare ref number.
	 *
	 * @return the medicare ref number
	 */
	public String getMedicareRefNumber() {
		return medicareRefNumber;
	}

	/**
	 * Sets the medicare ref number.
	 *
	 * @param medicareRefNumber
	 *            the new medicare ref number
	 */
	public void setMedicareRefNumber(String medicareRefNumber) {
		this.medicareRefNumber = medicareRefNumber;
	}

}
