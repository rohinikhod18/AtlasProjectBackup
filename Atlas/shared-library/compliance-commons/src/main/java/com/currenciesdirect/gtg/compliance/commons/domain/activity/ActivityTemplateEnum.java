package com.currenciesdirect.gtg.compliance.commons.domain.activity;

/**
 * The Enum ActivityTemplateEnum.
 */
public enum ActivityTemplateEnum {

	/** The add watchlist multiple pre watchlist. */
	ADD_WATCHLIST_MULTIPLE_PRE_WATCHLIST("{ADD_WATCHLIST} watchlist added into previous {PRE_WATCHLIST} watchlist"),
	
	/** The add watchlist single. */
	ADD_WATCHLIST_SINGLE("{ADD_WATCHLIST} watchlist added"),
	
	/** The del watchlist multiple pre watchlist. */
	DEL_WATCHLIST_MULTIPLE_PRE_WATCHLIST("{DEL_WATCHLIST} watchlist deleted from previous {PRE_WATCHLIST} watchlist"),
	
	/** The add and del watchlist multiple pre watchlist. */
	ADD_AND_DEL_WATCHLIST_MULTIPLE_PRE_WATCHLIST("{ADD_WATCHLIST} watchlist added and {DEL_WATCHLIST} deleted from previous {PRE_WATCHLIST} watchlist"),
	
	/** The update contact status. */
	UPDATE_CONTACT_STATUS("Contact status modified from {PRE_STATUS} to {UPDATED_STATUS}"),
	
	/** The update account status. */
	UPDATE_ACCOUNT_STATUS("Account status modified from {PRE_STATUS} to {UPDATED_STATUS}"),
	
	/** The recheck. */
	RECHECK("After recheck {RECHECK_SERVICE_NAME} status modified from {PRE_STATUS} to {UPDATED_STATUS} for {ENTITY_NAME}"),
	
	/** The payment recheck. */
	PAYMENT_RECHECK("After recheck {RECHECK_SERVICE_NAME} status modified from {PRE_STATUS} to {UPDATED_STATUS}"),
	
	/** The blacklist recheck. */
	BLACKLIST_RECHECK("After recheck {RECHECK_SERVICE_NAME} status modified from {PRE_STATUS} to {UPDATED_STATUS} for {ENTITY_NAME}"),
	
	/** The update sanction. */
	UPDATE_SANCTION("{FIELD} modified from {PRE_STATUS} to '{VALUE}' for {ENTITY_TYPE} {ENTITY_NAME}"),
	
	/** The wathclist. */
	WATHCLIST("{PRE_WATCHLIST} watchlist changed to {CURRENT_WATCHLIST} watchlist"),
	
	/** The all wathclist deleted. */
	ALL_WATHCLIST_DELETED("{PRE_WATCHLIST} watchlist removed"),

	/** The add watchlist. */
	ADD_WATCHLIST("{CURRENT_WATCHLIST} watchlist added"),

	/** The update payment out status. */
	PAYMENT_OUT_STATUS("Payment out status modified from {PRE_STATUS} to {UPDATED_STATUS}"),

	/** The activity log for account and contact. */
	ACTIVITY_LOG_FOR_ACCOUNT_AND_CONTACT("{ACTIVITY} for {NAME}"),
	
	/** The update payment out status. */
	PAYMENT_IN_STATUS("Payment in status modified from {PRE_STATUS} to {UPDATED_STATUS}"),
	
	/**when user has to just add  a comment when status of Contact and account is not changed. **/
	ADD_COMMENT("Comment Added"),
	
	/** The compliance log. */
	COMPLIANCE_LOG("Compliance Log Added"),
	
	/** The update onfido. */
	UPDATE_ONFIDO("{FIELD} modified from {PRE_STATUS} to '{VALUE}' for {ENTITY_TYPE} {ENTITY_NAME}");
	
	/** The template. */
	private String template;
	
	/**
	 * Instantiates a new activity template enum.
	 *
	 * @param template the template
	 */
	private ActivityTemplateEnum(String template){
		this.template = template;
	}
	
	/**
	 * Gets the template.
	 *
	 * @return the template
	 */
	public String getTemplate() {
		return this.template;
	}
}
