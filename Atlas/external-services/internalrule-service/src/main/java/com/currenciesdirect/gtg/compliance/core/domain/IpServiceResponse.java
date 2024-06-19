/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BaseIpResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class IpServiceResponse.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpServiceResponse extends BaseIpResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The distance. */
	private Double distance;

	/** The units. */
	private String units;

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance
	 *            the new distance
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * Sets the units.
	 *
	 * @param units
	 *            the new units
	 */
	public void setUnits(String units) {
		this.units = units;
	}

}
