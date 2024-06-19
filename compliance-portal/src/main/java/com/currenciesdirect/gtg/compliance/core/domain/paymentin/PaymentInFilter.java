package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;

/**
 * The Class PaymentInFilter.
 */
public class PaymentInFilter extends BaseFilter {

	/** The payment status. */
	private String[] paymentStatus;

	/** The country of fund. */
	private String[] countryofFund;
	
	/** The payment method. */
	private String[] paymentMethod;

	/**
	 * Gets the countryof fund.
	 *
	 * @return the countryof fund
	 */
	public String[] getCountryofFund() {
		return countryofFund;
	}


	/**
	 * Sets the countryof fund.
	 *
	 * @param countryofFund the new countryof fund
	 */
	public void setCountryofFund(String[] countryofFund) {
		this.countryofFund = countryofFund;
	}

	
	/**
	 * Gets the payment status.
	 *
	 * @return the payment status
	 */
	public String[] getPaymentStatus() {
		return paymentStatus;
	}


	/**
	 * Sets the payment status.
	 *
	 * @param paymentStatus the new payment status
	 */
	public void setPaymentStatus(String[] paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	/**
	 * Gets the payment method.
	 * 
	 * @return the payment method
	 */
	public String[] getPaymentMethod() {
		return paymentMethod;
	}


	/**
	 *  Sets the payment method.
	 *  
	 * @param the payment method
	 */
	public void setPaymentMethod(String[] paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
