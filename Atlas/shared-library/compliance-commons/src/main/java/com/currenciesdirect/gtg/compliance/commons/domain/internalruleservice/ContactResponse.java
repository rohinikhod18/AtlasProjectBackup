/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class ContactResponse.
 *
 * @author manish
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The contact status. */
	private String contactStatus;

	/** The global check. */
	private GlobalCheckContactResponse globalCheck;

	/** The blacklist. */
	private BlacklistContactResponse blacklist;

	/** The ip check. */
	private IpContactResponse ipCheck;

	/** The country check. */
	private CountryCheckContactResponse countryCheck;

	/** The card fraud check. */
	private CardFraudScoreResponse cardFraudCheck;

	/** The card fraud check. */
	private FraudSightScoreResponse fraudSightCheck;
	
	/** The entity type. */
	private String entityType;
	
	/** The name. */
	private String bankName;
	
	/** The BlacklistPayref*/
	private BlacklistPayrefContactResponse blacklistPayref;
	

	/**
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param banknName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * Gets the global check.
	 *
	 * @return the global check
	 */
	public GlobalCheckContactResponse getGlobalCheck() {
		return globalCheck;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public BlacklistContactResponse getBlacklist() {
		return blacklist;
	}

	/**
	 * Gets the ip check.
	 *
	 * @return the ip check
	 */
	public IpContactResponse getIpCheck() {
		return ipCheck;
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
	 * Sets the global check.
	 *
	 * @param globalCheck
	 *            the new global check
	 */
	public void setGlobalCheck(GlobalCheckContactResponse globalCheck) {
		this.globalCheck = globalCheck;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist
	 *            the new blacklist
	 */
	public void setBlacklist(BlacklistContactResponse blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Sets the ip check.
	 *
	 * @param ipCheck
	 *            the new ip check
	 */
	public void setIpCheck(IpContactResponse ipCheck) {
		this.ipCheck = ipCheck;
	}

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Gets the blacklist payref.
	 *
	 * @return the blacklist payref
	 */
	public BlacklistPayrefContactResponse getBlacklistPayref() {
		return blacklistPayref;
	}

	/**
	 * Sets the blacklist payref.
	 *
	 * @param blacklistPayref the new blacklist payref
	 */
	public void setBlacklistPayref(BlacklistPayrefContactResponse blacklistPayref) {
		this.blacklistPayref = blacklistPayref;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
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
	 * Gets the country check.
	 *
	 * @return the country check
	 */
	public CountryCheckContactResponse getCountryCheck() {
		return countryCheck;
	}

	/**
	 * Sets the country check.
	 *
	 * @param countryCheck
	 *            the new country check
	 */
	public void setCountryCheck(CountryCheckContactResponse countryCheck) {
		this.countryCheck = countryCheck;
	}

	/**
	 * Gets the card fraud check.
	 *
	 * @return the cardFraudCheck
	 */
	public CardFraudScoreResponse getCardFraudCheck() {
		return cardFraudCheck;
	}

	/**
	 * Sets the card fraud check.
	 *
	 * @param cardFraudCheck
	 *            the cardFraudCheck to set
	 */
	public void setCardFraudCheck(CardFraudScoreResponse cardFraudCheck) {
		this.cardFraudCheck = cardFraudCheck;
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
	 * @return the fraudSightCheck
	 */
	public FraudSightScoreResponse getFraudSightCheck() {
		return fraudSightCheck;
	}

	/**
	 * @param fraudSightCheck the fraudSightCheck to set
	 */
	public void setFraudSightCheck(FraudSightScoreResponse fraudSightCheck) {
		this.fraudSightCheck = fraudSightCheck;
	}

}
