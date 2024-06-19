package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

/**
 * The Class CustomCheckSearchResponse.
 */
public class CustomCheckSearchResponse implements IDomain{
	
	private String errorCode; 

	private String errorDescription; 

	private String status;  

	private Integer total;  

	private Integer occupation;  

	private Integer countriesOfTrade;  

	private Integer purposeOfTransaction;  

	private Integer sourceOfFund; 

	private Integer valueOfTransaction;  

	private Integer sourceOfLead;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getOccupation() {
		return occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	public Integer getCountriesOfTrade() {
		return countriesOfTrade;
	}

	public void setCountriesOfTrade(Integer countriesOfTrade) {
		this.countriesOfTrade = countriesOfTrade;
	}

	public Integer getPurposeOfTransaction() {
		return purposeOfTransaction;
	}

	public void setPurposeOfTransaction(Integer purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	public Integer getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(Integer sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public Integer getValueOfTransaction() {
		return valueOfTransaction;
	}

	public void setValueOfTransaction(Integer valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	public Integer getSourceOfLead() {
		return sourceOfLead;
	}

	public void setSourceOfLead(Integer sourceOfLead) {
		this.sourceOfLead = sourceOfLead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countriesOfTrade == null) ? 0 : countriesOfTrade.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((purposeOfTransaction == null) ? 0 : purposeOfTransaction.hashCode());
		result = prime * result + ((sourceOfFund == null) ? 0 : sourceOfFund.hashCode());
		result = prime * result + ((sourceOfLead == null) ? 0 : sourceOfLead.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		CustomCheckSearchResponse other = (CustomCheckSearchResponse) obj;
		if (countriesOfTrade == null) {
			if (other.countriesOfTrade != null)
				return false;
		} else if (!countriesOfTrade.equals(other.countriesOfTrade))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorDescription == null) {
			if (other.errorDescription != null)
				return false;
		} else if (!errorDescription.equals(other.errorDescription))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (purposeOfTransaction == null) {
			if (other.purposeOfTransaction != null)
				return false;
		} else if (!purposeOfTransaction.equals(other.purposeOfTransaction))
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
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
		return "CustomCheckSearchResponse [errorCode=" + errorCode + ", errorDescription=" + errorDescription
				+ ", status=" + status + ", total=" + total + ", occupation=" + occupation + ", countriesOfTrade="
				+ countriesOfTrade + ", purposeOfTransaction=" + purposeOfTransaction + ", sourceOfFund=" + sourceOfFund
				+ ", valueOfTransaction=" + valueOfTransaction + ", sourceOfLead=" + sourceOfLead + "]";
	}
	
	
	
}
