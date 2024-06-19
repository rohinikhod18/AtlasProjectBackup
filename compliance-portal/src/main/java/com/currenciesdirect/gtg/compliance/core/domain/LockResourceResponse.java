package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

/**
 * The Class LockResourceResponse.
 */
public class LockResourceResponse {
	
	/** The lock. */
	private Boolean lock;
	
	/** The status. */
	private String status;
	
	/** The contact id. */
	private Integer resourceId;
	
	/** The user resource id. */
	private Integer userResourceId;
	
	private Timestamp lockReleasedOn;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;
	
	/** The name. */
	private String name;

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getUserResourceId() {
		return userResourceId;
	}

	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lockReleasedOn
	 */
	public Timestamp getLockReleasedOn() {
		return lockReleasedOn;
	}

	/**
	 * @param lockReleasedOn the lockReleasedOn to set
	 */
	public void setLockReleasedOn(Timestamp lockReleasedOn) {
		this.lockReleasedOn = lockReleasedOn;
	}
	

}
