package com.currenciesdirect.gtg.compliance.customcheck.core.domain;

/**
 * The Class CustomCheckUpdateResponse.
 */
public class CustomCheckUpdateResponse implements IDomain{
	
	private String errorCode;

	private String errorDescription;

	private String status;
	
	private String OccupationStatus;
	
	private String sourceOfLeadStatus;
	
	private String sourceOfFundStatus;
	
	private String purposeOfTransactionStatus;
	
	private String valueOfTransactionStatus;
	
	private String countriesOfTradeStatus;

	public String getOccupationStatus() {
		return OccupationStatus;
	}

	public void setOccupationStatus(String occupationStatus) {
		OccupationStatus = occupationStatus;
	}

	public String getSourceOfLeadStatus() {
		return sourceOfLeadStatus;
	}

	public void setSourceOfLeadStatus(String sourceOfLeadStatus) {
		this.sourceOfLeadStatus = sourceOfLeadStatus;
	}

	public String getSourceOfFundStatus() {
		return sourceOfFundStatus;
	}

	public void setSourceOfFundStatus(String sourceOfFundStatus) {
		this.sourceOfFundStatus = sourceOfFundStatus;
	}

	public String getPurposeOfTransactionStatus() {
		return purposeOfTransactionStatus;
	}

	public void setPurposeOfTransactionStatus(String purposeOfTransactionStatus) {
		this.purposeOfTransactionStatus = purposeOfTransactionStatus;
	}

	public String getValueOfTransactionStatus() {
		return valueOfTransactionStatus;
	}

	public void setValueOfTransactionStatus(String valueOfTransactionStatus) {
		this.valueOfTransactionStatus = valueOfTransactionStatus;
	}

	public String getCountriesOfTradeStatus() {
		return countriesOfTradeStatus;
	}

	public void setCountriesOfTradeStatus(String countriesOfTradeStatus) {
		this.countriesOfTradeStatus = countriesOfTradeStatus;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CustomCheckUpdateResponse other = (CustomCheckUpdateResponse) obj;
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomCheckUpdateResponse [errorCode=" + errorCode + ", errorDescription=" + errorDescription
				+ ", status=" + status + "]";
	}
	

}