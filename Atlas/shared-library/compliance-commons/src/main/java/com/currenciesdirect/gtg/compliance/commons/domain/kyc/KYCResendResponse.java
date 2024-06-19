package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class KYCResendResponse.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KYCResendResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The summary. */
	private KYCSummary summary;

	/** The activity logs. */
	private ActivityLogs activityLogs;

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public KYCSummary getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary
	 *            the new summary
	 */
	public void setSummary(KYCSummary summary) {
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
	 * @param activityLogs
	 *            the new activity logs
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

}
