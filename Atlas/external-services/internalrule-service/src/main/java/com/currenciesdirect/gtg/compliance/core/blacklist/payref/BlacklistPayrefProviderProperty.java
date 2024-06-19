package com.currenciesdirect.gtg.compliance.core.blacklist.payref;


public class BlacklistPayrefProviderProperty 
{
	
	/** The enpoint url. */
	private String endPointUrl;
	
	/** The requestType. */
	private String requestType;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getEndPointUrl() {
		return endPointUrl;
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
}
