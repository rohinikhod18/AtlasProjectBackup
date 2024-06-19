package com.currenciesdirect.gtg.compliance.core.domain;

public class KeywordConstants {

	
	/**
	 * Instantiates a new constants.
	 */
	private KeywordConstants(){
		
	}
	
	/** The Constant colon. */
	public static final String COLON = ":";
	
	/** The Constant colon. */
	public static final String CONTACT_NAME = "name ";
	
	/** The Constant colon. */
	public static final String PAYOUT_CLIENT_NAME = "ClientName ";//PAYMENTOUT
	
	/** The Constant colon. */
	public static final String PAYIN_CLIENT_NAME = "ClientName ";//PAYMENTIN
	
	/** Keyword Type**/
	
	/** The Constant colon. */
	public static final String EMAIL = "email";
	
	/** The Constant colon. */
	public static final String CLIENT_ID = "clientId";
	
	/** The Constant colon. */
	public static final String CONTRACT_NO = "contractNo";
	
	/** The Constant colon. */
	public static final String COUNTRY_OF_RESIDENCE = "countryOfResidence";
	
	/** The Constant colon. */
	public static final String ACSF_ID = "acsfid";
	
	/** The Constant colon. */
	public static final String ADDRESS = "address";
	
	/** The Constant colon. */
	public static final String OCCUPATION = "occupation";
	
	/** The Constant colon. */
	public static final String PAYMENT_METHOD = "paymentMethod";
	
	/** The Constant colon. */
	public static final String AMOUNT = "amount";
	
	/** The Constant colon. */
	public static final String REASON_OF_TRANSFER = "reasonOfTransfer";
	
	/** The Constant colon. */
	public static final String BENEFICIARY = "beneficiary";
	
	/** The Constant colon. */
	public static final String CLIENT_NAME = "clientName";
	
	/** The Constant TELEPHONE. */
	public static final String TELEPHONE = "tel";
	
	public static final String CLIENT_ID_WITH_SIX_DIGITS_ONLY = "clientIdWithSixDigitsOnly";
	
	/** Keyword Regular Expression **/
	
	/** The Constant colon. */
	public static final String REGEX_EMAIL = "(.*)@(.*).(.*)";
	
	/** The Constant colon. 
	 * this RegEx commented for Jira AT-1704*/
	public static final String REGEX_CLIENT_ID_LEGACY = "(.*)[0-9](I|C)[0-9]{6,6}|[0-9]{16,16}$";
	
	/** The Constant colon. */
	//this RegEx changed for Jira AT-1704
	public static final String REGEX_CLIENT_ID = "^[0-9]*$";
	
	/** The Constant colon. */
	//This RegEx changed for Jira id AT-1195(16-9 digit keyword search for PaymentIn and PaymentOut)
	public static final String REGEX_CONTRACT_NO = "[0-9]{16}\\-[0-9]{9}|(.*)[0-9](I|C)[0-9]*-[0-9](.*)";
	
	/** The Constant colon. */
	public static final String REGEX_COUNTRY_OF_RESIDENCE = "cor:";
	
	/** The Constant colon. */
	public static final String REGEX_ACSF_ID = "acsfid:";
	
	/** The Constant colon. */
	public static final String REGEX_ADDRESS = "addr:";
	
	/** The Constant colon. */
	public static final String REGEX_OCCUPATION = "ocp:";
	
	/** The Constant colon. */
	public static final String REGEX_PAYMENT_METHOD = "pm:";
	
	/** The Constant colon. */
	public static final String REGEX_AMOUNT = "amt:";
	
	/** The Constant colon. */
	public static final String REGEX_REASON_OF_TRANSFER = "rot:";
	
	/** The Constant colon. */
	public static final String REGEX_BENEFICIARY = "bene:";
	
	/** The Constant RegEx_TELEPHONE. */
	public static final String REGEX_TELEPHONE = "tel:";
	
	/** The Constant REGEX FOR 6 DIGIT TRADE ACCOUNT NUMBER. */
	public static final String REGEX_CLIENT_ID_WITH_SIX_DIGITS_ONLY = "^[0-9]{6}$";
	
}
