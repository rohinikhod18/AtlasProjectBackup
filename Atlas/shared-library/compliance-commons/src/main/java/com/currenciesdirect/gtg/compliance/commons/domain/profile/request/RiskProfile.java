package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class RiskProfile.
 *
 * @author manish
 */
public class RiskProfile implements Serializable {

	/** The Constant serialVersionUID. */
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

	/**
	 * Gets the continent.
	 *
	 * @return the continent
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * Sets the continent.
	 *
	 * @param continent the new continent
	 */
	public void setContinent(String continent) {
		this.continent = continent;
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
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the state country.
	 *
	 * @return the state country
	 */
	public String getStateCountry() {
		return stateCountry;
	}

	/**
	 * Sets the state country.
	 *
	 * @param stateCountry the new state country
	 */
	public void setStateCountry(String stateCountry) {
		this.stateCountry = stateCountry;
	}

	/**
	 * Gets the duns number.
	 *
	 * @return the duns number
	 */
	public String getDunsNumber() {
		return dunsNumber;
	}

	/**
	 * Sets the duns number.
	 *
	 * @param dunsNumber the new duns number
	 */
	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}

	/**
	 * Gets the trading styles.
	 *
	 * @return the trading styles
	 */
	public String getTradingStyles() {
		return tradingStyles;
	}

	/**
	 * Sets the trading styles.
	 *
	 * @param tradingStyles the new trading styles
	 */
	public void setTradingStyles(String tradingStyles) {
		this.tradingStyles = tradingStyles;
	}

	/**
	 * Gets the us 1987 primary sic 4 digit.
	 *
	 * @return the us 1987 primary sic 4 digit
	 */
	public String getUs1987PrimarySic4Digit() {
		return us1987PrimarySic4Digit;
	}

	/**
	 * Sets the us 1987 primary sic 4 digit.
	 *
	 * @param us1987PrimarySic4Digit the new us 1987 primary sic 4 digit
	 */
	public void setUs1987PrimarySic4Digit(String us1987PrimarySic4Digit) {
		this.us1987PrimarySic4Digit = us1987PrimarySic4Digit;
	}

	/**
	 * Gets the financial figures month.
	 *
	 * @return the financial figures month
	 */
	public String getFinancialFiguresMonth() {
		return financialFiguresMonth;
	}

	/**
	 * Sets the financial figures month.
	 *
	 * @param financialFiguresMonth the new financial figures month
	 */
	public void setFinancialFiguresMonth(String financialFiguresMonth) {
		this.financialFiguresMonth = financialFiguresMonth;
	}

	/**
	 * Gets the financial figures year.
	 *
	 * @return the financial figures year
	 */
	public String getFinancialFiguresYear() {
		return financialFiguresYear;
	}

	/**
	 * Sets the financial figures year.
	 *
	 * @param financialFiguresYear the new financial figures year
	 */
	public void setFinancialFiguresYear(String financialFiguresYear) {
		this.financialFiguresYear = financialFiguresYear;
	}

	/**
	 * Gets the financial year end date.
	 *
	 * @return the financial year end date
	 */
	public String getFinancialYearEndDate() {
		return financialYearEndDate;
	}

	/**
	 * Sets the financial year end date.
	 *
	 * @param financialYearEndDate the new financial year end date
	 */
	public void setFinancialYearEndDate(String financialYearEndDate) {
		this.financialYearEndDate = financialYearEndDate;
	}

	/**
	 * Gets the annual sales.
	 *
	 * @return the annual sales
	 */
	public String getAnnualSales() {
		return annualSales;
	}

	/**
	 * Sets the annual sales.
	 *
	 * @param annualSales the new annual sales
	 */
	public void setAnnualSales(String annualSales) {
		this.annualSales = annualSales;
	}

	/**
	 * Gets the modelled annual sales.
	 *
	 * @return the modelled annual sales
	 */
	public String getModelledAnnualSales() {
		return modelledAnnualSales;
	}

	/**
	 * Sets the modelled annual sales.
	 *
	 * @param modelledAnnualSales the new modelled annual sales
	 */
	public void setModelledAnnualSales(String modelledAnnualSales) {
		this.modelledAnnualSales = modelledAnnualSales;
	}

	/**
	 * Gets the net worth amount.
	 *
	 * @return the net worth amount
	 */
	public String getNetWorthAmount() {
		return netWorthAmount;
	}

	/**
	 * Sets the net worth amount.
	 *
	 * @param netWorthAmount the new net worth amount
	 */
	public void setNetWorthAmount(String netWorthAmount) {
		this.netWorthAmount = netWorthAmount;
	}

	/**
	 * Gets the modelled net worth.
	 *
	 * @return the modelled net worth
	 */
	public String getModelledNetWorth() {
		return modelledNetWorth;
	}

	/**
	 * Sets the modelled net worth.
	 *
	 * @param modelledNetWorth the new modelled net worth
	 */
	public void setModelledNetWorth(String modelledNetWorth) {
		this.modelledNetWorth = modelledNetWorth;
	}

	/**
	 * Gets the location type.
	 *
	 * @return the location type
	 */
	public String getLocationType() {
		return locationType;
	}

	/**
	 * Sets the location type.
	 *
	 * @param locationType the new location type
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	/**
	 * Gets the import export indicator.
	 *
	 * @return the import export indicator
	 */
	public String getImportExportIndicator() {
		return importExportIndicator;
	}

	/**
	 * Sets the import export indicator.
	 *
	 * @param importExportIndicator the new import export indicator
	 */
	public void setImportExportIndicator(String importExportIndicator) {
		this.importExportIndicator = importExportIndicator;
	}

	/**
	 * Gets the domestic ultimate record.
	 *
	 * @return the domestic ultimate record
	 */
	public String getDomesticUltimateRecord() {
		return domesticUltimateRecord;
	}

	/**
	 * Sets the domestic ultimate record.
	 *
	 * @param domesticUltimateRecord the new domestic ultimate record
	 */
	public void setDomesticUltimateRecord(String domesticUltimateRecord) {
		this.domesticUltimateRecord = domesticUltimateRecord;
	}

	/**
	 * Gets the global ultimate record.
	 *
	 * @return the global ultimate record
	 */
	public String getGlobalUltimateRecord() {
		return globalUltimateRecord;
	}

	/**
	 * Sets the global ultimate record.
	 *
	 * @param globalUltimateRecord the new global ultimate record
	 */
	public void setGlobalUltimateRecord(String globalUltimateRecord) {
		this.globalUltimateRecord = globalUltimateRecord;
	}

	/**
	 * Gets the group structure number of levels.
	 *
	 * @return the group structure number of levels
	 */
	public String getGroupStructureNumberOfLevels() {
		return groupStructureNumberOfLevels;
	}

	/**
	 * Sets the group structure number of levels.
	 *
	 * @param groupStructureNumberOfLevels the new group structure number of levels
	 */
	public void setGroupStructureNumberOfLevels(String groupStructureNumberOfLevels) {
		this.groupStructureNumberOfLevels = groupStructureNumberOfLevels;
	}

	/**
	 * Gets the headquarter details.
	 *
	 * @return the headquarter details
	 */
	public String getHeadquarterDetails() {
		return headquarterDetails;
	}

	/**
	 * Sets the headquarter details.
	 *
	 * @param headquarterDetails the new headquarter details
	 */
	public void setHeadquarterDetails(String headquarterDetails) {
		this.headquarterDetails = headquarterDetails;
	}

	/**
	 * Gets the immediate parent details.
	 *
	 * @return the immediate parent details
	 */
	public String getImmediateParentDetails() {
		return immediateParentDetails;
	}

	/**
	 * Sets the immediate parent details.
	 *
	 * @param immediateParentDetails the new immediate parent details
	 */
	public void setImmediateParentDetails(String immediateParentDetails) {
		this.immediateParentDetails = immediateParentDetails;
	}

	/**
	 * Gets the domestic ultimate parent details.
	 *
	 * @return the domestic ultimate parent details
	 */
	public String getDomesticUltimateParentDetails() {
		return domesticUltimateParentDetails;
	}

	/**
	 * Sets the domestic ultimate parent details.
	 *
	 * @param domesticUltimateParentDetails the new domestic ultimate parent details
	 */
	public void setDomesticUltimateParentDetails(String domesticUltimateParentDetails) {
		this.domesticUltimateParentDetails = domesticUltimateParentDetails;
	}

	/**
	 * Gets the global ultimate parent details.
	 *
	 * @return the global ultimate parent details
	 */
	public String getGlobalUltimateParentDetails() {
		return globalUltimateParentDetails;
	}

	/**
	 * Sets the global ultimate parent details.
	 *
	 * @param globalUltimateParentDetails the new global ultimate parent details
	 */
	public void setGlobalUltimateParentDetails(String globalUltimateParentDetails) {
		this.globalUltimateParentDetails = globalUltimateParentDetails;
	}

	/**
	 * Gets the credit limit.
	 *
	 * @return the credit limit
	 */
	public String getCreditLimit() {
		return creditLimit;
	}

	/**
	 * Sets the credit limit.
	 *
	 * @param creditLimit the new credit limit
	 */
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	/**
	 * Gets the risk rating.
	 *
	 * @return the risk rating
	 */
	public String getRiskRating() {
		return riskRating;
	}

	/**
	 * Sets the risk rating.
	 *
	 * @param riskRating the new risk rating
	 */
	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}

	/**
	 * Gets the profit loss.
	 *
	 * @return the profit loss
	 */
	public String getProfitLoss() {
		return profitLoss;
	}

	/**
	 * Sets the profit loss.
	 *
	 * @param profitLoss the new profit loss
	 */
	public void setProfitLoss(String profitLoss) {
		this.profitLoss = profitLoss;
	}

}
