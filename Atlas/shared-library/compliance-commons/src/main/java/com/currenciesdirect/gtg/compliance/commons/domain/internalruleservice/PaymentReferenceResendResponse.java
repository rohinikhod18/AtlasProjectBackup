package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class BlacklistResendResponse.
 */
public class PaymentReferenceResendResponse extends BaseResponse{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", example = "751843", required = true)
	private Integer eventServiceLogId;
	
	/** The activity logs. */
	@ApiModelProperty(value = "Object containing activity logs", required = true)
	private ActivityLogs activityLogs;
	
	/** The summary. */
	@ApiModelProperty(value = "Object containing Payment Reference Summary", required = true)
	private BlacklistPayRefSummary summary;
	
	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public BlacklistPayRefSummary getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param blacklistPayRefSummary the new summary
	 */
	public void setSummary(BlacklistPayRefSummary blacklistPayRefSummary) {
		this.summary = blacklistPayRefSummary;
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