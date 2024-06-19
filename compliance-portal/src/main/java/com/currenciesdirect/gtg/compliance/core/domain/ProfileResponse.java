/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;


/**
 * @author manish
 *
 */
public class ProfileResponse {

	private String responseCode;
	private String responseDescription;
	private String reasonForInactive;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public String getReasonForInactive() {
		return reasonForInactive;
	}
	public void setReasonForInactive(String reasonForInactive) {
		this.reasonForInactive = reasonForInactive;
	}

}
