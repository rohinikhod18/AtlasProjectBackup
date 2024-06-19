package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.IpCheck;

/**
 * The Class IpSummary.
 */
public class IpSummary {

	/** The status. */
	private String status;
	
	/** The ip address. */
	private String ipAddress;
	
	/** The ip city. */
	private String ipCity;
	
	/** The ip country. */
	private String ipCountry;
	
	/** The geo difference. */
	private String geoDifference;
	
	/** The unit. */
	private String unit;
	
	/** The postcode. */
	private String postcode;

	/** The anonymizer status. */
	private String anonymizerStatus;
	
	/** The reg city. */
	private String regCity;
	
	/** The reg country. */
	private String regCountry;
	
	/** The ip rounting type. */
	private String ipRountingType;

	/** The ip latitude. */
	private String ipLatitude;

	/** The ip longitude. */
	private String ipLongitude;

	/** The post code latitude. */
	private String postCodeLatitude;

	/** The post code longitude. */
	private String postCodeLongitude;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/** The checks. */
	private List<IpCheck> checks;
	
	

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
	 * Gets the anonymizer status.
	 *
	 * @return the anonymizer status
	 */
	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	/**
	 * Sets the anonymizer status.
	 *
	 * @param anonymizerStatus the new anonymizer status
	 */
	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
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
	 * Gets the ip rounting type.
	 *
	 * @return the ip rounting type
	 */
	public String getIpRountingType() {
		return ipRountingType;
	}

	/**
	 * Sets the ip rounting type.
	 *
	 * @param ipRountingType the new ip rounting type
	 */
	public void setIpRountingType(String ipRountingType) {
		this.ipRountingType = ipRountingType;
	}

	/**
	 * Gets the ip latitude.
	 *
	 * @return the ip latitude
	 */
	public String getIpLatitude() {
		return ipLatitude;
	}

	/**
	 * Sets the ip latitude.
	 *
	 * @param ipLatitude the new ip latitude
	 */
	public void setIpLatitude(String ipLatitude) {
		this.ipLatitude = ipLatitude;
	}

	/**
	 * Gets the ip longitude.
	 *
	 * @return the ip longitude
	 */
	public String getIpLongitude() {
		return ipLongitude;
	}

	/**
	 * Sets the ip longitude.
	 *
	 * @param ipLongitude the new ip longitude
	 */
	public void setIpLongitude(String ipLongitude) {
		this.ipLongitude = ipLongitude;
	}

	/**
	 * Gets the post code latitude.
	 *
	 * @return the post code latitude
	 */
	public String getPostCodeLatitude() {
		return postCodeLatitude;
	}

	/**
	 * Sets the post code latitude.
	 *
	 * @param postCodeLatitude the new post code latitude
	 */
	public void setPostCodeLatitude(String postCodeLatitude) {
		this.postCodeLatitude = postCodeLatitude;
	}

	/**
	 * Gets the post code longitude.
	 *
	 * @return the post code longitude
	 */
	public String getPostCodeLongitude() {
		return postCodeLongitude;
	}

	/**
	 * Sets the post code longitude.
	 *
	 * @param postCodeLongitude the new post code longitude
	 */
	public void setPostCodeLongitude(String postCodeLongitude) {
		this.postCodeLongitude = postCodeLongitude;
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
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	 * Sets the error description.
	 *
	 * @param errorDescription the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
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
	 * Gets the ip city.
	 *
	 * @return the ip city
	 */
	public String getIpCity() {
		return ipCity;
	}

	/**
	 * Sets the ip city.
	 *
	 * @param ipCity the new ip city
	 */
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
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
	 * Sets the ip country.
	 *
	 * @param ipCountry the new ip country
	 */
	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
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
	 * Sets the geo difference.
	 *
	 * @param geoDifference the new geo difference
	 */
	public void setGeoDifference(String geoDifference) {
		this.geoDifference = geoDifference;
	}

	/** The ip rule. */
	private List<IpRule> ipRule;

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
	 * @param ipAddress
	 *            the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the ip rule.
	 *
	 * @return the ip rule
	 */
	public List<IpRule> getIpRule() {
		return ipRule;
	}

	/**
	 * Sets the ip rule.
	 *
	 * @param ipRule
	 *            the new ip rule
	 */
	public void setIpRule(List<IpRule> ipRule) {
		this.ipRule = ipRule;
	}

}
