package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateRequest;

/**
 * The Class PaymentOutUpdateRequest.
 */
public class PaymentOutUpdateRequest extends PaymentUpdateRequest {

	
	/** The payment out id. */
	private Integer paymentOutId;	
	
	private String tradeBeneficiayId;

	/** The updated payment out status. */
	private String updatedPaymentOutStatus;

	/** The pre payment out status. */
	private String prePaymentOutStatus;
	
	/** The beneficiary name. */
	private String beneficiaryName;
	
	/** The beneficiary amount. */
	private String beneficiaryAmount;
	
	private String beneCheckStatus;
	

	public String getUpdatedPaymentOutStatus() {
		return updatedPaymentOutStatus;
	}

	public void setUpdatedPaymentOutStatus(String updatedPaymentOutStatus) {
		this.updatedPaymentOutStatus = updatedPaymentOutStatus;
	}

	public String getPrePaymentOutStatus() {
		return prePaymentOutStatus;
	}

	public void setPrePaymentOutStatus(String prePaymentOutStatus) {
		this.prePaymentOutStatus = prePaymentOutStatus;
	}

	public Integer getPaymentOutId() {
		return paymentOutId;
	}
	
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public String getTradeBeneficiayId() {
		return tradeBeneficiayId;
	}

	public void setTradeBeneficiayId(String tradeBeneficiayId) {
		this.tradeBeneficiayId = tradeBeneficiayId;
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
	 * @return the beneCheckStatus
	 */
	public String getBeneCheckStatus() {
		return beneCheckStatus;
	}

	/**
	 * @param beneCheckStatus the beneCheckStatus to set
	 */
	public void setBeneCheckStatus(String beneCheckStatus) {
		this.beneCheckStatus = beneCheckStatus;
	}

	
}
