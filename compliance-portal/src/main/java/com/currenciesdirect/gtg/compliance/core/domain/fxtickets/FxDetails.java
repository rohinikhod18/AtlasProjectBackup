
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.core.domain.wallets.WalletDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FxDetails.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "trade_details", "wallet", "created_by", "created_on", "updated_by", "updated_on","income_source_of_fund" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class FxDetails {

	/** The id. */
	@JsonProperty("id")
	private Integer id;

	/** The trade details. */
	@JsonProperty("trade_details")
	private TradeDetails tradeDetails;

	/** The wallet. */
	@JsonProperty("wallet")
	private WalletDetails wallet;

	/** The created by. */
	@JsonProperty("created_by")
	private Integer createdBy;

	/** The created on. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	@JsonProperty("created_on")
	private Timestamp createdOn;

	/** The updated by. */
	@JsonProperty("updated_by")
	private Integer updatedBy;

	/** The updated on. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	@JsonProperty("updated_on")
	private Timestamp updatedOn;

	//ADDED as per Jira AT-2486
  	/** The source of funds. */
  	@JsonProperty("income_source_of_fund")
  	private String sourceOfFunds;
  	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the trade details.
	 *
	 * @return the trade details
	 */
	@JsonProperty("trade_details")
	public TradeDetails getTradeDetails() {
		return tradeDetails;
	}

	/**
	 * Sets the trade details.
	 *
	 * @param tradeDetails
	 *            the new trade details
	 */
	@JsonProperty("trade_details")
	public void setTradeDetails(TradeDetails tradeDetails) {
		this.tradeDetails = tradeDetails;
	}

	/**
	 * Gets the wallet.
	 *
	 * @return the wallet
	 */
	@JsonProperty("wallet")
	public WalletDetails getWallet() {
		return wallet;
	}

	/**
	 * Sets the wallet.
	 *
	 * @param wallet
	 *            the new wallet
	 */
	@JsonProperty("wallet")
	public void setWallet(WalletDetails wallet) {
		this.wallet = wallet;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	@JsonProperty("created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the new created by
	 */
	@JsonProperty("created_by")
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	@JsonProperty("created_on")
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	@JsonProperty("created_on")
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	@JsonProperty("updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy
	 *            the new updated by
	 */
	@JsonProperty("updated_by")
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	@JsonProperty("updated_on")
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	@JsonProperty("updated_on")
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	

	/**
	 * Gets the source of funds.
	 *
	 * @return the source of funds
	 */
	public String getSourceOfFunds() {
		return sourceOfFunds;
	}

	/**
	 * Sets the source of funds.
	 *
	 * @param sourceOfFunds the new source of funds
	 */
	public void setSourceOfFunds(String sourceOfFunds) {
		this.sourceOfFunds = sourceOfFunds;
	}

}
