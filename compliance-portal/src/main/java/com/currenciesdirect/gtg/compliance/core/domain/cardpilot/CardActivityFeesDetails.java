package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardActivityFeesDetails.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardActivityFeesDetails implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The amount. */
	@JsonProperty("amount")
	private BigDecimal amount;
	
	/** The currency. */
	@JsonProperty("currency")
	private String currency;
	
	/** The description. */
	@JsonProperty("description")
	private String description;
	
	/** The type. */
	@JsonProperty("type")
	private String type;

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardActivityFeesDetails [amount=");
		builder.append(amount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", description=");
		builder.append(description);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
