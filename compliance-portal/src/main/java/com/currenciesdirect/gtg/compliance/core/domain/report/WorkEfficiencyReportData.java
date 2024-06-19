package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.util.Date;

/**
 * The Class WorkEfficiencyReportData.
 * 
 * @author laxmib
 *
 */
public class WorkEfficiencyReportData {
	
	/** The user name. */
	private String userName;
	
	/** The queue type. */
	private String queueType; 
	
	/** The account type. */
	private String accountType;
	
	/** The locked records. */
	private Integer lockedRecords;
	
	/** The released records. */
	private Integer releasedRecords;
	
	/** The seconds. */
	private Double seconds;
	
	/** The date. */
	private Date date;
	
	/** The percent efficiency. */
	private Double percentEfficiency;
	
	/** The time efficiency. */
	private Double timeEfficiency;
	
	/** The total rows. */
	private Integer totalRows;
	
	/** The sla value. */
	private Double slaValue;
	

	public Double getSlaValue() {
		return slaValue;
	}

	public void setSlaValue(Double slaValue) {
		this.slaValue = slaValue;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the queue type.
	 *
	 * @return the queueType
	 */
	public String getQueueType() {
		return queueType;
	}

	/**
	 * Sets the queue type.
	 *
	 * @param queueType the queueType to set
	 */
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	/**
	 * Gets the account type.
	 *
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * Sets the account type.
	 *
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * Gets the locked records.
	 *
	 * @return the lockedRecords
	 */
	public Integer getLockedRecords() {
		return lockedRecords;
	}

	/**
	 * Sets the locked records.
	 *
	 * @param lockedRecords the lockedRecords to set
	 */
	public void setLockedRecords(Integer lockedRecords) {
		this.lockedRecords = lockedRecords;
	}

	/**
	 * Gets the released records.
	 *
	 * @return the releasedRecords
	 */
	public Integer getReleasedRecords() {
		return releasedRecords;
	}

	/**
	 * Sets the released records.
	 *
	 * @param releasedRecords the releasedRecords to set
	 */
	public void setReleasedRecords(Integer releasedRecords) {
		this.releasedRecords = releasedRecords;
	}
	
	/**
	 * Gets the seconds.
	 *
	 * @return the seconds
	 */
	public Double getSeconds() {
		return seconds;
	}

	/**
	 * Sets the seconds.
	 *
	 * @param seconds the seconds to set
	 */
	public void setSeconds(Double seconds) {
		this.seconds = seconds;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the percent efficiency.
	 *
	 * @return the percentEfficiency
	 */
	public Double getPercentEfficiency() {
		return percentEfficiency;
	}

	/**
	 * Sets the percent efficiency.
	 *
	 * @param percentEfficiency2 the new percent efficiency
	 */
	public void setPercentEfficiency(Double percentEfficiency2) {
		this.percentEfficiency = percentEfficiency2;
	}

	/**
	 * Gets the time efficiency.
	 *
	 * @return the timeEfficiency
	 */
	public Double getTimeEfficiency() {
		return timeEfficiency;
	}

	/**
	 * Sets the time efficiency.
	 *
	 * @param timeEfficiency the timeEfficiency to set
	 */
	public void setTimeEfficiency(Double timeEfficiency) {
		this.timeEfficiency = timeEfficiency;
	}

	/**
	 * Gets the total rows.
	 *
	 * @return the total rows
	 */
	public Integer getTotalRows() {
		return totalRows;
	}

	/**
	 * Sets the total rows.
	 *
	 * @param totalRows the total rows
	 */
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	
}
