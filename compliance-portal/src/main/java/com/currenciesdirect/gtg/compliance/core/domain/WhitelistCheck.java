package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class WhitelistCheck.
 */
public class WhitelistCheck {

	private String currency;
	
	private String amoutRange;
	
	private String thirdParty;
	
	private String reasonOfTransfer;
	
	private String status;
	
	private String maxAmount;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public String getAmoutRange() {
		return amoutRange;
	}

	public String getThirdParty() {
		return thirdParty;
	}

	public String getReasonOfTransfer() {
		return reasonOfTransfer;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setAmoutRange(String amoutRange) {
		this.amoutRange = amoutRange;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	public void setReasonOfTransfer(String reasonOfTransfer) {
		this.reasonOfTransfer = reasonOfTransfer;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

}
