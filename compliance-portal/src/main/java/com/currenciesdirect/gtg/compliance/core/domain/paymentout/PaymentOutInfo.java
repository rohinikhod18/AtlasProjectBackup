package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentInfo;

public class PaymentOutInfo extends PaymentInfo {
	
	private Integer id;
	
	private String beneficiaryName;
	
	private String reasonForTransfer;
	
	private String regReasonForTrade;
	
	private String countryOfBeneficiary;
	
	private String buyCurrency;
	
	private String tradeBeneficiaryId;
	
	private String beneAccountNumber;

	private String valuedate;
	
	private String countryOfBeneficiaryFullName;
	
	private String maturityDate;
	
	private String isDeleted;
	
	private String updatedOn;

	private Boolean thirdPartyPayment;
	
	/** The intuition Risk Level. */
	private String intuitionRiskLevel; // AT-4187
	
	/**
	 * @return the beneAccountNumber
	 */
	public String getBeneAccountNumber() {
		return beneAccountNumber;
	}

	/**
	 * @param beneAccountNumber
	 */
	public void setBeneAccountNumber(String beneAccountNumber) {
		this.beneAccountNumber = beneAccountNumber;
	}
	
	public String getTradeBeneficiaryId() {
		return tradeBeneficiaryId;
	}

	public void setTradeBeneficiaryId(String tradeBeneficiaryId) {
		this.tradeBeneficiaryId = tradeBeneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getReasonForTransfer() {
		return reasonForTransfer;
	}

	public void setReasonForTransfer(String reasonForTransfer) {
		this.reasonForTransfer = reasonForTransfer;
	}

	public String getRegReasonForTrade() {
		return regReasonForTrade;
	}

	public void setRegReasonForTrade(String regReasonForTrade) {
		this.regReasonForTrade = regReasonForTrade;
	}

	public String getCountryOfBeneficiary() {
		return countryOfBeneficiary;
	}

	public void setCountryOfBeneficiary(String countryOfBeneficiary) {
		this.countryOfBeneficiary = countryOfBeneficiary;
	}

	public String getBuyCurrency() {
		return buyCurrency;
	}

	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the valuedate
	 */
	public String getValuedate() {
		return valuedate;
	}

	/**
	 * @param valuedate the valuedate to set
	 */
	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}
	
	/**
	 * Gets the country of beneficiary full name.
	 * 
	 * @return the country of beneficiary full name
	 */
	public String getCountryOfBeneficiaryFullName() {
		return countryOfBeneficiaryFullName;
	}

	/**
	 * Sets the country of beneficiary full name.
	 * 
	 * @param countryOfBeneficiaryFullName the country of beneficiary full name
	 */
	public void setCountryOfBeneficiaryFullName(String countryOfBeneficiaryFullName) {
		this.countryOfBeneficiaryFullName = countryOfBeneficiaryFullName;
	}

	/**
	 * Gets the maturity date.
	 *
	 * @return the maturity date
	 */
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * Sets the maturity date.
	 *
	 * @param maturityDate the new maturity date
	 */
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the thirdPartyPayment
	 */
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * @param thirdPartyPayment the thirdPartyPayment to set
	 */
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * @return the updatedOn
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the intuitionRiskLevel
	 */
	public String getIntuitionRiskLevel() {
		return intuitionRiskLevel;
	}

	/**
	 * @param intuitionRiskLevel the intuitionRiskLevel to set
	 */
	public void setIntuitionRiskLevel(String intuitionRiskLevel) {
		this.intuitionRiskLevel = intuitionRiskLevel;
	}
	
	
}
