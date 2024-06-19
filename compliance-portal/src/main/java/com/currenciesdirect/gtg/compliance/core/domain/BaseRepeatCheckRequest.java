package com.currenciesdirect.gtg.compliance.core.domain;

public class BaseRepeatCheckRequest {
	
	/** The module name. */
	private String moduleName;
	
	/** The date from. */
	private String dateFrom;
	
	/** The date to. */
	private String dateTo;
	
	private Integer batchId;

	private Integer transTypeInteger;
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the batchId
	 */
	public Integer getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the transTypeInteger
	 */
	public Integer getTransTypeInteger() {
		return transTypeInteger;
	}

	/**
	 * @param transTypeInteger the transTypeInteger to set
	 */
	public void setTransTypeInteger(Integer transTypeInteger) {
		this.transTypeInteger = transTypeInteger;
	}



}
