/**
 * 
 */
package com.currenciesdirect.gtg.compliance.customchecks.exception;

/**
 * @author rajesh
 *
 */
public enum CustomChecksErrors {
	
	/**
	 * SUCCESS Response
	 */
	SUCCESS("0000", "Success"),

	/**
	 * FAILED Response
	 */
	FAILED("9999", "Service Error"),
	ERROR_INVALID_REQUEST("ES0111","Invalid Request"),
	ERROR_WHILE_SENDING_REQUEST("ES0001","Error while sending request to server"),
	ERROR_WHILE_INSERT_REQUEST ("ES0010","Error while inserting customer"),
	ERROR_WHILE_INSERT_REQUEST_ALREADY_PRESENT("ES0011","Customer already present"),
	ERROR_WHILE_UPDATE_REQUEST_NOT_FOUND("ES0020","Customer not found"),
	ERROR_WHILE_UPDATE_REQUEST("ES0021","Error while updating customer"),
	ERROR_WHILE_DELETE_REQUEST("ES0030","Error while deleting customer"),
	ERROR_WHILE_DELETE_REQUEST_NOT_FOUND("ES0031","Customer not found"),
	ERROR_WHILE_SEARCH_REQUEST("ES0040","Error while searching customer"),
	ERROR_WHILE_VALIDATING_DATA("ES0002","Eroor while validating customer"),
	ERROR_WHILE_DATABASE_CONNECTION("ES0003","Eroor while database connection"),
	ERROR_WHILE_PARSING_RESPONSE("ES0004","Eroor while parsing response"),
	ERROR_WHILE_DATABASE_QUERY_EXECUTION("ES0005","Eroor while database query execution"),
	ERROR_WHILE_VALIDATION("ES0006","Customer Data is null"),
	ERROR_WHILE_LOADING_CACHE_DATA("ES007","Error while loading cache data"),
	ERROR_WHILE_FETCHING_CACHE_DATA("ES008","Error while fetching cache data"),
	ERROR_WHILE_REFRESHING_VELOCITY_TIMEOUT("ES009","Error while refreshing velocity timeout"),
	ERROR_WHILE_INSERTING_DATA("ES010","Error while creating document"),
	ERROR_WHILE_UPDATING_DATA("ES011","Error while updating document"),
	ERROR_WHILE_UPSERTING_DATA("ES012","Error while upserting document"),
	ERROR_NOT_FOUND("ES013","Not Found");
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
	private CustomChecksErrors(String errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * @return error description
	 */
	public String getErrorDescription() {
		return this.errorDescription;
	}

	/**
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
