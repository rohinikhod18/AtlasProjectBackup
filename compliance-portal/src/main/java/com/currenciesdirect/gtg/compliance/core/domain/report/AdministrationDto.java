package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class AdminstrationDto.
 */
public class AdministrationDto implements IDomain {

	private List<String> countries;
	
	private String errorCode;
	
	private String errorDescription;

	/**
	 * @return the countries
	 */
	public List<String> getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
