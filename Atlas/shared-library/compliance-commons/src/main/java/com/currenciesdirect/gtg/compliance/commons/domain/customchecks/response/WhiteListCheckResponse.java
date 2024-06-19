package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class WhiteListCheckResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhiteListCheckResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "Whitelist currency", example = "USD", required = true)
	private String currency;
	@ApiModelProperty(value = "Whitelist Amount Range", required = true)
	private String amoutRange;
	@ApiModelProperty(value = "Third Party Account", required = true)
	private String thirdParty;
	@ApiModelProperty(value = "Reason for the Transfer", required = true)
	private String reasonOfTransfer;
	@ApiModelProperty(value = "Status of the check", required = true)
	private String status;
	@ApiModelProperty(value = "Maximum Amount", required = true)
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
