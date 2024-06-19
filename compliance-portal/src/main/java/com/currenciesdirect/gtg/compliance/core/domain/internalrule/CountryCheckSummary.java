package com.currenciesdirect.gtg.compliance.core.domain.internalrule;
/**
 * The Class CountryCheckSummary.
 */
public class CountryCheckSummary {
	
	private String status;
	private String country;
	private String riskLevel;

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
}
