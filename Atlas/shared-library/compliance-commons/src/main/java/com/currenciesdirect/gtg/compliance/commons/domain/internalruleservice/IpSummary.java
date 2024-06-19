package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.util.List;

/**
 * The Class IpSummary.
 */
public class IpSummary extends BaseIpResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;
	
	/** The ip address. */
	private String ipAddress;
	
	/** The geo difference. */
	private Double geoDifference;
	
	/** The unit. */
	private String unit;
	
	/** The postcode. */
	private String postcode;
	
	/** The reg city. */
	private String regCity;
	
	/** The reg country. */
	private String regCountry;
	
	/** The checks. */
	private List<IpCheck> checks;
	
	/**
	 * Gets the postcode.
	 *
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Sets the postcode.
	 *
	 * @param postcode the new postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Gets the reg city.
	 *
	 * @return the reg city
	 */
	public String getRegCity() {
		return regCity;
	}

	/**
	 * Sets the reg city.
	 *
	 * @param regCity the new reg city
	 */
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	/**
	 * Gets the reg country.
	 *
	 * @return the reg country
	 */
	public String getRegCountry() {
		return regCountry;
	}

	/**
	 * Sets the reg country.
	 *
	 * @param regCountry the new reg country
	 */
	public void setRegCountry(String regCountry) {
		this.regCountry = regCountry;
	}


	/**
	 * Gets the checks.
	 *
	 * @return the checks
	 */
	public List<IpCheck> getChecks() {
		return checks;
	}

	/**
	 * Sets the checks.
	 *
	 * @param checks the new checks
	 */
	public void setChecks(List<IpCheck> checks) {
		this.checks = checks;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Gets the geo difference.
	 *
	 * @return the geo difference
	 */
	public Double getGeoDifference() {
		return geoDifference;
	}

	/**
	 * Sets the geo difference.
	 *
	 * @param geoDifference the new geo difference
	 */
	public void setGeoDifference(Double geoDifference) {
		this.geoDifference = geoDifference;
	}

	/** The ip rule. */
	private List<IpCheck>  ipRule;
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * Gets the ip rule.
	 *
	 * @return the ip rule
	 */
	public List<IpCheck> getIpRule() {
		return ipRule;
	}
	
	/**
	 * Sets the ip rule.
	 *
	 * @param ipRule the new ip rule
	 */
	public void setIpRule(List<IpCheck> ipRule) {
		this.ipRule = ipRule;
	}
	
}
