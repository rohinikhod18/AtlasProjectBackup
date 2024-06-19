package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentOutEmailRequest.
 */
public class PaymentOutEmailRequest extends PaymentEmailRequest{
	
	/** The beneficiary name. */
	private String beneficiaryName;
	
	/** The beneficiary amount. */
	private String beneficiaryAmount;
	
	/** The payment out id. */
	private Integer paymentOutId;

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
	 * @return the beneficiaryAmount
	 */
	public String getBeneficiaryAmount() {
		return beneficiaryAmount;
	}

	/**
	 * @param beneficiaryAmount the beneficiaryAmount to set
	 */
	public void setBeneficiaryAmount(String beneficiaryAmount) {
		this.beneficiaryAmount = beneficiaryAmount;
	}

	/**
	 * @return the paymentOutId
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * @param paymentOutId the paymentOutId to set
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}
}
