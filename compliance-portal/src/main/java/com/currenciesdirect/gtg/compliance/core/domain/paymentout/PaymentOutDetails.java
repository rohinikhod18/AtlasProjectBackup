package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class PaymentOutDetails.
 */
public class PaymentOutDetails implements IDomain {

	private String dateOfPayment;
	
	private String amount;
	
	private String buyCurrency;
	
	private String accountName;
	
	private String account;

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
}
