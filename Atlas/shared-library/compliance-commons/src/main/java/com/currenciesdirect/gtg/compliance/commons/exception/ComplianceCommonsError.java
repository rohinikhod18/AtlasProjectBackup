package com.currenciesdirect.gtg.compliance.commons.exception;

public enum ComplianceCommonsError {
	
	/** The keycloak token creation error. */
	KEYCLOAK_TOKEN_CREATION_ERROR("KCT001","Keycloak Token Creation Error"),
	
	KEYCLOAK_TOKEN_PROPERTY_ERROR("KCT002","Keycloak Token Property Error"),
	
	/** The unauthorised user. */
	UNAUTHORISED_USER("CPUU00001","Unauthorised user"),
	
	/** FAILED Response. */
	FAILED("9999", "Service Error");
	
	/** The error code. */
	private String errorCode;
	
	/** The error description. */
	private String errorDescription;
	
	private ComplianceCommonsError(String errorCode,String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;		
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	protected void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	protected void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
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
