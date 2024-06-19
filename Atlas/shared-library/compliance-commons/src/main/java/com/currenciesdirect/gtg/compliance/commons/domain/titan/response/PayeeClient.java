package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

import java.util.List;

/**
 * The Class PayeeClient.
 */
public class PayeeClient {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payee id. */
	private String id;

	/** The payee name. */
	private String name;

	/** The total beneficiaries. */
	private Integer totalBeneficiaries;

	/** The type. */
	private String type;

	/** The payee payments. */
	private List<PayeePayments> payments;

	/** The children. */
	private List<ClientPayee> children;

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the total beneficiaries.
	 *
	 * @return the total beneficiaries
	 */
	public Integer getTotalBeneficiaries() {
		return totalBeneficiaries;
	}

	/**
	 * Sets the total beneficiaries.
	 *
	 * @param totalBeneficiaries
	 *            the new total beneficiaries
	 */
	public void setTotalBeneficiaries(Integer totalBeneficiaries) {
		this.totalBeneficiaries = totalBeneficiaries;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the payments.
	 *
	 * @return the payments
	 */
	public List<PayeePayments> getPayments() {
		return payments;
	}

	/**
	 * Sets the payments.
	 *
	 * @param payments
	 *            the new payments
	 */
	public void setPayments(List<PayeePayments> payments) {
		this.payments = payments;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<ClientPayee> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	public void setChildren(List<ClientPayee> children) {
		this.children = children;
	}

}
