package com.currenciesdirect.gtg.compliance.dbport;

public enum SavedSearchQueryConstant {

	/**
	 * retrieve saved searches
	 */
	GET_SAVE_SEARCH("SELECT [SearchName] AS searchName,[SearchCriteria] AS searchCriteria FROM SavedSearch where PageType = ? AND CreatedBy = ?"),
	
	/**
	 * insert saved searches
	 */
	INSERT_INTO_SAVE_SEARCH("INSERT into SavedSearch([PageType],[SearchName],[SearchCriteria],[CreatedBy],[CreatedOn],[UpdatedBy],[UpdatedOn]) VALUES (?,?,?,?,?,?,?)"),
	
	/**
	 * delete save searches
	 */
	DELETE_FROM_SAVE_SEARCH("DELETE FROM SavedSearch WHERE PageType = ? AND CreatedBy = ? AND SearchName = ?"),
	
	/**
	 * update save search
	 */
	UPDATE_SAVE_SEARCH("UPDATE SavedSearch SET SearchCriteria = ? WHERE PageType = ? AND CreatedBy = ? AND SearchName = ?"),
	
	/** The save search count. */
	SAVE_SEARCH_COUNT("SELECT COUNT(ID) FROM SavedSearch WHERE PageType = ? AND CreatedBy = ?");
	
	private String query;
	
	/**
	 * Instantiates a new saved search query constant
	 */
	SavedSearchQueryConstant(String query){
		this.query = query;
	}
	
	/**
	 * @return
	 */
	public String getQuery() {
		return this.query;
	}
}