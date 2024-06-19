package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

public class LockResourceDBDto {
	
	/** The resource type.  userResourceID*/
	private Integer id; 
	
	/** The user id. */
	private String userId;
	
	/** The resource type. */
	private String resourceType;
	
	/** The resource id. */
	private Integer resourceId;
	
	/** The lock released on. */
	private Timestamp lockReleasedOn;

	/** The created by. */
	private String createdBy;
	
	/** The created on. */
	private Timestamp createdOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Timestamp getLockReleasedOn() {
		return lockReleasedOn;
	}

	public void setLockReleasedOn(Timestamp lockReleasedOn) {
		this.lockReleasedOn = lockReleasedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

}
