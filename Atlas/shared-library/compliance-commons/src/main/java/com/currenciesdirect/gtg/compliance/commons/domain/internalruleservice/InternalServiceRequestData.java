/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class InternalServiceRequestData.
 *
 * @author manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalServiceRequestData implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The name. */
	private String name;

	/** The cc name. */
	private String ccName;

	/** The company name. */
	private String companyName;
	
	/** The web site. */
	private String webSite;

	/** The email. */
	private String email;

	/** The domain. */
	private String domain;

	/** The phone no list. */
	private List<String> phoneNoList;

	/** The ip address. */
	private String ipAddress;

	/** The is wild card search. */
	private Boolean isWildCardSearch;

	/** The state. */
	private String state;

	/** The country. */
	private String country;
	
	/** The org legal entity. */
	private String orgLegalEntity;

	/** The post code. */
	private String postCode;

	/** The third party payment. */
	private Boolean thirdPartyPayment;

	/** The source application. */
	private String sourceApplication;

	/** The entity type. */
	private String entityType;

	/** The account number. */
	private String accountNumber;

	/** The card fraud score. */
	private Double cardFraudScore;

	/** The card fraud score threshold. */
	private Double cardFraudScoreThreshold;

	/** The payment method. */
	private String paymentMethod;
	private Boolean isEmailDomainCheckRequired;
	
	/** The card fraud score. */
	private String fraudSightScore;

	/** The card fraud score threshold. */
	private String fraudSightMessage;
	/**
	 * The Bank Name
	 */
	private String bankName;
	
	/** The payement refernce. */
	private String payementRefernce;   //Add for AT-3658
	
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
	 * Gets the checks if is domain check required.
	 *
	 * @return the checks if is domain check required
	 */
	public Boolean getIsEmailDomainCheckRequired() {
		return isEmailDomainCheckRequired;
	}

	/**
	 * Sets the checks if is domain check required.
	 *
	 * @param isDomainCheckRequired
	 *            the new checks if is domain check required
	 */
	public void setIsEmailDomainCheckRequired(Boolean isDomainCheckRequired) {
		this.isEmailDomainCheckRequired = isDomainCheckRequired;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
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
	 * Gets the phone no list.
	 *
	 * @return the phone no list
	 */
	public List<String> getPhoneNoList() {
		return phoneNoList;
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
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
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
	 * Gets the org legal entity.
	 *
	 * @return the org legal entity
	 */
	public String getOrgLegalEntity() {
		return orgLegalEntity;
	}

	/**
	 * Sets the org legal entity.
	 *
	 * @param orgLegalEntity
	 *            the new org legal entity
	 */
	public void setOrgLegalEntity(String orgLegalEntity) {
		this.orgLegalEntity = orgLegalEntity;
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
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
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
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Sets the phone no list.
	 *
	 * @param phoneNoList
	 *            the new phone no list
	 */
	public void setPhoneNoList(List<String> phoneNoList) {
		this.phoneNoList = phoneNoList;
	}

	/**
	 * Adds the phone no.
	 *
	 * @param phoneNo
	 *            the phone no
	 */
	@JsonIgnore
	public void addPhoneNo(String phoneNo) {
		if (null == this.phoneNoList)
			this.phoneNoList = new ArrayList<>();
		this.phoneNoList.add(phoneNo);
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress
	 *            the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the post code.
	 *
	 * @param postCode
	 *            the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
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
	 * Gets the checks if is wild card search.
	 *
	 * @return the checks if is wild card search
	 */
	public Boolean getIsWildCardSearch() {
		return isWildCardSearch;
	}

	/**
	 * Sets the checks if is wild card search.
	 *
	 * @param isWildCardSearch
	 *            the new checks if is wild card search
	 */
	public void setIsWildCardSearch(Boolean isWildCardSearch) {
		this.isWildCardSearch = isWildCardSearch;
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
	 * @param entityType
	 *            the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName
	 *            the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod
	 *            the new payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the card fraud score.
	 *
	 * @return the card fraud score
	 */
	public Double getCardFraudScore() {
		return cardFraudScore;
	}

	/**
	 * Sets the card fraud score.
	 *
	 * @param cardFraudScore
	 *            the new card fraud score
	 */
	public void setCardFraudScore(Double cardFraudScore) {
		this.cardFraudScore = cardFraudScore;
	}

	/**
	 * Gets the card fraud score threshold.
	 *
	 * @return the cardFraudScoreThreshold
	 */
	public Double getCardFraudScoreThreshold() {
		return cardFraudScoreThreshold;
	}

	/**
	 * Sets the card fraud score threshold.
	 *
	 * @param cardFraudScoreThreshold
	 *            the cardFraudScoreThreshold to set
	 */
	public void setCardFraudScoreThreshold(Double cardFraudScoreThreshold) {
		this.cardFraudScoreThreshold = cardFraudScoreThreshold;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return the third party payment
	 */
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment
	 *            the new third party payment
	 */
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the cc name.
	 *
	 * @return the cc name
	 */
	public String getCcName() {
		return ccName;
	}

	/**
	 * Sets the cc name.
	 *
	 * @param ccName
	 *            the new cc name
	 */
	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	/**
	 * Gets the fraud sight score.
	 *
	 * @return the fraud sight score
	 */
	public String getFraudSightScore() {
		return fraudSightScore;
	}

	/**
	 * Sets the fraud sight score.
	 *
	 * @param fraudSightScore the new fraud sight score
	 */
	public void setFraudSightScore(String fraudSightScore) {
		this.fraudSightScore = fraudSightScore;
	}

	/**
	 * @return the fraudSightMessage
	 */
	public String getFraudSightMessage() {
		return fraudSightMessage;
	}

	/**
	 * @param fraudSightMessage the fraudSightMessage to set
	 */
	public void setFraudSightMessage(String fraudSightMessage) {
		this.fraudSightMessage = fraudSightMessage;
	}

	/**
	 * Gets the payement refernce.
	 *
	 * @return the payement refernce
	 */
	public String getPayementRefernce() {
		return payementRefernce;
	}

	/**
	 * Sets the payement refernce.
	 *
	 * @param payementRefernce the new payement refernce
	 */
	public void setPayementRefernce(String payementRefernce) {
		this.payementRefernce = payementRefernce;
	}
	
}
