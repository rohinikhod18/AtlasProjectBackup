/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FraugsterPaymentsInProviderRequest.
 *
 * @author manish
 */
public class FraugsterPaymentsInProviderRequest extends FraugsterPaymentsProviderBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pmt method. */
	@JsonProperty("pmt_method")
	private String pmtMethod;
	
	/** The cc first name. */
	@JsonProperty("cc_first_name")
	private String ccFirstName;
	
	/** The bill ad line 1. */
	@JsonProperty("bill_ad_line1")
	private String billAdLine1;
	
	/** The avs result. */
	@JsonProperty("avs_result")
	private String avsResult;
	
	/** The is threeds. */
	@JsonProperty("is_threeds")
	private String isThreeds;
	
	/** The debit card added date. */
	@JsonProperty("debit_card_added_date")
	private String debitCardAddedDate;
	
	/** The av trade value. */
	@JsonProperty("av_trade_value")
	private String avTradeValue;
	
	/** The av trade frequency. */
	@JsonProperty("av_trade_frequency")
	private String avTradeFrequency;
	
	/** The third party payment. */
	@JsonProperty("third_party_payment")
	private String thirdPartyPayment;
	
	/** The cheque clearance date. */
	@JsonProperty("cheque_clearance_date")
	private String chequeClearanceDate;
	
	/** The account identification. */
	@JsonProperty("account_identification")
	private String accountIdentification;
	
	/** The trans ts. */
	@JsonProperty("trans_ts")
	private String transTs;
	
	/** The bill ad zip. */
	@JsonProperty("bill_ad_zip")
	private String billAdZip;
	
	/** The transaction reference. */
	@JsonProperty("transaction_reference")
	private String transactionReference;

	/**
	 * Gets the pmt method.
	 *
	 * @return the pmt method
	 */
	public String getPmtMethod() {
		return pmtMethod;
	}

	/**
	 * Sets the pmt method.
	 *
	 * @param pmtMethod the new pmt method
	 */
	public void setPmtMethod(String pmtMethod) {
		this.pmtMethod = pmtMethod;
	}

	/**
	 * Gets the cc first name.
	 *
	 * @return the cc first name
	 */
	public String getCcFirstName() {
		return ccFirstName;
	}

	/**
	 * Sets the cc first name.
	 *
	 * @param ccFirstName the new cc first name
	 */
	public void setCcFirstName(String ccFirstName) {
		this.ccFirstName = ccFirstName;
	}

	/**
	 * Gets the bill ad line 1.
	 *
	 * @return the bill ad line 1
	 */
	public String getBillAdLine1() {
		return billAdLine1;
	}

	/**
	 * Sets the bill ad line 1.
	 *
	 * @param billAdLine1 the new bill ad line 1
	 */
	public void setBillAdLine1(String billAdLine1) {
		this.billAdLine1 = billAdLine1;
	}

	/**
	 * Gets the avs result.
	 *
	 * @return the avs result
	 */
	public String getAvsResult() {
		return avsResult;
	}

	/**
	 * Sets the avs result.
	 *
	 * @param avsResult the new avs result
	 */
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	/**
	 * Gets the checks if is threeds.
	 *
	 * @return the checks if is threeds
	 */
	public String getIsThreeds() {
		return isThreeds;
	}

	/**
	 * Sets the checks if is threeds.
	 *
	 * @param isThreeds the new checks if is threeds
	 */
	public void setIsThreeds(String isThreeds) {
		this.isThreeds = isThreeds;
	}

	/**
	 * Gets the av trade value.
	 *
	 * @return the av trade value
	 */
	public String getAvTradeValue() {
		return avTradeValue;
	}

	/**
	 * Sets the av trade value.
	 *
	 * @param avTradeValue the new av trade value
	 */
	public void setAvTradeValue(String avTradeValue) {
		this.avTradeValue = avTradeValue;
	}

	/**
	 * Gets the av trade frequency.
	 *
	 * @return the av trade frequency
	 */
	public String getAvTradeFrequency() {
		return avTradeFrequency;
	}

	/**
	 * Sets the av trade frequency.
	 *
	 * @param avTradeFrequency the new av trade frequency
	 */
	public void setAvTradeFrequency(String avTradeFrequency) {
		this.avTradeFrequency = avTradeFrequency;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return the third party payment
	 */
	public String getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment the new third party payment
	 */
	public void setThirdPartyPayment(String thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the cheque clearance date.
	 *
	 * @return the cheque clearance date
	 */
	public String getChequeClearanceDate() {
		return chequeClearanceDate;
	}

	/**
	 * Sets the cheque clearance date.
	 *
	 * @param chequeClearanceDate the new cheque clearance date
	 */
	public void setChequeClearanceDate(String chequeClearanceDate) {
		this.chequeClearanceDate = chequeClearanceDate;
	}

	/**
	 * Gets the account identification.
	 *
	 * @return the account identification
	 */
	public String getAccountIdentification() {
		return accountIdentification;
	}

	/**
	 * Sets the account identification.
	 *
	 * @param accountIdentification the new account identification
	 */
	public void setAccountIdentification(String accountIdentification) {
		this.accountIdentification = accountIdentification;
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
	 * Sets the trans ts.
	 *
	 * @param transTs the new trans ts
	 */
	public void setTransTs(String transTs) {
		this.transTs = transTs;
	}

	/**
	 * Gets the bill ad zip.
	 *
	 * @return the bill ad zip
	 */
	public String getBillAdZip() {
		return billAdZip;
	}

	/**
	 * Sets the bill ad zip.
	 *
	 * @param billAdZip the new bill ad zip
	 */
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	/**
	 * Gets the debit card added date.
	 *
	 * @return the debit card added date
	 */
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	/**
	 * Sets the debit card added date.
	 *
	 * @param debitCardAddedDate the new debit card added date
	 */
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	/**
	 * Gets the transaction reference.
	 *
	 * @return the transaction reference
	 */
	public String getTransactionReference() {
		return transactionReference;
	}

	/**
	 * Sets the transaction reference.
	 *
	 * @param transactionReference the new transaction reference
	 */
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
}
