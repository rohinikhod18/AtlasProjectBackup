package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class WatchlistUpdateRequest.
 */
public class WatchlistUpdateRequest {

	/** The name. */
	private String name;

	/** The pre value. */
	private Boolean preValue;

	/** The updated value. */
	private Boolean updatedValue;

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
	 * Gets the pre value.
	 *
	 * @return the pre value
	 */
	public Boolean getPreValue() {
		return preValue;
	}

	/**
	 * Sets the pre value.
	 *
	 * @param preValue
	 *            the new pre value
	 */
	public void setPreValue(Boolean preValue) {
		this.preValue = preValue;
	}

	/**
	 * Gets the updated value.
	 *
	 * @return the updated value
	 */
	public Boolean getUpdatedValue() {
		return updatedValue;
	}

	/**
	 * Sets the updated value.
	 *
	 * @param updatedValue
	 *            the new updated value
	 */
	public void setUpdatedValue(Boolean updatedValue) {
		this.updatedValue = updatedValue;
	}

}