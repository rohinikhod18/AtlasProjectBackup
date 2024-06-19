package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogData;

/**
 * The Class ActivityLogData.
 */
public class ActivityLogDataWrapper extends ActivityLogData  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The contact name. */
	private String contactName;
	
	/** The account name. */
	private String accountName;
	
	/** The is contact activity. */
	private Boolean isContactActivity;
	
	/** The is account activity. */
	private Boolean isAccountActivity;
	
	/** The comment given at the time of changing status*/
	private String comment;
	
	private String contractNumber;

	
	/**
	 * Gets the contact name.
	 *
	 * @return the contact name
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Sets the contact name.
	 *
	 * @param contactName the new contact name
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Gets the account name.
	 *
	 * @return the account name
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Sets the account name.
	 *
	 * @param accountName the new account name
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Gets the checks if is contact activity.
	 *
	 * @return the checks if is contact activity
	 */
	public Boolean getIsContactActivity() {
		return isContactActivity;
	}

	/**
	 * Sets the checks if is contact activity.
	 *
	 * @param isContactActivity the new checks if is contact activity
	 */
	public void setIsContactActivity(Boolean isContactActivity) {
		this.isContactActivity = isContactActivity;
	}

	/**
	 * Gets the checks if is account activity.
	 *
	 * @return the checks if is account activity
	 */
	public Boolean getIsAccountActivity() {
		return isAccountActivity;
	}

	/**
	 * Sets the checks if is account activity.
	 *
	 * @param isAccountActivity the new checks if is account activity
	 */
	public void setIsAccountActivity(Boolean isAccountActivity) {
		this.isAccountActivity = isAccountActivity;
	}

	
	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getContractNumber() {
		return contractNumber;
	}

	@Override
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
}
