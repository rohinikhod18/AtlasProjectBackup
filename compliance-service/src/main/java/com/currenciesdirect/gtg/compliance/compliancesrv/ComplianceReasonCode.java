package com.currenciesdirect.gtg.compliance.compliancesrv;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public enum ComplianceReasonCode {
	PASS("000", "All checks performed successfully", null),
	MISSINGINFO("901", "Missing Mandatory Fields", null),
	BLACKLISTED("902", "Black Listed Customer information", Constants.BLACKLIST_REASON),
	GLOBACLCHECK("903", "Global check Fail", "Licensing Prohibition"),
	BLACKLISTCOUNTRY("904", "Black listed Country", Constants.BLACKLIST_REASON), //AT-5019
	IPDISTANCECHECK("905", "IP Distance Check Fail", null),
	SANCTIONED("906", "Sanctions Check Fail", "Awaiting sanction review"),
	SANCTION_PENDING("907", "Sanctions Pending", "Awaiting sanction review"),
	KYC("908", "KYC Failed", Constants.AWAITING_DOCUMENTS),
	KYC_NA("909", "Country not supported. Awaiting Documents Info", Constants.AWAITING_DOCUMENTS),
	KYC_AND_SANCTIONED("910", "Sanctions and KYC Check Fail", Constants.AWAITING_DOCUMENTS),
	USGLOBAL_WATCHLIST("911", "Global check Pass for some for US State (List B)", null),
	HIGHRISK_WATCHLIST("912", "High Risk country other checks are performed", null),
	FRAUGSTER_WATCHLIST("913", "Fraugster low score observed all other checks are performed", null),
	BLACKLISTED_OTHER("914", "Other Contact Black Listed", Constants.BLACKLIST_REASON), //AT-5019
	BLACKLISTED_COMPANY("915", "Company Blacklisted", Constants.BLACKLIST_REASON), //AT-5019
	SANCTIONED_COMPANY("916", "Company Sanctions Check Fail",null),
	COMPLIANCE_PENDING("917", "Compliance pending", null),
	SYSTEM_FAILURE("999", "Something went wrong", null),
	//AT-355.-Tejas I
	//new reason code added.To remove reason code if old account status and new status is same
	ACCOUNT_STATUS_UNCHANGED(null,null,null),
	RECORD_UPDATED_SUCCESSFULLY("920", "Record updated Successfully", null),
	RECHECK_PASS("900","Recheck done successfully",null),
	// AT-1504 - EID Logic change
	KYC_POA("918", "KYC - address verification failed", "Awaiting POA Documents"),
	KYC_POI("919", "KYC - identity verification failed", "Awaiting POI Documents"),
	//AT-2047 Onfido services status changes
	ONFIDO_REJECT("921","Onfido Reject result for document",Constants.ONFIDO_REJECT),
	ONFIDO_CAUTION("922","Onfido Caution result for document" ,Constants.ONFIDO_CAUTION),
	ONFIDO_SUSPECT("923","Onfido Suspect result for document" ,Constants.ONFIDO_SUSPECT);
	
	private String reasonCode;
	private String reasonDescription;
	private String reasonShort;
	
	private ComplianceReasonCode(String reasonCode, String reasonDescription, String reasonShort){
		this.reasonCode = reasonCode;
		this.reasonDescription = reasonDescription;
		this.reasonShort = reasonShort;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}
	
	public String getReasonShort() {
		return reasonShort;
	}

	@Override
	public String toString(){
		return reasonCode + ": " + reasonDescription;
	}
	
}
