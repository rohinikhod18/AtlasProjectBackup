/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CorperateCompliance.
 *
 * @author manish
 */
public class CorperateCompliance implements Serializable {

	/** The Constant serialVersionUID. */
	@ApiModelProperty(value = "The Constant serialVersionUID", required = true)
	private static final long serialVersionUID = 1L;	
	
	/** The sic. */	
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
	@ApiModelProperty(value = "The statement date", required = true)
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
	@ApiModelProperty(value = "The financial strength", required = true)
	@JsonProperty(value = "financial_strength")
	private String financialStrength;
	
	/**
	 * Gets the sic.
	 *
	 * @return the sic
	 */
	public String getSic() {
		return sic;
	}
	
	/**
	 * Sets the sic.
	 *
	 * @param sic the new sic
	 */
	public void setSic(String sic) {
		this.sic = sic;
	}
	
	/**
	 * Gets the former name.
	 *
	 * @return the former name
	 */
	public String getFormerName() {
		return formerName;
	}
	
	/**
	 * Sets the former name.
	 *
	 * @param formerName the new former name
	 */
	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}
	
	/**
	 * Gets the legal form.
	 *
	 * @return the legal form
	 */
	public String getLegalForm() {
		return legalForm;
	}
	
	/**
	 * Sets the legal form.
	 *
	 * @param legalForm the new legal form
	 */
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}
	
	/**
	 * Gets the foreign owned company.
	 *
	 * @return the foreign owned company
	 */
	public String getForeignOwnedCompany() {
		return foreignOwnedCompany;
	}
	
	/**
	 * Sets the foreign owned company.
	 *
	 * @param foreignOwnedCompany the new foreign owned company
	 */
	public void setForeignOwnedCompany(String foreignOwnedCompany) {
		this.foreignOwnedCompany = foreignOwnedCompany;
	}
	
	/**
	 * Gets the net worth.
	 *
	 * @return the net worth
	 */
	public String getNetWorth() {
		return netWorth;
	}
	
	/**
	 * Sets the net worth.
	 *
	 * @param netWorth the new net worth
	 */
	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}
	
	/**
	 * Gets the fixed assets.
	 *
	 * @return the fixed assets
	 */
	public String getFixedAssets() {
		return fixedAssets;
	}
	
	/**
	 * Sets the fixed assets.
	 *
	 * @param fixedAssets the new fixed assets
	 */
	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}
	
	/**
	 * Gets the total liabilities and equities.
	 *
	 * @return the total liabilities and equities
	 */
	public String getTotalLiabilitiesAndEquities() {
		return totalLiabilitiesAndEquities;
	}
	
	/**
	 * Sets the total liabilities and equities.
	 *
	 * @param totalLiabilitiesAndEquities the new total liabilities and equities
	 */
	public void setTotalLiabilitiesAndEquities(String totalLiabilitiesAndEquities) {
		this.totalLiabilitiesAndEquities = totalLiabilitiesAndEquities;
	}
	
	/**
	 * Gets the total share holders.
	 *
	 * @return the total share holders
	 */
	public String getTotalShareHolders() {
		return totalShareHolders;
	}
	
	/**
	 * Sets the total share holders.
	 *
	 * @param totalShareHolders the new total share holders
	 */
	public void setTotalShareHolders(String totalShareHolders) {
		this.totalShareHolders = totalShareHolders;
	}
	
	/**
	 * Gets the global ultimate duns.
	 *
	 * @return the global ultimate duns
	 */
	public String getGlobalUltimateDuns() {
		return globalUltimateDuns;
	}
	
	/**
	 * Sets the global ultimate duns.
	 *
	 * @param globalUltimateDuns the new global ultimate duns
	 */
	public void setGlobalUltimateDuns(String globalUltimateDuns) {
		this.globalUltimateDuns = globalUltimateDuns;
	}
	
	/**
	 * Gets the global ultimate name.
	 *
	 * @return the global ultimate name
	 */
	public String getGlobalUltimateName() {
		return globalUltimateName;
	}
	
	/**
	 * Sets the global ultimate name.
	 *
	 * @param globalUltimateName the new global ultimate name
	 */
	public void setGlobalUltimateName(String globalUltimateName) {
		this.globalUltimateName = globalUltimateName;
	}
	
	/**
	 * Gets the global ultimate country.
	 *
	 * @return the global ultimate country
	 */
	public String getGlobalUltimateCountry() {
		return globalUltimateCountry;
	}
	
	/**
	 * Sets the global ultimate country.
	 *
	 * @param globalUltimateCountry the new global ultimate country
	 */
	public void setGlobalUltimateCountry(String globalUltimateCountry) {
		this.globalUltimateCountry = globalUltimateCountry;
	}
	
	/**
	 * Gets the registration date.
	 *
	 * @return the registration date
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}
	
	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	/**
	 * Gets the match name.
	 *
	 * @return the match name
	 */
	public String getMatchName() {
		return matchName;
	}
	
	/**
	 * Sets the match name.
	 *
	 * @param matchName the new match name
	 */
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	
	/**
	 * Gets the iso country code 2 digit.
	 *
	 * @return the iso country code 2 digit
	 */
	public String getIsoCountryCode2Digit() {
		return isoCountryCode2Digit;
	}
	
	/**
	 * Sets the iso country code 2 digit.
	 *
	 * @param isoCountryCode2Digit the new iso country code 2 digit
	 */
	public void setIsoCountryCode2Digit(String isoCountryCode2Digit) {
		this.isoCountryCode2Digit = isoCountryCode2Digit;
	}
	
	/**
	 * Gets the iso country code 3 digit.
	 *
	 * @return the iso country code 3 digit
	 */
	public String getIsoCountryCode3Digit() {
		return isoCountryCode3Digit;
	}
	
	/**
	 * Sets the iso country code 3 digit.
	 *
	 * @param isoCountryCode3Digit the new iso country code 3 digit
	 */
	public void setIsoCountryCode3Digit(String isoCountryCode3Digit) {
		this.isoCountryCode3Digit = isoCountryCode3Digit;
	}
	
	/**
	 * Gets the statement date.
	 *
	 * @return the statement date
	 */
	public String getStatementDate() {
		return statementDate;
	}
	
	/**
	 * Sets the statement date.
	 *
	 * @param statementDate the new statement date
	 */
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}
	
	/**
	 * Gets the gross income.
	 *
	 * @return the gross income
	 */
	public String getGrossIncome() {
		return grossIncome;
	}
	
	/**
	 * Sets the gross income.
	 *
	 * @param grossIncome the new gross income
	 */
	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}
	
	/**
	 * Gets the net income.
	 *
	 * @return the net income
	 */
	public String getNetIncome() {
		return netIncome;
	}
	
	/**
	 * Sets the net income.
	 *
	 * @param netIncome the new net income
	 */
	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}
	
	/**
	 * Gets the total current assets.
	 *
	 * @return the total current assets
	 */
	public String getTotalCurrentAssets() {
		return totalCurrentAssets;
	}
	
	/**
	 * Sets the total current assets.
	 *
	 * @param totalCurrentAssets the new total current assets
	 */
	public void setTotalCurrentAssets(String totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}
	
	/**
	 * Gets the total assets.
	 *
	 * @return the total assets
	 */
	public String getTotalAssets() {
		return totalAssets;
	}
	
	/**
	 * Sets the total assets.
	 *
	 * @param totalAssets the new total assets
	 */
	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}
	
	/**
	 * Gets the total long term liabilities.
	 *
	 * @return the total long term liabilities
	 */
	public String getTotalLongTermLiabilities() {
		return totalLongTermLiabilities;
	}
	
	/**
	 * Sets the total long term liabilities.
	 *
	 * @param totalLongTermLiabilities the new total long term liabilities
	 */
	public void setTotalLongTermLiabilities(String totalLongTermLiabilities) {
		this.totalLongTermLiabilities = totalLongTermLiabilities;
	}
	
	/**
	 * Gets the total current liabilities.
	 *
	 * @return the total current liabilities
	 */
	public String getTotalCurrentLiabilities() {
		return totalCurrentLiabilities;
	}
	
	/**
	 * Sets the total current liabilities.
	 *
	 * @param totalCurrentLiabilities the new total current liabilities
	 */
	public void setTotalCurrentLiabilities(String totalCurrentLiabilities) {
		this.totalCurrentLiabilities = totalCurrentLiabilities;
	}
	
	/**
	 * Gets the total matched shareholders.
	 *
	 * @return the total matched shareholders
	 */
	public String getTotalMatchedShareholders() {
		return totalMatchedShareholders;
	}
	
	/**
	 * Sets the total matched shareholders.
	 *
	 * @param totalMatchedShareholders the new total matched shareholders
	 */
	public void setTotalMatchedShareholders(String totalMatchedShareholders) {
		this.totalMatchedShareholders = totalMatchedShareholders;
	}
	
	/**
	 * Gets the financial strength.
	 *
	 * @return the financial strength
	 */
	public String getFinancialStrength() {
		return financialStrength;
	}
	
	/**
	 * Sets the financial strength.
	 *
	 * @param financialStrength the new financial strength
	 */
	public void setFinancialStrength(String financialStrength) {
		this.financialStrength = financialStrength;
	}
	
	/**
	 * Gets the sic desc.
	 *
	 * @return the sic desc
	 */
	public String getSicDesc() {
		return sicDesc;
	}
	
	/**
	 * Sets the sic desc.
	 *
	 * @param sicDesc the new sic desc
	 */
	public void setSicDesc(String sicDesc) {
		this.sicDesc = sicDesc;
	}
	
	/**
	 * Gets the registration number.
	 *
	 * @return the registration number
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	
	/**
	 * Sets the registration number.
	 *
	 * @param registrationNumber the new registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

}