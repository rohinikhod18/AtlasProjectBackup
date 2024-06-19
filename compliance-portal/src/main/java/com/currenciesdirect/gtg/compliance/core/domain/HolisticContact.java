package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class HolisticContact.
 */
public class HolisticContact extends ContactHistoryResponse{
	
	/** The account status. */
	private String accountStatus;

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
}
