/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

import java.util.List;

/**
 * The Class IpResponse.
 *
 * @author manish
 */
public class IpResponse {

	/** The unit. */
	private String unit;

	/** The geo difference. */
	private String geoDifference;

	/** The postcode. */
	private String postcode;

	/** The anonymizer status. */
	private String anonymizerStatus;

	/** The status. */
	private String status;

	/** The reg city. */
	private String regCity;

	/** The ip city. */
	private String ipCity;

	/** The reg country. */
	private String regCountry;

	/** The ip country. */
	private String ipCountry;

	/** The ip rounting type. */
	private String ipRountingType;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	private List<IpRule> checks;

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Gets the geo difference.
	 *
	 * @return the geo difference
	 */
	public String getGeoDifference() {
		return geoDifference;
	}

	/**
	 * Gets the postcode.
	 *
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Gets the anonymizer status.
	 *
	 * @return the anonymizer status
	 */
	public String getAnonymizerStatus() {
		return anonymizerStatus;
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
	 * Gets the ip city.
	 *
	 * @return the ip city
	 */
	public String getIpCity() {
		return ipCity;
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
	 * Gets the ip country.
	 *
	 * @return the ip country
	 */
	public String getIpCountry() {
		return ipCountry;
	}

	/**
	 * Gets the ip rounting type.
	 *
	 * @return the ip rounting type
	 */
	public String getIpRountingType() {
		return ipRountingType;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit
	 *            the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Sets the geo difference.
	 *
	 * @param geoDifference
	 *            the new geo difference
	 */
	public void setGeoDifference(String geoDifference) {
		this.geoDifference = geoDifference;
	}

	/**
	 * Sets the postcode.
	 *
	 * @param postcode
	 *            the new postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Sets the anonymizer status.
	 *
	 * @param anonymizerStatus
	 *            the new anonymizer status
	 */
	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	/**
	 * Sets the reg city.
	 *
	 * @param regCity
	 *            the new reg city
	 */
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ipCity
	 *            the new ip city
	 */
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	/**
	 * Sets the reg country.
	 *
	 * @param regCountry
	 *            the new reg country
	 */
	public void setRegCountry(String regCountry) {
		this.regCountry = regCountry;
	}

	/**
	 * Sets the ip country.
	 *
	 * @param ipCountry
	 *            the new ip country
	 */
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}

	/**
	 * Sets the ip rounting type.
	 *
	 * @param ipRountingType
	 *            the new ip rounting type
	 */
	public void setIpRountingType(String ipRountingType) {
		this.ipRountingType = ipRountingType;
	}

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
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public List<IpRule> getChecks() {
		return checks;
	}

	public void setChecks(List<IpRule> checks) {
		this.checks = checks;
	}
}
