/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.exception;

/**
 * @author manish
 *
 */
public enum KYCErrors {
	
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
	
	ERROR_WHILE_LOADING_CACHE("CH0001","Error loading cache init data"),
	
	ERROR_WHILE_POPULATING_OBJECT("OB0001","Error while populating object"),
	
	ERROR_WHILE_SENDING_REQUEST_TO_LEXISNEXIS_KYC_PROVIDER("KYC0001","Lexis-Nexis Kyc provider error out"),
	
	ERROR_WHILE_SENDING_REQUEST_TO_CARBONSERVICE_KYC_PROVIDER("KYC0010","Carbon Service Kyc provider error out"),
	
	ERROE_WHILE_GETTING_KYC_PROVIDER_FOR_COUNTRY("KYC0002","error while getting provider for country"),
	
	INVALID_REQUEST("IR0001","Invalid Request"),
	
	INVALID_PROVIDER("KYC0007","Given provider is invalid"),
	
	MANDATORY_PARAMETERS_MISSING("KYC0008","Mandatory fields are missing in request"),
	
	VALIDATION_EXCEPTION("KYC0009","Error while validating kyc request"),
	
	KYC_GENERIC_EXCEPTION("KYC0003","KYC generic exception"),
	
	COUNTRY_IS_NOT_SUPPORTED("KYC004","Given country is not supported for this provider"),
	
	COUNTRY_NOT_SUPPORTED("KYC0005","Country is not supported"),
	
	ERROR_WHILE_FETCHING_CACHE_DATA("CH0001","Error while fetching kyc provider cnofig propertiers from cache"),
	
	ERROR_GENERATING_SECURITY_HEADER("KYC0006","Error while generating wsse security header"),
	
	ERROR_WHILE_SENDING_REQUEST_TO_GBGROUP_KYC_PROVIDER("KYC0007","GBGroup Error: ");
	
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
	private KYCErrors(String errorCode, String errorDescription) {
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
