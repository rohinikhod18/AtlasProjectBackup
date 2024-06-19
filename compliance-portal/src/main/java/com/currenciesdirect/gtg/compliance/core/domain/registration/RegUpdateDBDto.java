package com.currenciesdirect.gtg.compliance.core.domain.registration;

import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateDBDto;

/**
 * The Class RegUpdateRequestHandlerDto.
 */
public class RegUpdateDBDto extends BaseUpdateDBDto {


	/** The contact status. */
	private String contactStatus;
	
	/** The account status. */
	private String accountStatus;
	
	/** The compliance done on. */
	private String complianceDoneOn;
	
	/** The registration in date. */
	private String registrationInDate;
	
	/** The compliance expiry. */
	private String complianceExpiry;

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}	
	
	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	
	/**
	 * Gets the account status.
	 *
	 * @return the account status
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * Sets the account status.
	 *
	 * @param accountStatus the new account status
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the complianceDoneOn
	 */
	public String getComplianceDoneOn() {
		return complianceDoneOn;
	}

	/**
	 * @param complianceDoneOn the complianceDoneOn to set
	 */
	public void setComplianceDoneOn(String complianceDoneOn) {
		this.complianceDoneOn = complianceDoneOn;
	}
	
	/**
	 * Gets registration in date
	 * 
	 * @return the registrationInDate
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 *  Sets registration in date
	 * 
	 * @param registrationInDate
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}
	
	/**
	 * Gets compliance expiry
	 * 
	 * @return the complianceExpiry
	 */
	public String getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * Sets compliance expiry
	 * 
	 * @param complianceExpiry
	 */
	public void setComplianceExpiry(String complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
	}
	
}
