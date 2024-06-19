package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class FulfilmentGraph.
 * @author abhijeetg
 */
public class FulfilmentGraph {
	
	/** The title. */
	private String title;
	
	/** The value. */
	private Integer value;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return "FulfilmentGraph [title=" + title + ", value=" + value + "]";
	}
	
}
