package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class LockResourceRequest.
 */
public class LockResourceRequest {

	/** The Id. */
	private Integer id;
	
	/** The lock. */
	private Boolean lock;
	
	/** The resource type. */
	private Integer userResourceId; 
	
	/** The resource type. */
	private String resourceType;
	
	/** The resource id. */
	private Integer resourceId;

	private UserProfile user;
	
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the lock
	 */
	public Boolean getLock() {
		return lock;
	}

	/**
	 * @return the userResourceId
	 */
	public Integer getUserResourceId() {
		return userResourceId;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param lock the lock to set
	 */
	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	/**
	 * @param userResourceId the userResourceId to set
	 */
	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	
}
