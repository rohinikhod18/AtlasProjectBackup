package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;


import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bnt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"payment_fundsout_id",
	"phone",
	"first_name",
	"last_name",
	"email",
	"country",
	"account_number",
	"currency_code",
	"amount",
	"beneficiary_id",
	"beneficiary_bankid",
	"beneficary_bank_name",
	"beneficary_bank_address",
	"trans_ts",
	"beneficiary_swift",
	"payment_reference",
	"opi_created_date",
	"beneficiary_type"
	
})
public class Beneficiary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("country")
	private String country;
	@JsonProperty("account_number")
	private String accountNumber;
	@JsonProperty("currency_code")
	private String currencyCode;
	@JsonProperty("amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double amount;
	@JsonProperty("beneficiary_id")
	private Integer beneficiaryId;
	@JsonProperty("beneficiary_bankid")
	private Integer beneficiaryBankid;
	@JsonProperty("beneficary_bank_name")
	private String beneficaryBankName;
	@JsonProperty("beneficary_bank_address")
	private String beneficaryBankAddress;
	@JsonProperty("beneficiary_swift")
	private String beneficiarySwift;
	@JsonProperty("payment_reference")
	private String paymentReference;
	@JsonProperty("opi_created_date")
	private String opiCreatedDate;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("trans_ts")
	private String transactionDateTime;

	/**
	 * Added new field
	 */
	@JsonProperty("beneficiary_type")
	private String beneficiaryType;
	
	/** The added by. */
	@JsonProperty("added_by")
	private String addedBy;
	
	/** The opi status. */
	@JsonProperty("opi_status")
	private String opiStatus;
	

	/**
	 * 
	 * @return The paymentFundsoutId
	 */
	@JsonProperty("payment_fundsout_id")
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/**
	 * 
	 * @param paymentFundsoutId
	 *            The payment_fundsout_id
	 */
	@JsonProperty("payment_fundsout_id")
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}

	/**
	 * 
	 * @return The firstName
	 */
	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 *            The first_name
	 */
	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return The lastName
	 */
	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 *            The last_name
	 */
	@JsonProperty("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return The email
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 *            The email
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return The country
	 */
	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @param country
	 *            The country
	 */
	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return The accountNumber
	 */
	@JsonProperty("account_number")
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * 
	 * @param accountNumber
	 *            The account_number
	 */
	@JsonProperty("account_number")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * 
	 * @return The currencyCode
	 */
	@JsonProperty("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 
	 * @param currencyCode
	 *            The currency_code
	 */
	@JsonProperty("currency_code")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * @return The amount
	 */
	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	/**
	 * 
	 * @param amount
	 *            The amount
	 */
	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}




	/**
	 * 
	 * @return The beneficiaryBankid
	 */
	@JsonProperty("beneficiary_bankid")
	public Integer getBeneficiaryBankid() {
		return beneficiaryBankid;
	}

	/**
	 * 
	 * @param beneficiaryBankid
	 *            The beneficiary_bankid
	 */
	@JsonProperty("beneficiary_bankid")
	public void setBeneficiaryBankid(Integer beneficiaryBankid) {
		this.beneficiaryBankid = beneficiaryBankid;
	}

	/**
	 * 
	 * @return The beneficaryBankName
	 */
	@JsonProperty("beneficary_bank_name")
	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}

	/**
	 * 
	 * @param beneficaryBankName
	 *            The beneficary_bank_name
	 */
	@JsonProperty("beneficary_bank_name")
	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
	}

	/**
	 * 
	 * @return The beneficaryBankAddress
	 */
	@JsonProperty("beneficary_bank_address")
	public String getBeneficaryBankAddress() {
		return beneficaryBankAddress;
	}

	/**
	 * 
	 * @param beneficaryBankAddress
	 *            The beneficary_bank_address
	 */
	@JsonProperty("beneficary_bank_address")
	public void setBeneficaryBankAddress(String beneficaryBankAddress) {
		this.beneficaryBankAddress = beneficaryBankAddress;
	}

	/**
	 * 
	 * @return The beneficiarySwift
	 */
	@JsonProperty("beneficiary_swift")
	public String getBeneficiarySwift() {
		return beneficiarySwift;
	}

	/**
	 * 
	 * @param beneficiarySwift
	 *            The beneficiary_swift
	 */
	@JsonProperty("beneficiary_swift")
	public void setBeneficiarySwift(String beneficiarySwift) {
		this.beneficiarySwift = beneficiarySwift;
	}

	/**
	 * 
	 * @return The paymentReference
	 */
	@JsonProperty("payment_reference")
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * 
	 * @param paymentReference
	 *            The payment_reference
	 */
	@JsonProperty("payment_reference")
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * 
	 * @return The opiCreatedDate
	 */
	@JsonProperty("opi_created_date")
	public String getOpiCreatedDate() {
		return opiCreatedDate;
	}

	/**
	 * 
	 * @param opiCreatedDate
	 *            The opi_created_date
	 */
	@JsonProperty("opi_created_date")
	public void setOpiCreatedDate(String opiCreatedDate) {
		this.opiCreatedDate = opiCreatedDate;
	}

	
	@JsonIgnore
	public String getFullName(){
		StringBuilder name = new StringBuilder();
		if (!isNullOrEmpty(getFirstName())) {
			name.append(getFirstName());
		}
		if(!isNullOrEmpty(getLastName())) {
			if(name.length()>0)
				name.append(' ').append(getLastName()) ;
			else
				name.append(getLastName()) ;
		}
		return name.toString();
	}
	
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;
		
		return result;
	}

	public Integer getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Integer beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getOpiStatus() {
		return opiStatus;
	}

	public void setOpiStatus(String opiStatus) {
		this.opiStatus = opiStatus;
	}



}