package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class CountryCheckContactResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryCheckContactResponse implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status. */
	private String status;
	
	/** The country. */
	private String country;
	
	/** The risk level. */
	private String riskLevel;
	
	/** The errorcode. */
	private String errorcode;
	
	/** The error description. */
	private String errorDescription;
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the risk level.
	 *
	 * @return the risk level
	 */
	public String getRiskLevel() {
		return riskLevel;
	}
	
	/**
	 * Sets the risk level.
	 *
	 * @param riskLevel the new risk level
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
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
	 * Gets the errorcode.
	 *
	 * @return the errorcode
	 */
	public String getErrorcode() {
		return errorcode;
	}
	
	/**
	 * Sets the errorcode.
	 *
	 * @param errorcode the new errorcode
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
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
}
