/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * @author manish
 *
 */
public class IpResponseData implements IDomain{

	private String id;
	private String orgCode;
    private String sourceApplication;
    private String auroraAccountId;
    private String auroraContactId;
    private String unit;
    private String geoDifference;
    private String postcode;
    private String anonymizerStatus;
	private String overAllStatus;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getGeoDifference() {
		return geoDifference;
	}

	public void setGeoDifference(String geoDifference) {
		this.geoDifference = geoDifference;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	public String getOverAllStatus() {
		return overAllStatus;
	}

	public void setOverAllStatus(String overAllStatus) {
		this.overAllStatus = overAllStatus;
	}

}
