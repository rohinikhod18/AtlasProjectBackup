package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

/**
 * The Class PayeePaymentsResponse.
 */
public class PayeePaymentsResponse {

	/** The reference. */
	private String reference;

	/** The date. */
	private String date;

	/** The third party. */
	private String currency;

	/** The amount. */
	private Double amount;

	/** The status. */
	private String status;

	/** The updated on. */
	private String updatedOn;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            the new updated on
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets the reference.
	 *
	 * @param reference
	 *            the new reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
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

}
