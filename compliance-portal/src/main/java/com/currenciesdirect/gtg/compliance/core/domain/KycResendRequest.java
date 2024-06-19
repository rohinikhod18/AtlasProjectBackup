package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class KycResendRequest.
 */
public class KycResendRequest {

	/** The account id. */
	private Integer accountId;

	/** The contact id. */
	private String contactId;

	/** The org code. */
	private String orgCode;

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

}
