package com.currenciesdirect.gtg.compliance.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomUpdateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("payment_fundsout_id")
	private Integer paymentOutId;
	
	@JsonProperty("payment_fundsin_id")
	private Integer paymentInId;
	
	@JsonProperty("org_code")
	private String orgCode;
	
	private String country;
	
	private String countryRiskLevel;
	
	private String beneCheckStatus;

	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the countryRiskLevel
	 */
	public String getCountryRiskLevel() {
		return countryRiskLevel;
	}

	/**
	 * @param countryRiskLevel the countryRiskLevel to set
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
	 * @param beneCheckStatus the beneCheckStatus to set
	 */
	public void setBeneCheckStatus(String beneCheckStatus) {
		this.beneCheckStatus = beneCheckStatus;
	}

	
}
