package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

import java.io.Serializable;

/**
 * The Class PayeeParents.
 */
public class PayeeParents implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payee id. */
	private String id;

	/** The no payments. */
	private Integer noPayments;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the no payments.
	 *
	 * @return the no payments
	 */
	public Integer getNoPayments() {
		return noPayments;
	}

	/**
	 * Sets the no payments.
	 *
	 * @param noPayments
	 *            the new no payments
	 */
	public void setNoPayments(Integer noPayments) {
		this.noPayments = noPayments;
	}

}
