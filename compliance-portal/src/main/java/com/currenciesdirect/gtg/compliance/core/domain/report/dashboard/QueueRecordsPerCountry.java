package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class QueueRecordsPerCountry.
 * @author abhijeetg
 */
public class QueueRecordsPerCountry {
	
	/** The country. */
	private String id;
	
	/** The records per country. */
	private Integer value;
	
	/** The country name. */
	private String countryName;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	
	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName the new country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueueRecordsPerCountry [id=" + id + ", value=" + value + ", countryName=" + countryName + "]";
	}

}
