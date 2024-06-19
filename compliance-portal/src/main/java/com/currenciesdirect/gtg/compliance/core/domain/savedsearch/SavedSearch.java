package com.currenciesdirect.gtg.compliance.core.domain.savedsearch;

import java.util.List;

public class SavedSearch {
	
	private List<SavedSearchData> savedSearchData;

	/**
	 * @return the savedSearchData
	 */
	public List<SavedSearchData> getSavedSearchData() {
		return savedSearchData;
	}

	/**
	 * @param savedSearchData the savedSearchData to set
	 */
	public void setSavedSearchData(List<SavedSearchData> savedSearchData) {
		this.savedSearchData = savedSearchData;
	}
}
