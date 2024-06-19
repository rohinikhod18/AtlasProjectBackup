package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

public enum FundsInShortReasonCode {

	PASS("000", "All checks performed successfully", "STP"),
	MISSINGINFO("701", "Missing Mandatory Fields", null),
	BLACKLISTED("702", "Black Listed Customer information", "Blacklist"),
	BLACKLISTED_NAME("702", "Black Listed Name information", "BLNAC"),
	BLACKLISTED_BANK_NAME("702", "Black Listed Bank Name information", "BLBNC"),
	BLACKLISTED_ACC_NUMBER("702", "Black Listed ACC_NUMBER information", "BLANC"),
	SANCTIONED("703", "Debtor Sanctions Check Fail", "SCDCF"),
	FRAUGSTER_WATCHLIST("704", "Fraudpredict Watchlist", "FRPCF"),
	CONTACT_ON_WATCHLIST("705","Contact already on watchlist", null),
	SELLING_AMOUNT_LIMIT_EXCEEDED("706","Selling Amount is more than average Transaction value",null),
	FRAUGSTER_WATCHLIST_SANCTIONED("707","Fraudpredict Watchlist and debtor Sanctions Check Fail",null),
	INACTIVE_CUSTOMER("708", "Inactive Customer", "INACU"),
	BLACKLISTED_BENE("709", "Black Listed Beneficiary Information", null),
	REASON_MISMATCH("710", "Different Reason of transfer", "CUWRT"),
	AMT_VELOCITY("711", "Amount Velocity Check Fail", "CUVAC"),
	FRQ_VELOCITY("712", "Frequency Velocity Check Fail", "CUVNT"),
	USGLOBAL_WATCHLIST("713", "Global check Pass for some for US State (List B)", null),
	HIGHRISK_WATCHLIST("714", "High Risk country other checks are performed", null),
	AMT_WHITELIST("715", "Amount whitelist Check Fail", "CUWAR"),
	CURRENCY_WHITELIST("716", "Currency whitelist Check Fail", "CUWCC"),
	THIRDPARTY_WHITELIST("717", "Third Party whitelist Check Fail", "CUWTP"),
	THIRDPARTY_SANCTION_NOT_ELIGIBLE("718", "Not eligible for third party sanction", null),
	CARD_SCORE("719", "Debit card fraud score is high", "RG-XXX.X"),
	SANCTION_SERVICE_FAIL("720","Sanction service is unavailable","SCSNA"),
	CARD_FRAUD_SERVICE_FAIL("721","Card Fraud service is unavailable","CFSNA"),
	COUNTRY_CHECK_SERVICE_FAIL("722","Country check service is unavailable","COSNA"),
	BLACKLIST_SERVICE_FAIL("723","Blacklist service is unavailable","BLSNA"),
	FRAUGSTER_SERVICE_FAIL("724","Fraudpredict service is unavailable","FPSNA"),
	CUSTOM_CHECK_SERVICE_FAIL("725","Custom check service is unavailable","CUSNA"),
	SANCTIONED_COUNTRY("726","Sanctioned country other checks are performed", null),
	BLACKLIST_NOT_REQUIRED("727","Blacklist check is not required", null),
	THIRDPARTY_ACCOUNT_NUM_OR_NAME_NOT_PRESENT("728","For Third Party Payment debtor name or account number not provided",null),
	MULTIPLE_FAILURE("777", "Multiple checks failed", null),
	SYSTEM_FAILURE("799", "Something went wrong", null),
	RECHECK_PASS("800","Recheck done successfully",null),
	FIRSTCREDITCHECK("801","First FundsIn Credit check failed ","CUFCC"),
	EUPOICHECK("802","EU Contact POI Check Fail under Custom Check","CUIDC"),
	COUNTRY_CHECK_FAIL("722","Country check failed","CONCF"),
	CDINC_FIRSTCREDITCHECK("803","CDINC First Credit Check Fail","CUCFC"), //Add for AT-3738
	CARD_FRAUDSIGHT_SCORE("804", "Debit card fraud Sight score is high", "FS-XXX.XXX"),//AT-3714
	FRAUD_SIGHT_SERVICE_FAIL("805","Fraud sight service is unavailable","FSSNA"),//AT-3714
	TRANSACTION_MONITORING_CHECK_FAIL("806","Transaction monitoring check fail","TMCF"), // AT-4594
	TRANSACTION_MONITORING_SERVICE_FAIL("807","Transaction monitoring service is unavailable","TMSNA"); // AT-4594
	
	private String fInReasonCode;
	private String fInReasonDescription;
	private String fInReasonShort;
	
	private FundsInShortReasonCode(String reasonCode, String reasonDescription, String reasonShort){
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
