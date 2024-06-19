package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class BaseUpdateRequest.
 */
public class BaseUpdateRequest {

	/** The watchlist. */
	private WatchlistUpdateRequest[] watchlist;

	/** The status resons. */
	private StatusReasonUpdateRequest[] statusReasons;

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
	
	/** The overall watchlist status. */
	private Boolean overallWatchlistStatus;
	
	private Boolean isOnQueue;

	/** The fraguster event service log id. */
	private Integer fragusterEventServiceLogId;
	
	private Integer userResourceId;
	

	/**
	 * Gets the fraguster event service log id.
	 *
	 * @return the fraguster event service log id
	 */
	public Integer getFragusterEventServiceLogId() {
		return fragusterEventServiceLogId;
	}

	/**
	 * Sets the fraguster event service log id.
	 *
	 * @param fragusterEventServiceLogId the new fraguster event service log id
	 */
	public void setFragusterEventServiceLogId(Integer fragusterEventServiceLogId) {
		this.fragusterEventServiceLogId = fragusterEventServiceLogId;
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
	 * @param watchlist the new watchlist
	 */
	public void setWatchlist(WatchlistUpdateRequest[] watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * Gets the status reasons.
	 *
	 * @return the status reasons
	 */
	public StatusReasonUpdateRequest[] getStatusReasons() {
		return statusReasons;
	}

	/**
	 * Sets the status reasons.
	 *
	 * @param statusReasons the new status reasons
	 */
	public void setStatusReasons(StatusReasonUpdateRequest[] statusReasons) {
		this.statusReasons = statusReasons;
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

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
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
	 * Sets the account sf id.
	 *
	 * @param accountSfId the new account sf id
	 */
	public void setAccountSfId(String accountSfId) {
		this.accountSfId = accountSfId;
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
	 * Sets the contact sf id.
	 *
	 * @param contactSfId the new contact sf id
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
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
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
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
	 * @param createdBy the new created by
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
	
}
