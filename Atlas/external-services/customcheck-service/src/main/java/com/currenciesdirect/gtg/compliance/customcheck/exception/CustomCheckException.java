package com.currenciesdirect.gtg.compliance.customcheck.exception;


public class CustomCheckException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private CustomCheckErrors customCheckErrors;
	
	private String description;
	
	private Throwable orgException;
	
	public static final String SUCCESSMESSAGE = "success";
	
	public static final String ERRORMESSAGE = "failed";
	
	public CustomCheckException(CustomCheckErrors customCheckErrors, Throwable orgException){
		super(customCheckErrors.getErrorCode(),orgException);
		this.customCheckErrors = customCheckErrors;
		this.description = customCheckErrors.getErrorDescription();
		this.orgException = orgException;
	}
	
	public CustomCheckException(CustomCheckErrors customCheckError){
		super(customCheckError.getErrorCode(),new Throwable(customCheckError.getErrorDescription()));
		this.customCheckErrors=customCheckError;
		this.description=customCheckError.getErrorDescription();
	}
	
	public CustomCheckException(CustomCheckErrors customCheckErrors,String description){
		super(customCheckErrors.getErrorCode(),new Throwable(description));
		this.customCheckErrors=customCheckErrors;
		this.description=description;
	}

	public CustomCheckErrors getCustomCheckErrors() {
		return customCheckErrors;
	}

	public void setCustomCheckErrors(CustomCheckErrors customCheckErrors) {
		this.customCheckErrors = customCheckErrors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String errordescription) {
		this.description = errordescription;
	}

	public Throwable getOrgException() {
		return orgException;
	}

	public void setOrgException(Throwable orgException) {
		this.orgException = orgException;
	}

}
