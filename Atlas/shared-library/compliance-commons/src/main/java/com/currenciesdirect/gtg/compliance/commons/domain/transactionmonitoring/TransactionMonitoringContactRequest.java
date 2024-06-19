package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class TransactionMonitoringContactRequest.
 */
public class TransactionMonitoringContactRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Integer id;
	
	/** The trade acc number. */
	@JsonProperty(value = "trade_acc_number")
	private String tradeAccNumber;
	
	/** The contact SFID. */
	@JsonProperty(value = "contact_sf_id")
	private String contactSFID;

	/** The trade contact ID. */
	@JsonProperty(value = "trade_contact_id")
	private Integer tradeContactID;

	/** The title. */
	@JsonProperty(value = "title")
	private String title;

	/** The preferred name. */
	@JsonProperty(value = "pref_name")
	private String preferredName;

	/** The first name. */
	@JsonProperty(value = "first_name")
	private String firstName;

	/** The middle name. */
	@JsonProperty(value = "middle_name")
	private String middleName;

	/** The last name. */
	@JsonProperty(value = "last_name")
	private String lastName;

	/** The second surname. */
	@JsonProperty(value = "second_surname")
	private String secondSurname;

	/** The mothers surname. */
	@JsonProperty(value = "mothers_surname")
	private String mothersSurname;

	/** The dob. */
	@JsonProperty(value = "dob")
	private String dob;

	/** The position of significance. */
	@JsonProperty(value = "position_of_significance")
	private String positionOfSignificance;

	/** The authorised signatory. */
	@JsonProperty(value = "authorised_signatory")
	private String authorisedSignatory;

	/** The job title. */
	@JsonProperty(value = "job_title")
	private String jobTitle;

	/** The address type. */
	@JsonProperty(value = "address_type")
	private String addressType;

	/** The city. */
	@JsonProperty(value = "town_city_muncipalty")
	private String city;

	/** The building name. */
	@JsonProperty(value = "building_name")
	private String buildingName;

	/** The street. */
	@JsonProperty(value = "street")
	private String street;

	/** The street number. */
	@JsonProperty(value = "street_number")
	private String streetNumber;

	/** The street type. */
	@JsonProperty(value = "street_type")
	private String streetType;

	/** The post code. */
	@JsonProperty(value = "post_code")
	private String postCode;

	/** The country. */
	@JsonProperty(value = "country_of_residence")
	private String country;

	/** The phone home. */
	@JsonProperty(value = "home_phone")
	private String phoneHome;

	/** The phone work. */
	@JsonProperty(value = "work_phone")
	private String phoneWork;

	/** The phone work extn. */
	@JsonProperty(value = "work_phone_ext")
	private String phoneWorkExtn;

	/** The phone mobile. */
	@JsonProperty(value = "mobile_phone")
	private String phoneMobile;

	/** The primary phone. */
	@JsonProperty(value = "primary_phone")
	private String primaryPhone;

	/** The email. */
	@JsonProperty(value = "email")
	private String email;

	/** The second email. */
	@JsonProperty(value = "second_email")
	private String secondEmail;

	/** The designation. */
	@JsonProperty(value = "designation")
	private String designation;

	/** The ip address. */
	@JsonProperty(value = "ip_address")
	private String ipAddress;

	/** The primary contact. */
	@JsonProperty(value = "is_primary_contact")
	private Boolean primaryContact;

	/** The nationality. */
	@JsonProperty(value = "country_of_nationality")
	private String nationality;

	/** The gender. */
	@JsonProperty(value = "gender")
	private String gender;

	/** The occupation. */
	@JsonProperty(value = "occupation")
	private String occupation;

	/** The passport number. */
	@JsonProperty(value = "passport_number")
	private String passportNumber;

	/** The passport country code. */
	@JsonProperty(value = "passport_country_code")
	private String passportCountryCode;

	/** The passport exiprydate. */
	@JsonProperty(value = "passport_exipry_date")
	private String passportExiprydate;

	/** The passport full name. */
	@JsonProperty(value = "passport_full_name")
	private String passportFullName;

	/** The passport MRZ line 1. */
	@JsonProperty(value = "passport_mrz_line_1")
	private String passportMRZLine1;

	/** The passport MRZ line 2. */
	@JsonProperty(value = "passport_mrz_line_2")
	private String passportMRZLine2;

	/** The passport family name at birth. */
	@JsonProperty(value = "passport_birth_family_name")
	private String passportFamilyNameAtBirth;

	/** The passport name at citizenship. */
	@JsonProperty(value = "passport_name_at_citizen")
	private String passportNameAtCitizenship;

	/** The passport place of birth. */
	@JsonProperty(value = "passport_birth_place")
	private String passportPlaceOfBirth;

	/** The dl license number. */
	@JsonProperty(value = "driving_license")
	private String dlLicenseNumber;

	/** The dl version number. */
	@JsonProperty(value = "driving_version_number")
	private String dlVersionNumber;

	/** The dl card number. */
	@JsonProperty(value = "driving_license_card_number")
	private String dlCardNumber;

	/** The dl country code. */
	@JsonProperty(value = "driving_license_country")
	private String dlCountryCode;

	/** The dl state code. */
	@JsonProperty(value = "driving_state_code")
	private String dlStateCode;

	/** The dl expiry date. */
	@JsonProperty(value = "driving_expiry")
	private String dlExpiryDate;

	/** The medicare card number. */
	@JsonProperty(value = "medicare_card_number")
	private String medicareCardNumber;

	/** The medicare reference number. */
	@JsonProperty(value = "medicare_ref_number")
	private String medicareReferenceNumber;

	/** The australia RTA card number. */
	@JsonProperty(value = "australia_rta_card_number")
	private String australiaRTACardNumber;

	/** The state. */
	@JsonProperty(value = "state_province_county")
	private String state;

	/** The municipality of birth. */
	@JsonProperty(value = "municipality_of_birth")
	private String municipalityOfBirth;

	/** The country of birth. */
	@JsonProperty(value = "country_of_birth")
	private String countryOfBirth;

	/** The state of birth. */
	@JsonProperty(value = "state_of_birth")
	private String stateOfBirth;

	/** The civic number. */
	@JsonProperty(value = "civic_number")
	private String civicNumber;

	/** The sub buildingor flat. */
	@JsonProperty(value = "sub_building")
	private String subBuildingorFlat;

	/** The unit number. */
	@JsonProperty(value = "unit_number")
	private String unitNumber;

	/** The sub city. */
	@JsonProperty(value = "sub_city")
	private String subCity;

	/** The region. */
	@JsonProperty(value = "region_suburb")
	private String region;

	/** The national id type. */
	@JsonProperty(value = "national_id_type")
	private String nationalIdType;

	/** The national id number. */
	@JsonProperty(value = "national_id_number")
	private String nationalIdNumber;

	/** The years in address. */
	@JsonProperty(value = "years_in_address")
	private String yearsInAddress;

	/** The residential status. */
	@JsonProperty(value = "residential_status")
	private String residentialStatus;

	/** The district. */
	@JsonProperty(value = "district")
	private String district;

	/** The area number. */
	@JsonProperty(value = "area_number")
	private String areaNumber;

	/** The aza. */
	@JsonProperty(value = "aza")
	private String aza;

	/** The prefecture. */
	@JsonProperty(value = "prefecture")
	private String prefecture;

	/** The floor number. */
	@JsonProperty(value = "floor_number")
	private String floorNumber;
	
	/** The date of last email change. */
	@JsonProperty(value = "date_of_last_email_change")
	private String dateOfLastEmailChange;

	/** The date of last address change. */
	@JsonProperty(value = "date_of_last_address_change")
	private String dateOfLastAddressChange;
	
	/** The ip domain. */
	@JsonProperty(value = "ip_domain")
	private String ipDomain;
	
	/** The ip isp. */
	@JsonProperty(value = "ip_isp")
	private String ipIsp;
	
	/** The ip city. */
	@JsonProperty(value = "ip_city")
	private String ipCity;
	
	/** The ip country. */
	@JsonProperty(value = "ip_country")
	private String ipCountry;
	
	/** The ssn number. */
	@JsonProperty(value = "ssn")
	private String ssn;

	/** The address 1. */
	@JsonProperty(value = "address1")
	private String address1;
	
	/** The house building number. */
	@JsonProperty(value = "house_building_number")
	private String houseBuildingNumber;
	
	/** The version. */
	@JsonProperty(value = "version")
	private String version;
	
	/** The watchlist. */
	@JsonProperty(value = "watchlist")
	private List<String> watchlist;
	
	/** The update status. */
	@JsonProperty(value = "update_status")
	private String updateStatus;
	
	/** The status update reason. */
	@JsonProperty(value = "status_update_reason")
	private List<String> statusUpdateReason;
	
	/** The compliance log. */
	@JsonProperty(value = "compliance_log")
	private String complianceLog;
	
	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	/** The e IDV status. */
	@JsonProperty(value = "EIDV_status")
	private String eIDVStatus;
	
	/** The sanction result. */
	@JsonProperty(value = "sanction_result")
	private String sanctionResult;
	
	/** The fraud predict date. */
	@JsonProperty(value = "fraud_predict_date")
	private String fraudPredictDate;
	
	/** The fraud predict score. */
	@JsonProperty(value = "fraud_predict_score")
	private String fraudPredictScore;
	
	/** The contact status. */
	@JsonProperty(value = "Contact_Status")
	private String contactStatus;
	
	/** The onfido. */
	@JsonProperty(value = "Onfido")
	private String onfido;

	/** The reg date time. */
	private String regDateTime;

	/** The last password change date. */
	@JsonProperty(value = "date_last_password_change")
	private String lastPasswordChangeDate;

	/** The app install date. */
	@JsonProperty(value = "date_app_installed")
	private String appInstallDate;

	/** The device id. */
	@JsonProperty(value = "device_id")
	private String deviceId;
	
	/** The custom check. */
	@JsonProperty(value = "custom_check")
	private String customCheck;  //Add for AT-5393
	
	/**
	 * Gets the reg date time.
	 *
	 * @return the reg date time
	 */
	public String getRegDateTime() {
		return regDateTime;
	}

	/**
	 * Sets the reg date time.
	 *
	 * @param regDateTime the new reg date time
	 */
	public void setRegDateTime(String regDateTime) {
		this.regDateTime = regDateTime;
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
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the trade acc number.
	 *
	 * @return the trade acc number
	 */
	public String getTradeAccNumber() {
		return tradeAccNumber;
	}

	/**
	 * Sets the trade acc number.
	 *
	 * @param tradeAccNumber the new trade acc number
	 */
	public void setTradeAccNumber(String tradeAccNumber) {
		this.tradeAccNumber = tradeAccNumber;
	}

	/**
	 * Gets the contact SFID.
	 *
	 * @return the contact SFID
	 */
	public String getContactSFID() {
		return contactSFID;
	}

	/**
	 * Sets the contact SFID.
	 *
	 * @param contactSFID the new contact SFID
	 */
	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}

	/**
	 * Gets the trade contact ID.
	 *
	 * @return the trade contact ID
	 */
	public Integer getTradeContactID() {
		return tradeContactID;
	}

	/**
	 * Sets the trade contact ID.
	 *
	 * @param tradeContactID the new trade contact ID
	 */
	public void setTradeContactID(Integer tradeContactID) {
		this.tradeContactID = tradeContactID;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the preferred name.
	 *
	 * @return the preferred name
	 */
	public String getPreferredName() {
		return preferredName;
	}

	/**
	 * Sets the preferred name.
	 *
	 * @param preferredName the new preferred name
	 */
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the second surname.
	 *
	 * @return the second surname
	 */
	public String getSecondSurname() {
		return secondSurname;
	}

	/**
	 * Sets the second surname.
	 *
	 * @param secondSurname the new second surname
	 */
	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	/**
	 * Gets the mothers surname.
	 *
	 * @return the mothers surname
	 */
	public String getMothersSurname() {
		return mothersSurname;
	}

	/**
	 * Sets the mothers surname.
	 *
	 * @param mothersSurname the new mothers surname
	 */
	public void setMothersSurname(String mothersSurname) {
		this.mothersSurname = mothersSurname;
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
	 * @param dob the new dob
	 */
	public void setDob(String dob) {
		this.dob = dob;
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
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the building name.
	 *
	 * @return the building name
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * Sets the building name.
	 *
	 * @param buildingName the new building name
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the street number.
	 *
	 * @return the street number
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * Sets the street number.
	 *
	 * @param streetNumber the new street number
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	/**
	 * Gets the street type.
	 *
	 * @return the street type
	 */
	public String getStreetType() {
		return streetType;
	}

	/**
	 * Sets the street type.
	 *
	 * @param streetType the new street type
	 */
	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	/**
	 * Gets the post code.
	 *
	 * @return the post code
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the post code.
	 *
	 * @param postCode the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the phone home.
	 *
	 * @return the phone home
	 */
	public String getPhoneHome() {
		return phoneHome;
	}

	/**
	 * Sets the phone home.
	 *
	 * @param phoneHome the new phone home
	 */
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	/**
	 * Gets the phone work.
	 *
	 * @return the phone work
	 */
	public String getPhoneWork() {
		return phoneWork;
	}

	/**
	 * Sets the phone work.
	 *
	 * @param phoneWork the new phone work
	 */
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}

	/**
	 * Gets the phone work extn.
	 *
	 * @return the phone work extn
	 */
	public String getPhoneWorkExtn() {
		return phoneWorkExtn;
	}

	/**
	 * Sets the phone work extn.
	 *
	 * @param phoneWorkExtn the new phone work extn
	 */
	public void setPhoneWorkExtn(String phoneWorkExtn) {
		this.phoneWorkExtn = phoneWorkExtn;
	}

	/**
	 * Gets the phone mobile.
	 *
	 * @return the phone mobile
	 */
	public String getPhoneMobile() {
		return phoneMobile;
	}

	/**
	 * Sets the phone mobile.
	 *
	 * @param phoneMobile the new phone mobile
	 */
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	/**
	 * Gets the primary phone.
	 *
	 * @return the primary phone
	 */
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	 * Sets the primary phone.
	 *
	 * @param primaryPhone the new primary phone
	 */
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
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
	 * @param secondEmail the new second email
	 */
	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
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
	 * Gets the primary contact.
	 *
	 * @return the primary contact
	 */
	public Boolean getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * Sets the primary contact.
	 *
	 * @param primaryContact the new primary contact
	 */
	public void setPrimaryContact(Boolean primaryContact) {
		this.primaryContact = primaryContact;
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
	 * @param occupation the new occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the passport country code.
	 *
	 * @return the passport country code
	 */
	public String getPassportCountryCode() {
		return passportCountryCode;
	}

	/**
	 * Sets the passport country code.
	 *
	 * @param passportCountryCode the new passport country code
	 */
	public void setPassportCountryCode(String passportCountryCode) {
		this.passportCountryCode = passportCountryCode;
	}

	/**
	 * Gets the passport exiprydate.
	 *
	 * @return the passport exiprydate
	 */
	public String getPassportExiprydate() {
		return passportExiprydate;
	}

	/**
	 * Sets the passport exiprydate.
	 *
	 * @param passportExiprydate the new passport exiprydate
	 */
	public void setPassportExiprydate(String passportExiprydate) {
		this.passportExiprydate = passportExiprydate;
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
	 * @param passportFullName the new passport full name
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
	 * @param passportMRZLine1 the new passport MRZ line 1
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
	 * @param passportMRZLine2 the new passport MRZ line 2
	 */
	public void setPassportMRZLine2(String passportMRZLine2) {
		this.passportMRZLine2 = passportMRZLine2;
	}

	/**
	 * Gets the passport family name at birth.
	 *
	 * @return the passport family name at birth
	 */
	public String getPassportFamilyNameAtBirth() {
		return passportFamilyNameAtBirth;
	}

	/**
	 * Sets the passport family name at birth.
	 *
	 * @param passportFamilyNameAtBirth the new passport family name at birth
	 */
	public void setPassportFamilyNameAtBirth(String passportFamilyNameAtBirth) {
		this.passportFamilyNameAtBirth = passportFamilyNameAtBirth;
	}

	/**
	 * Gets the passport name at citizenship.
	 *
	 * @return the passport name at citizenship
	 */
	public String getPassportNameAtCitizenship() {
		return passportNameAtCitizenship;
	}

	/**
	 * Sets the passport name at citizenship.
	 *
	 * @param passportNameAtCitizenship the new passport name at citizenship
	 */
	public void setPassportNameAtCitizenship(String passportNameAtCitizenship) {
		this.passportNameAtCitizenship = passportNameAtCitizenship;
	}

	/**
	 * Gets the passport place of birth.
	 *
	 * @return the passport place of birth
	 */
	public String getPassportPlaceOfBirth() {
		return passportPlaceOfBirth;
	}

	/**
	 * Sets the passport place of birth.
	 *
	 * @param passportPlaceOfBirth the new passport place of birth
	 */
	public void setPassportPlaceOfBirth(String passportPlaceOfBirth) {
		this.passportPlaceOfBirth = passportPlaceOfBirth;
	}

	/**
	 * Gets the dl license number.
	 *
	 * @return the dl license number
	 */
	public String getDlLicenseNumber() {
		return dlLicenseNumber;
	}

	/**
	 * Sets the dl license number.
	 *
	 * @param dlLicenseNumber the new dl license number
	 */
	public void setDlLicenseNumber(String dlLicenseNumber) {
		this.dlLicenseNumber = dlLicenseNumber;
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
	 * @param dlVersionNumber the new dl version number
	 */
	public void setDlVersionNumber(String dlVersionNumber) {
		this.dlVersionNumber = dlVersionNumber;
	}

	/**
	 * Gets the dl card number.
	 *
	 * @return the dl card number
	 */
	public String getDlCardNumber() {
		return dlCardNumber;
	}

	/**
	 * Sets the dl card number.
	 *
	 * @param dlCardNumber the new dl card number
	 */
	public void setDlCardNumber(String dlCardNumber) {
		this.dlCardNumber = dlCardNumber;
	}

	/**
	 * Gets the dl country code.
	 *
	 * @return the dl country code
	 */
	public String getDlCountryCode() {
		return dlCountryCode;
	}

	/**
	 * Sets the dl country code.
	 *
	 * @param dlCountryCode the new dl country code
	 */
	public void setDlCountryCode(String dlCountryCode) {
		this.dlCountryCode = dlCountryCode;
	}

	/**
	 * Gets the dl state code.
	 *
	 * @return the dl state code
	 */
	public String getDlStateCode() {
		return dlStateCode;
	}

	/**
	 * Sets the dl state code.
	 *
	 * @param dlStateCode the new dl state code
	 */
	public void setDlStateCode(String dlStateCode) {
		this.dlStateCode = dlStateCode;
	}

	/**
	 * Gets the dl expiry date.
	 *
	 * @return the dl expiry date
	 */
	public String getDlExpiryDate() {
		return dlExpiryDate;
	}

	/**
	 * Sets the dl expiry date.
	 *
	 * @param dlExpiryDate the new dl expiry date
	 */
	public void setDlExpiryDate(String dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}

	/**
	 * Gets the medicare card number.
	 *
	 * @return the medicare card number
	 */
	public String getMedicareCardNumber() {
		return medicareCardNumber;
	}

	/**
	 * Sets the medicare card number.
	 *
	 * @param medicareCardNumber the new medicare card number
	 */
	public void setMedicareCardNumber(String medicareCardNumber) {
		this.medicareCardNumber = medicareCardNumber;
	}

	/**
	 * Gets the medicare reference number.
	 *
	 * @return the medicare reference number
	 */
	public String getMedicareReferenceNumber() {
		return medicareReferenceNumber;
	}

	/**
	 * Sets the medicare reference number.
	 *
	 * @param medicareReferenceNumber the new medicare reference number
	 */
	public void setMedicareReferenceNumber(String medicareReferenceNumber) {
		this.medicareReferenceNumber = medicareReferenceNumber;
	}

	/**
	 * Gets the australia RTA card number.
	 *
	 * @return the australia RTA card number
	 */
	public String getAustraliaRTACardNumber() {
		return australiaRTACardNumber;
	}

	/**
	 * Sets the australia RTA card number.
	 *
	 * @param australiaRTACardNumber the new australia RTA card number
	 */
	public void setAustraliaRTACardNumber(String australiaRTACardNumber) {
		this.australiaRTACardNumber = australiaRTACardNumber;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the municipality of birth.
	 *
	 * @return the municipality of birth
	 */
	public String getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}

	/**
	 * Sets the municipality of birth.
	 *
	 * @param municipalityOfBirth the new municipality of birth
	 */
	public void setMunicipalityOfBirth(String municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}

	/**
	 * Gets the country of birth.
	 *
	 * @return the country of birth
	 */
	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	/**
	 * Sets the country of birth.
	 *
	 * @param countryOfBirth the new country of birth
	 */
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	/**
	 * Gets the state of birth.
	 *
	 * @return the state of birth
	 */
	public String getStateOfBirth() {
		return stateOfBirth;
	}

	/**
	 * Sets the state of birth.
	 *
	 * @param stateOfBirth the new state of birth
	 */
	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	/**
	 * Gets the civic number.
	 *
	 * @return the civic number
	 */
	public String getCivicNumber() {
		return civicNumber;
	}

	/**
	 * Sets the civic number.
	 *
	 * @param civicNumber the new civic number
	 */
	public void setCivicNumber(String civicNumber) {
		this.civicNumber = civicNumber;
	}

	/**
	 * Gets the sub buildingor flat.
	 *
	 * @return the sub buildingor flat
	 */
	public String getSubBuildingorFlat() {
		return subBuildingorFlat;
	}

	/**
	 * Sets the sub buildingor flat.
	 *
	 * @param subBuildingorFlat the new sub buildingor flat
	 */
	public void setSubBuildingorFlat(String subBuildingorFlat) {
		this.subBuildingorFlat = subBuildingorFlat;
	}

	/**
	 * Gets the unit number.
	 *
	 * @return the unit number
	 */
	public String getUnitNumber() {
		return unitNumber;
	}

	/**
	 * Sets the unit number.
	 *
	 * @param unitNumber the new unit number
	 */
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * Gets the sub city.
	 *
	 * @return the sub city
	 */
	public String getSubCity() {
		return subCity;
	}

	/**
	 * Sets the sub city.
	 *
	 * @param subCity the new sub city
	 */
	public void setSubCity(String subCity) {
		this.subCity = subCity;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the national id type.
	 *
	 * @return the national id type
	 */
	public String getNationalIdType() {
		return nationalIdType;
	}

	/**
	 * Sets the national id type.
	 *
	 * @param nationalIdType the new national id type
	 */
	public void setNationalIdType(String nationalIdType) {
		this.nationalIdType = nationalIdType;
	}

	/**
	 * Gets the national id number.
	 *
	 * @return the national id number
	 */
	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	/**
	 * Sets the national id number.
	 *
	 * @param nationalIdNumber the new national id number
	 */
	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	/**
	 * Gets the years in address.
	 *
	 * @return the years in address
	 */
	public String getYearsInAddress() {
		return yearsInAddress;
	}

	/**
	 * Sets the years in address.
	 *
	 * @param yearsInAddress the new years in address
	 */
	public void setYearsInAddress(String yearsInAddress) {
		this.yearsInAddress = yearsInAddress;
	}

	/**
	 * Gets the residential status.
	 *
	 * @return the residential status
	 */
	public String getResidentialStatus() {
		return residentialStatus;
	}

	/**
	 * Sets the residential status.
	 *
	 * @param residentialStatus the new residential status
	 */
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}

	/**
	 * Gets the district.
	 *
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * Sets the district.
	 *
	 * @param district the new district
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * Gets the area number.
	 *
	 * @return the area number
	 */
	public String getAreaNumber() {
		return areaNumber;
	}

	/**
	 * Sets the area number.
	 *
	 * @param areaNumber the new area number
	 */
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	/**
	 * Gets the aza.
	 *
	 * @return the aza
	 */
	public String getAza() {
		return aza;
	}

	/**
	 * Sets the aza.
	 *
	 * @param aza the new aza
	 */
	public void setAza(String aza) {
		this.aza = aza;
	}

	/**
	 * Gets the prefecture.
	 *
	 * @return the prefecture
	 */
	public String getPrefecture() {
		return prefecture;
	}

	/**
	 * Sets the prefecture.
	 *
	 * @param prefecture the new prefecture
	 */
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	/**
	 * Gets the floor number.
	 *
	 * @return the floor number
	 */
	public String getFloorNumber() {
		return floorNumber;
	}

	/**
	 * Sets the floor number.
	 *
	 * @param floorNumber the new floor number
	 */
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * Gets the date of last email change.
	 *
	 * @return the date of last email change
	 */
	public String getDateOfLastEmailChange() {
		return dateOfLastEmailChange;
	}

	/**
	 * Sets the date of last email change.
	 *
	 * @param dateOfLastEmailChange the new date of last email change
	 */
	public void setDateOfLastEmailChange(String dateOfLastEmailChange) {
		this.dateOfLastEmailChange = dateOfLastEmailChange;
	}

	/**
	 * Gets the date of last address change.
	 *
	 * @return the date of last address change
	 */
	public String getDateOfLastAddressChange() {
		return dateOfLastAddressChange;
	}

	/**
	 * Sets the date of last address change.
	 *
	 * @param dateOfLastAddressChange the new date of last address change
	 */
	public void setDateOfLastAddressChange(String dateOfLastAddressChange) {
		this.dateOfLastAddressChange = dateOfLastAddressChange;
	}

	/**
	 * Gets the ip domain.
	 *
	 * @return the ip domain
	 */
	public String getIpDomain() {
		return ipDomain;
	}

	/**
	 * Sets the ip domain.
	 *
	 * @param ipDomain the new ip domain
	 */
	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}

	/**
	 * Gets the ip isp.
	 *
	 * @return the ip isp
	 */
	public String getIpIsp() {
		return ipIsp;
	}

	/**
	 * Sets the ip isp.
	 *
	 * @param ipIsp the new ip isp
	 */
	public void setIpIsp(String ipIsp) {
		this.ipIsp = ipIsp;
	}

	/**
	 * Gets the ip city.
	 *
	 * @return the ip city
	 */
	public String getIpCity() {
		return ipCity;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ipCity the new ip city
	 */
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	/**
	 * Gets the ip country.
	 *
	 * @return the ip country
	 */
	public String getIpCountry() {
		return ipCountry;
	}

	/**
	 * Sets the ip country.
	 *
	 * @param ipCountry the new ip country
	 */
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}

	/**
	 * Gets the ssn.
	 *
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * Sets the ssn.
	 *
	 * @param ssn the new ssn
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * Gets the address 1.
	 *
	 * @return the address 1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the address 1.
	 *
	 * @param address1 the new address 1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * Gets the house building number.
	 *
	 * @return the house building number
	 */
	public String getHouseBuildingNumber() {
		return houseBuildingNumber;
	}

	/**
	 * Sets the house building number.
	 *
	 * @param houseBuildingNumber the new house building number
	 */
	public void setHouseBuildingNumber(String houseBuildingNumber) {
		this.houseBuildingNumber = houseBuildingNumber;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public List<String> getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist the new watchlist
	 */
	public void setWatchlist(List<String> watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * Gets the update status.
	 *
	 * @return the update status
	 */
	public String getUpdateStatus() {
		return updateStatus;
	}

	/**
	 * Sets the update status.
	 *
	 * @param updateStatus the new update status
	 */
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	/**
	 * Gets the status update reason.
	 *
	 * @return the status update reason
	 */
	public List<String> getStatusUpdateReason() {
		return statusUpdateReason;
	}

	/**
	 * Sets the status update reason.
	 *
	 * @param statusUpdateReason the new status update reason
	 */
	public void setStatusUpdateReason(List<String> statusUpdateReason) {
		this.statusUpdateReason = statusUpdateReason;
	}

	/**
	 * Gets the compliance log.
	 *
	 * @return the compliance log
	 */
	public String getComplianceLog() {
		return complianceLog;
	}

	/**
	 * Sets the compliance log.
	 *
	 * @param complianceLog the new compliance log
	 */
	public void setComplianceLog(String complianceLog) {
		this.complianceLog = complianceLog;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public String getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist the new blacklist
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Gets the e IDV status.
	 *
	 * @return the e IDV status
	 */
	public String geteIDVStatus() {
		return eIDVStatus;
	}

	/**
	 * Sets the e IDV status.
	 *
	 * @param eIDVStatus the new e IDV status
	 */
	public void seteIDVStatus(String eIDVStatus) {
		this.eIDVStatus = eIDVStatus;
	}

	/**
	 * Gets the sanction result.
	 *
	 * @return the sanction result
	 */
	public String getSanctionResult() {
		return sanctionResult;
	}

	/**
	 * Sets the sanction result.
	 *
	 * @param sanctionResult the new sanction result
	 */
	public void setSanctionResult(String sanctionResult) {
		this.sanctionResult = sanctionResult;
	}

	/**
	 * Gets the fraud predict date.
	 *
	 * @return the fraud predict date
	 */
	public String getFraudPredictDate() {
		return fraudPredictDate;
	}

	/**
	 * Sets the fraud predict date.
	 *
	 * @param fraudPredictDate the new fraud predict date
	 */
	public void setFraudPredictDate(String fraudPredictDate) {
		this.fraudPredictDate = fraudPredictDate;
	}

	/**
	 * Gets the fraud predict score.
	 *
	 * @return the fraud predict score
	 */
	public String getFraudPredictScore() {
		return fraudPredictScore;
	}

	/**
	 * Sets the fraud predict score.
	 *
	 * @param fraudPredictScore the new fraud predict score
	 */
	public void setFraudPredictScore(String fraudPredictScore) {
		this.fraudPredictScore = fraudPredictScore;
	}
	
	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * @return the onfido
	 */
	public String getOnfido() {
		return onfido;
	}

	/**
	 * @param onfido the onfido to set
	 */
	public void setOnfido(String onfido) {
		this.onfido = onfido;
	}

	/**
	 * Gets the last password change date.
	 *
	 * @return the last password change date
	 */
	public String getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	/**
	 * Sets the last password change date.
	 *
	 * @param lastPasswordChangeDate the new last password change date
	 */
	public void setLastPasswordChangeDate(String lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	/**
	 * Gets the app install date.
	 *
	 * @return the app install date
	 */
	public String getAppInstallDate() {
		return appInstallDate;
	}

	/**
	 * Sets the app install date.
	 *
	 * @param appInstallDate the new app install date
	 */
	public void setAppInstallDate(String appInstallDate) {
		this.appInstallDate = appInstallDate;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the customCheck
	 */
	public String getCustomCheck() {
		return customCheck;
	}

	/**
	 * @param customCheck the customCheck to set
	 */
	public void setCustomCheck(String customCheck) {
		this.customCheck = customCheck;
	}
	
}
