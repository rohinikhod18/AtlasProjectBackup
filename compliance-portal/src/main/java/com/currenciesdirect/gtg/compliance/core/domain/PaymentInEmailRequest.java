package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentInEmailRequest.
 */
public class PaymentInEmailRequest extends PaymentEmailRequest{
	
	/** The debtor name. */
	private String contactName;
	
	/** The debtor amount. */
	private String debtorAmount;
	
	/** The payment in id. */
	private Integer paymentInId;
	
	/** The payment method. */
	private String paymentMethod;

	/**
	 * @return the debtorName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param debtorName the debtorName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
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
	 * @return the paymentInId
	 */
	public Integer getPaymentInId() {
		return paymentInId;
	}

	/**
	 * @param paymentInId the paymentInId to set
	 */
	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
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
