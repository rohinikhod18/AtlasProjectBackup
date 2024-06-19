package com.currenciesdirect.gtg.compliance.customerdatascan.core.domain;

/**
 * The Class CustomerSearchData.
 */
public class CustomerSearchRequestData implements IRequest {

	
	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSfAccountID() {
		return sfAccountID;
	}

	public void setSfAccountID(String sfAccountID) {
		this.sfAccountID = sfAccountID;
	}

	public String getAuroraAccountID() {
		return auroraAccountID;
	}

	public void setAuroraAccountID(String auroraAccountID) {
		this.auroraAccountID = auroraAccountID;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getFamilyNameAtBirth() {
		return familyNameAtBirth;
	}

	public void setFamilyNameAtBirth(String familyNameAtBirth) {
		this.familyNameAtBirth = familyNameAtBirth;
	}

	public String getFamilyNameAtCitizenship() {
		return familyNameAtCitizenship;
	}

	public void setFamilyNameAtCitizenship(String familyNameAtCitizenship) {
		this.familyNameAtCitizenship = familyNameAtCitizenship;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDobDay() {
		return dobDay;
	}

	public void setDobDay(String dobDay) {
		this.dobDay = dobDay;
	}

	public String getDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}

	public String getDobYear() {
		return dobYear;
	}

	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public DigitalFootPrint getDigitalFootPrint() {
		return digitalFootPrint;
	}

	public void setDigitalFootPrint(DigitalFootPrint digitalFootPrint) {
		this.digitalFootPrint = digitalFootPrint;
	}
	
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerSearchRequestData other = (CustomerSearchRequestData) obj;
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
