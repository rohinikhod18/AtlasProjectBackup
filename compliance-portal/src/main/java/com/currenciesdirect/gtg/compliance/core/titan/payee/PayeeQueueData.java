package com.currenciesdirect.gtg.compliance.core.titan.payee;

/**
 * The Class BeneficiaryQueueData.
 */
public class PayeeQueueData {
	
	/** The payee id. */
	private Integer payeeId; 

	/** The last paid. */
	private String lastPaid;
	
	/** The organization. */
	private String organization;
	
	/** The beneficiary name. */
	private String beneficiaryName;
	
	/** The third party. */
	private String thirdParty;
	
	/** The bene account number. */
	private String beneAccountNumber;
	
	/** The swift code. */
	private String swiftCode;
	
	/** The currency. */
	private String currency;
	
	/** The country. */
	private String country;
	
	/** The client name. */
	private String clientName;
	
	/** The industry. */
	private String industry;
	
	/** The sic code. */
	private String sicCode;
	
	/** The website. */
	private String website;

	/**
	 * @return the lastPaid
	 */
	public String getLastPaid() {
		return lastPaid;
	}

	/**
	 * @param lastPaid the lastPaid to set
	 */
	public void setLastPaid(String lastPaid) {
		this.lastPaid = lastPaid;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * @param beneficiaryName the beneficiaryName to set
	 */
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	/**
	 * @return the thirdParty
	 */
	public String getThirdParty() {
		return thirdParty;
	}

	/**
	 * @param thirdParty the thirdParty to set
	 */
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	/**
	 * @return the beneAccountNumber
	 */
	public String getBeneAccountNumber() {
		return beneAccountNumber;
	}

	/**
	 * @param beneAccountNumber the beneAccountNumber to set
	 */
	public void setBeneAccountNumber(String beneAccountNumber) {
		this.beneAccountNumber = beneAccountNumber;
	}

	/**
	 * @return the swiftCode
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * @param swiftCode the swiftCode to set
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the payeeId
	 */
	public Integer getPayeeId() {
		return payeeId;
	}

	/**
	 * @param payeeId the payeeId to set
	 */
	public void setPayeeId(Integer payeeId) {
		this.payeeId = payeeId;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the sicCode
	 */
	public String getSicCode() {
		return sicCode;
	}

	/**
	 * @param sicCode the sicCode to set
	 */
	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneAccountNumber == null) ? 0 : beneAccountNumber.hashCode());
		result = prime * result + ((beneficiaryName == null) ? 0 : beneficiaryName.hashCode());
		result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
		result = prime * result + ((lastPaid == null) ? 0 : lastPaid.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((payeeId == null) ? 0 : payeeId.hashCode());
		result = prime * result + ((sicCode == null) ? 0 : sicCode.hashCode());
		result = prime * result + ((swiftCode == null) ? 0 : swiftCode.hashCode());
		result = prime * result + ((thirdParty == null) ? 0 : thirdParty.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("squid:S3776") 
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		
		PayeeQueueData other = (PayeeQueueData) obj;
		if (beneAccountNumber == null) {
			if (other.beneAccountNumber != null)
				return false;
		} else if (!beneAccountNumber.equals(other.beneAccountNumber)) {
			return false;
		}
		if (beneficiaryName == null) {
			if (other.beneficiaryName != null)
				return false;
		} else if (!beneficiaryName.equals(other.beneficiaryName)) {
			return false;
		}
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName)) {
			return false;
		}
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry)) {
			return false;
		}
		if (lastPaid == null) {
			if (other.lastPaid != null)
				return false;
		} else if (!lastPaid.equals(other.lastPaid)) {
			return false;
		}
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization)) {
			return false;
		}
		if (payeeId == null) {
			if (other.payeeId != null)
				return false;
		} else if (!payeeId.equals(other.payeeId)) {
			return false;
		}
		if (sicCode == null) {
			if (other.sicCode != null)
				return false;
		} else if (!sicCode.equals(other.sicCode)) {
			return false;
		}
		if (swiftCode == null) {
			if (other.swiftCode != null)
				return false;
		} else if (!swiftCode.equals(other.swiftCode)) {
			return false;
		}
		if (thirdParty == null) {
			if (other.thirdParty != null)
				return false;
		} else if (!thirdParty.equals(other.thirdParty)) {
			return false;
		}
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PayeeQueueData [payeeId=" + payeeId + ", lastPaid=" + lastPaid + ", organization=" + organization
				+ ", beneficiaryName=" + beneficiaryName + ", thirdParty=" + thirdParty + ", beneAccountNumber="
				+ beneAccountNumber + ", swiftCode=" + swiftCode + ", currency=" + currency + ", country=" + country
				+ ", clientName=" + clientName + ", industry=" + industry + ", sicCode=" + sicCode + ", website="
				+ website + "]";
	}

}
