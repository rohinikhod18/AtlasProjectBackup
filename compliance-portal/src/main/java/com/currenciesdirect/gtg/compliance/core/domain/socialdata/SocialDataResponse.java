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
public class SocialDataResponse {
	
    @JsonProperty("fullName")
    public String fullName;
    
    @JsonProperty("ageRange")
    public Object ageRange;
    
    @JsonProperty("gender")
    public String gender;
    
    @JsonProperty("location")
    public String location;
    
    @JsonProperty("title")
    public Object title;
    
    @JsonProperty("organization")
    public Object organization;
    
    @JsonProperty("twitter")
    public String twitter;
    
    @JsonProperty("linkedin")
    public Object linkedin;
    
    @JsonProperty("facebook")
    public String facebook;
    
    @JsonProperty("bio")
    public String bio;
    
    @JsonProperty("avatar")
    public String avatar;
    
    @JsonProperty("website")
    public Object website;
    
    @JsonProperty("details")
    public Details details;
    
    @JsonProperty("dataAddOns")
    public List<DataAddOn> dataAddOns = null;
    
    @JsonProperty("updated")
    public String updated;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Object getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(Object ageRange) {
		this.ageRange = ageRange;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Object getTitle() {
		return title;
	}

	public void setTitle(Object title) {
		this.title = title;
	}

	public Object getOrganization() {
		return organization;
	}

	public void setOrganization(Object organization) {
		this.organization = organization;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public Object getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(Object linkedin) {
		this.linkedin = linkedin;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Object getWebsite() {
		return website;
	}

	public void setWebsite(Object website) {
		this.website = website;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public List<DataAddOn> getDataAddOns() {
		return dataAddOns;
	}

	public void setDataAddOns(List<DataAddOn> dataAddOns) {
		this.dataAddOns = dataAddOns;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
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

}
