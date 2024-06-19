package com.currenciesdirect.gtg.compliance.compliancesrv;

public enum ExternalErrorCodes {

	SANCTION_ERROR_WHILE_TRANSFORMATION_REQUEST("SN0003", "Error while request transformation"),

	SANCTION_ERROR_WHILE_TRANSFORMATION_RESPONSE("SN0005", "Error while response transformation"),
	
	SANCTION_VALIDATION_EXCEPTION("SN0004","Some mandatory parameters are missing"),
	
	FRAUGSTER_ERROR_WHILE_TRANSFORMATION_REQUEST("SN0003", "Error while request transformation"),

	FRAUGSTER_ERROR_WHILE_TRANSFORMATION_RESPONSE("SN0005", "Error while response transformation"),
	
	FRAUGSTER_VALIDATION_EXCEPTION("SN0004","Some mandatory parameters are missing");
	
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
	private ExternalErrorCodes(String errorCode, String errorDescription) {
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
