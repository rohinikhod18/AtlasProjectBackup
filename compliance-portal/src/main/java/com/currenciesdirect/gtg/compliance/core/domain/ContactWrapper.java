package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;

/**
 * The Class Contact.
 */
public class ContactWrapper extends Contact{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	/** The compliance status. */
	private String complianceStatus;

	/** The country of residence. */
	private String countryOfResidence;

	/** The name. */
	private String name;

	/** The address. */
	private String address;
	
	/** The phone. */
	private String phone;

	/** The mobile. */
	private String mobile;

	/** The nationality full name. */
	private String nationalityFullName;

	/** The reg in. */
	private String regIn;

	/** The reg complete. */
	private String regComplete;
	
	/** The is us client. */
	private Boolean isUsClient;
		
	/** The cust type. */
	private String custType;
	
	/** The organization. */
	private String organization;
	
	/** The is country supported. */
	private Boolean isCountrySupported;

	private String previousKycStatus ;
	
	
	private String previousSanctionStatus ;
	
	
	private String previousFraugsterStatus ;
	
	
	private String previousBlacklistStatus ;
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


	public String getPreviousKycStatus() {
		return previousKycStatus;
	}

	public void setPreviousKycStatus(String previousKycStatus) {
		this.previousKycStatus = previousKycStatus;
	}

	public String getPreviousSanctionStatus() {
		return previousSanctionStatus;
	}

	public void setPreviousSanctionStatus(String previousSanctionStatus) {
		this.previousSanctionStatus = previousSanctionStatus;
	}

	public String getPreviousFraugsterStatus() {
		return previousFraugsterStatus;
	}

	public void setPreviousFraugsterStatus(String previousFraugsterStatus) {
		this.previousFraugsterStatus = previousFraugsterStatus;
	}

	public String getPreviousBlacklistStatus() {
		return previousBlacklistStatus;
	}

	public void setPreviousBlacklistStatus(String previousBlacklistStatus) {
		this.previousBlacklistStatus = previousBlacklistStatus;
	}

	public String getNationalityFullName() {
		return nationalityFullName;
	}

	public void setNationalityFullName(String nationalityFullName) {
		this.nationalityFullName = nationalityFullName;
	}
	
}
