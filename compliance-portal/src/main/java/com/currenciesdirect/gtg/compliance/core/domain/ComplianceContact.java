package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class ComplianceContact.
 */
public class ComplianceContact  extends ProfileResponse{

	private String contactSFID;
	private ComplianceStatus ccs;
	private Integer tradeContactID;

	public String getContactSFID() {
		return contactSFID;
	}

	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}

	public ComplianceStatus getCcs() {
		return ccs;
	}
	public void setCcs(ComplianceStatus ccs) {
		this.ccs = ccs;
	}

	public Integer getTradeContactID() {
		return tradeContactID;
	}

	public void setTradeContactID(Integer tradeContactID) {
		this.tradeContactID = tradeContactID;
	}
	
}
