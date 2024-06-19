package com.currenciesdirect.gtg.compliance.commons.domain.activity;

import com.currenciesdirect.gtg.compliance.commons.util.Constants;

/**
 * The Enum ActivityType.
 */
public enum ActivityType {

	/** The profile watchlist. */
	PROFILE_WATCHLIST(Constants.PROFILE, Constants.WATCH_LIST, "Profile watchlist"),

	/** The payment out watchlist. */
	PAYMENT_OUT_WATCHLIST(Constants.PAYMENT_OUT, Constants.WATCH_LIST, "Paymentout watchlist"),

	/** The profile contact status update. */
	PROFILE_CONTACT_STATUS_UPDATE(Constants.PROFILE, "CONTACT_STATUS_UPDATE", "Profile contact status update"),

	/** The profile account status update. */
	PROFILE_ACCOUNT_STATUS_UPDATE(Constants.PROFILE, "ACCOUNT_STATUS_UPDATE", "Profile account status update"),

	/** The payment out status update. */
	PAYMENT_OUT_STATUS_UPDATE(Constants.PAYMENT_OUT, "PAYMENT_OUT_STATUS_UPDATE", "Paymentout status update "),

	/** The profile sanction update. */
	PROFILE_SANCTION_UPDATE(Constants.PROFILE, Constants.SANCTIONS_UPDATE,"Profile sanction update"),

	/** The profile sanction recheck. */
	PROFILE_SANCTION_RECHECK(Constants.PROFILE, Constants.SANCTIONS_REPEAT, "Profile sanction repeat"),

	/** The profile fraugster recheck. */
	PROFILE_FRAUGSTER_RECHECK(Constants.PROFILE, Constants.FRAUGSTER_REPEAT, "Profile fraudpredict repeat"),

	/** The profile kyc recheck. */
	PROFILE_KYC_RECHECK(Constants.PROFILE, "EID_REPEAT", "Profile eid repeat"),

	/** The payment out sanction recheck. */
	PAYMENT_OUT_SANCTION_RECHECK(Constants.PAYMENT_OUT, Constants.SANCTIONS_REPEAT, "Paymentout sanction repeat"),

	/** The payment out fraugster recheck. */
	PAYMENT_OUT_FRAUGSTER_RECHECK(Constants.PAYMENT_OUT, Constants.FRAUGSTER_REPEAT, "Paymentout fraudpredict repeat"),

	/** The payment out update. */
	PAYMENT_OUT_UPDATE(Constants.PAYMENT_OUT, Constants.SANCTIONS_UPDATE, "Paymentout sanction update"),

	/** The payment out custom check repeat. */
	PAYMENT_OUT_CUSTOM_CHECK_REPEAT(Constants.PAYMENT_OUT, Constants.CUSTOM_CHECK_REPEAT, "Paymentout custom check repeat"),

	/** The payment out fraugster repeat. */
	PAYMENT_OUT_FRAUGSTER_REPEAT(Constants.PAYMENT_OUT, Constants.FRAUGSTER_REPEAT,"Paymentout fraudpredict repeat"),
	
	/** The payment out BLACKLIST repeat. */
	PAYMENT_OUT_BLACKLIST_REPEAT(Constants.PAYMENT_OUT, Constants.BLACKLIST_REPEAT,"Paymentout Blacklist repeat"),

	/** The payment in update. */
	PAYMENT_IN_STATUS_UPDATE(Constants.PAYMENT_IN, "PAYMENT_IN_STATUS_UPDATE", "PaymentIn status update"),

	/** The payment in custom check repeat. */
	PAYMENT_IN_CUSTOM_CHECK_REPEAT(Constants.PAYMENT_IN, Constants.CUSTOM_CHECK_REPEAT, "PaymentIn custom check repeat"),

	/** The payment in sanction recheck. */
	PAYMENT_IN_SANCTION_RECHECK(Constants.PAYMENT_IN, Constants.SANCTIONS_REPEAT, "PaymentIn sanction repeat"),

	/** The payment in fraugster recheck. */
	PAYMENT_IN_FRAUGSTER_RECHECK(Constants.PAYMENT_IN, Constants.FRAUGSTER_REPEAT,"PaymentIn fraudpredict repeat"),
	
	/** The payment in blacklist recheck. */
	PAYMENT_IN_BLACKLIST_RECHECK(Constants.PAYMENT_IN, Constants.BLACKLIST_REPEAT,"PaymentIn blacklist repeat"),
	
	/** The payment in update. */
	PAYMENT_IN_SANCTION_UPDATE(Constants.PAYMENT_IN, Constants.SANCTIONS_UPDATE,"PaymentIn sanction update"),
	
	/** profile add comment*. */
	
	PROFILE_ADDED_COMMENT(Constants.PROFILE, Constants.ADD_COMMENT,"Profile add comment"),
	
	/** payment in add comment*. */
	
	PAYMENT_IN_ADD_COMMENT(Constants.PAYMENT_IN, Constants.ADD_COMMENT,"Payment in comment"),
	
	/** payment out add comment*. */
	
	PAYMENT_OUT_ADD_COMMENT(Constants.PAYMENT_OUT, Constants.ADD_COMMENT,"Payment out comment"),
	
	/** The profile added compliance log. */
	PROFILE_ADDED_COMPLIANCE_LOG(Constants.PROFILE,"COMPLIANCE_LOG","Profile add compliance log"),
		
	/** The payment out sanction update. */
	PAYMENT_OUT_SANCTION_UPDATE(Constants.PAYMENT_OUT, "SANCTIONS_UPDATE", "Paymentout sanction update"),

	/** The payment out custom check recheck. */
	PAYMENT_OUT_CUSTOM_CHECK_RECHECK(Constants.PAYMENT_OUT, "CUSTOM_CHECK_REPEAT", "Paymentout custom check repeat"),

	/** The payment in custom check recheck. */
	PAYMENT_IN_CUSTOM_CHECK_RECHECK(Constants.PAYMENT_IN, "CUSTOM_CHECK_REPEAT", "PaymentIn custom check repeat"),

	/** The registration STP. */
	PROFILE_STP(Constants.PROFILE, "SIGNUP_STP", "Signup-STP"),

	/** The payment in STP. */
	PAYMENT_IN_STP(Constants.PAYMENT_IN, "PAYMENT_IN_STP", "Payment In-STP"),

	/** The payment out STP. */
	PAYMENT_OUT_STP(Constants.PAYMENT_OUT, "PAYMENT_OUT_STP", "Payment Out-STP"),

	/** The registration update STP. */
	PROFILE_UPDATE_STP(Constants.PROFILE, "UPDATE_SIGNUP_STP", "Update-STP"),
	
	/** The profile blacklist recheck. */
	PROFILE_BLACKLIST_RECHECK(Constants.PROFILE, Constants.BLACKLIST_REPEAT, "Profile blacklist repeat"),
	
	/** The profile bulk recheck. */
	PROFILE_BULK_RECHECK(Constants.PROFILE, Constants.BULK_RECHECK, "Profile-bulk recheck"),
	
	/** The payment out recheck. */
	PAYMENT_OUT_RECHECK(Constants.PAYMENT_OUT, Constants.BULK_RECHECK, "Payment Out-bulk recheck"),
	
	/** The payment in recheck. */
	PAYMENT_IN_RECHECK(Constants.PAYMENT_IN, Constants.BULK_RECHECK, "Payment In-bulk recheck"),
	
	/** The profile delete contact. */
	PROFILE_DELETE_CONTACT(Constants.PROFILE, "DELETE_CONTACT", "Profile Delete Contact"),
	
	/** The profile onfido update. */
	PROFILE_ONFIDO_UPDATE(Constants.PROFILE, Constants.ONFIDO_UPDATE, "Profile Onfido Update"),
	
	//Added for AT-3658
	/** The payment out payment reference repeat. */
	PAYMENT_OUT_PAYMENT_REFERENCE_REPEAT(Constants.PAYMENT_OUT, Constants.PAYMENT_REFERENCE_REPEAT, "Paymentout payment reference repeat");

	/** The module. */
	private String module;

	/** The type. */
	private String type;

	/** The display. */
	private String display;

	/**
	 * Instantiates a new activity type.
	 *
	 * @param module            the module
	 * @param type            the type
	 * @param display the display
	 */
	private ActivityType(String module, String type, String display) {
		this.module = module;
		this.type = type;
		this.display = display;
	}

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	public String getModule() {
		return module;
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
	 * Gets the display.
	 *
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * Gets the activity log display.
	 *
	 * @param activityType the activity type
	 * @return the activity log display
	 */
	public static String getActivityLogDisplay(String activityType) {
		String formatedActivityType = null;
		for (ActivityType type : ActivityType.values()) {
			String activitryModuleType = type.getModule() + " " + type.getType();
			if (activitryModuleType.equals(activityType)) {
				formatedActivityType = type.getDisplay();
				break;
			}
		}
		return formatedActivityType;
	}

}
