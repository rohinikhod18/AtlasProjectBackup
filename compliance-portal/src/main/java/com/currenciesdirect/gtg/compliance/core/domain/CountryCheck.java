package com.currenciesdirect.gtg.compliance.core.domain;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class CountryCheck.
 */
public class CountryCheck implements IDomain{

	private Integer id;
	
	private String  checkedOn;
	
	private String  status;
	
	private String entityType;

	private Integer countryCheckTotalRecords;
	
	private Integer passCount;
	
	private Integer failCount;
	
	//added by Vishal J 25th Jan 
	private Boolean isRequired;
	
	//added by Vishal J 25th Jan 
	private String statusValue;
	
	private String riskLevel;
	
	private String country ;
	

	public Integer getId() {
		return id;
	}

	public String getCheckedOn() {
		return checkedOn;
	}

	public String getStatus() {
		return status;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Integer getCountryCheckTotalRecords() {
		return countryCheckTotalRecords;
	}

	public void setCountryCheckTotalRecords(Integer countryCheckTotalRecords) {
		this.countryCheckTotalRecords = countryCheckTotalRecords;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
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
	 * @param b the new checks if is required
	 */
	public void setIsRequired(boolean b) {
		this.isRequired = b;
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

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
