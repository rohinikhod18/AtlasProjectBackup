/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * @author manish
 *
 */
public class ProviderResponseLogRequest {

	private Integer eventServiceLogId;
	
	private String serviceType;

	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}
