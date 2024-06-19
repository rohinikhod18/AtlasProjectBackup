package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class ActivityLogRequest.
 */
public class ActivityLogRequest {

	private Integer minRecord;
	
	private Integer maxRecord;
	
	private Integer noOfRecords;
	
	private String orgCode;
	
	private String custType;
	
	private Integer entityId;
	
	private String requestType;
	
	private UserProfile user;
	
	private Integer accountId;
	
	private Integer rowToFetch;
	
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public Integer getMinRecord() {
		return minRecord;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public String getCustType() {
		return custType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setMinRecord(Integer minRecord) {
		this.minRecord = minRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getRowToFetch() {
		return rowToFetch;
	}

	public void setRowToFetch(Integer rowToFetch) {
		this.rowToFetch = rowToFetch;
	}
	
	
}
