package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentOutSummary.
 */
public class PaymentOutSummary {
	
	/** The country of beneficiary. */
	private String countryOfBeneficiary;
	
	/** The beneficiary name. */
	private String beneficiaryName;
	
	/** The bank name. */
	private String bankName;
	
	/** The beneficiary account number. */
	private String beneficiaryAccountNumber;
	
	/** The buy currency. */
	private String buyCurrency;
	
	/** The is deleted. */
	private Boolean isDeleted;
	
	/** The beneficiary email. */
	private String beneficiaryEmail;
	
	/** The beneficiary type. */
	private String beneficiaryType;
	
	/** The payment reference. */
	private String paymentReference;
	
	/** The phone. */
	private String phone;

	/**
	 * @return the countryOfBeneficiary
	 */
	public String getCountryOfBeneficiary() {
		return countryOfBeneficiary;
	}

	/**
	 * @param countryOfBeneficiary the countryOfBeneficiary to set
	 */
	public void setCountryOfBeneficiary(String countryOfBeneficiary) {
		this.countryOfBeneficiary = countryOfBeneficiary;
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
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the beneficiaryAccountNumber
	 */
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	/**
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @return the beneficiaryEmail
	 */
	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}

	/**
	 * @return the beneficiaryType
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * @param beneficiaryAccountNumber the beneficiaryAccountNumber to set
	 */
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	/**
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @param beneficiaryEmail the beneficiaryEmail to set
	 */
	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}

	/**
	 * @param beneficiaryType the beneficiaryType to set
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * @return the paymentReference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param paymentReference the paymentReference to set
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
