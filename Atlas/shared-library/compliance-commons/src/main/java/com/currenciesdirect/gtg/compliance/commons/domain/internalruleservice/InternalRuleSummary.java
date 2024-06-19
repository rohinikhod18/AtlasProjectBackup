package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

/**
 * The Class InternalRuleSummary.
 */
public class InternalRuleSummary {

	/** The blacklist summary. */
	private BlacklistSummary blacklistSummary;

	/** The ip summary. */
	private IpSummary ipSummary;

	/** The hrc summary. */
	private CountryCheckContactResponse hrcSummary;

	/** The global check summary. */
	private GlobalCheckContactResponse globalCheckSummary;

	/** The card fraud score summary. */
	private CardFraudScoreResponse cardFraudScoreSummary;

	/** The card fraud score summary. */
	private FraudSightScoreResponse fraudSightScoreSummary;
	
	/** The blacklist pay ref summary. */
	private BlacklistPayRefSummary blacklistPayRefSummary;//Add for AT-3649
	
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

	/**
	 * Gets the hrc summary.
	 *
	 * @return the hrc summary
	 */
	public CountryCheckContactResponse getHrcSummary() {
		return hrcSummary;
	}

	/**
	 * Sets the hrc summary.
	 *
	 * @param hrcSummary
	 *            the new hrc summary
	 */
	public void setHrcSummary(CountryCheckContactResponse hrcSummary) {
		this.hrcSummary = hrcSummary;
	}

	/**
	 * Gets the global check summary.
	 *
	 * @return the global check summary
	 */
	public GlobalCheckContactResponse getGlobalCheckSummary() {
		return globalCheckSummary;
	}

	/**
	 * Sets the global check summary.
	 *
	 * @param globalCheckSummary
	 *            the new global check summary
	 */
	public void setGlobalCheckSummary(GlobalCheckContactResponse globalCheckSummary) {
		this.globalCheckSummary = globalCheckSummary;
	}

	/**
	 * Gets the card fraud score summary.
	 *
	 * @return the card fraud score summary
	 */
	public CardFraudScoreResponse getCardFraudScoreSummary() {
		return cardFraudScoreSummary;
	}

	/**
	 * Sets the card fraud score summary.
	 *
	 * @param cardFraudScoreSummary
	 *            the new card fraud score summary
	 */
	public void setCardFraudScoreSummary(CardFraudScoreResponse cardFraudScoreSummary) {
		this.cardFraudScoreSummary = cardFraudScoreSummary;
	}

	/**
	 * @return the fraudSightScoreSummary
	 */
	public FraudSightScoreResponse getFraudSightScoreSummary() {
		return fraudSightScoreSummary;
	}

	/**
	 * @param fraudSightScoreSummary the fraudSightScoreSummary to set
	 */
	public void setFraudSightScoreSummary(FraudSightScoreResponse fraudSightScoreSummary) {
		this.fraudSightScoreSummary = fraudSightScoreSummary;
	}

	/**
	 * Gets the blacklist pay ref summary.
	 *
	 * @return the blacklist pay ref summary
	 */
	public BlacklistPayRefSummary getBlacklistPayRefSummary() {
		return blacklistPayRefSummary;
	}

	/**
	 * Sets the blacklist pay ref summary.
	 *
	 * @param blacklistPayRefSummary the new blacklist pay ref summary
	 */
	public void setBlacklistPayRefSummary(BlacklistPayRefSummary blacklistPayRefSummary) {
		this.blacklistPayRefSummary = blacklistPayRefSummary;
	}
	
}
