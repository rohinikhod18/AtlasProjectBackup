/**
 * 
 */
package com.currenciesdirect.gtg.compliance.authentication.exception;

/**
 * @author manish
 *
 */
public class AuthenticationException extends Exception{

	
	private static final long serialVersionUID = 1L;
	public static final String SUCCESSMESSAGE = "successMsg";

	
	private Errorname errorname;
	

	/** The error description. */
	private String description;
	
	private Throwable exception;

	

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

	public AuthenticationException(Errorname errorname){
		super(errorname.getErrorCode(),new Throwable(errorname.getErrorDescription()));
		this.errorname=errorname;
		this.description=errorname.getErrorDescription();
	}
	
	public AuthenticationException(Errorname errorname, Throwable e){
		super(errorname.getErrorCode(),e);
		this.errorname=errorname;
		this.description=errorname.getErrorDescription();
		this.exception = e;
	}
	
	
	public AuthenticationException(Errorname errorname,String description){
		super(errorname.getErrorCode(),new Throwable(description));
		this.errorname=errorname;
		this.description=description;
	}


	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Errorname getErrorname() {
		return errorname;
	}

	public void setErrorname(Errorname errorname) {
		this.errorname = errorname;
	}
}
