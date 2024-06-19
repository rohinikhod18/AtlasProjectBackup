package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentInSummary.
 */
public class PaymentInSummary {
	
	/** The third party flag. */
	private Boolean thirdPartyFlag;
	
	/** The country of fund. */
	private String countryOfFund;
	
	/** The sell currency. */
	private String sellCurrency;
	
	/** The debtor account number. */
	private String debtorAccountNumber;
	
	/** The debtor name. */
	private String debtorName;
	
	/** The is deleted. */
	private Boolean isDeleted;
	
	/** The deleted date. */
	private String deletedDate;
	

	/**
	 * @return the thirdPartyFlag
	 */
	public Boolean getThirdPartyFlag() {
		return thirdPartyFlag;
	}

	/**
	 * @param thirdPartyFlag the thirdPartyFlag to set
	 */
	public void setThirdPartyFlag(Boolean thirdPartyFlag) {
		this.thirdPartyFlag = thirdPartyFlag;
	}
	
	/**
	 * @return the countryOfFund
	 */
	public String getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * @param countryOfFund the countryOfFund to set
	 */
	public void setCountryOfFund(String countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	/**
	 * @return the sellCurrency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * @return the debtorAccountNumber
	 */
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	/**
	 * @return the debtorName
	 */
	public String getDebtorName() {
		return debtorName;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @return the deletedDate
	 */
	public String getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param sellCurrency the sellCurrency to set
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * @param debtorAccountNumber the debtorAccountNumber to set
	 */
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	/**
	 * @param debtorName the debtorName to set
	 */
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}

	
	
}
