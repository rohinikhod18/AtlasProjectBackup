package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.internalrule.IpRule;

/**
 * The Class IpCheck.
 */
public class IpCheck {

	/** The id. */
	private Integer id;
	
	/** The entity type. */
	private String entityType;
	
	/** The ip address. */
	private String ipAddress;

	/** The ip rule. */
	private List<IpRule> ipRule;

	/** The checked on. */
	private String checkedOn;
	
	/** The status. */
	private String status;
	
	/** The ip check total records. */
	private Integer ipCheckTotalRecords;
	
	/** Added by Vishal J to check whether it is required to check or not. */
	private Boolean isRequired;
	
	/**Added by Vishal J to get the status value (Eg. PASS, FAIL, NOT_REQUIRED, NOT_PERFORMED)*/
	private String statusValue;
	
	/** The ip city. */
	private String ipCity;
	
	/** The ip country. */
	private String ipCountry;
	
	/** The geo difference. */
	private String geoDifference;

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

	/**
	 * Gets the checked on.
	 *
	 * @return the checked on
	 */
	public String getCheckedOn() {
		return checkedOn;
	}

	/**
	 * Sets the checked on.
	 *
	 * @param checkedOn
	 *            the new checked on
	 */
	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
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
	 * Gets the ip check total records.
	 *
	 * @return the ip check total records
	 */
	public Integer getIpCheckTotalRecords() {
		return ipCheckTotalRecords;
	}

	/**
	 * Sets the ip check total records.
	 *
	 * @param ipCheckTotalRecords the new ip check total records
	 */
	public void setIpCheckTotalRecords(Integer ipCheckTotalRecords) {
		this.ipCheckTotalRecords = ipCheckTotalRecords;
	}
	
	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Gets the status value.
	 *
	 * @return the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the status value.
	 *
	 * @param statusValue the new status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

}
