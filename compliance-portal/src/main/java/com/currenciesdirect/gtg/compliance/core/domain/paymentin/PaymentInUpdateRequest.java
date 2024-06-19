package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentUpdateRequest;

public class PaymentInUpdateRequest extends PaymentUpdateRequest {

	/** The payment in id. */
	private Integer paymentinId;

	/** The updated payment out status. */
	private String updatedPaymentInStatus;
	
	/** The pre payment out status. */
	private String prePaymentInStatus;
	
	/** The debtor amount. */
	private String debtorAmount;
	
	/** The payment method. */
	private String paymentMethod;

	public Integer getPaymentinId() {
		return paymentinId;
	}

	public void setPaymentinId(Integer paymentinId) {
		this.paymentinId = paymentinId;
	}

	public String getUpdatedPaymentInStatus() {
		return updatedPaymentInStatus;
	}

	public void setUpdatedPaymentInStatus(String updatedPaymentInStatus) {
		this.updatedPaymentInStatus = updatedPaymentInStatus;
	}

	public String getPrePaymentInStatus() {
		return prePaymentInStatus;
	}

	public void setPrePaymentInStatus(String prePaymentInStatus) {
		this.prePaymentInStatus = prePaymentInStatus;
	}

	/**
	 * @return the debtorAmount
	 */
	public String getDebtorAmount() {
		return debtorAmount;
	}

	/**
	 * @param debtorAmount the debtorAmount to set
	 */
	public void setDebtorAmount(String debtorAmount) {
		this.debtorAmount = debtorAmount;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
