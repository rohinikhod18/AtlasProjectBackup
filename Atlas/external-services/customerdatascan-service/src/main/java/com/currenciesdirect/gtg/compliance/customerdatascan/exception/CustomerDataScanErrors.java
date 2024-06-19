/**
 * 
 */
package com.currenciesdirect.gtg.compliance.customerdatascan.exception;

/**
 * @author rajesh
 *
 */
public enum CustomerDataScanErrors {
	
	/**
	 * SUCCESS Response
	 */
	SUCCESS("0000", "Success"),

	/**
	 * FAILED Response
	 */
	FAILED("9999", "Service Error"),

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
	ERROR_WHILE_VALIDATION("ES0006","Customer Data is null");
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
	private CustomerDataScanErrors(String errorCode, String errorDescription) {
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
