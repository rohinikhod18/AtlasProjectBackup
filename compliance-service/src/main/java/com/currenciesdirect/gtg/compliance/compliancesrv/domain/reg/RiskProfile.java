/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author manish
 *
 */
public class RiskProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The country risk indicator. */
	@ApiModelProperty(value = "The country risk indicator", required = true)
	@JsonProperty(value = "country_risk_indicator")
	private String countryRiskIndicator;
	
	/** The risk trend. */
	@ApiModelProperty(value = "The risk trend", required = true)
	@JsonProperty(value = "risk_trend")
	private String riskTrend;
	
	/** The risk direction. */
	@ApiModelProperty(value = "The risk direction", required = true)
	@JsonProperty(value = "risk_direction")
	private String riskDirection;
	
	/** The updated risk. */
	@ApiModelProperty(value = "The updated risk", required = true)
	@JsonProperty(value = "updated_risk")
	private String updatedRisk;
	
	/** The failure score. */
	@ApiModelProperty(value = "The D&B Failure Score is a predictive indicator of business insolvencies", required = true)
	@JsonProperty(value = "failure_score")
	private String failureScore;
	
	/** The delinquency failure score. */
	@ApiModelProperty(value = "The D&B Delinquency Score is a relative measure of risk, whereby 1 represents organisations that have the highest probability of delinquency and 100 the lowest", example = "50", required = true)
	@JsonProperty(value = "delinquency_failure_score")
	private String delinquencyFailureScore;

	/*
	 * 
	 * Newly Added DNB Fields
	 */
	
	/** The continent. */
	@ApiModelProperty(value = "The continent", required = true)
	@JsonProperty(value = "continent")
	private String continent;
	
	/** The country. */
	@ApiModelProperty(value = "The country", required = true)
	@JsonProperty(value = "country")
	private String country;
	
	/** The state country. */
	@ApiModelProperty(value = "The state country", required = true)
	@JsonProperty(value = "state_country")
	private String stateCountry;
	
	/** The duns number. */
	@ApiModelProperty(value = "The duns number", required = true)
	@JsonProperty(value = "duns_number")
	private String dunsNumber;
	
	/** The trading styles. */
	@ApiModelProperty(value = "The trading styles", required = true)
	@JsonProperty(value = "trading_styles")
	private String tradingStyles;
	
	/** The us 1987 primary sic 4 digit. */
	@ApiModelProperty(value = "The us 1987 primary sic 4 digit", required = true)
	@JsonProperty(value = "us1987_primary_SIC_4_digit")
	private String us1987PrimarySic4Digit;
	
	/** The financial figures month. */
	@ApiModelProperty(value = "The financial figures month", required = true)
	@JsonProperty(value = "financial_figures_month")
	private String financialFiguresMonth;
	
	/** The financial figures year. */
	@ApiModelProperty(value = "The financial figures year", required = true)
	@JsonProperty(value = "financial_figures_year")
	private String financialFiguresYear;
	
	/** The financial year end date. */
	@ApiModelProperty(value = "The financial year end date", required = true)
	@JsonProperty(value = "financial_year_end_date")
	private String financialYearEndDate;
	
	/** The annual sales. */
	@ApiModelProperty(value = "The annual sales", required = true)
	@JsonProperty(value = "annual_sales")
	private String annualSales;
	
	/** The modelled annual sales. */
	@ApiModelProperty(value = "The modelled annual sales", required = true)
	@JsonProperty(value = "modelled_annual_sales")
	private String modelledAnnualSales;
	
	/** The net worth amount. */
	@ApiModelProperty(value = "The net worth amount", required = true)
	@JsonProperty(value = "net_worth_amount")
	private String netWorthAmount;
	
	/** The modelled net worth. */
	@ApiModelProperty(value = "The modelled net worth", required = true)
	@JsonProperty(value = "modelled_net_worth")
	private String modelledNetWorth;
	
	/** The location type. */
	@ApiModelProperty(value = "The location type", required = true)
	@JsonProperty(value = "location_type")
	private String locationType;
	
	/** The import export indicator. */
	@ApiModelProperty(value = "The import export indicator", required = true)
	@JsonProperty(value = "import_export_indicator")
	private String importExportIndicator;
	
	/** The domestic ultimate record. */
	@ApiModelProperty(value = "The domestic ultimate record", required = true)
	@JsonProperty(value = "domestic_ultimate_record")
	private String domesticUltimateRecord;
	
	/** The global ultimate record. */
	@ApiModelProperty(value = "The global ultimate record", required = true)
	@JsonProperty(value = "global_ultimate_record")
	private String globalUltimateRecord;
	
	/** The group structure number of levels. */
	@ApiModelProperty(value = "The group structure number of levels", required = true)
	@JsonProperty(value = "group_structure_number_of_levels")
	private String groupStructureNumberOfLevels;
	
	/** The headquarter details. */
	@ApiModelProperty(value = "The headquarter details", required = true)
	@JsonProperty(value = "headquarter_details")
	private String headquarterDetails;
	
	/** The immediate parent details. */
	@ApiModelProperty(value = "The immediate parent details", required = true)
	@JsonProperty(value = "immediate_parent_details")
	private String immediateParentDetails;
	
	/** The domestic ultimate parent details. */
	@ApiModelProperty(value = "The domestic ultimate parent details", required = true)
	@JsonProperty(value = "domestic_ultimate_parent_details")
	private String domesticUltimateParentDetails;
	
	/** The global ultimate parent details. */
	@ApiModelProperty(value = "The global ultimate parent details", required = true)
	@JsonProperty(value = "global_ultimate_parent_details")
	private String globalUltimateParentDetails;
	
	/** The credit limit. */
	@ApiModelProperty(value = "The credit limit", required = true)
	@JsonProperty(value = "credit_limit")
	private String creditLimit;
	
	/** The risk rating. */
	@ApiModelProperty(value = "The risk rating", required = true)
	@JsonProperty(value = "risk_rating")
	private String riskRating;
	
	/** The profit loss. */
	@ApiModelProperty(value = "The profit loss", required = true)
	@JsonProperty(value = "profit_loss")
	private String profitLoss;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getCountryRiskIndicator() {
		return countryRiskIndicator;
	}

	public void setCountryRiskIndicator(String countryRiskIndicator) {
		this.countryRiskIndicator = countryRiskIndicator;
	}

	public String getRiskTrend() {
		return riskTrend;
	}

	public void setRiskTrend(String riskTrend) {
		this.riskTrend = riskTrend;
	}

	public String getRiskDirection() {
		return riskDirection;
	}

	public void setRiskDirection(String riskDirection) {
		this.riskDirection = riskDirection;
	}

	public String getUpdatedRisk() {
		return updatedRisk;
	}

	public void setUpdatedRisk(String updatedRisk) {
		this.updatedRisk = updatedRisk;
	}

	public String getFailureScore() {
		return failureScore;
	}

	public void setFailureScore(String failureScore) {
		this.failureScore = failureScore;
	}

	public String getDelinquencyFailureScore() {
		return delinquencyFailureScore;
	}

	public void setDelinquencyFailureScore(String delinquencyFailureScore) {
		this.delinquencyFailureScore = delinquencyFailureScore;
	}

	@Override
	public String toString() {
		return "RiskProfile [countryRiskIndicator=" + countryRiskIndicator + ", riskTrend=" + riskTrend
				+ ", riskDirection=" + riskDirection + ", updatedRisk=" + updatedRisk + ", failureScore=" + failureScore
				+ ", delinquencyFailureScore=" + delinquencyFailureScore + ", continent=" + continent + ", country="
				+ country + ", stateCountry=" + stateCountry + ", dunsNumber=" + dunsNumber + ", tradingStyles="
				+ tradingStyles + ", us1987PrimarySic4Digit=" + us1987PrimarySic4Digit + ", financialFiguresMonth="
				+ financialFiguresMonth + ", financialFiguresYear=" + financialFiguresYear + ", financialYearEndDate="
				+ financialYearEndDate + ", annualSales=" + annualSales + ", modelledAnnualSales=" + modelledAnnualSales
				+ ", netWorthAmount=" + netWorthAmount + ", modelledNetWorth=" + modelledNetWorth + ", locationType="
				+ locationType + ", importExportIndicator=" + importExportIndicator + ", domesticUltimateRecord="
				+ domesticUltimateRecord + ", globalUltimateRecord=" + globalUltimateRecord
				+ ", groupStructureNumberOfLevels=" + groupStructureNumberOfLevels + ", headquarterDetails="
				+ headquarterDetails + ", immediateParentDetails=" + immediateParentDetails
				+ ", domesticUltimateParentDetails=" + domesticUltimateParentDetails + ", globalUltimateParentDetails="
				+ globalUltimateParentDetails + ", creditLimit=" + creditLimit + ", riskRating=" + riskRating
				+ ", profitLoss=" + profitLoss + "]";
	}

	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annualSales == null) ? 0 : annualSales.hashCode());
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryRiskIndicator == null) ? 0 : countryRiskIndicator.hashCode());
		result = prime * result + ((creditLimit == null) ? 0 : creditLimit.hashCode());
		result = prime * result + ((delinquencyFailureScore == null) ? 0 : delinquencyFailureScore.hashCode());
		result = prime * result
				+ ((domesticUltimateParentDetails == null) ? 0 : domesticUltimateParentDetails.hashCode());
		result = prime * result + ((domesticUltimateRecord == null) ? 0 : domesticUltimateRecord.hashCode());
		result = prime * result + ((dunsNumber == null) ? 0 : dunsNumber.hashCode());
		result = prime * result + ((failureScore == null) ? 0 : failureScore.hashCode());
		result = prime * result + ((financialFiguresMonth == null) ? 0 : financialFiguresMonth.hashCode());
		result = prime * result + ((financialFiguresYear == null) ? 0 : financialFiguresYear.hashCode());
		result = prime * result + ((financialYearEndDate == null) ? 0 : financialYearEndDate.hashCode());
		result = prime * result + ((globalUltimateParentDetails == null) ? 0 : globalUltimateParentDetails.hashCode());
		result = prime * result + ((globalUltimateRecord == null) ? 0 : globalUltimateRecord.hashCode());
		result = prime * result
				+ ((groupStructureNumberOfLevels == null) ? 0 : groupStructureNumberOfLevels.hashCode());
		result = prime * result + ((headquarterDetails == null) ? 0 : headquarterDetails.hashCode());
		result = prime * result + ((immediateParentDetails == null) ? 0 : immediateParentDetails.hashCode());
		result = prime * result + ((importExportIndicator == null) ? 0 : importExportIndicator.hashCode());
		result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
		result = prime * result + ((modelledAnnualSales == null) ? 0 : modelledAnnualSales.hashCode());
		result = prime * result + ((modelledNetWorth == null) ? 0 : modelledNetWorth.hashCode());
		result = prime * result + ((netWorthAmount == null) ? 0 : netWorthAmount.hashCode());
		result = prime * result + ((profitLoss == null) ? 0 : profitLoss.hashCode());
		result = prime * result + ((riskDirection == null) ? 0 : riskDirection.hashCode());
		result = prime * result + ((riskRating == null) ? 0 : riskRating.hashCode());
		result = prime * result + ((riskTrend == null) ? 0 : riskTrend.hashCode());
		result = prime * result + ((stateCountry == null) ? 0 : stateCountry.hashCode());
		result = prime * result + ((tradingStyles == null) ? 0 : tradingStyles.hashCode());
		result = prime * result + ((updatedRisk == null) ? 0 : updatedRisk.hashCode());
		result = prime * result + ((us1987PrimarySic4Digit == null) ? 0 : us1987PrimarySic4Digit.hashCode());
		return result;
	}

	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RiskProfile other = (RiskProfile) obj;
		if (annualSales == null) {
			if (other.annualSales != null)
				return false;
		} else if (!annualSales.equals(other.annualSales)) {
			return false;
		  }
		if (continent == null) {
			if (other.continent != null)
				return false;
		} else if (!continent.equals(other.continent)) {
			return false;
		  }
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country)) {
			return false;
		  }
		if (countryRiskIndicator == null) {
			if (other.countryRiskIndicator != null)
				return false;
		} else if (!countryRiskIndicator.equals(other.countryRiskIndicator)) {
			return false;
		  }
		if (creditLimit == null) {
			if (other.creditLimit != null)
				return false;
		} else if (!creditLimit.equals(other.creditLimit)) {
			return false;
		  }
		if (delinquencyFailureScore == null) {
			if (other.delinquencyFailureScore != null)
				return false;
		} else if (!delinquencyFailureScore.equals(other.delinquencyFailureScore)) {
			return false;
		  }
		if (domesticUltimateParentDetails == null) {
			if (other.domesticUltimateParentDetails != null)
				return false;
		} else if (!domesticUltimateParentDetails.equals(other.domesticUltimateParentDetails)) {
			return false;
		  }
		if (domesticUltimateRecord == null) {
			if (other.domesticUltimateRecord != null)
				return false;
		} else if (!domesticUltimateRecord.equals(other.domesticUltimateRecord)) {
			return false;
		  }
		if (dunsNumber == null) {
			if (other.dunsNumber != null)
				return false;
		} else if (!dunsNumber.equals(other.dunsNumber)) {
			return false;
		  }
		if (failureScore == null) {
			if (other.failureScore != null)
				return false;
		} else if (!failureScore.equals(other.failureScore)) {
			return false;
		  }
		if (financialFiguresMonth == null) {
			if (other.financialFiguresMonth != null)
				return false;
		} else if (!financialFiguresMonth.equals(other.financialFiguresMonth)) {
			return false;
		  }
		if (financialFiguresYear == null) {
			if (other.financialFiguresYear != null)
				return false;
		} else if (!financialFiguresYear.equals(other.financialFiguresYear)) {
			return false;
		  }
		if (financialYearEndDate == null) {
			if (other.financialYearEndDate != null)
				return false;
		} else if (!financialYearEndDate.equals(other.financialYearEndDate)) {
			return false;
		  }
		if (globalUltimateParentDetails == null) {
			if (other.globalUltimateParentDetails != null)
				return false;
		} else if (!globalUltimateParentDetails.equals(other.globalUltimateParentDetails)) {
			return false;
		  }
		if (globalUltimateRecord == null) {
			if (other.globalUltimateRecord != null)
				return false;
		} else if (!globalUltimateRecord.equals(other.globalUltimateRecord)) {
			return false;
		  }
		if (groupStructureNumberOfLevels == null) {
			if (other.groupStructureNumberOfLevels != null)
				return false;
		} else if (!groupStructureNumberOfLevels.equals(other.groupStructureNumberOfLevels)) {
			return false;
		  }
		if (headquarterDetails == null) {
			if (other.headquarterDetails != null)
				return false;
		} else if (!headquarterDetails.equals(other.headquarterDetails)) {
			return false;
		  }
		if (immediateParentDetails == null) {
			if (other.immediateParentDetails != null)
				return false;
		} else if (!immediateParentDetails.equals(other.immediateParentDetails)) {
			return false;
		  }
		if (importExportIndicator == null) {
			if (other.importExportIndicator != null)
				return false;
		} else if (!importExportIndicator.equals(other.importExportIndicator)) {
			return false;
		  }
		if (locationType == null) {
			if (other.locationType != null)
				return false;
		} else if (!locationType.equals(other.locationType)) {
			return false;
		  }
		if (modelledAnnualSales == null) {
			if (other.modelledAnnualSales != null)
				return false;
		} else if (!modelledAnnualSales.equals(other.modelledAnnualSales)) {
			return false;
		  }
		if (modelledNetWorth == null) {
			if (other.modelledNetWorth != null)
				return false;
		} else if (!modelledNetWorth.equals(other.modelledNetWorth)) {
			return false;
		  }
		if (netWorthAmount == null) {
			if (other.netWorthAmount != null)
				return false;
		} else if (!netWorthAmount.equals(other.netWorthAmount)) {
			return false;
		  }
		if (profitLoss == null) {
			if (other.profitLoss != null)
				return false;
		} else if (!profitLoss.equals(other.profitLoss)) {
			return false;
		  }
		if (riskDirection == null) {
			if (other.riskDirection != null)
				return false;
		} else if (!riskDirection.equals(other.riskDirection)) {
			return false;
		  }
		if (riskRating == null) {
			if (other.riskRating != null)
				return false;
		} else if (!riskRating.equals(other.riskRating)) {
			return false;
		  }
		if (riskTrend == null) {
			if (other.riskTrend != null)
				return false;
		} else if (!riskTrend.equals(other.riskTrend)) {
			return false;
		  }
		if (stateCountry == null) {
			if (other.stateCountry != null)
				return false;
		} else if (!stateCountry.equals(other.stateCountry)) {
			return false;
		  }
		if (tradingStyles == null) {
			if (other.tradingStyles != null)
				return false;
		} else if (!tradingStyles.equals(other.tradingStyles)) {
			return false;
		  }
		if (updatedRisk == null) {
			if (other.updatedRisk != null)
				return false;
		} else if (!updatedRisk.equals(other.updatedRisk)) {
			return false;
		  }
		if (us1987PrimarySic4Digit == null) {
			if (other.us1987PrimarySic4Digit != null)
				return false;
		} else if (!us1987PrimarySic4Digit.equals(other.us1987PrimarySic4Digit)) {
			return false;
		  }
		return true;
	}

}
