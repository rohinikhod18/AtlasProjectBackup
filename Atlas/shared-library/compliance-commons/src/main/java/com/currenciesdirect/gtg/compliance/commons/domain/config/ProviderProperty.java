/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionProviderProperty.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.commons.domain.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class SanctionProviderProperty.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderProperty {

	/** The enpoint url. */
	private String endPointUrl;

	/** The user name. */
	private String userName;

	/** The pass word. */
	private String passWord;
	
	/** The socket timeout millis. */
	private Integer socketTimeoutMillis;
	
	/** The connection timeout millis. */
	private Integer connectionTimeoutMillis;

	/** The always pass. */
	private Boolean alwaysPass;
	
	/** The Proof Of Address required flag. */
	private Boolean poaRequired;
	
	/** The Proof Of Address required flag. */
	private Boolean poiRequired;
	
	/** The credential type user name. */
	private String credentialTypeUserName;
	
	/** The credential type domain. */
	private String credentialTypeDomain;
	
	/** The credential type ip address. */
	private String credentialTypeIpAddress;
	
	/** The identity verification account name. */
	private String identityVerificationAccountName;
	
	/** The identity verification ruleset. */
	private String identityVerificationRuleset;
	
	/** The mode type. */
	private String modeType;
	
	/** The account type customer id. */
	private String accountTypeCustomerId;
	
	
	

	/**
	 * Gets the end point url.
	 *
	 * @return the endPointUrl
	 */
	public String getEndPointUrl() {
		return endPointUrl;
	}

	/**
	 * Sets the end point url.
	 *
	 * @param endPointUrl the endPointUrl to set
	 */
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the pass word.
	 *
	 * @return the pass word
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Sets the pass word.
	 *
	 * @param passWord the new pass word
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	/**
	 * Gets the socket timeout millis.
	 *
	 * @return the socket timeout millis
	 */
	public Integer getSocketTimeoutMillis() {
		return socketTimeoutMillis;
	}

	/**
	 * Gets the connection timeout millis.
	 *
	 * @return the connection timeout millis
	 */
	public Integer getConnectionTimeoutMillis() {
		return connectionTimeoutMillis;
	}

	/**
	 * Sets the socket timeout millis.
	 *
	 * @param socketTimeoutMillis the new socket timeout millis
	 */
	public void setSocketTimeoutMillis(Integer socketTimeoutMillis) {
		this.socketTimeoutMillis = socketTimeoutMillis;
	}

	/**
	 * Sets the connection timeout millis.
	 *
	 * @param connectionTimeoutMillis the new connection timeout millis
	 */
	public void setConnectionTimeoutMillis(Integer connectionTimeoutMillis) {
		this.connectionTimeoutMillis = connectionTimeoutMillis;
	}
	
	/**
	 * Gets the always pass.
	 *
	 * @return the always pass
	 */
	public Boolean getAlwaysPass() {
		return alwaysPass;
	}

	/**
	 * Sets the always pass.
	 *
	 * @param alwaysPass the new always pass
	 */
	public void setAlwaysPass(Boolean alwaysPass) {
		this.alwaysPass = alwaysPass;
	}

	/**
	 * Gets the credential type user name.
	 *
	 * @return the credential type user name
	 */
	public String getCredentialTypeUserName() {
		return credentialTypeUserName;
	}

	/**
	 * Sets the credential type user name.
	 *
	 * @param credentialTypeUserName the new credential type user name
	 */
	public void setCredentialTypeUserName(String credentialTypeUserName) {
		this.credentialTypeUserName = credentialTypeUserName;
	}

	/**
	 * Gets the credential type domain.
	 *
	 * @return the credential type domain
	 */
	public String getCredentialTypeDomain() {
		return credentialTypeDomain;
	}

	/**
	 * Sets the credential type domain.
	 *
	 * @param credentialTypeDomain the new credential type domain
	 */
	public void setCredentialTypeDomain(String credentialTypeDomain) {
		this.credentialTypeDomain = credentialTypeDomain;
	}

	/**
	 * Gets the credential type ip address.
	 *
	 * @return the credential type ip address
	 */
	public String getCredentialTypeIpAddress() {
		return credentialTypeIpAddress;
	}

	/**
	 * Sets the credential type ip address.
	 *
	 * @param credentialTypeIpAddress the new credential type ip address
	 */
	public void setCredentialTypeIpAddress(String credentialTypeIpAddress) {
		this.credentialTypeIpAddress = credentialTypeIpAddress;
	}

	/**
	 * Gets the identity verification account name.
	 *
	 * @return the identity verification account name
	 */
	public String getIdentityVerificationAccountName() {
		return identityVerificationAccountName;
	}

	/**
	 * Sets the identity verification account name.
	 *
	 * @param identityVerificationAccountName the new identity verification account name
	 */
	public void setIdentityVerificationAccountName(String identityVerificationAccountName) {
		this.identityVerificationAccountName = identityVerificationAccountName;
	}

	/**
	 * Gets the identity verification ruleset.
	 *
	 * @return the identity verification ruleset
	 */
	public String getIdentityVerificationRuleset() {
		return identityVerificationRuleset;
	}

	/**
	 * Sets the identity verification ruleset.
	 *
	 * @param identityVerificationRuleset the new identity verification ruleset
	 */
	public void setIdentityVerificationRuleset(String identityVerificationRuleset) {
		this.identityVerificationRuleset = identityVerificationRuleset;
	}

	/**
	 * Gets the mode type.
	 *
	 * @return the mode type
	 */
	public String getModeType() {
		return modeType;
	}

	/**
	 * Sets the mode type.
	 *
	 * @param modeType the new mode type
	 */
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProviderProperty [endPointUrl=");
		builder.append(endPointUrl);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", passWord=");
		builder.append(passWord);
		builder.append(", socketTimeoutMillis=");
		builder.append(socketTimeoutMillis);
		builder.append(", connectionTimeoutMillis=");
		builder.append(connectionTimeoutMillis);
		builder.append(", credentialTypeUserName=");
		builder.append(credentialTypeUserName);
		builder.append(", credentialTypeDomain=");
		builder.append(credentialTypeDomain);
		builder.append(", credentialTypeIpAddress=");
		builder.append(credentialTypeIpAddress);
		builder.append(", identityVerificationAccountName=");
		builder.append(identityVerificationAccountName);
		builder.append(", identityVerificationRuleset=");
		builder.append(identityVerificationRuleset);
		builder.append(", accountTypeCustomerId=");
		builder.append(accountTypeCustomerId);
		builder.append(", modeType=");
		builder.append(modeType);
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Gets the poa required.
	 *
	 * @return the poaRequired
	 */
	public Boolean getPoaRequired() {
		return poaRequired;
	}

	/**
	 * Sets the poa required.
	 *
	 * @param poaRequired the poaRequired to set
	 */
	public void setPoaRequired(Boolean poaRequired) {
		this.poaRequired = poaRequired;
	}

	/**
	 * Gets the poi required.
	 *
	 * @return the poiRequired
	 */
	public Boolean getPoiRequired() {
		return poiRequired;
	}

	/**
	 * Sets the poi required.
	 *
	 * @param poiRequired the poiRequired to set
	 */
	public void setPoiRequired(Boolean poiRequired) {
		this.poiRequired = poiRequired;
	}

	/**
	 * Gets the account type customer id.
	 *
	 * @return the account type customer id
	 */
	public String getAccountTypeCustomerId() {
		return accountTypeCustomerId;
	}

	/**
	 * Sets the account type customer id.
	 *
	 * @param accountTypeCustomerId the new account type customer id
	 */
	public void setAccountTypeCustomerId(String accountTypeCustomerId) {
		this.accountTypeCustomerId = accountTypeCustomerId;
	}

	
}
