package com.currenciesdirect.gtg.compliance.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class RegistrationResponse.
 */
public class RegistrationResponse {

	private String orgCode;
	private ComplianceAccount account;
	@JsonProperty(value="osr_id")
	private String osrID;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public ComplianceAccount getAccount() {
		return account;
	}
	public void setAccount(ComplianceAccount account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "RegistrationResponse [orgCode=" + orgCode + ", account=" + account + "]";
	}
	/**
	 * @return the osrID
	 */
	public String getOsrID() {
		return osrID;
	}
	/**
	 * @param osrID the osrID to set
	 */
	@JsonProperty(value="osr_id")
	public void setOsrID(String osrID) {
		this.osrID = osrID;
	}
	

}