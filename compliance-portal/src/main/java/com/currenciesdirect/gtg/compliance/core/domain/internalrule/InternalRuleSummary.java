package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

/**
 * The Class InternalRuleSummary.
 */
public class InternalRuleSummary {

	/** The blacklist summary. */
	private BlacklistSummary blacklistSummary;

	/** The ip summary. */
	private IpSummary ipSummary;

	/**
	 * Gets the blacklist summary.
	 *
	 * @return the blacklist summary
	 */
	public BlacklistSummary getBlacklistSummary() {
		return blacklistSummary;
	}

	/**
	 * Sets the blacklist summary.
	 *
	 * @param blacklistSummary
	 *            the new blacklist summary
	 */
	public void setBlacklistSummary(BlacklistSummary blacklistSummary) {
		this.blacklistSummary = blacklistSummary;
	}

	/**
	 * Gets the ip summary.
	 *
	 * @return the ip summary
	 */
	public IpSummary getIpSummary() {
		return ipSummary;
	}

	/**
	 * Sets the ip summary.
	 *
	 * @param ipSummary
	 *            the new ip summary
	 */
	public void setIpSummary(IpSummary ipSummary) {
		this.ipSummary = ipSummary;
	}

}
