package com.currenciesdirect.gtg.compliance.customchecks.domain.fundsin;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.RiskScore;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.ESDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "org_code", "source_application", "trade", "device_info" })
public class FundsInCreateRequest extends ESDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("trade")
	private FundsInTrade fundsInTrade;

	@JsonProperty("device_info")
	private DeviceInfo fundsInDeviceInfo;

	@JsonProperty("risk_score")
	private RiskScore fundsInRiskScore;

	/**
	 * @return the fundsInTrade
	 */
	@JsonProperty("trade")
	public FundsInTrade getFundsInTrade() {
		return fundsInTrade;
	}

	/**
	 * @param fundsInTrade
	 *            the fundsInTrade to set
	 */
	@JsonProperty("trade")
	public void setFundsInTrade(FundsInTrade fundsInTrade) {
		this.fundsInTrade = fundsInTrade;
	}

	/**
	 * @return the fundsInDeviceInfo
	 */
	@JsonProperty("device_info")
	public DeviceInfo getFundsInDeviceInfo() {
		return fundsInDeviceInfo;
	}

	/**
	 * @param fundsInDeviceInfo
	 *            the fundsInDeviceInfo to set
	 */
	@JsonProperty("device_info")
	public void setFundsInDeviceInfo(DeviceInfo fundsInDeviceInfo) {
		this.fundsInDeviceInfo = fundsInDeviceInfo;
	}

	/**
	 * @return the fundsInRiskScore
	 */
	@JsonProperty("risk_score")
	public RiskScore getFundsInRiskScore() {
		return fundsInRiskScore;
	}

	/**
	 * @param fundsInRiskScore
	 *            the fundsInRiskScore to set
	 */
	@JsonProperty("risk_score")
	public void setFundsInRiskScore(RiskScore fundsInRiskScore) {
		this.fundsInRiskScore = fundsInRiskScore;
	}

	@Override
	public String getCurrencyCode() {
		return getFundsInTrade().getTransactionCurrency().toUpperCase();
	}

	@Override
	public Double getBaseAmount() {
		return getFundsInTrade().getSellingAmountBaseValue();
	}

}