package com.currenciesdirect.gtg.compliance.core.blacklist.payrefport;

/**
 * The Class BlacklistPayrefProviderProperty.
 */
public class BlacklistPayrefProviderProperty 
{
	
	/** The end point url. */
	private String endPointUrl;
	
	/** The requestType. */
	private String requestType;
	
	/** The fixed threshold. */
	private int fixedThreshold;

	/**
	 * Gets the end point url.
	 *
	 * @return the end point url
	 */
	public String getEndPointUrl() {
		return endPointUrl;
	}

	/**
	 * Sets the end point url.
	 *
	 * @param endPointUrl the new end point url
	 */
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the fixed threshold.
	 *
	 * @return the fixed threshold
	 */
	public int getFixedThreshold() {
		return fixedThreshold;
	}

	/**
	 * Sets the fixed threshold.
	 *
	 * @param fixedThreshold the new fixed threshold
	 */
	public void setFixedThreshold(int fixedThreshold) {
		this.fixedThreshold = fixedThreshold;
	}

	
	
}
