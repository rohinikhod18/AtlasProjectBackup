/**
 * 
 */
package com.currenciesdirect.gtg.compliance.exception.ip;

import com.currenciesdirect.gtg.compliance.exception.Errors;

/**
 * @author manish
 *
 */
public enum IpErrors implements Errors {
	
	/**
	 * SUCCESS Response
	 */
	SUCCESS("0000", "Success"),

	/**
	 * FAILED Response
	 */
	FAILED("9999", "Service Error"),

	/**
	 * DATABASE_GENERIC_ERROR
	 */
	DATABASE_GENERIC_ERROR("DB0001","Error while saving the data"),
	
	ERROR_WHILE_LOADING_PROVIDER_INIT_DATA("DB0002","Error while loading provider initialization data from database"),
	
	ERROR_WHILE_GETTING_POST_CODE_LOCATION("DB0002","Error while getting post code loacation data from database"),
	
	ERROR_WHILE_LOADING_CACHE("CH0001","Error loading cache init data"),
	
	ERROR_WHILE_POPULATING_OBJECT("OB0001","Error while populating object"),
	
	INVALID_REQUEST("IR0001","Invalid Request"),
	
	/**
	 * INVALID IP Address Validation Service Parameter
	 */
	
	IP_ADDRSSS_PARMETER_ERROR("IP1001", "Invalid parameter"),
	
	
	
	/**
	 * INVALID IP Address
	 */
	IP_GENERIC_EXCEPTION("IP1002","Generic Exception"),
	
	COUNTRY_NOT_SUPPORTED("IP0003","Country is not supported"),
	
	ERROR_WHILE_GENERATING_PROVIDER_URL("IP0004","Error while generating provider url"),
	
	ERROR_WHILE_CALCULATING_GEO_DIFFERENCE("IP0006","Error while calculating geo difference"),
	
	ERROR_WHILE_CALLING_IP_PROVIDER("IP0007","Error while calling  Ip service"),
	
	INVALID_POST_CODE("IP0008","Given post code in invalid"),
	
	INVALID_IP("IP0001", "IP format is invalid"),
	
	ERROR_WHILE_FETCHING_CACHE_DATA("CH0002","Error while fetching data from cache"),
	
	ERROR_WHILE_TRANSFORMING_IP_PROVIDER_RESPONSE("IP0005","Error while tranforming ip provider response");
	
	
	
	/**
	 * variable for error code
	 */
	private String errorCode;
	/**
	 * variable for error code
	 */
	private String errorDescription;

	/**
	 * @param errorCode
	 * @param errorDescription
	 */
	private IpErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * @return error description
	 */
	@Override
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
	 * @return error code
	 */
	@Override
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
