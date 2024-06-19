package com.currenciesdirect.gtg.compliance.util;

/**
 * The Enum ExternalErrorCodes.
 * 
 * @author abhijeetg
 */
public enum ExternalErrorCodeEnum {
	
	/** The ip service country not supported. */
	IP_SERVICE_COUNTRY_NOT_SUPPORTED("IP0001","Country is not supported"),
	
	/** The ip service invalid post code. */
	IP_SERVICE_INVALID_POST_CODE("IP0002","Given post code in invalid"),
	
	/** The ip service invalid ip. */
	IP_SERVICE_INVALID_IP("IP0003", "IP format is invalid"),
	
	/** The ip service ipservice parsing error. */
	IP_SERVICE_IPSERVICE_PARSING_ERROR("IP0004","Error while parsing from JSON to JAVA object"),
	
	/** The ip service ipservice mapping error. */
	IP_SERVICE_IPSERVICE_MAPPING_ERROR("IP0005","Error while mapping from JSON to JAVA object"),
	
	/** The ip service error while transforming ip provider response. */
	IP_SERVICE_ERROR_WHILE_TRANSFORMING_IP_PROVIDER_RESPONSE("IP0006","Error while tranforming ip provider response"),
	
	/** The ip service invalid longitude and latitude. */
	IP_SERVICE_INVALID_LONGITUDE_AND_LATITUDE("IP0007","Invalid longitude & latitude from ip provider response"),
	
	/** The ip service generic exception. */
	IP_SERVICE_GENERIC_EXCEPTION("IP1011","Generic Exception");

	
	/** variable for error code. */
	private String errorCode;
	
	/** variable for error code. */
	private String errorDescription;

	/**
	 * Instantiates a new external error codes.
	 *
	 * @param errorCode the error code
	 * @param errorDescription the error description
	 */
	private ExternalErrorCodeEnum(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * Gets the error description.
	 *
	 * @return error description
	 */
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * Gets the error code.
	 *
	 * @return error code
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.errorCode + ":" + this.errorDescription;
	}
}
