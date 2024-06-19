package com.currenciesdirect.gtg.compliance.iam.core.domain;

import java.io.Serializable;

/**
 * The Class UserProfile.
 */
public class UserProfile implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name; 
    
	/** The given name. */
	private String givenName;
    
	/** The family name. */
	private String familyName;
    
	/** The middle name. */
	private String middleName;
    
	/** The nick name. */
	private String nickName;
    
	/** The preferred user name. */
	private String preferredUserName;
    
	/** The website. */
	private String website;
    
	/** The email. */
	private String email;
    
	/** The gender. */
	private String gender;
    
	/** The birthdate. */
	private String birthdate;
   
	/** The zoneinfo. */
	private String zoneinfo;
   
	/** The locale. */
	private String locale;
    
    /** The phone number. */
    private String phoneNumber;
    
    /** The permissions. */
    private UserPermission permissions;
    
    /** The role. */
    private Role role;
    
    /** The id. */
    private Integer id;
    
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the given name.
	 *
	 * @return the given name
	 */
	public String getGivenName() {
		return givenName;
	}
	
	/**
	 * Gets the family name.
	 *
	 * @return the family name
	 */
	public String getFamilyName() {
		return familyName;
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
	 * Gets the nick name.
	 *
	 * @return the nick name
	 */
	public String getNickName() {
		return nickName;
	}
	
	/**
	 * Gets the preferred user name.
	 *
	 * @return the preferred user name
	 */
	public String getPreferredUserName() {
		return preferredUserName;
	}
	
	/**
	 * Gets the website.
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
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
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Gets the birthdate.
	 *
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}
	
	/**
	 * Gets the zoneinfo.
	 *
	 * @return the zoneinfo
	 */
	public String getZoneinfo() {
		return zoneinfo;
	}
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	
	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the given name.
	 *
	 * @param givenName the new given name
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	/**
	 * Sets the family name.
	 *
	 * @param familyName the new family name
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
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
	 * Sets the nick name.
	 *
	 * @param nickName the new nick name
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * Sets the preferred user name.
	 *
	 * @param preferredUserName the new preferred user name
	 */
	public void setPreferredUserName(String preferredUserName) {
		this.preferredUserName = preferredUserName;
	}
	
	/**
	 * Sets the website.
	 *
	 * @param website the new website
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * Sets the birthdate.
	 *
	 * @param birthdate the new birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * Sets the zoneinfo.
	 *
	 * @param zoneinfo the new zoneinfo
	 */
	public void setZoneinfo(String zoneinfo) {
		this.zoneinfo = zoneinfo;
	}
	
	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public UserPermission getPermissions() {
		return permissions;
	}
	
	/**
	 * Sets the permissions.
	 *
	 * @param permissions the new permissions
	 */
	public void setPermissions(UserPermission permissions) {
		this.permissions = permissions;
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
}
