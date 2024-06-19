package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

/**
 * The Class PayeePaymentsRequest.
 */
public class PayeePaymentsRequest {

	/** The trade bene id. */
	private String tradeBeneId;

	/** The trade account number. */
	private String tradeAccountNumber;

	/** The org code. */
	private String orgCode;

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber
	 *            the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the trade bene id.
	 *
	 * @return the trade bene id
	 */
	public String getTradeBeneId() {
		return tradeBeneId;
	}

	/**
	 * Sets the trade bene id.
	 *
	 * @param tradeBeneId
	 *            the new trade bene id
	 */
	public void setTradeBeneId(String tradeBeneId) {
		this.tradeBeneId = tradeBeneId;
	}

}
