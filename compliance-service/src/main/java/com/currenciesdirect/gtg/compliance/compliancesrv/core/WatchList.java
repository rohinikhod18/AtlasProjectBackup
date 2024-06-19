package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Enum WatchList.
 *
 * @author bnt
 */
public enum WatchList {

	/** The fraugster. */
	FRAUGSTER("Fraudpredict high risk of fraud"),
	
	/** The usglobalcheck. */
	USGLOBALCHECK("US Client List B client"),
	
	/** The countrycheck. */
	COUNTRYCHECK("High Risk Country"),
	
	/** The accountupdatedcheck. */
	//Added for AT-875
	ACCOUNTUPDATEDCHECK("Account Info Updated"),
	
	/** The inter company. */
	INTER_COMPANY("Inter-company transfers only"),
	
	/** The e tailer client vat required. */
	E_TAILER_CLIENT_VAT_REQUIRED("E-Tailer Client - VAT Required"),
	
	/** The e tailer client documentationrequired. */
	E_TAILER_CLIENT_DOCUMENTATIONREQUIRED("E-Tailer Client - Documentation Required"),
	
	/** The us client list b client. */
	US_CLIENT_LIST_B_CLIENT("US Client List B client"),
	
	/** The onfido suspect. */
	ONFIDO_SUSPECT("Onfido Suspect");
	
	/** The description. */
	private String description;
	
	/**
	 * Instantiates a new watch list.
	 *
	 * @param description the description
	 */
	private WatchList(String description){
		this.description = description;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
}
