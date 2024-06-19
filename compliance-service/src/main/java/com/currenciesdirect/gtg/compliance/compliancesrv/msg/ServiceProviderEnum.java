/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

/**
 * The Enum ServiceProviderEnum.
 *
 * @author manish
 */
public enum ServiceProviderEnum {

	/** The internal rule service. */
	INTERNAL_RULE_SERVICE(true, "INTERNAL_RULE_SERVICE"),

	/** The blacklist. */
	BLACKLIST(true, "BLACKLIST"),
	
	/** The blacklist. */
	BLACKLIST_PAY_REF(true, "BLACKLIST_PAY_REF"),

	/** The ip. */
	IP(true, "IP"),

	/** The globalcheck. */
	GLOBALCHECK(true, "GLOBALCHECK"),

	/** The countrycheck. */
	COUNTRYCHECK(true, "COUNTRYCHECK"),

	/** The card fraud score service. */
	CARD_FRAUD_SCORE_SERVICE(true, "CARDFRAUDSCORE"),
	
	/** The fraud sight score service. */
	FRAUD_SIGHT_SCORE_SERVICE(true, "FRAUDSIGHTSCORE"),

	/** The fraugster signup service. */
	FRAUGSTER_SIGNUP_SERVICE(true, "FRAUGSTER_SIGNUP_SERVICE"),

	/** The fraugster onupdate service. */
	FRAUGSTER_ONUPDATE_SERVICE(true, "FRAUGSTER_ONUPDATE_SERVICE"),

	/** The fraugster fundsout service. */
	FRAUGSTER_FUNDSOUT_SERVICE(true, "FRAUGSTER_FUNDSOUT_SERVICE"),

	/** The fraugster fundsin service. */
	FRAUGSTER_FUNDSIN_SERVICE(true, "FRAUGSTER_FUNDSIN_SERVICE"),

	/** The sanction service. */
	SANCTION_SERVICE(true, "SANCTION_SERVICE"),

	/** The fraugster service. */
	FRAUGSTER_SERVICE(true, "FRAUGSTER_SERVICE"),

	/** The kyc service. */
	KYC_SERVICE(true, "KYC_SERVICE"),

	/** The custom checks service. */
	CUSTOM_CHECKS_SERVICE(true, "VELOCITY_PERFORM_CHECK"),

	/** The repeat custom checks service. */
	REPEAT_CUSTOM_CHECKS_SERVICE(true, "VELOCITY_REPEAT_CHECK"),
	
	/** The onfido service. */
	ONFIDO_SERVICE(false,"ONFIDO_SERVICE"),
	
	/** The transaction monitoring sign up account. */
	TRANSACTION_MONITORING_SIGN_UP_ACCOUNT(true, "TRANSACTION_MONITORING_SIGN_UP_ACCOUNT"),
	
	/** The transaction monitoring sign up contact. */
	TRANSACTION_MONITORING_SIGN_UP_CONTACT(true, "TRANSACTION_MONITORING_SIGN_UP_CONTACT"),
	
	/** The transaction monitoring fundsin. */
	TRANSACTION_MONITORING_FUNDSIN(true, "TRANSACTION_MONITORING_FUNDSIN"),
	
	/** The transaction monitoring fundsout. */
	TRANSACTION_MONITORING_FUNDSOUT(true, "TRANSACTION_MONITORING_FUNDSOUT");

	/** The is internal. */
	private Boolean isInternal;

	/** The provider name. */
	private String providerName;

	/**
	 * Instantiates a new service provider enum.
	 *
	 * @param isInternal
	 *            the is internal
	 * @param name
	 *            the name
	 */
	private ServiceProviderEnum(boolean isInternal, String name) {
		this.isInternal = isInternal;
		this.providerName = name;
	}

	/**
	 * Checks if is internal.
	 *
	 * @return the boolean
	 */
	public Boolean isInternal() {
		return isInternal;
	}

	/**
	 * Gets the providername.
	 *
	 * @return the providername
	 */
	public String getProvidername() {
		return providerName;
	}
}
