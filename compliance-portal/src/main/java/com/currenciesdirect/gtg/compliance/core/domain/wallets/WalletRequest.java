package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WalletRequest.
 */
public class WalletRequest {

	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;
	
	/** The osr id. */
	@JsonProperty(value = "osr_id")
	private String osrId;
	
	/** The org code. */
	@JsonProperty(value = "org_code")
	private String orgCode;
	
	/** The account number. */
	@JsonProperty(value = "account_number")
	private String accountNumber;
	
	/**
	 * Gets the source application.
	 *
	 * @return the sourceApplication
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}
	
	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication the sourceApplication to set
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}
	
	/**
	 * Gets the osr id.
	 *
	 * @return the osr_id
	 */
	public String getOsrId() {
		return osrId;
	}
	
	/**
	 * Gets the org code.
	 *
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * Gets the account number.
	 *
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	
	/**
	 * Sets the osr id.
	 *
	 * @param osrId the new osr id
	 */
	public void setOsrId(String osrId) {
		this.osrId = osrId;
	}
	
	/**
	 * Sets the org code.
	 *
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * Sets the account number.
	 *
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
