package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentDBDto;

public class PaymentInDBDto extends PaymentDBDto {
	
	private Integer paymentInId;
	
	private String paymentInStatus;
	
	private String debitorName;
	
	private String sellCurrency;
	
	private Boolean thirdPartyPayment;
	
	private String paymentMethod;
	
	private String paymentInAttributes;
	
	private String amount;
	
	private String primaryContactName;
	
	private String countryOfFundFullName;
	
	private Integer noOfContactsForAccount;
	
	private String bankName;
	
	private String poiExists;
	
	private String initialStatus;
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentInAttributes() {
		return paymentInAttributes;
	}

	public void setPaymentInAttributes(String paymentInAttributes) {
		this.paymentInAttributes = paymentInAttributes;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	public String getSellCurrency() {
		return sellCurrency;
	}

	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	public String getPaymentInStatus() {
		return paymentInStatus;
	}

	public void setPaymentInStatus(String paymentInStatus) {
		this.paymentInStatus = paymentInStatus;
	}

	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}
	
	public String getCountryOfFundFullName() {
		return countryOfFundFullName;
	}

	public void setCountryOfFundFullName(String countryOfFundFullName) {
		this.countryOfFundFullName = countryOfFundFullName;
	}

	public Integer getNoOfContactsForAccount() {
		return noOfContactsForAccount;
	}

	public void setNoOfContactsForAccount(Integer noOfContactsForAccount) {
		this.noOfContactsForAccount = noOfContactsForAccount;
	}

	public String getPoiExists() {
		return poiExists;
	}

	public void setPoiExists(String poiExists) {
		this.poiExists = poiExists;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}

}
