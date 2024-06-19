package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;


/**
 * The Class BaseReportDto.
 */
public class BaseReportDto {

	/** The page. */
	private Page page;
	
	/** The user. */
	private UserProfile user;

	/** The error message. */
	private String errorMessage;

	/** The error code. */
	private String errorCode;
	
	/** The source. */
	private List<String> source;
	
	/** The organization. */
	private List<String> organization;
	
	/** The currency. */
	private List<String> currency;
	
	/** The transaction value. */
	private List<String> transValue;
	
	/** The country. */
	private List<String> country;
	
	/** The date difference. */
	private Integer dateDifference;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	public List<String> getOrganization() {
		return organization;
	}

	public void setOrganization(List<String> organization) {
		this.organization = organization;
	}

	public List<String> getCurrency() {
		return currency;
	}

	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}

	public List<String> getTransValue() {
		return transValue;
	}

	public void setTransValue(List<String> transValue) {
		this.transValue = transValue;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

	/**
	 * @return the dateDifference
	 */
	public Integer getDateDifference() {
		return dateDifference;
	}

	/**
	 * @param dateDifference the dateDifference to set
	 */
	public void setDateDifference(Integer dateDifference) {
		this.dateDifference = dateDifference;
	}
}
