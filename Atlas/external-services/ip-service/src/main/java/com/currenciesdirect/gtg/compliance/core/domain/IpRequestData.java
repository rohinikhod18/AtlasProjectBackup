package com.currenciesdirect.gtg.compliance.core.domain;


/**
 * @author manish.
 *
 */
public class IpRequestData {
	private String id;
	private String ipAddress;
	private String orgCode;
	private String country;
	private String postCode;
	private String correlationID;
	private String requestType;
    private String sourceApplication;
    private String auroraAccountId;
    private String auroraContactId;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSourceApplication() {
		return sourceApplication;
	}

	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	public String getAuroraAccountId() {
		return auroraAccountId;
	}

	public void setAuroraAccountId(String auroraAccountId) {
		this.auroraAccountId = auroraAccountId;
	}

	public String getAuroraContactId() {
		return auroraContactId;
	}

	public void setAuroraContactId(String auroraContactId) {
		this.auroraContactId = auroraContactId;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
}
