package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Class CustomerInsertRequestData.
 */
public class CustomerInsertRequestData implements IRequest {

	/** The id. */
	private String id;
	
	/** The org code. */
	private String orgCode;
	
	/** The sf account id. */
	private String sfAccountID;
	
	/** The aurora account id. */
	private String auroraAccountID;
	
	/** The name. */
	private Name name;
	
	/** The address. */
	private String address;
	
	/** The nationality. */
	private String nationality;
	
	/** The country of residence. */
	private String countryOfResidence;
	
	/** The place of birth. */
	private String placeOfBirth;
	
	/** The family name at birth. */
	private String familyNameAtBirth;
	
	/** The family name at citizenship. */
	private String familyNameAtCitizenship;
	
	/** The gender. */
	private String gender;
	
	/** The dob day. */
	private String dobDay;
	
	/** The dob month. */
	private String dobMonth;
	
	/** The dob year. */
	private String dobYear;
	
	/** The phone. */
	private String phone;
	
	/** The fax. */
	private String fax;
	
	/** The email. */
	private String email;
	
	/** The ip address. */
	private String ipAddress;
	
	/** The digital foot print. */
	private DigitalFootPrint digitalFootPrint;
	
	/** The user name. */
	private String userName;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the sf account ID.
	 *
	 * @return the sf account ID
	 */
	public String getSfAccountID() {
		return sfAccountID;
	}

	/**
	 * Sets the sf account ID.
	 *
	 * @param sfAccountID the new sf account ID
	 */
	public void setSfAccountID(String sfAccountID) {
		this.sfAccountID = sfAccountID;
	}

	/**
	 * Gets the aurora account ID.
	 *
	 * @return the aurora account ID
	 */
	public String getAuroraAccountID() {
		return auroraAccountID;
	}

	/**
	 * Sets the aurora account ID.
	 *
	 * @param auroraAccountID the new aurora account ID
	 */
	public void setAuroraAccountID(String auroraAccountID) {
		this.auroraAccountID = auroraAccountID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality the new nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the country of residence.
	 *
	 * @return the country of residence
	 */
	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	/**
	 * Sets the country of residence.
	 *
	 * @param countryOfResidence the new country of residence
	 */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * Gets the place of birth.
	 *
	 * @return the place of birth
	 */
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	/**
	 * Sets the place of birth.
	 *
	 * @param placeOfBirth the new place of birth
	 */
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
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
	 * @param familyNameAtBirth the new family name at birth
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
	 * @param familyNameAtCitizenship the new family name at citizenship
	 */
	public void setFamilyNameAtCitizenship(String familyNameAtCitizenship) {
		this.familyNameAtCitizenship = familyNameAtCitizenship;
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
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the dob day.
	 *
	 * @return the dob day
	 */
	public String getDobDay() {
		return dobDay;
	}

	/**
	 * Sets the dob day.
	 *
	 * @param dobDay the new dob day
	 */
	public void setDobDay(String dobDay) {
		this.dobDay = dobDay;
	}

	/**
	 * Gets the dob month.
	 *
	 * @return the dob month
	 */
	public String getDobMonth() {
		return dobMonth;
	}

	/**
	 * Sets the dob month.
	 *
	 * @param dobMonth the new dob month
	 */
	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}

	/**
	 * Gets the dob year.
	 *
	 * @return the dob year
	 */
	public String getDobYear() {
		return dobYear;
	}

	/**
	 * Sets the dob year.
	 *
	 * @param dobYear the new dob year
	 */
	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
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
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the fax.
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax the new fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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
	 * Gets the digital foot print.
	 *
	 * @return the digital foot print
	 */
	public DigitalFootPrint getDigitalFootPrint() {
		return digitalFootPrint;
	}

	/**
	 * Sets the digital foot print.
	 *
	 * @param digitalFootPrint the new digital foot print
	 */
	public void setDigitalFootPrint(DigitalFootPrint digitalFootPrint) {
		this.digitalFootPrint = digitalFootPrint;
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auroraAccountID == null) ? 0 : auroraAccountID.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((sfAccountID == null) ? 0 : sfAccountID.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerInsertRequestData other = (CustomerInsertRequestData) obj;
		if (auroraAccountID == null) {
			if (other.auroraAccountID != null)
				return false;
		} else if (!auroraAccountID.equals(other.auroraAccountID))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (sfAccountID == null) {
			if (other.sfAccountID != null)
				return false;
		} else if (!sfAccountID.equals(other.sfAccountID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerSearchData [sfAccountID=" + sfAccountID + ", auroraAccountID=" + auroraAccountID + ", name="
				+ name + ", address=" + address + ", nationality=" + nationality + ", countryOfResidence="
				+ countryOfResidence + ", placeOfBirth=" + placeOfBirth + ", familyNameAtBirth=" + familyNameAtBirth
				+ ", familyNameAtCitizenship=" + familyNameAtCitizenship + ", gender=" + gender + ", dobDay=" + dobDay
				+ ", dobMonth=" + dobMonth + ", dobYear=" + dobYear + ", phone=" + phone + ", fax=" + fax + ", email="
				+ email + ", ipAddress=" + ipAddress + ", digitalFootPrint=" + digitalFootPrint + "]";
	}

}
