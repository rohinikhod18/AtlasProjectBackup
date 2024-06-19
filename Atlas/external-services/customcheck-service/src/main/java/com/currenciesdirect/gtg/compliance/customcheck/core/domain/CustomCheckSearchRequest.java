package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

/**
 * The Class CustomCheckSearchRequest.
 */
public class CustomCheckSearchRequest implements IDomain{
	
	 private String occupation ; 

	 private String sourceOfFund ; 

	 private String sourceOfLead ; 
	   
	 private String valueOfTransaction ; 

	 private String purposeOfTransaction ; 
	  
	 private String countriesOfTrade ; 

	 private String requestType ; 

	 private String organizationCode ; 

	 private String sourceApplication ;
	 
	 private String correlationId;
	 
	 private String tradeAccountId;
	 
	 private String tradeContactId;

	public String getTradeAccountId() {
		return tradeAccountId;
	}

	public void setTradeAccountId(String tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	public String getTradeContactId() {
		return tradeContactId;
	}

	public void setTradeContactId(String tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public String getSourceOfLead() {
		return sourceOfLead;
	}

	public void setSourceOfLead(String sourceOfLead) {
		this.sourceOfLead = sourceOfLead;
	}

	public String getValueOfTransaction() {
		return valueOfTransaction;
	}

	public void setValueOfTransaction(String valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	public String getPurposeOfTransaction() {
		return purposeOfTransaction;
	}

	public void setPurposeOfTransaction(String purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	public String getCountriesOfTrade() {
		return countriesOfTrade;
	}

	public void setCountriesOfTrade(String countriesOfTrade) {
		this.countriesOfTrade = countriesOfTrade;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((countriesOfTrade == null) ? 0 : countriesOfTrade.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((organizationCode == null) ? 0 : organizationCode.hashCode());
		result = prime * result + ((purposeOfTransaction == null) ? 0 : purposeOfTransaction.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		result = prime * result + ((sourceOfFund == null) ? 0 : sourceOfFund.hashCode());
		result = prime * result + ((sourceOfLead == null) ? 0 : sourceOfLead.hashCode());
		result = prime * result + ((tradeAccountId == null) ? 0 : tradeAccountId.hashCode());
		result = prime * result + ((tradeContactId == null) ? 0 : tradeContactId.hashCode());
		result = prime * result + ((valueOfTransaction == null) ? 0 : valueOfTransaction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomCheckSearchRequest other = (CustomCheckSearchRequest) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (countriesOfTrade == null) {
			if (other.countriesOfTrade != null)
				return false;
		} else if (!countriesOfTrade.equals(other.countriesOfTrade))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (organizationCode == null) {
			if (other.organizationCode != null)
				return false;
		} else if (!organizationCode.equals(other.organizationCode))
			return false;
		if (purposeOfTransaction == null) {
			if (other.purposeOfTransaction != null)
				return false;
		} else if (!purposeOfTransaction.equals(other.purposeOfTransaction))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (sourceApplication == null) {
			if (other.sourceApplication != null)
				return false;
		} else if (!sourceApplication.equals(other.sourceApplication))
			return false;
		if (sourceOfFund == null) {
			if (other.sourceOfFund != null)
				return false;
		} else if (!sourceOfFund.equals(other.sourceOfFund))
			return false;
		if (sourceOfLead == null) {
			if (other.sourceOfLead != null)
				return false;
		} else if (!sourceOfLead.equals(other.sourceOfLead))
			return false;
		if (tradeAccountId == null) {
			if (other.tradeAccountId != null)
				return false;
		} else if (!tradeAccountId.equals(other.tradeAccountId))
			return false;
		if (tradeContactId == null) {
			if (other.tradeContactId != null)
				return false;
		} else if (!tradeContactId.equals(other.tradeContactId))
			return false;
		if (valueOfTransaction == null) {
			if (other.valueOfTransaction != null)
				return false;
		} else if (!valueOfTransaction.equals(other.valueOfTransaction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomCheckSearchRequest [occupation=" + occupation + ", sourceOfFund=" + sourceOfFund
				+ ", sourceOfLead=" + sourceOfLead + ", valueOfTransaction=" + valueOfTransaction
				+ ", purposeOfTransaction=" + purposeOfTransaction + ", countriesOfTrade=" + countriesOfTrade
				+ ", requestType=" + requestType + ", organizationCode=" + organizationCode + ", sourceApplication="
				+ sourceApplication + ", correlationId=" + correlationId + ", tradeAccountId=" + tradeAccountId
				+ ", tradeContactId=" + tradeContactId + "]";
	}
	
}
