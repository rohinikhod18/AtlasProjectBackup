package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.sql.Timestamp;

public class PaymentOutActivityLogDto {

	private Integer id;
	
	private Timestamp timeStatmp;
	
	private String activityBy;
	
	private String orgCode;
	
	private Integer accountId;
	
	private Integer paymentOutId;
	
	private String comment;
	
	private String createdBy;
	
	private Timestamp createdOn;
	
	private String updatedBy;
	
	private Timestamp updatedOn;
	
	private PaymentOutActivityLogDetailDto activityLogDetailDto;
	
	public Integer getId() {
		return id;
	}

	public Timestamp getTimeStatmp() {
		return timeStatmp;
	}

	public String getActivityBy() {
		return activityBy;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public String getComment() {
		return comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTimeStatmp(Timestamp timeStatmp) {
		this.timeStatmp = timeStatmp;
	}

	public void setActivityBy(String activityBy) {
		this.activityBy = activityBy;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public PaymentOutActivityLogDetailDto getActivityLogDetailDto() {
		return activityLogDetailDto;
	}

	public void setActivityLogDetailDto(PaymentOutActivityLogDetailDto activityLogDetailDto) {
		this.activityLogDetailDto = activityLogDetailDto;
	}
}
