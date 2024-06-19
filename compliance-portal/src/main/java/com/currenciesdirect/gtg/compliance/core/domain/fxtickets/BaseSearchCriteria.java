package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class BaseSearchCriteria.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseSearchCriteria implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant DEFAULT_PAGE index. */
	public static final Integer DEFAULT_PAGE = 1;

	/** The Constant DEFAULT_PAGE_SIZE. */
	public static final Integer DEFAULT_PAGE_SIZE = 5;

	/** The Constant DEFAULT_PAST_DATE_SIZE. */
	public static final Integer DEFAULT_PAST_DATE_SIZE = 7;

	/** The Constant DEFAULT_PAGE_OFFSET. */
	public static final Integer DEFAULT_PAGE_OFFSET = 0;

	/** The Constant DEFAULT_PAGE_OFFSET_DROP_DOWN. */
	public static final Integer DEFAULT_PAGE_OFFSET_DROP_DOWN = 0;

	/** The Constant DEFAULT_PAGE_SIZE_DROP_DOWN. */
	public static final Integer DEFAULT_PAGE_SIZE_DROP_DOWN = 100;

	/** The Constant DEFAULT_SORT_FIELD. */
	public static final String DEFAULT_SORT_FIELD = "date";

	/** The Constant DEFAULT_SORT_FIELD_ORDER. */
	public static final String DEFAULT_SORT_FIELD_ORDER = "false";

	/** The Constant DEFAULT_PAGE_OFFSET_CREATE_PAYMENT_OUT. */
	public static final Integer DEFAULT_PAGE_OFFSET_CREATE_PAYMENT_OUT = 10;

	/** The Constant DEFAULT_PAGE_SIZE_CREATE_PAYMENT_OUT. */
	public static final Integer DEFAULT_PAGE_SIZE_CREATE_PAYMENT_OUT = 100;

	/** The Constant TOTAL_RECORD_FRO_PDF_DOCUMENT_THRESHOLD. */
	public static final Integer TOTAL_RECORD_FRO_PDF_DOCUMENT_THRESHOLD = 20000;

	/** The page object to hold pagination details. */
	@JsonProperty(value = "page")
	private Page page;

	/** The sort. */
	@JsonProperty(value = "sort")
	private Sort sort;
	
	/** The is filter apply. */
	private Boolean isFilterApply = Boolean.FALSE;

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
	 * @param page
	 *            the new page
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseSearchCriteria [page=");
		builder.append(page);
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Gets the sort.
	 *
	 * @return the sort
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * Sets the sort.
	 *
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	/**
	 * @return the isFilterApply
	 */
	public Boolean getIsFilterApply() {
		return isFilterApply;
	}

	/**
	 * @param isFilterApply the isFilterApply to set
	 */
	public void setIsFilterApply(Boolean isFilterApply) {
		this.isFilterApply = isFilterApply;
	}

}
