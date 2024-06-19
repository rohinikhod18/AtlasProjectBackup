package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WhiteListCheckResponse.
 */
public class WhiteListCheckSummary {

	private String currency;
	private String amoutRange;
	private String thirdParty;
	private String reasonOfTransfer;
	@JsonProperty("maxAmount")
	private Double maxAmount;
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
	 * @return the amoutRange
	 */
	public String getAmoutRange() {
		return amoutRange;
	}
	/**
	 * @param amoutRange the amoutRange to set
	 */
	public void setAmoutRange(String amoutRange) {
		this.amoutRange = amoutRange;
	}
	/**
	 * @return the thirdParty
	 */
	public String getThirdParty() {
		return thirdParty;
	}
	/**
	 * @param thirdParty the thirdParty to set
	 */
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	/**
	 * @return the reasonOfTransfer
	 */
	public String getReasonOfTransfer() {
		return reasonOfTransfer;
	}
	/**
	 * @param reasonOfTransfer the reasonOfTransfer to set
	 */
	public void setReasonOfTransfer(String reasonOfTransfer) {
		this.reasonOfTransfer = reasonOfTransfer;
	}
	/**
	 * @return the maxAmount
	 */
	public Double getMaxAmount() {
		return maxAmount;
	}
	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
}
