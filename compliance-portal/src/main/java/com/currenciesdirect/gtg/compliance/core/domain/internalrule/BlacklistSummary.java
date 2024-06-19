package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

/**
 * The Class BlacklistSummary.
 */
public class BlacklistSummary {

	/** The ip. */
	private String ip;
	/** The data from table that matched. */
	private String ipMatchedData;

	/** The email. */
	private String email;
	/** The data from table that matched. */
	private String emailMatchedData;

	/** The phone. */
	private Boolean phone;
	/** The data from table that matched. */
	private String phoneMatchedData;

	/** The status. */
	private String status;
	
	/** The account number. */
	private String accountNumber;
	/** The data from table that matched. */
	private String accNumberMatchedData;
	
	/** The name. */
	private String name;
	/** The data from table that matched. */
	private String nameMatchedData;
	
	/** The web site. */
	private String webSite;
	/** The data from table that matched. */
	private String websiteMatchedData;

	/** The domain. */
	private String domain;
	/** The data from table that matched. */
	private String domainMatchedData;
	
	/** The data from table that matched. */
	private String matchedData;
	

	/** The bank name is added to resolve AT-1198*/ 
	private String bankName;
	/** The data from table that matched. */
	private String bankNameMatchedData;
	

	/**
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
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
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
	 * @param accountNumber the new account number
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param webSite the new web site
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * Gets the matched data.
	 *
	 * @return the matched data
	 */
	public String getMatchedData() {
		return matchedData;
	}

	/**
	 * Sets the matched data.
	 *
	 * @param matchedData the new matched data
	 */
	public void setMatchedData(String matchedData) {
		this.matchedData = matchedData;
	}

	/**
	 * Gets the ip matched data.
	 *
	 * @return the ip matched data
	 */
	public String getIpMatchedData() {
		return ipMatchedData;
	}

	/**
	 * Sets the ip matched data.
	 *
	 * @param ipMatchedData the new ip matched data
	 */
	public void setIpMatchedData(String ipMatchedData) {
		this.ipMatchedData = ipMatchedData;
	}

	/**
	 * Gets the email matched data.
	 *
	 * @return the email matched data
	 */
	public String getEmailMatchedData() {
		return emailMatchedData;
	}

	/**
	 * Sets the email matched data.
	 *
	 * @param emailMatchedData the new email matched data
	 */
	public void setEmailMatchedData(String emailMatchedData) {
		this.emailMatchedData = emailMatchedData;
	}

	/**
	 * Gets the phone matched data.
	 *
	 * @return the phone matched data
	 */
	public String getPhoneMatchedData() {
		return phoneMatchedData;
	}

	/**
	 * Sets the phone matched data.
	 *
	 * @param phoneMatchedData the new phone matched data
	 */
	public void setPhoneMatchedData(String phoneMatchedData) {
		this.phoneMatchedData = phoneMatchedData;
	}

	/**
	 * Gets the acc number matched data.
	 *
	 * @return the acc number matched data
	 */
	public String getAccNumberMatchedData() {
		return accNumberMatchedData;
	}

	/**
	 * Sets the acc number matched data.
	 *
	 * @param accNumberMatchedData the new acc number matched data
	 */
	public void setAccNumberMatchedData(String accNumberMatchedData) {
		this.accNumberMatchedData = accNumberMatchedData;
	}

	/**
	 * Gets the name matched data.
	 *
	 * @return the name matched data
	 */
	public String getNameMatchedData() {
		return nameMatchedData;
	}

	/**
	 * Sets the name matched data.
	 *
	 * @param nameMatchedData the new name matched data
	 */
	public void setNameMatchedData(String nameMatchedData) {
		this.nameMatchedData = nameMatchedData;
	}

	/**
	 * Gets the website matched data.
	 *
	 * @return the website matched data
	 */
	public String getWebsiteMatchedData() {
		return websiteMatchedData;
	}

	/**
	 * Sets the website matched data.
	 *
	 * @param websiteMatchedData the new website matched data
	 */
	public void setWebsiteMatchedData(String websiteMatchedData) {
		this.websiteMatchedData = websiteMatchedData;
	}

	/**
	 * Gets the domain matched data.
	 *
	 * @return the domain matched data
	 */
	public String getDomainMatchedData() {
		return domainMatchedData;
	}

	/**
	 * Sets the domain matched data.
	 *
	 * @param domainMatchedData the new domain matched data
	 */
	public void setDomainMatchedData(String domainMatchedData) {
		this.domainMatchedData = domainMatchedData;
	}
	
}
