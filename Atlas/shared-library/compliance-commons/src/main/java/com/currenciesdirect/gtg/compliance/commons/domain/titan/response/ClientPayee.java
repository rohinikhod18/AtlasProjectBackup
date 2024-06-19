package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ClientPayee.
 */
public class ClientPayee implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** The no payments. */
	private Integer noPayments;

	/** The type. */
	private String type;

	/** The other parents. */
	private List<PayeeParents> otherParents;

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
	 * Gets the other parents.
	 *
	 * @return the other parents
	 */
	public List<PayeeParents> getOtherParents() {
		return otherParents;
	}

	/**
	 * Sets the other parents.
	 *
	 * @param otherParents
	 *            the new other parents
	 */
	public void setOtherParents(List<PayeeParents> otherParents) {
		this.otherParents = otherParents;
	}

}
