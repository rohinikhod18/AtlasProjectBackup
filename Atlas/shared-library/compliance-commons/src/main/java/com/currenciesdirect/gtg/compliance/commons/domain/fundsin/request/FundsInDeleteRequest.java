package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsInDeleteRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "org_code", "source_application", "trade_account_number", "payment_fundsin_id", })
public class FundsInDeleteRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the request originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty("org_code")
	protected String orgCode;

	/** The source application. */
	@ApiModelProperty(value = "The source application where the request originated", required = true, example ="Salesforce, Dione, CD Aurora", position = 10)
	@JsonProperty("source_application")
	protected String sourceApplication;

	/** The trade account number. */
	@ApiModelProperty(value = "Trading Account Number", required = true)
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;

	/** The payment funds in id. */
	@ApiModelProperty(value = "Funds In ID", required = true)
	@JsonProperty("payment_fundsin_id")
	private Integer paymentFundsInId;

	/**
	 * Gets the trade account number.
	 *
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber
	 *            the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the payment funds in id.
	 *
	 * @return The paymentFundsinId
	 */
	@JsonProperty("payment_fundsin_id")
	public Integer getPaymentFundsInId() {
		return paymentFundsInId;
	}

	/**
	 * Sets the payment funds in id.
	 *
	 * @param paymentFundsInId
	 *            the new payment funds in id
	 */
	@JsonProperty("payment_fundsin_id")
	public void setPaymentFundsInId(Integer paymentFundsInId) {
		this.paymentFundsInId = paymentFundsInId;
	}

}
