package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

public class OnfidoUpdateResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The activity logs. */
	private ActivityLogs activityLogs;

	/** The event service log id. */
	private Integer eventServiceLogId;

	/** The event id. */
	private Integer eventId;

	/** The status. */
	private String status;

	/**
	 * @return the activityLogs
	 */
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	/**
	 * @param activityLogs the activityLogs to set
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	/**
	 * @return the eventServiceLogId
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * @param eventServiceLogId the eventServiceLogId to set
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
