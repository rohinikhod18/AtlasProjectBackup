package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class PaymentDashboard.
 * @author abhijeetg
 */
public class PaymentDashboard {

	/** The total records. */
	private Integer totalRecords;
	
	/** The business unit. */
	private BusinessUnit businessUnit;
	
	/** The fulfilment. */
	private Fulfilment fulfilment;
	
	/** The timeline snapshot. */
	private TimelineSnapshot timelineSnapshot;
	
	/** The fulfilment json string. */
	private String fulfilmentJsonString;
	
	/** The business unit json string. */
	private String businessUnitJsonString;

	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * Gets the business unit.
	 *
	 * @return the business unit
	 */
	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	/**
	 * Sets the business unit.
	 *
	 * @param businessUnit the new business unit
	 */
	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	/**
	 * Gets the fulfilment.
	 *
	 * @return the fulfilment
	 */
	public Fulfilment getFulfilment() {
		return fulfilment;
	}

	/**
	 * Sets the fulfilment.
	 *
	 * @param fulfilment the new fulfilment
	 */
	public void setFulfilment(Fulfilment fulfilment) {
		this.fulfilment = fulfilment;
	}

	/**
	 * Gets the timeline snapshot.
	 *
	 * @return the timeline snapshot
	 */
	public TimelineSnapshot getTimelineSnapshot() {
		return timelineSnapshot;
	}

	/**
	 * Sets the timeline snapshot.
	 *
	 * @param timelineSnapshot the new timeline snapshot
	 */
	public void setTimelineSnapshot(TimelineSnapshot timelineSnapshot) {
		this.timelineSnapshot = timelineSnapshot;
	}

	/**
	 * Gets the fulfilment json string.
	 *
	 * @return the fulfilment json string
	 */
	public String getFulfilmentJsonString() {
		return fulfilmentJsonString;
	}

	/**
	 * Sets the fulfilment json string.
	 *
	 * @param fulfilmentJsonString the new fulfilment json string
	 */
	public void setFulfilmentJsonString(String fulfilmentJsonString) {
		this.fulfilmentJsonString = fulfilmentJsonString;
	}

	/**
	 * Gets the business unit json string.
	 *
	 * @return the business unit json string
	 */
	public String getBusinessUnitJsonString() {
		return businessUnitJsonString;
	}

	/**
	 * Sets the business unit json string.
	 *
	 * @param businessUnitJsonString the new business unit json string
	 */
	public void setBusinessUnitJsonString(String businessUnitJsonString) {
		this.businessUnitJsonString = businessUnitJsonString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentDashboard [totalRecords=" + totalRecords + ", businessUnit=" + businessUnit + ", fulfilment="
				+ fulfilment + ", timelineSnapshot=" + timelineSnapshot + ", fulfilmentJsonString="
				+ fulfilmentJsonString + ", businessUnitJsonString=" + businessUnitJsonString + "]";
	}
	
}
