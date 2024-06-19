package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

import java.sql.Timestamp;

/**
 * The Class CustomCheckInsertRequest.
 */
public class CustomCheckInsertRequest implements IDomain{
	
	 private CustomCheckData occupation ; 

	 private CustomCheckData sourceOfFund ; 

	 private CustomCheckData sourceOfLead ; 
	   
	 private CustomCheckData valueOfTransaction ; 

	 private CustomCheckData purposeOfTransaction ; 
	  
	 private CustomCheckData countriesOfTrade ; 

	 private String requestType ; 

	 private String organizationCode ; 

	 private String sourceApplication ;
	 
	 private String correlationId;
	 
	 private Timestamp createdOn;

	 private Timestamp updatedOn;

	 private String createdBy;

	 private String updatedBy;
	 
	 private String tradeAccountId;
	 
	 private String tradeContactId;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public CustomCheckData getOccupation() {
		return occupation;
	}

	public void setOccupation(CustomCheckData occupation) {
		this.occupation = occupation;
	}

	public CustomCheckData getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(CustomCheckData sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public CustomCheckData getSourceOfLead() {
		return sourceOfLead;
	}

	public void setSourceOfLead(CustomCheckData sourceOfLead) {
		this.sourceOfLead = sourceOfLead;
	}

	public CustomCheckData getValueOfTransaction() {
		return valueOfTransaction;
	}

	public void setValueOfTransaction(CustomCheckData valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	public CustomCheckData getPurposeOfTransaction() {
		return purposeOfTransaction;
	}

	public void setPurposeOfTransaction(CustomCheckData purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	public CustomCheckData getCountriesOfTrade() {
		return countriesOfTrade;
	}

	public void setCountriesOfTrade(CustomCheckData countriesOfTrade) {
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
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((countriesOfTrade == null) ? 0 : countriesOfTrade.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((organizationCode == null) ? 0 : organizationCode.hashCode());
		result = prime * result + ((purposeOfTransaction == null) ? 0 : purposeOfTransaction.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((sourceApplication == null) ? 0 : sourceApplication.hashCode());
		result = prime * result + ((sourceOfFund == null) ? 0 : sourceOfFund.hashCode());
		result = prime * result + ((sourceOfLead == null) ? 0 : sourceOfLead.hashCode());
		result = prime * result + ((tradeAccountId == null) ? 0 : tradeAccountId.hashCode());
		result = prime * result + ((tradeContactId == null) ? 0 : tradeContactId.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
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
		CustomCheckInsertRequest other = (CustomCheckInsertRequest) obj;
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
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
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
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
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
		return "CustomCheckInsertRequest [occupation=" + occupation + ", sourceOfFund=" + sourceOfFund
				+ ", sourceOfLead=" + sourceOfLead + ", valueOfTransaction=" + valueOfTransaction
				+ ", purposeOfTransaction=" + purposeOfTransaction + ", countriesOfTrade=" + countriesOfTrade
				+ ", requestType=" + requestType + ", organizationCode=" + organizationCode + ", sourceApplication="
				+ sourceApplication + ", correlationId=" + correlationId + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", tradeAccountId="
				+ tradeAccountId + ", tradeContactId=" + tradeContactId + "]";
	}

	
}
