package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {

	@JsonProperty("name")
    public Name name;
	
    @JsonProperty("age")
    public Object age;
    
    @JsonProperty("gender")
    public String gender;
    
    @JsonProperty("emails")
    public List<Email> emails = null;
    
    @JsonProperty("phones")
    public List<Phones> phones = null;
    
    @JsonProperty("profiles")
    public Profiles profiles;
    
    @JsonProperty("locations")
    public List<Location> locations = null;
    
    @JsonProperty("employment")
    public List<Employment> employment = null;
    
    @JsonProperty("photos")
    public List<Photos> photos = null;
    
    @JsonProperty("education")
    public List<Object> education = null;
    
    @JsonProperty("urls")
    public List<Urls> urls = null;
    
    @JsonProperty("interests")
    public List<Interest> interests = null;
    
    @JsonProperty("topics")
    public List<Topic> topics = null;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	/**
	 * @return the name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public Object getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Object age) {
		this.age = age;
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
	 * @return the emails
	 */
	public List<Email> getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	/**
	 * @return the phones
	 */
	public List<Phones> getPhones() {
		return phones;
	}

	/**
	 * @param phones the phones to set
	 */
	public void setPhones(List<Phones> phones) {
		this.phones = phones;
	}

	/**
	 * @return the profiles
	 */
	public Profiles getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}

	/**
	 * @return the locations
	 */
	public List<Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	/**
	 * @return the employment
	 */
	public List<Employment> getEmployment() {
		return employment;
	}

	/**
	 * @param employment the employment to set
	 */
	public void setEmployment(List<Employment> employment) {
		this.employment = employment;
	}

	/**
	 * @return the photos
	 */
	public List<Photos> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}

	/**
	 * @return the education
	 */
	public List<Object> getEducation() {
		return education;
	}

	/**
	 * @param education the education to set
	 */
	public void setEducation(List<Object> education) {
		this.education = education;
	}

	/**
	 * @return the urls
	 */
	public List<Urls> getUrls() {
		return urls;
	}

	/**
	 * @param urls the urls to set
	 */
	public void setUrls(List<Urls> urls) {
		this.urls = urls;
	}

	/**
	 * @return the interests
	 */
	public List<Interest> getInterests() {
		return interests;
	}

	/**
	 * @param interests the interests to set
	 */
	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	/**
	 * @return the topics
	 */
	public List<Topic> getTopics() {
		return topics;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

}
