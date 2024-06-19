package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class HolisticViewRequest.
 */
public class HolisticViewRequest {

	/** The account id. */
	private Integer accountId;
	
	/** The contact id. */
	private Integer contactId;
	
	/** The user. */
	private UserProfile user;
	
	/** The cust type. */
	private String custType;
	
	/** The payment in id. */
	private Integer paymentInId;
	
	/** The payment out id. */
	private Integer paymentOutId;

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the paymentInId
	 */
	public Integer getPaymentInId() {
		return paymentInId;
	}

	/**
	 * @return the paymentOutId
	 */
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	/**
	 * @param paymentInId the paymentInId to set
	 */
	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	/**
	 * @param paymentOutId the paymentOutId to set
	 */
	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}
	
}
