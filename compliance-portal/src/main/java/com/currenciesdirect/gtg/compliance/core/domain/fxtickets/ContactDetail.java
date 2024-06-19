
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class ContactDetail.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nationality",
    "contact",
    "salutation",
    "first_name",
    "middle_name",
    "last_name",
    "registered_on",
    "country_of_nationality",
    "country_of_residency",
    "mailing_street",
    "mailing_city",
    "mailing_state",
    "mailing_country",
    "email_address",
    "job_title",
    "mailing_postal_code",
    "home_phone",
    "mobile_phone",
    "work_phone",
    "primary_phone",
    "registration_ip_address",
    "recent_ip_address"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDetail {

    /** The nationality. */
    @JsonProperty("nationality")
    private Integer nationality;
    
    /** The contact. */
    @JsonProperty("contact")
    private Integer contact;
    
    /** The salutation. */
    @JsonProperty("salutation")
    private String salutation;
    
    /** The first name. */
    @JsonProperty("first_name")
    private String firstName;
    
    /** The middle name. */
    @JsonProperty("middle_name")
    private String middleName;
    
    /** The last name. */
    @JsonProperty("last_name")
    private String lastName;
    
    /** The registered on. */
    @JsonProperty("registered_on")
    private String registeredOn;
    
    /** The country of nationality. */
    @JsonProperty("country_of_nationality")
    private String countryOfNationality;
    
    /** The country of residency. */
    @JsonProperty("country_of_residency")
    private String countryOfResidency;
    
    /** The mailing street. */
    @JsonProperty("mailing_street")
    private String mailingStreet;
    
    /** The mailing city. */
    @JsonProperty("mailing_city")
    private String mailingCity;
    
    /** The mailing state. */
    @JsonProperty("mailing_state")
    private String mailingState;
    
    /** The mailing country. */
    @JsonProperty("mailing_country")
    private String mailingCountry;
    
    /** The email address. */
    @JsonProperty("email_address")
    private String emailAddress;
    
    /** The job title. */
    @JsonProperty("job_title")
    private String jobTitle;
    
    /** The mailing postal code. */
    @JsonProperty("mailing_postal_code")
    private String mailingPostalCode;
    
    /** The home phone. */
    @JsonProperty("home_phone")
    private String homePhone;
    
    /** The mobile phone. */
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    
    /** The work phone. */
    @JsonProperty("work_phone")
    private String workPhone;
    
    /** The primary phone. */
    @JsonProperty("primary_phone")
    private String primaryPhone;
    
    /** The registration ip address. */
    @JsonProperty("registration_ip_address")
    private String registrationIpAddress;
    
    /** The recent ip address. */
    @JsonProperty("recent_ip_address")
    private String recentIpAddress;

    /**
     * Gets the nationality.
     *
     * @return the nationality
     */
    @JsonProperty("nationality")
    public Integer getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality.
     *
     * @param nationality the new nationality
     */
    @JsonProperty("nationality")
    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the contact.
     *
     * @return the contact
     */
    @JsonProperty("contact")
    public Integer getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     *
     * @param contact the new contact
     */
    @JsonProperty("contact")
    public void setContact(Integer contact) {
        this.contact = contact;
    }

    /**
     * Gets the salutation.
     *
     * @return the salutation
     */
    @JsonProperty("salutation")
    public String getSalutation() {
        return salutation;
    }

    /**
     * Sets the salutation.
     *
     * @param salutation the new salutation
     */
    @JsonProperty("salutation")
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    @JsonProperty("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    @JsonProperty("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the registered on.
     *
     * @return the registered on
     */
    @JsonProperty("registered_on")
    public String getRegisteredOn() {
        return registeredOn;
    }

    /**
     * Sets the registered on.
     *
     * @param registeredOn the new registered on
     */
    @JsonProperty("registered_on")
    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    /**
     * Gets the country of nationality.
     *
     * @return the country of nationality
     */
    @JsonProperty("country_of_nationality")
    public String getCountryOfNationality() {
        return countryOfNationality;
    }

    /**
     * Sets the country of nationality.
     *
     * @param countryOfNationality the new country of nationality
     */
    @JsonProperty("country_of_nationality")
    public void setCountryOfNationality(String countryOfNationality) {
        this.countryOfNationality = countryOfNationality;
    }

    /**
     * Gets the country of residency.
     *
     * @return the country of residency
     */
    @JsonProperty("country_of_residency")
    public String getCountryOfResidency() {
        return countryOfResidency;
    }

    /**
     * Sets the country of residency.
     *
     * @param countryOfResidency the new country of residency
     */
    @JsonProperty("country_of_residency")
    public void setCountryOfResidency(String countryOfResidency) {
        this.countryOfResidency = countryOfResidency;
    }

    /**
     * Gets the mailing street.
     *
     * @return the mailing street
     */
    @JsonProperty("mailing_street")
    public String getMailingStreet() {
        return mailingStreet;
    }

    /**
     * Sets the mailing street.
     *
     * @param mailingStreet the new mailing street
     */
    @JsonProperty("mailing_street")
    public void setMailingStreet(String mailingStreet) {
        this.mailingStreet = mailingStreet;
    }

    /**
     * Gets the mailing city.
     *
     * @return the mailing city
     */
    @JsonProperty("mailing_city")
    public String getMailingCity() {
        return mailingCity;
    }

    /**
     * Sets the mailing city.
     *
     * @param mailingCity the new mailing city
     */
    @JsonProperty("mailing_city")
    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    /**
     * Gets the mailing state.
     *
     * @return the mailing state
     */
    @JsonProperty("mailing_state")
    public String getMailingState() {
        return mailingState;
    }

    /**
     * Sets the mailing state.
     *
     * @param mailingState the new mailing state
     */
    @JsonProperty("mailing_state")
    public void setMailingState(String mailingState) {
        this.mailingState = mailingState;
    }

    /**
     * Gets the mailing country.
     *
     * @return the mailing country
     */
    @JsonProperty("mailing_country")
    public String getMailingCountry() {
        return mailingCountry;
    }

    /**
     * Sets the mailing country.
     *
     * @param mailingCountry the new mailing country
     */
    @JsonProperty("mailing_country")
    public void setMailingCountry(String mailingCountry) {
        this.mailingCountry = mailingCountry;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    @JsonProperty("email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the new email address
     */
    @JsonProperty("email_address")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the job title.
     *
     * @return the job title
     */
    @JsonProperty("job_title")
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the job title.
     *
     * @param jobTitle the new job title
     */
    @JsonProperty("job_title")
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Gets the mailing postal code.
     *
     * @return the mailing postal code
     */
    @JsonProperty("mailing_postal_code")
    public String getMailingPostalCode() {
        return mailingPostalCode;
    }

    /**
     * Sets the mailing postal code.
     *
     * @param mailingPostalCode the new mailing postal code
     */
    @JsonProperty("mailing_postal_code")
    public void setMailingPostalCode(String mailingPostalCode) {
        this.mailingPostalCode = mailingPostalCode;
    }

    /**
     * Gets the home phone.
     *
     * @return the home phone
     */
    @JsonProperty("home_phone")
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the home phone.
     *
     * @param homePhone the new home phone
     */
    @JsonProperty("home_phone")
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Gets the mobile phone.
     *
     * @return the mobile phone
     */
    @JsonProperty("mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the mobile phone.
     *
     * @param mobilePhone the new mobile phone
     */
    @JsonProperty("mobile_phone")
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Gets the work phone.
     *
     * @return the work phone
     */
    @JsonProperty("work_phone")
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the work phone.
     *
     * @param workPhone the new work phone
     */
    @JsonProperty("work_phone")
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    /**
     * Gets the primary phone.
     *
     * @return the primary phone
     */
    @JsonProperty("primary_phone")
    public String getPrimaryPhone() {
        return primaryPhone;
    }

    /**
     * Sets the primary phone.
     *
     * @param primaryPhone the new primary phone
     */
    @JsonProperty("primary_phone")
    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    /**
     * Gets the registration ip address.
     *
     * @return the registration ip address
     */
    @JsonProperty("registration_ip_address")
    public String getRegistrationIpAddress() {
        return registrationIpAddress;
    }

    /**
     * Sets the registration ip address.
     *
     * @param registrationIpAddress the new registration ip address
     */
    @JsonProperty("registration_ip_address")
    public void setRegistrationIpAddress(String registrationIpAddress) {
        this.registrationIpAddress = registrationIpAddress;
    }

    /**
     * Gets the recent ip address.
     *
     * @return the recent ip address
     */
    @JsonProperty("recent_ip_address")
    public String getRecentIpAddress() {
        return recentIpAddress;
    }

    /**
     * Sets the recent ip address.
     *
     * @param recentIpAddress the new recent ip address
     */
    @JsonProperty("recent_ip_address")
    public void setRecentIpAddress(String recentIpAddress) {
        this.recentIpAddress = recentIpAddress;
    }

}
