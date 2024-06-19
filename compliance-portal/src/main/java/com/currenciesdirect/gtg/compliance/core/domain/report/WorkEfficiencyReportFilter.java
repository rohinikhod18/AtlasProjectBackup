package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.util.Arrays;

/**
 * @author laxmib
 *
 */
public class WorkEfficiencyReportFilter {
	
	/** The date from. */
	private String dateFrom;
	
	/** The date to. */
	private String fromTo;

	/** The cust type. */
	private String[] custType;

	/** The queue type. */
	private String[] queueType;

	/** The user */
	private String[] user;

	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the fromTo
	 */
	public String getFromTo() {
		return fromTo;
	}

	/**
	 * @param fromTo the fromTo to set
	 */
	public void setFromTo(String fromTo) {
		this.fromTo = fromTo;
	}

	/**
	 * @return the custType
	 */
	public String[] getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String[] custType) {
		this.custType = custType;
	}

	/**
	 * @return the queueType
	 */
	public String[] getQueueType() {
		return queueType;
	}

	/**
	 * @param queueType the queueType to set
	 */
	public void setQueueType(String[] queueType) {
		this.queueType = queueType;
	}

	/**
	 * @return the user
	 */
	public String[] getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String[] user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkEfficiencyReportFilter [dateFrom=" + dateFrom + ", fromTo=" + fromTo + ", custType="
				+ Arrays.toString(custType) + ", queueType=" + Arrays.toString(queueType) + ", user="
				+ Arrays.toString(user) + "]";
	}
	
	
}
