package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Beneficiary.
 *
 * @author bnt
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "phone", "first_name", "last_name", "email", "country", "account_number", "currency_code",
		"beneficiary_id", "beneficiary_bankid", "beneficary_bank_name", "beneficary_bank_address", "trans_ts",
		"beneficiary_swift", "payment_reference", "opi_created_date", "beneficiary_type", "opi_updated_by", "opi_email",
		"opi_contact_no", "opi_type"

})
public class Beneficiary extends BaseBeneficiary implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The first name. */
	@ApiModelProperty(value = "beneficiary first name", example = "Hank", required = true)
	@JsonProperty("first_name")
	private String firstName;

	/** The last name. */
	@ApiModelProperty(value = "beneficiary last name", example = "Dicosta", required = true)
	@JsonProperty("last_name")
	private String lastName;

	/** The email. */
	@ApiModelProperty(value = "beneficiary email", example = "Dicosta.hank@world.com", required = true)
	@JsonProperty("email")
	private String email;

	/** The country. */
	@ApiModelProperty(value = "beneficiary country", required = true)
	@JsonProperty("country")
	private String country;

	/** The account number. */
	@ApiModelProperty(value = "beneficiary account number", required = true)
	@JsonProperty("account_number")
	private String accountNumber;

	/** The currency code. */
	@ApiModelProperty(value = "beneficiary currency code", required = true)
	@JsonProperty("currency_code")
	private String currencyCode;

	/** The beneficiary id. */
	@ApiModelProperty(value = "beneficiary identification number trade related", required = true)
	@JsonProperty("beneficiary_id")
	private Integer beneficiaryId;

	/** The beneficiary bankid. */
	@ApiModelProperty(value = "beneficiary bank id", required = true)
	@JsonProperty("beneficiary_bankid")
	private Integer beneficiaryBankid;

	/** The beneficary bank name. */
	@ApiModelProperty(value = "beneficiary bank name", required = true)
	@JsonProperty("beneficary_bank_name")
	private String beneficaryBankName;

	/** The beneficary bank address. */
	@ApiModelProperty(value = "beneficiary bank address", required = true)
	@JsonProperty("beneficary_bank_address")
	private String beneficaryBankAddress;

	/** The beneficiary swift. */
	@ApiModelProperty(value = "beneficiary bank swift code", required = true)
	@JsonProperty("beneficiary_swift")
	private String beneficiarySwift;

	/** The payment reference. */
	@ApiModelProperty(value = "beneficiary payment reference", required = true)
	@JsonProperty("payment_reference")
	private String paymentReference;

	/** The opi created date. */
	@ApiModelProperty(value = "funds out created date", example = "2017-12-21", required = true)
	@JsonProperty("opi_created_date")
	private String opiCreatedDate;

	/** The phone. */
	@ApiModelProperty(value = "phone number", required = true)
	@JsonProperty("phone")
	private String phone;

	/** The transaction date time. */
	@ApiModelProperty(value = "transaction date time", example = "2019-03-01T15:15:39Z", required = true)
	@JsonProperty("trans_ts")
	private String transactionDateTime;

	/** Added new field. */
	@ApiModelProperty(value = "beneficiary type", required = true)
	@JsonProperty("beneficiary_type")
	private String beneficiaryType;

	/** The opi updated by. */
	@ApiModelProperty(value = "funds out last updated by", required = true)
	@JsonProperty(value = "opi_updated_by")
	private String opiUpdatedBy;

	/** The added by. */
	@JsonProperty("added_by")
	private String addedBy;

	/** The opi status. */
	@JsonProperty("opi_status")
	private String opiStatus;

	/**
	 * Gets the opi updated by.
	 *
	 * @return the opi updated by
	 */
	public String getOpiUpdatedBy() {
		return opiUpdatedBy;
	}

	/**
	 * Sets the opi updated by.
	 *
	 * @param opiUpdatedBy the new opi updated by
	 */
	public void setOpiUpdatedBy(String opiUpdatedBy) {
		this.opiUpdatedBy = opiUpdatedBy;
	}

	/** The opi email. */
	@ApiModelProperty(value = "beneficiary funds out email", required = true)
	@JsonProperty(value = "opi_email")
	private String opiEmail;

	/**
	 * Gets the opi email.
	 *
	 * @return the opi email
	 */
	public String getOpiEmail() {
		return opiEmail;
	}

	/**
	 * Sets the opi email.
	 *
	 * @param opiEmail the new opi email
	 */
	public void setOpiEmail(String opiEmail) {
		this.opiEmail = opiEmail;
	}

	/** The opi contact no. */
	@ApiModelProperty(value = "beneficiary contact number", required = true)
	@JsonProperty(value = "opi_contact_no")
	private String opiContactNo;

	/**
	 * Gets the opi contact no.
	 *
	 * @return the opi contact no
	 */
	public String getOpiContactNo() {
		return opiContactNo;
	}

	/**
	 * Sets the opi contact no.
	 *
	 * @param opiContactNo the new opi contact no
	 */
	public void setOpiContactNo(String opiContactNo) {
		this.opiContactNo = opiContactNo;
	}

	/** The opi type. */
	@ApiModelProperty(value = "beneficiary funds out type", required = true)
	@JsonProperty(value = "opi_type")
	private String opiType;

	/**
	 * Gets the opi type.
	 *
	 * @return the opi type
	 */
	public String getOpiType() {
		return opiType;
	}

	/**
	 * Sets the opi type.
	 *
	 * @param opiType the new opi type
	 */
	public void setOpiType(String opiType) {
		this.opiType = opiType;
	}

	/**
	 * Gets the first name.
	 *
	 * @return The firstName
	 */
	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName The first_name
	 */
	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return The lastName
	 */
	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName The last_name
	 */
	@JsonProperty("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return The email
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email The email
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the country.
	 *
	 * @return The country
	 */
	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country The country
	 */
	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the account number.
	 *
	 * @return The accountNumber
	 */
	@JsonProperty("account_number")
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber The account_number
	 */
	@JsonProperty("account_number")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return The currencyCode
	 */
	@JsonProperty("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode The currency_code
	 */
	@JsonProperty("currency_code")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the beneficiary bankid.
	 *
	 * @return The beneficiaryBankid
	 */
	@JsonProperty("beneficiary_bankid")
	public Integer getBeneficiaryBankid() {
		return beneficiaryBankid;
	}

	/**
	 * Sets the beneficiary bankid.
	 *
	 * @param beneficiaryBankid The beneficiary_bankid
	 */
	@JsonProperty("beneficiary_bankid")
	public void setBeneficiaryBankid(Integer beneficiaryBankid) {
		this.beneficiaryBankid = beneficiaryBankid;
	}

	/**
	 * Gets the beneficary bank name.
	 *
	 * @return The beneficaryBankName
	 */
	@JsonProperty("beneficary_bank_name")
	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}

	/**
	 * Sets the beneficary bank name.
	 *
	 * @param beneficaryBankName The beneficary_bank_name
	 */
	@JsonProperty("beneficary_bank_name")
	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
	}

	/**
	 * Gets the beneficary bank address.
	 *
	 * @return The beneficaryBankAddress
	 */
	@JsonProperty("beneficary_bank_address")
	public String getBeneficaryBankAddress() {
		return beneficaryBankAddress;
	}

	/**
	 * Sets the beneficary bank address.
	 *
	 * @param beneficaryBankAddress The beneficary_bank_address
	 */
	@JsonProperty("beneficary_bank_address")
	public void setBeneficaryBankAddress(String beneficaryBankAddress) {
		this.beneficaryBankAddress = beneficaryBankAddress;
	}

	/**
	 * Gets the beneficiary swift.
	 *
	 * @return The beneficiarySwift
	 */
	@JsonProperty("beneficiary_swift")
	public String getBeneficiarySwift() {
		return beneficiarySwift;
	}

	/**
	 * Sets the beneficiary swift.
	 *
	 * @param beneficiarySwift The beneficiary_swift
	 */
	@JsonProperty("beneficiary_swift")
	public void setBeneficiarySwift(String beneficiarySwift) {
		this.beneficiarySwift = beneficiarySwift;
	}

	/**
	 * Gets the payment reference.
	 *
	 * @return The paymentReference
	 */
	@JsonProperty("payment_reference")
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference The payment_reference
	 */
	@JsonProperty("payment_reference")
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the opi created date.
	 *
	 * @return The opiCreatedDate
	 */
	@JsonProperty("opi_created_date")
	public String getOpiCreatedDate() {
		return opiCreatedDate;
	}

	/**
	 * Sets the opi created date.
	 *
	 * @param opiCreatedDate The opi_created_date
	 */
	@JsonProperty("opi_created_date")
	public void setOpiCreatedDate(String opiCreatedDate) {
		this.opiCreatedDate = opiCreatedDate;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	@JsonIgnore
	public String getFullName() {
		StringBuilder name = new StringBuilder();
		if (!isNullOrEmpty(getFirstName()))
			name.append(getFirstName());
		if (!isNullOrEmpty(getLastName())) {
			if (name.length() > 0) {
				name.append(' ').append(getLastName());
			} else {
				name.append(getLastName());
			}
		}
		return name.toString();
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str the str
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}

	/**
	 * Gets the beneficiary id.
	 *
	 * @return the beneficiary id
	 */
	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	/**
	 * Sets the beneficiary id.
	 *
	 * @param beneficiaryId the new beneficiary id
	 */
	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
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
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the transaction date time.
	 *
	 * @return the transaction date time
	 */
	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	/**
	 * Sets the transaction date time.
	 *
	 * @param transactionDateTime the new transaction date time
	 */
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	/**
	 * Gets the beneficiary type.
	 *
	 * @return the beneficiary type
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * Sets the beneficiary type.
	 *
	 * @param beneficiaryType the new beneficiary type
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * Gets the added by.
	 *
	 * @return the added by
	 */
	public String getAddedBy() {
		return addedBy;
	}

	/**
	 * Sets the added by.
	 *
	 * @param addedBy the new added by
	 */
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	/**
	 * Gets the opi status.
	 *
	 * @return the opi status
	 */
	public String getOpiStatus() {
		return opiStatus;
	}

	/**
	 * Sets the opi status.
	 *
	 * @param opiStatus the new opi status
	 */
	public void setOpiStatus(String opiStatus) {
		this.opiStatus = opiStatus;
	}

}