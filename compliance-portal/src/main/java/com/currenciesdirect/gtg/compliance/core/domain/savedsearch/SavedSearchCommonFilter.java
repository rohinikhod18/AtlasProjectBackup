package com.currenciesdirect.gtg.compliance.core.domain.savedsearch;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;

public class SavedSearchCommonFilter extends BaseFilter{

	/** The payment status. */
	private String[] paymentStatus;

	/** The country of fund. */
	private String[] countryofFund;
	
	/** The payment method. */
	private String[] paymentMethod;
	
	/** The country of beneficiary. */
	private String[] countryOfBeneficiary;

	private String valueDateFrom;

	private String valueDateTo;

	public String[] getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String[] paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String[] getCountryofFund() {
		return countryofFund;
	}

	public void setCountryofFund(String[] countryofFund) {
		this.countryofFund = countryofFund;
	}

	public String[] getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String[] paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String[] getCountryOfBeneficiary() {
		return countryOfBeneficiary;
	}

	public void setCountryOfBeneficiary(String[] countryOfBeneficiary) {
		this.countryOfBeneficiary = countryOfBeneficiary;
	}

	public String getValueDateFrom() {
		return valueDateFrom;
	}

	public void setValueDateFrom(String valueDateFrom) {
		this.valueDateFrom = valueDateFrom;
	}

	public String getValueDateTo() {
		return valueDateTo;
	}

	public void setValueDateTo(String valueDateTo) {
		this.valueDateTo = valueDateTo;
	}
}
