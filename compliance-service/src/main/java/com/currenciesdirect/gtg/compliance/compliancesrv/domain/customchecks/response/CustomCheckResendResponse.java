package com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class CustomCheckResendResponse.
 */
public class CustomCheckResendResponse extends BaseResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "The response object", required = true)
	private CustomCheckResponse response;
	
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;

	@ApiModelProperty(value = "The checked on date", required = true)
	private String checkedOn;
	
	@ApiModelProperty(value = "An array of activity logs", required = true)
	private ActivityLogs activityLogs;
	
	public CustomCheckResponse getResponse() {
		return response;
	}
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}
	public void setResponse(CustomCheckResponse response) {
		this.response = response;
	}
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}
	public String getCheckedOn() {
		return checkedOn;
	}
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}
	
}
