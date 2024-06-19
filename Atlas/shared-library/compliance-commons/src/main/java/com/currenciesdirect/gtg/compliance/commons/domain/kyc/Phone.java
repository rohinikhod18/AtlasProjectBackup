/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.io.Serializable;

/**
 * The Class Phone.
 *
 * @author manish
 */
public class Phone implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The number. */
	private String number;

	/** The type. */
	private String type;

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number
	 *            the new number
	 */
	public void setNumber(String number) {
		this.number = number;
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

}
