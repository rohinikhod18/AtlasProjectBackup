package com.currenciesdirect.gtg.compliance.core.domain;
/**
 * The Class CountryRiskLevelEnum.
 */
public enum CountryRiskLevelEnum {
	
	H("High Risk Country"),
	S("Sanctioned Country"),
	L("Low Risk Country");
	
	private String riskLevel;

	CountryRiskLevelEnum(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

}
