package com.currenciesdirect.gtg.compliance.core.domain;

public class GlobalCheck {

	private Integer id;
	
	private String  checkedOn;
	
	private String  status;
	
	private String entityType;
	
	private Integer globalCheckTotalRecords;
	
	/**added by Vishal J to check whether global check is required to perform or not*/
	private Boolean isRequired;

	/**added by Vishal J to get the value of status (Eg. PASS, FAIL, NOT_REQUIRED, NOT_PERFORMED)*/
	private String statusValue;

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

	public Integer getGlobalCheckTotalRecords() {
		return globalCheckTotalRecords;
	}

	public void setGlobalCheckTotalRecords(Integer globalCheckTotalRecords) {
		this.globalCheckTotalRecords = globalCheckTotalRecords;
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
