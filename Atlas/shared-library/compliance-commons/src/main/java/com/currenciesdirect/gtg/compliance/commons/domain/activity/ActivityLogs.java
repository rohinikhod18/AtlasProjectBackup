package com.currenciesdirect.gtg.compliance.commons.domain.activity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ActivityLogs.
 */
public class ActivityLogs implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The activity log data. */
	@ApiModelProperty(value = "An array of activity log data", required = true)
	private List<ActivityLogData> activityLogData;

	/**
	 * Gets the activity log data.
	 *
	 * @return the activity log data
	 */
	public List<ActivityLogData> getActivityLogData() {
		return activityLogData;
	}

	/**
	 * Sets the activity log data.
	 *
	 * @param activityLogData
	 *            the new activity log data
	 */
	public void setActivityLogData(List<ActivityLogData> activityLogData) {
		this.activityLogData = activityLogData;
	}

}
