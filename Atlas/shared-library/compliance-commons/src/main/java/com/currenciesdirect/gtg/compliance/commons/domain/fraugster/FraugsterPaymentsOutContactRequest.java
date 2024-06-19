/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FraugsterPaymentsOutContactRequest.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraugsterPaymentsOutContactRequest extends FraugsterPaymentsBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The transaction date time. */
	private String transactionDateTime;

	/** The value date. */
	private String valueDate;

	/** The beneficiary first name. */
	private String beneficiaryFirstName;

	/** The beneficiary last name. */
	private String beneficiaryLastName;

	/** The beneficiary account name. */
	private String beneficiaryAccountName;

	/** The beneficiary email. */
	private String beneficiaryEmail;

	/** The beneficiary phone. */
	private String beneficiaryPhone;

	/** The beneficiary country. */
	private String beneficiaryCountry;

	/** The beneficiary currency. */
	private String beneficiaryCurrency;

	/** The beneficiary account number. */
	private String beneficiaryAccountNumber;

	/** The beneficiary swift. */
	private String beneficiarySwift;

	/** The beneficiary bank name. */
	private String beneficiaryBankName;

	/** The beneficiary bank address. */
	private String beneficiaryBankAddress;

	/** The customer type. */
	private String customerType;

	/** The intermediary institution. */
	private String intermediaryInstitution;

	/** The sender correspondent. */
	private String senderCorrespondent;

	/** The account with institution. */
	private String accountWithInstitution;

	/** The remittance information. */
	private String remittanceInformation;

	/** The ordering institution. */
	private String orderingInstitution;

	/** The creditor agent BIC code or sort code. */
	private String creditorAgentBICCodeOrSortCode;

	/** The payment country. */
	private String paymentCountry;

	/** The o PI created date. */
	private String oPICreatedDate;

	/** The o PI country. */
	private String oPICountry;

	/** The deal id. */
	private String dealId;
	
	/** The buying Amount */
	private String buyingAmount;
	
	/** The customer No */
	private String customerNo;
	
	/** purpose Of Trade */
	private String purposeOfTrade;
	 
	/** The beneficiary Currency Code */
	private String beneficiaryCurrencyCode;
	
	/** The beneficiary Type */
	private String beneficiaryType;
	
	/** The beneficiary Type */
	private String contracrNumber;
	
	/** The selling Currency */
	private String sellingCurrency;
	
	/** The beneficiary country code. */
	private String beneficiaryCountryCode;
	
	/**
	 * @return selling Currency
	 */
	public String getSellingCurrency() {
		return sellingCurrency;
	}

	/**
	 * @param sellingCurrency
	 */
	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	/**
	 * @return contracr Number
	 */
	public String getContracrNumber() {
		return contracrNumber;
	}

	/**
	 * @param contracrNumber
	 */
	public void setContracrNumber(String contracrNumber) {
		this.contracrNumber = contracrNumber;
	}

	/**
	 * @return buyingAmount
	 */
	public String getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * @param buyingAmount
	 */
	public void setBuyingAmount(String buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * @return customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param customerNo
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @return purposeOfTrade
	 */
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * @param purposeOfTrade
	 */
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * @return beneficiaryCurrencyCode
	 */
	public String getBeneficiaryCurrencyCode() {
		return beneficiaryCurrencyCode;
	}

	/**
	 * @param beneficiaryCurrencyCode
	 */
	public void setBeneficiaryCurrencyCode(String beneficiaryCurrencyCode) {
		this.beneficiaryCurrencyCode = beneficiaryCurrencyCode;
	}

	/**
	 * @return beneficiaryType
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * @param beneficiaryType
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * Gets the beneficiary first name.
	 *
	 * @return the beneficiary first name
	 */
	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}

	/**
	 * Gets the beneficiary last name.
	 *
	 * @return the beneficiary last name
	 */
	public String getBeneficiaryLastName() {
		return beneficiaryLastName;
	}

	/**
	 * Gets the beneficiary account name.
	 *
	 * @return the beneficiary account name
	 */
	public String getBeneficiaryAccountName() {
		return beneficiaryAccountName;
	}

	/**
	 * Gets the beneficiary email.
	 *
	 * @return the beneficiary email
	 */
	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}

	/**
	 * Gets the beneficiary phone.
	 *
	 * @return the beneficiary phone
	 */
	public String getBeneficiaryPhone() {
		return beneficiaryPhone;
	}

	/**
	 * Gets the beneficiary country.
	 *
	 * @return the beneficiary country
	 */
	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	/**
	 * Gets the beneficiary currency.
	 *
	 * @return the beneficiary currency
	 */
	public String getBeneficiaryCurrency() {
		return beneficiaryCurrency;
	}

	/**
	 * Gets the beneficiary account number.
	 *
	 * @return the beneficiary account number
	 */
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	/**
	 * Gets the beneficiary swift.
	 *
	 * @return the beneficiary swift
	 */
	public String getBeneficiarySwift() {
		return beneficiarySwift;
	}

	/**
	 * Gets the beneficiary bank name.
	 *
	 * @return the beneficiary bank name
	 */
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	/**
	 * Gets the beneficiary bank address.
	 *
	 * @return the beneficiary bank address
	 */
	public String getBeneficiaryBankAddress() {
		return beneficiaryBankAddress;
	}

	/**
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Gets the intermediary institution.
	 *
	 * @return the intermediary institution
	 */
	public String getIntermediaryInstitution() {
		return intermediaryInstitution;
	}

	/**
	 * Gets the sender correspondent.
	 *
	 * @return the sender correspondent
	 */
	public String getSenderCorrespondent() {
		return senderCorrespondent;
	}

	/**
	 * Gets the account with institution.
	 *
	 * @return the account with institution
	 */
	public String getAccountWithInstitution() {
		return accountWithInstitution;
	}

	/**
	 * Gets the remittance information.
	 *
	 * @return the remittance information
	 */
	public String getRemittanceInformation() {
		return remittanceInformation;
	}

	/**
	 * Gets the ordering institution.
	 *
	 * @return the ordering institution
	 */
	public String getOrderingInstitution() {
		return orderingInstitution;
	}

	/**
	 * Gets the creditor agent BIC code or sort code.
	 *
	 * @return the creditor agent BIC code or sort code
	 */
	public String getCreditorAgentBICCodeOrSortCode() {
		return creditorAgentBICCodeOrSortCode;
	}

	/**
	 * Gets the payment country.
	 *
	 * @return the payment country
	 */
	public String getPaymentCountry() {
		return paymentCountry;
	}

	/**
	 * Gets the o PI country.
	 *
	 * @return the o PI country
	 */
	public String getoPICountry() {
		return oPICountry;
	}

	/**
	 * Sets the beneficiary first name.
	 *
	 * @param beneficiaryFirstName
	 *            the new beneficiary first name
	 */
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}

	/**
	 * Sets the beneficiary last name.
	 *
	 * @param beneficiaryLastName
	 *            the new beneficiary last name
	 */
	public void setBeneficiaryLastName(String beneficiaryLastName) {
		this.beneficiaryLastName = beneficiaryLastName;
	}

	/**
	 * Sets the beneficiary account name.
	 *
	 * @param beneficiaryAccountName
	 *            the new beneficiary account name
	 */
	public void setBeneficiaryAccountName(String beneficiaryAccountName) {
		this.beneficiaryAccountName = beneficiaryAccountName;
	}

	/**
	 * Sets the beneficiary email.
	 *
	 * @param beneficiaryEmail
	 *            the new beneficiary email
	 */
	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}

	/**
	 * Sets the beneficiary phone.
	 *
	 * @param beneficiaryPhone
	 *            the new beneficiary phone
	 */
	public void setBeneficiaryPhone(String beneficiaryPhone) {
		this.beneficiaryPhone = beneficiaryPhone;
	}

	/**
	 * Sets the beneficiary country.
	 *
	 * @param beneficiaryCountry
	 *            the new beneficiary country
	 */
	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	/**
	 * Sets the beneficiary currency.
	 *
	 * @param beneficiaryCurrency
	 *            the new beneficiary currency
	 */
	public void setBeneficiaryCurrency(String beneficiaryCurrency) {
		this.beneficiaryCurrency = beneficiaryCurrency;
	}

	/**
	 * Sets the beneficiary account number.
	 *
	 * @param beneficiaryAccountNumber
	 *            the new beneficiary account number
	 */
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	/**
	 * Sets the beneficiary swift.
	 *
	 * @param beneficiarySwift
	 *            the new beneficiary swift
	 */
	public void setBeneficiarySwift(String beneficiarySwift) {
		this.beneficiarySwift = beneficiarySwift;
	}

	/**
	 * Sets the beneficiary bank name.
	 *
	 * @param beneficiaryBankName
	 *            the new beneficiary bank name
	 */
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	/**
	 * Sets the beneficiary bank address.
	 *
	 * @param beneficiaryBankAddress
	 *            the new beneficiary bank address
	 */
	public void setBeneficiaryBankAddress(String beneficiaryBankAddress) {
		this.beneficiaryBankAddress = beneficiaryBankAddress;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType
	 *            the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Sets the intermediary institution.
	 *
	 * @param intermediaryInstitution
	 *            the new intermediary institution
	 */
	public void setIntermediaryInstitution(String intermediaryInstitution) {
		this.intermediaryInstitution = intermediaryInstitution;
	}

	/**
	 * Sets the sender correspondent.
	 *
	 * @param senderCorrespondent
	 *            the new sender correspondent
	 */
	public void setSenderCorrespondent(String senderCorrespondent) {
		this.senderCorrespondent = senderCorrespondent;
	}

	/**
	 * Sets the account with institution.
	 *
	 * @param accountWithInstitution
	 *            the new account with institution
	 */
	public void setAccountWithInstitution(String accountWithInstitution) {
		this.accountWithInstitution = accountWithInstitution;
	}

	/**
	 * Sets the remittance information.
	 *
	 * @param remittanceInformation
	 *            the new remittance information
	 */
	public void setRemittanceInformation(String remittanceInformation) {
		this.remittanceInformation = remittanceInformation;
	}

	/**
	 * Sets the ordering institution.
	 *
	 * @param orderingInstitution
	 *            the new ordering institution
	 */
	public void setOrderingInstitution(String orderingInstitution) {
		this.orderingInstitution = orderingInstitution;
	}

	/**
	 * Sets the creditor agent BIC code or sort code.
	 *
	 * @param creditorAgentBICCodeOrSortCode
	 *            the new creditor agent BIC code or sort code
	 */
	public void setCreditorAgentBICCodeOrSortCode(String creditorAgentBICCodeOrSortCode) {
		this.creditorAgentBICCodeOrSortCode = creditorAgentBICCodeOrSortCode;
	}

	/**
	 * Sets the payment country.
	 *
	 * @param paymentCountry
	 *            the new payment country
	 */
	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	/**
	 * Sets the o PI country.
	 *
	 * @param oPICountry
	 *            the new o PI country
	 */
	public void setoPICountry(String oPICountry) {
		this.oPICountry = oPICountry;
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
	 * Gets the value date.
	 *
	 * @return the value date
	 */
	public String getValueDate() {
		return valueDate;
	}

	/**
	 * Gets the o PI created date.
	 *
	 * @return the o PI created date
	 */
	public String getoPICreatedDate() {
		return oPICreatedDate;
	}

	/**
	 * Sets the transaction date time.
	 *
	 * @param transactionDateTime
	 *            the new transaction date time
	 */
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	/**
	 * Sets the value date.
	 *
	 * @param valueDate
	 *            the new value date
	 */
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * Sets the o PI created date.
	 *
	 * @param oPICreatedDate
	 *            the new o PI created date
	 */
	public void setoPICreatedDate(String oPICreatedDate) {
		this.oPICreatedDate = oPICreatedDate;
	}

	/**
	 * Gets the deal id.
	 *
	 * @return the deal id
	 */
	public String getDealId() {
		return dealId;
	}

	/**
	 * Sets the deal id.
	 *
	 * @param dealId
	 *            the new deal id
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/**
	 * @return the beneficiaryCountryCode
	 */
	public String getBeneficiaryCountryCode() {
		return beneficiaryCountryCode;
	}

	/**
	 * @param beneficiaryCountryCode the beneficiaryCountryCode to set
	 */
	public void setBeneficiaryCountryCode(String beneficiaryCountryCode) {
		this.beneficiaryCountryCode = beneficiaryCountryCode;
	}


}
