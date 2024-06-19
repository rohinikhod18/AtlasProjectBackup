package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class BaseSearchCriteria.
 */
public class BaseSearchCriteria {
	
	/** The Constant DEFAULT_PAGE_SIZE. */
	public static final Integer DEFAULT_PAGE_SIZE = 50;
	
	/** The Constant DEFAULT_PAGE_SIZE. */
	public static final Integer DEFAULT_PAST_DATE_SIZE = 7;

	/** The Constant DEFAULT_PAGE. */
	public static final Integer DEFAULT_PAGE = 1;
	
	/** The Constant DEFAULT_COMPLIANCE_EXPIRY_YEARS. */
	public static final Integer DEFAULT_COMPLIANCE_EXPIRY_YEARS = 3;

	/** The page. */
	private Page page;
	
	/** The cust type. */
	private String custType;
	
	/** The is filter apply. */
	private Boolean isFilterApply = Boolean.FALSE;
	
	/** The is request from report page. */
	private Boolean isRequestFromReportPage = Boolean.FALSE;
	
	private Boolean isLandingPage = Boolean.FALSE;
	

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the checks if is filter apply.
	 *
	 * @return the checks if is filter apply
	 */
	public Boolean getIsFilterApply() {
		return isFilterApply;
	}

	/**
	 * Sets the checks if is filter apply.
	 *
	 * @param isFilterApply the new checks if is filter apply
	 */
	public void setIsFilterApply(Boolean isFilterApply) {
		this.isFilterApply = isFilterApply;
	}

	/**
	 * Gets the checks if is request from report page.
	 *
	 * @return the checks if is request from report page
	 */
	public Boolean getIsRequestFromReportPage() {
		return isRequestFromReportPage;
	}

	/**
	 * Sets the checks if is request from report page.
	 *
	 * @param isRequestFromReportPage the new checks if is request from report page
	 */
	public void setIsRequestFromReportPage(Boolean isRequestFromReportPage) {
		this.isRequestFromReportPage = isRequestFromReportPage;
	}
	
	/**
	 * @return the isLandingPage
	 */
	public Boolean getIsLandingPage() {
		return isLandingPage;
	}

	/**
	 * @param isLandingPage the isLandingPage to set
	 */
	public void setIsLandingPage(Boolean isLandingPage) {
		this.isLandingPage = isLandingPage;
	}
	
}
