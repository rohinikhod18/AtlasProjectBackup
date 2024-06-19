/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class RegistrationRiskProfile.
 *
 * @author manish
 */
public class RegistrationRiskProfile {

	/** The country risk indicator. */
	@JsonProperty(value = "country_risk_indicator")
	private String countryRiskIndicator;
	
	/** The risk trend. */
	@JsonProperty(value = "risk_trend")
	private String riskTrend;
	
	/** The risk direction. */
	@JsonProperty(value = "risk_direction")
	private String riskDirection;
	
	/** The updated risk. */
	@JsonProperty(value = "updated_risk")
	private String updatedRisk;
	
	/** The failure score. */
	@JsonProperty(value = "failure_score")
	private String failureScore;
	
	/** The delinquency failure score. */
	@JsonProperty(value = "delinquency_failure_score")
	private String delinquencyFailureScore;
	
	
   /*********************************NEWLY ADDED DNB DATA ******************************************************************/
	
	@JsonProperty(value = "continent")
	private String continent;
	@JsonProperty(value = "country")
	private String country;
	@JsonProperty(value = "state_country")
	private String stateCountry;
	@JsonProperty(value = "duns_number")
	private String dunsNumber;
	@JsonProperty(value = "trading_styles")
	private String tradingStyles;
	@JsonProperty(value = "us1987_primary_SIC_4_digit")
	private String us1987PrimarySic4Digit;
	@JsonProperty(value = "financial_figures_month")
	private String financialFiguresMonth;
	@JsonProperty(value = "financial_figures_year")
	private String financialFiguresYear;
	@JsonProperty(value = "financial_year_end_date")
	private String financialYearEndDate;
	@JsonProperty(value = "annual_sales")
	private String annualSales;
	@JsonProperty(value = "modelled_annual_sales")
	private String modelledAnnualSales;
	@JsonProperty(value = "net_worth_amount")
	private String netWorthAmount;
	@JsonProperty(value = "modelled_net_worth")
	private String modelledNetWorth;
	@JsonProperty(value = "location_type")
	private String locationType;
	@JsonProperty(value = "import_export_indicator")
	private String importExportIndicator;
	@JsonProperty(value = "domestic_ultimate_record")
	private String domesticUltimateRecord;
	@JsonProperty(value = "global_ultimate_record")
	private String globalUltimateRecord;
	@JsonProperty(value = "group_structure_number_of_levels")
	private String groupStructureNumberOfLevels;
	@JsonProperty(value = "headquarter_details")
	private String headquarterDetails;
	@JsonProperty(value = "immediate_parent_details")
	private String immediateParentDetails;
	@JsonProperty(value = "domestic_ultimate_parent_details")
	private String domesticUltimateParentDetails;
	@JsonProperty(value = "global_ultimate_parent_details")
	private String globalUltimateParentDetails;
	@JsonProperty(value = "credit_limit")
	private String creditLimit;
	@JsonProperty(value = "risk_rating")
	private String riskRating;
	@JsonProperty(value = "profit_loss")
	private String profitLoss;
	/**
	 * Gets the country risk indicator.
	 *
	 * @return the country risk indicator
	 */
	public String getCountryRiskIndicator() {
		return countryRiskIndicator;
	}

	/**
	 * Sets the country risk indicator.
	 *
	 * @param countryRiskIndicator the new country risk indicator
	 */
	public void setCountryRiskIndicator(String countryRiskIndicator) {
		this.countryRiskIndicator = countryRiskIndicator;
	}

	/**
	 * Gets the risk trend.
	 *
	 * @return the risk trend
	 */
	public String getRiskTrend() {
		return riskTrend;
	}

	/**
	 * Sets the risk trend.
	 *
	 * @param riskTrend the new risk trend
	 */
	public void setRiskTrend(String riskTrend) {
		this.riskTrend = riskTrend;
	}

	/**
	 * Gets the risk direction.
	 *
	 * @return the risk direction
	 */
	public String getRiskDirection() {
		return riskDirection;
	}

	/**
	 * Sets the risk direction.
	 *
	 * @param riskDirection the new risk direction
	 */
	public void setRiskDirection(String riskDirection) {
		this.riskDirection = riskDirection;
	}

	/**
	 * Gets the updated risk.
	 *
	 * @return the updated risk
	 */
	public String getUpdatedRisk() {
		return updatedRisk;
	}

	/**
	 * Sets the updated risk.
	 *
	 * @param updatedRisk the new updated risk
	 */
	public void setUpdatedRisk(String updatedRisk) {
		this.updatedRisk = updatedRisk;
	}


	/**
	 * Gets the failure score.
	 *
	 * @return the failure score
	 */
	public String getFailureScore() {
		return failureScore;
	}

	/**
	 * Sets the failure score.
	 *
	 * @param failureScore the new failure score
	 */
	public void setFailureScore(String failureScore) {
		this.failureScore = failureScore;
	}

	/**
	 * Gets the delinquency failure score.
	 *
	 * @return the delinquency failure score
	 */
	public String getDelinquencyFailureScore() {
		return delinquencyFailureScore;
	}

	/**
	 * Sets the delinquency failure score.
	 *
	 * @param delinquencyFailureScore the new delinquency failure score
	 */
	public void setDelinquencyFailureScore(String delinquencyFailureScore) {
		this.delinquencyFailureScore = delinquencyFailureScore;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStateCountry() {
		return stateCountry;
	}

	public void setStateCountry(String stateCountry) {
		this.stateCountry = stateCountry;
	}

	public String getDunsNumber() {
		return dunsNumber;
	}

	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	public String getTradingStyles() {
		return tradingStyles;
	}

	public void setTradingStyles(String tradingStyles) {
		this.tradingStyles = tradingStyles;
	}

	public String getUs1987PrimarySic4Digit() {
		return us1987PrimarySic4Digit;
	}

	public void setUs1987PrimarySic4Digit(String us1987PrimarySic4Digit) {
		this.us1987PrimarySic4Digit = us1987PrimarySic4Digit;
	}

	public String getFinancialFiguresMonth() {
		return financialFiguresMonth;
	}

	public void setFinancialFiguresMonth(String financialFiguresMonth) {
		this.financialFiguresMonth = financialFiguresMonth;
	}

	public String getFinancialFiguresYear() {
		return financialFiguresYear;
	}

	public void setFinancialFiguresYear(String financialFiguresYear) {
		this.financialFiguresYear = financialFiguresYear;
	}

	public String getFinancialYearEndDate() {
		return financialYearEndDate;
	}

	public void setFinancialYearEndDate(String financialYearEndDate) {
		this.financialYearEndDate = financialYearEndDate;
	}

	public String getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(String annualSales) {
		this.annualSales = annualSales;
	}

	public String getModelledAnnualSales() {
		return modelledAnnualSales;
	}

	public void setModelledAnnualSales(String modelledAnnualSales) {
		this.modelledAnnualSales = modelledAnnualSales;
	}

	public String getNetWorthAmount() {
		return netWorthAmount;
	}

	public void setNetWorthAmount(String netWorthAmount) {
		this.netWorthAmount = netWorthAmount;
	}

	public String getModelledNetWorth() {
		return modelledNetWorth;
	}

	public void setModelledNetWorth(String modelledNetWorth) {
		this.modelledNetWorth = modelledNetWorth;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getImportExportIndicator() {
		return importExportIndicator;
	}

	public void setImportExportIndicator(String importExportIndicator) {
		this.importExportIndicator = importExportIndicator;
	}

	public String getDomesticUltimateRecord() {
		return domesticUltimateRecord;
	}

	public void setDomesticUltimateRecord(String domesticUltimateRecord) {
		this.domesticUltimateRecord = domesticUltimateRecord;
	}

	public String getGlobalUltimateRecord() {
		return globalUltimateRecord;
	}

	public void setGlobalUltimateRecord(String globalUltimateRecord) {
		this.globalUltimateRecord = globalUltimateRecord;
	}

	public String getGroupStructureNumberOfLevels() {
		return groupStructureNumberOfLevels;
	}

	public void setGroupStructureNumberOfLevels(String groupStructureNumberOfLevels) {
		this.groupStructureNumberOfLevels = groupStructureNumberOfLevels;
	}

	public String getHeadquarterDetails() {
		return headquarterDetails;
	}

	public void setHeadquarterDetails(String headquarterDetails) {
		this.headquarterDetails = headquarterDetails;
	}

	public String getImmediateParentDetails() {
		return immediateParentDetails;
	}

	public void setImmediateParentDetails(String immediateParentDetails) {
		this.immediateParentDetails = immediateParentDetails;
	}

	public String getDomesticUltimateParentDetails() {
		return domesticUltimateParentDetails;
	}

	public void setDomesticUltimateParentDetails(String domesticUltimateParentDetails) {
		this.domesticUltimateParentDetails = domesticUltimateParentDetails;
	}

	public String getGlobalUltimateParentDetails() {
		return globalUltimateParentDetails;
	}

	public void setGlobalUltimateParentDetails(String globalUltimateParentDetails) {
		this.globalUltimateParentDetails = globalUltimateParentDetails;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getRiskRating() {
		return riskRating;
	}

	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}

	public String getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(String profitLoss) {
		this.profitLoss = profitLoss;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryRiskIndicator == null) ? 0 : countryRiskIndicator.hashCode());
		result = prime * result + ((delinquencyFailureScore == null) ? 0 : delinquencyFailureScore.hashCode());
		result = prime * result + ((failureScore == null) ? 0 : failureScore.hashCode());
		result = prime * result + ((riskDirection == null) ? 0 : riskDirection.hashCode());
		result = prime * result + ((riskTrend == null) ? 0 : riskTrend.hashCode());
		result = prime * result + ((updatedRisk == null) ? 0 : updatedRisk.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRiskProfile other = (RegistrationRiskProfile) obj;
		if (countryRiskIndicator == null) {
			if (other.countryRiskIndicator != null)
				return false;
		} else if (!countryRiskIndicator.equals(other.countryRiskIndicator)) {
			return false;
		  }
		if (delinquencyFailureScore == null) {
			if (other.delinquencyFailureScore != null)
				return false;
		} else if (!delinquencyFailureScore.equals(other.delinquencyFailureScore)) {
			return false;
		  }
		if (failureScore == null) {
			if (other.failureScore != null)
				return false;
		} else if (!failureScore.equals(other.failureScore)) {
			return false;
		  }
		if (riskDirection == null) {
			if (other.riskDirection != null)
				return false;
		} else if (!riskDirection.equals(other.riskDirection)) {
			return false;
		  }
		if (riskTrend == null) {
			if (other.riskTrend != null)
				return false;
		} else if (!riskTrend.equals(other.riskTrend)) {
			return false;
		  }
		if (updatedRisk == null) {
			if (other.updatedRisk != null)
				return false;
		} else if (!updatedRisk.equals(other.updatedRisk)) {
			return false;
		  }
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegistrationRiskProfile [countryRiskIndicator=" + countryRiskIndicator + ", riskTrend=" + riskTrend
				+ ", riskDirection=" + riskDirection + ", updatedRisk=" + updatedRisk + ", failureScore=" + failureScore
				+ ", delinquencyFailureScore=" + delinquencyFailureScore + "]";
	}

}
