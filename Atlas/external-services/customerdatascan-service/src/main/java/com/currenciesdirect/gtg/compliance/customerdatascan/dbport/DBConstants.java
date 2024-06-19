package com.currenciesdirect.gtg.compliance.customerdatascan.dbport;

/**
 * The Enum DBConstants.
 */
public enum DBConstants {
	
	/** The version. */
	VERSION("_version"),
	
	/** The history version. */
	HISTORY_VERSION("version"),
	
	/** The created. */
	CREATED("created"),
	
	/** The type. */
	TYPE("type"),
	
	/** The error. */
	ERROR("error"),
	
	/** The hits. */
	HITS("hits"),
	
	/** The source. */
	SOURCE("_source"),
	
	/** The document already exist exception. */
	DOCUMENT_ALREADY_EXIST_EXCEPTION("document_already_exists_exception"),
	
	/** The document missing exception. */
	DOCUMENT_MISSING_EXCEPTION("document_missing_exception"),
	
	/** The whitelist index. */
	WHITELIST_INDEX("whitelist"),
	
	/** The whitelist search url. */
	WHITELIST_SEARCH_URL("whitelist/customer/_search?scroll=1m&size=50"),
	
	/** The whitelist update url. */
	WHITELIST_UPDATE_URL("whitelist/customer/{SfAccountId}/_update"),
	
	/** The whitelist create url. */
	WHITELIST_CREATE_URL("whitelist/customer/{SfAccountId}?op_type=create"),
	
	/** The whitelist delete url. */
	WHITELIST_DELETE_URL("whitelist/customer/{SfAccountId}/_update"),
	
	/** The method get. */
	GET("GET"),
	
	/** The method post. */
	POST("POST"),
	
	/** The method put. */
	PUT("PUT");
	
	/** The value. */
	private String value;
	
	/**
	 * Instantiates a new DB constants.
	 *
	 * @param value the value
	 */
	DBConstants(String value){
		this.value = value;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue(){
		return this.value;
	}
	
	/**
	 * Replace with sf account id.
	 *
	 * @param id the id
	 * @return the string
	 */
	public String replaceWithSfAccountId(String id){
		return this.getValue().replace("{SfAccountId}", id);
	}
}
