package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

import java.util.List;

/**
 * The Class Geography.
 * @author abhijeetg
 */
public class Geography {

	/** The queue records per country. */
	private List<QueueRecordsPerCountry>  queueRecordsPerCountry;

	/**
	 * Gets the queue records per country.
	 *
	 * @return the queue records per country
	 */
	public List<QueueRecordsPerCountry> getQueueRecordsPerCountry() {
		return queueRecordsPerCountry;
	}

	/**
	 * Sets the queue records per country.
	 *
	 * @param queueRecordsPerCountry the new queue records per country
	 */
	public void setQueueRecordsPerCountry(List<QueueRecordsPerCountry> queueRecordsPerCountry) {
		this.queueRecordsPerCountry = queueRecordsPerCountry;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Geography [queueRecordsPerCountry=" + queueRecordsPerCountry + "]";
	}
}
