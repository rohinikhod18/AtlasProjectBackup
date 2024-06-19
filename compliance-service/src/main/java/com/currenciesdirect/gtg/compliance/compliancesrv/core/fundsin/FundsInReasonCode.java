package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

public enum FundsInReasonCode {
	
	PASS("000", "All checks performed successfully", null),
	MISSINGINFO("701", "Missing Mandatory Fields", null),
	BLACKLISTED("702", "Black Listed Customer information", "Blacklist"),
	SANCTIONED("703", "Debtor Sanctions Check Fail", "Awaiting sanction review"),
	FRAUGSTER_WATCHLIST("704", "Fraudpredict Watchlist", null),
	CONTACT_ON_WATCHLIST("705","Contact already on watchlist", null),
	SELLING_AMOUNT_LIMIT_EXCEEDED("706","Selling Amount is more than average Transaction value",null),
	FRAUGSTER_WATCHLIST_SANCTIONED("707","Fraudpredict Watchlist and debtor Sanctions Check Fail",null),
	INACTIVE_CUSTOMER("708", "Inactive Customer", null),
	BLACKLISTED_BENE("709", "Black Listed Beneficiary Information", null),
	REASON_MISMATCH("710", "Different Reason of transfer", null),
	AMT_VELOCITY("711", "Amount Velocity Check Fail", null),
	FRQ_VELOCITY("712", "Frequency Velocity Check Fail", null),
	USGLOBAL_WATCHLIST("713", "Global check Pass for some for US State (List B)", null),
	HIGHRISK_WATCHLIST("714", "High Risk country other checks are performed", null),
	AMT_WHITELIST("715", "Amount whitelist Check Fail", null),
	CURRENCY_WHITELIST("716", "Currency whitelist Check Fail", null),
	THIRDPARTY_WHITELIST("717", "Third Party whitelist Check Fail", null),
	THIRDPARTY_SANCTION_NOT_ELIGIBLE("718", "Not eligible for third party sanction", null),
	CARD_SCORE("719", "Debit card fraud score is high", null),
	SANCTION_SERVICE_FAIL("720","Sanction service is unavailable",null),
	CARD_FRAUD_SERVICE_FAIL("721","Card Fraud service is unavailable",null),
	COUNTRY_CHECK_SERVICE_FAIL("722","Country check service is unavailable",null),
	BLACKLIST_SERVICE_FAIL("723","Blacklist service is unavailable",null),
	FRAUGSTER_SERVICE_FAIL("724","Fraudpredict service is unavailable",null),
	CUSTOM_CHECK_SERVICE_FAIL("725","Custom check service is unavailable",null),
	SANCTIONED_COUNTRY("726","Sanctioned country other checks are performed", null),
	BLACKLIST_NOT_REQUIRED("727","Blacklist check is not required", null),
	THIRDPARTY_ACCOUNT_NUM_OR_NAME_NOT_PRESENT("728","For Third Party Payment debtor name or account number not provided",null),
	MULTIPLE_FAILURE("777", "Multiple checks failed", null),
	SYSTEM_FAILURE("799", "Something went wrong", null),
	RECHECK_PASS("800","Recheck done successfully",null),
	FIRSTCREDITCHECK("801","First FundsIn Credit check failed ",null),//AT-3346
	EUPOICHECK("802","EU Contact POI Check Fail under Custom Check",null),//AT-3349
	CDINC_FIRSTCREDITCHECK("803","CDINC First Credit Check Fail",null), //Add for AT-3738
	CARD_FRAUDSIGHT_SCORE("804", "Debit card fraud Sight score is high", null),
	FRAUD_SIGHT_SERVICE_FAIL("805","Fraud sight service is unavailable",null),//AT-3714
	TRANSACTION_MONITORING_CHECK_FAIL("806","Transaction monitoring check fail",null), // AT-4594	
	TRANSACTION_MONITORING_SERVICE_FAIL("807","Transaction monitoring service is unavailable",null), // AT-4594
	CARD_CONTACT_ID_AND_TRADE_ACCOUNT_NUMBER_MISMATCHED("808","IDs are MisMatched for Card",null);//AT-4812
	
	private String fInReasonCode;
	private String fInReasonDescription;
	private String fInReasonShort;
	
	private FundsInReasonCode(String reasonCode, String reasonDescription, String reasonShort){
		this.fInReasonCode = reasonCode;
		this.fInReasonDescription = reasonDescription;
		this.fInReasonShort = reasonShort;
	}

	public String getReasonCode() {
		return fInReasonCode;
	}

	public String getReasonDescription() {
		return fInReasonDescription;
	}
	
	public String getReasonShort() {
		return fInReasonShort;
	}

	@Override
	public String toString(){
		return fInReasonCode + ": " + fInReasonDescription;
	}
}
