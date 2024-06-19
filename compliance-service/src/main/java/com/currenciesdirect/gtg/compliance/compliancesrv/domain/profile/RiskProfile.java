/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

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

}
