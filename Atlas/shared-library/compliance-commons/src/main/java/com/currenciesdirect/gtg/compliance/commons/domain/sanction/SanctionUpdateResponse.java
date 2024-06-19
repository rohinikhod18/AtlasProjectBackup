package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class SanctionUpdateResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SanctionUpdateResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
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
	 * Gets the event service log id.
	 *
	 * @return the event service log id
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * Sets the event service log id.
	 *
	 * @param eventServiceLogId
	 *            the new event service log id
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId
	 *            the new event id
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * Gets the activity logs.
	 *
	 * @return the activity logs
	 */
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	/**
	 * Sets the activity logs.
	 *
	 * @param activityLogs
	 *            the new activity logs
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
