/*
 * 
 * Copyright 2012-2017 Currencies Direct Ltd, United Kingdom
 *
 * titan-wrapper-service: PayeeBank.java Last modified: 26-Jul-2017
 */
package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.io.Serializable;
import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * POJO class for FunctionGroupMapping Details. payee bank details information
 * 
 * @author SwapnaliK
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperPayeeBank implements IDomain,Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WrapperPayeeBank.class);

	/** The legacy payee bank ID. */
	@JsonProperty("legacy_payee_bank_id")
	private Integer legacyPayeeBankID;

	/** The account name. */
	@JsonProperty("account_name")
	private String accountName;

	/** The account name in local language. */
	@JsonProperty("account_name_in_local_language")
	private String accountNameInLocalLanguage;

	/** The account number. */
	@JsonProperty("account_number")
	private String accountNumber;

	/** The iban. */
	@JsonProperty("iban")
	private String iban;

	/** The bank BIC. */
	@JsonProperty("bank_bic")
	private String bankBIC; // swift

	/** The bank intra country code. */
	@JsonProperty("bank_intra_country_code")
	private String bankIntraCountryCode; // sic

	/** The bank intra country code type. */
	@JsonProperty("bank_intra_country_code_type")
	private String bankIntraCountryCodeType;

	/** The bank name. */
	@JsonProperty("bank_name")
	private String bankName;

	/** The bank street. */
	@JsonProperty("bank_street")
	private String bankStreet;

	/** The bank city. */
	@JsonProperty("bank_city")
	private String bankCity;

	/** The bank state. */
	@JsonProperty("bank_state")
	private String bankState;

	/** The bank post code. */
	@JsonProperty("bank_post_code")
	private String bankPostCode;

	/** The intermediary bank BIC. */
	@JsonProperty("intermediary_bank_bic")
	private String intermediaryBankBIC;

	/** The intermediary bank intra country code. */
	@JsonProperty("intermediary_bank_intra_country_code")
	private String intermediaryBankIntraCountryCode;

	/** The intermediary bank intra country code type. */
	@JsonProperty("intermediary_bank_intra_country_code_type")
	private String intermediaryBankIntraCountryCodeType;

	/** The intermediary bank name. */
	@JsonProperty("intermediary_bank_name")
	private String intermediaryBankName;

	/** The intermediary bank street. */
	@JsonProperty("intermediary_bank_street")
	private String intermediaryBankStreet;

	/** The intermediary bank city. */
	@JsonProperty("intermediary_bank_city")
	private String intermediaryBankCity;

	/** The intermediary bank state. */
	@JsonProperty("intermediary_bank_state")
	private String intermediaryBankState;

	/** The intermediary bank post code. */
	@JsonProperty("intermediary_bank_post_code")
	private String intermediaryBankPostCode;

	/** The payee bank country code. */
	@JsonProperty("payee_bank_country_code")
	private String payeeBankCountryCode;

	/** The intermediary bank country code. */
	@JsonProperty("intermediary_bank_country_code")
	private String intermediaryBankCountryCode;

	/** The created on. */
	@JsonProperty("created_on")
	private Timestamp createdOn;

	/** The created by. */
	@JsonProperty("created_by")
	private Integer createdBy;

	/** The updated by. */
	@JsonProperty("updated_by")
	private Integer updatedBy;

	/** The updated on. */
	@JsonProperty("updated_on")
	private Timestamp updatedOn;

	/** The payee currency code. */
	@JsonProperty("payee_currency_code")
	private String payeeCurrencyCode;

	/** The payee bank country code. */
	@JsonProperty("payee_bank_country_name")
	private String payeeBankCountryName;

	/** The intermediary bank country name. */
	@JsonProperty("intermediary_bank_country_Name")
	private String intermediaryBankCountryName;

	/** The updated on date for UI. */
	@SuppressWarnings("squid:S1450")
	private String updatedOnDateForUI;

	/**
	 * Gets the legacy payee bank ID.
	 *
	 * @return the legacyPayeeBankID
	 */
	public Integer getLegacyPayeeBankID() {
		return legacyPayeeBankID;
	}

	/**
	 * Sets the legacy payee bank ID.
	 *
	 * @param legacyPayeeBankID
	 *            the legacyPayeeBankID to set
	 */
	public void setLegacyPayeeBankID(Integer legacyPayeeBankID) {
		if (null != legacyPayeeBankID && 0 != legacyPayeeBankID) {
			this.legacyPayeeBankID = legacyPayeeBankID;
		}
	}

	/**
	 * Gets the account name.
	 *
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Sets the account name.
	 *
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		if (null != accountName && !accountName.isEmpty()) {
			this.accountName = accountName;
		}
	}

	/**
	 * Gets the account name in local language.
	 *
	 * @return the accountNameInLocalLanguage
	 */
	public String getAccountNameInLocalLanguage() {
		return accountNameInLocalLanguage;
	}

	/**
	 * Sets the account name in local language.
	 *
	 * @param accountNameInLocalLanguage
	 *            the accountNameInLocalLanguage to set
	 */
	public void setAccountNameInLocalLanguage(String accountNameInLocalLanguage) {
		if (null != accountNameInLocalLanguage) {
			this.accountNameInLocalLanguage = accountNameInLocalLanguage;
		}
	}

	/**
	 * Gets the account number.
	 *
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		if (null != accountNumber && !accountNumber.isEmpty()) {
			this.accountNumber = accountNumber;
		}
	}

	/**
	 * Gets the iban.
	 *
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Sets the iban.
	 *
	 * @param iban
	 *            the iban to set
	 */
	public void setIban(String iban) {
		if (null != iban && !iban.isEmpty()) {
			this.iban = iban;
		}
	}

	/**
	 * Gets the bank BIC.
	 *
	 * @return the bankBIC
	 */
	public String getBankBIC() {
		return bankBIC;
	}

	/**
	 * Sets the bank BIC.
	 *
	 * @param bankBIC
	 *            the bankBIC to set
	 */
	public void setBankBIC(String bankBIC) {
		if (null != bankBIC && !bankBIC.isEmpty()) {
			this.bankBIC = bankBIC;
		}
	}

	/**
	 * Gets the bank intra country code.
	 *
	 * @return the bankIntraCountryCode
	 */
	public String getBankIntraCountryCode() {
		return bankIntraCountryCode;
	}

	/**
	 * Sets the bank intra country code.
	 *
	 * @param bankIntraCountryCode
	 *            the bankIntraCountryCode to set
	 */
	public void setBankIntraCountryCode(String bankIntraCountryCode) {
		if (null != bankIntraCountryCode && !bankIntraCountryCode.isEmpty()) {
			this.bankIntraCountryCode = bankIntraCountryCode;
		}
	}

	/**
	 * Gets the bank intra country code type.
	 *
	 * @return the bankIntraCountryCodeType
	 */
	public String getBankIntraCountryCodeType() {
		return bankIntraCountryCodeType;
	}

	/**
	 * Sets the bank intra country code type.
	 *
	 * @param bankIntraCountryCodeType
	 *            the bankIntraCountryCodeType to set
	 */
	public void setBankIntraCountryCodeType(String bankIntraCountryCodeType) {
		if (null != bankIntraCountryCodeType && !bankIntraCountryCodeType.isEmpty()) {
			this.bankIntraCountryCodeType = bankIntraCountryCodeType;
		}
	}

	/**
	 * Gets the bank name.
	 *
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		if (null != bankName && !bankName.isEmpty()) {
			this.bankName = bankName;
		}
	}

	/**
	 * Gets the bank street.
	 *
	 * @return the bankStreet
	 */
	public String getBankStreet() {
		return bankStreet;
	}

	/**
	 * Sets the bank street.
	 *
	 * @param bankStreet
	 *            the bankStreet to set
	 */
	public void setBankStreet(String bankStreet) {
		if (null != bankStreet) {
			this.bankStreet = bankStreet;
		}
	}

	/**
	 * Gets the bank city.
	 *
	 * @return the bankCity
	 */
	public String getBankCity() {
		return bankCity;
	}

	/**
	 * Sets the bank city.
	 *
	 * @param bankCity
	 *            the bankCity to set
	 */
	public void setBankCity(String bankCity) {
		if (null != bankCity) {
			this.bankCity = bankCity;
		}
	}

	/**
	 * Gets the bank state.
	 *
	 * @return the bankState
	 */
	public String getBankState() {
		return bankState;
	}

	/**
	 * Sets the bank state.
	 *
	 * @param bankState
	 *            the bankState to set
	 */
	public void setBankState(String bankState) {
		if (null != bankState) {
			this.bankState = bankState;
		}
	}

	/**
	 * Gets the bank post code.
	 *
	 * @return the bankPostCode
	 */
	public String getBankPostCode() {
		return bankPostCode;
	}

	/**
	 * Sets the bank post code.
	 *
	 * @param bankPostCode
	 *            the bankPostCode to set
	 */
	public void setBankPostCode(String bankPostCode) {
		if (null != bankPostCode) {
			this.bankPostCode = bankPostCode;
		}
	}

	/**
	 * Gets the intermediary bank BIC.
	 *
	 * @return the intermediaryBankBIC
	 */
	public String getIntermediaryBankBIC() {
		return intermediaryBankBIC;
	}

	/**
	 * Sets the intermediary bank BIC.
	 *
	 * @param intermediaryBankBIC
	 *            the intermediaryBankBIC to set
	 */
	public void setIntermediaryBankBIC(String intermediaryBankBIC) {
		if (null != intermediaryBankBIC && !intermediaryBankBIC.isEmpty()) {
			this.intermediaryBankBIC = intermediaryBankBIC;
		}
	}

	/**
	 * Gets the intermediary bank intra country code.
	 *
	 * @return the intermediaryBankIntraCountryCode
	 */
	public String getIntermediaryBankIntraCountryCode() {
		return intermediaryBankIntraCountryCode;
	}

	/**
	 * Sets the intermediary bank intra country code.
	 *
	 * @param intermediaryBankIntraCountryCode
	 *            the intermediaryBankIntraCountryCode to set
	 */
	public void setIntermediaryBankIntraCountryCode(String intermediaryBankIntraCountryCode) {
		if (null != intermediaryBankIntraCountryCode && !intermediaryBankIntraCountryCode.isEmpty()) {
			this.intermediaryBankIntraCountryCode = intermediaryBankIntraCountryCode;
		}
	}

	/**
	 * Gets the intermediary bank intra country code type.
	 *
	 * @return the intermediaryBankIntraCountryCodeType
	 */
	public String getIntermediaryBankIntraCountryCodeType() {
		return intermediaryBankIntraCountryCodeType;
	}

	/**
	 * Sets the intermediary bank intra country code type.
	 *
	 * @param intermediaryBankIntraCountryCodeType
	 *            the intermediaryBankIntraCountryCodeType to set
	 */
	public void setIntermediaryBankIntraCountryCodeType(String intermediaryBankIntraCountryCodeType) {
		if (null != intermediaryBankIntraCountryCodeType && !intermediaryBankIntraCountryCodeType.isEmpty()) {
			this.intermediaryBankIntraCountryCodeType = intermediaryBankIntraCountryCodeType;
		}
	}

	/**
	 * Gets the intermediary bank name.
	 *
	 * @return the intermediaryBankName
	 */
	public String getIntermediaryBankName() {
		return intermediaryBankName;
	}

	/**
	 * Sets the intermediary bank name.
	 *
	 * @param intermediaryBankName
	 *            the intermediaryBankName to set
	 */
	public void setIntermediaryBankName(String intermediaryBankName) {
		if (null != intermediaryBankName && !intermediaryBankName.isEmpty()) {
			this.intermediaryBankName = intermediaryBankName;
		}
	}

	/**
	 * Gets the intermediary bank street.
	 *
	 * @return the intermediaryBankStreet
	 */
	public String getIntermediaryBankStreet() {
		return intermediaryBankStreet;
	}

	/**
	 * Sets the intermediary bank street.
	 *
	 * @param intermediaryBankStreet
	 *            the intermediaryBankStreet to set
	 */
	public void setIntermediaryBankStreet(String intermediaryBankStreet) {
		if (null != intermediaryBankStreet && !intermediaryBankStreet.isEmpty()) {
			this.intermediaryBankStreet = intermediaryBankStreet;
		}
	}

	/**
	 * Gets the intermediary bank city.
	 *
	 * @return the intermediaryBankCity
	 */
	public String getIntermediaryBankCity() {
		return intermediaryBankCity;
	}

	/**
	 * Sets the intermediary bank city.
	 *
	 * @param intermediaryBankCity
	 *            the intermediaryBankCity to set
	 */
	public void setIntermediaryBankCity(String intermediaryBankCity) {
		if (null != intermediaryBankCity && !intermediaryBankCity.isEmpty()) {
			this.intermediaryBankCity = intermediaryBankCity;
		}
	}

	/**
	 * Gets the intermediary bank state.
	 *
	 * @return the intermediaryBankState
	 */
	public String getIntermediaryBankState() {
		return intermediaryBankState;
	}

	/**
	 * Sets the intermediary bank state.
	 *
	 * @param intermediaryBankState
	 *            the intermediaryBankState to set
	 */
	public void setIntermediaryBankState(String intermediaryBankState) {
		if (null != intermediaryBankState && !intermediaryBankState.isEmpty()) {
			this.intermediaryBankState = intermediaryBankState;
		}
	}

	/**
	 * Gets the intermediary bank post code.
	 *
	 * @return the intermediaryBankPostCode
	 */
	public String getIntermediaryBankPostCode() {
		return intermediaryBankPostCode;
	}

	/**
	 * Sets the intermediary bank post code.
	 *
	 * @param intermediaryBankPostCode
	 *            the intermediaryBankPostCode to set
	 */
	public void setIntermediaryBankPostCode(String intermediaryBankPostCode) {
		if (null != intermediaryBankPostCode && !intermediaryBankPostCode.isEmpty()) {
			this.intermediaryBankPostCode = intermediaryBankPostCode;
		}
	}

	/**
	 * Gets the payee bank country code.
	 *
	 * @return the payee bank country code
	 */
	public String getPayeeBankCountryCode() {
		return payeeBankCountryCode;
	}

	/**
	 * Sets the payee bank country code.
	 *
	 * @param payeeBankCountryCode
	 *            the new payee bank country code
	 */
	public void setPayeeBankCountryCode(String payeeBankCountryCode) {
		if (null != payeeBankCountryCode && !payeeBankCountryCode.isEmpty()) {
			this.payeeBankCountryCode = payeeBankCountryCode;
		}
	}

	/**
	 * Gets the intermediary bank country code.
	 *
	 * @return the intermediary bank country code
	 */
	public String getIntermediaryBankCountryCode() {
		return intermediaryBankCountryCode;
	}

	/**
	 * Sets the intermediary bank country code.
	 *
	 * @param intermediaryBankCountryCode
	 *            the new intermediary bank country code
	 */
	public void setIntermediaryBankCountryCode(String intermediaryBankCountryCode) {
		if (null != intermediaryBankCountryCode && !intermediaryBankCountryCode.isEmpty()) {
			this.intermediaryBankCountryCode = intermediaryBankCountryCode;
		}
	}

	/**
	 * Gets the created on.
	 *
	 * @return the createdOn
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(Timestamp createdOn) {
		if (null != createdOn) {
			this.createdOn = createdOn;
		}
	}

	/**
	 * Gets the created by.
	 *
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		if (null != createdBy) {
			this.createdBy = createdBy;
		}
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		if (null != updatedBy) {
			this.updatedBy = updatedBy;
		}
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updatedOn
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the updatedOn to set
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		if (null != updatedOn) {
			this.updatedOn = updatedOn;
		}
	}

	/**
	 * Gets the payee currency code.
	 *
	 * @return the payee currency code
	 */
	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	/**
	 * Sets the payee currency code.
	 *
	 * @param payeeCurrencyCode
	 *            the new payee currency code
	 */
	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		if (null != payeeCurrencyCode && !payeeCurrencyCode.isEmpty()) {
			this.payeeCurrencyCode = payeeCurrencyCode;
		}
	}

	/**
	 * Gets the payee bank country name.
	 *
	 * @return the payeeBankCountryName
	 */
	public String getPayeeBankCountryName() {
		return payeeBankCountryName;
	}

	/**
	 * Sets the payee bank country name.
	 *
	 * @param payeeBankCountryName
	 *            the payeeBankCountryName to set
	 */
	public void setPayeeBankCountryName(String payeeBankCountryName) {
		this.payeeBankCountryName = payeeBankCountryName;
	}

	/**
	 * Gets the intermediary bank country name.
	 *
	 * @return the intermediaryBankCountryName
	 */
	public String getIntermediaryBankCountryName() {
		return intermediaryBankCountryName;
	}

	/**
	 * Sets the intermediary bank country name.
	 *
	 * @param intermediaryBankCountryName
	 *            the intermediaryBankCountryName to set
	 */
	public void setIntermediaryBankCountryName(String intermediaryBankCountryName) {
		this.intermediaryBankCountryName = intermediaryBankCountryName;
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param timestamp
	 *            the str
	 * @return true, if is null or empty
	 */
	public static boolean isNull(Timestamp timestamp) {
		boolean result = true;
		if (timestamp != null)
			return false;

		return result;
	}

	/**
	 * Gets the updated on date for UI.
	 *
	 * @return the updated on date for UI
	 */
	public String getUpdatedOnDateForUI() {
		try {
			if (!isNull(this.getUpdatedOn())) {
				this.updatedOnDateForUI = DateTimeFormatter.dateTimeFormatter(this.getUpdatedOn());
			} else {
				this.updatedOnDateForUI = Constants.DASH_DETAILS_PAGE;
			}
		} catch (Exception exception) {
			LOG.debug("Exception in getUpdatedOnDateForUI:", exception);
			this.updatedOnDateForUI = Constants.DASH_DETAILS_PAGE;
		}
		return updatedOnDateForUI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", legacyPayeeBankID=");
		builder.append(legacyPayeeBankID);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", accountNameInLocalLanguage=");
		builder.append(accountNameInLocalLanguage);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", iban=");
		builder.append(iban);
		builder.append(", bankBIC=");
		builder.append(bankBIC);
		builder.append(", bankIntraCountryCode=");
		builder.append(bankIntraCountryCode);
		builder.append(", bankIntraCountryCodeType=");
		builder.append(bankIntraCountryCodeType);
		builder.append(", bankName=");
		builder.append(bankName);
		builder.append(", bankStreet=");
		builder.append(bankStreet);
		builder.append(", bankCity=");
		builder.append(bankCity);
		builder.append(", bankState=");
		builder.append(bankState);
		builder.append(", bankPostCode=");
		builder.append(bankPostCode);
		builder.append(", intermediaryBankBIC=");
		builder.append(intermediaryBankBIC);
		builder.append(", intermediaryBankIntraCountryCode=");
		builder.append(intermediaryBankIntraCountryCode);
		builder.append(", intermediaryBankIntraCountryCodeType=");
		builder.append(intermediaryBankIntraCountryCodeType);
		builder.append(", intermediaryBankName=");
		builder.append(intermediaryBankName);
		builder.append(", intermediaryBankStreet=");
		builder.append(intermediaryBankStreet);
		builder.append(", intermediaryBankCity=");
		builder.append(intermediaryBankCity);
		builder.append(", intermediaryBankState=");
		builder.append(intermediaryBankState);
		builder.append(", intermediaryBankPostCode=");
		builder.append(intermediaryBankPostCode);
		builder.append(", payeeBankCountryCode=");
		builder.append(payeeBankCountryCode);
		builder.append(", intermediaryBankCountryCode=");
		builder.append(intermediaryBankCountryCode);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", updatedOn=");
		builder.append(updatedOn);
		builder.append(", payeeCurrencyCode=");
		builder.append(payeeCurrencyCode);
		builder.append(", payeeBankCountryName=");
		builder.append(payeeBankCountryName);
		builder.append(", intermediaryBankCountryName=");
		builder.append(intermediaryBankCountryName);
		builder.append(']');
		return builder.toString();
	}

}
