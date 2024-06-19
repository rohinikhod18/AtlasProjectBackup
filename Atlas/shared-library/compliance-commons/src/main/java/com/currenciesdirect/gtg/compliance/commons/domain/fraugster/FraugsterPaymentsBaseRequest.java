/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;

/**
 * The Class FraugsterPaymentsBaseRequest.
 *
 * @author manish
 */
public class FraugsterPaymentsBaseRequest extends FraugsterBaseRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The customer first name. */
	private String customerFirstName;

	/** The customer last name. */
	private String customerLastName;

	/** The transaction amount. */
	private Float transactionAmount;

	/** The currency. */
	private String currency;

	/** The customer account number. */
	private String customerAccountNumber;

	/** The reason. */
	private String reason;

	private Float  custSignupScore;
	
	/**
	 * Gets the customer first name.
	 *
	 * @return the customer first name
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * Sets the customer first name.
	 *
	 * @param customerFirstName
	 *            the new customer first name
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	/**
	 * Gets the customer last name.
	 *
	 * @return the customer last name
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * Sets the customer last name.
	 *
	 * @param customerLastName
	 *            the new customer last name
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * Gets the transaction amount.
	 *
	 * @return the transaction amount
	 */
	public Float getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * Sets the transaction amount.
	 *
	 * @param transactionAmount
	 *            the new transaction amount
	 */
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency
	 *            the new currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the customer account number.
	 *
	 * @return the customer account number
	 */
	public String getCustomerAccountNumber() {
		return customerAccountNumber;
	}

	/**
	 * Sets the customer account number.
	 *
	 * @param customerAccountNumber
	 *            the new customer account number
	 */
	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}

	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets the reason.
	 *
	 * @param reason
	 *            the new reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the custSignupScore
	 */
	public Float getCustSignupScore() {
		return custSignupScore;
	}

	/**
	 * @param custSignupScore the custSignupScore to set
	 */
	public void setCustSignupScore(Float custSignupScore) {
		this.custSignupScore = custSignupScore;
	}

}
