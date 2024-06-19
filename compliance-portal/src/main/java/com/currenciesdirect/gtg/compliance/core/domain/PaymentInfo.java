package com.currenciesdirect.gtg.compliance.core.domain;

public class PaymentInfo {

	private String status;
	
	private String tradePaymentId;
	
	private String transactionNumber;
	
	private String amount;
	
	private String dateOfPayment;
	
	private String legalEntity;

	private String initialStatus;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradePaymentId() {
		return tradePaymentId;
	}

	public void setTradePaymentId(String tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}
	
}
