package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public enum FundsOutReasonCode {
	PASS("000", "All checks performed successfully", null),
	MISSINGINFO("801", "Missing Mandatory Fields", null),
	BLACKLISTED("802", "Black Listed Customer information", "Blacklist"),
	BLACKLISTED_COUNTRY("803", "Black Listed Country", null),
	IPDISTANCECHECK("804", "IP Distance Check Fail", null),
	SANCTIONED("805", "Sanctions Check Fail",Constants.AWAITING_SANCTION_REVIEW),
	INACTIVE_CUSTOMER("806", "Inactive Customer", null),
	BLACKLISTED_BENE("807", "Black Listed Beneficiary Information", null),
	REASON_MISMATCH("808", "Different Reason of transfer", null),
	CURRENCY_MISMATCH("809", "Different Buy Currency", null),
	AMT_MISMATCH("810", "Buy Currency Amount Exceeds Limit", null),
	AMT_VELOCITY("811", "Amount Velocity Check Fail", null),
	FRQ_VELOCITY("812", "Frequency Velocity Check Fail", null),
	DUPLICATE_BENE("813", "Beneficiary is in different client's contract", null),
	USGLOBAL_WATCHLIST("814", "Global check Pass for some for US State (List B)", null),
	HIGHRISK_WATCHLIST("815", "High Risk country other checks are performed", null),
	FRAUGSTER_WATCHLIST("816", "Fraudpredict low score observed all other checks are performed", null),
	CONTACT_WATCHLIST("817", "Contact(s) on watchlist", null),
	CONTACT_WATCHLIST_REASON("818", "WatchList Reason", null),
	CONTACT_SANCTIONED("819", "Contact Sanctions Check Fail", Constants.AWAITING_SANCTION_REVIEW),
	BENEFICIARY_SANCTIONED("820", "Beneficiary Sanctions Check Fail", Constants.AWAITING_SANCTION_REVIEW),
	BANK_SANCTIONED("821", "Bank Sanctions Check Fail",Constants.AWAITING_SANCTION_REVIEW),
	SYSTEM_FAILURE("888", "Something went wrong", null),
	MULTIPLE_FAILURE("899", "Multiple checks failed", null),
	RECHECK_PASS("900","Recheck done successfully",null),
	DUPLICATE_PAYMENT_FAIL("822","FAIL",null),
	DUPLICATE_PAYMENT_PASS("823","PASS",null),
	CUSTOMCHECK("824","CustomCheck Fail",null),
	CUSTOM_RULE_FRAUDPREDICT_CHECK("825", "Custom Rule FraudPredict Check Fail", null),//Add for AT-3161
	CUSTOM_RULE_EU_POI_CHECK_FAIL("826","Custom Rule EUPOICheck Fail",null),//Add for AT-3349
	SANCTION_SERVICE_FAIL("827","Sanction service is unavailable",null),//Add for AT-3580
	FRAUGSTER_SERVICE_FAIL("828","Fraudpredict service is unavailable",null),//Add for AT-3580
	BLACKLISTED_PAY_REF("829", "Black Listed Payment Reference", null),//Add for AT-3649
	BLACKLISTED_PAY_REF_SERVICE_FAIL("829", "Payment Reference service is unavailable", null),//Add for AT-3649
	TRANSACTION_MONITORING_CHECK_FAIL("830","Transaction monitoring check fail",null), // AT-4594
	TRANSACTION_MONITORING_SERVICE_FAIL("831","Transaction monitoring service is unavailable",null); // AT-4594
	
	private String fOutReasonCode;
	private String fOutReasonDescription;
	private String fOutReasonShort;
	
	private FundsOutReasonCode(String reasonCode, String reasonDescription, String reasonShort){
		this.fOutReasonCode = reasonCode;
		this.fOutReasonDescription = reasonDescription;
		this.fOutReasonShort = reasonShort;
	}

	public String getFundsOutReasonCode() {
		return fOutReasonCode;
	}

	public String getFundsOutReasonDescription() {
		return fOutReasonDescription;
	}
	
	public String getFundsOutReasonShort() {
		return fOutReasonShort;
	}

	@Override
	public String toString(){
		return fOutReasonCode + ": " + fOutReasonDescription;
	}
}
