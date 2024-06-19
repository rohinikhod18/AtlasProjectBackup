package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;

public class PaymentOutActivityLogDetailDto {
	
	private Integer id;
	
	private Integer activityId;
	
	private ActivityType activityType;
	
	private String log;
	
	private String createdBy;
	
	private Timestamp createdOn;
	
	private String updatedBy;
	
	private Timestamp updatedOn;

	public Integer getId() {
		return id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public String getLog() {
		return log;
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

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public void setLog(String log) {
		this.log = log;
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
	

}
