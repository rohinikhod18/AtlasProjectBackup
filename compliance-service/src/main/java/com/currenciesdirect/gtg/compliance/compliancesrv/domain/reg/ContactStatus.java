package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

public class ContactStatus {

	private String contactSFID;
	private String overAllStatus;
	private String overAllDescription;
	private String kycStatus;
	private String internalRuleStatus;
	private String customCheckStatus;
	private String sactionStatus;
	public String getContactSFID() {
		return contactSFID;
	}
	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}
	public String getOverAllStatus() {
		return overAllStatus;
	}
	public void setOverAllStatus(String overAllStatus) {
		this.overAllStatus = overAllStatus;
	}
	public String getOverAllDescription() {
		return overAllDescription;
	}
	public void setOverAllDescription(String overAllDescription) {
		this.overAllDescription = overAllDescription;
	}
	public String getKycStatus() {
		return kycStatus;
	}
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}
	public String getInternalRuleStatus() {
		return internalRuleStatus;
	}
	public void setInternalRuleStatus(String internalRuleStatus) {
		this.internalRuleStatus = internalRuleStatus;
	}
	public String getCustomCheckStatus() {
		return customCheckStatus;
	}
	public void setCustomCheckStatus(String customCheckStatus) {
		this.customCheckStatus = customCheckStatus;
	}
	public String getSactionStatus() {
		return sactionStatus;
	}
	public void setSactionStatus(String sactionStatus) {
		this.sactionStatus = sactionStatus;
	}
	@Override
	public String toString() {
		return "ContactStatus [contactSFID=" + contactSFID + ", overAllStatus=" + overAllStatus
				+ ", overAllDescription=" + overAllDescription + ", kycStatus=" + kycStatus + ", internalRuleStatus="
				+ internalRuleStatus + ", customCheckStatus=" + customCheckStatus + ", sactionStatus=" + sactionStatus
				+ "]";
	}
	
	
}
