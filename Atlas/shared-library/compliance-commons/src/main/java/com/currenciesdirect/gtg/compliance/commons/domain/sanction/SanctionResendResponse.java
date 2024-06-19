package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class SanctionResendResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SanctionResendResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The event service log id. */
	private Integer eventServiceLogId;
	
	/** The summary. */
	private SanctionSummary summary;
	
	/** The activity logs. */
	private ActivityLogs activityLogs;
	
	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public SanctionSummary getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(SanctionSummary summary) {
		this.summary = summary;
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
	 * @param activityLogs the new activity logs
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

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
	 * @param eventServiceLogId the new event service log id
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

}
