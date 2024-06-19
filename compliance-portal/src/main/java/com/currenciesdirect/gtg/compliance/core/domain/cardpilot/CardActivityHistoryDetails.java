package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardActivityHistoryDetails.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardActivityHistoryDetails implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cd payment lifecycle id. */
	@JsonProperty("cdPaymentLifecycleId")
	private String cdPaymentLifecycleId;
	
	/** The instruction number. */
	@JsonProperty("instruction_number")
	private String instructionNumber;
	
	/** The icon type. */
	@JsonProperty("iconType")
	private String iconType;
	
	/** The transaction date. */
	@JsonProperty("transactionDate")
	private String transactionDate;
	
	/** The transaction amount. */
	@JsonProperty("transactionAmount")
	private BigDecimal transactionAmount;
	
	/** The transaction currency. */
	@JsonProperty("transactionCurrency")
	private String transactionCurrency;
	
	/** The card activity element details. */
	@JsonProperty("settlementDetails")
	private List<CardActivityElementDetails> cardActivityElementDetails = new ArrayList<>();
	
	/** The merchant name. */
	@JsonProperty("merchantName")
	private String merchantName;
	
	/** The merchant address. */
	@JsonProperty("merchantAddress")
	private String merchantAddress;
	
	/** The transaction type. */
	@JsonProperty("transactionType")
	private String transactionType;
	
	/** The auth method. */
	@JsonProperty("authMethod")
	private String authMethod;
	
	/** The purchase category. */
	@JsonProperty("purchaseCategory")
	private String purchaseCategory;
	
	/** The card activity fees details. */
	@JsonProperty("fees")
	private List<CardActivityFeesDetails> cardActivityFeesDetails = new ArrayList<>();

	/**
	 * @return the cdPaymentLifecycleId
	 */
	public String getCdPaymentLifecycleId() {
		return cdPaymentLifecycleId;
	}

	/**
	 * @param cdPaymentLifecycleId the cdPaymentLifecycleId to set
	 */
	public void setCdPaymentLifecycleId(String cdPaymentLifecycleId) {
		this.cdPaymentLifecycleId = cdPaymentLifecycleId;
	}
	
	/**
	 * @return the instructionNumber
	 */
	public String getInstructionNumber() {
		return instructionNumber;
	}

	/**
	 * @param instructionNumber the instructionNumber to set
	 */
	public void setInstructionNumber(String instructionNumber) {
		this.instructionNumber = instructionNumber;
	}

	/**
	 * @return the iconType
	 */
	public String getIconType() {
		return iconType;
	}

	/**
	 * @param iconType the iconType to set
	 */
	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionAmount
	 */
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * @param transactionCurrency the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the cardActivityElementDetails
	 */
	public List<CardActivityElementDetails> getCardActivityElementDetails() {
		return cardActivityElementDetails;
	}

	/**
	 * @param cardActivityElementDetails the cardActivityElementDetails to set
	 */
	public void setCardActivityElementDetails(List<CardActivityElementDetails> cardActivityElementDetails) {
		this.cardActivityElementDetails = cardActivityElementDetails;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the merchantAddress
	 */
	public String getMerchantAddress() {
		return merchantAddress;
	}

	/**
	 * @param merchantAddress the merchantAddress to set
	 */
	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the authMethod
	 */
	public String getAuthMethod() {
		return authMethod;
	}

	/**
	 * @param authMethod the authMethod to set
	 */
	public void setAuthMethod(String authMethod) {
		this.authMethod = authMethod;
	}

	/**
	 * @return the purchaseCategory
	 */
	public String getPurchaseCategory() {
		return purchaseCategory;
	}

	/**
	 * @param purchaseCategory the purchaseCategory to set
	 */
	public void setPurchaseCategory(String purchaseCategory) {
		this.purchaseCategory = purchaseCategory;
	}

	/**
	 * @return the cardActivityFeesDetails
	 */
	public List<CardActivityFeesDetails> getCardActivityFeesDetails() {
		return cardActivityFeesDetails;
	}

	/**
	 * @param cardActivityFeesDetails the cardActivityFeesDetails to set
	 */
	public void setCardActivityFeesDetails(List<CardActivityFeesDetails> cardActivityFeesDetails) {
		this.cardActivityFeesDetails = cardActivityFeesDetails;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardActivityHistoryDetails [cdPaymentLifecycleId=");
		builder.append(cdPaymentLifecycleId);
		builder.append(", instructionNumber=");
		builder.append(instructionNumber);
		builder.append(", iconType=");
		builder.append(iconType);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append(", transactionAmount=");
		builder.append(transactionAmount);
		builder.append(", transactionCurrency=");
		builder.append(transactionCurrency);
		builder.append(", cardActivityElementDetails=");
		builder.append(cardActivityElementDetails);
		builder.append(", merchantName=");
		builder.append(merchantName);
		builder.append(", merchantAddress=");
		builder.append(merchantAddress);
		builder.append(", transactionType=");
		builder.append(transactionType);
		builder.append(", authMethod=");
		builder.append(authMethod);
		builder.append(", purchaseCategory=");
		builder.append(purchaseCategory);
		builder.append(", cardActivityFeesDetails=");
		builder.append(cardActivityFeesDetails);
		builder.append("]");
		return builder.toString();
	}
}
