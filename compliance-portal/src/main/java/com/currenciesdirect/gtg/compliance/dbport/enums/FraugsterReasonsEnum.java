package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum FraugsterReasonsEnum.
 * @author Tejas I
 */
public enum FraugsterReasonsEnum {
	
	/** The confirmed fraud 1st party dc fraud. */
	CONFIRMED_FRAUD_1ST_PARTY_DC_FRAUD("Confirmed Fraud 1st Party DC fraud","DC Fraud"),
	
	/** The Confirmed fraud 1 st party payment fraud. */
	CONFIRMED_FRAUD_1ST_PARTY_PAYMENT_FRAUD("Confirmed Fraud 1st Party payment fraud","Payment Fraud"),
	
	/** The Confirmed fraud 3 rd party D C fraud. */
	CONFIRMED_FRAUD_3RD_PARTY_DC_FRAUD("Confirmed Fraud 3rd Party DC fraud","DC Fraud"),
	
	/** The Confirmed fraud 3 rd party payment fraud. */
	CONFIRMED_FRAUD_3RD_PARTY_PAYMENT_FRAUD("Confirmed Fraud 3rd Party payment fraud","Payment Fraud"),
	
	/** The Confirmed fraud account takeover. */
	CONFIRMED_FRAUD_ACCOUNT_TAKEOVER("Confirmed Fraud Account Takeover","Account Takeover"),
	
	/** The Confirmed fraud email hack. */
	CONFIRMED_FRAUD_EMAIL_HACK("Confirmed Fraud Email Hack","Email Hack"),
	
	/** The Confirmed fraud family fraud. */
	CONFIRMED_FRAUD_FAMILY_FRAUD("Confirmed Fraud Family Fraud","Family Fraud"),
	
	/** The Confirmed fraud front company. */
	CONFIRMED_FRAUD_FRONT_COMPANY("Confirmed Fraud Front Company","Front Company"),
	
	/** The Confirmed fraud phishing. */
	CONFIRMED_FRAUD_PHISHING("Confirmed Fraud Phishing","Fraud Phishing"),
	
	/** The Confirmed money laundering. */
	CONFIRMED_MONEY_LAUNDERING("Confirmed Money Laundering","Money Laundering"),
	
	/** The approved. */
	APPROVED("Approved","Approved"), ;
	
	/** The reason. */
	private String reason;
	
	/** The reason code. */
	private String reasonCode;
	
	/**
	 * Instantiates a new fraugster reasons enum.
	 *
	 * @param reason the reason
	 * @param reasonCode the reason code
	 */
	FraugsterReasonsEnum(String reason, String reasonCode) {
		this.reason = reason;
		this.reasonCode=reasonCode;
	}

	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	
	

	/**
	 * Gets the reason code.
	 *
	 * @return the reason code
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * Gets the fraugster reason.
	 *
	 * @param reason the reason
	 * @return the fraugster reason
	 */
	public static Boolean getFraugsterReason(String reason) {
		Boolean value;
		for (FraugsterReasonsEnum fraugsterReasonsEnum : FraugsterReasonsEnum.values()) {
			if (fraugsterReasonsEnum.getReason().equals(reason)) {
				value=Boolean.TRUE;
				return value;
			}
		}
		value=Boolean.FALSE;
		return value;
	}
	
	/**
	 * Gets the fraugster reason code.
	 *
	 * @param reason the reason
	 * @return the fraugster reason code
	 */
	public static String getFraugsterReasonCode(String reason) {
		for (FraugsterReasonsEnum fraugsterReasonsEnum : FraugsterReasonsEnum.values()) {
			if (fraugsterReasonsEnum.getReason().equals(reason)) {
				return fraugsterReasonsEnum.getReasonCode();
			}
		}
		return null;
	}
}
