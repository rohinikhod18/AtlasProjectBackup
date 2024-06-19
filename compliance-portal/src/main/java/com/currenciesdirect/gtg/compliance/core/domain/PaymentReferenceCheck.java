package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

public class PaymentReferenceCheck implements IDomain {

	private String checkedOn;
	
	private String paymentReference;
	
	private String matchedKeyword;
	
	private int closenessScore;
	
	private String overallStatus;
	
	private Integer totalRecords;
	
	private Boolean isRequired;
	
	private Integer passCount;
	
	private Integer failCount;
	
	private String entityType;
	
	private Integer id;
	
	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public String getCheckedOn() {
		return checkedOn;
	}

	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getMatchedKeyword() {
		return matchedKeyword;
	}

	public void setMatchedKeyword(String matchedKeyword) {
		this.matchedKeyword = matchedKeyword;
	}

	public int getClosenessScore() {
		return closenessScore;
	}

	public void setClosenessScore(int closenessScore) {
		this.closenessScore = closenessScore;
	}

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
