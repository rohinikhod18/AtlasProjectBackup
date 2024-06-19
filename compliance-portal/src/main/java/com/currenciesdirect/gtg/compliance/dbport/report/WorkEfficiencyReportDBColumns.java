package com.currenciesdirect.gtg.compliance.dbport.report;

/**
 * The Enum WorkEfficiencyReportDBColumns.
 */
public enum WorkEfficiencyReportDBColumns {

	TYPE("AccType"),CFX_TYPE(" ( cfx.[type]"),PFX_TYPE("pfx.[type]"),PAYIN_TYPE("pin.[type]"),PAYOUT_TYPE("pout.[type]"),
	DATE("Date"), QUEUETYPE("url.ResourceType"), USER("SSOUserID");
	
	private String name;

	private WorkEfficiencyReportDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
