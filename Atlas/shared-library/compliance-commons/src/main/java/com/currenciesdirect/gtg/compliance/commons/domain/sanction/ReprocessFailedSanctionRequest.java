package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

/**
 * The Class ReprocessFailedSanctionRequest.
 */
public class ReprocessFailedSanctionRequest {

	/** The payment out id. */
	private Long paymentOutId;

	/** The start date. */
	private String startDate;

	/** The end date. */
	private String endDate;

	/** The ord code. */
	private String ordCode;

	/** The batch size. */
	private Long batchSize;

	/**
	 * Gets the payment out id.
	 *
	 * @return the payment out id
	 */
	public Long getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * Sets the payment out id.
	 *
	 * @param paymentOutId
	 *            the new payment out id
	 */
	public void setPaymentOutId(Long paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the ord code.
	 *
	 * @return the ord code
	 */
	public String getOrdCode() {
		return ordCode;
	}

	/**
	 * Sets the ord code.
	 *
	 * @param ordCode
	 *            the new ord code
	 */
	public void setOrdCode(String ordCode) {
		this.ordCode = ordCode;
	}

	/**
	 * Gets the batch size.
	 *
	 * @return the batch size
	 */
	public Long getBatchSize() {
		return batchSize;
	}

	/**
	 * Sets the batch size.
	 *
	 * @param batchSize
	 *            the new batch size
	 */
	public void setBatchSize(Long batchSize) {
		this.batchSize = batchSize;
	}
}
