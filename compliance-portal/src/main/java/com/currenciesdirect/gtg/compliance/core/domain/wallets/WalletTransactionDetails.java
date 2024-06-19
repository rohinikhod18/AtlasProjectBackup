package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WalletTransactionDetails.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionDetails {
	  
	@JsonProperty(value = "customer_instruction")
	private float customerInstruction;
	
	@JsonProperty(value = "transaction_date")
	private String transactionDate;
	
	@JsonProperty(value = "amount")
	private float amount;
	
	@JsonProperty(value = "current_total_balance")
	private float currentTotalBalance;
	
	@JsonProperty(value = "entry_type")
	private String entryType;
	
	@JsonProperty(value = "reference")
	private String reference;
	
	@JsonProperty(value = "reason")
	private String reason;
	
	@JsonProperty(value = "status")
	private String status;

	/**
	 * @return the customerInstruction
	 */
	public float getCustomerInstruction() {
		return customerInstruction;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @return the currentTotalBalance
	 */
	public float getCurrentTotalBalance() {
		return currentTotalBalance;
	}

	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param customerInstruction the customerInstruction to set
	 */
	public void setCustomerInstruction(float customerInstruction) {
		this.customerInstruction = customerInstruction;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @param currentTotalBalance the currentTotalBalance to set
	 */
	public void setCurrentTotalBalance(float currentTotalBalance) {
		this.currentTotalBalance = currentTotalBalance;
	}

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
