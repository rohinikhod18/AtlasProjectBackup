package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public enum FundsOutShortReasonCode {

	PASS("000", "All checks performed successfully", "STP"),
	MISSINGINFO("801", "Missing Mandatory Fields", null),
	BLACKLISTED("802", "Black Listed Customer information", "Blacklist"),
	BLACKLISTED_NAME("807", "Black Listed Name information", "BLNAC"),
	BLACKLISTED_ACC_NUMBER("807", "Black Listed ACC_NUMBER information", "BLANC"),
	BLACKLISTED_BANK_NAME("807", "Black Listed Bank Name information", "BLBNC"),
	BLACKLISTED_COUNTRY("803", "Black Listed Country", "CONCF"),
	IPDISTANCECHECK("804", "IP Distance Check Fail", null),
	SANCTIONED("805", "Sanctions Check Fail",Constants.AWAITING_SANCTION_REVIEW),
	INACTIVE_CUSTOMER("806", "Inactive Customer", "INACU"),
	BLACKLISTED_BENE("807", "Black Listed Beneficiary Information", null),
	REASON_MISMATCH("808", "Different Reason of transfer", "CUWRT"),
	CURRENCY_MISMATCH("809", "Different Buy Currency", "CUWCC"),
	AMT_MISMATCH("810", "Buy Currency Amount Exceeds Limit", "CUWAR"),
	AMT_VELOCITY("811", "Amount Velocity Check Fail", "CUVAC"),
	FRQ_VELOCITY("812", "Frequency Velocity Check Fail", "CUVNT"),
	DUPLICATE_BENE("813", "Beneficiary is in different client's contract", "CUVBC"),
	USGLOBAL_WATCHLIST("814", "Global check Pass for some for US State (List B)", null),
	HIGHRISK_WATCHLIST("815", "High Risk country other checks are performed", null),
	FRAUGSTER_WATCHLIST("816", "Fraudpredict low score observed all other checks are performed", "FRPCF"),
	CONTACT_WATCHLIST("817", "Contact(s) on watchlist", null),
	CONTACT_WATCHLIST_REASON("818", "WatchList Reason", null),
	CONTACT_SANCTIONED("819", "Contact Sanctions Check Fail", "SCCOF"),
	BENEFICIARY_SANCTIONED("820", "Beneficiary Sanctions Check Fail", "SCBCF"),
	BANK_SANCTIONED("821", "Bank Sanctions Check Fail", "SCBAF"),
	SYSTEM_FAILURE("888", "Something went wrong", null),
	MULTIPLE_FAILURE("899", "Multiple checks failed", null),
	RECHECK_PASS("900","Recheck done successfully",null),
	DUPLICATE_PAYMENT_FAIL("822","FAIL",null),
	DUPLICATE_PAYMENT_PASS("823","PASS",null),
	CUSTOMCHECK("824","CustomCheck Fail","CUSNA"),
	CUSTOM_RULE_FRAUDPREDICT_CHECK("825", "Custom Rule FraudPredict Check Fail", "CUFPC"),
	CUSTOM_RULE_EU_POI_CHECK_FAIL("826","Custom Rule EUPOICheck Fail", "CUIDC"),
	SANCTION_SERVICE_FAIL("827","Sanction service is unavailable","SCSNA"),
	COUNTRY_CHECK_SERVICE_FAIL("803","Country check service is unavailable","COSNA"),
	BLACKLIST_SERVICE_FAIL("807","Blacklist service is unavailable","BLSNA"),
	FRAUGSTER_SERVICE_FAIL("828","Fraudpredict service is unavailable","FPSNA"),
	BLACKLISTED_PAY_REF("829", "Black Listed Payment Reference", "PRBCF"),
	BLACKLISTED_PAY_REF_SERVICE_FAIL("829", "Payment Reference service is unavailable", "PRSNA"),
	TRANSACTION_MONITORING_CHECK_FAIL("830","Transaction monitoring check fail","TMCF"), // AT-4594
	TRANSACTION_MONITORING_SERVICE_FAIL("831","Transaction monitoring service is unavailable","TMSNA"); // AT-4594
	
	private String fOutReasonCode;
	private String fOutReasonDescription;
	private String fOutReasonShort;
	
	private FundsOutShortReasonCode(String reasonCode, String reasonDescription, String reasonShort){
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
