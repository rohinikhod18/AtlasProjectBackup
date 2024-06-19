package com.currenciesdirect.gtg.compliance.core.domain.report;

import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.BaseSearchCriteria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class BeneficiaryReportSearchCriteria.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryReportSearchCriteria extends BaseSearchCriteria {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The beneficiary report filter. */
	@JsonProperty(value = "filter")
	private BeneficiaryReportFilter filter;

	/** The request source. */
	@JsonProperty(value = "request_source")
	private String requestSource;

	/** The is from clear filter. */
	private Boolean isFromClearFilter = Boolean.FALSE;

	/** The is filter apply. */
	private Boolean isFilterApply = Boolean.FALSE;

	/**
	 * Gets the filter.
	 *
	 * @return the beneficiaryReportFilter
	 */
	public BeneficiaryReportFilter getFilter() {
		return filter;
	}

	/**
	 * Sets the filter.
	 *
	 * @param beneficiaryReportFilter
	 *            the beneficiaryReportFilter to set
	 */
	public void setFilter(BeneficiaryReportFilter beneficiaryReportFilter) {
		this.filter = beneficiaryReportFilter;
	}

	/**
	 * Gets the checks if is from clear filter.
	 *
	 * @return the isFromClearFilter
	 */
	public Boolean getIsFromClearFilter() {
		return isFromClearFilter;
	}

	/**
	 * Sets the checks if is from clear filter.
	 *
	 * @param isFromClearFilter
	 *            the isFromClearFilter to set
	 */
	public void setIsFromClearFilter(Boolean isFromClearFilter) {
		this.isFromClearFilter = isFromClearFilter;
	}

	/**
	 * Gets the checks if is filter apply.
	 *
	 * @return the isFilterApply
	 */
	@Override
	public Boolean getIsFilterApply() {
		return isFilterApply;
	}

	/**
	 * Sets the checks if is filter apply.
	 *
	 * @param isFilterApply
	 *            the isFilterApply to set
	 */
	@Override
	public void setIsFilterApply(Boolean isFilterApply) {
		this.isFilterApply = isFilterApply;
	}

	/**
	 * Gets the request source.
	 *
	 * @return the request source
	 */
	public String getRequestSource() {
		return requestSource;
	}

	/**
	 * Sets the request source.
	 *
	 * @param requestSource
	 *            the new request source
	 */
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
}
