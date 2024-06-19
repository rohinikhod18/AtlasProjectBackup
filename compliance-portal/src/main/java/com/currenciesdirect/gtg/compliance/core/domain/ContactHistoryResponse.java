package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class Contact.
 */
public class ContactHistoryResponse {

	/** The id. */
	private Integer id;

	/** The account id. */
	private Integer accountId;

	/** The crm account id. */
	private String crmAccountId;

	/** The crm contact id. */
	private String crmContactId;
	
	/** The cc crm contact id. */
	private String ccCrmContactId;

	/** The trade contact id. */
	private String tradeContactId;

	/** The compliance status. */
	private String complianceStatus;

	/** The country of residence. */
	private String countryOfResidence;

	/** The occupation. */
	private String occupation;

	/** The email. */
	private String email;

	/** The name. */
	private String name;

	/** The address. */
	private String address;
	
	/** The address type. */
	private String addressType;

	/** The phone. */
	private String phone;

	/** The mobile. */
	private String mobile;

	/** The nationality. */
	private String nationality;

	/** The reg in. */
	private String regIn;

	/** The reg complete. */
	private String regComplete;
	
	/** The ip address. */
	private String ipAddress;

	/** The is us client. */
	private Boolean isUsClient;
	
	/** The position of significance. */
	private String  positionOfSignificance;
	
	/** The authorised signatory. */
	private String  authorisedSignatory;
	
	/** The job title. */
	private String  jobTitle;
	
	/** The designation. */
	private String designation;
	
	/** The is primary contact. */
	private Boolean isPrimaryContact;
	
	/** The cust type. */
	private String custType;
	
	/** The organization. */
	private String organization;
	
	/** The is country supported. */
	private Boolean isCountrySupported;
	
	/**The Date of Birth. */
	private String dateofbirth;
	
	/**The work Phone */
	private String workphone;
	
	/**The work Phone Ext */
	private String workphoneext;
	
	private String australiaRTACardNumber;
	
	private String areaNumber;
	
	private String aza;
	
	private String buildingNumber; 
	
	private String civicNumber; 
	
	private String countryOfBirth; 
	
	private String district;
	
	private String postCode;
	
	private String prefName;

	private String prefecture;

	private String primaryPhoneNumber;

	private String regionSuburb;

	private String residentialStatus;

	private String secondEmail;

	private String secondSurname;

	private String stateOfBirth;

	private String stateProvinceCounty;

	private String street;

	private String streetNumber;

	private String streetType;

	private String subBuilding;

	private String subCity;

	private String title;

	private String townCityMuncipalty;

	private String unitNumber;

	private String yearsInAddress;
	
	private String dlLicenseNumber;
	
	
	private String dlExpiryDate; 
	
	
	private String dlCardNumber; 
	
	
	private String dlCountryCode; 
	
	
	private String dlStateCode; 
	
	
	private String dlVersionNumber;
	
	
	private String firstName ; 
	
	
	private String floorNumber;
	
	
	private String gender;
	
	
	private String phoneHome;
	
	/** The lastName */
	private String lastName ; 
	
	/** The medicareCardNumber */
	private String medicareCardNumber; 
	
	/** The medicareReferenceNumber */
	private String medicareReferenceNumber; 
	
	/** The middleName */
	private String middleName ; 
	
	/** The mothersSurname */
	private String mothersSurname;
	
	/** The municipality of birth. */
	private String municipalityOfBirth; 

	/** The national id number. */
	private String nationalIdNumber;
	
	/** The national id type. */
	private String nationalIdType;
	
	/** The passport family name at birth. */
	private String passportFamilyNameAtBirth; 
	
	/** The passport place of birth. */
	private String passportPlaceOfBirth; 
	
	/** The passport country code. */
	private String passportCountryCode; 
	
	/** The passport exiprydate. */
	private String passportExiprydate;
	
	/** The passport full name. */
	private String passportFullName;
	
	/** The passport MRZ line 1. */
	private String passportMRZLine1;
	
	/** The passport MRZ line 2. */
	private String passportMRZLine2;
	
	/** The passport name at citizenship. */
	private String passportNameAtCitizenship; 
	
	/** The passport number. */
	private String passportNumber; 
	
	/** The is all fields empty. */
	private Boolean isAllFieldsEmpty;
	
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/**
	 * Gets the compliance status.
	 *
	 * @return the compliance status
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus
	 *            the new compliance status
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
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
	 * @param countryOfResidence
	 *            the new country of residence
	 */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * Gets the occupation.
	 *
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * Sets the occupation.
	 *
	 * @param occupation
	 *            the new occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
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
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
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
	 * @param address
	 *            the new address
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * Gets the mobile.
	 *
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 *
	 * @param mobile
	 *            the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 * @param nationality
	 *            the new nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the crm account id.
	 *
	 * @return the crm account id
	 */
	public String getCrmAccountId() {
		return crmAccountId;
	}

	/**
	 * Sets the crm account id.
	 *
	 * @param crmAccountId
	 *            the new crm account id
	 */
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	/**
	 * Gets the crm contact id.
	 *
	 * @return the crm contact id
	 */
	public String getCrmContactId() {
		return crmContactId;
	}

	/**
	 * Sets the crm contact id.
	 *
	 * @param crmContactId
	 *            the new crm contact id
	 */
	public void setCrmContactId(String crmContactId) {
		this.crmContactId = crmContactId;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	public String getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId
	 *            the new trade contact id
	 */
	public void setTradeContactId(String tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Gets the reg in.
	 *
	 * @return the reg in
	 */
	public String getRegIn() {
		return regIn;
	}

	/**
	 * Sets the reg in.
	 *
	 * @param regIn
	 *            the new reg in
	 */
	public void setRegIn(String regIn) {
		this.regIn = regIn;
	}

	/**
	 * Gets the reg complete.
	 *
	 * @return the reg complete
	 */
	public String getRegComplete() {
		return regComplete;
	}

	/**
	 * Sets the reg complete.
	 *
	 * @param regComplete
	 *            the new reg complete
	 */
	public void setRegComplete(String regComplete) {
		this.regComplete = regComplete;
	}

	/**
	 * Gets the checks if is us client.
	 *
	 * @return the checks if is us client
	 */
	public Boolean getIsUsClient() {
		return isUsClient;
	}

	/**
	 * Sets the checks if is us client.
	 *
	 * @param isUsClient the new checks if is us client
	 */
	public void setIsUsClient(Boolean isUsClient) {
		this.isUsClient = isUsClient;
	}

	/**
	 * Gets the position of significance.
	 *
	 * @return the position of significance
	 */
	public String getPositionOfSignificance() {
		return positionOfSignificance;
	}

	/**
	 * Sets the position of significance.
	 *
	 * @param positionOfSignificance the new position of significance
	 */
	public void setPositionOfSignificance(String positionOfSignificance) {
		this.positionOfSignificance = positionOfSignificance;
	}

	/**
	 * Gets the authorised signatory.
	 *
	 * @return the authorised signatory
	 */
	public String getAuthorisedSignatory() {
		return authorisedSignatory;
	}

	/**
	 * Sets the authorised signatory.
	 *
	 * @param authorisedSignatory the new authorised signatory
	 */
	public void setAuthorisedSignatory(String authorisedSignatory) {
		this.authorisedSignatory = authorisedSignatory;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the designation.
	 *
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * Sets the designation.
	 *
	 * @param designation the new designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * Gets the checks if is primary contact.
	 *
	 * @return the checks if is primary contact
	 */
	public Boolean getIsPrimaryContact() {
		return isPrimaryContact;
	}

	/**
	 * Sets the checks if is primary contact.
	 *
	 * @param isPrimaryContact the new checks if is primary contact
	 */
	public void setIsPrimaryContact(Boolean isPrimaryContact) {
		this.isPrimaryContact = isPrimaryContact;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the address type.
	 *
	 * @return the address type
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * Sets the address type.
	 *
	 * @param addressType the new address type
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Gets the cc crm contact id.
	 *
	 * @return the cc crm contact id
	 */
	public String getCcCrmContactId() {
		return ccCrmContactId;
	}

	/**
	 * Sets the cc crm contact id.
	 *
	 * @param ccCrmContactId the new cc crm contact id
	 */
	public void setCcCrmContactId(String ccCrmContactId) {
		this.ccCrmContactId = ccCrmContactId;
	}

	/**
	 * Gets the checks if is country supported.
	 *
	 * @return the checks if is country supported
	 */
	public Boolean getIsCountrySupported() {
		return isCountrySupported;
	}

	/**
	 * Sets the checks if is country supported.
	 *
	 * @param isCountrySupported the new checks if is country supported
	 */
	public void setIsCountrySupported(Boolean isCountrySupported) {
		this.isCountrySupported = isCountrySupported;
	}

	/**
	 * @return the dateofbirth
	 */
	public String getDateofbirth() {
		return dateofbirth;
	}

	/**
	 * @param dateofbirth the dateofbirth to set
	 */
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	/**
	 * @return the workphone
	 */
	public String getWorkphone() {
		return workphone;
	}

	/**
	 * @param workphone the workphone to set
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * @return the workphoneext
	 */
	public String getWorkphoneext() {
		return workphoneext;
	}

	/**
	 * @param workphoneext the workphoneext to set
	 */
	public void setWorkphoneext(String workphoneext) {
		this.workphoneext = workphoneext;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the isAllFieldsEmpty
	 */
	public Boolean getIsAllFieldsEmpty() {
		return isAllFieldsEmpty;
	}

	/**
	 * @param isAllFieldsEmpty the isAllFieldsEmpty to set
	 */
	public void setIsAllFieldsEmpty(Boolean isAllFieldsEmpty) {
		this.isAllFieldsEmpty = isAllFieldsEmpty;
	}

	public String getAustraliaRTACardNumber() {
		return australiaRTACardNumber;
	}

	public void setAustraliaRTACardNumber(String australiaRTACardNumber) {
		this.australiaRTACardNumber = australiaRTACardNumber;
	}

	public String getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	public String getAza() {
		return aza;
	}

	public void setAza(String aza) {
		this.aza = aza;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getCivicNumber() {
		return civicNumber;
	}

	public void setCivicNumber(String civicNumber) {
		this.civicNumber = civicNumber;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}

	public String getRegionSuburb() {
		return regionSuburb;
	}

	public void setRegionSuburb(String regionSuburb) {
		this.regionSuburb = regionSuburb;
	}

	public String getResidentialStatus() {
		return residentialStatus;
	}

	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	public String getSecondEmail() {
		return secondEmail;
	}

	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public String getStateOfBirth() {
		return stateOfBirth;
	}

	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	public String getStateProvinceCounty() {
		return stateProvinceCounty;
	}

	public void setStateProvinceCounty(String stateProvinceCounty) {
		this.stateProvinceCounty = stateProvinceCounty;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	public String getSubBuilding() {
		return subBuilding;
	}

	public void setSubBuilding(String subBuilding) {
		this.subBuilding = subBuilding;
	}

	public String getSubCity() {
		return subCity;
	}

	public void setSubCity(String subCity) {
		this.subCity = subCity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTownCityMuncipalty() {
		return townCityMuncipalty;
	}

	public void setTownCityMuncipalty(String townCityMuncipalty) {
		this.townCityMuncipalty = townCityMuncipalty;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getYearsInAddress() {
		return yearsInAddress;
	}

	public void setYearsInAddress(String yearsInAddress) {
		this.yearsInAddress = yearsInAddress;
	}

	public String getDlLicenseNumber() {
		return dlLicenseNumber;
	}

	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
	}

	public String getDlExpiryDate() {
		return dlExpiryDate;
	}

	public void setDlExpiryDate(String dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}

	public String getDlCardNumber() {
		return dlCardNumber;
	}

	public void setDlCardNumber(String dlCardNumber) {
		this.dlCardNumber = dlCardNumber;
	}

	public String getDlCountryCode() {
		return dlCountryCode;
	}

	public void setDlCountryCode(String dlCountryCode) {
		this.dlCountryCode = dlCountryCode;
	}

	public String getDlStateCode() {
		return dlStateCode;
	}

	public void setDlStateCode(String dlStateCode) {
		this.dlStateCode = dlStateCode;
	}

	public String getDlVersionNumber() {
		return dlVersionNumber;
	}

	public void setDlVersionNumber(String dlVersionNumber) {
		this.dlVersionNumber = dlVersionNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneHome() {
		return phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMedicareCardNumber() {
		return medicareCardNumber;
	}

	public void setMedicareCardNumber(String medicareCardNumber) {
		this.medicareCardNumber = medicareCardNumber;
	}

	public String getMedicareReferenceNumber() {
		return medicareReferenceNumber;
	}

	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMothersSurname() {
		return mothersSurname;
	}

	public void setMothersSurname(String mothersSurname) {
		this.mothersSurname = mothersSurname;
	}

	public String getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}

	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}

	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	public String getNationalIdType() {
		return nationalIdType;
	}

	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
	}

	public String getPassportFamilyNameAtBirth() {
		return passportFamilyNameAtBirth;
	}

	public void setPassportFamilyNameAtBirth(String passportFamilyNameAtBirth) {
		this.passportFamilyNameAtBirth = passportFamilyNameAtBirth;
	}

	public String getPassportPlaceOfBirth() {
		return passportPlaceOfBirth;
	}

	public void setPassportPlaceOfBirth(String passportPlaceOfBirth) {
		this.passportPlaceOfBirth = passportPlaceOfBirth;
	}

	public String getPassportCountryCode() {
		return passportCountryCode;
	}

	public void setPassportCountryCode(String passportCountryCode) {
		this.passportCountryCode = passportCountryCode;
	}

	public String getPassportExiprydate() {
		return passportExiprydate;
	}

	public void setPassportExiprydate(String passportExiprydate) {
		this.passportExiprydate = passportExiprydate;
	}

	public String getPassportFullName() {
		return passportFullName;
	}

	public void setPassportFullName(String passportFullName) {
		this.passportFullName = passportFullName;
	}

	public String getPassportMRZLine1() {
		return passportMRZLine1;
	}

	public void setPassportMRZLine1(String passportMRZLine1) {
		this.passportMRZLine1 = passportMRZLine1;
	}

	public String getPassportMRZLine2() {
		return passportMRZLine2;
	}

	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
	}

	public String getPassportNameAtCitizenship() {
		return passportNameAtCitizenship;
	}

	public void setPassportNameAtCitizenship(String passportNameAtCitizenship) {
		this.passportNameAtCitizenship = passportNameAtCitizenship;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	
}
