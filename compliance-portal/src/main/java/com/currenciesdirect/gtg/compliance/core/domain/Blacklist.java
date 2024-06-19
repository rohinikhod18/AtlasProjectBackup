package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class Blacklist.
 */
public class Blacklist {

	/** The id. */
	private Integer id;
	
	/** The entity type. */
	private String entityType;
	
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
	private Boolean status;

	/** The pass count. */
	private Integer passCount;

	/** The fail count. */
	private Integer failCount;
	
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
	
	/** added to check whether service check is required to perform or not. */
	/** Requirement of Blacklist check.*/
	private Boolean isRequired;
	
	/** added by Vishal J to store what is status of response. */
	/** Value of status (PASS, FAIL, NOT_REQUIRED etc.) */
	private String statusValue;

	/** The domain. */
	private String domain;
	/** The data from table that matched. */
	private String domainMatchedData;
	
	/** The bank name is added to resolve AT-1198*/ 
	private String bankName;
	
	/** The bank name from table that matched */
	private String bankNameMatchedData;
	
	private String prevStatusValue;
	
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
		if(null == bankNameMatchedData || bankNameMatchedData.isEmpty() ){
			this.bankNameMatchedData = "";
		}else{
			this.bankNameMatchedData = "(" + bankNameMatchedData + ")";
		}
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
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Gets the pass count.
	 *
	 * @return the pass count
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 *
	 * @param passCount the new pass count
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 *
	 * @return the fail count
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 *
	 * @param failCount the new fail count
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
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
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
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
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	/**
	 * Gets the status value.
	 *
	 * @return the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the status value.
	 *
	 * @param statusValue the new status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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
		if(null == ipMatchedData || ipMatchedData.isEmpty() ){
			this.ipMatchedData = "";
		}else{
			this.ipMatchedData = "(" + ipMatchedData + ")";
		}
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
		if(null == emailMatchedData || emailMatchedData.isEmpty() ){
			this.emailMatchedData = "";
		}else{
			this.emailMatchedData = "(" + emailMatchedData + ")";
		}
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
		if(null == phoneMatchedData || phoneMatchedData.isEmpty() ){
			this.phoneMatchedData = "";
		}else{
			this.phoneMatchedData = "(" + phoneMatchedData + ")";
		}
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
		
		if(null == accNumberMatchedData || accNumberMatchedData.isEmpty() ){
			this.accNumberMatchedData = "";
		}else{
			this.accNumberMatchedData = "(" + accNumberMatchedData + ")";
		}
		
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
		if(null == nameMatchedData || nameMatchedData.isEmpty() ){
			this.nameMatchedData = "";
		}else{
			this.nameMatchedData = "(" + nameMatchedData + ")";
		}
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
		if(null == websiteMatchedData || websiteMatchedData.isEmpty() ){
			this.websiteMatchedData = "";
		}else{
			this.websiteMatchedData = "(" + websiteMatchedData + ")";
		}
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
		
		if(null == domainMatchedData || domainMatchedData.isEmpty() ){
			this.domainMatchedData = "";
		}else{
			this.domainMatchedData = "(" + domainMatchedData + ")";
		}
	}

	public String getPrevStatusValue() {
		return prevStatusValue;
	}

	public void setPrevStatusValue(String prevStatusValue) {
		this.prevStatusValue = prevStatusValue;
	}
	
	
}
