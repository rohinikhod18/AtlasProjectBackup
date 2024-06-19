package com.currenciesdirect.gtg.compliance.commons.domain.whitelist;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class UdateWhiteListRequest.
 */
public class UdateWhiteListRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty("org_code")
	private String orgCode;

	/** The payment funds out id. */
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsOutId;

	/** The payment funds in id. */
	@ApiModelProperty(value = "funds in id", required = true)
	@JsonProperty("payment_fundsin_id")
	private Integer paymentFundsInId;

	/** The country. */
	@ApiModelProperty(value = "funds in country", required = true)
	private String country;

	/** The country risk level. */
	@ApiModelProperty(value = "funds in country risk level", required = true)
	private String countryRiskLevel;

	@ApiModelProperty(value = "beneficiary status", required = true)
	private String beneCheckStatus;

	/** The is documentation required watchlist. */
	private boolean isDocumentationRequiredWatchlist = false;

	private boolean isUSClientListBBeneAccNumber = false;

	@ApiModelProperty(value = "is beneficiary present in US client list b flag", required = true)
	public boolean isUSClientListBBeneAccNumber() {
		return isUSClientListBBeneAccNumber;
	}

	public void setUSClientListBBeneAccNumber(boolean isUSClientListBFundsOutWatchlist) {
		this.isUSClientListBBeneAccNumber = isUSClientListBFundsOutWatchlist;
	}

	/**
	 * Checks if is documentation required watchlist.
	 *
	 * @return true, if is documentation required watchlist
	 */
	@ApiModelProperty(value = "is documentation required for watchlist flag", required = true)
	public boolean isDocumentationRequiredWatchlist() {
		return isDocumentationRequiredWatchlist;
	}

	/**
	 * Sets the documentation required watchlist.
	 *
	 * @param isDocumentationRequiredWatchlist
	 *            the new documentation required watchlist
	 */
	public void setDocumentationRequiredWatchlist(boolean isDocumentationRequiredWatchlist) {
		this.isDocumentationRequiredWatchlist = isDocumentationRequiredWatchlist;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the payment funds out id.
	 *
	 * @return the paymentFundsOutId
	 */
	public Integer getPaymentFundsOutId() {
		return paymentFundsOutId;
	}

	/**
	 * Sets the payment funds out id.
	 *
	 * @param paymentFundsOutId
	 *            the paymentFundsOutId to set
	 */
	public void setPaymentFundsOutId(Integer paymentFundsOutId) {
		this.paymentFundsOutId = paymentFundsOutId;
	}

	/**
	 * Gets the payment funds in id.
	 *
	 * @return the paymentFundsInId
	 */
	public Integer getPaymentFundsInId() {
		return paymentFundsInId;
	}

	/**
	 * Sets the payment funds in id.
	 *
	 * @param paymentFundsInId
	 *            the paymentFundsInId to set
	 */
	public void setPaymentFundsInId(Integer paymentFundsInId) {
		this.paymentFundsInId = paymentFundsInId;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the country risk level.
	 *
	 * @return the countryRiskLevel
	 */
	public String getCountryRiskLevel() {
		return countryRiskLevel;
	}

	/**
	 * Sets the country risk level.
	 *
	 * @param countryRiskLevel
	 *            the countryRiskLevel to set
	 */
	public void setCountryRiskLevel(String countryRiskLevel) {
		this.countryRiskLevel = countryRiskLevel;
	}

	/**
	 * @return the beneCheckStatus
	 */
	public String getBeneCheckStatus() {
		return beneCheckStatus;
	}

	/**
	 * @param beneCheckStatus
	 *            the beneCheckStatus to set
	 */
	public void setBeneCheckStatus(String beneCheckStatus) {
		this.beneCheckStatus = beneCheckStatus;
	}

}
