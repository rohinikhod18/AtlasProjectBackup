package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class FraugsterDetails.
 * 
 * @author abhijeetg
 */
public class FraugsterDetails {

	/** The fraugster. */
	private List<Fraugster> fraugster;

	/** The fraugster total records. */
	private Integer fraugsterTotalRecords;

	/**
	 * Gets the fraugster.
	 *
	 * @return the fraugster
	 */
	public List<Fraugster> getFraugster() {
		return fraugster;
	}

	/**
	 * Sets the fraugster.
	 *
	 * @param fraugster
	 *            the new fraugster
	 */
	public void setFraugster(List<Fraugster> fraugster) {
		this.fraugster = fraugster;
	}

	/**
	 * Gets the fraugster total records.
	 *
	 * @return the fraugster total records
	 */
	public Integer getFraugsterTotalRecords() {
		return fraugsterTotalRecords;
	}

	/**
	 * Sets the fraugster total records.
	 *
	 * @param fraugsterTotalRecords
	 *            the new fraugster total records
	 */
	public void setFraugsterTotalRecords(Integer fraugsterTotalRecords) {
		this.fraugsterTotalRecords = fraugsterTotalRecords;
	}

}
