package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class PaymentInDetails.
 */
public class FurtherPaymentInDetails implements IDomain {
	
	private String dateOfPayment;
	
	private String amount;
	
	private String sellCurrency;
	
	private String method;
	
	private String accountName;
	
	private String account;
	
	private String riskGuardianScore;
	
	private String tradeContractNumber;
	
	private String paymentReference;
	
	private Integer paymentId;
	
	//AT-1731 - SnehaZagade
	private String bankName = Constants.DASH_SINGLE_DETAILS;
	
	
	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public String getAmount() {
		return amount;
	}

	public String getSellCurrency() {
		return sellCurrency;
	}

	public String getMethod() {
		return method;
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

	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * Gets the risk guardian score.
	 *
	 * @return the risk guardian score
	 */
	public String getRiskGuardianScore() {
		return riskGuardianScore;
	}

	/**
	 * Sets the risk guardian score.
	 *
	 * @param riskGuardianScore the new risk guardian score
	 */
	public void setRiskGuardianScore(String riskGuardianScore) {
		this.riskGuardianScore = riskGuardianScore;
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
