package com.currenciesdirect.gtg.compliance.commons.domain.activity;

import java.sql.Timestamp;

/**
 * The Class PaymentInActivityLogDto.
 */
public class PaymentInActivityLogDto {

	/** The id. */
	private Integer id;
	
	/** The time-stamp. */
	private Timestamp timeStatmp;
	
	/** The activity by. */
	private String activityBy;
	
	/** The org code. */
	private String orgCode;
	
	/** The account id. */
	private Integer accountId;
	
	/** The payment in id. */
	private Integer paymentInId;
	
	/** The comment. */
	private String comment;
	
	/** The created by. */
	private String createdBy;
	
	/** The created on. */
	private Timestamp createdOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated on. */
	private Timestamp updatedOn;
	
	/** The activity log detail dto. */
	private PaymentInActivityLogDetailDto activityLogDetailDto;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the time statmp.
	 *
	 * @return the time statmp
	 */
	public Timestamp getTimeStatmp() {
		return timeStatmp;
	}

	/**
	 * Gets the activity by.
	 *
	 * @return the activity by
	 */
	public String getActivityBy() {
		return activityBy;
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
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
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
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the time statmp.
	 *
	 * @param timeStatmp the new time statmp
	 */
	public void setTimeStatmp(Timestamp timeStatmp) {
		this.timeStatmp = timeStatmp;
	}

	/**
	 * Sets the activity by.
	 *
	 * @param activityBy the new activity by
	 */
	public void setActivityBy(String activityBy) {
		this.activityBy = activityBy;
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
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn the new updated on
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the payment in id.
	 *
	 * @return the payment in id
	 */
	public Integer getPaymentInId() {
		return paymentInId;
	}

	/**
	 * Sets the payment in id.
	 *
	 * @param paymentInId the new payment in id
	 */
	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	/**
	 * Gets the activity log detail dto.
	 *
	 * @return the activity log detail dto
	 */
	public PaymentInActivityLogDetailDto getActivityLogDetailDto() {
		return activityLogDetailDto;
	}

	/**
	 * Sets the activity log detail dto.
	 *
	 * @param activityLogDetailDto the new activity log detail dto
	 */
	public void setActivityLogDetailDto(PaymentInActivityLogDetailDto activityLogDetailDto) {
		this.activityLogDetailDto = activityLogDetailDto;
	}
}
