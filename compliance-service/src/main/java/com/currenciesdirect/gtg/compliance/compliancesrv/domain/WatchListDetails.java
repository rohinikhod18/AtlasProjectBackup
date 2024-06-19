package com.currenciesdirect.gtg.compliance.compliancesrv.domain;

/**
 * The Class WatchListDetails.
 */
public class WatchListDetails {

	/** The name. */
	private String name;
	
	/** The stop payment out. */
	private boolean stopPaymentOut;
	
	/** The stop payment in. */
	private boolean stopPaymentIn;

	/** The category. */
	private Integer category;
	
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
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is stop payment out.
	 *
	 * @return true, if is stop payment out
	 */
	public boolean isStopPaymentOut() {
		return stopPaymentOut;
	}

	/**
	 * Sets the stop payment out.
	 *
	 * @param stopPaymentOut the new stop payment out
	 */
	public void setStopPaymentOut(boolean stopPaymentOut) {
		this.stopPaymentOut = stopPaymentOut;
	}

	/**
	 * Checks if is stop payment in.
	 *
	 * @return true, if is stop payment in
	 */
	public boolean isStopPaymentIn() {
		return stopPaymentIn;
	}

	/**
	 * Sets the stop payment in.
	 *
	 * @param stopPaymentIn the new stop payment in
	 */
	public void setStopPaymentIn(boolean stopPaymentIn) {
		this.stopPaymentIn = stopPaymentIn;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	
}
