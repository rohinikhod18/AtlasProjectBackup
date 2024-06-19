/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterPaymentsOutProviderRequest.
 *
 * @author manish
 */
public class FraugsterPaymentsOutProviderRequest extends FraugsterPaymentsProviderBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trans ID. */
	@JsonProperty("trans_id")
	private String transID;

	/** The event type. */
	@JsonProperty("event_type")
	private String eventType;

	/** The trans ts. */
	@JsonProperty("trans_ts")
	private String transTs;

	/** The value date. */
	@JsonProperty("value_date")
	private String valueDate;

	/** The currency instructed amount. */
	@JsonProperty("currency_instructed_amount")
	private String currencyInstructedAmount;

	/** The sender to receiver info. */
	@JsonProperty("sender_to_receiver_info")
	private String senderToReceiverInfo;

	/** The beneficiary first name. */
	@JsonProperty("beneficiary_first_name")
	private String beneficiaryFirstName;

	/** The beneficiary last name. */
	@JsonProperty("beneficiary_last_name")
	private String beneficiaryLastName;

	/** The beneficiary account name. */
	@JsonProperty("beneficiary_account_name")
	private String beneficiaryAccountName;

	/** The beneficiary email. */
	@JsonProperty("beneficiary_email")
	private String beneficiaryEmail;

	/** The beneficiary phone. */
	@JsonProperty("beneficiary_phone")
	private String beneficiaryPhone;

	/** The beneficiary country. */
	@JsonProperty("beneficiary_country")
	private String beneficiaryCountry;

	/** The beneficiary currency. */
	@JsonProperty("beneficiary_currency")
	private String beneficiaryCurrency;

	/** The beneficiary account number. */
	@JsonProperty("beneficiary_account_number")
	private String beneficiaryAccountNumber;

	/** The beneficiary swift. */
	@JsonProperty("beneficiary_swift")
	private String beneficiarySwift;

	/** The beneficiary bank name. */
	@JsonProperty("beneficiary_bank_name")
	private String beneficiaryBankName;

	/** The beneficiary bank address. */
	@JsonProperty("beneficiary_bank_address")
	private String beneficiaryBankAddress;

	/** The customer type. */
	@JsonProperty("customer_type")
	private String customerType;

	/** The intermediary institution. */
	@JsonProperty("intermediary_institution")
	private String intermediaryInstitution;

	/** The sender correspondent. */
	@JsonProperty("sender_correspondent")
	private String senderCorrespondent;

	/** The account with institution. */
	@JsonProperty("account_with_institution")
	private String accountWithInstitution;

	/** The remittance info. */
	@JsonProperty("remittance_info")
	private String remittanceInfo;

	/** The ordering institution. */
	@JsonProperty("ordering_institution")
	private String orderingInstitution;

	/** The ba bic. */
	@JsonProperty("ba_bic")
	private String baBic;

	/** The payment country. */
	@JsonProperty("payment_country")
	private String paymentCountry;

	/** The opi created date. */
	@JsonProperty("opi_created_date")
	private String opiCreatedDate;

	/** The opi amendment date. */
	@JsonProperty("opi_amendment_date")
	private String opiAmendmentDate;

	/** The opi updated by. */
	@JsonProperty("opi_updated_by")
	private String opiUpdatedBy;

	/** The opi country. */
	@JsonProperty("opi_country")
	private String opiCountry;

	/** The deal id. */
	@JsonProperty("deal_id")
	private String dealId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderBaseRequest#getTrans_id()
	 */
	@Override
	public String getTransId() {
		return transID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderBaseRequest#getEvent_type()
	 */
	@Override
	public String getEventType() {
		return eventType;
	}

	/**
	 * Gets the trans ts.
	 *
	 * @return the trans ts
	 */
	public String getTransTs() {
		return transTs;
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
	 * Gets the sender to receiver info.
	 *
	 * @return the sender to receiver info
	 */
	public String getSenderToReceiverInfo() {
		return senderToReceiverInfo;
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
	 * Gets the remittance info.
	 *
	 * @return the remittance info
	 */
	public String getRemittanceInfo() {
		return remittanceInfo;
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
	 * Gets the ba bic.
	 *
	 * @return the ba bic
	 */
	public String getBaBic() {
		return baBic;
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
	 * Gets the opi created date.
	 *
	 * @return the opi created date
	 */
	public String getOpiCreatedDate() {
		return opiCreatedDate;
	}

	/**
	 * Gets the opi amendment date.
	 *
	 * @return the opi amendment date
	 */
	public String getOpiAmendmentDate() {
		return opiAmendmentDate;
	}

	/**
	 * Gets the opi updated by.
	 *
	 * @return the opi updated by
	 */
	public String getOpiUpdatedBy() {
		return opiUpdatedBy;
	}

	/**
	 * Gets the opi country.
	 *
	 * @return the opi country
	 */
	public String getOpiCountry() {
		return opiCountry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderBaseRequest#setTrans_id(java.lang.String)
	 */
	@Override
	public void setTransId(String transID) {
		this.transID = transID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterProviderBaseRequest#setEvent_type(java.lang.String)
	 */
	@Override
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Sets the trans ts.
	 *
	 * @param transTs
	 *            the new trans ts
	 */
	public void setTransTs(String transTs) {
		this.transTs = transTs;
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
	 * Sets the currency instructed amount.
	 *
	 * @param currencyInstructedAmount
	 *            the new currency instructed amount
	 */
	public void setCurrencyInstructedAmount(String currencyInstructedAmount) {
		this.currencyInstructedAmount = currencyInstructedAmount;
	}

	/**
	 * Sets the sender to receiver info.
	 *
	 * @param senderToReceiverInfo
	 *            the new sender to receiver info
	 */
	public void setSenderToReceiverInfo(String senderToReceiverInfo) {
		this.senderToReceiverInfo = senderToReceiverInfo;
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
	 * @param beneficiaryBankAddress
	 *            the new beneficiary bank name
	 */
	public void setBeneficiaryBankName(String beneficiaryBankAddress) {
		this.beneficiaryBankName = beneficiaryBankAddress;
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
	 * Sets the remittance info.
	 *
	 * @param remittanceInfo
	 *            the new remittance info
	 */
	public void setRemittanceInfo(String remittanceInfo) {
		this.remittanceInfo = remittanceInfo;
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
	 * Sets the ba bic.
	 *
	 * @param baBic
	 *            the new ba bic
	 */
	public void setBaBic(String baBic) {
		this.baBic = baBic;
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
	 * Sets the opi created date.
	 *
	 * @param opiCreatedDate
	 *            the new opi created date
	 */
	public void setOpiCreatedDate(String opiCreatedDate) {
		this.opiCreatedDate = opiCreatedDate;
	}

	/**
	 * Sets the opi amendment date.
	 *
	 * @param opiAmendmentDate
	 *            the new opi amendment date
	 */
	public void setOpiAmendmentDate(String opiAmendmentDate) {
		this.opiAmendmentDate = opiAmendmentDate;
	}

	/**
	 * Sets the opi updated by.
	 *
	 * @param opiUpdatedBy
	 *            the new opi updated by
	 */
	public void setOpiUpdatedBy(String opiUpdatedBy) {
		this.opiUpdatedBy = opiUpdatedBy;
	}

	/**
	 * Sets the opi country.
	 *
	 * @param opiCountry
	 *            the new opi country
	 */
	public void setOpiCountry(String opiCountry) {
		this.opiCountry = opiCountry;
	}

	/**
	 * Gets the currency instructed amount.
	 *
	 * @return the currency instructed amount
	 */
	public String getCurrencyInstructedAmount() {
		return currencyInstructedAmount;
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

}
