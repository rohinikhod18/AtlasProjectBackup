package com.currenciesdirect.gtg.compliance.blacklist.dbport;

/**
 * The Enum DBQueryConstant.
 *
 * @author Rajesh
 */
public enum DBQueryConstant {

	/** The Constant INSERT_INTO_BLACKLIST_DATA. */
	INSERT_INTO_BLACKLIST_DATA("INSERT INTO BlackListData(BlackListType, Value, Created_On, Created_By, Updated_On, Updated_By) VALUES((SELECT ID FROM BlackListType where Type=?), ?, ?, ?, ?, ?)"),

	/** The Constant DELETE_BLACKLIST_DATA. */
	DELETE_BLACKLIST_DATA("DELETE FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Value=?"),

	/** The Constant UPDATE_BALCKLIST_DATA. */
	UPDATE_BALCKLIST_DATA("UPDATE BlackListData SET Value=?,Updated_On=?,Updated_By=? WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and value=?"),

	/** The Constant SEARCH_BALCKLIST_DATA. */
	SEARCH_BALCKLIST_DATA("SELECT * FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Value=?"),

	/** The Constant SEARCH_BALCKLIST_DATA_WILD_CARD. */
	SEARCH_BALCKLIST_DATA_WILD_CARD("SELECT * FROM BlackListData WHERE BlackListType=(SELECT ID FROM BlackListType WHERE Type=?) and Value LIKE ?"),

	/** The Constant GET_BLACKLIST_TYPE. */
	GET_BLACKLIST_TYPE("SELECT * FROM BlackListType");

	String query;

	DBQueryConstant(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

}

