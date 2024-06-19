/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.ip;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author manish
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class IpDetailsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String  errorCode;

	private String  errorDescription;

	private String  longitude;

	private String  latitiude;
	
	private String anonymizerStatus;

	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitiude() {
		return latitiude;
	}

	public void setLatitiude(String latitiude) {
		this.latitiude = latitiude;
	}
}
