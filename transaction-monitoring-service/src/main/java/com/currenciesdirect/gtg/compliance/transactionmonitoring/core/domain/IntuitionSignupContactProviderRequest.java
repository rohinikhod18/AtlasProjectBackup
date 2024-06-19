package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionSignupContactProviderRequest.
 */
@Getter 
@Setter
public class IntuitionSignupContactProviderRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The contact sf id. */
	@JsonProperty(value = "contact_sf_id")
	private String contactSfId;

	/** The trade contact id. */
	@JsonProperty(value = "trade_contact_id")
	private String tradeContactId;

	/** The title. */
	@JsonProperty(value = "title")
	private String title;

	/** The pref name. */
	@JsonProperty(value = "pref_name")
	private String prefName;

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

	/** The gender. */
	@JsonProperty(value = "gender")
	private String gender;

	/** The position of significance. */
	@JsonProperty(value = "position_of_significance")
	private String positionOfSignificance;

	/** The authorised signatory. */
	@JsonProperty(value = "authorised_signatory")
	private String authorisedSignatory;

	/** The address type. */
	@JsonProperty(value = "address_type")
	private String addressType;

	/** The address 1. */
	@JsonProperty(value = "address1")
	private String address1;

	/** The town city muncipalty. */
	@JsonProperty(value = "town_city_muncipalty")
	private String townCityMuncipalty;

	/** The sub city. */
	@JsonProperty(value = "sub_city")
	private String subCity;

	/** The building name. */
	@JsonProperty(value = "building_name")
	private String buildingName;

	/** The house building number. */
	@JsonProperty(value = "house_building_number")
	private String houseBuildingNumber;

	/** The sub building. */
	@JsonProperty(value = "sub_building")
	private String subBuilding;

	/** The floor number. */
	@JsonProperty(value = "floor_number")
	private String floorNumber;

	/** The street. */
	@JsonProperty(value = "street")
	private String street;

	/** The street number. */
	@JsonProperty(value = "street_number")
	private String streetNumber;

	/** The street type. */
	@JsonProperty(value = "street_type")
	private String streetType;

	/** The unit number. */
	@JsonProperty(value = "unit_number")
	private String unitNumber;

	/** The post code. */
	@JsonProperty(value = "post_code")
	private String postCode;

	/** The district. */
	@JsonProperty(value = "district")
	private String district;

	/** The area number. */
	@JsonProperty(value = "area_number")
	private String areaNumber;

	/** The prefecture. */
	@JsonProperty(value = "prefecture")
	private String prefecture;

	/** The country of residence. */
	@JsonProperty(value = "country_of_residence")
	private String countryOfResidence;

	/** The years in address. */
	@JsonProperty(value = "years_in_address")
	private Double yearsInAddress;

	/** The residential status. */
	@JsonProperty(value = "residential_status")
	private String residentialStatus;

	/** The home phone. */
	@JsonProperty(value = "home_phone")
	private String homePhone;

	/** The work phone. */
	@JsonProperty(value = "work_phone")
	private String workPhone;

	/** The work phone ext. */
	@JsonProperty(value = "work_phone_ext")
	private String workPhoneExt;

	/** The mobile phone. */
	@JsonProperty(value = "mobile_phone")
	private String mobilePhone;

	/** The primary phone. */
	@JsonProperty(value = "primary_phone")
	private String primaryPhone;

	/** The email. */
	@JsonProperty(value = "email")
	private String email;

	/** The second email. */
	@JsonProperty(value = "second_email")
	private String secondEmail;

	/** The ip address. */
	@JsonProperty(value = "ip_address")
	private String ipAddress;

	/** The is primary contact. */
	@JsonProperty(value = "is_primary_contact")
	private String isPrimaryContact;

	/** The country of nationality. */
	@JsonProperty(value = "country_of_nationality")
	private String countryOfNationality;

	/** The job title. */
	@JsonProperty(value = "job_title")
	private String jobTitle;

	/** The designation. */
	@JsonProperty(value = "designation")
	private String designation;

	/** The occupation. */
	@JsonProperty(value = "occupation")
	private String occupation;

	/** The national id type. */
	@JsonProperty(value = "national_id_type")
	private String nationalIdType;

	/** The national id number. */
	@JsonProperty(value = "national_id_number")
	private String nationalIdNumber;

	/** The version. */
	@JsonProperty(value = "version")
	private String version;

	/** The passport number. */
	@JsonProperty(value = "passport_number")
	private String passportNumber;

	/** The passport country code. */
	@JsonProperty(value = "passport_country_code")
	private String passportCountryCode;

	/** The passport exipry date. */
	@JsonProperty(value = "passport_exipry_date")
	private String passportExipryDate;

	/** The passport full name. */
	@JsonProperty(value = "passport_full_name")
	private String passportFullName;

	/** The passport mrz line 1. */
	@JsonProperty(value = "passport_mrz_line_1")
	private String passportMrzLine1;

	/** The passport mrz line 2. */
	@JsonProperty(value = "passport_mrz_line_2")
	private String passportMrzLine2;

	/** The passport birth family name. */
	@JsonProperty(value = "passport_birth_family_name")
	private String passportBirthFamilyName;

	/** The passport name at citizen. */
	@JsonProperty(value = "passport_name_at_citizen")
	private String passportNameAtCitizen;

	/** The passport birth place. */
	@JsonProperty(value = "passport_birth_place")
	private String passportBirthPlace;

	/** The driving license. */
	@JsonProperty(value = "driving_license")
	private String drivingLicense;

	/** The driving version number. */
	@JsonProperty(value = "driving_version_number")
	private String drivingVersionNumber;

	/** The driving license card number. */
	@JsonProperty(value = "driving_license_card_number")
	private String drivingLicenseCardNumber;

	/** The driving license country. */
	@JsonProperty(value = "driving_license_country")
	private String drivingLicenseCountry;

	/** The driving state code. */
	@JsonProperty(value = "driving_state_code")
	private String drivingStateCode;

	/** The driving expiry. */
	@JsonProperty(value = "driving_expiry")
	private String drivingExpiry;

	/** The medicare card number. */
	@JsonProperty(value = "medicare_card_number")
	private String medicareCardNumber;

	/** The medicare ref number. */
	@JsonProperty(value = "medicare_ref_number")
	private String medicareRefNumber;

	/** The australia rta card number. */
	@JsonProperty(value = "australia_rta_card_number")
	private String australiaRtaCardNumber;

	/** The state province county. */
	@JsonProperty(value = "state_province_county")
	private String stateProvinceCounty;

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

	/** The aza. */
	@JsonProperty(value = "aza")
	private String aza;

	/** The watchlist. */
	@JsonProperty(value = "watchlist")
	private String watchlist;

	/** The update status. */
	@JsonProperty(value = "update_status")
	private String updateStatus;

	/** The status update reason. */
	@JsonProperty(value = "status_update_reason")
	private String statusUpdateReason;

	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;

	/** The e IDV status. */
	@JsonProperty(value = "EIDV_status")
	private String eIDVStatus;

	/** The custom check. */
	@JsonProperty(value = "custom_check")
	private String customCheck; // Add for AT-5393
	
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

	/** The trade acc number. */	
	@JsonProperty(value = "trade_acc_number")	
	private String tradeAccNumber;	
		
	/** The buying currency. */	
	@JsonProperty(value = "buying_currency")	
	private String buyingCurrency;	
		
	/** The selling currency. */	
	@JsonProperty(value = "selling_currency")	
	private String sellingCurrency;	
		
	/** The cust type. */	
	@JsonProperty(value = "cust_type")	
	private String custType;

	@JsonProperty(value = "date_last_password_change")
	private String lastPasswordChangeDate;

	@JsonProperty(value = "date_app_installed")
	private String appInstallDate;

	@JsonProperty(value = "device_id")
	private String deviceId;

	@JsonProperty(value = "date_last_email_change")
	private String lastEmailChangeDate;

	@JsonProperty(value = "date_last_phone_change")
	private String lastPhoneChangeDate;

	@JsonProperty(value = "date_last_address_change")
	private String lastAddressChangeDate;

	@JsonProperty(value = "date_last_pin_change")
	private String lastPinChangeDate;

}
