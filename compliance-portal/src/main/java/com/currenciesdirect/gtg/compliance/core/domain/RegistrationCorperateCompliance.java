/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class RegistrationCorperateCompliance.
 *
 * @author manish
 */
public class RegistrationCorperateCompliance {

	/** The sic. */
	@JsonProperty(value = "sic")
	private String  sic;
	
	/** The sic desc. */
	@JsonProperty(value = "sic_desc")
	private String  sicDesc;
	
	/** The registration number. */
	@JsonProperty(value = "registration_number")
	private String  registrationNumber;
	
	/** The former name. */
	@JsonProperty(value = "former_name")
	private String  formerName;
	
	/** The legal form. */
	@JsonProperty(value = "legal_form")
	private String  legalForm;
	
	/** The foreign owned company. */
	@JsonProperty(value = "foreign_owned_company")
	private String  foreignOwnedCompany;
	
	/** The net worth. */
	@JsonProperty(value = "net_worth")
	private String  netWorth;
	
	/** The fixed assets. */
	@JsonProperty(value = "fixed_assets")
	private String  fixedAssets;
	
	/** The total liabilities and equities. */
	@JsonProperty(value = "total_liabilities_and_equities")
	private String  totalLiabilitiesAndEquities;
	
	/** The total share holders. */
	@JsonProperty(value = "total_share_holders")
	private String  totalShareHolders;
	
	/************************NEWLY ADDED DNB DATA*****************************************************/
	
	@JsonProperty(value = "global_ultimate_DUNS")
	private String globalUltimateDuns;
	@JsonProperty(value = "global_ultimate_name")
	private String globalUltimateName;
	@JsonProperty(value = "global_ultimate_country")
	private String globalUltimateCountry;
	@JsonProperty(value = "registration_date")
	private String registrationDate;
	@JsonProperty(value = "match_name")
	private String matchName;
	@JsonProperty(value = "iso_country_code_2_digit")
	private String isoCountryCode2Digit;
	@JsonProperty(value = "iso_country_code_3_digit")
	private String isoCountryCode3Digit;
	@JsonProperty(value = "statement_date")
	private String statementDate;
	@JsonProperty(value = "gross_income")
	private String grossIncome;
	@JsonProperty(value = "net_income")
	private String netIncome;
	@JsonProperty(value = "total_current_assets")
	private String totalCurrentAssets;
	@JsonProperty(value = "total_assets")
	private String totalAssets;
	@JsonProperty(value = "total_long_term_liabilities")
	private String totalLongTermLiabilities;
	@JsonProperty(value = "total_current_liabilities")
	private String totalCurrentLiabilities;
	@JsonProperty(value = "total_matched_shareholders")
	private String totalMatchedShareholders;
	@JsonProperty(value = "financial_strength")
	private String financialStrength;
	//newly added DNB fields ends here 
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixedAssets == null) ? 0 : fixedAssets.hashCode());
		result = prime * result + ((foreignOwnedCompany == null) ? 0 : foreignOwnedCompany.hashCode());
		result = prime * result + ((formerName == null) ? 0 : formerName.hashCode());
		result = prime * result + ((legalForm == null) ? 0 : legalForm.hashCode());
		result = prime * result + ((netWorth == null) ? 0 : netWorth.hashCode());
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		result = prime * result + ((sic == null) ? 0 : sic.hashCode());
		result = prime * result + ((sicDesc == null) ? 0 : sicDesc.hashCode());
		result = prime * result + ((totalLiabilitiesAndEquities == null) ? 0 : totalLiabilitiesAndEquities.hashCode());
		result = prime * result + ((totalShareHolders == null) ? 0 : totalShareHolders.hashCode());
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
		RegistrationCorperateCompliance other = (RegistrationCorperateCompliance) obj;
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
		if (legalForm == null) {
			if (other.legalForm != null)
				return false;
		} else if (!legalForm.equals(other.legalForm)) {
			return false;
		  }
		if (netWorth == null) {
			if (other.netWorth != null)
				return false;
		} else if (!netWorth.equals(other.netWorth)) {
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
		if (totalLiabilitiesAndEquities == null) {
			if (other.totalLiabilitiesAndEquities != null)
				return false;
		} else if (!totalLiabilitiesAndEquities.equals(other.totalLiabilitiesAndEquities)) {
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegistrationCorperateCompliance [sic=" + sic + ", sicDesc=" + sicDesc + ", registrationNumber="
				+ registrationNumber + ", formerName=" + formerName + ", legalForm=" + legalForm
				+ ", foreignOwnedCompany=" + foreignOwnedCompany + ", netWorth=" + netWorth + ", fixedAssets="
				+ fixedAssets + ", totalLiabilitiesAndEquities=" + totalLiabilitiesAndEquities + ", totalShareHolders="
				+ totalShareHolders + "]";
	}

	
}
