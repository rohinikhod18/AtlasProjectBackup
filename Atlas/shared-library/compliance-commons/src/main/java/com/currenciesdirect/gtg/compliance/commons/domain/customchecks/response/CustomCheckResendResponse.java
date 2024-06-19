package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CustomCheckResendResponse.
 */
public class CustomCheckResendResponse extends BaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response. */
	@ApiModelProperty(value = "The response object", required = true)
	private CustomCheckResponse response;

	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;

	/** The checked on. */
	@ApiModelProperty(value = "The checked on date", required = true)
	private String checkedOn;

	/** The activity logs. */
	@ApiModelProperty(value = "An array of activity logs", required = true)
	private ActivityLogs activityLogs;

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public CustomCheckResponse getResponse() {
		return response;
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
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	public void setResponse(CustomCheckResponse response) {
		this.response = response;
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
	 * Gets the event service log id.
	 *
	 * @return the event service log id
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * Gets the checked on.
	 *
	 * @return the checked on
	 */
	public String getCheckedOn() {
		return checkedOn;
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
	 * Sets the checked on.
	 *
	 * @param checkedOn
	 *            the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

}
