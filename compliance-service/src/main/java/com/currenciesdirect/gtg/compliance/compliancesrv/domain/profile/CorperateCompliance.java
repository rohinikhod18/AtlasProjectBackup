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
public class CorperateCompliance implements Serializable {

	@ApiModelProperty(value = "The Constant serialVersionUID", required = true)
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The Standard Industrial Classification code for the company's economic activities", example = "47910", required = true)
	@JsonProperty(value = "sic")
	private String  sic;
	
	/** The sic desc. */
	@ApiModelProperty(value = "The Standard Industrial Classification description for the company's economic activities", example = "Retail sale via mail order houses or via Internet", required = true)
	@JsonProperty(value = "sic_desc")
	private String  sicDesc;
	
	/** The registration number. */
	@ApiModelProperty(value = "The registration number", required = true)
	@JsonProperty(value = "registration_number")
	private String  registrationNumber;
	
	/** The former name. */
	@ApiModelProperty(value = "The former name of the company", example = "", required = true)
	@JsonProperty(value = "former_name")
	private String  formerName;
	
	/** The legal form. */
	@ApiModelProperty(value = "The legal form", required = true)
	@JsonProperty(value = "legal_form")
	private String  legalForm;
	
	/** The foreign owned company. */
	@ApiModelProperty(value = "Whether this company is foreign owned (true or false)", example = "false", required = true)
	@JsonProperty(value = "foreign_owned_company")
	private String  foreignOwnedCompany;
	
	/** The net worth. */
	@ApiModelProperty(value = "The net worth of the company", example = "100000", required = true)
	@JsonProperty(value = "net_worth")
	private String  netWorth;
	
	/** The fixed assets. */
	@ApiModelProperty(value = "The fixed assets", required = true)
	@JsonProperty(value = "fixed_assets")
	private String  fixedAssets;
	
	/** The total liabilities and equities. */
	@ApiModelProperty(value = "The total liabilities and equities", example = "290,206", required = true)
	@JsonProperty(value = "total_liabilities_and_equities")
	private String  totalLiabilitiesAndEquities;
	
	/** The total share holders. */
	@ApiModelProperty(value = "The number of share holders", example = "1", required = true)
	@JsonProperty(value = "total_share_holders")
	private String  totalShareHolders;
	/* 
	 * Newly added DNBfields
	 */
	/** The global ultimate duns. */
	@ApiModelProperty(value = "The global ultimate DUNS number (Data Universal Numbering System)", example = "218218283", required = true)
	@JsonProperty(value = "global_ultimate_DUNS")
	private String globalUltimateDuns;
	
	/** The global ultimate name. */
	@ApiModelProperty(value = "The DUNS global ultimate name", required = true)
	@JsonProperty(value = "global_ultimate_name")
	private String globalUltimateName;
	
	/** The global ultimate country. */
	@ApiModelProperty(value = "The global ultimate country", required = true)
	@JsonProperty(value = "global_ultimate_country")
	private String globalUltimateCountry;
	
	/** The registration date. */
	@ApiModelProperty(value = "The registration date", required = true)
	@JsonProperty(value = "registration_date")
	private String registrationDate;
	
	/** The match name. */
	@ApiModelProperty(value = "The match name is the company name used in D&B", required = true)
	@JsonProperty(value = "match_name")
	private String matchName;
	
	/** The iso country code 2 digit. */
	@ApiModelProperty(value = "The iso country code 2 digit", required = true)
	@JsonProperty(value = "iso_country_code_2_digit")
	private String isoCountryCode2Digit;
	
	/** The iso country code 3 digit. */
	@ApiModelProperty(value = "The iso country code 3 digit", required = true)
	@JsonProperty(value = "iso_country_code_3_digit")
	private String isoCountryCode3Digit;
	
	/** The statement date. */
	@ApiModelProperty(value = "The D&B statement date in format yyyymmdd", example = "20170630", required = true)
	@JsonProperty(value = "statement_date")
	private String statementDate;
	
	/** The gross income. */
	@ApiModelProperty(value = "The gross income", required = true)
	@JsonProperty(value = "gross_income")
	private String grossIncome;
	
	/** The net income. */
	@ApiModelProperty(value = "The net income", required = true)
	@JsonProperty(value = "net_income")
	private String netIncome;
	
	/** The total current assets. */
	@ApiModelProperty(value = "The total current assets", required = true)
	@JsonProperty(value = "total_current_assets")
	private String totalCurrentAssets;
	
	/** The total assets. */
	@ApiModelProperty(value = "The total assets", required = true)
	@JsonProperty(value = "total_assets")
	private String totalAssets;
	
	/** The total long term liabilities. */
	@ApiModelProperty(value = "The total long term liabilities", required = true)
	@JsonProperty(value = "total_long_term_liabilities")
	private String totalLongTermLiabilities;
	
	/** The total current liabilities. */
	@ApiModelProperty(value = "The total current liabilities", required = true)
	@JsonProperty(value = "total_current_liabilities")
	private String totalCurrentLiabilities;
	
	/** The total matched shareholders. */
	@ApiModelProperty(value = "The total matched shareholders", required = true)
	@JsonProperty(value = "total_matched_shareholders")
	private String totalMatchedShareholders;
	
	/** The financial strength. */
	@ApiModelProperty(value = "The financial strength rating", example = "A2", required = true)
	@JsonProperty(value = "financial_strength")
	private String financialStrength;
	
	public String getSic() {
		return sic;
	}
	public void setSic(String sic) {
		this.sic = sic;
	}
	public String getFormerName() {
		return formerName;
	}
	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}
	public String getLegalForm() {
		return legalForm;
	}
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}
	public String getForeignOwnedCompany() {
		return foreignOwnedCompany;
	}
	public void setForeignOwnedCompany(String foreignOwnedCompany) {
		this.foreignOwnedCompany = foreignOwnedCompany;
	}
	public String getNetWorth() {
		return netWorth;
	}
	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}
	public String getFixedAssets() {
		return fixedAssets;
	}
	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}
	public String getTotalLiabilitiesAndEquities() {
		return totalLiabilitiesAndEquities;
	}
	public void setTotalLiabilitiesAndEquities(String totalLiabilitiesAndEquities) {
		this.totalLiabilitiesAndEquities = totalLiabilitiesAndEquities;
	}
	public String getTotalShareHolders() {
		return totalShareHolders;
	}
	public void setTotalShareHolders(String totalShareHolders) {
		this.totalShareHolders = totalShareHolders;
	}
	public String getGlobalUltimateDuns() {
		return globalUltimateDuns;
	}
	public void setGlobalUltimateDuns(String globalUltimateDuns) {
		this.globalUltimateDuns = globalUltimateDuns;
	}
	public String getGlobalUltimateName() {
		return globalUltimateName;
	}
	public void setGlobalUltimateName(String globalUltimateName) {
		this.globalUltimateName = globalUltimateName;
	}
	public String getGlobalUltimateCountry() {
		return globalUltimateCountry;
	}
	public void setGlobalUltimateCountry(String globalUltimateCountry) {
		this.globalUltimateCountry = globalUltimateCountry;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getIsoCountryCode2Digit() {
		return isoCountryCode2Digit;
	}
	public void setIsoCountryCode2Digit(String isoCountryCode2Digit) {
		this.isoCountryCode2Digit = isoCountryCode2Digit;
	}
	public String getIsoCountryCode3Digit() {
		return isoCountryCode3Digit;
	}
	public void setIsoCountryCode3Digit(String isoCountryCode3Digit) {
		this.isoCountryCode3Digit = isoCountryCode3Digit;
	}
	public String getStatementDate() {
		return statementDate;
	}
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}
	public String getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}
	public String getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}
	public String getTotalCurrentAssets() {
		return totalCurrentAssets;
	}
	public void setTotalCurrentAssets(String totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}
	public String getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}
	public String getTotalLongTermLiabilities() {
		return totalLongTermLiabilities;
	}
	public void setTotalLongTermLiabilities(String totalLongTermLiabilities) {
		this.totalLongTermLiabilities = totalLongTermLiabilities;
	}
	public String getTotalCurrentLiabilities() {
		return totalCurrentLiabilities;
	}
	public void setTotalCurrentLiabilities(String totalCurrentLiabilities) {
		this.totalCurrentLiabilities = totalCurrentLiabilities;
	}
	public String getTotalMatchedShareholders() {
		return totalMatchedShareholders;
	}
	public void setTotalMatchedShareholders(String totalMatchedShareholders) {
		this.totalMatchedShareholders = totalMatchedShareholders;
	}
	public String getFinancialStrength() {
		return financialStrength;
	}
	public void setFinancialStrength(String financialStrength) {
		this.financialStrength = financialStrength;
	}
	public String getSicDesc() {
		return sicDesc;
	}
	public void setSicDesc(String sicDesc) {
		this.sicDesc = sicDesc;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

}
