package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class PaymentOutDetails.
 */
public class FurtherPaymentOutDetails implements IDomain {

	private String dateOfPayment;
	
	private String amount;
	
	private String buyCurrency;
	
	private String accountName;
	
	private String account;
	
	private String valuedate;

    private String tradeContractNumber;
	
	private String paymentReference = Constants.DASH_SINGLE_DETAILS;
	
	private Integer paymentId;
	
	//AT-1731 - SnehaZagade
	private String bankName = Constants.DASH_SINGLE_DETAILS;
	
	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public String getAmount() {
		return amount;
	}


	public String getAccountName() {
		return accountName;
	}

	public String getAccount() {
		return account;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBuyCurrency() {
		return buyCurrency;
	}

	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
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

	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * @return the paymentId
	 */
	public Integer getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
