package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CustomCheckResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomCheckResponse extends BaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The organisation code", required = true)
	@JsonProperty("orgCode")
	private String orgCode;

	/** The acc id. */
	@ApiModelProperty(value = "The account id", required = true)
	@JsonProperty("accId")
	private Integer accId;

	/** The payment trans id. */
	@ApiModelProperty(value = "The payment transaction id", required = true)
	@JsonProperty("paymentTransId")
	private Integer paymentTransId;

	/** The overall status. */
	@ApiModelProperty(value = "The overall status", required = true)
	@JsonProperty("overallStatus")
	private String overallStatus;

	/** The velocity check. */
	@ApiModelProperty(value = "The velocity check object", required = true)
	@JsonProperty("velocityCheck")
	private VelocityCheckResponse velocityCheck;

	/** The white list check. */
	@ApiModelProperty(value = "The white list check object", required = true)
	@JsonProperty("whiteListCheck")
	private WhiteListCheckResponse whiteListCheck;

	/** The checked on. */
	@ApiModelProperty(value = "Timestamp when the check occurred", required = true)
	private String checkedOn;

	/** The is country whitelisted. */
	@JsonIgnore
	private boolean isCountryWhitelisted;

	/** The is country whitelisted for funds in. */
	@JsonIgnore
	private boolean isCountryWhitelistedForFundsIn;

	/** The account white list. */
	@ApiModelProperty(value = "The account white list", required = true)
	@JsonProperty("accountWhiteList")
	private AccountWhiteList accountWhiteList;
	
	/** The fraud predict status. */
	@ApiModelProperty(value = "The fraud predict status", required = true)
	@JsonProperty("fraudPredictStatus")
	private String fraudPredictStatus;
	
	/** The First Credit Check. */
	@ApiModelProperty(value = "The First Credit check object", required = true)
	@JsonProperty("firstCreditCheck")
	private FirstCreditCheckResponse firstCreditCheck;
	
	/** The eu poi check. */
	@ApiModelProperty(value = "The EU POI check object", required = true)
	@JsonProperty("euPoiCheck")
	private EuPoiCheckResponse euPoiCheck;
	
	/** The cdinc first credit check. */
	@ApiModelProperty(value = "The CDINC First Credit check object", required = true)
	@JsonProperty("cdincFirstCreditCheck")
	private CDINCFirstCreditCheckResponse cdincFirstCreditCheck;
	
	/**
	 * Gets the account white list.
	 *
	 * @return the account white list
	 */
	public AccountWhiteList getAccountWhiteList() {
		return accountWhiteList;
	}

	/**
	 * Sets the account white list.
	 *
	 * @param accountWhiteList
	 *            the new account white list
	 */
	public void setAccountWhiteList(AccountWhiteList accountWhiteList) {
		this.accountWhiteList = accountWhiteList;
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
	 * Gets the acc id.
	 *
	 * @return the accId
	 */
	public Integer getAccId() {
		return accId;
	}

	/**
	 * Sets the acc id.
	 *
	 * @param accId
	 *            the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	/**
	 * Gets the payment trans id.
	 *
	 * @return the paymentTransId
	 */
	public Integer getPaymentTransId() {
		return paymentTransId;
	}

	/**
	 * Sets the payment trans id.
	 *
	 * @param paymentTransId
	 *            the paymentTransId to set
	 */
	public void setPaymentTransId(Integer paymentTransId) {
		this.paymentTransId = paymentTransId;
	}

	/**
	 * Gets the overall status.
	 *
	 * @return the overallStatus
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * Sets the overall status.
	 *
	 * @param overallStatus
	 *            the overallStatus to set
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * Gets the velocity check.
	 *
	 * @return the velocityCheck
	 */
	public VelocityCheckResponse getVelocityCheck() {
		return velocityCheck;
	}

	/**
	 * Sets the velocity check.
	 *
	 * @param velocityCheck
	 *            the velocityCheck to set
	 */
	public void setVelocityCheck(VelocityCheckResponse velocityCheck) {
		this.velocityCheck = velocityCheck;
	}

	/**
	 * Gets the white list check.
	 *
	 * @return the whiteListCheck
	 */
	public WhiteListCheckResponse getWhiteListCheck() {
		return whiteListCheck;
	}

	/**
	 * Sets the white list check.
	 *
	 * @param whiteListCheck
	 *            the whiteListCheck to set
	 */
	public void setWhiteListCheck(WhiteListCheckResponse whiteListCheck) {
		this.whiteListCheck = whiteListCheck;
	}

	/**
	 * Gets the checked on.
	 *
	 * @return the checked on
	 */
	public String getCheckedOn() {
		return checkedOn;
	}

	/**
	 * Sets the checked on.
	 *
	 * @param checkedOn
	 *            the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomCheckResponse [errorCode=" + errorCode + ", errorDescription=" + errorDescription + ", orgCode="
				+ orgCode + ", accId=" + accId + ", paymentTransId=" + paymentTransId + ", overallStatus="
				+ overallStatus + ", velocityCheck=" + velocityCheck + ", whiteListCheck=" + whiteListCheck
				+ ", checkedOn=" + checkedOn + ", fraudPredictStatus=" + fraudPredictStatus + ", firstCreditCheck=" + firstCreditCheck
				+ ", cdincFirstCreditCheck=" + cdincFirstCreditCheck +"]";
	}

	/**
	 * Checks if is country whitelisted.
	 *
	 * @return the isCountryWhitelisted
	 */
	@ApiModelProperty(value = "Whether the country is whitelisted", required = true)
	public boolean isCountryWhitelisted() {
		return isCountryWhitelisted;
	}

	/**
	 * Sets the country whitelisted.
	 *
	 * @param isCountryWhitelisted
	 *            the isCountryWhitelisted to set
	 */
	public void setCountryWhitelisted(boolean isCountryWhitelisted) {
		this.isCountryWhitelisted = isCountryWhitelisted;
	}

	/**
	 * Checks if is country whitelisted for funds in.
	 *
	 * @return true, if is country whitelisted for funds in
	 */
	@ApiModelProperty(value = "Whether the country is whitelisted for funds in", required = true)
	public boolean isCountryWhitelistedForFundsIn() {
		return isCountryWhitelistedForFundsIn;
	}

	/**
	 * Sets the country whitelisted for funds in.
	 *
	 * @param isCountryWhitelistedForFundsIn
	 *            the new country whitelisted for funds in
	 */
	public void setCountryWhitelistedForFundsIn(boolean isCountryWhitelistedForFundsIn) {
		this.isCountryWhitelistedForFundsIn = isCountryWhitelistedForFundsIn;
	}

	/**
	 * Gets the fraud predict status.
	 *
	 * @return the fraud predict status
	 */
	public String getFraudPredictStatus() {
		return fraudPredictStatus;
	}

	/**
	 * Sets the fraud predict status.
	 *
	 * @param fraudPredictStatus the new fraud predict status
	 */
	public void setFraudPredictStatus(String fraudPredictStatus) {
		this.fraudPredictStatus = fraudPredictStatus;
	}

	/**
	 * @return the firstCreditCheck
	 */
	public FirstCreditCheckResponse getFirstCreditCheck() {
		return firstCreditCheck;
	}

	/**
	 * @param firstCreditCheck the firstCreditCheck to set
	 */
	public void setFirstCreditCheck(FirstCreditCheckResponse firstCreditCheck) {
		this.firstCreditCheck = firstCreditCheck;
	}

	/**
	 * @return the euPoiCheck
	 */
	public EuPoiCheckResponse getEuPoiCheck() {
		return euPoiCheck;
	}

	/**
	 * @param euPoiCheck the euPoiCheck to set
	 */
	public void setEuPoiCheck(EuPoiCheckResponse euPoiCheck) {
		this.euPoiCheck = euPoiCheck;
	}

	/**
	 * Gets the cdinc first credit check.
	 *
	 * @return the cdinc first credit check
	 */
	public CDINCFirstCreditCheckResponse getCdincFirstCreditCheck() {
		return cdincFirstCreditCheck;
	}

	/**
	 * Sets the cdinc first credit check.
	 *
	 * @param cdincFirstCreditCheck the new cdinc first credit check
	 */
	public void setCdincFirstCreditCheck(CDINCFirstCreditCheckResponse cdincFirstCreditCheck) {
		this.cdincFirstCreditCheck = cdincFirstCreditCheck;
	}

	
}
