package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The Class BaseUpdateDBDto.
 */
public class BaseUpdateDBDto {

	/** The deleted watchlist. */
	private List<String> deletedWatchlist = new ArrayList<>();

	/** The add watchlist. */
	private List<String> addWatchlist = new ArrayList<>();

	/** The deleted reasons. */
	private List<String> deletedReasons;

	/** The add reasons. */
	private List<String> addReasons;
	
	/** The comment. */
	private String comment;

	/** The contact id. */
	private Integer contactId;

	/** The account id. */
	private Integer accountId;
	
	/** The account sf id. */
	private String accountSfId;
	
	/** The contact sf id. */
	private String contactSfId;

	/** The org code. */
	private String orgCode;
	
	/** The created by. */
	private String createdBy;
	
	/** The activity log. */
	private List<ProfileActivityLogDto> activityLog;
	
	/** The compliance log. */
	private String complianceLog;
	
	/** The overall payment in watchlist status. */
	private ServiceStatusEnum overallPaymentInWatchlistStatus;
	
	/** The overall payment out watchlist status. */
	private ServiceStatusEnum overallPaymentOutWatchlistStatus;
	
	/** The watchlist. */
	private WatchlistUpdateRequest[] watchlist;
	
	/** The cust type. */
	private String custType;
	
	private Boolean isContactOnQueue;
	
	private Boolean isAccountOnQueue;
	
	private Boolean isPaymentOnQueue;
	
	private Boolean isRequestFromQueue;
	
	/** The fraguster event service log id. */
	private Integer fragusterEventServiceLogId;
	
	private Integer userResourceId;
	
	private Boolean isWatchlistUpdated;

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
	 * Gets the activity log.
	 *
	 * @return the activity log
	 */
	public List<ProfileActivityLogDto> getActivityLog() {
		return activityLog;
	}

	/**
	 * Sets the activity log.
	 *
	 * @param activityLog the new activity log
	 */
	public void setActivityLog(List<ProfileActivityLogDto> activityLog) {
		this.activityLog = activityLog;
	}

	/**
	 * Gets the deleted watchlist.
	 *
	 * @return the deleted watchlist
	 */
	public List<String> getDeletedWatchlist() {
		return deletedWatchlist;
	}

	/**
	 * Sets the deleted watchlist.
	 *
	 * @param deletedWatchlist the new deleted watchlist
	 */
	public void setDeletedWatchlist(List<String> deletedWatchlist) {
		this.deletedWatchlist = deletedWatchlist;
	}

	/**Account
	 * Gets the adds the watchlist.
	 *
	 * @return the adds the watchlist
	 */
	public List<String> getAddWatchlist() {
		return addWatchlist;
	}

	/**
	 * Sets the adds the watchlist.
	 *
	 * @param addWatchlist the new adds the watchlist
	 */
	public void setAddWatchlist(List<String> addWatchlist) {
		this.addWatchlist = addWatchlist;
	}

	/**
	 * Gets the deleted reasons.
	 *
	 * @return the deleted reasons
	 */
	public List<String> getDeletedReasons() {
		return deletedReasons;
	}

	/**
	 * Sets the deleted reasons.
	 *
	 * @param deletedReasons the new deleted reasons
	 */
	public void setDeletedReasons(List<String> deletedReasons) {
		this.deletedReasons = deletedReasons;
	}

	/**
	 * Gets the adds the reasons.
	 *
	 * @return the adds the reasons
	 */
	public List<String> getAddReasons() {
		return addReasons;
	}

	/**
	 * Sets the adds the reasons.
	 *
	 * @param addReasons the new adds the reasons
	 */
	public void setAddReasons(List<String> addReasons) {
		this.addReasons = addReasons;
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
	 * Gets the overall payment in watchlist status.
	 *
	 * @return the overall payment in watchlist status
	 */
	public ServiceStatusEnum getOverallPaymentInWatchlistStatus() {
		return overallPaymentInWatchlistStatus;
	}

	/**
	 * Sets the overall payment in watchlist status.
	 *
	 * @param overallPaymentInWatchlistStatus the new overall payment in watchlist status
	 */
	public void setOverallPaymentInWatchlistStatus(ServiceStatusEnum overallPaymentInWatchlistStatus) {
		this.overallPaymentInWatchlistStatus = overallPaymentInWatchlistStatus;
	}

	/**
	 * Gets the overall payment out watchlist status.
	 *
	 * @return the overall payment out watchlist status
	 */
	public ServiceStatusEnum getOverallPaymentOutWatchlistStatus() {
		return overallPaymentOutWatchlistStatus;
	}

	/**
	 * Sets the overall payment out watchlist status.
	 *
	 * @param overallPaymentOutWatchlistStatus the new overall payment out watchlist status
	 */
	public void setOverallPaymentOutWatchlistStatus(ServiceStatusEnum overallPaymentOutWatchlistStatus) {
		this.overallPaymentOutWatchlistStatus = overallPaymentOutWatchlistStatus;
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

	public Boolean getIsContactOnQueue() {
		return isContactOnQueue;
	}

	public void setIsContactOnQueue(Boolean isContactOnQueue) {
		this.isContactOnQueue = isContactOnQueue;
	}

	public Boolean getIsAccountOnQueue() {
		return isAccountOnQueue;
	}

	public void setIsAccountOnQueue(Boolean isAccountOnQueue) {
		this.isAccountOnQueue = isAccountOnQueue;
	}

	public Boolean getIsPaymentOnQueue() {
		return isPaymentOnQueue;
	}

	public void setIsPaymentOnQueue(Boolean isPaymentOnQueue) {
		this.isPaymentOnQueue = isPaymentOnQueue;
	}

	public Boolean getIsRequestFromQueue() {
		return isRequestFromQueue;
	}

	public void setIsRequestFromQueue(Boolean isRequestFromQueue) {
		this.isRequestFromQueue = isRequestFromQueue;
	}

	public Integer getUserResourceId() {
		return userResourceId;
	}

	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	public Boolean getIsWatchlistUpdated() {
		return isWatchlistUpdated;
	}

	public void setIsWatchlistUpdated(Boolean isWatchlistUpdated) {
		this.isWatchlistUpdated = isWatchlistUpdated;
	}
	
}
