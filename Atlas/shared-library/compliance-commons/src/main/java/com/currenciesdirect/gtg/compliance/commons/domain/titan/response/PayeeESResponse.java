package com.currenciesdirect.gtg.compliance.commons.domain.titan.response;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

/**
 * The Class PayeeESResponse.
 */
public class PayeeESResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The payee client. */
	private List<PayeeClient> children;

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
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<PayeeClient> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	public void setChildren(List<PayeeClient> children) {
		this.children = children;
	}

}
