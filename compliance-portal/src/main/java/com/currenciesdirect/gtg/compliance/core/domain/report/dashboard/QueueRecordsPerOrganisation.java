package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class QueueRecordsPerOrganisation.
 * @author abhijeetg
 */
public class QueueRecordsPerOrganisation {

	/** The organisation. */
	private String country;
	
	/** The records per org. */
	private Integer visits;

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the visits.
	 *
	 * @return the visits
	 */
	public Integer getVisits() {
		return visits;
	}

	/**
	 * Sets the visits.
	 *
	 * @param visits the new visits
	 */
	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueueRecordsPerOrganisation [country=" + country + ", visits=" + visits + "]";
	}

}
