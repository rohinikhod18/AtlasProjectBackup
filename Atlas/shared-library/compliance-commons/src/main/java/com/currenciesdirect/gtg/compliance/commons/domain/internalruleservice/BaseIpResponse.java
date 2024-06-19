package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

/**
 * The Class BaseIpResponse.
 */
public class BaseIpResponse implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The error code. */
	protected String errorCode;

	/** The error description. */
	protected String errorDescription;

	/** The ip city. */
	protected String  ipCity;
	
	/** The ip country. */
	protected String  ipCountry;
	
	/** The ip rounting type. */
	protected String  ipRountingType;
	
	/** The ip latitude. */
	protected String  ipLatitude;
	
	/** The ip longitude. */
	protected String  ipLongitude;
	
	/** The post code latitude. */
	protected String  postCodeLatitude;
	
	/** The post code longitude. */
	protected String  postCodeLongitude;
	
	/** The anonymizer status. */
	protected String anonymizerStatus;

	/**
	 * Gets the error code.
	 *
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the ip city.
	 *
	 * @return the ipCity
	 */
	public String getIpCity() {
		return ipCity;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ipCity the ipCity to set
	 */
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	/**
	 * Gets the ip country.
	 *
	 * @return the ipCountry
	 */
	public String getIpCountry() {
		return ipCountry;
	}

	/**
	 * Sets the ip country.
	 *
	 * @param ipCountry the ipCountry to set
	 */
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}

	/**
	 * Gets the ip rounting type.
	 *
	 * @return the ipRountingType
	 */
	public String getIpRountingType() {
		return ipRountingType;
	}

	/**
	 * Sets the ip rounting type.
	 *
	 * @param ipRountingType the ipRountingType to set
	 */
	public void setIpRountingType(String ipRountingType) {
		this.ipRountingType = ipRountingType;
	}

	/**
	 * Gets the ip latitude.
	 *
	 * @return the ipLatitude
	 */
	public String getIpLatitude() {
		return ipLatitude;
	}

	/**
	 * Sets the ip latitude.
	 *
	 * @param ipLatitude the ipLatitude to set
	 */
	public void setIpLatitude(String ipLatitude) {
		this.ipLatitude = ipLatitude;
	}

	/**
	 * Gets the ip longitude.
	 *
	 * @return the ipLongitude
	 */
	public String getIpLongitude() {
		return ipLongitude;
	}

	/**
	 * Sets the ip longitude.
	 *
	 * @param ipLongitude the ipLongitude to set
	 */
	public void setIpLongitude(String ipLongitude) {
		this.ipLongitude = ipLongitude;
	}

	/**
	 * Gets the post code latitude.
	 *
	 * @return the postCodeLatitude
	 */
	public String getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude the postCodeLatitude to set
	 */
	public void setPostCodeLatitude(String postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the postCodeLongitude
	 */
	public String getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude the postCodeLongitude to set
	 */
	public void setPostCodeLongitude(String postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
	}

	/**
	 * Gets the anonymizer status.
	 *
	 * @return the anonymizerStatus
	 */
	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	/**
	 * Sets the anonymizer status.
	 *
	 * @param anonymizerStatus the anonymizerStatus to set
	 */
	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	
}
