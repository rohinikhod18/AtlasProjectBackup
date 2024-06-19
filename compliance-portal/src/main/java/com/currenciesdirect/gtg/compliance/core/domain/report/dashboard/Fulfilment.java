package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

import java.util.List;

/**
 * The Class Fulfilment.
 * @author abhijeetg
 */
public class Fulfilment {
	
	/** The avg clearing time. */
	private Integer avgClearingTime;
	
	/** The avg per hour. */
	private Integer avgPerHour;
	
	/** The cleared today. */
	private Integer clearedToday;
	
	/** The from time. */
	private String fromTime;
	
	/** The fulfilment graph. */
	private List<FulfilmentGraph> fulfilmentGraph;

	/**
	 * Gets the avg clearing time.
	 *
	 * @return the avg clearing time
	 */
	public Integer getAvgClearingTime() {
		return avgClearingTime;
	}

	/**
	 * Sets the avg clearing time.
	 *
	 * @param avgClearingTime the new avg clearing time
	 */
	public void setAvgClearingTime(Integer avgClearingTime) {
		this.avgClearingTime = avgClearingTime;
	}

	/**
	 * Gets the avg per hour.
	 *
	 * @return the avg per hour
	 */
	public Integer getAvgPerHour() {
		return avgPerHour;
	}

	/**
	 * Sets the avg per hour.
	 *
	 * @param avgPerHour the new avg per hour
	 */
	public void setAvgPerHour(Integer avgPerHour) {
		this.avgPerHour = avgPerHour;
	}

	/**
	 * Gets the cleared today.
	 *
	 * @return the cleared today
	 */
	public Integer getClearedToday() {
		return clearedToday;
	}

	/**
	 * Sets the cleared today.
	 *
	 * @param clearedToday the new cleared today
	 */
	public void setClearedToday(Integer clearedToday) {
		this.clearedToday = clearedToday;
	}

	/**
	 * Gets the from time.
	 *
	 * @return the from time
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * Sets the from time.
	 *
	 * @param fromTime the new from time
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * Gets the fulfilment graph.
	 *
	 * @return the fulfilment graph
	 */
	public List<FulfilmentGraph> getFulfilmentGraph() {
		return fulfilmentGraph;
	}

	/**
	 * Sets the fulfilment graph.
	 *
	 * @param fulfilmentGraph the new fulfilment graph
	 */
	public void setFulfilmentGraph(List<FulfilmentGraph> fulfilmentGraph) {
		this.fulfilmentGraph = fulfilmentGraph;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fulfilment [avgClearingTime=" + avgClearingTime + ", avgPerHour=" + avgPerHour + ", clearedToday="
				+ clearedToday + ", fromTime=" + fromTime + ", fulfilmentGraph=" + fulfilmentGraph + "]";
	}

}
