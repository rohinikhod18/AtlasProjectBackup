package com.currenciesdirect.gtg.compliance.core.domain.registration;

import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class RegistrationUpdateRequest.
 */
public class RegistrationUpdateRequest {

	/** The watchlist. */
	private WatchlistUpdateRequest[] watchlist;

	/** The updated contact status. */
	private String updatedContactStatus;

	/** The pre contact status. */
	private String preContactStatus = "NOT_PERFORMED";//Initialise for if preContactStatus is null (Add for AT-5170)

	/** The contact status reasons. */
	private StatusReasonUpdateRequest[] contactStatusReasons;

	/** The comment. */
	private String comment;

	/** The account id. */
	private Integer accountId;

	/** The contact id. */
	private Integer contactId;
	
	/** The account sf id. */
	private String accountSfId;
	
	/** The contact sf id. */
	private String contactSfId;

	/** The org code. */
	private String orgCode;

	/** The created by. */
	private String createdBy;

	/** The user name. */
	private UserProfile user;
	
	/** The cust type. */
	private String custType;
	
	/** The updated account status. */
	private String updatedAccountStatus;

	/** The pre account status. */
	private String preAccountStatus;
	
	/** The overall watchlist status. */
	private Boolean overallWatchlistStatus;
	
	/** The compliance done on. */
	private String complianceDoneOn;

	/** The registration in date. */
	private String registrationInDate;
	
	/** The compliance expiry. */
	private String complianceExpiry;
	
	/** The compliance log. */
	private String complianceLog;
	
	private Boolean isOnQueue;
	/** The fraguster event service log id. */
	private Integer fraugsterEventServiceLogId;
	
	private Integer userResourceId;


	/**
	 * Gets the fraugster event service log id.
	 *
	 * @return the fraugster event service log id
	 */
	public Integer getFraugsterEventServiceLogId() {
		return fraugsterEventServiceLogId;
	}

	/**
	 * Sets the fraugster event service log id.
	 *
	 * @param fraugsterEventServiceLogId the new fraugster event service log id
	 */
	public void setFraugsterEventServiceLogId(Integer fraugsterEventServiceLogId) {
		this.fraugsterEventServiceLogId = fraugsterEventServiceLogId;
	}

	/**
	 * Gets the overall watchlist status.
	 *
	 * @return the overall watchlist status
	 */
	public Boolean getOverallWatchlistStatus() {
		return overallWatchlistStatus;
	}

	/**
	 * Sets the overall watchlist status.
	 *
	 * @param overallWatchlistStatus the new overall watchlist status
	 */
	public void setOverallWatchlistStatus(Boolean overallWatchlistStatus) {
		this.overallWatchlistStatus = overallWatchlistStatus;
	}

	/**
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public WatchlistUpdateRequest[] getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist
	 *            the new watchlist
	 */
	public void setWatchlist(WatchlistUpdateRequest[] watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * Gets the updated contact status.
	 *
	 * @return the updated contact status
	 */
	public String getUpdatedContactStatus() {
		return updatedContactStatus;
	}

	/**
	 * Sets the updated contact status.
	 *
	 * @param updatedContactStatus
	 *            the new updated contact status
	 */
	public void setUpdatedContactStatus(String updatedContactStatus) {
		this.updatedContactStatus = updatedContactStatus;
	}

	/**
	 * Gets the pre contact status.
	 *
	 * @return the pre contact status
	 */
	public String getPreContactStatus() {
		return preContactStatus;
	}

	/**
	 * Sets the pre contact status.
	 *
	 * @param preContactStatus
	 *            the new pre contact status
	 */
	public void setPreContactStatus(String preContactStatus) {
		this.preContactStatus = preContactStatus;
	}

	/**
	 * Gets the contact status reasons.
	 *
	 * @return the contact status reasons
	 */
	public StatusReasonUpdateRequest[] getContactStatusReasons() {
		return contactStatusReasons;
	}

	/**
	 * Sets the contact status reasons.
	 *
	 * @param contactStatusReasons
	 *            the new contact status reasons
	 */
	public void setContactStatusReasons(StatusReasonUpdateRequest[] contactStatusReasons) {
		this.contactStatusReasons = contactStatusReasons;
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
	 * @param comment
	 *            the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * Gets the account sf id.
	 *
	 * @return the account sf id
	 */
	public String getAccountSfId() {
		return accountSfId;
	}

	/**
	 * Gets the contact sf id.
	 *
	 * @return the contact sf id
	 */
	public String getContactSfId() {
		return contactSfId;
	}

	/**
	 * Sets the account sf id.
	 *
	 * @param accountSfId the new account sf id
	 */
	public void setAccountSfId(String accountSfId) {
		this.accountSfId = accountSfId;
	}

	/**
	 * Sets the contact sf id.
	 *
	 * @param contactSfId the new contact sf id
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
	}

	
	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the updated account status.
	 *
	 * @return the updated account status
	 */
	public String getUpdatedAccountStatus() {
		return updatedAccountStatus;
	}

	/**
	 * Sets the updated account status.
	 *
	 * @param updatedAccountStatus the new updated account status
	 */
	public void setUpdatedAccountStatus(String updatedAccountStatus) {
		this.updatedAccountStatus = updatedAccountStatus;
	}

	/**
	 * Gets the pre account status.
	 *
	 * @return the pre account status
	 */
	public String getPreAccountStatus() {
		return preAccountStatus;
	}

	/**
	 * Sets the pre account status.
	 *
	 * @param preAccountStatus the new pre account status
	 */
	public void setPreAccountStatus(String preAccountStatus) {
		this.preAccountStatus = preAccountStatus;
	}

	/**
	 * Gets the compliance done on.
	 *
	 * @return the complianceDoneOn
	 */
	public String getComplianceDoneOn() {
		return complianceDoneOn;
	}

	/**
	 * Sets the compliance done on.
	 *
	 * @param complianceDoneOn the complianceDoneOn to set
	 */
	public void setComplianceDoneOn(String complianceDoneOn) {
		this.complianceDoneOn = complianceDoneOn;
	}

	/**
	 * Gets registration in date.
	 *
	 * @return the registrationInDate
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 *  Sets registration in date.
	 *
	 * @param registrationInDate the new registration in date
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}
	
	/**
	 * Gets compliance expiry.
	 *
	 * @return the complianceExpiry
	 */
	public String getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * Sets compliance expiry.
	 *
	 * @param complianceExpiry the new compliance expiry
	 */
	public void setComplianceExpiry(String complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
	}

	/**
	 * Gets the compliance log.
	 *
	 * @return the compliance log
	 */
	public String getComplianceLog() {
		return complianceLog;
	}

	/**
	 * Sets the compliance log.
	 *
	 * @param complianceLog the new compliance log
	 */
	public void setComplianceLog(String complianceLog) {
		this.complianceLog = complianceLog;
	}
	
	public Boolean getIsOnQueue() {
		return isOnQueue;
	}

	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	public Integer getUserResourceId() {
		return userResourceId;
	}

	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	@Override
	public String toString() {
		return "RegistrationUpdateRequest [watchlist=" + Arrays.toString(watchlist) + ", updatedContactStatus="
				+ updatedContactStatus + ", preContactStatus=" + preContactStatus + ", contactStatusReasons="
				+ Arrays.toString(contactStatusReasons) + ", comment=" + comment + ", accountId=" + accountId
				+ ", contactId=" + contactId + ", accountSfId=" + accountSfId + ", contactSfId=" + contactSfId
				+ ", orgCode=" + orgCode + ", createdBy=" + createdBy + ", user=" + user + ", custType=" + custType
				+ ", updatedAccountStatus=" + updatedAccountStatus + ", preAccountStatus=" + preAccountStatus
				+ ", overallWatchlistStatus=" + overallWatchlistStatus + ", complianceDoneOn=" + complianceDoneOn
				+ ", registrationInDate=" + registrationInDate + ", complianceExpiry=" + complianceExpiry
				+ ", complianceLog=" + complianceLog + ", isOnQueue=" + isOnQueue + ", fraugsterEventServiceLogId="
				+ fraugsterEventServiceLogId + ", userResourceId=" + userResourceId + "]";
	}
	
}
