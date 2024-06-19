package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransaction implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The customer instruction number id. */
	@JsonProperty(value = "customer_instruction")
	private Long customerInstructionId;

	@JsonProperty(value = "transaction_date")
	private Timestamp transactionDate;

	/** The amount. */
	@JsonProperty(value = "amount")
	private BigDecimal amount;

	/** The current total balance. */
	@JsonProperty(value = "current_total_balance")
	private BigDecimal currentTotalBalance;

	/** The entry type. */
	@JsonProperty(value = "entry_type")
	private String entryType;

	/** The reason. */
	@JsonProperty(value = "reason")
	private String reason;

	/** The reference. */
	@JsonProperty(value = "reference")
	private String reference;

	/** The status code. */
	@JsonProperty(value = "status")
	private String statusCode;

	/** The wallet Amount. */
	private String walletAmount;

	/** The total Balance. */
	private String totalBalance;

	/** The transDate. */
	private String transDate;

	/**
	 * @return transDate
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return walletAmount
	 */
	public String getWalletAmount() {
		return walletAmount;
	}

	/**
	 * @param walletAmount
	 */
	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}

	/**
	 * @return totalBalance
	 */
	public String getTotalBalance() {
		return totalBalance;
	}

	/**
	 * @param totalBalance
	 */
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	/**
	 * @return the customerInstructionId
	 */
	public Long getCustomerInstructionId() {
		return customerInstructionId;
	}

	/**
	 * @param customerInstructionId
	 *            the customerInstructionId to set
	 */
	public void setCustomerInstructionId(Long customerInstructionId) {
		this.customerInstructionId = customerInstructionId;
	}

	/**
	 * @return the transactionDate
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}

	/**
	 * @param entryType
	 *            the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the currentTotalBalance
	 */
	public BigDecimal getCurrentTotalBalance() {
		return currentTotalBalance;
	}

	/**
	 * @param currentTotalBalance
	 *            the currentTotalBalance to set
	 */
	public void setCurrentTotalBalance(BigDecimal currentTotalBalance) {
		this.currentTotalBalance = currentTotalBalance;
	}
}
