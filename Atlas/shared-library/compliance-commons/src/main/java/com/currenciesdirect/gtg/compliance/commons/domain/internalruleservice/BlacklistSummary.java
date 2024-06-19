package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BlacklistSummary.
 */
public class BlacklistSummary implements Serializable{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	@ApiModelProperty(value = "The status", example = "", required = true)
	private String status;

	/** The ip. */
	@ApiModelProperty(value = "The IP address", example = "", required = true)
	private String ip;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The IP data that matched the blacklist", example = "", required = true)
	private String ipMatchedData;

	/** The email. */
	@ApiModelProperty(value = "The email address", example = "", required = true)
	private String email;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The email address that matched the blacklist", example = "", required = true)
	private String emailMatchedData;

	/** The phone. */
	@ApiModelProperty(value = "The phone number", example = "", required = true)
	private Boolean phone;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The phone data that matched the blacklist", example = "", required = true)
	private String phoneMatchedData;

	/** The account number. */
	@ApiModelProperty(value = "The account number", example = "", required = true)
	private String accountNumber;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The account number data that matched the blacklist", example = "", required = true)
	private String accNumberMatchedData;

	/** The name. */
	@ApiModelProperty(value = "The name", example = "", required = true)
	private String name;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The name data that matched the blacklist", example = "", required = true)
	private String nameMatchedData;

	/** The Domain. */
	@ApiModelProperty(value = "The domain name to check", example = "", required = true)
	private String domain;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The domain name that matched the blacklist", example = "", required = true)
	private String domainMatchedData;

	/** The website. */
	@ApiModelProperty(value = "The website", example = "", required = true)
	private String webSite;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The website data that matched the blacklist", example = "", required = true)
	private String websiteMatchedData;
	
	/** The Bank name is added to resolve  AT-1198*/
	@ApiModelProperty(value = "The bank name", example = "", required = true)
	private String bankName;
	
	/** The data from table that matched. */
	@ApiModelProperty(value = "The bank name data that matched the blacklist", example = "", required = true)
	private String bankNameMatchedData;
	
	/**
	 * Gets the bank name.
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return
	 */
	public String getBankNameMatchedData() {
		return bankNameMatchedData;
	}

	/**
	 * @param bankNameMatchedData
	 */
	public void setBankNameMatchedData(String bankNameMatchedData) {
		this.bankNameMatchedData = bankNameMatchedData;
	}

	

	/**
	 * Gets the account number.
	 *
	 * @return the account number
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber
	 *            the new account number
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public Boolean getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(Boolean phone) {
		this.phone = phone;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the Domain.
	 *
	 * @return the Domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the Domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Gets the web site.
	 *
	 * @return the web site
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * Sets the web site.
	 *
	 * @param webSite
	 *            the new web site
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * Gets the ip matched data.
	 *
	 * @return the ipMatchedData
	 */
	public String getIpMatchedData() {
		return ipMatchedData;
	}

	/**
	 * Sets the ip matched data.
	 *
	 * @param ipMatchedData
	 *            the ipMatchedData to set
	 */
	public void setIpMatchedData(String ipMatchedData) {
		this.ipMatchedData = ipMatchedData;
	}

	/**
	 * Gets the email matched data.
	 *
	 * @return the emailMatchedData
	 */
	public String getEmailMatchedData() {
		return emailMatchedData;
	}

	/**
	 * Sets the email matched data.
	 *
	 * @param emailMatchedData
	 *            the emailMatchedData to set
	 */
	public void setEmailMatchedData(String emailMatchedData) {
		this.emailMatchedData = emailMatchedData;
	}

	/**
	 * Gets the phone matched data.
	 *
	 * @return the phoneMatchedData
	 */
	public String getPhoneMatchedData() {
		return phoneMatchedData;
	}

	/**
	 * Sets the phone matched data.
	 *
	 * @param phoneMatchedData
	 *            the phoneMatchedData to set
	 */
	public void setPhoneMatchedData(String phoneMatchedData) {
		this.phoneMatchedData = phoneMatchedData;
	}

	/**
	 * Gets the acc number matched data.
	 *
	 * @return the accNumberMatchedData
	 */
	public String getAccNumberMatchedData() {
		return accNumberMatchedData;
	}

	/**
	 * Sets the acc number matched data.
	 *
	 * @param accNumberMatchedData
	 *            the accNumberMatchedData to set
	 */
	public void setAccNumberMatchedData(String accNumberMatchedData) {
		this.accNumberMatchedData = accNumberMatchedData;
	}

	/**
	 * Gets the name matched data.
	 *
	 * @return the nameMatchedData
	 */
	public String getNameMatchedData() {
		return nameMatchedData;
	}

	/**
	 * Sets the name matched data.
	 *
	 * @param nameMatchedData
	 *            the nameMatchedData to set
	 */
	public void setNameMatchedData(String nameMatchedData) {
		this.nameMatchedData = nameMatchedData;
	}

	/**
	 * Gets the domain matched data.
	 *
	 * @return the domainMatchedData
	 */
	public String getDomainMatchedData() {
		return domainMatchedData;
	}

	/**
	 * Sets the domain matched data.
	 *
	 * @param domainMatchedData
	 *            the domainMatchedData to set
	 */
	public void setDomainMatchedData(String domainMatchedData) {
		this.domainMatchedData = domainMatchedData;
	}

	/**
	 * Gets the website matched data.
	 *
	 * @return the websiteMatchedData
	 */
	public String getWebsiteMatchedData() {
		return websiteMatchedData;
	}

	/**
	 * Sets the website matched data.
	 *
	 * @param websiteMatchedData
	 *            the websiteMatchedData to set
	 */
	public void setWebsiteMatchedData(String websiteMatchedData) {
		this.websiteMatchedData = websiteMatchedData;
	}

}
