package com.currenciesdirect.gtg.compliance.dbport.whitelistbeneficiary;

/**
 * The Enum DBQueryConstantWhitelistBeneficiary.
 */
public enum DBQueryConstantWhitelistBeneficiary {

	/** The get whitelist beneficiary data. */
	GET_WHITELIST_BENEFICIARY_DATA("SELECT CreatedOn, FirstName, AccountNumber, Notes FROM WhiteListBeneficiary WHERE Deleted = 0"),
	
	/** The insert into whitelist beneficiary data. */
	INSERT_INTO_WHITELIST_BENEFICIARY_DATA("INSERT INTO WhiteListBeneficiary(FirstName, AccountNumber, Deleted, CreatedBy, CreatedOn, Notes) VALUES(?, ?, ?, ?, ?, ?)"),

	/** The delete whitelist beneficiary data. */
	DELETE_WHITELIST_BENEFICIARY_DATA("update WhiteListBeneficiary set Deleted = 1 WHERE FirstName = ? AND AccountNumber = ?"),
	
	/** The is whitelist beneficiary data present. */
	IS_WHITELIST_BENEFICIARY_DATA_PRESENT("SELECT ID, AccountNumber, Deleted FROM WhiteListBeneficiary WHERE AccountNumber = ?"),

	/** The update delete status for whitelist beneficiary. */
	UPDATE_DELETE_STATUS_FOR_WHITELIST_BENEFICIARY("UPDATE WhiteListBeneficiary SET Deleted = 0 where ID = ?"),
	
	/** The search whitelist beneficiary data. */
	SEARCH_WHITELIST_BENEFICIARY_DATA(" SELECT CreatedOn, FirstName, AccountNumber, Notes FROM WhiteListBeneficiary WHERE (FirstName LIKE ? OR AccountNumber LIKE ?) and Deleted = 0 ");

	
	/** The query. */
	private String query;

	/**
	 * Instantiates a new DB query constant whitelist beneficiary.
	 *
	 * @param query the query
	 */
	DBQueryConstantWhitelistBeneficiary(String query) {
		this.query = query;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return this.query;
	}


}
