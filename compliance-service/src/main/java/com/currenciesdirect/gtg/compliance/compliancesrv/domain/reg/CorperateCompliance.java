/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author manish
 *
 */
public class CorperateCompliance implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The Standard Industrial Classification code for the company's economic activities", example = "47910", required = true)
	@JsonProperty(value = "sic")
	@FieldDisplayName(displayName = "SIC")
	private String  sic;
	
	/** The sic desc. */
	@ApiModelProperty(value = "The Standard Industrial Classification description for the company's economic activities", example = "Retail sale via mail order houses or via Internet", required = true)
	@JsonProperty(value = "sic_desc")
	@FieldDisplayName(displayName = "SIC Desc")
	private String  sicDesc;
	
	/** The registration number. */
	@ApiModelProperty(value = "The registration number", required = true)
	@JsonProperty(value = "registration_number")
	@FieldDisplayName(displayName = "Registration Number")
	private String  registrationNumber;
	
	/** The former name. */
	@ApiModelProperty(value = "The former name of the company", example = "", required = true)
	@JsonProperty(value = "former_name")
	@FieldDisplayName(displayName = "Former Name")
	private String  formerName;
	
	/** The legal form. */
	@ApiModelProperty(value = "The legal form", required = true)
	@JsonProperty(value = "legal_form")
	@FieldDisplayName(displayName = "Legal Form")
	private String  legalForm;
	
	/** The foreign owned company. */
	@ApiModelProperty(value = "Whether this company is foreign owned (true or false)", example = "false", required = true)
	@JsonProperty(value = "foreign_owned_company")
	@FieldDisplayName(displayName = "Foreign Owned Company")
	private String  foreignOwnedCompany;
	
	/** The net worth. */
	@ApiModelProperty(value = "The net worth of the company", example = "100000", required = true)
	@JsonProperty(value = "net_worth")
	@FieldDisplayName(displayName = "Net Worth")
	private String  netWorth;
	
	/** The fixed assets. */
	@ApiModelProperty(value = "The fixed assets", required = true)
	@JsonProperty(value = "fixed_assets")
	@FieldDisplayName(displayName = "Fixed Assets")
	private String  fixedAssets;
	
	/** The total liabilities and equities. */
	@ApiModelProperty(value = "The total liabilities and equities", example = "290,206", required = true)
	@JsonProperty(value = "total_liabilities_and_equities")
	@FieldDisplayName(displayName = "Total Liabilities And Equities")
	private String  totalLiabilitiesAndEquities;
	
	/** The total share holders. */
	@ApiModelProperty(value = "The number of share holders", example = "1", required = true)
	@JsonProperty(value = "total_share_holders")
	@FieldDisplayName(displayName = "Total Share Holders")
	private String  totalShareHolders;
	/* 
	 * Newly added DNBfields
	 */
	/** The global ultimate duns. */
	@ApiModelProperty(value = "The global ultimate DUNS number (Data Universal Numbering System)", example = "218218283", required = true)
	@JsonProperty(value = "global_ultimate_DUNS")
	@FieldDisplayName(displayName = "Global Ultimate DUNS")
	private String globalUltimateDuns;
	
	/** The global ultimate name. */
	@ApiModelProperty(value = "The DUNS global ultimate name", required = true)
	@JsonProperty(value = "global_ultimate_name")
	@FieldDisplayName(displayName = "Global Ultimate Name")
	private String globalUltimateName;
	
	/** The global ultimate country. */
	@ApiModelProperty(value = "The global ultimate country", required = true)
	@JsonProperty(value = "global_ultimate_country")
	@FieldDisplayName(displayName = "Global Ultimate Country")
	private String globalUltimateCountry;
	
	/** The registration date. */
	@ApiModelProperty(value = "The registration date", required = true)
	@JsonProperty(value = "registration_date")
	@FieldDisplayName(displayName = "Registration Date")
	private String registrationDate;
	
	/** The match name. */
	@ApiModelProperty(value = "The match name is the company name used in D&B", required = true)
	@JsonProperty(value = "match_name")
	@FieldDisplayName(displayName = "Match Name")
	private String matchName;
	
	/** The iso country code 2 digit. */
	@ApiModelProperty(value = "The iso country code 2 digit", required = true)
	@JsonProperty(value = "iso_country_code_2_digit")
	@FieldDisplayName(displayName = "Iso Country Code 2 Digit")
	private String isoCountryCode2Digit;
	
	/** The iso country code 3 digit. */
	@ApiModelProperty(value = "The iso country code 3 digit", required = true)
	@JsonProperty(value = "iso_country_code_3_digit")
	@FieldDisplayName(displayName = "Iso Country Code 3 Digit")
	private String isoCountryCode3Digit;
	
	/** The statement date. */
	@ApiModelProperty(value = "The D&B statement date in format yyyymmdd", example = "20170630", required = true)
	@JsonProperty(value = "statement_date")
	@FieldDisplayName(displayName = "Statement Date")
	private String statementDate;
	
	/** The gross income. */
	@ApiModelProperty(value = "The gross income", required = true)
	@JsonProperty(value = "gross_income")
	@FieldDisplayName(displayName = "Gross Income")
	private String grossIncome;
	
	/** The net income. */
	@ApiModelProperty(value = "The net income", required = true)
	@JsonProperty(value = "net_income")
	@FieldDisplayName(displayName = "Net Income")
	private String netIncome;
	
	/** The total current assets. */
	@ApiModelProperty(value = "The total current assets", required = true)
	@JsonProperty(value = "total_current_assets")
	@FieldDisplayName(displayName = "Total Current Assets")
	private String totalCurrentAssets;
	
	/** The total assets. */
	@ApiModelProperty(value = "The total assets", required = true)
	@JsonProperty(value = "total_assets")
	@FieldDisplayName(displayName = "Total Assets")
	private String totalAssets;
	
	/** The total long term liabilities. */
	@ApiModelProperty(value = "The total long term liabilities", required = true)
	@JsonProperty(value = "total_long_term_liabilities")
	@FieldDisplayName(displayName = "Total Long Term Liabilities")
	private String totalLongTermLiabilities;
	
	/** The total current liabilities. */
	@ApiModelProperty(value = "The total current liabilities", required = true)
	@JsonProperty(value = "total_current_liabilities")
	@FieldDisplayName(displayName = "Total Current Liabilities")
	private String totalCurrentLiabilities;
	
	/** The total matched shareholders. */
	@ApiModelProperty(value = "The total matched shareholders", required = true)
	@JsonProperty(value = "total_matched_shareholders")
	@FieldDisplayName(displayName = "Total Matched Shareholders")
	private String totalMatchedShareholders;
	
	/** The financial strength. */
	@ApiModelProperty(value = "The financial strength rating", example = "A2", required = true)
	@JsonProperty(value = "financial_strength")
	@FieldDisplayName(displayName = "Financial Strength")
	private String financialStrength;
	
	
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
	
	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((financialStrength == null) ? 0 : financialStrength.hashCode());
		result = prime * result + ((fixedAssets == null) ? 0 : fixedAssets.hashCode());
		result = prime * result + ((foreignOwnedCompany == null) ? 0 : foreignOwnedCompany.hashCode());
		result = prime * result + ((formerName == null) ? 0 : formerName.hashCode());
		result = prime * result + ((globalUltimateCountry == null) ? 0 : globalUltimateCountry.hashCode());
		result = prime * result + ((globalUltimateDuns == null) ? 0 : globalUltimateDuns.hashCode());
		result = prime * result + ((globalUltimateName == null) ? 0 : globalUltimateName.hashCode());
		result = prime * result + ((grossIncome == null) ? 0 : grossIncome.hashCode());
		result = prime * result + ((isoCountryCode2Digit == null) ? 0 : isoCountryCode2Digit.hashCode());
		result = prime * result + ((isoCountryCode3Digit == null) ? 0 : isoCountryCode3Digit.hashCode());
		result = prime * result + ((legalForm == null) ? 0 : legalForm.hashCode());
		result = prime * result + ((matchName == null) ? 0 : matchName.hashCode());
		result = prime * result + ((netIncome == null) ? 0 : netIncome.hashCode());
		result = prime * result + ((netWorth == null) ? 0 : netWorth.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		result = prime * result + ((sic == null) ? 0 : sic.hashCode());
		result = prime * result + ((sicDesc == null) ? 0 : sicDesc.hashCode());
		result = prime * result + ((statementDate == null) ? 0 : statementDate.hashCode());
		result = prime * result + ((totalAssets == null) ? 0 : totalAssets.hashCode());
		result = prime * result + ((totalCurrentAssets == null) ? 0 : totalCurrentAssets.hashCode());
		result = prime * result + ((totalCurrentLiabilities == null) ? 0 : totalCurrentLiabilities.hashCode());
		result = prime * result + ((totalLiabilitiesAndEquities == null) ? 0 : totalLiabilitiesAndEquities.hashCode());
		result = prime * result + ((totalLongTermLiabilities == null) ? 0 : totalLongTermLiabilities.hashCode());
		result = prime * result + ((totalMatchedShareholders == null) ? 0 : totalMatchedShareholders.hashCode());
		result = prime * result + ((totalShareHolders == null) ? 0 : totalShareHolders.hashCode());
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
		CorperateCompliance other = (CorperateCompliance) obj;
		if (financialStrength == null) {
			if (other.financialStrength != null)
				return false;
		} else if (!financialStrength.equals(other.financialStrength)) {
			return false;
		  }
		if (fixedAssets == null) {
			if (other.fixedAssets != null)
				return false;
		} else if (!fixedAssets.equals(other.fixedAssets)) {
			return false;
		  }
		if (foreignOwnedCompany == null) {
			if (other.foreignOwnedCompany != null)
				return false;
		} else if (!foreignOwnedCompany.equals(other.foreignOwnedCompany)) {
			return false;
		  }
		if (formerName == null) {
			if (other.formerName != null)
				return false;
		} else if (!formerName.equals(other.formerName)) {
			return false;
		  }
		if (globalUltimateCountry == null) {
			if (other.globalUltimateCountry != null)
				return false;
		} else if (!globalUltimateCountry.equals(other.globalUltimateCountry)) {
			return false;
		  }
		if (globalUltimateDuns == null) {
			if (other.globalUltimateDuns != null)
				return false;
		} else if (!globalUltimateDuns.equals(other.globalUltimateDuns)) {
			return false;
		  }
		if (globalUltimateName == null) {
			if (other.globalUltimateName != null)
				return false;
		} else if (!globalUltimateName.equals(other.globalUltimateName)) {
			return false;
		  }
		if (grossIncome == null) {
			if (other.grossIncome != null)
				return false;
		} else if (!grossIncome.equals(other.grossIncome)) {
			return false;
		  }
		if (isoCountryCode2Digit == null) {
			if (other.isoCountryCode2Digit != null)
				return false;
		} else if (!isoCountryCode2Digit.equals(other.isoCountryCode2Digit)) {
			return false;
		  }
		if (isoCountryCode3Digit == null) {
			if (other.isoCountryCode3Digit != null)
				return false;
		} else if (!isoCountryCode3Digit.equals(other.isoCountryCode3Digit)) {
			return false;
		  }
		if (legalForm == null) {
			if (other.legalForm != null)
				return false;
		} else if (!legalForm.equals(other.legalForm)) {
			return false;
		  }
		if (matchName == null) {
			if (other.matchName != null)
				return false;
		} else if (!matchName.equals(other.matchName)) {
			return false;
		  }
		if (netIncome == null) {
			if (other.netIncome != null)
				return false;
		} else if (!netIncome.equals(other.netIncome)) {
			return false;
		  }
		if (netWorth == null) {
			if (other.netWorth != null)
				return false;
		} else if (!netWorth.equals(other.netWorth)) {
			return false;
		  }
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate)) {
			return false;
		  }
		if (registrationNumber == null) {
			if (other.registrationNumber != null)
				return false;
		} else if (!registrationNumber.equals(other.registrationNumber)) {
			return false;
		  }
		if (sic == null) {
			if (other.sic != null)
				return false;
		} else if (!sic.equals(other.sic)) {
			return false;
		  }
		if (sicDesc == null) {
			if (other.sicDesc != null)
				return false;
		} else if (!sicDesc.equals(other.sicDesc)) {
			return false;
		  }
		if (statementDate == null) {
			if (other.statementDate != null)
				return false;
		} else if (!statementDate.equals(other.statementDate)) {
			return false;
		  }
		if (totalAssets == null) {
			if (other.totalAssets != null)
				return false;
		} else if (!totalAssets.equals(other.totalAssets)) {
			return false;
		  }
		if (totalCurrentAssets == null) {
			if (other.totalCurrentAssets != null)
				return false;
		} else if (!totalCurrentAssets.equals(other.totalCurrentAssets)) {
			return false;
		  }
		if (totalCurrentLiabilities == null) {
			if (other.totalCurrentLiabilities != null)
				return false;
		} else if (!totalCurrentLiabilities.equals(other.totalCurrentLiabilities)) {
			return false;
		  }
		if (totalLiabilitiesAndEquities == null) {
			if (other.totalLiabilitiesAndEquities != null)
				return false;
		} else if (!totalLiabilitiesAndEquities.equals(other.totalLiabilitiesAndEquities)) {
			return false;
		  }	
		if (totalLongTermLiabilities == null) {
			if (other.totalLongTermLiabilities != null)
				return false;
		} else if (!totalLongTermLiabilities.equals(other.totalLongTermLiabilities)) {
			return false;
		  }
		if (totalMatchedShareholders == null) {
			if (other.totalMatchedShareholders != null)
				return false;
		} else if (!totalMatchedShareholders.equals(other.totalMatchedShareholders)) {
			return false;
		  }
		if (totalShareHolders == null) {
			if (other.totalShareHolders != null)
				return false;
		} else if (!totalShareHolders.equals(other.totalShareHolders)) {
			return false;
		  }
		return true;
	}
	@Override
	public String toString() {
		return "CorperateCompliance [sic=" + sic + ", sicDesc=" + sicDesc + ", registrationNumber=" + registrationNumber
				+ ", formerName=" + formerName + ", legalForm=" + legalForm + ", foreignOwnedCompany="
				+ foreignOwnedCompany + ", netWorth=" + netWorth + ", fixedAssets=" + fixedAssets
				+ ", totalLiabilitiesAndEquities=" + totalLiabilitiesAndEquities + ", totalShareHolders="
				+ totalShareHolders + ", globalUltimateDuns=" + globalUltimateDuns + ", globalUltimateName="
				+ globalUltimateName + ", globalUltimateCountry=" + globalUltimateCountry + ", registrationDate="
				+ registrationDate + ", matchName=" + matchName + ", isoCountryCode2Digit=" + isoCountryCode2Digit
				+ ", isoCountryCode3Digit=" + isoCountryCode3Digit + ", statementDate=" + statementDate
				+ ", grossIncome=" + grossIncome + ", netIncome=" + netIncome + ", totalCurrentAssets="
				+ totalCurrentAssets + ", totalAssets=" + totalAssets + ", totalLongTermLiabilities="
				+ totalLongTermLiabilities + ", totalCurrentLiabilities=" + totalCurrentLiabilities
				+ ", totalMatchedShareholders=" + totalMatchedShareholders + ", financialStrength=" + financialStrength
				+ "]";
	}
	
}
