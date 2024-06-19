package com.currenciesdirect.gtg.compliance.core.domain.savedsearch;

import com.currenciesdirect.gtg.compliance.core.domain.BaseSearchCriteria;

public class SavedSearchRequest extends BaseSearchCriteria{

	private SavedSearchCommonFilter filter;
	
	private String saveSearchName;
	
	private String pageType;
	
	private Boolean isFromClearFilter = Boolean.FALSE;

	/**
	 * @return the saveSearchName
	 */
	public String getSaveSearchName() {
		return saveSearchName;
	}

	/**
	 * @param saveSearchName the saveSearchName to set
	 */
	public void setSaveSearchName(String saveSearchName) {
		this.saveSearchName = saveSearchName;
	}

	/**
	 * @return the filter
	 */
	public SavedSearchCommonFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(SavedSearchCommonFilter filter) {
		this.filter = filter;
	}

	/**
	 * @return the isFromClearFilter
	 */
	public Boolean getIsFromClearFilter() {
		return isFromClearFilter;
	}

	/**
	 * @param isFromClearFilter the isFromClearFilter to set
	 */
	public void setIsFromClearFilter(Boolean isFromClearFilter) {
		this.isFromClearFilter = isFromClearFilter;
	}

	/**
	 * @return the pageType
	 */
	public String getPageType() {
		return pageType;
	}

	/**
	 * @param pageType the pageType to set
	 */
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
}
