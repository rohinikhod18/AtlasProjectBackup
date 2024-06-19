/**
 * 
 */
package com.currenciesdirect.gtg.compliance.exception;

/**
 * @author manish
 *
 */
public class IpException extends Exception{

	
	private static final long serialVersionUID = 1L;
	public static final String SUCCESSMESSAGE = "successMsg";

	
	private IpErrors ipErrors;
	
	private Throwable exception;
	

	/** The error description. */
	private String description;

	

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public IpException(IpErrors ipErrors){
		super(ipErrors.getErrorCode(),new Throwable(ipErrors.getErrorDescription()));
		this.ipErrors=ipErrors;
		this.description=ipErrors.getErrorDescription();
		this.exception = this;
	}
	
	
	public IpException(IpErrors ipErrors,String description){
		super(ipErrors.getErrorCode(),new Throwable(description));
		this.ipErrors=ipErrors;
		this.description=description;
		this.exception = this;
	}

	public IpException(IpErrors ipErrors, Throwable e){
		super(ipErrors.getErrorCode(),e);
		this.ipErrors=ipErrors;
		this.description=ipErrors.getErrorDescription();
		this.exception = e;
	}
	/**
	 * @return
	 */
	public IpErrors getIpErrors() {
		return ipErrors;
	}

	/**
	 * @param ipvalidationErrors
	 */
	public void setIpErrors(IpErrors ipErrors) {
		this.ipErrors = ipErrors;
	}
	
	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
