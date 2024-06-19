package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class InternalRule.
 */
public class InternalRule {

	/** The blacklist. */
	private Blacklist blacklist;

	
	private CustomCheck customCheck;

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public Blacklist getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist
	 *            the new blacklist
	 */
	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public CustomCheck getCustomCheck() {
		return customCheck;
	}

	public void setCustomCheck(CustomCheck customCheck) {
		this.customCheck = customCheck;
	}

}
