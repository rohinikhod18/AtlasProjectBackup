package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardActivityElementDetails.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardActivityElementDetails implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The settlement amount. */
	@JsonProperty("settlementAmount")
	private BigDecimal settlementAmount;
	
	/** The settlement currency. */
	@JsonProperty("settlementCurrency")
	private String settlementCurrency;
	
	/** The settlement to transaction currency rate. */
	@JsonProperty("settlementToTransactionCurrencyRate")
	private BigDecimal settlementToTransactionCurrencyRate;
	
	/** The settlement amount sign. */
	@JsonProperty("settlementAmountSign")
	private String settlementAmountSign;

	/**
	 * @return the settlementAmount
	 */
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	/**
	 * @param settlementAmount the settlementAmount to set
	 */
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	/**
	 * @return the settlementCurrency
	 */
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	/**
	 * @param settlementCurrency the settlementCurrency to set
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	/**
	 * @return the settlementToTransactionCurrencyRate
	 */
	public BigDecimal getSettlementToTransactionCurrencyRate() {
		return settlementToTransactionCurrencyRate;
	}

	/**
	 * @param settlementToTransactionCurrencyRate the settlementToTransactionCurrencyRate to set
	 */
	public void setSettlementToTransactionCurrencyRate(BigDecimal settlementToTransactionCurrencyRate) {
		this.settlementToTransactionCurrencyRate = settlementToTransactionCurrencyRate;
	}

	/**
	 * @return the settlementAmountSign
	 */
	public String getSettlementAmountSign() {
		return settlementAmountSign;
	}

	/**
	 * @param settlementAmountSign the settlementAmountSign to set
	 */
	public void setSettlementAmountSign(String settlementAmountSign) {
		this.settlementAmountSign = settlementAmountSign;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardActivityElementDetails [settlementAmount=");
		builder.append(settlementAmount);
		builder.append(", settlementCurrency=");
		builder.append(settlementCurrency);
		builder.append(", settlementToTransactionCurrencyRate=");
		builder.append(settlementToTransactionCurrencyRate);
		builder.append(", settlementAmountSign=");
		builder.append(settlementAmountSign);
		builder.append("]");
		return builder.toString();
	}
	
}
